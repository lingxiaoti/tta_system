package com.sie.watsons.base.pon.project.model.inter.server;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.pon.project.model.entities.EquPonProductSpecsEntity_HI;
import com.sie.watsons.base.pon.project.model.entities.readonly.EquPonProductSpecsEntity_HI_RO;
import com.sie.watsons.base.pon.project.model.inter.IEquPonProductSpecs;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("equPonProductSpecsServer")
public class EquPonProductSpecsServer extends BaseCommonServer<EquPonProductSpecsEntity_HI> implements IEquPonProductSpecs{
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPonProductSpecsServer.class);

	@Autowired
	private ViewObject<EquPonProductSpecsEntity_HI> equPonProductSpecsDAO_HI;

	@Autowired
	private BaseViewObject<EquPonProductSpecsEntity_HI_RO> equPonProductSpecsEntity_HI_RO;

	public EquPonProductSpecsServer() {
		super();
	}

	/**
	 * 报价管理-产品规格查询，分页查询
	 *
	 * @param queryParamJSON 参数：密级Entity中的字段
	 * @param pageIndex
	 * @param pageRows
	 * @return
	 */
	public JSONObject findProductSpecs(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {

		StringBuffer sql = new StringBuffer(EquPonProductSpecsEntity_HI_RO.QUERY_SQL);
		Map<String, Object> map = new HashMap<>();
		SaafToolUtils.parperHbmParam(EquPonProductSpecsEntity_HI_RO.class, queryParamJSON, sql, map);
		sql.append(" order by t.specs_id");
		Pagination<EquPonProductSpecsEntity_HI_RO> pagination = equPonProductSpecsEntity_HI_RO.findPagination(sql, map, pageIndex, pageRows);
		return JSONObject.parseObject(JSONObject.toJSONString(pagination));
	}

	/**
	 * 报价管理-产品规格保存
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public EquPonProductSpecsEntity_HI saveProductSpecs(JSONObject params) throws Exception {
		return this.saveOrUpdate(params);
	}

	/**
	 * 报价管理-产品规格删除
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public void deleteProductSpecs(JSONObject params) throws Exception {
		this.deleteById(params.getInteger("id"));
	}

	/**
	 * 报价管理-产品规格批量导入
	 * @param jsonObject 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public int importProductSpecsInfo(JSONObject jsonObject) throws Exception {
		Integer userId = jsonObject.getInteger("varUserId");
		JSONArray jsonArray = jsonObject.getJSONArray("data");
		Integer projectId = jsonObject.getJSONObject("info").getInteger("projectId");
		//清空历史数据
		List<EquPonProductSpecsEntity_HI> hisList = equPonProductSpecsDAO_HI.findByProperty("projectId",projectId);
		for(int i = 0; i < hisList.size(); i++){
			equPonProductSpecsDAO_HI.delete(hisList);
		}
		JSONArray errList = new JSONArray();
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject json = jsonArray.getJSONObject(i);
			json.put("operatorUserId", userId);
			json.put("projectId", projectId);
			saveOrUpdate(json);
//			JSONObject json = jsonArray.getJSONObject(i);
//			// 校验导入数据
//			String supplierNumber = json.getString("supplierNumber");
//			for (int n = i + 1; n < jsonArray.size(); n++) {
//				JSONObject json2 = jsonArray.getJSONObject(n);
//				String supplierNumber2 = json2.getString("supplierNumber");
//				if (supplierNumber != null && supplierNumber2 != null && supplierNumber2.equals(supplierNumber)){
//					throw new IllegalArgumentException(supplierNumber+"重复了，请重新导入");
//				}
//			}
//
//			JSONObject errJson = new JSONObject();
//			String msgStr = null;
//			try {
//				// 日期转换
//				json.put("beginValidDate", json.get("beginValidDate"));
//				json.put("endValidDate", json.get("endValidDate"));
//
//				if (SaafToolUtils.isNullOrEmpty(json.getString("supplierNumber"))) {
//					msgStr += "供应商档案号不能为空";
//				}
//				if (SaafToolUtils.isNullOrEmpty(json.getString("category"))) {
//					msgStr += "分类不能为空";
//				}
//				if (SaafToolUtils.isNullOrEmpty(json.getString("score"))) {
//					msgStr += "分数不能为空";
//				}
//				if (SaafToolUtils.isNullOrEmpty(json.getString("result"))) {
//					msgStr += "结果不能为空";
//				}
//				if (SaafToolUtils.isNullOrEmpty(json.getString("beginValidDate"))) {
//					msgStr += "有效期从不能为空";
//				}
//				if (SaafToolUtils.isNullOrEmpty(json.getString("endValidDate"))) {
//					msgStr += "有效期至不能为空";
//				}
//
//				// 校验供应商档案号、品类是否有错误(与系统现有信息进行校验)，若存在则不允许导入，并且给出提示“**行供应商档案号/品类有误，请处理！
//				// 校验是否有供应商档案号对应的供应商
//				List<EquPosSupplierInfoEntity_HI> supplierInfoList = supplierInfoDao.findByProperty("supplierNumber", json.getString("supplierNumber"));
//				if(supplierInfoList == null || supplierInfoList.size()<=0){
//					throw new IllegalArgumentException("第"+i+"行,输入的的供应商档案号在供应商信息表中不存在");
//				}
//				// 校验供应商品类表里是否有该品类
//				List<EquPosSupplierCategoryEntity_HI> categoryList = supplierCategoryDao.findByProperty("categoryGroup", json.getString("category"));
//				if(categoryList == null || categoryList.size()<=0){
//					throw new IllegalArgumentException("第"+i+"行,输入的分类在供应商品分类表中不存在");
//				}
//				// 校验该供应商和该品类在要导入的表中是否存在，如果存在则抛异常
//				HashMap<String, Object> paraMap = new HashMap<>(4);
//				paraMap.put("category", json.getString("category"));
//				paraMap.put("supplierNumber", json.getString("supplierNumber"));
//				List<EquPosQualityUpdateLineEntity_HI> entityHiList = equPosQualityUpdateLineDAO_HI.findByProperty(paraMap);
//				if(entityHiList != null && entityHiList.size()>0){
//					throw new IllegalArgumentException("第"+i+"行,供应商档案号/品类在表中已存在，请处理！");
//				}
//
//				if (msgStr != null) {
//					errJson.put("ROW_NUM", json.get("ROW_NUM"));
//					errJson.put("ERR_MESSAGE", msgStr);
//					errList.add(errJson);
//				} else {
//					json.put("operatorUserId", userId);
//					json.put("updateHeadId", updateHeadId);
//					saveOrUpdate(json);
//				}
//			} catch (Exception e) {
//				msgStr = "有异常,数据有误.";
//				errJson.put("ROW_NUM", json.get("ROW_NUM"));
//				errJson.put("ERR_MESSAGE", msgStr);
//				errList.add(errJson);
//				e.printStackTrace();
//			}
		}
//		if (!errList.isEmpty()) {
//			throw new Exception(errList.toJSONString());
//		}
		return jsonArray.size();
	}
}
