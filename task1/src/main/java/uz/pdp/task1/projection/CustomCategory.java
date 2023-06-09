package uz.pdp.task1.projection;

import org.springframework.data.rest.core.config.Projection;
import uz.pdp.task1.entity.Category;

@Projection(types = Category.class)
public interface CustomCategory {

    Integer getId();

    String getName();

    boolean getActive();

    Category getParentCategory();

}
