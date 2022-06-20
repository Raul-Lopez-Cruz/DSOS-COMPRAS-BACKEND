/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ito.dsos.compras.model;

import javax.persistence.*;

/**
 *
 * @author Oscar
 */
@Entity
@Table(name = "ModeloModel")
public class ModeloModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idModelo", nullable = false, updatable = false, length = 10)
    private Integer idModelo;

    @Column(name = "nombreModelo", nullable = false, length = 20)
    private String nombreModelo;

    public ModeloModel() {
    }

    public Integer getIdModelo() {
        return idModelo;
    }

    public void setIdModelo(Integer idModelo) {
        this.idModelo = idModelo;
    }

    public String getNombreModelo() {
        return nombreModelo;
    }

    public void setNombreModelo(String numeroModelo) {
        this.nombreModelo = numeroModelo;
    }

}
