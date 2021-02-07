package com.sie.watsons.base.plmBase.model.inter.server;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.user.model.entities.BaseUsersEntity_HI;
import com.sie.saaf.base.user.model.entities.readonly.BaseUsersEntity_HI_RO;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.plmBase.model.entities.PlmBrandInfoEntity_HI;
import com.sie.watsons.base.plmBase.model.entities.PlmGroupBrandEntity_HI;
import com.sie.watsons.base.plmBase.model.entities.PlmMotherCompanyEntity_HI;
import com.sie.watsons.base.plmBase.model.entities.PlmUserBrandMapEntity_HI;
import com.sie.watsons.base.plmBase.model.entities.readonly.PlmUserBrandMapEntity_HI_RO;
import com.sie.watsons.base.plmBase.model.inter.IPlmGroupBrand;
import com.sie.watsons.base.plmBase.model.inter.IPlmMotherCompany;
import com.sie.watsons.base.plmBase.model.inter.IPlmUserBrandMap;
import com.sie.watsons.base.product.model.dao.PlmProductHeadDAO_HI;
import com.sie.watsons.base.product.model.entities.readonly.PlmProductHeadEntity_HI_RO;
import com.sie.watsons.base.supplier.model.inter.server.PlmSupplierUserBrandAclServer;
import com.yhg.base.utils.DateUtil;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Component("plmUserBrandMapServer")
public class PlmUserBrandMapServer extends BaseCommonServer<PlmUserBrandMapEntity_HI> implements IPlmUserBrandMap {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmUserBrandMapServer.class);

	@Autowired
	protected HibernateTemplate hibernateTemplete;
	@Autowired
	private BaseViewObject<PlmUserBrandMapEntity_HI_RO> plmUserBrandMapDAO_HI_RO;
	@Autowired
	private BaseViewObject<PlmProductHeadEntity_HI_RO> plmProductHeadDAO_HI_RO;
	@Autowired
	private PlmSupplierUserBrandAclServer plmSupplierUserBrandAclServer;
	@Autowired
	private PlmBrandInfoServer plmBrandInfoServer;
	@Autowired
	private IPlmUserBrandMap plmUserBrandMapServer;
	@Autowired
	private IPlmMotherCompany motherCompanyServer;
	@Autowired
	private IPlmGroupBrand groupBrandServer;
	@Autowired
	ThreadPoolTaskExecutor taskExecutor;

	@Resource
	private RestTemplate restTemplate;

	public static final String BASE_SERVER = "http://1006-SAAF-BASE-SERVER";

	public PlmUserBrandMapServer() {
		super();

	}

	@Override
	public List<PlmUserBrandMapEntity_HI> getUserBrandMaps(Integer brandMapId, Integer brandInfoId) {

		return getUserBrandMaps(brandMapId, brandInfoId, null, null, null, null, null);
	}

	@Transactional(rollbackFor = RuntimeException.class, propagation = Propagation.REQUIRES_NEW)
	@Override
	public List<PlmUserBrandMapEntity_HI> getUserBrandMaps(Integer brandMapId, Integer brandInfoId, Integer supUserId,
														   Integer motherCompanyId, Integer groupBrandId,
														   List<Integer> status, Integer deleteFlag) {
		Criteria query = hibernateTemplete.getSessionFactory().getCurrentSession()
				.createCriteria(PlmUserBrandMapEntity_HI.class);
		if (null != brandMapId) {
			query.add(Restrictions.eq("brandMapId",brandMapId));
		}
		if(null != brandInfoId) {
			query.add(Restrictions.eq("brandInfoId",brandInfoId));
		}
		if(null != supUserId) {
			query.add(Restrictions.eq("supUserId",supUserId));
		}
		if(null != motherCompanyId) {
			query.add(Restrictions.eq("motherCompanyId",motherCompanyId));
		}
		if(null != groupBrandId) {
			query.add(Restrictions.eq("groupBrandId",groupBrandId));
		}
		if(null != status) {
			query.add(Restrictions.in("status",status));
		}
		if(null != deleteFlag) {
			query.add(Restrictions.eq("deleteFlag",deleteFlag));
		}else {
			query.add(Restrictions.ne("deleteFlag",1));
		}

		return query.list();
	}

	@Override
	public void deleteByBrandMapId(Integer brandMapId) {
		String hql = "UPDATE PlmUserBrandMapEntity_HI ubm SET ubm.deleteFlag = ? WHERE ubm.brandMapId = ?";
		Query query = hibernateTemplete.getSessionFactory().getCurrentSession()
				.createQuery(hql);
		query.setInteger(0,1);
		query.setInteger(1,brandMapId);

		query.executeUpdate();
	}

	@Override
	public void deleteByBrandInfoId(Integer brandInfoId) {
		String hql = "UPDATE PlmUserBrandMapEntity_HI ubm SET ubm.deleteFlag = ? WHERE ubm.brandInfoId = ?";
		Query query = hibernateTemplete.getSessionFactory().getCurrentSession()
				.createQuery(hql);
		query.setInteger(0,1);
		query.setInteger(1,brandInfoId);

		query.executeUpdate();
	}

	@Override
	public void updateStatusByBrandInfoId(Integer brandInfoId, Integer status) {
		String hql = "UPDATE PlmUserBrandMapEntity_HI ubm SET ubm.status = ? WHERE ubm.brandInfoId = ? AND ubm.deleteFlag != 1 ";
		Query query = hibernateTemplete.getSessionFactory().getCurrentSession()
				.createQuery(hql);
		query.setInteger(0,status);
		query.setInteger(1,brandInfoId);

		query.executeUpdate();
	}

	@Override
	public void updateStatusByBpm(Integer brandInfoId, Integer status) {

		String hql = "UPDATE PlmUserBrandMapEntity_HI ubm SET ubm.status = ? WHERE ubm.brandInfoId = ? AND " +
				"ubm.status=? AND ubm.deleteFlag != 1 ";

		String delete_hql = "UPDATE PlmUserBrandMapEntity_HI ubm SET ubm.deleteFlag = ? WHERE ubm.brandInfoId = ? AND " +
				"ubm.status=? AND ubm.deleteFlag != 1 ";

		if(status == 0) {//拒绝
			Query query = hibernateTemplete.getSessionFactory().getCurrentSession()
					.createQuery(hql);
			query.setInteger(0,10);//将待审核为删除状态变更为生效状态
			query.setInteger(1,brandInfoId);
			query.setInteger(2,11);
			query.executeUpdate();

			Query delete_query = hibernateTemplete.getSessionFactory().getCurrentSession()
					.createQuery(delete_hql);
			delete_query.setInteger(0,1);//将待审核为新增状态，假删除
			delete_query.setInteger(1,brandInfoId);
			delete_query.setInteger(2,5);
			delete_query.executeUpdate();
		}else if(status == 10) {//通过
			Query query = hibernateTemplete.getSessionFactory().getCurrentSession()
					.createQuery(hql);
			query.setInteger(0,10);//将待审核为新增状态，变为生效
			query.setInteger(1,brandInfoId);
			query.setInteger(2,5);
			query.executeUpdate();

			Query delQuery = hibernateTemplete.getSessionFactory().getCurrentSession()
					.createQuery(delete_hql);
			delQuery.setInteger(0,1);//将待审核为删除状态变更为假删除
			delQuery.setInteger(1,brandInfoId);
			delQuery.setInteger(2,5);
		}

	}

	@Override
	public Pagination<PlmUserBrandMapEntity_HI_RO> findBaseUsersPage(
			JSONObject param, Integer pageIndex, Integer pageRows,String certificate)
	{
		StringBuffer querySql = new StringBuffer(PlmUserBrandMapEntity_HI_RO.QUERY_USER_BRAND_MAP_SQL);
		if(null != param) {
			if(param.containsKey("userName") && StringUtils.isNotBlank(param.getString("userName").trim())){
				String userNames = param.getString("userName").trim();
				userNames = userNames.replace(",","','");
				userNames = "'"+userNames+"'";
				querySql.append(" AND map.USER_NAME IN (");
				querySql.append(userNames);
				querySql.append(") ");
			}
			if(param.containsKey("profileCode") && StringUtils.isNotBlank(param.getString("profileCode").trim())){//通过profile找user相关的id
				HttpHeaders headers = new HttpHeaders();
				headers.add("Certificate",certificate);
				headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
				HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
				ResponseEntity<String> result = restTemplate.exchange(BASE_SERVER+"/baseProfileValueService/getUserIds/"
						+param.getString("profileCode").trim(), HttpMethod.GET, entity, String.class);

				String userIds = result.getBody();

				if(StringUtils.isNotBlank(userIds)) {
					querySql.append(" AND map.supUserId IN (");
					querySql.append(userIds.trim());
					querySql.append(") ");
				}
			}
			if(param.containsKey("cnbrand") && StringUtils.isNotBlank(param.getString("cnbrand").trim())){
				querySql.append(" AND map.BRAND_CN LIKE '%");
				querySql.append(param.getString("cnbrand").trim());
				querySql.append("%' ");
			}
			if(param.containsKey("enbrand") && StringUtils.isNotBlank(param.getString("enbrand").trim())){
				querySql.append(" AND map.BRAND_EN LIKE '%");
				querySql.append(param.getString("enbrand").trim());
				querySql.append("%' ");
			}
			if(param.containsKey("userEmail") && StringUtils.isNotBlank(param.getString("userEmail").trim())){
				querySql.append(" AND map.USER_EMAIL LIKE '%");
				querySql.append(param.getString("userEmail").trim());
				querySql.append("%' ");
			}
		}
		Pagination<PlmUserBrandMapEntity_HI_RO> pagination = plmUserBrandMapDAO_HI_RO
				.findPagination(querySql.toString(), SaafToolUtils.getSqlCountString(querySql), pageIndex, pageRows);

		return pagination;
	}

	@Transactional(rollbackFor = RuntimeException.class, propagation = Propagation.REQUIRED)
	@Override
	public void syncToUserBrandMaps(Date startDate, Date endDate) {

		String start = null;
		String end = null;

		if(null != startDate && null != endDate) {
			start = DateUtil.date2Str(startDate,DateUtil.FORMAT_YMD);
			end = DateUtil.date2Str(endDate,DateUtil.FORMAT_YMD);
		}
		//获取数据源
		List<Map<String, Object>> productInfos = plmSupplierUserBrandAclServer
				.findProductHeadByPrivilegeLine(null,start,end);

		AtomicInteger successCount = new AtomicInteger();
		AtomicInteger aclSuccessCount = new AtomicInteger();

		// 1 循环插入中间表
		productInfos.forEach(productInfo ->
				// 多线程操作
				taskExecutor.execute(() -> {
					int i = 0;
					try {
						String brandCn = (String) productInfo.getOrDefault("brandCn",null);
						String brandEn = (String) productInfo.getOrDefault("brandEn",null);
						Integer accountId = null;
						Integer motherCompany = null;
						Integer groupBrand = null;
//						String motherCompany = (String) productInfo.getOrDefault("motherCompany",null);
//						String groupBrand = (String) productInfo.getOrDefault("groupBrand",null);
						if(productInfo.containsKey("accountId") && null !=productInfo.get("accountId")) {
							accountId = Integer.valueOf(productInfo.get("accountId").toString());

						}
						if(productInfo.containsKey("groupBrand") && null !=productInfo.get("groupBrand")) {
							groupBrand = Integer.valueOf(productInfo.get("groupBrand").toString());

						}
						if(productInfo.containsKey("motherCompany") && null !=productInfo.get("motherCompany")) {
							motherCompany = Integer.valueOf(productInfo.get("motherCompany").toString());

						}

						//插入相关的userbrandmap表（如果符合条件）
						if(null != motherCompany || null != groupBrand) {
							i = plmUserBrandMapServer.insertUserBrandMap(motherCompany, groupBrand, brandCn, brandEn,
									accountId);
						}
					} catch (Exception e) {
						LOGGER.error("写入userBrandMap数据失败", e);
					}
					successCount.addAndGet(i);
				})
		);
		LOGGER.info("成功写入userBrandMap表{}条数据", successCount.get());
	}

	@Transactional(rollbackFor = RuntimeException.class, propagation = Propagation.REQUIRES_NEW)
	@Override
	public int insertUserBrandMap(Integer motherCompanyId, Integer groupBrandId, String brandCn, String brandEn,
								   Integer accountId) {

		int i = 0;
		try{
			PlmMotherCompanyEntity_HI mc = null;
			PlmGroupBrandEntity_HI gb = null;
			if(null != motherCompanyId){
				mc = motherCompanyServer.getById(motherCompanyId);
			}
			if(null != groupBrandId){
				gb = groupBrandServer.getById(groupBrandId);
			}

			if(null == mc && null == gb) {
				return i;
			}

			//查询BrandInfo是否存在
			PlmBrandInfoEntity_HI brandInfo = plmBrandInfoServer.findBybrandOrCompany(brandCn, brandEn,
					null!=mc?mc.getPlmMotherCompanyId():null,null != gb?gb.getPlmGroupBrandId():null, null);

			if(null != brandInfo) {
				//品牌信息存在,查询对应关系
				List<PlmUserBrandMapEntity_HI> userBrandMaps = plmUserBrandMapServer.getUserBrandMaps(null,brandInfo.getPlmBrandInfoId(),
						accountId,null!=mc?mc.getPlmMotherCompanyId():null,null != gb?gb.getPlmGroupBrandId():null,
						null, 0);

				if(CollectionUtils.isNotEmpty(userBrandMaps)) {
					for(PlmUserBrandMapEntity_HI userbrandMap : userBrandMaps) {
						userbrandMap.setMasterright(1);
						if(brandInfo.getBillStatus().contains("EFFECT")) {
							userbrandMap.setStatus(10);
						}
						plmUserBrandMapServer.saveOrUpdate(userbrandMap);
						i++;
					}

				}else {//存在对应关系，但不存在userbrandmap数据
					PlmUserBrandMapEntity_HI ubm = new PlmUserBrandMapEntity_HI();
					ubm.setMasterright(1);
					if(null != mc) {
						ubm.setMotherCompanyId(mc.getPlmMotherCompanyId());
						ubm.setMotherCompanyName(mc.getPlmMotherCompanyName());
					}
					if(null != gb) {
						ubm.setGroupBrandId(gb.getPlmGroupBrandId());
						ubm.setGroupBrandName(gb.getPlmGroupBrandName());
					}
					ubm.setBrandInfoId(brandInfo.getPlmBrandInfoId());
					ubm.setBrandCn(brandCn);
					ubm.setBrandEn(brandEn);
					ubm.setBrandCnUdaValue(brandInfo.getBrandCnUdaValue());
					ubm.setBrandCnUdaId(brandInfo.getBrandCnUdaId());
					ubm.setBrandEnUdaValue(brandInfo.getBrandEnUdaValue());
					ubm.setBrandEnUdaId(brandInfo.getBrandEnUdaId());
					HttpHeaders headers = new HttpHeaders();

					headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
					HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
					ResponseEntity<BaseUsersEntity_HI> result = restTemplate.exchange(BASE_SERVER+"/baseUsersService/getByUserId/"
							+accountId, HttpMethod.GET, entity, BaseUsersEntity_HI.class);

					BaseUsersEntity_HI baseUser = result.getBody();

					ubm.setMotherCompanyId(brandInfo.getMotherCompanyId());
					ubm.setSupUserId(accountId);
					ubm.setUserName(null != baseUser?baseUser.getUserName():"");
					ubm.setUserEmail(null != baseUser?baseUser.getEmailAddress():"");
					if(brandInfo.getBillStatus().contains("EFFECT")) {
						ubm.setStatus(10);
					}else {
						ubm.setStatus(0);
					}

					plmUserBrandMapServer.save(ubm);
					i++;
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
			LOGGER.warn("=================同步商品供应商权限========失败====参数motherCompany:"+motherCompanyId+",groupBrand:" +
					groupBrandId+",brandCn,"+brandCn+",brandEn:"+brandEn+",accountId:"+accountId);
		}

		return i;
	}
}
