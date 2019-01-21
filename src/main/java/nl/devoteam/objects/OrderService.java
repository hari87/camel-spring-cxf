package nl.devoteam.objects;
import javax.jws.WebService;

@WebService
public interface OrderService {

    OrderResponse orderProcess(IncomingOrder input);

}
