package com.sie.saaf.base.shiro.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.shiro.model.entities.BaseButtonDataEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

/**
 * 接口：对功能基础表base_button_data提供CRUD操作
 *
 * @author ZhangJun
 * @creteTime 2017/12/14
 */
public interface IBaseButtonData extends IBaseCommon<BaseButtonDataEntity_HI> {
	/**
	 * 校验按钮名称或编码
	 * @author ZhangJun
	 * @createTime 2018/3/16
	 * @description 校验按钮名称或编码
	 */
	JSONObject validNameOrCode(JSONObject jsonParam);


	/**
	 * 分页查询数据
	 *
	 * @param queryParamJSON JSON参数<br>
	 * {<br>
	 * bbdCode:功能编码<br>
	 * bbdName:功能名称<br>
	 * }
	 * @param pageIndex 页码
	 * @param pageRows 每页查询记录数
	 *
	 * @return 分页数据列表<br>
	 * { <br>
	 * count: 总记录数,<br>
	 * curIndex: 当前页索引,<br>
	 * data: [{<br>
	 * bbdId:主键ID,<br>
	 * bbdName:功能按钮名称,<br>
	 * bbdCode:功能按钮编码,<br>
	 * creationDate:创建日期,<br>
	 * createdBy:创建人,<br>
	 * lastUpdatedBy:更新人,<br>
	 * lastUpdateDate:更新日期,<br>
	 * versionNum:版本号,<br>
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
	//Pagination<BaseButtonDataEntity_HI> findBaseButtonDataPagination(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

	/**
	 * 保存一条数据
	 *
	 * @param queryParamJSON JSON参数 <br>
	 * {<br>
	 * bbdId:主键ID,（更新数据时必填）<br>
	 * bbdName:功能按钮名称,<br>
	 * bbdCode:功能按钮编码,<br>
	 * versionNum:版本号,（更新数据时必填）<br>
	 * }
	 *
	 * @author ZhangJun
	 * @creteTime 2017/12/14
	 */
	//BaseButtonDataEntity_HI saveBaseButtonDataInfo(JSONObject queryParamJSON);

}
