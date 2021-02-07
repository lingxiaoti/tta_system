package com.sie.saaf.business.model.inter.server;

import com.sie.saaf.business.model.dao.TtaOiSpliteDAO_HI;
import com.sie.saaf.business.model.entities.readonly.TtaOiSalesSceneEntity_HI_RO;
import com.sie.saaf.business.model.inter.ITtaOiSplite;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.util.SaafDateUtils;
import com.sie.saaf.schedule.utils.SaafToolUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("ttaOiSpliteServer")
public class TtaOiSpliteServer extends BaseCommonServer<HashMap> implements ITtaOiSplite {
	@Autowired
	private TtaObjectServer ttaObjectServer;

	private static final Logger LOGGER = LoggerFactory.getLogger(TtaOiSpliteServer.class);

	@Autowired
	private TtaOiSpliteDAO_HI ttaOiSpliteDao;

	public TtaOiSpliteServer() {
		super();
	}

	/**
	 * 功能描述： OI分摊 场景一：SALES占比非purchase_type计算，tta_oi_summary_line
	 */
	@Override
	public void saveOiSaleScene(String yearMonth) {
		Long startTime = System.currentTimeMillis();
		String year = yearMonth.substring(0,4);
		LOGGER.info("#################OI分摊 场景一：SALES占比非purchase_type计算开始执行,入参数信息yearMonth:{},开始执行时间:{}", yearMonth, new Date().toLocaleString());
		HashMap<String, Object> queryMap = new HashMap<>();
		queryMap.put("yearMonth", yearMonth);
		List<Map<String, Object>> list = ttaOiSpliteDao.queryNamedSQLForList(TtaOiSalesSceneEntity_HI_RO.CHECK_TTA_OI_SALES_SCENE_BASE_YTD_SQL, queryMap);
		if (list != null && !list.isEmpty() && Integer.parseInt(list.get(0).get("CNT") + "") > 0) {
			LOGGER.info("OI分摊 场景一,tta_oi_sales_scene_base_ytd 表已经有数据，调度终止。");
			return;
		}
		String oiSalesSceneOne = TtaOiSalesSceneEntity_HI_RO.getFirstAOiSalesSceneOne(yearMonth); //getOiSalesSceneOne(yearMonth);
		LOGGER.info("OI分摊 场景一正在执行step 1:" + oiSalesSceneOne);
		ttaOiSpliteDao.executeSqlUpdate(oiSalesSceneOne);
		Map<String, List<String>> fieldMap = ttaOiSpliteDao.findOiSpliteTableFieldMapping(year, "1");//场景一
        if (fieldMap == null || fieldMap.isEmpty() || fieldMap.get("sourceList") == null || fieldMap.get("targetList") == null) {
            LOGGER.info("OI分摊场景二: 没有配置对应字段，执行失败" );
            return;
        }
		List<String> sourceList = fieldMap.get("sourceList");
		List<String> targetList = fieldMap.get("targetList");
        LOGGER.info("OI分摊 场景一sourceList:{}, targetList:{}", sourceList, targetList);
		String ttaOiSalesSceneYtd = TtaOiSalesSceneEntity_HI_RO.getFirstBTtaOiSalesSceneYtd(yearMonth, sourceList, targetList);
		LOGGER.info("OI分摊 场景一正在执行step 2:" + ttaOiSalesSceneYtd);
		ttaOiSpliteDao.executeSqlUpdate(ttaOiSalesSceneYtd);

		Map<String, List<String>> fieldSourceMap = ttaOiSpliteDao.findOiSpliteTableFieldMapping(year, "1");//场景一
		List<String> source = fieldSourceMap.get("sourceList");
		String firstSumTableSql = TtaOiSalesSceneEntity_HI_RO.getFirstSumTableSql(source, yearMonth);

		LOGGER.info("OI分摊 场景一正在执行step 3:" + firstSumTableSql);
		ttaOiSpliteDao.executeSqlUpdate(firstSumTableSql);

		LOGGER.info("OI分摊场景一：场景一：SALES占比非purchase_type计算执行完毕,耗时：{}秒。" , (System.currentTimeMillis() - startTime) / 1000);
	}

