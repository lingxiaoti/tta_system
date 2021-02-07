package com.sie.watsons.base.clause.model.inter.server;

import java.text.SimpleDateFormat;
import java.util.*;

import com.sie.saaf.base.dict.model.entities.BaseLookupValuesEntity_HI;
import com.sie.watsons.base.clause.model.dao.TtaTermsFrameHeaderDAO_HI;
import com.sie.watsons.base.clause.model.entities.TtaCollectTypeLineEntity_HI;
import com.sie.watsons.base.clause.model.entities.TtaTermsFrameHeaderEntity_HI;
import com.sie.watsons.base.clause.model.entities.readonly.TtaTermsFrameHeaderEntity_HI_RO;
import com.sie.watsons.base.clauseitem.model.entities.TtaClauseTreeHEntity_HI;
import com.sie.watsons.base.clauseitem.model.entities.TtaCollectTypeLineHEntity_HI;
import com.sie.watsons.base.clauseitem.model.entities.TtaTermsFrameHeaderHEntity_HI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.model.inter.server.GenerateCodeServer;
import com.sie.saaf.common.util.SaafBeanUtils;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.clause.model.entities.TtaClauseTreeEntity_HI;
import com.sie.watsons.base.clause.model.inter.ITtaTermsFrameHeader;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

