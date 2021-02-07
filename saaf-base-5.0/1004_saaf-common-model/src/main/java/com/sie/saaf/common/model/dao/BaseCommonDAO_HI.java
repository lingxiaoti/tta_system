package com.sie.saaf.common.model.dao;

import com.alibaba.fastjson.JSON;
import com.sie.saaf.common.bean.FieldDesc;
import com.sie.saaf.common.bean.UserSessionBean;
import com.sie.saaf.common.util.EscColumnToBean;
import com.sie.saaf.common.util.SaafToolUtils;
import com.yhg.hibernate.core.dao.ViewObjectImpl;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate3.HibernateTemplate;
import redis.clients.jedis.JedisCluster;

import javax.annotation.Resource;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.lang.reflect.*;
import java.sql.*;
import java.util.*;

public class BaseCommonDAO_HI<T> extends ViewObjectImpl<T> {

	private Class entityClass;
	public BaseCommonDAO_HI() {
		entityClass = SaafToolUtils.getSuperClassGenricType(this.getClass(),0);
	}

	private static final Logger LOGGER = LoggerFactory.getLogger(BaseCommonDAO_HI.class);
	@Resource(name="hibernateTemplete")
	@Override
	public void setMyHibernateTemplace(HibernateTemplate myHibernateTemplace) {
		super.setMyHibernateTemplace(myHibernateTemplace);
	}

	@Override
	public Object save(T entity) {
		return super.save(entity);
	}

	/**
	 * 更新一个属性值 
	 * @author ZhangJun 
	 * @creteTime 2017-12-12
	 * @param entity 泛型对象
	 * @param field 更新字段
	 * @param value 更新值
	 */
	public void updateProperty(T entity, String field, Object value) {
		
		try {
			String primaryKey = "";
			Object idValue = null;
			Method[] methods = entity.getClass().getMethods();
	        for (Method method : methods) {
	            Id idfield = method.getAnnotation(Id.class);
	            GeneratedValue generatedValue = method.getAnnotation(GeneratedValue.class);
	            if (idfield != null && generatedValue != null) {
	                primaryKey= method.getAnnotation(Column.class).name();
	                primaryKey= primaryKey.substring(0,1).toLowerCase()+primaryKey.substring(1);
	                idValue = method.invoke(entity);
	                break;
	            }
	        }
	        
	        StringBuffer sb = new StringBuffer();
	        sb.append("update ").append(entity.getClass().getName()).append(" set ");
	        sb.append(field).append("=:fieldValue where ").append(primaryKey).append("=:idValue");
	        Query query = getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(sb.toString());
	        query.setParameter("fieldValue", value);
	        query.setParameter("idValue", idValue);
	        query.executeUpdate();
	        
		} catch (SecurityException  | IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
			LOGGER.error(e.getMessage(),e);
		}

	}

	/**
	 * 获取表中某个字段下一个值，字段类型必须是Integr或Long类型
	 * @param clazz 实体对象Class
	 * @param fieldName 属性名
	 * @return 表中字段下一个数值
	 * @author ZhangJun
	 * @createTime 2018/3/29
	 * @description 获取表中某个字段下一个值
	 */
	public Integer findNextValue(Class<T> clazz,String fieldName) {
		try {
			Field field = clazz.getDeclaredField(fieldName);
			String fieldType = field.getType().getSimpleName();
			if(!StringUtils.equals(fieldType,"Integer") && !StringUtils.equals(fieldType,"Long")){
				throw new ClassCastException("查询字段类型不是整型");
			}
			StringBuffer sb = new StringBuffer();
			sb.append("select max("+fieldName+")+1 from "+clazz.getName());
			Query query = getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(sb.toString());
			return (int)query.uniqueResult();
		} catch (NoSuchFieldException e) {
			LOGGER.error(e.getMessage(),e);
		}
		return 0;
	}

	
	//通过名称调用存储过程
	@SuppressWarnings("all")
	public void callProName(String proName) {
		try {
			Session session = this.getSessionFactory().getCurrentSession();
			session.doWork(new Work() {
				public void execute(Connection conn) throws SQLException {
					String sql = "{call "+ proName + "()}";
					//	conn.setAutoCommit(false);
					CallableStatement call = conn.prepareCall(sql);
					call.execute();
					close(call, proName);
				}
			});
		} catch (Exception e) {
			LOGGER.error(".callProName 调用存储过程名称:{},异常信息:{}", proName, e);
		}
	}

