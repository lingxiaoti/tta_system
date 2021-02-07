package com.sie.saaf.common.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yhg.base.utils.DigestUtils;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.dao.ViewObject;

public final class SaafToolUtils {

	public static Class getSuperClassGenricType(Class clazz, int index) {
		try {
			Type genType = clazz.getGenericSuperclass();
			if (!(genType instanceof ParameterizedType)) {
				return Object.class;
			}
			Type[] params = ((ParameterizedType) genType)
					.getActualTypeArguments();
			if (index >= params.length || index < 0) {
				return Object.class;
			}
			if (!(params[index] instanceof Class)) {
				return Object.class;
			}
			return (Class) params[index];
		} catch (Exception e) {
			log.error("getSuperClassGenricType:" + e, e);
		}
		return Object.class;
	}

	private static final Logger log = LoggerFactory
			.getLogger(SaafToolUtils.class);

	/**
	 * 字符串判断
	 * 
	 * @param obj
	 * @return
	 */
	public static boolean isNullOrEmpty(Object obj) {
		return null == obj || "".equals(obj.toString().trim());
	}

	/**
	 * @param sql
	 * @return Map<String,String> key:别名，val:sql字段
	 * @description 查找原生sql中查询字段的别名
	 */
	private static Map<String, String> fromatSqlFild(String sql) {
		Map<String, String> map = new HashMap<>();
		if (StringUtils.isBlank(sql))
			return map;
		String reg = "(?<=select[\\s\\n\\r]).+(?=[\\r\\n\\s]from)";
		Pattern p = Pattern.compile(reg, Pattern.CASE_INSENSITIVE
				| Pattern.DOTALL);
		Matcher m = p.matcher(sql);
		if (m.find() == false)
			return map;
		String selects = m.group().trim();
		String[] selectArray = selects.split(",");
		if (selectArray.length == 0)
			return map;
		reg = "[\\s\\n\\r]+as[\\s\\n\\r]+";
		p = Pattern.compile(reg, Pattern.CASE_INSENSITIVE);
		Pattern emptyPattern = Pattern.compile("[\\s\\n\\r]+");
		for (String str : selectArray) {
			if (StringUtils.isBlank(str))
				continue;
			m = p.matcher(str.trim());
			// 包含as
			if (m.find()) {
				String[] arr = str.trim().split(m.group());
				if (arr.length != 2)
					continue;
				map.put(arr[1].trim(), arr[0].trim());
				continue;
			}
			m = emptyPattern.matcher(str.trim());
			// 不包含as
			if (m.find()) {
				String[] arr = str.split(m.group());
				if (arr.length != 2)
					continue;
				map.put(arr[1].trim(), arr[0].trim());
				continue;
			}
			// 无别名
			map.put(str.trim(), str.trim());
		}
		return map;
	}

	/**
	 * @param clazz
	 *            查询的Hbm对象
	 * @param jsonParam
	 *            查询条件 { xxx: 等于 xxx_like: 包含 xxx_lt: 小于 xxx_lte: 小于等于 xxx_gt:
	 *            大于 xxx_gte: 大于等于 xxx_in: in }
	 * @param sql
	 *            sql
	 * @param queryParamMap
	 *            Hbm参数Map
	 */
	public static void parperHbmParam(Class<?> clazz, JSONObject jsonParam,
			StringBuffer sql, Map<String, Object> queryParamMap) {
		if (jsonParam == null)
			return;
		if (queryParamMap == null)
			queryParamMap = new HashMap<>();
		Set<String> keys = jsonParam.keySet();
		Map<String, String> selectFields = fromatSqlFild(sql.toString());
		for (String key : keys) {
			if (StringUtils.isBlank(jsonParam.getString(key))
					|| "operatorUserId".equals(key))
				continue;
			String queryAttr = key;
			String operator = "=";
			if (key.endsWith("_like")) {
				// 包含
				operator = "like";
				key = key.substring(0, key.lastIndexOf("_like"));
			} else if (key.endsWith("_iclike")) {
				// 包含
				operator = "iclike";
				key = key.substring(0, key.lastIndexOf("_iclike"));
			} else if (key.endsWith("_fulllike")) {
				// 包含
				operator = "fulllike";
				key = key.substring(0, key.lastIndexOf("_fulllike"));
			} else if (key.endsWith("_lt")) {
				// 小于
				operator = "<";
				key = key.substring(0, key.lastIndexOf("_lt"));
			} else if (key.endsWith("_lte")) {
				// 小于等于
				operator = "<=";
				key = key.substring(0, key.lastIndexOf("_lte"));
			} else if (key.endsWith("_gt")) {
				// 大于
				operator = ">";
				key = key.substring(0, key.lastIndexOf("_gt"));
			} else if (key.endsWith("_gte")) {
				// 大于等于
				operator = ">=";
				key = key.substring(0, key.lastIndexOf("_gte"));
			} else if (key.endsWith("_in")) {
				// in
				operator = "in";
				key = key.substring(0, key.lastIndexOf("_in"));
			} else if (key.endsWith("_neq")) {
				// 不等于
				operator = "!=";
				key = key.substring(0, key.lastIndexOf("_neq"));
			} else if (key.endsWith("_notin")) {
				// not in
				operator = "not in";
				key = key.substring(0, key.lastIndexOf("_notin"));
			}

			String sqlField = selectFields.containsKey(key) ? selectFields
					.get(key) : key;
			try {
				Object attributeValue = jsonParam.get(queryAttr);
				if (clazz == null || "iclike".equals(operator)
						|| "fulllike".equals(operator)
						|| "like".equals(operator) || "in".equals(operator)
						|| "not in".equals(operator)
						|| attributeValue instanceof Date) {
					parperParam(sql, sqlField, queryAttr, attributeValue,
							queryParamMap, operator);
					continue;
				}

				Field field = clazz.getDeclaredField(key);
				String fieldType = field.getType().getSimpleName();
				if ("Integer".equals(fieldType)) {
					Integer attributeValueInteger = SToolUtils
							.object2Int(attributeValue);
					parperParam(sql, sqlField, queryAttr,
							attributeValueInteger, queryParamMap, operator);
				} else if ("Long".equals(fieldType)) {
					Long attributeValueInteger = Long.parseLong(SToolUtils
							.object2String(attributeValue));
					parperParam(sql, sqlField, queryAttr,
							attributeValueInteger, queryParamMap, operator);
				} else if ("Date".equals(fieldType)) {
					Date date = SaafDateUtils.string2UtilDate(attributeValue
							.toString());
					if ("<=".equals(operator) || "<".equals(operator)) {
						date = SToolUtils.addDate(1, Calendar.DATE, date);
						try {
							date = SToolUtils.string2DateTime(SToolUtils
									.date2String(date, "yyyy-MM-dd HH:mm:ss"));
						} catch (ParseException e) {
							log.warn(e.getMessage(), e);
						}
					}
					parperParam(sql, sqlField, queryAttr, date, queryParamMap,
							operator);
				} else {
					String attributeValueStr = SToolUtils
							.object2String(attributeValue);
					parperParam(sql, sqlField, queryAttr, attributeValueStr,
							queryParamMap, operator);
				}
			} catch (NumberFormatException | NoSuchFieldException
					| SecurityException e) {
				log.error(e.getMessage());
			}
		}
	}

