package com.telco.plan.domain.repository;

import com.telco.plan.domain.entity.PlanReservation;
import com.telco.plan.domain.entity.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

/**
 * 요금제 예약 정보에 접근하기 위한 Repository 인터페이스입니다.
 */
public interface PlanReservationRepository extends JpaRepository<PlanReservation, String> {
    
    /**
     * 특정 사용자의 예약 상태별 예약 정보를 조회합니다.
     */
    List<PlanReservation> findByUserIdAndStatus(String userId, ReservationStatus status);
}
