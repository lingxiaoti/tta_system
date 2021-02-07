package com.sie.watsons.base.ttasastdtpl.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sie.saaf.common.bean.UserSessionBean;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.ttasastdtpl.model.entities.readonly.TtaSaStdTplDefHeaderEntity_HI_RO;
import com.yhg.hibernate.core.dao.BaseViewObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yhg.base.utils.SToolUtils;
import com.sie.watsons.base.ttasastdtpl.model.entities.TtaSaStdTplDefHeaderEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.ttasastdtpl.model.inter.ITtaSaStdTplDefHeader;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

@Component("ttaSaStdTplDefHeaderServer")
public class TtaSaStdTplDefHeaderServer extends BaseCommonServer<TtaSaStdTplDefHeaderEntity_HI> implements ITtaSaStdTplDefHeader{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaSaStdTplDefHeaderServer.class);

	@Autowired
	private ViewObject<TtaSaStdTplDefHeaderEntity_HI> ttaSaStdTplDefHeaderDAO_HI;

	@Autowired
	private BaseViewObject<TtaSaStdTplDefHeaderEntity_HI_RO> ttaSaStdTplDefHeaderDAO_HI_RO;

	public TtaSaStdTplDefHeaderServer() {
		super();
	}

	/**
	 * 查询ztree
	 * @param queryParamJSON
	 * @return
	 */
	@Override
	public List<TtaSaStdTplDefHeaderEntity_HI_RO> findTtaSaStdTplDefHeaderTree(JSONObject queryParamJSON) {
		Map<String, Object> queryParamMap = new HashMap<>();
		StringBuffer querySql = new StringBuffer(TtaSaStdTplDefHeaderEntity_HI_RO.QUERY_TREE_LIST);
		querySql.append("  order by to_number(cur_ts.tpl_code) asc");
		return ttaSaStdTplDefHeaderDAO_HI_RO.findList(querySql, queryParamMap);
	}

	@Override
	public TtaSaStdTplDefHeaderEntity_HI saveOrUpdate(JSONObject paramsJSON, UserSessionBean userSessionBean, int userId) throws Exception {

		TtaSaStdTplDefHeaderEntity_HI instance = SaafToolUtils.setEntity(TtaSaStdTplDefHeaderEntity_HI.class, paramsJSON, ttaSaStdTplDefHeaderDAO_HI, userId);
		if(SaafToolUtils.isNullOrEmpty(instance.getSaStdTplDefHeaderId())){
			instance.setOrgCode(userSessionBean.getDeptCode());
			instance.setOrgName(userSessionBean.getDeptName());
			instance.setDeptCode(userSessionBean.getGroupCode());
			instance.setDeptName(userSessionBean.getGroupName());
			Integer saStdTplDefHeaderId = paramsJSON.getInteger("saStdTplDefHeaderId");
			String tplCode = paramsJSON.getString("tplCode");
			Map<String,Object> map=new HashMap<>();
			map.put("saStdTplDefHeaderId",saStdTplDefHeaderId);
			map.put("tplCode",tplCode);
			List<TtaSaStdTplDefHeaderEntity_HI> collectionList= ttaSaStdTplDefHeaderDAO_HI.findByProperty(map);
			if (collectionList!=null&&collectionList.size()>0) {
				throw new IllegalArgumentException("编号重复!请修改!");
			};
		}

		ttaSaStdTplDefHeaderDAO_HI.saveOrUpdate(instance);
		return instance;
	}

	@Override
	public void delete(Integer pkId) {
		if (pkId == null) {
			return;
		}
		TtaSaStdTplDefHeaderEntity_HI instance = ttaSaStdTplDefHeaderDAO_HI.getById(pkId);
		if (instance == null) {
			return;
		}
		ttaSaStdTplDefHeaderDAO_HI.delete(instance);
	}

}
