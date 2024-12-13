package com.telco.plan.api.dto;

import com.telco.plan.domain.entity.Plan;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import java.math.BigDecimal;

@Getter
@Schema(description = "요금제 정보 응답 DTO")
public class PlanDTO {
    @Schema(description = "요금제 ID")
    private final String planId;
    
    @Schema(description = "요금제 이름")
    private final String planName;
    
    @Schema(description = "월 요금")
    private final BigDecimal monthlyFee;
    
    @Schema(description = "설명")
    private final String description;
    
    public PlanDTO(Plan plan) {
        this.planId = plan.getPlanId();
        this.planName = plan.getPlanName();
        this.monthlyFee = plan.getMonthlyFee();
        this.description = plan.getDescription();
    }
}
