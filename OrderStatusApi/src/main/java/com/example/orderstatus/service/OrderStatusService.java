package src.main.java.com.example.orderstatus.service;

public interface OrderStatusService {
    void createStatus(String requestTraceId,String status);
    void updateStatus(String requestTraceId,String status);
    String getStatus(String requestTraceId);
}