@Component("ttaTermsFrameHeaderServer")
public class TtaTermsFrameHeaderServer extends BaseCommonServer<TtaTermsFrameHeaderEntity_HI> implements ITtaTermsFrameHeader{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaTermsFrameHeaderServer.class);
	@Autowired
	private  ViewObject<TtaTermsFrameHeaderHEntity_HI> ttaTermsFrameHeaderHDAO_HI;
	@Autowired
	private  ViewObject<TtaClauseTreeHEntity_HI> ttaClauseTreeHDAO_HI;
	@Autowired
	private  ViewObject<TtaTermsFrameHeaderEntity_HI> ttaTermsFrameHeaderDAO_HI;
	@Autowired
	private  ViewObject<TtaClauseTreeEntity_HI> ttaClauseTreeDAO_HI;

	@Autowired
	private  ViewObject<TtaCollectTypeLineEntity_HI> ttaCollectTypeLineDAO_HI;

	@Autowired
	private TtaTermsFrameHeaderDAO_HI ttaTermsFrameHeaderDAO;

	@Autowired
	private  ViewObject<TtaCollectTypeLineHEntity_HI> ttaCollectTypeLineHDAO_HI;

	@Autowired
	private  BaseViewObject<TtaTermsFrameHeaderEntity_HI_RO> ttaTermsFrameHeaderDAO_HI_RO;
	@Autowired
	private GenerateCodeServer generateCodeServer;

	@Autowired
	private TtaCollectTypeLineServer ttaCollectTypeLineServer;

	public TtaTermsFrameHeaderServer() {
		super();
	}

	/**
	 * 查询条件框架列表（分页）
	 *
	 * @param queryParamJSON 对象属性的JSON格式
	 * @param pageIndex      页码
	 * @param pageRows       每页查询记录数
	 * @return Pagination<BaseJobEntity_HI_RO>
	 */
	@Override
	public Pagination<TtaTermsFrameHeaderEntity_HI_RO> findTtaTermsFrameHeaderPagination(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
		Map<String, Object> queryParamMap = new HashMap<>();
		StringBuffer querySQL = new StringBuffer(TtaTermsFrameHeaderEntity_HI_RO.QUERY_ATF_SQL);
		SaafToolUtils.parperHbmParam(TtaTermsFrameHeaderEntity_HI_RO.class, queryParamJSON, "atf.frame_code", "frameCode", querySQL, queryParamMap, "fulllike");
		SaafToolUtils.parperHbmParam(TtaTermsFrameHeaderEntity_HI_RO.class, queryParamJSON, "atf.bill_status", "billStatus", querySQL, queryParamMap, "=");
		SaafToolUtils.parperHbmParam(TtaTermsFrameHeaderEntity_HI_RO.class, queryParamJSON, "atf.team_Framework_Id", "teamFrameworkId", querySQL, queryParamMap, "=");
		if(!SaafToolUtils.isNullOrEmpty(queryParamJSON.getString("cluseCopy")) && "copy".equals(queryParamJSON.getString("cluseCopy"))){
			querySQL.append(" order by pass_date desc ");
		}else {
			querySQL.append(" order by atf.team_framework_id ");
		}
		StringBuffer countSql = SaafToolUtils.getSimpleSqlCountString(querySQL,"count(*)");
		return ttaTermsFrameHeaderDAO_HI_RO.findPagination(querySQL,countSql, queryParamMap, pageIndex, pageRows);
	}

	public JSONObject saveOrUpdateTraermsAll(JSONObject paramsJSON, Integer userId) throws Exception {
		JSONObject result=new JSONObject();
		JSONArray clauseEY = (JSONArray) paramsJSON.get("clauseEY");
		JSONArray clauseEN = (JSONArray) paramsJSON.get("clauseEN");
		Map hashMap = new HashMap<String,Object>();
		hashMap.put("year",((JSONObject) paramsJSON.get("clause")).getInteger("year"));
		hashMap.put("deptCode",((JSONObject) paramsJSON.get("clause")).getString("deptCode"));
		List<TtaTermsFrameHeaderEntity_HI> year = ttaTermsFrameHeaderDAO_HI.findByProperty(hashMap);
		if(year.size()>0){
			if(SaafToolUtils.isNullOrEmpty(((JSONObject) paramsJSON.get("clause")).getInteger("teamFrameworkId"))){
				throw new IllegalArgumentException("已经存在"+year.get(0).getYear()+"年，和相同部门"+((JSONObject) paramsJSON.get("clause")).getInteger("deptCode")+"的单据,不允许重复建相同年份的单据");
			}else{
				if(!((JSONObject) paramsJSON.get("clause")).getInteger("teamFrameworkId").equals(year.get(0).getTeamFrameworkId())){
					throw new IllegalArgumentException("已经存在"+year.get(0).getYear()+"年，和相同部门"+((JSONObject) paramsJSON.get("clause")).getInteger("deptCode")+"的单据,不允许重复建相同年份的单据");
				}
			}

		}
		//保存条款头框架
		TtaTermsFrameHeaderEntity_HI ttaTermsFrameHeaderEntity = SaafToolUtils.setEntity(TtaTermsFrameHeaderEntity_HI.class, (JSONObject)paramsJSON.get("clause"), ttaTermsFrameHeaderDAO_HI, userId);
		TtaClauseTreeEntity_HI ttaClauseTreeEntity = SaafToolUtils.setEntity(TtaClauseTreeEntity_HI.class, (JSONObject)paramsJSON.get("clauseT"), ttaClauseTreeDAO_HI, userId);


		//保存框架投表
		if (null == ttaTermsFrameHeaderEntity.getTeamFrameworkId()) {
			ttaTermsFrameHeaderEntity.setBusinessVersion("001");
		}
		ttaTermsFrameHeaderEntity.setFrameCode(String.valueOf(ttaTermsFrameHeaderEntity.getYear()));
		ttaTermsFrameHeaderDAO_HI.saveOrUpdate(ttaTermsFrameHeaderEntity);

		if (null == ttaClauseTreeEntity.getClauseId()) {
			if("01".equals(ttaClauseTreeEntity.getBusinessType())) {
				String clauseBtCodeKey = "tta_clauseT_bt" + ttaTermsFrameHeaderEntity.getTeamFrameworkId();
				int generateId = generateCodeServer.getGenerateId(clauseBtCodeKey);
				String clauseCode = ttaClauseTreeEntity.getPaymentMethod() + String.format("%0" + 3 + "d", generateId);
				ttaClauseTreeEntity.setClauseCode(clauseCode);
			}
			String clauseBvCodeKey = "tta_clauseT_bv" + ttaTermsFrameHeaderEntity.getTeamFrameworkId();
			int generateId2 = generateCodeServer.getGenerateId(clauseBvCodeKey);
			ttaClauseTreeEntity.setBusinessVersion(generateId2);
			String clauseTCodeKey= "tta_clauseT_code" + ttaTermsFrameHeaderEntity.getTeamFrameworkId();
			int generateId3 = generateCodeServer.getGenerateId(clauseTCodeKey);
			String format = (new SimpleDateFormat("yyyyMMdd")).format(new Date());
			String clauseCodePre = ttaClauseTreeEntity.getPaymentMethod() + String.format("%0" + 4 + "d", generateId3);
			String clauseTcode = format + clauseCodePre+ttaTermsFrameHeaderEntity.getTeamFrameworkId() ;
			ttaClauseTreeEntity.setCode(clauseTcode);
			if(SaafToolUtils.isNullOrEmpty(ttaClauseTreeEntity.getPCode())){
				ttaClauseTreeEntity.setPCode("0");
			}
		}
		//查找历史单据
		if(!SaafToolUtils.isNullOrEmpty(ttaClauseTreeEntity.getOldOrderNo())){
			if(SaafToolUtils.isNullOrEmpty(ttaTermsFrameHeaderEntity.getResourceId())){
				throw new IllegalArgumentException("不存在对应历史单据,所以不能填写 历史序号");

			}else{
				StringBuffer query = new StringBuffer("from  TtaClauseTreeEntity_HI   tt  \n" +
						"     where  tt.orderNo =:orderNo \n" +
						"     and   tt.teamFrameworkId =:teamFrameworkId \n" +
						"            and nvl(tt.deleteFlag,0) =0");
				Map<String, Object> paramMapBase = new HashMap<String, Object>();
				Map  oldLineMap = new HashMap<String,Object>();
				oldLineMap.put("orderNo",ttaClauseTreeEntity.getOldOrderNo());
				oldLineMap.put("teamFrameworkId",ttaTermsFrameHeaderEntity.getResourceId());
				List<TtaClauseTreeEntity_HI> oldLines = ttaClauseTreeDAO_HI.findList(query,oldLineMap);
				if(oldLines.size() == 0){
					throw new IllegalArgumentException("填写历史序列不正确,请重新填写");
				}else if(oldLines.size() >1){
					throw new IllegalArgumentException("填写历史序列出现重复,请修改历史单据的序号");
				}else{
					ttaClauseTreeEntity.setOldClauseId(oldLines.get(0).getClauseId());
				}
			}
		}
		//保存tree表
		ttaClauseTreeEntity.setTeamFrameworkId(ttaTermsFrameHeaderEntity.getTeamFrameworkId());
		ttaClauseTreeDAO_HI.saveOrUpdate(ttaClauseTreeEntity);
		//保存单位表
		List<TtaCollectTypeLineEntity_HI> ttaCollectTypeLineEntity_his = ttaCollectTypeLineServer.saveOrUpdate(clauseEY,clauseEN, userId, ttaClauseTreeEntity.getClauseId(),ttaTermsFrameHeaderEntity.getTeamFrameworkId());
		result.put("clause", ttaTermsFrameHeaderEntity);
		result.put("clauseT", ttaClauseTreeEntity);
		result.put("clauseE", ttaCollectTypeLineEntity_his);
		return result;
	}

	public JSONObject saveOrUpdateCopy(JSONObject paramsJSON, Integer userId) throws Exception {
		JSONObject param=new JSONObject();

		//调用存储过程
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
			paramsMap.put("Id",Integer.valueOf(-1));
			paramsMap.put("deptCode",paramsJSON.getString("deptCode"));
			paramsMap.put("userId",userId);
			resultMap = ttaTermsFrameHeaderDAO.callOrderCopy(paramsMap);
			Integer xFlag = (Integer) resultMap.get("xFlag");
			String xMsg = (String) resultMap.get("xMsg");
			//Integer newId = (Integer) resultMap.get("newId");
			JSONObject result=new JSONObject(resultMap);
			//result.put("teamFrameworkId",newId);
			if (xFlag!=1) {
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); //
				throw new Exception(xMsg);
			};



