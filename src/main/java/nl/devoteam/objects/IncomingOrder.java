package nl.devoteam.objects;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class IncomingOrder {

    private int customer_id;
    private String order_item;
    private int order_quantity;

}
