/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ito.dsos.compras.repository;

import ito.dsos.compras.model.MarcaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

/**
 *
 * @author Oscar
 */
public interface MarcaRepository extends JpaRepository<MarcaModel, Integer> {
    @Query("select s from MarcaModel s where s.nombreMarca = ?1")
    Optional<MarcaModel> findByNombreMarca(String nombre);
}
