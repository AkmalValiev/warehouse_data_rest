package uz.pdp.task1.payload;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    @NotNull(message = "firstName null bo'lmasligi shart!")
    private String firstName;

    @NotNull(message = "lastName null bo'lmasligi shart!")
    private String lastName;

    @NotNull(message = "phoneNumber null bo'lmasligi shart!")
    private String phoneNumber;

    @NotNull(message = "password null bo'lmasligi shart!")
    private String password;

    private boolean active;

    @NotNull(message = "warehousesId null bo'lmasligi shart!")
    private Set<Integer> warehousesId;

}
