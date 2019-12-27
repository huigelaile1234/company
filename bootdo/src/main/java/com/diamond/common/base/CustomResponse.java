package com.diamond.common.base;

import java.util.HashMap;
import java.util.Map;

public class CustomResponse extends HashMap<String, Object> {
    private static final long serialVersionUID = 1L;

    public CustomResponse() {
        put("code", 0);
        put("msg", "操作成功");
    }

    public static CustomResponse error() {
        return error(1, "操作失败");
    }

    public static CustomResponse error(String msg) {
        return error(500, msg);
    }

    public static CustomResponse error(int code, String msg) {
        CustomResponse customResponse = new CustomResponse();
        customResponse.put("code", code);
        customResponse.put("msg", msg);
        return customResponse;
    }

    public static CustomResponse ok(String msg) {
        CustomResponse customResponse = new CustomResponse();
        customResponse.put("msg", msg);
        return customResponse;
    }

    public static CustomResponse ok(Map<String, Object> map) {
        CustomResponse customResponse = new CustomResponse();
        customResponse.putAll(map);
        return customResponse;
    }

    public static CustomResponse ok() {
        return new CustomResponse();
    }

    @Override
    public CustomResponse put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
