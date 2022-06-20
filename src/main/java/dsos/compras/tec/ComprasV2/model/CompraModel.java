/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ito.dsos.compras.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 *
 * @author Oscar
 */
@Entity
@Table(name = "CompraModel")
public class CompraModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idCompra", nullable = false, updatable = false, length = 10)
    private Integer idCompra;

    @Column(name = "total", nullable = false, length = 10, precision = 2)
    private Double total;

    @Column(name = "fechaAdquirido", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime fechaAdquirido;
/*
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "detalleCompras")
    private List<DetalleCompraModel> detalleCompras;
*/
    public CompraModel() {

    }

    public Integer getIdCompra() {
        return idCompra;
    }

    public void setIdCompra(Integer idCompra) {
        this.idCompra = idCompra;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public LocalDateTime getFechaAdquirido() {
        return fechaAdquirido;
    }

    public void setFechaAdquirido(LocalDateTime fechaAdquirido) {
        this.fechaAdquirido = fechaAdquirido;
    }
/*
    public List<DetalleCompraModel> getDetalleCompras() {
        return detalleCompras;
    }

    public void setDetalleCompras(List<DetalleCompraModel> detalleCompras) {
        this.detalleCompras = detalleCompras;
    }
*/
}
