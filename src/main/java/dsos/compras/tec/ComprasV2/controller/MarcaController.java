/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dsos.compras.tec.ComprasV2.controller;

import dsos.compras.tec.ComprasV2.model.MarcaModel;
import dsos.compras.tec.ComprasV2.service.MarcaService;
import java.util.HashMap;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Oscar
 */
@RestController
@RequestMapping("/api/marca")
@CrossOrigin(origins = "*")
public class MarcaController {

    HashMap<String, Object> response;

    @Autowired
    private MarcaService marcaService;

    /**
     * Busca todas las marcas en la base de datos
     *
     * @return todas las marcas
     */
    @GetMapping("/")
    public ResponseEntity<HashMap<String, Object>> getAll() {
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
    @GetMapping("/{id}")
    public ResponseEntity<HashMap<String, Object>> get(@PathVariable String id) {
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
    @PostMapping("/")
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
     * @param id - id del producto a actulizar
     * @return ResponseEntity caso de exito o fracaso
     */
    @Transactional
    @PutMapping("/{id}")
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
    @DeleteMapping("/{id}")
    public ResponseEntity<HashMap<String, Object>> delete(@PathVariable String id) {
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
