package com.sie.watsons.base.proposal.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

import com.sie.saaf.base.commmon.model.inter.IBaseRequestLog;
import com.sie.saaf.base.dict.model.entities.BaseLookupValuesEntity_HI;
import com.sie.saaf.base.user.model.entities.BaseUsersEntity_HI;
import com.sie.saaf.base.user.model.inter.IBaseUsers;
import com.sie.saaf.common.bean.ResponsibilityBean;
import com.sie.saaf.common.bean.UserSessionBean;
import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.saaf.common.services.GenerateCodeService;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.proposal.model.dao.TtaProposalHeaderDAO_HI;
import com.sie.watsons.base.proposal.model.entities.readonly.TtaDeptFeeHeaderEntity_HI_RO;
import com.sie.watsons.base.proposal.model.entities.readonly.TtaProposalHeaderEntity_HI_RO;
import com.sie.watsons.base.proposal.model.entities.TtaProposalHeaderEntity_HI;
import com.sie.watsons.base.proposal.model.entities.readonly.TtaProposalTermsHeaderEntity_HI_RO;
import com.sie.watsons.base.proposal.model.entities.readonly.TtaTermsHEntity_HI_RO;
import com.sie.watsons.base.supplier.model.entities.readonly.TtaSupplierEntity_HI_RO;
import com.sie.watsons.base.supplier.model.inter.ITtaSupplier;
import com.yhg.hibernate.core.HibernateHandler;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.QueryParameterException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.proposal.model.inter.ITtaProposalHeader;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import org.springframework.transaction.NoTransactionException;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.Assert;
import redis.clients.jedis.JedisCluster;

