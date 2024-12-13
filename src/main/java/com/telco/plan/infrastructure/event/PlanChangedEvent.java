package com.telco.plan.infrastructure.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import java.time.LocalDateTime;

/**
 * 요금제 변경 이벤트 클래스입니다.
 */
@Getter
@RequiredArgsConstructor
public class PlanChangedEvent {
    private final String userId;
    private final String planId;
    private final LocalDateTime changedAt;
    private final String changeType = "IMMEDIATE";
}
