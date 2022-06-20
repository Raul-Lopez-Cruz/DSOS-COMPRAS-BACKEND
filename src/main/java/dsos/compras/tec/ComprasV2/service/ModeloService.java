/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ito.dsos.compras.service;

import ito.dsos.compras.model.ModeloModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


/**
 *
 * @author Oscar
 */

@Service
public interface ModeloService {
    
    public void createModelo(ModeloModel modelo);

    public void save(ModeloModel nuevoModelo);

    public void delete(Integer id);

    public void update(ModeloModel modeloUpdate, Integer id);

    public Optional<ModeloModel> getById(Integer id);

    public List<ModeloModel> getAll();

}
