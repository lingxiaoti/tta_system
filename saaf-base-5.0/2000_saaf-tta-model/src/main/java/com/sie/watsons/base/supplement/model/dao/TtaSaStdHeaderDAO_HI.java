package com.sie.watsons.base.supplement.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.supplement.model.entities.TtaSaStdHeaderEntity_HI;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("ttaSaStdHeaderDAO_HI")
public class TtaSaStdHeaderDAO_HI extends BaseCommonDAO_HI<TtaSaStdHeaderEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaSaStdHeaderDAO_HI.class);

	public TtaSaStdHeaderDAO_HI() {
		super();
	}

	//补充协议标准变更
	public Map<String, Object> callSupplementAgreementStandardChangStatus(Map<String, Object> map) throws Exception{
		final Map<String, Object> paramsMap = new HashMap<String, Object>();
		try {
			Session session = this.getSessionFactory().getCurrentSession();
			session.doWork(new Work() {
				public void execute(Connection conn) throws SQLException {
					String sql = "{call P_TTA_SUPPLEMENT_AGREMENT_COPY(?,?,?,?,?)}";
					conn.setAutoCommit(false);
					CallableStatement call = conn.prepareCall(sql);
					// 设置输入参数
					call.setInt(1, (Integer) map.get("pkId"));//saStdHeaderId
					call.setInt(2, (Integer) map.get("userId"));
					// 设置输出参数
					call.registerOutParameter(3, Types.INTEGER);
					call.registerOutParameter(4, Types.VARCHAR);
					call.registerOutParameter(5, Types.INTEGER);

					call.execute();
					int xFlag = call.getInt(3);
					String xMsg = call.getString(4);
					int retrunSaStdHeaderId = call.getInt(5);//返回的id
					paramsMap.put("xFlag",xFlag);
					paramsMap.put("xMsg",xMsg);
					paramsMap.put("saStdHeaderId",retrunSaStdHeaderId);
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
