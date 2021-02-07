package com.sie.watsons.base.pon.itproject.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.pon.itproject.model.entities.readonly.EquPonItProjectAttachmentEntity_HI_RO;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yhg.base.utils.SToolUtils;
import com.sie.watsons.base.pon.itproject.model.entities.EquPonItProjectAttachmentEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.pon.itproject.model.inter.IEquPonItProjectAttachment;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

@Component("equPonItProjectAttachmentServer")
public class EquPonItProjectAttachmentServer extends BaseCommonServer<EquPonItProjectAttachmentEntity_HI> implements IEquPonItProjectAttachment{
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPonItProjectAttachmentServer.class);

	@Autowired
	private ViewObject<EquPonItProjectAttachmentEntity_HI> equPonItProjectAttachmentDAO_HI;

	@Autowired
	private BaseViewObject<EquPonItProjectAttachmentEntity_HI_RO> equPonItProjectAttachmentEntity_HI_RO;

	public EquPonItProjectAttachmentServer() {
		super();
	}

	/**
	 * IT报价管理-立项单据附件查询，分页查询
	 *
	 * @param queryParamJSON 参数：密级Entity中的字段
	 * @param pageIndex
	 * @param pageRows
	 * @return
	 */
	public JSONObject findItProjectAttachment(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {

		StringBuffer sql = new StringBuffer(EquPonItProjectAttachmentEntity_HI_RO.QUERY_SQL);
		Map<String, Object> map = new HashMap<>();
		SaafToolUtils.parperHbmParam(EquPonItProjectAttachmentEntity_HI_RO.class, queryParamJSON, sql, map);
//		if ("QuotationFixedFile".equals(queryParamJSON.getString("fileType"))){
//			sql.append(" order by t.attachment_id asc");
//		}
//		if ("NonPriceSelectFile".equals(queryParamJSON.getString("fileType"))){
//			sql.append(" order by t.source_id asc");
//		}
		sql.append(" order by t.attachment_id asc");
		Pagination<EquPonItProjectAttachmentEntity_HI_RO> pagination = equPonItProjectAttachmentEntity_HI_RO.findPagination(sql, map, pageIndex, pageRows);
		return JSONObject.parseObject(JSONObject.toJSONString(pagination));
	}

	/**
	 * IT报价管理-立项单据附件删除
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public void deleteItProjectAttachment(JSONObject params) throws Exception {
		this.deleteById(params.getInteger("id"));
	}
}
