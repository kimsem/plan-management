package com.telco.common.exception;

import lombok.Getter;

/**
 * 시스템 전반에서 사용되는 기본 예외 클래스입니다.
 */
@Getter
public class BaseException extends RuntimeException {
    private final Integer status;

    public BaseException(String message, Integer status) {
        super(message);
        this.status = status;
    }
}
