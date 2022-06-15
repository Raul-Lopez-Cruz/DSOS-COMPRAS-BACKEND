/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dsos.compras.tec.ComprasV2.repository;

import dsos.compras.tec.ComprasV2.model.ModeloModel;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Oscar
 */
public interface ModeloRepository extends JpaRepository<ModeloModel, Integer>  {
    
}
