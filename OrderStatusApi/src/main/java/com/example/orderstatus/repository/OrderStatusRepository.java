package src.main.java.com.example.orderstatus.repository;

import src.main.java.com.example.orderstatus.entity.OrderStatusEntity;
import org.springframework.data.repository.CrudRepository;

public interface OrderStatusRepository extends CrudRepository<OrderStatusEntity,String> {
}
