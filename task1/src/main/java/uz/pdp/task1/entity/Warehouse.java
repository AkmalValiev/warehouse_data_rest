package uz.pdp.task1.entity;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import uz.pdp.task1.entity.abs.AbsObject;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Warehouse extends AbsObject {
}
