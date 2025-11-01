package com.svc.ventas.service.impl;

import com.svc.ventas.exception.EntityNotFoundException;
import com.svc.ventas.message.request.ProductoParaComprar;
import com.svc.ventas.message.response.Response;
import com.svc.ventas.models.dao.CompraRepository;
import com.svc.ventas.models.dao.ProductoCompradoRepository;
import com.svc.ventas.models.entity.Compra;
import com.svc.ventas.models.entity.ProductoComprado;
import com.svc.ventas.models.entity.Proveedor;
import com.svc.ventas.models.mapstruct.dto.*;
import com.svc.ventas.models.mapstruct.mappers.*;
import com.svc.ventas.service.*;
import com.svc.ventas.util.AppUtils;
import com.svc.ventas.util.Constantes;
import com.svc.ventas.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompraServiceImpl implements ICompraService {

	private final ProductoCompradoRepository productoCompradoRepo;

	private final CompraRepository compraRepo;

	private final ITipoDocumentoService tipoDocumentoService;

	private final IProductoService productoService;

	private final IProveedorService proveedorService;

	private final ProductoMapper productoMapper;

	private final UsuarioMapper usuarioMapper;

	private final TipoDocumentoMapper tipoDocumentoMapper;

	private final CompraMapper compraMapper;

	private final SecurityUtils securityUtils;

	@Transactional
	@Override
	public Response registrar(CompraDto compra) {

		log.info("Iniciando registro de compra...");

		log.info("Busca proveedor :: ");
		proveedorService.obtener(compra.getProveedor().getIdProveedor());

		log.info("Busca tipo de documento existente :: ");
		tipoDocumentoService.obtener(compra.getTipoDocumento().getIdTipoDocumento());

		log.info("Obtiene usuario logueado :: ");
		UsuarioDto usuarioLogueado =  securityUtils.obtenerUsuarioLogueado();

		log.info("Valida montos ::");
		CompraMontosDto compraMontosDto = validarYCalcularMontos(compra);

		log.info("Registra los datos del comprobante :: ");

		Compra compraNew = Compra.builder()
				.fecha(AppUtils.convert(compra.getFecha()))
				.serie(compra.getSerie())
				.correlativo(compra.getCorrelativo())
				.tipoDocumento(tipoDocumentoMapper.mapTipoDocumento(compra.getTipoDocumento()))
				.proveedor(Proveedor.builder().idProveedor(compra.getProveedor().getIdProveedor()).build())
				.tipoPago(compra.getTipoPago())
				.igv(compraMontosDto.getIgv())
				.subTotal(compraMontosDto.getSubTotal())
				.total(compraMontosDto.getTotal())
				.estado(Constantes.STATUS_CREADO)
				.usuRegistro(usuarioMapper.mapToUsuarioGet(usuarioLogueado))
				.build();

		Compra compraEntity = compraRepo.save(compraNew);
    log.info("Compra guardada con ID: {}", compraEntity.getIdCompra());

		log.info("Registra los productos a comprar :: ");

		compra.getProductos()
				.forEach(ppc -> {
					log.info("Busca producto y actualiza el stock del producto ::");
					ProductoDTO productoBD = productoService.obtener(ppc.getIdProducto());
					productoBD.sumarStock(ppc.getCantidad());

					productoService.modificar(productoBD.getIdProducto(), productoMapper.mapToGet(productoBD));

					productoCompradoRepo.save(ProductoComprado
							.builder()
							.compra(compraEntity)
							.idProducto(productoBD.getIdProducto())
							.nombre(productoBD.getNombre())
							.precio(productoBD.getPrecio())
							.cantidad(ppc.getCantidad())
							.build());
				});

		String numeroDocumento = compra.getSerie() + "-" + compra.getCorrelativo();
		log.info("Compra registrada correctamente con número {}", numeroDocumento);

		return Response
				.builder()
				.mensaje(Constantes.MENSAJE_SAVE)
				.build();

	}

	@Override
	public List<CompraGetDto> listado(Boolean isViewMore) {
		LocalDate dateToday = LocalDate.now();
		LocalDate sevenDaysAgo = dateToday.minusWeeks(1);

		return compraRepo.findAll().stream()
				.filter(compra -> {
					if (isViewMore) {
						return compra.getFecha().isAfter(sevenDaysAgo.minusDays(1)) && compra.getFecha().isBefore(dateToday.plusDays(1));
					} else {
						return compra.getFecha().isEqual(dateToday);
					}
				})
				.map(compraMapper::mapCompraToDto)
				.collect(Collectors.toList());
	}

	@Override
	public Object details(Long id) {
		return compraRepo.findById(id)
				.map(compraMapper::mapCompraToDto)
				.orElseThrow(() -> new EntityNotFoundException(String.format(Constantes.MENSAJE_NOT_FOUND, "Compra", id)));

	}

	/**
	 * Valida que los montos enviados por el cliente coincidan con los calculados
	 * y devuelve los valores correctos desde backend.
	 */
	private CompraMontosDto validarYCalcularMontos(CompraDto compra) {

		BigDecimal subtotalCalculado = BigDecimal.ZERO;
		BigDecimal porcentajeIGV = new BigDecimal("0.18");

		for (ProductoParaComprar p : compra.getProductos()) {
			ProductoDTO productoBD = productoService.obtener(p.getIdProducto());

			if (Objects.isNull(p.getCantidad()) || p.getCantidad() <= 0) {
				throw new IllegalArgumentException("Cantidad inválida para el producto ID: " + p.getIdProducto());
			}

			// Subtotal del producto según su precio real
			BigDecimal subtotalProducto = productoBD.getPrecio()
							.multiply(BigDecimal.valueOf(p.getCantidad()));

			subtotalCalculado = subtotalCalculado.add(subtotalProducto);
		}

		BigDecimal igvCalculado = BigDecimal.ZERO;
		BigDecimal totalCalculado = subtotalCalculado;

		//if (Boolean.TRUE.equals(compra.getAplicarImpuesto())) {
			igvCalculado = subtotalCalculado.multiply(porcentajeIGV).setScale(2, RoundingMode.HALF_UP);
			totalCalculado = subtotalCalculado.add(igvCalculado);
		//}

		if (Objects.isNull(compra.getSubTotal()) ||
						compra.getSubTotal().setScale(2, RoundingMode.HALF_UP).compareTo(subtotalCalculado) != 0) {
			log.info("subtotal servidor: {}", subtotalCalculado);
			throw new IllegalArgumentException("El subtotal no coincide con el cálculo del servidor.");
		}

		if (Objects.isNull(compra.getIgv()) ||
						compra.getIgv().setScale(2, RoundingMode.HALF_UP).compareTo(igvCalculado) != 0) {
			log.info("IGV servidor: {}", igvCalculado);
			throw new IllegalArgumentException("El IGV no coincide con el cálculo del servidor.");
		}

		if (Objects.isNull(compra.getTotal()) ||
						compra.getTotal().setScale(2, RoundingMode.HALF_UP).compareTo(totalCalculado) != 0) {
			log.info("total servidor: {}", totalCalculado);
			throw new IllegalArgumentException("El total no coincide con el cálculo del servidor.");
		}

		log.info("Montos validados correctamente: Subtotal={}, IGV={}, Total={}",
						subtotalCalculado, igvCalculado, totalCalculado);

		return new CompraMontosDto(subtotalCalculado, igvCalculado, totalCalculado);
	}


}
