package com.sie.watsons.base.item.model.inter.server;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.item.model.entities.readonly.TtaItemEntity_HI_RO;
import com.sie.watsons.base.item.model.inter.ITtaItem;
import com.sie.watsons.base.proposal.model.entities.TtaProposalHeaderEntity_HI;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.sie.watsons.base.item.model.entities.TtaItemEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

@Component("ttaItemServer")
public class TtaItemServer extends BaseCommonServer<TtaItemEntity_HI> implements ITtaItem {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaItemServer.class);

	@Autowired
	private ViewObject<TtaItemEntity_HI> ttaItemDAO_HI;

	@Autowired
	private BaseViewObject<TtaItemEntity_HI_RO> ttaItemDAO_HI_RO;

	public TtaItemServer() {
		super();
	}

	@Override
	public Pagination<TtaItemEntity_HI_RO> find(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
		StringBuffer sql = new StringBuffer();
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		if("userGroupDeptBrand".equals(queryParamJSON.getString("findFlag"))){
			sql.append(TtaItemEntity_HI_RO.TTA_ITEM_LIST);
			SaafToolUtils.parperParam(queryParamJSON, "ti.brand_cn", "brandCn", sql, paramsMap, "like");
			SaafToolUtils.parperParam(queryParamJSON, "ti.dept_code", "deptCode", sql, paramsMap, "like");
			SaafToolUtils.parperParam(queryParamJSON, "ti.group_Code", "groupCode", sql, paramsMap, "like");
			SaafToolUtils.parperParam(queryParamJSON, "ti.dept_desc", "deptDesc", sql, paramsMap, "like");
			SaafToolUtils.parperParam(queryParamJSON, "ti.group_desc", "groupDesc", sql, paramsMap, "like");
			sql.append(" group   by  group_code,group_desc,dept_code,dept_desc,brand_code,brand_cn,brand_en");
            SaafToolUtils.changeQuerySort(queryParamJSON, sql, "ti.group_code desc", false);
        }else if("findDept".equals(queryParamJSON.getString("findFlag"))){  //选择部门
			sql.append(TtaItemEntity_HI_RO.FIND_DEPT_LIST);
			SaafToolUtils.parperParam(queryParamJSON, "ti.dept_code", "departmentCode", sql, paramsMap, "like");
			SaafToolUtils.parperParam(queryParamJSON, "ti.dept_desc", "departmentName", sql, paramsMap, "like");
			sql.append(" group by dept_code,dept_desc");
            SaafToolUtils.changeQuerySort(queryParamJSON, sql, "ti.dept_code desc", false);
        }else if("findBrand".equals(queryParamJSON.getString("findFlag"))){  //选择 Brand
			sql.append(TtaItemEntity_HI_RO.FIND_BARND_LIST).append(" and  nvl(brand_cn,brand_en) is not null ");
			SaafToolUtils.parperParam(queryParamJSON, "ti.brand_cn", "brandCn", sql, paramsMap, "like");
			SaafToolUtils.parperParam(queryParamJSON, "ti.brand_en", "brandEn", sql, paramsMap, "like");
			SaafToolUtils.parperParam(queryParamJSON, "ti.brand_code", "brandCode", sql, paramsMap, "like");
			sql.append(" group by brand_cn,brand_en");
			SaafToolUtils.changeQuerySort(queryParamJSON, sql, "ti.brand_en desc", false);
		}else if("findGroup".equals(queryParamJSON.getString("findFlag"))){  //选择 Group
			sql.append(TtaItemEntity_HI_RO.FIND_GROUP_LIST);
			SaafToolUtils.parperParam(queryParamJSON, "ti.group_code", "groupCode", sql, paramsMap, "like");
			SaafToolUtils.parperParam(queryParamJSON, "ti.group_desc", "groupDesc", sql, paramsMap, "like");
			sql.append(" group by group_code,group_desc");
			SaafToolUtils.changeQuerySort(queryParamJSON, sql, "ti.group_code desc", false);
		}else if("findGroupDept".equals(queryParamJSON.getString("findFlag"))){  //选择 Group
			sql.append(TtaItemEntity_HI_RO.FIND_GROUP_DEPT_LIST);
			SaafToolUtils.parperParam(queryParamJSON, "ti.group_code", "groupCode", sql, paramsMap, "like");
			SaafToolUtils.parperParam(queryParamJSON, "ti.group_desc", "groupDesc", sql, paramsMap, "like");
			SaafToolUtils.parperParam(queryParamJSON, "ti.dept_code", "deptCode", sql, paramsMap, "like");
			SaafToolUtils.parperParam(queryParamJSON, "ti.dept_desc", "deptDesc", sql, paramsMap, "like");
			sql.append(" group by group_code,group_desc,dept_code,dept_desc");
			SaafToolUtils.changeQuerySort(queryParamJSON, sql, "ti.group_code desc", false);
		}else{
			sql.append(TtaItemEntity_HI_RO.TTA_ITEM_V);
			SaafToolUtils.parperParam(queryParamJSON, "v.item_Id", "itemId", sql, paramsMap, "=");
			SaafToolUtils.parperParam(queryParamJSON, "v.status", "status", sql, paramsMap, "in");
			SaafToolUtils.parperParam(queryParamJSON, "v.item_Nbr", "itemNbr", sql, paramsMap, "like");
			SaafToolUtils.parperParam(queryParamJSON, "v.item_Desc_Cn", "itemDescCn", sql, paramsMap, "like");
			SaafToolUtils.parperParam(queryParamJSON, "v.item_Desc_En", "itemDescEn", sql, paramsMap, "like");
			SaafToolUtils.parperParam(queryParamJSON, "v.group_desc", "groupDesc", sql, paramsMap, "like");
			SaafToolUtils.parperParam(queryParamJSON, "v.brand_Cn", "brandCn", sql, paramsMap, "like");
			SaafToolUtils.parperParam(queryParamJSON, "v.dept_desc", "deptDesc", sql, paramsMap, "like");
			SaafToolUtils.changeQuerySort(queryParamJSON, sql, "v.item_Id desc", false);
		}

		Pagination<TtaItemEntity_HI_RO> findList = ttaItemDAO_HI_RO.findPagination(sql,SaafToolUtils.getSqlCountString(sql), paramsMap, pageIndex, pageRows);
		return findList;
	}

	@Override
	public TtaItemEntity_HI saveOrUpdate(JSONObject paramsJSON, int userId) throws Exception {
		TtaItemEntity_HI instance = SaafToolUtils.setEntity(TtaItemEntity_HI.class, paramsJSON, ttaItemDAO_HI, userId);
		ttaItemDAO_HI.saveOrUpdate(instance);
		return instance;
	}

	@Override
	public void delete(Integer pkId) {
		if (pkId == null) {
			return;
		}
		TtaItemEntity_HI instance = ttaItemDAO_HI.getById(pkId);
		if (instance == null) {
			return;
		}
		ttaItemDAO_HI.delete(instance);
	}


	@Override
	public TtaItemEntity_HI_RO findByRoId(JSONObject queryParamJSON) {
		StringBuffer sql = new StringBuffer();
		sql.append(TtaItemEntity_HI_RO.TTA_ITEM_V);

		Map<String, Object> paramsMap = new HashMap<String, Object>();
		SaafToolUtils.parperParam(queryParamJSON, "v.item_id", "itemId", sql, paramsMap, "=");
		return (TtaItemEntity_HI_RO)ttaItemDAO_HI_RO.get(sql,paramsMap);
	}

    @Override
    public TtaItemEntity_HI_RO findByVendor(JSONObject queryParamJSON) {
        StringBuffer sql = new StringBuffer();
        sql.append(TtaItemEntity_HI_RO.TTA_ITEM_V);

        Map<String, Object> paramsMap = new HashMap<String, Object>();
        SaafToolUtils.parperParam(queryParamJSON, "v.vendor_nbr", "vendorNbr", sql, paramsMap, "=");
        return (TtaItemEntity_HI_RO)ttaItemDAO_HI_RO.get(sql,paramsMap);
    }

	@Override
	public List<TtaItemEntity_HI_RO> findDeptInfo() {
		StringBuffer sql = new StringBuffer();
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		sql.append(TtaItemEntity_HI_RO.FIND_GROUP_DEPT_LIST);
		sql.append(" group by group_code,group_desc,dept_code,dept_desc");
		List<TtaItemEntity_HI_RO> findList = ttaItemDAO_HI_RO.findList(sql, paramsMap);
		return findList;
	}
}
