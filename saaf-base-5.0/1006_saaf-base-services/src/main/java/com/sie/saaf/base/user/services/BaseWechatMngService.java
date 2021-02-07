package com.sie.saaf.base.user.services;

import com.sie.saaf.base.user.model.entities.BaseWechatMngEntity_HI;
import com.sie.saaf.base.user.model.inter.IBaseWechatMng;
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
 * @createTime 2017-12-27 18:05
 * @description
 */
@RestController
@RequestMapping("/baseWechatMngService")
public class BaseWechatMngService extends CommonAbstractService {
	private static final Logger logger = LoggerFactory.getLogger(BaseWechatMngService.class);
	@Autowired
	private IBaseWechatMng baseWechatMngServer;
	@Override
	public IBaseCommon<BaseWechatMngEntity_HI> getBaseCommonServer() {
		return baseWechatMngServer;
	}

	/**
	 * 查找数据
	 * @param params 查询参数<br>
	 *     {<br>
	 *         userId:用户Id<br>
	 *		   wxOpenId:公众号Id<br>
	 *     }
	 * @param pageIndex 页码
	 * @param pageRows 每页查询记录数
	 * @return {
	 * status:操作是否成功,E:失败，S:成功
	 * msg:成功或者失败后消息
	 * count:成功的记录数
	 * data:[{
	 *      wxId:主键Id<br>
	 *	    userId:用户Id,<br>
	 *      wxOpenId:公众号Id,<br>
	 *      unionId:unionId,<br>
	 *      creationDate:创建时间,<br>
	 *      createdBy:创建人,<br>
	 *		lastUpdatedBy:更新人,<br>
	 *		lastUpdateDate:更新时间,<br>
	 *		versionNum:版本号,<br>
	 *		operatorUserId:操作者,<br>
	 * }]
	 * }
	 * @author ZhangJun
	 * @创建时间 2017/12/27
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findPagination")
	public String findPagination(@RequestParam(required = false) String params,
			@RequestParam(required = false,defaultValue = "1") Integer pageIndex,
			@RequestParam(required = false,defaultValue = "10") Integer pageRows) {
		return super.findPagination(params,pageIndex,pageRows);
	}

	/**
	 * 保存或更新数据
	 * @param params 参数列表<br>
	 *     {<br>
	 *			wxId:主键Id（更新时必填）,<br>
	 *			userId:用户Id（必填）,<br>
	 *			wxOpenId:公众号Id（必填）,<br>
	 *		    unionId:unionId,<br>
	 *			creationDate:创建时间,<br>
	 *			createdBy:创建人,<br>
	 *			lastUpdatedBy:更新人,<br>
	 *			lastUpdateDate:更新时间,<br>
	 *			versionNum:版本号（更新时必填）,<br>
	 *     }
	 * @return
	 * @author ZhangJun
	 * @创建时间 2017/12/27
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
	 * @创建时间 2017/12/27
	 */
	@RequestMapping(method = RequestMethod.POST, value = "delete")
	public String delete(@RequestParam(required = false) String params) {
		return super.delete(params);
	}
}
