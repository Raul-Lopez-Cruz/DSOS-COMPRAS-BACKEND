/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ito.dsos.compras.implement;

import ito.dsos.compras.model.CompraModel;
import ito.dsos.compras.model.DetalleCompraModel;
import ito.dsos.compras.repository.DetalleCompraRepository;
import ito.dsos.compras.service.DetalleCompraService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Oscar
 */
@Service
public class DetalleCompraImplement implements DetalleCompraService {

    private final Log LOG = LogFactory.getLog(DetalleCompraService.class);

    @Autowired
    private DetalleCompraRepository detalleCompraRepository;

    public DetalleCompraImplement(DetalleCompraRepository detalleCompraRepository) {
        this.detalleCompraRepository = detalleCompraRepository;
    }

    @Override
    public void save(DetalleCompraModel nuevoDetalle) {
        detalleCompraRepository.save(nuevoDetalle);
    }

    @Override
    public DetalleCompraModel save2(DetalleCompraModel nuevoDetalle) {
        return detalleCompraRepository.save(nuevoDetalle);
    }
    
    @Override
    public void delete(Integer id) {
        detalleCompraRepository.deleteById(id);
    }

    /*
    @Override
    public void update(DetalleCompraModel compraUpdate, Integer id) {
        DetalleCompraModel productoModel = detalleCompraRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("La compra no existe"));
        productoModel.setFechaAdquirido(compraUpdate.getFechaAdquirido());
        productoModel.s
        detalleCompraRepository.save(productoModel);
    }
     */
    @Override
    public Optional<DetalleCompraModel> getById(Integer id) {
        return detalleCompraRepository.findById(id);
    }

    @Override
    public List<DetalleCompraModel> getAll() {
        return detalleCompraRepository.findAll();
    }
    @Override
    public Collection<DetalleCompraModel> getAllCompra(CompraModel compra) {
        return detalleCompraRepository.findByModeloMarca( compra);
    }
    
    
    

}
