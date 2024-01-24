package com.BookStoreBE.Service;

import com.BookStoreBE.Model.CartItems;
import com.BookStoreBE.Model.User;
import com.BookStoreBE.Repository.BookRepository;
import com.BookStoreBE.Repository.CartRepository;
import com.BookStoreBE.utilityClasses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    CartRepository cartRepository;
    @Autowired
    BookRepository bookRepository;

    public ApiResponse<CartItems> createCartItem(CartItems cartItem){
        String finalResponse;
        if(cartItem.getCartItemId()==null){
            cartRepository.save(cartItem);
            finalResponse="Item created in the cart";
            return new ApiResponse<CartItems>(
                    201,
                    "success",
                    finalResponse,
                    cartItem
            );
        }
        else{
            Optional<CartItems> newItem=cartRepository.findById(cartItem.getCartItemId());
            CartItems currItem=newItem.get();
            currItem.setQuantity(cartItem.getQuantity()+1);
            cartRepository.save(currItem);
            finalResponse="Item added to cart";
            return new ApiResponse<CartItems>(
                    201,
                    "success",
                    finalResponse,
                    currItem
            );
        }



    }

    public ApiResponse<CartItems> deleteCartItem(CartItems cartItems){
        Optional<CartItems> toBeDeleted= cartRepository.findById(cartItems.getCartItemId());
        if(toBeDeleted.isEmpty()){
            return new ApiResponse<CartItems>(
                    404,
                    "fail",
                    "Item not found",
                    null
            );
        }
        String finalResponse;
        int currQuant=toBeDeleted.get().getQuantity();
        if(currQuant==1){
            cartRepository.deleteById(cartItems.getCartItemId());
            finalResponse="Item no more in the cart";
            return new ApiResponse<CartItems>(
                    200,
                    "success",
                    finalResponse,
                    null
            );

        }
        else{
            cartItems.setQuantity(cartItems.getQuantity()-1);
            cartRepository.save(cartItems);
            finalResponse="Item removed from the cart";

        }


        return new ApiResponse<CartItems>(
                200,
                "success",
                finalResponse,
                cartItems
        );
    }

    public ApiResponse<List<CartItems>> getCartItems(Integer cartId){
        //List<CartItems> allItems= new ArrayList<>();
        List<CartItems> allItems= cartRepository.findCartItemsByIdCustom(cartId);

        return new ApiResponse<List<CartItems>>(
                200,
                "success",
                "Items retrieved",
                allItems

        );

    }
}
