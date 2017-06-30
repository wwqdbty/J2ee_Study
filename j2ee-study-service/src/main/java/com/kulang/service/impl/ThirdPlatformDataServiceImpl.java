package com.kulang.service.impl;

/*import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.fengjr.p2p.asset.admin.service.IThirdPlatformDataService;
import com.fengjr.p2p.asset.common.config.ThirdPlatformConfig;
import com.fengjr.p2p.asset.common.dto.ImportExcelResultDetail;
import com.fengjr.p2p.asset.common.dto.ThirdPlatformDictionaryExcelData;
import com.fengjr.p2p.asset.common.dto.ThirdPlatformRepaymentExcelData;
import com.fengjr.p2p.asset.common.enums.EnumThirdPlatform;
import com.fengjr.p2p.asset.common.utils.*;
import com.fengjr.p2p.asset.common.utils.excel.Rule;
import com.fengjr.p2p.asset.common.utils.httpclient.HttpClientBuilder;
import com.fengjr.p2p.asset.common.utils.httpclient.HttpResult;
import com.fengjr.p2p.asset.dao.entity.*;
import com.fengjr.p2p.asset.dao.mapper.AssetInfoMapper;
import com.fengjr.p2p.asset.dao.mapper.ThirdPlatformDictionaryMapper;
import com.fengjr.p2p.asset.dao.mapper.ThirdPlatformDictionaryMapperSelf;
import com.fengjr.p2p.asset.dao.mapper.ThirdPlatformRepaymentDataMapper;
import com.fengjr.ruler.credit.api.FengfdCreditService;
import com.fengjr.ruler.credit.api.dto.Result;
import com.fengjr.ruler.credit.api.dto.RulerCreditUserRequestDto;
import com.fengjr.ruler.credit.api.enums.SystemEnum;
import com.fengjr.upm.filter.util.UserUtils;
import com.fengjr.usercenter.criteria.PageInfo;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.beanutils.converters.BigDecimalConverter;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.*;

*//**
 * 资产系统的asset 的三方平台数据服务. 可参照的内容有
 * 1. 数据校验validateAndProcess、validateAndProcessDict
 * 2. apache BeanUtils 类型转换器注册 batchAddRepayData、a、b
 *//*
@Slf4j
@Service
public class ThirdPlatformDataServiceImpl implements IThirdPlatformDataService {

    @Autowired
    private ThirdPlatformDictionaryMapper thirdPlatformDictionaryMapper;

    @Autowired
    private ThirdPlatformDictionaryMapperSelf thirdPlatformDictionaryMapperSelf;

    @Autowired
    private ThirdPlatformRepaymentDataMapper thirdPlatformRepaymentDataMapper;

    @Autowired
    private AssetInfoMapper assetInfoMapper;

    @Autowired
    private ThirdPlatformConfig thirdPlatformConfig;

    @Autowired
    FengfdCreditService fengfdCreditServiceDubbo;

    @Override
    public PageResult<ThirdPlatformDictionary> pageListThirdPlatformDictionary(final EnumThirdPlatform enumThirdPlatform, final String eName, PageInfo pageInfo) {
        if (pageInfo == null) {
            pageInfo = new PageInfo(0, 20);
        }

        ThirdPlatformDictionaryCondition thirdPlatformDictionaryCondition = new ThirdPlatformDictionaryCondition();
        ThirdPlatformDictionaryCondition.Criteria criteria = thirdPlatformDictionaryCondition.createCriteria();
        if (enumThirdPlatform != null) {
            criteria.andPlatformEqualTo(enumThirdPlatform.name());
        }
        if (StringUtils.isNotBlank(eName)) {
            criteria.andEnameEqualTo(eName);
        }
        criteria.andSstatusEqualTo("1");
        thirdPlatformDictionaryCondition.setLimitOffset(pageInfo.getOffset());
        thirdPlatformDictionaryCondition.setLimitSize(pageInfo.getSize());
        thirdPlatformDictionaryCondition.setOrderByClause("PARENT_ID ASC, ORDER_NUM ASC");

        List<ThirdPlatformDictionary> listThirdPlatformDictionary = thirdPlatformDictionaryMapper.selectByCondition(thirdPlatformDictionaryCondition);

        // 无效数据时设为空
        if (CollectionUtils.isEmpty(listThirdPlatformDictionary)) {
            log.info("listThirdPlatformDictionary -> 不存在第三方平台返回数据的中英文对照字典, params=[enumThirdPlatform={}, eName={}, pageInfo={}]", enumThirdPlatform.name(), eName, pageInfo);

            return null;
        }
        // 总记录数
        int iTotalCount = thirdPlatformDictionaryMapper.countByCondition(thirdPlatformDictionaryCondition);

        return new PageResult<ThirdPlatformDictionary>(listThirdPlatformDictionary, iTotalCount);
    }

    @Override
    public List<ThirdPlatformDictionary> listThirdPlatformDictionary(EnumThirdPlatform enumThirdPlatform) {
        Preconditions.checkArgument(enumThirdPlatform != null, "非法参数：enumThirdPlatform" + enumThirdPlatform);

        List<ThirdPlatformDictionary> listThirdPlatformDictionary = thirdPlatformDictionaryMapperSelf.selectByPlatform(enumThirdPlatform.name(), "1");

        // 无效数据时设为空
        if (CollectionUtils.isEmpty(listThirdPlatformDictionary)) {
            log.info("listThirdPlatformDictionary -> 不存在第三方平台返回数据的中英文对照字典");

            return null;
        }

        return listThirdPlatformDictionary;


        *//*List<ThirdPlatformDictionary> listDc = new ArrayList<ThirdPlatformDictionary>();

        ThirdPlatformDictionary dc1 = new ThirdPlatformDictionary();
        dc1.setId("1");dc1.setEname("n_1");dc1.setCname("名字1");dc1.setParentId("0");
        ThirdPlatformDictionary dc1_1 = new ThirdPlatformDictionary();
        dc1_1.setId("1_1");dc1_1.setEname("n_1_1");dc1_1.setCname("名字1_1");dc1_1.setParentId("1");
        ThirdPlatformDictionary dc1_2 = new ThirdPlatformDictionary();
        dc1_2.setId("1_2");dc1_2.setEname("n_1_2");dc1_2.setCname("名字1_2");dc1_2.setParentId("1");
        ThirdPlatformDictionary dc1_3 = new ThirdPlatformDictionary();
        dc1_3.setId("1_3");dc1_3.setEname("n_1_3");dc1_3.setCname("名字1_3");dc1_3.setParentId("1");

        ThirdPlatformDictionary dc1_3_1 = new ThirdPlatformDictionary();
        dc1_3_1.setId("1_3_1");dc1_3_1.setEname("n_1_3_1");dc1_3_1.setCname("名字1_3_1");dc1_3_1.setParentId("1_3");
        ThirdPlatformDictionary dc1_3_2 = new ThirdPlatformDictionary();
        dc1_3_2.setId("1_3_2");dc1_3_2.setEname("n_1_3_2");dc1_3_2.setCname("名字1_3_2");dc1_3_2.setParentId("1_3");


        ThirdPlatformDictionary dc1_4 = new ThirdPlatformDictionary();
        dc1_4.setId("1_4");dc1_4.setEname("n_1_4");dc1_4.setCname("名字1_4");dc1_4.setParentId("1");

        ThirdPlatformDictionary dc1_4_1 = new ThirdPlatformDictionary();
        dc1_4_1.setId("1_4_1");dc1_4_1.setEname("n_1_4_1");dc1_4_1.setCname("名字1_4_1");dc1_4_1.setParentId("1_4");
        ThirdPlatformDictionary dc1_4_2 = new ThirdPlatformDictionary();
        dc1_4_2.setId("1_4_2");dc1_4_2.setEname("n_1_4_2");dc1_4_2.setCname("名字1_4_2");dc1_4_2.setParentId("1_4");

        ThirdPlatformDictionary dc2 = new ThirdPlatformDictionary();
        dc2.setId("2");dc2.setEname("n_2");dc2.setCname("名字2");dc2.setParentId("0");
        ThirdPlatformDictionary dc2_1 = new ThirdPlatformDictionary();
        dc2_1.setId("2_1");dc2_1.setEname("n_2_1");dc2_1.setCname("名字2_1");dc2_1.setParentId("2");
        ThirdPlatformDictionary dc2_2 = new ThirdPlatformDictionary();
        dc2_2.setId("2_2");dc2_2.setEname("n_2_2");dc2_2.setCname("名字2_2");dc2_2.setParentId("2");
        ThirdPlatformDictionary dc2_3 = new ThirdPlatformDictionary();
        dc2_3.setId("2_3");dc2_3.setEname("n_2_3");dc2_3.setCname("名字2_3");dc2_3.setParentId("2");

        ThirdPlatformDictionary dc3 = new ThirdPlatformDictionary();
        dc3.setId("3");dc3.setEname("n_3");dc3.setCname("名字3");dc3.setParentId("0");
        ThirdPlatformDictionary dc3_1 = new ThirdPlatformDictionary();
        dc3_1.setId("3_1");dc3_1.setEname("n_3_1");dc3_1.setCname("名字3_1");dc3_1.setParentId("3");
        ThirdPlatformDictionary dc3_2 = new ThirdPlatformDictionary();
        dc3_2.setId("3_2");dc3_2.setEname("n_3_2");dc3_2.setCname("名字3_2");dc3_2.setParentId("3");
        ThirdPlatformDictionary dc3_3 = new ThirdPlatformDictionary();
        dc3_3.setId("3_3");dc3_3.setEname("n_3_3");dc3_3.setCname("名字3_3");dc3_3.setParentId("3");

        listDc.add(dc1);
        listDc.add(dc1_1);
        listDc.add(dc1_2);
        listDc.add(dc1_3);
        listDc.add(dc1_3_1);
        listDc.add(dc1_3_2);
        listDc.add(dc1_4);
        listDc.add(dc1_4_1);
        listDc.add(dc1_4_2);

        listDc.add(dc2);
        listDc.add(dc2_1);
        listDc.add(dc2_2);
        listDc.add(dc2_3);

        listDc.add(dc3);
        listDc.add(dc3_1);
        listDc.add(dc3_2);
        listDc.add(dc3_3);

        return listDc;*//*
    }

    @Override
    public List<ImportExcelResultDetail> batchAddRepayData(final List<String[]> listRepayData) {
        Preconditions.checkArgument(CollectionUtils.isNotEmpty(listRepayData), "非法参数：listRepayData");

        // Excel业务对象集合
        List<ThirdPlatformRepaymentExcelData> listThirdPlatformRepaymentExcelData = recombineParsedExcelRepayData(listRepayData);
        if (CollectionUtils.isEmpty(listThirdPlatformRepaymentExcelData)) {
            return null;
        }

        // 数据校验并处理
        List<ImportExcelResultDetail> listFailedRecord = validateAndProcess(listThirdPlatformRepaymentExcelData);
        // 处理后无需继续导入的记录,　则直接返回
        if (CollectionUtils.isEmpty(listThirdPlatformRepaymentExcelData)) {
            return listFailedRecord;
        }

        // 当前操作用户
        String strAdminName = UserUtils.getUser().getName();
        // 生成批次号
        String strBatchNumber = strAdminName + "_" + UtilAll.formatDate(new Date(), UtilAll.yyyyMMddHHmmss);
        ConvertUtils.deregister();
        for (ThirdPlatformRepaymentExcelData thirdPlatformRepaymentExcelData : listThirdPlatformRepaymentExcelData) {
            ThirdPlatformRepaymentData thirdPlatformRepaymentData = new ThirdPlatformRepaymentData();

            ConvertUtils.register(new Converter() {
                @Override
                public Date convert(Class type, Object value) {
                    if (value == null || "".equals(value)) {
                        return null;
                    }
                    String strTmp = value.toString();
                    if (strTmp.length() == 7 && strTmp.contains("-") && !strTmp.contains(":")) {
                        return DateUtils.parseDate(strTmp, "yyyy-MM");
                    }

                    return DateUtils.formatDate(value.toString());
                }
            }, Date.class);
            ConvertUtils.register(new BigDecimalConverter(null), BigDecimal.class);
            ConvertUtils.register(new Converter() {
                @Override
                public Integer convert(Class type, Object value) {
                    // 当value参数等于空时返回空
                    if (value == null || "".equals(value)) {
                        return null;
                    }

                    // Excel中的数字类型返回的都是double类型
                    return new BigDecimal(value.toString()).intValue();
                }
            }, Integer.class);
            try {
                org.apache.commons.beanutils.BeanUtils.copyProperties(thirdPlatformRepaymentData, thirdPlatformRepaymentExcelData);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

            // ID
            thirdPlatformRepaymentData.setId(UUIDGenerator.getUUID());
            // 批次号
            thirdPlatformRepaymentData.setBatchNum(strBatchNumber);
            // 产品ID
            thirdPlatformRepaymentData.setBaseAssetId(thirdPlatformRepaymentExcelData.getProductId());
            // 创建者ID
            thirdPlatformRepaymentData.setEmployeeId(strAdminName);
            // 创建时间
            thirdPlatformRepaymentData.setCreateTime(new Date());
            try {
                // 写入数据表. TODO 后期数据量增大后, 应修改为分批批量插入
                int iResult = thirdPlatformRepaymentDataMapper.insertSelective(thirdPlatformRepaymentData);
                if (iResult == 0) {
                    listFailedRecord.add(new ImportExcelResultDetail(thirdPlatformRepaymentExcelData.getSheetNum(), thirdPlatformRepaymentExcelData.getRowNum(), "写入数据表失败"));

                    continue;
                }
            } catch (Exception e) {
                e.printStackTrace();
                log.error("batchAddRepayData -> 写入数据表失败, e={}", e);
                listFailedRecord.add(new ImportExcelResultDetail(thirdPlatformRepaymentExcelData.getSheetNum(), thirdPlatformRepaymentExcelData.getRowNum(), "写入数据表失败"));

                continue;
            }
        }

        return listFailedRecord;
    }

    @Override
    public List<ThirdPlatformRepaymentData> listRepaymentDataByAssetName(final String assetName) {
        // 资产信息
        AssetInfoExample assetInfoExample = new AssetInfoExample();
        AssetInfoExample.Criteria criteria = assetInfoExample.createCriteria();
        criteria.andProductNameEqualTo(assetName);
        List<AssetInfo> listAssetInfo = assetInfoMapper.selectByExample(assetInfoExample);
        if (CollectionUtils.isEmpty(listAssetInfo)) {
            return null;
        }
        if (listAssetInfo.size() > 1) {
            log.error("listRepaymentDataByAssetName -> 业务级错误. 根据资产名称查询资产,包含同名的资产数据, [assetName={}]", assetName);

            return null;
        }

        // 还款数据
        ThirdPlatformRepaymentDataCondition condition = new ThirdPlatformRepaymentDataCondition();
        ThirdPlatformRepaymentDataCondition.Criteria criteria1 = condition.createCriteria();
        criteria1.andBaseAssetIdEqualTo(listAssetInfo.get(0).getId().toString());
        List<ThirdPlatformRepaymentData> listThirdPlatformRepaymentData = thirdPlatformRepaymentDataMapper.selectByCondition(condition);
        if (CollectionUtils.isEmpty(listThirdPlatformRepaymentData)) {
            return null;
        }

        return listThirdPlatformRepaymentData;
    }

    @Override
    public Map<String, String> getCreditData(EnumThirdPlatform enumThirdPlatform, final String idCard) {
        // 组装调用参数
        Map<String, String> mapParam = new HashMap<>();
        String url = thirdPlatformConfig.getCreditUrl(idCard, enumThirdPlatform);

        long lStartCallTime = new Date().getTime();
        log.info("请求获取用户征信信息接口, url={}, [requestParam={}]", url, mapParam);

        *//*Map<String, String> mapCallResult = null;
        try {
            mapCallResult = httpCommon.callGetService(url, mapParam, new TypeReference<Map<String, String>>() {});
        } catch (Exception ex) {
            log.error("错误:exception={}. 请求获取用户征信信息接口, url={}, [requestParam={}]", ex, url, JSON.toJSONString(mapParam));

            return null;
        }*//*

        HttpResult result = null;
        try {
            result = HttpClientBuilder.getClient(url).setToPostJsonString("{'rdm':" + new Random().nextInt() + "}").post();

        } catch (Exception ex) {
            log.error("错误:exception={}. 请求获取用户征信信息接口, url={}, [requestParam={}]", ex, url, JSON.toJSONString(mapParam));

            return null;
        }
        String responseBody = Strings.nullToEmpty(result.getBody());
        Map<String, String> mapCallResult = JSON.parseObject(responseBody, new TypeReference<Map<String, String>>(){});

        long lEndCallTime = new Date().getTime();
        // 请求耗时
        long lTimeConsuming = lEndCallTime - lStartCallTime;
        if (MapUtils.isEmpty(mapCallResult)) {
            log.warn("警告. 请求获取用户征信信息接口完成, 未返回有效结果. 耗时={}, url={}, [requestParam={}], response result={}", lTimeConsuming, url, JSON.toJSONString(mapParam), (mapCallResult == null ? "NULL" : JSON.toJSONString(mapCallResult)));

            return mapCallResult;
        }

        log.info("请求获取用户征信信息接口完成, 耗时={}, url={}, [requestParam={}], response result={}", lTimeConsuming, url, JSON.toJSONString(mapParam), JSON.toJSONString(mapCallResult));

        // 本期硬编码写死
        if (enumThirdPlatform == EnumThirdPlatform.JXL) {
            String strTmp = mapCallResult.get("user_grid_search");
            Map<String, String> map = JSON.parseObject(strTmp, new TypeReference<Map<String, String>>() {});
            strTmp = map.get("grid_info");
            map = JSON.parseObject(strTmp, new TypeReference<Map<String, String>>() {});
            strTmp = map.get("result");
            map = JSON.parseObject(strTmp, new TypeReference<Map<String, String>>() {});

            return map;
        }

        return mapCallResult;


        *//*String strCreditJson = "{\n" +
                "    \"n_1\": {\n" +
                "        \"n_1_1\": \"value_1_1\",\n" +
                "        \"n_1_2\": \"value_1_2\",\n" +
                "        \"n_1_3\": {\n" +
                "            \"n_1_3_1\": \"value_1_3_1\",\n" +
                "            \"n_1_3_2\": \"value_1_3_2\"\n" +
                "        },\n" +
                "        \"n_1_4\": [\n" +
                "            {\n" +
                "                \"n_1_4_1\": \"value[0]_1_4_1\",\n" +
                "                \"n_1_4_2\": \"value[0]_1_4_2\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"n_1_4_1\": \"value[1]_1_4_1\",\n" +
                "                \"n_1_4_2\": \"value[1]_1_4_2\"\n" +
                "            }\n" +
                "        ]\n" +
                "    },\n" +
                "    \"n_2\": {\n" +
                "        \"n_2_1\": \"value_1_1\",\n" +
                "        \"n_2_2\": \"value_1_2\",\n" +
                "        \"n_2_3\": \"value_1_3\"\n" +
                "    },\n" +
                "    \"n_3\": {\n" +
                "        \"n_3_1\": \"value_3_1\",\n" +
                "        \"n_3_2\": \"value_3_2\",\n" +
                "        \"n_3_3\": \"value_3_3\"\n" +
                "    }\n" +
                "}";

        Map<String, String> map = JSON.parseObject(strCreditJson, new com.alibaba.fastjson.TypeReference<Map<String, String>>() {
        });

        return map;*//*
    }

    @Override
    public List<ImportExcelResultDetail> batchAddDictionary(final Map<Integer, List<String[]>> mapListDictExcelData, EnumThirdPlatform enumThirdPlatform) {
        Preconditions.checkArgument(MapUtils.isNotEmpty(mapListDictExcelData), "非法参数：mapListDictExcelData");

        // 合并所有数据
        List<String[]> listAllExcelData = new ArrayList<>();
        for (List<String[]> listExcelData : mapListDictExcelData.values()) {
            listAllExcelData.addAll(listExcelData);
        }

        // Excel业务对象集合
        List<ThirdPlatformDictionaryExcelData> listExcelDataBean = recombineParsedExcelDictData(mapListDictExcelData);
        if (CollectionUtils.isEmpty(listExcelDataBean)) {
            return null;
        }

        // 数据校验并处理
        List<ImportExcelResultDetail> listFailedRecord = validateAndProcessDict(listExcelDataBean);
        // 处理后无需继续导入的记录,　则直接返回
        if (CollectionUtils.isEmpty(listExcelDataBean)) {
            return listFailedRecord;
        }

        // 当前操作用户
        String strAdminName = UserUtils.getUser().getName();
        // 生成批次号
        String strBatchNumber = strAdminName + "_" + UtilAll.formatDate(new Date(), UtilAll.yyyyMMddHHmmss);
        for (ThirdPlatformDictionaryExcelData excelDataBean : listExcelDataBean) {
            ThirdPlatformDictionary thirdPlatformDictionary = new ThirdPlatformDictionary();
            ConvertUtils.deregister();
            ConvertUtils.register(new Converter() {
                @Override
                public Integer convert(Class type, Object value) {
                    // 当value参数等于空时返回空
                    if (value == null || "".equals(value)) {
                        return null;
                    }

                    // Excel中的数字类型返回的都是double类型
                    return new BigDecimal(value.toString()).intValue();
                }
            }, Integer.class);
            try {
                org.apache.commons.beanutils.BeanUtils.copyProperties(thirdPlatformDictionary, excelDataBean);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            // 所属平台
            thirdPlatformDictionary.setPlatform(enumThirdPlatform.name());
            // 状态
            thirdPlatformDictionary.setSstatus(excelDataBean.getIsShow());
            // 创建者ID
            thirdPlatformDictionary.setEmployeeId(strAdminName);
            // 创建时间
            thirdPlatformDictionary.setCreateTime(new Date());
            try {
                // 写入数据表, 存在则更新. TODO 后期数据量增大后, 应修改为分批批量插入
                // 查询
                ThirdPlatformDictionary tmpO = thirdPlatformDictionaryMapper.selectById(excelDataBean.getId());
                int iResult = 0;
                // 更新
                if (tmpO != null) {
                    iResult = thirdPlatformDictionaryMapper.updateById(thirdPlatformDictionary);
                }
                // 新增
                else {
                    iResult = thirdPlatformDictionaryMapper.insertSelective(thirdPlatformDictionary);
                }
            } catch (Exception e) {
                e.printStackTrace();
                log.error("batchAddDictionary -> 写入数据表失败, e={}", e);
                listFailedRecord.add(new ImportExcelResultDetail(excelDataBean.getSheetNum(), excelDataBean.getRowNum(), "写入数据表失败"));

                continue;
            }
        }

        return listFailedRecord;
    }

    @Override
    public List<Map<String, String>> ListUserRiskResultOfRuler(final String idCard) {
        RulerCreditUserRequestDto rulerCreditUserRequestDto = new RulerCreditUserRequestDto();
        rulerCreditUserRequestDto.setIdCard(idCard);
        rulerCreditUserRequestDto.setSaleSystem(SystemEnum.creditRisk.name());

        Result<List<Map<String, String>>> result = null;
        try {
            result = fengfdCreditServiceDubbo.getCreditUser(rulerCreditUserRequestDto);
        } catch (Exception e) {
            log.error("ListUserRiskResultOfRuler -> 获取用户风控结果发生异常, [idCard={}, saleSystem={}], e={}", idCard, rulerCreditUserRequestDto.getSaleSystem(), e);

            return null;
        }
        if (result == null) {
            return null;
        }

        // 调用返回失败
        if (!result.getIsSuccess()) {
            log.error("ListUserRiskResultOfRuler -> 获取用户风控结果失败, [idCard={}, saleSystem={}]", idCard, rulerCreditUserRequestDto.getSaleSystem());

            return null;
        }

        return result.getData();
    }
    *//**
     * 重组已解析为数组的三方机构还款数据Excel
     *
     * @param listParsedExcelRepayData
     * @return
     *//*
    private List<ThirdPlatformRepaymentExcelData> recombineParsedExcelRepayData(final List<String[]> listParsedExcelRepayData) {
        if (CollectionUtils.isEmpty(listParsedExcelRepayData)) {
            return listParsedExcelRepayData == null ? null : Collections.EMPTY_LIST;
        }

        List<ThirdPlatformRepaymentExcelData> list = new ArrayList<>();

        for (int i = 1, iLoopCount = listParsedExcelRepayData.size(); i < iLoopCount; i++) {
            ThirdPlatformRepaymentExcelData thirdPlatformRepaymentData = new ThirdPlatformRepaymentExcelData(1, i + 2);

            // Excel从第1列开始为有效值
            int arrIndex = 0;
            String[] arrStrRepayData = listParsedExcelRepayData.get(i);
            // 基本信息-产品名称
            thirdPlatformRepaymentData.setProductName(arrStrRepayData[arrIndex++].trim());
            // 基本信息-当前月份_BASE_MONTHS
            thirdPlatformRepaymentData.setBaseMonths(arrStrRepayData[arrIndex++]);
            // 基本信息-组织机构代码
            thirdPlatformRepaymentData.setBaseOrgCode(arrStrRepayData[arrIndex++]);
            // 基本信息-合同号
            thirdPlatformRepaymentData.setBaseContractNo(arrStrRepayData[arrIndex++]);
            // 基本信息-客户剩余本金，总的未还的本金，如先息后本的话，此值将一直保持为借款金额，直至最后一期才会减
            thirdPlatformRepaymentData.setBaseSurplusPrincipal(arrStrRepayData[arrIndex++]);
            // 基本信息-客户还款状态
            thirdPlatformRepaymentData.setBaseRepayStatus(arrStrRepayData[arrIndex++]);
            // 基本信息-客户逾期天数
            thirdPlatformRepaymentData.setBaseOverdueDays(arrStrRepayData[arrIndex++]);
            // 还款计划-客户当期还款状态
            thirdPlatformRepaymentData.setPlanCurRepayStatus(arrStrRepayData[arrIndex++]);
            // 还款计划-还款期数
            thirdPlatformRepaymentData.setPlanPeriods(arrStrRepayData[arrIndex++]);
            // 还款计划-结账日期，yyyy/MM/dd
            thirdPlatformRepaymentData.setPlanSettleDate(arrStrRepayData[arrIndex++]);
            // 还款计划-本金
            thirdPlatformRepaymentData.setPlanPrincipal(arrStrRepayData[arrIndex++]);
            // 还款计划-利息
            thirdPlatformRepaymentData.setPlanInterest(arrStrRepayData[arrIndex++]);
            // 还款计划-管理费
            thirdPlatformRepaymentData.setPlanManageFee(arrStrRepayData[arrIndex++]);
            // 还款计划-手续费
            thirdPlatformRepaymentData.setPlanProcedureFee(arrStrRepayData[arrIndex++]);
            // 还款计划-滞纳金
            thirdPlatformRepaymentData.setPlanLateFee(arrStrRepayData[arrIndex++]);
            // 还款计划-违约金
            thirdPlatformRepaymentData.setPlanBreachFee(arrStrRepayData[arrIndex++]);
            // 还款计划-计划总额
            thirdPlatformRepaymentData.setPlanPlanTotalamount(arrStrRepayData[arrIndex++]);
            // 实际还款-客户实际还款日期，yyyy-MM-dd HH:mm:ss
            thirdPlatformRepaymentData.setRealRepayDate(arrStrRepayData[arrIndex++]);
            // 实际还款-本金
            thirdPlatformRepaymentData.setRealPrincipal(arrStrRepayData[arrIndex++]);
            // 实际还款-利息
            thirdPlatformRepaymentData.setRealInterest(arrStrRepayData[arrIndex++]);
            // 实际还款-管理费
            thirdPlatformRepaymentData.setRealManageFee(arrStrRepayData[arrIndex++]);
            // 实际还款-手续费
            thirdPlatformRepaymentData.setRealProcedureFee(arrStrRepayData[arrIndex++]);
            // 实际还款-滞纳金
            thirdPlatformRepaymentData.setRealLateFee(arrStrRepayData[arrIndex++]);
            // 实际还款-违约金
            thirdPlatformRepaymentData.setRealBreachFee(arrStrRepayData[arrIndex++]);
            // 实际还款-实还总额
            thirdPlatformRepaymentData.setRealTotalamount(arrStrRepayData[arrIndex++]);
            // 实际还款-还款账号
            thirdPlatformRepaymentData.setRealRepayAccount(arrStrRepayData[arrIndex++]);
            // 实际还款-还款方式
            thirdPlatformRepaymentData.setRealRepayMode(arrStrRepayData[arrIndex++]);

            list.add(thirdPlatformRepaymentData);
        }

        return list;
    }

    *//**
     * 校验并对数据进行处理, 将直接对参数的内容进行操作
     *
     * @param listThirdPlatformRepaymentExcelData 三方平台导入的Excel还款数据
     *
     * @return 校验不通过的记录
     *//*
    private List<ImportExcelResultDetail> validateAndProcess(List<ThirdPlatformRepaymentExcelData> listThirdPlatformRepaymentExcelData) {
        if (CollectionUtils.isEmpty(listThirdPlatformRepaymentExcelData)) {
            return listThirdPlatformRepaymentExcelData == null ? null : Collections.EMPTY_LIST;
        }

        List<ImportExcelResultDetail> listFailedRecord = new ArrayList<>();

        Set<String> setProductName = new HashSet<>();
        Iterator<ThirdPlatformRepaymentExcelData> iterator = listThirdPlatformRepaymentExcelData.iterator();
        while (iterator.hasNext()) {
            ThirdPlatformRepaymentExcelData thirdPlatformRepaymentExcelData = iterator.next();

            int iSheetNum = thirdPlatformRepaymentExcelData.getSheetNum();
            int iRowNum = thirdPlatformRepaymentExcelData.getRowNum();

            // 基本信息-产品名称
            String strTmp = thirdPlatformRepaymentExcelData.getProductName();
            if (StringUtils.isBlank(strTmp)) {
                listFailedRecord.add(new ImportExcelResultDetail(iSheetNum, iRowNum, "基本信息-产品名称为空"));
                iterator.remove();

                continue;
            }
            setProductName.add(strTmp);
            // 基本信息-当前月份_BASE_MONTHS
            strTmp = thirdPlatformRepaymentExcelData.getBaseMonths();
            if (StringUtils.isBlank(strTmp)) {
                listFailedRecord.add(new ImportExcelResultDetail(iSheetNum, iRowNum, "基本信息-当前月份为空"));
                iterator.remove();

                continue;
            }
            if (!DateUtils.isValidDate(strTmp, "yyyy-MM")) {
                listFailedRecord.add(new ImportExcelResultDetail(iSheetNum, iRowNum, "基本信息-当前月份非日期格式"));
                iterator.remove();

                continue;
            }
            // 基本信息-客户剩余本金，总的未还的本金，如先息后本的话，此值将一直保持为借款金额，直至最后一期才会减
            strTmp = thirdPlatformRepaymentExcelData.getBaseSurplusPrincipal();
            if (StringUtils.isNotEmpty(strTmp) && !UtilAll.isValidNumber(strTmp)) {
                listFailedRecord.add(new ImportExcelResultDetail(iSheetNum, iRowNum, "基本信息-客户剩余本金非有效格式"));
                iterator.remove();

                continue;
            }
            // 基本信息-客户逾期天数
            strTmp = thirdPlatformRepaymentExcelData.getBaseOverdueDays();
            if (StringUtils.isNotEmpty(strTmp) && !UtilAll.isValidNumber(strTmp)) {
                listFailedRecord.add(new ImportExcelResultDetail(iSheetNum, iRowNum, "基本信息-客户逾期天数非有效格式"));
                iterator.remove();

                continue;
            }
            // 还款计划-还款期数
            strTmp = thirdPlatformRepaymentExcelData.getPlanPeriods();
            if (StringUtils.isBlank(strTmp)) {
                listFailedRecord.add(new ImportExcelResultDetail(iSheetNum, iRowNum, "还款计划-客户当期还款期数为空"));
                iterator.remove();

                continue;
            }
            if (!UtilAll.isValidNumber(strTmp)) {
                listFailedRecord.add(new ImportExcelResultDetail(iSheetNum, iRowNum, "还款计划-还款期数非有效格式"));
                iterator.remove();

                continue;
            }
            // 还款计划-结账日期，yyyy/MM/dd
            strTmp = thirdPlatformRepaymentExcelData.getPlanSettleDate();
            if (StringUtils.isNotEmpty(strTmp) && !DateUtils.isValidDate(strTmp, "yyyy-MM-dd")) {
                listFailedRecord.add(new ImportExcelResultDetail(iSheetNum, iRowNum, "还款计划-结账日期非日期格式"));
                iterator.remove();

                continue;
            }
            // 还款计划-本金
            strTmp = thirdPlatformRepaymentExcelData.getPlanPrincipal();
            if (StringUtils.isNotEmpty(strTmp) && !UtilAll.isValidNumber(strTmp)) {
                listFailedRecord.add(new ImportExcelResultDetail(iSheetNum, iRowNum, "还款计划-本金非有效格式"));
                iterator.remove();

                continue;
            }
            // 还款计划-利息
            strTmp = thirdPlatformRepaymentExcelData.getPlanInterest();
            if (StringUtils.isNotEmpty(strTmp) && !UtilAll.isValidNumber(strTmp)) {
                listFailedRecord.add(new ImportExcelResultDetail(iSheetNum, iRowNum, "还款计划-利息非有效格式"));
                iterator.remove();

                continue;
            }
            // 还款计划-管理费
            strTmp = thirdPlatformRepaymentExcelData.getPlanManageFee();
            if (StringUtils.isNotEmpty(strTmp) && !UtilAll.isValidNumber(strTmp)) {
                listFailedRecord.add(new ImportExcelResultDetail(iSheetNum, iRowNum, "还款计划-管理费非有效格式"));
                iterator.remove();

                continue;
            }
            // 还款计划-手续费
            strTmp = thirdPlatformRepaymentExcelData.getPlanProcedureFee();
            if (StringUtils.isNotEmpty(strTmp) && !UtilAll.isValidNumber(strTmp)) {
                listFailedRecord.add(new ImportExcelResultDetail(iSheetNum, iRowNum, "还款计划-手续费非有效格式"));
                iterator.remove();

                continue;
            }
            // 还款计划-滞纳金
            strTmp = thirdPlatformRepaymentExcelData.getPlanLateFee();
            if (StringUtils.isNotEmpty(strTmp) && !UtilAll.isValidNumber(strTmp)) {
                listFailedRecord.add(new ImportExcelResultDetail(iSheetNum, iRowNum, "还款计划-滞纳金非有效格式"));
                iterator.remove();

                continue;
            }
            // 还款计划-违约金
            strTmp = thirdPlatformRepaymentExcelData.getPlanBreachFee();
            if (StringUtils.isNotEmpty(strTmp) && !UtilAll.isValidNumber(strTmp)) {
                listFailedRecord.add(new ImportExcelResultDetail(iSheetNum, iRowNum, "还款计划-违约金非有效格式"));
                iterator.remove();

                continue;
            }
            // 还款计划-计划总额
            strTmp = thirdPlatformRepaymentExcelData.getPlanPlanTotalamount();
            if (StringUtils.isNotEmpty(strTmp) && !UtilAll.isValidNumber(strTmp)) {
                listFailedRecord.add(new ImportExcelResultDetail(iSheetNum, iRowNum, "还款计划-计划总额非有效格式"));
                iterator.remove();

                continue;
            }
            // 实际还款-客户实际还款日期，yyyy-MM-dd HH:mm:ss
            strTmp = thirdPlatformRepaymentExcelData.getRealRepayDate();
            if (StringUtils.isNotEmpty(strTmp) && !DateUtils.isValidDate(strTmp, "yyyy-MM-dd")) {
                listFailedRecord.add(new ImportExcelResultDetail(iSheetNum, iRowNum, "实际还款-客户实际还款日期非日期格式"));
                iterator.remove();

                continue;
            }
            // 实际还款-本金
            strTmp = thirdPlatformRepaymentExcelData.getRealPrincipal();
            if (StringUtils.isNotEmpty(strTmp) && !UtilAll.isValidNumber(strTmp)) {
                listFailedRecord.add(new ImportExcelResultDetail(iSheetNum, iRowNum, "实际还款-本金非有效格式"));
                iterator.remove();

                continue;
            }
            // 实际还款-利息
            strTmp = thirdPlatformRepaymentExcelData.getRealInterest();
            if (StringUtils.isNotEmpty(strTmp) && !UtilAll.isValidNumber(strTmp)) {
                listFailedRecord.add(new ImportExcelResultDetail(iSheetNum, iRowNum, "实际还款-利息非有效格式"));
                iterator.remove();

                continue;
            }
            // 实际还款-管理费
            strTmp = thirdPlatformRepaymentExcelData.getRealManageFee();
            if (StringUtils.isNotEmpty(strTmp) && !UtilAll.isValidNumber(strTmp)) {
                listFailedRecord.add(new ImportExcelResultDetail(iSheetNum, iRowNum, "实际还款-管理费非有效格式"));
                iterator.remove();

                continue;
            }
            // 实际还款-手续费
            strTmp = thirdPlatformRepaymentExcelData.getRealProcedureFee();
            if (StringUtils.isNotEmpty(strTmp) && !UtilAll.isValidNumber(strTmp)) {
                listFailedRecord.add(new ImportExcelResultDetail(iSheetNum, iRowNum, "实际还款-手续费非有效格式"));
                iterator.remove();

                continue;
            }
            // 实际还款-滞纳金
            strTmp = thirdPlatformRepaymentExcelData.getRealLateFee();
            if (StringUtils.isNotEmpty(strTmp) && !UtilAll.isValidNumber(strTmp)) {
                listFailedRecord.add(new ImportExcelResultDetail(iSheetNum, iRowNum, "实际还款-滞纳金非有效格式"));
                iterator.remove();

                continue;
            }
            // 实际还款-违约金
            strTmp = thirdPlatformRepaymentExcelData.getRealBreachFee();
            if (StringUtils.isNotEmpty(strTmp) && !UtilAll.isValidNumber(strTmp)) {
                listFailedRecord.add(new ImportExcelResultDetail(iSheetNum, iRowNum, "实际还款-违约金非有效格式"));
                iterator.remove();

                continue;
            }
            // 实际还款-实还总额
            strTmp = thirdPlatformRepaymentExcelData.getRealTotalamount();
            if (StringUtils.isNotEmpty(strTmp) && !UtilAll.isValidNumber(strTmp)) {
                listFailedRecord.add(new ImportExcelResultDetail(iSheetNum, iRowNum, "实际还款-实还总额非有效格式"));
                iterator.remove();

                continue;
            }
        }

        // 产品名称有效性校验
        // 获取所有产品名称, 以批量查询产品数据 TODO 后期如数据量增大, 需分批查询最后合并结果集, 否则IN查询超限
        AssetInfoExample assetInfoExample = new AssetInfoExample();
        AssetInfoExample.Criteria criteria = assetInfoExample.createCriteria();
        criteria.andProductNameIn(new ArrayList<String>(setProductName));
        List<AssetInfo> listAssetInfo = assetInfoMapper.selectByExample(assetInfoExample);

        // 筛选装配
        iterator = listThirdPlatformRepaymentExcelData.iterator();
        while (iterator.hasNext()) {
            ThirdPlatformRepaymentExcelData thirdPlatformRepaymentExcelData = iterator.next();

            AssetInfo curAssetInfo = null;
            // 查找产品名是否存在查询结果, 不存在表示无效的产品名
            for (AssetInfo assetInfo : listAssetInfo) {
                if (thirdPlatformRepaymentExcelData.getProductName() .equals(assetInfo.getProductName())) {
                    curAssetInfo = assetInfo;

                    break;
                }
            }
            if (curAssetInfo == null) {
                listFailedRecord.add(new ImportExcelResultDetail(thirdPlatformRepaymentExcelData.getSheetNum(), thirdPlatformRepaymentExcelData.getRowNum(), "基础信息-产品名称在系统中不存在"));
                iterator.remove();

                continue;
            }
            // 设置产品ID
            thirdPlatformRepaymentExcelData.setProductId(curAssetInfo.getId().toString());

            // 唯一性校验, TODO 数据量太大影响效率时, 应修改为批量查询. 在查询时对每组进行查询并UNION结果集, 这样可减少数据库连接数
            ThirdPlatformRepaymentDataCondition thirdPlatformRepaymentDataCondition = new ThirdPlatformRepaymentDataCondition();
            ThirdPlatformRepaymentDataCondition.Criteria criteria1 = thirdPlatformRepaymentDataCondition.createCriteria();
            criteria1.andBaseAssetIdEqualTo(thirdPlatformRepaymentExcelData.getProductId());
            criteria1.andPlanPeriodsEqualTo(new BigDecimal(thirdPlatformRepaymentExcelData.getPlanPeriods()).intValue());
            List<ThirdPlatformRepaymentData> list = thirdPlatformRepaymentDataMapper.selectByCondition(thirdPlatformRepaymentDataCondition);
            if (CollectionUtils.isNotEmpty(list)) {
                listFailedRecord.add(new ImportExcelResultDetail(thirdPlatformRepaymentExcelData.getSheetNum(), thirdPlatformRepaymentExcelData.getRowNum(), "已存在该期产品的还款计划"));
                iterator.remove();

                continue;
            }
        }

        return listFailedRecord;
    }

    *//**
     * 重组已解析为数组的三方机构字典数据Excel
     *
     * @param mapListParsedExcelData
     * @return
     *//*
    private List<ThirdPlatformDictionaryExcelData> recombineParsedExcelDictData(final Map<Integer, List<String[]>> mapListParsedExcelData) {
        if (MapUtils.isEmpty(mapListParsedExcelData)) {
            return mapListParsedExcelData == null ? null : Collections.EMPTY_LIST;
        }

        List<ThirdPlatformDictionaryExcelData> list = new ArrayList<>();

        for (Map.Entry<Integer, List<String[]>> entry : mapListParsedExcelData.entrySet()) {
            List<String[]> listSheetExcelData = entry.getValue();
            for (int i = 0, iLoopCount = listSheetExcelData.size(); i < iLoopCount; i++) {
                ThirdPlatformDictionaryExcelData excelData = new ThirdPlatformDictionaryExcelData(entry.getKey(), i + 2);

                // Excel从第1列开始为有效值
                int arrIndex = 0;
                String[] arrStrRepayData = listSheetExcelData.get(i);
                // 编码_ID(唯一)
                excelData.setId(arrStrRepayData[arrIndex++].trim());
                // 英文
                excelData.setEname(arrStrRepayData[arrIndex++]);
                // 中文
                excelData.setCname(arrStrRepayData[arrIndex++]);
                // 顺序号
                excelData.setOrderNum(arrStrRepayData[arrIndex++]);
                // 是否显示
                excelData.setIsShow(arrStrRepayData[arrIndex++]);
                // 父编码_ID
                excelData.setParentId(arrStrRepayData[arrIndex++]);

                list.add(excelData);
            }
        }

        return list;
    }

    *//**
     * 校验并对数据进行处理, 将直接对参数的内容进行操作, 分步校验, 每条数据仅返回一个错误
     *
     * @param listExcelData 三方平台导入的Excel字典数据
     *
     * @return 校验不通过的记录
     *//*
    private List<ImportExcelResultDetail> validateAndProcessDict(List<ThirdPlatformDictionaryExcelData> listExcelData) {
        if (CollectionUtils.isEmpty(listExcelData)) {
            return listExcelData == null ? null : Collections.EMPTY_LIST;
        }

        List<ImportExcelResultDetail> listFailedRecord = new ArrayList<>();

        // 普通校验
        Iterator<ThirdPlatformDictionaryExcelData> iterator = listExcelData.iterator();
        while (iterator.hasNext()) {
            ThirdPlatformDictionaryExcelData excelData = iterator.next();

            int iSheetNum = excelData.getSheetNum();
            int iRowNum = excelData.getRowNum();

            // 编码_ID(唯一)
            String strTmp = excelData.getId();
            if (StringUtils.isBlank(strTmp)) {
                listFailedRecord.add(new ImportExcelResultDetail(iSheetNum, iRowNum, "编码_ID(唯一)为空"));
                iterator.remove();

                continue;
            }
            // 英文
            strTmp = excelData.getEname();
            if (StringUtils.isBlank(strTmp)) {
                listFailedRecord.add(new ImportExcelResultDetail(iSheetNum, iRowNum, "英文为空"));
                iterator.remove();

                continue;
            }
            // 中文
            strTmp = excelData.getCname();
            if (StringUtils.isBlank(strTmp)) {
                listFailedRecord.add(new ImportExcelResultDetail(iSheetNum, iRowNum, "中文为空"));
                iterator.remove();

                continue;
            }
            // 顺序号
            strTmp = excelData.getOrderNum();
            if (StringUtils.isBlank(strTmp)) {
                listFailedRecord.add(new ImportExcelResultDetail(iSheetNum, iRowNum, "顺序号为空"));
                iterator.remove();

                continue;
            }
            if (!UtilAll.isValidNumber(strTmp)) {
                listFailedRecord.add(new ImportExcelResultDetail(iSheetNum, iRowNum, "顺序号非有效格式"));
                iterator.remove();

                continue;
            }
            // 是否显示
            strTmp = excelData.getIsShow();
            if (StringUtils.isBlank(strTmp)) {
                listFailedRecord.add(new ImportExcelResultDetail(iSheetNum, iRowNum, "是否显示为空"));
                iterator.remove();

                continue;
            }
            if (!UtilAll.isValidNumber(strTmp)) {
                listFailedRecord.add(new ImportExcelResultDetail(iSheetNum, iRowNum, "是否显示非有效格式"));
                iterator.remove();

                continue;
            }
            // 父编码_ID
            strTmp = excelData.getParentId();
            if (StringUtils.isBlank(strTmp)) {
                listFailedRecord.add(new ImportExcelResultDetail(iSheetNum, iRowNum, "是否显示为空"));
                iterator.remove();

                continue;
            }

            // 自关联(ID等于父ID)
            if (excelData.getId().equals(excelData.getParentId())) {
                listFailedRecord.add(new ImportExcelResultDetail(iSheetNum, iRowNum, "自关联(编码_ID(唯一)等于父编码_ID)"));
                iterator.remove();

                continue;
            }
        }

        // 重复性校验
        // 1. 查找重复数据
        Set<ThirdPlatformDictionaryExcelData> setRepetitionData = new HashSet<>();
        iterator = listExcelData.iterator();
        while (iterator.hasNext()) {
            final ThirdPlatformDictionaryExcelData excelData = iterator.next();
            final String strId = excelData.getId();

            List<ThirdPlatformDictionaryExcelData> listTmp = ListUtils.filter(listExcelData, new Rule<ThirdPlatformDictionaryExcelData>() {
                @Override
                public boolean apply(ThirdPlatformDictionaryExcelData thirdPlatformDictionaryExcelData) {
                    String strCurId = thirdPlatformDictionaryExcelData.getId();
                    // 重复
                    if (StringUtils.isNotBlank(strCurId) && strCurId.equals(strId)) {
                        return true;
                    }

                    return false;
                }
            });

            if (listTmp.size() > 1) {
                setRepetitionData.addAll(listTmp);
            }
        }
        // 2. 移除重复数据
        for (ThirdPlatformDictionaryExcelData tmpO : setRepetitionData) {
            boolean bResult = listExcelData.remove(tmpO);
            if (!bResult) {
                System.out.println("*************************** 草草草 **************************");
            }
            listFailedRecord.add(new ImportExcelResultDetail(tmpO.getSheetNum(), tmpO.getRowNum(), "编码_ID(唯一)存在重复项"));
        }

        // 父编码存在性校验
        iterator = listExcelData.iterator();
        while (iterator.hasNext()) {
            ThirdPlatformDictionaryExcelData excelData = iterator.next();

            // 是否存在父记录
            boolean isParentExists = false;
            String strParentId = excelData.getParentId();
            if (strParentId.equals("0")) {
                continue;
            }

            for (ThirdPlatformDictionaryExcelData tmpO : listExcelData) {
                String strCurId = tmpO.getId();
                if (strCurId.equals(strParentId)) {
                    isParentExists = true;

                    break;
                }
            }
            if (!isParentExists) {
                listFailedRecord.add(new ImportExcelResultDetail(excelData.getSheetNum(), excelData.getRowNum(), "父编码_ID对应的记录不存在"));

                iterator.remove();
            }
        }

        return listFailedRecord;
    }


    public static void main(String[] args) {
        a();
        b();
    }

    private static void a() {
        ThirdPlatformDictionaryExcelData excelDataBean = new ThirdPlatformDictionaryExcelData(0, 1);
        excelDataBean.setOrderNum("1.0");

        ThirdPlatformDictionary thirdPlatformDictionary = new ThirdPlatformDictionary();

        ConvertUtils.register(new Converter() {
            @Override
            public Integer convert(Class type, Object value) {
                // 当value参数等于空时返回空
                if (value == null || "".equals(value)) {
                    return null;
                }

                // Excel中的数字类型返回的都是double类型
                return new BigDecimal(value.toString()).intValue();
            }
        }, Integer.class);

        ConvertUtils.register(new Converter() {
            @Override
            public Integer convert(Class type, Object value) {
                // 当value参数等于空时返回空
                if (value == null || "".equals(value)) {
                    return null;
                }

                System.out.println("11111111111111111111");
                // Excel中的数字类型返回的都是double类型
                return new BigDecimal(value.toString()).intValue();
            }
        }, Integer.class);
        try {
            org.apache.commons.beanutils.BeanUtils.copyProperties(thirdPlatformDictionary, excelDataBean);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private static void b() {
        ThirdPlatformDictionaryExcelData excelDataBean = new ThirdPlatformDictionaryExcelData(0, 1);
        excelDataBean.setOrderNum("1.0");

        ThirdPlatformDictionary thirdPlatformDictionary = new ThirdPlatformDictionary();
        try {
            org.apache.commons.beanutils.BeanUtils.copyProperties(thirdPlatformDictionary, excelDataBean);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}*/
