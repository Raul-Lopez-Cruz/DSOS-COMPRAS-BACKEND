/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dsos.compras.tec.ComprasV2.controller;

import dsos.compras.tec.ComprasV2.model.ModeloModel;
import dsos.compras.tec.ComprasV2.service.ModeloService;
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
@RequestMapping("/api/modelo")
@CrossOrigin(origins = "*")
public class ModeloController {
    
    HashMap<String, Object> response;

    @Autowired
    private ModeloService modeloService;
 
    /**
     * Busca todos los modelos en la base de datos
     *
     * @return todas las marcas
     */
    @GetMapping("/")
    public ResponseEntity<HashMap<String, Object>> getAll() {
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
    @GetMapping("/{id}")
    public ResponseEntity<HashMap<String, Object>> get(@PathVariable String id) {
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
    @PostMapping("/")
    public ResponseEntity<HashMap<String, Object>> post(@RequestBody ModeloModel modelo) {
        response = new HashMap<>();
            if (modelo.getNumeroModelo()== null) {
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
     * @param id - id del producto a actulizar
     * @return ResponseEntity caso de exito o fracaso
     */
    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<HashMap<String, Object>> put(@RequestBody ModeloModel modelo, @PathVariable String id) {
        response = new HashMap<>();
        Optional<ModeloModel> modeloModel = modeloService.getById(Integer.parseInt(id));
        if (modeloModel.isPresent()) {
            if (modelo.getNumeroModelo() == null) {
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
        response.put("message", HttpStatus.NOT_FOUND.getReasonPhrase() + ": La marca no existe");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);

    }

    /**
     * elimina un modelo
     *
     * @param id - id del modelo a eliminar
     * @return ResponseEntity de exito o fracaso
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<HashMap<String, Object>> delete(@PathVariable String id) {
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
}
