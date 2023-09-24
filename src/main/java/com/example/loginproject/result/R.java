package com.example.loginproject.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 统一返回结果
 * @auther fmh
 * @date 2023/9/10
 * @param <T>
 */
@Data
@Accessors(chain = true)
public class R<T>  implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 响应码
     */
    private int code;

    /**
     * 消息
     */
    private String message;

    /**
     * 操作是否成功
     */
    private boolean success;

    /**
     * 响应数据
     * https://bedebug.com/archives/openfeign
     */
    @JsonProperty("data")
    private T data;

    public R() {
        this(ResultCodeEnum.SUCCESS);
    }

    public R(ResultCode resultCode) {
        this(resultCode, true);
    }

    public R(ResultCode resultCode, Boolean success) {
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
        this.success = success;
        this.data = null;
    }

    public static <T> R<T> stub() { return new R<>(ResultCodeEnum.API_IS_STUB); }

    public static R<Empty> ok() {
        return new R<>();
    }

    public static <T> R<T> ok(Class<T> clazz) {
        return new R<>();
    }

    public static <T> R<T> ok(T data) {
        return new R<T>().setData(data);
    }

    public static <T> R<T> ok(String message, T data) {
        return new R<T>().setMessage(message).setData(data);
    }

    public static R<Empty> ok(ResultCode resultCode) {
        return new R<>(resultCode);
    }

    public static <T> R<T> ok(ResultCode resultCode, T data) {
        return new R<T>(resultCode).setData(data);
    }

    public static <T> R<T> error() {
        return new R<>(ResultCodeEnum.UNKNOWN_EXCEPTION, false);
    }

    public static <T> R<T> error(String message) {
        return new R<T>(ResultCodeEnum.UNKNOWN_EXCEPTION, false)
                .setMessage(message);
    }

    public static <T> R<T> error(int code, String message) {
        return new R<T>().setCode(code).setMessage(message).setSuccess(false);
    }

    public static <T> R<T> error(ResultCode resultCode) {
        return new R<>(resultCode, false);
    }

    public static <T> R<T> error(String message, T data) {
        return new R<T>(ResultCodeEnum.UNKNOWN_EXCEPTION, false)
                .setMessage(message)
                .setData(data);
    }

    public static <T> R<T> error(int code, String message, T data) {
        return new R<T>().setCode(code).setMessage(message).setSuccess(false).setData(data);
    }

    public static <T> R<T> error(ResultCode resultCode, T data) {
        return new R<T>(resultCode, false).setData(data);
    }



}
