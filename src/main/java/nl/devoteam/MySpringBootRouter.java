package nl.devoteam;

import nl.devoteam.objects.IncomingOrder;
import nl.devoteam.objects.OrderResponse;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.cxf.CxfEndpoint;
import org.apache.camel.component.cxf.DataFormat;
import org.springframework.stereotype.Component;

/**
 * A simple Camel route that triggers from a timer and calls a bean and prints to system out.
 * <p/>
 * Use <tt>@Component</tt> to make Camel auto detect this route when starting.
 */
@Component
public class MySpringBootRouter extends RouteBuilder {

    CamelContext camelContext = getContext();

    CxfEndpoint cxfEndpoint = new CxfEndpoint();


    @Override
    public void configure() throws Exception{
        cxfEndpoint.setAddress("http://localhost:8083/interface");
        cxfEndpoint.setServiceClass(nl.devoteam.objects.OrderService.class);
        cxfEndpoint.setCamelContext(camelContext);
        cxfEndpoint.setDataFormat(DataFormat.POJO);
        from(cxfEndpoint)
                .to("log:body")
                .to("direct:orderProcess");

        from("direct:orderProcess")
                .log("inside Order Process")
                .process(new Processor() {
                    public void process(Exchange exchange) throws Exception {
                        OrderResponse output = new OrderResponse();
                        output.setOrder_status("processing");
                        output.setWarehouse_location("Nederland");
                        exchange.getOut().setBody(output);
                    }
                })
                .to("log:output");


            from("timer://testTimer?repeatCount=10&delay=30s")
        .setHeader(Exchange.HTTP_METHOD, constant("POST"))
                    .setBody()
                    .simple("resource:classpath:test.xml")
                    //.log("${body}")
                    .to("http://localhost:8083/interface/orderProcess");

    }

}