/*		List<TtaClauseTreeEntity_HI> actList= new ArrayList<TtaClauseTreeEntity_HI>();
		//查询对应年份的头表
		List<TtaTermsFrameHeaderEntity_HI> ttaTermsFrameHeaderEntitys = ttaTermsFrameHeaderDAO_HI.findByProperty("teamFrameworkId", paramsJSON.getInteger("teamFrameworkId"));
		//查询对应年份的行表
		List<TtaClauseTreeEntity_HI> ttaClauseTreeEntitys = ttaClauseTreeDAO_HI.findByProperty("teamFrameworkId", paramsJSON.getInteger("teamFrameworkId"));
		//复制
		TtaTermsFrameHeaderEntity_HI atfNew = new TtaTermsFrameHeaderEntity_HI();
		SaafBeanUtils.copyUnionProperties(ttaTermsFrameHeaderEntitys.get(0), atfNew);
		atfNew.setOperatorUserId(userId);
		atfNew.setPassDate(null);
		atfNew.setVersionNum(null);
		atfNew.setBusinessVersion("001");
		atfNew.setBillStatus("DS01");
		ttaTermsFrameHeaderDAO_HI.saveOrUpdate(atfNew);
		for (int i = 0 ;i<ttaClauseTreeEntitys.size();i++){
			TtaClauseTreeEntity_HI actNew = new TtaClauseTreeEntity_HI();
			SaafBeanUtils.copyUnionProperties(ttaClauseTreeEntitys.get(i), actNew);
			actNew.setOperatorUserId(userId);
			actNew.setVersionNum(null);
			actNew.setTeamFrameworkId(atfNew.getTeamFrameworkId());
			actNew.setBusinessVersion(1);
			actNew.setOldClauseId(ttaClauseTreeEntitys.get(i).getClauseId());
			actNew.setOldOrderNo(ttaClauseTreeEntitys.get(i).getOrderNo());
			actList.add(actNew);
		}
		ttaClauseTreeDAO_HI.saveOrUpdateAll(actList);
		result.put("clause", atfNew);*/
		return result;
	}

	@Override
	public JSONArray updateStatus(JSONObject paramsJSON, Integer userId) throws Exception {

		Integer teamFrameworkId = paramsJSON.getIntValue("id");//单据Id
		JSONObject headerObject = new JSONObject();
		headerObject.put("teamFrameworkId", teamFrameworkId);

		JSONObject param=new JSONObject();
		JSONArray result=new JSONArray();
		List<TtaClauseTreeEntity_HI> actList= new ArrayList<TtaClauseTreeEntity_HI>();
		//查询表单工具
		List<TtaTermsFrameHeaderEntity_HI> ttaTermsFrameHeaderEntitys = ttaTermsFrameHeaderDAO_HI.findByProperty("teamFrameworkId",headerObject.get("teamFrameworkId"));
		String orderStatus = null;//状态
		switch (paramsJSON.getString("status")) {
			case "REFUSAL"://驳回
			case "REVOKE"://撤回
				orderStatus = "DS01";
				ttaTermsFrameHeaderEntitys.get(0).setBillStatus(orderStatus);
				ttaTermsFrameHeaderDAO_HI.saveOrUpdate(ttaTermsFrameHeaderEntitys.get(0));
				break;
			case "ALLOW":
				orderStatus = "DS03";
				ttaTermsFrameHeaderEntitys.get(0).setBillStatus(orderStatus);
				ttaTermsFrameHeaderEntitys.get(0).setPassDate(new Date());
				ttaTermsFrameHeaderDAO_HI.saveOrUpdate(ttaTermsFrameHeaderEntitys.get(0));
				break;
			case "DRAFT":
				orderStatus = "DS01";
				ttaTermsFrameHeaderEntitys.get(0).setBillStatus(orderStatus);
				ttaTermsFrameHeaderDAO_HI.saveOrUpdate(ttaTermsFrameHeaderEntitys.get(0));
				break;
			case "APPROVAL":
				orderStatus = "DS02";
				ttaTermsFrameHeaderEntitys.get(0).setBillStatus(orderStatus);
				ttaTermsFrameHeaderDAO_HI.saveOrUpdate(ttaTermsFrameHeaderEntitys.get(0));
				break;
			case "CLOSED":
				orderStatus = "DS10";
				ttaTermsFrameHeaderEntitys.get(0).setBillStatus(orderStatus);
				ttaTermsFrameHeaderDAO_HI.saveOrUpdate(ttaTermsFrameHeaderEntitys.get(0));
				break;

		}
		ttaTermsFrameHeaderDAO_HI.fluch();
		ttaTermsFrameHeaderEntitys.get(0).setOperatorUserId(userId);
		ttaTermsFrameHeaderDAO_HI.saveOrUpdate(ttaTermsFrameHeaderEntitys.get(0));
		return result;
	}

	@Override
	public JSONObject saveChangeTraermsAll(JSONObject paramsJSON, Integer userId) throws Exception {

		//调用存储过程
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		Map<String, Object> resultMap = new HashMap<String, Object>();

			paramsMap.put("Id",paramsJSON.getInteger("teamFrameworkId"));
			paramsMap.put("userId",userId);
			resultMap = ttaTermsFrameHeaderDAO.callOrder(paramsMap);
			Integer xFlag = (Integer) resultMap.get("xFlag");
			String xMsg = (String) resultMap.get("xMsg");
			JSONObject result=new JSONObject(resultMap);
			if (xFlag!=1) {
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); //
				throw new Exception(xMsg);
			};


		//使用java  代码