	/**
	 * 功能描述：场景二 PO（不含退货）占比计算
	 */
	@Override
	public void saveOiPoScene(String yearMonth) {
		Long startTime = System.currentTimeMillis();
		LOGGER.info("#################OI分摊 场景二：PO（不含退货）占比计算开始执行,入参数信息yearMonth:{},开始执行时间:{}", yearMonth, new Date().toLocaleString());
        Map<String, List<String>> fieldMap = ttaOiSpliteDao.findOiSpliteTableFieldMapping(yearMonth.substring(0,4), "2");//场景二
        if (fieldMap == null || fieldMap.isEmpty() || fieldMap.get("sourceList") == null || fieldMap.get("targetList") == null) {
            LOGGER.info("OI分摊场景二: 没有配置对应字段，执行失败" );
            return;
        }
		String ttaOiPoSceneBaseYtd = TtaOiSalesSceneEntity_HI_RO.getSecondATtaOiPoSceneBaseYtd(yearMonth);
		LOGGER.info("OI分摊场景二：PO（不含退货）占比计算正在执行执行step 1:" + ttaOiPoSceneBaseYtd);
        ttaOiSpliteDao.executeSqlUpdate(ttaOiPoSceneBaseYtd);
        LOGGER.info("OI分摊 场景二 sourceList:{}, targetList:{}",  fieldMap.get("sourceList"), fieldMap.get("targetList"));
        String ttaOiPoSceneYtd = TtaOiSalesSceneEntity_HI_RO.getSecondBTtaOiPoSceneYtd(yearMonth, fieldMap.get("sourceList"), fieldMap.get("targetList"));
        LOGGER.info("OI分摊 场景二 正在执行step 2:" + ttaOiPoSceneYtd);
        ttaOiSpliteDao.executeSqlUpdate(ttaOiPoSceneYtd);

		String sumTableSql = TtaOiSalesSceneEntity_HI_RO.getSecondSumTableSql(null, yearMonth);
		LOGGER.info("OI分摊 场景二 正在执行step 3:" + sumTableSql);
		ttaOiSpliteDao.executeSqlUpdate(sumTableSql);

		LOGGER.info("OI分摊场景二：PO（不含退货）占比计算执行完毕,耗时：{}秒。", (System.currentTimeMillis() - startTime) / 1000);
    }


	/**
	 * 功能描述：场景三：PO（退货RV）占比计算，tta_oi_summary_line
	 */
	@Override
	public void saveOiPoRvScene(String yearMonth) {
		Long startTime = System.currentTimeMillis();
		LOGGER.info("#################OI分摊 场景三：PO（退货RV）占比计算开始执行,入参数信息yearMonth:{},开始执行时间:{}", yearMonth, new Date().toLocaleString());
		Map<String, List<String>> fieldMap = ttaOiSpliteDao.findOiSpliteTableFieldMapping(yearMonth.substring(0, 4), "3");//场景三
		if (fieldMap == null || fieldMap.isEmpty() || fieldMap.get("sourceList") == null || fieldMap.get("targetList") == null) {
			LOGGER.info("OI分摊场景三: 没有配置对应字段，执行失败" );
			return;
		}
		String ttaOiPoRvSceneBaseYtd = TtaOiSalesSceneEntity_HI_RO.getThirdATtaOiPoRvSceneBaseYtd(yearMonth);
		LOGGER.info("OI分摊场景三：PO（退货RV）占比计算正在执行执行step 1:" + ttaOiPoRvSceneBaseYtd);
		ttaOiSpliteDao.executeSqlUpdate(ttaOiPoRvSceneBaseYtd);
		LOGGER.info("OI分摊 场景三 sourceList:{}, targetList:{}",  fieldMap.get("sourceList"), fieldMap.get("targetList"));
		String ttaOiPoSceneYtd = TtaOiSalesSceneEntity_HI_RO.getThirdBTtaOiPoRSceneYtd(yearMonth, fieldMap.get("sourceList"), fieldMap.get("targetList"));
		LOGGER.info("OI分摊 场景三 正在执行step 2:" + ttaOiPoSceneYtd);
		ttaOiSpliteDao.executeSqlUpdate(ttaOiPoSceneYtd);
		//暂时注释
		String ttaOiPoSceneSum = TtaOiSalesSceneEntity_HI_RO.getTtaOiPoSceneSum(yearMonth, fieldMap.get("sourceList"), fieldMap.get("targetList"));
		LOGGER.info("OI分摊 场景三 正在执行step 3:" + ttaOiPoSceneSum);
		ttaOiSpliteDao.executeSqlUpdate(ttaOiPoSceneSum);
		LOGGER.info("OI分摊场景三：PO（退货RV）占比计算执行完毕,耗时：{}秒。", (System.currentTimeMillis() - startTime) / 1000);
	}


