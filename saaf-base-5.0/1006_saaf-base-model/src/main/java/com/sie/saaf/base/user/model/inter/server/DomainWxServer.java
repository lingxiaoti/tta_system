package com.sie.saaf.base.user.model.inter.server;

import com.sie.saaf.base.user.model.entities.DomainWxEntity_HI;
import com.sie.saaf.base.user.model.inter.IDomainWx;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.yhg.hibernate.core.dao.ViewObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("domainWxServer")
public class DomainWxServer extends BaseCommonServer<DomainWxEntity_HI> implements IDomainWx {
//	private static final Logger LOGGER = LoggerFactory.getLogger(DomainWxServer.class);
	@Autowired
	private ViewObject<DomainWxEntity_HI> domainWxDAO_HI;
	public DomainWxServer() {
		super();
	}


	/**
	 * 通过域名查询数据
	 * @param domain
	 * @return
	 */
	@Override
	public DomainWxEntity_HI findByDomain(String domain){
		if (StringUtils.isBlank(domain))
			return null;
		if (!domain.startsWith("http://")  && ! domain.startsWith("https://"))
			domain="http://"+domain;
		List<DomainWxEntity_HI> list=domainWxDAO_HI.findByProperty("domain",domain);
		if (list.size()>0)
			return list.get(0);
		return null;
	}


}
