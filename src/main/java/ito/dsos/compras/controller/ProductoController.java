package ito.dsos.compras.controller;

import ito.dsos.compras.model.ProductoModel;
import ito.dsos.compras.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/api/compras")
@CrossOrigin(origins = "*")
public class ProductoController {

    HashMap<String, Object> response;

    @Autowired
    private ProductoService productoService;

    //Devuelve todos los productos
    @GetMapping("/")
    public ResponseEntity<HashMap<String, Object>> getAll() {
        response = new HashMap<>();
        response.put("httpCode", HttpStatus.OK.value());
        response.put("data", productoService.getAll());
        response.put("message", HttpStatus.OK.getReasonPhrase() +": Producto encontrado");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //Devuelve un producto por su id
    @GetMapping("/{id}")
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
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //Crea un producto
    @PostMapping("/")
    public ResponseEntity<HashMap<String, Object>> post(@RequestBody ProductoModel producto) {
        response = new HashMap<>();
        if (producto.getPrecioCompra() == null || producto.getStock() == null
                || producto.getColor() == null || producto.getMarca() == null || producto.getTalla() == null || producto.getModelo() == null
                || producto.getPrecioVenta() == null) {
            response.put("httpCode", 400);
            response.put("message", HttpStatus.BAD_REQUEST.getReasonPhrase() + ": Uno o más campos están vacíos");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } else {
            productoService.createProducto(producto);
            response.put("httpCode", HttpStatus.CREATED.value());
            response.put("data", producto);
            response.put("message", HttpStatus.CREATED.getReasonPhrase()+ ": El producto se ha creado correctamente");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }
    }

    //Actualiza un producto (todos los atributos)
    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<HashMap<String, Object>> put(@RequestBody ProductoModel producto, @PathVariable String id) {
        response = new HashMap<>();
        Optional<ProductoModel> productoModel = productoService.getById(Integer.parseInt(id));
        //check if producto exists
        if (productoModel.isPresent()) {
            //check if fields are not null
            if (producto.getPrecioCompra() == null || producto.getStock() == null
                    || producto.getColor() == null || producto.getMarca() == null || producto.getTalla() == null || producto.getModelo() == null
                    || producto.getPrecioVenta() == null) {
                response.put("httpCode", 400);
                response.put("message", HttpStatus.BAD_REQUEST.getReasonPhrase() + ": Uno o más campos están vacíos");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            } else {
                productoService.update(producto, Integer.parseInt(id));
                response.put("httpCode", HttpStatus.OK.value());
                response.put("data", producto);
                response.put("message", HttpStatus.OK.getReasonPhrase() + ": El producto se ha actualizado correctamente");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        }
        response.put("httpCode", HttpStatus.NOT_FOUND.value());
        response.put("data", null);
        response.put("message", HttpStatus.NOT_FOUND.getReasonPhrase() + ": El producto no existe");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);

    }

    //Actualiza las existencias de un producto
    @Transactional
    @PutMapping("/vender/{id}/{unidades}")
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
            response.put("data", productoService.getById(Integer.parseInt(id)));
            response.put("message", HttpStatus.OK.getReasonPhrase() + ": Se han vendido " + unidades + " unidades");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        //If producto does not exist
        response.put("httpCode", HttpStatus.NOT_FOUND.value());
        response.put("message", HttpStatus.NOT_FOUND.getReasonPhrase() + ": El producto no existe");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    //Elimina un producto
    @DeleteMapping("/{id}")
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
}