	/**
	 * 功能描述： 场景四：ABOI(非试用装)占比计算，tta_aboi_summary
	 */


	@Override
	public void saveFourABOI(String yearMonth) {
		List<Map<String, Object>> list = ttaOiSpliteDao.queryNamedSQLForList("select  t.tta_aboi_summary_id from tta_aboi_summary t where t.account_month =" + yearMonth + " and rownum <= 10", new HashMap<String, Object>());
		Assert.notEmpty(list,"tta_aboi_summary 表数据，月份为" + yearMonth + "的数据没有，执行失败！");

		LOGGER.info("#################OI分摊 场景四：入参数信息yearMonth:{},开始执行时间:{}", yearMonth, new Date().toLocaleString());
		LOGGER.info("OI分摊 场景四 正在执行step 1:\n" + " drop table tta_oi_aboi_ng_suit_scene_temp;\n");
		ttaObjectServer.saveDropOrTruncateTable("tta_oi_aboi_ng_suit_scene_temp",0);

		String ttaOiaboingSuitSceneTemp = TtaOiSalesSceneEntity_HI_RO.getTtaOiaboingSuitSceneTemp(yearMonth);
		LOGGER.info("OI分摊 场景四 正在执行step 2:\n" + ttaOiaboingSuitSceneTemp + ";\n");
		ttaOiSpliteDao.executeSqlUpdate(ttaOiaboingSuitSceneTemp);

		LOGGER.info("OI分摊 场景四 正在执行step 3:\n" + "drop table tta_oi_aboi_ng_suit_scene_update_temp;\n");
		ttaObjectServer.saveDropOrTruncateTable("tta_oi_aboi_ng_suit_scene_update_temp",0);

		String ttaOiAboiNgSuitSceneUpdateTemp = TtaOiSalesSceneEntity_HI_RO.getTtaOiAboiNgSuitSceneUpdateTemp(yearMonth);
		LOGGER.info("OI分摊 场景四 正在执行step 4:\n" + ttaOiAboiNgSuitSceneUpdateTemp  + ";\n");
		ttaOiSpliteDao.executeSqlUpdate(ttaOiAboiNgSuitSceneUpdateTemp);

		String sceneTemp1 = TtaOiSalesSceneEntity_HI_RO.getTtaOiAboiNgSuitSceneTemp1();
		LOGGER.info("OI分摊 场景四 正在执行step sceneTemp1:\n" + sceneTemp1 + ";\n");
		ttaOiSpliteDao.executeSqlUpdate(sceneTemp1);

		String sceneTemp2 = TtaOiSalesSceneEntity_HI_RO.getTtaOiAboiNgSuitSceneTemp2();
		LOGGER.info("OI分摊 场景四 正在执行step sceneTemp2:\n" + sceneTemp2 + ";\n");
		ttaOiSpliteDao.executeSqlUpdate(sceneTemp2);

		String sceneTemp3 = TtaOiSalesSceneEntity_HI_RO.getTtaOiAboiNgSuitSceneTemp3();
		LOGGER.info("OI分摊 场景四 正在执行step sceneTemp3:\n" + sceneTemp3 + ";\n");
		ttaOiSpliteDao.executeSqlUpdate(sceneTemp3);

		String sceneTemp4 = TtaOiSalesSceneEntity_HI_RO.getTtaOiAboiNgSuitSceneTemp4();
		LOGGER.info("OI分摊 场景四 正在执行step sceneTemp4:\n" + sceneTemp4 + ";\n");
		ttaOiSpliteDao.executeSqlUpdate(sceneTemp4);

		String sceneTemp5 = TtaOiSalesSceneEntity_HI_RO.getTtaOiAboiNgSuitSceneTemp5();
		LOGGER.info("OI分摊 场景四 正在执行step sceneTemp5:\n" + sceneTemp5 + ";\n");
		ttaOiSpliteDao.executeSqlUpdate(sceneTemp5);

		String sceneTemp6 = TtaOiSalesSceneEntity_HI_RO.getTtaOiAboiNgSuitSceneTemp6();
		LOGGER.info("OI分摊 场景四 正在执行step sceneTemp6:\n" + sceneTemp6 + ";\n");
		ttaOiSpliteDao.executeSqlUpdate(sceneTemp6);

		String sceneTemp7 = TtaOiSalesSceneEntity_HI_RO.getTtaOiAboiNgSuitSceneTemp7();
		LOGGER.info("OI分摊 场景四 正在执行step sceneTemp7:\n" + sceneTemp7 + ";\n");
		ttaOiSpliteDao.executeSqlUpdate(sceneTemp7);

		String sceneTemp8 = TtaOiSalesSceneEntity_HI_RO.getTtaOiAboiNgSuitSceneTemp8();
		LOGGER.info("OI分摊 场景四 正在执行step sceneTemp8:\n" + sceneTemp8 + ";\n");
		ttaOiSpliteDao.executeSqlUpdate(sceneTemp8);

		String sceneTemp9 = TtaOiSalesSceneEntity_HI_RO.getTtaOiAboiNgSuitSceneTemp9();
		LOGGER.info("OI分摊 场景四 正在执行step sceneTemp9:\n" + sceneTemp9 + ";\n");
		ttaOiSpliteDao.executeSqlUpdate(sceneTemp9);

		String sceneTemp10 = TtaOiSalesSceneEntity_HI_RO.getTtaOiAboiNgSuitSceneTemp10();
		LOGGER.info("OI分摊 场景四 正在执行step sceneTemp10:\n" + sceneTemp10 + ";\n");
		ttaOiSpliteDao.executeSqlUpdate(sceneTemp10);

		String sceneTemp11 = TtaOiSalesSceneEntity_HI_RO.getTtaOiAboiNgSuitSceneTemp11();
		LOGGER.info("OI分摊 场景四 正在执行step sceneTemp11:\n" + sceneTemp11 + ";\n");
		ttaOiSpliteDao.executeSqlUpdate(sceneTemp11);

		String sceneTemp12 = TtaOiSalesSceneEntity_HI_RO.getTtaOiAboiNgSuitSceneTemp12();
		LOGGER.info("OI分摊 场景四 正在执行step sceneTemp12:\n" + sceneTemp12 + ";\n");
		ttaOiSpliteDao.executeSqlUpdate(sceneTemp12);

		String sceneTemp13 = TtaOiSalesSceneEntity_HI_RO.getTtaOiAboiNgSuitSceneTemp13();
		LOGGER.info("OI分摊 场景四 正在执行step sceneTemp13:\n" + sceneTemp13 + ";\n");
		ttaOiSpliteDao.executeSqlUpdate(sceneTemp13);

		String sceneTemp14 = TtaOiSalesSceneEntity_HI_RO.getTtaOiAboiNgSuitSceneTemp14();
		LOGGER.info("OI分摊 场景四 正在执行step sceneTemp14:\n" + sceneTemp14 + ";\n");
		ttaOiSpliteDao.executeSqlUpdate(sceneTemp14);

		String sceneTemp15 = TtaOiSalesSceneEntity_HI_RO.getTtaOiAboiNgSuitSceneTemp15();
		LOGGER.info("OI分摊 场景四 正在执行step sceneTemp15:\n" + sceneTemp15 + ";\n");
		ttaOiSpliteDao.executeSqlUpdate(sceneTemp15);

		String sceneTemp16 = TtaOiSalesSceneEntity_HI_RO.getTtaOiAboiNgSuitSceneTemp16();
		LOGGER.info("OI分摊 场景四 正在执行step sceneTemp16:\n" + sceneTemp16 + ";\n");
		ttaOiSpliteDao.executeSqlUpdate(sceneTemp16);

		LOGGER.info("OI分摊 场景四 正在执行 step 17:\n" + " drop   table tta_oi_aboi_ng_suit_temp"  + ";\n");
		ttaOiSpliteDao.executeSqlUpdate("drop  table tta_oi_aboi_ng_suit_temp");

		String step18 = TtaOiSalesSceneEntity_HI_RO.getStep18();
		LOGGER.info("OI分摊 场景四 正在执行 step18:\n" + step18  + ";\n");
		ttaOiSpliteDao.executeSqlUpdate(step18);

		String step19 = TtaOiSalesSceneEntity_HI_RO.getStep19(yearMonth);
		LOGGER.info("OI分摊 场景四 正在执行 step19:\n" + step19 + ";\n");
		ttaOiSpliteDao.executeSqlUpdate(step19);

		String step20 = TtaOiSalesSceneEntity_HI_RO.getStep20(yearMonth);
		LOGGER.info("OI分摊 场景四 正在执行 step20:\n" + step20 + ";\n");
		ttaOiSpliteDao.executeSqlUpdate(step20);

		String step21 = TtaOiSalesSceneEntity_HI_RO.getStep21(yearMonth);
		LOGGER.info("OI分摊 场景四 正在执行 step21:\n" + step21 + ";\n");
		ttaOiSpliteDao.executeSqlUpdate(step21);

		String fourStep22 = TtaOiSalesSceneEntity_HI_RO.getFourStepDiff22(yearMonth);
		LOGGER.info("OI分摊 场景四 正在执行 step22:\n" + fourStep22 + ";\n");
		ttaOiSpliteDao.executeSqlUpdate(fourStep22);

		String fourthSum = TtaOiSalesSceneEntity_HI_RO.getFourthSum(yearMonth);
		LOGGER.info("OI分摊 场景四 正在执行 step23:\n" + fourthSum + ";\n");
		ttaOiSpliteDao.executeSqlUpdate(fourthSum);
	}



