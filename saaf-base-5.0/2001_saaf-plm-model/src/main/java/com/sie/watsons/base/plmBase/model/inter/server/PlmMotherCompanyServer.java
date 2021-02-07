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
import com.sie.watsons.base.plmBase.model.dao.PlmMotherCompanyDAO_HI;
import com.sie.watsons.base.plmBase.model.entities.PlmMotherCompanyEntity_HI;
import com.sie.watsons.base.plmBase.model.entities.readonly.PlmMotherCompanyEntity_HI_RO;
import com.sie.watsons.base.plmBase.model.inter.IPlmMotherCompany;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;

@Component("plmMotherCompanyServer")
public class PlmMotherCompanyServer extends
		BaseCommonServer<PlmMotherCompanyEntity_HI> implements
		IPlmMotherCompany {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(PlmMotherCompanyServer.class);
	@Autowired
	private ViewObject<PlmMotherCompanyEntity_HI> plmMotherCompanyDAO_HI;
	@Autowired
	private BaseViewObject<PlmMotherCompanyEntity_HI_RO> plmMotherCompanyDAO_HI_RO;

	@Autowired
	private PlmMotherCompanyDAO_HI plmMotherCompany;

	@Autowired
	protected HibernateTemplate hibernateTemplete;

	public PlmMotherCompanyServer() {
		super();
	}

	@Override
	public Pagination<PlmMotherCompanyEntity_HI_RO> findPlmMotherCompanyInfo(
			JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
		StringBuffer sql = new StringBuffer(
				PlmMotherCompanyEntity_HI_RO.QUERY_SQL);
		Map<String, Object> paramsMap = new HashMap<>();
		if (queryParamJSON.containsKey("plmMotherCompanyName_like")) {
			sql.append(" and upper(pmc.PLM_MOTHER_COMPANY_NAME) like '%"
					+ queryParamJSON.getString("plmMotherCompanyName_like")
							.toUpperCase() + "%' ");
			queryParamJSON.remove("plmMotherCompanyName_like");

		}

		com.sie.saaf.common.util.SaafToolUtils.parperHbmParam(
				PlmMotherCompanyEntity_HI_RO.class, queryParamJSON, sql,
				paramsMap);
		sql.append(" order by pmc.LAST_UPDATE_DATE desc");
		Pagination<PlmMotherCompanyEntity_HI_RO> pagination = plmMotherCompanyDAO_HI_RO
				.findPagination(sql, paramsMap, pageIndex, pageRows);
		return pagination;
	}

	@Override
	public PlmMotherCompanyEntity_HI savePlmMotherCompanyInfo(
			JSONObject queryParamJSON) {
		PlmMotherCompanyEntity_HI entity = JSON.parseObject(
				queryParamJSON.toString(), PlmMotherCompanyEntity_HI.class);
		entity.setOperatorUserId(queryParamJSON.getInteger("varUserId"));
		if (SaafToolUtils.isNullOrEmpty(entity.getCreator())) {
			entity.setCreator(queryParamJSON.getString("varUserName"));
		}
		// plmMotherCompanyId

		plmMotherCompanyDAO_HI.saveOrUpdate(entity);
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
	public PlmMotherCompanyEntity_HI saveConfirmedPlmMotherCompanyStatus(
			JSONObject parseObject) throws Exception {
		Integer plmMotherCompanyId = parseObject.getIntValue("billNo");
		String INCIDENT = parseObject.getString("INCIDENT");
		String TASKID = parseObject.getString("TASKID");
		String VERSION = parseObject.getString("VERSION");
		String TASKUSER = parseObject.getString("TASKUSER");
		String allNo=parseObject.getString("allNo");

		String status = null;
		PlmMotherCompanyEntity_HI entity = new PlmMotherCompanyEntity_HI();
		switch (parseObject.getString("status")) {
		case "REJECT":
			status = "REJECT";
			entity = this.getById(plmMotherCompanyId);
			entity.setBillStatus(status);
			entity.setBillStatusName("已驳回");
			String refus = INCIDENT + "&&&" + TASKID + "&&&" + VERSION + "&&&"
					+ TASKUSER+"&&&"+allNo;
			entity.setProcessReject(refus);

			this.saveOrUpdate(entity);
			break;
		case "Y":
			status = "EFFECTIVE";
			entity = this.getById(plmMotherCompanyId);
			entity.setBillStatus(status);
			entity.setBillStatusName("已生效");
			this.saveOrUpdate(entity);
			break;
		case "APPROVING":
			status = "APPROVING";
			entity = this.getById(plmMotherCompanyId);
			entity.setBillStatus(status);
			entity.setBillStatusName("审批中");
			entity.setProcessUser(TASKUSER);
			entity.setProcessIncident(INCIDENT);
			this.saveOrUpdate(entity);
			break;
		case "N":
			status = "INACTIVE";
			entity = this.getById(plmMotherCompanyId);
			entity.setBillStatus(status);
			entity.setBillStatusName("已失效");
			this.saveOrUpdate(entity);
			break;
		case "RMSREJECT":
			status = "RMSREJECT";
			entity = this.getById(plmMotherCompanyId);
			entity.setBillStatus(status);
			entity.setBillStatusName("RMS驳回");

			this.saveOrUpdate(entity);
			break;
		case "RMSCONFIRM":
			status = "RMSCONFIRM";
			entity = this.getById(plmMotherCompanyId);
			entity.setBillStatus(status);
			entity.setBillStatusName("RMS待确认");
			this.saveOrUpdate(entity);
			break;
		case "TODO":
			status = "TODO";
			entity = this.getById(plmMotherCompanyId);
			entity.setBillStatus(status);
			entity.setBillStatusName("制单中");
			this.saveOrUpdate(entity);
			break;
		default:
		}

		return entity;
	}

	@Override
	public PlmMotherCompanyEntity_HI findByCompanyName(String companyName) {
		return (PlmMotherCompanyEntity_HI) hibernateTemplete.getSessionFactory().getCurrentSession()
				.createCriteria(PlmMotherCompanyEntity_HI.class)
				.add(Restrictions.eq("plmMotherCompanyName",companyName))
				.setMaxResults(1).uniqueResult();
	}

	@Override
	public String syncMotherCompany() throws Exception {
		Map<String, Object> map = plmMotherCompany.callPkgSyncMotherCompany();

		return map.toString();
	}
}
