package com.svc.ventas.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import com.svc.ventas.config.AppContext;
import com.svc.ventas.message.request.ProductoRequest;
import com.svc.ventas.models.dao.*;
import com.svc.ventas.models.entity.*;
import com.svc.ventas.models.mapstruct.dto.ProductoDTO;
import com.svc.ventas.models.mapstruct.dto.ProductoDetailsDTO;
import com.svc.ventas.models.mapstruct.mappers.SucursalMapper;
import com.svc.ventas.models.specifications.ProductSpecifications;

import com.svc.ventas.service.ISucursalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.svc.ventas.exception.EntityNotFoundException;
import com.svc.ventas.message.response.ProductoSearchResponse;
import com.svc.ventas.message.response.Response;
import com.svc.ventas.models.mapstruct.mappers.ProductoMapper;
import com.svc.ventas.service.IProductoService;
import com.svc.ventas.util.Constantes;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductoServiceImpl implements IProductoService {

  private final ProductoRepo productoRepo;

  private final ProductoPresentacionRepo productoPresentacionRepo;

  private final ProductoStockPresentacionRepo ProductoStockPresentacionRepo;

  private final ProductoStockRepo productoStockRepo;

  private final ProductoMapper productoMapper;

  private final MarcaRepo marcaRepo;

  private final UnidadMedidaRepo unidadMedidaRepo;

  private final CategoriaRepo categoriaRepo;

  private final ISucursalService sucursalService;

  private final SucursalMapper sucursalMapper;

  private final AppContext appContext;

  @Override
  public List<ProductoDTO> lista() {
    return productoRepo.listaActivos()
            .stream()
            .map(productoMapper::map)
            .collect(Collectors.toList());
  }

  @Transactional
  @Override
  public Response agregar(ProductoRequest producto) {
    log.info("Iniciando registro de producto...");

    log.info("Se obtiene usuario logueado...");
    Empresa empresa = appContext.getEmpresa();

    log.info("Obtener Marca ::");
    Marca marca = marcaRepo.findById(producto.getIdMarca())
            .orElseThrow(() -> new EntityNotFoundException(String.format(Constantes.MENSAJE_NOT_FOUND, "Marca",
                    producto.getIdMarca())));

    log.info("Obtener Categoria ::");
    Categoria categoria = categoriaRepo.findById(producto.getIdCategoria())
            .orElseThrow(() -> new EntityNotFoundException(String.format(Constantes.MENSAJE_NOT_FOUND, "Categoria",
                    producto.getIdCategoria())));

    log.info("Obtener Unidad Medida ::");
    UnidadMedida unidadMedida = unidadMedidaRepo.findById(producto.getIdUnidadMedida())
            .orElseThrow(() -> new EntityNotFoundException(String.format(Constantes.MENSAJE_NOT_FOUND, "UnidadMedida",
                    producto.getIdUnidadMedida())));

    Producto productoNew = productoMapper.mapToProducto(producto, marca, categoria, unidadMedida, empresa);

    log.info("Registrar producto ::");
    Producto productoSave = productoRepo.save(productoNew);

    log.info("Obtener presentaciones según tipo de unidad de medida ::");
    List<ProductoPresentacion> productoPresentaciones = generarPresentacionesPorUnidad(productoSave);

    log.info("guardar presentaciones");
    productoPresentacionRepo.saveAll(productoPresentaciones);

    log.info("replicar productos y presentaciones para cada sucursal");
    registrarProductosEnSucursales(productoSave, productoPresentaciones);

    return Response
            .builder()
            .mensaje(Constantes.MENSAJE_SAVE)
            .build();
  }

  @Override
  public Response modificar(Long id, ProductoRequest producto) {

    log.info("Iniciando modificación de producto...");

    log.info("Valida si existe producto...");
    Producto productoBD = productoRepo.findById(id)
            .orElseThrow(() -> new EntityNotFoundException(String.format(Constantes.MENSAJE_NOT_FOUND, "Producto", id)));

    log.info("Obtener Marca ::");
    Marca marca = marcaRepo.findById(producto.getIdMarca())
            .orElseThrow(() -> new EntityNotFoundException(String.format(Constantes.MENSAJE_NOT_FOUND, "Marca",
                    producto.getIdMarca())));

    log.info("Obtener Categoria ::");
    Categoria categoria = categoriaRepo.findById(producto.getIdCategoria())
            .orElseThrow(() -> new EntityNotFoundException(String.format(Constantes.MENSAJE_NOT_FOUND, "Categoria",
                    producto.getIdCategoria())));

    log.info("Obtener Unidad Medida ::");
    UnidadMedida unidadMedida = unidadMedidaRepo.findById(producto.getIdUnidadMedida())
            .orElseThrow(() -> new EntityNotFoundException(String.format(Constantes.MENSAJE_NOT_FOUND, "UnidadMedida",
                    producto.getIdUnidadMedida())));

    productoBD.setNombre(producto.getNombre());
    productoBD.setDescripcion(producto.getDescripcion());
    productoBD.setImagen(producto.getImagen());
    productoBD.setMarca(marca);
    productoBD.setCategoria(categoria);
    productoBD.setUnidadMedida(unidadMedida);
    productoRepo.save(productoBD);

    return Response
            .builder()
            .mensaje(Constantes.MENSAJE_MOD)
            .build();
  }

  @Override
  public ProductoDTO obtener(Long id) {
    return productoRepo.findById(id)
            .map(productoMapper::map)
            .orElseThrow(() -> new EntityNotFoundException(String.format(Constantes.MENSAJE_NOT_FOUND, "Producto", id)));
  }

  @Override
  public ProductoDetailsDTO details(Long id) {
    return productoRepo.findById(id)
            .map(productoMapper::mapDetails)
            .orElseThrow(() -> new EntityNotFoundException(String.format(Constantes.MENSAJE_NOT_FOUND, "Producto", id)));
  }

  @Override
  public void eliminar(Long id) {
    Producto productoSave = productoRepo.findById(id)
            .orElseThrow(() -> new EntityNotFoundException(String.format(Constantes.MENSAJE_NOT_FOUND, "Producto", id)));

    productoSave.setIndEstado(Constantes.IND_INACTIVO);
    productoRepo.save(productoSave);
  }

  /**
   * Se listan los productos al realizar la compra
   * @param nombre
   * @param categoriaId
   * @param estado
   * @param page
   * @param size
   * @return Map<String, Object>
   */
  @Override
  public Map<String, Object> searchProductos(String nombre, Integer categoriaId,
                                             Boolean estado, int page, int size) {

    Specification<Producto> spec = Specification.where(null);
    if (Objects.nonNull(nombre) && !nombre.isEmpty()) {
      spec = spec.and(ProductSpecifications.hasName(nombre));
    }

    if (Objects.nonNull(categoriaId)) {
      spec = spec.and(ProductSpecifications.hasCategory(categoriaId));
    }

    if (Objects.nonNull(estado)) {
      spec = spec.and(ProductSpecifications.hasStatus(estado));
    }

    Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "fecAdd"));

    Page<Producto> pageProductos = productoRepo.findAll(spec, pageable);

    List<ProductoSearchResponse> listProducts = pageProductos.getContent()
            .stream()
            .map(productoMapper::mapProductoSearch)
            .toList();
    return Map.of(
            "products", listProducts,
            "currentPage", pageProductos.getNumber(),
            "pageSize", pageProductos.getSize(),
            "totalItems", pageProductos.getTotalElements(),
            "totalPages", pageProductos.getTotalPages(),
            "empty", pageProductos.isEmpty()
    );
  }

  @Override
  public void updateEstado(Long idProducto) {
    productoRepo.findById(idProducto)
            .map(p -> {
              Boolean estado = !p.getIndEstado();
              p.setIndEstado(estado);
              return productoRepo.save(p);
            }).orElseThrow(() -> new EntityNotFoundException
                    (String.format(Constantes.MENSAJE_NOT_FOUND, "Producto", idProducto)));
  }

  public Page<ProductoSearchResponse> buscarPorNombreOCodigo(String termino, Pageable pageable) {
    return productoStockRepo.buscarPorNombreOCodigoPage(termino, pageable)
            .map(productoMapper::mapToSearch);
  }

  private void registrarProductosEnSucursales(Producto productoNew, List<ProductoPresentacion> productoPresentaciones) {

    log.info("Obtiene sucursales por empresa ::");
    List<Sucursal> sucursales = sucursalService.lista()
            .stream().map(sucursalMapper::mapToSucursalPost)
            .toList();

    log.info("Registrando stock en {} sucursales", sucursales.size());
    sucursales.forEach(sucursal -> {
      ProductoStock productoStockNew = ProductoStock
              .builder()
              .producto(productoNew)
              .stock(BigDecimal.ZERO)
              .minCantidad(5)
              .maxCantidad(100)
              .costoPromedio(BigDecimal.ZERO)
              .sucursal(sucursal)
              .estado(Constantes.IND_ACTIVO)
              .build();

      ProductoStock productoStockBD = productoStockRepo.save(productoStockNew);

      log.info("Nro presentaciones {} para productoStock {} para la sucursal {} ::",
              productoPresentaciones.size(), productoStockBD.getIdProductoStock(), sucursal.getIdSucursal());

      List<ProductoStockPresentacion> productoStockPresentaciones = productoPresentaciones
              .stream()
              .map(pp -> ProductoStockPresentacion
                      .builder()
                      .productoStock(productoStockBD)
                      .equivalencia(pp.getEquivalencia())
                      .nombre(pp.getNombre())
                      .precioVenta(BigDecimal.ZERO)
                      .isPrincipal(pp.getIsPrincipal())
                      .precioSugerido(BigDecimal.ZERO)
                      .estado(Boolean.TRUE)
                      .build()).toList();

      ProductoStockPresentacionRepo.saveAll(productoStockPresentaciones);
    });
  }

  private List<ProductoPresentacion> generarPresentacionesPorUnidad(Producto producto) {

    List<ProductoPresentacion> lista = new ArrayList<>();

    String unidad = producto.getUnidadMedida().getNombre();

    if (Boolean.FALSE.equals(producto.getManejaPresentaciones())) {

      lista.add(buildPrincipal(producto, unidad, BigDecimal.ONE));
      return lista;
    }

    switch (producto.getUnidadMedida().getNombre()) {

      case "UNIDAD" -> {
        lista.add(build(producto, "UNIDAD", BigDecimal.ONE));
        lista.add(build(producto, "PACK 3", new BigDecimal("3")));
        lista.add(build(producto, "PACK 6", new BigDecimal("6")));
        lista.add(build(producto, "PACK 12", new BigDecimal("12")));
      }

      case "KILO" -> {
        lista.add(build(producto, "KILO", BigDecimal.ONE));
        lista.add(build(producto, "1/2 KILO", new BigDecimal("0.5")));
        lista.add(build(producto, "1/4 KILO", new BigDecimal("0.25")));
      }

      case "LITRO" -> {
        lista.add(build(producto, "1 LITRO", BigDecimal.ONE));
        lista.add(build(producto, "1/2 LITRO", new BigDecimal("0.5")));
        lista.add(build(producto, "2 LITROS", new BigDecimal("2")));
      }
    }

    return lista;
  }

  private ProductoPresentacion buildPrincipal(Producto producto,
                                              String nombre,
                                              BigDecimal equivalencia) {

    ProductoPresentacion p = build(producto, nombre, equivalencia);
    p.setIsPrincipal(true);
    return p;
  }

  private ProductoPresentacion build(Producto producto, String nombre, BigDecimal equivalencia) {

    ProductoPresentacion p = new ProductoPresentacion();
    p.setProducto(producto);
    p.setNombre(nombre);
    p.setEstado(Boolean.TRUE);
    p.setEquivalencia(equivalencia);
    Boolean isPrincipal = (equivalencia.compareTo(BigDecimal.ONE) == 0);
    p.setIsPrincipal(isPrincipal);
    return p;
  }

}
