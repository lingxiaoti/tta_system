package com.sie.watsons.base.plmBase.model.inter.server;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.plmBase.model.entities.PlmGroupBrandEntity_HI;
import com.sie.watsons.base.plmBase.model.entities.readonly.PlmGroupBrandEntity_HI_RO;
import com.sie.watsons.base.plmBase.model.inter.IPlmGroupBrand;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;

@Component("plmGroupBrandServer")
public class PlmGroupBrandServer extends
		BaseCommonServer<PlmGroupBrandEntity_HI> implements IPlmGroupBrand {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(PlmGroupBrandServer.class);
	@Autowired
	private ViewObject<PlmGroupBrandEntity_HI> plmGroupBrandDAO_HI;
	@Autowired
	private BaseViewObject<PlmGroupBrandEntity_HI_RO> plmGroupBrandDAO_HI_RO;
	@Autowired
	protected HibernateTemplate hibernateTemplete;

	public PlmGroupBrandServer() {
		super();
	}

	@Override
	public Pagination<PlmGroupBrandEntity_HI_RO> findPlmGroupBrandInfo(
			JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
		StringBuffer sql = new StringBuffer(PlmGroupBrandEntity_HI_RO.QUERY_SQL);

		if (queryParamJSON.containsKey("plmGroupBrandName_like")) {
			sql.append(" and upper(pgb.PLM_GROUP_BRAND_NAME) like '%"
					+ queryParamJSON.getString("plmGroupBrandName_like")
							.toUpperCase() + "%' ");
			queryParamJSON.remove("plmGroupBrandName_like");

		}
		Map<String, Object> paramsMap = new HashMap<>();
		com.sie.saaf.common.util.SaafToolUtils
				.parperHbmParam(PlmGroupBrandEntity_HI_RO.class,
						queryParamJSON, sql, paramsMap);
		sql.append(" order by pgb.LAST_UPDATE_DATE desc");
		Pagination<PlmGroupBrandEntity_HI_RO> pagination = plmGroupBrandDAO_HI_RO
				.findPagination(sql, paramsMap, pageIndex, pageRows);
		return pagination;
	}

	@Override
	public PlmGroupBrandEntity_HI savePlmGroupBrandInfo(
			JSONObject queryParamJSON) {
		PlmGroupBrandEntity_HI entity = JSON.parseObject(
				queryParamJSON.toString(), PlmGroupBrandEntity_HI.class);
		entity.setOperatorUserId(queryParamJSON.getInteger("varUserId"));
		if (SaafToolUtils.isNullOrEmpty(entity.getCreator())) {
			entity.setCreator(queryParamJSON.getString("varUserName"));
		}
		plmGroupBrandDAO_HI.saveOrUpdate(entity);
		return entity;
	}

	/**
	 * 更改单据审批状态
	 * 
	 * @param parseObject
	 * @return
	 * @throws Exception
	 */
	@Override
	public PlmGroupBrandEntity_HI saveConfirmedPlmGroupStatus(
			JSONObject parseObject) throws Exception {
		Integer plmGroupBrandId = parseObject.getIntValue("billNo");
		String INCIDENT = parseObject.getString("INCIDENT");
		String TASKID = parseObject.getString("TASKID");
		String VERSION = parseObject.getString("VERSION");
		String TASKUSER = parseObject.getString("TASKUSER");
		String allNo=parseObject.getString("allNo");

		String status = null;
		PlmGroupBrandEntity_HI entity = new PlmGroupBrandEntity_HI();
		switch (parseObject.getString("status")) {
		case "REJECT":
			status = "REJECT";
			entity = this.getById(plmGroupBrandId);
			entity.setBillStatus(status);
			entity.setBillStatusName("已驳回");
			String refus = INCIDENT + "&&&" + TASKID + "&&&" + VERSION + "&&&"
					+ TASKUSER+"&&&"+allNo;
			entity.setProcessReject(refus);

			this.saveOrUpdate(entity);
			break;
		case "Y":
			status = "EFFECTIVE";
			entity = this.getById(plmGroupBrandId);
			entity.setBillStatus(status);
			entity.setBillStatusName("已生效");
			this.saveOrUpdate(entity);
			break;
		case "APPROVING":
			status = "APPROVING";
			entity = this.getById(plmGroupBrandId);
			entity.setBillStatus(status);
			entity.setBillStatusName("审批中");
			entity.setProcessUser(TASKUSER);
			entity.setProcessIncident(INCIDENT);
			this.saveOrUpdate(entity);
			break;
		case "N":
			status = "INACTIVE";
			entity = this.getById(plmGroupBrandId);
			entity.setBillStatus(status);
			entity.setBillStatusName("已失效");
			this.saveOrUpdate(entity);
			break;
		case "RMSREJECT":
			status = "RMSREJECT";
			entity = this.getById(plmGroupBrandId);
			entity.setBillStatus(status);
			entity.setBillStatusName("RMS驳回");

			this.saveOrUpdate(entity);
			break;
		case "RMSCONFIRM":
			status = "RMSCONFIRM";
			entity = this.getById(plmGroupBrandId);
			entity.setBillStatus(status);
			entity.setBillStatusName("RMS待确认");
			this.saveOrUpdate(entity);
			break;
		case "TODO":
			status = "TODO";
			entity = this.getById(plmGroupBrandId);
			entity.setBillStatus(status);
			entity.setBillStatusName("制单中");
			this.saveOrUpdate(entity);
			break;
		default:
		}

		return entity;
	}

	@Override
	public PlmGroupBrandEntity_HI findByGroupBrandName(String groupBrandName) {
		return (PlmGroupBrandEntity_HI) hibernateTemplete.getSessionFactory().getCurrentSession()
				.createCriteria(PlmGroupBrandEntity_HI.class)
				.add(Restrictions.eq("plmGroupBrandName",groupBrandName))
				.setMaxResults(1).uniqueResult();
	}

}
