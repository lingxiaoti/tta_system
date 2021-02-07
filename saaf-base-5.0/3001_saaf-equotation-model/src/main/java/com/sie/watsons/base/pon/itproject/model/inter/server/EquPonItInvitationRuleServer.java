package com.sie.watsons.base.pon.itproject.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.pon.itproject.model.entities.readonly.EquPonItInvitationRuleEntity_HI_RO;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yhg.base.utils.SToolUtils;
import com.sie.watsons.base.pon.itproject.model.entities.EquPonItInvitationRuleEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.pon.itproject.model.inter.IEquPonItInvitationRule;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

@Component("equPonItInvitationRuleServer")
public class EquPonItInvitationRuleServer extends BaseCommonServer<EquPonItInvitationRuleEntity_HI> implements IEquPonItInvitationRule{
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPonItInvitationRuleServer.class);

	@Autowired
	private ViewObject<EquPonItInvitationRuleEntity_HI> equPonItInvitationRuleDAO_HI;

	@Autowired
	private BaseViewObject<EquPonItInvitationRuleEntity_HI_RO> equPonItInvitationRuleEntity_HI_RO;

	public EquPonItInvitationRuleServer() {
		super();
	}

	/**
	 * IT报价管理-邀请细则查询，分页查询
	 *
	 * @param queryParamJSON 参数：密级Entity中的字段
	 * @param pageIndex
	 * @param pageRows
	 * @return
	 */
	public JSONObject findItInvitationRule(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {

		StringBuffer sql = new StringBuffer(EquPonItInvitationRuleEntity_HI_RO.QUERY_SQL);
		Map<String, Object> map = new HashMap<>();
		SaafToolUtils.parperHbmParam(EquPonItInvitationRuleEntity_HI_RO.class, queryParamJSON, sql, map);
		Pagination<EquPonItInvitationRuleEntity_HI_RO> pagination = equPonItInvitationRuleEntity_HI_RO.findPagination(sql, map, pageIndex, pageRows);
		return JSONObject.parseObject(JSONObject.toJSONString(pagination));
	}

	/**
	 * IT报价管理-邀请细则删除
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public void deleteItInvitationRule(JSONObject params) throws Exception {
		this.deleteById(params.getInteger("id"));
	}
}
