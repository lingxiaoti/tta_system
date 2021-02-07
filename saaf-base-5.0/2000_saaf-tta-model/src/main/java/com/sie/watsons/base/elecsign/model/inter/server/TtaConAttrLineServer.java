package com.sie.watsons.base.elecsign.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sie.saaf.common.model.inter.IBaseAttachment;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.elecsign.model.entities.TtaConAttrLineEntity_HI;
import com.sie.watsons.base.elecsign.model.entities.readonly.TtaConAttrLineEntity_HI_RO;
import com.sie.watsons.base.elecsign.model.inter.ITtaConAttrLine;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

@Component("ttaProposalConAttrLineServer")
public class TtaConAttrLineServer extends BaseCommonServer<TtaConAttrLineEntity_HI> implements ITtaConAttrLine {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaConAttrLineServer.class);

	@Autowired
	private ViewObject<TtaConAttrLineEntity_HI> ttaProposalConAttrLineDAO_HI;

	@Autowired
	private BaseViewObject<TtaConAttrLineEntity_HI_RO> ttaProposalConAttrLineDAO_HI_RO;

	@Autowired
	private IBaseAttachment baseAttachmentServer;

	public TtaConAttrLineServer() {
		super();
	}

	@Override
	public Pagination<TtaConAttrLineEntity_HI_RO> findTtaProposalConAttrLinePagination(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
		Map<String, Object> queryParamMap = new HashMap<>();
		StringBuffer querySQL = new StringBuffer(TtaConAttrLineEntity_HI_RO.QUERY_CONTRACT_LIST);
		SaafToolUtils.parperHbmParam(TtaConAttrLineEntity_HI_RO.class, queryParamJSON, "tcal.org_code", "orgCode", querySQL, queryParamMap, "fulllike");
		SaafToolUtils.parperHbmParam(TtaConAttrLineEntity_HI_RO.class, queryParamJSON, "tcal.org_name", "orgName", querySQL, queryParamMap, "fulllike");
		SaafToolUtils.parperHbmParam(TtaConAttrLineEntity_HI_RO.class, queryParamJSON, "tcal.dept_code", "deptCode", querySQL, queryParamMap, "fulllike");
		SaafToolUtils.parperHbmParam(TtaConAttrLineEntity_HI_RO.class, queryParamJSON, "tcal.dept_name", "depName", querySQL, queryParamMap, "fulllike");
		querySQL.append(" order by tcal.con_attr_line_id ");
		StringBuffer countSql = SaafToolUtils.getSimpleSqlCountString(querySQL,"count(*)");
		return ttaProposalConAttrLineDAO_HI_RO.findPagination(querySQL,countSql, queryParamMap, pageIndex, pageRows);
	}

	@Override
	public Integer findcount(JSONObject queryParamJSON) {
		HashMap hashMap = new HashMap();
		StringBuffer countSql = new StringBuffer("select count(*) from TtaConAttrLineEntity_HI tc " +
				"where tc.orgCode = :orgCode and tc.deptCode = :deptCode and tc.fileType = :deptFileType and tc.conYear = :conYear");
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("orgCode",queryParamJSON.getString("orgCode")) ;
		map.put("deptCode",queryParamJSON.getString("deptCode")) ;
        map.put("deptFileType",queryParamJSON.getString("deptFileType")) ;
        map.put("conYear",queryParamJSON.getInteger("conYear")) ;
		Integer count = ttaProposalConAttrLineDAO_HI.count(countSql, map);

		return count ;
	}

	/**
	 *
	 * @param queryParamJSON
	 * @return
	 * @throws Exception
	 */
	@Override
	public JSONObject deleteInfo(JSONObject queryParamJSON) throws Exception{
		JSONObject result = new JSONObject();
		if(queryParamJSON.getInteger("conAttrLineId")!=null || !"".equals(queryParamJSON.getInteger("conAttrLineId"))){
			ttaProposalConAttrLineDAO_HI.delete(queryParamJSON.getInteger("conAttrLineId"));
		}
		return result;
	}

	/**
	 *
	 * @param queryParamJSON
	 * @return
	 * @throws Exception
	 */
	@Override
	public JSONObject findFileIds(JSONObject queryParamJSON) throws Exception{
		JSONObject result = new JSONObject();
		String baseAttachmentFileIds = baseAttachmentServer.findBaseAttachmentFileIds(queryParamJSON.getLong("businessId"), queryParamJSON.getString("functionId"));
		result.put("fileIds",baseAttachmentFileIds);
		return result;
	}

	/**
	 *
	 * @param fileId
	 * @throws Exception
	 */
	@Override
	public void deleteByFileId(Long fileId) throws Exception{
		JSONObject result = new JSONObject();
		if(fileId!=null){
			ttaProposalConAttrLineDAO_HI.executeSqlUpdate("delete from TTA_CON_ATTR_LINE t where t.FILE_ID =" + fileId);

		}
	}

}
