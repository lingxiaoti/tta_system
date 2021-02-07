package com.sie.saaf.common.cache.server;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.constant.CommonConstants;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisCluster;

/**
 * Redis数据获取
 * @author ZhangJun
 * @createTime 2018-03-31 00:11
 * @description 该类可直接注入到需要使用的Server中，通过该类可以获取保存在Redis中的业务数据
 */
@Component
public class RedisCacheDataServer implements IRedisCacheData {

	@Autowired
	private JedisCluster jedisCluster;

	private JSONObject parseObject(String cacheVal){
		if(StringUtils.isBlank(cacheVal)){
			return new JSONObject();
		}else {
			return JSONObject.parseObject(cacheVal);
		}
	}

	private JSONArray parseArray(String cacheVal){
		if(StringUtils.isBlank(cacheVal)){
			return new JSONArray();
		}else {
			return JSONArray.parseArray(cacheVal);
		}
	}

	/**
	 * 根据快码类型、系统编码查询快码值
	 * @param lookupType 快码类型
	 * @param systemCode 系统编码
	 * @return [{
	 * 		"description": 描述,
	 *      "lookupCode": 编码,
	 *      "lookupType": 快码类型,
	 *      "lookupValuesId": 快码ID,
	 *      "meaning": 快码说明,
	 *      "parentLookupValuesId": 父节点，
	 *      "buOrgId": BU组织ID
	 * }]
	 * @author ZhangJun
	 * @createTime 2018/3/31
	 * @description 根据快码类型、系统编码查询快码值
	 */
	@Override
	public JSONArray findLookupValuesByLookupType(String lookupType, String systemCode){
		String key = lookupType+"_"+systemCode;
		String cacheVal = jedisCluster.hget(CommonConstants.RedisCacheKey.BASE_LOOKUP_DATA_KEY_BY_LOOKUPTYPE,key);
		return parseArray(cacheVal);
	}

	/**
	 * 根据快码类型、系统编码查询快码
	 * @param lookupType 快码类型
	 * @param systemCode 系统编码
	 * @return {
	 *     lookupValueCode：meaning
	 * }
	 * @author ZhangJun
	 * @createTime 2018/4/4
	 * @description 根据快码类型、系统编码查询快码
	 */
	@Override
	public JSONObject findLookupValueMeaning(String lookupType, String systemCode) {
		JSONArray resultArray = this.findLookupValuesByLookupType(lookupType,systemCode);
		JSONObject result = new JSONObject();
		if (resultArray != null && !resultArray.isEmpty()) {

			for (int i = 0; i < resultArray.size(); i++) {
				JSONObject jsonObject = resultArray.getJSONObject(i);
				result.put(jsonObject.getString("lookupCode"),jsonObject.getString("meaning"));
			}
		}
		return result;
	}

	/**
	 * 根据快码类型、系统编码查询快码
	 * @param lookupType 快码类型
	 * @param systemCode 系统编码
	 * @return {
	 *     lookupValueCode：Description
	 * }
	 * @author ZhangJun
	 * @createTime 2018/4/4
	 * @description 根据快码类型、系统编码查询快码
	 */
	@Override
	public JSONObject findLookupValueDescription(String lookupType, String systemCode) {
		JSONArray resultArray = this.findLookupValuesByLookupType(lookupType,systemCode);
		JSONObject result = new JSONObject();
		if (resultArray != null && !resultArray.isEmpty()) {

			for (int i = 0; i < resultArray.size(); i++) {
				JSONObject jsonObject = resultArray.getJSONObject(i);
				result.put(jsonObject.getString("lookupCode"),jsonObject.getString("description"));
			}
		}
		return result;
	}

