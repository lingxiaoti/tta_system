package com.sie.watsons.base.plmBase.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.plmBase.model.entities.PlmMotherCompanyEntity_HI;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

@Component("plmMotherCompanyDAO_HI")
public class PlmMotherCompanyDAO_HI extends BaseCommonDAO_HI<PlmMotherCompanyEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmMotherCompanyDAO_HI.class);
	public PlmMotherCompanyDAO_HI() {
		super();
	}

	@Override
	public Object save(PlmMotherCompanyEntity_HI entity) {
		return super.save(entity);
	}


	//中英文品牌同步 调用存储过程
	public Map<String, Object> callPkgSyncMotherCompany() throws Exception {
		final Map<String, Object> paramsMap = new HashMap<String, Object>();
		try {
			Session session = this.getSessionFactory().getCurrentSession();
			session.doWork(new Work() {
				public void execute(Connection conn) throws SQLException {
					String sql = "{call PLM_UDA_PKG.SYNC_MOTHER_COMPANY(?,?)}";
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
