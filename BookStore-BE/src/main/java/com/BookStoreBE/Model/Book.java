package com.BookStoreBE.Model;

import com.BookStoreBE.utilityClasses.BINDING;
import com.BookStoreBE.utilityClasses.GENRE;
import com.BookStoreBE.utilityClasses.LANG;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.sql.Time;
import java.sql.Timestamp;


@Entity
public class Book {

    @Id
    @GeneratedValue
    private Integer bookId;
    private String title;
    private String author;
    private String publication;
    private GENRE genre;
    private String description;
    private LANG language;
    private BINDING binding;
    private Integer mrp;
    private Float discount;
    private Integer qtyAvail;
    private Integer qtySold;
    private Integer pages;

    private float rating;
    private Integer reviewCnt;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    private Timestamp createdAt;

    private String url;
    public Book() {
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublication() {
        return publication;
    }

    public void setPublication(String publication) {
        this.publication = publication;
    }

    public GENRE getGenre() {
        return genre;
    }

    public void setGenre(GENRE genre) {
        this.genre = genre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LANG getLanguage() {
        return language;
    }

    public void setLanguage(LANG language) {
        this.language = language;
    }

    public BINDING getBinding() {
        return binding;
    }

    public void setBinding(BINDING binding) {
        this.binding = binding;
    }

    public Integer getMrp() {
        return mrp;
    }

    public void setMrp(Integer mrp) {
        this.mrp = mrp;
    }

    public Float getDiscount() {
        return discount;
    }

    public void setDiscount(Float discount) {
        this.discount = discount;
    }

    public Integer getQtyAvail() {
        return qtyAvail;
    }

    public void setQtyAvail(Integer qtyAvail) {
        this.qtyAvail = qtyAvail;
    }

    public Integer getQtySold() {
        return qtySold;
    }

    public void setQtySold(Integer qtySold) {
        this.qtySold = qtySold;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public Integer getReviewCnt() {
        return reviewCnt;
    }

    public void setReviewCnt(Integer reviewCnt) {
        this.reviewCnt = reviewCnt;
    }

    public Timestamp getCreatedAt(){
        return this.createdAt;
    }
    public void setCreatedAt(Timestamp timestamp){
        this.createdAt=timestamp;
    }
}
