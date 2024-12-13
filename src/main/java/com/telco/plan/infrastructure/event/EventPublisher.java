package com.telco.plan.infrastructure.event;

/**
 * 이벤트 발행을 위한 인터페이스입니다.
 */
public interface EventPublisher {
    void publishPlanChanged(PlanChangedEvent event);
    void publishPlanReserved(PlanReservedEvent event);
}
