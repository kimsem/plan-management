package com.telco.plan.api.controller;

import com.telco.common.dto.ApiResponse;
import com.telco.plan.api.dto.*;
import com.telco.plan.service.PlanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 요금제 관리 API 컨트롤러입니다.
 */
@Slf4j
@RestController
@RequestMapping("/api/plans")
@RequiredArgsConstructor
@Tag(name = "요금제 관리", description = "요금제 조회, 변경, 예약 API")
public class PlanController {
    
    private final PlanService planService;

    @GetMapping("/current")
    @Operation(summary = "현재 요금제 조회", description = "사용자의 현재 요금제 정보를 조회합니다.")
    public ResponseEntity<ApiResponse<CurrentPlanDTO>> getCurrentPlan(
            @Parameter(hidden = true) @AuthenticationPrincipal String userId) {
        log.info("현재 요금제 조회 요청. userId: {}", userId);
        CurrentPlanDTO currentPlan = planService.getCurrentPlan(userId);
        return ResponseEntity.ok(ApiResponse.success(currentPlan));
    }

    @GetMapping("/available")
    @Operation(summary = "이용 가능한 요금제 목록 조회", description = "변경 가능한 전체 요금제 목록을 조회합니다.")
    public ResponseEntity<ApiResponse<List<PlanDTO>>> getAvailablePlans(
            @Parameter(description = "검색 키워드") @RequestParam(required = false) String searchKeyword) {
        log.info("이용 가능한 요금제 목록 조회 요청. searchKeyword: {}", searchKeyword);
        List<PlanDTO> plans = planService.getAvailablePlans(searchKeyword);
        return ResponseEntity.ok(ApiResponse.success(plans));
    }

    @PutMapping("/change")
    @Operation(summary = "즉시 요금제 변경", description = "현재 요금제를 즉시 변경합니다.")
    public ResponseEntity<ApiResponse<ChangePlanResultDTO>> changePlan(
            @Parameter(hidden = true) @AuthenticationPrincipal String userId,
            @Parameter(description = "변경할 요금제 ID") @RequestParam String planId) {
        log.info("요금제 변경 요청. userId: {}, planId: {}", userId, planId);
        ChangePlanResultDTO result = planService.changePlan(userId, planId);
        return ResponseEntity.ok(ApiResponse.success(result));
    }

    @PostMapping("/reserve")
    @Operation(summary = "예약 요금제 변경", description = "다음달에 적용될 요금제를 예약합니다.")
    public ResponseEntity<ApiResponse<ReservePlanResultDTO>> reservePlan(
            @Parameter(hidden = true) @AuthenticationPrincipal String userId,
            @RequestBody ReservePlanRequestDTO request) {
        log.info("요금제 예약 요청. userId: {}, request: {}", userId, request);
        ReservePlanResultDTO result = planService.reservePlan(
                userId, request.getPlanId(), request.getStartDate());
        return ResponseEntity.ok(ApiResponse.success(result));
    }
}
