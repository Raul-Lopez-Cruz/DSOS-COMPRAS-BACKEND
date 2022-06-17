package dsos.compras.tec.ComprasV2.controller;

import dsos.compras.tec.ComprasV2.model.MarcaModel;
import dsos.compras.tec.ComprasV2.model.ModeloModel;
import dsos.compras.tec.ComprasV2.model.ProductoModel;
import dsos.compras.tec.ComprasV2.service.MarcaService;
import dsos.compras.tec.ComprasV2.service.ModeloService;
import dsos.compras.tec.ComprasV2.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Optional;

@RestController
@RequestMapping("/")
@CrossOrigin(origins = "*")
public class ProductoController {

    HashMap<String, Object> response;

    @Autowired
    private ProductoService productoService;

    @Autowired
    private ModeloService modeloService;

    @Autowired
    private MarcaService marcaService;

    //Devuelve todos los productos
    @GetMapping("api/compras/")
    public ResponseEntity<HashMap<String, Object>> getAll() {
        response = new HashMap<>();
        response.put("httpCode", HttpStatus.OK.value());
        response.put("data", productoService.getAll());
        response.put("message", HttpStatus.OK.getReasonPhrase() + ": Producto encontrado");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //Devuelve un producto por su id
    @GetMapping("api/compras/{id}")
    public ResponseEntity<HashMap<String, Object>> get(@PathVariable String id) {
        response = new HashMap<>();
        //check if producto exists
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
    }

    //Crea un producto
    @PostMapping("api/compras/")
    public ResponseEntity<HashMap<String, Object>> post(@RequestBody ProductoModel producto) {
        response = new HashMap<>();
        if (producto.getPrecioCompra() == null || producto.getStock() == null
                || producto.getColor() == null || producto.getMarca() == null || producto.getTalla() == null || producto.getModelo() == null
                || producto.getPrecioVenta() == null) {
            response.put("httpCode", 400);
            response.put("message", HttpStatus.BAD_REQUEST.getReasonPhrase() + ": Uno o más campos están vacíos");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } else {

            Optional<ModeloModel> modeloModel = modeloService.getById(producto.getModelo().getIdModelo());
            if (modeloModel.isPresent()) {
                Optional<MarcaModel> marcaModel = marcaService.getById(producto.getMarca().getIdMarca());
                if (marcaModel.isPresent()) {
                    productoService.createProducto(producto);
                    response.put("httpCode", HttpStatus.CREATED.value());
                    response.put("data", producto);
                    response.put("message", HttpStatus.CREATED.getReasonPhrase() + ": El producto se ha creado correctamente");
                    return new ResponseEntity<>(response, HttpStatus.CREATED);
                }
                response.put("httpCode", HttpStatus.NOT_FOUND.value());
                response.put("data", null);
                response.put("message", HttpStatus.NOT_FOUND.getReasonPhrase() + ": La marca no existe");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
            response.put("httpCode", HttpStatus.NOT_FOUND.value());
            response.put("data", null);
            response.put("message", HttpStatus.NOT_FOUND.getReasonPhrase() + ": El modelo no existe");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);

        }
    }

    //Actualiza un producto (todos los atributos)
    @Transactional
    @PutMapping("api/compras/{id}")
    public ResponseEntity<HashMap<String, Object>> put(@RequestBody ProductoModel producto, @PathVariable String id) {
        response = new HashMap<>();
        Optional<ProductoModel> productoModel = productoService.getById(Integer.parseInt(id));
        //check if producto exists
        if (productoModel.isPresent()) {
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

    }

    //Actualiza las existencias de un producto
    @Transactional
    @PutMapping("api/compras/vender/{id}/{unidades}")
    public ResponseEntity<HashMap<String, Object>> vender(@PathVariable String id, @PathVariable String unidades) {
        response = new HashMap<>();
        //check if producto exists
        Optional<ProductoModel> productoModel = productoService.getById(Integer.parseInt(id));
        if (productoModel.isPresent()) {
            //check for stock
            ProductoModel producto = productoModel.get();
            //if stock is not enough
            if (producto.getStock() < Integer.parseInt(unidades)) {
                response.put("httpCode", 400);
                response.put("message", HttpStatus.BAD_REQUEST.getReasonPhrase() + ": No hay suficiente stock");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
            //if stock is enough, update stock
            productoService.vender(Integer.parseInt(id), Integer.parseInt(unidades));
            response.put("httpCode", HttpStatus.OK.value());
            HashMap<String, Object> data = new HashMap<>();
            //put into data stock and id
            data.put("stock", producto.getStock());
            data.put("id", producto.getIdProducto());
            response.put("data", data);
            response.put("message", HttpStatus.OK.getReasonPhrase() + ": Se ha vendido " + unidades + " unidades");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        //If producto does not exist
        response.put("httpCode", HttpStatus.NOT_FOUND.value());
        response.put("message", HttpStatus.NOT_FOUND.getReasonPhrase() + ": El producto no existe");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    //Actualiza las existencias de un producto
    @Transactional
    @PutMapping("api/compras/devolver/{id}/{unidades}")
    public ResponseEntity<HashMap<String, Object>> devolver(@PathVariable String id, @PathVariable String unidades) {
        response = new HashMap<>();
        //check if producto exists
        Optional<ProductoModel> productoModel = productoService.getById(Integer.parseInt(id));
        if (productoModel.isPresent()) {
            ProductoModel producto = productoModel.get();
            //update stock
            productoService.devolver(Integer.parseInt(id), Integer.parseInt(String.valueOf(unidades)));
            response.put("httpCode", HttpStatus.OK.value());
            HashMap<String, Object> data = new HashMap<>();
            //put into data stock and id
            data.put("stock", producto.getStock());
            data.put("id", producto.getIdProducto());
            response.put("data", data);
            response.put("message", HttpStatus.OK.getReasonPhrase() + ": Se ha devuelto " + unidades + "unidad(es) al inventario.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        //If producto does not exist
        response.put("httpCode", HttpStatus.NOT_FOUND.value());
        response.put("message", HttpStatus.NOT_FOUND.getReasonPhrase() + ": El producto no existe");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    //Elimina un producto
    @DeleteMapping("api/compras/{id}")
    public ResponseEntity<HashMap<String, Object>> delete(@PathVariable String id) {
        response = new HashMap<>();
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

    }

    /**
     * Busca todos los modelos en la base de datos
     *
     * @return todas las marcas
     */
    @GetMapping("api/modelo/")
    public ResponseEntity<HashMap<String, Object>> getAllModelo() {
        response = new HashMap<>();
        response.put("httpCode", HttpStatus.OK.value());
        response.put("data", modeloService.getAll());
        response.put("message", HttpStatus.OK.getReasonPhrase() + ": Modelo encontrado");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Busca un modelo en la base de datos
     *
     * @param id - id del modelo a buscar
     * @return ResponseEntity de exito o fracaso
     */
    @GetMapping("api/modelo/{id}")
    public ResponseEntity<HashMap<String, Object>> getModelo(@PathVariable String id) {
        response = new HashMap<>();
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
    }

    /**
     * Crea un nuevo modelo y lo guarda en la base de datos
     *
     * @param modelo - nuevo modelo a guardar
     * @return ResponseEntity de exito o fracaso
     */
    @PostMapping("api/modelo/")
    public ResponseEntity<HashMap<String, Object>> post(@RequestBody ModeloModel modelo) {
        response = new HashMap<>();
        if (modelo.getNombreModelo() == null) {
            response.put("httpCode", 400);
            response.put("message", HttpStatus.BAD_REQUEST.getReasonPhrase() + ": Uno o más campos están vacíos");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } else {
            modeloService.createModelo(modelo);
            response.put("httpCode", HttpStatus.CREATED.value());
            response.put("data", modelo);
            response.put("message", HttpStatus.CREATED.getReasonPhrase() + ": El modelo se ha creado correctamente");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
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
    @PutMapping("api/modelo/{id}")
    public ResponseEntity<HashMap<String, Object>> put(@RequestBody ModeloModel modelo, @PathVariable String id) {
        response = new HashMap<>();
        Optional<ModeloModel> modeloModel = modeloService.getById(Integer.parseInt(id));
        if (modeloModel.isPresent()) {
            if (modelo.getNombreModelo() == null) {
                response.put("httpCode", 400);
                response.put("message", HttpStatus.BAD_REQUEST.getReasonPhrase() + ": Uno o más campos están vacíos");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
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

    }

    /**
     * elimina un modelo
     *
     * @param id - id del modelo a eliminar
     * @return ResponseEntity de exito o fracaso
     */
    @DeleteMapping("api/modelo/{id}")
    public ResponseEntity<HashMap<String, Object>> deleteModelo(@PathVariable String id) {
        response = new HashMap<>();
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

    }

    /**
     * Busca todas las marcas en la base de datos
     *
     * @return todas las marcas
     */
    @GetMapping("api/marca/")
    public ResponseEntity<HashMap<String, Object>> getAllMarca() {
        response = new HashMap<>();
        response.put("httpCode", HttpStatus.OK.value());
        response.put("data", marcaService.getAll());
        response.put("message", HttpStatus.OK.getReasonPhrase() + ": Marca encontrada");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Busca una marca en la base de datos
     *
     * @param id - id de la marca a buscar
     * @return ResponseEntity de exito o fracaso
     */
    @GetMapping("api/marca/{id}")
    public ResponseEntity<HashMap<String, Object>> getMarca(@PathVariable String id) {
        response = new HashMap<>();
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
    }

    /**
     * Crea una nueva marca y la guarda en la base de datos
     *
     * @param marca - nueva marca a guardar
     * @return ResponseEntity de exito o fracaso
     */
    @PostMapping("api/marca/")
    public ResponseEntity<HashMap<String, Object>> post(@RequestBody MarcaModel marca) {
        response = new HashMap<>();
        Optional<MarcaModel> marcaModel = marcaService.getByNombre(marca.getNombreMarca());
        if (!marcaModel.isPresent()) {
            if (marca.getNombreMarca() == null) {
                response.put("httpCode", 400);
                response.put("message", HttpStatus.BAD_REQUEST.getReasonPhrase() + ": Uno o más campos están vacíos");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
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
    @PutMapping("api/marca/{id}")
    public ResponseEntity<HashMap<String, Object>> put(@RequestBody MarcaModel marca, @PathVariable String id) {
        response = new HashMap<>();
        Optional<MarcaModel> marcaModel = marcaService.getById(Integer.parseInt(id));
        //check if marca exists
        if (marcaModel.isPresent()) {
            //check if fields are not null
            if (marca.getNombreMarca() == null) {
                response.put("httpCode", 400);
                response.put("message", HttpStatus.BAD_REQUEST.getReasonPhrase() + ": Uno o más campos están vacíos");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
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

    }

    /**
     * elimina una marca
     *
     * @param id - id de la marca a eliminar
     * @return ResponseEntity de exito o fracaso
     */
    @DeleteMapping("api/marca/{id}")
    public ResponseEntity<HashMap<String, Object>> deleteMarca(@PathVariable String id) {
        response = new HashMap<>();
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

    }
}
