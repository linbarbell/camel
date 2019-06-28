package com.emmett.orders;

import com.emmett.orders.bean.Order;
import com.emmett.orders.bean.Orders;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CamelRouter extends RouteBuilder {

    @Override
    public void configure() {

        rest("/orders")
                .get("{id}").outType(Order.class).to("direct:getOrder")
                .post().type(Order.class).to("direct:postOrder")
                .put().type(Order.class).to("direct:putOrder")
                .delete("{id}").to("direct:deleteOrder");

        rest("/list")
                .get().type(new ArrayList<Order>().getClass()).to("direct:getOrders");

        rest("/bulk")
                .post().type(Orders.class).to("direct:bulkOrder");

        from("direct:getOrders").to("bean:orderService?method=getOrders");
        from("direct:getOrder").to("bean:orderService?method=getOrder(${header.id})");
        from("direct:putOrder").to("bean:orderService?method=updateOrder");
        from("direct:deleteOrder").to("bean:orderService?method=cancelOrder(${header.id})");
        from("direct:postOrder").to("bean:orderService?method=createOrder");

        from("direct:bulkOrder")
                .process(exchange -> {
                    List<Order> orders = exchange.getIn().getBody(Orders.class).getOrdersList();
                    exchange.getIn().setBody(orders);
                })
                .split().jsonpath("$")
                .parallelProcessing(true)
                .convertBodyTo(Order.class)
                .to("direct:postOrder");
    }
}
