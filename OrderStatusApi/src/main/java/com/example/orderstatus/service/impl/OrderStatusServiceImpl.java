package src.main.java.com.example.orderstatus.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import src.main.java.com.example.orderstatus.entity.OrderStatusEntity;
import src.main.java.com.example.orderstatus.exception.InvalidRequestException;
import src.main.java.com.example.orderstatus.repository.OrderStatusRepository;
import src.main.java.com.example.orderstatus.service.OrderStatusService;

@Service
@AllArgsConstructor
public class OrderStatusServiceImpl implements OrderStatusService {
    private final OrderStatusRepository orderStatusRepository;

    @Override
    public void createStatus(String requestTraceId, String status) {
        var orderStatus = orderStatusRepository.findById(requestTraceId);
        orderStatus.ifPresentOrElse(
                (item) -> {
                    throw new InvalidRequestException();
                },
                () -> orderStatusRepository.save(
                        OrderStatusEntity.builder()
                                .requestTraceId(requestTraceId)
                                .status(status)
                                .build()));
    }

    @Override
    public void updateStatus(String requestTraceId, String status) {
        var orderStatus = orderStatusRepository.findById(requestTraceId);
        orderStatus.ifPresentOrElse(
                (item) -> {
                    item.setStatus(status);
                    orderStatusRepository.save(item);
                },
                () -> {
                    throw new InvalidRequestException();
                });
    }

    @Override
    public String getStatus(String requestTraceId) {
        var orderStatus = orderStatusRepository.findById(requestTraceId);
        if (orderStatus.isEmpty())
        {
            throw new InvalidRequestException();
        }
        return orderStatus.get().getStatus();
    }
}
