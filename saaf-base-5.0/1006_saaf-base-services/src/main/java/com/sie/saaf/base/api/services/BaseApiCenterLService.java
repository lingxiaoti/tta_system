package com.sie.saaf.base.api.services;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.api.model.entities.BaseApiCenterLEntity_HI;
import com.sie.saaf.base.api.model.inter.IBaseApiCenterL;
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
 * 对外提供API 项目模块/中心模块管理服务
 * @author ZhangJun
 * @creteTime 2017-12-15
 */
@RestController
@RequestMapping("/baseApiCenterLService")
public class BaseApiCenterLService extends CommonAbstractService {
	private static final Logger logger = LoggerFactory.getLogger(BaseApiCenterLService.class);

	@Autowired
	private IBaseApiCenterL baseApiCenterLServer;
	public IBaseCommon getBaseCommonServer(){
		return this.baseApiCenterLServer;
	}

	/**
	 * 保存或更新数据
	 * @param params JSON参数
	 * {<br>
	 *     apilId:主键（更新时必填）<br>
	 *     centerCode:项目中心编码<br>
	 *     modelName:模块名称<br>
	 *     modelCode：模块编码<br>
	 *     versionNum:版本号<br>
	 * }
	 * @return
	 * @author ZhangJun
	 * @creteTime 2017/12/15
	 */
	@RequestMapping(method = RequestMethod.POST, value = "save")
	public String saveOrUpdate(@RequestParam(required = true) String params){
		JSONObject queryParamJSON = parseObject(params);
		String modelCode = queryParamJSON.getString("modelCode");
		if(!queryParamJSON.containsKey("versionNum")) {
			List<BaseApiCenterLEntity_HI> entitys = this.baseApiCenterLServer.findByModelCode(modelCode);
			if (entitys != null && !entitys.isEmpty()) {
				return SToolUtils.convertResultJSONObj(ERROR_STATUS, "已存在编码为" + modelCode + "的记录", 0, null).toString();
			}
		}
		return super.saveOrUpdate(params);
	}

	/**
	 * 查找数据
	 * @param params JSON参数 <br>
	 *     {<br>
	 *         	centerCode:项目中心编码<br>
	 *         	modelName:模块名称<br>
	 *         	modelCode：模块编码<br>
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
	 */
	@RequestMapping(method = RequestMethod.POST, value = "delete")
	public String delete(@RequestParam(required = false) String params) {
		return super.delete(params);
	}
}
