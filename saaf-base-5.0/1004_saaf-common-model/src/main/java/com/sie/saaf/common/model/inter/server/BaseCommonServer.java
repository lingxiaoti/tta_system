package com.sie.saaf.common.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.util.SaafToolUtils;
import com.yhg.hibernate.core.paging.Pagination;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class BaseCommonServer<T> implements IBaseCommon<T> {
	private static final Logger logger = LoggerFactory.getLogger(BaseCommonServer.class);

	@Autowired
	private BaseCommonDAO_HI<T> baseCommonDAO_HI;

	@Override
	public T getById(Serializable Id) {
		Method[] methods = baseCommonDAO_HI.getEntityClass().getMethods();
		for (Method method : methods) {
			if (method.getAnnotation(Id.class) != null) {
				String returnTypeName = method.getReturnType().getSimpleName();
				if ("Integer".equals(returnTypeName)) {
					Integer intId = Integer.parseInt(String.valueOf(Id));
					return baseCommonDAO_HI.getById(intId);
				}else if("String".equals(returnTypeName)) {
					String strId = String.valueOf(Id);
					return baseCommonDAO_HI.getById(strId);
				}else if("Long".equals(returnTypeName)){
					Long longId=Long.valueOf(Id.toString());
					return baseCommonDAO_HI.getById(longId);
				}
			}
		}
		return baseCommonDAO_HI.getById(Id);
	}

	@Override
	public void deleteById(Serializable Id) {
		T obj = getById(Id);
		baseCommonDAO_HI.delete(obj);
	}

	@Override
	public void saveOrUpdate(T entity) {
		this.setEntityDefaultValue(entity);
		baseCommonDAO_HI.saveOrUpdate(entity);
	}

	@Override
	public void update(T entity) {
		this.setEntityDefaultValue(entity);
		baseCommonDAO_HI.update(entity);
	}

	@Override
	public void save(List<T> entitys) {
		Iterator<T> it = entitys.iterator();
		while(it.hasNext()){
			save(it.next());
		}
	}

	@Override
	public Object save(T entity) {
		this.setEntityDefaultValue(entity);
		return baseCommonDAO_HI.save(entity);
	}

	@Override
	public void updateProperty(T entity,String field, Object value) {
		baseCommonDAO_HI.updateProperty(entity,field,value);
	}

	@Override
	public T saveOrUpdate(JSONObject queryParamJSON){
		T entity = (T)JSON.parseObject(queryParamJSON.toString(),baseCommonDAO_HI.getEntityClass());

		Object idValue = null;
		try {
			Method[] methods = baseCommonDAO_HI.getEntityClass().getMethods();
			for (Method method : methods) {
				Id idfield = method.getAnnotation(Id.class);
				GeneratedValue generatedValue = method.getAnnotation(GeneratedValue.class);
				if (idfield != null && generatedValue != null) {
					idValue = method.invoke(entity);
					break;
				}
			}
		}catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException e){
			logger.error(e.getMessage(),e);
		}

		if (idValue != null) {
			//更新数据
			if(StringUtils.isEmpty(queryParamJSON.getString("versionNum"))){
			    throw new IllegalArgumentException("缺少参数 versionNum");
			}
		}
		setEntityDefaultValue(entity);
		baseCommonDAO_HI.saveOrUpdate(entity);
		return entity;
	}

	/**
	 * 保存或更新时设置默认值，由各继承类自由实现
	 * @param entity 泛型对象
	 * @author ZhangJun
	 * @creteTime 2017/12/15
	 */
	protected void setEntityDefaultValue(T entity){

	}

	@Override
	public void deleteAll(List<Serializable> ids){
		Iterator<Serializable> it = ids.iterator();
		while(it.hasNext()){
			this.deleteById(it.next());
		}
	}

	@Override
	public Pagination<T> findPagination(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows){

		String className = baseCommonDAO_HI.getEntityClass().getSimpleName();
		String alias = className.replace("Entity_HI","");
		alias = String.valueOf(alias.charAt(0)).toLowerCase()+alias.substring(1);
		StringBuffer sb = new StringBuffer(" from "+className+" AS " + alias + " where 1=1 ");
		StringBuffer countSQL = new StringBuffer("select count(*) ").append(sb);

		Map<String,Object> queryParamMap = new HashMap<String,Object>();
		changeQuerySql(queryParamJSON,queryParamMap,sb,true);
		changeQuerySort(queryParamJSON,sb,"",true);

		changeQuerySql(queryParamJSON,queryParamMap,countSQL,true);

		Pagination<T> findList = baseCommonDAO_HI.findPagination(sb,countSQL,queryParamMap,pageIndex,pageRows);
		return findList;
	}

	@Override
	public List<T> findList(JSONObject queryParamJSON) {
		String className = baseCommonDAO_HI.getEntityClass().getSimpleName();
		String alias = className.replace("Entity_HI","");
		alias = String.valueOf(alias.charAt(0)).toLowerCase()+alias.substring(1);
		StringBuffer sb = new StringBuffer(" from "+className+" AS " + alias + " where 1=1 ");

		Map<String,Object> queryParamMap = new HashMap<String,Object>();
		changeQuerySql(queryParamJSON,queryParamMap,sb,true);
		changeQuerySort(queryParamJSON,sb,"",true);
		List<T> findList = baseCommonDAO_HI.findList(sb,queryParamMap);
		return findList;
	}

	/**
	 * 设置查询条件
	 *
	 * @param queryParamJSON
	 *            入参
	 * @param paramsMap
	 *            sql或hql参数
	 * @param sql
	 *            sql或hql
	 * @param isHql
	 *            是否HQL查询，如果是HQL查询，自动将查询字段转换为对象属性
	 */
	protected void changeQuerySql(JSONObject queryParamJSON, Map<String, Object> paramsMap, StringBuffer sql,boolean isHql) {
		SaafToolUtils.parperHbmParam(baseCommonDAO_HI.getEntityClass(), queryParamJSON, sql, paramsMap);
//		Field[] fields = baseCommonDAO_HI.getEntityClass().getDeclaredFields();
//		if (fields != null) {
//			for(Field field : fields){
//				if(!field.getName().equalsIgnoreCase("operatorUserId")) {
//					Set<String> keys = queryParamJSON.keySet();
//					for(String key : keys){
//						if(StringUtils.startsWith(key,field.getName())){
//							SaafToolUtils.parperHbmParam(baseCommonDAO_HI.getEntityClass(), queryParamJSON, sql, paramsMap);
//							break;
//						}
//					}
					//if(queryParamJSON.containsKey(field.getName().split("_")[0])) {
					//	SaafToolUtils.parperHbmParam(baseCommonDAO_HI.getEntityClass(), queryParamJSON, sql, paramsMap);
						//paramsMap.put(field.getName(), queryParamJSON.get(field.getName()));
					//}
//				}
//			}
//		}
	}

	/**
	 * 设置排序字段
	 * @author ZhangJun
	 * @creteTime 2017-12-11
	 * @param queryParamJSON 参数
	 * @param sql SQL或HQL语句
	 * @param defaultOrderBy 设置默认的排序，如：order_no asc,user_name desc
	 * @param isHql 是否HQL语句
	 */
	protected void changeQuerySort(JSONObject queryParamJSON,StringBuffer sql,String defaultOrderBy,boolean isHql){
		SaafToolUtils.changeQuerySort(queryParamJSON,sql,defaultOrderBy,isHql);
	}
}
