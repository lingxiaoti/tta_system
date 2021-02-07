package com.sie.watsons.base.report.model.dao;

import com.sie.saaf.common.bean.UserSessionBean;
import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.report.model.entities.TtaFreeGoodsPolistEntity_HI;
import com.sie.watsons.base.report.utils.JdbcUtils;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.JedisCluster;

@Component("ttaFreeGoodsPolistDAO_HI")
public class TtaFreeGoodsPolistDAO_HI extends BaseCommonDAO_HI<TtaFreeGoodsPolistEntity_HI> {
	private static final Logger logger = LoggerFactory.getLogger(TtaFreeGoodsPolistDAO_HI.class);

	public TtaFreeGoodsPolistDAO_HI() {
		super();
	}

	public List<Integer> saveSeqBatchJDBC(final String table, List<Map<String, Object>> fieldParamsList, String majorKey, String seq, String symbol) throws Exception {
		List<Map<Integer, Object>> paramsList = new ArrayList<>();
		try {
			/*if (conn == null) {
				throw new IllegalArgumentException("当前无数据库连接");
			}*/
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
				/*if (conn != null) {
					conn.setAutoCommit(false);
				}*/
				conn = JdbcUtils.getConnection();
				logger.info(strsql);
				pstmt = conn.prepareStatement(strsql, PreparedStatement.RETURN_GENERATED_KEYS);
				//for (Map<Integer, Object> map : paramsList) {
				for (int i = 0; i < paramsList.size(); i++) {
					Map<Integer, Object> map = paramsList.get(i);
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
					logger.info("执行第"+ (i + 1) +"行的参数:[{}]",JSONObject.toJSONString(map));
				}
				//logger.info("==> Parameters："+loginfo.deleteCharAt(loginfo.length()-1));
				if(logger.isDebugEnabled()){
					logger.debug("==> Parameters："+loginfo.deleteCharAt(loginfo.length()-1));
				}
				pstmt.executeBatch();
				//conn.commit();
				/*rs = pstmt.getGeneratedKeys();// 获取主键ID
				while(rs.next()){
					list.add(rs.getInt(1));
				}*/
			} catch (Exception es) {
				//if (conn != null) {
				//	conn.rollback();//回滚
				//}
				logger.error("start batch insert exception:{}" + es);
				throw new IllegalArgumentException("start batch insert exception:" + es.getMessage());
			}finally{
				//if(null != conn){conn.close();}
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

	public List<Integer> saveSeqBatchJDBC(final String table, List<Map<String, Object>> fieldParamsList, String majorKey, String seq, String symbol, JedisCluster jedisCluster, UserSessionBean sessionBean) throws Exception {
		List<Map<Integer, Object>> paramsList = new ArrayList<>();
		try {
			/*if (conn == null) {
				throw new IllegalArgumentException("当前无数据库连接");
			}*/
			if (fieldParamsList == null || fieldParamsList.isEmpty()) {
				return Collections.EMPTY_LIST;
			}
			String strsql = this.createSQLSeqParamsForMap(table, fieldParamsList, paramsList,majorKey,seq,jedisCluster,sessionBean);
			//logger.info("[begin 批量保存:"+paramsList.size()+"条数据, ==>  Preparing：{}]", sql);
			List<Integer> list = new ArrayList<>(paramsList.size());
			long t1 = System.currentTimeMillis();
			final StringBuilder loginfo = new StringBuilder();
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try {
				/*if (conn != null) {
					conn.setAutoCommit(false);
				}*/
				conn = JdbcUtils.getConnection();
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
					if(!SaafToolUtils.isNullOrEmpty(jedisCluster)){
						jedisCluster.setex(sessionBean.getCertificate(),3600,"{status:'U',currentStage:'拼装阶段二',orderNum:"+"'" + key+"'}");
					}
				}
				if(logger.isDebugEnabled()){
					logger.debug("==> Parameters："+loginfo.deleteCharAt(loginfo.length()-1));
				}
				pstmt.executeBatch();
				//conn.commit();
				/*rs = pstmt.getGeneratedKeys();// 获取主键ID
				while(rs.next()){
					list.add(rs.getInt(1));
				}*/
			} catch (Exception es) {
				//if (conn != null) {
				//	conn.rollback();//回滚
				//}
				logger.error("start batch insert exception:{}" + es);
				throw new IllegalArgumentException("start batch insert exception:" + es.getMessage());
			}finally{
				//if(null != conn){conn.close();}
				if(null != pstmt){pstmt.close();}
				if(null != rs){rs.close();}
			}
			long t2 = System.currentTimeMillis();
			logger.info("[end batchJDBCSave批量保存:"+paramsList.size()+"条数据,times:"+(t2-t1)+"ms.]");
			return list;
		} catch (Exception e) {
			// LOGGER.error("fieldParamsList 参数是:{},paramsList 参数是:{}" + fieldParamsList, paramsList);
			JdbcUtils.rollbackTransaction();
			logger.error("batchSave-exception", e);
			throw new Exception("batchSave-exception:" + e);
		}
	}

}