	/**
	 * 生成Hbm条件
	 * 
	 * @param clazz
	 *            查询的Hbm对象
	 * @param jsonParam
	 *            入参
	 * @param queryAttr
	 *            查询属性及从jsonParam取值的key
	 * @param sb
	 *            HQL
	 * @param queryParamMap
	 *            Hbm参数Map
	 * @param operator
	 *            查询符号：=、like、in
	 */
	public static void parperHbmParam(Class<?> clazz, JSONObject jsonParam,
			String queryAttr, StringBuffer sb,
			Map<String, Object> queryParamMap, String operator) {
		parperHbmParam(clazz, jsonParam, queryAttr, queryAttr, sb,
				queryParamMap, operator);
	}

	/**
	 * 生成Hbm条件
	 * 
	 * @param clazz
	 *            查询的Hbm对象
	 * @param jsonParam
	 *            入参
	 * @param fieldName
	 *            查询属性
	 * @param queryAttr
	 *            从jsonParam取值的Key
	 * @param sb
	 *            HQL
	 * @param queryParamMap
	 *            Hbm参数Map
	 * @param operator
	 *            查询符号：=、like、in
	 */
	public static void parperHbmParam(Class<?> clazz, JSONObject jsonParam,
			String fieldName, String queryAttr, StringBuffer sb,
			Map<String, Object> queryParamMap, String operator) {
		try {
			if (null == jsonParam || jsonParam.containsKey(queryAttr) == false
					|| StringUtils.isBlank(jsonParam.getString(queryAttr))) {
				return;
			}

			Object attributeValue = jsonParam.get(queryAttr);

			Field field = clazz.getDeclaredField(queryAttr);
			String fieldType = field.getType().getSimpleName();

			if ("in".equals(operator) || "not in".equals(operator)) {
				parperParam(sb, fieldName, queryAttr, attributeValue,
						queryParamMap, operator);
			} else if ("Integer".equals(fieldType)) {
				Integer attributeValueInteger = SToolUtils
						.object2Int(attributeValue);
				parperParam(sb, fieldName, queryAttr, attributeValueInteger,
						queryParamMap, operator);
			} else if ("Long".equals(fieldType)) {
				Long attributeValueInteger = Long.parseLong(SToolUtils
						.object2String(attributeValue));
				parperParam(sb, fieldName, queryAttr, attributeValueInteger,
						queryParamMap, operator);
			} else if ("Date".equals(fieldType)) {
				if (attributeValue instanceof Date) {
					parperParam(sb, fieldName, queryAttr, attributeValue,
							queryParamMap, operator);
					return;
				}
				parperParam(
						sb,
						fieldName,
						queryAttr,
						SaafDateUtils.string2UtilDate(attributeValue.toString()),
						queryParamMap, operator);
			} else {
				String attributeValueStr = SToolUtils
						.object2String(attributeValue);
				parperParam(sb, fieldName, queryAttr, attributeValueStr,
						queryParamMap, operator);
			}

		} catch (NumberFormatException | NoSuchFieldException
				| SecurityException e) {
			log.error(e.getMessage());
		}
	}

