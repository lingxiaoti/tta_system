package com.sie.watsons.base.plmBase.model.inter.server;

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
import com.sie.watsons.base.plmBase.model.entities.PlmDeptListEntity_HI;
import com.sie.watsons.base.plmBase.model.entities.readonly.PlmDeptListEntity_HI_RO;
import com.sie.watsons.base.plmBase.model.inter.IPlmDeptList;
import com.sie.watsons.base.product.model.entities.PlmDataAclHeaderEntity_HI;
import com.sie.watsons.base.product.model.entities.PlmDataAclLineEntity_HI;
import com.sie.watsons.base.product.model.inter.IPlmDataAclHeader;
import com.sie.watsons.base.product.model.inter.IPlmDataAclLine;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;

@Component("plmDeptListServer")
public class PlmDeptListServer extends BaseCommonServer<PlmDeptListEntity_HI>
		implements IPlmDeptList {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(PlmDeptListServer.class);
	@Autowired
	private ViewObject<PlmDeptListEntity_HI> plmDeptListDAO_HI;
	@Autowired
	private BaseViewObject<PlmDeptListEntity_HI_RO> plmDeptListDAO_HI_RO;

	@Autowired
	private IPlmDataAclHeader plmDataAclHeaderServer;// 权限表
	@Autowired
	private IPlmDataAclLine IPlmDataAclLineServer; // 权限行表

	public PlmDeptListServer() {
		super();
	}

	@Override
	public Pagination<PlmDeptListEntity_HI_RO> findPlmDeptListInfo(
			JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
		StringBuffer sql = new StringBuffer(PlmDeptListEntity_HI_RO.QUERY_SQL);
		Map<String, Object> paramsMap = new HashMap<>();
		if (queryParamJSON.containsKey("plmDeptName")) {
			String plmDeptName = queryParamJSON.getString("plmDeptName");
			sql.append(" and upper(pdl.PLM_DEPT_NAME)  like '%"
					+ plmDeptName.toUpperCase() + "%' ");
			queryParamJSON.remove("plmDeptName");
		}

		if (queryParamJSON.containsKey("isselect")) {
			JSONObject deptparam = new JSONObject();
			deptparam.put("deptId", queryParamJSON.getInteger("userDept"));
			List<PlmDataAclHeaderEntity_HI> headli = plmDataAclHeaderServer
					.findList(deptparam);

			if (headli.size() > 0) {
				String location = "";
				PlmDataAclHeaderEntity_HI headinfo = headli.get(0);
				Integer headid = headinfo.getHeadId();

				JSONObject lineparam = new JSONObject();
				lineparam.put("headId", headid);
				lineparam.put("enableFlag","Y");
				List<PlmDataAclLineEntity_HI> linedata = IPlmDataAclLineServer
						.findList(lineparam);
				String sqlappend = " and (";
				for (PlmDataAclLineEntity_HI line : linedata) {
					// QUERY
					String acltype = line.getAclType();
					if (acltype.equals("NEW")) { // 新建权限
						String groupCode = line.getGroupCode();
						if (groupCode.length() == 1 || groupCode.length() == 2) {
							sqlappend += " pdl.PLM_DEPT_CODE like '"
									+ groupCode + "%'  or ";
						} else {
							location += "'" + groupCode + "',";
						}

					}
				}
				sqlappend += " 1=1";
				if (!location.equals("")) {
					sqlappend += " or pdl.PLM_DEPT_CODE in("
							+ location.substring(0, location.length() - 1)
							+ ")";

				}
				sql.append(sqlappend.replace("or  1=1", "").replace("1=1 or",
						""));
				sql.append(")");

			}
		}
		queryParamJSON.remove("userDept");

		com.sie.saaf.common.util.SaafToolUtils.parperHbmParam(
				PlmDeptListEntity_HI_RO.class, queryParamJSON, sql, paramsMap);
		sql.append(" ORDER BY pdl.PLM_DEPT_CODE ");

		Pagination<PlmDeptListEntity_HI_RO> pagination = plmDeptListDAO_HI_RO
				.findPagination(sql, SaafToolUtils.getSqlCountString(sql),
						paramsMap, pageIndex, pageRows);
		return pagination;
	}

	@Override
	public PlmDeptListEntity_HI savePlmDeptListInfo(JSONObject queryParamJSON) {
		PlmDeptListEntity_HI entity = JSON.parseObject(
				queryParamJSON.toString(), PlmDeptListEntity_HI.class);
		entity.setOperatorUserId(queryParamJSON.getInteger("varUserId"));
		plmDeptListDAO_HI.saveOrUpdate(entity);
		return entity;
	}

}
