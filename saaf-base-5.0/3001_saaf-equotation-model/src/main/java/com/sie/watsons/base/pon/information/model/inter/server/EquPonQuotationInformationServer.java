package com.sie.watsons.base.pon.information.model.inter.server;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jcraft.jsch.HASH;
import com.sie.saaf.common.model.inter.IFastdfs;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.model.inter.server.GenerateCodeServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.pon.information.model.entities.EquPonInformationRejectionEntity_HI;
import com.sie.watsons.base.pon.information.model.entities.EquPonQuotationInformationEntity_HI;
import com.sie.watsons.base.pon.information.model.entities.readonly.EquPonQuotationInformationEntity_HI_RO;
import com.sie.watsons.base.pon.information.model.inter.IEquPonQuotationInformation;
import com.sie.watsons.base.pon.itproject.model.entities.EquPonItProjectInfoEntity_HI;
import com.sie.watsons.base.pon.itproject.model.entities.EquPonItScoringTeamEntity_HI;
import com.sie.watsons.base.pon.itproject.model.entities.EquPonItSupplierInvitationEntity_HI;
import com.sie.watsons.base.pon.itproject.model.entities.EquPonItWitnessTeamEntity_HI;
import com.sie.watsons.base.pon.itproject.model.entities.readonly.EquPonItWitnessTeamEntity_HI_RO;
import com.sie.watsons.base.pon.itproject.model.inter.IEquPonItProjectAttachment;
import com.sie.watsons.base.pon.itproject.model.inter.IEquPonItScoringTeam;
import com.sie.watsons.base.pon.itquotation.model.entities.EquPonQuoContentItEntity_HI;
import com.sie.watsons.base.pon.itquotation.model.entities.EquPonQuotationInfoItEntity_HI;
import com.sie.watsons.base.pon.itscoring.model.entities.EquPonItScoringDetailEntity_HI;
import com.sie.watsons.base.pon.itscoring.model.entities.EquPonItScoringInfoEntity_HI;
import com.sie.watsons.base.pon.itscoring.model.entities.readonly.EquPonItScoringInfoEntity_HI_RO;
import com.sie.watsons.base.pon.itscoring.model.inter.server.EquPonItScoringDetailServer;
import com.sie.watsons.base.pon.itscoring.model.inter.server.EquPonItScoringInfoServer;
import com.sie.watsons.base.pon.project.model.entities.EquPonProjectInfoEntity_HI;
import com.sie.watsons.base.pon.project.model.entities.EquPonScoringTeamEntity_HI;
import com.sie.watsons.base.pon.project.model.entities.EquPonSupplierInvitationEntity_HI;
import com.sie.watsons.base.pon.project.model.entities.EquPonWitnessTeamEntity_HI;
import com.sie.watsons.base.pon.project.model.entities.readonly.EquPonProjectInfoEntity_HI_RO;
import com.sie.watsons.base.pon.project.model.entities.readonly.EquPonWitnessTeamEntity_HI_RO;
import com.sie.watsons.base.pon.project.model.inter.IEquPonProjectAttachment;
import com.sie.watsons.base.pon.quotation.model.entities.EquPonQuotationInfoEntity_HI;
import com.sie.watsons.base.pon.scoring.model.entities.EquPonScoringInfoEntity_HI;
import com.sie.watsons.base.pon.scoring.model.inter.server.ExportExcelEntity;
import com.sie.watsons.base.pon.standards.model.entities.EquPonStandardsHEntity_HI;
import com.sie.watsons.base.pon.standards.model.entities.EquPonStandardsLEntity_HI;
import com.sie.watsons.base.pon.standards.model.entities.readonly.EquPonStandardsHEntity_HI_RO;
import com.sie.watsons.base.pos.supplierinfo.model.entities.EquPosSuppInfoWithDeptEntity_HI;
import com.sie.watsons.base.pos.supplierinfo.model.entities.EquPosSupplierInfoEntity_HI;
import com.sie.watsons.base.utils.CommonUtils;
import com.sie.watsons.base.utils.EmailUtil;
import com.sie.watsons.base.utils.ResultUtils;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import static com.alibaba.fastjson.JSON.parseObject;

