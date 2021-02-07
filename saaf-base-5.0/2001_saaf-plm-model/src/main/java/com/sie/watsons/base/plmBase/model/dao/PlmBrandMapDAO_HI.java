package com.sie.watsons.base.plmBase.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.plmBase.model.entities.PlmBrandMapEntity_HI;
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

@Component("plmBrandMapDAO_HI")
public class PlmBrandMapDAO_HI extends BaseCommonDAO_HI<PlmBrandMapEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmBrandMapDAO_HI.class);

	public PlmBrandMapDAO_HI() {
		super();
	}

	//中英文品牌同步 调用存储过程
	public Map<String, Object> callPkgSyncCnEnBrand() throws Exception {
		final Map<String, Object> paramsMap = new HashMap<String, Object>();
		try {
			Session session = this.getSessionFactory().getCurrentSession();
			session.doWork(new Work() {
				public void execute(Connection conn) throws SQLException {
					String sql = "{call PLM_UDA_PKG.SYNC_CN_EN_BRAND(?,?)}";
					conn.setAutoCommit(false);
					CallableStatement call = conn.prepareCall(sql);
					// 设置输入参数
//					call.setString(1,  map.get("eco_id").toString());
					// 设置输出参数
					call.registerOutParameter(1, Types.VARCHAR);
					call.registerOutParameter(2, Types.VARCHAR);
					call.execute();
					String x_status  = call.getString(1);
					String x_msg = call.getString(2);
					paramsMap.put("x_status",x_status);
					paramsMap.put("x_msg",x_msg);
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
