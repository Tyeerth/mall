package com.xdu.common.exception;

import com.xdu.common.result.ResultCodeEnum;

public class GmallException extends RuntimeException{
    //异常状态码
    private Integer code;

    /**
     * 通过状态码和错误消息创建异常对象
     * @param message
     * @param code
     */
    public GmallException(String message, Integer code) {
        super(message);
        this.code = code;
    }


    /**
     * 接收枚举类型对象
     * @param resultCodeEnum
     */
    public GmallException(ResultCodeEnum resultCodeEnum) {
        super(resultCodeEnum.getMessage());
        this.code = resultCodeEnum.getCode();
    }
}
