package com.sie.watsons.base.pos.csrUpdate.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.model.inter.server.GenerateCodeServer;
import com.sie.saaf.common.util.SaafBeanUtils;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.pos.csrUpdate.model.entities.EquPosCsrUpdateHeadEntity_HI;
import com.sie.watsons.base.pos.csrUpdate.model.entities.EquPosCsrUpdateLineEntity_HI;
import com.sie.watsons.base.pos.csrUpdate.model.entities.readonly.EquPosCsrUpdateHeadEntity_HI_RO;
import com.sie.watsons.base.pos.csrUpdate.model.inter.IEquPosCsrUpdateHead;
import com.sie.watsons.base.pos.csrUpdate.model.inter.IEquPosCsrUpdateLine;
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

@Component("equPosCsrUpdateHeadServer")
public class EquPosCsrUpdateHeadServer extends BaseCommonServer<EquPosCsrUpdateHeadEntity_HI> implements IEquPosCsrUpdateHead{
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosCsrUpdateHeadServer.class);

	@Autowired
	private ViewObject<EquPosCsrUpdateHeadEntity_HI> equPosCsrUpdateDAO_HI;
	@Autowired
	private BaseViewObject<EquPosCsrUpdateHeadEntity_HI_RO> equPosCsrUpdateDAO_HI_RO;
	@Autowired
	private GenerateCodeServer generateCodeServer;
	@Autowired
	private IEquPosCsrUpdateLine equPosCsrUpdateLine;
    /**
     *new ArrayList再包一层，list才可以做增加和删除元素的操作 IA:13 Security:12
     */
    List<String> queryList = new ArrayList<>(Arrays.asList("0E", "13","12"));

	@Override
	public JSONObject findCsrUpdatePagination(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {

		StringBuffer sqlBuffer = new StringBuffer(EquPosCsrUpdateHeadEntity_HI_RO.QUERY_HEAD_SQL);
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
		Pagination<EquPosCsrUpdateHeadEntity_HI_RO> findResult = equPosCsrUpdateDAO_HI_RO.findPagination(sqlBuffer, map, pageIndex, pageRows);
		return JSONObject.parseObject(JSONObject.toJSONString(findResult));
	}

	@Override
	public EquPosCsrUpdateHeadEntity_HI_RO findCsrUpdateHeadInfo(JSONObject queryParamJSON) {

		StringBuffer headSql = new StringBuffer(EquPosCsrUpdateHeadEntity_HI_RO.Query_HEAD_ONLY);
		Map<String, Object> map = new HashMap<String, Object>(16);
		// 根据头id查询头数据
		SaafToolUtils.parperParam(queryParamJSON, "t.update_head_id", "updateHeadId", headSql, map, "=");
		EquPosCsrUpdateHeadEntity_HI_RO headData = equPosCsrUpdateDAO_HI_RO.get(headSql, map);
		return headData;
	}


	@Override
	public Integer saveCsrUpdateHeadAndLine(String editParams,Integer userId) throws Exception {
		JSONObject jsonObject = JSON.parseObject(editParams);
		EquPosCsrUpdateHeadEntity_HI headEntity = JSONObject.parseObject(editParams, EquPosCsrUpdateHeadEntity_HI.class);
        // 安全校验，如果点提交会有拟定状态变成已批准
        if ("submit".equals(jsonObject.getString("status"))){
        	if(null != headEntity.getUpdateHeadId()){
				EquPosCsrUpdateHeadEntity_HI byId = equPosCsrUpdateDAO_HI.getById(headEntity.getUpdateHeadId());
				if (!"DRAFT".equals(byId.getDocStatus())){
					throw new IllegalArgumentException("非拟定状态单据不能修改");
				}
			}
        }
		List<EquPosCsrUpdateLineEntity_HI> lineList = JSONObject.parseArray(JSONObject.toJSONString(jsonObject.get("lineData")), EquPosCsrUpdateLineEntity_HI.class);
		EquPosCsrUpdateHeadEntity_HI entityHi = new EquPosCsrUpdateHeadEntity_HI();
		// 处理修改
		if (headEntity.getUpdateHeadId() != null) {
			entityHi = equPosCsrUpdateDAO_HI.getById(headEntity.getUpdateHeadId());
			SaafBeanUtils.copyUnionProperties(headEntity, entityHi);
			// 处理新增
		} else {
			entityHi = headEntity;
			String updateCode = generateCodeServer.getSupplierSuspendCode("CSRGX", 4, true, true);
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
		equPosCsrUpdateDAO_HI.saveOrUpdate(entityHi);

		// 执行保存或更新行数据
		equPosCsrUpdateLine.saveCsrUpdateLineList(entityHi.getUpdateHeadId(),lineList,userId);
		return entityHi.getUpdateHeadId();
	}

	@Override
	public String deleteCsrUpdate(JSONObject jsonObject, int userId) {

		Integer tempSpecialId = jsonObject.getInteger("updateHeadId");
		EquPosCsrUpdateHeadEntity_HI entity = equPosCsrUpdateDAO_HI.getById(tempSpecialId);
		if (!ObjectUtils.isEmpty(entity)) {
			equPosCsrUpdateDAO_HI.delete(entity);
			return SToolUtils.convertResultJSONObj("S", "操作成功", 0, null).toString();
		} else {
			return SToolUtils.convertResultJSONObj("E", "根据删除id查询数据为空,操作失败", 0, null).toString();
		}
	}

}
