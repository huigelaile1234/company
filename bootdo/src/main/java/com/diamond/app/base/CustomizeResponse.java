package com.diamond.app.base;

import lombok.Data;

/**
 * @ClassName CustomizeResponse
 * Description TODO
 * @Author chinadep
 * @Date 2019/12/30 11:14
 **/
@Data
public class CustomizeResponse {
    private int code;           //状态码
    private String message;     //相应信息
    private Object data;        //相应数据
    private boolean isMore;     //是否更多

    public CustomizeResponse(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static CustomizeResponse ofMessage(int code, String message) {
        return new CustomizeResponse(code, message, null);
    }

    public static CustomizeResponse ofSuccess(Object data) {
        return new CustomizeResponse(Status.SUCCESS.getCode(), Status.SUCCESS.getMessage(), data);
    }

    public static CustomizeResponse ofStatus(Status status) {
        return new CustomizeResponse(status.getCode(), status.getMessage(), null);
    }

    public static CustomizeResponse ofData(Status status, Object data) {
        return new CustomizeResponse(status.getCode(), status.getMessage(), data);
    }

    public enum Status {
        SUCCESS(200, "SUCCESS"),
        BAD_REQUEST(400, "Bad Request"),
        UNAUTHORIZED(401, "Unauthorized"),
        FORBIDDEN(403, "forbidden"),
        NOT_FOUND(404, "Not Found"),
        INTERNAL_SERVER_ERROR(500, "Unknown Internal Error"),
        EXIST_PHONE(40101, "手机号码已经存在，请更换手机号码后注册"),
        PASSWORD_WRONG(40102, "用户名密码错误");

        private int code;
        private String message;

        Status(int code, String message) {
            this.code = code;
            this.message = message;
        }

        public int getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }
    }
}