	/**
	 * 根据快码行表ID查询快码值
	 * @param lookupValuesId 快码行表ID
	 * @return 快码对象 {
	 * 		"description": 描述,
	 *      "lookupCode": 编码,
	 *      "lookupType": 快码类型,
	 *      "lookupValuesId": 快码ID,
	 *      "meaning": 快码说明,
	 *      "parentLookupValuesId": 父节点，
	 *      "buOrgId": BU组织ID
	 * }
	 * @author ZhangJun
	 * @createTime 2018/3/31
	 * @description 根据快码行表ID查询快码值
	 */
	@Override
	public JSONObject findLookupValueById(Integer lookupValuesId) {
		if(lookupValuesId==null){
			return new JSONObject();
		}
		String cacheVal = jedisCluster.hget(CommonConstants.RedisCacheKey.BASE_LOOKUP_DATA_KEY_BY_LOOKUPVALUEID,String.valueOf(lookupValuesId));
		return parseObject(cacheVal);
	}

	/**
	 * 获取全局Redis缓存，Redis中的键为GLOBAL_REDIS_CACHE
	 * @param redisKey Redis键
	 * @return redis值
	 * @author ZhangJun
	 * @createTime 2018/3/31
	 * @description 获取全局Redis缓存，由于全局缓存中可以自行设置值，这里只返回String类型
	 */
	@Override
	public String findGlocalRedisCacheValue(String redisKey){
		if(StringUtils.isBlank(redisKey)){
			return null;
		}
		return jedisCluster.hget(CommonConstants.RedisCacheKey.GLOBAL_REDIS_CACHE,redisKey);
	}

	/**
	 * 根据库存组织Id获取库存组织对象
	 * @param organizationId 库存组织Id
	 * @return {
	 *     organizationId:库存组织ID,
	 *     organizationCode:库存组织编码,
	 *     organizationName:库存组织名称,
	 *     orgId:OU组织ID
	 * }
	 * @author ZhangJun
	 * @createTime 2018/3/31
	 * @description 根据库存组织Id获取库存组织对象
	 */
	@Override
	public JSONObject findOrganizationInv(Integer organizationId){
		if(organizationId==null){
			return new JSONObject();
		}
		String cacheVal = jedisCluster.hget(CommonConstants.RedisCacheKey.BASE_ORGANIZATION_INV_VIEW_KEY,String.valueOf(organizationId));
		return parseObject(cacheVal);
	}

	/**
	 * 根据产品Id获取产品对象
	 * @param itemId 产品ID
	 * @author ZhangJun
	 * @createTime 2018/3/31
	 * @description 根据产品Id获取产品对象
	 */
	@Override
	public JSONObject findProductInfoByItemId(Integer itemId){
		if(itemId==null){
			return new JSONObject();
		}
		String cacheVal = jedisCluster.hget(CommonConstants.RedisCacheKey.BASE_PRODUCT_INFO_BY_ITEM_ID_KEY,String.valueOf(itemId));
		return parseObject(cacheVal);
	}

	/**
	 * 根据产品编码获取产品对象
	 * @param itemCode 产品编码
	 * @author ZhangJun
	 * @createTime 2018/3/31
	 * @description 根据产品编码获取产品对象
	 */
	@Override
	public JSONObject findProductInfoByCode(String itemCode){
		if(StringUtils.isBlank(itemCode)){
			return new JSONObject();
		}
		String cacheVal = jedisCluster.hget(CommonConstants.RedisCacheKey.BASE_PRODUCT_INFO_BY_ITEM_CODE_KEY,itemCode);
		return parseObject(cacheVal);
	}

	/**
	 * 根据子库编码获取子库对象
	 * @param warehouseCode 子库编码
	 * @author ZhangJun
	 * @createTime 2018/3/31
	 * @description 根据子库编码获取子库对象
	 */
	@Override
	public JSONObject findWarehouseByCode(String warehouseCode){
		if(StringUtils.isBlank(warehouseCode)){
			return new JSONObject();
		}
		String cacheVal = jedisCluster.hget(CommonConstants.RedisCacheKey.BASE_WAREHOUSE_MAPPING_KEY,warehouseCode);
		return parseObject(cacheVal);
	}
}
