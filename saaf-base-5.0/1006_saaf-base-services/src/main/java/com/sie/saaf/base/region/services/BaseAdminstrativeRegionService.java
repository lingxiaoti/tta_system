package com.sie.saaf.base.region.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.region.model.client.BaseAdministrativeRegionClient;
import com.sie.saaf.base.region.model.entities.BaseAdminstrativeRegionEntity_HI;
import com.sie.saaf.base.region.model.inter.IBaseAdminstrativeRegion;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/baseAdminstrativeRegionService")
public class BaseAdminstrativeRegionService extends CommonAbstractService {
	private static final Logger LOGGER = LoggerFactory.getLogger(BaseAdminstrativeRegionService.class);

	@Autowired
	private IBaseAdminstrativeRegion baseAdminstrativeRegionServer;
	@Autowired
	private BaseAdministrativeRegionClient baseAdministrativeRegionClient;

	@Override
	public IBaseCommon getBaseCommonServer(){
		return this.baseAdminstrativeRegionServer;
	}

	/**
	 * 初始化
	 * @author ZhangJun
	 * @createTime 2018/11/21
	 * @description 初始化
	 */
	@RequestMapping(method = RequestMethod.POST,value="initialize")
	public String initialize(@RequestParam(required=false) String params){
	    try{
			JSONObject queryParamJSON = parseObject(params);
			baseAdministrativeRegionClient.initialize(queryParamJSON);
	        return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, 0, null).toString();
	    }catch(Exception e){
	        LOGGER.error(e.getMessage(),e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
	    }
	}

	/**
	 * 查询国家
	 * @param params {
	 *     regionName:国家名称
	 * }
	 * @author ZhangJun
	 * @createTime 2018/11/21
	 * @description 查询国家
	 */
	@RequestMapping(method = RequestMethod.POST,value="findCountry")
	public String findCountry(@RequestParam(required=false) String params){
		try{
			JSONObject queryParamJSON = parseObject(params);
			List findList = this.baseAdminstrativeRegionServer.findCountry(queryParamJSON);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, findList.size(), findList).toString();
		}catch(Exception e){
			LOGGER.error(e.getMessage(),e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * 查询省份
	 * @param params {
	 *     regionName:省份名称,
	 *     adCode:区域编码
	 * }
	 * @author ZhangJun
	 * @createTime 2018/11/21
	 * @description 查询省份
	 */
	@RequestMapping(method = RequestMethod.POST,value="findProvinces")
	public String findProvinces(@RequestParam(required=false) String params){
		try{
			JSONObject queryParamJSON = parseObject(params);
			List findList = this.baseAdminstrativeRegionServer.findProvinces(queryParamJSON);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, findList.size(), findList).toString();
		}catch(Exception e){
			LOGGER.error(e.getMessage(),e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}


	/**
	 * 分页查询列表
	 * @param params {
	 *     regionId:行政区域Id,
	 *     parentRegionId:上级区域Id,
	 *     cityCode:城市编码,
	 *     adCode:区域编码,
	 *     regionName:行政区域名称,
	 *     regionLevel:行政区划级别。country:国家,province:省份,city:市,district:区县,street:街道
	 *     isHotCity:是否热门城市,Y/N
	 * }
	 * @author ZhangJun
	 * @createTime 2018/11/21
	 * @description 分页查询列表
	 */
	@Override
	@RequestMapping(method = RequestMethod.POST,value="findPagination")
	public String findPagination(@RequestParam(required=false) String params,
								 @RequestParam(required = false,defaultValue = "1") Integer pageIndex,
								 @RequestParam(required = false,defaultValue = "10") Integer pageRows){
		try{
			JSONObject queryParamJSON = parseObject(params);
			Pagination findList = this.baseAdminstrativeRegionServer.findRegionPagination(queryParamJSON,pageIndex,pageRows);
			JSONObject results = JSONObject.parseObject(JSON.toJSONString(findList));
			results.put(SToolUtils.STATUS, SUCCESS_STATUS);
			results.put(SToolUtils.MSG, SUCCESS_MSG);
			return results.toString();
		}catch(Exception e){
			LOGGER.error(e.getMessage(),e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * 保存
	 * @param params {
	 *     regionId:行政区域Id,
	 *     parentRegionId:上级行政区域Id,
	 *     cityCode:城市编码,
	 *     adCode:区域编码,
	 *     regionName:行政区域名称,
	 *     regionCenter:城市中心点经纬度,
	 *     regionLevel:行政区划级别。country:国家,province:省份,city:市,district:区县,street:街道,
	 *     regionDescription:行政区域描述,
	 *     isHotCity:是否热门城市,Y/N,
	 *     versionNum:版本号
	 * }
	 * @author ZhangJun
	 * @createTime 2018/11/21
	 * @description 保存
	 */
	@Override
	@RequestMapping(method = RequestMethod.POST,value="save")
	public String saveOrUpdate(@RequestParam(required=false) String params){
		return super.saveOrUpdate(params);
	}

	/**
	 * 根据主键查询
	 * @param params {
	 *     id:行政区域Id
	 * }
	 * @author ZhangJun
	 * @createTime 2018/11/21
	 * @description
	 */
	@Override
	@RequestMapping(method = RequestMethod.POST,value="findById")
	public String findById(@RequestParam(required=false) String params){
		return super.findById(params);
	}
}