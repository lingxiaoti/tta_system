package com.sie.saaf.base.user.services;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.user.model.entities.BasePersonOrganizationEntity_HI;
import com.sie.saaf.base.user.model.inter.IBasePersonOrganization;
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
 * @creteTime 2017-12-15
 */
@RestController
@RequestMapping("/basePersonOrganizationService")
public class BasePersonOrganizationService extends CommonAbstractService {
	private static final Logger logger = LoggerFactory.getLogger(BasePersonOrganizationService.class);
	@Autowired
	private IBasePersonOrganization basePersonOrganizationServer;
	@Override
	public IBaseCommon getBaseCommonServer() {
		return this.basePersonOrganizationServer;
	}

	/**
	 * 根据Id查询记录
	 * @author ZhangJun
	 * @createTime 2018/1/21 15:25
	 * @description 根据Id查询记录
	 */
	@Override
	public String findById(String params) {
		return super.findById(params);
	}

	/**
	 * 保存或更新数据
	 * @param params <br>
	 *            {<br>
	 *            personOrgId:主键Id（更新时必填）<br>
	 *            positionId:职位Id<br>
	 *            orgId:组织Id<br>
	 *            personId:人员Id<br>
	 *            startDate:生效日期<br>
	 *            endDate:失效日期<br>
	 *            versionNum: 版本号（更新时必填）<br>
	 *            }<br>
	 * @return {
	 * status:操作是否成功,E:失败，S:成功
	 * msg:成功或者失败后消息
	 * count:成功的记录数
	 * data:成功的数据
	 * }
	 * @author ZhangJun
	 * @creteTime 2017/12/15
	 */
	@RequestMapping(method = RequestMethod.POST, value = "save")
	@Override
	public String saveOrUpdate(@RequestParam(required = true) String params){
		return super.saveOrUpdate(params);
	}

	/**
	 * 查找数据
	 * @param params
	 * @param pageIndex
	 * @param pageRows
	 * @return {
	 * status:操作是否成功,E:失败，S:成功
	 * msg:成功或者失败后消息
	 * count:成功的记录数
	 * data:成功的数据
	 * }
	 * @author ZhangJun
	 * @creteTime 2017/12/15
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findPagination")
	@Override
	public String findPagination(@RequestParam(required = false) String params,
								 @RequestParam(required = false,defaultValue = "1") Integer pageIndex,
								 @RequestParam(required = false,defaultValue = "10") Integer pageRows) {
		return super.findPagination(params,pageIndex,pageRows);
	}


	/**
	 * 删除数据
	 * @param params 参数id
	 * {
	 *     id:需要删除的数据Id，如果需要删除多个，则用;分隔
	 * }
	 * @return {
	 * status:操作是否成功,E:失败，S:成功
	 * msg:成功或者失败后消息
	 * count:成功的记录数
	 * data:成功的数据
	 * }
	 * @author ZhangJun
	 * @creteTime 2017/12/15
	 */
	@RequestMapping(method = RequestMethod.POST, value = "delete")
	@Override
	public String delete(@RequestParam(required = false) String params) {
		return super.delete(params);
	}

	/**
	 * 保存组织与人员关系
	 * @param params 保存参数<br>
	 * {<br>
	 *     positionId:职位Id,<br>
	 *     orgId:组织Id,<br>
	 *     personIds:[人员Id数组],<br>
	 *     startDate:生效日期,<br>
	 *     endDate:失效日期<br>
	 * }
	 * @return
	 *
	 * @author ZhangJun
	 * @createTime 2018/1/21 13:45
	 * @description 保存组织与人员关系
	 */
	@RequestMapping(method = RequestMethod.POST,value="saveOrgPerson")
	public String saveOrgPerson(@RequestParam(required=false) String params){
	    try{
			JSONObject queryParamJSON = parseObject(params);
			List<BasePersonOrganizationEntity_HI> saveList = this.basePersonOrganizationServer.saveOrgPerson(queryParamJSON);
	        return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", saveList.size(), saveList).toString();
	    }catch(Exception e){
	        logger.error(e.getMessage(),e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
	    }
	}
	
}
