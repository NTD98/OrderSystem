package src.main.com.example.orderstatus.controller;

import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import src.main.com.example.orderstatus.service.OrderStatusService;

@RestController
@RequestMapping("${app.api.version}/order")
@AllArgsConstructor
@Validated
public class OrderStatusController {
    private final OrderStatusService orderReservation;
    @PostMapping
    public String getOrderStatus(@RequestParam String requestId) {
        return orderReservation.getStatus(requestId);
    }
    @PostMapping
    public String createOrderStatus(@RequestParam String requestId,@RequestParam String status) {
        orderReservation.createStatus(requestId,status);
        return "SUCCESS";
    }
    @PutMapping
    public String updateOrderStatus(@RequestParam String requestId,@RequestParam String status) {
        orderReservation.updateStatus(requestId,status);
        return "SUCCESS";
    }
}
