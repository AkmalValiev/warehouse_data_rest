package uz.pdp.task1.payload;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    @NotNull(message = "name null bo'lmasligi kerak!")
    private String name;

    private boolean active;

    @NotNull(message = "categoryId null bo'lmasligi kerak!")
    private Integer categoryId;

    private Integer attachmentId;

    @NotNull(message = "measurementId null bo'lmasligi kerak!")
    private Integer measurementId;

}
