package dsos.compras.tec.ComprasV2.implement;

import dsos.compras.tec.ComprasV2.model.ProductoModel;
import dsos.compras.tec.ComprasV2.repository.ProductoRepository;
import dsos.compras.tec.ComprasV2.service.ProductoService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public void delete(Integer id) {
        productoRepository.deleteById(id);
    }

    @Override
    public void update(ProductoModel productoUpdate, Integer id) {
        ProductoModel productoModel = productoRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("El producto no existe"));
        productoModel.setTalla(productoUpdate.getTalla());
        productoModel.setModelo(productoUpdate.getModelo());
        productoModel.setColor(productoUpdate.getColor());
        productoModel.setMarca(productoUpdate.getMarca());
        productoModel.setPrecioCompra(productoUpdate.getPrecioCompra());
        productoModel.setPrecioVenta(productoUpdate.getPrecioVenta());
        productoModel.setStock(productoUpdate.getStock());
        productoRepository.save(productoModel);
    }

    @Override
    public Optional<ProductoModel> getById(Integer id) {
        return productoRepository.findById(id);
    }

    @Override
    public List<ProductoModel> getAll() {
        return productoRepository.findAll();
    }
}

