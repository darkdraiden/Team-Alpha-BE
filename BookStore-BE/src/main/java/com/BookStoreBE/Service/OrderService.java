package com.BookStoreBE.Service;

import com.BookStoreBE.Model.CartItems;
import com.BookStoreBE.Model.OrderDetail;
import com.BookStoreBE.Model.OrderItem;
import com.BookStoreBE.Repository.CartRepository;
import com.BookStoreBE.Repository.OrderDetailRepository;
import com.BookStoreBE.Repository.OrderItemRepository;
import com.BookStoreBE.utilityClasses.ApiResponse;
import com.BookStoreBE.utilityClasses.OrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private CartRepository cartItemRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;
    public ApiResponse<String> placeOrder(OrderRequest orderRequest){
        float amount = orderRequest.getTotalAmount();
        List<Integer> cartItemIds = orderRequest.getCartItemIds();
        String address = orderRequest.getAddress();
        Integer userId = orderRequest.getUserId();
        String paymentMode = orderRequest.getPaymentMode();

        if(cartItemIds.size()==0){
            return new ApiResponse<String>(
                    400,
                    "fail",
                    "empty cart! Include atleast one item",
                    null
            );
        }

        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setUserId(userId);
        orderDetail.setAmount(amount);
        orderDetail.setAddress(address);
        orderDetail.setPaymentMode(paymentMode);
        orderDetail.setCreatedAt(new Timestamp(System.currentTimeMillis()));

        orderDetail=orderDetailRepository.save(orderDetail);

        Integer orderDetailId = orderDetail.getOrderDetailId();

        for(Integer cartItemId:cartItemIds) {
            Optional<CartItems> opCartItem = cartItemRepository.findById(cartItemId);

            if(opCartItem.isEmpty()){
                return new ApiResponse<String>(
                        404,
                        "fail",
                        "Item not found in cart!",
                        null
                );
            }

            Integer bookId=opCartItem.get().getBookId();
            Integer qty=opCartItem.get().getQuantity();

            OrderItem orderItem = new OrderItem();
            orderItem.setOrderDetailId(orderDetailId);
            orderItem.setBookId(bookId);
            orderItem.setQuantity(qty);

            orderItemRepository.save(orderItem);

        }

        for(Integer cartItemId:cartItemIds) {
            cartItemRepository.deleteById(cartItemId);
        }


        return new ApiResponse<String>(
                200,
                "success",
                "order placed successfully",
                null
        );
    }

    public ApiResponse<String> cancelOrder(Integer orderDetailId){
        orderDetailRepository.deleteById(orderDetailId);
        orderItemRepository.deleteByOrderDetailId(orderDetailId);

        return new ApiResponse<String>(
                200,
                "success",
                "order cancelled successfully",
                null
        );
    }

}
