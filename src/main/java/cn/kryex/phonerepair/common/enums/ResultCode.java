package cn.kryex.phonerepair.common.enums;

import lombok.Getter;

@Getter
public enum ResultCode {
    SUCCESS(0, "操作成功"),
    FAIL(1, "操作失败"),
    UNAUTHORIZED(401, "未认证"),
    FORBIDDEN(403, "无权限"),
    NOT_FOUND(404, "资源不存在"),
    SERVER_ERROR(500, "服务器内部错误");

    private final int code;
    private final String message;

    // Getter + 构造器...
    ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

}