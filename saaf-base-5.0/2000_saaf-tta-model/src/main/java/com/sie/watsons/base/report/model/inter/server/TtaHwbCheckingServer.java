package com.sie.watsons.base.report.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

import com.sie.saaf.common.bean.UserSessionBean;
import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.saaf.common.services.GenerateCodeService;
import com.sie.saaf.common.util.SaafBeanUtils;
import com.sie.saaf.common.util.SaafDateUtils;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.report.model.dao.TtaHwbCheckingDAO_HI;
import com.sie.watsons.base.report.model.entities.TtaHwbBaseLineEntity_HI;
import com.sie.watsons.base.report.model.entities.TtaHwbCheckingEntity_HI_MODEL;
import com.sie.watsons.base.report.model.entities.TtaReportHeaderEntity_HI;
import com.sie.watsons.base.report.model.entities.readonly.TtaHwbCheckingEntity_HI_RO;
import com.sie.watsons.base.report.model.inter.ITtaHwbBaseLine;
import com.sie.watsons.base.report.utils.EasyExcelUtil;
import com.yhg.base.utils.StringUtils;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yhg.base.utils.SToolUtils;
import com.sie.watsons.base.report.model.entities.TtaHwbCheckingEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.report.model.inter.ITtaHwbChecking;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisCluster;

