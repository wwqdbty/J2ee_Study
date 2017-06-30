package com.kulang.service;

import com.kulang.study.domain.model.ImportExcelResultDetail;
import com.kulang.study.domain.model.enums.EnumThirdPlatform;

import java.util.List;
import java.util.Map;

/**
 * 第三方平台相关数据服务
 * <p>
 * Created by wenqiang.wang@fengjr.com on 2017/3/2.
 */
public interface IThirdPlatformDataService {

    /**
     * 分页查询第三方平台返回数据的中英文对照字典, 不存在数据时返回NULL
     *
     * @param eName
     * @return
     */
//    PageResult<ThirdPlatformDictionary> pageListThirdPlatformDictionary(final EnumThirdPlatform enumThirdPlatform, final String eName, PageInfo pageInfo);

    /**
     * 获取第三方平台返回数据的中英文对照字典, 不存在数据时返回NULL
     *
     * @param enumThirdPlatform 三方平台名称
     * @return
     */
//    List<ThirdPlatformDictionary> listThirdPlatformDictionary(EnumThirdPlatform enumThirdPlatform);

    /**
     * 通过调用大数据, 拉取第三方平台征信数据, 拉取失败时返回NULL
     *
     * @param enumThirdPlatform 三方平台名称
     * @param idCard            身份证号
     * @return
     */
//    Map<String, String> getCreditData(EnumThirdPlatform enumThirdPlatform, final String idCard);

    /**
     * 批量添加三方机构字典数据, 返回添加失败的记录
     *
     * @param mapListDictionaryData key为sheet号value为数据集的Hash
     * @return
     */
    List<ImportExcelResultDetail> batchAddDictionary(final Map<Integer, List<String[]>> mapListDictionaryData, EnumThirdPlatform enumThirdPlatform);
}
