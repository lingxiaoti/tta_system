package com.sie.watsons.base.pon.project.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.pon.project.model.entities.readonly.EquPonProjectAttachmentEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yhg.base.utils.SToolUtils;
import com.sie.watsons.base.pon.project.model.entities.EquPonProjectChangeCauseEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.pon.project.model.inter.IEquPonProjectChangeCause;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

@Component("equPonProjectChangeCauseServer")
public class EquPonProjectChangeCauseServer extends BaseCommonServer<EquPonProjectChangeCauseEntity_HI> implements IEquPonProjectChangeCause{
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPonProjectChangeCauseServer.class);

	@Autowired
	private ViewObject<EquPonProjectChangeCauseEntity_HI> equPonProjectChangeCauseDAO_HI;

	public EquPonProjectChangeCauseServer() {
		super();
	}

	@Override
	public JSONObject findProjectChangeCause(JSONObject paramsJONS, Integer pageIndex, Integer pageRows) {
		pageRows = 99999;
		StringBuffer sql = new StringBuffer("from EquPonProjectChangeCauseEntity_HI where sourceProjectNumber = :sourceProjectNumber order by changeCauseId");
		Map<String, Object> map = new HashMap<>();
		map.put("sourceProjectNumber",paramsJONS.getString("sourceProjectNumber"));
		Pagination<EquPonProjectChangeCauseEntity_HI> pagination = equPonProjectChangeCauseDAO_HI.findPagination(sql, map, pageIndex, pageRows);
		return JSONObject.parseObject(JSONObject.toJSONString(pagination));

	}

	/**
	 * 报价管理-立项变更历史删除
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public void deleteChangeCause(JSONObject params) throws Exception {
		this.deleteById(params.getInteger("id"));
	}
}
