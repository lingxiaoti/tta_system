package com.sie.watsons.base.clauseitem.model.inter.server;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.text.SimpleDateFormat;
import java.util.*;

import com.sie.saaf.common.model.inter.server.GenerateCodeServer;
import com.sie.saaf.common.util.SaafBeanUtils;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.clause.model.dao.TtaTermsFrameHeaderDAO_HI;
import com.sie.watsons.base.clause.model.entities.TtaClauseTreeEntity_HI;
import com.sie.watsons.base.clause.model.entities.TtaCollectTypeLineEntity_HI;
import com.sie.watsons.base.clause.model.entities.TtaTermsFrameHeaderEntity_HI;
import com.sie.watsons.base.clause.model.entities.readonly.TtaClauseTreeEntity_HI_RO;
import com.sie.watsons.base.clauseitem.model.dao.TtaTermsFrameHeaderHDAO_HI;
import com.sie.watsons.base.clauseitem.model.entities.TtaCollectTypeLineHEntity_HI;
import com.sie.watsons.base.clauseitem.model.entities.TtaTermsFrameHeaderHEntity_HI;
import com.sie.watsons.base.clauseitem.model.entities.readonly.TtaClauseTreeHEntity_HI_RO;
import com.sie.watsons.base.clauseitem.model.entities.TtaClauseTreeHEntity_HI;
import com.sie.watsons.base.clauseitem.model.entities.readonly.TtaTermsFrameHeaderHEntity_HI_RO;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.clauseitem.model.inter.ITtaTermsFrameHeaderH;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

