package com.BookStoreBE.utilityClasses;

public class ApiResponse<T> {
    private Integer statusCode;
    private String status;

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    private String message;
    private T data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
// getters, setters, constructors, etc.

    public ApiResponse(Integer statusCode,String status, String message, T data) {
        this.statusCode=statusCode;
        this.status = status;
        this.message = message;
        this.data = data;
    }
}
