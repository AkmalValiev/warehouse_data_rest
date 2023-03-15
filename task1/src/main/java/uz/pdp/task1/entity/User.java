package uz.pdp.task1.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Set;

@Data
@Entity(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String phoneNumber;

    @Column(nullable = false, unique = true)
    private String code;

    @Column(nullable = false)
    private String password;

    private boolean active;

    @ManyToMany
    private Set<Warehouse> warehouses;

}
