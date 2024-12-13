package com.telco.plan.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 요금제 예약 정보를 저장하는 엔티티 클래스입니다.
 */
@Entity
@Table(name = "plan_reservations")
@Getter
@NoArgsConstructor
public class PlanReservation {
    @Id
    private String reservationId;
    
    private String planId;
    
    private String userId;
    
    private LocalDate startDate;
    
    @Enumerated(EnumType.STRING)
    private ReservationStatus status;
    
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;
    
    @Version
    private Long version;
    
    /**
     * 예약 유효성을 검증합니다.
     */
    public boolean validate() {
        return status == ReservationStatus.PENDING &&
               startDate.isAfter(LocalDate.now());
    }
    
    /**
     * 예약을 취소합니다.
     */
    public boolean cancel() {
        if (status != ReservationStatus.PENDING) {
            return false;
        }
        status = ReservationStatus.CANCELLED;
        return true;
    }
}
