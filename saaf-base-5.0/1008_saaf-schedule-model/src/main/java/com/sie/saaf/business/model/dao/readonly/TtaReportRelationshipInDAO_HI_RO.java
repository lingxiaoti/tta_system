package com.sie.saaf.business.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.saaf.business.model.entities.readonly.TtaReportRelationshipInEntity_HI_RO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.annotation.Transactional;

import com.mysql.jdbc.Statement;
import com.sie.saaf.schedule.utils.SaafToolUtils;

@Component("ttaReportRelationshipInDAO_HI_RO")
public class TtaReportRelationshipInDAO_HI_RO extends DynamicViewObjectImpl<TtaReportRelationshipInEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaReportRelationshipInDAO_HI_RO.class);



/*	final public  Session getSession() {
		return sessionFactory.getCurrentSession();
	}*/

	public TtaReportRelationshipInDAO_HI_RO() {
		super();
	}



}
