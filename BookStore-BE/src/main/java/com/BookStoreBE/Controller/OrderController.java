package com.BookStoreBE.Controller;


import com.BookStoreBE.Service.OrderService;
import com.BookStoreBE.utilityClasses.ApiResponse;
import com.BookStoreBE.utilityClasses.OrderRequest;
import org.hibernate.query.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<ApiResponse<String>> placeOrder(@RequestBody OrderRequest orderRequest){
        ApiResponse<String> res = orderService.placeOrder(orderRequest);

        return new ResponseEntity<>(res, HttpStatusCode.valueOf(res.getStatusCode()));
    }

}
