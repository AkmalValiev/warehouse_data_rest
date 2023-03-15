package uz.pdp.task1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.task1.entity.Input;

public interface InputRepository extends JpaRepository<Input, Integer> {
}