	/**
	 * 生成sql条件
	 * 
	 * @param jsonParam
	 *            入参
	 * @param queryAttr
	 *            查询字段及从jsonParam取值的key
	 * @param sb
	 *            sql
	 * @param queryParamMap
	 *            sql参数Map
	 * @param operator
	 *            查询符号：=、like、in
	 */
	public static void parperParam(JSONObject jsonParam, String queryAttr,
			StringBuffer sb, Map<String, Object> queryParamMap, String operator) {
		parperParam(jsonParam, queryAttr, queryAttr, sb, queryParamMap,
				operator, false);
	}

	/**
	 * 生成sql或hql条件，根据isHql判断，当传入的字段是数据库字段时使用
	 * 
	 * @param jsonParam
	 *            入参
	 * @param fieldName
	 *            查询字段
	 * @param queryAttr
	 *            jsonParam取值的key
	 * @param sb
	 *            sql
	 * @param queryParamMap
	 *            sql参数Map
	 * @param operator
	 *            查询符号：=、like、in
	 * @param isHql
	 *            是否根据字段查询
	 * @author ZhangJun
	 * @creteTime 2017-12-11
	 */
	public static void parperParam(JSONObject jsonParam, String fieldName,
			String queryAttr, StringBuffer sb,
			Map<String, Object> queryParamMap, String operator, boolean isHql) {
		if (isHql) {
			if (fieldName.indexOf(".") != -1) {
				// 有别名，截取别名后面字段
				String alias = fieldName.substring(0, fieldName.indexOf("."));
				String newFieldName = fieldName.substring(fieldName
						.indexOf(".") + 1);
				newFieldName = remove_AndNextChar2UpperCase(newFieldName);
				if (StringUtils.isNotBlank(alias)) {
					fieldName = alias + "." + newFieldName;
				} else {
					fieldName = newFieldName;
				}
			} else {
				fieldName = remove_AndNextChar2UpperCase(fieldName);
			}
		}
		parperParam(jsonParam, fieldName, queryAttr, sb, queryParamMap,
				operator);
	}

	/**
	 * 生成sql条件
	 * 
	 * @param jsonParam
	 *            入参
	 * @param fieldName
	 *            查询字段
	 * @param queryAttr
	 *            jsonParam取值的key
	 * @param sb
	 *            sql
	 * @param queryParamMap
	 *            sql参数Map
	 * @param operator
	 *            查询符号：=、like、in
	 */
	public static void parperParam(JSONObject jsonParam, String fieldName,
			String queryAttr, StringBuffer sb,
			Map<String, Object> queryParamMap, String operator) {
		if (null == jsonParam) {
			return;
		}
		if (jsonParam.containsKey(queryAttr)
				&& StringUtils.isNotBlank(queryAttr)) {
			Object attributeValue = jsonParam.get(queryAttr);
			String str = ObjectUtils.toString(attributeValue, "");
			if (StringUtils.isBlank(str)) {
				return;
			}
			if (attributeValue instanceof Integer) {
				Integer attributeValueInteger = SToolUtils
						.object2Int(attributeValue);
				parperParam(sb, fieldName, queryAttr, attributeValueInteger,
						queryParamMap, operator);
			} else if (attributeValue instanceof Long) {
				Long attributeValueInteger = Long.parseLong(SToolUtils
						.object2String(attributeValue));
				parperParam(sb, fieldName, queryAttr, attributeValueInteger,
						queryParamMap, operator);
			} else if (SToolUtils.isNumber(attributeValue)) {
				Long attributeValueInteger = Long.parseLong(SToolUtils
						.object2String(attributeValue));
				if (attributeValueInteger > Integer.MAX_VALUE)
					parperParam(sb, fieldName, queryAttr,
							attributeValueInteger, queryParamMap, operator);
				else
					parperParam(sb, fieldName, queryAttr,
							SToolUtils.object2Int(attributeValue),
							queryParamMap, operator);
			} else {
				String attributeValueStr = SToolUtils
						.object2String(attributeValue);
				parperParam(sb, fieldName, queryAttr, attributeValueStr,
						queryParamMap, operator);
			}
		}
	}

	/**
	 * 功能描述： 采用正则表达式的方式来判断一个字符串是否为数字，这种方式判断面比较全面，可以判断正负、整数小数 （推荐）
	 */
	public static boolean isNumber(Object str) {
		if (str == null) {
			return false;
		}
		return (str + "").matches("-?[0-9]+.?[0-9]*");
	}

