package com.BookStoreBE.utilityClasses;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {
    private float totalAmount;
    private List<Integer> cartItemIds;
    private String address;

    private Integer userId;

    private String paymentMode;
}
