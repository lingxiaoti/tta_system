package com.sie.wastons.ttadata.model.inter.server;

import com.sie.wastons.ttadata.model.entities.VmiSalesInSumEntity_HI;
import com.sie.wastons.ttadata.model.entities.readyonly.VmiPersonLevelInfoEntity_HI_RO;
import com.sie.wastons.ttadata.model.entities.readyonly.VmiSalesInSearchEntity_HI_RO;
import com.sie.wastons.ttadata.model.inter.IVmiSalesInSum;
import com.yhg.hibernate.core.dao.BaseViewObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;


@Slf4j
@Component("vmiSalesInSumServer")
public class VmiSalesInSumServer implements IVmiSalesInSum {

	@Autowired
	private BaseViewObject<VmiSalesInSearchEntity_HI_RO> vmiSalesInSearchDAO_HI_RO;
	@Autowired
	private BaseViewObject<VmiPersonLevelInfoEntity_HI_RO> vmiPersonLevelInfoDAO_HI_RO;

	public VmiSalesInSumServer() {
		super();
	}

	/**
	 * 获取销售量
	 * @return
	 */
	public List<VmiSalesInSumEntity_HI> findSalesInSum(String store, String itemCode) throws Exception {
		Integer userId = -1;
		Calendar cal = Calendar.getInstance();
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		cal.setTime(new Date());
		int curYear = cal.get(Calendar.YEAR);
		DecimalFormat df = new DecimalFormat("00");
		Integer weekIndex = Integer.parseInt(curYear + df.format(cal.get(Calendar.WEEK_OF_YEAR)));

		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		Date currentMonday = cal.getTime();//本周一凌晨0点时间
		cal.add(Calendar.DATE, -28);
		Date fourMonday = cal.getTime();//28天后的时间
		cal.add(Calendar.DATE, -63);
		Date thirteenMonday = cal.getTime();//91天后的时间

		SimpleDateFormat format2 = new SimpleDateFormat("yyyyMMdd");

		String ee = format2.format(currentMonday);
		String ee1 = format2.format(fourMonday);
		String ee2 = format2.format(thirteenMonday);

		int year = Integer.parseInt(ee.substring(0, 4));
		int month = Integer.parseInt(ee.substring(4, 6));
		int year1 = Integer.parseInt(ee1.substring(0, 4));
		int month1 = Integer.parseInt(ee1.substring(4, 6));
		int year2 = Integer.parseInt(ee2.substring(0, 4));
		int month2 = Integer.parseInt(ee2.substring(4, 6));
		//月份差=（第二年份-第一年份）*12 + 第二月份-第一月份
		int index1 = (year - year1) * 12 + month - month1;
		int index2 = (year - year2) * 12 + month - month2;

		Map<String, Object> salesMap = new HashMap<String, Object>();
		salesMap.put("varStore", store);
		salesMap.put("varItem", itemCode);
		salesMap.put("varEndDate", Integer.parseInt(format2.format(currentMonday)));

		List<VmiSalesInSumEntity_HI> salesInSumList = new ArrayList<VmiSalesInSumEntity_HI>();
		List<VmiSalesInSearchEntity_HI_RO> result = null;

		for (int i = index1 + 1; i > 0; i--) {
			String tableName = "tta_sales_in_" + year + (month >= 10 ? "" : "0") + month;
			salesMap.put("varStarteDate", Integer.parseInt(format2.format(fourMonday)));
			try {
				result = vmiSalesInSearchDAO_HI_RO.findList(getSaleSumSql(tableName, "Y").toString(), salesMap);

				for (VmiSalesInSearchEntity_HI_RO vmiSalesInSearchEntity_HI_RO : result) {
					String saleStore = vmiSalesInSearchEntity_HI_RO.getStore();
					String saleItem = vmiSalesInSearchEntity_HI_RO.getItem();
					String flag = "N";
					for (VmiSalesInSumEntity_HI vmiSalesInSumEntity_HI : salesInSumList) {
						if (vmiSalesInSumEntity_HI.getStore().equals(saleStore) && vmiSalesInSumEntity_HI.getItem().equals(saleItem)) {
							vmiSalesInSumEntity_HI.setSalesQtyFour(vmiSalesInSumEntity_HI.getSalesQtyFour().add(vmiSalesInSearchEntity_HI_RO.getSumSalesQty()));
							vmiSalesInSumEntity_HI.setSalesAmtFour(vmiSalesInSumEntity_HI.getSalesAmtFour().add(vmiSalesInSearchEntity_HI_RO.getSumSalesAmt()));
							vmiSalesInSumEntity_HI.setSalesExcAmtFour(vmiSalesInSumEntity_HI.getSalesExcAmtFour().add(vmiSalesInSearchEntity_HI_RO.getSumSalesExcludeAmt()));
							vmiSalesInSumEntity_HI.setWeekTime(weekIndex);
							vmiSalesInSumEntity_HI.setOperatorUserId(userId);
							flag = "Y";
							break;
						}
					}
					if ("N".equals(flag)) {
						VmiSalesInSumEntity_HI vmiSalesInSumEntity_HI = new VmiSalesInSumEntity_HI();
						vmiSalesInSumEntity_HI.setStore(saleStore);
						vmiSalesInSumEntity_HI.setItem(saleItem);
						vmiSalesInSumEntity_HI.setSalesQtyFour(vmiSalesInSearchEntity_HI_RO.getSumSalesQty());
						vmiSalesInSumEntity_HI.setSalesAmtFour(vmiSalesInSearchEntity_HI_RO.getSumSalesAmt());
						vmiSalesInSumEntity_HI.setSalesExcAmtFour(vmiSalesInSearchEntity_HI_RO.getSumSalesExcludeAmt());
						vmiSalesInSumEntity_HI.setWeekTime(weekIndex);
						vmiSalesInSumEntity_HI.setOperatorUserId(userId);
						salesInSumList.add(vmiSalesInSumEntity_HI);
					}
				}
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}

			month--;
			if (month == 0) {
				month = 12;
				year--;
			}
		}

		year = Integer.parseInt(ee.substring(0, 4));
		month = Integer.parseInt(ee.substring(4, 6));
		for (int i = index2 + 1; i > 0; i--) {
			String tableName = "tta_sales_in_" + year + (month >= 10 ? "" : "0") + month;
			salesMap.put("varStarteDate", Integer.parseInt(format2.format(thirteenMonday)));

			try {
				result = vmiSalesInSearchDAO_HI_RO.findList(getSaleSumSql(tableName, "Y").toString(), salesMap);

				for (VmiSalesInSearchEntity_HI_RO vmiSalesInSearchEntity_HI_RO : result) {
					String saleStore = vmiSalesInSearchEntity_HI_RO.getStore();
					String saleItem = vmiSalesInSearchEntity_HI_RO.getItem();
					String flag = "N";
					for (VmiSalesInSumEntity_HI vmiSalesInSumEntity_HI : salesInSumList) {
						if (vmiSalesInSumEntity_HI.getStore().equals(saleStore) && vmiSalesInSumEntity_HI.getItem().equals(saleItem)) {
							vmiSalesInSumEntity_HI.setSalesQtyThirteen(vmiSalesInSumEntity_HI.getSalesQtyThirteen().add(vmiSalesInSearchEntity_HI_RO.getSumSalesQty()));
							vmiSalesInSumEntity_HI.setSalesAmtThirteen(vmiSalesInSumEntity_HI.getSalesAmtThirteen().add(vmiSalesInSearchEntity_HI_RO.getSumSalesAmt()));
							vmiSalesInSumEntity_HI.setSalesExcAmtThirteen(vmiSalesInSumEntity_HI.getSalesExcAmtThirteen().add(vmiSalesInSearchEntity_HI_RO.getSumSalesExcludeAmt()));
							vmiSalesInSumEntity_HI.setWeekTime(weekIndex);
							vmiSalesInSumEntity_HI.setOperatorUserId(userId);
							flag = "Y";
							break;
						}
					}
					if ("N".equals(flag)) {
						VmiSalesInSumEntity_HI vmiSalesInSumEntity_HI = new VmiSalesInSumEntity_HI();
						vmiSalesInSumEntity_HI.setStore(saleStore);
						vmiSalesInSumEntity_HI.setItem(saleItem);
						vmiSalesInSumEntity_HI.setSalesQtyFour(BigDecimal.ZERO);
						vmiSalesInSumEntity_HI.setSalesAmtFour(BigDecimal.ZERO);
						vmiSalesInSumEntity_HI.setSalesExcAmtFour(BigDecimal.ZERO);
						vmiSalesInSumEntity_HI.setSalesQtyThirteen(vmiSalesInSearchEntity_HI_RO.getSumSalesQty());
						vmiSalesInSumEntity_HI.setSalesAmtThirteen(vmiSalesInSearchEntity_HI_RO.getSumSalesAmt());
						vmiSalesInSumEntity_HI.setSalesExcAmtThirteen(vmiSalesInSearchEntity_HI_RO.getSumSalesExcludeAmt());
						vmiSalesInSumEntity_HI.setWeekTime(weekIndex);
						vmiSalesInSumEntity_HI.setOperatorUserId(userId);
						salesInSumList.add(vmiSalesInSumEntity_HI);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			month--;
			if (month == 0) {
				month = 12;
				year--;
			}
		}
		return salesInSumList;
	}

	private StringBuffer getSaleSumSql(String tableName,String flag){
		StringBuffer saleSql = new StringBuffer();
		saleSql.append(" SELECT nvl(tsi.store, 'null') store,\n");
		saleSql.append(" nvl(tsi.item, 'null') item, \n");
		saleSql.append(" sum(nvl(tsi.sales_qty, 0)) sumSalesQty,\n");
		saleSql.append(" sum(nvl(tsi.sales_amt, 0)) sumSalesAmt,\n");
		saleSql.append(" sum(nvl(tsi.sales_exclude_amt, 0)) sumSalesExcludeAmt\n");
		saleSql.append(" FROM ");
		saleSql.append(tableName);
		saleSql.append("  tsi \n");
		saleSql.append(" WHERE 1 = 1 \n");
		saleSql.append(" and tsi.tran_date >= :varStarteDate  \n");
		saleSql.append(" and tsi.tran_date < :varEndDate  \n");
		if("Y".equals(flag)){
			saleSql.append(" and tsi.store = :varStore \n");
			saleSql.append(" and tsi.item = :varItem \n");
		}
		saleSql.append(" GROUP BY nvl(tsi.store, 'null'),\n");
		saleSql.append(" nvl(tsi.item, 'null')\n");
		return saleSql;
	}


	//获取上级邮箱地址
	public String[] findMgrEmail(BigDecimal userId,BigDecimal createdBy)throws Exception{
		List<String> emails = new ArrayList<String>();
		Map<String,Object> searchMap = new HashMap<String,Object>();
		searchMap.put("userId",userId);
		List<VmiPersonLevelInfoEntity_HI_RO> vmiPersonLevelInfoEntity_HI_ROs = vmiPersonLevelInfoDAO_HI_RO.findList(VmiPersonLevelInfoEntity_HI_RO.personLevel_sql,searchMap);
		if(vmiPersonLevelInfoEntity_HI_ROs.size()>0){
			Map<String,Object> searchMap1 = new HashMap<String,Object>();
			//添加TA本人的email
			if(vmiPersonLevelInfoEntity_HI_ROs.get(0).getEmail()!=null)
			emails.add(vmiPersonLevelInfoEntity_HI_ROs.get(0).getEmail());
			searchMap1.put("personId",vmiPersonLevelInfoEntity_HI_ROs.get(0).getMgrPersonId());
			List<VmiPersonLevelInfoEntity_HI_RO> vmiPersonLevelInfoEntity_HI_ROs2 = vmiPersonLevelInfoDAO_HI_RO.findList(VmiPersonLevelInfoEntity_HI_RO.personLevel_sql,searchMap1);
			if(vmiPersonLevelInfoEntity_HI_ROs2.size()>0&&vmiPersonLevelInfoEntity_HI_ROs2.get(0).getEmail()!=null)
				emails.add(vmiPersonLevelInfoEntity_HI_ROs2.get(0).getEmail());

		}
		//添加采购员创建人的email
		searchMap.put("userId",createdBy);
		List<VmiPersonLevelInfoEntity_HI_RO> vmiPersonLevelInfoEntity_HI_ROs2 = vmiPersonLevelInfoDAO_HI_RO.findList(VmiPersonLevelInfoEntity_HI_RO.personLevel_sql,searchMap);
		if(vmiPersonLevelInfoEntity_HI_ROs2.size()>0){
			if(vmiPersonLevelInfoEntity_HI_ROs2.get(0).getEmail()!=null)
				emails.add(vmiPersonLevelInfoEntity_HI_ROs2.get(0).getEmail());
		}
		String[] array = emails.toArray(new String[emails.size()]);
		log.info("array.length:"+array.length);
		for(int i = 0;i<array.length;i++){
			log.info("email:"+array[i]);
		}
		return array;
	}

	//获取汇报线邮箱地址
	public String[] findMgrLineEmail(BigDecimal userId)throws Exception{
		List<String> emails = new ArrayList<String>();
		Map<String,Object> searchMap = new HashMap<String,Object>();
		searchMap.put("userId",userId);
		List<VmiPersonLevelInfoEntity_HI_RO> vmiPersonLevelInfoEntity_HI_ROs = vmiPersonLevelInfoDAO_HI_RO.findList(VmiPersonLevelInfoEntity_HI_RO.personLevel_sql,searchMap);
		if(vmiPersonLevelInfoEntity_HI_ROs.size()>0){
			if(vmiPersonLevelInfoEntity_HI_ROs.get(0).getEmail()!=null)
				emails.add(vmiPersonLevelInfoEntity_HI_ROs.get(0).getEmail());
			Map<String,Object> searchMap1 = new HashMap<String,Object>();
			searchMap1.put("personId",vmiPersonLevelInfoEntity_HI_ROs.get(0).getMgrPersonId());
			List<VmiPersonLevelInfoEntity_HI_RO> vmiPersonLevelInfoEntity_HI_ROs2 = vmiPersonLevelInfoDAO_HI_RO.findList(VmiPersonLevelInfoEntity_HI_RO.personLevel_sql,searchMap1);
			if(vmiPersonLevelInfoEntity_HI_ROs2.size()>0){
				if(vmiPersonLevelInfoEntity_HI_ROs2.get(0).getEmail()!=null)
					emails.add(vmiPersonLevelInfoEntity_HI_ROs2.get(0).getEmail());
				if(vmiPersonLevelInfoEntity_HI_ROs2.get(0).getMgrPersonId()!=null)
				 getEmail(emails,vmiPersonLevelInfoEntity_HI_ROs2.get(0).getMgrPersonId());
			}else{
				return emails.toArray(new String[emails.size()]);
			}
		}

		String[] array = emails.toArray(new String[emails.size()]);
		log.info("array.length:"+array.length);
		for(int i = 0;i<array.length;i++){
			log.info("email:"+array[i]);
		}
		return array;
	}

	public List<String> getEmail(List<String> emails,BigDecimal personId)throws Exception{
		Map<String,Object> searchMap1 = new HashMap<String,Object>();
		searchMap1.put("personId",personId);
		List<VmiPersonLevelInfoEntity_HI_RO> vmiPersonLevelInfoEntity_HI_ROs2 = vmiPersonLevelInfoDAO_HI_RO.findList(VmiPersonLevelInfoEntity_HI_RO.personLevel_sql,searchMap1);
		if(vmiPersonLevelInfoEntity_HI_ROs2.size()>0){
			if(vmiPersonLevelInfoEntity_HI_ROs2.get(0).getEmail()!=null){
				emails.add(vmiPersonLevelInfoEntity_HI_ROs2.get(0).getEmail());
			}
			if(vmiPersonLevelInfoEntity_HI_ROs2.get(0).getMgrPersonId()!=null)
			getEmail(emails,vmiPersonLevelInfoEntity_HI_ROs2.get(0).getMgrPersonId());
		}else{
			return emails;
		}
		return emails;
	}


}
