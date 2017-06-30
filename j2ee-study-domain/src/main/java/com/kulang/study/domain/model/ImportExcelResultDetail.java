package com.kulang.study.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Excel导入结果详细信息
 *
 * 导入操作后, 需要记录导入成功/失败的记录的详细信息时适用
 *
 * Created by wenqiang.wang on 2017/3/11.
 */

@Data
@AllArgsConstructor
public class ImportExcelResultDetail<T> {
    /** Sheet号 */
    private int iSheetNum;

    /** 行号 */
    private int iRowNum;

    /** 导入结果说明 */
    private String strMessage;

    /** 行数据, 根据需求可自行决定设置什么值 */
    private T rowData;

    public ImportExcelResultDetail(int iSheetNum, int iRowNum, String strMessage) {
        this.iSheetNum = iSheetNum;
        this.iRowNum = iRowNum;
        this.strMessage = strMessage;
    }
}
