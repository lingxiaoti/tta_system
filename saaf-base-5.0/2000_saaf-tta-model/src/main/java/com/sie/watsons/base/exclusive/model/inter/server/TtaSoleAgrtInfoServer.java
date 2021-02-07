package com.sie.watsons.base.exclusive.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.deepoove.poi.XWPFTemplate;
import com.sie.saaf.common.bean.ResultFileEntity;
import com.sie.saaf.common.model.entities.readonly.BaseAttachmentEntity_HI_RO;
import com.sie.saaf.common.model.inter.IBaseAttachment;
import com.sie.saaf.common.model.inter.IFastdfs;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.exclusive.model.entities.TtaSoleAgrtEntity_HI;
import com.sie.watsons.base.exclusive.model.entities.TtaSoleItemEntity_HI;
import com.sie.watsons.base.exclusive.model.entities.readonly.TtaSoleAgrtInfoEntity_HI_RO;
import com.sie.watsons.base.exclusive.model.entities.readonly.TtaSoleItemEntity_HI_RO;
import com.sie.watsons.base.exclusive.model.entities.readonly.TtaSoleSupplierEntity_HI_RO;
import com.sie.watsons.base.exclusive.model.inter.ITtaSoleAgrt;
import com.sie.watsons.base.exclusive.model.inter.ITtaSoleItem;
import com.sie.watsons.base.item.model.entities.TtaItemEntity_HI;
import com.sie.watsons.base.item.model.entities.readonly.TtaItemEntity_HI_RO;
import com.sie.watsons.base.item.model.inter.ITtaItem;
import com.sie.watsons.base.proposal.model.entities.TtaBrandplnLEntity_HI;
import com.sie.watsons.base.proposal.model.entities.readonly.TtaBrandplnLEntity_HI_RO;
import com.sie.watsons.base.rule.model.entities.readonly.RuleFileTemplateEntity_HI_RO;
import com.sie.watsons.base.rule.model.entities.readonly.TempRuleDefEntity_HI_RO;
import com.sie.watsons.base.rule.model.inter.IRuleFileTemplate;
import com.sie.watsons.base.rule.services.TempRuleDefService;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.yhg.hibernate.core.paging.Pagination;
import jdk.nashorn.internal.runtime.regexp.JoniRegExp;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections4.Put;
import org.apache.commons.lang.StringUtils;
import org.apache.qpid.proton.amqp.transport.End;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.watsons.base.exclusive.model.entities.TtaSoleAgrtInfoEntity_HI;
import com.sie.watsons.base.exclusive.model.inter.ITtaSoleAgrtInfo;
import org.springframework.util.Assert;
import sun.nio.ch.Net;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component("ttaSoleAgrtInfoServer")
public class TtaSoleAgrtInfoServer extends BaseCommonServer<TtaSoleAgrtInfoEntity_HI> implements ITtaSoleAgrtInfo{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaSoleAgrtInfoServer.class);

	@Autowired
	private BaseCommonDAO_HI<TtaSoleAgrtInfoEntity_HI> ttaSoleAgrtInfoDAO_HI;
	@Autowired
	private BaseViewObject<TtaSoleAgrtInfoEntity_HI_RO> ttaSoleAgrtInfoDAO_HI_RO;
	@Autowired
	private BaseViewObject<TtaItemEntity_HI_RO> ttaItemDAO_HI_RO;
	@Autowired
	private ITtaSoleItem ttaSoleItemServer;
	@Autowired
	private BaseViewObject<TtaBrandplnLEntity_HI_RO> ttaBrandplnLDAO_HI_RO;
	@Autowired
	private BaseCommonDAO_HI<TtaSoleAgrtEntity_HI> ttaSoleAgrtDAO_HI;
	@Autowired
	private BaseCommonDAO_HI<TtaSoleItemEntity_HI> ttaSoleItemDAO_HI;
	@Autowired
	private BaseViewObject<TtaSoleItemEntity_HI_RO> ttaSoleItemDAO_HI_RO;
	@Autowired
	private ITtaItem ttaItemServer;
	@Autowired
	private ITtaSoleAgrt ttaSoleAgrtServer;
	@Autowired
	private IRuleFileTemplate ruleFileTemplate;
	@Autowired
	private TempRuleDefService tempRuleDefService;
	@Autowired
	private IFastdfs fastdfsServer;
	@Autowired
	private IBaseAttachment baseAttachmentServer;
	@Autowired
	private DynamicViewObjectImpl<TtaSoleSupplierEntity_HI_RO> ttaSoleSupplierEntity_HI_RO;

	public TtaSoleAgrtInfoServer() {
		super();
	}

	/**
	 * 保存或者更新独家协议信息
	 * @param paramsJSON 参数
	 * @param userId 用户id
	 * @return
	 * @throws Exception
	 */
	@Override
	public TtaSoleAgrtInfoEntity_HI saveOrUpdate(JSONObject paramsJSON, int userId) throws Exception {
		LOGGER.info("用户id :{}",userId);
		Integer soleAgrtHId = paramsJSON.getInteger("soleAgrtHId");
		JSONObject jsonObject = paramsJSON.getJSONObject("current");
		Assert.notNull(soleAgrtHId,"头信息未保存,请先保存再进行操作");
		TtaSoleAgrtEntity_HI hiById = ttaSoleAgrtDAO_HI.getById(soleAgrtHId);
		List<TtaSoleAgrtInfoEntity_HI> byProperty = ttaSoleAgrtInfoDAO_HI.findByProperty(new JSONObject().fluentPut("soleAgrtHId",soleAgrtHId));
		if (byProperty != null && byProperty.size() == 1) {
			throw new IllegalArgumentException("系统中已经存在了一条独家信息信息,不能再次添加");
		}
		TtaSoleAgrtInfoEntity_HI ttaSoleAgrtInfoEntity_hi = SaafToolUtils.setEntity(TtaSoleAgrtInfoEntity_HI.class, jsonObject, ttaSoleAgrtInfoDAO_HI, userId);
		ttaSoleAgrtInfoEntity_hi.setSoleAgrtHId(soleAgrtHId);
		ttaSoleAgrtInfoDAO_HI.saveOrUpdate(ttaSoleAgrtInfoEntity_hi);
		//如果独家产品类型为全品牌,就执行
		if ("1".equals(jsonObject.getString("productType"))) {//1:全品牌,2:指定产品
			saveSoleItem(jsonObject,hiById,ttaSoleAgrtInfoEntity_hi,userId);
		}
		insertDocToFileService(ttaSoleAgrtInfoEntity_hi, soleAgrtHId,userId);
		return ttaSoleAgrtInfoEntity_hi;
	}

	/**
	 * 插入独家协议doc合同进文件服务器
	 * @param ttaSoleAgrtInfoEntity_hi
	 * @param soleAgrtHId
	 */
	private void insertDocToFileService(TtaSoleAgrtInfoEntity_HI ttaSoleAgrtInfoEntity_hi, Integer soleAgrtHId,int userId) throws Exception{
		String fileName = "";
		InputStream in = null;
		ByteArrayOutputStream byteOut = null;
		XWPFTemplate template = null;
		ByteArrayInputStream wordStream = null;
		try {
			TtaSoleAgrtEntity_HI headerSoleAgrt = ttaSoleAgrtDAO_HI.getById(soleAgrtHId);
			TempRuleDefEntity_HI_RO tempRuleDef = ttaSoleAgrtServer.findRuleInfoByCondition(JSON.parseObject(JSONObject.toJSONString(ttaSoleAgrtInfoEntity_hi)));
			RuleFileTemplateEntity_HI_RO entity = ruleFileTemplate.findByBusinessType("1");
			if (entity == null || entity.getFileContent() == null) {
				LOGGER.info(this.getClass() + "独家协议模板word文件未上传!");
				throw new IllegalArgumentException("独家协议模板word文件未上传");
			}
			if (tempRuleDef.getRulId() != null) {
				Map<String, Object> resultMap = tempRuleDefService.generateDocx(tempRuleDef.getRulId(), false);
				in = (FileInputStream)resultMap.get("fis");
				//fileName = new String((resultMap.get("fileName") + "").getBytes(), "ISO8859-1") + ".pdf";
			} else {
				throw new IllegalArgumentException("当前选择的独家协议信息未找到合同模板");
			}
			//替换,设置参数
			Map<String, Object> underLineMap = ttaSoleAgrtServer.setParam(ttaSoleAgrtInfoEntity_hi.getSoleAgrtInfoId(), soleAgrtHId);
			template = XWPFTemplate.compile(in).render(underLineMap);
			byteOut = new ByteArrayOutputStream();
			template.write(byteOut);
			wordStream = new ByteArrayInputStream(byteOut.toByteArray());

			//先删除文件服务器上已存在的合同文件
			List<BaseAttachmentEntity_HI_RO> baseAttachmentInfos = baseAttachmentServer.findBaseAttachmentAllFileId(Long.valueOf(ttaSoleAgrtInfoEntity_hi.getSoleAgrtInfoId()), "TTA_SOLE_AGRT_INFO");
			for (BaseAttachmentEntity_HI_RO baseAttachmentInfo : baseAttachmentInfos) {
				baseAttachmentServer.deleteById(baseAttachmentInfo.getFileId());
			}
			//供应商编号-单据号-2位版本号
			fileName = headerSoleAgrt.getVendorCode() + "-" + headerSoleAgrt.getSoleAgrtCode() + "-" + headerSoleAgrt.getSoleAgrtVersion() + ".docx";
			ResultFileEntity resultFileEntity = fastdfsServer.fileUpload(wordStream,fileName, "TTA_SOLE_AGRT_INFO", Long.valueOf(ttaSoleAgrtInfoEntity_hi.getSoleAgrtInfoId()), userId);
		} catch (Exception e) {
			LOGGER.info("DOC合同文件存入异常:" + e.getMessage());
			throw new IllegalArgumentException(e.getMessage());
		} finally {
			try {
				if (null != in) {
					in.close();
				}
				if (null != template) {
					template.close();
				}
				if (null != byteOut) {
					byteOut.close();
				}
				if (null != wordStream) {
					wordStream.close();
				}
			} catch (IOException e) {
				LOGGER.error("DOC合同文件存入异常", e);
			}
		}
	}

	private void saveSoleItem(Object object,TtaSoleAgrtEntity_HI soleAgrtEntity,TtaSoleAgrtInfoEntity_HI ttaSoleAgrtInfoEntity_hi,int userId){
		String groupCode = "";
		String deptCode = "";
		String brandCn = "";
		String brandEn = "";
		Integer proposalId = null;
		String soleBrandCn = "";
		String orgCode = "";
/*		//1:选中,0:未选中
		int checked = 0;//默认未选中*/
		if (object instanceof JSONObject) {
			JSONObject jsonObject = (JSONObject)object;
			proposalId = jsonObject.getInteger("proposalId");
			soleBrandCn = jsonObject.getString("soleBrandCn");
			orgCode = jsonObject.getString("orgCode");
/*			if (StringUtils.isNotBlank(jsonObject.getString("productType"))) {
				//全品牌选中所有数据
				checked = "1".equals(jsonObject.getString("productType")) ? 1 : 0;//1:全品牌,2:指定产品
			}*/
		} else if (object instanceof TtaSoleAgrtInfoEntity_HI) {
			TtaSoleAgrtInfoEntity_HI instance = (TtaSoleAgrtInfoEntity_HI)object;
			proposalId = instance.getProposalId();
			soleBrandCn = instance.getSoleBrandCn();
			orgCode = instance.getOrgCode();

/*			if (StringUtils.isNotBlank(instance.getProductType())) {
				checked = "1".equals(instance.getProductType()) ? 1 : 0;//1:全品牌,2:指定产品
			}*/
		}

		Map<String,Object> paramMap = new HashMap<>();
		String[] split = orgCode.split(",");
		String orgCodeJoin = "'"+StringUtils.join(split, "','") + "'";
		paramMap.put("proposalId",proposalId);
		paramMap.put("brandCn",soleBrandCn);
		//paramMap.put("deptCode",orgCodeJoin);
		StringBuffer sb = new StringBuffer(TtaBrandplnLEntity_HI_RO.TTA_ITEM_V);
		sb.append(" and V.proposal_id =:proposalId and v.BRAND_CN =:brandCn and v.DEPT_CODE in("+orgCodeJoin+")");
		List<TtaBrandplnLEntity_HI_RO> brandDetailList = ttaBrandplnLDAO_HI_RO.findList(sb, paramMap);
		List<String> groupList = new ArrayList<>();
		List<String> deptList = new ArrayList<>();
		Set<String> brandCnList = new HashSet<>();
		Set<String> brandEnList = new HashSet<>();
		for (TtaBrandplnLEntity_HI_RO entityHiRo : brandDetailList) {
			groupList.add(entityHiRo.getGroupCode());
			deptList.add(entityHiRo.getDeptCode());
			brandCnList.add(entityHiRo.getBrandCn());
			brandEnList.add(entityHiRo.getBrandEn());
		}
		groupCode = "'"+StringUtils.join(groupList, "','") + "'";
		deptCode = "'"+StringUtils.join(deptList, "','") + "'";
		brandCn = "'"+StringUtils.join(brandCnList, "','") + "'";
		brandEn = "'"+StringUtils.join(brandEnList, "','") + "'";
		//获取group dept brand等信息
		String vendorCode = soleAgrtEntity.getVendorCode();
		String vendorName = soleAgrtEntity.getVendorName();

		Map<String,Object> paramMapValue = new HashMap<>();
		StringBuffer sql = new StringBuffer(TtaItemEntity_HI_RO.getTtaItem(vendorCode,groupCode,deptCode,brandCn,brandEn));
		List<TtaItemEntity_HI_RO> itemList = ttaItemDAO_HI_RO.findList(sql, paramMapValue);
		if (CollectionUtils.isNotEmpty(itemList)) {
			for (TtaItemEntity_HI_RO entityHiRo : itemList) {
				TtaSoleItemEntity_HI entity = new TtaSoleItemEntity_HI();
				entity.setSoleAgrtHId(soleAgrtEntity.getSoleAgrtHId());
				entity.setSoleAgrtInfoId(ttaSoleAgrtInfoEntity_hi.getSoleAgrtInfoId());
				entity.setVendorNbr(vendorCode);
				entity.setVendorName(vendorName);
				entity.setItemCode(entityHiRo.getItemNbr());
				entity.setItemName(entityHiRo.getItemDescCn());
				entity.setGroupCode(entityHiRo.getGroupCode().toString());
				entity.setGroupName(entityHiRo.getGroupDesc());
				entity.setDeptCode(entityHiRo.getDeptCode());
				entity.setDeptDesc(entityHiRo.getDeptDesc());
				entity.setBrandCn(entityHiRo.getBrandCn());
				entity.setBrandEn(entityHiRo.getBrandEn());
				entity.setBarCode(entityHiRo.getUpc());
				//生效和失效时间
				entity.setEffectiveDate(entityHiRo.getStartDate());
				entity.setExpirationDate(entityHiRo.getEndDate());
				//entity.setChecked(checked);//是否选中
				entity.setCreatedBy(userId);
				entity.setCreationDate(new Date());
				entity.setOperatorUserId(userId);
				ttaSoleItemDAO_HI.saveOrUpdate(entity);
			}
		}
	}
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	@Override
	public void deleteSoleAgrtInfoById(Integer id) throws Exception{
		TtaSoleAgrtInfoEntity_HI ttaSoleAgrtInfoEntity_hi = ttaSoleAgrtInfoDAO_HI.get(TtaSoleAgrtInfoEntity_HI.class, id);
		if (ttaSoleAgrtInfoEntity_hi == null){
			throw new IllegalArgumentException("你选择的数据不存在,不能删除");
		}
		ttaSoleAgrtInfoDAO_HI.delete(ttaSoleAgrtInfoEntity_hi);
	}

	@Override
	public void saveBatchSoleAgrtInfo(JSONObject paramsJson, Integer userId) throws Exception {
		LOGGER.info("*********************独家协议信息保存操作*******************************");
		Integer soleAgrtHId = paramsJson.getInteger("soleAgrtHId");
		Assert.notNull(soleAgrtHId,"头信息未保存,请先保存,再进行操作");
		LOGGER.info("参数soleAgrtHId:{}",new Object[]{soleAgrtHId});
		JSONArray exclusiveProtocolInfo = paramsJson.getJSONArray("exclusiveProtocolInfo");
		if (null == exclusiveProtocolInfo || exclusiveProtocolInfo.size() == 0){
			return;
		}
		TtaSoleAgrtEntity_HI hiById = ttaSoleAgrtDAO_HI.getById(soleAgrtHId);
		StringBuffer sql = new StringBuffer(TtaSoleAgrtInfoEntity_HI_RO.QUERY_SOLE_AGRT_INFO);
		Map<String,Object> paramMap = new HashMap<>();
		SaafToolUtils.parperParam(paramsJson, "tsai.sole_agrt_h_id", "soleAgrtHId",sql , paramMap, "=",false);
		SaafToolUtils.changeQuerySort(paramsJson, sql, "tsai.sole_agrt_info_id asc", false);
		//查询出原来的独家协议信息
		List<TtaSoleAgrtInfoEntity_HI_RO> infoDAOHiRoList = ttaSoleAgrtInfoDAO_HI_RO.findList(sql, paramMap);
		for (int i = 0; i < exclusiveProtocolInfo.size(); i++) {
			JSONObject jsonObject = exclusiveProtocolInfo.getJSONObject(i);
			TtaSoleAgrtInfoEntity_HI infoEntityHi = SaafToolUtils.setEntity(TtaSoleAgrtInfoEntity_HI.class, jsonObject, ttaSoleAgrtInfoDAO_HI, userId);
			String[] split = infoEntityHi.getOrgCode().split(",");
			Arrays.sort(split);
			String splitJoin = "'" + StringUtils.join(split,"','") + "'";
			for (TtaSoleAgrtInfoEntity_HI_RO entityHiRo : infoDAOHiRoList) {
				if (Objects.equals(infoEntityHi.getSoleAgrtInfoId(), entityHiRo.getSoleAgrtInfoId())) {
					String soleBrandCn = entityHiRo.getSoleBrandCn();//独家品牌
					String productType = entityHiRo.getProductType();//独家产品类型
					String[] orgCodeSplit = entityHiRo.getOrgCode().split(",");
					Arrays.sort(orgCodeSplit);
					String orgCodeJoin = "'" + StringUtils.join(orgCodeSplit,"','") + "'";
					//<1> 独家品牌和原来的不一样
					// <2>独家产品类型和原来的不一样
					// <3> 对应的品牌计划行中的品牌不一样
					// 都删除已存在的独家信息,再重新生成ITEM明细
					if (!soleBrandCn.equals(infoEntityHi.getSoleBrandCn()) || !productType.equals(infoEntityHi.getProductType()) ||
							!splitJoin.equals(orgCodeJoin)) {
						List<TtaSoleItemEntity_HI> soleItemList = ttaSoleItemDAO_HI.findByProperty(new JSONObject().fluentPut("soleAgrtInfoId", infoEntityHi.getSoleAgrtInfoId()));
						ttaSoleItemDAO_HI.deleteAll(soleItemList);
						if ("1".equals(infoEntityHi.getProductType())){//独家产品类型为全品牌才执行
							saveSoleItem(infoEntityHi,hiById,infoEntityHi,userId);
						}
					}
					break;
				}
			}
			infoEntityHi.setLastUpdateDate(new Date());
			infoEntityHi.setLastUpdatedBy(userId);
			infoEntityHi.setLastUpdateLogin(userId);
			infoEntityHi.setOperatorUserId(userId);
			if (null == infoEntityHi.getSoleAgrtHId()) {
				infoEntityHi.setSoleAgrtHId(soleAgrtHId);
			}
			ttaSoleAgrtInfoDAO_HI.saveOrUpdate(infoEntityHi);
		}
		TtaSoleAgrtInfoEntity_HI agrtInfoEntity_hi = ttaSoleAgrtInfoDAO_HI.getById(infoDAOHiRoList.get(0).getSoleAgrtInfoId());
		this.insertDocToFileService(agrtInfoEntity_hi,soleAgrtHId,userId);
	}

	/**
	 * 查询数据
	 * @param queryParamJSON 对象属性的JSON格式
	 * @param pageIndex 页码
	 * @param pageRows 每页查询记录数
	 * @return
	 */
	@Override
	public Pagination<TtaSoleAgrtInfoEntity_HI_RO> findSoleAgrtInfo(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows){
		Map<String, Object> queryParamMap = new HashMap<String, Object>();
		StringBuffer sb = new StringBuffer(TtaSoleAgrtInfoEntity_HI_RO.QUERY_SOLE_AGRT_INFO);
		SaafToolUtils.parperParam(queryParamJSON, "tsai.sole_agrt_h_id", "soleAgrtHId", sb, queryParamMap, "=",false);
		SaafToolUtils.changeQuerySort(queryParamJSON, sb, "tsai.sole_agrt_info_id asc", false);
		Pagination<TtaSoleAgrtInfoEntity_HI_RO> findListResult = ttaSoleAgrtInfoDAO_HI_RO.findPagination(sb, queryParamMap, pageIndex, pageRows);
		return findListResult;
	}

	@Override
	public TtaSoleAgrtInfoEntity_HI_RO findSoleAgrtInfoResult(Integer soleAgrtInfoId) {
		StringBuffer sql = new StringBuffer("select * from tta_sole_agrt_info tsai where 1=1 and tsai.sole_agrt_info_id =:soleAgrtInfoId ");
		Map<String,Object> paramMap = new HashMap<>();
		paramMap.put("soleAgrtInfoId",soleAgrtInfoId);
		List<TtaSoleAgrtInfoEntity_HI_RO> list = ttaSoleAgrtInfoDAO_HI_RO.findList(sql, paramMap);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 独家协议信息新增弹窗soleAgrtInfo中的部门按钮数据
	 * @param queryParamJSON
	 * @param pageIndex
	 * @param pageRows
	 * @return
	 */
	@Override
	public Pagination<TtaItemEntity_HI_RO> findItemDept(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) throws Exception{
		Map<String, Object> queryParamMap = new HashMap<String, Object>();
		StringBuffer sb = new StringBuffer(TtaItemEntity_HI_RO.TTA_ITEM_DEPT_LIST);
		SaafToolUtils.parperParam(queryParamJSON, "tti.dept_code", "deptCode", sb, queryParamMap, "=",false);
		SaafToolUtils.parperParam(queryParamJSON, "tti.dept_desc", "deptDesc", sb, queryParamMap, "like",false);
		SaafToolUtils.parperParam(queryParamJSON, "tti.group_code", "groupCode", sb, queryParamMap, "=",false);
		SaafToolUtils.parperParam(queryParamJSON, "tti.group_desc", "groupDesc", sb, queryParamMap, "like",false);
		sb.append(" group by tti.dept_code, tti.dept_desc");
		SaafToolUtils.changeQuerySort(queryParamJSON, sb, "tti.dept_code asc", false);
		Pagination<TtaItemEntity_HI_RO> findListResult = ttaItemDAO_HI_RO.findPagination(sb, SaafToolUtils.getSqlCountString(sb),queryParamMap, pageIndex, pageRows);
		return findListResult;
	}

	/**
	 * 批量导入ITEM信息
	 * @param queryParamJSON
	 * @return
	 * @throws Exception
	 */
	@Override
	public int saveImportItemDetail(JSONObject queryParamJSON,int userId) throws Exception {
		JSONArray jsonArray = queryParamJSON.getJSONArray("data");//获取导入的数据
		JSONObject headerInfo = queryParamJSON.getJSONObject("info");
		Integer soleAgrtInfoId = headerInfo.getInteger("date");
		Integer soleAgrtHId = headerInfo.getInteger("id");

		if (soleAgrtInfoId == null) {
			JSONArray errorList = new JSONArray();
			JSONObject errJson = new JSONObject();
			String errorMsg = "系统中不存在独家协议信息,导入错误.";
			errJson.put("ROW_NUM",0);
			errJson.put("ERR_MESSAGE",errorMsg);
			errorList.add(errJson);
			throw new IllegalArgumentException(errorList.toJSONString());
		}
		TtaSoleAgrtInfoEntity_HI byId = ttaSoleAgrtInfoDAO_HI.getById(soleAgrtInfoId);
		List<TtaItemEntity_HI_RO> deptInfo = ttaItemServer.findDeptInfo();//部门信息
		StringBuffer sql = new StringBuffer(TtaSoleItemEntity_HI_RO.QUERY_SOLE_ITEM);
		sql.append(" and tsi.sole_agrt_info_id =:soleAgrtInfoId");
		Map<String,Object> paramMap = new HashMap<>();
		paramMap.put("soleAgrtInfoId",soleAgrtInfoId);
		List<TtaSoleItemEntity_HI_RO> daoHiRoList = ttaSoleItemDAO_HI_RO.findList(sql, paramMap);

		JSONArray errList = new JSONArray();
		for(int i=0;i < jsonArray.size(); i++){
			JSONObject json = jsonArray.getJSONObject(i);
			JSONObject errJson = new JSONObject();
			String msgStr = "";
			try {
				if(SaafToolUtils.isNullOrEmpty(json.getString("deptCode"))){
					msgStr += "导入的【部门】列的值不能为空";
				}
				if(SaafToolUtils.isNullOrEmpty(json.getString("brandCn"))){
					msgStr += "导入的【品牌中文名】列的值不能为空";
				}
				if(SaafToolUtils.isNullOrEmpty(json.getString("brandEn"))){
					msgStr += " 导入的【品牌英文名】列的值不能为空";
				}
				if(SaafToolUtils.isNullOrEmpty(json.getString("itemName"))){
					msgStr += "导入的【货品名称】列的值不能为空";
				}
				if(SaafToolUtils.isNullOrEmpty(json.getString("barCode"))){
					msgStr += " 导入的【货品条码】列的值不能为空";
				}
				String barCode = json.getString("barCode");
				if (!SaafToolUtils.isNullOrEmpty(barCode)){
					barCode = barCode.trim();
					StringBuilder msgStrBuilder = new StringBuilder(msgStr);
					for (TtaSoleItemEntity_HI_RO entityHiRo : daoHiRoList) {
						//货品条码已存在,提示
						if (barCode.equals(entityHiRo.getBarCode())) {
							msgStrBuilder.append("系统中已存在相同的货品条码,货品条码为[#]".replace("#",barCode));
							break;
						}
					}
					msgStr = msgStrBuilder.toString();
				}
				if(!"".equals(msgStr)){
					errJson.put("ROW_NUM",json.get("ROW_NUM"));
					errJson.put("ERR_MESSAGE",msgStr);
					errList.add(errJson);
				}else{
					String groupCode = "";
					String groupName = "";
					String deptCode = json.getString("deptCode");
					String deptDesc = "";
					for (TtaItemEntity_HI_RO entityHiRo : deptInfo) {
						if (Integer.valueOf(deptCode).equals(entityHiRo.getDeptCode())) {
							groupCode = entityHiRo.getGroupCode().toString();
							groupName = entityHiRo.getGroupDesc();
							deptDesc = entityHiRo.getDeptDesc();
							break;
						}
					}
					json.put("operatorUserId",queryParamJSON.get("operatorUserId"));
					json.put("soleAgrtInfoId",soleAgrtInfoId);//tta_sole_item表中的外键
					json.put("soleAgrtHId",soleAgrtHId);
					json.put("checked",1);//导入默认选中
					json.put("vendorNbr",byId.getSupplierCode());
					json.put("vendorName",byId.getSupplierName());
					json.put("groupCode",groupCode);
					json.put("groupName",groupName);
					json.put("deptDesc",deptDesc);
					if (SaafToolUtils.isNullOrEmpty(json.getString("itemCode"))){
						json.put("itemCode","NEW");
					}
					ttaSoleItemServer.saveOrUpdate(json);
				}
			} catch (Exception e){
				msgStr += ("有异常,数据有误.");
				errJson.put("ROW_NUM",json.get("ROW_NUM"));
				errJson.put("ERR_MESSAGE",msgStr);
				errList.add(errJson);
				e.printStackTrace();
			}
		}
		if (!errList.isEmpty()){
			throw new Exception(errList.toJSONString());
		}
		return jsonArray.size();
	}

	@Override
	public Pagination<TtaBrandplnLEntity_HI_RO> findProposalBrand(JSONObject jsonObject, Integer pageIndex, Integer pageRows) {
		Integer soleAgrtHId = jsonObject.getInteger("soleAgrtHId");
		Integer proposalId = jsonObject.getInteger("proposalId");
		String deptCode = jsonObject.getString("deptCode");
		if (SaafToolUtils.isNullOrEmpty(deptCode)){
			throw new IllegalArgumentException("未选择DEPT相关信息,请先选择");
		}
		Assert.notNull(soleAgrtHId,"请先保存独家协议(标准)头信息");
		Assert.notNull(proposalId,"请先选择Proposal信息");
		TtaSoleSupplierEntity_HI_RO soleSupplierEntity = getSoleSupplierEntity(jsonObject);
		jsonObject.put("proposalId",soleSupplierEntity.getPoposalId());
		StringBuffer sql = new StringBuffer(TtaBrandplnLEntity_HI_RO.FIND_BRNADPLN_BRAND);
		//sql.append("select distinct * from ( ").append(TtaBrandplnLEntity_HI_RO.TTA_ITEM_V).append( ") v  where 1 = 1 \n");
		Map<String,Object> paramsMap = new HashMap<>();
		SaafToolUtils.parperParam(jsonObject, "v.proposal_id", "proposalId",sql, paramsMap, "=");
		SaafToolUtils.parperParam(jsonObject, "v.group_code", "groupCode",sql, paramsMap, "like");
		SaafToolUtils.parperParam(jsonObject, "v.group_desc", "groupDesc",sql, paramsMap, "like");
		SaafToolUtils.parperParam(jsonObject, "v.dept_code", "deptCode",sql, paramsMap, "in");
		SaafToolUtils.parperParam(jsonObject, "v.dept_desc", "deptDesc",sql, paramsMap, "like");
		SaafToolUtils.parperParam(jsonObject, "v.brand_cn", "brandCn",sql, paramsMap, "like");
		SaafToolUtils.parperParam(jsonObject, "v.brand_en", "brandEn",sql, paramsMap, "like");
		sql.append(" group by v.BRAND_CN,v.BRAND_EN ");
		SaafToolUtils.changeQuerySort(jsonObject,sql,"v.BRAND_CN asc",false);
		Pagination<TtaBrandplnLEntity_HI_RO> findList = ttaBrandplnLDAO_HI_RO.findPagination(sql, paramsMap, pageIndex, pageRows);
		return findList;
	}

	@Override
	public void deleteTtaSoleItem(Integer id) throws Exception {
		TtaSoleAgrtInfoEntity_HI ttaSoleAgrtInfoEntity_hi = ttaSoleAgrtInfoDAO_HI.get(TtaSoleAgrtInfoEntity_HI.class, id);
		if (ttaSoleAgrtInfoEntity_hi == null){
			throw new IllegalArgumentException("你选择的数据不存在,请重试!");
		}
		List<TtaSoleItemEntity_HI> itemEntityHis = ttaSoleItemDAO_HI.findByProperty(new JSONObject().fluentPut("soleAgrtInfoId", id));
		ttaSoleItemDAO_HI.deleteAll(itemEntityHis);
	}

	@Override
	public Pagination<TtaBrandplnLEntity_HI_RO> findProposalDept(JSONObject jsonObject, Integer pageIndex, Integer pageRows) {
		Integer soleAgrtHId = jsonObject.getInteger("soleAgrtHId");
		Integer proposalId = jsonObject.getInteger("proposalId");
		Assert.notNull(soleAgrtHId,"请先保存独家协议(标准)头信息");
		Assert.notNull(proposalId,"请先选择Proposal信息");
		//查询出最新的Proposal
		TtaSoleSupplierEntity_HI_RO soleSupplierEntity = getSoleSupplierEntity(jsonObject);
		jsonObject.put("proposalId",soleSupplierEntity.getPoposalId());
		StringBuffer sql = new StringBuffer(TtaBrandplnLEntity_HI_RO.FIND_BRNADPLN_DEPT);
		Map<String,Object> paramsMap = new HashMap<>();
		SaafToolUtils.parperParam(jsonObject, "v.proposal_id", "proposalId",sql, paramsMap, "=");
		SaafToolUtils.parperParam(jsonObject, "v.group_code", "groupCode",sql, paramsMap, "like");
		SaafToolUtils.parperParam(jsonObject, "v.group_desc", "groupDesc",sql, paramsMap, "like");
		SaafToolUtils.parperParam(jsonObject, "v.dept_code", "deptCode",sql, paramsMap, "like");
		SaafToolUtils.parperParam(jsonObject, "v.dept_desc", "deptDesc",sql, paramsMap, "like");
		SaafToolUtils.parperParam(jsonObject, "v.brand_cn", "brandCn",sql, paramsMap, "like");
		SaafToolUtils.parperParam(jsonObject, "v.brand_en", "brandEn",sql, paramsMap, "like");
		sql.append(" group by v.DEPT_CODE,v.DEPT_DESC ");
		SaafToolUtils.changeQuerySort(jsonObject,sql,"v.dept_code asc",false);
		Pagination<TtaBrandplnLEntity_HI_RO> findList = ttaBrandplnLDAO_HI_RO.findPagination(sql, paramsMap, pageIndex, pageRows);
		return findList;
	}

	private TtaSoleSupplierEntity_HI_RO getSoleSupplierEntity(JSONObject queryParam){
		Map<String,Object> soleSupplierMap = new HashMap<>();
		StringBuffer soleSql = new StringBuffer();
		StringBuffer sbf = new StringBuffer(TtaSoleSupplierEntity_HI_RO.QUEREY_PROPOSAL_CONTRACT_VENDOR);
		SaafToolUtils.parperParam(queryParam,"tss.sole_agrt_h_id","soleAgrtHId",sbf,soleSupplierMap,"=");
		changeQuerySort(queryParam,sbf,"tss.proposal_year desc,tss.poposal_id desc",false);
		soleSql.append("select * from (\n").append(sbf).append("\n) t where rownum = 1");
		return ttaSoleSupplierEntity_HI_RO.get(soleSql, soleSupplierMap);
	}
}
