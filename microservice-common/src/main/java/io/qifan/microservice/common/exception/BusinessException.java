package io.qifan.microservice.common.exception;

import io.qifan.microservice.common.constants.ResultCode;
import lombok.Data;

/**
 * 业务异常，需要返回给用户看的
 */
@Data
public class BusinessException extends RuntimeException {
    ResultCode resultCode;

    public BusinessException(ResultCode resultCode) {
        super(resultCode.getName());
        this.resultCode = resultCode;
    }

    public BusinessException(ResultCode resultCode, String msg) {
        super(msg);
        this.resultCode = resultCode;
    }
}
