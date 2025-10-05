package com.svc.ventas.service.impl;

import com.svc.ventas.exception.EntityNotFoundException;
import com.svc.ventas.message.response.Response;
import com.svc.ventas.models.dao.CompraRepository;
import com.svc.ventas.models.dao.ProductoCompradoRepository;
import com.svc.ventas.models.entity.Compra;
import com.svc.ventas.models.entity.ProductoComprado;
import com.svc.ventas.models.entity.Proveedor;
import com.svc.ventas.models.mapstruct.dto.CompraDto;
import com.svc.ventas.models.mapstruct.dto.CompraGetDto;
import com.svc.ventas.models.mapstruct.dto.ProductoGetDTO;
import com.svc.ventas.models.mapstruct.dto.UsuarioGetDto;
import com.svc.ventas.models.mapstruct.mappers.*;
import com.svc.ventas.service.IProductoService;
import com.svc.ventas.service.ITipoDocumentoService;
import com.svc.ventas.service.IUsuarioService;
import com.svc.ventas.util.AppUtils;
import com.svc.ventas.util.Constantes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.svc.ventas.service.ICompraService;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompraServiceImpl implements ICompraService {

	private final ProductoCompradoRepository productoCompradoRepo;

	private final CompraRepository compraRepo;

	private final ITipoDocumentoService tipoDocumentoService;

	private final IUsuarioService usuarioService;

	private final IProductoService productoService;

	private final ProveedorMapper proveedorMapper;

	private final ProductoMapper productoMapper;

	private final UsuarioMapper usuarioMapper;

	private final TipoDocumentoMapper tipoDocumentoMapper;

	private final CompraMapper compraMapper;

	@Transactional
	@Override
	public Response registrar(CompraDto compra) {

		log.info("Busca proveedor :: ");

		log.info("Busca tipo de documento existente :: ");
		tipoDocumentoService.obtener(compra.getTipoDocumento().getIdTipoDocumento());

		log.info("Obtiene usuario logueado :: ");
		UsuarioGetDto usuarioLogueado = usuarioService.obtener(2);

		log.info("Registra los datos del comprobante :: ");
		Compra compraNew = Compra.builder()
				.fecha(AppUtils.convert(compra.getFecha()))
				.serie(compra.getSerie())
				.correlativo(compra.getCorrelativo())
				.tipoDocumento(tipoDocumentoMapper.mapTipoDocumento(compra.getTipoDocumento()))
				.proveedor(Proveedor.builder().idProveedor(compra.getProveedor().getIdProveedor()).build())
				.igv(compra.getIgv())
				.subTotal(compra.getSubTotal())
				.total(compra.getTotal())
				.estado(Constantes.STATUS_CREADO)
				.usuRegistro(usuarioMapper.mapToUsuarioGet(usuarioLogueado))
				.build();

		log.info("Registra los productos a comprar :: ");
		compra.getProductos()
				.forEach(ppc -> {
					log.info("Busca producto y actualiza el stock del producto ::");
					ProductoGetDTO productoBD = productoService.obtener(ppc.getIdProducto());
					productoBD.sumarStock(ppc.getCantidad());

					productoService.modificar(productoBD.getIdProducto(), productoMapper.mapToGet(productoBD));

					productoCompradoRepo.save(ProductoComprado
							.builder()
							.idProducto(productoBD.getIdProducto())
							.nombre(productoBD.getNombre())
							.precio(productoBD.getPrecio())
							.cantidad(ppc.getCantidad())
							.compra(compraRepo.save(compraNew))
							.build());
				});

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

}
