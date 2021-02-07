package com.sie.watsons.base.pos.scoreUpdate.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.model.inter.server.GenerateCodeServer;
import com.sie.saaf.common.util.SaafBeanUtils;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.pos.scoreUpdate.model.entities.EquPosScoreUpdateHeadEntity_HI;
import com.sie.watsons.base.pos.scoreUpdate.model.entities.EquPosScoreUpdateLineEntity_HI;
import com.sie.watsons.base.pos.scoreUpdate.model.entities.readonly.EquPosScoreUpdateHeadEntity_HI_RO;
import com.sie.watsons.base.pos.scoreUpdate.model.inter.IEquPosScoreUpdateHead;
import com.sie.watsons.base.pos.scoreUpdate.model.inter.IEquPosScoreUpdateLine;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("equPosScoreUpdateHeadServer")
public class EquPosScoreUpdateHeadServer extends BaseCommonServer<EquPosScoreUpdateHeadEntity_HI> implements IEquPosScoreUpdateHead {
    private static final Logger LOGGER = LoggerFactory.getLogger(EquPosScoreUpdateHeadServer.class);

    @Autowired
    private ViewObject<EquPosScoreUpdateHeadEntity_HI> equPosScoreUpdateDAO_HI;
    @Autowired
    private BaseViewObject<EquPosScoreUpdateHeadEntity_HI_RO> equPosScoreUpdateDAO_HI_RO;
    @Autowired
    private GenerateCodeServer generateCodeServer;
    @Autowired
    private IEquPosScoreUpdateLine equPosScoreUpdateLine;

    public EquPosScoreUpdateHeadServer() {
        super();
    }

    @Override
    public JSONObject findScoreUpdatePagination(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {

        StringBuffer sqlBuffer = new StringBuffer(EquPosScoreUpdateHeadEntity_HI_RO.QUERY_HEAD_SQL);
        Map<String, Object> map = new HashMap<String, Object>(16);
        // 供应商名称
        SaafToolUtils.parperParam(queryParamJSON, "si.supplier_name", "supplierName", sqlBuffer, map, "like");
        // 评分审核更新单号
        SaafToolUtils.parperParam(queryParamJSON, "t.update_code", "updateCode", sqlBuffer, map, "like");
        // 单据状态
        SaafToolUtils.parperParam(queryParamJSON, "t.doc_status", "docStatus", sqlBuffer, map, "=");
        // 部门
//		SaafToolUtils.parperParam(queryParamJSON, "t.dept_id", "deptId", sqlBuffer, map, "=");
        //SaafToolUtils.parperParam(queryParamJSON, "t.dept_code", "deptCode", sqlBuffer, map, "=");
        // 创建时间从到创建时间至
        SaafToolUtils.parperParam(queryParamJSON, "t.creation_Date", "endDate", sqlBuffer, map, "<=");
        SaafToolUtils.parperParam(queryParamJSON, "t.creation_Date", "startDate", sqlBuffer, map, ">=");
        // 排序
//        if("0E".equals(queryParamJSON.getString("deptCode"))){
//            sqlBuffer.append(" and t.dept_code = '" + queryParamJSON.getString("deptCode") + "'");
//        }else{
//            sqlBuffer.append(" and t.dept_code <> '0E'");
//        }

        sqlBuffer.append(" and t.dept_code = '0E' order by t.last_update_date desc");
        Pagination<EquPosScoreUpdateHeadEntity_HI_RO> findResult = equPosScoreUpdateDAO_HI_RO.findPagination(sqlBuffer, map, pageIndex, pageRows);
        return JSONObject.parseObject(JSONObject.toJSONString(findResult));
    }

    @Override
    public EquPosScoreUpdateHeadEntity_HI_RO findScoreUpdateHeadInfo(JSONObject queryParamJSON) {

        StringBuffer headSql = new StringBuffer(EquPosScoreUpdateHeadEntity_HI_RO.Query_HEAD_ONLY);
        Map<String, Object> map = new HashMap<String, Object>(16);
        // 根据头id查询头数据
        SaafToolUtils.parperParam(queryParamJSON, "t.update_head_id", "updateHeadId", headSql, map, "=");
        EquPosScoreUpdateHeadEntity_HI_RO headData = equPosScoreUpdateDAO_HI_RO.get(headSql, map);
        return headData;
    }


    @Override
    public Integer saveScoreUpdateHeadAndLine(String editParams, Integer userId) throws Exception {
        JSONObject jsonObject = JSON.parseObject(editParams);
        EquPosScoreUpdateHeadEntity_HI headEntity = JSONObject.parseObject(editParams, EquPosScoreUpdateHeadEntity_HI.class);
        // 安全校验，如果点提交会有拟定状态变成已批准
        if ("submit".equals(jsonObject.getString("status"))){
            if(null != headEntity.getUpdateHeadId()){
                EquPosScoreUpdateHeadEntity_HI byId = equPosScoreUpdateDAO_HI.getById(headEntity.getUpdateHeadId());
                if (!"DRAFT".equals(byId.getDocStatus())){
                    throw new IllegalArgumentException("非拟定状态单据不能修改");
                }
            }
        }
        List<EquPosScoreUpdateLineEntity_HI> lineList = JSONObject.parseArray(JSONObject.toJSONString(jsonObject.get("lineData")), EquPosScoreUpdateLineEntity_HI.class);
        EquPosScoreUpdateHeadEntity_HI entityHi = new EquPosScoreUpdateHeadEntity_HI();
        // 处理修改
        if (headEntity.getUpdateHeadId() != null) {
            entityHi = equPosScoreUpdateDAO_HI.getById(headEntity.getUpdateHeadId());
            SaafBeanUtils.copyUnionProperties(headEntity, entityHi);
            // 处理新增
        } else {
            entityHi = headEntity;
            String updateCode = generateCodeServer.getSupplierSuspendCode("PFGX", 4, true, true);
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
        equPosScoreUpdateDAO_HI.saveOrUpdate(entityHi);
        // 执行保存或更新行数据
        equPosScoreUpdateLine.saveScoreUpdateLineList(entityHi.getUpdateHeadId(), lineList, userId);
        return entityHi.getUpdateHeadId();
    }

    @Override
    public String deleteScoreUpdate(JSONObject jsonObject, int userId) {

        Integer tempSpecialId = jsonObject.getInteger("updateHeadId");
        EquPosScoreUpdateHeadEntity_HI entity = equPosScoreUpdateDAO_HI.getById(tempSpecialId);
        if (!ObjectUtils.isEmpty(entity)) {
            equPosScoreUpdateDAO_HI.delete(entity);
            return SToolUtils.convertResultJSONObj("S", "操作成功", 0, null).toString();
        } else {
            return SToolUtils.convertResultJSONObj("E", "根据删除id查询数据为空,操作失败", 0, null).toString();
        }
    }

}
