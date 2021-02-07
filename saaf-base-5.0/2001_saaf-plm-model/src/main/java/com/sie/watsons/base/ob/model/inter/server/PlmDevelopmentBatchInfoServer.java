package com.sie.watsons.base.ob.model.inter.server;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.ob.model.entities.PlmDevelopmentBatchInfoEntity_HI;
import com.sie.watsons.base.ob.model.entities.readonly.PlmDevelopmentBatchInfoEntity_HI_RO;
import com.sie.watsons.base.ob.model.inter.IPlmDevelopmentBatchInfo;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;

@Component("plmDevelopmentBatchInfoServer")
public class PlmDevelopmentBatchInfoServer extends
        BaseCommonServer<PlmDevelopmentBatchInfoEntity_HI> implements
        IPlmDevelopmentBatchInfo {
    private static final Logger LOGGER = LoggerFactory
            .getLogger(PlmDevelopmentBatchInfoServer.class);
    @Autowired
    private ViewObject<PlmDevelopmentBatchInfoEntity_HI> plmDevelopmentBatchInfoDAO_HI;
    @Autowired
    private BaseViewObject<PlmDevelopmentBatchInfoEntity_HI_RO> plmDevelopmentBatchInfoDAO_HI_RO;

    public PlmDevelopmentBatchInfoServer() {
        super();
    }

    @Override
    public Pagination<PlmDevelopmentBatchInfoEntity_HI_RO> findPlmDevelopmentBatchInfoInfo(
            JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
        StringBuffer sql = new StringBuffer(
                PlmDevelopmentBatchInfoEntity_HI_RO.QUERY_SQL);
        if (!SaafToolUtils.isNullOrEmpty(queryParamJSON.getString("report"))) {
            sql = new StringBuffer(PlmDevelopmentBatchInfoEntity_HI_RO.REPORT_SQL);
        }
        Map<String, Object> paramsMap = new HashMap<>();
        SaafToolUtils.parperHbmParam(PlmDevelopmentBatchInfoEntity_HI_RO.class,
                queryParamJSON, sql, paramsMap);
        if (!SaafToolUtils.isNullOrEmpty(queryParamJSON.getString("report"))) {
            sql.append(" order by pdbi.LAST_UPDATE_DATE desc ");
        }
        StringBuffer countSql = SaafToolUtils.getSimpleSqlCountString(sql,
                "count(*)");
        Pagination<PlmDevelopmentBatchInfoEntity_HI_RO> pagination = plmDevelopmentBatchInfoDAO_HI_RO
                .findPagination(sql, paramsMap, pageIndex, pageRows);
        return pagination;
    }

    @Override
    public List<PlmDevelopmentBatchInfoEntity_HI> savePlmDevelopmentBatchInfoInfo(
            JSONObject queryParamJSON) {
        List<PlmDevelopmentBatchInfoEntity_HI> dataList = JSON.parseArray(
                queryParamJSON.getJSONArray("plmDevelopmentBatchInfoList")
                        .toString(), PlmDevelopmentBatchInfoEntity_HI.class);
        dataList = this.changeStatusByCommand(dataList,
                queryParamJSON.getString("commandStatus"),
                queryParamJSON.getInteger("varUserId"),
                queryParamJSON.getInteger("plmDevelopmentInfoId"),
                queryParamJSON.getInteger("plmProjectId"));
        plmDevelopmentBatchInfoDAO_HI.saveOrUpdateAll(dataList);
        return dataList;
    }

    public List<PlmDevelopmentBatchInfoEntity_HI> changeStatusByCommand(
            List<PlmDevelopmentBatchInfoEntity_HI> dataList,
            String commandStatus, Integer userId, Integer plmDevelopmentInfoId,
            Integer plmProjectId) {
        for (PlmDevelopmentBatchInfoEntity_HI data : dataList) {

            data.setOperatorUserId(userId);
            if (plmProjectId != null)
                data.setPlmProjectId(plmProjectId);
            if (plmDevelopmentInfoId != null) {
                data.setPlmDevelopmentInfoId(plmDevelopmentInfoId);
            }
        }
        return dataList;
    }

    @Override
    public Integer deletePlmDevelopmentBatchInfoInfo(JSONObject queryParamJSON) {
        if (SaafToolUtils.isNullOrEmpty(queryParamJSON
                .getJSONArray("plmDevelopmentBatchInfoList"))) {
            PlmDevelopmentBatchInfoEntity_HI entity = JSON.parseObject(
                    queryParamJSON.toString(),
                    PlmDevelopmentBatchInfoEntity_HI.class);
            plmDevelopmentBatchInfoDAO_HI.delete(entity);
            return 1;
        }
        List<PlmDevelopmentBatchInfoEntity_HI> dataList = JSON.parseArray(
                queryParamJSON.getJSONArray("plmDevelopmentBatchInfoList")
                        .toString(), PlmDevelopmentBatchInfoEntity_HI.class);
        plmDevelopmentBatchInfoDAO_HI.deleteAll(dataList);
        return dataList.size();
    }

}
