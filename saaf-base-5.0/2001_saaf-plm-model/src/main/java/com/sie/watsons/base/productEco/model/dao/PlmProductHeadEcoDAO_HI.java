package com.sie.watsons.base.productEco.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.productEco.model.entities.PlmProductHeadEcoEntity_HI;
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

@Component("plmProductHeadEcoDAO_HI")
public class PlmProductHeadEcoDAO_HI extends BaseCommonDAO_HI<PlmProductHeadEcoEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmProductHeadEcoDAO_HI.class);

	public PlmProductHeadEcoDAO_HI() {
		super();
	}

    public void callProc() throws Exception {
		try {
			Session session = this.getSessionFactory().getCurrentSession();
			session.doWork(new Work() {
				public void execute(Connection conn) throws SQLException {
					String sql = "{CALL PLM_SYNC_FROM_RMS_PKG.SYNC_FROM_RMS(?,?)}";
					conn.setAutoCommit(false);
					CallableStatement call = conn.prepareCall(sql);
					call.registerOutParameter(1, Types.VARCHAR);
					call.registerOutParameter(2, Types.VARCHAR);
					call.execute();
                    String  x_status = call.getString(1);
                    String  x_msg = call.getString(2);
                    LOGGER.info("x_status" + x_status + "x_msg" + x_msg);
                    close(call);
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//变更
	public Map<String, Object> callPkg(Map<String, Object>  map ) throws Exception {
		final Map<String, Object> paramsMap = new HashMap<String, Object>();
		try {
			Session session = this.getSessionFactory().getCurrentSession();
			session.doWork(new Work() {
				public void execute(Connection conn) throws SQLException {
					String sql = "{call plm_product_modify.diference_check(?,?,?)}";
					conn.setAutoCommit(false);
					CallableStatement call = conn.prepareCall(sql);
// 设置输入参数
					call.setString(1,  map.get("eco_id").toString());
// 设置输出参数
					call.registerOutParameter(2, Types.VARCHAR);
					call.registerOutParameter(3, Types.VARCHAR);
					call.execute();
//					String x_msg = call.getString(2);
//					String x_status  = call.getString(3);
					String  x_status = call.getString(2);
					String  x_msg = call.getString(3);
					paramsMap.put("x_msg",x_msg);
					paramsMap.put("x_status",x_status);
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
