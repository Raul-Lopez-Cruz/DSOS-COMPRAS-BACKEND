package ito.dsos.compras.service;

import ito.dsos.compras.model.ProductoModel;
import ito.dsos.compras.model.MarcaModel;
import ito.dsos.compras.model.ModeloModel;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public interface ProductoService {

    public void createProducto(ProductoModel producto);

    public void save(ProductoModel nuevoProducto);
    
    public ProductoModel save2(ProductoModel nuevoProducto);

    public void delete(Integer id);

    public void update(ProductoModel productoUpdate, Integer id);

    public Optional<ProductoModel> getById(Integer id);

    public List<ProductoModel> getAll();
    

    public void vender(Integer id, Integer cantidad);

    public void devolver(int id, int unidades);

    public Collection<ProductoModel> getAllMarca(MarcaModel marca);

    public Collection<ProductoModel> getAllModelo(ModeloModel modelo);

    public Collection<ProductoModel> getAllModeloMarca(ModeloModel modelo, MarcaModel marca);

   public Optional<ProductoModel> getByDatos(Double talla, String color, ModeloModel modelo, MarcaModel marca);
}