@Component("ttaHwbCheckingServer")
public class TtaHwbCheckingServer extends BaseCommonServer<TtaHwbCheckingEntity_HI> implements ITtaHwbChecking{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaHwbCheckingServer.class);

	@Autowired
	private BaseCommonDAO_HI<TtaHwbCheckingEntity_HI> ttaHwbCheckingDAO_HI;

	@Autowired
	private BaseViewObject<TtaHwbCheckingEntity_HI_RO> ttaHwbCheckingDAO_HI_RO;

	@Autowired
	private TtaHwbCheckingDAO_HI ttaHwbCheckingDAO ;

	@Autowired
	private ITtaHwbBaseLine ttaHwbBaseLineServer;

	@Autowired
	private ViewObject<TtaReportHeaderEntity_HI> ttaReportHeaderDAO_HI;

	@Autowired
	private GenerateCodeService codeService;
	@Autowired
	private JedisCluster jedisCluster;

	public TtaHwbCheckingServer() {
		super();
	}

	/**
	 * OI批量导入
	 * @param queryParamJSON
	 * @return
	 * @throws Exception
	 */
	@Override
	public int saveImportHwbMajorInfo(JSONObject queryParamJSON, MultipartFile file, UserSessionBean sessionBean) throws Exception{
		jedisCluster.setex(sessionBean.getCertificate(),3600,"{status,'U'}");
		JSONArray errList = new JSONArray();
		List<Map<String,Object>> list = null ;
		if(file != null ){
			Map<String,Object> result = null ;
			Boolean flag = (Boolean) result.get("flag");
			if(flag){
				list = (List<Map<String,Object>>) result.get("datas");
			}else{
				JSONObject errJson2 = new JSONObject();
				errJson2.put("ROW_NUM",'0');
				errJson2.put("ERR_MESSAGE","表头信息错误");
				errList.add(errJson2) ;
			}
		}

		for(int i=0;i<list.size();i++){
			Map<String,Object> json = list.get(i);
			JSONObject errJson = new JSONObject();
			json.put("CREATED_BY",queryParamJSON.getInteger("varUserId")) ;
			json.put("CREATION_DATE",new Date()) ;
			json.put("LAST_UPDATED_BY",queryParamJSON.getInteger("varUserId")) ;
			json.put("LAST_UPDATE_DATE",new Date()) ;
			json.put("LAST_UPDATE_LOGIN",queryParamJSON.getInteger("varUserId")) ;
			json.put("VERSION_NUM",0) ;
			String msgStr = "";
			try {
				if(!"".equals(msgStr)){
					errJson.put("ROW_NUM",json.get("ROW_NUM"));
					errJson.put("ERR_MESSAGE",msgStr);
					errList.add(errJson);
				}else{
					//	json.put("operatorUserId",queryParamJSON.get("operatorUserId"));
					//	super.saveOrUpdate(json);
				}
			}catch (Exception e){
				msgStr += ("有异常,数据有误.");
				errJson.put("ROW_NUM",json.get("ROW_NUM"));
				errJson.put("ERR_MESSAGE",msgStr);
				errList.add(errJson);
				e.printStackTrace();
			}
		}
		if (!errList.isEmpty()){
			throw new Exception(errList.toJSONString());
		}else{
			ttaHwbCheckingDAO.saveSeqBatchJDBC("TTA_OSD_MONTHLY_CHECKING",list,"OSD_ID","SEQ_TTA_OSD_MONTHLY_CHECKING.NEXTVAL",jedisCluster,sessionBean);
			jedisCluster.setex(sessionBean.getCertificate(),3600,"{status:'S',currentStage:'完成',orderNum:"+"'无'}");

		}
		return list.size();
	}

	/**
	 *
	 * @param queryParamJSON
	 * @param pageIndex
	 * @param pageRows
	 * @return
	 * @throws Exception
	 */
	@Override
	public Pagination<TtaHwbCheckingEntity_HI_RO> findHwbMajorInfo(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows, UserSessionBean sessionBean) throws Exception{



		StringBuffer sql = new StringBuffer();
		StringBuffer countSql = new StringBuffer();
		Map<String,Object> map = new HashMap<String,Object>();
		String flag = queryParamJSON.getString("flag");
		//流程单据查询
		if("process".equals(flag)){
			SaafToolUtils.validateJsonParms(queryParamJSON, "reportProcessHeaderId");
			sql.append(TtaHwbCheckingEntity_HI_RO.QUERY) ;
			SaafToolUtils.parperParam(queryParamJSON, "tom.process_id", "reportProcessHeaderId", sql, map, "=");

		}else{
			//校验数据
			SaafToolUtils.validateJsonParms(queryParamJSON, "batchCode");
			//  非 BIC用户
			if( !("45".equals(sessionBean.getUserType()))  && !(Integer.valueOf(1).equals(queryParamJSON.getInteger("varUserId")) )){
				sql.append(TtaHwbCheckingEntity_HI_RO.QUERY_NO_BIC) ;
				map.put("userId",sessionBean.getUserId()) ;
				queryParamJSON.remove("orderBy");
				getSearch(queryParamJSON,sql,map);
				SaafToolUtils.parperParam(queryParamJSON, "tom.process_Status", "processStatus", sql, map, "=");
				sql.append(" and  nvl(tom.batch_code,'-1') =:batchCode ") ;
				countSql = SaafToolUtils.getSqlCountString(sql) ;
				SaafToolUtils.changeQuerySort(queryParamJSON, sql, " tom.hwb_id desc", false);
			}else{
				sql.append(TtaHwbCheckingEntity_HI_RO.QUERY) ;
				sql.append(" and  nvl(tom.batch_code,'-1') =:batchCode ") ;
				queryParamJSON.remove("orderBy");
				SaafToolUtils.parperParam(queryParamJSON, "TRPH.status", "processStatus", sql, map, "=");
				getSearch(queryParamJSON,sql,map);
				countSql = SaafToolUtils.getSqlCountString(sql) ;
				SaafToolUtils.changeQuerySort(queryParamJSON, sql, " tom.hwb_id desc", false);
			}

			map.put("batchCode",queryParamJSON.getString("batchCode")) ;
		}

		Pagination<TtaHwbCheckingEntity_HI_RO> resultList =ttaHwbCheckingDAO_HI_RO.findPagination(sql,countSql,map,pageIndex,pageRows);
		return resultList;
	}
	@Override
	public Pagination<TtaHwbCheckingEntity_HI_RO> findNotExist(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) throws Exception{
		StringBuffer sql = new StringBuffer();
		sql.append(TtaHwbCheckingEntity_HI_RO.QUERY_EXISTS);
		StringBuffer countSql = new StringBuffer();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("batchCode",queryParamJSON.getString("batchCode"));
		SaafToolUtils.parperParam(queryParamJSON, "tom.group_code", "groupCode", sql, map, "fulllike");
		SaafToolUtils.parperParam(queryParamJSON, "tom.group_desc", "groupDesc", sql, map, "fulllike");
		SaafToolUtils.parperParam(queryParamJSON, "tom.dept_code", "deptCode", sql, map, "fulllike");
		SaafToolUtils.parperParam(queryParamJSON, "tom.brand_cn", "brandCn", sql, map, "fulllike");
		sql.append(" group by     tom.group_code,tom.group_desc,tom.dept_code,tom.dept_desc,tom.brand_cn ");

		countSql = SaafToolUtils.getSqlCountString(sql) ;
		Pagination<TtaHwbCheckingEntity_HI_RO> resultList =ttaHwbCheckingDAO_HI_RO.findPagination(sql,countSql,map,pageIndex,pageRows);
		return resultList;
	};
	private  void getSearch(JSONObject queryParamJSON,StringBuffer sql,Map<String,Object> map){
		SaafToolUtils.parperParam(queryParamJSON, "tom.promotion_section", "promotionSection", sql, map, "fulllike");
		SaafToolUtils.parperParam(queryParamJSON, "tom.prior_vendor_code", "priorVendorCode", sql, map, "fulllike");
		SaafToolUtils.parperParam(queryParamJSON, "tom.prior_vendor_name", "priorVendorName", sql, map, "fulllike");
		SaafToolUtils.parperParam(queryParamJSON, "tom.hwb_type", "hwbType", sql, map, "=");
		SaafToolUtils.parperParam(queryParamJSON, "tom.brand_cn", "brandCn", sql, map, "fulllike");
		SaafToolUtils.parperParam(queryParamJSON, "tom.group_desc", "groupDesc", sql, map, "fulllike");
		SaafToolUtils.parperParam(queryParamJSON, "tom.dept_code", "deptCode", sql, map, "in");


	}
	/**
	 *
	 * @param queryParamJSON
	 * @return
	 * @throws Exception
	 */
	public JSONObject deleteImportHwbMajorInfo(JSONObject queryParamJSON) throws Exception{
		JSONObject result = new JSONObject();
		if(!SaafToolUtils.isNullOrEmpty(queryParamJSON.getString("flag"))){
			if("pl".equals(queryParamJSON.getString("flag"))){
				if(queryParamJSON.getString("month")!=null || !"".equals(queryParamJSON.getString("month"))){
					List<TtaHwbCheckingEntity_HI> ttaPogSpaceLineEntityList = ttaHwbCheckingDAO_HI.findByProperty("promotion_section", queryParamJSON.getString("month"));
					if(ttaPogSpaceLineEntityList.size() == 0){
						throw new IllegalArgumentException("当前促销区间不存在,删除失败");
					}
					ttaHwbCheckingDAO_HI.executeSqlUpdate("delete from tta_pog_space_line t where t.promotion_section ='" + queryParamJSON.getString("month")+"'");
				}
			}else{
				if(queryParamJSON.getInteger("pogSpaceLineId")!=null || !"".equals(queryParamJSON.getInteger("pogSpaceLineId"))){
					ttaHwbCheckingDAO_HI.delete(queryParamJSON.getInteger("pogSpaceLineId"));
				}
			}
		}
		return result;
	}

	@Override
	public JSONObject saveOrUpdateFind(JSONObject paramsJSON, int userId) throws Exception {
		JSONObject jsonObject = new JSONObject();

		//校验数据
		SaafToolUtils.validateJsonParms(paramsJSON, "promotionSection");

			String batchCode = codeService.getTtaOiCw("HWB") ;
			Date date = new Date();
		    String dateString = SaafDateUtils.convertDateToString(date);
			//插入基础数据
			ttaHwbCheckingDAO_HI.executeSqlUpdate(TtaHwbCheckingEntity_HI_RO.getInsertReportBase(batchCode,userId,paramsJSON.getString("promotionSection"),null,dateString)) ;

		   //插入上个月的将收取
		    ttaHwbCheckingDAO_HI.executeSqlUpdate(TtaHwbCheckingEntity_HI_RO.getUpdateReportBaseLastTimes(batchCode,userId,paramsJSON.getString("promotionSection"),dateString)) ;
			//更新基础表
			ttaHwbCheckingDAO_HI.executeSqlUpdate(TtaHwbCheckingEntity_HI_RO.getUpdateReportBasePog(userId,paramsJSON.getString("promotionSection"))) ;

		   //备份数据
		   ttaHwbCheckingDAO_HI.executeSqlUpdate(TtaHwbCheckingEntity_HI_RO.getUpdateReportBaseHistory(userId,paramsJSON.getString("promotionSection"))) ;

			//头表插入一条数据
			TtaReportHeaderEntity_HI ttaReportHeaderEntity_hi = new TtaReportHeaderEntity_HI();
			ttaReportHeaderEntity_hi.setOperatorUserId(userId);
			ttaReportHeaderEntity_hi.setStatus("DS01");
			ttaReportHeaderEntity_hi.setIsPublish("N");
			ttaReportHeaderEntity_hi.setBatchId(batchCode);
			ttaReportHeaderEntity_hi.setReportType("HWB");
            ttaReportHeaderEntity_hi.setPromotionSection(paramsJSON.getString("promotionSection"));
			ttaReportHeaderDAO_HI.saveOrUpdate(ttaReportHeaderEntity_hi);
			jsonObject.put("report",ttaReportHeaderEntity_hi);


		return jsonObject;
	}

	@Override
	public List<TtaHwbCheckingEntity_HI> saveOrUpdateALL(JSONObject paramsJSON, int userId) throws Exception {
		ArrayList<TtaHwbCheckingEntity_HI> objects = new ArrayList<>();
		JSONArray save = paramsJSON.getJSONArray("save");
		for(int i = 0 ;i<save.size();i++){
			JSONObject  json = (JSONObject)save.get(i) ;
			TtaHwbCheckingEntity_HI instance = SaafToolUtils.setEntity(TtaHwbCheckingEntity_HI.class, json, ttaHwbCheckingDAO_HI, userId);
			objects.add(instance);
		}
		ttaHwbCheckingDAO_HI.saveOrUpdateAll(objects);
		return objects;
	}

	/**
	 *转办
	 * @param paramsJSON
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<TtaHwbCheckingEntity_HI> saveOrUpdateTransferALL(JSONObject paramsJSON, int userId) throws Exception {
		ArrayList<TtaHwbCheckingEntity_HI> objects = new ArrayList<>();
		JSONArray jsonArrayLine = paramsJSON.getJSONArray("lines");
		Integer personIds = paramsJSON.getInteger("personId");
		Date date = new Date();
		for(int i = 0 ;i<jsonArrayLine.size();i++){
			JSONObject  json = (JSONObject)jsonArrayLine.get(i) ;
			TtaHwbCheckingEntity_HI instance = SaafToolUtils.setEntity(TtaHwbCheckingEntity_HI.class, json, ttaHwbCheckingDAO_HI, userId);
			instance.setTransferInDate(date);
			instance.setTransferOutDate(instance.getTransferOutDate() == null ?date:instance.getTransferOutDate());
			instance.setTransferLastDate(date);
			instance.setTransferInPerson(personIds);
			instance.setTransferOutPerson(instance.getTransferOutPerson() == null ? userId:instance.getTransferOutPerson());
			instance.setTransferLastPerson(userId);
			instance.setOperatorUserId(userId);
			objects.add(instance);
		}
		ttaHwbCheckingDAO_HI.saveOrUpdateAll(objects);
		return objects;
	}

	@Override
	public List<TtaHwbCheckingEntity_HI> saveOrUpdateSplitALL(JSONArray paramsJSON, int userId,JSONObject currentRow) throws Exception {
		ArrayList<TtaHwbCheckingEntity_HI> objects = new ArrayList<>();
		StringBuffer stringBuffer = new StringBuffer();
		BigDecimal totalBigD = new BigDecimal("0");
		BigDecimal basezZero = new BigDecimal("0");
		BigDecimal totalBigDNoIncludeLast = new BigDecimal("0");
		BigDecimal totalBigDIncludeLast = new BigDecimal("0");
		BigDecimal baseBig = new BigDecimal("1");

		boolean isExistCurrentVendorCode = false ;

		TtaHwbCheckingEntity_HI ttaHwbCheckingEntity_HI = SaafToolUtils.setEntity(TtaHwbCheckingEntity_HI.class, currentRow, ttaHwbCheckingDAO_HI, userId);
		//校验数据
		if(null == ttaHwbCheckingEntity_HI ){
			throw new IllegalArgumentException("当前行不存在,无法拆分，请重新尝试");
		}
		TtaHwbCheckingEntity_HI baseInstance = new TtaHwbCheckingEntity_HI();
		SaafBeanUtils.copyUnionProperties(ttaHwbCheckingEntity_HI, baseInstance);


		for (int i = 0 ;i<paramsJSON.size();i++){
			JSONObject  json = (JSONObject)paramsJSON.get(i) ;
			totalBigD = totalBigD.add(json.getBigDecimal("noUnconfirmAmount"));
		}
		if(1 == paramsJSON.size() ){
			JSONObject  json = (JSONObject)paramsJSON.get(0) ;
			ttaHwbCheckingEntity_HI.setPriorVendorCode(json.getString("priorVendorCode"));
			ttaHwbCheckingEntity_HI.setPriorVendorName(json.getString("priorVendorName"));
			//采购确认金额
			ttaHwbCheckingEntity_HI.setNoUnconfirmAmount(json.getBigDecimal("noUnconfirmAmount"));
			stringBuffer.append(ttaHwbCheckingEntity_HI.getPromotionSection());

			if ("Award".equals(ttaHwbCheckingEntity_HI.getHwbType())){
				stringBuffer.append("-").append(ttaHwbCheckingEntity_HI.getDescriptionCn()) ;
			}
			stringBuffer.append("-").append(ttaHwbCheckingEntity_HI.getGroupDesc())
					.append("-").append(ttaHwbCheckingEntity_HI.getBrandCn()).append("-").append(json.getString("priorVendorCode")) ;
			if(SaafToolUtils.isNullOrEmpty(ttaHwbCheckingEntity_HI.getCollect())){
				ttaHwbCheckingEntity_HI.setDifference(basezZero);
			}else if("TTA".equals(ttaHwbCheckingEntity_HI.getCollect())){
				ttaHwbCheckingEntity_HI.setUnconfirmAmount(
						json.getBigDecimal("noUnconfirmAmount").multiply( (new BigDecimal("100")).add(ttaHwbCheckingEntity_HI.getAdditionRate()))
								.divide(new BigDecimal("100"),0,BigDecimal.ROUND_HALF_UP)
				);
			}else{
				ttaHwbCheckingEntity_HI.setUnconfirmAmount(json.getBigDecimal("noUnconfirmAmount"));
			}
			int compareInt = ttaHwbCheckingEntity_HI.getUnconfirmAmount()
											        .subtract(ttaHwbCheckingEntity_HI.getReceiveAmount()==null?basezZero:ttaHwbCheckingEntity_HI.getReceiveAmount())
													.compareTo(BigDecimal.ZERO);
			if(0 > compareInt){
				ttaHwbCheckingEntity_HI.setDifference(ttaHwbCheckingEntity_HI.getUnconfirmAmount()
										.subtract(ttaHwbCheckingEntity_HI.getReceiveAmount()==null?basezZero:ttaHwbCheckingEntity_HI.getReceiveAmount()));
			}else{
				ttaHwbCheckingEntity_HI.setDifference(new BigDecimal("0"));
			}
			ttaHwbCheckingEntity_HI.setContent(stringBuffer.toString());
			ttaHwbCheckingEntity_HI.setOperatorUserId(userId);
			objects.add(ttaHwbCheckingEntity_HI);
		}else{
			for(int i = 0 ;i<paramsJSON.size();i++){
				JSONObject  json = (JSONObject)paramsJSON.get(i) ;
				stringBuffer.setLength(0);
				TtaHwbCheckingEntity_HI instance = new TtaHwbCheckingEntity_HI();
				//是否是第一次拆分
				if(SaafToolUtils.isNullOrEmpty(ttaHwbCheckingEntity_HI.getParentId())){
					ttaHwbCheckingEntity_HI.setStatus(0);
					SaafBeanUtils.copyUnionProperties(ttaHwbCheckingEntity_HI, instance);
					instance.setAdditionRate(json.getBigDecimal("additionRate")==null?basezZero:json.getBigDecimal("additionRate"));
				}else{
					if( ( ttaHwbCheckingEntity_HI.getPriorVendorCode().equals(json.getString("priorVendorCode")) || paramsJSON.size()-1 == i)
							&& !isExistCurrentVendorCode){

						instance = ttaHwbCheckingEntity_HI ;
						isExistCurrentVendorCode = true ;
					}else{
						SaafBeanUtils.copyUnionProperties(ttaHwbCheckingEntity_HI, instance);
						instance.setAdditionRate(json.getBigDecimal("additionRate")==null?basezZero:json.getBigDecimal("additionRate"));
					}
				}
				//instance.getPromotionLocation()促销位置
				stringBuffer.append(instance.getPromotionSection())
						.append("-").append(ttaHwbCheckingEntity_HI.getDescriptionCn())
						.append("-").append(instance.getGroupDesc())
						.append("-").append(instance.getBrandCn())
						.append("-").append(json.getString("priorVendorCode")) ;

				instance.setParentId(SaafToolUtils.isNullOrEmpty(ttaHwbCheckingEntity_HI.getParentId())?ttaHwbCheckingEntity_HI.getHwbId():ttaHwbCheckingEntity_HI.getParentId());
				instance.setParentVendorCode(SaafToolUtils.isNullOrEmpty(ttaHwbCheckingEntity_HI.getParentId())?ttaHwbCheckingEntity_HI.getPriorVendorCode():ttaHwbCheckingEntity_HI.getParentVendorCode());
				instance.setParentVendorName(SaafToolUtils.isNullOrEmpty(ttaHwbCheckingEntity_HI.getParentId())?ttaHwbCheckingEntity_HI.getPriorVendorName():ttaHwbCheckingEntity_HI.getParentVendorName());

				instance.setPriorVendorCode(json.getString("priorVendorCode"));
				instance.setPriorVendorName(json.getString("priorVendorName"));
				//采购确认金额
				instance.setNoUnconfirmAmount(json.getBigDecimal("noUnconfirmAmount"));

				//拆分后的应收金额
				BigDecimal currentNoReceiveAmount = baseInstance.getNoReceiveAmount().multiply(json.getBigDecimal("noUnconfirmAmount")).divide(totalBigD,0,BigDecimal.ROUND_HALF_UP);
				BigDecimal currentReceiveAmount = (baseInstance.getReceiveAmount()==null?basezZero:baseInstance.getReceiveAmount()).multiply(json.getBigDecimal("noUnconfirmAmount")).divide(totalBigD,0,BigDecimal.ROUND_HALF_UP);
				//应收金额

				if(paramsJSON.size()-1 == i){
					//应收金额(不含加成)
					instance.setNoReceiveAmount((baseInstance.getNoReceiveAmount()==null?basezZero:baseInstance.getNoReceiveAmount()).subtract(totalBigDNoIncludeLast));
					//应收金额(含加成)
					instance.setReceiveAmount((baseInstance.getReceiveAmount()==null?basezZero:baseInstance.getReceiveAmount()).subtract(totalBigDIncludeLast));

				}else{
					//应收金额(不含加成)
					instance.setNoReceiveAmount(currentNoReceiveAmount);
					//应收金额(含加成)
					instance.setReceiveAmount(currentReceiveAmount);
					totalBigDNoIncludeLast = totalBigDNoIncludeLast.add(currentNoReceiveAmount) ;
					totalBigDIncludeLast = totalBigDIncludeLast.add(currentReceiveAmount) ;

				}
				if("TTA".equals(instance.getCollect())){
					instance.setUnconfirmAmount(
							json.getBigDecimal("noUnconfirmAmount").multiply( (new BigDecimal("100")).add(instance.getAdditionRate() == null ? basezZero:instance.getAdditionRate()))
									.divide(new BigDecimal("100"),0,BigDecimal.ROUND_HALF_UP)
					);
				}else{
					instance.setUnconfirmAmount(json.getBigDecimal("noUnconfirmAmount"));
				}
				int compareInt = instance.getUnconfirmAmount().subtract(instance.getReceiveAmount()==null?basezZero:instance.getReceiveAmount()).compareTo(BigDecimal.ZERO);
				if(0 > compareInt){
					instance.setDifference(instance.getUnconfirmAmount().subtract(instance.getReceiveAmount()==null?basezZero:instance.getReceiveAmount()));
				}else{
					instance.setDifference(new BigDecimal("0"));
				}

				instance.setContent(stringBuffer.toString());
				instance.setOperatorUserId(userId);
				instance.setStatus(1);
				objects.add(instance);
			}
		}

		ttaHwbCheckingDAO_HI.saveOrUpdateAll(objects);
		ttaHwbCheckingDAO_HI.saveOrUpdate(ttaHwbCheckingEntity_HI);
		return objects;
	}

	/**
	 * 查询CW 报表的SUNMARY
	 * @param queryParamJSON
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<TtaHwbCheckingEntity_HI_RO> findHwbMajorSummaryList(JSONObject queryParamJSON) throws Exception{
		StringBuffer sql = new StringBuffer();
		Map<String,Object> map = new HashMap<String,Object>();
		SaafToolUtils.validateJsonParms(queryParamJSON,"batchCode");
		sql.append(TtaHwbCheckingEntity_HI_RO.SUMMARY_QUERY) ;
		SaafToolUtils.parperParam(queryParamJSON, "tcc.batch_code", "batchCode", sql, map, "in");
		sql.append(" group by tcc.group_desc") ;
		List<TtaHwbCheckingEntity_HI_RO> list = ttaHwbCheckingDAO_HI_RO.findList(sql, map);
		return list;
	}

	/**
	 * 报表导出  ABOI模板
	 * @param queryParamJSON
	 * @param pageIndex
	 * @param pageRows
	 * @return
	 * @throws Exception
	 */
	@Override
	public Pagination<TtaHwbCheckingEntity_HI_RO> findExportPagination(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) throws Exception{
		SaafToolUtils.validateJsonParms(queryParamJSON,"batchCode");
		StringBuffer sql = new StringBuffer();
		StringBuffer countSql = new StringBuffer();
		sql.append(TtaHwbCheckingEntity_HI_RO.ABOI_TEMPLATES_QUERY) ;
		Map<String,Object> map = new HashMap<String,Object>();
		SaafToolUtils.parperParam(queryParamJSON, "tom.batch_code", "batchCode", sql, map, "in");
		Pagination<TtaHwbCheckingEntity_HI_RO> resultList = ttaHwbCheckingDAO_HI_RO.findPagination(sql, SaafToolUtils.getSqlCountString(sql), map, pageIndex, pageRows);
		return resultList;
	}

	@SuppressWarnings("all")
	@Override
	public void updateImportHWBInfo(JSONObject jsonObject, MultipartFile file) throws Exception {
		try {
			Map<String,Object> result = EasyExcelUtil.readExcel(file, TtaHwbCheckingEntity_HI_MODEL.class,0);
			List<Map<String, Object>> datas = (List<Map<String, Object>>)result.get("datas");
			if (datas != null && !datas.isEmpty()) {
				datas.forEach(item->{
					item.put("CREATED_BY",jsonObject.getInteger("userId"));
					item.put("LAST_UPDATED_BY",jsonObject.getInteger("userId"));
					item.put("LAST_UPDATE_LOGIN",jsonObject.getInteger("userId"));
					item.put("LAST_UPDATE_DATE", new Date());
					item.put("CREATION_DATE", new Date());
				});
			}
			ttaHwbCheckingDAO.updateBatchJDBC("tta_hwb_checking", TtaHwbCheckingEntity_HI_MODEL.class,  datas);
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
			throw  new Exception(e);
		}
	}

	@Override
	public Pagination<TtaHwbCheckingEntity_HI_RO>  findProcessSummaryHwbInfo(JSONObject queryParamJSON, UserSessionBean sessionBean) throws Exception {
		Pagination<TtaHwbCheckingEntity_HI_RO> pagination = ttaHwbCheckingDAO_HI_RO.findPagination(TtaHwbCheckingEntity_HI_RO.getHwbProcessSummary(queryParamJSON.getString("batchCode"),sessionBean.getUserName(), sessionBean.getUserId()), new HashMap<String, Object>(), 0, Integer.MAX_VALUE);
		return pagination;
	}

	@Override
	public Pagination<TtaHwbCheckingEntity_HI_RO>  findProcessHwbInfo(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows, UserSessionBean sessionBean) throws Exception {
		StringBuffer sql = new StringBuffer();
		Map<String, Object> queryMap = new HashMap<String, Object>();
		sql.append(TtaHwbCheckingEntity_HI_RO.getQueryProcess(sessionBean.getUserName(), sessionBean.getUserId()));
		Pagination<TtaHwbCheckingEntity_HI_RO> resultList = ttaHwbCheckingDAO_HI_RO.findPagination(sql, SaafToolUtils.getSqlCountString(sql), queryMap, pageIndex, pageRows);
		return resultList;
	}

	@Override
	public void findCheckGroupCount(JSONObject paramsJson, UserSessionBean sessionBean) {
		LOGGER.info("当前登录的用户信息：{}" , SaafToolUtils.toJson(sessionBean));
		String isCheckProcess = jedisCluster.hget("GLOBAL_REDIS_CACHE","IS_CHECK_PROCESS");
		LOGGER.info("流程校验开关参数值：" + isCheckProcess);
		if ("N".equalsIgnoreCase(isCheckProcess)) {
			return;
		}
        /* 用户需求：
        Trading Manager审批节点提示当前部门仍有单据未审批。
        建议：仅在ATD审批节点需要校验部门是否提交全部单据（Food部门需要在Senior Manager节点校验,Health在TC节点）。
        后续优化：各审批节点配置校验不同的单据批量审批完成情况。*/
		//用户类型： Senior Trading Manager 高级经理（userType:25），TC(userType:30), ATD(userType:40)
		//部门信息：1:Health and Fitness, 5： Food and Sundries
		LOGGER.info("当前登录的用户信息：{}" , SaafToolUtils.toJson(sessionBean));
		String userType = sessionBean.getUserType();
		String deptCode = sessionBean.getGroupCode();
		if (StringUtils.isBlank(userType) || StringUtils.isBlank(deptCode)) {
			Assert.isTrue(false, "sorry，不能审批该单据，请联系管理员配置你的用户类型或小部门信息！");
		}
		if ("40".equalsIgnoreCase(userType)) {
			//ATD审批节点需要校验部门是否提交全部单据
			checkProcesss(paramsJson);
		}
		if ("5".equalsIgnoreCase(deptCode) && "25".equalsIgnoreCase(userType)) {
			//Food部门需要在Senior Manager节点校验
			checkProcesss(paramsJson);
		}
		if ("1".equalsIgnoreCase(deptCode) && "30".equalsIgnoreCase(userType)) {
			//Health在TC节点校验
			checkProcesss(paramsJson);
		}
	}

	public void checkProcesss(JSONObject paramsJson) {
		String batchCode = paramsJson.getString("batchId");
		HashMap<String, Object> queryMap = new HashMap<>();
		queryMap.put("batchCode", batchCode);
		List<Map<String, Object>> listMap = ttaHwbCheckingDAO_HI.queryNamedSQLForList(TtaHwbCheckingEntity_HI_RO.getCheckGroupHwb(), queryMap);
		JSONArray paramsList = paramsJson.getJSONArray("paramsList");
		for (int idx = 0; idx < paramsList.size(); idx ++) {
			JSONObject jsonObject = paramsList.getJSONObject(idx);
			//String paramGroupCode = jsonObject.getString("groupCode");
			int paramGroupDmCnt = jsonObject.getInteger("groupHwbCnt");
			String  paramGroupDesc = jsonObject.getString("groupDesc");

			for (Map<String, Object> map: listMap) {
				String groupDesc = map.get("groupDesc") + "";
				if (!paramGroupDesc.equals(groupDesc)) {
					continue;
				}
				int cnt = Integer.parseInt(map.get("cnt") + "");
				Assert.isTrue(paramGroupDmCnt == cnt,  groupDesc + "部门还有" + (cnt - paramGroupDmCnt) + "条单据未到此节点，暂不能审批/驳回！");
			}
		}
	}

}
