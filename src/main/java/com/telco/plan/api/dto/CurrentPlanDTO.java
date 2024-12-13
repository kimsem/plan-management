package com.telco.plan.api.dto;

import com.telco.plan.domain.entity.Plan;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Schema(description = "현재 요금제 정보 응답 DTO")
public class CurrentPlanDTO {
    @Schema(description = "요금제 ID")
    private final String planId;
    
    @Schema(description = "요금제 이름")
    private final String planName;
    
    @Schema(description = "월 요금")
    private final BigDecimal monthlyFee;
    
    @Schema(description = "시작일")
    private final LocalDate startDate;
    
    public CurrentPlanDTO(Plan plan) {
        this.planId = plan.getPlanId();
        this.planName = plan.getPlanName();
        this.monthlyFee = plan.getMonthlyFee();
        this.startDate = LocalDate.now(); // 실제로는 UserPlan에서 가져와야 함
    }
}
