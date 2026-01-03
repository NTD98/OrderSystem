package src.main.java.com.example.orderstatus.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class OrderStatusEntity {
    @Id
    private String requestTraceId;
    private String status;
    private LocalDateTime createdDateTime;
    private LocalDateTime lastUpdatedDateTime;

    @PrePersist
    void initTime(){
        if(this.createdDateTime==null) {
            createdDateTime = LocalDateTime.now();
        }else {
            lastUpdatedDateTime = LocalDateTime.now();
        }
    }
}
