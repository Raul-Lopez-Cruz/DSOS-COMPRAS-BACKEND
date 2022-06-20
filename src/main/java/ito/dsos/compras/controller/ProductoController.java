package ito.dsos.compras.controller;

import ito.dsos.compras.authentication.Authentication;
import ito.dsos.compras.exceptions.ExternalMicroserviceException;
import ito.dsos.compras.exceptions.UnauthorizedException;
import ito.dsos.compras.model.*;
import ito.dsos.compras.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("api/v1")
@CrossOrigin(origins = "*")
public class ProductoController {

    HashMap<String, Object> response;

    @Autowired
    private ProductoService productoService;

    @Autowired
    private ModeloService modeloService;

    @Autowired
    private MarcaService marcaService;

    @Autowired
    private CompraService compraService;

    @Autowired
    private DetalleCompraService detalleCompraService;

    @Autowired
    private Authentication authentication;

    /**
     * Obtiene todos los modelos con el id indicado
     *
     * @param idModelo
     * @return
     */
    @GetMapping("/producto/modelo/{idModelo}")
    public ResponseEntity<HashMap<String, Object>> getAllProductoModel(@PathVariable String idModelo) {
        response = new HashMap<>();
        try {
            Optional<ModeloModel> modeloModel = modeloService.getById(Integer.parseInt(idModelo));
            if (modeloModel.isPresent()) {

                response.put("httpCode", HttpStatus.OK.value());
                response.put("data", productoService.getAllModelo(modeloModel.get()));
                response.put("message", HttpStatus.OK.getReasonPhrase() + ": Productos encontrado");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            response.put("httpCode", HttpStatus.NOT_FOUND.value());
            response.put("data", null);
            response.put("message", HttpStatus.NOT_FOUND.getReasonPhrase() + ": Sin productos");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);

        } catch (Exception ex) {
            response.put("httpCode", HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.put("message", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Ontiene todos los modelos con el id indicado
     *
     * @return
     */
    @GetMapping("/compras/marca/{idMarca}")
    public ResponseEntity<HashMap<String, Object>> getAllProductoMarca(@PathVariable String idMarca) {
        response = new HashMap<>();
        try {
            Optional<MarcaModel> marcaModel = marcaService.getById(Integer.parseInt(idMarca));
            if (marcaModel.isPresent()) {

                response.put("httpCode", HttpStatus.OK.value());
                response.put("data", productoService.getAllMarca(marcaModel.get()));
                response.put("message", HttpStatus.OK.getReasonPhrase() + ": Productos encontrado");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            response.put("httpCode", HttpStatus.NOT_FOUND.value());
            response.put("data", null);
            response.put("message", HttpStatus.NOT_FOUND.getReasonPhrase() + ": Sin productos");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            response.put("httpCode", HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.put("message", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/compras/MM/{idModelo}/{idMarca}")
    public ResponseEntity<HashMap<String, Object>> getAllProductoMM(@PathVariable String idModelo, @PathVariable String idMarca) {
        response = new HashMap<>();
        try {
            Optional<ModeloModel> modeloModel = modeloService.getById(Integer.parseInt(idModelo));
            Optional<MarcaModel> marcaModel = marcaService.getById(Integer.parseInt(idMarca));
            if (marcaModel.isPresent() && modeloModel.isPresent()) {
                response.put("httpCode", HttpStatus.OK.value());
                response.put("data", productoService.getAllModeloMarca(modeloModel.get(), marcaModel.get()));
                response.put("message", HttpStatus.OK.getReasonPhrase() + ": Productos encontrado");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            response.put("httpCode", HttpStatus.NOT_FOUND.value());
            response.put("data", null);
            response.put("message", HttpStatus.NOT_FOUND.getReasonPhrase() + ": Sin productos");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            response.put("httpCode", HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.put("message", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Devuelve todos los productos
    @GetMapping("/producto/")
    public ResponseEntity<HashMap<String, Object>> getAll() {
        response = new HashMap<>();
        try {
            response.put("httpCode", HttpStatus.OK.value());
            response.put("data", productoService.getAll());
            response.put("message", HttpStatus.OK.getReasonPhrase() + ": Producto encontrado");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            response.put("httpCode", HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.put("message", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Devuelve un producto por su id
    @GetMapping("/producto/{id}")
    public ResponseEntity<HashMap<String, Object>> get(@PathVariable String id) {
        response = new HashMap<>();
        try {
            Optional<ProductoModel> productoModel = productoService.getById(Integer.parseInt(id));
            if (productoModel.isPresent()) {
                response.put("httpCode", HttpStatus.OK.value());
                response.put("data", productoModel);
                response.put("message", HttpStatus.OK.getReasonPhrase() + ": Producto encontrado");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            response.put("httpCode", HttpStatus.NOT_FOUND.value());
            response.put("data", null);
            response.put("message", HttpStatus.NOT_FOUND.getReasonPhrase() + ": El producto no existe");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            response.put("httpCode", HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.put("message", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Crea un producto
    @PostMapping("/producto/")
    public ResponseEntity<HashMap<String, Object>> post(@RequestBody ProductoModel producto, HttpServletRequest request) {
        response = new HashMap<>();
        try {
            authentication.auth(request);
            //valueResponse = ResponseEntity.status(HttpStatus.NO_CONTENT).build();

            if (producto.getPrecioCompra() == null || producto.getStock() == null
                    || producto.getColor() == null || producto.getMarca() == null || producto.getTalla() == null || producto.getModelo() == null
                    || producto.getPrecioVenta() == null) {
                response.put("httpCode", HttpStatus.NOT_FOUND.value());
                response.put("message", HttpStatus.NOT_FOUND.getReasonPhrase() + ": Uno o más campos están vacíos");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            } else {
                productoService.createProducto(producto);
                response.put("httpCode", HttpStatus.CREATED.value());
                response.put("data", producto);
                response.put("message", HttpStatus.CREATED.getReasonPhrase() + ": El producto se ha creado correctamente");
                return new ResponseEntity<>(response, HttpStatus.CREATED);
            }

        } catch (UnauthorizedException ex) {
            response.put("httpCode", HttpStatus.UNAUTHORIZED.value());
            response.put("data", ex.toJSON());
            response.put("message", HttpStatus.UNAUTHORIZED.getReasonPhrase());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);

        } catch (ExternalMicroserviceException ex) {
            response.put("httpCode", HttpStatus.SERVICE_UNAVAILABLE.value());
            response.put("data", ex.toJSON());
            response.put("message", HttpStatus.SERVICE_UNAVAILABLE.getReasonPhrase());
            return new ResponseEntity<>(response, HttpStatus.SERVICE_UNAVAILABLE);
        } catch (Exception ex) {
            response.put("httpCode", HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.put("message", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Actualiza un producto (todos los atributos)
    @Transactional
    @PutMapping("/producto/{id}")
    public ResponseEntity<HashMap<String, Object>> put(@RequestBody ProductoModel producto, @PathVariable String id, HttpServletRequest request) {
        response = new HashMap<>();
        try {
            authentication.auth(request);

            Optional<ProductoModel> productoModel = productoService.getById(Integer.parseInt(id));
            //check if producto exists
            if (productoModel.isPresent()) {
                //if attributes are null, they will be set to the previous values
                if (producto.getPrecioCompra() == null) producto.setPrecioCompra(productoModel.get().getPrecioCompra());
                if (producto.getStock() == null) producto.setStock(productoModel.get().getStock());
                if (producto.getColor() == null) producto.setColor(productoModel.get().getColor());
                if (producto.getMarca() == null) producto.setMarca(productoModel.get().getMarca());
                if (producto.getTalla() == null) producto.setTalla(productoModel.get().getTalla());
                if (producto.getModelo() == null) producto.setModelo(productoModel.get().getModelo());
                if (producto.getPrecioVenta() == null) producto.setPrecioVenta(productoModel.get().getPrecioVenta());

                    productoService.update(producto, Integer.parseInt(id));
                    response.put("httpCode", HttpStatus.OK.value());
                    response.put("data", producto);
                    response.put("message", HttpStatus.OK.getReasonPhrase() + ": El producto se ha actualizado correctamente");
                    return new ResponseEntity<>(response, HttpStatus.OK);

            }
            response.put("httpCode", HttpStatus.NOT_FOUND.value());
            response.put("data", null);
            response.put("message", HttpStatus.NOT_FOUND.getReasonPhrase() + ": El producto no existe");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);

        } catch (UnauthorizedException ex) {
            response.put("httpCode", HttpStatus.UNAUTHORIZED.value());
            response.put("data", ex.toJSON());
            response.put("message", HttpStatus.UNAUTHORIZED.getReasonPhrase());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);

        } catch (ExternalMicroserviceException ex) {
            response.put("httpCode", HttpStatus.SERVICE_UNAVAILABLE.value());
            response.put("data", ex.toJSON());
            response.put("message", HttpStatus.SERVICE_UNAVAILABLE.getReasonPhrase());
            return new ResponseEntity<>(response, HttpStatus.SERVICE_UNAVAILABLE);

        } catch (Exception ex) {
            response.put("httpCode", HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.put("message", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Actualiza las existencias de un producto
    @Transactional
    @PutMapping("/producto/vender/{id}/{unidades}")
    public ResponseEntity<HashMap<String, Object>> vender(@PathVariable String id, @PathVariable String unidades, HttpServletRequest request) {
        response = new HashMap<>();
        try {
            authentication.auth(request);
            Optional<ProductoModel> productoModel = productoService.getById(Integer.parseInt(id));
            if (productoModel.isPresent()) {
                //check for stock
                ProductoModel producto = productoModel.get();
                //if stock is not enough
                if (producto.getStock() < Integer.parseInt(unidades)) {
                    response.put("httpCode", HttpStatus.UNPROCESSABLE_ENTITY.value());
                    response.put("message", HttpStatus.UNPROCESSABLE_ENTITY.getReasonPhrase() + ": No hay suficiente stock");
                    return new ResponseEntity<>(response, HttpStatus.UNPROCESSABLE_ENTITY);
                }
                //if stock is enough, update stock
                productoService.vender(Integer.parseInt(id), Integer.parseInt(unidades));
                HashMap<String, Object> data = new HashMap<>();
                //put into data stock and id
                data.put("stock", producto.getStock());
                data.put("id", producto.getIdProducto());
                response.put("httpCode", HttpStatus.OK.value());
                response.put("data", data);
                response.put("message", HttpStatus.OK.getReasonPhrase() + ": Se ha vendido " + unidades + " unidades");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            //If producto does not exist
            response.put("httpCode", HttpStatus.NOT_FOUND.value());
            response.put("message", HttpStatus.NOT_FOUND.getReasonPhrase() + ": El producto no existe");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (UnauthorizedException ex) {
            response.put("httpCode", HttpStatus.UNAUTHORIZED.value());
            response.put("data", ex.toJSON());
            response.put("message", HttpStatus.UNAUTHORIZED.getReasonPhrase());
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);

        } catch (ExternalMicroserviceException ex) {
            response.put("httpCode", HttpStatus.SERVICE_UNAVAILABLE.value());
            response.put("data", ex.toJSON());
            response.put("message", HttpStatus.SERVICE_UNAVAILABLE.getReasonPhrase());
            return new ResponseEntity<>(response, HttpStatus.SERVICE_UNAVAILABLE);
        } catch (Exception ex) {
            response.put("httpCode", HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.put("message", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Actualiza las existencias de un producto
    @Transactional
    @PutMapping("/producto/devolver/{id}/{unidades}")
    public ResponseEntity<HashMap<String, Object>> devolver(@PathVariable String id, @PathVariable String unidades, HttpServletRequest request) {
        response = new HashMap<>();
        try {
            authentication.auth(request);
            //check if producto exists
            Optional<ProductoModel> productoModel = productoService.getById(Integer.parseInt(id));
            if (productoModel.isPresent()) {
                ProductoModel producto = productoModel.get();
                //update stock
                productoService.devolver(Integer.parseInt(id), Integer.parseInt(String.valueOf(unidades)));
                HashMap<String, Object> data = new HashMap<>();
                //put into data stock and id
                data.put("stock", producto.getStock());
                data.put("id", producto.getIdProducto());
                response.put("httpCode", HttpStatus.OK.value());
                response.put("data", data);
                response.put("message", HttpStatus.OK.getReasonPhrase() + ": Se ha devuelto " + unidades + "unidad(es) al inventario.");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            //If producto does not exist
            response.put("httpCode", HttpStatus.NOT_FOUND.value());
            response.put("message", HttpStatus.NOT_FOUND.getReasonPhrase() + ": El producto no existe");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (UnauthorizedException ex) {
            response.put("httpCode", HttpStatus.UNAUTHORIZED.value());
            response.put("data", ex.toJSON());
            response.put("message", HttpStatus.UNAUTHORIZED.getReasonPhrase());
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);

        } catch (ExternalMicroserviceException ex) {
            response.put("httpCode", HttpStatus.SERVICE_UNAVAILABLE.value());
            response.put("data", ex.toJSON());
            response.put("message", HttpStatus.SERVICE_UNAVAILABLE.getReasonPhrase());
            return new ResponseEntity<>(response, HttpStatus.SERVICE_UNAVAILABLE);
        } catch (Exception ex) {
            response.put("httpCode", HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.put("message", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Elimina un producto
    @DeleteMapping("/producto/{id}")
    public ResponseEntity<HashMap<String, Object>> delete(@PathVariable String id, HttpServletRequest request) {
        response = new HashMap<>();
        try {
            authentication.auth(request);
            //check if producto exists
            Optional<ProductoModel> productoModel = productoService.getById(Integer.parseInt(id));
            if (productoModel.isPresent()) {
                productoService.delete(Integer.parseInt(id));
                response.put("httpCode", HttpStatus.OK.value());
                response.put("data", productoService.getById(Integer.parseInt(id)));
                response.put("message", HttpStatus.OK.getReasonPhrase() + ": El producto se ha eliminado correctamente");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            response.put("httpCode", HttpStatus.NOT_FOUND.value());
            response.put("data", null);
            response.put("message", HttpStatus.NOT_FOUND.getReasonPhrase() + ": El producto no existe");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (UnauthorizedException ex) {
            response.put("httpCode", HttpStatus.UNAUTHORIZED.value());
            response.put("data", ex.toJSON());
            response.put("message", HttpStatus.UNAUTHORIZED.getReasonPhrase());
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);

        } catch (ExternalMicroserviceException ex) {
            response.put("httpCode", HttpStatus.SERVICE_UNAVAILABLE.value());
            response.put("data", ex.toJSON());
            response.put("message", HttpStatus.SERVICE_UNAVAILABLE.getReasonPhrase());
            return new ResponseEntity<>(response, HttpStatus.SERVICE_UNAVAILABLE);
        } catch (Exception ex) {
            response.put("httpCode", HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.put("message", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//MODELOS

    /**
     * Busca todos los modelos en la base de datos
     *
     * @return todas las marcas
     */
    @GetMapping("/modelo/")
    public ResponseEntity<HashMap<String, Object>> getAllModelo() {
        response = new HashMap<>();
        try {
            response.put("httpCode", HttpStatus.OK.value());
            response.put("data", modeloService.getAll());
            response.put("message", HttpStatus.OK.getReasonPhrase() + ": Modelo encontrado");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            response.put("httpCode", HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.put("message", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Busca un modelo en la base de datos
     *
     * @param id - id del modelo a buscar
     * @return ResponseEntity de exito o fracaso
     */
    @GetMapping("/modelo/{id}")
    public ResponseEntity<HashMap<String, Object>> getModelo(@PathVariable String id) {
        response = new HashMap<>();
        try {
            //check if producto exists
            Optional<ModeloModel> modeloModel = modeloService.getById(Integer.parseInt(id));
            if (modeloModel.isPresent()) {
                response.put("httpCode", HttpStatus.OK.value());
                response.put("data", modeloModel);
                response.put("message", HttpStatus.OK.getReasonPhrase() + ": Modelo encontrado");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            response.put("httpCode", HttpStatus.NOT_FOUND.value());
            response.put("data", null);
            response.put("message", HttpStatus.NOT_FOUND.getReasonPhrase() + ": El modelo no existe");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            response.put("httpCode", HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.put("message", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Crea un nuevo modelo y lo guarda en la base de datos
     *
     * @param modelo - nuevo modelo a guardar
     * @return ResponseEntity de exito o fracaso
     */
    @PostMapping("/modelo/")
    public ResponseEntity<HashMap<String, Object>> postModelo(@RequestBody ModeloModel modelo, HttpServletRequest request) {
        response = new HashMap<>();
        try {
            authentication.auth(request);
            if (modelo.getNombreModelo() == null) {
                response.put("httpCode", HttpStatus.NOT_FOUND.value());
                response.put("message", HttpStatus.NOT_FOUND.getReasonPhrase() + ": Uno o más campos están vacíos");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            } else {
                modeloService.createModelo(modelo);
                response.put("httpCode", HttpStatus.CREATED.value());
                response.put("data", modelo);
                response.put("message", HttpStatus.CREATED.getReasonPhrase() + ": El modelo se ha creado correctamente");
                return new ResponseEntity<>(response, HttpStatus.CREATED);
            }
        } catch (UnauthorizedException ex) {
            response.put("httpCode", HttpStatus.UNAUTHORIZED.value());
            response.put("data", ex.toJSON());
            response.put("message", HttpStatus.UNAUTHORIZED.getReasonPhrase());
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);

        } catch (ExternalMicroserviceException ex) {
            response.put("httpCode", HttpStatus.SERVICE_UNAVAILABLE.value());
            response.put("data", ex.toJSON());
            response.put("message", HttpStatus.SERVICE_UNAVAILABLE.getReasonPhrase());
            return new ResponseEntity<>(response, HttpStatus.SERVICE_UNAVAILABLE);
        } catch (Exception ex) {
            response.put("httpCode", HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.put("message", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    //Actualiza un producto (todos los atributos)

    /**
     * Actuliza un modelo
     *
     * @param modelo - datos modelo a actulizar
     * @param id     - id del producto a actulizar
     * @return ResponseEntity caso de exito o fracaso
     */
    @Transactional
    @PutMapping("/modelo/{id}")
    public ResponseEntity<HashMap<String, Object>> putModelo(@RequestBody ModeloModel modelo, @PathVariable String id, HttpServletRequest request) {
        response = new HashMap<>();
        try {
            authentication.auth(request);

            Optional<ModeloModel> modeloModel = modeloService.getById(Integer.parseInt(id));
            if (modeloModel.isPresent()) {
                if (modelo.getNombreModelo() == null) {
                    response.put("httpCode", HttpStatus.NOT_FOUND.value());
                    response.put("message", HttpStatus.NOT_FOUND.getReasonPhrase() + ": Uno o más campos están vacíos");
                    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
                } else {
                    modeloService.update(modelo, Integer.parseInt(id));
                    response.put("httpCode", HttpStatus.OK.value());
                    response.put("data", modelo);
                    response.put("message", HttpStatus.OK.getReasonPhrase() + ": El modelo se ha actualizado correctamente");
                    return new ResponseEntity<>(response, HttpStatus.OK);
                }
            }
            response.put("httpCode", HttpStatus.NOT_FOUND.value());
            response.put("data", null);
            response.put("message", HttpStatus.NOT_FOUND.getReasonPhrase() + ": El modelo no existe");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (UnauthorizedException ex) {
            response.put("httpCode", HttpStatus.UNAUTHORIZED.value());
            response.put("data", ex.toJSON());
            response.put("message", HttpStatus.UNAUTHORIZED.getReasonPhrase());
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);

        } catch (ExternalMicroserviceException ex) {
            response.put("httpCode", HttpStatus.SERVICE_UNAVAILABLE.value());
            response.put("data", ex.toJSON());
            response.put("message", HttpStatus.SERVICE_UNAVAILABLE.getReasonPhrase());
            return new ResponseEntity<>(response, HttpStatus.SERVICE_UNAVAILABLE);
        } catch (Exception ex) {
            response.put("httpCode", HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.put("message", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /**
     * elimina un modelo
     *
     * @param id - id del modelo a eliminar
     * @return ResponseEntity de exito o fracaso
     */
    @DeleteMapping("/modelo/{id}")
    public ResponseEntity<HashMap<String, Object>> deleteModelo(@PathVariable String id, HttpServletRequest request) {
        response = new HashMap<>();
        try {
            authentication.auth(request);
            Optional<ModeloModel> modeloModel = modeloService.getById(Integer.parseInt(id));
            if (modeloModel.isPresent()) {
                modeloService.delete(Integer.parseInt(id));
                response.put("httpCode", HttpStatus.OK.value());
                response.put("data", modeloService.getById(Integer.parseInt(id)));
                response.put("message", HttpStatus.OK.getReasonPhrase() + ": El modelo se ha eliminado correctamente");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            response.put("httpCode", HttpStatus.NOT_FOUND.value());
            response.put("data", null);
            response.put("message", HttpStatus.NOT_FOUND.getReasonPhrase() + ": El modelo no existe");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (UnauthorizedException ex) {
            response.put("httpCode", HttpStatus.UNAUTHORIZED.value());
            response.put("data", ex.toJSON());
            response.put("message", HttpStatus.UNAUTHORIZED.getReasonPhrase());
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);

        } catch (ExternalMicroserviceException ex) {
            response.put("httpCode", HttpStatus.SERVICE_UNAVAILABLE.value());
            response.put("data", ex.toJSON());
            response.put("message", HttpStatus.SERVICE_UNAVAILABLE.getReasonPhrase());
            return new ResponseEntity<>(response, HttpStatus.SERVICE_UNAVAILABLE);
        } catch (Exception ex) {
            response.put("httpCode", HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.put("message", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
//MARCA

    /**
     * Busca todas las marcas en la base de datos
     *
     * @return todas las marcas
     */
    @GetMapping("/marca/")
    public ResponseEntity<HashMap<String, Object>> getAllMarca() {
        response = new HashMap<>();
        try {
            response.put("httpCode", HttpStatus.OK.value());
            response.put("data", marcaService.getAll());
            response.put("message", HttpStatus.OK.getReasonPhrase() + ": Marca encontrada");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            response.put("httpCode", HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.put("message", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Busca una marca en la base de datos
     *
     * @param id - id de la marca a buscar
     * @return ResponseEntity de exito o fracaso
     */
    @GetMapping("/marca/{id}")
    public ResponseEntity<HashMap<String, Object>> getMarca(@PathVariable String id) {
        response = new HashMap<>();
        try {
            //check if producto exists
            Optional<MarcaModel> marcaModel = marcaService.getById(Integer.parseInt(id));
            if (marcaModel.isPresent()) {
                response.put("httpCode", HttpStatus.OK.value());
                response.put("data", marcaModel);
                response.put("message", HttpStatus.OK.getReasonPhrase() + ": Marca encontrada");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            response.put("httpCode", HttpStatus.NOT_FOUND.value());
            response.put("data", null);
            response.put("message", HttpStatus.NOT_FOUND.getReasonPhrase() + ": La marca no existe");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            response.put("httpCode", HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.put("message", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Crea una nueva marca y la guarda en la base de datos
     *
     * @param marca - nueva marca a guardar
     * @return ResponseEntity de exito o fracaso
     */
    @PostMapping("/marca/")
    public ResponseEntity<HashMap<String, Object>> postMarca(@RequestBody MarcaModel marca, HttpServletRequest request) {
        response = new HashMap<>();
        try {
            authentication.auth(request);
            Optional<MarcaModel> marcaModel = marcaService.getByNombre(marca.getNombreMarca());
            if (!marcaModel.isPresent()) {
                if (marca.getNombreMarca() == null) {
                    response.put("httpCode", 400);
                    response.put("message", HttpStatus.NOT_FOUND.getReasonPhrase() + ": Uno o más campos están vacíos");
                    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
                } else {
                    marcaService.createMarca(marca);
                    response.put("httpCode", HttpStatus.CREATED.value());
                    response.put("data", marca);
                    response.put("message", HttpStatus.CREATED.getReasonPhrase() + ": La marca se ha creado correctamente");
                    return new ResponseEntity<>(response, HttpStatus.CREATED);
                }
            }
            response.put("httpCode", HttpStatus.NOT_FOUND.value());
            response.put("data", null);
            response.put("message", HttpStatus.NOT_FOUND.getReasonPhrase() + ": La marca ya esta registrada");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (UnauthorizedException ex) {
            response.put("httpCode", HttpStatus.UNAUTHORIZED.value());
            response.put("data", ex.toJSON());
            response.put("message", HttpStatus.UNAUTHORIZED.getReasonPhrase());
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);

        } catch (ExternalMicroserviceException ex) {
            response.put("httpCode", HttpStatus.SERVICE_UNAVAILABLE.value());
            response.put("data", ex.toJSON());
            response.put("message", HttpStatus.SERVICE_UNAVAILABLE.getReasonPhrase());
            return new ResponseEntity<>(response, HttpStatus.SERVICE_UNAVAILABLE);
        } catch (Exception ex) {
            response.put("httpCode", HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.put("message", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Actualiza un producto (todos los atributos)

    /**
     * Actuliza una marca
     *
     * @param marca - datos producto a actulizar
     * @param id    - id del producto a actulizar
     * @return ResponseEntity caso de exito o fracaso
     */
    @Transactional
    @PutMapping("/marca/{id}")
    public ResponseEntity<HashMap<String, Object>> putMarca(@RequestBody MarcaModel marca, @PathVariable String id, HttpServletRequest request) {
        response = new HashMap<>();
        try {
            authentication.auth(request);
            Optional<MarcaModel> marcaModel = marcaService.getById(Integer.parseInt(id));
            //check if marca exists
            if (marcaModel.isPresent()) {
                //check if fields are not null
                if (marca.getNombreMarca() == null) {
                    response.put("httpCode", 400);
                    response.put("message", HttpStatus.NOT_FOUND.getReasonPhrase() + ": Uno o más campos están vacíos");
                    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
                } else {
                    marcaService.update(marca, Integer.parseInt(id));
                    response.put("httpCode", HttpStatus.OK.value());
                    response.put("data", marca);
                    response.put("message", HttpStatus.OK.getReasonPhrase() + ": La marca se ha actualizado correctamente");
                    return new ResponseEntity<>(response, HttpStatus.OK);
                }
            }
            response.put("httpCode", HttpStatus.NOT_FOUND.value());
            response.put("data", null);
            response.put("message", HttpStatus.NOT_FOUND.getReasonPhrase() + ": La marca no existe");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (UnauthorizedException ex) {
            response.put("httpCode", HttpStatus.UNAUTHORIZED.value());
            response.put("data", ex.toJSON());
            response.put("message", HttpStatus.UNAUTHORIZED.getReasonPhrase());
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);

        } catch (ExternalMicroserviceException ex) {
            response.put("httpCode", HttpStatus.SERVICE_UNAVAILABLE.value());
            response.put("data", ex.toJSON());
            response.put("message", HttpStatus.SERVICE_UNAVAILABLE.getReasonPhrase());
            return new ResponseEntity<>(response, HttpStatus.SERVICE_UNAVAILABLE);
        } catch (Exception ex) {
            response.put("httpCode", HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.put("message", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * elimina una marca
     *
     * @param id - id de la marca a eliminar
     * @return ResponseEntity de exito o fracaso
     */
    @DeleteMapping("/marca/{id}")
    public ResponseEntity<HashMap<String, Object>> deleteMarca(@PathVariable String id, HttpServletRequest request) {
        response = new HashMap<>();
        try {
            authentication.auth(request);
            Optional<MarcaModel> marcaModel = marcaService.getById(Integer.parseInt(id));
            if (marcaModel.isPresent()) {
                marcaService.delete(Integer.parseInt(id));
                response.put("httpCode", HttpStatus.OK.value());
                response.put("data", marcaService.getById(Integer.parseInt(id)));
                response.put("message", HttpStatus.OK.getReasonPhrase() + ": La marca se ha eliminado correctamente");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            response.put("httpCode", HttpStatus.NOT_FOUND.value());
            response.put("data", null);
            response.put("message", HttpStatus.NOT_FOUND.getReasonPhrase() + ": La marca no existe");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);

        } catch (UnauthorizedException ex) {
            response.put("httpCode", HttpStatus.UNAUTHORIZED.value());
            response.put("data", ex.toJSON());
            response.put("message", HttpStatus.UNAUTHORIZED.getReasonPhrase());
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);

        } catch (ExternalMicroserviceException ex) {
            response.put("httpCode", HttpStatus.SERVICE_UNAVAILABLE.value());
            response.put("data", ex.toJSON());
            response.put("message", HttpStatus.SERVICE_UNAVAILABLE.getReasonPhrase());
            return new ResponseEntity<>(response, HttpStatus.SERVICE_UNAVAILABLE);
        } catch (Exception ex) {
            response.put("httpCode", HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.put("message", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Compras
    @GetMapping("/compra/")
    public ResponseEntity<HashMap<String, Object>> getAllCompras() {
        response = new HashMap<>();
        try {
            response.put("httpCode", HttpStatus.OK.value());
            response.put("data", compraService.getAll());
            response.put("message", HttpStatus.OK.getReasonPhrase() + ": Compras encontradas");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            response.put("httpCode", HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.put("message", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/compras/new/")
    public ResponseEntity<HashMap<String, Object>> postCompras2(@RequestBody List<ProductoModel> productosLista, HttpServletRequest request) {
        response = new HashMap<>();
        try {
            authentication.auth(request);
            if (productosLista != null) {
                CompraModel compra = new CompraModel();
                compra.setFechaAdquirido(LocalDateTime.now());
                compra.setTotal(0.0);
                compraService.save(compra);
                Optional<CompraModel> compraRt = compraService.getByCompra(compra.getTotal(), compra.getFechaAdquirido());

                for (ProductoModel productoN : productosLista) {
                    int cantidad;
                    if (productoN.getPrecioCompra() == null || productoN.getStock() == null
                            || productoN.getColor() == null || productoN.getMarca() == null
                            || productoN.getTalla() == null || productoN.getModelo() == null
                            || productoN.getPrecioVenta() == null) {
                        response.put("httpCode", 400);
                        response.put("message", HttpStatus.BAD_REQUEST.getReasonPhrase() + ": Uno o más campos están vacíos");
                        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
                    } else {
                        cantidad = productoN.getStock();
                        compra.setTotal(compra.getTotal() + (productoN.getStock() * productoN.getPrecioCompra()));
                        Optional<ProductoModel> productoModel = productoService.getByDatos(productoN.getTalla(), productoN.getColor(), productoN.getModelo(), productoN.getMarca());
                        if (!productoModel.isPresent()) {
                            productoService.save(productoN);
                        } else {
                            productoN.setStock(productoN.getStock() + productoModel.get().getStock());
                            productoService.update(productoN, productoModel.get().getIdProducto());
                        }

                        productoModel = productoService.getByDatos(productoN.getTalla(), productoN.getColor(), productoN.getModelo(), productoN.getMarca());

                        DetalleCompraModel detalleCompra = new DetalleCompraModel();
                        detalleCompra.setCompra(compraRt.get());
                        detalleCompra.setProducto(productoModel.get());
                        detalleCompra.setCantidad(cantidad);
                        detalleCompraService.save(detalleCompra);
                    }

                }
                compraService.update(compra, compraRt.get().getIdCompra());
                Collection dc = detalleCompraService.getAllCompra(compraRt.get());
                List<Object> salida = new ArrayList<>();
                salida.add(compraRt.get());
                salida.add(dc);
                response.put("httpCode", HttpStatus.OK.value());
                response.put("data", salida);
                response.put("message", HttpStatus.CREATED.getReasonPhrase() + ": La compra se ha creado correctamente");
                return new ResponseEntity<>(response, HttpStatus.CREATED);
            }
            response.put("httpCode", 400);
            response.put("message", HttpStatus.NOT_FOUND.getReasonPhrase() + ": No ingreso ningun producto");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (UnauthorizedException ex) {
            response.put("httpCode", HttpStatus.UNAUTHORIZED.value());
            response.put("data", ex.toJSON());
            response.put("message", HttpStatus.UNAUTHORIZED.getReasonPhrase());
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        } catch (ExternalMicroserviceException ex) {
            response.put("httpCode", HttpStatus.SERVICE_UNAVAILABLE.value());
            response.put("data", ex.toJSON());
            response.put("message", HttpStatus.SERVICE_UNAVAILABLE.getReasonPhrase());
            return new ResponseEntity<>(response, HttpStatus.SERVICE_UNAVAILABLE);
        } catch (Exception ex) {
            response.put("httpCode", HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.put("message", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/compra-detalle/{idCompra}")
    public ResponseEntity<HashMap<String, Object>> getAllDetalle(@PathVariable String idCompra) {

        response = new HashMap<>();
        try {
            Optional<CompraModel> compraRt = compraService.getById(Integer.parseInt(idCompra));
            if (compraRt.isPresent()) {
                Collection dc = detalleCompraService.getAllCompra(compraRt.get());
                List<Object> salida = new ArrayList<>();
                salida.add(compraRt.get());
                salida.add(dc);
                response.put("httpCode", HttpStatus.OK.value());
                response.put("data", salida);
                response.put("message", HttpStatus.OK.getReasonPhrase() + ": Lista compra");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            response.put("httpCode", HttpStatus.NOT_FOUND.value());
            response.put("data", null);
            response.put("message", HttpStatus.NOT_FOUND.getReasonPhrase() + ": La compra no existe");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            response.put("httpCode", HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.put("message", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase() + "Eror detalle");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
