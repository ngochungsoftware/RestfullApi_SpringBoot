package com.example.OnlineFoodOrdering.repository;

import com.example.OnlineFoodOrdering.model.Order;
import com.example.OnlineFoodOrdering.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem,Long> {
}
