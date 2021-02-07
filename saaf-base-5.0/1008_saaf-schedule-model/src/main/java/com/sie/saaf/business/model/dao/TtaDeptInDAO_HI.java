package com.sie.saaf.business.model.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import org.hibernate.Session;
import org.hibernate.jdbc.Work;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sie.saaf.business.model.entities.TtaDeptInEntity_HI;
import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;

@Component("ttaDeptInDAO_HI")
public class TtaDeptInDAO_HI extends BaseCommonDAO_HI<TtaDeptInEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaDeptInDAO_HI.class);

	public TtaDeptInDAO_HI() {
		super();
	}

	
	//行信息保存
		@SuppressWarnings("all")
		public void callProBaseDeptPositionAssign() {
			try {
				Session session = this.getSessionFactory().getCurrentSession();
				session.doWork(new Work() {
					public void execute(Connection conn) throws SQLException {
						String sql = "{call pro_base_dept_position_assign()}";
						//	conn.setAutoCommit(false);
						CallableStatement call = conn.prepareCall(sql);
						call.execute();
						close(call);
					}
				});
			} catch (Exception e) {
				LOGGER.error(".pro_base_dept_position_assign 调用存储过程异常:{}", e);
			}
		}

		private void close(CallableStatement cs) {
			try {
				if (cs != null) {
					cs.close();
				}

			} catch (Exception e) {
				LOGGER.error(".close  pro_base_dept_position_assign 异常:{}", e);
			}
		}
		
}
