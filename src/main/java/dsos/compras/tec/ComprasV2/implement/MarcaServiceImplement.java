/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ito.dsos.compras.implement;

import ito.dsos.compras.model.MarcaModel;
import ito.dsos.compras.repository.MarcaRepository;
import ito.dsos.compras.service.MarcaService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 *
 * @author Oscar
 */
@Service
public class MarcaServiceImplement implements MarcaService {

    private final Log LOG = LogFactory.getLog(MarcaService.class);

    @Autowired
    private MarcaRepository marcaRepository;

    public MarcaServiceImplement(MarcaRepository marcaRepository) {
        this.marcaRepository = marcaRepository;
    }

    @Override
    public void createMarca(MarcaModel marca) {
        marcaRepository.save(marca);
    }

    @Override
    public void save(MarcaModel nuevaMarca) {
        marcaRepository.save(nuevaMarca);
    }

    @Override
    public void delete(Integer id) {
        marcaRepository.deleteById(id);
    }

    @Override
    public void update(MarcaModel marcaUpdate, Integer id) {
        MarcaModel marcaModel = marcaRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("La marca no existe"));
        marcaModel.setNombreMarca(marcaUpdate.getNombreMarca());
        marcaRepository.save(marcaModel);
    }

    @Override
    public Optional<MarcaModel> getById(Integer id) {
        return marcaRepository.findById(id);
    }
    
    @Override
    public Optional<MarcaModel> getByNombre(String nombre) {
        return marcaRepository.findByNombreMarca(nombre);
    }

    @Override
    public List<MarcaModel> getAll() {
        return marcaRepository.findAll();
    }
    
    
}
