package io.qifan.microservice.common.exception;

/**
 * 系统异常，不需要给用户看
 */
public class SystemException extends RuntimeException {
    public SystemException(String msg) {
        super(msg);
    }
}
