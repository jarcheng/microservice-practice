package io.qifan.microservice.order.infrastructure.config;

import io.qifan.microservice.common.constants.ResultCode;
import io.qifan.microservice.common.exception.BusinessException;
import io.qifan.microservice.common.exception.SystemException;
import io.qifan.microservice.common.model.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionAdvice {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<R<String>> handleBusinessException(BusinessException e) {
        log.error("业务异常", e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(R.fail(e.getResultCode()));
    }

    @ExceptionHandler(SystemException.class)
    public ResponseEntity<R<String>> handleSystemException(SystemException e) {
        log.error("系统异常", e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(R.fail(ResultCode.SystemError));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<R<String>> handleException(Exception e) {
        log.error("系统异常", e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(R.fail(ResultCode.SystemError));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<R<String>> handleValidateException(ConstraintViolationException e) {
        log.warn("校验异常", e);
        // 不合格的字段，可能有多个，只需要返回其中一个提示用户就行
        // 比如密码为空
        ArrayList<ConstraintViolation<?>> constraintViolations = new ArrayList<>(e.getConstraintViolations());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(R.fail(ResultCode.ValidateError, constraintViolations.get(0).getMessage()));

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<R<String>> handleValidateException(MethodArgumentNotValidException e) {
        log.warn("校验异常", e);
        // 不合格的字段，可能有多个，只需要返回其中一个提示用户就行
        // 比如密码为空
        List<FieldError> fieldErrors = e.getFieldErrors();
        FieldError fieldError = fieldErrors.get(0);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(R.fail(ResultCode.ValidateError, fieldError.getDefaultMessage()));

    }
}
