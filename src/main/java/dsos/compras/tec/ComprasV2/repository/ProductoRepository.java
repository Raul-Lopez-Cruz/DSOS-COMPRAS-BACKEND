package dsos.compras.tec.ComprasV2.repository;

<<<<<<< Updated upstream:src/main/java/dsos/compras/tec/ComprasV2/repository/ProductoRepository.java
import dsos.compras.tec.ComprasV2.model.ProductoModel;
=======
import ito.dsos.compras.model.MarcaModel;
import ito.dsos.compras.model.ModeloModel;
import ito.dsos.compras.model.ProductoModel;
>>>>>>> Stashed changes:src/main/java/ito/dsos/compras/repository/ProductoRepository.java
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.Optional;

public interface ProductoRepository extends JpaRepository<ProductoModel, Integer> {
<<<<<<< Updated upstream:src/main/java/dsos/compras/tec/ComprasV2/repository/ProductoRepository.java
=======

    @Query("select s from ProductoModel s where s.marca = ?1")
    Collection<ProductoModel> findByMarca(MarcaModel marca);

    @Query("select s from ProductoModel s where s.modelo = ?1")
    Collection<ProductoModel> findByModelo(ModeloModel modelo);

    @Query("select s from ProductoModel s where s.modelo = ?1 and s.marca = ?2")
    Collection<ProductoModel> findByModeloMarca(ModeloModel modelo, MarcaModel marca);

    @Query("select s from ProductoModel s where s.talla = ?1 and s.color = ?2 and s.modelo = ?3 and s.marca = ?4")
    Optional<ProductoModel> findByDatos(Double talla, String color, ModeloModel modelo, MarcaModel marca);

>>>>>>> Stashed changes:src/main/java/ito/dsos/compras/repository/ProductoRepository.java
}
