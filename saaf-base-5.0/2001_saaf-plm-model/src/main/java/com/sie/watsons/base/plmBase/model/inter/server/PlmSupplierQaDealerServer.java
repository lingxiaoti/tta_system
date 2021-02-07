package com.sie.watsons.base.plmBase.model.inter.server;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.sie.watsons.base.plmBase.model.inter.IPlmSupplierQaBrand;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.plmBase.model.entities.PlmSupplierQaDealerEntity_HI;
import com.sie.watsons.base.plmBase.model.entities.PlmSupplierQaNonObInfoEntity_HI;
import com.sie.watsons.base.plmBase.model.entities.readonly.PlmSupplierQaDealerEntity_HI_RO;
import com.sie.watsons.base.plmBase.model.inter.IPlmSupplierQaDealer;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;

@Component("plmSupplierQaDealerServer")
public class PlmSupplierQaDealerServer extends
        BaseCommonServer<PlmSupplierQaDealerEntity_HI> implements
        IPlmSupplierQaDealer {
    private static final Logger LOGGER = LoggerFactory
            .getLogger(PlmSupplierQaDealerServer.class);
    @Autowired
    private ViewObject<PlmSupplierQaDealerEntity_HI> plmSupplierQaDealerDAO_HI;
    @Autowired
    private BaseViewObject<PlmSupplierQaDealerEntity_HI_RO> plmSupplierQaDealerDAO_HI_RO;
    @Autowired
    private ViewObject<PlmSupplierQaNonObInfoEntity_HI> plmSupplierQaNonObInfoDAO_HI;

    @Autowired
    private IPlmSupplierQaBrand plmSupplierQaBrandServer;

    public PlmSupplierQaDealerServer() {
        super();
    }

    @Override
    public Pagination<PlmSupplierQaDealerEntity_HI_RO> findPlmSupplierQaDealerInfo(
            JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
        StringBuffer sql = new StringBuffer(
                PlmSupplierQaDealerEntity_HI_RO.QUERY_SQL);
        Map<String, Object> paramsMap = new HashMap<>();
        com.sie.saaf.common.util.SaafToolUtils.parperHbmParam(
                PlmSupplierQaDealerEntity_HI_RO.class, queryParamJSON, sql,
                paramsMap);
        Pagination<PlmSupplierQaDealerEntity_HI_RO> pagination = plmSupplierQaDealerDAO_HI_RO
                .findPagination(sql, paramsMap, pageIndex, pageRows);
        return pagination;
    }

    @Override
    public List<PlmSupplierQaDealerEntity_HI> savePlmSupplierQaDealerInfo(
            JSONObject queryParamJSON) {
        List<PlmSupplierQaDealerEntity_HI> dataList = JSON.parseArray(
                queryParamJSON.getJSONArray("plmSupplierQaDealerList")
                        .toString(), PlmSupplierQaDealerEntity_HI.class);
        Set<String> personNameSet = new HashSet<>();
        for (PlmSupplierQaDealerEntity_HI data : dataList) {
            data.setOperatorUserId(queryParamJSON.getInteger("varUserId"));
            if (data.getPersonName() != null)
                personNameSet.add(data.getPersonName());
            if (SaafToolUtils.isNullOrEmpty(data.getPlmSupplierQaNonObInfoId())) {
                data.setPlmSupplierQaNonObInfoId(queryParamJSON
                        .getInteger("plmSupplierQaNonObInfoId"));
            }
            plmSupplierQaDealerDAO_HI.saveOrUpdate(data);
        }
        // 保存行数据生产商、经销商名至头表
        if (dataList.size() != 0) {
            Integer headId = null;
            if (dataList.get(0).getPlmSupplierQaNonObInfoId() != null) {
                headId = dataList.get(0).getPlmSupplierQaNonObInfoId();
            } else {
                headId = queryParamJSON.getInteger("plmSupplierQaNonObInfoId");
            }
            List<PlmSupplierQaNonObInfoEntity_HI> headerList = plmSupplierQaNonObInfoDAO_HI
                    .findByProperty("plmSupplierQaNonObInfoId", headId);
            if (headerList.size() == 0)
                throw new IllegalArgumentException("未获取头表信息，请刷新重试！");
            PlmSupplierQaNonObInfoEntity_HI data = headerList.get(0);
            // if (dataList.get(0).getPersonTypeName().equals("经销商")) {
            // // data.setDealerName(personNameSet.toString()
            // // .substring(1, personNameSet.toString().length() - 1)
            // // .replace(" ", ""));
            // } else {
            // // data.setProducerName(personNameSet.toString()
            // // .substring(1, personNameSet.toString().length() - 1)
            // // .replace(" ", ""));
            // }
            plmSupplierQaNonObInfoDAO_HI.saveOrUpdate(data);
        }
        return dataList;
    }

    @Override
    public Integer deletePlmSupplierQaDealerInfo(JSONObject queryParamJSON) throws Exception {
        if (SaafToolUtils.isNullOrEmpty(queryParamJSON
                .getJSONArray("plmSupplierQaDealerList"))) {
            Integer plmSupplierQaNonObInfoId = queryParamJSON.getInteger("plmSupplierQaNonObInfoId");
            if (null != plmSupplierQaNonObInfoId) {
                // 校验资质组删除
                plmSupplierQaBrandServer.saveCheckSupplierQaDelete(plmSupplierQaNonObInfoId);
            }
            PlmSupplierQaDealerEntity_HI entity = JSON.parseObject(
                    queryParamJSON.toString(),
                    PlmSupplierQaDealerEntity_HI.class);
            plmSupplierQaDealerDAO_HI.delete(entity);
            return 1;
        }
        List<PlmSupplierQaDealerEntity_HI> dataList = JSON.parseArray(
                queryParamJSON.getJSONArray("plmSupplierQaDealerList")
                        .toString(), PlmSupplierQaDealerEntity_HI.class);
        dataList.forEach(row->{
            // 校验资质组删除
            try {
                plmSupplierQaBrandServer.saveCheckSupplierQaDelete(row.getPlmSupplierQaNonObInfoId());
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        });

        plmSupplierQaDealerDAO_HI.deleteAll(dataList);
        plmSupplierQaDealerDAO_HI.fluch();

        if (SaafToolUtils.isNullOrEmpty(queryParamJSON
                .getInteger("plmSupplierQaNonObInfoId"))) {
            throw new IllegalArgumentException("无法更新头表信息！无法删除行数据！");
        }
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("plmSupplierQaNonObInfoId",
                queryParamJSON.getInteger("plmSupplierQaNonObInfoId"));
        queryParams.put("personType", dataList.get(0).getPersonType());
        List<PlmSupplierQaDealerEntity_HI> existList = plmSupplierQaDealerDAO_HI
                .findByProperty(queryParams);
        if (existList.size() != 0) {
            Set<String> personNameSet = new HashSet<>();
            for (PlmSupplierQaDealerEntity_HI data : existList) {
                personNameSet.add(data.getPersonName());
            }
            List<PlmSupplierQaNonObInfoEntity_HI> headerList = plmSupplierQaNonObInfoDAO_HI
                    .findByProperty("plmSupplierQaNonObInfoId", queryParamJSON
                            .getInteger("plmSupplierQaNonObInfoId"));
            if (headerList.size() == 0)
                throw new IllegalArgumentException("未获取头表信息！请刷新重试！");
            PlmSupplierQaNonObInfoEntity_HI headerEntity = headerList.get(0);
            if (dataList.get(0).getPersonType().equals("DEALER")) {
                headerEntity.setDealerName(personNameSet.toString()
                        .substring(1, personNameSet.toString().length() - 1)
                        .replace(" ", ""));
            } else {
                headerEntity.setProducerName(personNameSet.toString()
                        .substring(1, personNameSet.toString().length() - 1)
                        .replace(" ", ""));
            }
        }
        return dataList.size();
    }
}