	/**
	 * 功能描述： 场景五：ABOI(试用装)计算,不需要计算比值，tta_oi_summary_line
	 */
	@Override
	public void saveOiAboiSuitScene(String yearMonth) {
		Long startTime = System.currentTimeMillis();
		LOGGER.info("#################OI分摊 场景五：ABOI(试用装)计算,不需要计算比值,yearMonth:{},开始执行时间:{}", yearMonth, new Date().toLocaleString());
		/**
		 * <code> //废弃
		 * final String ttaOiPoRvSceneBaseYtd = TtaOiSalesSceneEntity_HI_RO.getTtaOiAboiSuitSceneBaseYtd(yearMonth);
		 *	LOGGER.info("OI分摊场景五：ABOI(试用装)计算,不需要计算比值step 1:" + ttaOiPoRvSceneBaseYtd);
		 *	ttaOiSpliteDao.executeSqlUpdate(ttaOiPoRvSceneBaseYtd);
		 * </code>
		*/
		final String ttaOiAboiSuitSceneYtd = TtaOiSalesSceneEntity_HI_RO.getTtaOiAboiSuitSceneYtd(yearMonth);
		LOGGER.info("OI分摊场景五：ABOI(试用装)计算,不需要计算比值step 1:" + ttaOiAboiSuitSceneYtd);
		ttaOiSpliteDao.executeSqlUpdate(ttaOiAboiSuitSceneYtd);

		String fifthDiff = TtaOiSalesSceneEntity_HI_RO.getFifthDiff(yearMonth);
		LOGGER.info("OI分摊场景五：ABOI(试用装)计算,不需要计算比值step 2:" + fifthDiff);
		ttaOiSpliteDao.executeSqlUpdate(fifthDiff);

		String fifthSum = TtaOiSalesSceneEntity_HI_RO.getFifthSum(yearMonth);
		LOGGER.info("OI分摊场景五：ABOI(试用装)计算,不需要计算比值step 3:" + fifthSum);
		ttaOiSpliteDao.executeSqlUpdate(fifthSum);

		LOGGER.info("OI分摊场景五：ABOI(试用装)计算,不需要计算比值执行完毕,耗时：{}秒。", (System.currentTimeMillis() - startTime) / 1000);
	}

