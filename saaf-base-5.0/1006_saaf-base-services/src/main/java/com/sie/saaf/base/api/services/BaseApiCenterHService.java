package com.sie.saaf.base.api.services;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.api.model.entities.BaseApiCenterHEntity_HI;
import com.sie.saaf.base.api.model.inter.IBaseApiCenterH;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.yhg.base.utils.SToolUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 对外提供的API 项目/中心管理
 * @author ZhangJun
 * @creteTime 2017-12-14
 */
@RestController
@RequestMapping("/baseApiCenterHService")
public class BaseApiCenterHService extends CommonAbstractService {
	private static final Logger logger = LoggerFactory.getLogger(BaseApiCenterHService.class);

	@Autowired
	private IBaseApiCenterH baseApiCenterHServer;

	public IBaseCommon getBaseCommonServer(){
		return this.baseApiCenterHServer;
	}

	/**
	 * 保存或更新数据
	 * @param params JSON参数 <br>
	 *     {<br>
	 *			apihId:主键，（更新时必填）<br>
	 *			centerName:项目/中心名称<br>
	 *			centerCode:项目/中心编码<br>
	 *			versionNum:版本号（更新时必填）<br>
	 *     }
	 * @return
	 * @author ZhangJun
	 * @creteTime 2017/12/15
	 */
	@RequestMapping(method = RequestMethod.POST, value = "save")
	public String saveOrUpdate(@RequestParam(required = true) String params){
		JSONObject queryParamJSON = this.parseObject(params);
		String centerCode = queryParamJSON.getString("centerCode");
		if(!queryParamJSON.containsKey("versionNum")) {
			List<BaseApiCenterHEntity_HI> entitys = this.baseApiCenterHServer.findByCenterCode(centerCode);
			if (entitys != null && !entitys.isEmpty()) {
				return SToolUtils.convertResultJSONObj(ERROR_STATUS, "已存在编码为" + centerCode + "", 0, null).toString();
			}
		}
		return super.saveOrUpdate(params);
	}

	/**
	 * 查找数据
	 * @param params JSON参数<br>
	 *     {<br>
	 *			centerName:项目/中心名称<br>
	 *			centerCode:项目/中心编码<br>
	 *     }
	 * @param pageIndex
	 * @param pageRows
	 * @return
	 * @author ZhangJun
	 * @creteTime 2017/12/15
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findPagination")
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
	 * @return
	 * @author ZhangJun
	 * @creteTime 2017/12/15
	 *
	 */
	@RequestMapping(method = RequestMethod.POST, value = "delete")
	public String delete(@RequestParam(required = false) String params) {
		JSONObject jsonObject=parseObject(params);
		if (StringUtils.isNotBlank("id"))
			baseApiCenterHServer.delete(jsonObject.getInteger("id"));
		return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", 0, null).toString();
	}
}
