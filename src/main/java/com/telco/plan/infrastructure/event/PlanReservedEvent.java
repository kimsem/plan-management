package com.telco.plan.infrastructure.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 요금제 예약 이벤트 클래스입니다.
 */
@Getter
@RequiredArgsConstructor
public class PlanReservedEvent {
    private final String reservationId;
    private final String userId;
    private final String planId;
    private final LocalDate startDate;
    private final LocalDateTime reservedAt;
}