	/**
	 * 功能描述： 场景六：OTHER OI(L&N)占比计算
	 * 待业务确认tta_ln_monthly_line表数据导入
	 */
	@Override
	public void saveTtaOiLnScene(String yearMonth) {
		Long startTime = System.currentTimeMillis();
		LOGGER.info("#################OI分摊 场景六：OTHER OI(L&N)占比计算开始执行,入参数信息yearMonth:{},开始执行时间:{}", yearMonth, new Date().toLocaleString());
		//2020.10.29添加,当月检查上一个月的L&N数据, 传参数202010,实际得到是202009的数据
		String lastYearMonth = SaafDateUtils.dateDiffMonth(yearMonth, -1);
		//List<Map<String, Object>> list = ttaOiSpliteDao.queryNamedSQLForList("select t.receive_date from tta_ln_monthly_line t where substr(t.receive_date,0,6)=" + yearMonth + " and rownum <= 10", new HashMap<String, Object>());
		List<Map<String, Object>> list = ttaOiSpliteDao.queryNamedSQLForList("select t.receive_date from tta_ln_monthly_line t where substr(t.receive_date,0,6)=" + lastYearMonth + " and rownum <= 10", new HashMap<String, Object>());
		Assert.notEmpty(list,"tta_ln_monthly_line 表数据，月份为" + yearMonth + "的数据没有，执行失败！");

		Map<String, List<String>> fieldMap = ttaOiSpliteDao.findOiSpliteTableFieldMapping(yearMonth.substring(0,4), "6");//场景6
		/*<code>
		if (fieldMap == null || fieldMap.isEmpty() || fieldMap.get("sourceList") == null || fieldMap.get("targetList") == null) {
			LOGGER.info("OI分摊场景六: 没有配置对应字段，执行失败。" );
			return;
		}
		String ttaOiLnSceneBaseYtd = TtaOiSalesSceneEntity_HI_RO.getTtaOiLnSceneBaseYtd(yearMonth);
		LOGGER.info("OI分摊场景六：OTHER OI(L&N)占比计算step 1:" + ttaOiLnSceneBaseYtd);
		ttaOiSpliteDao.executeSqlUpdate(ttaOiLnSceneBaseYtd);
		String ttaOiLnSceneYtd = TtaOiSalesSceneEntity_HI_RO.getTtaOiLnSceneYtd(yearMonth, fieldMap.get("sourceList"), fieldMap.get("targetList"));
		LOGGER.info("OI分摊场景六：OTHER OI(L&N)占比计算step 2:" + ttaOiLnSceneYtd);
		ttaOiSpliteDao.executeSqlUpdate(ttaOiLnSceneYtd);</code>
		*/
		LOGGER.info("OI分摊场景六：OTHER OI(L&N)占比计算step 1:" + "drop table tta_oi_ln_scene_temp;");
		ttaObjectServer.saveDropOrTruncateTable("tta_oi_ln_scene_temp",0);

		String sixStep2 = TtaOiSalesSceneEntity_HI_RO.getSixStep2(yearMonth);
		LOGGER.info("OI分摊场景六：OTHER OI(L&N)占比计算step 2:" + sixStep2);
		ttaOiSpliteDao.executeSqlUpdate(sixStep2);

		LOGGER.info("OI分摊场景六：OTHER OI(L&N)占比计算step 3:" + "drop table tta_ln_monthly_line_temp;");
		ttaObjectServer.saveDropOrTruncateTable("tta_ln_monthly_line_temp",0);

		String sixStep4 = TtaOiSalesSceneEntity_HI_RO.getSixStep4(yearMonth);
		LOGGER.info("OI分摊场景六：OTHER OI(L&N)占比计算step 4:" + sixStep4);
		ttaOiSpliteDao.executeSqlUpdate(sixStep4);

		LOGGER.info("OI分摊场景六：OTHER OI(L&N)占比计算step 5:" + "truncate table tta_oi_ln_scene_base_ytd;");
		ttaObjectServer.saveDropOrTruncateTable("tta_oi_ln_scene_base_ytd",1);

		String sixStep6 = TtaOiSalesSceneEntity_HI_RO.getSixStep6(yearMonth);
		LOGGER.info("OI分摊场景六：OTHER OI(L&N)占比计算step 6:" + sixStep6);
		ttaOiSpliteDao.executeSqlUpdate(sixStep6);

		String sixStep7 = TtaOiSalesSceneEntity_HI_RO.getSixStep7(yearMonth);
		LOGGER.info("OI分摊场景六：OTHER OI(L&N)占比计算step 7:" + sixStep7);
		ttaOiSpliteDao.executeSqlUpdate(sixStep7);
		
		String sixSum = TtaOiSalesSceneEntity_HI_RO.getSixSum(yearMonth);
		LOGGER.info("OI分摊场景六：OTHER OI(L&N)占比计算step 8:" + sixSum);
		ttaOiSpliteDao.executeSqlUpdate(sixSum);

		LOGGER.info("OI分摊场景六：OTHER OI(L&N)占比计算执行完毕,耗时：{}秒。" + (System.currentTimeMillis() - startTime) / 1000);

	}

	@Override
	public void findTest() {
		Map<String, List<String>> fieldMap = ttaOiSpliteDao.findOiSpliteTableFieldMapping("2020", "1");//场景一
		List<String> sourceList = fieldMap.get("sourceList");
		String firstSumTableSql = TtaOiSalesSceneEntity_HI_RO.getFirstSumTableSql(sourceList, "202001");
		System.out.println(firstSumTableSql);
	}


/*	public static void main(String[] args) {
		String sumTableSql = TtaOiSalesSceneEntity_HI_RO.getSecondSumTableSql(null, "202001");
		LOGGER.info("OI分摊 场景二 正在执行step 3:" + sumTableSql);
	}*/

}
