/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dsos.compras.tec.ComprasV2.model;

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

    @Column(name = "numeroModelo", nullable = false, length = 10)
    private Integer numeroModelo;

    public ModeloModel() {
    }

    public ModeloModel(Integer idModelo, Integer numeroModelo) {
        this.idModelo = idModelo;
        this.numeroModelo = numeroModelo;
    }

    public Integer getIdModelo() {
        return idModelo;
    }

    public void setIdModelo(Integer idModelo) {
        this.idModelo = idModelo;
    }

    public Integer getNumeroModelo() {
        return numeroModelo;
    }

    public void setNumeroModelo(Integer numeroModelo) {
        this.numeroModelo = numeroModelo;
    }

}
