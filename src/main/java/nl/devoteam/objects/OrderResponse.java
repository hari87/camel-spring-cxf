package nl.devoteam.objects;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderResponse {
    private String order_status;
    private String warehouse_location;
}
