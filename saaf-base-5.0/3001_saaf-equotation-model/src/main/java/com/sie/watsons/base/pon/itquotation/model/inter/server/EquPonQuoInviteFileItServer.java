package com.sie.watsons.base.pon.itquotation.model.inter.server;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.pon.itquotation.model.entities.EquPonQuoInviteFileItEntity_HI;
import com.sie.watsons.base.pon.itquotation.model.entities.readonly.EquPonQuoInviteFileItEntity_HI_RO;
import com.sie.watsons.base.pon.itquotation.model.inter.IEquPonQuoInviteFileIt;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("equPonQuoInviteFileItServer")
public class EquPonQuoInviteFileItServer extends BaseCommonServer<EquPonQuoInviteFileItEntity_HI> implements IEquPonQuoInviteFileIt{
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPonQuoInviteFileItServer.class);
	@Autowired
	private ViewObject<EquPonQuoInviteFileItEntity_HI> equPonQuoInviteFileItDAO_HI;
    @Autowired
    private BaseViewObject<EquPonQuoInviteFileItEntity_HI_RO> equPonQuoInviteFileItDAO_HI_RO;
	public EquPonQuoInviteFileItServer() {
		super();
	}

    @Override
    public List<EquPonQuoInviteFileItEntity_HI_RO> findQuoInviteFileIt(JSONObject jsonObject) {

	    SaafToolUtils.validateJsonParms(jsonObject,"fileType","quotationId");
        StringBuffer sb = new StringBuffer();
        sb.append(EquPonQuoInviteFileItEntity_HI_RO.QUERY_SELECT_SQL);
        Map<String, Object> map = new HashMap<String, Object>(4);
        SaafToolUtils.parperParam(jsonObject, "fi.quotation_id", "quotationId", sb, map, "=");
        SaafToolUtils.parperParam(jsonObject, "fi.file_type", "fileType", sb, map, "=");
        sb.append(" order by fi.attachment_id asc");
        List<EquPonQuoInviteFileItEntity_HI_RO> list = equPonQuoInviteFileItDAO_HI_RO.findList(sb, map);
        return list;
    }
}