	/**
	 * @param sb
	 *            sql或hql
	 * @param fieldName
	 *            字段或属性
	 * @param queryAttr
	 *            jsonParam取值的key
	 * @param value
	 *            查询字段值
	 * @param queryParamMap
	 *            sql或hql参数Map
	 * @param operator
	 *            查询符号：=、like、in
	 * @author ZhangJun
	 * @creteTime 2017-12-11
	 */
	private static void parperParam(StringBuffer sb, String fieldName,
			String queryAttr, Object value, Map<String, Object> queryParamMap,
			String operator) {
		// sb.append(" and " + fieldName + " " + operator + " :" +
		// queryAttr);//由于in判断直接赋值，不需要此语句，因此将此语句放入判断内
		if ("like".equals(operator.toLowerCase(Locale.ENGLISH))) {
			sb.append(" and " + fieldName + " " + operator + " :" + queryAttr);
			queryParamMap.put(queryAttr, "%" + value + "%");
		} else if ("fulllike".equals(operator.toLowerCase(Locale.ENGLISH))) {
			sb.append(" and " + fieldName + " " + " like ").append(" '%")
					.append(value).append("%' ");
		} else if ("iclike".equals(operator.toLowerCase(Locale.ENGLISH))) {
			sb.append(" and UPPER(" + fieldName + ") " + " like ")
					.append(" UPPER(CONCAT( '%','").append(value)
					.append("','%')) ");
		} else if ("in".equals(operator.toLowerCase(Locale.ENGLISH))
				|| "not in".equals(operator.toLowerCase(Locale.ENGLISH))) {
			if (StringUtils.isBlank(Objects.toString(value, "")))
				return;
			if (value instanceof Integer || value instanceof Long) {
				sb.append(" and " + fieldName + " " + operator + " ( " + value
						+ " )");
			} else if (value instanceof String) {
				sb.append(" and " + fieldName + " " + operator + " ( '"
						+ value.toString().replaceAll(",", "','") + "')");
			}
		} else {
			// only for Oracle
			if (value instanceof String) {
				Pattern pattern = Pattern
						.compile("^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$");
				Matcher m = pattern.matcher(value.toString());
				if (m.matches()) {
					sb.append(" and to_char(" + fieldName
							+ ",'yyyy-mm-dd hh24:mi:ss')  " + operator + " :"
							+ queryAttr);
				} else {
					sb.append(" and " + fieldName + " " + operator + " :"
							+ queryAttr);
				}
			} else {
				// general For Mysql
				sb.append(" and " + fieldName + " " + operator + " :"
						+ queryAttr);
			}

			queryParamMap.put(queryAttr, value);
		}
	}

	/**
	 * 实体类数据set
	 * 
	 * @param <T>
	 *            泛型
	 * @param t
	 *            实体类
	 * @param parameters
	 *            参数
	 * @param vo
	 *            ViewObject
	 * @param userId
	 *            当前用户ID
	 * @return 返回实体行
	 * @throws Exception
	 *             设置实体类数据异常
	 */
	public static <T> T setEntity(Class<T> t, JSONObject parameters,
			ViewObject vo, int userId) throws Exception {
		T tclass = getEditRow(t, vo, parameters, userId);
		Method[] methodArr = tclass.getClass().getMethods();
		for (Method m : methodArr) {
			String methodName = m.getName();
			if (methodName.startsWith("set") == false
					|| methodName.length() <= 3
					|| "setCreatedBy|setCreationDate|setLastUpdatedBy|setLastUpdateDate|setLastUpdateLogin|setOperatorUserId"
							.indexOf(methodName) != -1)
				continue;
			String attribute = new String(methodName.charAt(3) + "")
					.toLowerCase() + methodName.substring(4).toString();
			if (parameters.containsKey(attribute) == false)
				continue;
			Object obj = parameters.get(attribute);
			if (obj == null)
				continue;
			try {
				// 获取参数定义
				Class<?>[] clazz = m.getParameterTypes();
				String type = clazz[0].getName();
				if (type.equals("java.lang.String")) {
					m.invoke(tclass, obj.toString());
				} else if (type.equals("java.sql.Timestamp")) {
					m.invoke(tclass, obj.toString());
				} else if (type.equals("java.lang.Short")
						|| type.equals("short")) {
					m.invoke(tclass, Short.parseShort(obj.toString()));
				} else if (type.equals("java.lang.Long") || type.equals("long")) {
					m.invoke(tclass, Long.parseLong(obj.toString()));
				} else if (type.equals("java.lang.Integer")
						|| type.equals("int")) {
					if (isNullOrEmpty(obj.toString())) {
						m.invoke(tclass, new Object[] { null });
					} else {
						m.invoke(tclass, Integer.parseInt(obj.toString()));
					}

				} else if (type.equals("java.lang.Float")
						|| type.equals("float")) {
					m.invoke(tclass, obj.toString());
				} else if (type.equals("java.lang.Double")
						|| type.equals("double")) {
					m.invoke(tclass, Double.parseDouble(obj.toString()));
				} else if (type.equals("java.math.BigDecimal")
						|| type.equals("BigDecimal")) {
					if (isNullOrEmpty(obj.toString())) {
						m.invoke(tclass, new Object[] { null });
					} else {
						BigDecimal bigDecimal = new BigDecimal(obj.toString());
						m.invoke(tclass, bigDecimal);
					}
				} else if (type.equals("java.lang.Boolean")
						|| type.equals("boolean")) {
					m.invoke(tclass, obj.toString());
				} else if (type.equals("java.util.Date")
						|| type.equals("java.sql.Date")) {
					if (obj.toString().length() > 10)
						m.invoke(tclass, SaafDateUtils.string2UtilDate(
								obj.toString(), "yyyy-MM-dd HH:mm:ss"));
					else if (obj.toString().length() == 10)
						m.invoke(tclass, SaafDateUtils.string2UtilDate(
								obj.toString(), null));
				} else {
					// more other types
				}
			} catch (Exception e) {
				log.error("", e);
			}
		}
		return tclass;
	}

