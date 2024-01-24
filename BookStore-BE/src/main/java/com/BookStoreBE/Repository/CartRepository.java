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

}
