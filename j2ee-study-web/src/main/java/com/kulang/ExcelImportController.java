package com.kulang;

import com.kulang.service.IThirdPlatformDataService;
import com.kulang.study.domain.model.ImportExcelResult;
import com.kulang.study.domain.model.ImportExcelResultDetail;
import com.kulang.study.domain.model.enums.EnumThirdPlatform;
import com.kulang.study.domain.model.enums.ErrorCodeEnum;
import com.kulang.study.domain.util.excel.ExcelUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * Created by wenqiang.wang on 2017/3/21.
 */
@RequestMapping("/excelImport")
public class ExcelImportController {
    @Autowired
    private IThirdPlatformDataService thirdPlatformDataService;

    @RequestMapping(value = "/to", method = RequestMethod.GET)
    public ModelAndView to() {
        ModelAndView modelAndView = new ModelAndView("/excelImport");

        return modelAndView;
    }

    /**
     * 导入字典数据
     *
     * @param file
     * @return
     */
    @RequestMapping(value = "/do")
    @ResponseBody
    public ImportExcelResult importAssetInfo(@RequestParam("uploadFile") MultipartFile file, EnumThirdPlatform thirdPlatform) {
        // 导入校验 - 空校验
        if (file == null) {
            return ImportExcelResult.directFailed(ErrorCodeEnum.EMPTY_FILE.getComment());
        }
        // 导入校验 - 文件有效性校验
        String strOriginalFile = file.getOriginalFilename();
        long lFileSize = file.getSize();
        if (StringUtils.isBlank(strOriginalFile) || lFileSize == 0) {
            return ImportExcelResult.directFailed(ErrorCodeEnum.EMPTY_FILE.getComment());
        }
        // 导入校验 - 文件格式校验
        boolean isExcel07 = false;
        if (strOriginalFile.indexOf(".xlsx") >= 0) {
            isExcel07 = true;
        } else if (strOriginalFile.indexOf(".xls") >= 0) {
            isExcel07 = false;
        } else {
            return ImportExcelResult.directFailed(ErrorCodeEnum.FILE_FORMAT_ERROR.getComment());
        }


        InputStream in = null;
        try {
            in = file.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 简析EXCEL
        Map<Integer, List<String[]>> mapListParsedExcel = ExcelUtils.parseExcel(isExcel07, in);
        if (mapListParsedExcel.isEmpty()) {
            return ImportExcelResult.directFailed(ErrorCodeEnum.EMPTY_FILE.getComment());
        }
        // 批量导入
        List<ImportExcelResultDetail> listFailedRecord = thirdPlatformDataService.batchAddDictionary(mapListParsedExcel, thirdPlatform);

        int iTotalCount = 0;
        for (List<String[]> listTmp : mapListParsedExcel.values()) {
            iTotalCount += listTmp.size();
        }
        int iFailedCount = listFailedRecord.size();
        ImportExcelResult.EnumResultCode enumResultCode = ImportExcelResult.EnumResultCode.SUCCESS;
        if (iFailedCount != 0) {
            if (iTotalCount != iFailedCount) {
                enumResultCode = ImportExcelResult.EnumResultCode.SUCCESS_PART;
            } else {
                enumResultCode = ImportExcelResult.EnumResultCode.FAILED;
            }
        }
        ImportExcelResult importExcelResult = new ImportExcelResult(enumResultCode, "", iTotalCount, iTotalCount - iFailedCount, iFailedCount, listFailedRecord);

        return importExcelResult;
    }
}