	/**
	 * 获取新增或修改行
	 * 
	 * @param <T>
	 *            泛型
	 * @param t
	 *            实体类
	 * @param vo
	 *            ViewObjectImpl
	 * @param parameters
	 *            参数
	 * @param userId
	 *            当前用户ID
	 * @return 返回实体行
	 * @throws Exception
	 *             获取新增或修改行异常
	 */
	public static <T> T getEditRow(Class<T> t, ViewObject vo,
			JSONObject parameters, int userId) throws Exception {
		T tclass = null;
		String primaryKey = "";
		// 通过 @id 注解找出主键，然后根据parameters是否包含 主键参数来确定新增或修改操作
		Method[] methods = t.getMethods();
		for (Method method : methods) {
			Id idfield = method.getAnnotation(Id.class);
			GeneratedValue generatedValue = method
					.getAnnotation(GeneratedValue.class);
			if (idfield != null && generatedValue != null) {
				primaryKey = method.getName().replaceFirst("get", "");
				primaryKey = primaryKey.substring(0, 1).toLowerCase()
						+ primaryKey.substring(1);
				break;
			}
		}
		if (StringUtils.isBlank(primaryKey))
			throw new Exception("not found the primaryKey with entity "
					+ t.getName());
		if (StringUtils.isBlank(parameters.getString(primaryKey))) {
			tclass = t.newInstance();
		} else {
			tclass = (T) vo.getById(parameters.getInteger(primaryKey));
			if (tclass == null)
				throw new IllegalArgumentException(t.getName()
						+ " data not existed with id  "
						+ parameters.getIntValue(primaryKey));
		}
		try {
			Method method = tclass.getClass().getMethod("setOperatorUserId",
					new Class[] { Integer.class });
			if (method != null)
				method.invoke(tclass, new Object[] { userId });
		} catch (NoSuchMethodException e) {
			log.error(e.getMessage(), e);
		}
		return tclass;
	}

	/***
	 * 功能：驼峰转下划线命名规则
	 * 
	 * @param para
	 *            驼峰命名的字符串
	 */
	@SuppressWarnings("all")
	public static String humpToUnderline(String para) {
		final String UNDERLINE = "_";
		StringBuilder sb = new StringBuilder(para);
		int temp = 0;// 定位
		if (!para.contains(UNDERLINE)) {
			for (int i = 0; i < para.length(); i++) {
				if (Character.isUpperCase(para.charAt(i))) {
					sb.insert(i + temp, UNDERLINE);
					temp += 1;
				}
			}
		}
		return sb.toString();
	}

	/**
	 * 功能：下划线转驼峰名称规则 移除下划线，并将下划线后一位转换为大写
	 * 
	 * @param param
	 *            带下划线字段
	 * @return 字段属性
	 * @author ZhangJun
	 * @creteTime 2017-12-11
	 */
	public static String remove_AndNextChar2UpperCase(String param) {
		boolean endFlag = false;
		if (param.endsWith("_")) {
			endFlag = true;
		}
		if (param == null || "".equals(param.trim())) {
			return "";
		}
		param = param.toLowerCase();
		if ('_' == param.charAt(0)) {
			param = param.substring(1);
		}
		if ('_' == param.charAt(param.length() - 1)) {
			param = param.substring(0, param.length() - 1);
		}
		StringBuilder sb = new StringBuilder(param);
		Matcher mc = Pattern.compile("_").matcher(param);
		int i = 0;
		while (mc.find()) {
			int position = mc.end() - (i++);
			// String.valueOf(Character.toUpperCase(sb.charAt(position)));
			sb.replace(position - 1, position + 1,
					sb.substring(position, position + 1).toUpperCase());
		}
		if (endFlag) {
			return sb.toString() + "_";
		} else {
			return sb.toString();
		}
	}

	public static void main(String[] args) {
		String str = "java_hell";
		str = remove_AndNextChar2UpperCase(str);
		System.out.println(str);
	}

	/**
	 * @param jsonObject
	 *            JSON对象
	 * @param keys
	 *            多个key
	 * @description 检查 json指定key的值是否为null 或者空字符
	 * @creteTime 2017年12月12日 11:59:55
	 */
	public static void validateJsonParms(JSONObject jsonObject, String... keys)
			throws IllegalArgumentException {
		if (keys == null || keys.length == 0)
			return;
		if (jsonObject == null)
			throw new IllegalArgumentException("缺少参数 " + keys[0]);
		for (String key : keys) {
			if (StringUtils.isBlank(jsonObject.getString(key)))
				throw new IllegalArgumentException("缺少参数 " + key);
		}
	}

