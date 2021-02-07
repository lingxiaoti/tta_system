package com.sie.watsons.base.report.model.inter.server;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.bean.UserSessionBean;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.services.GenerateCodeService;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.report.model.entities.TtaReportProcessHeaderEntity_HI;
import com.sie.watsons.base.report.model.entities.readonly.TtaReportProcessHeaderEntity_HI_RO;
import com.sie.watsons.base.report.model.inter.*;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.*;

@Component("ttaReportProcessHeaderServer")
public class TtaReportProcessHeaderServer extends BaseCommonServer<TtaReportProcessHeaderEntity_HI> implements ITtaReportProcessHeader {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaReportProcessHeaderServer.class);

	@Autowired
	private ViewObject<TtaReportProcessHeaderEntity_HI> ttaReportProcessHeaderDAO_HI;

	@Autowired
	private BaseViewObject<TtaReportProcessHeaderEntity_HI_RO> ttaReportProcessHeaderEntity_HI_RO;

	@Autowired
	private GenerateCodeService codeService;

	@Autowired
	private ITtaCwChecking ttaCwCheckingServer;

	@Autowired
	private ITtaHwbChecking ttaHwbCheckingServer;

	@Autowired
	private ITtaDmChecking ttaDMCheckingServer;

	@Autowired
	private ITtaMonthlyChecking ttaMonthlyCheckingServer;

	@Autowired
	private ITtaNppNewProductReport ttaNppNewProductReportServer;


	public TtaReportProcessHeaderServer() {
		super();
	}


	@Override
	public Pagination<TtaReportProcessHeaderEntity_HI_RO> find(JSONObject queryParamJSON, UserSessionBean userSessionBean, Integer pageIndex, Integer pageRows) {
		StringBuffer sql = new StringBuffer();
		sql.append(TtaReportProcessHeaderEntity_HI_RO.TTA_REPORT_PROCCES_HEADER);
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		SaafToolUtils.parperParam(queryParamJSON, "h.batch_code", "batchCode", sql, paramsMap, "fulllike");
		SaafToolUtils.parperParam(queryParamJSON, "h.status", "status", sql, paramsMap, "fulllike");
		if( ( !("45".equals(userSessionBean.getUserType())) ) && (! ("1".equals(userSessionBean.getUserId()) )   ) ){
			queryParamJSON.put("userId",userSessionBean.getUserId()) ;
			SaafToolUtils.parperParam(queryParamJSON, "h.created_by", "userId", sql, paramsMap, "=");
		}
		SaafToolUtils.changeQuerySort(queryParamJSON, sql, " h.report_process_header_id desc", false);
		Pagination<TtaReportProcessHeaderEntity_HI_RO> findList = ttaReportProcessHeaderEntity_HI_RO.findPagination(sql,SaafToolUtils.getSqlCountString(sql), paramsMap, pageIndex, pageRows);
		return findList;
	}

	@Override
	public TtaReportProcessHeaderEntity_HI saveOrUpdate(JSONObject paramsJSON, int userId) throws Exception {

		TtaReportProcessHeaderEntity_HI instance = new TtaReportProcessHeaderEntity_HI();
		String  type = paramsJSON.getString("reprotTypeName") ;
		JSONArray lines = paramsJSON.getJSONArray("lines");
		Assert.notEmpty(lines, "没有选中生成的数据。");
		Assert.notNull(type,"生成的type参数不能为空。");

		JSONObject oneLine = lines.getJSONObject(0);
		String batchId = oneLine.getString("batchCode");
		HashSet<String> groupSet = new HashSet<>();
		lines.forEach(item->{
			String groupCode =  ((JSONObject)item).getString("groupCode");
			groupSet.add(groupCode);
		});
		Assert.isTrue(groupSet.size() == 1,"只能选择同一个部门的单据！");
		createProcessH(instance,batchId,type, userId);
		for (int idx = 0; idx < lines.size(); idx ++) {
			JSONObject jsonObject = lines.getJSONObject(idx);
			if (jsonObject != null) {
				jsonObject.put("processId", instance.getReportProcessHeaderId());
			}
		}
		if("CW".equals(type)){
			paramsJSON.put("save",lines) ;
			paramsJSON.put("flag","createProcee") ;
			paramsJSON.put("reportProcessHeaderId",instance.getReportProcessHeaderId()) ;
			ttaCwCheckingServer.saveOrUpdateALL(paramsJSON,userId);
		} else if ("DM".equals(type)) {
			ttaDMCheckingServer.saveOrUpdateALL(lines, userId);
		} else if ("OSD".equals(type)) {
			paramsJSON.put("save",lines) ;
			ttaMonthlyCheckingServer.saveOrUpdateALL(paramsJSON,userId);
		} else if ("NPP".equals(type)) {
			paramsJSON.put("save",lines) ;
			ttaNppNewProductReportServer.saveOrUpdateALL(paramsJSON, userId);
		} else if ("HWB".equals(type)){
			paramsJSON.put("save",lines) ;
			ttaHwbCheckingServer.saveOrUpdateALL(paramsJSON, userId);
		}
		return instance;
	}

	private void  createProcessH (TtaReportProcessHeaderEntity_HI ttaReportProcessHeaderEntity_hi, String batchId, String reportType, int userId){
		ttaReportProcessHeaderEntity_hi.setOperatorUserId(userId);
		ttaReportProcessHeaderEntity_hi.setStatus( "DS01");
		ttaReportProcessHeaderEntity_hi.setReportType( reportType);
		String batchCode = codeService.getReportProcessCode(reportType) ;
		ttaReportProcessHeaderEntity_hi.setBatchCode(batchCode);
		ttaReportProcessHeaderEntity_hi.setBatchId(batchId);//记录头的批次号
		ttaReportProcessHeaderDAO_HI.saveOrUpdate(ttaReportProcessHeaderEntity_hi);
	}


	@Override
	public TtaReportProcessHeaderEntity_HI_RO findOne(JSONObject queryParamJSON, Integer userId) {
		StringBuffer sql = new StringBuffer();
		sql.append(TtaReportProcessHeaderEntity_HI_RO.TTA_REPORT_PROCCES_HEADER);
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		SaafToolUtils.parperParam(queryParamJSON, "h.report_Process_Header_Id", "reportProcessHeaderId", sql, paramsMap, "=");
		TtaReportProcessHeaderEntity_HI_RO ttaReportProcessHeaderEntity_hi_ro = ttaReportProcessHeaderEntity_HI_RO.get(sql, paramsMap);
		return ttaReportProcessHeaderEntity_hi_ro;
	}

	    @Override
    public JSONArray updateStatus(JSONObject paramsJSON, Integer userId) throws Exception {

        Integer id = paramsJSON.getIntValue("id");//单据Id
        JSONObject headerObject = new JSONObject();
        headerObject.put("id", id);

        JSONObject param=new JSONObject();
        JSONArray result=new JSONArray();
        //查询表单工具
        List<TtaReportProcessHeaderEntity_HI> ttaReportProcessHeaderEntitys = ttaReportProcessHeaderDAO_HI.findByProperty("id",headerObject.get("id"));
        String orderStatus = null;//状态
        switch (paramsJSON.getString("status")) {
			case "REVOKE":
            case "REFUSAL":
            	//撤回和驳回都把单据状态置为制单中
                orderStatus = "DS01";
				ttaReportProcessHeaderEntitys.get(0).setStatus(orderStatus);
				ttaReportProcessHeaderDAO_HI.saveOrUpdate(ttaReportProcessHeaderEntitys.get(0));
                break;
            case "ALLOW":
                orderStatus = "DS03";
				ttaReportProcessHeaderEntitys.get(0).setStatus(orderStatus);
				ttaReportProcessHeaderEntitys.get(0).setPassDate(new Date());
				ttaReportProcessHeaderDAO_HI.saveOrUpdate(ttaReportProcessHeaderEntitys.get(0));
                break;
            case "DRAFT":
                orderStatus = "DS01";
				ttaReportProcessHeaderEntitys.get(0).setStatus(orderStatus);
				ttaReportProcessHeaderDAO_HI.saveOrUpdate(ttaReportProcessHeaderEntitys.get(0));
                break;
            case "APPROVAL":
                orderStatus = "DS02";
				ttaReportProcessHeaderEntitys.get(0).setStatus(orderStatus);
				ttaReportProcessHeaderDAO_HI.saveOrUpdate(ttaReportProcessHeaderEntitys.get(0));
                break;
            case "CLOSED":
                orderStatus = "DS10";
				ttaReportProcessHeaderEntitys.get(0).setStatus(orderStatus);
				ttaReportProcessHeaderDAO_HI.saveOrUpdate(ttaReportProcessHeaderEntitys.get(0));
                break;

        }
			ttaReportProcessHeaderDAO_HI.fluch();
			ttaReportProcessHeaderEntitys.get(0).setOperatorUserId(userId);
			ttaReportProcessHeaderDAO_HI.saveOrUpdate(ttaReportProcessHeaderEntitys.get(0));
        return result;
    };

	@Override
	public TtaReportProcessHeaderEntity_HI saveOrUpdate2(JSONObject paramsJSON, int userId) throws Exception {
		TtaReportProcessHeaderEntity_HI instance = SaafToolUtils.setEntity(TtaReportProcessHeaderEntity_HI.class, paramsJSON, ttaReportProcessHeaderDAO_HI, userId);
		ttaReportProcessHeaderDAO_HI.saveOrUpdate(instance);
		return instance;
	}
}
