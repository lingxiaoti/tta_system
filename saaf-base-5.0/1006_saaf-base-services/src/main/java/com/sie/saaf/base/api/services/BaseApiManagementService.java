package com.sie.saaf.base.api.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.api.model.inter.IBaseApiManagement;
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

/**
 * @author ZhangJun
 * @creteTime 2017-12-15
 */
@RestController
@RequestMapping("/baseApiManagementService")
public class BaseApiManagementService extends CommonAbstractService {
	private static final Logger logger = LoggerFactory.getLogger(BaseApiManagementService.class);
	@Autowired
	private IBaseApiManagement baseApiManagementServer;

	public IBaseCommon getBaseCommonServer() {
		return this.baseApiManagementServer;
	}

	/**
	 * 保存或更新数据
	 *
	 * @param params
	 *
	 * @return
	 *
	 * @author ZhangJun
	 * @creteTime 2017/12/15
	 */
	@RequestMapping(method = RequestMethod.POST, value = "save")
	public String saveOrUpdate(@RequestParam(required = true) String params) {
		return super.saveOrUpdate(params);
	}

	/**
	 * 查找数据
	 *
	 * @param params {
	 * apiId:主键ID
	 * interfaceName:API名称
	 * requestMode:请求方式：Post/Get
	 * apiStatus:状态
	 * developer:开发人员
	 * centerName:项目/中心名称
	 * centerCode:项目/中心编码
	 * modelName:模块名称
	 * modelCode:模块编码
	 * versionNum:版本号
	 * }
	 * @param pageIndex
	 * @param pageRows
	 *
	 * @return {
	 * msg: 成功,
	 * data: [
	 * {
	 * apiId:主键ID
	 * interfaceName:API名称
	 * requestMode:请求方式：Post/Get
	 * apiStatus:状态
	 * urlAddress:服务地址
	 * developer:开发人员
	 * apiDesc:详细描述
	 * requestParam:请求参数
	 * requestParamDict:请求参数描述
	 * responseParam:返回参数
	 * responseParamDict:返回参数描述
	 * centerName:项目/中心名称
	 * centerCode:项目/中心编码
	 * modelName:模块名称
	 * modelCode:模块编码
	 * versionNum:版本号
	 * }],
	 * firstIndex: 首页索引,
	 * lastIndex: 尾页索引,
	 * nextIndex: 下一页索引,
	 * pageSize: 每页记录数,
	 * pagesCount: 总页数,
	 * preIndex: 上一页索引
	 * count: 总记录数,
	 * curIndex: 当前页索引,
	 * }
	 *
	 * @author ZhangJun
	 * @creteTime 2017/12/15
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findPagination")
	public String findPagination(@RequestParam(required = false) String params,
								 @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
								 @RequestParam(required = false, defaultValue = "10") Integer pageRows) {
		try{
			JSONObject queryParamJSON = parseObject(params);
			queryParamJSON = SaafToolUtils.cleanNull(queryParamJSON,"apiStatus");
			Pagination findList = this.baseApiManagementServer.findBaseApiManagementPagination(queryParamJSON,pageIndex,pageRows);

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
	 * @creteTime 2017/12/15
	 */
	@RequestMapping(method = RequestMethod.POST, value = "delete")
	public String delete(@RequestParam(required = false) String params) {
		return super.delete(params);
	}

	/**
	 * 根据Id查询数据
	 * @param params 参数id
	 * {
	 *     id:主键Id
	 * }
	 * @return
	 * @author ZhangJun
	 * @creteTime 2018/1/10
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findById")
	public String findById(String params) {
		return super.findById(params);
	}

}