	/**
	 * @param entity
	 *            实体类
	 * @param fields
	 *            多个字段
	 * @description 检查 实体类指定字段的值是否为null 或者空字符
	 * @creteTime 2017年12月12日 11:59:46
	 */
	public static void validateEntityParams(Object entity, String... fields) {
		if (fields == null || fields.length == 0)
			return;
		if (entity == null)
			throw new IllegalArgumentException("缺少参数 " + fields);
		for (String field : fields) {
			try {
				Field f = entity.getClass().getDeclaredField(field);
				if (f.isAccessible() == false)
					f.setAccessible(true);
				Object obj = f.get(entity);
				if (obj == null || (obj instanceof String && "".equals(obj)))
					throw new IllegalArgumentException("缺少参数 " + field);
			} catch (NoSuchFieldException | IllegalAccessException e) {
				log.warn("field {} in {} not existed", field, entity.getClass()
						.getName());
			}
		}
	}

	/**
	 * 生成获取总数SQL（SQL语句）
	 * 
	 * @param sql
	 *            sql语句
	 * @return 返回select count(1) from (sql参数) cntemp
	 */
	public static StringBuffer getSqlCountString(StringBuffer sql) {
		StringBuffer countsql = new StringBuffer();
		countsql.append("select count(1) from (" + sql + ") cntemp");
		return countsql;
	}

	/**
	 * 生成获取总数SQL（SQL语句） 加索引列
	 * 
	 * @param sql
	 * @param countParam
	 * @return
	 */
	public static StringBuffer getSimpleSqlCountString(StringBuffer sql,
			String countParam) {
		String countSql = sql.toString();
		String lowerCountSql = countSql.toLowerCase();
		int index = lowerCountSql.indexOf(" from ");
		if (index == -1) {
			index = lowerCountSql.indexOf("\nfrom ");
		}
		if (index == -1) {
			index = lowerCountSql.indexOf("from\n");
		}
		if (index == -1) {
			index = lowerCountSql.indexOf("\nfrom\n");
		}
		StringBuffer newSql = new StringBuffer("select " + countParam + "\n");
		newSql.append(countSql.substring(index));
		return newSql;
	}

	/**
	 * 设置排序字段
	 * 
	 * @param queryParamJSON
	 *            参数
	 * @param sql
	 *            SQL或HQL语句
	 * @param defaultOrderBy
	 *            设置默认的排序，如：order_no asc,user_name desc
	 * @param isHql
	 *            是否HQL语句
	 * @author ZhangJun
	 * @creteTime 2017-12-11
	 */
	public static void changeQuerySort(JSONObject queryParamJSON,
			StringBuffer sql, String defaultOrderBy, boolean isHql) {

		String orderBy = queryParamJSON.getString("orderBy");
		String sort = queryParamJSON.getString("sort");
		if (StringUtils.isBlank(orderBy)) {
			if (StringUtils.isNotBlank(defaultOrderBy)) {
				if (isHql) {
					String alias = "";
					String field = defaultOrderBy;
					// 如果是hql语句，需要将字段名转换为属性
					if (StringUtils.indexOf(defaultOrderBy, ".") != -1) {
						alias = defaultOrderBy.substring(0,
								defaultOrderBy.indexOf("."));
						field = defaultOrderBy.substring(defaultOrderBy
								.indexOf(".") + 1);
					}
					field = SaafToolUtils.remove_AndNextChar2UpperCase(field);

					if (StringUtils.isNotBlank(alias)) {
						defaultOrderBy = alias + "." + field;
					} else {
						defaultOrderBy = field;
					}
				}
				sql.append(" order by ").append(defaultOrderBy);
			}
		} else {

			if (isHql) {
				// 如果是hql语句，需要将字段名转换为属性
				orderBy = SaafToolUtils.remove_AndNextChar2UpperCase(orderBy);
			}

			if (StringUtils.isBlank(sort)) {
				sort = "asc";
			}
			if (StringUtils.isNotBlank(orderBy)) {
				sql.append(" order by " + orderBy + " " + sort);
			}
		}
	}

	public static JSONObject preaseServiceResultJSONObject(String requestURL,
			JSONObject queryParamJSON) {
		JSONArray array = preaseServiceResult(requestURL, queryParamJSON);
		if (null != array && array.size() >= 1) {
			JSONObject jSONObject = array.getJSONObject(0);
			return jSONObject;
		} else {
			return new JSONObject();
		}
	}

	/**
	 * @param requestURL
	 *            请求地址：工程名+路径
	 * @param queryParamJSON
	 *            参数
	 * @return
	 * @throws IOException
	 */
	public static JSONArray preaseServiceResult(String requestURL,
			JSONObject queryParamJSON) {
		Assert.isTrue(StringUtils.isNotBlank(requestURL),
				"requesturl is required");
		RestTemplate restTemplate = (RestTemplate) SpringBeanUtil
				.getBean("restTemplate");
		if (restTemplate == null)
			throw new ExceptionInInitializerError("restTemplate 初始化异常");
		MultiValueMap header = new LinkedMultiValueMap();
		Long timestamp = System.currentTimeMillis();
		header.add("timestamp", timestamp + "");
		header.add("pdasign", buildSign(timestamp));
		JSONObject resultJSON = restSpringCloudPost(requestURL, queryParamJSON,
				header, restTemplate);
		log.debug("request result:{}", resultJSON);

		if (resultJSON.getIntValue("statusCode") == 200) {
			JSONObject data = resultJSON.getJSONObject("data");
			if ("S".equalsIgnoreCase(data.getString("status"))) {
				JSONArray jsonArray = data.getJSONArray("data");
				return jsonArray == null ? new JSONArray() : jsonArray;
			} else {
				log.info("服务[{}]请求失败:{}", requestURL, resultJSON);
			}
		} else {
			log.info("服务[{}]请求异常:{}", requestURL, resultJSON);
		}
		return null;
	}

