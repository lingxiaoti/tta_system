package com.sie.watsons.base.ttasastdtpl.model.inter.server;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.*;

import com.sie.saaf.common.model.inter.server.GenerateCodeServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.ttasastdtpl.model.entities.readonly.TtaSaStdTplFieldCfgEntity_HI_RO;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.sie.watsons.base.ttasastdtpl.model.entities.TtaSaStdTplFieldCfgEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.ttasastdtpl.model.inter.ITtaSaStdTplFieldCfg;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

@Component("ttaSaStdTplFieldCfgServer")
public class TtaSaStdTplFieldCfgServer extends BaseCommonServer<TtaSaStdTplFieldCfgEntity_HI> implements ITtaSaStdTplFieldCfg{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaSaStdTplFieldCfgServer.class);

	@Autowired
	private ViewObject<TtaSaStdTplFieldCfgEntity_HI> ttaSaStdTplFieldCfgDAO_HI;

	@Autowired
	private BaseViewObject<TtaSaStdTplFieldCfgEntity_HI_RO> ttaSaStdTplFieldCfgDAO_HI_RO;

	@Autowired
	private GenerateCodeServer generateCodeServer;

	public TtaSaStdTplFieldCfgServer() {
		super();
	}

	/**
	 * 查询TTA_SA_STD_TPL_FIELD_CFG 数据
	 * @param queryParamJSON
	 * @param pageIndex
	 * @param pageRows
	 * @return
	 */
	@Override
	public Pagination<TtaSaStdTplFieldCfgEntity_HI_RO> findTtaSaStdTplFieldCfgPagination(
			JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
		StringBuffer sql = new StringBuffer();
		sql.append(TtaSaStdTplFieldCfgEntity_HI_RO.TTA_TSSTF_LIST);
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		SaafToolUtils.parperHbmParam(TtaSaStdTplFieldCfgEntity_HI_RO.class,
				queryParamJSON, "tsstf.filed_name", "filedName", sql,
				paramsMap, "fulllike");
		SaafToolUtils.parperHbmParam(TtaSaStdTplFieldCfgEntity_HI_RO.class,
				queryParamJSON, "tsstf.is_enable", "isEnable", sql,
				paramsMap, "=");
		SaafToolUtils.changeQuerySort(queryParamJSON, sql, "tsstf.order_num desc",
				false);
		return ttaSaStdTplFieldCfgDAO_HI_RO.findPagination(sql,
				SaafToolUtils.getSqlCountString(sql), paramsMap, pageIndex,
				pageRows);

	}

	/**
	 * 保存 TTA_SA_STD_TPL_FIELD_CFG 数据
	 * @param queryParamJSON
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<TtaSaStdTplFieldCfgEntity_HI> saveOrUpdateAll(JSONObject queryParamJSON, Integer userId)
			throws Exception {
		ArrayList<TtaSaStdTplFieldCfgEntity_HI> objects = new ArrayList<>();
		JSONArray jsonList = queryParamJSON.getJSONArray("list");
		for(int i = 0 ;i<jsonList.size();i++){
			JSONObject  json = (JSONObject)jsonList.get(i) ;
			TtaSaStdTplFieldCfgEntity_HI instance = SaafToolUtils.setEntity(TtaSaStdTplFieldCfgEntity_HI.class, json, ttaSaStdTplFieldCfgDAO_HI, userId);
			if (SaafToolUtils.isNullOrEmpty(instance.getTplCode())) {
				String instanceKey= "tta_sa_std_tpl_field_cfg" ;
				String instanceKey2= "tta_sa_std_tpl_field_cfg2" ;
				int generateId = generateCodeServer.getGenerateId(instanceKey);
				int generateId2 = generateCodeServer.getGenerateId(instanceKey2);
				String clauseCodePre = "#{FIELD" + String.format("%0" + 4 + "d", generateId) + "}";
				instance.setTplCode(clauseCodePre);
				instance.setOrderNum(generateId2);
			}
			objects.add(instance);
		}
		ttaSaStdTplFieldCfgDAO_HI.saveOrUpdateAll(objects);
		return objects;
	}

}
