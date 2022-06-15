package dsos.compras.tec.ComprasV2.model;

import javax.persistence.*;

@Entity
@Table(name = "ProductoModel")
public class ProductoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idProducto", nullable = false, updatable = false, length = 10)
    private Integer idProducto;

    @Column(name = "precioCompra", nullable = false, length = 10, precision = 2)
    private Double precioCompra;

    @Column(name = "precioVenta", nullable = false, length = 10, precision = 2)
    private Double precioVenta;

    @Column(name = "talla", nullable = false, length = 2, precision = 1)
    private Double talla;

    @Column(name = "stock", nullable = false, length = 10)
    private Integer stock;

    @Column(name = "color", nullable = false, length = 20)
    private String color;

    @ManyToOne
    @JoinColumn(name = "marca", nullable = false)
    private MarcaModel marca;

    @ManyToOne
    @JoinColumn(name = "modelo", nullable = false)
    private ModeloModel modelo;

    public ProductoModel() {
    }

    public ProductoModel(Integer idProducto, Double precioCompra, Double precioVenta, Double talla, Integer stock, String color, MarcaModel marca, ModeloModel modelo) {
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

    public MarcaModel getMarca() {
        return marca;
    }

    public void setMarca(MarcaModel marca) {
        this.marca = marca;
    }

    public ModeloModel getModelo() {
        return modelo;
    }

    public void setModelo(ModeloModel modelo) {
        this.modelo = modelo;
    }

}
