package com.sie.saaf.base.api.model.inter;

import com.sie.saaf.base.api.model.entities.BaseApiCenterLEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.base.api.model.entities.BaseApiCenterLEntity_HI;

import java.util.List;

/**
 * 接口：API 项目模块/中心模块管理
 * 
 * @author ZhangJun
 * @creteTime 2017-12-12
 */
public interface IBaseApiCenterL extends IBaseCommon<BaseApiCenterLEntity_HI> {
	/**
	 * 根据模块编码查询记录
	 * @param modelCode 模块编码
	 * @return BaseApiCenterLEntity_HI集合
	 * @author ZhangJun
	 * @creteTime 2017/12/18
	 */
	List<BaseApiCenterLEntity_HI> findByModelCode(String modelCode);
}