import javax.naming.ldap.PagedResultsControl;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component("ttaProposalHeaderServer")
public class TtaProposalHeaderServer extends BaseCommonServer<TtaProposalHeaderEntity_HI> implements ITtaProposalHeader{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaProposalHeaderServer.class);

	@Autowired
	private JedisCluster jedisCluster;

	@Autowired
	private ViewObject<TtaProposalHeaderEntity_HI> ttaProposalHeaderDAO_HI;

	@Autowired
	private TtaProposalHeaderDAO_HI ttaProposalHeaderDAO;

	@Autowired
	private GenerateCodeService codeService;

	@Autowired
	private BaseViewObject<TtaProposalHeaderEntity_HI_RO> ttaProposalHeaderDAO_HI_RO;
	
	@Autowired
	private BaseViewObject<TtaTermsHEntity_HI_RO> ttaTermsHDAO_HI_RO;

	public TtaProposalHeaderServer() {
		super();
	}

	@Autowired
	private BaseCommonDAO_HI<BaseLookupValuesEntity_HI> baseLookupValuesDAO_HI;

	@Autowired
	BaseViewObject<JSONObject> commonDAO_HI_DY ;

	@Autowired
	private BaseCommonDAO_HI<TtaProposalHeaderEntity_HI> ttaProposalHeaderEntityHs;

	@Autowired
	private IBaseUsers baseUsersServer;

	@Autowired
	private ITtaSupplier ttaSupplierServer;

	@Autowired
	private IBaseRequestLog baseRequestLog;


    //当年proposal根据ID取值，确认条件都确认了就行
    @Override
    public List<TtaProposalHeaderEntity_HI> findByTerm(String proposalId,String year) {
        String sql = "from TtaProposalHeaderEntity_HI h where " +
                "h.isTermsConfirm='Y' and "  +
                "h.isPlnConfirm='Y' and "  +
                "h.isDepartConfirm='Y' and "  +
                "h.isNewConfirm='Y' and "  +
                "h.proposalYear='"+year+"' and "  +
//                "h.vendorNbr = '"+vendorCode+"'";
                "h.proposalId = "+proposalId+"";

        List<TtaProposalHeaderEntity_HI> findList = ttaProposalHeaderDAO_HI.findList(sql);
        return findList;
    }

    //18年旧数据只要STATUS审核状态通过就行，19年的STATUS也包含制作中，需要要其他状态确认为Y就行
    @Override
    public List<TtaProposalHeaderEntity_HI> findByTermOld(String year,String vendorCode) {
        String sql = "from TtaProposalHeaderEntity_HI h where " +
                "h.status='C' and "  +
                "h.proposalYear='"+year+"' and "  +
                "h.vendorNbr = '"+vendorCode+"'";

        List<TtaProposalHeaderEntity_HI> findList = ttaProposalHeaderDAO_HI.findList(sql);
        return findList;
    }

	@Override
	public List<TtaProposalHeaderEntity_HI> findLastProposal(Integer lastProposalId) {
		String sql = "from TtaProposalHeaderEntity_HI h where " +
				" h.proposalId =" + lastProposalId;

		List<TtaProposalHeaderEntity_HI> findList = ttaProposalHeaderDAO_HI.findList(sql);
		return findList;
	}

	@Override
	public Pagination<TtaProposalHeaderEntity_HI_RO> find(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows, UserSessionBean userSessionBean) {
		StringBuffer sql = new StringBuffer();
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		if("LAST_YEAR_ORDER_NO".equals(queryParamJSON.getString("flag"))){
			SaafToolUtils.validateJsonParms(queryParamJSON,"oldProposalYear","oldVendorNbr");
			sql.append(TtaProposalHeaderEntity_HI_RO.QUERY_LAST_YEAR_ORDER_NO);
			paramsMap.put("proposalYear",queryParamJSON.getString("oldProposalYear")) ;
			paramsMap.put("vendorNbr",queryParamJSON.getString("oldVendorNbr")) ;
		}else{
			sql.append(TtaProposalHeaderEntity_HI_RO.TTA_ITEM_V);
			String vSql = TtaDeptFeeHeaderEntity_HI_RO.getTableDeptSql(queryParamJSON.getInteger("varUserId"),queryParamJSON.getString("varUserType")) ;
			if(!SaafToolUtils.isNullOrEmpty(vSql)){

				//sql.append(" and exists (select 1 from").append(vSql).append(" where v.MAJOR_DEPT_CODE = dept.department_code and v.user_group_name = dept.group_name) ");
				sql.append(" and exists (select 1 from").append(vSql).append(" where v.MAJOR_DEPT_CODE = dept.department_code and v.user_group_code = dept.group_code) ");
			}
		}
		/*if (!"45".equals(userSessionBean.getUserType()) || !"Y".equals(userSessionBean.getIsadmin())) {
			//BIC权限或者管理员可看生成的虚拟Proposal,采购等权限下不能查看虚拟的Proposal单
			sql.append(" and v.is_split <> 'Y' ");
		}*/
		//当前用户职责
		List<ResponsibilityBean> userRespList = userSessionBean.getUserRespList();
		boolean respFlag = false;
		for (ResponsibilityBean responsibilityBean : userRespList) {
			String responsibilityCode = responsibilityBean.getResponsibilityCode();
			//BIC权限或者管理员可看生成的虚拟Proposal,采购等权限下不能查看虚拟的Proposal单
			if ("BICZZ".equals(responsibilityCode) || "Y".equals(userSessionBean.getIsadmin())) {
				respFlag = true;
			}
		}
		if (!respFlag) {
			sql.append(" and v.is_split <> 'Y' ");
		}

		SaafToolUtils.parperParam(queryParamJSON, "v.proposal_Id", "proposalId", sql, paramsMap, "=");
		SaafToolUtils.parperParam(queryParamJSON, "v.PROPOSAL_YEAR", "proposalYear", sql, paramsMap, "=");
		SaafToolUtils.parperParam(queryParamJSON, "v.status", "status", sql, paramsMap, "in");
		SaafToolUtils.parperParam(queryParamJSON, "v.order_Nbr", "orderNbr", sql, paramsMap, "like");
		SaafToolUtils.parperParam(queryParamJSON, "v.is_change", "isChange", sql, paramsMap, "=");
		SaafToolUtils.parperParam(queryParamJSON, "to_char(v.creation_Date,'YYYY-MM-DD')", "creationDate1", sql, paramsMap, ">=");
		SaafToolUtils.parperParam(queryParamJSON, "to_char(v.creation_Date,'YYYY-MM-DD')", "creationDate2", sql, paramsMap, "<=");

		SaafToolUtils.parperParam(queryParamJSON, "v.vendor_Nbr", "vendorNbr", sql, paramsMap, "like");
		SaafToolUtils.parperParam(queryParamJSON, "v.vendor_Name", "vendorName", sql, paramsMap, "like");

		SaafToolUtils.parperParam(queryParamJSON, "v.version_Code", "versionCode", sql, paramsMap, "=");
		SaafToolUtils.parperParam(queryParamJSON, "v.created_name", "createdName", sql, paramsMap, "like");
		if ("contract".equalsIgnoreCase(queryParamJSON.getString("resource")) || "LAST_YEAR_ORDER_NO".equals(queryParamJSON.getString("flag"))) {
			SaafToolUtils.changeQuerySort(queryParamJSON, sql, "v.proposal_year desc", false);
		} else {
			SaafToolUtils.changeQuerySort(queryParamJSON, sql, "v.creation_date desc", false);
		}

		Pagination<TtaProposalHeaderEntity_HI_RO> findList = ttaProposalHeaderDAO_HI_RO.findPagination(sql, SaafToolUtils.getSqlCountString(sql), paramsMap, pageIndex, pageRows);
		return findList;

	}

	@Override
	public Pagination<TtaProposalHeaderEntity_HI_RO> findVendor(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
		StringBuffer sql = new StringBuffer();
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		sql.append(TtaProposalHeaderEntity_HI_RO.getTtaVendorCode(queryParamJSON.getString("proposalYear")));
		//SaafToolUtils.parperParam(queryParamJSON, "tphh.proposal_year", "proposalYear", sql, paramsMap, "like");
		SaafToolUtils.parperParam(queryParamJSON, "tphh.vendor_Name", "vendorName", sql, paramsMap, "like");
		SaafToolUtils.parperParam(queryParamJSON, "tphh.vendor_code", "vendorCode", sql, paramsMap, "like");
		SaafToolUtils.changeQuerySort(queryParamJSON, sql, "tphh.proposal_id desc", false);

		Pagination<TtaProposalHeaderEntity_HI_RO> findList = ttaProposalHeaderDAO_HI_RO.findPagination(sql, SaafToolUtils.getSqlCountString(sql), paramsMap, pageIndex, pageRows);
		return findList;

	}

	@Override
	public TtaProposalHeaderEntity_HI saveOrUpdate(JSONObject paramsJSON, int userId,HttpServletRequest request,HttpServletResponse response) throws Exception {
    	// 5. 如果去年供应商为空, 则 去寻找去年供应商
		//	if(SaafToolUtils.isNullOrEmpty(instance.getFeedeptId())){
		String proposalYear = paramsJSON.getString("proposalYear");
		String vendorNbr = paramsJSON.getString("vendorNbr");
		Integer proposalId = paramsJSON.getInteger("proposalId");
		String isChange = paramsJSON.getString("isChange");
		StringBuffer getLastYearOrderNoSb = new StringBuffer();
		Map<String,Object> map=new HashMap<>();
		Map<String,Object> getLastYearOrderNoMap=new HashMap<>();
		map.put("proposalYear",proposalYear);
		map.put("vendorNbr",vendorNbr);

		String hql = "from TtaProposalHeaderEntity_HI t where t.proposalYear =:proposalYear and " +
				"t.vendorNbr =:vendorNbr  ";
		if(!SaafToolUtils.isNullOrEmpty(proposalId)){
			hql = hql+" and t.proposalId <> '"+String.valueOf(proposalId)+"' ";
		}
		hql = hql+" and t.status  in ('A','B','C')  ";
		List<TtaProposalHeaderEntity_HI> collectionList = ttaProposalHeaderDAO_HI.findList(hql, map);

		//不是变更的Proposal,不需要校验
		if (StringUtils.isBlank(isChange) || "N".equalsIgnoreCase(isChange)) {
			if (collectionList != null && collectionList.size() > 0) {
				TtaProposalHeaderEntity_HI inst = collectionList.get(0);
				String illExcept = "本年度的供应商PROPOSAL已存在!请修改!";
				throw new IllegalArgumentException(illExcept);
			}
		}

		if (!SaafToolUtils.isNullOrEmpty(proposalId)) {
			TtaProposalHeaderEntity_HI entity_hi = ttaProposalHeaderDAO_HI.getById(proposalId);
			if (!proposalYear.equals(entity_hi.getProposalYear())) {
				//保存请求日志
				this.saveRequestLog(request,response, paramsJSON,userId);
			}
		}
		boolean dataIsExits = this.checkBrandplnHAndTtaTermsDataIsExits(paramsJSON, userId);
		if (!dataIsExits){
			throw new IllegalArgumentException("要修改的Proposal制作年度与原Proposal制作年度不一致,不能修改");
		} else {
			ttaProposalHeaderDAO_HI.executeSqlUpdate("update tta_brandpln_header tbh set tbh.year_code = '"+proposalYear+"' where tbh.proposal_id =" + proposalId);
		}

		TtaProposalHeaderEntity_HI instance = SaafToolUtils.setEntity(TtaProposalHeaderEntity_HI.class, paramsJSON, ttaProposalHeaderDAO_HI, userId);

		if(SaafToolUtils.isNullOrEmpty(instance.getProposalId())){
			instance.setStatus("A");
			instance.setIsChange("N");
			instance.setIsSplit("N");
			instance.setOrderNbr(codeService.getProposalSizeCode());

			if(StringUtils.isBlank(instance.getMajorDeptCode())){
				throw new IllegalArgumentException("主部门不能为空,请先配置当前登录人的主部门信息");
			}

			//设置用户的group_code,group_name
			JSONObject isIN = new JSONObject();
			isIN.put("userId", userId);
			List<BaseUsersEntity_HI> list = this.baseUsersServer.findList(isIN);
			Assert.notNull(list,"当前登录信息已失效,请重新刷新重试");
			BaseUsersEntity_HI hi = list.get(0);
			if (StringUtils.isBlank(hi.getGroupCode())){
				throw new IllegalArgumentException("当前登录人没有小部门信息,请检查");
			}
			instance.setUserGroupCode(hi.getGroupCode());
			instance.setUserGroupName(hi.getGroupName());
		}
		//5. 如果去年供应商为空, 则 去寻找去年供应商
		if(SaafToolUtils.isNullOrEmpty(instance.getLastYearProposalId())){
			//只有少于等于一个的时候填充
			getLastYearOrderNoSb.append(TtaProposalHeaderEntity_HI_RO.getLastYearOrderNo(instance.getVendorNbr(),new BigDecimal(instance.getProposalYear()).subtract(new BigDecimal("1")).toString())) ;
			List<Map<String, Object>>  getLastYearOrderNoList = ttaProposalHeaderEntityHs.queryNamedSQLForList(getLastYearOrderNoSb.toString(),getLastYearOrderNoMap);
			if( ( 0 <getLastYearOrderNoList.size()) && !SaafToolUtils.isNullOrEmpty(getLastYearOrderNoList.get(0).get("LAST_YEAR_PROPOSAL_IDS"))){
				if( -1 == Integer.parseInt((String)getLastYearOrderNoList.get(0).get("LAST_YEAR_PROPOSAL_IDS")) ){
					throw new IllegalArgumentException("当前PROPOSAL单据 出现多个对应的去年的PROPOSAL单据,请选择一一个 ");
				}else{
					instance.setLastYearProposalId(Integer.valueOf((String)getLastYearOrderNoList.get(0).get("LAST_YEAR_PROPOSAL_IDS"))   );
				}
			}
		}
		String keyStrArr = jedisCluster.hget("GLOBAL_REDIS_CACHE","TTA_SHOW_QST_DEPT");
		//当前方法的作用 是判断是空的，空格，制表符，tab r如果是空的就直接返回
		if (StringUtils.isNotBlank(keyStrArr)){
			 List<String> keyArr = Arrays.asList(keyStrArr.split(","));
			if (!keyArr.contains(instance.getMajorDeptCode())) {
				instance.setIsQuestConfirm("Y");
			}
		}
		ttaProposalHeaderDAO_HI.saveOrUpdate(instance);
		return instance;
	}

	/**
	 * 检查品牌计划和TTA Terms上的数据是否存在
	 * @param queryParamJson
	 * @param userId
	 * @return 返回true 可修改,返回false,提示不能修改
	 */
	public boolean checkBrandplnHAndTtaTermsDataIsExits(JSONObject queryParamJson,int userId) throws Exception{
		String proposalYear = queryParamJson.getString("proposalYear");
		Integer proposalId = queryParamJson.getInteger("proposalId");
		int ttaBrandCount = 0;
		List<Map<String, Object>> mapList = ttaProposalHeaderEntityHs.queryNamedSQLForList(TtaProposalHeaderEntity_HI_RO.getTtaBrandCount(proposalId, proposalYear), new HashMap<>());
		if (CollectionUtils.isNotEmpty(mapList)) {
			ttaBrandCount = ((BigDecimal)mapList.get(0).get("BRAND_COUNT")).intValue();
		}
		if(ttaBrandCount > 0) {
			return false;
		}
		return true;
	}

	@Override
	public void delete(Integer pkId) {
		if (pkId == null) {
			return;
		}
		TtaProposalHeaderEntity_HI instance = ttaProposalHeaderDAO_HI.getById(pkId);
		if (instance == null) {
			return;
		}
		ttaProposalHeaderDAO_HI.delete(instance);
	}


	@Override
	public TtaProposalHeaderEntity_HI_RO findByRoId(JSONObject queryParamJSON) {
		SaafToolUtils.validateJsonParms(queryParamJSON,"proposalId");
		StringBuffer sql = new StringBuffer();
		sql.append(TtaProposalHeaderEntity_HI_RO.TTA_ITEM_V);
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		SaafToolUtils.parperParam(queryParamJSON, "v.proposal_Id", "proposalId", sql, paramsMap, "=");
		TtaProposalHeaderEntity_HI_RO proposalHeadEntity = (TtaProposalHeaderEntity_HI_RO)ttaProposalHeaderDAO_HI_RO.get(sql,paramsMap);
		//tta terms 头部信息
		StringBuffer ttaTermSql = new StringBuffer();
		ttaTermSql.append(TtaTermsHEntity_HI_RO.TTA_ITEM_OLD_V).append(" and v.proposal_Id=:proposalId");
		TtaTermsHEntity_HI_RO termsHeadEntity = ttaTermsHDAO_HI_RO.get(ttaTermSql, paramsMap);
		if (proposalHeadEntity != null && termsHeadEntity != null) {
			proposalHeadEntity.setDeptCode(termsHeadEntity.getDeptCode());
			proposalHeadEntity.setFcsPurchse(termsHeadEntity.getFcsPurchse());
			proposalHeadEntity.setAgreementEdition(termsHeadEntity.getAgreementEdition());
			proposalHeadEntity.setInvoiceType(termsHeadEntity.getInvoiceType());
		}
		String keyStrArr = jedisCluster.hget("GLOBAL_REDIS_CACHE","TTA_SHOW_QST_DEPT");
		//当前方法的作用 是判断是空的，空格，制表符，tab r如果是空的就直接返回
		if (StringUtils.isNotBlank(keyStrArr) && proposalHeadEntity != null){
			List<String> keyArr = Arrays.asList(keyStrArr.split(","));
			if (!keyArr.contains(proposalHeadEntity.getMajorDeptCode())) {
				proposalHeadEntity.setIsHiddenQuestion("Y");
			}
		}
		return proposalHeadEntity;
	}


	@Override
	public Map<String, Object> callCopyOrder(JSONObject paramsJSON, int userId)  {
		Integer newPkId =0;
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			paramsMap.put("pkId",paramsJSON.getInteger("proposalId"));
			paramsMap.put("userId",userId);
			resultMap = ttaProposalHeaderDAO.callCut(paramsMap);
			Integer xFlag = (Integer) resultMap.get("xFlag");
			String xMsg = (String) resultMap.get("xMsg");

			if (xFlag!=1) {
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); //
				//throw new Exception(xMsg);
			};
		} catch (Exception e) {
			e.printStackTrace();
		}
        Integer proposalId  =  (Integer) resultMap.get("proposalId");
		TtaProposalHeaderEntity_HI instance = ttaProposalHeaderDAO_HI.getById(proposalId);

		instance.setOrderNbr(codeService.getProposalSizeCode());

		ttaProposalHeaderDAO_HI.saveOrUpdate(instance);

		return resultMap;
	}


	@Override
	public Map<String, Object> callChangeOrder(JSONObject paramsJSON, int userId)  {
		Integer newPkId =0;
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			paramsMap.put("pkId",paramsJSON.getInteger("proposalId"));
			paramsMap.put("userId",userId);
			paramsMap.put("dealType",paramsJSON.getString("remark"));
			resultMap = ttaProposalHeaderDAO.callOrder(paramsMap);
			Integer xFlag = (Integer) resultMap.get("xFlag");
			String xMsg = (String) resultMap.get("xMsg");

			if (xFlag!=1) {
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); //
				//throw new Exception(xMsg);
			};
		} catch (Exception e) {
			e.printStackTrace();
		}

		return resultMap;
	}



	@Override
	public void updateApprove(JSONObject paramsJSON, int userId) throws Exception {

		Integer proposalId = paramsJSON.getIntValue("id");//单据Id
		TtaProposalHeaderEntity_HI instance = ttaProposalHeaderDAO_HI.getById(proposalId);
		String orderStatus = null;//状态
		switch (paramsJSON.getString("status")) {
			case "REFUSAL":
			case "REVOKE":
				orderStatus = "A";
				instance.setStatus(orderStatus);
				ttaProposalHeaderDAO_HI.saveOrUpdate(instance);
				break;
			case "ALLOW":
				orderStatus = "C";
				instance.setStatus(orderStatus);
				instance.setApproveDate(new Date());
				ttaProposalHeaderDAO_HI.saveOrUpdate(instance);
				break;
			case "DRAFT":
				orderStatus = "A";
				instance.setStatus(orderStatus);
				ttaProposalHeaderDAO_HI.saveOrUpdate(instance);
				break;
			case "APPROVAL":
				orderStatus = "B";
				instance.setStatus(orderStatus);
				ttaProposalHeaderDAO_HI.saveOrUpdate(instance);
				break;
			case "CLOSED":
				orderStatus = "D";
				instance.setStatus(orderStatus);
				ttaProposalHeaderDAO_HI.saveOrUpdate(instance);
				break;

		}
		instance.setOperatorUserId(userId);
		ttaProposalHeaderDAO_HI.saveOrUpdate(instance);
		ttaProposalHeaderDAO_HI.fluch();
	}


	@Override
	public void findApproveCheck(JSONObject paramsJSON, int userId) throws Exception {

		Integer proposalId = paramsJSON.getInteger("proposalId");
		String toStatus   = "C";
		String status   = paramsJSON.getString("status");

		TtaProposalHeaderEntity_HI instance = ttaProposalHeaderDAO_HI.getById(proposalId);

		if(!"Y".equals(instance.getIsPlnConfirm())){
			throw new IllegalArgumentException("【品牌计划】 未确认!不能提交!");
		}

		if(!"Y".equals(instance.getIsTermsConfirm())){
			throw new IllegalArgumentException("【TTA Terms】 未确认!不能提交!");
		}
		if(!"Y".equals(instance.getIsDepartConfirm())){
			throw new IllegalArgumentException("【部门协定标准】 未确认!不能提交!");
		}
		if(!"Y".equals(instance.getIsNewConfirm())){
			throw new IllegalArgumentException("【新品种推广服务费】未确认!不能提交!");
		}
		if(!"Y".equals(instance.getIsQuestConfirm())){
			throw new IllegalArgumentException("【问卷】 未确认!不能提交!");
		}

		if (instance == null) {
			throw new IllegalArgumentException("ID:"+proposalId+"proposalId不存在!请修改!");
		}

		if(!status.equals(instance.getStatus())){
			throw new IllegalArgumentException("状态发生变更，操作无效!");
		}
		if(!"A".equals(instance.getStatus())){
			throw new IllegalArgumentException("状态不是草稿，不能提交");
		}
	}


	@Override
	public TtaProposalHeaderEntity_HI updateStatus(JSONObject paramsJSON, int userId) throws Exception {

		Integer proposalId = paramsJSON.getInteger("proposalId");
		String toStatus   = paramsJSON.getString("toStatus");
		String status   = paramsJSON.getString("status");

		TtaProposalHeaderEntity_HI instance = ttaProposalHeaderDAO_HI.getById(proposalId);
		if (instance == null) {
			throw new IllegalArgumentException("ID:"+proposalId+"proposalId不存在!请修改!");
		}

		if(!status.equals(instance.getStatus())){
			throw new IllegalArgumentException("状态发生并更，操作无效!");
		}


		instance.setStatus(toStatus);
		if("C".equals(toStatus)) {
			instance.setApproveDate(new Date());
		}
		instance.setOperatorUserId(userId);
		instance.setLastUpdatedBy(userId);
		instance.setLastUpdateLogin(userId);
		ttaProposalHeaderDAO_HI.saveOrUpdate(instance);
		return instance;
	}

	@Override
	public Map<String, Object> callChangProposalBillStatus(JSONObject paramsJSON, int userId) {
		LOGGER.info("用戶id：{}",userId);
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			paramsMap.put("pkId",paramsJSON.getInteger("proposalId"));
			paramsMap.put("userId",userId);
			resultMap = ttaProposalHeaderDAO.callChangProposalBillStatus(paramsMap);
			Integer xFlag = (Integer) resultMap.get("xFlag");
			String xMsg = (String) resultMap.get("xMsg");
			Integer proposalId = (Integer) resultMap.get("proposalId");
			LOGGER.info("执行变更后的proposalId:{}", proposalId);

			if (xFlag!=1) {
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); //回滚事务
			};
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultMap;
	}

	@Override
	public Map<String, Object> cutProposalBillVersion(JSONObject jsonObject, int userId) {
		LOGGER.info("用戶id：{},参数: {}",userId,jsonObject.toString());
		Map<String,Object> paramMap = new HashMap<>();
		Map<String,Object> resultMap = new HashMap<>();

		try {
			paramMap.put("pkId",jsonObject.getInteger("proposalId"));
			paramMap.put("userId",userId);
			resultMap = ttaProposalHeaderDAO.cutProposalBillVersion(paramMap);

			Integer xFlag = (Integer) resultMap.get("xFlag");
			String xMsg = (String) resultMap.get("xMsg");
			Integer proposalId = (Integer) resultMap.get("proposalId");
			LOGGER.info("执行变更后的proposalId:{}", proposalId);

			if (xFlag!=1) {
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); //回滚事务
			}
			return resultMap;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultMap;
	}

	@Override
	public TtaProposalHeaderEntity_HI_RO findProposalSaleType(JSONObject queryParamJSON) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(TtaProposalHeaderEntity_HI_RO.SELECT_SALETYPE_PROPOSALYEAR);

		LOGGER.info("参数 proposalYear:{},vendorNbr:{}",new Object[]{queryParamJSON.getInteger("proposalYear"),queryParamJSON.getString("vendorNbr")});
		Integer proposalYear = queryParamJSON.getInteger("proposalYear");
		String newOrExisting = queryParamJSON.getString("newOrExisting");

		if (null == proposalYear ) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
			String format = sdf.format(new Date());
			proposalYear = Integer.parseInt(format) - 1;
		}else {
			proposalYear = proposalYear - 1;
		}

		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("vendorNbr",queryParamJSON.getString("vendorNbr"));
		paramsMap.put("proposalYear",String.valueOf(proposalYear));
		paramsMap.put("newOrExisting",newOrExisting);
		//SaafToolUtils.parperParam(queryParamJSON, "v.proposal_Id", "proposalId", sql, paramsMap, "=");
		TtaProposalHeaderEntity_HI_RO entity_hi_ro = ttaProposalHeaderDAO_HI_RO.get(sql, paramsMap);

		if (null == entity_hi_ro ) {
			entity_hi_ro = new TtaProposalHeaderEntity_HI_RO();
			entity_hi_ro.setSaleType("no");
		}

		return entity_hi_ro;
	}

	/**
	 * 查询供应商年度
	 * @param year1
	 * @param year2
	 * @param midList
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<TtaProposalHeaderEntity_HI_RO> findProposalInfoBySupplierAndYear(int year1, int year2, Set<String> midList) throws Exception {
		JSONObject queryParamJSON = new JSONObject();
		StringBuffer sql = new StringBuffer(TtaProposalHeaderEntity_HI_RO.SELECT_PROPOSAL_BY_SUPPLIER);
		Map<String,Object> queryParamMap = new HashMap<>();
		List<String> yearList = new ArrayList<>();
		String ys = String.valueOf(year1);
		yearList.add(ys);
		if (year2 != -1) {
			String ys2 = String.valueOf(year2);
			yearList.add(ys2);
		}
		String year_in = StringUtils.join(yearList, ",");
		if (StringUtils.isBlank(year_in)) {
			year_in = "0";
		}
		String mid_supplier_in = StringUtils.join(midList, ",");
		if (StringUtils.isBlank(mid_supplier_in)) {
			mid_supplier_in = "0";
		}
		queryParamJSON.put("vendorNbr",year_in.toString());
		queryParamJSON.put("proposalYear",mid_supplier_in.toString());
		SaafToolUtils.parperParam(queryParamJSON, "tph.vendor_nbr", "vendorNbr", sql, queryParamMap, "in", false);
		SaafToolUtils.parperParam(queryParamJSON, "tph.proposal_year", "proposalYear", sql, queryParamMap, "in", false);
		List<TtaProposalHeaderEntity_HI_RO> findList = ttaProposalHeaderDAO_HI_RO.findList(sql, queryParamMap);
		return findList;
	}

	@Override
	public TtaProposalHeaderEntity_HI updateSkipStatus(JSONObject paramsJSON, Integer userId) throws Exception {
		Integer proposalId = paramsJSON.getIntValue("proposalId");//单据Id
		String isSkipApprove = paramsJSON.getString("isSkipApprove");
		TtaProposalHeaderEntity_HI instance = ttaProposalHeaderDAO_HI.getById(proposalId);
		if (StringUtils.isNotBlank(isSkipApprove)) {
			instance.setLastUpdateDate(new Date());
			instance.setIsSkipApprove(isSkipApprove);
			ttaProposalHeaderDAO_HI.update(instance);
		}
		return instance;
	}

	@Override
	public Map<String, Object> findProccessNodeStauts(JSONObject paramsJSON) throws Exception {
		Map<String, Object> resutMap = new HashMap<>();
		resutMap.put("flag", false);
		String procDefKey = paramsJSON.getString("procDefKey");
		String dicKey = paramsJSON.getString("dicKey");
		Object businessKey = paramsJSON.get("businessKey");
		if (StringUtils.isBlank(procDefKey) || StringUtils.isBlank(dicKey) || businessKey == null) {
			LOGGER.info(".findProccessNodeStauts 入参信息:{}, 出参信息:{}, 返回值：", paramsJSON, false);
			return resutMap;
		}
		Map<String, Object> queryMap = new HashMap<>();
		queryMap.put("procDefKey",procDefKey); //"TTA_PROPOSAL.-999"
		queryMap.put("businessKey", businessKey); //1072
		List<Map<String, Object>> list = ttaProposalHeaderDAO.queryNamedSQLForList(TtaProposalHeaderEntity_HI_RO.QUERY_CURRENT_NODE_NAME, queryMap);
		if (list == null || list.isEmpty() || list.get(0).get("currentNodeName") == null) {
			LOGGER.info(".findProccessNodeStauts 入参信息:{}, list出参信息:{}, 返回值：", paramsJSON, list, false);
			return resutMap;
		}
		String currentNodeName = list.get(0).get("currentNodeName") + "";
		List<BaseLookupValuesEntity_HI> lookUpList = baseLookupValuesDAO_HI.findByProperty(new JSONObject().fluentPut("lookupType", dicKey.toUpperCase()).fluentPut("enabledFlag", "Y").fluentPut("deleteFlag", 0));
		if (lookUpList == null || lookUpList.isEmpty()) {
			LOGGER.info(".findProccessNodeStauts 入参信息:{}, lookUpList出参信息:{}, 返回值：", paramsJSON, lookUpList, false);
			return resutMap;
		}
		for (BaseLookupValuesEntity_HI entity_hi : lookUpList) {
			if(currentNodeName.equalsIgnoreCase(entity_hi.getMeaning())){
				LOGGER.info(".findProccessNodeStauts 入参信息:{}, 匹配的快码值是:{}", paramsJSON, currentNodeName);
				resutMap.put("flag", true);
				resutMap.put("nodeName", currentNodeName);
				return resutMap;
			}
		}
		return resutMap;
	}

	/**
	 * 查找proposal单据
	 * @param queryParamJSON
	 * @param pageIndex
	 * @param pageRows
	 * @return
	 */
	@Override
	public Pagination<TtaProposalHeaderEntity_HI_RO> findProposalCode(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
		StringBuffer sql = new StringBuffer();
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		sql.append(TtaProposalHeaderEntity_HI_RO.PROPOSAL_QUERY);
		paramsMap.put("vendorNbr",queryParamJSON.getString("vendorNbr"));
		paramsMap.put("proposalYear",queryParamJSON.getString("proposalYear"));
		paramsMap.put("status",queryParamJSON.getString("status"));
		//SaafToolUtils.parperParam(queryParamJSON, "v.vendor_Nbr", "vendorNbr", sql, paramsMap, "like");
		//SaafToolUtils.parperParam(queryParamJSON, "v.vendor_Name", "vendorName", sql, paramsMap, "like");
		//SaafToolUtils.parperParam(queryParamJSON, "v.PROPOSAL_YEAR", "proposalYear", sql, paramsMap, "=");
		//SaafToolUtils.parperParam(queryParamJSON, "v.status", "status", sql, paramsMap, "in");
		//SaafToolUtils.parperParam(queryParamJSON, "v.order_Nbr", "orderNbr", sql, paramsMap, "like");
		//SaafToolUtils.changeQuerySort(queryParamJSON, sql, "v.order_Nbr asc", false);
		Pagination<TtaProposalHeaderEntity_HI_RO> findList = ttaProposalHeaderDAO_HI_RO.findPagination(sql, SaafToolUtils.getSqlCountString(sql), paramsMap, pageIndex, pageRows);
		return findList;
	}

	/**
	 * 获取Proposal信息
	 * @param proposalCode
	 * @return
	 */
	@Override
	public TtaProposalHeaderEntity_HI_RO getProposalHearder(String proposalCode,BigDecimal vesionCode) {
		LOGGER.info("参数 proposalCode:{},vesionCode:{}",new Object[]{proposalCode,vesionCode});
		StringBuffer sql = new StringBuffer();
		sql.append(TtaProposalHeaderEntity_HI_RO.TTA_GET_PROPOSAL_HEARER);
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		//JSONObject queryParamJSON = new JSONObject();
		paramsMap.put("orderNbr",proposalCode);
		paramsMap.put("vesionCode",vesionCode.intValue());
		sql.append(" and tph.order_nbr =:orderNbr and tph.status = 'C' and tph.version_code =:vesionCode");
		//SaafToolUtils.parperParam(queryParamJSON, "tph.order_nbr", "orderNbr", sql, paramsMap, "=");
		return ttaProposalHeaderDAO_HI_RO.get(sql,paramsMap);
	}

	@Override
	public void updateVendorName(JSONObject queryParamJSON, int userId) throws Exception {
		LOGGER.info("用户id:{}",userId);
		Assert.notNull(queryParamJSON.getInteger("proposalId"),"当前Proposal信息未保存,更新供应商名称失败!");
		Integer proposalId = queryParamJSON.getInteger("proposalId");
		String orderNbr = queryParamJSON.getString("orderNbr");
		String vendorNbr = queryParamJSON.getString("vendorNbr");
		if (!vendorNbr.contains("P")) {
			throw new IllegalArgumentException("更新供应商名称必须为P开头的供应商编号");
		}
		//查找供应商
		TtaSupplierEntity_HI_RO supplierEntityHiRo = ttaSupplierServer.findByRoId(queryParamJSON);
		String supplierName = supplierEntityHiRo.getSupplierName();//供应商名称

		//1.更新Proposal头信息
		ttaProposalHeaderDAO_HI.executeSqlUpdate("update tta_proposal_header tph set tph.vendor_name = '"+supplierName+"' where tph.proposal_id =" + proposalId);
		//2.更新品牌计划头信息
		ttaProposalHeaderDAO_HI.executeSqlUpdate("update tta_brandpln_header tbh set tbh.vendor_name = '"+supplierName+"' where tbh.proposal_id =" + proposalId);
		//3.更新TTA Terms头信息
		ttaProposalHeaderDAO.executeSqlUpdate("update tta_proposal_terms_header tpth set tpth.vendor_desc = '"+supplierName+"' where tpth.proposal_id =" + proposalId);
		//4.更新合同表(tta_contract_line)
		ttaProposalHeaderDAO.executeSqlUpdate("update tta_contract_line ttcl set ttcl.vendor_name = '"+supplierName+"' where ttcl.proposal_id =" + proposalId);
		//注意的地方,不更新tta_contract_line_h合同历史表,为了以后追溯历史数据
		//5.更新 TTA Analysis信息tta_analysis_line
		ttaProposalHeaderDAO.executeSqlUpdate("update tta_analysis_line tal set tal.supplier_name = '"+supplierName+"' where tal.proposal_id =" + proposalId);
		//6.更新合同拆分与编辑模块上的头信息
		ttaProposalHeaderDAO.executeSqlUpdate("update tta_contract_header tch set tch.vendor_name = '"+supplierName+"' where tch.proposal_code='"+orderNbr+"'");
		//7.更新tta_rel_supplier表的供应商名称
		ttaProposalHeaderDAO.executeSqlUpdate("update tta_rel_supplier trs set trs.rel_supplier_name = '"+supplierName+"' where trs.rel_supplier_code = '"+vendorNbr+"'");
	}

	public void saveRequestLog(HttpServletRequest request, HttpServletResponse response, JSONObject paramJSON,int userId) throws Exception {
		LOGGER.info("请求服务服务[{}]：{}",request.getMethod(),request.getRequestURI());
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("requestUrl",request.getRequestURL());
		jsonObject.put("requestMethod",request.getMethod());
		JSONObject headerJSON = new JSONObject();
		headerJSON.put("proposalId",paramJSON.getInteger("proposalId"));
		jsonObject.put("requestHeader",JSONObject.toJSONString(headerJSON));
		jsonObject.put("requestBody",JSONObject.toJSONString(paramJSON));
		jsonObject.put("response","响应ProposalId:" + paramJSON.getInteger("proposalId"));
		jsonObject.put("creationDate",new Date());
		jsonObject.put("createdBy",userId);
		jsonObject.put("lastUpdatedBy",userId);
		jsonObject.put("lastUpdateDate",new Date());
		jsonObject.put("lastUpdateLogin",userId);
		baseRequestLog.saveBaseRequestLogInfo(jsonObject);
	}
}
