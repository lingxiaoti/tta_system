package com.sie.watsons.base.report.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sie.watsons.base.report.model.dao.TtaOiReportFieldMappingDAO_HI;
import com.sie.watsons.base.report.model.entities.readonly.TtaOiReportFieldHeaderEntity_HI_RO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yhg.base.utils.SToolUtils;
import com.sie.watsons.base.report.model.entities.TtaOiReportFieldMappingEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.report.model.inter.ITtaOiReportFieldMapping;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

@Component("ttaOiReportFieldMappingServer")
public class TtaOiReportFieldMappingServer extends BaseCommonServer<TtaOiReportFieldMappingEntity_HI> implements ITtaOiReportFieldMapping{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaOiReportFieldMappingServer.class);

	@Autowired
	private ViewObject<TtaOiReportFieldMappingEntity_HI> ttaOiReportFieldMappingDAO_HI;
	@Autowired
    private TtaOiReportFieldMappingDAO_HI ttaOiReportFieldMappingDAOHi;

	public TtaOiReportFieldMappingServer() {
		super();
	}

	@Override
	public int insertSceneFieldFeeTempData(String timeString,String singalFlag, String vendorNbr, String groupCode, String queryType, String groupDimensionality, String fieldName, String value, String tableName) {
		LOGGER.info("OI分摊多线程执行需要的参数,日期:{},供应商:{},部门编码:{}",timeString,vendorNbr,groupCode);
		String sql = TtaOiReportFieldHeaderEntity_HI_RO.getOiSceneFeeSql(timeString, timeString, vendorNbr, groupCode, queryType, groupDimensionality,fieldName,value,tableName);
		String insertSql = "insert into tta_scene_field_fee_temp\n" +
				"  (account_month,\n" +
				"   vendor_nbr,\n" +
				"   vender_name,\n" +
				"   group_key,\n" +
				"   group_code,\n" +
				"   group_desc,\n" +
				"   dept_code,\n" +
				"   dept_desc,\n" +
				"   brand_cn,\n" +
				"   brand_en,\n" +
				"   item_nbr,\n" +
				"   item_desc_cn,\n" +
				"   key,\n" +
				"   value,\n" +
				"   singalflag)\n" +
				"  select tl.account_month,\n" +
				"         tl.vendor_nbr,\n" +
				"         tl.supplier_name,\n" +
				"         tl.group_key,\n" +
				"         tl.group_code,\n" +
				"         tl.group_desc,\n" +
				"         tl.dept_code,\n" +
				"         tl.dept_desc,\n" +
				"         tl.brand_cn,\n" +
				"         tl.brand_en,\n" +
				"         tl.item_nbr,\n" +
				"         tl.item_desc_cn,\n" +
				"         tl.key,\n" +
				"         tl.value,\n" +
				"'" + singalFlag + "'" +
				"    from (\n" + sql + "\n) tl ";
		int inserCount = ttaOiReportFieldMappingDAO_HI.executeSqlUpdate(insertSql);
		LOGGER.info("当前线程{},成功插入到临时表tta_scene_field_fee_temp的条数为:{}",Thread.currentThread().getName(),inserCount);
		return inserCount;
	}

    @Override
    public List<Map<String, Object>> findSceneFieldFeeData(String timeString, String singalFlag, String vendorNbr, String groupCode, String queryType, String groupDimensionality, String key, String value) {
       LOGGER.info("当前线程:" + Thread.currentThread().getName());
        String sql = TtaOiReportFieldHeaderEntity_HI_RO.getOiSceneFeeDataSql(timeString, vendorNbr, groupCode, queryType, groupDimensionality,key,value);
		long startTime = System.currentTimeMillis();
		List<Map<String, Object>> mapList = ttaOiReportFieldMappingDAOHi.queryNamedSQLForList(sql, new HashMap<>());
		long endTime = System.currentTimeMillis();
		LOGGER.info("当前线程[{}],执行查询OI分摊费用花费的时间:{}S",Thread.currentThread().getName(),(endTime - startTime) / 1000);
		return mapList;
    }
}
