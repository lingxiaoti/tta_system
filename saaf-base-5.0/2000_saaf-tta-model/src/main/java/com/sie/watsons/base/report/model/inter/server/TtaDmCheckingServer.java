package com.sie.watsons.base.report.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.bean.UserSessionBean;
import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.util.BigDecimalUtils;
import com.sie.saaf.common.util.SaafBeanUtils;
import com.sie.saaf.common.util.SaafDateUtils;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.report.model.entities.TtaDmCheckingEntity_HI;
import com.sie.watsons.base.report.model.entities.TtaDmCheckingEntity_HI_MODEL;
import com.sie.watsons.base.report.model.entities.TtaDmFullLineEntity_HI;
import com.sie.watsons.base.report.model.entities.readonly.TtaDmCheckingEntity_HI_RO;
import com.sie.watsons.base.report.model.inter.ITtaDmChecking;
import com.sie.watsons.base.report.utils.EasyExcelUtil;
import com.yhg.base.utils.StringUtils;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisCluster;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

@Component("ttaDmCheckingServer")
public class TtaDmCheckingServer extends BaseCommonServer<TtaDmCheckingEntity_HI> implements ITtaDmChecking{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaDmCheckingServer.class);

	@Autowired
	private BaseCommonDAO_HI<TtaDmCheckingEntity_HI> ttaDmCheckingDAO_HI;

	@Autowired
	private BaseViewObject<TtaDmCheckingEntity_HI_RO> ttaDmCheckingDAO_HI_RO;


	public TtaDmCheckingServer() {
		super();
	}

    @Autowired
    private JedisCluster jedisCluster;

	@SuppressWarnings("all")
	@Override
	public void updateImportDMInfo(JSONObject jsonObject, MultipartFile file) throws Exception {
		try {
			Map<String,Object> result = EasyExcelUtil.readExcel(file, TtaDmCheckingEntity_HI_MODEL.class,0);
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
			ttaDmCheckingDAO_HI.updateBatchJDBC("tta_dm_checking",TtaDmCheckingEntity_HI_MODEL.class,  datas);
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
			throw  new Exception(e);
		}
	}


	@Override
	public int saveImportCwInfo(JSONObject queryParamJSON, MultipartFile file, UserSessionBean sessionBean) throws Exception {
		return 0;
	}

	@SuppressWarnings("all")
	@Override
	public Pagination<TtaDmCheckingEntity_HI_RO> findDmInfo(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows, UserSessionBean sessionBean) throws Exception {
		StringBuffer sql = new StringBuffer();
		StringBuffer countSql = new StringBuffer();
		Map<String, Object> map = new HashMap<String, Object>();
		String flag = queryParamJSON.getString("flag");
		if ("process".equals(flag)) {
			SaafToolUtils.validateJsonParms(queryParamJSON, "reportProcessHeaderId");
			sql.append(TtaDmCheckingEntity_HI_RO.QUERY);
			SaafToolUtils.parperParam(queryParamJSON, "tdc.process_id", "reportProcessHeaderId", sql, map, "=");
		} else {
			String batchCodes = queryParamJSON.getString("batchCode");
			String ids = null;
			if (StringUtils.isNotEmpty(batchCodes)) {
				ids = "'"+ org.apache.commons.lang.StringUtils.join(batchCodes.split(","), "','") + "'";
			}
			if (!("45".equals(sessionBean.getUserType()))) {
				sql.append(TtaDmCheckingEntity_HI_RO.getNoBicSql(ids));
				map.put("userId", sessionBean.getUserId());
			} else {
				sql.append(TtaDmCheckingEntity_HI_RO.QUERY);
			}
			//年份导出数据
			if (queryParamJSON.getString("substrYear") == null) {
				//校验数据
				SaafToolUtils.validateJsonParms(queryParamJSON, "batchCode");
				sql.append(" and tdc.batch_code in (" + ids + ")");
			}
		}
		String groupCode = queryParamJSON.getString("groupCode");
		if (StringUtils.isNotEmpty(groupCode)) {
            sql.append(" and tdc.group_code in ('").append(String.join("','", groupCode.split(","))).append("') ");
        }
		String deptCode = queryParamJSON.getString("deptCode");
		if (StringUtils.isNotEmpty(deptCode)) {
			sql.append(" and tdc.dept_code in ('").append(String.join("','", deptCode.split(","))).append("') ");
		}
		SaafToolUtils.parperParam(queryParamJSON, "tdc.duration_period", "promotionSection", sql, map, "fulllike");
		SaafToolUtils.parperParam(queryParamJSON, "tdc.item_nbr", "itemCode", sql, map, "fulllike");
		SaafToolUtils.parperParam(queryParamJSON, "tdc.brand_cn", "brandCn", sql, map, "fulllike");
		SaafToolUtils.parperParam(queryParamJSON, "tdc.prior_vendor_code", "priorVendorCode", sql, map, "fulllike");
		SaafToolUtils.parperParam(queryParamJSON, "trph.status", "status", sql, map, "=");//审批状态
		SaafToolUtils.parperParam(queryParamJSON, "tdc.purchase", "purchase", sql, map, "=");//采购行动
		SaafToolUtils.parperParam(queryParamJSON, "to_char(tdc.creation_date, 'yyyy')", "substrYear", sql, map, "=");//采购行动
		SaafToolUtils.changeQuerySort(queryParamJSON, sql, " tdc.dm_id desc", false);
		Pagination<TtaDmCheckingEntity_HI_RO> resultList = ttaDmCheckingDAO_HI_RO.findPagination(sql, countSql, map, pageIndex, pageRows);
		return resultList;
	}

	@Override
	public Pagination<TtaDmCheckingEntity_HI_RO>  findProcessDmInfo(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows, UserSessionBean sessionBean) throws Exception {
		StringBuffer sql = new StringBuffer();
		Map<String, Object> queryMap = new HashMap<String, Object>();
		sql.append(TtaDmCheckingEntity_HI_RO.getQueryProcess(sessionBean.getUserName(), sessionBean.getUserId()));
        SaafToolUtils.parperParam(queryParamJSON, "tdc.batch_code", "batchCode", sql, queryMap, "=");//批次号
		Pagination<TtaDmCheckingEntity_HI_RO> resultList = ttaDmCheckingDAO_HI_RO.findPagination(sql, SaafToolUtils.getSqlCountString(sql), queryMap, pageIndex, pageRows);
		return resultList;
	}

	@Override
	public Pagination<TtaDmCheckingEntity_HI_RO>  findProcessSummaryDmInfo(JSONObject queryParamJSON, UserSessionBean sessionBean) throws Exception {
		Pagination<TtaDmCheckingEntity_HI_RO> pagination = ttaDmCheckingDAO_HI_RO.findPagination(TtaDmCheckingEntity_HI_RO.getDmProcessSummary(queryParamJSON.getString("batchCode"),sessionBean.getUserName(), sessionBean.getUserId()), new HashMap<String, Object>(), 0, Integer.MAX_VALUE);
		return pagination;
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

	@Override
	public Pagination<TtaDmCheckingEntity_HI_RO> findNotExist(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
		StringBuffer sql = new StringBuffer();
		sql.append(TtaDmCheckingEntity_HI_RO.QUERY_EXISTS);
		StringBuffer countSql = new StringBuffer();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("batchCode",queryParamJSON.getString("batchCode"));
		SaafToolUtils.parperParam(queryParamJSON, "tom.group_code", "groupCode", sql, map, "fulllike");
		SaafToolUtils.parperParam(queryParamJSON, "tom.group_desc", "groupDesc", sql, map, "fulllike");
		SaafToolUtils.parperParam(queryParamJSON, "tom.dept_code", "deptCode", sql, map, "fulllike");
		SaafToolUtils.parperParam(queryParamJSON, "tom.dept_desc", "deptDesc", sql, map, "fulllike");
		SaafToolUtils.parperParam(queryParamJSON, "tom.brand_cn", "brandCn", sql, map, "fulllike");
		sql.append(" group by     tom.group_code,tom.group_desc,tom.dept_code,tom.dept_desc,tom.brand_cn ");
		countSql = SaafToolUtils.getSqlCountString(sql) ;
		Pagination<TtaDmCheckingEntity_HI_RO> resultList =ttaDmCheckingDAO_HI_RO.findPagination(sql,countSql,map,pageIndex,pageRows);
		return resultList;
	}

	@Override
	public TtaDmCheckingEntity_HI_RO findChangeVender(JSONObject queryParamJSON) {
		//获取加成比例，计费金额，应收金额（不含加成），rateCard 年份，合同条款
		TtaDmCheckingEntity_HI_RO entityHiRo = JSON.parseObject(queryParamJSON.toJSONString(), TtaDmCheckingEntity_HI_RO.class);
		String priorVendorCode = entityHiRo.getPriorVendorCode();
		String acceptUnit = entityHiRo.getAcceptUnit();
		Integer effectiveAreaCnt = entityHiRo.getEffectiveAreaCnt();
		Integer contractYear = Integer.parseInt(entityHiRo.getContractYear());
		String groupCode = entityHiRo.getGroupCode();
		//查找单位
		List<Map<String, Object>> list = ttaDmCheckingDAO_HI.queryNamedSQLForList(TtaDmCheckingEntity_HI_RO.getUnitByName(acceptUnit), new HashMap<String, Object>());
		TtaDmCheckingEntity_HI_RO result = ttaDmCheckingDAO_HI_RO.get(TtaDmCheckingEntity_HI_RO.getChangeVenderSql(priorVendorCode, contractYear, effectiveAreaCnt, groupCode, list.get(0).get("LOOKUP_CODE") + ""));
		Assert.notNull(result,"没有找到该供应商的主从供应商信息！");
		entityHiRo.setReceiveAmount(result.getReceiveAmount());//应收金额（含加成）
		entityHiRo.setNoReceiveAmount(result.getNoReceiveAmount());//应收金额（不含加成）
		entityHiRo.setChargeMoney(result.getChargeMoney());//计费金额
		entityHiRo.setContractYear(result.getContractYear());//年份
		entityHiRo.setContractTerms(result.getContractTerms());//条款
		entityHiRo.setAdditionRate(result.getAdditionRate());//加成比例
		entityHiRo.setChargeStandards(result.getChargeStandards());//收费标准
		return entityHiRo;
	}

	private void checkProcesss(JSONObject paramsJson) {
        String batchCode = paramsJson.getString("batchId");
        HashMap<String, Object> queryMap = new HashMap<>();
        queryMap.put("batchCode", batchCode);
        List<Map<String, Object>> listMap = ttaDmCheckingDAO_HI.queryNamedSQLForList(TtaDmCheckingEntity_HI_RO.getCheckGroupDm(), queryMap);
        JSONArray paramsList = paramsJson.getJSONArray("paramsList");
        for (int idx = 0; idx < paramsList.size(); idx ++) {
            JSONObject jsonObject = paramsList.getJSONObject(idx);
            String paramGroupCode = jsonObject.getString("groupCode");
            int paramGroupDmCnt = jsonObject.getInteger("groupDmCnt");
            String  groupDesc = jsonObject.getString("groupDesc");

            for (Map<String, Object> map: listMap) {
                String groupCode = map.get("groupCode") + "";
                if (!paramGroupCode.equals(groupCode)) {
                    continue;
                }
                int cnt = Integer.parseInt(map.get("cnt") + "");
                Assert.isTrue(paramGroupDmCnt == cnt,  groupDesc + "部门还有" + (cnt - paramGroupDmCnt) + "条单据未到此节点，暂不能审批/驳回！");
            }
        }
    }

    @Override
	public JSONObject deleteImportDmInfo(JSONObject queryParamJSON) throws Exception {
		return null;
	}

	@Override
	public JSONObject saveOrUpdateFind(JSONObject paramsJSON, int userId) throws Exception {
		return null;
	}

	@Override
	public List<TtaDmCheckingEntity_HI> saveOrUpdateALL(JSONArray paramsJSON, int userId) throws Exception {
		ArrayList<TtaDmCheckingEntity_HI> objects = new ArrayList<>();
		for(int i = 0 ;i<paramsJSON.size();i++){
			JSONObject  json = (JSONObject)paramsJSON.get(i) ;
			TtaDmCheckingEntity_HI instance = SaafToolUtils.setEntity(TtaDmCheckingEntity_HI.class, json, ttaDmCheckingDAO_HI, userId);
			objects.add(instance);
		}
		ttaDmCheckingDAO_HI.saveOrUpdateAll(objects);
		return objects;
	}

	@Override
	@SuppressWarnings("all")
	public List<TtaDmCheckingEntity_HI> saveOrUpdateSplitALL(JSONArray paramsJSON, int userId, JSONObject currentRow) throws Exception {
		ArrayList<TtaDmCheckingEntity_HI> objects = new ArrayList<>();
		//被拆分记录
		TtaDmCheckingEntity_HI parentCheckingEntity = SaafToolUtils.setEntity(TtaDmCheckingEntity_HI.class, currentRow, ttaDmCheckingDAO_HI, userId);
		//校验数据
		if(null == parentCheckingEntity ){
			throw new IllegalArgumentException("当前行不存在,无法拆分，请重新尝试");
		}
		//对【需采购确认收取金额（不含加成）】用户录入的值进行求和。
		BigDecimal totalNoUnConfirmAmt = new BigDecimal("0");//需采购确认收取金额（不含加成），用于计算总比例值
		for (int i = 0 ;i<paramsJSON.size();i++){
			JSONObject  json = (JSONObject)paramsJSON.get(i) ;
			totalNoUnConfirmAmt = totalNoUnConfirmAmt.add(json.getBigDecimal("noUnconfirmAmount")); //需采购确认收取金额（不含加成）,直接写入表不需要计算
		}
        StringBuffer stringBuffer = null;
		//需要拆分的供应商列表信息
		for(int i = 0 ;i<paramsJSON.size();i++){
		    stringBuffer = new StringBuffer();
			JSONObject  json = (JSONObject)paramsJSON.get(i) ;
			TtaDmCheckingEntity_HI instance = new TtaDmCheckingEntity_HI();
			//记录最上级拆分父级
			if(SaafToolUtils.isNullOrEmpty(parentCheckingEntity.getParentId())){
				//第一次拆分
				instance.setParentId(parentCheckingEntity.getDmId());
			} else {
				//N次拆分
				instance.setParentId(parentCheckingEntity.getParentId());
			}
			SaafBeanUtils.copyUnionProperties(parentCheckingEntity, instance);
            parentCheckingEntity.setStatus(0);
            instance.setStatus(1);
			//置空字段：实收金额,采购确认代垫费用,代垫费用表编号,借记单编号
			instance.setReceipts(null);//实收金额
			instance.setAdvanceAmount(null); //采购确认代垫费用
			instance.setAdvanceCode(null);//代垫费用表编号
			instance.setDebitOrderCode(null);//借记单编号
            instance.setPurchase(null);// 采购行动
            instance.setExemptionReason(null); //豁免原因_1
            instance.setExemptionReason2(null); //豁免原因_2
			instance.setTransferInPerson(null);//转办人(转入)
			instance.setTransferInDate(null);//转办时间(转入)
			instance.setTransferOutPerson(null);//转办人(转出)
			instance.setTransferOutDate(null);//转办时间(转出)
			instance.setTransferLastPerson(null);//转办人(LAST_转出)
			instance.setTransferLastDate(null);// 转办时间(LAST_转出)
			// ="DM"-促销期间-DM页码-位置-生效区域-图位-部门-品牌-供应商编号
			stringBuffer.append("DM-" + instance.getDurationPeriod() + "-" + instance.getLocationCode() + "-" + instance.getGroupDesc()
					+ "-" + instance.getBrandCn()+ "-" + json.getString("priorVendorCode"));
			instance.setParentId(SaafToolUtils.isNullOrEmpty(parentCheckingEntity.getParentId()) ? parentCheckingEntity.getDmId() : parentCheckingEntity.getParentId());
			instance.setParentVendorCode(SaafToolUtils.isNullOrEmpty(parentCheckingEntity.getParentId()) ? parentCheckingEntity.getPriorVendorCode() : parentCheckingEntity.getParentVendorCode());
			instance.setParentVendorName(SaafToolUtils.isNullOrEmpty(parentCheckingEntity.getParentId()) ? parentCheckingEntity.getPriorVendorName() : parentCheckingEntity.getParentVendorName());
			instance.setPriorVendorCode(json.getString("priorVendorCode"));//当前拆分的供应商编码
			instance.setPriorVendorName(json.getString("priorVendorName"));//当前拆分的供应商名称
			instance.setNoUnconfirmAmount(json.getBigDecimal("noUnconfirmAmount")); //需采购确认收取金额（不含加成）
            //父级加成比例跟拆分后的加成比例一样
            BigDecimal additionRate = parentCheckingEntity.getAdditionRate();
            instance.setAdditionRate(additionRate);

            /**
             * 计费金额：CHARGE_MONEY (拆分后不变化)
             * 应收金额(含加成)：RECEIVE_AMOUNT
             * 应收金额(不含加成)：NO_RECEIVE_AMOUNT
             * 需采购确认收取金额(不含加成)：NO_UNCONFIRM_AMOUNT
             * 需采购确认收取金额(含加成)：UNCONFIRM_AMOUNT
             */
            //按照需采购确认收取金额（不含加成）求出每行的比例值
            double noUnconfirmAmt = instance.getNoUnconfirmAmount().doubleValue();
            Double rateAmount = BigDecimalUtils.divide(noUnconfirmAmt, totalNoUnConfirmAmt.doubleValue());

			//1.应收金额（含加成）处理：按照需采购确认收取金额（不含加成）拆分 应收金额(含)*(当行需采购确认收取金额（不含加成）/需采购确认收取金额（不含加成）)
			BigDecimal parentReceiveAmount = parentCheckingEntity.getReceiveAmount();//父级应收金额
			BigDecimal childRecceiveAmt = new BigDecimal(BigDecimalUtils.multiply(parentReceiveAmount.doubleValue(), rateAmount)).setScale(0, BigDecimal.ROUND_HALF_UP);
			instance.setReceiveAmount(childRecceiveAmt);//通过比例计算下级的应收金额（含加成）

            //2.应收金额（不含加成）处理：
            BigDecimal parentNoReceiveAmount = parentCheckingEntity.getNoReceiveAmount();
            BigDecimal childNoRecceiveAmt = new BigDecimal(BigDecimalUtils.multiply(parentNoReceiveAmount.doubleValue(), rateAmount)).setScale(0, BigDecimal.ROUND_HALF_UP);
            instance.setNoReceiveAmount(childNoRecceiveAmt);

            //需采购确认收取金额（含加成）= 需采购确认收取金额（不含加成）*(1+加成比例)
            Double unconfirmAmount = BigDecimalUtils.multiply(instance.getNoUnconfirmAmount().doubleValue(), BigDecimalUtils.add(new BigDecimal(1), additionRate).doubleValue());
            instance.setUnconfirmAmount(new BigDecimal(unconfirmAmount).setScale(0, BigDecimal.ROUND_HALF_UP));
			instance.setContent(stringBuffer.toString());
			instance.setOperatorUserId(userId);
			objects.add(instance);
		}
		ttaDmCheckingDAO_HI.saveOrUpdateAll(objects);
		ttaDmCheckingDAO_HI.saveOrUpdate(parentCheckingEntity);
		return objects;
	}

	@SuppressWarnings("all")
	@Override
	public List<TtaDmCheckingEntity_HI> saveOrUpdateTransferALL(JSONObject paramsJSON, int userId) throws Exception {
		ArrayList<TtaDmCheckingEntity_HI> objects = new ArrayList<>();
		JSONArray jsonArrayLine = paramsJSON.getJSONArray("lines");
		Integer personIds = paramsJSON.getInteger("personId");
		Date date = new Date();
		for (int i = 0; i < jsonArrayLine.size(); i++) {
			JSONObject json = (JSONObject) jsonArrayLine.get(i);
			TtaDmCheckingEntity_HI instance = SaafToolUtils.setEntity(TtaDmCheckingEntity_HI.class, json, ttaDmCheckingDAO_HI, userId);
			instance.setTransferInDate(date);
			instance.setTransferOutDate(instance.getTransferOutDate() == null ? date : instance.getTransferOutDate());
			instance.setTransferLastDate(date);
			instance.setTransferInPerson(personIds);//转给谁
			instance.setTransferOutPerson(instance.getTransferOutPerson() == null ? userId : instance.getTransferOutPerson());
			instance.setTransferLastPerson(userId);//当前操作用户
			instance.setOperatorUserId(userId);
			objects.add(instance);
		}
		ttaDmCheckingDAO_HI.saveOrUpdateAll(objects);
		return objects;
	}


	public static void main(String[] args) {
		boolean nullOrEmpty = SaafToolUtils.isNullOrEmpty(true);
		System.out.println(nullOrEmpty);
	}
}