@Component("ttaTermsFrameHeaderHServer")
public class TtaTermsFrameHeaderHServer extends BaseCommonServer<TtaTermsFrameHeaderHEntity_HI> implements ITtaTermsFrameHeaderH{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaTermsFrameHeaderHServer.class);

	@Autowired
	private ViewObject<TtaTermsFrameHeaderHEntity_HI> ttaTermsFrameHeaderHDAO_HI;

	@Autowired
	private ViewObject<TtaClauseTreeHEntity_HI> ttaClauseTreeHDAO_HI;

	@Autowired
	private  ViewObject<TtaTermsFrameHeaderEntity_HI> ttaTermsFrameHeaderDAO_HI;

	@Autowired
	private  ViewObject<TtaClauseTreeEntity_HI> ttaClauseTreeDAO_HI;

	@Autowired
	private GenerateCodeServer generateCodeServer;

	@Autowired
	private BaseViewObject<TtaTermsFrameHeaderHEntity_HI_RO> ttaTermsFrameHeaderHDAO_HI_RO;

	@Autowired
	private BaseViewObject<TtaClauseTreeHEntity_HI_RO> ttaClauseTreeHDAO_HI_RO;

	@Autowired
	private TtaTermsFrameHeaderHDAO_HI ttaTermsFrameHeaderHDAO;

	@Autowired
	private ViewObject<TtaCollectTypeLineHEntity_HI> ttaCollectTypeLineHDAO_HI;

	@Autowired
	private ViewObject<TtaCollectTypeLineEntity_HI> ttaCollectTypeLineDAO_HI;

	@Autowired
	private BaseViewObject<TtaClauseTreeEntity_HI_RO> ttaClauseTreeDAO_HI_RO;


	@Autowired
	private TtaCollectTypeLineHServer ttaCollectTypeLineHServer;

	public TtaTermsFrameHeaderHServer() {
		super();
	}
	/**
	 * 查询条款名目（分页）
	 *
	 * @param queryParamJSON 对象属性的JSON格式
	 * @param pageIndex      页码
	 * @param pageRows       每页查询记录数
	 * @return Pagination<BaseJobEntity_HI_RO>
	 */
	@Override
	public Pagination<TtaTermsFrameHeaderHEntity_HI_RO> findTtaTermsFrameHeaderHPagination(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
		Map<String, Object> queryParamMap = new HashMap<>();
		StringBuffer querySQL = new StringBuffer(TtaTermsFrameHeaderHEntity_HI_RO.QUERY_ATF_H_SQL);
		SaafToolUtils.parperHbmParam(TtaTermsFrameHeaderHEntity_HI_RO.class, queryParamJSON, "atfh.business_version", "businessVersion", querySQL, queryParamMap, "<>");
		SaafToolUtils.parperHbmParam(TtaTermsFrameHeaderHEntity_HI_RO.class, queryParamJSON, "atfh.bill_status", "billStatus", querySQL, queryParamMap, "=");
		SaafToolUtils.parperHbmParam(TtaTermsFrameHeaderHEntity_HI_RO.class, queryParamJSON, "atfh.year", "year", querySQL, queryParamMap, "=");
		SaafToolUtils.parperHbmParam(TtaTermsFrameHeaderHEntity_HI_RO.class, queryParamJSON, "atfh.team_Framework_Id", "teamFrameworkId", querySQL, queryParamMap, "=");
		querySQL.append(" order by atfh.team_framework_id ");
		return ttaTermsFrameHeaderHDAO_HI_RO.findPagination(querySQL,SaafToolUtils.getSqlCountString(querySQL), queryParamMap, pageIndex, pageRows);
	}
	@Override
	public JSONObject saveOrUpdateTraermsHAll(JSONObject paramsJSON, Integer userId) throws Exception {
		JSONArray clauseEY = (JSONArray) paramsJSON.get("clauseEY");
		JSONArray clauseEN = (JSONArray) paramsJSON.get("clauseEN");
		JSONObject result=new JSONObject();
		//保存条款头框架
		TtaTermsFrameHeaderHEntity_HI ttaTermsFrameHeaderHEntity = SaafToolUtils.setEntity(TtaTermsFrameHeaderHEntity_HI.class, (JSONObject)paramsJSON.get("clauseh"), ttaTermsFrameHeaderHDAO_HI, userId);
		TtaTermsFrameHeaderEntity_HI ttaTermsFrameHeaderEntity_HI = ttaTermsFrameHeaderDAO_HI.getById(ttaTermsFrameHeaderHEntity.getResouceId());
		TtaClauseTreeHEntity_HI ttaClauseTreeHEntity = SaafToolUtils.setEntity(TtaClauseTreeHEntity_HI.class, (JSONObject)paramsJSON.get("clausehT"), ttaClauseTreeHDAO_HI, userId);
		ttaTermsFrameHeaderHEntity.setFrameCode(String.valueOf(ttaTermsFrameHeaderHEntity.getYear()));
		ttaTermsFrameHeaderHDAO_HI.saveOrUpdate(ttaTermsFrameHeaderHEntity);
		if (null == ttaClauseTreeHEntity.getClauseId()) {
			if("02".equals(ttaClauseTreeHEntity.getBusinessType())) {
				String clauseBtCodeKey = "tta_clausehT_bt" + "0";
				int generateId = generateCodeServer.getGenerateId(clauseBtCodeKey);
				String clauseCode = ttaClauseTreeHEntity.getPaymentMethod() + String.format("%0" + 3 + "d", generateId);
				ttaClauseTreeHEntity.setClauseCode(clauseCode);
			}
			String clauseBvCodeKey = "tta_clausehT_bv" + "0";
			int generateId2 = generateCodeServer.getGenerateId(clauseBvCodeKey);
			ttaClauseTreeHEntity.setBusinessVersion(generateId2);
			String clauseTCodeKey= "tta_clausehT_code" + ttaTermsFrameHeaderHEntity.getTeamFrameworkId();
			int generateId3 = generateCodeServer.getGenerateId(clauseTCodeKey);
			String format = (new SimpleDateFormat("yyyyMMdd")).format(new Date());
			String clauseCodePre = ttaClauseTreeHEntity.getPaymentMethod() + String.format("%0" + 4 + "d", generateId3);
			String clauseTcode = format + clauseCodePre+ttaTermsFrameHeaderHEntity.getTeamFrameworkId() ;
			ttaClauseTreeHEntity.setCode(clauseTcode);
			ttaClauseTreeHEntity.setIsChange(Integer.valueOf(3));
			if(SaafToolUtils.isNullOrEmpty(ttaClauseTreeHEntity.getPCode())){
				ttaClauseTreeHEntity.setPCode("0");
			}
		}else{
			if(Integer.valueOf(1).equals(ttaClauseTreeHEntity.getDeleteFlag()) &&  (ttaClauseTreeHEntity.getIsChange() ==null || ttaClauseTreeHEntity.getIsChange().equals(Integer.valueOf(0)))){
				ttaClauseTreeHEntity.setIsChange(Integer.valueOf(1));//代表删除
			}else if(ttaClauseTreeHEntity.getIsChange() ==null || ttaClauseTreeHEntity.getIsChange().equals(Integer.valueOf(0))){
				ttaClauseTreeHEntity.setIsChange(Integer.valueOf(2));//代表删除
			}
		}

		//查找历史单据
		if(!SaafToolUtils.isNullOrEmpty(ttaClauseTreeHEntity.getOldOrderNo())){
			if(SaafToolUtils.isNullOrEmpty(ttaTermsFrameHeaderEntity_HI.getResourceId())){
				throw new IllegalArgumentException("不存在对应历史单据,所以不能填写 历史序号");

			}else{
				StringBuffer query = new StringBuffer("from  TtaClauseTreeEntity_HI   tt  \n" +
						"     where  tt.orderNo =:orderNo \n" +
						"     and   tt.teamFrameworkId =:teamFrameworkId \n" +
						"            and nvl(tt.deleteFlag,0) =0");
				Map  oldLineMap = new HashMap<String,Object>();
				oldLineMap.put("orderNo",ttaClauseTreeHEntity.getOldOrderNo());
				oldLineMap.put("teamFrameworkId",ttaTermsFrameHeaderEntity_HI.getResourceId());
				List<TtaClauseTreeEntity_HI> oldLines = ttaClauseTreeDAO_HI.findList(query,oldLineMap);
				if(oldLines.size() == 0){
					throw new IllegalArgumentException("填写历史序列不正确,请重新填写");
				}else if(oldLines.size() >1){
					throw new IllegalArgumentException("填写历史序列出现重复,请修改历史单据的序号");
				}else{
					ttaClauseTreeHEntity.setOldClauseId(oldLines.get(0).getClauseId());
				}
			}
		}

		//保存tree表
		ttaClauseTreeHEntity.setTeamFrameworkId(ttaTermsFrameHeaderHEntity.getTeamFrameworkId());
		ttaClauseTreeHDAO_HI.saveOrUpdate(ttaClauseTreeHEntity);
		//保存单位
		List<TtaCollectTypeLineHEntity_HI> ttaCollectTypeLineEntity_his = ttaCollectTypeLineHServer.saveOrUpdate(clauseEY,clauseEN, userId, ttaClauseTreeHEntity.getClauseId(),ttaTermsFrameHeaderHEntity.getTeamFrameworkId());

		result.put("clauseh", ttaTermsFrameHeaderHEntity);
		result.put("clausehT", ttaClauseTreeHEntity);
		result.put("clausehE", ttaCollectTypeLineEntity_his);
		return result;
	}

	@Override
	public JSONArray updateStatusH(JSONObject paramsJSON, Integer userId) throws Exception {
		Integer teamFrameworkIds = paramsJSON.getIntValue("id");//单据Id
		JSONObject headerObject = new JSONObject();
		headerObject.put("teamFrameworkId", teamFrameworkIds);
		JSONObject param=new JSONObject();
		JSONArray result=new JSONArray();
		List<TtaClauseTreeHEntity_HI> actList= new ArrayList<TtaClauseTreeHEntity_HI>();
		//查询表单工具
		List<TtaTermsFrameHeaderHEntity_HI> ttaTermsFrameHeaderHEntitys = ttaTermsFrameHeaderHDAO_HI.findByProperty("teamFrameworkId", headerObject.get("teamFrameworkId"));

		String orderStatus = null;//状态
		switch (paramsJSON.getString("status")) {
			case "REFUSAL":
				orderStatus = "DS01";
				ttaTermsFrameHeaderHEntitys.get(0).setBillStatus(orderStatus);
				ttaTermsFrameHeaderHDAO_HI.saveOrUpdate(ttaTermsFrameHeaderHEntitys.get(0));
				break;
			case "ALLOW":

				orderStatus = "DS03";
				ttaTermsFrameHeaderHEntitys.get(0).setBillStatus(orderStatus);
				ttaTermsFrameHeaderHEntitys.get(0).setPassDate(new Date());
				//调用存储过程
				Map<String, Object> paramsMap = new HashMap<String, Object>();
				Map<String, Object> resultMap = new HashMap<String, Object>();
				paramsMap.put("Id",paramsJSON.getIntValue("id"));
				paramsMap.put("userId",userId);
				resultMap = ttaTermsFrameHeaderHDAO.callOrderReturn(paramsMap);
				Integer xFlag = (Integer) resultMap.get("xFlag");
				String xMsg = (String) resultMap.get("xMsg");
				Integer newId = (Integer) resultMap.get("newId");
				//result.put("teamFrameworkId",newId);
				if (xFlag!=1) {
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); //
					throw new Exception(xMsg);
				};
				//审批通过的时候更新到条款框架表
/*				Date date = new Date();
				List<TtaTermsFrameHeaderEntity_HI> teamFrameworkId = ttaTermsFrameHeaderDAO_HI.findByProperty("teamFrameworkId", ttaTermsFrameHeaderHEntitys.get(0).getResouceId());
				teamFrameworkId.get(0).setLastUpdatedBy(userId);
				teamFrameworkId.get(0).setLastUpdateDate(date);
				teamFrameworkId.get(0).setYear(ttaTermsFrameHeaderHEntitys.get(0).getYear());
				teamFrameworkId.get(0).setBusinessVersion(ttaTermsFrameHeaderHEntitys.get(0).getBusinessVersion());
				teamFrameworkId.get(0).setEffectiveDate(ttaTermsFrameHeaderHEntitys.get(0).getEffectiveDate());
				teamFrameworkId.get(0).setExpirationDate(ttaTermsFrameHeaderHEntitys.get(0).getExpirationDate());
				teamFrameworkId.get(0).setFrameCode(ttaTermsFrameHeaderHEntitys.get(0).getFrameCode());
				ttaTermsFrameHeaderDAO_HI.saveOrUpdate(teamFrameworkId.get(0));

				//更新改动过的条款明细表   ischange 1
				JSONObject jsonObject = new JSONObject();
				Map<String, Object> queryParamMap = new HashMap<>();
				jsonObject.put("teamFrameworkId", headerObject.get("teamFrameworkId"));
				jsonObject.put("isChange", Integer.valueOf(0));
				StringBuffer querySQLA = new StringBuffer(TtaClauseTreeHEntity_HI_RO.QUERY_CLAUSE_TREE_CHANGE);
				SaafToolUtils.parperHbmParam(TtaClauseTreeEntity_HI_RO.class, jsonObject, "act.team_Framework_Id", "teamFrameworkId", querySQLA, queryParamMap, "=");
				SaafToolUtils.parperHbmParam(TtaClauseTreeEntity_HI_RO.class, jsonObject, "act.is_Change", "isChange", querySQLA, queryParamMap, "<>");
				List<TtaClauseTreeHEntity_HI_RO> TtaClauseTreeHEntity_HI_ROS = ttaClauseTreeHDAO_HI_RO.findList(querySQLA, queryParamMap);
				queryParamMap.clear();
				StringBuffer querySQL = new StringBuffer(TtaClauseTreeEntity_HI_RO.QUERY_CLAUSE_TREE_CHANGE);
				SaafToolUtils.parperHbmParam(TtaClauseTreeEntity_HI_RO.class, jsonObject, "acth.team_Framework_Id", "teamFrameworkId", querySQL, queryParamMap, "=");
				SaafToolUtils.parperHbmParam(TtaClauseTreeEntity_HI_RO.class, jsonObject, "acth.is_Change", "isChange", querySQL, queryParamMap, "<>");
				List<TtaClauseTreeEntity_HI_RO> listTtaClauseTree = ttaClauseTreeDAO_HI_RO.findList(querySQL, queryParamMap);
				for (int i =0;i<TtaClauseTreeHEntity_HI_ROS.size();i++){
					boolean flag =  true ;
					for (int j =0;j<listTtaClauseTree.size();j++){
						TtaClauseTreeEntity_HI ttaClauseTreeEntity_hi = new TtaClauseTreeEntity_HI();
						SaafBeanUtils.copyUnionProperties(listTtaClauseTree.get(i), ttaClauseTreeEntity_hi);
						if((null != TtaClauseTreeHEntity_HI_ROS.get(i).getResouceId()) && TtaClauseTreeHEntity_HI_ROS.get(i).getResouceId().equals(listTtaClauseTree.get(j).getClauseId())){
							flag = false ;
							ttaClauseTreeEntity_hi.setBusinessType(TtaClauseTreeHEntity_HI_ROS.get(i).getBusinessType());
							ttaClauseTreeEntity_hi.setPaymentMethod(TtaClauseTreeHEntity_HI_ROS.get(i).getPaymentMethod());
							ttaClauseTreeEntity_hi.setStatus(TtaClauseTreeHEntity_HI_ROS.get(i).getStatus());
							ttaClauseTreeEntity_hi.setOrderNo(TtaClauseTreeHEntity_HI_ROS.get(i).getOrderNo());
							ttaClauseTreeEntity_hi.setClauseId(listTtaClauseTree.get(i).getClauseId());
							ttaClauseTreeEntity_hi.setClauseCode(TtaClauseTreeHEntity_HI_ROS.get(i).getClauseCode());
							ttaClauseTreeEntity_hi.setClauseCn(TtaClauseTreeHEntity_HI_ROS.get(i).getClauseCn());
							ttaClauseTreeEntity_hi.setClauseEn(TtaClauseTreeHEntity_HI_ROS.get(i).getClauseEn());
							ttaClauseTreeEntity_hi.setIsLeaf(TtaClauseTreeHEntity_HI_ROS.get(i).getIsLeaf());
							ttaClauseTreeEntity_hi.setIsHierarchyShow(TtaClauseTreeHEntity_HI_ROS.get(i).getIsHierarchyShow());
							ttaClauseTreeEntity_hi.setGlobalValue(TtaClauseTreeHEntity_HI_ROS.get(i).getGlobalValue());
							ttaClauseTreeEntity_hi.setGlobalValueTta(TtaClauseTreeHEntity_HI_ROS.get(i).getGlobalValueTta());
							ttaClauseTreeEntity_hi.setGlobalValueFee(TtaClauseTreeHEntity_HI_ROS.get(i).getGlobalValueFee());
							ttaClauseTreeEntity_hi.setIsGlobalVariable(TtaClauseTreeHEntity_HI_ROS.get(i).getIsGlobalVariable());
							ttaClauseTreeEntity_hi.setIsGlobalFee(TtaClauseTreeHEntity_HI_ROS.get(i).getIsGlobalFee());
							ttaClauseTreeEntity_hi.setIsGlobalTta(TtaClauseTreeHEntity_HI_ROS.get(i).getIsGlobalTta());
							ttaClauseTreeEntity_hi.setExpressionValue(TtaClauseTreeHEntity_HI_ROS.get(i).getExpressionValue());
							ttaClauseTreeEntity_hi.setExpressionValueFee(TtaClauseTreeHEntity_HI_ROS.get(i).getExpressionValueFee());
							ttaClauseTreeEntity_hi.setExpressionValueTta(TtaClauseTreeHEntity_HI_ROS.get(i).getExpressionValueTta());
							ttaClauseTreeEntity_hi.setDeleteFlag(TtaClauseTreeHEntity_HI_ROS.get(i).getDeleteFlag());
							ttaClauseTreeEntity_hi.setPCode(TtaClauseTreeHEntity_HI_ROS.get(i).getPCode());
							ttaClauseTreeDAO_HI.saveOrUpdate(ttaClauseTreeEntity_hi);
							//新增修改单位
							List<TtaCollectTypeLineHEntity_HI> TtaCollectTypeLineHEntityHiList = ttaCollectTypeLineHDAO_HI.findByProperty("clauseId", TtaClauseTreeHEntity_HI_ROS.get(i).getClauseId());

							List<TtaCollectTypeLineEntity_HI> TtaCollectTypeLineEntityiList = ttaCollectTypeLineDAO_HI.findByProperty("clauseId", TtaClauseTreeHEntity_HI_ROS.get(i).getResouceId());

							for(TtaCollectTypeLineHEntity_HI v:TtaCollectTypeLineHEntityHiList){
								boolean flag2 =  true ;
								for(TtaCollectTypeLineEntity_HI tctleh: TtaCollectTypeLineEntityiList ){
									if(null != v.getResouceId() &&  v.getResouceId().equals(tctleh.getCollectTypeId())){
										flag2 = false ;
										tctleh.setDeleteFlag(v.getDeleteFlag());
										tctleh.setUnitValue(v.getUnitValue());
										tctleh.setIsEnable(v.getIsEnable());
										tctleh.setStandardValue(v.getStandardValue());
										tctleh.setOperatorUserId(userId);
										ttaCollectTypeLineDAO_HI.saveOrUpdate(tctleh);
									} else {
										continue ;
									}
								}
								if(flag2){
									TtaCollectTypeLineEntity_HI ttaCollectTypeLineEntity_hi = new TtaCollectTypeLineEntity_HI();
									SaafBeanUtils.copyUnionProperties(v, ttaCollectTypeLineEntity_hi);
									ttaCollectTypeLineEntity_hi.setClauseId(ttaClauseTreeEntity_hi.getClauseId());
									ttaCollectTypeLineEntity_hi.setOperatorUserId(userId);
									ttaCollectTypeLineDAO_HI.saveOrUpdate(ttaCollectTypeLineEntity_hi);
								}
							}

						}else{
							continue;
						}
					}
					//为TRUE就是新增
					if(flag){
						TtaClauseTreeEntity_HI ttaClauseTreeEntity_hr = new TtaClauseTreeEntity_HI();
						SaafBeanUtils.copyUnionProperties(TtaClauseTreeHEntity_HI_ROS.get(i),ttaClauseTreeEntity_hr);
						ttaClauseTreeEntity_hr.setClauseId(null);
						ttaClauseTreeEntity_hr.setVersionNum(null);
						ttaClauseTreeEntity_hr.setTeamFrameworkId(teamFrameworkId.get(0).getTeamFrameworkId());
						ttaClauseTreeDAO_HI.saveOrUpdate(ttaClauseTreeEntity_hr);

						//新增修改单位
						List<TtaCollectTypeLineHEntity_HI> TtaCollectTypeLineHEntityHiList = ttaCollectTypeLineHDAO_HI.findByProperty("clauseId", TtaClauseTreeHEntity_HI_ROS.get(i).getClauseId());
						for(TtaCollectTypeLineHEntity_HI v:TtaCollectTypeLineHEntityHiList){
							TtaCollectTypeLineEntity_HI ttaCollectTypeLineEntity_hi = new TtaCollectTypeLineEntity_HI();
							SaafBeanUtils.copyUnionProperties(v, ttaCollectTypeLineEntity_hi);
							ttaCollectTypeLineEntity_hi.setClauseId(ttaClauseTreeEntity_hr.getClauseId());
							ttaCollectTypeLineEntity_hi.setOperatorUserId(userId);
							ttaCollectTypeLineEntity_hi.setCollectTypeId(null);
							ttaCollectTypeLineDAO_HI.saveOrUpdate(ttaCollectTypeLineEntity_hi);
						}
					}


				}*/
				break;
			case "DRAFT":
				orderStatus = "DS01";
				ttaTermsFrameHeaderHEntitys.get(0).setBillStatus(orderStatus);
				ttaTermsFrameHeaderHDAO_HI.saveOrUpdate(ttaTermsFrameHeaderHEntitys.get(0));
				break;
			case "APPROVAL":
				orderStatus = "DS02";
				ttaTermsFrameHeaderHEntitys.get(0).setBillStatus(orderStatus);
				ttaTermsFrameHeaderHDAO_HI.saveOrUpdate(ttaTermsFrameHeaderHEntitys.get(0));
				break;
			case "CLOSED":
				orderStatus = "DS10";
				ttaTermsFrameHeaderHEntitys.get(0).setBillStatus(orderStatus);
				ttaTermsFrameHeaderHDAO_HI.saveOrUpdate(ttaTermsFrameHeaderHEntitys.get(0));
				break;

		}

		ttaTermsFrameHeaderHEntitys.get(0).setLastUpdatedBy(userId);
		ttaTermsFrameHeaderHEntitys.get(0).setLastUpdateDate(new Date());
		ttaTermsFrameHeaderHDAO_HI.saveOrUpdate(ttaTermsFrameHeaderHEntitys.get(0));
		return result;
	}
}
