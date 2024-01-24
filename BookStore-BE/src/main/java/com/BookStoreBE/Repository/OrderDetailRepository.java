package com.BookStoreBE.Repository;

import com.BookStoreBE.Model.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail,Integer> {

    @Query("SELECT orderDetailId FROM OrderDetail WHERE userId=:id")
    public List<Integer> findIdByUserId(@Param("id") Integer userId);
}
