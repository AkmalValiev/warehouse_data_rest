package uz.pdp.task1.payload;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OutputProductDto {

    @NotNull(message = "productId null bo'lmasligi shart!")
    private Integer productId;

    @NotNull(message = "amount null bo'lmasligi shart!")
    private Double amount;

    @NotNull(message = "price null bo'lmasligi shart!")
    private Double price;

    @NotNull(message = "outputId null bo'lmasligi shart!")
    private Integer outputId;

}
