/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ito.dsos.compras.service;

import ito.dsos.compras.model.CompraModel;
import ito.dsos.compras.model.DetalleCompraModel;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Oscar
 */
@Service
public interface DetalleCompraService {

    public void save(DetalleCompraModel nuevoDetalle);

    public DetalleCompraModel save2(DetalleCompraModel nuevoDetalle);
    
    public void delete(Integer id);

    //public void update(DetalleCompraModel compraUpdate, Integer id);
    public Optional<DetalleCompraModel> getById(Integer id);

    public List<DetalleCompraModel> getAll();

    public Collection<DetalleCompraModel> getAllCompra(CompraModel compra);
}
