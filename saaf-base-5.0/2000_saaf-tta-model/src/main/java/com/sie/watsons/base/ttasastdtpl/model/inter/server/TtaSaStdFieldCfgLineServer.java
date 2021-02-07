package com.sie.watsons.base.ttasastdtpl.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.ttasastdtpl.model.entities.readonly.TtaSaStdFieldCfgLineEntity_HI_RO;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yhg.base.utils.SToolUtils;
import com.sie.watsons.base.ttasastdtpl.model.entities.TtaSaStdFieldCfgLineEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.ttasastdtpl.model.inter.ITtaSaStdFieldCfgLine;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

@Component("ttaSaStdFieldCfgLineServer")
public class TtaSaStdFieldCfgLineServer extends BaseCommonServer<TtaSaStdFieldCfgLineEntity_HI> implements ITtaSaStdFieldCfgLine{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaSaStdFieldCfgLineServer.class);

	@Autowired
	private ViewObject<TtaSaStdFieldCfgLineEntity_HI> ttaSaStdFieldCfgLineDAO_HI;

	@Autowired
	private BaseViewObject<TtaSaStdFieldCfgLineEntity_HI_RO> ttaSaStdFieldCfgLineDAO_HI_RO;

	public TtaSaStdFieldCfgLineServer() {
		super();
	}

	/**
	 * 查询TTA_SA_STD_FIELD_CFG_LINE 数据
	 * @param queryParamJSON
	 * @param pageIndex
	 * @param pageRows
	 * @return
	 */
	@Override
	public Pagination<TtaSaStdFieldCfgLineEntity_HI_RO> findTtaSaStdFieldCfgLinePagination(
			JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
		StringBuffer sql = new StringBuffer();
		sql.append(TtaSaStdFieldCfgLineEntity_HI_RO.TTA_TSSFCL_LIST);
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		SaafToolUtils.parperHbmParam(TtaSaStdFieldCfgLineEntity_HI_RO.class,
				queryParamJSON, "tssfcl.filed_name", "filedName", sql,
				paramsMap, "fulllike");
        SaafToolUtils.parperHbmParam(TtaSaStdFieldCfgLineEntity_HI_RO.class,
                queryParamJSON, "tssfcl.sa_std_tpl_def_header_id", "saStdTplDefHeaderId", sql,
                paramsMap, "=");
		SaafToolUtils.changeQuerySort(queryParamJSON, sql, "tssfcl.order_num desc",
				false);
		return ttaSaStdFieldCfgLineDAO_HI_RO.findPagination(sql,
				SaafToolUtils.getSqlCountString(sql), paramsMap, pageIndex,
				pageRows);

	}

	/**
	 * 保存
	 * @param queryParamJSON
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<TtaSaStdFieldCfgLineEntity_HI> saveOrUpdateAll(JSONObject queryParamJSON, Integer userId)
			throws Exception {
		ArrayList<TtaSaStdFieldCfgLineEntity_HI> objects = new ArrayList<>();
		JSONArray jsonList = queryParamJSON.getJSONArray("list");
		Integer id = queryParamJSON.getInteger("id");
		if (id != null) {
            for(int i = 0 ;i<jsonList.size();i++){
                JSONObject  json = (JSONObject)jsonList.get(i) ;
                TtaSaStdFieldCfgLineEntity_HI instance = SaafToolUtils.setEntity(TtaSaStdFieldCfgLineEntity_HI.class, json, ttaSaStdFieldCfgLineDAO_HI, userId);
                instance.setSaStdTplDefHeaderId(id);
                objects.add(instance);
            }
            ttaSaStdFieldCfgLineDAO_HI.saveOrUpdateAll(objects);
        }

		return objects;
	}

	@Override
	public void delete(Integer pkId) {
		if (pkId == null) {
			return;
		}
		TtaSaStdFieldCfgLineEntity_HI instance = ttaSaStdFieldCfgLineDAO_HI.getById(pkId);
		if (instance == null) {
			return;
		}
		ttaSaStdFieldCfgLineDAO_HI.delete(instance);
	}

}
