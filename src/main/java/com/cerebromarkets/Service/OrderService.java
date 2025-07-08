package com.cerebromarkets.Service;

import com.cerebromarkets.domain.OrderType;
import com.cerebromarkets.modal.Coin;
import com.cerebromarkets.modal.Order;
import com.cerebromarkets.modal.OrderItem;
import com.cerebromarkets.modal.User;

import java.util.List;

public interface OrderService {

    Order createOrder(User user, OrderItem orderItem, OrderType orderType);

    Order getOrderById(Long orderId) throws Exception;

    List<Order>  getAllOrdersOfUser(Long userId,OrderType  OrderType,String assertSymbol);

    Order processOrder(Coin coin,double quantity,OrderType orderType,User user) throws Exception;

}
