package com.example.orderprocessingconsumer.enums;



public enum OrderStatus {
    ORDER_CREATED,
    ORDER_PROCESSING,
    ORDER_FAILED,
    ORDER_COMPLETED;
    public enum CreditStatus{
        CREDIT_LOCK_ACQUIRED,
        CREDIT_LOCK_CONFIRMED,
        CREDIT_LOCK_CANCELLED
    }
    public enum ItemStatus{
        ITEM_LOCK_ACQUIRED,
        ITEM_LOCK_CONFIRMED,
        ITEM_LOCK_CANCELLED
    }
}
