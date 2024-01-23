package com.BookStoreBE.Controller;

import com.BookStoreBE.Model.CartItems;
import com.BookStoreBE.Model.User;
import com.BookStoreBE.Service.CartService;
import com.BookStoreBE.utilityClasses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="api/v1/cart")
public class CartController {

    @Autowired
    CartService cartService;

    @PostMapping
    public ResponseEntity<ApiResponse> createItem(@RequestBody CartItems cartItem){
        ApiResponse<String> resultResponse=cartService.createCartItem(cartItem);

        return new ResponseEntity<>(
                resultResponse,
                HttpStatusCode.valueOf(resultResponse.getStatusCode())
        );
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse> deleteItem(@RequestBody CartItems cartItem){
        ApiResponse<String> resultResponse=cartService.deleteCartItem(cartItem.getCartItemId());

        return new ResponseEntity<>(
                resultResponse,
                HttpStatusCode.valueOf(resultResponse.getStatusCode())
        );

    }

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse> getItems(@PathVariable Integer userId){
        ApiResponse<List<CartItems>> resultResponse=cartService.getCartItems(userId);
        return new ResponseEntity<>(
                resultResponse,
                HttpStatusCode.valueOf(resultResponse.getStatusCode())
        );
    }
}
