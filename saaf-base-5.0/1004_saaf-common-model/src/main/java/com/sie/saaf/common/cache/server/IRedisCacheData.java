package com.sie.saaf.common.cache.server;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * Redis缓存数据接口
 * @author ZhangJun
 * @createTime 2018-03-31 00:43
 * @description
 */
public interface IRedisCacheData {
	/**
	 * 根据快码类型、系统编码查询快码值
	 * @param lookupType 快码类型
	 * @param systemCode 系统编码
	 * @author ZhangJun
	 * @createTime 2018/3/31
	 * @description 根据快码类型、系统编码查询快码值
	 */
	JSONArray findLookupValuesByLookupType(String lookupType, String systemCode);

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
	JSONObject findLookupValueMeaning(String lookupType, String systemCode);

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
	JSONObject findLookupValueDescription(String lookupType, String systemCode);

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
	JSONObject findLookupValueById(Integer lookupValuesId);
	/**
	 * 获取全局Redis缓存，Redis中的键为GLOBAL_REDIS_CACHE
	 * @param redisKey Redis键
	 * @return redis值
	 * @author ZhangJun
	 * @createTime 2018/3/31
	 * @description 获取全局Redis缓存，由于全局缓存中可以自行设置值，这里只返回String类型
	 */
	String findGlocalRedisCacheValue(String redisKey);
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
	JSONObject findOrganizationInv(Integer organizationId);
	/**
	 * 根据产品Id获取产品对象
	 * @param itemId 产品ID
	 * @author ZhangJun
	 * @createTime 2018/3/31
	 * @description 根据产品Id获取产品对象
	 */
	JSONObject findProductInfoByItemId(Integer itemId);
	/**
	 * 根据产品编码获取产品对象
	 * @param itemCode 产品编码
	 * @author ZhangJun
	 * @createTime 2018/3/31
	 * @description 根据产品编码获取产品对象
	 */
	JSONObject findProductInfoByCode(String itemCode);
	/**
	 * 根据子库编码获取子库对象
	 * @param warehouseCode 子库编码
	 * @author ZhangJun
	 * @createTime 2018/3/31
	 * @description 根据子库编码获取子库对象
	 */
	JSONObject findWarehouseByCode(String warehouseCode);

}
