package com.yy.admin.exception;

import lombok.Data;


/**
 * 异常处理
 */
@Data
public class ResponseException extends RuntimeException {

    private static final int ERROR_CODE = 500;
    private static final String ERROR_MESSAGE = "请求接口异常";

    /**
     * 错误码
     */
    private Integer code = ERROR_CODE;
    /**
     * 错误消息提示
     */
    private String message = ERROR_MESSAGE;

    public ResponseException() {
        super(ERROR_MESSAGE);
    }

    public ResponseException(Integer code, String message) {
        super(message);
        this.message = message;
        this.code = code;
    }

    public ResponseException(String message) {
        super(message);
        this.message = message;
    }


}
