package com.sie.watsons.base.pon.quotation.model.inter.server;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.pon.quotation.model.entities.EquPonQuotationNopriceDocEntity_HI;
import com.sie.watsons.base.pon.quotation.model.entities.readonly.EquPonQuotationNopriceDocEntity_HI_RO;
import com.sie.watsons.base.pon.quotation.model.inter.IEquPonQuotationNopriceDoc;
import com.yhg.hibernate.core.dao.BaseViewObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("equPonQuotationNopriceDocServer")
public class EquPonQuotationNopriceDocServer extends BaseCommonServer<EquPonQuotationNopriceDocEntity_HI> implements IEquPonQuotationNopriceDoc{
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPonQuotationNopriceDocServer.class);
    @Autowired
    private BaseViewObject<EquPonQuotationNopriceDocEntity_HI_RO> equPonQuotationNopriceDocDAO_HI_RO;

	public EquPonQuotationNopriceDocServer() {
		super();
	}

    @Override
    public List<EquPonQuotationNopriceDocEntity_HI_RO> findQuotationNopriceInfo(JSONObject jsonObject) {

	    // 针对非价格选择文件
        StringBuffer sql = null;
	    if("NonPriceSelectFile".equals(jsonObject.getString("fileType"))){
            sql = new StringBuffer(EquPonQuotationNopriceDocEntity_HI_RO.SELECT_NOPRICE_SELECT_SQL);
        }
        // 针对非价格文件
        if("NonPriceFile".equals(jsonObject.getString("fileType"))){
            sql = new StringBuffer(EquPonQuotationNopriceDocEntity_HI_RO.SELECT_NOPRICE_SQL);
        }
        Map<String, Object> map = new HashMap<String, Object>(16);
        // 根据头id查询头数据
        SaafToolUtils.parperParam(jsonObject, "nd.quotation_id", "quotationId", sql, map, "=");
        System.out.println(sql.toString());
        sql.append(" order by pa.file_id asc");
        return equPonQuotationNopriceDocDAO_HI_RO.findList(sql, map);
    }
}
