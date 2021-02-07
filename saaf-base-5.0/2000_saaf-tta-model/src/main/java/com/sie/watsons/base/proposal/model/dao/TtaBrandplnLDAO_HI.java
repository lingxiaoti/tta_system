package com.sie.watsons.base.proposal.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.proposal.model.entities.TtaBrandplnLEntity_HI;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

@Component("ttaBrandplnLDAO_HI")
public class TtaBrandplnLDAO_HI extends BaseCommonDAO_HI<TtaBrandplnLEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaBrandplnLDAO_HI.class);

	public TtaBrandplnLDAO_HI() {
		super();
	}


	/**
	 * 2019.8.17注释,不用这种调用存储的方式
	 * @param map
	 * @return
	 * @throws Exception
	 */
	//行信息保存
	public Map<String, Object> callBrandPlnSave(Map<String, Object>  map ) throws Exception {
		final Map<String, Object> paramsMap = new HashMap<String, Object>();
		try {
			Session session = this.getSessionFactory().getCurrentSession();
			session.doWork(new Work() {
				public void execute(Connection conn) throws SQLException {
					String sql = "{call PKG_TTA_PROPOSAL.P_TTA_BRANDPLNL_SAVE(?,?,?,?)}";
				//	conn.setAutoCommit(false);
					CallableStatement call = conn.prepareCall(sql);
// 设置输入参数
					call.setInt(1, (Integer) map.get("pkId"));
					call.setInt(2, (Integer) map.get("userId"));

// 设置输出参数
					call.registerOutParameter(3, Types.INTEGER);
					call.registerOutParameter(4, Types.VARCHAR);


					call.execute();
					int xFlag = call.getInt(3);
					String xMsg = call.getString(4);

					paramsMap.put("xFlag",xFlag);
					paramsMap.put("xMsg",xMsg);

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
