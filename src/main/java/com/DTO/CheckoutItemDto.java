package com.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckoutItemDto {
    private String productName;
    private int  quantity;
    private double price;
    private long productId;

}
