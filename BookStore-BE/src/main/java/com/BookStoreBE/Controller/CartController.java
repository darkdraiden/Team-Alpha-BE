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


@CrossOrigin
@RestController
@RequestMapping(path="api/v1/cart")
public class CartController {

    @Autowired
    CartService cartService;

    // Creates cart item if not present already(id==null)
    // Increments its qty if already present(id!=null)
    @PostMapping(path="/create")
    public ResponseEntity<ApiResponse> createItem(@RequestBody CartItems cartItem){
        ApiResponse<CartItems> resultResponse=cartService.createCartItem(cartItem);

        return new ResponseEntity<>(
                resultResponse,
                HttpStatusCode.valueOf(resultResponse.getStatusCode())
        );
    }

    // Decrements qty if qty >1
    // Removes cartItem from db if qty==1
    @PostMapping(path="/remove")
    public ResponseEntity<ApiResponse> deleteItem(@RequestBody CartItems cartItem){
        ApiResponse<CartItems> resultResponse=cartService.deleteCartItem(cartItem);

        return new ResponseEntity<>(
                resultResponse,
                HttpStatusCode.valueOf(resultResponse.getStatusCode())
        );
    }

    // Returns list of all cartItems for a specific user given by {userId}
    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse> getItems(@PathVariable Integer userId){
        ApiResponse<List<CartItems>> resultResponse=cartService.getCartItems(userId);
        return new ResponseEntity<>(
                resultResponse,
                HttpStatusCode.valueOf(resultResponse.getStatusCode())
        );
    }

    //Removes a cart item completely
    @DeleteMapping("remove/{cartItemId}")
    public ResponseEntity<ApiResponse<String>> removeItem(@PathVariable Integer cartItemId){
        ApiResponse<String> resultResponse=cartService.removeCartItem(cartItemId);
        return new ResponseEntity<>(
                resultResponse,
                HttpStatusCode.valueOf(resultResponse.getStatusCode())
        );
    }


    //Clear the cart items
    @DeleteMapping("clear/{userId}")
    public ResponseEntity<ApiResponse<String>> clearCart(@PathVariable Integer userId){
        ApiResponse<String> resultResponse=cartService.clearUserCart(userId);
        return new ResponseEntity<>(
                resultResponse,
                HttpStatusCode.valueOf(resultResponse.getStatusCode())
        );
    }
}
