package com.sie.saaf.business.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.saaf.business.model.entities.TtaReportRelationshipInEntity_HI;
import com.sie.saaf.common.util.EscColumnToBean;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("ttaReportRelationshipInDAO_HI")
public class TtaReportRelationshipInDAO_HI extends BaseCommonDAO_HI<TtaReportRelationshipInEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaReportRelationshipInDAO_HI.class);

	public TtaReportRelationshipInDAO_HI() {

		super();
	}
}
