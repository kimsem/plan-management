package com.telco.plan.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Schema(description = "요금제 변경 결과 응답 DTO")
public class ChangePlanResultDTO {
    @Schema(description = "성공 여부")
    private final Boolean success;
    
    @Schema(description = "결과 메시지")
    private final String message;
    
    @Schema(description = "변경된 요금제 ID")
    private final String planId;
}
