package com.BookStoreBE.utilityClasses;

import com.BookStoreBE.Model.Book;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {
    private Integer orderDetailId;
    private Float amount;
    private Timestamp createdAt;

    List<Pair<Book,Integer>> orderItems;
}
