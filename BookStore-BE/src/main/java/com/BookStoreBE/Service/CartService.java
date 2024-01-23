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

    public ApiResponse<String> createCartItem(CartItems cartItem){
        Optional<CartItems> newItem=cartRepository.findById(cartItem.getCartItemId());

        if(newItem.isPresent()){
            //cartRepository.increaseCount(cartItem.getCartItemId(),cartItem.getQuantity()+1);
            CartItems currItem=newItem.get();
            currItem.setQuantity(cartItem.getQuantity()+1);
            cartRepository.save(currItem);
        }
        else{

            cartRepository.save(cartItem);
        }

        return new ApiResponse<String>(
                201,
                "success",
                "Item added to cart",
                //bookRepository.getById(cartItem.getBookId()).getTitle()     // to be integrated with bookrep
                "book"
        );

    }

    public ApiResponse<String> deleteCartItem(Integer cartId){
        Optional<CartItems> toBeDeleted= cartRepository.findById(cartId);
        if(toBeDeleted.isEmpty()){
            return new ApiResponse<String>(
                    404,
                    "fail",
                    "Item not found",
                    null
            );
        }
        String name="demoBook";
        //String name=bookRepository.findById(toBeDeleted.get().getBookId()).get().getTitle();
        int newQuant=toBeDeleted.get().getQuantity();
        if(newQuant==1){
            cartRepository.deleteById(cartId);
        }
        else{
            cartRepository.decreaseCount(cartId,newQuant-1);
        }


        return new ApiResponse<String>(
                200,
                "success",
                "Item deleted from cart",
                name
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
