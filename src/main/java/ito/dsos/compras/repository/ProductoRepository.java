package ito.dsos.compras.repository;

import ito.dsos.compras.model.ProductoModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<ProductoModel, Integer> {

}
