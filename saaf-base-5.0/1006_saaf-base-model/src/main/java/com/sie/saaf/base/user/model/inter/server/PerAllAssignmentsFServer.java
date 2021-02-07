package com.sie.saaf.base.user.model.inter.server;

import com.sie.saaf.base.user.model.entities.PerAllAssignmentsFEntity_HI;
import com.sie.saaf.base.user.model.inter.IPerAllAssignmentsF;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.yhg.hibernate.core.dao.ViewObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("perAllAssignmentsFServer")
public class PerAllAssignmentsFServer extends BaseCommonServer<PerAllAssignmentsFEntity_HI> implements IPerAllAssignmentsF {
//	private static final Logger LOGGER = LoggerFactory.getLogger(PerAllAssignmentsFServer.class);
//	@Autowired
//	private ViewObject<PerAllAssignmentsFEntity_HI> perAllAssignmentsFDAO_HI;
//	public PerAllAssignmentsFServer() {
//		super();
//	}
}
