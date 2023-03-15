package uz.pdp.task1.payload;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InputDto {

    @NotNull(message = "warehouseId null bo'lmasligi kerak!")
    private Integer warehouseId;

    @NotNull(message = "supplierId null bo'lmasligi kerak!")
    private Integer supplierId;

    @NotNull(message = "currencyId null bo'lmasligi kerak!")
    private Integer currencyId;

    @NotNull(message = "factureNumber null bo'lmasligi kerak!")
    private String factureNumber;

}
