package com.example.OnlineFoodOrdering.service;

import com.example.OnlineFoodOrdering.model.Order;
import com.example.OnlineFoodOrdering.model.User;
import com.example.OnlineFoodOrdering.request.OrderRequest;
import org.aspectj.weaver.ast.Or;
import org.ietf.jgss.Oid;

import java.util.List;

public interface OrderService {
    public Order createOrder(OrderRequest oder, User user) throws Exception;
    public Order updateOrder(Long orderId,String orderStatus) throws Exception;
    public void cancelOrder(Long orderId) throws Exception;
    public List<Order> getUserOrder(Long userId) throws Exception;
    public List<Order> getRestaurantsOrder(Long restaurantId,String orderStatus) throws Exception;
    public Order findOrderById(Long orderId) throws Exception;
}
