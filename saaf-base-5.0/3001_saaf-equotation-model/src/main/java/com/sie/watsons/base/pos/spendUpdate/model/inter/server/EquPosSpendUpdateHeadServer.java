package com.sie.watsons.base.pos.spendUpdate.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.model.inter.server.GenerateCodeServer;
import com.sie.saaf.common.util.SaafBeanUtils;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.pos.spendUpdate.model.entities.EquPosSpendUpdateHeadEntity_HI;
import com.sie.watsons.base.pos.spendUpdate.model.entities.EquPosSpendUpdateLineEntity_HI;
import com.sie.watsons.base.pos.spendUpdate.model.entities.readonly.EquPosSpendUpdateHeadEntity_HI_RO;
import com.sie.watsons.base.pos.spendUpdate.model.inter.IEquPosSpendUpdateHead;
import com.sie.watsons.base.pos.spendUpdate.model.inter.IEquPosSpendUpdateLine;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("equPosSpendUpdateHeadServer")
public class EquPosSpendUpdateHeadServer extends BaseCommonServer<EquPosSpendUpdateHeadEntity_HI> implements IEquPosSpendUpdateHead {
    private static final Logger LOGGER = LoggerFactory.getLogger(EquPosSpendUpdateHeadServer.class);

    @Autowired
    private ViewObject<EquPosSpendUpdateHeadEntity_HI> equPosSpendUpdateDAO_HI;
    @Autowired
    private BaseViewObject<EquPosSpendUpdateHeadEntity_HI_RO> equPosSpendUpdateDAO_HI_RO;
    @Autowired
    private GenerateCodeServer generateCodeServer;
    @Autowired
    private IEquPosSpendUpdateLine equPosSpendUpdateLine;

    public EquPosSpendUpdateHeadServer() {
        super();
    }

    @Override
    public JSONObject findSpendUpdatePagination(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {

        StringBuffer sqlBuffer = new StringBuffer(EquPosSpendUpdateHeadEntity_HI_RO.QUERY_HEAD_SQL);
        Map<String, Object> map = new HashMap<String, Object>(16);
        // 供应商名称
        SaafToolUtils.parperParam(queryParamJSON, "si.supplier_name", "supplierName", sqlBuffer, map, "like");
        // 概要文件审核更新单号
        SaafToolUtils.parperParam(queryParamJSON, "t.update_code", "updateCode", sqlBuffer, map, "like");
        // 单据状态
        SaafToolUtils.parperParam(queryParamJSON, "t.doc_status", "docStatus", sqlBuffer, map, "=");
        // 部门
        // 部门
//        if(StringUtils.equals("0E",queryParamJSON.getString("deptCode")) || StringUtils.equals("IA",queryParamJSON.getString("deptCode"))){
//            sqlBuffer.append(" and t.dept_code = '0E'");
//        }
        // 创建时间从到创建时间至
        SaafToolUtils.parperParam(queryParamJSON, "t.creation_Date", "endDate", sqlBuffer, map, "<=");
        SaafToolUtils.parperParam(queryParamJSON, "t.creation_Date", "startDate", sqlBuffer, map, ">=");
        // 排序
        sqlBuffer.append(" and t.dept_code = '0E' order by t.last_update_date desc");
        Pagination<EquPosSpendUpdateHeadEntity_HI_RO> findResult = equPosSpendUpdateDAO_HI_RO.findPagination(sqlBuffer, map, pageIndex, pageRows);
        return JSONObject.parseObject(JSONObject.toJSONString(findResult));
    }

    @Override
    public EquPosSpendUpdateHeadEntity_HI_RO findSpendUpdateHeadInfo(JSONObject queryParamJSON) {

        StringBuffer headSql = new StringBuffer(EquPosSpendUpdateHeadEntity_HI_RO.Query_HEAD_ONLY);
        Map<String, Object> map = new HashMap<String, Object>(16);
        // 根据头id查询头数据
        SaafToolUtils.parperParam(queryParamJSON, "t.update_head_id", "updateHeadId", headSql, map, "=");
        return equPosSpendUpdateDAO_HI_RO.get(headSql, map);
    }


    @Override
    public Integer saveSpendUpdateHeadAndLine(String editParams, Integer userId) throws Exception {
        JSONObject jsonObject = JSON.parseObject(editParams);
        EquPosSpendUpdateHeadEntity_HI headEntity = JSONObject.parseObject(editParams, EquPosSpendUpdateHeadEntity_HI.class);
        // 安全校验，如果点提交会有拟定状态变成已批准
        if ("submit".equals(jsonObject.getString("status"))) {
            EquPosSpendUpdateHeadEntity_HI byId = equPosSpendUpdateDAO_HI.getById(headEntity.getUpdateHeadId());
            if (!"DRAFT".equals(byId.getDocStatus())) {
                throw new IllegalArgumentException("非拟定状态单据不能修改");
            }
        }
        List<EquPosSpendUpdateLineEntity_HI> lineList = JSONObject.parseArray(JSONObject.toJSONString(jsonObject.get("lineData")), EquPosSpendUpdateLineEntity_HI.class);
        EquPosSpendUpdateHeadEntity_HI entityHi = new EquPosSpendUpdateHeadEntity_HI();
        // 处理修改
        if (headEntity.getUpdateHeadId() != null) {
            entityHi = equPosSpendUpdateDAO_HI.getById(headEntity.getUpdateHeadId());
            SaafBeanUtils.copyUnionProperties(headEntity, entityHi);
            // 处理新增
        } else {
            entityHi = headEntity;
            String updateCode = generateCodeServer.getSupplierSuspendCode("SSPGX", 4, true, true);
            entityHi.setUpdateCode(updateCode);
        }
        entityHi.setOperatorUserId(userId);
        // 处理状态
        String status = jsonObject.getString("status");
        switch (status) {
            case "save":
                entityHi.setDocStatus("DRAFT");
                break;
            case "submit":
                entityHi.setDocStatus("APPROVAL");
                break;
        }
        equPosSpendUpdateDAO_HI.saveOrUpdate(entityHi);
        // 执行保存或更新行数据
        equPosSpendUpdateLine.saveSpendUpdateLineList(entityHi.getUpdateHeadId(), lineList, userId);
        return entityHi.getUpdateHeadId();
    }

    @Override
    public String deleteSpendUpdate(JSONObject jsonObject, int userId) {

        Integer tempSpecialId = jsonObject.getInteger("updateHeadId");
        EquPosSpendUpdateHeadEntity_HI entity = equPosSpendUpdateDAO_HI.getById(tempSpecialId);
        if (!ObjectUtils.isEmpty(entity)) {
            equPosSpendUpdateDAO_HI.delete(entity);
            return SToolUtils.convertResultJSONObj("S", "操作成功", 0, null).toString();
        } else {
            return SToolUtils.convertResultJSONObj("E", "根据删除id查询数据为空,操作失败", 0, null).toString();
        }
    }

}
