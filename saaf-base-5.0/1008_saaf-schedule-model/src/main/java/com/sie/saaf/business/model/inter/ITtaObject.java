package com.sie.saaf.business.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.business.model.dao.readonly.TtaElectEntity_RO;
import com.sie.saaf.business.model.dao.readonly.TtaExclusiveHeaderEntity_RO;
import com.sie.saaf.common.model.inter.IBaseCommon;

import java.io.BufferedInputStream;
import java.util.List;
import java.util.Map;

public interface ITtaObject extends IBaseCommon<Object>{

	public void saveJdbcBatchObject(final String tableName, List<Map<String,Object>> list);
	
	/**
	 *  更新采购数据
	 * @param year
	 */
	public void updateTtaPurchase(String year);
	/**
	 *  将接口中的物料信息推送到物料表并更新物料表，关联：ITEM_NBR、VENDOR_NBR、UPC
	 */
	public void callProUpdateTtaItem();
	
	/**
	 * 将接口中的供应商信息推送到供应商表并更新供应商表,关联：supplier_code
	 */
	public void callProUpdateTtaSupplier();
	
	/**
	 * 功能描述： 跑销售或者采购数据
	 * @param
	 * @return 
	 */
	public void saveSalePurchaseJob(String currentYear, String currentYearMonth, String lastYear, String lastYearMonth);


	public List<Map<String, Object>> queryNamedSQLForList(String sql, Map<String, Object> queryMap);

	public void saveBatchTtaSalesByDay(BufferedInputStream in, String yearMonth);

	public void saveBatchTtaPurchaseByDay(BufferedInputStream in);

	public void saveBatchDataList(String table, BufferedInputStream bis, String separator, int rownumber);

	/**
	 * 功能描述：创建表
	 */
	public void saveCreateTable(String tableName, String sourceTableName);

	/**
	 * 功能描述： flag 1:truncate 0:drop
	 */
	public void saveDropOrTruncateTable(String tableName, int flag);

	public  void saveCreateSaleIdx(String tableName, String... cols);
	
	public long findSaleByDayCheck();

	/**
	 * 调度电子签章详情信息
	 */
	public void saveElectStauts(TtaElectEntity_RO entity);

	void saveExclusiveProposalData(TtaExclusiveHeaderEntity_RO entity);

	JSONObject sendMailByAllScheduler(String msgContent);
}
