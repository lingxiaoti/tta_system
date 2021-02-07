package com.sie.saaf.base.shiro.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.shiro.model.inter.IBaseResource;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.common.util.SaafToolUtils;
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
 * @creteTime 2017-12-18
 */
@RestController
@RequestMapping("/baseResourceService")
public class BaseResourceService extends CommonAbstractService {
	private static final Logger logger = LoggerFactory.getLogger(BaseResourceService.class);

	@Autowired
	private IBaseResource baseResourceServer;

	@Override
	public IBaseCommon getBaseCommonServer() {
		return baseResourceServer;
	}

	@RequestMapping(method = RequestMethod.POST, value = "findById")
	@Override
	public String findById(String params) {
		return super.findById(params);
	}

	/**
	 * 查找数据
	 * @param params
	 * @param pageIndex
	 * @param pageRows
	 * @return
	 * @author ZhangJun
	 * @creteTime 2017/12/18
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findPagination")
	public String findPagination(@RequestParam(required = false) String params,
			@RequestParam(required = false,defaultValue = "1") Integer pageIndex,
			@RequestParam(required = false,defaultValue = "10") Integer pageRows) {
		try{
			JSONObject queryParamJSON = parseObject(params);
			queryParamJSON=SaafToolUtils.cleanNull(queryParamJSON,"systemCode","resourceType");
			Pagination findList = this.baseResourceServer.findResourcePagination(queryParamJSON,pageIndex,pageRows);
			JSONObject results = JSONObject.parseObject(JSON.toJSONString(findList));
			results.put(SToolUtils.STATUS, SUCCESS_STATUS);
			results.put(SToolUtils.MSG, "成功");
			return results.toString();
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * 保存或更新数据
	 * @param params
	 * @return
	 * @author ZhangJun
	 * @creteTime 2017/12/18
	 */
	@RequestMapping(method = RequestMethod.POST, value = "save")
	public String saveOrUpdate(@RequestParam(required = true) String params){
		JSONObject queryParamJSON = parseObject(params);
		JSONObject result = this.baseResourceServer.validCodeOrName(queryParamJSON);
		if(!result.getBooleanValue("validFlag")){
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, result.getString("msg"), 0, null).toString();
		}else {
			return super.saveOrUpdate(params);
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
	 * @creteTime 2017/12/18
	 */
	@RequestMapping(method = RequestMethod.POST, value = "delete")
	public String delete(@RequestParam(required = false) String params) {
		return super.delete(params);
	}

	/**
	 * 根据菜单查询资源
	 *
	 * @param params JSON参数
	 * {menuId:菜单Id}
	 *
	 * @return 资源列表<br>
	 * [{<br>
	 * resourceId:资源标识<br>
	 * menuId:菜单Id，节点标识 对应到功能<br>
	 * resourceCode:资源编号（与功能按钮编码对应）<br>
	 * buttonUrl:按钮对应的执行方法地址<br>
	 * orderNo:排序号<br>
	 * resourceType:类型标识(按钮、方法、字段名、代码片段)<br>
	 * resourceName:资源名称<br>
	 * resourceDesc:资源描述<br>
	 * creationDate:创建日期<br>
	 * createdBy:创建人<br>
	 * lastUpdatedBy:更新人<br>
	 * lastUpdateDate:更新日期<br>
	 * versionNum:版本号<br>
	 * }]
	 *
	 * @author ZhangJun
	 * @creteTime 2017/12/14
	 */
	@RequestMapping(method = RequestMethod.POST,value="findBaseResourceByMenuId")
	public String findBaseResourceByMenuId(@RequestParam(required=false) String params){
	    try{
			JSONObject queryParamJSON = parseObject(params);
			Integer menuId = queryParamJSON.getInteger("menuId");
			List findList = this.baseResourceServer.findBaseResourceByMenuId(menuId);
	        return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", findList.size(), findList).toString();
	    }catch(Exception e){
	        logger.error(e.getMessage(),e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
	    }
	}

	/**
	 * 根据角色Id查询角色所分配的资源
	 *
	 * @param params JSON参数
	 * {roleId:角色Id}
	 * @param pageIndex 页码
	 * @param pageRows 每页查询记录数
	 *
	 * @return 分页资源列表<br>
	 * { <br>
	 * count: 总记录数,<br>
	 * curIndex: 当前页索引,<br>
	 * data: [{<br>
	 * resourceId:资源标识<br>
	 * menuId:菜单Id，节点标识 对应到功能<br>
	 * resourceCode:资源编号（与功能按钮编码对应）<br>
	 * buttonUrl:按钮对应的执行方法地址<br>
	 * orderNo:排序号<br>
	 * resourceType:类型标识(按钮、方法、字段名、代码片段)<br>
	 * resourceName:资源名称<br>
	 * resourceDesc:资源描述<br>
	 * creationDate:创建日期<br>
	 * createdBy:创建人<br>
	 * lastUpdatedBy:更新人<br>
	 * lastUpdateDate:更新日期<br>
	 * versionNum:版本号<br>
	 * }],<br>
	 * firstIndex: 首页索引,<br>
	 * lastIndex: 尾页索引,<br>
	 * nextIndex: 下一页索引,<br>
	 * pageSize: 每页记录数,<br>
	 * pagesCount: 总页数,<br>
	 * preIndex: 上一页索引<br>
	 * }
	 *
	 * @author ZhangJun
	 * @creteTime 2017/12/14
	 */
	@RequestMapping(method = RequestMethod.POST,value="findBaseResourceByRoleId")
	public String findBaseResourceByRoleId(@RequestParam(required=false) String params,
										   @RequestParam(required = false,defaultValue = "1") Integer pageIndex,
										   @RequestParam(required = false,defaultValue = "10") Integer pageRows){
	    try{
			JSONObject queryParamJSON = parseObject(params);
			Integer roleId = queryParamJSON.getInteger("roleId");
			Pagination findList = this.baseResourceServer.findBaseResourceByRoleId(roleId,pageIndex,pageRows);
			JSONObject results = JSONObject.parseObject(JSON.toJSONString(findList));
			results.put(SToolUtils.STATUS, SUCCESS_STATUS);
			results.put(SToolUtils.MSG, "成功");
			return results.toString();
	    }catch(Exception e){
	        logger.error(e.getMessage(),e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
	    }
	}

	/**
	 * 根据菜单和职责id获取资源
	 * @param params {
	 *     menuId:菜单Id,
	 *     respId:职责Id
	 * }
	 * @return 资源列表<br>
	 * { <br>
	 * count: 总记录数,<br>
	 * status: 消息状态,<br>
	 * msg: 消息,<br>
	 * data: [{<br>
	 * resourceId:资源标识<br>
	 * menuId:菜单Id，节点标识 对应到功能<br>
	 * resourceCode:资源编号（与功能按钮编码对应）<br>
	 * buttonUrl:按钮对应的执行方法地址<br>
	 * orderNo:排序号<br>
	 * resourceType:类型标识(按钮、方法、字段名、代码片段)<br>
	 * resourceName:资源名称<br>
	 * resourceDesc:资源描述<br>
	 * creationDate:创建日期<br>
	 * createdBy:创建人<br>
	 * lastUpdatedBy:更新人<br>
	 * lastUpdateDate:更新日期<br>
	 * versionNum:版本号<br>
	 * }]<br>
	 * }
	 * @author ZhangJun
	 * @createTime 2018/2/2
	 * @description 根据菜单和职责id获取资源
	 */
	@RequestMapping(method = RequestMethod.POST,value="findBaseResourceByRespMenuId")
	public String findBaseResourceByRespId(@RequestParam(required=false) String params){
	    try{
			JSONObject queryParamJSON = parseObject(params);
			if("Y".equals(queryParamJSON.getString("varIsadmin"))){
				queryParamJSON.put("respId", Integer.MIN_VALUE);
			}
			SaafToolUtils.validateJsonParms(queryParamJSON,"respId","menuId");
			Integer respId = queryParamJSON.getInteger("respId");
			Integer menuId = queryParamJSON.getInteger("menuId");
			List findList = baseResourceServer.findBaseResourceByRespMenuId(menuId,respId);
	        return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", findList.size(), findList).toString();
	    }catch(Exception e){
	        logger.error(e.getMessage(),e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
	    }
	}
}
