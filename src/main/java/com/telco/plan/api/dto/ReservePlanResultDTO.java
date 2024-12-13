package com.telco.plan.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Schema(description = "요금제 예약 결과 응답 DTO")
public class ReservePlanResultDTO {
    @Schema(description = "예약 ID")
    private final String reservationId;
    
    @Schema(description = "성공 여부")
    private final Boolean success;
    
    @Schema(description = "결과 메시지")
    private final String message;
}
