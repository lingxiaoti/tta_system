package com.sie.saaf.base.shiro.services;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.shiro.model.inter.IBaseOrgResponsibility;
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

@RestController
@RequestMapping("/baseOrgResponsibilityService")
public class BaseOrgResponsibilityService extends CommonAbstractService {
	private static final Logger logger = LoggerFactory.getLogger(BaseOrgResponsibilityService.class);
	@Autowired
	private IBaseOrgResponsibility baseOrgResponsibilityServer;

	@Override
	public IBaseCommon getBaseCommonServer() {
		return baseOrgResponsibilityServer;
	}

	/**
	 * 查找数据
	 *
	 * @param params
	 * @param pageIndex
	 * @param pageRows
	 *
	 * @return
	 *
	 * @author ZhangJun
	 * @创建时间 2018/1/10
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findPagination")
	public String findPagination(@RequestParam(required = false) String params,
								 @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
								 @RequestParam(required = false, defaultValue = "10") Integer pageRows) {
		return super.findPagination(params, pageIndex, pageRows);
	}

	/**
	 * 保存或更新数据
	 *
	 * @param params
	 *
	 * @return
	 *
	 * @author ZhangJun
	 * @创建时间 2018/1/10
	 */
	@RequestMapping(method = RequestMethod.POST, value = "save")
	public String saveOrUpdate(@RequestParam(required = true) String params) {
		return super.saveOrUpdate(params);
	}

	/**
	 * 删除数据
	 *
	 * @param params 参数id
	 * {
	 * id:需要删除的数据Id，如果需要删除多个，则用;分隔
	 * }
	 *
	 * @return
	 *
	 * @author ZhangJun
	 * @创建时间 2018/1/10
	 */
	@RequestMapping(method = RequestMethod.POST, value = "delete")
	public String delete(@RequestParam(required = false) String params) {
		return super.delete(params);
	}

	/**
	 * 保存数据
	 * @param params 参数<br>
	 *     {<br>
	 *         responsibilityId:职责Id,<br>
	 *         orgIds:[1,2,3,4]组织Id数组<br>
	 *     }
	 * @return
	 * @author ZhangJun
	 * @createTime 2018/1/10 11:19
	 * @description 保存数据
	 */
	@RequestMapping(method = RequestMethod.POST,value="saveOrgResp")
	public String saveOrgResp(@RequestParam(required=false) String params){
	    try{
			JSONObject queryParamJSON = parseObject(params);
			this.baseOrgResponsibilityServer.saveOrgResp(queryParamJSON);
	        return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", 0, null).toString();
	    }catch(Exception e){
	        logger.error(e.getMessage(),e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
	    }
	}

}
