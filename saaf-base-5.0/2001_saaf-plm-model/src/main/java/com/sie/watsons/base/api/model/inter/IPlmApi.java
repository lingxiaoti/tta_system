package com.sie.watsons.base.api.model.inter;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.sie.watsons.base.product.model.entities.PlmProductHeadEntity_HI;
import com.sie.watsons.base.product.model.entities.readonly.PlmProductObLicenseEntity_HI_RO;
import com.sie.watsons.base.productEco.model.entities.PlmProductDrugEcoEntity_HI;
import com.sie.watsons.base.productEco.model.entities.PlmProductHeadEcoEntity_HI;

/**
 * Created by Administrator on 2019/12/13/013.
 */
public interface IPlmApi {

	/**
	 * 新增ITEM返回接口
	 * 
	 * @param params
	 * @return
	 */
	void addItemReturnMethod(String params) throws Exception;

	/**
	 * 校验是否能SPA
	 * 
	 * @param ro
	 * @return
	 */
	boolean validProductData(PlmProductObLicenseEntity_HI_RO ro);

	/**
	 * （UDA，售价，成本）修改接口
	 * 
	 * @param jsonObject
	 * @return
	 */
	void updateUdaMethod(JSONObject jsonObject) throws Exception;

	/**
	 * （UDA，售价，成本）修改接口 --新 根据修改单ID 处理货品的修改
	 *
	 * @param queryParamJSON
	 * @return
	 */

	void updateUdaMethodByEcoId(JSONObject queryParamJSON) throws Exception;

	/**
	 * 修改ITEM属性返回接口
	 * 
	 * @param jsonObject
	 * @return
	 */
	void updateItemPropertyReturns(JSONObject jsonObject) throws Exception;

	/**
	 * UDA属性同步
	 * 
	 * @param jsonObject
	 * @return
	 */
	void udaAttributeSyn(JSONObject jsonObject) throws Exception;

	/**
	 * 部门及分类编码传输
	 * 
	 * @param jsonObject
	 * @return
	 */
	void depClasCode(JSONObject jsonObject) throws Exception;

	/**
	 * 地点清单查询接口
	 * 
	 * @param
	 * @return
	 */
	JSONObject siteListingMethod(int id) throws Exception;

	/**
	 * 商品售价区域接口
	 * 
	 * @param jsonObject
	 * @return
	 */
	void commodityPriceArea(JSONObject jsonObject) throws Exception;

	/**
	 * 获取货品数据
	 * 
	 * @param jsonObject
	 * @return
	 */
	JSONObject obtainGoodsData(JSONObject jsonObject) throws Exception;

	/**
	 * 获取促销数据
	 * 
	 * @param jsonObject
	 * @return
	 */
	JSONObject gainSalesOutlets(JSONObject jsonObject) throws Exception;

	/**
	 * 获取促销门店
	 * 
	 * @param jsonObject
	 * @return
	 */
	JSONObject gainSalesOutletsShop(JSONObject jsonObject) throws Exception;

	List<Object> getUdaAttributeByUdaId(JSONObject jsonObject) throws Exception;

	List<Object> getLocByDescName(JSONObject queryParamJSON) throws Exception;

	void toSendEmailforNew() throws Exception;

	void generateDrugCsv(PlmProductDrugEcoEntity_HI entity, PlmProductHeadEcoEntity_HI headEco) throws Exception;

	void updateCommenDate(List<Map<String, String>> updatePriceDateMap);

	String getBpmUrl();
}
