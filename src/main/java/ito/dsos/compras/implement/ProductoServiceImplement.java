package ito.dsos.compras.implement;

import ito.dsos.compras.model.ProductoModel;
import ito.dsos.compras.repository.ProductoRepository;
import ito.dsos.compras.service.ProductoService;
import ito.dsos.compras.model.MarcaModel;
import ito.dsos.compras.model.ModeloModel;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class ProductoServiceImplement implements ProductoService {

    private final Log LOG = LogFactory.getLog(ProductoService.class);

    @Autowired
    private ProductoRepository productoRepository;

    public ProductoServiceImplement(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    @Override
    public void vender(Integer id, Integer cantidad) {
        Optional<ProductoModel> producto = productoRepository.findById(id);
        if (producto.isPresent()) {
            ProductoModel productoUpdate = producto.get();
            productoUpdate.setStock(productoUpdate.getStock() - cantidad);
            productoRepository.save(productoUpdate);
        }
    }

    @Override
    public void devolver(int parseInt, int unidades) {
        Optional<ProductoModel> producto = productoRepository.findById(parseInt);
        if (producto.isPresent()) {
            ProductoModel productoUpdate = producto.get();
            productoUpdate.setStock(productoUpdate.getStock() + unidades);
            productoRepository.save(productoUpdate);
        }
    }

    @Override
    public void createProducto(ProductoModel producto) {
        productoRepository.save(producto);
    }

    @Override
    public void save(ProductoModel nuevoProducto) {
        productoRepository.save(nuevoProducto);
    }
    
    @Override
    public ProductoModel save2(ProductoModel nuevoProducto) {
        return productoRepository.save(nuevoProducto);
    }

    @Override
    public void delete(Integer id) {
        productoRepository.deleteById(id);
    }

    @Override
    public void update(ProductoModel productoUpdate, Integer id) {
        ProductoModel productoModel = productoRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("El producto no existe"));
        if (productoUpdate.getTalla() != null) {
            productoModel.setTalla(productoUpdate.getTalla());
        }
        if (productoUpdate.getModelo() != null) {
            productoModel.setModelo(productoUpdate.getModelo());
        }
        if (productoUpdate.getColor() != null) {
            productoModel.setColor(productoUpdate.getColor());
        }
        if (productoUpdate.getMarca() != null) {
            productoModel.setMarca(productoUpdate.getMarca());
        }
        if (productoUpdate.getPrecioCompra() != null) {
            productoModel.setPrecioCompra(productoUpdate.getPrecioCompra());
        }
        if (productoUpdate.getPrecioVenta() != null) {
            productoModel.setPrecioVenta(productoUpdate.getPrecioVenta());
        }
        if (productoUpdate.getStock() != null) {
            productoModel.setStock(productoUpdate.getStock());
        }

        productoRepository.save(productoModel);
    }

    @Override
    public Optional<ProductoModel> getById(Integer id) {
        return productoRepository.findById(id);
    }

    @Override
    public Optional<ProductoModel> getByDatos(Double talla, String color, ModeloModel modelo, MarcaModel marca) {
        return productoRepository.findByDatos(talla, color, modelo, marca);
    }
    
    @Override
    public List<ProductoModel> getAll() {
        return productoRepository.findAll();
    }

    @Override
    public Collection<ProductoModel> getAllMarca(MarcaModel marca) {
        return productoRepository.findByMarca(marca);
    }

    @Override
    public Collection<ProductoModel> getAllModelo(ModeloModel modelo) {
        return productoRepository.findByModelo(modelo);
    }

    @Override
    public Collection<ProductoModel> getAllModeloMarca(ModeloModel modelo, MarcaModel marca) {
        return productoRepository.findByModeloMarca(modelo,marca);
    }
    
    
    
}
