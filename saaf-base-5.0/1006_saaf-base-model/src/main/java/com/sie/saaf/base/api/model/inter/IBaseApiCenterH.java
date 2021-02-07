package com.sie.saaf.base.api.model.inter;

import com.sie.saaf.base.api.model.entities.BaseApiCenterHEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

import java.util.List;

/**
 * 接口：API 项目/中心管理
 *
 * @author ZhangJun
 * @creteTime 2017-12-12
 */
public interface IBaseApiCenterH extends IBaseCommon<BaseApiCenterHEntity_HI> {

	/**
	 * 根据项目中心编码获取一条数据
	 * @param centerCode 项目中心编码
	 * @return BaseApiCenterHEntity_HI对象集合
	 * @author ZhangJun
	 * @creteTime 2017-12-118
	 */
	List<BaseApiCenterHEntity_HI> findByCenterCode(String centerCode);

    void delete(Integer id);
}
