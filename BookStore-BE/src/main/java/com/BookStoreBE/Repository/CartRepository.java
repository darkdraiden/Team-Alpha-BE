package com.BookStoreBE.Repository;

import com.BookStoreBE.Model.CartItems;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<CartItems,Integer> {

    @Query(value = "select c from CartItems c where c.userId=:id ")
    List<CartItems> findCartItemsByIdCustom(@Param("id") Integer id);

    @Transactional
    @Modifying
    @Query("update CartItems set quantity=:q where cartItemId=:id")
    Integer decreaseCount(@Param("id") Integer cartId,@Param("q") Integer quant);

    @Transactional
    @Modifying
    @Query("update CartItems set quantity=:q where cartItemId=:id")
    Integer increaseCount(@Param("id") Integer cartId,@Param("q") Integer quant);


}
