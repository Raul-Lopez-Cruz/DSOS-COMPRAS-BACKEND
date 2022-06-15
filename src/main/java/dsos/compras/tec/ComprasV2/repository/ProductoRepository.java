package dsos.compras.tec.ComprasV2.repository;

import dsos.compras.tec.ComprasV2.model.ProductoModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<ProductoModel, Integer> {

}
