package com.kulang.study.domain.model.enums;

/**
 * Created by jian.zhang on 2016/11/28.
 */
public enum ErrorCodeEnum {

    SUCCEED(0, "成功"),
    FAILED(-1, "失败"),
    FILE_FORMAT_ERROR(-2,"文件格式错误"),
    EMPTY_FILE(-3,"上传文件为空"),
    FILE_INFO_ERROR(-4,"文件内容错误"),
    FILE_INFO_UNIQ_ERROR(-5,"产品名称重复");

    private int code;

    private String comment;

    ErrorCodeEnum(int code, String comment) {
        this.code = code;
        this.comment = comment;
    }

    public int getCode() {
        return code;
    }

    public String getComment() {
        return comment;
    }
}
