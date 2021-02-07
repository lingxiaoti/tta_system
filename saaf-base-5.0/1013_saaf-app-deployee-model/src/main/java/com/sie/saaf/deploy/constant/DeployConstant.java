package com.sie.saaf.deploy.constant;

/**
 * 定义APP发布平台常量
 * @author laoqunzhao
 * @createTime 2018-08-18
 */
public interface DeployConstant {
	
	/**
	 * 存放用户应用信息的hashkey
	 */
	String REDIS_KEY_USR_APP_KEY = "DEP_USR_APP";
	
	/**
	 * 存放用户应用信息前缀   
	 * key:  USR_ + ouId + :: + userId + :: + appCode
	 * value: List<BaseDeployeeAppInfoEntity_HI>的JSON格式
	 */
	String REDIS_KEY_USR_APP = "USR_";
	
	/**
	 * 存放所有用户菜单收藏的hashkey
	 */
	String REDIS_KEY_APP_MENU_MYF_KEY = "DEP_MENU_MYF";
	
	/**
	 * 存放用户个人收藏菜单
	 * key: MYF_ + ouId + :: + userId + :: + appCode
	 * value:[menuCode1, menuCode2]
	 */
	String REDIS_KEY_APP_MENU_MYF = "MYF_";
	
	/**
	 * 存放所有用户最近使用的菜单的hashkey
	 */
	String REDIS_KEY_APP_MENU_MYL_KEY = "DEP_MENU_MYL";
	
	/**
	 * 存放所有用户最近使用的菜单   
	 * key: MYL_ + ouId + :: + userId + :: + appCode
	 * value:[menuCode1, menuCode2]
	 */
	String REDIS_KEY_APP_MENU_MYL = "MYL_";

	/**
	 * 存放所有用户最后登录的Certificate
	 */
	String REDIS_KEY_APP_USR_CTF_KEY = "DEP_USR_CTF";

	/**
	 * 存放所有用户最后登录的Certificate
	 * key: CTF_ + ouId + :: + userId + :: + appCode
	 * value:[certificate]
	 */
	String REDIS_KEY_APP_USR_CTF = "CTF_";

	/**
	 * 权限对象类型：员工
	 */
	String AUTH_TYPE_DEPT = "DEPT";

	/**
	 * 权限对象类型：员工
	 */
	String AUTH_TYPE_EMP = "20";
	/**
	 * 权限对象类型：经销商
	 */
	String AUTH_TYPE_DEALER = "30";
	/**
	 * 权限对象类型：门店
	 */
	String AUTH_TYPE_STORE = "40";
	
}