	/**
	 * 跨模块请求，返回请求结果JSON
	 * 
	 * @author ZhangJun
	 * @createTime 2018/3/13
	 * @description 跨模块请求，返回请求结果JSON
	 */
	public static JSONObject preaseServiceResultJSON(String requestURL,
			JSONObject queryParamJSON) {
		Assert.isTrue(StringUtils.isNotBlank(requestURL),
				"requesturl is required");
		RestTemplate restTemplate = (RestTemplate) SpringBeanUtil
				.getBean("restTemplate");
		if (restTemplate == null)
			throw new ExceptionInInitializerError("restTemplate 初始化异常");

		MultiValueMap header = new LinkedMultiValueMap();
		Long timestamp = System.currentTimeMillis();
		header.add("timestamp", timestamp + "");
		header.add("pdasign", buildSign(timestamp));
		JSONObject resultJSON = restSpringCloudPost(requestURL, queryParamJSON,
				header, restTemplate);
		log.info("request result:{}", resultJSON);

		if (resultJSON.getIntValue("statusCode") == 200) {
			JSONObject data = resultJSON.getJSONObject("data");
			return data;
		}
		return null;
	}

	/**
	 * ResultSet转list
	 * 
	 * @param rs
	 * @return
	 * @throws java.sql.SQLException
	 */
	public static List<JSONObject> resultSetToList(ResultSet rs)
			throws java.sql.SQLException {
		if (rs == null)
			return Collections.EMPTY_LIST;
		ResultSetMetaData md = rs.getMetaData(); // 得到结果集(rs)的结构信息，比如字段数、字段名等
		int columnCount = md.getColumnCount(); // 返回此 ResultSet 对象中的列数
		List<JSONObject> list = new ArrayList();

		while (rs.next()) {
			JSONObject rowData = new JSONObject();
			for (int i = 1; i <= columnCount; i++) {

				rowData.put(md.getColumnLabel(i) == null ? md.getColumnName(i)
						: md.getColumnLabel(i), rs.getObject(i));
			}
			list.add(rowData);
		}
		return list;
	}

	/**
	 * 构造in语句，若valueList超过1000时，该函数会自动拆分成多个in语句
	 * 
	 * @param item
	 *            字段
	 * @param valueList
	 *            值集合
	 * @author ZhangJun
	 * @createTime 2018/3/13
	 * @return item in (valueList)
	 */
	public static String buildLogicIN(String item, List valueList) {
		int n = (valueList.size() - 1) / 1000;
		StringBuffer rtnStr = new StringBuffer();
		Object obj = valueList.get(0);
		boolean isString = false;
		if (obj instanceof Character || obj instanceof String) {
			isString = true;
		}
		String tmpStr;
		for (int i = 0; i <= n; i++) {
			int size = i == n ? valueList.size() : (i + 1) * 1000;
			if (i > 0) {
				rtnStr.append(" or ");
			}
			rtnStr.append(item + " in (");
			if (isString) {
				StringBuffer tmpBuf = new StringBuffer();
				for (int j = i * 1000; j < size; j++) {
					tmpStr = valueList.get(j).toString().replaceAll("'", "''");
					tmpBuf.append(",'").append(tmpStr).append("'");
				}
				tmpStr = tmpBuf.substring(1);
			} else {
				tmpStr = valueList.subList(i * 1000, size).toString();
				tmpStr = tmpStr.substring(1, tmpStr.length() - 1);
			}
			rtnStr.append(tmpStr);
			rtnStr.append(")");
		}
		if (n > 0) {
			return "(" + rtnStr.toString() + ")";
		} else {
			return rtnStr.toString();
		}
	}

	public static String buildSign(Long timeStamp) {
		String timestamp = timeStamp + "";
		Integer lastNum = Integer.valueOf(timestamp.substring(timestamp
				.length() - 1));
		int first = lastNum % 5 + 1;
		int count = 0;
		for (int i = 0; i < first; i++) {
			int n = timestamp.charAt(i) - '0';
			count += n;
		}
		int md5Times = count % 4 + 1;
		String str = timestamp;
		while ((md5Times--) > 0) {
			str = DigestUtils.md5(str);
		}
		return str;
	}

	public static String buildAPPSign(Long timeStamp) {
		String timestamp = timeStamp + "";
		Integer lastNum = Integer.valueOf(timestamp.substring(
				timestamp.length() - 2, timestamp.length() - 1));
		int first = lastNum % 4 + 1;
		int count = 0;
		for (int i = 0; i < first; i++) {
			int n = timestamp.charAt(i) - '0';
			count += n;
		}
		int md5Times = count % 4 + 1;
		String str = timestamp;
		while ((md5Times--) > 0) {
			str = DigestUtils.md5(str);
		}
		return str;
	}

