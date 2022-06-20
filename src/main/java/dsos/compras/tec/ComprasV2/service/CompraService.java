/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ito.dsos.compras.service;

import ito.dsos.compras.model.CompraModel;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Oscar
 */
@Service
public interface CompraService {

    public void save(CompraModel nuevaCompra);

    public CompraModel save2(CompraModel nuevaCompra);
    
    public void delete(Integer id);

    public void update(CompraModel compraUpdate, Integer id);
    
    public Optional<CompraModel> getById(Integer id);

    public List<CompraModel> getAll();

    public Optional<CompraModel> getByCompra(Double total,LocalDateTime fechaAdquirido);
}
