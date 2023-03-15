package uz.pdp.task1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.task1.entity.OutputProduct;

public interface OutputProductRepository extends JpaRepository<OutputProduct, Integer> {
}
