package com.sie.saaf.base.user.model.inter.server;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.user.model.entities.BaseWechatMngEntity_HI;
import com.sie.saaf.base.user.model.inter.IBaseWechatMng;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("baseWechatMngServer")
public class BaseWechatMngServer extends BaseCommonServer<BaseWechatMngEntity_HI> implements IBaseWechatMng {
//	private static final Logger LOGGER = LoggerFactory.getLogger(BaseWechatMngServer.class);
//	@Autowired
//	private ViewObject<BaseWechatMngEntity_HI> baseWechatMngDAO_HI;

//	public BaseWechatMngServer() {
//		super();
//	}

	/**
	 * 保存一条数据
	 * @param queryParamJSON 参数列表<br>
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
	 *		    operatorUserId:操作者,<br>
	 *     }
	 * @return BaseWechatMngEntity_HI
	 * @author ZhangJun
	 * @createTime 2017/12/27 14:25
	 * @description 保存一条数据
	 */
	@Override
	public BaseWechatMngEntity_HI saveOrUpdate(JSONObject queryParamJSON) {
		return super.saveOrUpdate(queryParamJSON);
	}

	/**
	 * 分页查询列表
	 * @param queryParamJSON 查询参数<br>
	 *     {<br>
	 *         userId:用户Id<br>
	 *		   wxOpenId:公众号Id<br>
	 *     }
	 * @param pageIndex 页码
	 * @param pageRows 每页查询记录数
	 * @return 分页结构
	 * { <br>
	 *         count: 总记录数,<br>
	 *         curIndex: 当前页索引,<br>
	 *         data: [{<br>
	 *				wxId:主键Id（更新时必填）,<br>
	 *				userId:用户Id（必填）,<br>
	 *				wxOpenId:公众号Id（必填）,<br>
	 *	    		unionId:unionId,<br>
	 *				creationDate:创建时间,<br>
	 *				createdBy:创建人,<br>
	 *				lastUpdatedBy:更新人,<br>
	 *				lastUpdateDate:更新时间,<br>
	 *				versionNum:版本号（更新时必填）,<br>
	 *				lastUpdateLogin:lastUpdateLogin
	 * 			}],<br>
	 *         firstIndex: 首页索引,<br>
	 *         lastIndex: 尾页索引,<br>
	 *         nextIndex: 下一页索引,<br>
	 *         pageSize: 每页记录数,<br>
	 *         pagesCount: 总页数,<br>
	 *         preIndex: 上一页索引<br>
	 *         }
	 * @author ZhangJun
	 * @createTime 2017/12/28 08:41
	 * @description 分页查询列表
	 */
	@Override
	public Pagination<BaseWechatMngEntity_HI> findPagination(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
		return super.findPagination(queryParamJSON, pageIndex, pageRows);
	}

	/**
	 * 查询列表
	 * @param queryParamJSON 查询参数<br>
	 *     {<br>
	 *         userId:用户Id（必填）,<br>
	 *		   wxOpenId:公众号Id（必填）,<br>
	 *     }
	 * @return 集合列表<br>
	 * [{<br>
	 *		wxId:主键Id（更新时必填）,<br>
	 *		userId:用户Id（必填）,<br>
	 *		wxOpenId:公众号Id（必填）,<br>
	 *	    unionId:unionId,<br>
	 *		creationDate:创建时间,<br>
	 *		createdBy:创建人,<br>
	 *		lastUpdatedBy:更新人,<br>
	 *		lastUpdateDate:更新时间,<br>
	 *		versionNum:版本号（更新时必填）,<br>
	 *		lastUpdateLogin:lastUpdateLogin
	 * }]
	 * @author ZhangJun
	 * @createTime 2017/12/27 14:25
	 * @description 查询列表
	 */
	@Override
	public List<BaseWechatMngEntity_HI> findList(JSONObject queryParamJSON) {
		return super.findList(queryParamJSON);
	}

	/**
	 * 根据用户Id查询公众号列表
	 * @param userId
	 * @return 公众号列表,br>
	 * [{
	 *      wxId:主键Id（更新时必填）,<br>
	 *	    userId:用户Id（必填）,<br>
	 *      wxOpenId:公众号Id（必填）,<br>
	 *      unionId:unionId,<br>
	 *      creationDate:创建时间,<br>
	 *      createdBy:创建人,<br>
	 *		lastUpdatedBy:更新人,<br>
	 *		lastUpdateDate:更新时间,<br>
	 *		versionNum:版本号（更新时必填）,<br>
	 *		operatorUserId:操作者,<br>
	 * }]
	 * @author ZhangJun
	 * @createTime 2017/12/27 14:59
	 * @description 根据用户Id查询公众号列表
	 */
	@Override
	public List<BaseWechatMngEntity_HI> findListByUserId(Integer userId){
		JSONObject queryParamJSON = new JSONObject();
		queryParamJSON.put("userId",userId);
		return this.findList(queryParamJSON);
	}

	/**
	 * 设置默认值，或判断必填字段
	 * @author ZhangJun
	 * @createTime 2017/12/27 14:46
	 * @description 设置默认值，或判断必填字段
	 */
	@Override
	protected void setEntityDefaultValue(BaseWechatMngEntity_HI entity) {
		if(entity.getUserId()==null){
			throw new IllegalArgumentException("缺少参数 userId");
		}
		if(StringUtils.isEmpty(entity.getWxOpenId())){
			throw new IllegalArgumentException("缺少参数 wxOpenId");
		}
	}
}
