package com.kulang.dto;

import com.kulang.utils.DateUtils;
import lombok.Data;

import java.util.List;

@Data
public class ImportExcelResult<T> {
    // 导入结果CODE
    private EnumResultCode enumResultCode;
    // 导入说明
    private String strMessage;
    // 导入总记录数
    private int iTotalCount;
    // 导入成功条数
    private int iSuccessCount;
    // 导入失败条数
    private int iFailedCount;
    // 导入结果详情
    private List<ImportExcelResultDetail<T>> listImportResultDetail;

    public ImportExcelResult(EnumResultCode enumResultCode, String strMessage, int iTotalCount, int iSuccessCount, int iFailedCount, List<ImportExcelResultDetail<T>> listImportResultDetail) {
        this.enumResultCode = enumResultCode;
        this.strMessage = strMessage;
        this.iTotalCount = iTotalCount;
        this.iSuccessCount = iSuccessCount;
        this.iFailedCount = iFailedCount;
        this.listImportResultDetail = listImportResultDetail;
    }

    public static ImportExcelResult directFailed(String strMessage) {
        return new ImportExcelResult(EnumResultCode.FAILED_DIRECT, strMessage, 0, 0, 0, null);
    }

    public static enum EnumResultCode {
        SUCCESS("成功"),
        SUCCESS_PART("部分成功"),
        FAILED("失败"),
        // 表示解析错误, 如文件为空、文件大小为0、格式错误
        FAILED_DIRECT("直接失败");

        private final String strImplication;

        private EnumResultCode(String strImplication) {
            this.strImplication = strImplication;
        }
    }

    public static void main(String[] args) {
        List l = null;

        String strDate = "2017-01-03";
        System.out.println(DateUtils.parseDate(strDate, "yyyy-MM-dd HH:mm:ss"));
    }
}