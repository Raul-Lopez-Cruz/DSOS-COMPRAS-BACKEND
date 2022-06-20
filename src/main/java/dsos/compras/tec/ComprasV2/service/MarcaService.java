/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ito.dsos.compras.service;

import ito.dsos.compras.model.MarcaModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


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