/*
		JSONObject param=new JSONObject();
		JSONObject result=new JSONObject();
		HashSet<TtaClauseTreeHEntity_HI> ttaClauseTreeHEntitySet = new HashSet<>();
		HashSet<TtaCollectTypeLineHEntity_HI> ttaCollectTypeLineHEntitySet = new HashSet<>();
		List<TtaClauseTreeEntity_HI> actList= new ArrayList<TtaClauseTreeEntity_HI>();
		TtaTermsFrameHeaderHEntity_HI ttaTermsFrameHeaderHEntity_hi = new TtaTermsFrameHeaderHEntity_HI();
		//查询单据
		List<TtaTermsFrameHeaderEntity_HI> ttaTermsFrameHeaderEntitys = ttaTermsFrameHeaderDAO_HI.findByProperty("teamFrameworkId", paramsJSON.getInteger("teamFrameworkId"));
		//版本为001插入两次   留作备底
		if("001".equals(ttaTermsFrameHeaderEntitys.get(0).getBusinessVersion())){
			TtaTermsFrameHeaderHEntity_HI ttaTermsFrameHeaderHEntity_hr = new TtaTermsFrameHeaderHEntity_HI();
			SaafBeanUtils.copyUnionProperties(ttaTermsFrameHeaderEntitys.get(0), ttaTermsFrameHeaderHEntity_hr);
			ttaTermsFrameHeaderHEntity_hr.setResouceId(ttaTermsFrameHeaderEntitys.get(0).getTeamFrameworkId());
			ttaTermsFrameHeaderHEntity_hr.setTeamFrameworkId(null);
			ttaTermsFrameHeaderHDAO_HI.saveOrUpdate(ttaTermsFrameHeaderHEntity_hr);
		}
		//存入备底

		SaafBeanUtils.copyUnionProperties(ttaTermsFrameHeaderEntitys.get(0), ttaTermsFrameHeaderHEntity_hi);
		ttaTermsFrameHeaderHEntity_hi.setResouceId(ttaTermsFrameHeaderEntitys.get(0).getTeamFrameworkId());
		ttaTermsFrameHeaderHEntity_hi.setCreationDate(new Date());
		ttaTermsFrameHeaderHEntity_hi.setCreatedBy(userId);
		ttaTermsFrameHeaderHEntity_hi.setPassDate(null);
		ttaTermsFrameHeaderHEntity_hi.setVersionNum(null);
		Integer businessVersion  = Integer.valueOf(ttaTermsFrameHeaderEntitys.get(0).getBusinessVersion());
		ttaTermsFrameHeaderHEntity_hi.setBusinessVersion(String.format("%0" + 3 + "d", businessVersion + 1));
		ttaTermsFrameHeaderHEntity_hi.setBillStatus("DS01");
		ttaTermsFrameHeaderHEntity_hi.setResouceBusinessVersion(ttaTermsFrameHeaderEntitys.get(0).getBusinessVersion());
		ttaTermsFrameHeaderHDAO_HI.saveOrUpdate(ttaTermsFrameHeaderHEntity_hi);

		List<TtaClauseTreeEntity_HI> ttaClauseTreeEntitys = ttaClauseTreeDAO_HI.findByProperty("teamFrameworkId", paramsJSON.getInteger("teamFrameworkId"));
		for (int i = 0 ;i<ttaClauseTreeEntitys.size();i++){
			//原始版本留底
			TtaClauseTreeHEntity_HI ttaClauseTreeHEntity_HI = new TtaClauseTreeHEntity_HI();
			if(1 == ttaClauseTreeEntitys.get(i).getBusinessVersion()){
				TtaClauseTreeHEntity_HI ttaClauseTreeHEntity_HIR = new TtaClauseTreeHEntity_HI();
				SaafBeanUtils.copyUnionProperties(ttaClauseTreeEntitys.get(i), ttaClauseTreeHEntity_HIR);
				ttaClauseTreeHEntity_HIR.setResouceId(ttaClauseTreeEntitys.get(i).getClauseId());
				ttaClauseTreeHEntity_HIR.setIsChange(0);
				ttaClauseTreeHDAO_HI.saveOrUpdate(ttaClauseTreeHEntity_HIR);
			}
			//存历史版本

			SaafBeanUtils.copyUnionProperties(ttaClauseTreeEntitys.get(i), ttaClauseTreeHEntity_HI);
			ttaClauseTreeHEntity_HI.setOperatorUserId(userId);
			ttaClauseTreeHEntity_HI.setTeamFrameworkId(ttaTermsFrameHeaderHEntity_hi.getTeamFrameworkId());
			ttaClauseTreeHEntity_HI.setResouceId(ttaClauseTreeEntitys.get(i).getClauseId());
			ttaClauseTreeHEntity_HI.setIsChange(0);
			ttaClauseTreeHDAO_HI.saveOrUpdate(ttaClauseTreeHEntity_HI);

			//复制单位
			List<TtaCollectTypeLineEntity_HI> TtaCollectTypeLineEntityList = ttaCollectTypeLineDAO_HI.findByProperty("clauseId", ttaClauseTreeEntitys.get(i).getClauseId());
			for( TtaCollectTypeLineEntity_HI paramv:TtaCollectTypeLineEntityList){
				TtaCollectTypeLineHEntity_HI ttaCollectTypeLineHEntity_hi = new TtaCollectTypeLineHEntity_HI();
				SaafBeanUtils.copyUnionProperties(paramv, ttaCollectTypeLineHEntity_hi);
				ttaCollectTypeLineHEntity_hi.setOperatorUserId(userId);
				ttaCollectTypeLineHEntity_hi.setVersionNum(null);
				ttaCollectTypeLineHEntity_hi.setResouceId(paramv.getCollectTypeId());
				ttaCollectTypeLineHEntity_hi.setClauseId(ttaClauseTreeHEntity_HI.getClauseId());
				ttaCollectTypeLineHEntitySet.add(ttaCollectTypeLineHEntity_hi);
			}
			ttaCollectTypeLineHDAO_HI.saveAll(ttaCollectTypeLineHEntitySet);
		}
*/

		return result;
	}
}
