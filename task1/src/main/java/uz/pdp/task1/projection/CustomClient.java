package uz.pdp.task1.projection;

import org.springframework.data.rest.core.config.Projection;
import uz.pdp.task1.entity.Client;

@Projection(types = Client.class)
public interface CustomClient {

    Integer getId();
    String getName();
    String getPhoneNumber();


}
