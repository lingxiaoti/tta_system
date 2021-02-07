package com.sie.saaf.deploy.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.deploy.model.entities.BaseAppAuthExcludeEntity_HI;

import java.util.List;

public interface IBaseAppAuthExclude {

	/**
	 * 根据应用ID查询应用权限列表
	 * @author laoqunzhao 2018-08-22
	 * @param appWapId 应用ID
	 * @return List<BaseAppAuthExcludeEntity_HI>
	 */
	List<BaseAppAuthExcludeEntity_HI> findByAppWapId(int appWapId);
	
	/**
	 * 根据应用ID查询应用权限返回JSON格式数据
	 * @author laoqunzhao 2018-08-22
	 * @param appWapId 应用ID
	 * @return JSONObject{depCodes:[],empIds:[],storeIds:[],dealerIds:[]}
	 * }
	 */
	JSONObject findJSONByAppWapId(int appWapId);
	
	/**
	 * 根据应用ID、权限对象类型查询应用权限列表
	 * @author laoqunzhao 2018-08-22
	 * @param appWapId 应用ID
	 * @param objectType 对象类型   员工/ 经销商/ 门店
	 * @param ouId ouId
	 * @return List<BaseAppAuthExcludeEntity_HI>
	 */
	List<BaseAppAuthExcludeEntity_HI> findByAppWapIdAndType(int appWapId, int ouId, String objectType);

	/**
	 * 保存权限信息
	 * @author laoqunzhao 2018-08-22
	 * @param paramsJSON
	 * {
	 * appWapId 应用ID
	 * objectType 对象类型  员工/ 经销商/门店
	 * ouId ouId 
	 * depCodes 部门编码[]
	 * dealerIds 经销商ID[](门店)/[{id:,deptCode:}}](经销商)
	 * empIds 员工ID[{id:,deptCode:}]
	 * storeIds 门店ID[{id:,deptCode:}]
	 * }
	 */
	void save(JSONObject paramsJSON, int userId);	
	
	/**
	 * 根据应用ID、权限对象类型删除权限设置对象
	 * @author laoqunzhao 2018-08-27
	 * @param appWapId 应用ID
	 * @param objectType 对象类型   员工 / 经销商 / 门店
	 * @param ouId ouId
	 */
	void delete(int appWapId, int ouId, String objectType);

}
