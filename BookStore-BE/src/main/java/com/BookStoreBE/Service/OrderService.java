package com.BookStoreBE.Service;

import com.BookStoreBE.Model.Book;
import com.BookStoreBE.Model.CartItems;
import com.BookStoreBE.Model.OrderDetail;
import com.BookStoreBE.Model.OrderItem;
import com.BookStoreBE.Repository.BookRepository;
import com.BookStoreBE.Repository.CartRepository;
import com.BookStoreBE.Repository.OrderDetailRepository;
import com.BookStoreBE.Repository.OrderItemRepository;
import com.BookStoreBE.utilityClasses.ApiResponse;
import com.BookStoreBE.utilityClasses.OrderRequest;
import com.BookStoreBE.utilityClasses.OrderResponse;
import com.BookStoreBE.utilityClasses.Pair;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private CartRepository cartItemRepository;

    @Autowired
    private BookRepository bookRepository;

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

    public ApiResponse<List<OrderResponse>> getOrderHistory(Integer userId){
        List<Integer> orderDetailIds=orderDetailRepository.findIdByUserId(userId);

        List<OrderResponse> result=new ArrayList<OrderResponse>();

        for(Integer orderDetailId:orderDetailIds){
            OrderResponse orderResponse = new OrderResponse();
            Optional<OrderDetail> opOrderDetail = orderDetailRepository.findById(orderDetailId);

            if(opOrderDetail.isEmpty()){
                return new ApiResponse<List<OrderResponse>>(
                        404,
                        "fail",
                        "order not found!",
                        null
                );
            }

            Timestamp createdAt=opOrderDetail.get().getCreatedAt();
            Float amount = opOrderDetail.get().getAmount();

            List<Pair<Book,Integer>> orderItems = new ArrayList<Pair<Book,Integer>>();

            List<Integer> orderItemIds=orderItemRepository.findIdByOrderDetailId(orderDetailId);

            for(Integer orderItemId:orderItemIds){
                Optional<OrderItem> opOrderItem=orderItemRepository.findById(orderItemId);
                if(opOrderItem.isEmpty()){
                    return new ApiResponse<List<OrderResponse>>(
                            404,
                            "fail",
                            "order item not found!",
                            null
                    );
                }

                Optional<Book> opBook= bookRepository.findById(opOrderItem.get().getBookId());

                orderItems.add(new Pair<Book,Integer>(opBook.get(),opOrderItem.get().getQuantity()));

            }

            orderResponse.setOrderItems(orderItems);
            orderResponse.setOrderDetailId(orderDetailId);
            orderResponse.setCreatedAt(createdAt);
            orderResponse.setAmount(amount);

            result.add(orderResponse);
        }

        return new ApiResponse<>(
                200,
                "success",
                "orders fetched successfully!",
                result
        );
    }

}
