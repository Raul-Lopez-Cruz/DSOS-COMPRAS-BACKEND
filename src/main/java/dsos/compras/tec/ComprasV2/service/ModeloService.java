/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dsos.compras.tec.ComprasV2.service;

import dsos.compras.tec.ComprasV2.model.ModeloModel;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

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
