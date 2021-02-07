package com.sie.watsons.base.pon.project.model.inter.server;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.pon.project.model.entities.EquPonProjectAttachmentEntity_HI;
import com.sie.watsons.base.pon.project.model.entities.readonly.EquPonProjectAttachmentEntity_HI_RO;
import com.sie.watsons.base.pon.project.model.inter.IEquPonProjectAttachment;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component("equPonProjectAttachmentServer")
public class EquPonProjectAttachmentServer extends BaseCommonServer<EquPonProjectAttachmentEntity_HI> implements IEquPonProjectAttachment{
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPonProjectAttachmentServer.class);

	@Autowired
	private ViewObject<EquPonProjectAttachmentEntity_HI> equPonProjectAttachmentDAO_HI;

	@Autowired
	private BaseViewObject<EquPonProjectAttachmentEntity_HI_RO> equPonProjectAttachmentEntity_HI_RO;

	public EquPonProjectAttachmentServer() {
		super();
	}

	/**
	 * 报价管理-立项单据附件查询，分页查询
	 *
	 * @param queryParamJSON 参数：密级Entity中的字段
	 * @param pageIndex
	 * @param pageRows
	 * @return
	 */
	public JSONObject findProjectAttachment(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {

		StringBuffer sql = new StringBuffer(EquPonProjectAttachmentEntity_HI_RO.QUERY_SQL);
		Map<String, Object> map = new HashMap<>();
		SaafToolUtils.parperHbmParam(EquPonProjectAttachmentEntity_HI_RO.class, queryParamJSON, sql, map);
		if ("FixedFile".equals(queryParamJSON.getString("fileType"))){
            sql.append(" order by t.attachment_id asc");
        }
        if ("NonPriceSelectFile".equals(queryParamJSON.getString("fileType"))){
            sql.append(" order by t.source_id asc");
        }
		Pagination<EquPonProjectAttachmentEntity_HI_RO> pagination = equPonProjectAttachmentEntity_HI_RO.findPagination(sql, map, pageIndex, pageRows);
		return JSONObject.parseObject(JSONObject.toJSONString(pagination));
	}

	/**
	 * 报价管理-立项单据附件存
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public EquPonProjectAttachmentEntity_HI saveProjectAttachment(JSONObject params) throws Exception {
		return this.saveOrUpdate(params);
	}

	/**
	 * 报价管理-立项单据附件删除
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public void deleteProjectAttachment(JSONObject params) throws Exception {
		this.deleteById(params.getInteger("id"));
	}
}
