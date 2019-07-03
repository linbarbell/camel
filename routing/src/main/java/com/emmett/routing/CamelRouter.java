package com.emmett.routing;

import com.emmett.routing.bean.Order;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class CamelRouter extends RouteBuilder {

    @Override
    public void configure() {

        onException(Exception.class).log("${exception.message}");

        rest("/bulk")
                .post().type(new ArrayList<Order>().getClass()).to("direct:bulkOrder");

        from("direct:bulkOrder")
                .split().body()
                .parallelProcessing(true)
                .aggregationStrategy(new ListAggregationStrategy())
                .to("direct:postOrder");

        from("direct:postOrder")
                .log("${body}")
                .marshal().json(JsonLibrary.Jackson)
                .removeHeaders("CamelHttp*")
                .setHeader(Exchange.HTTP_METHOD, constant("POST"))
                .setHeader("Content-Type", constant("application/json"))
                .to("http://localhost:8080/api/orders");
    }
}
