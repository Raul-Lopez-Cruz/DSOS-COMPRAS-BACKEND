/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ito.dsos.compras.repository;

import ito.dsos.compras.model.CompraModel;
import ito.dsos.compras.model.DetalleCompraModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

/**
 *
 * @author Oscar
 */
public interface DetalleCompraRepository extends JpaRepository<DetalleCompraModel, Integer> {

    @Query("select s from DetalleCompraModel s where s.compra = ?1")
    Collection<DetalleCompraModel> findByModeloMarca(CompraModel compra);
}
