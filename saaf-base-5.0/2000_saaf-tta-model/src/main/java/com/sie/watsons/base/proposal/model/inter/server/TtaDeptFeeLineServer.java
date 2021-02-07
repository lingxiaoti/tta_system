package com.sie.watsons.base.proposal.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.math.BigDecimal;
import java.util.*;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.feedept.model.entities.readonly.TtaFeeDeptHEntity_HI_RO;
import com.sie.watsons.base.proposal.model.dao.TtaProposalHeaderDAO_HI;
import com.sie.watsons.base.proposal.model.entities.TtaProposalHeaderEntity_HI;
import com.sie.watsons.base.proposal.model.entities.readonly.TtaDeptFeeLineEntity_HI_RO;
import com.sie.watsons.base.proposal.model.entities.readonly.TtaProposalHeaderEntity_HI_RO;
import com.sie.watsons.base.withdrawal.model.entities.TtaSupplierItemRelSupplierEntity_HI;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yhg.base.utils.SToolUtils;
import com.sie.watsons.base.proposal.model.entities.TtaDeptFeeLineEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.proposal.model.inter.ITtaDeptFeeLine;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import org.springframework.util.Assert;

@Component("ttaDeptFeeLineServer")
public class TtaDeptFeeLineServer extends BaseCommonServer<TtaDeptFeeLineEntity_HI> implements ITtaDeptFeeLine{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaDeptFeeLineServer.class);

	@Autowired
	private ViewObject<TtaDeptFeeLineEntity_HI> ttaDeptFeeLineDAO_HI;
	@Autowired
	private BaseViewObject<TtaDeptFeeLineEntity_HI_RO> ttaDeptFeeLineDAO_HI_RO;

	@Autowired
	private TtaProposalHeaderServer ttaProposalHeaderServer ;
	
	@Autowired
	private TtaProposalHeaderDAO_HI ttaProposalHeaderDAO;

	@Autowired
	private BaseCommonDAO_HI<TtaDeptFeeLineEntity_HI> baseCommonDAO_hi;

	@Autowired
	private BaseViewObject<TtaProposalHeaderEntity_HI_RO> ttaProposalHeaderDAO_HI_RO;

    @Autowired
    private BaseViewObject<TtaFeeDeptHEntity_HI_RO> ttaFeeDeptHDAO_HI_RO;


	public TtaDeptFeeLineServer() {
		super();
	}



	@Override
	public Pagination<TtaDeptFeeLineEntity_HI_RO> find(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
		StringBuffer sql = new StringBuffer();
		sql.append(TtaDeptFeeLineEntity_HI_RO.TTA_ITEM_V);
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		SaafToolUtils.parperParam(queryParamJSON, "v.proposal_Id", "proposalId", sql, paramsMap, "=");
		SaafToolUtils.parperParam(queryParamJSON, "v.accord_Type", "accordType", sql, paramsMap, "=");
		SaafToolUtils.parperParam(queryParamJSON, "v.dept_Fee_Id", "deptFeeId", sql, paramsMap, "=");

		if ("B".equals(queryParamJSON.getString("accordType")) && queryParamJSON.containsKey("dmFlyerType")) {
			String dmFlyerType = queryParamJSON.getString("dmFlyerType");
			//查询的是宣传单张类型中的DM 或者 FYLER 类型的数据
			if (StringUtils.isNotBlank(dmFlyerType)) {
				sql.append(" and v.dm_flyer = '" + dmFlyerType + "' ");
			}
		}

		SaafToolUtils.changeQuerySort(queryParamJSON, sql, " FORSTR(v.line_Code) asc,nvl(v.unit,'Z') asc", false);
		Pagination<TtaDeptFeeLineEntity_HI_RO> findList = ttaDeptFeeLineDAO_HI_RO.findPagination(sql, paramsMap, pageIndex, pageRows);

		//判断accordType为B类型,数据中是否含有DM或者FLYER,含有则返回空数据
		if ("B".equals(queryParamJSON.getString("accordType")) && queryParamJSON.containsKey("dmFlyerType")) {
			String dmFlyerType = queryParamJSON.getString("dmFlyerType");
			if (StringUtils.isBlank(dmFlyerType)) {
				List<String> strings = new ArrayList<>();
				if (findList != null && findList.getData().size() >0) {
					for (TtaDeptFeeLineEntity_HI_RO datum : findList.getData()) {

						if(!strings.contains(datum.getDmFlyer())){
							strings.add(datum.getDmFlyer());
						}
					}
				}

				//如果strings里面有一个元素,则返回空集合,给前端重新发请求加载数据
				if(strings.size() == 1) {
					findList=  new Pagination<TtaDeptFeeLineEntity_HI_RO>();
					findList.setData(Collections.emptyList());
				}
			}
		}

		return findList;
	}

	@Override
	public TtaDeptFeeLineEntity_HI saveOrUpdate(JSONObject paramsJSON, int userId) throws Exception {
		TtaDeptFeeLineEntity_HI instance = SaafToolUtils.setEntity(TtaDeptFeeLineEntity_HI.class, paramsJSON, ttaDeptFeeLineDAO_HI, userId);


		ttaDeptFeeLineDAO_HI.saveOrUpdate(instance);
		return instance;
	}

	@Override
	public void delete(Integer pkId) {
		if (pkId == null) {
			return;
		}
		TtaDeptFeeLineEntity_HI instance = ttaDeptFeeLineDAO_HI.getById(pkId);
		if (instance == null) {
			return;
		}
		ttaDeptFeeLineDAO_HI.delete(instance);
	}


	@Override
	public TtaDeptFeeLineEntity_HI_RO findByRoId(JSONObject queryParamJSON) {
		StringBuffer sql = new StringBuffer();
		sql.append(TtaDeptFeeLineEntity_HI_RO.TTA_ITEM_V);

		Map<String, Object> paramsMap = new HashMap<String, Object>();
		SaafToolUtils.parperParam(queryParamJSON, "v.deptfee_Line_Id", "deptfeeLineId", sql, paramsMap, "=");
		return (TtaDeptFeeLineEntity_HI_RO)ttaDeptFeeLineDAO_HI_RO.get(sql,paramsMap);
	}

	@Override
	public JSONObject findDeptFeeLineReport(JSONObject queryParamJSON) throws Exception{
		JSONObject result =  new JSONObject();
		StringBuffer sql = new StringBuffer();
		sql.append(TtaDeptFeeLineEntity_HI_RO.TTA_ITEM_REPORT);

		//2019.12.12 hmb 添加
		sql.append("and exists(select 1 from \n" +
				"        tta_proposal_terms_line tptl,\n" +
				"        tta_dept_fee   tdf\n" +
				"        where tptl.proposal_id = tdfrv.proposalId\n" +
				"        and tdf.proposal_id = tptl.proposal_id \n" +
				"        and (tdf.line_code = tdfrv.lineCode or tdfrv.accordTypeName is null)\n" +
				"        and (tptl.y_terms = decode(nvl(tdf.dm_flyer,'陈列服务费'),'DM','DM','FLYER','FYLER','陈列服务费'))  \n" +
				"        AND nvl(tptl.income_type,'按公司标准') = '按其他协定标准'\n" +
				"        and tdfrv.accordType = decode(nvl(tdf.dm_flyer,'陈列服务费'),'DM','B','FLYER','B','A') \n" +
				"          )  ");

		Map<String, Object> queryMap = new HashMap<String, Object>();
		SaafToolUtils.parperParam(queryParamJSON, "tdfrv.proposalId", "proposalId", sql, queryMap, "=");
		//sql.append(" group by  tdfrv.item_desc_cn,tdf.accord_type,lookup4.meaning");
		SaafToolUtils.changeQuerySort(queryParamJSON, sql, "  accordType asc, FORSTR(lineCode) asc", false);
		List<TtaDeptFeeLineEntity_HI_RO> countList = ttaDeptFeeLineDAO_HI_RO.findList(sql.toString(), queryMap);
		TtaProposalHeaderEntity_HI_RO byRoId = ttaProposalHeaderServer.findByRoId(queryParamJSON);
		String venderName = queryParamJSON.getString("venderName");
		if (byRoId != null && StringUtils.isNotBlank(venderName)) {
			byRoId.setVendorName(venderName);
		}
		result.put("ttaDeptFeeReportTable",countList);
		result.put("head",byRoId);
		return result;
	}

	@Override
	public JSONObject findDeptFeeShowHideByProposal(JSONObject jsonObject) {
		Assert.notNull(jsonObject.getInteger("proposalId"),"缺少ProposalId");
		List<Map<String, Object>> mapList = ttaProposalHeaderDAO.queryNamedSQLForList(TtaDeptFeeLineEntity_HI_RO.getDeptFeeShowOrHide(jsonObject.getInteger("proposalId").toString()), new HashMap<>());
		JSONObject object = new JSONObject();
		object.put("flag",(BigDecimal)mapList.get(0).get("FLAG"));
		return object;
	}

	/**
	 * 查找往年的部门协定标准信息
	 * @param queryParamJSON
	 * @return
	 * @throws Exception
	 */
	@Override
	public JSONObject findDeptFeeLFindByOldYear(JSONObject queryParamJSON) throws Exception {
		JSONObject result =  new JSONObject();

		//找出往年的Proposal
		String sb = "select tph.proposal_id ,\n" +
				"       tph.proposal_year,\n" +
				"       tph.dept_code1,\n" +
				"       tph.dept_desc1,\n" +
				"       tph.dept_code2,\n" +
				"       tph.dept_desc2,\n" +
				"       tph.dept_code3,\n" +
				"       tph.dept_desc3,\n" +
				"       tph.is_transdepart,\n" +
				"       tph.is_depart_confirm \n" +
				"  from tta_proposal_header tph\n" +
				" where\n" +
				"   tph.status = 'C'  ";

		StringBuffer sql = new StringBuffer(sb);
		Map<String, Object> queryMap = new HashMap<String, Object>();
		//queryParamJSON.put("proposalYear",2019);测试使用

		//SaafToolUtils.parperParam(queryParamJSON,"tph.proposal_id","proposalId",sql,queryMap,"=");
		SaafToolUtils.parperParam(queryParamJSON,"tph.major_dept_code","majorDeptCode",sql,queryMap,"=");
		SaafToolUtils.parperParam(queryParamJSON,"tph.proposal_year","proposalYear",sql,queryMap,"=");
		//SaafToolUtils.parperParam(queryParamJSON,"tph.status","status",sql,queryMap,"=");
		SaafToolUtils.parperParam(queryParamJSON,"tph.vendor_nbr","vendorNbr",sql,queryMap,"=");
		SaafToolUtils.parperParam(queryParamJSON,"tph.new_or_existing","newOrExisting",sql,queryMap,"=");
		TtaProposalHeaderEntity_HI_RO entity_hi_ro = ttaProposalHeaderDAO_HI_RO.get(sql, queryMap);

		if (null == entity_hi_ro) {
			Integer oldYearInfo = (Integer) queryParamJSON.get("proposalYear");
			throw new IllegalArgumentException("没有存在往年("+oldYearInfo+")的Proposal单据");
		}

		String deptSql = "SELECT t.DEPTFEE_LINE_ID,\n" +
				"       t.DEPT_FEE_ID,\n" +
				"       t.LINE_CODE,\n" +
				"       t.PARENT_LINE_CODE,\n" +
				"       t.SORT_ID,\n" +
				"       t.ITEM_NBR,\n" +
				"       t.CREATION_DATE,\n" +
				"       t.CREATED_BY,\n" +
				"       t.LAST_UPDATED_BY,\n" +
				"       t.LAST_UPDATE_DATE,\n" +
				"       t.LAST_UPDATE_LOGIN,\n" +
				"       t.VERSION_NUM,\n" +
				"       t.REMARK,\n" +
				"       t.PROPOSAL_ID,\n" +
				"       t.ITEM_DESC_CN,\n" +
				"       t.ITEM_DESC_EN,\n" +
				"       t.UNIT1,\n" +
				"       t.STANDARD_VALUE1,\n" +
				"       t.UNIT2,\n" +
				"       t.STANDARD_VALUE2,\n" +
				"       t.UNIT3,\n" +
				"       t.STANDARD_VALUE3,\n" +
				"       t.UNIT4,\n" +
				"       t.STANDARD_VALUE4,\n" +
				"       t.UNIT5,\n" +
				"       t.STANDARD_VALUE5,\n" +
				"       t.UNIT,\n" +
				"       t.line_code || t.item_desc_en || t.item_desc_cn as item_detail,\n" +
				"       t.accord_type\n" +
				"   FROM tta_dept_fee t\n" +
				"    where 1=1 ";

		StringBuffer deptSbSql = new StringBuffer(deptSql);
		Map<String,Object> queryParamMap1 = new HashMap<>();
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("proposalId",entity_hi_ro.getProposalId());
		jsonObject.put("accordType","A");
		getSql(jsonObject,deptSbSql,queryParamMap1);
		List<TtaDeptFeeLineEntity_HI_RO> listA = ttaDeptFeeLineDAO_HI_RO.findList(deptSbSql, queryParamMap1);

		StringBuffer deptSbSql2 = new StringBuffer(deptSql);
		Map<String,Object> queryParamMap2 = new HashMap<>();
		JSONObject jsonObject1 = new JSONObject();
		jsonObject1.put("proposalId",entity_hi_ro.getProposalId());
		jsonObject1.put("accordType","B");
		getSql(jsonObject1,deptSbSql2,queryParamMap2);
		List<TtaDeptFeeLineEntity_HI_RO> listB = ttaDeptFeeLineDAO_HI_RO.findList(deptSbSql2, queryParamMap2);

		result.put("proposalHeader",entity_hi_ro);//proposal头信息
		result.put("deptFeeLADataTableByOld",listA);//促销陈列服务费
		result.put("deptFeeLBDataTableByOld",listB);//宣传单张服务费
		return result;
	}

	private void getSql(JSONObject queryParamJSON,StringBuffer sql,Map<String,Object> queryParamMap){
		SaafToolUtils.parperParam(queryParamJSON,"t.proposal_id","proposalId",sql,queryParamMap,"=");
		SaafToolUtils.parperParam(queryParamJSON, "t.accord_type", "accordType", sql, queryParamMap, "=");
		SaafToolUtils.changeQuerySort(queryParamJSON, sql, " FORSTR(t.line_Code) asc,nvl(t.unit,'Z') asc", false);
	}

    /**
	 * 查看部门的标准值和单位
	 * @param queryParamJSON
	 * @return
	 * @throws Exception
	 */
    @Override
    public List<Map<String, Object>> ttaDeptFeeLFindSearchPromotionCost(JSONObject queryParamJSON) throws Exception {
        Integer proposalId = queryParamJSON.getInteger("proposalId");
        String accordType = queryParamJSON.getString("accordType");//费用类型
        Assert.notNull(proposalId,"Proposal单据不存在");
		TtaProposalHeaderEntity_HI entity_hi_ro = ttaProposalHeaderDAO.getById(proposalId);
		if (null ==entity_hi_ro) {
            throw new IllegalArgumentException("Proposal单据未保存");
        }

        //找出部门协定收费标准单据,区分促销陈列服务费和宣传单服务费
        String sql = "select tfdh.feedept_id as feedeptId,\n" +
				"  tfdh.dept_code as deptCode,\n" +
				"  tfdh.dept_desc as deptDesc\n" +
				" from TTA_FEE_DEPT_HEADER tfdh\n" +
				" where 1 = 1\n" +
				"   and tfdh.status = 'C' \n" +
				"   and tfdh.year_code = :yearCode \n" +
				"   and tfdh.dept_code = :deptCode \n" +
				"   and tfdh.accord_type = :accordType ";

        StringBuffer feeDeptSql  = new StringBuffer(sql);
		Map<String,Object> map = new HashMap<>();
		map.put("yearCode",entity_hi_ro.getProposalYear());
		map.put("deptCode",entity_hi_ro.getMajorDeptCode());
		map.put("accordType",accordType);

		TtaFeeDeptHEntity_HI_RO ttaFeeDeptHEntity_hi_ro = ttaFeeDeptHDAO_HI_RO.get(feeDeptSql, map);

		List<Map<String, Object>> list = null;

		if (null == ttaFeeDeptHEntity_hi_ro) {
			return list = new ArrayList<>();
		}

		Integer feedeptId = ttaFeeDeptHEntity_hi_ro.getFeedeptId();
		
		String feeSql = "select t1.*,\n" +
				"       t3.line_code      as PARENT_LINE_CODE,\n" +
				"       TD.UNIT           UNITD,--单位\n" +
				"       TD.STANDARD_VALUE STANDARD_VALUED, -- 标准值\n" +
				"       TD.DEPT_CODE,\n" +
				"       TD.DEPT_DESC\n" +
				"  from TTA_FEE_DEPT_LINE T1\n" +
				"  left join TTA_FEE_DEPT_LINE T3\n" +
				"    on t1.PARENT_LINE_ID = T3.FEEDEPT_LINE_ID\n" +
				"  left join TTA_FEE_DEPT_DETAIL TD\n" +
				"    on t1.feedept_line_id = td.feedept_line_id\n" +
				" where t1.feedept_id = "+feedeptId+"\n" +
				"   and nvl(t1.if_effect, 'N') = 'Y'\n" +
				" order by FORSTR(T1.line_code) asc ";

		 list = baseCommonDAO_hi.queryNamedSQLForList(feeSql, new HashMap<String,Object>());
		return list;
    }

	/**
	 *删除部门协定标准 促销陈列服务费和宣传单费用数据
	 * @param jsonObject 参数
	 * @throws Exception
	 */
	@Override
	public void deleteDeptFeeByProposalAndDmFlyer(JSONObject jsonObject) throws Exception {
		String proposalId = jsonObject.getString("proposalId");
		String symbol = jsonObject.getString("symbol");

		String sql = "";
		if (StringUtils.isBlank(symbol) || "promotion".equals(symbol)) {
			sql = "select deptfee_line_id,\n" +
					"       dept_fee_id,\n" +
					"       line_code,\n" +
					"       parent_line_code,\n" +
					"       sort_id,\n" +
					"       item_nbr,\n" +
					"       creation_date,\n" +
					"       created_by,\n" +
					"       last_updated_by,\n" +
					"       last_update_date,\n" +
					"       last_update_login,\n" +
					"       version_num,\n" +
					"       remark,\n" +
					"       proposal_id,\n" +
					"       item_desc_cn,\n" +
					"       item_desc_en,\n" +
					"       unit1,\n" +
					"       standard_value1,\n" +
					"       unit2,\n" +
					"       standard_value2,\n" +
					"       unit3,\n" +
					"       standard_value3,\n" +
					"       unit4,\n" +
					"       standard_value4,\n" +
					"       unit5,\n" +
					"       standard_value5,\n" +
					"       unit,\n" +
					"       accord_type,\n" +
					"       directory_level,\n" +
					"       unit_value,\n" +
					"       standard_unit_value,\n" +
					"       default_valus1,\n" +
					"       default_valus2,\n" +
					"       default_valus3,\n" +
					"       default_valus4,\n" +
					"       default_valus5,\n" +
					"       default_unit1,\n" +
					"       default_unit2,\n" +
					"       default_unit3,\n" +
					"       default_unit4,\n" +
					"       default_unit5,\n" +
					"       proposal_year,\n" +
					"       dm_flyer\n" +
					"  from tta_dept_fee tdf\n" +
					" where tdf.proposal_id = "+proposalId+"\n" +
					"   and tdf.dm_flyer is null ";
		}else {
			sql = "select deptfee_line_id,\n" +
					"       dept_fee_id,\n" +
					"       line_code,\n" +
					"       parent_line_code,\n" +
					"       sort_id,\n" +
					"       item_nbr,\n" +
					"       creation_date,\n" +
					"       created_by,\n" +
					"       last_updated_by,\n" +
					"       last_update_date,\n" +
					"       last_update_login,\n" +
					"       version_num,\n" +
					"       remark,\n" +
					"       proposal_id,\n" +
					"       item_desc_cn,\n" +
					"       item_desc_en,\n" +
					"       unit1,\n" +
					"       standard_value1,\n" +
					"       unit2,\n" +
					"       standard_value2,\n" +
					"       unit3,\n" +
					"       standard_value3,\n" +
					"       unit4,\n" +
					"       standard_value4,\n" +
					"       unit5,\n" +
					"       standard_value5,\n" +
					"       unit,\n" +
					"       accord_type,\n" +
					"       directory_level,\n" +
					"       unit_value,\n" +
					"       standard_unit_value,\n" +
					"       default_valus1,\n" +
					"       default_valus2,\n" +
					"       default_valus3,\n" +
					"       default_valus4,\n" +
					"       default_valus5,\n" +
					"       default_unit1,\n" +
					"       default_unit2,\n" +
					"       default_unit3,\n" +
					"       default_unit4,\n" +
					"       default_unit5,\n" +
					"       proposal_year,\n" +
					"       dm_flyer\n" +
					"  from tta_dept_fee tdf\n" +
					" where tdf.proposal_id = "+proposalId+"\n" +
					"   and tdf.dm_flyer ='"+symbol+"'";
		}

		List<Map<String, Object>> list = baseCommonDAO_hi.queryNamedSQLForList(sql, new HashMap<String, Object>());
		if (list  == null || list.size() == 0) {
			return;
		}
		LOGGER.info("TTA_TERMS进行删除部门协定标准值操作");
		List<TtaDeptFeeLineEntity_HI> ttaDeptFeeLineEntity_his = JSON.parseArray(JSON.toJSONString(list), TtaDeptFeeLineEntity_HI.class);
		ttaDeptFeeLineDAO_HI.delete(ttaDeptFeeLineEntity_his);
	}
}
