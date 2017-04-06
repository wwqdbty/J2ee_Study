package com.kulang.dto;

import lombok.Data;

/**
 * Created by wenqiang.wang on 2017/3/12.
 */
@Data
public class ThirdPlatformDictionaryExcelData {
    private int sheetNum;

    private int rowNum;

    /** 编码_ID(唯一) */
    private String id;

    /** 英文 */
    private String ename;

    /** 英文 */
    private String cname;

    /** 顺序号 */
    private String orderNum;

    /** 是否显示 */
    private String isShow;

    /** 父编码_ID */
    private String parentId;

    private static final long serialVersionUID = 1L;

    public ThirdPlatformDictionaryExcelData(int sheetNum, int rowNum) {
        this.sheetNum = sheetNum;
        this.rowNum = rowNum;
    }
}
