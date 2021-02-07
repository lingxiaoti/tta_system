package com.sie.watsons.base.ob.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.ob.model.entities.PlmObPackageReportEntity_HI;
import com.sie.watsons.base.ob.model.entities.PlmPackageSpecificationEntity_HI;
import com.sie.watsons.base.ob.model.entities.readonly.PlmObPackageReportEntity_HI_RO;
import com.sie.watsons.base.ob.model.entities.readonly.PlmPackageSpecificationEntity_HI_RO;
import com.sie.watsons.base.ob.model.inter.IPlmPackageSpecification;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("plmPackageSpecificationServer")
public class PlmPackageSpecificationServer extends BaseCommonServer<PlmPackageSpecificationEntity_HI> implements IPlmPackageSpecification{
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmPackageSpecificationServer.class);
	@Autowired
	private ViewObject<PlmPackageSpecificationEntity_HI> plmPackageSpecificationDAO_HI;
	@Autowired
	private BaseViewObject<PlmPackageSpecificationEntity_HI_RO> plmPackageSpecificationDAO_HI_RO;
	@Autowired
	private ViewObject<PlmObPackageReportEntity_HI> plmObPackageReportDAO_HI;
	@Autowired
	private BaseViewObject<PlmObPackageReportEntity_HI_RO> plmObPackageReportDAO_HI_RO;
	public PlmPackageSpecificationServer() {
		super();
	}

	@Override
	public Pagination<PlmPackageSpecificationEntity_HI_RO> findPlmPackageSpecificationInfo(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
		StringBuffer sql = new StringBuffer(PlmPackageSpecificationEntity_HI_RO.QUERY_SQL);
		if(!SaafToolUtils.isNullOrEmpty(queryParamJSON.getString("report"))){
			sql = new StringBuffer(PlmPackageSpecificationEntity_HI_RO.REPORT_SQL);
		}
		Map<String, Object> paramsMap = new HashMap<>();
		if(!SaafToolUtils.isNullOrEmpty(queryParamJSON.getString("consumption"))){
			sql = new StringBuffer(PlmObPackageReportEntity_HI_RO.QUERY_SQL);
			SaafToolUtils.parperHbmParam(PlmObPackageReportEntity_HI_RO.class, queryParamJSON, sql, paramsMap);
			Pagination<PlmObPackageReportEntity_HI_RO> pagination = plmObPackageReportDAO_HI_RO.findPagination(sql, paramsMap, pageIndex, pageRows);
		}

		SaafToolUtils.parperHbmParam(PlmPackageSpecificationEntity_HI_RO.class, queryParamJSON, sql, paramsMap);
		if(!SaafToolUtils.isNullOrEmpty(queryParamJSON.getString("report"))){
			sql.append(" ORDER BY pps.LAST_UPDATE_DATE desc ");
		}
		StringBuffer countSql = SaafToolUtils.getSimpleSqlCountString(sql,"count(*)");
		Pagination<PlmPackageSpecificationEntity_HI_RO> pagination = plmPackageSpecificationDAO_HI_RO.findPagination(sql, paramsMap, pageIndex, pageRows);
		return pagination;
	}

	@Override
	public Pagination<PlmObPackageReportEntity_HI_RO> findPlmObPackageReportInfo(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
		Map<String, Object> paramsMap = new HashMap<>();
		StringBuffer sql = new StringBuffer(PlmObPackageReportEntity_HI_RO.QUERY_SQL);
		SaafToolUtils.parperHbmParam(PlmObPackageReportEntity_HI_RO.class, queryParamJSON, sql, paramsMap);
		sql.append(" ORDER BY pdi.LAST_UPDATE_DATE desc ");
		Pagination<PlmObPackageReportEntity_HI_RO> pagination = plmObPackageReportDAO_HI_RO.findPagination(sql, paramsMap, pageIndex, pageRows);
		return pagination;
	}

	@Override
	public List<PlmPackageSpecificationEntity_HI> savePlmPackageSpecificationInfo(JSONObject queryParamJSON) throws Exception {
		if(!SaafToolUtils.isNullOrEmpty(queryParamJSON.getJSONArray("data"))){
			queryParamJSON = this.saveAndImportPackageSpecification(queryParamJSON);
		}
		List<PlmPackageSpecificationEntity_HI> dataList = JSON.parseArray(queryParamJSON.getJSONArray("plmPackageSpecificationList").toString(),PlmPackageSpecificationEntity_HI.class);
		dataList = this.changeStatusByCommand(dataList,queryParamJSON.getString("commandStatus"), queryParamJSON.getInteger("varUserId"), queryParamJSON.getInteger("plmDevelopmentInfoId"), queryParamJSON.getInteger("plmProjectId"));
		plmPackageSpecificationDAO_HI.saveOrUpdateAll(dataList);
		return dataList;
	}

	//导入
	public JSONObject saveAndImportPackageSpecification(JSONObject queryParamJSON) throws Exception{
		JSONArray dataArray = queryParamJSON.getJSONArray("data");
		JSONObject infoObject = queryParamJSON.getJSONObject("info");
		SaafToolUtils.validateJsonParms(infoObject,"plmProjectId","plmDevelopmentInfoId");
		Integer plmProjectId = infoObject.getInteger("plmProjectId");
		Integer plmDevelopmentInfoId = infoObject.getInteger("plmDevelopmentInfoId");

		String localLization = "篮筐/BASKET," +
				"吸塑/BLISTER," +
				"瓶身/BOTTLE," +
				"桶/BUCKET," +
				"透明盒/CLEAR BOX," +
				"瓶盖(螺丝/扭旋)/CAP (SCREW/FLIP)," +
				"玻璃纸/CELLOPHANE," +
				"夹条/CLIP STRIP," +
				"密闭盒/CLOSED BOX," +
				"展示罐/DISPLAY JAR," +
				"展示托盘/DISPLAY TRAY," +
				"一次性纸/OPP/Poly袋)/DISPOSABLE BAG (PAPER / OPP / POLY)," +
				"饮料纸箱/DRINKS CARTON," +
				"吊卡/HANGTAG," +
				"头卡/HEADER CARD," +
				"充气袋/INFLATABLE POUCH," +
				"内卡(背/J型/U型卡)INSERT CARD (BACK/J/U CARD)," +
				"罐/JAR," +
				"罐盖/JAR LID," +
				"标签/LABEL," +
				"其他/OTHER," +
				"颜料/PALETTE," +
				"泵/PUMP," +
				"喷嘴/SPRAY HEAD," +
				"不干胶/TEMPER SEAL," +
				"盆/TUB," +
				"管/TUBE";


		String material = "以玻璃为主要原料的复合包装/COMPOSITE PACKAGING, IN WHICH THE MAJORITY MATERIAL IS GLASS\n" +
				"复合包装铝<50微米/钢<50%但最高重量<50微米/COMPOSITE PACKAGING ALUMINIUM < 50 MICRONS/STEEL < 50% BUT HIGHEST WEIGHT < 50 MICRONS\n" +
				"复合包装铝<50%/钢<50%但最高重量均>50微米/COMPOSITE PACKAGING ALUMINIUM < 50 % BUT HIGHEST WEIGHT / STEEL < 50% BUT HIGHEST WEIGHT > 50 MICRONS\n" +
				"金属-其他/METAL-OTHERS\n" +
				"金属-钢(>50%)/METAL-STEEL (>50%)\n" +
				"金属-锡/Metal-Tin\n" +
				"金属-铝(>50%和>50微米)/METAL - ALUMINIUM (>50% AND >50 MICRONS)\n" +
				"其他可回收材料-纺织品等/OTHER RECOVERABLE MATERIALS - TEXTILE AND ETC,\n" +
				"其他不可回收材料-瓷器/石器等/OTHER NON-RECOVERABLE MATERIALS-PORCELAIN/STONEWARE AND ETC,\n" +
				"纸-纸板(>85%)/PAPER-CARDBOARD (>85%),\n" +
				"以纸/纸板为主要原料的复合包装/COMPOSITES WITH MAJORITY OF PAPER/CARDBOARD,\n" +
				"塑胶-HDPE/PLASTIC-HDPE,\n" +
				"塑胶-LDPE/PLASTIC-LDPE,\n" +
				"塑胶-PC/PLASTIC-PC,\n" +
				"塑胶-其他/PLASTIC-OTHERS,\n" +
				"塑胶-PET/PLASTIC-PET,\n" +
				"塑胶-PP/PLASTIC-PP,\n" +
				"塑胶-PS/PLASTIC-PS,\n" +
				"塑胶-PVC/PLASTIC-PVC,\n" +
				"塑胶-ABS/PLASTIC-ABS,\n" +
				"塑胶-PA/PLASTIC-PA,\n" +
				"以塑胶为主要原料的复合包装/COMPOSITE PACKAGING IN WHICH THE MAJORITY MATERIAL IS PLASTIC,\n" +
				"木头/WOOD \n";

		String[] materialArray = material.split("\n");
		BigDecimal[] valueArray = new BigDecimal[materialArray.length];
		for(int i = 0; i < valueArray.length; i++){
			valueArray[i] = new BigDecimal(0);
		}

		String yes_or_no = "是/Yes,不是/No";

		JSONArray returnArray = new JSONArray();
		JSONArray errArray = new JSONArray();

		for(int i = 0; i < dataArray.size(); i++){
			String errMsg = "";
			JSONObject data = dataArray.getJSONObject(i);

			data.put("plmDevelopmentInfoId",plmDevelopmentInfoId);
			data.put("plmProjectId",plmProjectId);
			data.put("itemLocalizationOrName",data.getString("itemLocalizationOrName").trim());
			data.put("material", data.getString("material").trim());
			data.put("recyclable", data.getString("recyclable").trim());
			if(!SaafToolUtils.isNullOrEmpty(data.getString("remarks"))) {
				data.put("remarks", data.getString("remarks").trim());
			}
			String param = data.getString("netWeightPerGram").trim();
			data.put("netWeightPerGram", param);

			if (param.endsWith(".")) {
				errMsg = errMsg + "净重克数"+data.getString("netWeightPerGram")+"不合规范";
			}

			String[] test = param.split("\\.");
//			if(!StringUtils.isNumeric(test)){
			if(!isNumeric(test)){
				errMsg = errMsg + "净重克数"+data.getString("netWeightPerGram")+"不合规范";
			}

			if(localLization.indexOf(data.getString("itemLocalizationOrName").trim())==-1){
				errMsg = errMsg + "部件位置/名称" + data.getString("itemLocalizationOrName")+"不符规范！";
			}
			if(material.indexOf(data.getString("material").trim())==-1){
				errMsg = errMsg + "材质" + data.getString("material")+"不符规范！";
			}
			for(int index = 0; index < materialArray.length; index++){
				if(materialArray[index].indexOf(data.getString("material").trim())>=0&&errMsg.equals("")){
					valueArray[index] = valueArray[index].add(data.getBigDecimal("netWeightPerGram"));
					break;
				}
			}
			if(yes_or_no.indexOf(data.getString("recyclable").trim())==-1){
				errMsg = errMsg + "可回收利用的" + data.getString("recyclable")+"不符规范！";
			}

			if(!errMsg.equals("")){
				JSONObject errJson = new JSONObject();
				errJson.put("ROW_NUM",data.get("ROW_NUM"));
				errJson.put("ERR_MESSAGE",errMsg);
				errArray.add(errJson);
			}
			returnArray.add(data);
		}

		if(errArray.size()!=0){
			throw new Exception(errArray.toJSONString());
		}

		List<PlmObPackageReportEntity_HI> deleteReportArray = plmObPackageReportDAO_HI.findByProperty("plmDevelopmentInfoId",plmDevelopmentInfoId);
		plmObPackageReportDAO_HI.deleteAll(deleteReportArray);
		PlmObPackageReportEntity_HI entity = new PlmObPackageReportEntity_HI();
		entity.setOperatorUserId(queryParamJSON.getInteger("varUserId"));
		entity.setBusinessUnit("Watsons China");
		entity.setPlmDevelopmentInfoId(plmDevelopmentInfoId);
		for(int i = 0; i < valueArray.length; i++){
			Method method = entity.getClass().getDeclaredMethod("setV"+String.valueOf(i+1),BigDecimal.class);
			Object object = method.invoke(entity,new Object[]{valueArray[i]});
		}
		plmObPackageReportDAO_HI.saveOrUpdate(entity);

		List<PlmPackageSpecificationEntity_HI> deleteArray = plmPackageSpecificationDAO_HI.findByProperty("plmDevelopmentInfoId",plmDevelopmentInfoId);
		plmPackageSpecificationDAO_HI.deleteAll(deleteArray);

		queryParamJSON.put("plmPackageSpecificationList", returnArray);
		return queryParamJSON;
	}

    private boolean isNumeric(String[] test) {
        if (test.length == 1 || test.length == 2) {
            for (String str : test) {
                if (!StringUtils.isNumeric(str)) {
                    return false;
                }
            }
			if (test.length > 1 && test[1].length() > 2) {
				return false;
			}
            return true;
        }
        return false;
    }

    public List<PlmPackageSpecificationEntity_HI> changeStatusByCommand(List<PlmPackageSpecificationEntity_HI> dataList, String commandStatus, Integer userId, Integer plmDevelopmentInfoId, Integer plmProjectId){
		for(PlmPackageSpecificationEntity_HI data: dataList) {

			data.setOperatorUserId(userId);
			if(plmProjectId!=null)
				data.setPlmProjectId(plmProjectId);
			if(plmDevelopmentInfoId!=null){
				data.setPlmDevelopmentInfoId(plmDevelopmentInfoId);
			}
		}
		return dataList;
	}

	@Override
	public Integer deletePlmPackageSpecificationInfo(JSONObject queryParamJSON) {
		if(SaafToolUtils.isNullOrEmpty(queryParamJSON.getJSONArray("plmPackageSpecificationList"))){
			PlmPackageSpecificationEntity_HI entity = JSON.parseObject(queryParamJSON.toString(), PlmPackageSpecificationEntity_HI.class);
			plmPackageSpecificationDAO_HI.delete(entity);
			return 1;
		}
		List<PlmPackageSpecificationEntity_HI> dataList = JSON.parseArray(queryParamJSON.getJSONArray("plmPackageSpecificationList").toString(),PlmPackageSpecificationEntity_HI.class);
		plmPackageSpecificationDAO_HI.deleteAll(dataList);
		return dataList.size();
	}




}
