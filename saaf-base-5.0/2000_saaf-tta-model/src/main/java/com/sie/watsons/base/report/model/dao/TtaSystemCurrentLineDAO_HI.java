package com.sie.watsons.base.report.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.report.model.entities.TtaSystemCurrentLineEntity_HI;
import com.sie.watsons.base.report.utils.JdbcUtils;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("ttaSystemCurrentLineDAO_HI")
public class TtaSystemCurrentLineDAO_HI extends BaseCommonDAO_HI<TtaSystemCurrentLineEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaSystemCurrentLineDAO_HI.class);

	public TtaSystemCurrentLineDAO_HI() {
		super();
	}

	@SuppressWarnings("all")
	public void updateBatchJDBC(final String table, Class cls, List<Map<String, Object>> fieldParamsList) {
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
				//conn = jdbcTemplate.getDataSource().getConnection();
				//conn.setAutoCommit(false);
				conn = JdbcUtils.getConnection();
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
				//conn.commit();

				logger.info("[end updateBatchJDBC批量更新Sql语句:" + updateSql + "; 操作的字段信息：" + fieldList);
			} catch (Exception e) {
			/*	if (conn != null) {
					conn.rollback();
				}*/
				logger.error("start batchUpdate-exception:" + e, e);
				throw new IllegalArgumentException("start batchUpdate-exception:" + e, e);
			} finally{
				//if(null != conn){conn.close();}
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

}
