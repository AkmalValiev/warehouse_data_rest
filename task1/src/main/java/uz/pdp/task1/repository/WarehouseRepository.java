package uz.pdp.task1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import uz.pdp.task1.entity.Warehouse;
import uz.pdp.task1.projection.CustomWarehouse;

@RepositoryRestResource(path = "warehouse", collectionResourceRel = "warehouse list", excerptProjection = CustomWarehouse.class)
public interface WarehouseRepository extends JpaRepository<Warehouse, Integer> {

}
