package uz.pdp.task1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.task1.entity.Output;

public interface OutputRepository extends JpaRepository<Output, Integer> {
}