	private void close(CallableStatement cs, String proName) {
		try {
			if (cs != null) {
				cs.close();
			}

		} catch (Exception e) {
			LOGGER.error(".callProName 关闭调用存储过程名称:{},异常信息:{}", proName, e);
		}
	}

	//###########################################新增jdbc操作数据库start################################################
	@Autowired
	@Qualifier("jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	/**
	 * 原生sql语句的批量保存
	 * @param sql			sql语句
	 * @param paramsList
	 * @param paramsCount
	 */
	private void batchSave(final String sql, final List<Map<Integer,Object>> paramsList){
		//logger.info("[begin 批量保存:"+paramsList.size()+"条数据, ==>  Preparing：{}]", sql);
		long t1 = System.currentTimeMillis();
		final StringBuilder loginfo = new StringBuilder();
		jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement pst, int i) throws SQLException {
				Map<Integer,Object> map = paramsList.get(i);
				for(int k = 1; k <= map.size(); k++){
					Object value = map.get(k);
					pst.setObject(k, value);
					String className = null == value ? "NULL" : value.getClass().getName();
					loginfo.append(value).append("(").append(className).append(")").append(",");
				}
				loginfo.deleteCharAt(loginfo.length()-1).append("|");
			}
			@Override
			public int getBatchSize() {
				return paramsList.size();
			}
		});
		if(logger.isDebugEnabled()){
			logger.debug("==> Parameters："+loginfo.deleteCharAt(loginfo.length()-1));
		}
		long t2 = System.currentTimeMillis();
		logger.info("[end 批量保存:"+paramsList.size()+"条数据,times:"+(t2-t1)+"ms.]");
	}


	private String createSQLParamsForMap(final String table, List<Map<String, Object>> fieldParamsList,
										 List<Map<Integer, Object>> paramsList) {
		StringBuilder sql = new StringBuilder("insert into ").append(table).append(" (");
		StringBuilder prefix = new StringBuilder();
		StringBuilder suffix = new StringBuilder();

		// 对list的每项数据map遍历，保存每个值对应占位符的位置，sql语句的占位符位置是从1开始的
		for(Map<String,Object> map : fieldParamsList){
			Map<Integer, Object> valueMap = new HashMap<>();
			int k = 1;
			for(Map.Entry<String, Object> entry : map.entrySet()){
				// 防止字段重复添加到insert里，所以这里做判断，对于不存在的字段才添加进去
				List<String> listFiled = Arrays.asList(prefix.toString().split(","));
				if(!listFiled.contains(entry.getKey())){
					prefix.append(entry.getKey()).append(",");
					suffix.append("?,");
				}
				// 保存占位符位置与值的对应关系
				valueMap.put(k, entry.getValue());
				//loginfo.append(entry.getKey()).append("=").append(entry.getValue()).append(",");
				k++;
			}
			paramsList.add(valueMap);
		}
		sql.append(prefix.deleteCharAt(prefix.length()-1).append(") values ("));
		String strsql = sql.append(suffix.deleteCharAt(suffix.length()-1).append(")")).toString();
		return strsql;
	}



	@SuppressWarnings("unchecked")
	public List<Integer> saveBatchJDBC(final String table, List<Map<String, Object>> fieldParamsList) {
		List<Map<Integer, Object>> paramsList = new ArrayList<>();
		try {
			if (fieldParamsList == null || fieldParamsList.isEmpty()) {
				return Collections.EMPTY_LIST;
			}
			String strsql = this.createSQLParamsForMap(table, fieldParamsList, paramsList);
			//logger.info("[begin 批量保存:"+paramsList.size()+"条数据, ==>  Preparing：{}]", sql);
			List<Integer> list = new ArrayList<>(paramsList.size());
			long t1 = System.currentTimeMillis();
			final StringBuilder loginfo = new StringBuilder();
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try {
				conn = jdbcTemplate.getDataSource().getConnection();
				conn.setAutoCommit(false);
				pstmt = conn.prepareStatement(strsql, PreparedStatement.RETURN_GENERATED_KEYS);
				for (Map<Integer, Object> map : paramsList) {
					int key = 1;
					for (Map.Entry<Integer, Object> entry : map.entrySet()) {
						Object value = entry.getValue();
						if (value instanceof java.util.Date) {
							pstmt.setDate(key++, new java.sql.Date(((java.util.Date) value).getTime()));
						} else if (value instanceof java.sql.Date) {
							pstmt.setDate(key++, (java.sql.Date)value);
						} else {
							pstmt.setObject(key++, value);
						}
						String className = null == value ? "NULL" : value.getClass().getName();
						loginfo.append(value).append("(").append(className).append(")").append(",");
					}
					pstmt.addBatch();
				}
				if(logger.isDebugEnabled()){
					logger.debug("==> Parameters："+loginfo.deleteCharAt(loginfo.length()-1));
				}
				pstmt.executeBatch();
				conn.commit();
				/*rs = pstmt.getGeneratedKeys();// 获取主键ID
				while(rs.next()){
					list.add(rs.getInt(1));
				}*/
			} finally{
				if(null != conn){conn.close();}
				if(null != pstmt){pstmt.close();}
				if(null != rs){rs.close();}
			}
			long t2 = System.currentTimeMillis();
			logger.info("[end batchJDBCSave批量保存:"+paramsList.size()+"条数据,times:"+(t2-t1)+"ms.]");
			return list;
		} catch (Exception e) {
			// LOGGER.error("fieldParamsList 参数是:{},paramsList 参数是:{}" + fieldParamsList, paramsList);
			logger.error("batchSave-exception", e);
		}
		return Collections.EMPTY_LIST;
	}

	@SuppressWarnings("all")
	public List<Integer> saveSeqBatchJDBC(final String table, List<Map<String, Object>> fieldParamsList, String majorKey, String seq, JedisCluster je, UserSessionBean us) throws Exception {
		List<Map<Integer, Object>> paramsList = new ArrayList<>();
		try {
			if (fieldParamsList == null || fieldParamsList.isEmpty()) {
				return Collections.EMPTY_LIST;
			}
			String strsql = this.createSQLSeqParamsForMap(table, fieldParamsList, paramsList,majorKey,seq,je,us);
			//logger.info("[begin 批量保存:"+paramsList.size()+"条数据, ==>  Preparing：{}]", sql);
			List<Integer> list = new ArrayList<>(paramsList.size());
			long t1 = System.currentTimeMillis();
			final StringBuilder loginfo = new StringBuilder();
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try {
				conn = jdbcTemplate.getDataSource().getConnection();
				conn.setAutoCommit(false);
				logger.info(strsql);
				pstmt = conn.prepareStatement(strsql, PreparedStatement.RETURN_GENERATED_KEYS);
				for (Map<Integer, Object> map : paramsList) {
					int key = 1;
					for (Map.Entry<Integer, Object> entry : map.entrySet()) {
						Object value = entry.getValue();
						if (value instanceof java.util.Date) {
							pstmt.setDate(key++, new java.sql.Date(((java.util.Date) value).getTime()));
						} else if (value instanceof java.sql.Date) {
							pstmt.setDate(key++, (java.sql.Date)value);
						} else {
							pstmt.setObject(key++, value);
						}
						String className = null == value ? "NULL" : value.getClass().getName();
						loginfo.append(value).append("(").append(className).append(")").append(",");
					}
					pstmt.addBatch();
					if(!SaafToolUtils.isNullOrEmpty(je)){
						je.setex(us.getCertificate(),3600,"{status:'U',currentStage:'拼装阶段二',orderNum:"+"'" + key+"'}");
					}
				}
				if(logger.isDebugEnabled()){
					logger.debug("==> Parameters："+loginfo.deleteCharAt(loginfo.length()-1));
				}
				pstmt.executeBatch();
				conn.commit();
				/*rs = pstmt.getGeneratedKeys();// 获取主键ID
				while(rs.next()){
					list.add(rs.getInt(1));
				}*/
			} finally{
				if(null != conn){conn.close();}
				if(null != pstmt){pstmt.close();}
				if(null != rs){rs.close();}
			}
			long t2 = System.currentTimeMillis();
			logger.info("[end batchJDBCSave批量保存:"+paramsList.size()+"条数据,times:"+(t2-t1)+"ms.]");
			return list;
		} catch (Exception e) {
			// LOGGER.error("fieldParamsList 参数是:{},paramsList 参数是:{}" + fieldParamsList, paramsList);
			logger.error("batchSave-exception", e);
			throw new Exception("batchSave-exception:" + e);
		}
	}

	public List<Integer> saveSeqBatchJDBC(final String table, List<Map<String, Object>> fieldParamsList,String majorKey,String seq) throws Exception {
		List<Map<Integer, Object>> paramsList = new ArrayList<>();
		try {
			if (fieldParamsList == null || fieldParamsList.isEmpty()) {
				return Collections.EMPTY_LIST;
			}
			String strsql = this.createSQLSeqParamsForMap(table, fieldParamsList, paramsList,majorKey,seq);
			//logger.info("[begin 批量保存:"+paramsList.size()+"条数据, ==>  Preparing：{}]", sql);
			List<Integer> list = new ArrayList<>(paramsList.size());
			long t1 = System.currentTimeMillis();
			final StringBuilder loginfo = new StringBuilder();
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try {
				conn = jdbcTemplate.getDataSource().getConnection();
				conn.setAutoCommit(false);
				logger.info(strsql);
				pstmt = conn.prepareStatement(strsql, PreparedStatement.RETURN_GENERATED_KEYS);
				for (Map<Integer, Object> map : paramsList) {
					int key = 1;
					for (Map.Entry<Integer, Object> entry : map.entrySet()) {
						Object value = entry.getValue();
						if (value instanceof java.util.Date) {
							pstmt.setDate(key++, new java.sql.Date(((java.util.Date) value).getTime()));
						} else if (value instanceof java.sql.Date) {
							pstmt.setDate(key++, (java.sql.Date)value);
						} else {
							pstmt.setObject(key++, value);
						}
						String className = null == value ? "NULL" : value.getClass().getName();
						loginfo.append(value).append("(").append(className).append(")").append(",");
					}
					pstmt.addBatch();
				}
				if(logger.isDebugEnabled()){
					logger.debug("==> Parameters："+loginfo.deleteCharAt(loginfo.length()-1));
				}
				pstmt.executeBatch();
				conn.commit();
				/*rs = pstmt.getGeneratedKeys();// 获取主键ID
				while(rs.next()){
					list.add(rs.getInt(1));
				}*/
			} catch (Exception e){
				logger.error("start saveSeqBatchJDBC-exception:" + e, e);
				throw new IllegalArgumentException("start saveSeqBatchJDBC-exception:" + e, e);
			} finally{
				if(null != conn){conn.close();}
				if(null != pstmt){pstmt.close();}
				if(null != rs){rs.close();}
			}
			long t2 = System.currentTimeMillis();
			logger.info("[end batchJDBCSave批量保存:"+paramsList.size()+"条数据,times:"+(t2-t1)+"ms.]");
			return list;
		} catch (Exception e) {
			// LOGGER.error("fieldParamsList 参数是:{},paramsList 参数是:{}" + fieldParamsList, paramsList);
			logger.error("batchSave-exception", e);
			throw new Exception("batchSave-exception:" + e);
		}
	}

	protected String createSQLSeqParamsForMap(final String table, List<Map<String, Object>> fieldParamsList,
										 List<Map<Integer, Object>> paramsList,String majorKey,String seq,JedisCluster je, UserSessionBean us) {
		StringBuilder sql = new StringBuilder("insert into ").append(table).append(" (");
		StringBuilder prefix = new StringBuilder();
		StringBuilder suffix = new StringBuilder();

		// 对list的每项数据map遍历，保存每个值对应占位符的位置，sql语句的占位符位置是从1开始的
		for(Map<String,Object> map : fieldParamsList){
			Map<Integer, Object> valueMap = new HashMap<>();
			int k = 1;
			for(Map.Entry<String, Object> entry : map.entrySet()){
				// 防止字段重复添加到insert里，所以这里做判断，对于不存在的字段才添加进去
				List<String> listFiled = Arrays.asList(prefix.toString().split(","));
				if(!listFiled.contains(entry.getKey())){
					prefix.append(entry.getKey()).append(",");
					if(entry.getKey().equals(majorKey)){
						suffix.append(seq).append(",");
					}else{
						suffix.append("?,");

					}
				}
				if(!entry.getKey().equals(majorKey)){
					valueMap.put(k, entry.getValue());
				}

				// 保存占位符位置与值的对应关系

				//loginfo.append(entry.getKey()).append("=").append(entry.getValue()).append(",");
				k++;
			}
			paramsList.add(valueMap);
			if(!SaafToolUtils.isNullOrEmpty(je)){
				je.setex(us.getCertificate(),3600,"{status:'U',currentStage:'拼装阶段一',orderNum:"+"'" + k+"'}");
			}
		}
		sql.append(prefix.deleteCharAt(prefix.length()-1).append(") values ("));
		String strsql = sql.append(suffix.deleteCharAt(suffix.length()-1).append(")")).toString();
		return strsql;
	}

	protected String createSQLSeqParamsForMap(final String table, List<Map<String, Object>> fieldParamsList,
											List<Map<Integer, Object>> paramsList,String majorKey,String seq) {
		StringBuilder sql = new StringBuilder("insert into ").append(table).append(" (");
		StringBuilder prefix = new StringBuilder();
		StringBuilder suffix = new StringBuilder();

		// 对list的每项数据map遍历，保存每个值对应占位符的位置，sql语句的占位符位置是从1开始的
		for(Map<String,Object> map : fieldParamsList){
			Map<Integer, Object> valueMap = new HashMap<>();
			int k = 1;
			for(Map.Entry<String, Object> entry : map.entrySet()){
				// 防止字段重复添加到insert里，所以这里做判断，对于不存在的字段才添加进去
				List<String> listFiled = Arrays.asList(prefix.toString().split(","));
				if(!listFiled.contains(entry.getKey())){
					prefix.append(entry.getKey()).append(",");
					if(entry.getKey().equals(majorKey)){
						suffix.append(seq).append(",");
					}else{
						suffix.append("?,");

					}
				}
				if(!entry.getKey().equals(majorKey)){
					valueMap.put(k, entry.getValue());
				}

				// 保存占位符位置与值的对应关系

				//loginfo.append(entry.getKey()).append("=").append(entry.getValue()).append(",");
				k++;
			}
			paramsList.add(valueMap);
		}
		sql.append(prefix.deleteCharAt(prefix.length()-1).append(") values ("));
		String strsql = sql.append(suffix.deleteCharAt(suffix.length()-1).append(")")).toString();
		return strsql;
	}
	/**
	 * 批量保存
	 * @param
	 * @param
	 * @param
	 */
	public void saveBatch(final String table, List<Map<String, Object>> fieldParamsList) {
		try {
			List<Map<Integer, Object>> paramsList = new ArrayList<>();
			String strsql = createSQLParamsForMap(table, fieldParamsList, paramsList);
			batchOranginSave(strsql, paramsList);
		} catch (Exception e) {
			logger.error("batchSave-exception", e);
		}
	}


	/**
	 * 原生sql语句的批量保存
	 * @param sql			sql语句
	 * @param paramsList
	 * @param paramsCount
	 */
	private void batchOranginSave(final String sql, final List<Map<Integer,Object>> paramsList){
		//logger.info("[begin 批量保存:"+paramsList.size()+"条数据, ==>  Preparing：{}]", sql);
		long t1 = System.currentTimeMillis();
		final StringBuilder loginfo = new StringBuilder();
		jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement pst, int i) throws SQLException {
				Map<Integer,Object> map = paramsList.get(i);
				for(int k = 1; k <= map.size(); k++){
					Object value = map.get(k);
					pst.setObject(k, value);
					String className = null == value ? "NULL" : value.getClass().getName();
					loginfo.append(value).append("(").append(className).append(")").append(",");
				}
				loginfo.deleteCharAt(loginfo.length()-1).append("|");
			}
			@Override
			public int getBatchSize() {
				return paramsList.size();
			}
		});
		if(logger.isDebugEnabled()){
			logger.debug("==> Parameters："+loginfo.deleteCharAt(loginfo.length()-1));
		}
		long t2 = System.currentTimeMillis();
		logger.info("[end 批量保存:"+paramsList.size()+"条数据,times:"+(t2-t1)+"ms.]");
	}

	private SQLQuery getSQLQuery(final String sql) {
		SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		return query;
	}

	/**
	 * 原生sql语句带命名参数的查询，返回一个List,其中每条记录是一个Map,key为字段名，value为数据值
	 * @param sql			要执行sql语句
	 * @param params		占位符对应的参数
	 * @return
	 */
	public List<Map<String,Object>> queryNamedSQLForList(final String sql, final Map<String, Object> params){
		logger.info("==>  Preparing:"+sql);
		return queryBySQL(sql, params, null);
	}

	/**
	 * 设置用命名参数查询的参数值
	 * @param query
	 * @param params
	 * @return
	 */
	private <T> List<T> queryBySQL(String sql, Map<String,Object> params, Class<T> clazz){
		SQLQuery query = getSQLQuery(sql);
		if (params != null && !params.isEmpty()) {
			setParameter(query, params);
		}
		List<T> list = new ArrayList<>();
		if(null != clazz){
			list = query.setResultTransformer(new EscColumnToBean(clazz)).list();
		} else {
			list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		}
		return list;
	}

	/**
	 * 设置查询参数值，这里为了方便记录日志，输出字段的具体参数值，故返回一个字符串
	 * @param query		一个查询sql的SQLQuery 对象
	 * @param params	命名参数的参数/参数值集合
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private void setParameter(SQLQuery query, Map<String, Object> params) {
		if(params == null || params.isEmpty()) {
			return ;
		}
		StringBuilder logmsg = new StringBuilder();
		for (Map.Entry<String, Object> entry : params.entrySet()) {
			Class<?> clazz = entry.getValue().getClass();
			if(clazz.getSimpleName().equals("ArrayList")){
				List list = (List) entry.getValue();
				query.setParameterList(entry.getKey(), list);
				logmsg.append(list);
			} else if(clazz.getSimpleName().equals("HashSet")){
				Set set = (Set) entry.getValue();
				query.setParameterList(entry.getKey(), set);
				logmsg.append(set);
			} else if(clazz.isArray()){
				Object[] o = (Object[]) entry.getValue();
				query.setParameterList(entry.getKey(), o);
				logmsg.append(Arrays.asList(o));
			} else {
				query.setParameter(entry.getKey(), entry.getValue());
				logmsg.append(entry.getValue());
			}
			logmsg.append("("+clazz.getName()+")").append(",");
		}
		if(logger.isDebugEnabled()){
			String parameterlog = logmsg.substring(0, logmsg.length()-1);
			logger.debug("==> Parameters："+ parameterlog);
		}
		return ;
	}


	@SuppressWarnings("all")
	public void updateBatchJDBC(final String table, Class cls, List<Map<String, Object>> fieldParamsList)  {
		LOGGER.info("当前执行批量更新方法,需要更新的表名:{}, 操作的类名:{}", table, cls.getClass());
		long t1 = System.currentTimeMillis();
		Connection conn = null;
		try {
			if (fieldParamsList == null || fieldParamsList.isEmpty()) {
				LOGGER.info("updateBatchJDBC :需要更新的集合为空!");
				return;
			}
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			Map<String, Object> resultMap = this.getUpdateSqlAndFieldList(table, cls);
			String updateSql = (String)resultMap.get("sql");
			List<String> fieldList = (List<String>) resultMap.get("fieldList");
			try {
				conn = jdbcTemplate.getDataSource().getConnection();
				conn.setAutoCommit(false);
				pstmt = conn.prepareStatement(updateSql, PreparedStatement.RETURN_GENERATED_KEYS);
				for (Map<String, Object> map : fieldParamsList) {
                    Map<Integer, Object> paramsInfo = new LinkedHashMap<>();
                    for (int key = 0; key < fieldList.size(); key ++) {
						int paramIdx = key + 1;
						String fieldName = fieldList.get(key) + "";
						Object value = map.get(fieldName);
						if (value == null && !fieldName.contains("_")) {//兼容驼峰和下划线
							fieldName = SaafToolUtils.humpToUnderline(fieldName).toLowerCase();
							value =  map.get(fieldName);//转下划线小写
							if (value == null) {
								value =  map.get(fieldName.toUpperCase());//转下划线大写
							}
						}
						if (value instanceof java.util.Date) {
							pstmt.setDate(paramIdx, new java.sql.Date(((java.util.Date) value).getTime()));
						} else if (value instanceof java.sql.Date) {
							pstmt.setDate(paramIdx, (java.sql.Date)value);
						} else {
							pstmt.setObject(paramIdx, value);
						}
                        paramsInfo.put(paramIdx, value);
					}
                    LOGGER.info("批量添加的入参信息:{}", paramsInfo);
					pstmt.addBatch();
				}
				pstmt.executeBatch();
				conn.commit();
				logger.info("[end updateBatchJDBC批量更新Sql语句:" + updateSql + "; 操作的字段信息：" + fieldList);
			} catch (Exception e) {
				if (conn != null) {
					conn.rollback();
				}
				logger.error("start batchUpdate-exception:" + e, e);
				throw new IllegalArgumentException("start batchUpdate-exception:" + e, e);
			} finally{
				if(null != conn){conn.close();}
				if(null != pstmt){pstmt.close();}
				if(null != rs){rs.close();}
			}
			long t2 = System.currentTimeMillis();
			logger.info("[end updateBatchJDBC批量更新:" + fieldParamsList.size()+"条数据,times:"+(t2-t1)+"ms.]");
		} catch (Exception e) {
			logger.error("end batchUpdate-exception:" + e, e);
			throw new IllegalArgumentException("end batchUpdate-exception:" + e, e);
        }
	}

		public void updateBatchListMap(List<Map<String, Object>> fieldParamsList) {
			String tableName = entityClass.getSimpleName().replace("Entity_HI", "");
			tableName = tableName.substring(0,1).toLowerCase().concat(tableName.substring(1));//第个字母转小写
			tableName = SaafToolUtils.humpToUnderline(tableName); //转下划线
			this.updateBatchJDBC(tableName, entityClass, fieldParamsList);
		}

		public void updateBatchJDBC(List<T> entityList) {
		List<Map<String, Object>> fieldParamsList = new ArrayList<>();
		for (T entity : entityList) {
			HashMap<String, Object> hashMap = JSON.parseObject(SaafToolUtils.toJson(entity), HashMap.class);
			fieldParamsList.add(hashMap);
		}
		String tableName = entityClass.getSimpleName().replace("Entity_HI", "");
		tableName = tableName.substring(0,1).toLowerCase().concat(tableName.substring(1));//第个字母转小写
		tableName = SaafToolUtils.humpToUnderline(tableName); //转下划线
		this.updateBatchJDBC(tableName, entityClass, fieldParamsList);
	}
		/**
         * 功能描述： 获取更新语句
         */
	protected Map<String, Object> getUpdateSqlAndFieldList(String tableName, Class cls) {
		Map<String, Object> resultMap = new HashMap<>();
		List<String> updateFieldList = new ArrayList<>();
		List<String> whereFieldList = new ArrayList<>();
		StringBuffer buffer = new StringBuffer();
		buffer.append("update ").append(tableName).append(" ");
		StringBuffer whereBuffer = new StringBuffer();
		StringBuffer updateBuffer = new StringBuffer();
		Field[] fieldArray = cls.getDeclaredFields();
		for(int i = 0; i < fieldArray.length; i++){
			Field field = fieldArray[i];
			field.setAccessible(true);
			FieldDesc fieldDesc = field.getAnnotation(FieldDesc.class);
			if (fieldDesc != null) {
				String fieldName = field.getName();
				String fieldNameUnderLine = SaafToolUtils.humpToUnderline(fieldName);
				if (fieldDesc.isUpdateWhereKey()){
					whereFieldList.add(fieldName);
					whereBuffer.append(" and ").append(fieldNameUnderLine).append("=?");
				} else {
					updateBuffer.append(fieldNameUnderLine).append("=?,");
					updateFieldList.add(fieldName);
				}
			}
		}
		if (whereBuffer.length() == 0) {
			new IllegalArgumentException("使用该批量更新必须要带有条件!");
		}
		updateBuffer.insert(0, " set ");
		buffer.append(updateBuffer.deleteCharAt(updateBuffer.length() - 1)).append(" where 1 = 1 ").append(whereBuffer);
		updateFieldList.addAll(whereFieldList);
		resultMap.put("sql", buffer.toString());
		resultMap.put("fieldList", updateFieldList);
		return resultMap;
	}

	//###########################################新增jdbc操作数据库end################################################
}
