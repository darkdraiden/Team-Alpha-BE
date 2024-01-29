package com.BookStoreBE.Service;

import com.BookStoreBE.Model.CartItems;
import com.BookStoreBE.Model.Book;
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

            Optional<Book> opBook=bookRepository.findById(cartItem.getBookId());
            Float price=opBook.get().getMrp()*(100-opBook.get().getDiscount())/100;
            cartItem.setPrice(price);
            cartItem.setBookName(opBook.get().getTitle());
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

    public ApiResponse<List<CartItems>> getCartItems(Integer userId){
        //List<CartItems> allItems= new ArrayList<>();
        List<CartItems> allItems= cartRepository.findCartItemsByIdCustom(userId);



        return new ApiResponse<List<CartItems>>(
                200,
                "success",
                "Items retrieved",
                allItems

        );

    }


    public ApiResponse<String> removeCartItem(Integer cartItemId){
        Optional<CartItems> currItem=cartRepository.findById(cartItemId);

        if(currItem.isEmpty()) {
            return new ApiResponse<String>(
                    404,
                    "fail",
                    "Item not found",
                    null
            );
        }

        cartRepository.deleteById(cartItemId);
        return new ApiResponse<String>(
                200,
                "success",
                "Cart Item Deleted Successfully",
                "Deleted"
        );

    }

    public ApiResponse<String> clearUserCart(Integer userId){
        Integer rowsAffected=cartRepository.clearCartByUserId(userId);

        if(rowsAffected==0){
            return new ApiResponse<String>(
                    200,
                    "success",
                    "Cart Already Empty",
                    null
            );
        }

        return new ApiResponse<String>(
                200,
                "success",
                "Cart Cleared Successfully",
                "Cleared"
        );
    }
}
