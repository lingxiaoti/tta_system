package com.sie.saaf.common.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.yhg.hibernate.core.paging.Pagination;

import java.io.Serializable;
import java.util.List;

/**
 * 通用的BaseCommon接口，提供泛型通用的CRUD操作，Server接口可以继承此接口
 * @param <T> 泛型
 * @author ZhangJun 
 * @creteTime 2017-12-11
 */
public interface IBaseCommon<T> {
	/**
	 * 根据Id查询对象
	 * @author ZhangJun
	 * @creteTime 2017-12-11
	 * @param Id 主键Id
	 * @return 泛型对象
	 */
	T getById(Serializable Id);
	
	/**
	 * 根据Id删除
	 * @author ZhangJun
	 * @creteTime 2017-12-11
	 * @param Id 主键Id
	 */
	void deleteById(Serializable Id);
	
	/**
	 * 保存或更新一个对象
	 * @author ZhangJun
	 * @creteTime 2017-12-11
	 * @param entity 泛型对象
	 */
	void saveOrUpdate(T entity);

	/**
	 * 更新一个对象
	 * @author ZhangJun
	 * @createTime 2018/4/27
	 * @description 更新一个对象
	 */
	void update(T entity);

	/**
	 * 保存多个对象
	 * @author ZhangJun
	 * @createTime 2017/12/23
	 * @description 保存多个对象
	 */
	void save(List<T> entitys);
	
	/**
	 * 保存一个对象
	 * @author ZhangJun
	 * 创建时间 2017-12-11
	 * @param entity 保存一个对象
	 * @return Id
	 */
	Object save(T entity);
	
	/**
	 * 更新一个属性
	 * @author ZhangJun
	 * @creteTime 2017-12-11
	 * @param entity 泛型对象
	 * @param field 更新字段
	 * @param value 更新值
	 */
	void updateProperty(T entity, String field, Object value);

	/**
	 * 更新一条记录
	 * @param queryParamJSON 对象属性的JSON格式
	 * @return 泛型对象
	 * @author ZhangJun
	 * @creteTime 2017/12/15
	 */
	T saveOrUpdate(JSONObject queryParamJSON);

	/**
	 * 删除多条记录
	 * @param ids Id集合
	 * @author ZhangJun
	 * @creteTime 2017/12/15
	 */
	void deleteAll(List<Serializable> ids);

	/**
	 * 分页查询数据
	 * @param queryParamJSON 对象属性的JSON格式
	 * @param pageIndex 页码
	 * @param pageRows 每页查询记录数
	 * @return Pagination 分页对象
	 * { <br>
	 *         count: 总记录数,<br>
	 *         curIndex: 当前页索引,<br>
	 *         data: [{<br>
	 *             数据对象JSON数组
	 *         }],<br>
	 *         firstIndex: 首页索引,<br>
	 *         lastIndex: 尾页索引,<br>
	 *         nextIndex: 下一页索引,<br>
	 *         pageSize: 每页记录数,<br>
	 *         pagesCount: 总页数,<br>
	 *         preIndex: 上一页索引<br>
	 * }
	 * @author ZhangJun
	 * @creteTime 2017/12/15
	 */
	Pagination<T> findPagination(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

	/**
	 * 查询匹配据的数
	 * @param queryParamJSON 对象属性的JSON格式
	 * @return List
	 * @author ZhangJun
	 * @createTime 2017/12/25 09:06
	 * @description 查询匹配的数据
	 */
	List<T> findList(JSONObject queryParamJSON);
}
