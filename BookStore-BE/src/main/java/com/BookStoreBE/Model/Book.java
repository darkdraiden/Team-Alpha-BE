package com.BookStoreBE.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

enum GENRE{
    FICTION,
    ROMANCE,
    NARRATIVE,
    THRILLER,
    SCI_FI,
    MYSTERY,
    FANATASY,
    BIOGRAPHY,
    HORROR,
    HISTORY,
    ADVENTURE
}

enum LANG{
    ENGLISH,
    HINDI,
    SPANISH,
    LATIN,
    FRENCH
}

enum BINDING{
    HARD,
    SOFT
}

@Entity
public class Book {
    @Id
    private Integer bookId;
    private String title;
    private String author;
    private String publication;
    private  GENRE genre;
    private String description;
    private LANG language;
    private BINDING binding;
    private Integer mrp;
    private Float discount;
    private Integer qtyAvail;
    private Integer qtySold;
    private Integer pages;
}
