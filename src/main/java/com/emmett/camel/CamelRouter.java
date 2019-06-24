package com.emmett.camel;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class CamelRouter extends RouteBuilder {

    @Override
    public void configure() {

        rest("/orders")
            .get("{id}").outType(Order.class).to("bean:orderService?method=getOrder(${header.id})")
            .post().type(Order.class).to("bean:orderService?method=createOrder")
            .put().type(Order.class).to("bean:orderService?method=updateOrder")
            .delete("{id}").to("bean:orderService?method=cancelOrder(${header.id})");

        rest()
    }
}
