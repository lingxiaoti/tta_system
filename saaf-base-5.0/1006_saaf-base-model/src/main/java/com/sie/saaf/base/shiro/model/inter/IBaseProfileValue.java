package com.sie.saaf.base.shiro.model.inter;

import com.sie.saaf.base.shiro.model.entities.BaseProfileValueEntity_HI;
import com.sie.saaf.base.shiro.model.entities.readonly.BaseProfileValue_HI_RO;
import com.sie.saaf.common.model.inter.IBaseCommon;

import java.util.List;

/**
 * 接口：对表base_profile_value进行CRUD操作
 *
 * @author ZhangJun
 * @creteTime 2017/12/14
 */
public interface IBaseProfileValue extends IBaseCommon<BaseProfileValueEntity_HI> {

	/**
	 * 查询列表，根据tableName,业务主键
	 * @param tableName 表名
	 * @param businessKey 业务主键
	 * @return {@link BaseProfileValueEntity_HI}
	 * @author ZhangJun
	 * @createTime 2018/1/12 15:55
	 * @description 查询列表，根据tableName,业务主键
	 */
	List<BaseProfileValueEntity_HI> findList(String tableName, Integer businessKey);

	/**
	 * 查询列表
	 * @author ZhangJun
	 * @createTime 2018/4/16
	 * @description 查询列表
	 */
	List<BaseProfileValue_HI_RO> findList(String tableName, String profileCode, List<Integer> businessKey);

	/**
	 * 查询列表
	 * @author ZhangJun
	 * @createTime 2018/4/16
	 * @description 查询列表
	 */
	String getUserIds(String profileCodes, String tableName);
}
