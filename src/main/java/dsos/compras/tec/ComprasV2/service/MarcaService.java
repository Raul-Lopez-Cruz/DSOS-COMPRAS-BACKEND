/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dsos.compras.tec.ComprasV2.service;

import dsos.compras.tec.ComprasV2.model.MarcaModel;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

/**
 *
 * @author Oscar
 */
@Service
public interface MarcaService {

    public void createMarca(MarcaModel marca);

    public void save(MarcaModel nuevaMarca);

    public void delete(Integer id);

    public void update(MarcaModel marcaUpdate, Integer id);

    public Optional<MarcaModel> getById(Integer id);

    public List<MarcaModel> getAll();

    public Optional<MarcaModel> getByNombre(String nombre);

}
