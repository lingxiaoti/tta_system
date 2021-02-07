package com.sie.watsons.base.newbreed.model.inter.server;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.newbreed.model.dao.TtaNewbreedSetHeaderDAO_HI;
import com.sie.watsons.base.newbreed.model.entities.TtaNewbreedSetHeaderEntity_HI;
import com.sie.watsons.base.newbreed.model.entities.TtaNewbreedSetLineEntity_HI;
import com.sie.watsons.base.newbreed.model.entities.readonly.TtaNewbreedSetHeaderEntity_HI_RO;
import com.sie.watsons.base.newbreed.model.entities.readonly.TtaNewbreedSetLineEntity_HI_RO;
import com.sie.watsons.base.newbreed.model.inter.ITtaNewbreedSetHeader;
import com.sie.watsons.base.newbreed.model.inter.ITtaNewbreedSetLine;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

@Component("ttaNewbreedSetHeaderServer")
public class TtaNewbreedSetHeaderServer extends BaseCommonServer<TtaNewbreedSetHeaderEntity_HI> implements ITtaNewbreedSetHeader {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaNewbreedSetHeaderServer.class);

	@Autowired
	private ViewObject<TtaNewbreedSetHeaderEntity_HI> ttaNewbreedSetHeaderDAO_HI;

	@Autowired
	private ViewObject<TtaNewbreedSetLineEntity_HI> ttaNewbreedSetLineDAO_HI;

	@Autowired
	private TtaNewbreedSetHeaderDAO_HI ttaNewbreedSetHeaderDAO;

	@Autowired
	private BaseViewObject<TtaNewbreedSetHeaderEntity_HI_RO> ttaNewbreedSetHeaderDAO_HI_RO;

	@Autowired
	private BaseViewObject<TtaNewbreedSetLineEntity_HI_RO> ttaNewbreedSetLineDAO_HI_RO;
	@Autowired
	private ITtaNewbreedSetLine ttaNewbreedSetLineServer;

	public TtaNewbreedSetHeaderServer() {
		super();
	}

	/**
	 * 查询新品种列表（分页）
	 *
	 * @param queryParamJSON 对象属性的JSON格式
	 * @param pageIndex      页码
	 * @param pageRows       每页查询记录数
	 * @return Pagination<BaseJobEntity_HI_RO>
	 */
	@Override
	public Pagination<TtaNewbreedSetHeaderEntity_HI_RO> findTtaNewbreedSetHeaderPagination(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
		Map<String, Object> queryParamMap = new HashMap<>();
		StringBuffer querySQL = new StringBuffer(TtaNewbreedSetHeaderEntity_HI_RO.QUERY_NEW_BREED_LIST);

		SaafToolUtils.parperHbmParam(TtaNewbreedSetHeaderEntity_HI_RO.class, queryParamJSON, "tnh.breed_name", "breedName", querySQL, queryParamMap, "=");
		SaafToolUtils.parperHbmParam(TtaNewbreedSetHeaderEntity_HI_RO.class, queryParamJSON, "tnh.newbreed_year", "newbreedYear", querySQL, queryParamMap, "like");
		SaafToolUtils.parperHbmParam(TtaNewbreedSetHeaderEntity_HI_RO.class, queryParamJSON, "tnh.dept_name", "deptName", querySQL, queryParamMap, "like");
		SaafToolUtils.parperHbmParam(TtaNewbreedSetHeaderEntity_HI_RO.class, queryParamJSON, "tnh.dept_code", "deptCode", querySQL, queryParamMap, "like");
		SaafToolUtils.parperHbmParam(TtaNewbreedSetHeaderEntity_HI_RO.class, queryParamJSON, "tne.dept_code", "lineDeptCode", querySQL, queryParamMap, "like");
		SaafToolUtils.parperHbmParam(TtaNewbreedSetHeaderEntity_HI_RO.class, queryParamJSON, "tne.dept_name", "lineDeptName", querySQL, queryParamMap, "like");
		SaafToolUtils.parperHbmParam(TtaNewbreedSetHeaderEntity_HI_RO.class, queryParamJSON, "tne.range", "range", querySQL, queryParamMap, "=");
		SaafToolUtils.parperHbmParam(TtaNewbreedSetHeaderEntity_HI_RO.class, queryParamJSON, "tnh.newbreed_set_id", "newbreedSetId", querySQL, queryParamMap, "=");
		querySQL.append(" order by tnh.newbreed_year desc, tnh.dept_code asc ,tne.dept_code asc");
		return ttaNewbreedSetHeaderDAO_HI_RO.findPagination(querySQL,SaafToolUtils.getSqlCountString(querySQL), queryParamMap, pageIndex, pageRows);
	}

	/**
	 * 查询新品种详情页
	 *
	 * @param queryParamJSON 对象属性的JSON格式
	 * @param pageIndex      页码
	 * @param pageRows       每页查询记录数
	 * @return Pagination<BaseJobEntity_HI_RO>
	 */
	@Override
	public JSONObject findTtaNewbreedOne(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
		Map<String, Object> queryParamMap = new HashMap<>();
		JSONObject jsonObject = new JSONObject();
		SaafToolUtils.validateJsonParms(queryParamJSON, "newbreedSetId");
		StringBuffer querySQL = new StringBuffer(TtaNewbreedSetHeaderEntity_HI_RO.QUERY_NEW_BREED_FIND);
		SaafToolUtils.parperHbmParam(TtaNewbreedSetHeaderEntity_HI_RO.class, queryParamJSON, "tns.newbreed_set_id", "newbreedSetId", querySQL, queryParamMap, "=");
		TtaNewbreedSetHeaderEntity_HI_RO ttaNewbreedSetHeaderEntity_hi_ro = ttaNewbreedSetHeaderDAO_HI_RO.get(querySQL,queryParamMap);

		StringBuffer queryLineSQL = new StringBuffer(TtaNewbreedSetLineEntity_HI_RO.QUERY_FIND);
		HashMap<String,Object> hashMap = new HashMap<String,Object>();
		queryParamJSON.put("deleteFlag",Integer.valueOf(0)) ;
		SaafToolUtils.parperHbmParam(TtaNewbreedSetLineEntity_HI_RO.class, queryParamJSON, "tnb.newbreed_set_id", "newbreedSetId", queryLineSQL, hashMap, "=");
		SaafToolUtils.parperHbmParam(TtaNewbreedSetLineEntity_HI_RO.class, queryParamJSON, "tnb.delete_flag", "deleteFlag", queryLineSQL, hashMap, "=");
		SaafToolUtils.changeQuerySort(queryParamJSON, queryLineSQL, "tnb.dept_code asc,range asc", false);
		List<TtaNewbreedSetLineEntity_HI_RO> lineList = ttaNewbreedSetLineDAO_HI_RO.findList(queryLineSQL,hashMap);
		jsonObject.put("nbHearder",ttaNewbreedSetHeaderEntity_hi_ro);
		jsonObject.put("nbLine",lineList);
		return jsonObject;
	}

	/**
	 * 新增&修改新品种信息
	 *
	 * @param paramsJSON 对象属性的JSON格式
	 * @param userId     当前用户ID
	 * @return 返回实体行
	 * @throws Exception 抛出异常
	 */
	@Override
	public JSONObject saveOrUpdateTtaNewbreedSetALLInfo(JSONObject paramsJSON, Integer userId) throws Exception {
		JSONObject jsonObject = new JSONObject();
		JSONObject nbHeader  =(JSONObject)paramsJSON.get("nbHearder") ;
		Map<String,Object> map =  new HashMap<String,Object>();

		Map<String, Object> paramsMap = new HashMap<String, Object>();
		JSONArray nbLine  =(JSONArray)paramsJSON.get("nbLine") ;
		SaafToolUtils.validateJsonParms(nbHeader, "breedName","deptCode","newbreedYear");
			map.put("breedName",nbHeader.getString("breedName"));
			map.put("deptCode",nbHeader.getString("deptCode"));
			map.put("newbreedYear",nbHeader.getInteger("newbreedYear"));
			map.put("deleteFlag",0);
			List<TtaNewbreedSetHeaderEntity_HI> byProperty = ttaNewbreedSetHeaderDAO_HI.findByProperty(map);
			if(byProperty.size()>0){
				if(SaafToolUtils.isNullOrEmpty(nbHeader.getInteger("newbreedSetId"))){
					throw new IllegalArgumentException("新品种属性名称,部门,年度 组合在一起必须唯一 " );
				}else{
					if(!nbHeader.getInteger("newbreedSetId").equals(byProperty.get(0).getNewbreedSetId())){
						throw new IllegalArgumentException("新品种属性名称,部门,年度 组合在一起必须唯一 " );
					}
				}

			}


		//校验部门
//		StringBuffer stringBuffer = new StringBuffer();
//		stringBuffer.append(TtaNewbreedSetHeaderEntity_HI_RO.TTA_DEPT_COUNT);
//		stringBuffer.append(" and  bd .department_code in (");
//		//HashSet hashSet = new HashSet<String>();
//		for(int i=0 ;i<nbLine.size();i++){
//			stringBuffer.append( "'"+ ((JSONObject)nbLine.get(i)).getString("deptCode") +"'," ) ;
//		}
//		stringBuffer.append("'-1')");
//		stringBuffer.append(" and bdp.department_code !='") ;
//		stringBuffer.append(nbHeader.getString("deptCode")) ;
//		stringBuffer.append("'") ;
		//TtaNewbreedSetHeaderEntity_HI_RO TtaNewbreedSetHeaderEntity_HI_RO = ttaNewbreedSetHeaderDAO_HI_RO.get(stringBuffer, paramsMap);
		//if(!SaafToolUtils.isNullOrEmpty(TtaNewbreedSetHeaderEntity_HI_RO)){
		//	int  nums =	TtaNewbreedSetHeaderEntity_HI_RO.getCounts().intValue();
		//	if(nums>0){
			//	throw new IllegalArgumentException("部门和单位行的部门存在不是上下级关系的,!请修改!");
		//	}
		//}




		TtaNewbreedSetHeaderEntity_HI ttaNewbreedSetHeaderEntity = SaafToolUtils.setEntity(TtaNewbreedSetHeaderEntity_HI.class, nbHeader, ttaNewbreedSetHeaderDAO_HI, userId);
		ttaNewbreedSetHeaderEntity.setDeleteFlag(paramsJSON.getInteger("deleteFlag"));
		ttaNewbreedSetHeaderDAO_HI.saveOrUpdate(ttaNewbreedSetHeaderEntity);
		List<TtaNewbreedSetLineEntity_HI> ttaNewbreedSetLineEntity_his = ttaNewbreedSetLineServer.saveOrUpdateTtaNewbreedSetLineInfo(nbLine, userId, ttaNewbreedSetHeaderEntity.getNewbreedSetId(), paramsJSON.getInteger("deleteFlag"));
		jsonObject.put("nbHearder",ttaNewbreedSetHeaderEntity);
		jsonObject.put("nbLine",ttaNewbreedSetLineEntity_his);
		return jsonObject;
	}

	public JSONObject saveOrUpdateCopy(JSONObject paramsJSON, Integer userId) throws Exception {
		JSONObject param=new JSONObject();

		//调用存储过程
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		paramsMap.put("Id",Integer.valueOf(-1));
		paramsMap.put("deptCode",paramsJSON.getString("deptCode"));
		paramsMap.put("userId",userId);
		resultMap = ttaNewbreedSetHeaderDAO.callOrderCopy(paramsMap);
		Integer xFlag = (Integer) resultMap.get("xFlag");
		String xMsg = (String) resultMap.get("xMsg");
		//Integer newId = (Integer) resultMap.get("newId");
		JSONObject result=new JSONObject(resultMap);
		//result.put("teamFrameworkId",newId);
		if (xFlag!=1) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); //
			throw new Exception(xMsg);
		};

		return result;
	}


}
