package com.sie.saaf.deploy.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.deploy.model.entities.BaseDeployeeAppMenuEntity_HI;
import com.yhg.hibernate.core.paging.Pagination;

import java.util.List;

public interface IBaseDeployeeAppMenu extends IBaseCommon<BaseDeployeeAppMenuEntity_HI> {
	
	/**
	 * 根据ID查询应用菜单信息
	 * @author laoqunzhao 2018-08-16
	 * @param id 主键
	 * @return BaseDeployeeAppInfoEntity_HI
	 */
	BaseDeployeeAppMenuEntity_HI getById(Integer id);
	
	/**
	 * 根据应用ID查询应用菜单信息
	 * @author laoqunzhao 2018-08-16
	 * @param appWapId 应用主键
	 * @return List<BaseDeployeeAppMenuEntity_HI>
	 */
	List<BaseDeployeeAppMenuEntity_HI> findByAppWapId(Integer appWapId);

	
	/**
	 * 通过属性查询应用菜单
	 * @author huqitao
	 * @param queryParamJSON 对象属性的JSON格式
	 * @return List<BaseDeployeeAppMenuEntity_HI>
	 */
	List<BaseDeployeeAppMenuEntity_HI> findByProperty(JSONObject queryParamJSON);

	/**
	 * 查询应用菜单（分页）
	 *
	 * @param queryParamJSON 对象属性的JSON格式
	 * @param pageIndex      页码
	 * @param pageRows       每页查询记录数
	 * @return 应用菜单（分页）
	 */
	Pagination<BaseDeployeeAppMenuEntity_HI> findAppMenus(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

	/**
	 * 新增&修改应用菜单
	 * @author laoqunzhao 2018-08-16
	 * @param paramsJSON
	 * {
	 * appWapId 应用ID
	 * dataTable
	 * [{
	 * appMenuId 菜单ID
	 * appMenuName app菜单名称
	 * appMenuCode app菜单编码
	 * appMenuImagePath app菜单图标
	 * appMenuUrl app菜单访问地址
	 * appMenuSort app菜单排序
	 * appDefaultDisplay 默认显示在用户常用收藏栏,1表示显示 0表示不显示 默认值为0
	 * }]
	 * }
	 * @param userId     当前用户ID
	 * @return 返回实体行
	 * @throws Exception 抛出异常
	 */
	void saveOrUpdate(JSONObject paramsJSON, Integer userId) throws Exception;

	/**
	 * 删除应用菜单信息
	 * @author laoqunzhao 2018-08-16
	 * @param paramsJSON 
	 * {
	 * appMenuIds:[] 主键数组
	 * }
	 * @param userId     当前用户ID
	 * @return 返回实体行
	 */
	void delete(JSONObject paramsJSON, Integer userId);

	/**
	 * 查询用户最近使用的菜单
	 * @author laoqunzhao 2018-08-18
	 * @param ouId  OUID
	 * @param userId  用户ID
	 * @param appCode APP编码
	 * @return List<BaseDeployeeAppMenuEntity_HI>
	 */
	List<BaseDeployeeAppMenuEntity_HI> getMyLatestMenus(int ouId, int userId, String appCode);

	
	/**
	 * 添加最近使用的菜单Code,以JSONArray的形式存储到Redis中
	 * @author laoqunzhao 2018-08-18
	 * @param ouId ouID
	 * @param userId 用户ID
	 * @param appCode APP编码
	 * @param appMenuCode 菜单Code
	 */
	void addMyLatestMenu(int ouId, int userId, String appCode, String appMenuCode);
	
	/**
	 * 将我的收藏菜单ID存在到redis中
	 * @author laoqunzhao 2018-08-17
	 * @param paramsJSON
	 * {
	 * ouId  OUID
	 * userId 用户ID
	 * appCode APP编码
	 * appMenuCodes 收藏的菜单CODE数组[]
	 * }
	 */
	void resetMyFavoriteMenus(JSONObject paramsJSON);

	/**
	 * 查询我的收藏菜单
	 * @author laoqunzhao 2018-08-18
	 * @param ouId  OUID
	 * @param userId  用户ID
	 * @param appCode APP编码
	 * @return List<BaseDeployeeAppMenuEntity_HI>
	 */
	List<BaseDeployeeAppMenuEntity_HI> getMyFavoriteMenus(int ouId, int userId, String appCode);



}
