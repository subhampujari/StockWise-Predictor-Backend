package com.cerebromarkets.response;

import com.cerebromarkets.domain.OrderType;
import lombok.Data;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class CreateOrderRequest {
    public OrderType getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public String getCoinId() {
        return coinId;
    }

    public void setCoinId(String coinId) {
        this.coinId = coinId;
    }

    private String coinId;
    private double quantity;
    private OrderType orderType;
}
