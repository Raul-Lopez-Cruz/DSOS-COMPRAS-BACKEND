package ito.dsos.compras.model;

import javax.persistence.*;

@Entity
@Table(name = "ProductoModel")
public class ProductoModel {

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(name = "idProducto", nullable = false, updatable = false, length = 10)
        private Integer idProducto;

        @Column(name = "precioCompra", nullable = false, length = 10,precision = 2)
        private Double precioCompra;

        @Column(name = "precioVenta", nullable = false, length = 10,precision = 2)
        private Double precioVenta;

        @Column(name = "talla", nullable = false, length = 2, precision = 1)
        private Double talla;

        @Column(name = "stock", nullable = false, length = 10)
        private Integer stock;

        @Column(name="color", nullable = false, length = 20)
        private String color;

        @Column(name="marca", nullable = false)
        private String marca;

        @Column(name="modelo", nullable = false)
        private String modelo;

    public ProductoModel() {
    }

    public ProductoModel(Integer idProducto, Double precioCompra, Double precioVenta, Double talla, Integer stock, String color, String marca, String modelo) {
        this.idProducto = idProducto;
        this.precioCompra = precioCompra;
        this.precioVenta = precioVenta;
        this.talla = talla;
        this.stock = stock;
        this.color = color;
        this.marca = marca;
        this.modelo = modelo;
    }

    public Integer getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Integer idProducto) {
        this.idProducto = idProducto;
    }

    public Double getPrecioCompra() {
        return precioCompra;
    }

    public void setPrecioCompra(Double precioCompra) {
        this.precioCompra = precioCompra;
    }

    public Double getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(Double precioVenta) {
        this.precioVenta = precioVenta;
    }

    public Double getTalla() {
        return talla;
    }

    public void setTalla(Double talla) {
        this.talla = talla;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer unidades) {
        this.stock = unidades;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }
}
