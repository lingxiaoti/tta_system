package com.sie.saaf.base.shiro.services;

import com.sie.saaf.base.shiro.model.inter.IBasePrivilegeDetail;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ZhangJun
 * @createTime 2018-01-11 19:13
 * @description
 */
@RestController
@RequestMapping("/basePrivilegeDetailService")
public class BasePrivilegeDetailService extends CommonAbstractService {
	private static final Logger logger = LoggerFactory.getLogger(BasePrivilegeDetailService.class);

	@Autowired
	private IBasePrivilegeDetail basePrivilegeDetailServer;
	@Override
	public IBaseCommon getBaseCommonServer() {
		return basePrivilegeDetailServer;
	}

	/**
	 * 根据Id查询数据
	 * @param params 参数id
	 * {
	 *     id:主键Id
	 * }
	 * @return
	 * @author ZhangJun
	 * @creteTime 2018/1/11
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findById")
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
	 * @创建时间 2018/1/11
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
	 * @创建时间 2018/1/11
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
	 * @创建时间 2018/1/11
	 */
	@RequestMapping(method = RequestMethod.POST, value = "delete")
	public String delete(@RequestParam(required = false) String params) {
		return super.delete(params);
	}
}
