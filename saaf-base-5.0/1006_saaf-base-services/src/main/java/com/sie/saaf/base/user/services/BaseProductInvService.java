package com.sie.saaf.base.user.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.accredit.annotation.Permission;
import com.sie.saaf.base.user.model.entities.BaseProductInvEntity_HI;
import com.sie.saaf.base.user.model.inter.IBaseProductInv;
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

/**
 * @author ZhangJun
 * @createTime 2018-03-09 10:08
 * @description
 */
@RestController
@RequestMapping("/baseProductInvService")
public class BaseProductInvService extends CommonAbstractService {
	private static final Logger logger = LoggerFactory.getLogger(BaseProductInvService.class);

	@Autowired
	private IBaseProductInv baseProductInvServer;

	@Override
	public IBaseCommon getBaseCommonServer() {
		return baseProductInvServer;
	}

	/**
	 * 根据Id查询数据
	 * @param params 参数id
	 * {
	 *     id:主键Id
	 * }
	 * @return
	 * @author ZhangJun
	 * @creteTime 2018/3/9
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findById")
	public String findById(String params) {
		return super.findById(params);
	}

	/**
	 * 查找数据
	 * @param params
	 * @return
	 * @author ZhangJun
	 * @createTime 2018/3/9
	 * @description 查找数据
	 */
	@RequestMapping(method = RequestMethod.POST,value="findList")
	public String findList(@RequestParam(required=false) String params){
		try{
			JSONObject queryParamJSON = parseObject(params);
			List list = baseProductInvServer.findROList(queryParamJSON);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", list.size(), list).toString();
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * 查找数据
	 * @param params
	 * @param pageIndex
	 * @param pageRows
	 * @return
	 * @author ZhangJun
	 * @createTime 2018/3/9
	 */
	@Permission(menuCode = "CPZKGXWH")
	@RequestMapping(method = RequestMethod.POST, value = "findPagination")
	public String findPagination(@RequestParam(required = false) String params,
			@RequestParam(required = false,defaultValue = "1") Integer pageIndex,
			@RequestParam(required = false,defaultValue = "10") Integer pageRows) {
		try{
			JSONObject queryParamJSON = parseObject(params);
			queryParamJSON.put("operationOrgIds",baseAccreditCacheServer.getOrgId(getSessionUserId()));
			Pagination findList = baseProductInvServer.findROPagination(queryParamJSON,pageIndex,pageRows);

			JSONObject results = JSONObject.parseObject(JSON.toJSONString(findList));
			results.put(SToolUtils.STATUS, SUCCESS_STATUS);
			results.put(SToolUtils.MSG, "成功");
			return results.toString();
		}catch (Exception e){
			logger.error(e.getMessage(),e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}
	
	/**
	 * 保存或更新数据
	 * @param params {
	 *     itemCode:产品编码,
	 *     warehouseCodes:['xx','xx']子库编码集合,
	 *     productInvId:主键,
	 *     versionNum:版本号
	 * }
	 * @return
	 * @author ZhangJun
	 * @createTime 2018/3/9
	 */
	@RequestMapping(method = RequestMethod.POST, value = "save")
	public String saveOrUpdate(@RequestParam(required = true) String params){
		try{
			JSONObject queryParamJSON = parseObject(params);

            BaseProductInvEntity_HI entitie = baseProductInvServer.saveAll(queryParamJSON);

			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", entitie == null ? 0 : 1, entitie).toString();
		}catch (Exception e){
			logger.error(e.getMessage(),e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * 删除数据
	 * @param params 参数id
	 * {
	 *     id:需要删除的数据Id，如果需要删除多个，则用;分隔
	 * }
	 * @return
	 * @author ZhangJun
	 * @createTime 2018/3/9
	 */
	@RequestMapping(method = RequestMethod.POST, value = "delete")
	public String delete(@RequestParam(required = false) String params) {
		return super.delete(params);
	}
}
