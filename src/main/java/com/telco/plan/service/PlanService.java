package com.telco.plan.service;

import com.telco.plan.api.dto.ChangePlanResultDTO;
import com.telco.plan.api.dto.CurrentPlanDTO;
import com.telco.plan.api.dto.PlanDTO;
import com.telco.plan.api.dto.ReservePlanResultDTO;
import java.time.LocalDate;
import java.util.List;

/**
 * 요금제 관리를 위한 비즈니스 로직 인터페이스입니다.
 */
public interface PlanService {
    CurrentPlanDTO getCurrentPlan(String userId);
    List<PlanDTO> getAvailablePlans(String searchKeyword);
    ChangePlanResultDTO changePlan(String userId, String planId);
    ReservePlanResultDTO reservePlan(String userId, String planId, LocalDate startDate);
}
