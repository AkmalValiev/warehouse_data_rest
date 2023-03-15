package uz.pdp.task1.payload;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OutputDto {

    @NotNull(message = "warehouseId null bo'lmasligi shart!")
    private Integer warehouseId;

    @NotNull(message = "currencyId null bo'lmasligi shart!")
    private Integer currencyId;

    @NotNull(message = "factureNumber null bo'lmasligi shart!")
    private String factureNUmber;

    @NotNull(message = "clientId null bo'lmasligi shart!")
    private Integer clientId;

}
