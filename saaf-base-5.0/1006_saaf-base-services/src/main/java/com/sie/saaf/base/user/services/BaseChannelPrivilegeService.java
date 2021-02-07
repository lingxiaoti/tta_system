package com.sie.saaf.base.user.services;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.user.model.inter.IBaseChannelPrivilege;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.yhg.base.utils.SToolUtils;
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
 * @createTime 2018-03-14 18:52
 * @description
 */
@RestController
@RequestMapping("/baseChannelPrivilegeService")
public class BaseChannelPrivilegeService extends CommonAbstractService {
	private static final Logger logger = LoggerFactory.getLogger(BaseChannelPrivilegeService.class);
	@Autowired
	private IBaseChannelPrivilege baseChannelPrivilegeServer;
	@Override
	public IBaseCommon getBaseCommonServer() {
		return baseChannelPrivilegeServer;
	}

	/**
	 * 根据Id查询数据
	 * @param params 参数id
	 * {
	 *     id:主键Id
	 * }
	 * @return
	 * @author ZhangJun
	 * @creteTime 2018/3/14
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
	 * @createTime 2018/3/14
	 * @description 查找数据
	 */
	@RequestMapping(method = RequestMethod.POST,value="findList")
	public String findList(@RequestParam(required=false) String params){
		return super.findList(params);
	}

	/**
	 * 查找数据
	 * @param params
	 * @param pageIndex
	 * @param pageRows
	 * @return
	 * @author ZhangJun
	 * @createTime 2018/3/14
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findPagination")
	public String findPagination(@RequestParam(required = false) String params,
			@RequestParam(required = false,defaultValue = "1") Integer pageIndex,
			@RequestParam(required = false,defaultValue = "10") Integer pageRows) {
		return super.findPagination(params,pageIndex,pageRows);
	}

	/**
	 * 保存或更新数据
	 * @param params
	 * @return
	 * @author ZhangJun
	 * @createTime 2018/3/14
	 */
	@RequestMapping(method = RequestMethod.POST, value = "save")
	public String saveOrUpdate(@RequestParam(required = true) String params){
		return super.saveOrUpdate(params);
	}

	/**
	 * 删除数据
	 * @param params 参数id
	 * {
	 *     id:需要删除的数据Id，如果需要删除多个，则用;分隔
	 * }
	 * @return
	 * @author ZhangJun
	 * @createTime 2018/3/14
	 */
	@RequestMapping(method = RequestMethod.POST, value = "delete")
	public String delete(@RequestParam(required = false) String params) {
		return super.delete(params);
	}
	
	@RequestMapping(method = RequestMethod.POST,value="saveSyncBaseChannelPrivilege")
	public String saveSyncBaseChannelPrivilege(@RequestParam(required=false) String params){
	    try{
			JSONObject queryParamJSON = parseObject(params);
	        JSONObject obj = this.baseChannelPrivilegeServer.saveSyncBaseChannelPrivilege(queryParamJSON);
	        return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", 0, new JSONArray().fluentAdd(obj)).toString();
	    }catch(Exception e){
	        logger.error(e.getMessage(),e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
	    }
	}

	@RequestMapping(method = RequestMethod.POST,value="findListByOrgId")
	public String findListByOrgId(@RequestParam(required=false) String params){
		try {
			JSONObject paramJSON = parseObject(params);
			List<String> listByOrgId = this.baseChannelPrivilegeServer.findListByOrgId(paramJSON);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", listByOrgId.size(), listByOrgId).toString();
		}catch (Exception e){
			logger.error(e.getMessage(),e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}
}
