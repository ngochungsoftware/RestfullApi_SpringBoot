package com.example.OnlineFoodOrdering.request;

import com.example.OnlineFoodOrdering.model.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {

    private Long restaurantId;
    private Address deliveryAddress;
}
