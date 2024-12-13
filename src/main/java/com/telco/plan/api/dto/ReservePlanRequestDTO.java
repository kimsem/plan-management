package com.telco.plan.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import java.time.LocalDate;

@Getter
@Schema(description = "요금제 예약 요청 DTO")
public class ReservePlanRequestDTO {
    @NotNull
    @Schema(description = "예약할 요금제 ID", required = true)
    private String planId;
    
    @NotNull
    @Schema(description = "시작일", required = true)
    private LocalDate startDate;
}
