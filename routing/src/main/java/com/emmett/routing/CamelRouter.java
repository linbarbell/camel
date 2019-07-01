package com.emmett.routing;

import com.emmett.routing.bean.Order;
import com.emmett.routing.bean.Orders;
import com.google.gson.Gson;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CamelRouter extends RouteBuilder {


    @Override
    public void configure() {

        onException(Exception.class).log("${exception.message}");

        rest("/bulk")
                .post().type(Orders.class).to("direct:bulkOrder");

        from("direct:bulkOrder")
                .process(exchange -> {
                    List<Order> orders = exchange.getIn().getBody(Orders.class).getOrderList();
                    exchange.getIn().setBody(orders);
                })
                .split().body()
                .parallelProcessing(true)
                .to("direct:postOrder");

        from("direct:postOrder")
                .marshal().json(JsonLibrary.Jackson)
                .log("${body}")
                .removeHeaders("CamelHttp*")
                .setHeader(Exchange.HTTP_METHOD, constant("POST"))
                .setHeader("Content-Type", constant("application/json"))
                .to("http://localhost:8080/api/orders");
    }
}
