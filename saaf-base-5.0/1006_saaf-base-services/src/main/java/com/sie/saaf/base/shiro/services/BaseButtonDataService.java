package com.sie.saaf.base.shiro.services;


import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.shiro.model.inter.IBaseButtonData;
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

/**
 * @author ZhangJun
 * @creteTime 2017-12-18
 */
@RestController
@RequestMapping("/baseButtonDataService")
public class BaseButtonDataService extends CommonAbstractService {
	private static final Logger logger = LoggerFactory.getLogger(BaseButtonDataService.class);
	@Autowired
	private IBaseButtonData baseButtonDataServer;

	@Override
	public IBaseCommon getBaseCommonServer() {
		return baseButtonDataServer;
	}

	/**
	 * 查找数据
	 *
	 * @param params JSON参数<br>
	 * {<br>
	 * menuId:菜单Id<br>
	 * resourceCode:资源编码<br>
	 * resourceType:资源类型<br>
	 * resourceName:资源名称<br>
	 * }
	 * @param pageIndex 页码
	 * @param pageRows 每页查询记录数
	 *
	 * @return 分页数据列表<br>
	 * { <br>
	 * count: 总记录数,<br>
	 * curIndex: 当前页索引,<br>
	 * data: [{<br>
	 * bbdId:主键ID,（更新数据时必填）<br>
	 * bbdName:功能按钮名称,<br>
	 * bbdCode:功能按钮编码,<br>
	 * creationDate:创建日期<br>
	 * createdBy:创建人<br>
	 * lastUpdatedBy:更新人<br>
	 * lastUpdateDate:更新日期<br>
	 * versionNum:版本号,（更新数据时必填）<br>
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
	 * @creteTime 2017/12/18
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
	 * @param params JSON参数 <br>
	 * {<br>
	 * bbdId:主键ID,（更新数据时必填）<br>
	 * bbdName:功能按钮名称,<br>
	 * bbdCode:功能按钮编码,<br>
	 * resIcon:图标,<br>
	 * versionNum:版本号,（更新数据时必填）<br>
	 * }
	 *
	 * @return BaseButtonDataEntity_HI对象
	 *
	 * @author ZhangJun
	 * @creteTime 2017/12/18
	 */
	@RequestMapping(method = RequestMethod.POST, value = "save")
	public String saveOrUpdate(@RequestParam(required = true) String params) {
		JSONObject queryParamJSON = parseObject(params);
		JSONObject result = this.baseButtonDataServer.validNameOrCode(queryParamJSON);
		if(!result.getBooleanValue("validFlag")){
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, result.getString("msg"), 0, null).toString();
		}else {
			return super.saveOrUpdate(params);
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
	 * @creteTime 2017/12/18
	 */
	@RequestMapping(method = RequestMethod.POST, value = "delete")
	public String delete(@RequestParam(required = false) String params) {
		return super.delete(params);
	}

}
