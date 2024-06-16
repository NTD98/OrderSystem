package src.main.com.example.orderstatus.repository;

import src.main.com.example.orderstatus.entity.OrderStatusEntity;
import org.springframework.data.repository.CrudRepository;

public interface OrderStatusRepository extends CrudRepository<OrderStatusEntity,String> {
}
