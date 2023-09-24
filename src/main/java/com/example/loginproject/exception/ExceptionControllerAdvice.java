package com.example.loginproject.exception;

import com.example.loginproject.result.Empty;
import com.example.loginproject.result.R;
import com.example.loginproject.result.ResultCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * 全局异常捕获
 * @author fmh
 * @date 2023/8/3
 */
@Slf4j
@RestControllerAdvice
public class ExceptionControllerAdvice {

    /** TODO:
     * @see org.springframework.web.bind.MissingPathVariableException
     */

    @ExceptionHandler(FCException.class)
    public R<Empty> handleFCException(FCException e) {
        return R.error(e.getCode(), e.getMsg());
    }

    /**
     * 400 请求参数错误
     * @return 返回参数检验错误信息列表
     */
    //@ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R<Map<String, String>> handleValidationException(MethodArgumentNotValidException e){
        BindingResult bindingResult = e.getBindingResult();
        Map<String, String> errorMap = new HashMap<>();

        StringBuilder errorMessage = new StringBuilder();
        errorMessage.append(ResultCodeEnum.VALID_EXCEPTION.message + ": ");
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
            errorMessage.append(fieldError.getDefaultMessage());
            errorMessage.append("; ");
        }
        return R.error(errorMessage.toString(), errorMap);
        //return R.error(ResultCodeEnum.VALID_EXCEPTION, errorMap);
    }

    /**
     * 缺少请求参数
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public R<Empty> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.error(e.toString());
        return R.error(ResultCodeEnum.REQUEST_BODY_IS_MISSING);
    }


    /**
     * 其他未指定错误
     * @return 未知错误
     */
    @ExceptionHandler(Exception.class)
    public R<Empty> handleFCException(Exception e) {
        log.error(e.toString());
        e.printStackTrace();
        return R.error(ResultCodeEnum.UNKNOWN_EXCEPTION);
    }
}

