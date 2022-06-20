/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ito.dsos.compras.implement;

import ito.dsos.compras.model.CompraModel;
import ito.dsos.compras.repository.CompraRepository;
import ito.dsos.compras.service.CompraService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Oscar
 */
@Service
public class CompraServiceImplement implements CompraService {

    private final Log LOG = LogFactory.getLog(CompraService.class);

    @Autowired
    private CompraRepository compraRepository;

    public CompraServiceImplement(CompraRepository compraRepository) {
        this.compraRepository = compraRepository;
    }

    @Override
    public void save(CompraModel nuevaCompra) {
        compraRepository.save(nuevaCompra);
    }
    
    @Override
    public CompraModel save2(CompraModel com){
        return compraRepository.save(com);
    }

    @Override
    public void delete(Integer id) {
        compraRepository.deleteById(id);
    }

    
   @Override
    public void update(CompraModel compraUpdate, Integer id) {
        CompraModel productoModel = compraRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("La compra no existe"));
        if(compraUpdate.getTotal()!=null)
        productoModel.setTotal(compraUpdate.getTotal());
        
        compraRepository.save(compraUpdate);
    }
     
    @Override
    public Optional<CompraModel> getById(Integer id) {
        return compraRepository.findById(id);
    }

    @Override
    public List<CompraModel> getAll() {
        return compraRepository.findAll();
    }
    @Override
    public Optional<CompraModel> getByCompra(Double total,LocalDateTime fechaAdquirido) {
        return compraRepository.findByCompra(total, fechaAdquirido);
    }
    
}
