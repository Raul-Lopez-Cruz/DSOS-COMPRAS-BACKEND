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
@Table(name = "MarcaModel")
public class MarcaModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idMarca", nullable = false, updatable = false, length = 10)
    private Integer idMarca;
    
    @Column(name = "nombreMarca", nullable = false, length = 20)
    private String nombreMarca;

    public MarcaModel() {
    }


    public Integer getIdMarca() {
        return idMarca;
    }

    public void setIdMarca(Integer idMarca) {
        this.idMarca = idMarca;
    }

    public String getNombreMarca() {
        return nombreMarca;
    }

    public void setNombreMarca(String nombreMarca) {
        this.nombreMarca = nombreMarca;
    }
    
    

}
