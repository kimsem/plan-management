package com.telco.plan.service.impl;

import com.telco.common.exception.BaseException;
import com.telco.plan.api.dto.*;
import com.telco.plan.domain.entity.Plan;
import com.telco.plan.domain.entity.PlanReservation;
import com.telco.plan.domain.repository.PlanRepository;
import com.telco.plan.domain.repository.PlanReservationRepository;
import com.telco.plan.infrastructure.cache.PlanCacheManager;
import com.telco.plan.infrastructure.event.EventPublisher;
import com.telco.plan.infrastructure.event.PlanChangedEvent;
import com.telco.plan.infrastructure.event.PlanReservedEvent;
import com.telco.plan.service.PlanService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * 요금제 관리 서비스 구현체입니다.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PlanServiceImpl implements PlanService {
    
    private final PlanRepository planRepository;
    private final PlanReservationRepository reservationRepository;
    private final PlanCacheManager cacheManager;
    private final EventPublisher eventPublisher;
	
    @Override
    @Transactional(readOnly = true)
    public CurrentPlanDTO getCurrentPlan(String userId) {
        return cacheManager.getCurrentPlan(userId)
                .orElseGet(() -> {
                    Plan plan = planRepository.findCurrentPlan(userId)
                            .orElseThrow(() -> new BaseException("현재 요금제를 찾을 수 없습니다.", 404));
                    
                    CurrentPlanDTO currentPlan = new CurrentPlanDTO(plan);
                    cacheManager.cacheCurrentPlan(userId, currentPlan);
                    return currentPlan;
                });
    }

    @Override
    @Transactional(readOnly = true)
    public List<PlanDTO> getAvailablePlans(String searchKeyword) {
        return cacheManager.getAvailablePlans()
                .orElseGet(() -> {
                    List<PlanDTO> plans = planRepository.findAvailablePlans().stream()
                            .map(PlanDTO::new)
                            .filter(plan -> searchKeyword == null || 
                                    plan.getPlanName().contains(searchKeyword) ||
                                    plan.getDescription().contains(searchKeyword))
                            .collect(Collectors.toList());
                    
                    cacheManager.cacheAvailablePlans(plans);
                    return plans;
                });
    }

    @Override
    @Transactional
    public ChangePlanResultDTO changePlan(String userId, String planId) {
        Plan plan = planRepository.findById(planId)
                .orElseThrow(() -> new BaseException("요금제를 찾을 수 없습니다.", 404));
                
        if (!plan.validateForChange()) {
            return new ChangePlanResultDTO(false, "변경할 수 없는 요금제입니다.", planId);
        }
        
        boolean updated = planRepository.updateUserPlan(userId, planId);
        if (!updated) {
            return new ChangePlanResultDTO(false, "요금제 변경에 실패했습니다.", planId);
        }
        
        cacheManager.invalidateCurrentPlan(userId);
        
        eventPublisher.publishPlanChanged(new PlanChangedEvent(
                userId, planId, LocalDateTime.now()));
                
        return new ChangePlanResultDTO(true, "요금제가 변경되었습니다.", planId);
    }

    @Override
    @Transactional
    public ReservePlanResultDTO reservePlan(String userId, String planId, LocalDate startDate) {
        Plan plan = planRepository.findById(planId)
                .orElseThrow(() -> new BaseException("요금제를 찾을 수 없습니다.", 404));
                
        if (!plan.validateForReservation()) {
            return new ReservePlanResultDTO(null, false, "예약할 수 없는 요금제입니다.");
        }
        
        if (startDate.isBefore(LocalDate.now())) {
            return new ReservePlanResultDTO(null, false, "시작일은 오늘 이후여야 합니다.");
        }
        
        String reservationId = UUID.randomUUID().toString();
        PlanReservation reservation = new PlanReservation();
        // reservation 객체 설정
        
        reservationRepository.save(reservation);
        
        eventPublisher.publishPlanReserved(new PlanReservedEvent(
                reservationId, userId, planId, startDate, LocalDateTime.now()));
                
        return new ReservePlanResultDTO(reservationId, true, "요금제가 예약되었습니다.");
    }
}
