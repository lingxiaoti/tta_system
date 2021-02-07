package com.sie.watsons.base.pos.supplierinfo.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.pos.supplierinfo.model.entities.EquPosSuppInfoWithDeptEntity_HI;
import com.sie.watsons.base.pos.supplierinfo.model.entities.readonly.EquPosSuppInfoWithDeptEntity_HI_RO;
import com.sie.watsons.base.pos.supplierinfo.model.inter.IEquPosSuppInfoWithDept;
import com.sie.watsons.base.utils.ResultUtils;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("equPosSuppInfoWithDeptServer")
public class EquPosSuppInfoWithDeptServer extends BaseCommonServer<EquPosSuppInfoWithDeptEntity_HI> implements IEquPosSuppInfoWithDept{
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosSuppInfoWithDeptServer.class);

	@Autowired
	private ViewObject<EquPosSuppInfoWithDeptEntity_HI> equPosSuppInfoWithDeptDAO_HI;

	@Autowired
	private BaseViewObject<EquPosSuppInfoWithDeptEntity_HI_RO> equPosSuppInfoWithDeptEntity_HI_RO;

	public EquPosSuppInfoWithDeptServer() {
		super();
	}

	/**
	 * 供应商基础信息查询-区分部门
	 * @param queryParamJSON 参数：密级Entity中的字段
	 * @return
	 */
	public JSONObject findSupplierInfoWithDept(JSONObject queryParamJSON, Integer pageIndex,
									   Integer pageRows) {
		StringBuffer sql = new StringBuffer(EquPosSuppInfoWithDeptEntity_HI_RO.QUERY_SQL);
		Map<String, Object> map = new HashMap<>();
		SaafToolUtils.parperHbmParam(EquPosSuppInfoWithDeptEntity_HI_RO.class, queryParamJSON, sql, map);
		Pagination<EquPosSuppInfoWithDeptEntity_HI_RO> pagination = equPosSuppInfoWithDeptEntity_HI_RO.findPagination(sql, map,pageIndex,pageRows);

		List<String> incomingParam = new ArrayList<>();
		List<String> efferentParam = new ArrayList<>();
		List<String> typeParam = new ArrayList<>();
		incomingParam.add("supplierType");
		efferentParam.add("supplierTypeMeaning");
		typeParam.add("EQU_SUPPLIER_TYPE");
		JSONObject returnJson = (JSONObject) JSON.toJSON(pagination);
		JSONArray data = returnJson.getJSONArray("data");
		data = ResultUtils.getLookUpValues( data , incomingParam, efferentParam, typeParam);
		returnJson.put("data",data);

		return JSONObject.parseObject(JSONObject.toJSONString(returnJson));
	}

	/**
	 * 供应商基础信息保存-区分部门
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public EquPosSuppInfoWithDeptEntity_HI saveSupplierInfoWithDept(JSONObject params) throws Exception {
		return this.saveOrUpdate(params);
	}

    @Override
    public EquPosSuppInfoWithDeptEntity_HI saveForSupplierFilesDetail(JSONObject paramsJONS, Integer userId) {
        HashMap<String, Object> hashMap = Maps.newHashMap();
        hashMap.put("deptCode", paramsJONS.getString("deptCode"));
        hashMap.put("supplierId", paramsJONS.getInteger("supplierId"));
        List<EquPosSuppInfoWithDeptEntity_HI> list = equPosSuppInfoWithDeptDAO_HI.findByProperty(hashMap);
        Assert.notEmpty(list, "根据供应商部门和供应商id查询供应商部门表不存在");
        EquPosSuppInfoWithDeptEntity_HI entityHi1 = list.get(0);
        entityHi1.setOperatorUserId(userId);
        entityHi1.setRemark(paramsJONS.getString("remark"));
        entityHi1.setWhetherSign(paramsJONS.getString("whetherSign"));
        equPosSuppInfoWithDeptDAO_HI.saveOrUpdate(entityHi1);
        return entityHi1;
    }
}
