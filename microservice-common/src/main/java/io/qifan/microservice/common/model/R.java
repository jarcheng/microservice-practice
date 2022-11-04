package io.qifan.microservice.common.model;

import io.qifan.microservice.common.constants.ResultCode;
import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

/**
 * 通一返回结果
 *
 */
@Data
public class R<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer code;

    private String msg;

    private T result;

    public R() {
        
    }


    private R(T result, Integer code, String msg) {
        this.result = result;
        this.code = code;
        this.msg = msg;
    }

    private R(T result, ResultCode resultCode) {
        this.result = result;
        this.msg = resultCode.getName();
        this.code = resultCode.getCode();
    }

    public static R<String> ok() {
        return new R<>("", ResultCode.Success);
    }

    public static <T> R<T> ok(T data) {
        return new R<>(data, ResultCode.Success);
    }

    public static R<String> fail(ResultCode resultCode) {
        return new R<>("", resultCode);
    }

    public static R<String> fail(ResultCode resultCode, String msg) {
        return new R<>("", resultCode.getCode(), msg);
    }

    public boolean isSuccess() {
        return Objects.equals(ResultCode.Success.getCode(), this.getCode());
    }

}
