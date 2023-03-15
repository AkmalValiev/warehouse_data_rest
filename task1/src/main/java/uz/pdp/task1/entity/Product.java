package uz.pdp.task1.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.pdp.task1.entity.abs.AbsObject;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Product extends AbsObject {

    @ManyToOne
    private Category category;

    @OneToOne
    private Attachment file;

    @Column(nullable = false, unique = true)
    private String code;

    @ManyToOne
    private Measurement measurement;

}
