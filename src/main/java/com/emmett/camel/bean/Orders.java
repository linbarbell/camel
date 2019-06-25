package com.emmett.camel.bean;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class Orders {
    private List<Order> ordersList;
}
