package com.BookStoreBE.Repository;

import com.BookStoreBE.Model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem,Integer> {

    @Transactional
    @Modifying
    @Query("DELETE FROM OrderItem WHERE orderDetailId=:id")
    public void deleteByOrderDetailId(@Param("id") Integer orderDetailId);

    @Query("SELECT orderItemId FROM OrderItem WHERE orderDetailId=:id")
    public List<Integer> findIdByOrderDetailId(@Param("id") Integer orderDetailId);



}
