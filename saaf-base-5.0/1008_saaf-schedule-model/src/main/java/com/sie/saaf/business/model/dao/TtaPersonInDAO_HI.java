package com.sie.saaf.business.model.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import org.hibernate.Session;
import org.hibernate.jdbc.Work;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sie.saaf.business.model.entities.TtaPersonInEntity_HI;
import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;

@Component("ttaPersonInDAO_HI")
public class TtaPersonInDAO_HI extends BaseCommonDAO_HI<TtaPersonInEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaPersonInDAO_HI.class);

	public TtaPersonInDAO_HI() {
		super();
	}

	//行信息保存 PRO_PERSON_IN,PRO_BASE_JOB
	@SuppressWarnings("all")
	public void callProBasePersonJob() {
		try {
			Session session = this.getSessionFactory().getCurrentSession();
			session.doWork(new Work() {
				public void execute(Connection conn) throws SQLException {
					String sql = "{call PRO_BASE_PERSON_JOB()}";
					//	conn.setAutoCommit(false);
					CallableStatement call = conn.prepareCall(sql);
					call.execute();
					close(call);
				}
			});
		} catch (Exception e) {
			LOGGER.error(".callProPersonIn 调用存储过程异常:{}", e);
		}
	}

	private void close(CallableStatement cs) {
		try {
			if (cs != null) {
				cs.close();
			}

		} catch (Exception e) {
			LOGGER.error(".close  CallableStatement 异常:{}", e);
		}
	}
}
