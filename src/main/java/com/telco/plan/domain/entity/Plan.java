package com.telco.plan.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 요금제 정보를 저장하는 엔티티 클래스입니다.
 */
@Entity
@Table(name = "plans")
@Getter
@NoArgsConstructor
public class Plan {
    @Id
    private String planId;
    
    private String planName;
    
    private BigDecimal monthlyFee;
    
    private String description;
    
    @Enumerated(EnumType.STRING)
    private PlanStatus status;
    
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;
    
    @Version
    private Long version;
    
    /**
     * 요금제 변경 가능 여부를 검증합니다.
     */
    public boolean validateForChange() {
        return status == PlanStatus.ACTIVE;
    }
    
    /**
     * 요금제 예약 가능 여부를 검증합니다.
     */
    public boolean validateForReservation() {
        return status == PlanStatus.ACTIVE;
    }
}
