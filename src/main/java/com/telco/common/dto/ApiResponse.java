package com.telco.common.dto;

import lombok.Getter;
import java.time.LocalDateTime;

/**
 * API 응답을 위한 공통 DTO 클래스입니다.
 * 
 * @param <T> 응답 데이터의 타입
 */
@Getter
public class ApiResponse<T> {
    private final Integer status;
    private final String message;
    private final T data;
    private final LocalDateTime timestamp;

    public ApiResponse(Integer status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.timestamp = LocalDateTime.now();
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(200, "Success", data);
    }

    public static <T> ApiResponse<T> error(Integer status, String message) {
        return new ApiResponse<>(status, message, null);
    }
}
