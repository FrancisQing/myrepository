package cn.kryex.phonerepair.entity.common;

import cn.kryex.phonerepair.common.enums.ResultCode;

import java.io.Serial;
import java.io.Serializable;
import java.util.Map;
import java.util.HashMap;

import lombok.Data;

@Data
public class R<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Integer code;           // 状态码
    private String msg;             // 消息提示
    private T data;                 // 返回数据
    private long timestamp;         // 时间戳（ms）

    /** 构造器 */
    private R(ResultCode resultCode, T data) {
        this.code = resultCode.getCode();
        this.msg = resultCode.getMessage();
        this.data = data;
        this.timestamp = System.currentTimeMillis();
    }
    private R(ResultCode resultCode) {
        this(resultCode, null);
    }

    private R(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.timestamp = System.currentTimeMillis();
    }
    private R(Integer code, String msg) {
        this(code, msg, null);
    }

    /** 默认成功响应 */
    public static <T> R<T> ok() {
        return new R<>(ResultCode.SUCCESS);
    }
    public static <T> R<T> ok(T data) {
        return new R<>(ResultCode.SUCCESS, data);
    }

    /** 默认失败响应 */
    public static <T> R<T> fail() {
        return new R<>(ResultCode.FAIL);
    }
    public static <T> R<T> fail(T data) {
        return new R<>(ResultCode.FAIL, data);
    }

    /** 预设枚举响应 */
    public static <T> R<T> of(ResultCode resultCode) {
        return new R<>(resultCode);
    }
    public static <T> R<T> of(ResultCode resultCode, T data) {
        return new R<>(resultCode, data);
    }

    /** 自定义响应   */
    public static <T> R<T> ok(String msg) {
        return new R<>(ResultCode.SUCCESS.getCode(), msg);
    }
    public static <T> R<T> ok(String msg, T data) {
        return new R<>(ResultCode.SUCCESS.getCode(), msg, data);
    }
    public static <T> R<T> fail(String msg) {
        return new R<>(ResultCode.FAIL.getCode(), msg);
    }
    public static <T> R<T> fail(String msg, T data) {
        return new R<>(ResultCode.FAIL.getCode(), msg, data);
    }

    /** 链式添加数据到 data 字段（data 需为 Map） */
    @SuppressWarnings("unchecked")
    public R<T> put(String key, Object value) {
        if (this.data == null || !(this.data instanceof Map)) {
            this.data = (T) new HashMap<String, Object>();
        }
        ((Map<String, Object>) this.data).put(key, value);
        return this;
    }

}