@Component("equPonQuotationInformationServer")
public class EquPonQuotationInformationServer extends BaseCommonServer<EquPonQuotationInformationEntity_HI> implements IEquPonQuotationInformation{
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPonQuotationInformationServer.class);

	@Autowired
	private ViewObject<EquPonQuotationInformationEntity_HI> equPonQuotationInformationDAO_HI;

	@Autowired
	private ViewObject<EquPonStandardsHEntity_HI> equPonStandardsHDAO_HI;

	@Autowired
	private ViewObject<EquPonStandardsLEntity_HI> equPonStandardsLDAO_HI;

	@Autowired
	private ViewObject<EquPonInformationRejectionEntity_HI> equPonInformationRejectionDAO_HI;

	@Autowired
	private ViewObject<EquPonWitnessTeamEntity_HI> equPonWitnessTeamDAO_HI;

	@Autowired
	private ViewObject<EquPonItWitnessTeamEntity_HI> equPonItWitnessTeamDAO_HI;


	@Autowired
	private ViewObject<EquPonSupplierInvitationEntity_HI> ponSupplierInvitationDao;

	@Autowired
	private ViewObject<EquPonItSupplierInvitationEntity_HI> ponItSupplierInvitationDao;

	@Autowired
	private ViewObject<EquPonScoringTeamEntity_HI> equPonScoringTeamDAO_HI;

	@Autowired
	private ViewObject<EquPonItScoringTeamEntity_HI> equPonItScoringTeamDAO_HI;

	@Autowired
	private BaseViewObject<EquPonQuotationInformationEntity_HI_RO> equPonQuotationInformationDAO_HI_RO;

	@Autowired
	private BaseViewObject<EquPonItScoringInfoEntity_HI_RO> equPonItScoringInfoEntity_HI_RO;

	@Autowired
	private GenerateCodeServer generateCodeServer;

	@Autowired
	private EquPonItScoringDetailServer equPonItScoringDetailServer;

	@Autowired
	private ViewObject<EquPonSupplierInvitationEntity_HI> equPonSupplierInvitationDAO_HI;

	@Autowired
	private ViewObject<EquPonItSupplierInvitationEntity_HI> equPonItSupplierInvitationDAO_HI;

	@Autowired
	private BaseViewObject<EquPonWitnessTeamEntity_HI_RO> equPonWitnessTeamEntity_HI_RO;

	@Autowired
	private BaseViewObject<EquPonItWitnessTeamEntity_HI_RO> equPonItWitnessTeamEntity_HI_RO;

	@Autowired
	private ViewObject<EquPonProjectInfoEntity_HI> equPonProjectInfoDAO_HI;

	@Autowired
	private ViewObject<EquPonItProjectInfoEntity_HI> equPonItProjectInfoDAO_HI;


	@Autowired
	private ViewObject<EquPonQuotationInfoEntity_HI> equPonQuotationInfoDAO_HI;

	@Autowired
	private ViewObject<EquPonQuotationInfoItEntity_HI> equPonQuotationInfoItDAO_HI;

	@Autowired
	private IEquPonProjectAttachment equPonProjectAttachmentServer;

	@Autowired
	private IEquPonItProjectAttachment equPonItProjectAttachmentServer;

	@Autowired
	private IEquPonItScoringTeam equPonItScoringTeamServer;

	@Autowired
	private EquPonItScoringInfoServer equPonItScoringInfoServer;

    @Autowired
    private ViewObject<EquPonScoringInfoEntity_HI> equPonScoringInfoDAO_HI;

	@Autowired
	private ViewObject<EquPonItScoringInfoEntity_HI> equPonItScoringInfoDAO_HI;

	@Autowired
	private ViewObject<EquPosSupplierInfoEntity_HI> equPosSupplierInfoDAO_HI;

	@Autowired
	private ViewObject<EquPosSuppInfoWithDeptEntity_HI> equPosSuppInfoWithDeptDAO_HI;

	@Autowired
	private ViewObject<EquPonItScoringDetailEntity_HI> equPonItScoringDetailDAO_HI;

	@Autowired
	private ViewObject<EquPonQuoContentItEntity_HI> equPonQuoContentItDAO_HI;

	@Autowired
	private IFastdfs fastDfsServer;


	public EquPonQuotationInformationServer() {
		super();
	}

	private ExportExcelEntity generateTemplateExport(JSONObject jsonObject) throws Exception {
		String sheetTitle = "Panel Test导入模板";

		//根据projectId,查询立项产品规格
		// 构建列名
		List<String> sheetFieldsName = new ArrayList<>();
		// 设置列宽
		List<Integer> sheetColWidth = new ArrayList<Integer>();

		sheetFieldsName.add("item description");
		sheetColWidth.add(0, 4000);

		sheetFieldsName.add("Highest Score");
		sheetColWidth.add(0, 4000);

		sheetFieldsName.add("benchmark");
		sheetColWidth.add(0, 4000);

		for(int i = 0; i < 3; i++){
			sheetFieldsName.add("aabbccdd");
			sheetColWidth.add(i + 3, 7000);
		}
		// 构建数据
//		List<EquPonQuotationProductInfoEntity_HI> productInfoList = productInfoDao.findByProperty("quotationId", jsonObject.getInteger("sourceId"));
		// 构建表单内容实体
		return new ExportExcelEntity(sheetTitle, sheetFieldsName, null, sheetColWidth);
	}

	private void writeExcelSheet(ExportExcelEntity expoEntity, SXSSFSheet sheet, JSONObject jsonObject) {
		// 总列数
		int colsCount = expoEntity.getSheetFieldsName().size();
		// 创建Excel的sheet的一行
		SXSSFRow row = sheet.createRow(0);
		// 创建sheet的列名
		SXSSFCell rowCell = null;
		for (int i = 0; i < colsCount; i++) {
			rowCell = row.createCell(i);
			String fieldName = expoEntity.getSheetFieldsName().get(i);
			rowCell.setCellValue(fieldName);
			// 设置自定义列宽
			System.out.println("123");
			if (expoEntity.getSheetColWidth() != null) {
				sheet.setColumnWidth(i, expoEntity.getSheetColWidth().get(i));
			}
		}
		System.out.println("123");
		//向表格内写入获取的动态数据

		for (int i = 0; i < 5; i++) {
			row = sheet.createRow(1 + i);
			SXSSFCell productName = row.createCell(0);
			productName.setCellType(CellType.STRING);
			productName.setCellValue("还是测试下"+i);
		}
	}

	@Override
	public Pagination<EquPonQuotationInformationEntity_HI_RO> findPonInformationInfo(String params, Integer pageIndex, Integer pageRows) throws Exception {
		JSONObject jsonParam = parseObject(params);
		LOGGER.info("------jsonParam------" + jsonParam.toString());
		StringBuffer queryString = new StringBuffer(
				EquPonQuotationInformationEntity_HI_RO.QUERY_LIST_SQL);
		Map<String, Object> map = new HashMap<String, Object>();
		SaafToolUtils.parperParam(jsonParam, "t.project_Name", "projectName", queryString, map, "like");
		SaafToolUtils.parperParam(jsonParam, "T.information_Code", "informationCode", queryString, map, "like");
		SaafToolUtils.parperParam(jsonParam, "to_number(T.information_Status)", "informationStatus", queryString, map, "=");
		JSONObject dateParam = new JSONObject();
		dateParam.put("creationDate_gte",jsonParam.getString("creationDateFrom"));
		dateParam.put("creationDate_lte",jsonParam.getString("creationDateTo"));


		if(jsonParam.get("searchType")!=null&&"informationSearch".equals(jsonParam.getString("searchType"))){
			map.put("userId",jsonParam.getString("userId"));
			map.put("userNumber",jsonParam.getString("userNumber"));
			//创建人和非拟定状态下的见证人查询
			queryString.append(" and (t.created_by = :userId " +
					"or exists (select 1 from equ_pon_witness_team c where   t.information_Status <> '10' and c.project_id = t.project_Id and (c.DEFAULT_MEMBER = :userNumber or c.WITNESS_MEMBER = :userNumber))" +
					")  ");
		}
		SaafToolUtils.parperHbmParam(EquPonStandardsHEntity_HI_RO.class, dateParam, queryString, map);
		// 排序
		queryString.append(" order by t.creation_date desc");
		Pagination<EquPonQuotationInformationEntity_HI_RO> returnList = equPonQuotationInformationDAO_HI_RO.findPagination(queryString, map, pageIndex, pageRows);
		return returnList;
	}

	@Override
	public Pagination<EquPonQuotationInformationEntity_HI_RO> findPonInformationInfoByDeptCode(String params, Integer pageIndex, Integer pageRows) throws Exception {
		JSONObject jsonParam = parseObject(params);
		String deptCode = jsonParam.getString("deptCode");
		LOGGER.info("------jsonParam------" + jsonParam.toString());
		StringBuffer queryString = new StringBuffer(
				EquPonQuotationInformationEntity_HI_RO.QUERY_LIST_ALL_SQL);
		Map<String, Object> map = new HashMap<String, Object>();
		SaafToolUtils.parperParam(jsonParam, "t.project_Name", "projectName", queryString, map, "like");
		SaafToolUtils.parperParam(jsonParam, "T.information_Code", "informationCode", queryString, map, "like");
		SaafToolUtils.parperParam(jsonParam, "to_number(T.information_Status)", "informationStatus", queryString, map, "=");
		//SaafToolUtils.parperParam(jsonParam, "T.dept_code", "deptCode", queryString, map, "=");

//		if("0E".equals(deptCode) || "03".equals(deptCode)){
//			queryString.append(" and T.dept_code = '" + deptCode + "'");
//		}

		JSONObject dateParam = new JSONObject();
		dateParam.put("creationDate_gte",jsonParam.getString("creationDateFrom"));
		dateParam.put("creationDate_lte",jsonParam.getString("creationDateTo"));


		if(jsonParam.get("searchType")!=null&&"informationSearch".equals(jsonParam.getString("searchType"))){
			map.put("userId",jsonParam.getString("userId"));
			map.put("userNumber",jsonParam.getString("userNumber"));
			//创建人和非拟定状态下的见证人查询
			queryString.append(" and (t.created_by = :userId " +
					"or exists (select 1 from equ_pon_witness_team c where   t.information_Status <> '10' and c.project_id = t.project_Id and (c.DEFAULT_MEMBER = :userNumber or c.WITNESS_MEMBER = :userNumber))" +
					"or exists (select 1 from equ_pon_it_witness_team ct where   t.information_Status <> '10' and ct.project_id = t.project_Id and (ct.DEFAULT_MEMBER = :userNumber or ct.WITNESS_MEMBER = :userNumber))" +
					")  ");
		}
		SaafToolUtils.parperHbmParam(EquPonStandardsHEntity_HI_RO.class, dateParam, queryString, map);
		// 排序
		queryString.append(" order by t.creation_date desc");
		Pagination<EquPonQuotationInformationEntity_HI_RO> returnList = equPonQuotationInformationDAO_HI_RO.findPagination(queryString, map, pageIndex, pageRows);
		return returnList;
	}

	@Override
	public Pagination<EquPonQuotationInformationEntity_HI_RO> findPonItInformationInfo(String params, Integer pageIndex, Integer pageRows) throws Exception {

		JSONObject jsonParam = parseObject(params);
		LOGGER.info("------jsonParam------" + jsonParam.toString());
		StringBuffer queryString = new StringBuffer(
				EquPonQuotationInformationEntity_HI_RO.QUERY_IT_LIST_SQL);
		Map<String, Object> map = new HashMap<String, Object>();
		SaafToolUtils.parperParam(jsonParam, "t.project_Name", "projectName", queryString, map, "like");
		SaafToolUtils.parperParam(jsonParam, "T.information_Code", "informationCode", queryString, map, "like");
		SaafToolUtils.parperParam(jsonParam, "to_number(T.information_Status)", "informationStatus", queryString, map, "=");
		System.out.println();
		JSONObject dateParam = new JSONObject();
		dateParam.put("creationDate_gte",jsonParam.getString("creationDateFrom"));
		dateParam.put("creationDate_lte",jsonParam.getString("creationDateTo"));


		if(jsonParam.get("searchType")!=null&&"informationSearch".equals(jsonParam.getString("searchType"))){
			map.put("userId",jsonParam.getString("userId"));
			map.put("userNumber",jsonParam.getString("userNumber"));
			//创建人 和非拟定状态下的见证人可以看到
			queryString.append(" and (t.created_by = :userId " +
					"or exists (select 1 from equ_pon_it_witness_team c where   t.information_Status <> '10' and c.project_id = t.project_Id and (c.DEFAULT_MEMBER = :userNumber or c.WITNESS_MEMBER = :userNumber))" +
					")  ");
		}
		SaafToolUtils.parperHbmParam(EquPonStandardsHEntity_HI_RO.class, dateParam, queryString, map);
		// 排序
		queryString.append(" order by t.creation_date desc");
		Pagination<EquPonQuotationInformationEntity_HI_RO> returnList = equPonQuotationInformationDAO_HI_RO.findPagination(queryString, map, pageIndex, pageRows);
		return returnList;
	}




	@Override
	public JSONObject findInformationIdDatail(String params) {
		JSONObject jsonParam = parseObject(params);
		JSONObject returnJson= new JSONObject();
		StringBuffer queryString = new StringBuffer(
				EquPonQuotationInformationEntity_HI_RO.QUERY_LIST_SQL);

		Map<String, Object> map = new HashMap<>();
		SaafToolUtils.parperParam(jsonParam, "t.information_Id", "informationId", queryString, map, "=");
		EquPonQuotationInformationEntity_HI_RO hEntity = (EquPonQuotationInformationEntity_HI_RO) equPonQuotationInformationDAO_HI_RO.get(queryString, map);
		//如果是拟定/驳回状态下,那么要判断当前项目是不是最高版本的项目,如果不是就替换为最高版本项目信息(项目采购金额大于阈值不需要替换)
        if(("10".equals(hEntity.getInformationStatus())||"40".equals(hEntity.getInformationStatus()))&&hEntity.getProjectNumber()!=null && "Y".equals(hEntity.getPurchaseFlag())){
            String projectNumber = hEntity.getProjectNumber();
            Integer projectId = hEntity.getProjectId();
            EquPonProjectInfoEntity_HI projectInfoEntityHi = equPonProjectInfoDAO_HI.getById(projectId);
            String sourceProjectNumber = projectInfoEntityHi.getSourceProjectNumber();
            Map maps = new HashMap();
            maps.put("sourceProjectNumber",sourceProjectNumber);
            List<EquPonProjectInfoEntity_HI> projectInfoEntityHiList = equPonProjectInfoDAO_HI.findList("from EquPonProjectInfoEntity_HI where  sourceProjectNumber = :sourceProjectNumber and projectStatus = '30'",maps);
            try{
				EquPonProjectInfoEntity_HI maxProject = projectInfoEntityHiList.stream().max((stu1,stu2)->Integer.compare(Integer.parseInt(stu1.getProjectVersion()),Integer.parseInt(stu2.getProjectVersion()))).get();
				if(maxProject.getProjectNumber()!=projectNumber){
					hEntity.setProjectCategory(maxProject.getProjectCategory());
					hEntity.setProjectId(maxProject.getProjectId());
					hEntity.setProjectName(maxProject.getProjectName());
					hEntity.setProjectNumber(maxProject.getProjectNumber());
					hEntity.setProjectVersion(maxProject.getProjectVersion());
					hEntity.setProjectStatus(maxProject.getProjectStatus());
					hEntity.setRejectionReasons(maxProject.getRejectReason());
					hEntity.setProjectCategory(maxProject.getProjectCategory());
					hEntity.setSeriesName(maxProject.getSeriesName());
//					hEntity.setProjectPurchaseAmount(BigDecimal.valueOf(maxProject.getProjectPurchaseAmount()));
					hEntity.setProjectPurchaseAmount(maxProject.getProjectPurchaseAmount());
					hEntity.setProjectCycleFrom(maxProject.getProjectCycleFrom());
					hEntity.setProjectCycleTo(maxProject.getProjectCycleTo());
					hEntity.setQuotationDeadline(maxProject.getQuotationDeadline());
//					hEntity.setStandardsCode(maxProject.getScoringStandard());
					hEntity.setPresentCooperationSupplier(maxProject.getPresentCooperationSupplier());
					hEntity.setSensoryTestTypes(maxProject.getSensoryTestTypes());
					hEntity.setRemark(maxProject.getRemark());
				}
			}catch (Exception e){
				System.out.println(e.getMessage());
			}
        }

		hEntity.setProjectCycle(isnull(hEntity.getProjectCycleFrom()) + "至" + isnull(hEntity.getProjectCycleTo()));
        returnJson.put("scoringFlag",'Y');//需求变更，这个标识已经无用了。


		JSONObject json = JSONObject.parseObject(JSONObject.toJSONString(hEntity));
		List<String> incomingParam = new ArrayList<>();
		List<String> efferentParam = new ArrayList<>();
		List<String> typeParam = new ArrayList<>();
		incomingParam.add("projectStatus");
		efferentParam.add("projectStatusName");
		typeParam.add("EQU_PON_PROJECT_STATUS");
		json = ResultUtils.getLookUpValues( json , incomingParam, efferentParam, typeParam);

		returnJson.put("data",json);
		return returnJson;
	}

	@Override
	public JSONObject findItInformationIdDatail(String params) {
		JSONObject jsonParam = parseObject(params);
		JSONObject returnJson= new JSONObject();
		StringBuffer queryString = new StringBuffer(
				EquPonQuotationInformationEntity_HI_RO.QUERY_IT_LIST_SQL);

		Map<String, Object> map = new HashMap<>();
		SaafToolUtils.parperParam(jsonParam, "t.information_Id", "informationId", queryString, map, "=");
		EquPonQuotationInformationEntity_HI_RO hEntity = (EquPonQuotationInformationEntity_HI_RO) equPonQuotationInformationDAO_HI_RO.get(queryString, map);
		//如果是拟定状态下,那么要判断当前项目是不是最高版本的项目,如果不是就修改为最高版本项目
		if(("10".equals(hEntity.getInformationStatus())||"40".equals(hEntity.getInformationStatus()))&&hEntity.getProjectNumber()!=null){
			String projectNumber = hEntity.getProjectNumber();
			Integer projectId = hEntity.getProjectId();
			EquPonItProjectInfoEntity_HI projectInfoEntityHi = equPonItProjectInfoDAO_HI.getById(projectId);
			String sourceProjectNumber = projectInfoEntityHi.getSourceProjectNumber();
			Map maps = new HashMap();
			maps.put("sourceProjectNumber",sourceProjectNumber);
			List<EquPonItProjectInfoEntity_HI> projectInfoEntityHiList = equPonItProjectInfoDAO_HI.findList("from EquPonItProjectInfoEntity_HI where  sourceProjectNumber = :sourceProjectNumber and projectStatus = '30'",maps);
			try{
				EquPonItProjectInfoEntity_HI maxProject = projectInfoEntityHiList.stream().max((stu1,stu2)->Integer.compare(Integer.parseInt(stu1.getProjectVersion()),Integer.parseInt(stu2.getProjectVersion()))).get();
				if(maxProject.getProjectNumber()!=projectNumber){
					hEntity.setProjectId(maxProject.getProjectId());
					hEntity.setProjectName(maxProject.getProjectName());
					hEntity.setProjectNumber(maxProject.getProjectNumber());
					hEntity.setProjectVersion(maxProject.getProjectVersion());
					hEntity.setProjectStatus(maxProject.getProjectStatus());
					hEntity.setRejectionReasons(maxProject.getRejectReason());
					hEntity.setQuotationDeadline(maxProject.getQuotationDeadline());
				}
			}catch (Exception e){
				System.out.println(e.getMessage());
			}
		}

//		hEntity.setProjectCycle(isnull(hEntity.getProjectCycleFrom()) + "至" + isnull(hEntity.getProjectCycleTo()));
		//假如是监控报价,就查询对应的评分是不是在审批完成前.
		returnJson.put("scoringFlag",'Y');
//        if("monitor".equals(jsonParam.getString("searchType"))){
////            private ViewObject<EquPonScoringInfoEntity_HI> equPonScoringInfoDAO_HI;
//            Map scoringMap = new HashMap();
//            scoringMap.put("informationId",hEntity.getInformationId());
//            List<EquPonScoringInfoEntity_HI> scoringList = equPonScoringInfoDAO_HI.findList(" from EquPonScoringInfoEntity_HI where informationId = :informationId and scoringStatus not in ('30','50','60')",scoringMap);
//            if(scoringList.size()>0){
//                returnJson.put("scoringFlag",'Y');
//            }
//        }

		JSONObject json = JSONObject.parseObject(JSONObject.toJSONString(hEntity));
		List<String> incomingParam = new ArrayList<>();
		List<String> efferentParam = new ArrayList<>();
		List<String> typeParam = new ArrayList<>();
		incomingParam.add("projectStatus");
		efferentParam.add("projectStatusName");
		typeParam.add("EQU_PON_PROJECT_STATUS");
		json = ResultUtils.getLookUpValues( json , incomingParam, efferentParam, typeParam);

		returnJson.put("data",json);
		return returnJson;
	}


	public String isnull(Date date) {
		if (date == null) {
			return "";
		} else {
			final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			return sdf.format(date);
		}
	}

	@Override
	public JSONObject saveRejectPonInvitation(String params, int userId, String userNumber)  throws  Exception{
		JSONObject jsonParam = parseObject(params);
		JSONObject returnJson = new JSONObject();
		Integer informationId = jsonParam.getInteger("informationId");
		String rejectionReason = jsonParam.getString("rejectionReason");
		EquPonInformationRejectionEntity_HI saveEntity = new EquPonInformationRejectionEntity_HI();
		saveEntity.setInformationId(informationId);
		saveEntity.setRemark(rejectionReason);
		saveEntity.setRejectionName(jsonParam.getString("userName"));
		saveEntity.setAttribute1(jsonParam.getString("projectVersion"));
		saveEntity.setOperatorUserId(userId);
		equPonInformationRejectionDAO_HI.save(saveEntity);
		EquPonQuotationInformationEntity_HI saveEntityHi = equPonQuotationInformationDAO_HI.getById(jsonParam.getInteger("informationId"));
		saveEntityHi.setInformationStatus("40");
		saveEntityHi.setOperatorUserId(userId);
		equPonQuotationInformationDAO_HI.save(saveEntityHi);
		//修改当前立项版本变更标志(can_change_flag)为Y
		EquPonProjectInfoEntity_HI projectEntity = null;
		projectEntity = equPonProjectInfoDAO_HI.getById(saveEntityHi.getProjectId());
		projectEntity.setCanChangeFlag("Y");
		projectEntity.setOperatorUserId(userId);
		equPonProjectInfoDAO_HI.save(projectEntity);
//==========================发送邮件===============================
		Map hasMap = new HashMap();
		hasMap.put("projectId",saveEntityHi.getProjectId());
		hasMap.put("userNumber",userNumber);
		//获取见证人信息
		List<EquPonWitnessTeamEntity_HI> teamEntityHiList = equPonWitnessTeamDAO_HI.findList("FROM EquPonWitnessTeamEntity_HI WHERE projectId = :projectId and ( " +
				"defaultMember = :userNumber or witnessMember = :userNumber" +
				")",hasMap);

		/*发送邮件给owner*/
		//邮件内容
		String mailBody = CommonUtils.rejectWitnessMailContent(jsonParam.getString("projectLeaderName"), jsonParam.getString("projectName"),"提交");
		JSONObject paramsJson = new JSONObject();
		paramsJson.put("userId", jsonParam.getInteger("createdBy"));
		JSONObject resultJson = ResultUtils.getServiceResult("http://1002-saaf-api-gateway/api/ttadataServer" + "/ttadata/ttaBaseDataService/v2/getUserInfo", paramsJson);
		if (resultJson.get("email") != null) {
			EmailUtil.sendInMail("报价资料开启见证", mailBody,resultJson.getString("email"));
		}

		//如果当前驳回人不是默认见证人,需要发送邮件给默认见证人
		EquPonWitnessTeamEntity_HI teamEntity = teamEntityHiList.get(0);
		if(userNumber.equals(teamEntity.getWitnessMember())){
			String defaultMember = teamEntity.getDefaultMember();
			paramsJson = new JSONObject();
			paramsJson.put("userName", defaultMember);
			JSONObject resultJson2 = ResultUtils.getServiceResult("http://1002-saaf-api-gateway/api/ttadataServer" + "/ttadata/ttaBaseDataService/v2/getUserInfoByUsrName", paramsJson);
			mailBody = CommonUtils.rejectWitnessMailContent(resultJson2.getString("personNameCn"), jsonParam.getString("projectName"),"指派见证开启");
			if (resultJson2.get("email") != null) {
				EmailUtil.sendInMail("报价资料开启见证", mailBody,resultJson2.getString("email"));
			}
		}

		return returnJson;
	}

	@Override
	public JSONObject saveItRejectPonInvitation(String params, int userId, String userNumber)  throws  Exception{
		JSONObject jsonParam = parseObject(params);
		JSONObject returnJson = new JSONObject();
		Integer informationId = jsonParam.getInteger("informationId");
		String rejectionReason = jsonParam.getString("rejectionReason");
		EquPonInformationRejectionEntity_HI saveEntity = new EquPonInformationRejectionEntity_HI();
		saveEntity.setInformationId(informationId);
		saveEntity.setRemark(rejectionReason);
		saveEntity.setRejectionName(jsonParam.getString("userName"));
		saveEntity.setAttribute1(jsonParam.getString("projectVersion"));
		saveEntity.setOperatorUserId(userId);
		equPonInformationRejectionDAO_HI.save(saveEntity);
		EquPonQuotationInformationEntity_HI saveEntityHi = equPonQuotationInformationDAO_HI.getById(jsonParam.getInteger("informationId"));
		saveEntityHi.setInformationStatus("40");
		saveEntityHi.setOperatorUserId(userId);
		equPonQuotationInformationDAO_HI.save(saveEntityHi);
		//修改当前立项版本变更标志(can_change_flag)为Y
		EquPonItProjectInfoEntity_HI projectEntity = null;
		projectEntity = equPonItProjectInfoDAO_HI.getById(saveEntityHi.getProjectId());
		projectEntity.setCanChangeFlag("Y");
		projectEntity.setOperatorUserId(userId);
		equPonItProjectInfoDAO_HI.save(projectEntity);
//==========================发送邮件===============================
		Map hasMap = new HashMap();
		hasMap.put("projectId",saveEntityHi.getProjectId());
		hasMap.put("userNumber",userNumber);
		//获取见证人信息
//		List<EquPonWitnessTeamEntity_HI> teamEntityHiList = equPonItWitnessTeamDAO_HI.findList("FROM EquPonItWitnessTeamEntity_HI WHERE projectId = :projectId and ( " +
//				"defaultMember = :userNumber or witnessMember = :userNumber" +
//				")",hasMap);

		/*发送邮件给owner*/
		//邮件内容
		String mailBody = CommonUtils.rejectWitnessMailContent(jsonParam.getString("projectLeaderName"), jsonParam.getString("projectName"),"提交");
		JSONObject paramsJson = new JSONObject();
		paramsJson.put("userId", jsonParam.getInteger("createdBy"));
		JSONObject resultJson = ResultUtils.getServiceResult("http://1002-saaf-api-gateway/api/ttadataServer" + "/ttadata/ttaBaseDataService/v2/getUserInfo", paramsJson);
		if (resultJson.get("email") != null) {
			EmailUtil.sendInMail("报价资料开启见证", mailBody,resultJson.getString("email"));
		}

		return returnJson;
	}

	@Override
	public Pagination<EquPonInformationRejectionEntity_HI> findRejectionReason(String params) {
		JSONObject jsonParam = parseObject(params);
		if (jsonParam.get("informationId") != null) {
			String queryString = "from EquPonInformationRejectionEntity_HI where informationId = "+ jsonParam.getInteger("informationId") + " order by creationDate desc";
			Pagination<EquPonInformationRejectionEntity_HI> returnList = equPonInformationRejectionDAO_HI.findPagination(queryString,1,100);
			return returnList;
		}else{
			return null;
		}
	}

	@Override
	public JSONObject savePonInvitation(String params ,int userId , String userNumber)  throws  Exception{
		JSONObject jsonParam = parseObject(params);
		JSONObject returnJson = new JSONObject();
		EquPonQuotationInformationEntity_HI jsonEntityHi = parseObject(jsonParam.toJSONString(), EquPonQuotationInformationEntity_HI.class);
		EquPonQuotationInformationEntity_HI saveEntityHi;
		String deptCode = jsonParam.getString("deptCode");
		if (jsonParam.get("informationId") != null) {
			saveEntityHi = equPonQuotationInformationDAO_HI.getById(jsonParam.getInteger("informationId"));
			PropertyUtils.copyProperties(saveEntityHi, jsonEntityHi);
			if(saveEntityHi.getCreatedBy()==null){
				saveEntityHi.setCreatedBy(userId);
				saveEntityHi.setCreationDate(new Date());
			}
		} else {
			saveEntityHi = jsonEntityHi;
			String code  = generateCodeServer.getSupplierSuspendCode("KQ", 4, true, true);
			saveEntityHi.setInformationCode(code);
			saveEntityHi.setCreatedBy(userId);
			saveEntityHi.setCreationDate(new Date());
		}
		String action = jsonParam.getString("action");
		EquPonProjectInfoEntity_HI projectInfoEntityHi = equPonProjectInfoDAO_HI.getById(saveEntityHi.getProjectId());
		JSONArray  supplierInvitationDataTable = jsonParam.getJSONArray("supplierInvitationDataTable");
		switch (action) {
			case "submit":
//				校验,如果当前版本不是最高的项目不给通过.如果是的就改状态
				String sourceProjectNumber = projectInfoEntityHi.getSourceProjectNumber();
				Map maps = new HashMap();
				maps.put("sourceProjectNumber",sourceProjectNumber);
				List<EquPonProjectInfoEntity_HI> projectInfoEntityHiList = equPonProjectInfoDAO_HI.findList("from EquPonProjectInfoEntity_HI where sourceProjectNumber = :sourceProjectNumber and projectStatus in ('10','20','30')",maps);
				Boolean canSubmit = false;
				try{
					EquPonProjectInfoEntity_HI maxProject = projectInfoEntityHiList.stream().max((stu1,stu2)->Integer.compare(Integer.parseInt(stu1.getProjectVersion()),Integer.parseInt(stu2.getProjectVersion()))).get();
					//如果不是最新的项目,直接出提示
					if(maxProject.getProjectNumber()!=projectInfoEntityHi.getProjectNumber()){
						canSubmit= true;
					}else{
						saveEntityHi.setInformationStatus("20");
						projectInfoEntityHi.setCanChangeFlag("N");
					}
				}catch (Exception e){
					saveEntityHi.setInformationStatus("20");
					projectInfoEntityHi.setCanChangeFlag("N");
				}
				if(canSubmit){
					Assert.notNull(null, "立项资料确认失败,项目不是最高版本");
					return null;
				}
				//校验当前选择的评分标准是否是批准状态
				if(jsonParam.get("standardsId")!=null){
					Integer standardsId =  jsonParam.getInteger("standardsId");
					EquPonStandardsHEntity_HI standardsHEntityHi = equPonStandardsHDAO_HI.getById(standardsId);
					if( !"30".equals(standardsHEntityHi.getStandardsStatus())){
						Assert.notNull(null, "立项资料确认失败,所选评分标准不是已批准状态");
					}
				}
				//验证供应商是否全部是已经批准的供应商

				String supplierName = "";
				for (Object o : supplierInvitationDataTable) {
					JSONObject file = JSONObject.parseObject(o.toString());
					Integer supplierId = file.getInteger("supplierId");
					EquPosSupplierInfoEntity_HI supplierInfoEntityHi = equPosSupplierInfoDAO_HI.getById(supplierId);
//					if(!"QUALIFIED".equals(supplierInfoEntityHi.getSupplierStatus())){
//						supplierName = supplierInfoEntityHi.getSupplierName()+" ,"+supplierName;
//					}
					Map newMap = new HashMap();
					newMap.put("supplierId",supplierId);
					newMap.put("deptCode",deptCode);
					List<EquPosSuppInfoWithDeptEntity_HI>  withDeptEntityHiList = equPosSuppInfoWithDeptDAO_HI.findList(" from EquPosSuppInfoWithDeptEntity_HI where supplierId = :supplierId and deptCode = :deptCode",newMap);
					for (EquPosSuppInfoWithDeptEntity_HI withDeptEntityHi : withDeptEntityHiList) {
						if(!"QUALIFIED".equals(withDeptEntityHi.getSupplierStatus())){
							supplierName = supplierInfoEntityHi.getSupplierName()+" ,"+supplierName;
						}
					}
				}
				if(!"".equals(supplierName)){
					Assert.notNull(null, "供应商 "+supplierName+" 不是合格供应商,不允许见证开启");
					return null;
				}
				/*发送邮件*/
				JSONArray witnessTeamDataTable = jsonParam.getJSONArray("witnessTeamDataTable");
				for (Object o : witnessTeamDataTable) {
					JSONObject witnessTeamData = JSONObject.parseObject(o.toString());
					if("N".equals(witnessTeamData.getString("remarkFlag"))){
						//已开启的见证人无需发送邮件  attribute1 =Y 表示已开启
						if(!"Y".equals(witnessTeamData.getString("attribute1"))){
							if (witnessTeamData.get("defaultMemberName") != null) {
								JSONObject paramsJson = new JSONObject();
								paramsJson.put("userName", witnessTeamData.getString("defaultMember"));
								JSONObject resultJson = ResultUtils.getServiceResult("http://1002-saaf-api-gateway/api/ttadataServer" + "/ttadata/ttaBaseDataService/v2/getUserInfoByUsrName", paramsJson);
								String mailBody = CommonUtils.witnessMailContent(resultJson.getString("personNameCn"), jsonParam.getString("projectName"));
								if (resultJson.get("email") != null) {
									EmailUtil.sendInMail("报价资料开启见证", mailBody,resultJson.getString("email"));
								}
							}
							if (witnessTeamData.get("witnessMemberName") != null) {
//							String mailBody = CommonUtils.witnessMailContent(witnessTeamData.getString("witnessMemberName"), jsonParam.getString("projectName"));
								JSONObject paramsJson = new JSONObject();
								paramsJson.put("userName", witnessTeamData.getString("witnessMember"));
								JSONObject resultJson = ResultUtils.getServiceResult("http://1002-saaf-api-gateway/api/ttadataServer" + "/ttadata/ttaBaseDataService/v2/getUserInfoByUsrName", paramsJson);
								String mailBody = CommonUtils.witnessMailContent(resultJson.getString("personNameCn"), jsonParam.getString("projectName"));
								if (resultJson.get("email") != null) {
									EmailUtil.sendInMail("报价资料开启见证", mailBody,resultJson.getString("email"));
								}
							}
						}
					}
				}
				break;
			case "over":
				//将立项 报价 资料开启状态全部变为终止 (次功能已废弃)

				projectInfoEntityHi.setOperatorUserId(userId);
				projectInfoEntityHi.setProjectStatus("50");
				equPonProjectInfoDAO_HI.save(projectInfoEntityHi);
				//报价
				Map qmap = new HashMap();
				qmap.put("projectNumber",saveEntityHi.getProjectNumber());
				List<EquPonQuotationInfoEntity_HI> quotationInfoEntityHi = equPonQuotationInfoDAO_HI.findList("" +
						"FROM EquPonQuotationInfoEntity_HI where projectNumber = :projectNumber" +
						"");
				for (EquPonQuotationInfoEntity_HI entityHi : quotationInfoEntityHi) {
					entityHi.setDocStatus("BREAK");
					entityHi.setOperatorUserId(userId);
				}
				equPonQuotationInfoDAO_HI.save(quotationInfoEntityHi);
				saveEntityHi.setInformationStatus("50");
				break;
            case "open":
                /*将见证人的状态改为已开启,如果所有见证人开启见证并且项目截止时间已经到了就把状态改成已批准*/
				Map hasMap = new HashMap();
				hasMap.put("projectId",saveEntityHi.getProjectId());
				hasMap.put("userNumber",userNumber);
                List<EquPonWitnessTeamEntity_HI> teamEntityHiList = equPonWitnessTeamDAO_HI.findList("FROM EquPonWitnessTeamEntity_HI WHERE projectId = :projectId and ( " +
						"defaultMember = :userNumber or witnessMember = :userNumber" +
						")",hasMap);
				for (EquPonWitnessTeamEntity_HI entityHi : teamEntityHiList) {
					entityHi.setOperatorUserId(userId);
					entityHi.setApproveDate(new Date());
					entityHi.setAttribute1("Y");
					//将监控报价开启人员信息保存起来
					String deptName = entityHi.getDeptName();
					if(deptName!=null){
						switch (deptName) {
                            case "QA":
                                saveEntityHi.setQaUserId(userId);
                                saveEntityHi.setQaUserNumber(userNumber);
                                break;
                            case "IA":
                                saveEntityHi.setIaUserId(userId);
                                saveEntityHi.setIaUserNumber(userNumber);
                                break;
                            case "Security":
                                saveEntityHi.setSecurityUserId(userId);
                                saveEntityHi.setSecurityUserNumber(userNumber);
                                break;
						}
					}
				}

				JSONObject paramJson = new JSONObject();
				paramJson.put("userId", userId);
				JSONObject userJson = ResultUtils.getServiceResult("http://1002-saaf-api-gateway/api/ttadataServer" + "/ttadata/ttaBaseDataService/v2/getUserInfo", paramJson);

				equPonWitnessTeamDAO_HI.save(teamEntityHiList);
				EquPonWitnessTeamEntity_HI_RO teamEntityHi = (EquPonWitnessTeamEntity_HI_RO)equPonWitnessTeamEntity_HI_RO.get("SELECT COUNT(1) projectId  FROM EQU_PON_WITNESS_TEAM WHERE project_Id = :projectId and ( " +
						"nvl(default_Member,'a') <> :userNumber AND nvl(witness_Member,'a') <> :userNumber" +
						") AND NVL(attribute1,'N') = 'N'",hasMap);
				String mailBody = CommonUtils.openWitnessMailContent(jsonParam.getString("projectLeaderName"), jsonParam.getString("projectName"),userJson.getString("personNameCn"),"提交");

				JSONObject paramsJson = new JSONObject();
				paramsJson.put("userId", jsonParam.getInteger("createdBy"));
				JSONObject resultJson = ResultUtils.getServiceResult("http://1002-saaf-api-gateway/api/ttadataServer" + "/ttadata/ttaBaseDataService/v2/getUserInfo", paramsJson);
//				发送邮件至创建人
				if (resultJson.get("email") != null) {
					EmailUtil.sendInMail("报价资料开启见证", mailBody,resultJson.getString("email"));
				}

				//如果当前见证人不是默认见证人,需要发送邮件给默认见证人
				EquPonWitnessTeamEntity_HI teamEntity = teamEntityHiList.get(0);
				if(userNumber.equals(teamEntity.getWitnessMember())){
					String defaultMember = teamEntity.getDefaultMember();
					paramsJson = new JSONObject();
					System.out.println();
					paramsJson.put("userName", defaultMember);
					JSONObject resultJson2 = ResultUtils.getServiceResult("http://1002-saaf-api-gateway/api/ttadataServer" + "/ttadata/ttaBaseDataService/v2/getUserInfoByUsrName", paramsJson);
					mailBody = CommonUtils.openWitnessMailContent(resultJson2.getString("personNameCn"), jsonParam.getString("projectName"),userJson.getString("personNameCn"),"指派见证开启");
					if (resultJson2.get("email") != null) {
						EmailUtil.sendInMail("报价资料开启见证", mailBody,resultJson.getString("email"));
					}
				}

				//当所有人都开启见证并且项目截止时间已经到了就把状态改成已批准，teamEntityHi.getProjectId()表示所有见证人都开启了
				if(teamEntityHi.getProjectId()==0){
					Date date = new Date();
					System.out.println();
					Date quotationDeadline = saveEntityHi.getQuotationDeadline();
					projectInfoEntityHi.setCanChangeFlag("N");
					equPonProjectInfoDAO_HI.save(projectInfoEntityHi);
//					判断当前时间是否到了项目截止时间
					if(quotationDeadline.before(date)){
						saveEntityHi.setInformationStatus("30");
						saveEntityHi.setFirstRoundTime(date);
						//发送邮件给创建人   submitWitnessMailContent
						mailBody = CommonUtils.submitWitnessMailContent(jsonParam.getString("projectLeaderName"), jsonParam.getString("projectName"));
						paramsJson = new JSONObject();
						System.out.println();
						paramsJson.put("userId", jsonParam.getInteger("createdBy"));
						resultJson = ResultUtils.getServiceResult("http://1002-saaf-api-gateway/api/ttadataServer" + "/ttadata/ttaBaseDataService/v2/getUserInfo", paramsJson);
						if (resultJson.get("email") != null) {
							EmailUtil.sendInMail("报价资料开启见证", mailBody,resultJson.getString("email"));
						}
						//发送邮件至开启人
						witnessTeamDataTable = jsonParam.getJSONArray("witnessTeamDataTable");
						for (Object o : witnessTeamDataTable) {

							JSONObject witnessTeamData = JSONObject.parseObject(o.toString());
                            if("N".equals(witnessTeamData.getString("remarkFlag"))) {
                                if (witnessTeamData.get("defaultMemberName") != null) {
//                                    mailBody = CommonUtils.submitWitnessMailContent(witnessTeamData.getString("defaultMemberName"), jsonParam.getString("projectName"));
                                    paramsJson = new JSONObject();
									System.out.println();
                                    paramsJson.put("userName", witnessTeamData.getString("defaultMember"));
                                    resultJson = ResultUtils.getServiceResult("http://1002-saaf-api-gateway/api/ttadataServer" + "/ttadata/ttaBaseDataService/v2/getUserInfoByUsrName", paramsJson);
									mailBody = CommonUtils.submitWitnessMailContent(resultJson.getString("personNameCn"), jsonParam.getString("projectName"));
                                    if (resultJson.get("email") != null) {
                                        EmailUtil.sendInMail("报价资料开启见证", mailBody,resultJson.getString("email"));
                                    }
                                }
                                if (witnessTeamData.get("witnessMemberName") != null) {
//                                    mailBody = CommonUtils.submitWitnessMailContent(witnessTeamData.getString("witnessMemberName"), jsonParam.getString("projectName"));
                                    paramsJson = new JSONObject();
									System.out.println();
                                    paramsJson.put("userName", witnessTeamData.getString("witnessMember"));
                                    resultJson = ResultUtils.getServiceResult("http://1002-saaf-api-gateway/api/ttadataServer" + "/ttadata/ttaBaseDataService/v2/getUserInfoByUsrName", paramsJson);
									mailBody = CommonUtils.submitWitnessMailContent(resultJson.getString("personNameCn"), jsonParam.getString("projectName"));
                                    if (resultJson.get("email") != null) {
                                        EmailUtil.sendInMail("报价资料开启见证", mailBody,resultJson.getString("email"));
                                    }
                                }
                            }
						}
					}else{
						saveEntityHi.setInformationStatus("60");
						saveEntityHi.setFirstRoundTime(saveEntityHi.getQuotationDeadline());
					}
				}
                break;
			case "openAll":

				//验证供应商是否全部是已经批准的供应商

				supplierName = "";
				for (Object o : supplierInvitationDataTable) {
					JSONObject file = JSONObject.parseObject(o.toString());
					Integer supplierId = file.getInteger("supplierId");
					EquPosSupplierInfoEntity_HI supplierInfoEntityHi = equPosSupplierInfoDAO_HI.getById(supplierId);
					Map newMap = new HashMap();
					newMap.put("supplierId",supplierId);
					newMap.put("deptCode",deptCode);
					List<EquPosSuppInfoWithDeptEntity_HI>  withDeptEntityHiList = equPosSuppInfoWithDeptDAO_HI.findList(" from EquPosSuppInfoWithDeptEntity_HI where supplierId = :supplierId and deptCode = :deptCode",newMap);
					for (EquPosSuppInfoWithDeptEntity_HI withDeptEntityHi : withDeptEntityHiList) {
						if(!"QUALIFIED".equals(withDeptEntityHi.getSupplierStatus())){
							supplierName = supplierInfoEntityHi.getSupplierName()+" ,"+supplierName;
						}
					}
				}
				if(!"".equals(supplierName)){
					Assert.notNull(null, "供应商 "+supplierName+" 不是合格供应商,不允许见证开启");
					return null;
				}

				//若截止时间已过，报价资料开启单据状态跳转为“已批准”，若截止时间未到，报价资料开启单据状态跳转为“已开启”
				Date quotationDeadline = saveEntityHi.getQuotationDeadline();
				Date date = new Date();
				if(quotationDeadline.before(date)){
					saveEntityHi.setInformationStatus("30");
				}else{
					saveEntityHi.setInformationStatus("60");
				}
				break;
			case "cancel":saveEntityHi.setInformationStatus("50");break;
			case "reject":
				saveEntityHi.setInformationStatus("40");
				//发送邮件
				mailBody = CommonUtils.rejectWitnessMailContent(jsonParam.getString("projectLeaderName"), jsonParam.getString("projectName"),"提交");
				paramsJson = new JSONObject();
				paramsJson.put("userId", jsonParam.getInteger("createdBy"));
				resultJson = ResultUtils.getServiceResult("http://1002-saaf-api-gateway/api/ttadataServer" + "/ttadata/ttaBaseDataService/v2/getUserInfo", paramsJson);
				if (resultJson.get("email") != null) {
					EmailUtil.sendInMail("报价资料开启见证", mailBody,resultJson.getString("email"));
				}
				break;
		}
		saveEntityHi.setOperatorUserId(userId);
		//将评分标准反写到立项表
		EquPonProjectInfoEntity_HI ponProjectInfoEntityHi = equPonProjectInfoDAO_HI.getById(saveEntityHi.getProjectId());

		ponProjectInfoEntityHi.setScoringStandard(saveEntityHi.getStandardsCode());
		ponProjectInfoEntityHi.setOperatorUserId(userId);
		equPonProjectInfoDAO_HI.save(ponProjectInfoEntityHi);
		equPonQuotationInformationDAO_HI.save(saveEntityHi);
		returnJson.put("headerData",saveEntityHi);

		return returnJson;
	}


	@Override
	public JSONObject savePonItInvitation(JSONObject jsonStr ,String params ,int userId , String userNumber)  throws  Exception{
		JSONObject jsonParam = parseObject(params);
		JSONObject returnJson = new JSONObject();
		EquPonQuotationInformationEntity_HI jsonEntityHi = parseObject(jsonParam.toJSONString(), EquPonQuotationInformationEntity_HI.class);
		EquPonQuotationInformationEntity_HI saveEntityHi;
		JSONArray scoringDetailInfoArray = jsonStr.getJSONArray("scoringDetailTable") == null ? new JSONArray() : jsonParam.getJSONArray("scoringDetailTable");

		String deptCode = jsonParam.getString("deptCode");
		String fullPriceTag = jsonParam.getString("fullPriceTag");
		boolean saveScoringDetailFlag = false;
		if (jsonParam.get("informationId") != null) {
			saveEntityHi = equPonQuotationInformationDAO_HI.getById(jsonParam.getInteger("informationId"));
			PropertyUtils.copyProperties(saveEntityHi, jsonEntityHi);
			if(saveEntityHi.getCreatedBy()==null){
				saveEntityHi.setCreatedBy(userId);
				saveEntityHi.setCreationDate(new Date());
			}
		} else {
			saveEntityHi = jsonEntityHi;
			String code  = generateCodeServer.getSupplierSuspendCode("KQ", 4, true, true);
			if(null != fullPriceTag && "Y".equals(fullPriceTag)){
				Integer scoringId = equPonItScoringInfoServer.getScoringId();
				saveEntityHi.setScoringId(scoringId);
			}
			saveEntityHi.setInformationCode(code);
			saveEntityHi.setCreatedBy(userId);
			saveEntityHi.setCreationDate(new Date());
			saveScoringDetailFlag = true;
		}

		String action = jsonParam.getString("action");

		EquPonItProjectInfoEntity_HI projectInfoEntityHi = equPonItProjectInfoDAO_HI.getById(saveEntityHi.getProjectId());
		JSONArray  supplierInvitationDataTable = jsonParam.getJSONArray("supplierInvitationDataTable");
		switch (action) {
			case "submit":
//				立项资料确认的时候做校验,如果当前版本不是最高的项目不给通过.如果是的就改状态

				String sourceProjectNumber = projectInfoEntityHi.getSourceProjectNumber();
				Map maps = new HashMap();
				maps.put("sourceProjectNumber",sourceProjectNumber);
				List<EquPonItProjectInfoEntity_HI> projectInfoEntityHiList = equPonItProjectInfoDAO_HI.findList("from EquPonItProjectInfoEntity_HI where sourceProjectNumber = :sourceProjectNumber and projectStatus in ('10','20','30')",maps);
				Boolean canSubmit = false;
				EquPonItProjectInfoEntity_HI maxProject = null;
				try{
					maxProject = projectInfoEntityHiList.stream().max((stu1,stu2)->Integer.compare(Integer.parseInt(stu1.getProjectVersion()),Integer.parseInt(stu2.getProjectVersion()))).get();
					//如果不是最新的项目,直接出提示
					if(maxProject.getProjectNumber()!=projectInfoEntityHi.getProjectNumber()){
						canSubmit= true;
					}else{
						saveEntityHi.setInformationStatus("20");
						projectInfoEntityHi.setCanChangeFlag("N");
					}
				}catch (Exception e){
					saveEntityHi.setInformationStatus("20");
					projectInfoEntityHi.setCanChangeFlag("N");
				}
				if(canSubmit){
					Assert.notNull(null, "立项资料确认失败,项目不是最高版本");
				}
				//校验当前选择的评分标准是否是批准状态
				if(jsonParam.get("standardsId")!=null){
					Integer standardsId =  jsonParam.getInteger("standardsId");
					EquPonStandardsHEntity_HI standardsHEntityHi = equPonStandardsHDAO_HI.getById(standardsId);
					if( !"30".equals(standardsHEntityHi.getStandardsStatus())){
						Assert.notNull(null, "立项资料确认失败,所选评分标准不是已批准状态");
					}

					Map standardsQueryMap = new HashMap();
					standardsQueryMap.put("standardsId",standardsId);
					standardsQueryMap.put("lineLv",1);
					standardsQueryMap.put("gradingDivision","2 价格");
					standardsQueryMap.put("weight",100);
					List<EquPonStandardsLEntity_HI> standardsLineList = equPonStandardsLDAO_HI.findByProperty(standardsQueryMap);
					if("Y".equals(maxProject.getFullPriceTag())){
						if(standardsLineList == null || standardsLineList.size() == 0){
							Assert.notNull(null, "立项资料确认失败,所选评分标准价格栏权重非100%");
						}
					}else{
						if(standardsLineList != null && standardsLineList.size() > 0){
							Assert.notNull(null, "立项资料确认失败,所选评分标准非价格权重不能为0");
						}
					}
				}
				//验证供应商是否全部是已经批准的供应商

				String supplierName = "";
				for (Object o : supplierInvitationDataTable) {
					JSONObject file = JSONObject.parseObject(o.toString());
					Integer supplierId = file.getInteger("supplierId");
					EquPosSupplierInfoEntity_HI supplierInfoEntityHi = equPosSupplierInfoDAO_HI.getById(supplierId);
//					if("TEMPLATE".equals(supplierInfoEntityHi.getSupplierStatus())){
//						supplierName = supplierInfoEntityHi.getSupplierName()+" ,"+supplierName;
//					}
					Map newMap = new HashMap();
					newMap.put("supplierId",supplierId);
					newMap.put("deptCode",deptCode);
					List<EquPosSuppInfoWithDeptEntity_HI>  withDeptEntityHiList = equPosSuppInfoWithDeptDAO_HI.findList(" from EquPosSuppInfoWithDeptEntity_HI where supplierId = :supplierId and deptCode = :deptCode",newMap);
					for (EquPosSuppInfoWithDeptEntity_HI withDeptEntityHi : withDeptEntityHiList) {
						if(!"QUALIFIED".equals(withDeptEntityHi.getSupplierStatus())){
							supplierName = supplierInfoEntityHi.getSupplierName()+" ,"+supplierName;
						}
					}
				}
				if(!"".equals(supplierName)){
					Assert.notNull(null, "供应商 "+supplierName+" 不是合格供应商,不允许见证开启");
//					throw new Exception("供应商 "+supplierName+" 不是合格供应商,不允许见证开启");
				}

				//保存评分小组
				JSONArray scoringTeamInfoArray = jsonStr.getJSONArray("scoringTeamDataTable") == null ? new JSONArray() : jsonParam.getJSONArray("scoringTeamDataTable");
				for(int i = 0; i < scoringTeamInfoArray.size(); i++){
					JSONObject scoringTeamInfo = getParamsJSONObject(jsonStr,scoringTeamInfoArray.getJSONObject(i));
					scoringTeamInfo.put("projectId",maxProject.getProjectId());
					equPonItScoringTeamServer.saveOrUpdate(scoringTeamInfo);
				}

				//发送邮件
				JSONArray witnessTeamDataTable = jsonParam.getJSONArray("witnessTeamDataTable");
				for (Object o : witnessTeamDataTable) {
					JSONObject witnessTeamData = JSONObject.parseObject(o.toString());
					if("N".equals(witnessTeamData.getString("remarkFlag"))){
						//已开启的见证人无需发送邮件
						if(!"Y".equals(witnessTeamData.getString("attribute1"))){
							if (witnessTeamData.get("defaultMemberName") != null) {
//							String mailBody = CommonUtils.witnessMailContent(witnessTeamData.getString("defaultMemberName"), jsonParam.getString("projectName"));
								JSONObject paramsJson = new JSONObject();
								paramsJson.put("userName", witnessTeamData.getString("defaultMember"));
								JSONObject resultJson = ResultUtils.getServiceResult("http://1002-saaf-api-gateway/api/ttadataServer" + "/ttadata/ttaBaseDataService/v2/getUserInfoByUsrName", paramsJson);
								String mailBody = CommonUtils.witnessMailContent(resultJson.getString("personNameCn"), jsonParam.getString("projectName"));
								if (resultJson.get("email") != null) {
									EmailUtil.sendInMail("报价资料开启见证", mailBody,resultJson.getString("email"));
								}
							}
							if (witnessTeamData.get("witnessMemberName") != null) {
//							String mailBody = CommonUtils.witnessMailContent(witnessTeamData.getString("witnessMemberName"), jsonParam.getString("projectName"));
								JSONObject paramsJson = new JSONObject();
								paramsJson.put("userName", witnessTeamData.getString("witnessMember"));
								JSONObject resultJson = ResultUtils.getServiceResult("http://1002-saaf-api-gateway/api/ttadataServer" + "/ttadata/ttaBaseDataService/v2/getUserInfoByUsrName", paramsJson);
								String mailBody = CommonUtils.witnessMailContent(resultJson.getString("personNameCn"), jsonParam.getString("projectName"));
								if (resultJson.get("email") != null) {
									EmailUtil.sendInMail("报价资料开启见证", mailBody,resultJson.getString("email"));
								}
							}
						}
					}
				}
				break;
			case "open":
				//将见证人的状态改为已经审批,如果所有见证人都审批完,改状态为已批准
				Map hasMap = new HashMap();
				hasMap.put("projectId",saveEntityHi.getProjectId());
				hasMap.put("userNumber",userNumber);
				List<EquPonItWitnessTeamEntity_HI> teamEntityHiList = equPonItWitnessTeamDAO_HI.findList("FROM EquPonItWitnessTeamEntity_HI WHERE projectId = :projectId and ( " +
						"defaultMember = :userNumber or witnessMember = :userNumber" +
						")",hasMap);
				String unionDeptmentName = jsonParam.getString("unionDeptmentName");
				BigDecimal projectPurchaseAmount = jsonParam.getBigDecimal("projectPurchaseAmount");
				String type = jsonParam.getString("type");
				System.out.println("联合部门名称：【" + unionDeptmentName + "】");
				System.out.println("项目采购金额：【" + projectPurchaseAmount + "】");
				System.out.println("见证小组查询参数：【" + type + "】");
				String witnessType = "";
				//采购金额大于2.4M
				if(type.contains("GT")){
					witnessType = " and witness_type in ('1','2') ";
				}
				//有关联部门
				if(type.contains("U")){
					witnessType = " and witness_type in ('1','3') ";
				}
				//采购金额大于2.4M,且有关联部门
				if(type.contains("GTU")){
					witnessType = " and witness_type in ('1','2','3') ";
				}
				if(!type.contains("GT") && !type.contains("U") && !type.contains("GTU")){
					witnessType = " and witness_type = '1' ";
				}
				System.out.println("见证小组类型：【" + witnessType + "】");
				for (EquPonItWitnessTeamEntity_HI entityHi : teamEntityHiList) {
					entityHi.setOperatorUserId(userId);
					entityHi.setApproveDate(new Date());
					entityHi.setAttribute1("Y");
					//将监控报价记录保存起来
					String deptName = entityHi.getDeptName();
					if(deptName!=null){
						switch (deptName) {
//						    userNumber
							case "QA":
								saveEntityHi.setQaUserId(userId);
								saveEntityHi.setQaUserNumber(userNumber);
								break;
							case "IA":
								saveEntityHi.setIaUserId(userId);
								saveEntityHi.setIaUserNumber(userNumber);
								break;
							case "Security":
								saveEntityHi.setSecurityUserId(userId);
								saveEntityHi.setSecurityUserNumber(userNumber);
								break;
						}
					}
				}

				JSONObject paramJson = new JSONObject();
				paramJson.put("userId", userId);
				JSONObject userJson = ResultUtils.getServiceResult("http://1002-saaf-api-gateway/api/ttadataServer" + "/ttadata/ttaBaseDataService/v2/getUserInfo", paramJson);

				equPonItWitnessTeamDAO_HI.save(teamEntityHiList);
				hasMap.put("witnessType",witnessType);

				EquPonItWitnessTeamEntity_HI_RO teamEntityHi = (EquPonItWitnessTeamEntity_HI_RO)equPonItWitnessTeamEntity_HI_RO.get("SELECT COUNT(1) projectId  FROM EQU_PON_IT_WITNESS_TEAM WHERE project_Id = " + saveEntityHi.getProjectId() + witnessType + " and ( " +
						"nvl(default_Member,'a') <> '" + userNumber + "' AND nvl(witness_Member,'a') <> '" + userNumber + "') AND NVL(attribute1,'N') = 'N'");

//				EquPonItWitnessTeamEntity_HI_RO teamEntityHi = (EquPonItWitnessTeamEntity_HI_RO)equPonItWitnessTeamEntity_HI_RO.get("SELECT COUNT(1) projectId  FROM EQU_PON_IT_WITNESS_TEAM WHERE witness_type = :witnessType and project_Id = :projectId and ( " +
//						"nvl(default_member,'a') <> :userNumber" +
//						") AND NVL(attribute1,'N') = 'N' and (witness_Member is not null or default_member is not null)",hasMap);
				String mailBody = CommonUtils.openWitnessMailContent(jsonParam.getString("projectLeaderName"), jsonParam.getString("projectName"),userJson.getString("personNameCn"),"提交");

				JSONObject paramsJson = new JSONObject();
				paramsJson.put("userId", jsonParam.getInteger("createdBy"));
				JSONObject resultJson = ResultUtils.getServiceResult("http://1002-saaf-api-gateway/api/ttadataServer" + "/ttadata/ttaBaseDataService/v2/getUserInfo", paramsJson);
				if (resultJson.get("email") != null) {
					EmailUtil.sendInMail("报价资料开启见证", mailBody,resultJson.getString("email"));
				}

				//如果当前见证人不是默认见证人,需要发送邮件给默认见证人
				EquPonItWitnessTeamEntity_HI teamEntity = teamEntityHiList.get(0);
				if(userNumber.equals(teamEntity.getWitnessMember())){
					String defaultMember = teamEntity.getDefaultMember();
					paramsJson = new JSONObject();
					paramsJson.put("userName", defaultMember);
					JSONObject resultJson2 = ResultUtils.getServiceResult("http://1002-saaf-api-gateway/api/ttadataServer" + "/ttadata/ttaBaseDataService/v2/getUserInfoByUsrName", paramsJson);
					mailBody = CommonUtils.openWitnessMailContent(resultJson2.getString("personNameCn"), jsonParam.getString("projectName"),userJson.getString("personNameCn"),"指派见证开启");
					if (resultJson2.get("email") != null) {
						EmailUtil.sendInMail("报价资料开启见证", mailBody,resultJson.getString("email"));
					}
				}

//				boolean isAllOpen = false;
//				System.out.println("见证类型：【" + witnessType + "】");
//				//当witnessType为GT(项目采购金额大于2400000),需要IA/SECURITY开启
//				if("GT".equals(witnessType)){
//					System.out.println("step111111111111111111111111111111");
//					System.out.println("witnessCount：" + teamEntityHi.getProjectId());
//					if(teamEntityHi.getProjectId()==0){
//						System.out.println("step222222222222222222222222222222");
//						isAllOpen = true;
//					}
//				}
//				if("LT".equals(witnessType)){
//					if(unionDeptmentName != null && !"".equals(unionDeptmentName)){
//						if(teamEntityHi.getProjectId()==0){
//							isAllOpen = true;
//						}
//					}else{
//						if(teamEntityHi.getProjectId()==1){
//							isAllOpen = true;
//						}
//					}
//				}
				System.out.println("isAllOpen：【" + teamEntityHi.getProjectId() + "】");
				//当所有人都开启见证并且项目截止时间已经到了  就把状态改成已批准
				if(teamEntityHi.getProjectId()==0){
					Date date = new Date();
					Date quotationDeadline = saveEntityHi.getQuotationDeadline();
					projectInfoEntityHi.setCanChangeFlag("N");
					equPonItProjectInfoDAO_HI.save(projectInfoEntityHi);
					if(quotationDeadline.before(date)){
						saveEntityHi.setInformationStatus("30");
						saveEntityHi.setFirstRoundTime(date);
						//发送邮件给开启人  submitWitnessMailContent
						mailBody = CommonUtils.submitWitnessMailContent(jsonParam.getString("projectLeaderName"), jsonParam.getString("projectName"));
						paramsJson = new JSONObject();
						paramsJson.put("userId", jsonParam.getInteger("createdBy"));
						resultJson = ResultUtils.getServiceResult("http://1002-saaf-api-gateway/api/ttadataServer" + "/ttadata/ttaBaseDataService/v2/getUserInfo", paramsJson);
						if (resultJson.get("email") != null) {
							EmailUtil.sendInMail("报价资料开启见证", mailBody,resultJson.getString("email"));
						}
						//发送邮件至开启人
						witnessTeamDataTable = jsonParam.getJSONArray("witnessTeamDataTable");
						for (Object o : witnessTeamDataTable) {

							JSONObject witnessTeamData = JSONObject.parseObject(o.toString());
							if("N".equals(witnessTeamData.getString("remarkFlag"))) {
								if (witnessTeamData.get("defaultMemberName") != null) {
//                                    mailBody = CommonUtils.submitWitnessMailContent(witnessTeamData.getString("defaultMemberName"), jsonParam.getString("projectName"));
									paramsJson = new JSONObject();
									paramsJson.put("userName", witnessTeamData.getString("defaultMember"));
									resultJson = ResultUtils.getServiceResult("http://1002-saaf-api-gateway/api/ttadataServer" + "/ttadata/ttaBaseDataService/v2/getUserInfoByUsrName", paramsJson);
									mailBody = CommonUtils.submitWitnessMailContent(resultJson.getString("personNameCn"), jsonParam.getString("projectName"));
									if (resultJson.get("email") != null) {
										EmailUtil.sendInMail("报价资料开启见证", mailBody,resultJson.getString("email"));
									}
								}
								if (witnessTeamData.get("witnessMemberName") != null) {
//                                    mailBody = CommonUtils.submitWitnessMailContent(witnessTeamData.getString("witnessMemberName"), jsonParam.getString("projectName"));
									paramsJson = new JSONObject();
									paramsJson.put("userName", witnessTeamData.getString("witnessMember"));
									resultJson = ResultUtils.getServiceResult("http://1002-saaf-api-gateway/api/ttadataServer" + "/ttadata/ttaBaseDataService/v2/getUserInfoByUsrName", paramsJson);
									mailBody = CommonUtils.submitWitnessMailContent(resultJson.getString("personNameCn"), jsonParam.getString("projectName"));
									if (resultJson.get("email") != null) {
										EmailUtil.sendInMail("报价资料开启见证", mailBody,resultJson.getString("email"));
									}
								}
							}
						}
						if("Y".equals(projectInfoEntityHi.getFullPriceTag())){
							quotationScoreCalculate(saveEntityHi.getScoringId(),saveEntityHi.getProjectId(),saveEntityHi.getStandardsId());
						}
					}else{
						saveEntityHi.setInformationStatus("60");
						saveEntityHi.setFirstRoundTime(saveEntityHi.getQuotationDeadline());
					}
				}

				//将见证开启的人保存起来f
				break;
			case "cancel":saveEntityHi.setInformationStatus("50");break;
			case "reject":
				saveEntityHi.setInformationStatus("40");
				//发送邮件至
//				rejectWitnessMailContent(String contactName, String projectName)
				mailBody = CommonUtils.rejectWitnessMailContent(jsonParam.getString("projectLeaderName"), jsonParam.getString("projectName"),"提交");
				paramsJson = new JSONObject();
				paramsJson.put("userId", jsonParam.getInteger("createdBy"));
				resultJson = ResultUtils.getServiceResult("http://1002-saaf-api-gateway/api/ttadataServer" + "/ttadata/ttaBaseDataService/v2/getUserInfo", paramsJson);
				if (resultJson.get("email") != null) {
					EmailUtil.sendInMail("报价资料开启见证", mailBody,resultJson.getString("email"));
				}
				break;
		}
		saveEntityHi.setOperatorUserId(userId);
		//将评分标准反写到立项表
		EquPonItProjectInfoEntity_HI ponProjectInfoEntityHi = equPonItProjectInfoDAO_HI.getById(saveEntityHi.getProjectId());

		ponProjectInfoEntityHi.setScoringStandard(saveEntityHi.getStandardsCode());
		ponProjectInfoEntityHi.setOperatorUserId(userId);
		equPonItProjectInfoDAO_HI.save(ponProjectInfoEntityHi);
		saveEntityHi = equPonQuotationInformationDAO_HI.saveEntity(saveEntityHi);
//		if(jsonParam.get("supplierTable")!=null&&jsonParam.getJSONArray("supplierTable").size()>0){
//			JSONArray supplierTable = jsonParam.getJSONArray("supplierTable");
//			for(int i=0; i<supplierTable.size(); i++) {
//				JSONObject jsonLine =  supplierTable.getJSONObject(i);
//				Integer id = jsonLine.getInteger("invitationId");
//				EquPonSupplierInvitationEntity_HI supplierInvitationEntityHi = equPonSupplierInvitationDAO_HI.getById(id);
//				supplierInvitationEntityHi.setIsQuit(jsonLine.getString("isQuit"));
//                System.out.println(jsonLine.getString("reason"));
//				supplierInvitationEntityHi.setReason(jsonLine.getString("reason"));
//                supplierInvitationEntityHi.setOperatorUserId(userId);
//				supplierInvitationEntityHi.setProjectVersion("0");
//				equPonSupplierInvitationDAO_HI.save(supplierInvitationEntityHi);
//			}
//		}

		if(null != saveEntityHi.getScoringId() && saveScoringDetailFlag){
			for(int i = 0; i < scoringDetailInfoArray.size(); i++){
				JSONObject scoringDetailInfo = getParamsJSONObject(jsonStr,scoringDetailInfoArray.getJSONObject(i));
				Object scoringDetailId = scoringDetailInfo.get("scoringDetailId");

				if(!scoringDetailInfo.containsKey("scoringDetailId") || "".equals(scoringDetailId)){
					//新增保存
					scoringDetailInfo.put("scoringId",saveEntityHi.getScoringId());
				}
				equPonItScoringDetailServer.saveOrUpdate(scoringDetailInfo);
			}
		}

		returnJson.put("headerData",saveEntityHi);

		return returnJson;
	}

	@Override
	public JSONObject findItBidSupplierList(String params) {
		JSONObject jsonObject = parseObject(params);

		String nonPriceFlag = "N";
		String priceFlag = "N";
		Integer projectId = jsonObject.getInteger("projectId");
		Map queryMap = new HashMap();
		queryMap.put("projectId",projectId);
		List<EquPonItProjectInfoEntity_HI> projectList = equPonItProjectInfoDAO_HI.findByProperty(queryMap);
		List<EquPonItScoringInfoEntity_HI> scoringList2 = equPonItScoringInfoDAO_HI.findByProperty(queryMap);
		List<EquPonQuotationInformationEntity_HI> informationList = equPonQuotationInformationDAO_HI.findByProperty(queryMap);
		System.out.println("【projectId】:" + projectId);
		System.out.println("【projectListSize】:" + projectList.size());
		if(null != projectList && projectList.size() > 0){
			EquPonItProjectInfoEntity_HI projectEntity = projectList.get(0);
			System.out.println("【FullPriceTag】:" + projectEntity.getFullPriceTag());
			if("Y".equals(projectEntity.getFullPriceTag())){
				if(null != informationList && informationList.size() > 0){
					EquPonQuotationInformationEntity_HI informationEntity = informationList.get(0);
					if("30".equals(informationEntity.getInformationStatus())){
						//价格文件
						priceFlag = "Y";
					}
				}
			}else{
				System.out.println("【informationList】:" + informationList.size());
				System.out.println("【scoringList2】:" + scoringList2.size());
				if(null != informationList && informationList.size() > 0){
					EquPonQuotationInformationEntity_HI informationEntity = informationList.get(0);
					System.out.println("【InformationStatus】:" + informationEntity.getInformationStatus());
					if("30".equals(informationEntity.getInformationStatus())){
						System.out.println("1111111111111111111111111111");
						//非价格文件
						nonPriceFlag = "Y";
					}
				}
				if(null != scoringList2 && scoringList2.size() > 0){
					EquPonItScoringInfoEntity_HI scoringEntity = scoringList2.get(0);
					if("30".equals(scoringEntity.getScoringStatus())){
						//价格文件
						priceFlag = "Y";
					}
				}
			}
		}



		JSONObject json = new JSONObject();
		json.put("fileType","NonPriceSelectFile");
		json.put("projectId",projectId);
		json.put("selectedFlag","Y");
		JSONObject json2 = new JSONObject();
		json2.put("fileType","NonPriceInsertFile");
		json2.put("projectId",projectId);
		JSONObject json3 = new JSONObject();
		json3.put("fileType","PriceSelectFile");
		json3.put("projectId",projectId);
		json3.put("selectedFlag","Y");
		JSONObject json4 = new JSONObject();
		json4.put("fileType","PriceInsertFile");
		json4.put("projectId",projectId);
		//获取评分状态，评分状态价格文件的下载
		String scoringStatus = null;
		Map scoringMap = new HashMap();
		scoringMap.put("informationId", jsonObject.getInteger("informationId"));
		List<EquPonItScoringInfoEntity_HI> scoringList = equPonItScoringInfoDAO_HI.findList(" from EquPonItScoringInfoEntity_HI where informationId = :informationId ", scoringMap);
		if (scoringList.size() > 0) {
			scoringStatus = scoringList.get(0).getScoringStatus();
		}
		//获取项目附件
		JSONArray fileArr = new JSONArray();
		System.out.println("【priceFlag】:" + priceFlag);
		System.out.println("【nonPriceFlag】:" + nonPriceFlag);
		if("Y".equals(priceFlag)){
			JSONObject result3 =equPonItProjectAttachmentServer.findItProjectAttachment(json3,1,10000);
			JSONObject result4 =equPonItProjectAttachmentServer.findItProjectAttachment(json4,1,10000);
			System.out.println("【result3Size】:" + result3.getJSONArray("data").size());
			System.out.println(result3);
			System.out.println("【result4Size】:" + result4.getJSONArray("data").size());
			System.out.println(result4);
			fileArr.addAll(result3.getJSONArray("data"));
			fileArr.addAll(result4.getJSONArray("data"));
		}
		if("Y".equals(nonPriceFlag)){
			JSONObject result =equPonItProjectAttachmentServer.findItProjectAttachment(json,1,10000);
			JSONObject result2 =equPonItProjectAttachmentServer.findItProjectAttachment(json2,1,10000);
			System.out.println("【resultSize】:" + result.getJSONArray("data").size());
			System.out.println(result);
			System.out.println("【result2Size】:" + result2.getJSONArray("data").size());
			System.out.println(result2);
			fileArr.addAll(result.getJSONArray("data"));
			fileArr.addAll(result2.getJSONArray("data"));
		}
		System.out.println("【fileArr】:" + fileArr.size());
		System.out.println(fileArr);
//		JSONObject result  =equPonItProjectAttachmentServer.findItProjectAttachment(json,1,10000);
//		JSONObject result2  =equPonItProjectAttachmentServer.findItProjectAttachment(json2,1,10000);
//		JSONObject result3  =equPonItProjectAttachmentServer.findItProjectAttachment(json3,1,10000);
//		JSONObject result4  =equPonItProjectAttachmentServer.findItProjectAttachment(json4,1,10000);
//		JSONArray fileArr = result.getJSONArray("data");
//		JSONArray fileArr2 = result2.getJSONArray("data");
//		JSONArray fileArr3 = result3.getJSONArray("data");
//		JSONArray fileArr4 = result4.getJSONArray("data");
//		fileArr.addAll(fileArr2);
//		fileArr.addAll(fileArr3);
//		fileArr.addAll(fileArr4);
		//获取立项所有供应商信息
		StringBuffer queryString = new StringBuffer(
				EquPonQuotationInformationEntity_HI_RO.QUERY_IT_SUPPLIER_SQL);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("projectNumber",jsonObject.getString("projectNumber"));
		queryString.append(" and p.project_Number = :projectNumber");
		queryString.append(" order by psi.invitation_id asc");
		List<EquPonQuotationInformationEntity_HI_RO> returnList = equPonQuotationInformationDAO_HI_RO.findList(queryString, map);
		//获取所有的供应商附件信息
		StringBuffer queryFileString = new StringBuffer(
				EquPonQuotationInformationEntity_HI_RO.QUERY_IT_FILE_SQL);
		List<EquPonQuotationInformationEntity_HI_RO> returnFlieList = equPonQuotationInformationDAO_HI_RO.findList(queryFileString, map);
		//组装数据
		JSONObject returnJson = new JSONObject();
		JSONArray supplierArr = new JSONArray();
		//循环供应商
		for (EquPonQuotationInformationEntity_HI_RO entityHiRo : returnList) {
			//循环项目附件  因为每个供应商都要有相同的项目附件
			JSONArray returnFileArr = new JSONArray();
			JSONObject supplierJson = new JSONObject();
			if(entityHiRo.getQuotationId()!=null){
				for (Object o : fileArr) {
					JSONObject file = JSONObject.parseObject(o.toString());
					//将供应商附件加进file里面去
					//将供应商附件表对应的数据抓取出来.要找的是报价ID 与项目附件ID一致的数据
					try{
//						List<EquPonQuotationInformationEntity_HI_RO> aList = returnFlieList.stream().filter(s -> file.getString("fileId").equals(s.getFileId().toString())).collect(Collectors.toList());
//System.out.println("【aListSize】:" + aList);
//System.out.println("【aListContent】:" + aList.get(0).toString());

						List<EquPonQuotationInformationEntity_HI_RO> setList = new ArrayList();
						for(int i = 0; i < returnFlieList.size();i++){
							EquPonQuotationInformationEntity_HI_RO row = returnFlieList.get(i);

							System.out.println("循环" + i+ ":" + file.getString("fileId"));
							System.out.println("循环" + i+ ":" + row.getFileId());
							System.out.println("循环" + i+ ":" + row.getQuotationId());
							System.out.println("循环" + i+ ":" + entityHiRo.getQuotationId().toString());

							if(file.getString("fileId").equals(row.getFileId().toString()) && entityHiRo.getQuotationId().toString().equals(row.getQuotationId().toString())){
								setList.add(row);
							}
						}

						System.out.println("【setListSize】" + setList);

//						List<EquPonQuotationInformationEntity_HI_RO> setList = aList.stream().filter(s -> entityHiRo.getQuotationId().toString().equals(s.getQuotationId().toString())).collect(Collectors.toList());
//System.out.println("【quotationID】:" + entityHiRo.getQuotationId());
//System.out.println("【setListSize】:" + setList);
//System.out.println("【setListContent】:" + setList.get(0).toString());
						file.put("supplierFilePath",setList.get(0).getFilePath());
						file.put("supplierFileName",setList.get(0).getSourceFileName());
						file.put("onceFilePath",setList.get(0).getOnceFilePath());
						file.put("onceFileName",setList.get(0).getOnceFileName());
						file.put("scoringStatus",scoringStatus);
						if(setList.get(0).getCreationDate()!=null){
							SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
							String dateString = formatter.format(setList.get(0).getCreationDate());
							file.put("upDate",dateString);
						}
						if(setList.get(0).getUpdateCreationDate()!=null){
							SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
							String dateString = formatter.format(setList.get(0).getUpdateCreationDate());

							file.put("updateDate",dateString);
						}
					}catch (Exception e){
						e.printStackTrace();
						System.out.println("没有数据"+e.getMessage());
					}
					returnFileArr.add(file);
				}
			}
//			判断供应商报价状态
			if("Y".equals(entityHiRo.getIsQuit())){
				if("R".equals(entityHiRo.getQuotationFlag())){
					entityHiRo.setSupplierName(entityHiRo.getSupplierName() +" (拒绝参与)" ) ;
				}else{
					entityHiRo.setSupplierName(entityHiRo.getSupplierName() +" (退出)" ) ;
				}
			}else if(entityHiRo.getDocStatus()==null){
				entityHiRo.setSupplierName(entityHiRo.getSupplierName() +" (未回应)" ) ;
			}else{
				//判断此供应商报价的状态  (已提交报价/报价中/报价超时)
				String breakFlag =entityHiRo.getBreakFlag();

				if("Y".equals(breakFlag)){
					entityHiRo.setSupplierName(entityHiRo.getSupplierName() +" (报价超时)" ) ;
				}else if("QUOTATION".equals(entityHiRo.getDocStatus())||"BARGAIN".equals(entityHiRo.getDocStatus())||"MODIFYING".equals(entityHiRo.getDocStatus())){
					entityHiRo.setSupplierName(entityHiRo.getSupplierName() +" (报价中)" ) ;
				}else if("COMMIT".equals(entityHiRo.getDocStatus())||"STOP".equals(entityHiRo.getDocStatus())){
					entityHiRo.setSupplierName(entityHiRo.getSupplierName() +" (已提交报价)" ) ;
				}else if("COMPLETE".equals(entityHiRo.getDocStatus())){
					entityHiRo.setSupplierName(entityHiRo.getSupplierName() +" (已完成)" ) ;
				}else if("BREAK".equals(entityHiRo.getDocStatus())){
					entityHiRo.setSupplierName(entityHiRo.getSupplierName() +" (终止)" ) ;
				}
			}

			supplierJson.put("supplierName",entityHiRo.getSupplierName());
			supplierJson.put("fileData",returnFileArr);
			supplierJson.put("quatationData",entityHiRo);
			supplierJson.put("scoringStatus",scoringStatus);
			supplierArr.add(supplierJson);
		}
		returnJson.put("status","S");
		returnJson.put("data",supplierArr);
		return returnJson;
	}


	@Override
	public JSONObject findBidSupplierList(String params) {
		JSONObject jsonObject = parseObject(params);
		JSONObject json = new JSONObject();
		json.put("fileType_neq","ProjectInfo");
		json.put("projectId",jsonObject.getInteger("projectId"));
		//获取评分状态，评分状态价格文件的下载
		String scoringStatus = null;
		Map scoringMap = new HashMap();
		scoringMap.put("informationId", jsonObject.getInteger("informationId"));
		List<EquPonScoringInfoEntity_HI> scoringList = equPonScoringInfoDAO_HI.findList(" from EquPonScoringInfoEntity_HI where informationId = :informationId ", scoringMap);
		if (scoringList.size() > 0) {
			scoringStatus = scoringList.get(0).getScoringStatus();
		}
		//获取项目附件
		JSONObject result  =equPonProjectAttachmentServer.findProjectAttachment(json,1,10000);
		JSONArray fileArr = result.getJSONArray("data");
		//获取立项所有供应商信息
		StringBuffer queryString = new StringBuffer(
				EquPonQuotationInformationEntity_HI_RO.QUERY_SUPPLIER_SQL);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("projectNumber",jsonObject.getString("projectNumber"));
		queryString.append(" and p.project_Number = :projectNumber");
		queryString.append(" order by psi.invitation_id asc");
		List<EquPonQuotationInformationEntity_HI_RO> returnList = equPonQuotationInformationDAO_HI_RO.findList(queryString, map);
	    //获取所有的供应商附件信息
        StringBuffer queryFileString = new StringBuffer(
                EquPonQuotationInformationEntity_HI_RO.QUERY_FILE_SQL);
        List<EquPonQuotationInformationEntity_HI_RO> returnFlieList = equPonQuotationInformationDAO_HI_RO.findList(queryFileString, map);
		//组装数据
        JSONObject returnJson = new JSONObject();
        JSONArray supplierArr = new JSONArray();
        //循环供应商
        for (EquPonQuotationInformationEntity_HI_RO entityHiRo : returnList) {
            //循环项目附件  因为每个供应商都要有相同的项目附件
            JSONArray returnFileArr = new JSONArray();
            JSONObject supplierJson = new JSONObject();
            if(entityHiRo.getQuotationId()!=null){
				for (Object o : fileArr) {
					JSONObject file = JSONObject.parseObject(o.toString());
					//将供应商附件加进file里面去
					//将供应商附件表对应的数据抓取出来.要找的是报价ID 与项目附件ID一致的数据
					try{
						List<EquPonQuotationInformationEntity_HI_RO> aList = returnFlieList.stream().filter(s -> file.getString("fileId").equals(s.getFileId().toString())).collect(Collectors.toList());

						List<EquPonQuotationInformationEntity_HI_RO> setList = aList.stream().filter(s -> entityHiRo.getQuotationId().toString().equals(s.getQuotationId().toString())).collect(Collectors.toList());
						file.put("supplierFilePath",setList.get(0).getFilePath());
						file.put("supplierFileName",setList.get(0).getSourceFileName());
						file.put("onceFilePath",setList.get(0).getOnceFilePath());
						file.put("onceFileName",setList.get(0).getOnceFileName());
                        file.put("scoringStatus",scoringStatus);
						if(setList.get(0).getCreationDate()!=null){
							SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
							String dateString = formatter.format(setList.get(0).getCreationDate());
							file.put("upDate",dateString);
						}
						if(setList.get(0).getUpdateCreationDate()!=null){
							SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
							String dateString = formatter.format(setList.get(0).getUpdateCreationDate());

							file.put("updateDate",dateString);
						}
					}catch (Exception e){
						System.out.println("没有数据"+e.getMessage());
					}
					returnFileArr.add(file);
				}
			}
//			判断供应商报价状态
            if("Y".equals(entityHiRo.getIsQuit())){
            	if("R".equals(entityHiRo.getQuotationFlag())){
					entityHiRo.setSupplierName(entityHiRo.getSupplierName() +" (拒绝参与)" ) ;
				}else{
					entityHiRo.setSupplierName(entityHiRo.getSupplierName() +" (退出)" ) ;
				}
			}else if(entityHiRo.getDocStatus()==null){
				entityHiRo.setSupplierName(entityHiRo.getSupplierName() +" (未回应)" ) ;
			}else{
				//判断此供应商报价的状态  (已提交报价/报价中/报价超时)
				String breakFlag =entityHiRo.getBreakFlag();

				if("Y".equals(breakFlag)){
					entityHiRo.setSupplierName(entityHiRo.getSupplierName() +" (报价超时)" ) ;
				}else if("QUOTATION".equals(entityHiRo.getDocStatus())||"BARGAIN".equals(entityHiRo.getDocStatus())||"MODIFYING".equals(entityHiRo.getDocStatus())){
					entityHiRo.setSupplierName(entityHiRo.getSupplierName() +" (报价中)" ) ;
				}else if("COMMIT".equals(entityHiRo.getDocStatus())||"STOP".equals(entityHiRo.getDocStatus())){
					entityHiRo.setSupplierName(entityHiRo.getSupplierName() +" (已提交报价)" ) ;
				}else if("COMPLETE".equals(entityHiRo.getDocStatus())){
					entityHiRo.setSupplierName(entityHiRo.getSupplierName() +" (已完成)" ) ;
				}else if("BREAK".equals(entityHiRo.getDocStatus())){
                    entityHiRo.setSupplierName(entityHiRo.getSupplierName() +" (终止)" ) ;
                }
			}

            supplierJson.put("supplierName",entityHiRo.getSupplierName());
            supplierJson.put("fileData",returnFileArr);
			supplierJson.put("quatationData",entityHiRo);
			supplierJson.put("scoringStatus",scoringStatus);
            supplierArr.add(supplierJson);
        }
        returnJson.put("status","S");
        returnJson.put("data",supplierArr);
		return returnJson;
	}

	@Override
	public void deleteInformation(JSONObject jsonObject, int userId) {
		Integer informationId = jsonObject.getInteger("informationId");
		equPonQuotationInformationDAO_HI.delete(informationId);
	}



	@Override
	public JSONObject saveItWitnessTeamData(String params, int userId, String userNumber) {
		JSONObject jsonObject = parseObject(params);
		JSONArray witnessTeamDataTable = jsonObject.getJSONArray("witnessTeamDataTable");
		for (Object o : witnessTeamDataTable) {
			JSONObject file = JSONObject.parseObject(o.toString());
			if(!"Y".equals(file.getString("remarkFlag"))){
				EquPonItWitnessTeamEntity_HI a = equPonItWitnessTeamDAO_HI.getById(file.getInteger("teamId"));
				a.setOperatorUserId(userId);
				a.setWitnessMember(file.getString("witnessMember"));
				a.setWitnessMemberName(file.getString("witnessMemberName"));
				a.setDefaultMember(file.getString("defaultMember"));
				a.setDefaultMemberName(file.getString("defaultMemberName"));
				equPonItWitnessTeamDAO_HI.save(a);
			}

		}

		if(jsonObject.get("scoringTeamDataTable")!=null){
			JSONArray scoringTeamDataTable = jsonObject.getJSONArray("scoringTeamDataTable");
			for(int i = 0; i < scoringTeamDataTable.size(); i++){
				JSONObject scoringTeamJson = scoringTeamDataTable.getJSONObject(i);
				scoringTeamJson.put("userId",userId);
				scoringTeamJson.put("varUserId",userId);
				scoringTeamJson.put("operatorUserId",userId);
				equPonItScoringTeamServer.saveOrUpdate(scoringTeamJson);
			}
		}
		return null;
	}



	@Override
    public JSONObject saveWitnessTeamData(String params, int userId, String userNumber) {
        JSONObject jsonObject = parseObject(params);
	    JSONArray witnessTeamDataTable = jsonObject.getJSONArray("witnessTeamDataTable");
        for (Object o : witnessTeamDataTable) {
			JSONObject file = JSONObject.parseObject(o.toString());
			if(!"Y".equals(file.getString("remarkFlag"))){
				EquPonWitnessTeamEntity_HI a = equPonWitnessTeamDAO_HI.getById(file.getInteger("teamId"));
				a.setOperatorUserId(userId);
				a.setWitnessMember(file.getString("witnessMember"));
				a.setWitnessMemberName(file.getString("witnessMemberName"));
				a.setDefaultMember(file.getString("defaultMember"));
				a.setDefaultMemberName(file.getString("defaultMemberName"));
				equPonWitnessTeamDAO_HI.save(a);
			}

        }

		if(jsonObject.get("scoringTeamDataTable")!=null){
			JSONArray scoringTeamDataTable = jsonObject.getJSONArray("scoringTeamDataTable");
			for (Object o : scoringTeamDataTable) {
				JSONObject file = JSONObject.parseObject(o.toString());
				EquPonScoringTeamEntity_HI a = equPonScoringTeamDAO_HI.getById(file.getInteger("teamId"));
				a.setOperatorUserId(userId);
				a.setMemberName(file.getString("memberName"));
				a.setMemberNumber(file.getString("memberNumber"));
				equPonScoringTeamDAO_HI.save(a);
			}
		}
        return null;
    }

	@Override
	public JSONObject updateSupplierQuotation(String params, int userId, String userNumber) {
		JSONObject jsonObject = parseObject(params);
		EquPonQuotationInfoEntity_HI saveEntity =  equPonQuotationInfoDAO_HI.getById(jsonObject.getInteger("quotationId"));
		saveEntity.setOperatorUserId(userId);
		saveEntity.setDocStatus("MODIFYING");
		equPonQuotationInfoDAO_HI.save(saveEntity);
		return null;

	}

	@Override
	public JSONObject updateSupplierQuotationIt(String params, int userId, String userNumber) {
		JSONObject jsonObject = parseObject(params);
		EquPonQuotationInfoItEntity_HI saveEntity =  equPonQuotationInfoItDAO_HI.getById(jsonObject.getInteger("quotationId"));
		saveEntity.setOperatorUserId(userId);
		saveEntity.setDocStatus("MODIFYING");
		equPonQuotationInfoItDAO_HI.save(saveEntity);
		return null;

	}

	@Override
	public Pagination<EquPonQuotationInformationEntity_HI_RO> findMaxProjectInfo(String params, Integer pageIndex, Integer pageRows) {
		JSONObject jsonParam = parseObject(params);
		jsonParam.remove("userName");
		StringBuffer sql = new StringBuffer(EquPonQuotationInformationEntity_HI_RO.QUERY_MAX_PROJECT_SQL);
		Map<String, Object> map = new HashMap<>();
		SaafToolUtils.parperHbmParam(EquPonProjectInfoEntity_HI_RO.class, jsonParam, sql, map);
		Pagination<EquPonQuotationInformationEntity_HI_RO> pagination = equPonQuotationInformationDAO_HI_RO.findPagination(sql, map, pageIndex, pageRows);
		return pagination;

	}

	@Override
	public Pagination<EquPonQuotationInformationEntity_HI_RO> findMaxItProjectInfo(String params, Integer pageIndex, Integer pageRows) {
		JSONObject jsonParam = parseObject(params);
		jsonParam.remove("userName");
		StringBuffer sql = new StringBuffer(EquPonQuotationInformationEntity_HI_RO.QUERY_MAX_IT_PROJECT_SQL);
		Map<String, Object> map = new HashMap<>();
		SaafToolUtils.parperHbmParam(EquPonProjectInfoEntity_HI_RO.class, jsonParam, sql, map);
		Pagination<EquPonQuotationInformationEntity_HI_RO> pagination = equPonQuotationInformationDAO_HI_RO.findPagination(sql, map, pageIndex, pageRows);
		return pagination;

	}

	@Override
	public void saveProjectSupplierQuit(JSONObject jsonObject,Integer userId) {

 		if(jsonObject.get("invitationId")!=null){
			//将所有的对应的项目的供应商的状态改为退出
			StringBuffer sql = new StringBuffer(EquPonQuotationInformationEntity_HI_RO.QUERY_PROJECT_SUPPLIER);
			Map<String, Object> map = new HashMap<>();
			map.put("invitationId",jsonObject.getInteger("invitationId"));
			List<EquPonQuotationInformationEntity_HI_RO> invitationDAOHiList = equPonQuotationInformationDAO_HI_RO.findList(sql, map);
			for (EquPonQuotationInformationEntity_HI_RO entityHi : invitationDAOHiList) {
				EquPonSupplierInvitationEntity_HI saveEo = equPonSupplierInvitationDAO_HI.getById(entityHi.getInvitationId());
				saveEo.setOperatorUserId(userId);
				saveEo.setIsQuit("Y");
				saveEo.setReason(jsonObject.getString("reason"));
				equPonSupplierInvitationDAO_HI.save(saveEo);
				Map<String, Object> maps = new HashMap<>(4);
				maps.put("sourceId", saveEo.getInvitationId());
				maps.put("supplierId", saveEo.getSupplierId());
				List<EquPonSupplierInvitationEntity_HI> invitationList = ponSupplierInvitationDao.findByProperty(maps);
				// 如果为空则没有修改立项，直接返回
				for (EquPonSupplierInvitationEntity_HI entityHi1 : invitationList) {
					entityHi1.setQuotationFlag("R");
					entityHi1.setReason(jsonObject.getString("reason"));
					entityHi1.setIsQuit("Y");
					entityHi1.setOperatorUserId(userId);
					ponSupplierInvitationDao.update(entityHi1);

				}
			}
			//将此供应商对应的所有报价改为终结
			sql = new StringBuffer(EquPonQuotationInformationEntity_HI_RO.QUERY_PROJECT_QUOTATION);
			map = new HashMap<>();
			map.put("projectId",jsonObject.getInteger("projectId"));
			map.put("supplierId",jsonObject.getInteger("supplierId"));
			List<EquPonQuotationInformationEntity_HI_RO> quotationList = equPonQuotationInformationDAO_HI_RO.findList(sql, map);
			for (EquPonQuotationInformationEntity_HI_RO entityHiRo : quotationList) {
				EquPonQuotationInfoEntity_HI saveEo = equPonQuotationInfoDAO_HI.getById(entityHiRo.getQuotationId());
				saveEo.setOperatorUserId(userId);
				saveEo.setDocStatus("BREAK");
				equPonQuotationInfoDAO_HI.save(saveEo);
			}
		}
	}

	@Override
	public void saveItProjectSupplierQuit(JSONObject jsonObject,Integer userId) {
		if(jsonObject.get("invitationId")!=null){
			//将所有的对应的项目的供应商的状态改为退出
			StringBuffer sql = new StringBuffer(EquPonQuotationInformationEntity_HI_RO.QUERY_IT_PROJECT_SUPPLIER);
			Map<String, Object> map = new HashMap<>();
			map.put("invitationId",jsonObject.getInteger("invitationId"));
			List<EquPonQuotationInformationEntity_HI_RO> invitationDAOHiList = equPonQuotationInformationDAO_HI_RO.findList(sql, map);
			for (EquPonQuotationInformationEntity_HI_RO entityHi : invitationDAOHiList) {
				EquPonItSupplierInvitationEntity_HI saveEo = equPonItSupplierInvitationDAO_HI.getById(entityHi.getInvitationId());
				saveEo.setOperatorUserId(userId);
				saveEo.setIsQuit("Y");
				saveEo.setReason(jsonObject.getString("reason"));
				equPonItSupplierInvitationDAO_HI.save(saveEo);
				Map<String, Object> maps = new HashMap<>(4);
				maps.put("sourceId", saveEo.getInvitationId());
				maps.put("supplierId", saveEo.getSupplierId());
				List<EquPonItSupplierInvitationEntity_HI> invitationList = ponItSupplierInvitationDao.findByProperty(maps);
				// 如果为空则没有修改立项，直接返回
				for (EquPonItSupplierInvitationEntity_HI entityHi1 : invitationList) {
					entityHi1.setQuotationFlag("R");
					entityHi1.setReason(jsonObject.getString("reason"));
					entityHi1.setIsQuit("Y");
					entityHi1.setOperatorUserId(userId);
					ponItSupplierInvitationDao.update(entityHi1);

				}
			}
			//将此供应商对应的所有报价改为终结
			sql = new StringBuffer(EquPonQuotationInformationEntity_HI_RO.QUERY_IT_PROJECT_QUOTATION);
			map = new HashMap<>();
			map.put("projectId",jsonObject.getInteger("projectId"));
			map.put("supplierId",jsonObject.getInteger("supplierId"));
			List<EquPonQuotationInformationEntity_HI_RO> quotationList = equPonQuotationInformationDAO_HI_RO.findList(sql, map);
			for (EquPonQuotationInformationEntity_HI_RO entityHiRo : quotationList) {
				EquPonQuotationInfoItEntity_HI saveEo = equPonQuotationInfoItDAO_HI.getById(entityHiRo.getQuotationId());
				saveEo.setOperatorUserId(userId);
				saveEo.setDocStatus("BREAK");
				equPonQuotationInfoItDAO_HI.save(saveEo);
			}
		}
	}

	@Override
	public void saveQuotationScoreCalculate(JSONObject params) throws Exception {
		Integer scoringId = params.getInteger("scoringId");
		Integer projectId = params.getInteger("projectId");
		Integer standardsId = params.getInteger("standardsId");
		quotationScoreCalculate(scoringId,projectId,standardsId);
	}

	public void quotationScoreCalculate(Integer scoringId,Integer projectId,Integer standardsId){
		EquPonStandardsHEntity_HI standardsEntity = equPonStandardsHDAO_HI.getById(standardsId);

		Map queryMap9 = new HashMap();
		queryMap9.put("scoringType","nonPriceCal");
		queryMap9.put("scoringItem","1 非价格");
		queryMap9.put("scoringId",scoringId);
		List<EquPonItScoringDetailEntity_HI> nonPriceCalList = equPonItScoringDetailDAO_HI.findByProperty(queryMap9);

		Map queryMap = new HashMap();
		queryMap.put("scoringType","nonPriceCal");
		queryMap.put("scoringItem","2 价格");
		queryMap.put("scoringId",scoringId);
		List<EquPonItScoringDetailEntity_HI> priceCalList = equPonItScoringDetailDAO_HI.findByProperty(queryMap);

		Map queryMap1 = new HashMap();
		queryMap1.put("scoringType","contractAmountExclusive");
		queryMap1.put("scoringId",scoringId);
		List<EquPonItScoringDetailEntity_HI> contractAmountExclusiveList = equPonItScoringDetailDAO_HI.findByProperty(queryMap1);

		Map queryMap2 = new HashMap();
		queryMap2.put("scoringType","contractAmountInclusive");
		queryMap2.put("scoringId",scoringId);
		List<EquPonItScoringDetailEntity_HI> contractAmountInclusiveList = equPonItScoringDetailDAO_HI.findByProperty(queryMap2);

		Map queryMap3 = new HashMap();
		queryMap3.put("scoringType","deductible");
		queryMap3.put("scoringId",scoringId);
		List<EquPonItScoringDetailEntity_HI> deductibleList = equPonItScoringDetailDAO_HI.findByProperty(queryMap3);

		Map queryMap4 = new HashMap();
		queryMap4.put("scoringType","contractAmountRanking");
		queryMap4.put("scoringId",scoringId);
		List<EquPonItScoringDetailEntity_HI> contractAmountRankingList = equPonItScoringDetailDAO_HI.findByProperty(queryMap4);

		Map queryMap5 = new HashMap();
		queryMap5.put("scoringId",scoringId);
		queryMap5.put("scoringType","quotationCalTotalScore");
		List<EquPonItScoringDetailEntity_HI> quotationCalTotalList = equPonItScoringDetailDAO_HI.findByProperty(queryMap5);

		Map queryMap6 = new HashMap();
		queryMap6.put("scoringId",scoringId);
		queryMap6.put("scoringType","quotationCalRank");
		List<EquPonItScoringDetailEntity_HI> quotationCalRankingList = equPonItScoringDetailDAO_HI.findByProperty(queryMap6);

//		Map queryMap7 = new HashMap();
//		queryMap7.put("scoringType","nonPriceCalTotalScore");
//		queryMap7.put("scoringId",scoringId);
//		List<EquPonItScoringDetailEntity_HI> nonPriceCalTotalScoreList = equPonItScoringDetailDAO_HI.findByProperty(queryMap7);
//
//		Map queryMap8 = new HashMap();
//		queryMap8.put("scoringType","nonPriceCalRanking");
//		queryMap8.put("scoringId",scoringId);
//		List<EquPonItScoringDetailEntity_HI> nonPriceCalRankingList = equPonItScoringDetailDAO_HI.findByProperty(queryMap8);

		Map<Integer,Double> contractAmountExclusiveMap = new HashMap<Integer,Double>();
		Map<Integer,Double> contractAmountInclusiveMap = new HashMap<Integer,Double>();
		Map<Integer,Double> deductibleMap = new HashMap<Integer,Double>();

		for(int i = 0; i < contractAmountExclusiveList.size(); i++){
			EquPonItScoringDetailEntity_HI contractAmountExclusiveEntity = contractAmountExclusiveList.get(i);
			Integer supplierId = contractAmountExclusiveEntity.getSupplierId();
			//查找报价单
			Map queryMap15 = new HashMap();
			queryMap15.put("projectId",projectId);
			queryMap15.put("supplierId",supplierId);
			List<EquPonQuotationInfoItEntity_HI> quotationList = equPonQuotationInfoItDAO_HI.findByProperty(queryMap15);

			//查找报价内容
			Map queryMap16 = new HashMap();
			queryMap16.put("quotationId",quotationList.get(0).getQuotationId());
			List<EquPonQuoContentItEntity_HI> quoContentList = equPonQuoContentItDAO_HI.findByProperty(queryMap16);

			double taxTotalSum = 0.0;//含税总额
			double taxAmountSum = 0.0;//可抵扣税款
			double nonTaxAmountSum = 0.0;//不含税价

			for(int j = 0; j < quoContentList.size(); j++){
				EquPonQuoContentItEntity_HI quoContentEntity = quoContentList.get(j);
				BigDecimal amount = quoContentEntity.getAmount();
				BigDecimal unitPrice = quoContentEntity.getUnitPrice();
				BigDecimal discount = quoContentEntity.getDiscount() == null ? new BigDecimal(100) : quoContentEntity.getDiscount();
				BigDecimal taxRate = quoContentEntity.getTaxRate();

				double taxTotal = unitPrice.doubleValue() * amount.doubleValue() * discount.doubleValue() * 0.01; //含税总价
				double taxAmount = unitPrice.doubleValue() * amount.doubleValue()* discount.doubleValue() * taxRate.doubleValue() * 0.01 * 0.01;//税额
				double nonTaxAmount = taxTotal - taxAmount;//不含税价

				taxTotalSum = taxTotalSum + taxTotal;
				taxAmountSum = taxAmountSum + taxAmount;
				nonTaxAmountSum = nonTaxAmountSum + nonTaxAmount;
			}
			contractAmountExclusiveMap.put(supplierId,nonTaxAmountSum);
			contractAmountInclusiveMap.put(supplierId,taxTotalSum);
			deductibleMap.put(supplierId,taxAmountSum);
		}


		for(int i = 0; i < contractAmountExclusiveList.size(); i++){
			EquPonItScoringDetailEntity_HI contractAmountExclusiveEntity = contractAmountExclusiveList.get(i);
			Integer supplierId = contractAmountExclusiveEntity.getSupplierId();
			contractAmountExclusiveEntity.setPriceSummary(new BigDecimal(contractAmountExclusiveMap.get(supplierId)));
		}

		for(int i = 0; i < contractAmountInclusiveList.size(); i++){
			EquPonItScoringDetailEntity_HI contractAmountInclusiveEntity = contractAmountInclusiveList.get(i);
			Integer supplierId = contractAmountInclusiveEntity.getSupplierId();
			contractAmountInclusiveEntity.setPriceSummary(new BigDecimal(contractAmountInclusiveMap.get(supplierId)));
		}

		for(int i = 0; i < deductibleList.size(); i++){
			EquPonItScoringDetailEntity_HI deductibleEntity = deductibleList.get(i);
			Integer supplierId = deductibleEntity.getSupplierId();
			deductibleEntity.setPriceSummary(new BigDecimal(deductibleMap.get(supplierId)));
		}


		//排序
		List<Map.Entry<Integer, Double>> list = new ArrayList<Map.Entry<Integer, Double>>(contractAmountExclusiveMap.entrySet()); //转换为list
		list.sort(new Comparator<Map.Entry<Integer, Double>>() {
			@Override
			public int compare(Map.Entry<Integer, Double> o1, Map.Entry<Integer, Double> o2) {
				return o1.getValue().compareTo(o2.getValue());
			}
		});

		//排名
		for (int i = 0; i < list.size(); i++) {
			for(int j = 0; j < contractAmountRankingList.size(); j++){
				EquPonItScoringDetailEntity_HI contractAmountRankingEntity = contractAmountRankingList.get(j);
				if(contractAmountRankingList.get(j).getSupplierId().intValue() == list.get(i).getKey().intValue()){
					contractAmountRankingEntity.setScoringRanking(new BigDecimal(i + 1));
				}
			}
		}

//		//最低价格
		double minDeductible = list.get(0).getValue();
//
//		//价格评分计算
		Map<Integer, Double> priceScoreMap = new HashMap<Integer, Double>();
		for(int i = 0; i < priceCalList.size(); i++){
			EquPonItScoringDetailEntity_HI priceEntity = priceCalList.get(i);
//			BigDecimal fullScore = priceEntity.getFullScore();
//			BigDecimal weighting = priceEntity.getWeighting();
			BigDecimal fullScore = standardsEntity.getScore();
			BigDecimal weighting = new BigDecimal(100);
			Integer supplierId = priceEntity.getSupplierId();
//			double deductibleAmount = deductibleMap.get(supplierId);
			double deductibleAmount = contractAmountExclusiveMap.get(supplierId);
			double priceRating = fullScore.doubleValue() * (minDeductible / deductibleAmount);
			double priceScore = priceRating * weighting.doubleValue() * 0.01;
			priceEntity.setNonPriceScore(new BigDecimal(priceScore));
			priceEntity.setNonPriceRating(new BigDecimal(priceRating));
			priceScoreMap.put(supplierId,priceScore);
		}

		Map<Integer, Double> nonPriceScoreMap = new HashMap<Integer, Double>();
		for(int i = 0; i < nonPriceCalList.size(); i++){
			EquPonItScoringDetailEntity_HI nonPriceEntity = nonPriceCalList.get(i);
			Integer supplierId = nonPriceEntity.getSupplierId();
			double deductibleAmount = deductibleMap.get(supplierId);
			double priceRating = 0;
			double priceScore = 0;
			nonPriceEntity.setNonPriceScore(new BigDecimal(priceScore));
			nonPriceEntity.setNonPriceRating(new BigDecimal(priceRating));
			nonPriceScoreMap.put(supplierId,priceScore);
		}

//		Map<Integer, Double> nonPriceCalTotalScoreMap = new HashMap<Integer, Double>();
////		Map<Integer, Double> nonPriceCalRankingMap = new HashMap<Integer, Double>();
//		for(int i = 0; i < nonPriceCalTotalScoreList.size(); i++){
//			EquPonItScoringDetailEntity_HI nonPriceCalTotalScoreEntity = nonPriceCalTotalScoreList.get(i);
//			Integer supplierId = nonPriceCalTotalScoreEntity.getSupplierId();
//			BigDecimal nonPriceScore = emptyToZero(nonPriceCalTotalScoreEntity.getNonPriceScore());
////			BigDecimal nonPriceRating = emptyToZero(nonPriceCalTotalScoreEntity.getNonPriceRating());
//			nonPriceCalTotalScoreMap.put(supplierId,nonPriceScore.doubleValue());
////			nonPriceCalRankingMap.put(supplierId,nonPriceRating.doubleValue());
//		}

		Map<Integer, Double> quotationCalTotalScoreMap = new HashMap<Integer, Double>();
		for(int i = 0; i < quotationCalTotalList.size(); i++){
			EquPonItScoringDetailEntity_HI quotationCalTotalScoreEntity = quotationCalTotalList.get(i);
			Integer supplierId = quotationCalTotalScoreEntity.getSupplierId();
			double priceScore = priceScoreMap.get(supplierId);
//			double nonPriceCalTotalScore = nonPriceCalTotalScoreMap.get(supplierId);
			double quotationCalTotalScore = priceScore;
			quotationCalTotalScoreMap.put(supplierId,quotationCalTotalScore);
			quotationCalTotalScoreEntity.setNonPriceScore(new BigDecimal(quotationCalTotalScore));
//			quotationCalTotalScoreEntity.setNonPriceRating(new BigDecimal(nonPriceCalRankingMap.get(supplierId)));
		}


		//总分排序
		List<Map.Entry<Integer, Double>> list2 = new ArrayList<Map.Entry<Integer, Double>>(quotationCalTotalScoreMap.entrySet()); //转换为list
		list2.sort(new Comparator<Map.Entry<Integer, Double>>() {
			@Override
			public int compare(Map.Entry<Integer, Double> o1, Map.Entry<Integer, Double> o2) {
				return o2.getValue().compareTo(o1.getValue());
			}
		});

		//总分排名
		for (int i = 0; i < list2.size(); i++) {
			for(int j = 0; j < quotationCalRankingList.size(); j++){
				EquPonItScoringDetailEntity_HI quotationCalRankingEntity = quotationCalRankingList.get(j);
				if(quotationCalRankingList.get(j).getSupplierId().intValue() == list2.get(i).getKey().intValue()){
					quotationCalRankingEntity.setScoringRanking(new BigDecimal(i + 1));
				}
			}
		}
	}

	public BigDecimal emptyToZero(BigDecimal b){
		if(null == b){
			return new BigDecimal(0);
		}
		return b;
	}

	/**
	 * 解析json参数
	 */
	public JSONObject getParamsJSONObject(JSONObject params,JSONObject targetJsonObject){
		if(params.containsKey("varUserId")){
			targetJsonObject.put("varUserId",params.get("varUserId"));
		}
		if(params.containsKey("username")){
			targetJsonObject.put("username",params.get("username"));
		}
		if(params.containsKey("varSystemCode")){
			targetJsonObject.put("varSystemCode",params.get("varSystemCode"));
		}
		if(params.containsKey("varUserName")){
			targetJsonObject.put("varUserName",params.get("varUserName"));
		}
		if(params.containsKey("varEmployeeNumber")){
			targetJsonObject.put("varEmployeeNumber",params.get("varEmployeeNumber"));
		}
		if(params.containsKey("varUserFullName")){
			targetJsonObject.put("varUserFullName",params.get("varUserFullName"));
		}
		if(params.containsKey("varOrgId")){
			targetJsonObject.put("varOrgId",params.get("varOrgId"));
		}
		if(params.containsKey("varOrgCode")){
			targetJsonObject.put("varOrgCode",params.get("varOrgCode"));
		}
		if(params.containsKey("varLeaderId")){
			targetJsonObject.put("varLeaderId",params.get("varLeaderId"));
		}
		if(params.containsKey("varIsadmin")){
			targetJsonObject.put("varIsadmin",params.get("varIsadmin"));
		}
		if(params.containsKey("varUserType")){
			targetJsonObject.put("varUserType",params.get("varUserType"));
		}
		if(params.containsKey("operationOrgIds")){
			targetJsonObject.put("operationOrgIds",params.get("operationOrgIds"));
		}
		if(params.containsKey("operationOrgId")){
			targetJsonObject.put("operationOrgId",params.get("operationOrgId"));
		}
		if(params.containsKey("operationRespId")){
			targetJsonObject.put("operationRespId",params.get("operationRespId"));
		}
		if(params.containsKey("operatorUserId")){
			targetJsonObject.put("operatorUserId",params.get("operatorUserId"));
		}
		return targetJsonObject;
	}
}
