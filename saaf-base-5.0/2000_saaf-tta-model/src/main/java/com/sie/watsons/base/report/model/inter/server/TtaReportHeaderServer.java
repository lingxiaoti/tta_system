package com.sie.watsons.base.report.model.inter.server;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Clob;
import java.util.*;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.clause.model.entities.TtaClauseTreeEntity_HI;
import com.sie.watsons.base.report.model.entities.TtaReportHeaderEntity_HI;
import com.sie.watsons.base.report.model.entities.readonly.TtaDmCheckingEntity_HI_RO;
import com.sie.watsons.base.report.model.entities.readonly.TtaReportHeaderEntity_HI_RO;
import com.sie.watsons.base.report.model.inter.ITtaReportHeader;
import com.yhg.base.utils.StringUtils;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

@Component("ttaReportHeaderServer")
public class TtaReportHeaderServer extends BaseCommonServer<TtaReportHeaderEntity_HI> implements ITtaReportHeader {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaReportHeaderServer.class);

	@Autowired
	private BaseCommonDAO_HI<TtaReportHeaderEntity_HI> ttaReportHeaderDAO_HI;
    @Autowired
    private BaseViewObject<TtaReportHeaderEntity_HI_RO> ttaReportHeaderDAO_RO;

	public TtaReportHeaderServer() {
		super();
	}

    @Override
    public Pagination<TtaReportHeaderEntity_HI_RO> find(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
        StringBuffer sql = new StringBuffer();
        sql.append(TtaReportHeaderEntity_HI_RO.TTA_REPORT_HEADER);
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        SaafToolUtils.parperParam(queryParamJSON, "h.is_publish", "isPublish", sql, paramsMap, "=");
        SaafToolUtils.parperParam(queryParamJSON, "h.batch_id", "batchId", sql, paramsMap, "fulllike");
        SaafToolUtils.parperParam(queryParamJSON, "h.status", "status", sql, paramsMap, "fulllike");
        SaafToolUtils.parperParam(queryParamJSON, "h.report_type", "reportType", sql, paramsMap, "fulllike");
        SaafToolUtils.parperParam(queryParamJSON, "h.promotion_section", "promotionSection", sql, paramsMap, "fulllike");
        SaafToolUtils.changeQuerySort(queryParamJSON, sql, " h.ID desc", false);
        Pagination<TtaReportHeaderEntity_HI_RO> findList = ttaReportHeaderDAO_RO.findPagination(sql,SaafToolUtils.getSqlCountString(sql), paramsMap, pageIndex, pageRows);
        return findList;
    }


    @Override
    public JSONArray updateStatus(JSONObject paramsJSON, Integer userId) throws Exception {

        Integer id = paramsJSON.getIntValue("id");//单据Id
        JSONObject headerObject = new JSONObject();
        headerObject.put("id", id);

        JSONObject param=new JSONObject();
        JSONArray result=new JSONArray();
        List<TtaClauseTreeEntity_HI> actList= new ArrayList<TtaClauseTreeEntity_HI>();
        //查询表单工具
        List<TtaReportHeaderEntity_HI> ttaReportHeaderEntitys = ttaReportHeaderDAO_HI.findByProperty("id",headerObject.get("id"));
        String orderStatus = null;//状态
        switch (paramsJSON.getString("status")) {
            case "REFUSAL":
                orderStatus = "DS01";
                ttaReportHeaderEntitys.get(0).setStatus(orderStatus);
                ttaReportHeaderDAO_HI.saveOrUpdate(ttaReportHeaderEntitys.get(0));
                break;
            case "ALLOW":
                orderStatus = "DS03";
                ttaReportHeaderEntitys.get(0).setStatus(orderStatus);
                ttaReportHeaderEntitys.get(0).setPassDate(new Date());
                ttaReportHeaderDAO_HI.saveOrUpdate(ttaReportHeaderEntitys.get(0));
                break;
            case "DRAFT":
                orderStatus = "DS01";
                ttaReportHeaderEntitys.get(0).setStatus(orderStatus);
                ttaReportHeaderDAO_HI.saveOrUpdate(ttaReportHeaderEntitys.get(0));
                break;
            case "APPROVAL":
                orderStatus = "DS02";
                ttaReportHeaderEntitys.get(0).setStatus(orderStatus);
                ttaReportHeaderDAO_HI.saveOrUpdate(ttaReportHeaderEntitys.get(0));
                break;
            case "CLOSED":
                orderStatus = "DS10";
                ttaReportHeaderEntitys.get(0).setStatus(orderStatus);
                ttaReportHeaderDAO_HI.saveOrUpdate(ttaReportHeaderEntitys.get(0));
                break;

        }
        ttaReportHeaderDAO_HI.fluch();
        ttaReportHeaderEntitys.get(0).setOperatorUserId(userId);
        ttaReportHeaderDAO_HI.saveOrUpdate(ttaReportHeaderEntitys.get(0));
        return result;
    }


    @Override
    public TtaReportHeaderEntity_HI saveOrUpdate(JSONObject paramsJSON, int userId) throws Exception {
        TtaReportHeaderEntity_HI instance = SaafToolUtils.setEntity(TtaReportHeaderEntity_HI.class, paramsJSON, ttaReportHeaderDAO_HI, userId);
        List<Map<String, Object>> entityList = ttaReportHeaderDAO_HI.queryNamedSQLForList(TtaDmCheckingEntity_HI_RO.getDmPublishCheck(instance.getBatchId()), new HashMap<String, Object>());
        if (entityList != null && !entityList.isEmpty()) {
            Clob  contentIds = (Clob) entityList.get(0).get("CONTENT_IDS");
            if (contentIds != null) {
                String clob2Str = getClob2Str(contentIds);
                throw new IllegalArgumentException("如下活动ID没有找到对应的人员信息：\n" + clob2Str);
            }
        }
        instance.setIsPublish("Y");
        instance.setIsPublishBy(userId);
        instance.setIsPublishDate(new Date());
        ttaReportHeaderDAO_HI.saveOrUpdate(instance);
        return instance;
    }

    @Override
    public void deleteReportHeader(Integer id) {
        TtaReportHeaderEntity_HI entity = ttaReportHeaderDAO_HI.getById(id);
        if (entity == null) {
            throw new IllegalArgumentException("参数错误，删除的数据不存在！");
        }
        if ("Y".equalsIgnoreCase(entity.getIsPublish())) {
            throw new IllegalArgumentException("只能删除未发布的记录！");
        }
        String batchId = entity.getBatchId();
        String reportType = entity.getReportType();
        String sql = "";
        switch (reportType){
            case "DM":
                sql = "delete from tta_dm_checking where  batch_code = '" + batchId +"'";
                break;
            case "OSD":
                break;
            case "NPP":
                sql = "delete from tta_npp_new_product_report tnn where tnn.batch_code ='" + batchId + "'";
            default:
                break;
        }
        ttaReportHeaderDAO_HI.delete(id);
        ttaReportHeaderDAO_HI.executeSqlUpdate(sql);
    }

    public String  getClob2Str(Clob content) {
        StringBuffer buffer = new StringBuffer();
        if (content != null) {
            BufferedReader br = null;
            try {
                Reader r = content.getCharacterStream();  //将Clob转化成流
                br = new BufferedReader(r);
                String s = null;
                while ((s = br.readLine()) != null) {
                    buffer.append(s);
                }
            } catch (Exception e) {
                e.printStackTrace();	//打印异常信息
            } finally {
                if (br != null) {
                    try {
                        br.close(); //关闭流
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return buffer.toString();
    }

}
