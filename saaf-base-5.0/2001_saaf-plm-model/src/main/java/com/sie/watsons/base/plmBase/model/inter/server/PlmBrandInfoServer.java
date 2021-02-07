package com.sie.watsons.base.plmBase.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.model.inter.server.GenerateCodeServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.plmBase.model.entities.PlmBrandInfoEntity_HI;
import com.sie.watsons.base.plmBase.model.entities.PlmGroupBrandEntity_HI;
import com.sie.watsons.base.plmBase.model.entities.PlmMotherCompanyEntity_HI;
import com.sie.watsons.base.plmBase.model.entities.readonly.PlmBrandInfoEntity_HI_RO;
import com.sie.watsons.base.plmBase.model.inter.IPlmBrandInfo;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("plmBrandInfoServer")
public class PlmBrandInfoServer extends BaseCommonServer<PlmBrandInfoEntity_HI>
		implements IPlmBrandInfo {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(PlmBrandInfoServer.class);
	@Autowired
	private ViewObject<PlmBrandInfoEntity_HI> plmBrandInfoDAO_HI;
	@Autowired
	private BaseViewObject<PlmBrandInfoEntity_HI_RO> plmBrandInfoDAO_HI_RO;
	@Autowired
	private GenerateCodeServer generateCodeServer;
	@Autowired
	private PlmMotherCompanyServer motherCompanyServer;
	@Autowired
	private PlmUserBrandMapServer plmUserBrandMapServer;

	@Autowired
	protected HibernateTemplate hibernateTemplete;

	public PlmBrandInfoServer() {
		super();
	}

	@Override
	public Pagination<PlmBrandInfoEntity_HI_RO> findPlmBrandInfoInfo(
			JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
		StringBuffer sql = new StringBuffer(PlmBrandInfoEntity_HI_RO.QUERY_SQL);
		Map<String, Object> paramsMap = new HashMap<>();
		if (queryParamJSON.containsKey("plmBrandCn")) {
			String plmDeptName = queryParamJSON.getString("plmBrandCn");
            plmDeptName = plmDeptName.replaceAll("'", "'||chr(39)||'").replaceAll("&", "'||chr(38)||'");
            sql.append(" and upper(pbi.PLM_BRAND_CN)  like '%"
					+ plmDeptName.toUpperCase() + "%' ");
			queryParamJSON.remove("plmBrandCn");
		}

		if (queryParamJSON.containsKey("plmBrandEn")) {
			String plmDeptName = queryParamJSON.getString("plmBrandEn");
            plmDeptName = plmDeptName.replaceAll("'", "'||chr(39)||'").replaceAll("&", "'||chr(38)||'");
            sql.append(" and upper(pbi.PLM_BRAND_EN)  like '%"
					+ plmDeptName.toUpperCase() + "%' ");
			queryParamJSON.remove("plmBrandEn");
		}
		if (queryParamJSON.containsKey("billStatus")) {
			String billStatus = queryParamJSON.getString("billStatus");
			sql.append(" and pbi.bill_Status ='" + billStatus + "' ");
			queryParamJSON.remove("billStatus");
		}
		if (queryParamJSON.containsKey("plmMotherCompany_like")) {
            String plmMotherCompany = queryParamJSON.getString("plmMotherCompany_like");
            plmMotherCompany = plmMotherCompany.replaceAll("'", "'||chr(39)||'").replaceAll("&", "'||chr(38)||'");
            sql.append(" and upper(pbi.plm_mother_company) like '%"
					+ plmMotherCompany.toUpperCase() + "%' ");
			queryParamJSON.remove("plmMotherCompany_like");

		}
		// plmGroupBrand_like

		if (queryParamJSON.containsKey("plmGroupBrand_like")) {
			sql.append(" and upper(pbi.plm_group_brand) like '%"
					+ queryParamJSON.getString("plmGroupBrand_like")
							.toUpperCase() + "%' ");
			queryParamJSON.remove("plmGroupBrand_like");

		}

		com.sie.saaf.common.util.SaafToolUtils.parperHbmParam(
				PlmBrandInfoEntity_HI.class, queryParamJSON, sql, paramsMap);
		if (!SaafToolUtils.isNullOrEmpty(queryParamJSON.getInteger("id"))) {
			sql.append(" and pbi.PLM_BRAND_INFO_ID = "
					+ queryParamJSON.getInteger("id"));
		}
		sql.append(" order by pbi.plm_brand_info_id desc ");
		Pagination<PlmBrandInfoEntity_HI_RO> pagination = plmBrandInfoDAO_HI_RO
				.findPagination(sql, SaafToolUtils.getSqlCountString(sql),
						paramsMap, pageIndex, pageRows);
		return pagination;
	}

	@Override
	public PlmBrandInfoEntity_HI savePlmBrandInfoInfo(JSONObject queryParamJSON)
			throws Exception {
		if (!SaafToolUtils.isNullOrEmpty(queryParamJSON.getJSONArray("data"))) {
			this.saveAndImportPlmBrandInfo(queryParamJSON);
			return new PlmBrandInfoEntity_HI();
		}
		PlmBrandInfoEntity_HI entity = JSON.parseObject(
				queryParamJSON.toString(), PlmBrandInfoEntity_HI.class);
		if (SaafToolUtils.isNullOrEmpty(entity.getPlmLocalBrandCode())) {
			// entity.setPlmLocalBrandCode(generateCodeServer.generateCode("B",
			// new SimpleDateFormat("yyyy"), 5));
			entity.setPlmLocalBrandCode(this.findPlmBrandInfocode()
					.getPlmLocalBrandCode());
		}
		if (SaafToolUtils.isNullOrEmpty(entity.getCreator())) {
			entity.setCreator(queryParamJSON.getString("varUserName"));
		}
		entity.setOperatorUserId(queryParamJSON.getInteger("varUserId"));
		plmBrandInfoDAO_HI.saveOrUpdate(entity);
		return entity;
	}

	public void saveAndImportPlmBrandInfo(JSONObject params) throws Exception {
		JSONArray dataArray = params.getJSONArray("data");
		// JSONObject infoObject = params.getJSONObject("info");
		JSONArray saveList = new JSONArray();
		JSONArray errArray = new JSONArray();
		for (int i = 0; i < dataArray.size(); i++) {
			JSONObject data = dataArray.getJSONObject(i);
			String errMsg = "";
			if (SaafToolUtils.isNullOrEmpty(data.getString("plmMotherCompany"))
					&& SaafToolUtils.isNullOrEmpty(data
							.getString("plmGroupBrand"))) {
				errMsg += "MotherCompany和Group二者必填其一！";
			}
			data.put("plmLocalBrandCode", generateCodeServer.generateCode("B",
					new SimpleDateFormat("yyyy"), 5));
			data.put("operatorUserId", (params.getInteger("varUserId")));
			data.put("billStatus", "Y");
			data.put("billStatusName", "已生效");
			data.put("creator", params.getString("varUserName"));
			if (!errMsg.equals("")) {
				JSONObject errObject = new JSONObject();
				errObject.put("ERR_MESSAGE", errMsg);
				errObject.put("ROW_NUM", data.get("ROW_NUM"));
				errArray.add(errObject);
			}
			saveList.add(data);
		}
		if (errArray.size() != 0) {
			throw new IllegalStateException(errArray.toJSONString());
		}
		List<PlmBrandInfoEntity_HI> entityList = saveList
				.toJavaList(PlmBrandInfoEntity_HI.class);
		plmBrandInfoDAO_HI.saveOrUpdateAll(entityList);
		return;
	}

	// 更改单据审批状态

	/**
	 * 更改单据审批状态
	 * 
	 * @param parseObject
	 * @return
	 * @throws Exception
	 */
	@Override
	public PlmBrandInfoEntity_HI saveConfirmedPlmBrandStatus(
			JSONObject parseObject) throws Exception {
		String plmBrandInfoCode = parseObject.getString("billNo");
		String INCIDENT = parseObject.getString("INCIDENT");
		String TASKID = parseObject.getString("TASKID");
		String VERSION = parseObject.getString("VERSION");
		String TASKUSER = parseObject.getString("TASKUSER");
		String allNo=parseObject.getString("allNo");
		JSONObject jt = new JSONObject();
		jt.put("plmLocalBrandCode", plmBrandInfoCode);
		Integer plmBrandInfoId = 0;
		List<PlmBrandInfoEntity_HI> librand = this.findList(jt);
		if (librand.size() > 0) {
			PlmBrandInfoEntity_HI brandinfo = librand.get(0);
			plmBrandInfoId = brandinfo.getPlmBrandInfoId();
		}
		PlmBrandInfoEntity_HI entity = new PlmBrandInfoEntity_HI();
		if (plmBrandInfoId != 0) {
			entity = this.getById(plmBrandInfoId);
			String status = null;

			boolean effectModify = false;
			if(null != entity && entity.getBillStatus().contains("EFFECTIVE_")) {
				effectModify = true;
			}

			switch (parseObject.getString("status")) {
			case "REJECT":
				status = "REJECT";
				entity.setBillStatus(status);
				entity.setBillStatusName("已驳回");
				String refus = INCIDENT + "&&&" + TASKID + "&&&" + VERSION
						+ "&&&" + TASKUSER+"&&&"+allNo;
				entity.setProcessReject(refus);
				if(effectModify) {
					entity.setBillStatus("EFFECTIVE");
					entity.setBillStatusName("已生效");
					plmUserBrandMapServer.updateStatusByBpm(plmBrandInfoId,0);
				}
				this.saveOrUpdate(entity);
				break;
			case "Y":
				status = "EFFECTIVE";
				entity = this.getById(plmBrandInfoId);
				entity.setBillStatus(status);
				entity.setBillStatusName("已生效");
				this.saveOrUpdate(entity);

				if(effectModify) {
					//相关userbrandMap 也生效
					plmUserBrandMapServer.updateStatusByBpm(plmBrandInfoId,10);
				}else {
					plmUserBrandMapServer.updateStatusByBrandInfoId(plmBrandInfoId,10);
				}
				break;
			case "APPROVING":
				status = "APPROVING";
				entity = this.getById(plmBrandInfoId);
				entity.setProcessUser(TASKUSER);
				entity.setProcessIncident(INCIDENT);
				if(!entity.getBillStatus().contains("EFFECTIVE_")) {
					entity.setBillStatus(status);
					entity.setBillStatusName("审批中");
				}
				this.saveOrUpdate(entity);
				break;
			case "N":
				status = "INACTIVE";
				entity = this.getById(plmBrandInfoId);
				entity.setBillStatus(status);
				entity.setBillStatusName("已失效");
				this.saveOrUpdate(entity);
				break;
			case "RMSREJECT":
				status = "RMSREJECT";
				entity = this.getById(plmBrandInfoId);
				entity.setBillStatus(status);
				entity.setBillStatusName("RMS驳回");

				this.saveOrUpdate(entity);
				break;
			case "RMSCONFIRM":
				status = "RMSCONFIRM";
				entity = this.getById(plmBrandInfoId);
				entity.setBillStatus(status);
				entity.setBillStatusName("RMS待确认");
				this.saveOrUpdate(entity);
				break;
			case "TODO":
				status = "TODO";
				entity = this.getById(plmBrandInfoId);
				if(!effectModify) {
					entity.setBillStatus(status);
					entity.setBillStatusName("制单中");
				}
				this.saveOrUpdate(entity);
				break;
			default:
			}
		}
		return entity;
	}

	@Override
	public PlmBrandInfoEntity_HI_RO findPlmBrandInfocode() {
		StringBuffer sql = new StringBuffer(
				PlmBrandInfoEntity_HI_RO.BrandcodeSql);
		Map<String, Object> map = new HashMap<String, Object>();
		List<PlmBrandInfoEntity_HI_RO> list = plmBrandInfoDAO_HI_RO.findList(
				sql, map);
		if (list.get(0) != null) {
			return list.get(0);
		}
		// this.
		return null;
	}

	@Transactional(rollbackFor = RuntimeException.class, propagation = Propagation.REQUIRES_NEW)
	@Override
	public PlmBrandInfoEntity_HI findBybrandOrCompany(String cname, String ename, Integer motherCompanyId,
													  Integer groupbrandId, Integer brandInfoId)
	{
        return this.findBybrandOrCompany(cname,ename,motherCompanyId,groupbrandId,brandInfoId,null);
	}

	@Transactional(rollbackFor = RuntimeException.class, propagation = Propagation.REQUIRES_NEW)
	@Override
    public PlmBrandInfoEntity_HI findBybrandOrCompany(String cname, String ename, Integer motherCompanyId,
                                               Integer groupbrandId, Integer brandInfoId, List<String> status)
    {
        Criteria criteria = hibernateTemplete.getSessionFactory().getCurrentSession()
                .createCriteria(PlmBrandInfoEntity_HI.class);
        criteria.add(Restrictions.eq("plmBrandCn",cname))
                .add(Restrictions.eq("plmBrandEn",ename));
        if(null != motherCompanyId) {
            criteria.add(Restrictions.eq("motherCompanyId",motherCompanyId));
        }
        if(null != groupbrandId) {
            criteria.add(Restrictions.eq("groupbrandId",groupbrandId));
        }
        if(null != brandInfoId) {
            criteria.add(Restrictions.ne("plmBrandInfoId",brandInfoId));
        }
        if(null != status && CollectionUtils.isNotEmpty(status)) {
            criteria.add(Restrictions.in("billStatus", status));
        }
        List<PlmBrandInfoEntity_HI> list = criteria.list();
        if(CollectionUtils.isNotEmpty(list)) {
            return list.get(0);
        }
        return null;
    }

	@Override
	public void initMotherCompanyIdForMC() {
		List<PlmBrandInfoEntity_HI> list = hibernateTemplete.getSessionFactory().getCurrentSession()
				.createCriteria(PlmBrandInfoEntity_HI.class).add(Restrictions.isNotNull("plmMotherCompany"))
				.add(Restrictions.isNull("motherCompanyId")).list();
		if(CollectionUtils.isNotEmpty(list)) {
//			List<PlmBrandInfoEntity_HI> infos = new ArrayList<PlmBrandInfoEntity_HI>();
			for(PlmBrandInfoEntity_HI info:list) {
				if (StringUtils.isNotBlank(info.getPlmMotherCompany())) {
					PlmMotherCompanyEntity_HI company = (PlmMotherCompanyEntity_HI) hibernateTemplete.getSessionFactory().getCurrentSession()
							.createCriteria(PlmMotherCompanyEntity_HI.class)
							.add(Restrictions.eq("plmMotherCompanyName",info.getPlmMotherCompany()))
							.setMaxResults(1).uniqueResult();
					if(null != company) {
						String hql = "UPDATE PlmBrandInfoEntity_HI SET motherCompanyId = ? WHERE plmBrandInfoId=?";
						Query query = hibernateTemplete.getSessionFactory().getCurrentSession().createQuery(hql);
						query.setInteger(0,company.getPlmMotherCompanyId());
						query.setInteger(1,info.getPlmBrandInfoId());
						query.executeUpdate();
					}
				}
			}
		}
	}

	@Override
	public void initGroupBrandId() {

		List<PlmBrandInfoEntity_HI> groupBrands = hibernateTemplete.getSessionFactory().getCurrentSession()
				.createCriteria(PlmBrandInfoEntity_HI.class).add(Restrictions.isNotNull("plmGroupBrand"))
				.add(Restrictions.isNull("groupbrandId")).list();
		if(CollectionUtils.isNotEmpty(groupBrands)) {
			List<PlmBrandInfoEntity_HI> infos = new ArrayList<PlmBrandInfoEntity_HI>();
			for(PlmBrandInfoEntity_HI info:groupBrands) {
				if (StringUtils.isNotBlank(info.getPlmGroupBrand())) {
					PlmGroupBrandEntity_HI brand = (PlmGroupBrandEntity_HI) hibernateTemplete.getSessionFactory().getCurrentSession()
							.createCriteria(PlmGroupBrandEntity_HI.class)
							.add(Restrictions.eq("plmGroupBrandName",info.getPlmGroupBrand()))
							.setMaxResults(1).uniqueResult();
					if(null != brand) {
						info.setGroupbrandId(brand.getPlmGroupBrandId());
						infos.add(info);
						//this.saveOrUpdate(info);
					}
				}
			}
			if(CollectionUtils.isNotEmpty(infos)) {
				this.save(infos);
			}
		}
	}

	@Override
	public PlmBrandInfoEntity_HI findBybrand(String cname, String ename,
											 String motherCompany, String groupbrand, Integer brandInfoId) {

		Criteria criteria = hibernateTemplete.getSessionFactory().getCurrentSession()
				.createCriteria(PlmBrandInfoEntity_HI.class);
		criteria.add(Restrictions.eq("plmBrandCn",cname))
				.add(Restrictions.eq("plmBrandEn",ename));
		if(null != motherCompany) {
			criteria.add(Restrictions.eq("plmMotherCompany",motherCompany));
		}
		if(null != groupbrand) {
			criteria.add(Restrictions.eq("plmGroupBrand",groupbrand));
		}
		if(null != brandInfoId) {
			criteria.add(Restrictions.ne("plmBrandInfoId",brandInfoId));
		}
		List<PlmBrandInfoEntity_HI> list = criteria.list();
		if(CollectionUtils.isNotEmpty(list)) {
			return list.get(0);
		}
		return null;
	}

	@Transactional(rollbackFor = RuntimeException.class, propagation = Propagation.REQUIRES_NEW)
	@Override
	public List<PlmBrandInfoEntity_HI> findList(JSONObject queryParamJSON) {
		return super.findList(queryParamJSON);
	}
}