	/**
	 * @param jsonObject
	 *            JSON对象
	 * @param keys
	 *            多个key
	 * @author YangXiaowei
	 * @creteTime 2018/4/21
	 * @description 清除空值
	 */
	public static JSONObject cleanNull(JSONObject jsonObject, String... keys) {
		if (keys == null || keys.length == 0 || jsonObject == null)
			return jsonObject;
		for (String key : keys) {
			if (StringUtils.isBlank(jsonObject.getString(key))) {
				jsonObject.remove(key);
			}
		}
		return jsonObject;
	}

	/**
	 * @param jsonObject
	 *            JSON对象
	 * @param keys
	 *            多个key
	 * @author YangXiaowei
	 * @creteTime 2018/4/21
	 * @description 解决搜素条件中含空格问题
	 */
	public static JSONObject replaceBlank(JSONObject jsonObject, String... keys) {
		if (keys == null || keys.length == 0 || jsonObject == null)
			return jsonObject;
		for (String key : keys) {
			String value = jsonObject.getString(key);
			if (StringUtils.isNotBlank(jsonObject.getString(key))) {
				jsonObject.put(key, value.replaceAll(" ", "%"));
			}
		}
		return jsonObject;
	}

	public static JSONObject restSpringCloudPost(String requestURL,
			JSONObject postParam, MultiValueMap<String, String> headerParams,
			RestTemplate restTemplate) {
		JSONObject resultJSONObject = new JSONObject();
		if (headerParams == null) {
			headerParams = new LinkedMultiValueMap();
			headerParams.add("Accept", "application/json");
			headerParams.add("Accept-Charset", "utf-8");
			headerParams.add("Content-Type",
					"application/x-www-form-urlencoded; charset=UTF-8");
			headerParams.add("Content-Encoding", "UTF-8");
		} else {
			if (!headerParams.containsKey("Accept")) {
				headerParams.add("Accept", "application/json");
			}

			if (!headerParams.containsKey("Accept-Charset")) {
				headerParams.add("Accept-Charset", "utf-8");
			}

			if (!headerParams.containsKey("Content-Type")) {
				headerParams.add("Content-Type",
						"application/x-www-form-urlencoded; charset=UTF-8");
			}

			if (!headerParams.containsKey("Content-Encoding")) {
				headerParams.add("Content-Encoding", "UTF-8");
			}
		}

		StringBuilder requestBodey = new StringBuilder();
		if (postParam != null && postParam.size() > 0) {
			Set<Map.Entry<String, Object>> entrySet = postParam.entrySet();
			Iterator iterator = entrySet.iterator();
			while (iterator.hasNext()) {
				Map.Entry<String, Object> entry = (Map.Entry) iterator.next();
				requestBodey.append(entry.getKey()).append("=");
				if (entry.getValue() != null) {
					try {
						requestBodey.append(URLEncoder.encode(entry.getValue()
								.toString(), "utf-8"));
					} catch (UnsupportedEncodingException e) {
						log.error(e.getMessage());
					}
				}
				if (iterator.hasNext())
					requestBodey.append("&");

			}
		}

		HttpClientBuilder httpClientBuilder = HttpClients.custom();
		HttpClient httpClient = httpClientBuilder.build();
		HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(
				httpClient);

		if (postParam != null
				&& StringUtils.isNotBlank(postParam.toJSONString())) {
			try {
				restTemplate.setRequestFactory(clientHttpRequestFactory);
				HttpEntity request = new HttpEntity(requestBodey.toString(),
						headerParams);
				ResponseEntity responseEntity = restTemplate.postForEntity(
						requestURL, request, String.class, new Object[0]);

				HttpStatus strVlaue = responseEntity.getStatusCode();
				if (strVlaue.value() == 200) {
					resultJSONObject.put("statusCode", strVlaue.value());
				}

				String body = (String) responseEntity.getBody();
				resultJSONObject.put("data", body);
			} catch (Exception var13) {
				log.info(var13.getMessage(), var13);
				resultJSONObject.put("msg", var13.getMessage());
			}
		}

		return resultJSONObject;
	}

	/**
	 * DRP接口三个参数进行字典序排序
	 * 
	 * @param token
	 * @param timestamp
	 * @param nonce
	 * @return
	 */
	public static String sort(String token, String timestamp, String nonce) {
		String[] strArray = { token, timestamp, nonce };
		Arrays.sort(strArray);
		StringBuilder sbuilder = new StringBuilder();
		for (String str : strArray) {
			sbuilder.append(str);
		}
		return sbuilder.toString();
	}

	public static String getCharIdx(String ch) {
		Map<String, String> map = new HashMap<>();
		for (int c = 65; c < 91; c++) {
			map.put(String.valueOf((char) c), String.valueOf(c - 65));
		}
		return map.get(ch);
	}

	public static String toJson(Object object) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		try {
			return mapper.writeValueAsString(object);
		} catch (Exception e) {
			return null;
		}
	}

}
