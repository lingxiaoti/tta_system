package com.sie.saaf.deploy.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.deploy.model.entities.BaseDeployeeAppInfoEntity_HI;
import com.sie.saaf.deploy.model.entities.readonly.BaseDeployeeAppAuthEntity_HI_RO;
import com.sie.saaf.deploy.model.entities.readonly.BaseDeployeeAppInfoEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;

import java.util.List;

public interface IBaseDeployeeAppInfo extends IBaseCommon<BaseDeployeeAppInfoEntity_HI> {
	
	/**
	 * 根据ID查询应用信息
	 * @author laoqunzhao 2018-08-15
	 * @param id 主键
	 * @return BaseDeployeeAppInfoEntity_HI
	 */
	BaseDeployeeAppInfoEntity_HI getById(Integer id);
	
	/**
	 * 通过属性查询应用信息
	 * @author huqitao
	 * @param queryParamJSON 对象属性的JSON格式
	 * @return 应用列表
	 */
	List<BaseDeployeeAppInfoEntity_HI> findByProperty(JSONObject queryParamJSON);

	/**
	 * 查询应用列表（分页）
	 * @author laoqunzhao 2018-08-15
	 * @param queryParamJSON 对象属性的JSON格式
	 * {
	 * appCode APP编码
	 * appName APP名称
	 * appWapCode 应用编码
	 * appWapName 应用名称
	 * appWapDesc 应用描述
	 * appWapVersion 应用版本
	 * appWapStatus 1.上架  0.下架
	 * appWapDeployeeTimeStart 发布起始时间 yyyy-MM-dd
	 * appWapDeployeeTimeEnd 发布截止时间 yyyy-MM-dd
	 * }
	 * @param pageIndex      页码
	 * @param pageRows       每页查询记录数
	 * @return Pagination<BaseDeployeeAppInfoEntity_HI>
	 * @throws Exception 
	 */
	Pagination<BaseDeployeeAppInfoEntity_HI> findDeployApps(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) throws Exception;

	/**
	 * 查询用户应用列表，同时查询应用的菜单列表
	 * @author laoqunzhao 2018-08-23
	 * @param queryParamJSON
	 * {
	 * ouId  OUID
	 * userId  用户ID
	 * appCode APP编码
	 * }
	 * @return List<BaseDeployeeAppInfoEntity_HI_RO>
	 */
	List<BaseDeployeeAppInfoEntity_HI_RO> findDeployAppsByUserId(JSONObject queryParamJSON);
	
	/**
	 * 查询用户应用列表，同时查询应用的菜单列表,优先从Redis中查询
	 * @author laoqunzhao 2018-08-22
	 * @param ouId  OUID
	 * @param userId  用户ID
	 * @param appCode APP编码
	 * @return Pagination<BaseDeployeeAppInfoEntity_HI>
	 */
	List<BaseDeployeeAppInfoEntity_HI_RO> findDeployAppsByUserIdInRedis(int ouId, int userId, String appCode);
	
	/**
	 * 新增&修改应用信息
	 * @author laoqunzhao 2018-08-15
	 * @param paramsJSON
	 * {
	 * appWapId 主键Id
     * appName app底座名称
     * appWapCode 应用编码
     * appWapName 应用名称
     * appWapDesc 描述
     * appWapSortId 应用排序
     * appWapVersion 应用版本
     * appPackingVersion 打包版本
     * appWapAccessHomePath 应用首页访问地址
     * appWapFullScreen 是否全屏 Y.是  N.否
     * appWapBackupPath 应用备份地址
     * appWapImagePath 应用图标地址
     * appWapUploadPath 应用发布地址
     * appWapStatus 1.上架  0.下架
     * appWapDeployeeTime 发布时间
	 * }
	 * @param userId     当前用户ID
	 * @return 返回实体行
	 * @throws Exception 抛出异常
	 */
	BaseDeployeeAppInfoEntity_HI saveOrUpdate(JSONObject paramsJSON, Integer userId) throws Exception;


	/**
	 * 更新应用状态：上架/下架
	 * @author laoqunzhao 2018-08-16
	 * @param paramsJSON 对象属性的JSON格式
	 * {
	 * appWapIds:[] 主键数组
	 * appWapStatus   1.上架  0.下架
	 * }
	 * @param userId     当前用户ID
	 * @return 返回实体行
	 */
	void updateStatus(JSONObject paramsJSON, Integer userId);

	/**
	 * 删除应用信息
	 * @author laoqunzhao 2018-08-16
	 * @param paramsJSON 对象属性的JSON格式
	 * {
	 * appWapIds:[] 主键数组
	 * appWapStatus   1.上架  0.下架
	 * }
	 * @param userId     当前用户ID
	 * @return 返回实体行
	 */
	void delete(JSONObject paramsJSON, Integer userId);

	/**
	 * 查询应用权限对象列表（分页）
	 * @author laoqunzhao 2018-09-18
	 * @param queryParamJSON 对象属性的JSON格式
	 * {
	 * appWapId 应用ID
	 * ouId OU ID
	 * objectType 对象类型
	 * }
	 * @param pageIndex      页码
	 * @param pageRows       每页查询记录数
	 * @return Pagination<BaseDeployeeAppAuthEntity_HI_RO>
	 */
	public Pagination<BaseDeployeeAppAuthEntity_HI_RO> findAuthEntities(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

	/**
	 * 保存应用权限对象列表
	 * @author laoqunzhao 2018-09-13
	 * @param paramsJSON
	 * {
	 * appWapId  应用ID
	 * dataTable
	 * [{
	 * ouId ouID
	 * objectType 对象类型
	 * }]
	 * }
	 */
	void saveAuths(JSONObject paramsJSON, int userId);
	
	
	/**
	 * 根据应用ID、权限对象类型删除权限设置对象
	 * @author laoqunzhao 2018-08-27
	 * @param appWapId 应用ID
	 * @param objectType 对象类型   员工 / 经销商 / 门店
	 * @param ouId ouId
	 */
	void deleteAuths(int appWapId, int ouId, String objectType);

	/**
	 * 校验当前用户是否重新登录，刷新Redis缓存
	 * @param ouId
	 * @param userId
	 * @param appCode
	 * @param certificate
	 */
	void checkUserCertificate(Integer ouId, Integer userId, String appCode, String certificate);
	

}
