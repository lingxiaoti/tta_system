package com.sie.watsons.base.pos.qualityUpdate.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.model.inter.server.GenerateCodeServer;
import com.sie.saaf.common.util.SaafBeanUtils;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.pos.qualityUpdate.model.entities.EquPosQualityUpdateHeadEntity_HI;
import com.sie.watsons.base.pos.qualityUpdate.model.entities.EquPosQualityUpdateLineEntity_HI;
import com.sie.watsons.base.pos.qualityUpdate.model.entities.readonly.EquPosQualityUpdateHeadEntity_HI_RO;
import com.sie.watsons.base.pos.qualityUpdate.model.inter.IEquPosQualityUpdateHead;
import com.sie.watsons.base.pos.qualityUpdate.model.inter.IEquPosQualityUpdateLine;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.*;

@Component("equPosQualityUpdateHeadServer")
public class EquPosQualityUpdateHeadServer extends BaseCommonServer<EquPosQualityUpdateHeadEntity_HI> implements IEquPosQualityUpdateHead {
    private static final Logger LOGGER = LoggerFactory.getLogger(EquPosQualityUpdateHeadServer.class);

    @Autowired
    private ViewObject<EquPosQualityUpdateHeadEntity_HI> equPosQualityUpdateDAO_HI;
    @Autowired
    private BaseViewObject<EquPosQualityUpdateHeadEntity_HI_RO> equPosQualityUpdateDAO_HI_RO;
    @Autowired
    private GenerateCodeServer generateCodeServer;
    @Autowired
    private IEquPosQualityUpdateLine equPosQualityUpdateLine;
    /**
     *new ArrayList再包一层，list才可以做增加和删除元素的操作
     */
    List<String> queryList = new ArrayList<>(Arrays.asList("0E", "13","12"));

    public EquPosQualityUpdateHeadServer() {
        super();
    }

    @Override
    public JSONObject findQualityUpdatePagination(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {

        StringBuffer sqlBuffer = new StringBuffer(EquPosQualityUpdateHeadEntity_HI_RO.QUERY_HEAD_SQL);
        Map<String, Object> map = new HashMap<String, Object>(16);
        // 供应商名称
        SaafToolUtils.parperParam(queryParamJSON, "si.supplier_name", "supplierName", sqlBuffer, map, "like");
        // 质量审核更新单号
        SaafToolUtils.parperParam(queryParamJSON, "t.update_code", "updateCode", sqlBuffer, map, "like");
        // 单据状态
        SaafToolUtils.parperParam(queryParamJSON, "t.doc_status", "docStatus", sqlBuffer, map, "=");
        // 部门 年度CSR审核导入更新”的“新增”“导入”权限只开放给QA，OEM、IA、security有查看权限
//        if(queryList.contains(queryParamJSON.getString("deptCode"))){
//            sqlBuffer.append(" and t.dept_code = '0E'");
//        }
        // 创建时间从到创建时间至
        SaafToolUtils.parperParam(queryParamJSON, "t.creation_Date", "endDate", sqlBuffer, map, "<=");
        SaafToolUtils.parperParam(queryParamJSON, "t.creation_Date", "startDate", sqlBuffer, map, ">=");
        // 排序
        sqlBuffer.append(" and t.dept_code = '0E' order by t.last_update_date desc");
        Pagination<EquPosQualityUpdateHeadEntity_HI_RO> findResult = equPosQualityUpdateDAO_HI_RO.findPagination(sqlBuffer, map, pageIndex, pageRows);
        return JSONObject.parseObject(JSONObject.toJSONString(findResult));
    }

    @Override
    public EquPosQualityUpdateHeadEntity_HI_RO findQualityUpdateHeadInfo(JSONObject queryParamJSON) {

        StringBuffer headSql = new StringBuffer(EquPosQualityUpdateHeadEntity_HI_RO.Query_HEAD_ONLY);
        Map<String, Object> map = new HashMap<String, Object>(16);
        // 根据头id查询头数据
        SaafToolUtils.parperParam(queryParamJSON, "t.update_head_id", "updateHeadId", headSql, map, "=");
        EquPosQualityUpdateHeadEntity_HI_RO headData = equPosQualityUpdateDAO_HI_RO.get(headSql, map);
        return headData;
    }


    @Override
    public Integer saveQualityUpdateHeadAndLine(String editParams, Integer userId) throws Exception {
        JSONObject jsonObject = JSON.parseObject(editParams);
        EquPosQualityUpdateHeadEntity_HI headEntity = JSONObject.parseObject(editParams, EquPosQualityUpdateHeadEntity_HI.class);
        // 安全校验，如果点提交会有拟定状态变成已批准
        if ("submit".equals(jsonObject.getString("status"))){
            if(null != headEntity.getUpdateHeadId()){
                EquPosQualityUpdateHeadEntity_HI byId = equPosQualityUpdateDAO_HI.getById(headEntity.getUpdateHeadId());
                if (!"DRAFT".equals(byId.getDocStatus())){
                    throw new IllegalArgumentException("非拟定状态单据不能修改");
                }
            }
        }
        List<EquPosQualityUpdateLineEntity_HI> lineList = JSONObject.parseArray(JSONObject.toJSONString(jsonObject.get("lineData")), EquPosQualityUpdateLineEntity_HI.class);
        EquPosQualityUpdateHeadEntity_HI entityHi = new EquPosQualityUpdateHeadEntity_HI();
        // 处理修改
        if (headEntity.getUpdateHeadId() != null) {
            entityHi = equPosQualityUpdateDAO_HI.getById(headEntity.getUpdateHeadId());
            SaafBeanUtils.copyUnionProperties(headEntity, entityHi);
            // 处理新增
        } else {
            entityHi = headEntity;
            String updateCode = generateCodeServer.getSupplierSuspendCode("ZLGX", 4, true, true);
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
        equPosQualityUpdateDAO_HI.saveOrUpdate(entityHi);
        // 执行保存或更新行数据
        equPosQualityUpdateLine.saveQualityUpdateLineList(entityHi.getUpdateHeadId(), lineList, userId);
        return entityHi.getUpdateHeadId();
    }

    @Override
    public String deleteQualityUpdate(JSONObject jsonObject, int userId) {

        Integer tempSpecialId = jsonObject.getInteger("updateHeadId");
        EquPosQualityUpdateHeadEntity_HI entity = equPosQualityUpdateDAO_HI.getById(tempSpecialId);
        if (!ObjectUtils.isEmpty(entity)) {
            equPosQualityUpdateDAO_HI.delete(entity);
            return SToolUtils.convertResultJSONObj("S", "操作成功", 0, null).toString();
        } else {
            return SToolUtils.convertResultJSONObj("E", "根据删除id查询数据为空,操作失败", 0, null).toString();
        }
    }

}
