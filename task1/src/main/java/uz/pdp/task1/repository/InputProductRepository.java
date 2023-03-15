package uz.pdp.task1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.task1.entity.InputProduct;

public interface InputProductRepository extends JpaRepository<InputProduct, Integer> {
}
