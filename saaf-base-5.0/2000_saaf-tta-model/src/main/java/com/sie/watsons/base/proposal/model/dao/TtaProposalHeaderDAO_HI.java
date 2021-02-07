package com.sie.watsons.base.proposal.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.proposal.model.entities.TtaProposalHeaderEntity_HI;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;
import org.springframework.stereotype.Component;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("ttaProposalHeaderDAO_HI")
public class TtaProposalHeaderDAO_HI extends BaseCommonDAO_HI<TtaProposalHeaderEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaProposalHeaderDAO_HI.class);

	public TtaProposalHeaderDAO_HI() {
		super();
	}

	//变更
	public  Map<String, Object>  callOrder(Map<String, Object>  map ) throws Exception {
		final Map<String, Object> paramsMap = new HashMap<String, Object>();
		try {
			Session session = this.getSessionFactory().getCurrentSession();
			session.doWork(new Work() {
				public void execute(Connection conn) throws SQLException {
					String sql = "{call PKG_TTA_PROPOSAL.P_TTA_PROPOSAL_COPY(?,?,?,?,?,?)}";
					conn.setAutoCommit(false);
					CallableStatement call = conn.prepareCall(sql);
// 设置输入参数
					call.setInt(1, (Integer) map.get("pkId"));
					call.setInt(2, (Integer) map.get("userId"));
					call.setString(3, (String) map.get("dealType"));
// 设置输出参数
					call.registerOutParameter(4, Types.INTEGER);
					call.registerOutParameter(5, Types.VARCHAR);
					call.registerOutParameter(6, Types.INTEGER);

					call.execute();
					int xFlag = call.getInt(4);
					String xMsg = call.getString(5);
					int proposalId = call.getInt(6);
					paramsMap.put("xFlag",xFlag);
					paramsMap.put("xMsg",xMsg);
					paramsMap.put("proposalId",proposalId);
					close(call);
				}
			});
			return paramsMap;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return paramsMap;
	}




	//版本切换
	public  Map<String, Object>  callCut(Map<String, Object>  map ) throws Exception {
		final Map<String, Object> paramsMap = new HashMap<String, Object>();
		try {
			Session session = this.getSessionFactory().getCurrentSession();
			session.doWork(new Work() {
				public void execute(Connection conn) throws SQLException {
					String sql = "{call PKG_TTA_PROPOSAL.P_TTA_PROPOSAL_CUT(?,?,?,?,?)}";
					conn.setAutoCommit(false);
					CallableStatement call = conn.prepareCall(sql);
// 设置输入参数
					call.setInt(1, (Integer) map.get("pkId"));
					call.setInt(2, (Integer) map.get("userId"));

// 设置输出参数
					call.registerOutParameter(3, Types.INTEGER);
					call.registerOutParameter(4, Types.VARCHAR);
					call.registerOutParameter(5, Types.INTEGER);

					call.execute();
					int xFlag = call.getInt(3);
					String xMsg = call.getString(4);
					int proposalId = call.getInt(5);
					paramsMap.put("xFlag",xFlag);
					paramsMap.put("xMsg",xMsg);
					paramsMap.put("proposalId",proposalId);
					close(call);
				}
			});
			return paramsMap;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return paramsMap;
	}

	//proposal生成页面中的变更
	public Map<String, Object> callChangProposalBillStatus(Map<String, Object> map) throws Exception{
		final Map<String, Object> paramsMap = new HashMap<String, Object>();
		try {
			Session session = this.getSessionFactory().getCurrentSession();
			session.doWork(new Work() {
				public void execute(Connection conn) throws SQLException {
					String sql = "{call P_TTA_PROPOSAL_BUILD_BILL_COPY(?,?,?,?,?)}";
					conn.setAutoCommit(false);
					CallableStatement call = conn.prepareCall(sql);
					// 设置输入参数
					call.setInt(1, (Integer) map.get("pkId"));//proposal id
					call.setInt(2, (Integer) map.get("userId"));
					//call.setString(3, (String) map.get("dealType"));
					// 设置输出参数
					call.registerOutParameter(3, Types.INTEGER);
					call.registerOutParameter(4, Types.VARCHAR);
					call.registerOutParameter(5, Types.INTEGER);

					call.execute();
					int xFlag = call.getInt(3);
					String xMsg = call.getString(4);
					int proposalId = call.getInt(5);
					paramsMap.put("xFlag",xFlag);
					paramsMap.put("xMsg",xMsg);
					paramsMap.put("proposalId",proposalId);
					close(call);
				}
			});
			return paramsMap;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return paramsMap;
	}

	//proposal生成:切换上一版本
	public Map<String, Object> cutProposalBillVersion(Map<String, Object> map) throws Exception{
		final Map<String, Object> paramsMap = new HashMap<String, Object>();
		try {
			Session session = this.getSessionFactory().getCurrentSession();
			session.doWork(new Work() {
				public void execute(Connection conn) throws SQLException {
					String sql = "{call p_tta_proposal_build_bill_cut(?,?,?,?,?)}";
					conn.setAutoCommit(false);
					CallableStatement call = conn.prepareCall(sql);
// 设置输入参数
					call.setInt(1, (Integer) map.get("pkId"));
					call.setInt(2, (Integer) map.get("userId"));

// 设置输出参数
					call.registerOutParameter(3, Types.INTEGER);
					call.registerOutParameter(4, Types.VARCHAR);
					call.registerOutParameter(5, Types.INTEGER);

					call.execute();
					int xFlag = call.getInt(3);
					String xMsg = call.getString(4);
					int proposalId = call.getInt(5);
					paramsMap.put("xFlag",xFlag);
					paramsMap.put("xMsg",xMsg);
					paramsMap.put("proposalId",proposalId);
					close(call);
				}
			});
			return paramsMap;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return paramsMap;
	}

	private void close(CallableStatement cs) {
		try {
			if (cs != null) {
				cs.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}



}
