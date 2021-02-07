package com.sie.saaf.base.report.model.inter.server;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.report.model.entities.BaseReportGroupEntity_HI;
import com.sie.saaf.base.report.model.entities.BaseReportGroupHeaderEntity_HI;
import com.sie.saaf.base.report.model.entities.BaseReportHeaderEntity_HI;
import com.sie.saaf.base.report.model.entities.readonly.BaseReportHeaerDatasourceEntity_HI_RO;
import com.sie.saaf.base.report.model.inter.IBaseReportGroup;
import com.sie.saaf.base.report.model.inter.IBaseReportGroupHeader;
import com.sie.saaf.base.report.model.inter.IBaseReportHeader;
import com.sie.saaf.common.bean.UserSessionBean;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component("baseReportGroupServer")
public class BaseReportGroupServer extends BaseCommonServer<BaseReportGroupEntity_HI> implements IBaseReportGroup {
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseReportGroupServer.class);
    @Autowired
    private ViewObject<BaseReportGroupEntity_HI> baseReportGroupDAO_HI;

    @Autowired
    private ViewObject<BaseReportHeaderEntity_HI> baseReportHeaderDAO_HI;

    @Autowired
    private ViewObject<BaseReportGroupHeaderEntity_HI> baseReportGroupHeaderDAO_HI;

    @Autowired
    private BaseViewObject<BaseReportHeaerDatasourceEntity_HI_RO> baseReportHeaerDatasourceDAO_HI_RO;

    @Autowired
    private IBaseReportGroupHeader baseReportGroupHeaderServer;

    @Autowired
    private IBaseReportHeader baseReportHeaderServer;




    public BaseReportGroupServer() {
        super();
    }


    /**
     * 新增、保存报表组
     *
     * @param group           报表组信息
     * @param headers         报表头信息
     * @param userSessionBean 用户sesson
     * @return
     * @throws Exception
     */
    @Override
    public BaseReportGroupEntity_HI saveOrUpdateReportGroup(JSONObject group, JSONArray headers, UserSessionBean userSessionBean) throws Exception {
        BaseReportGroupEntity_HI instance = null;
        if (group != null)
            instance = saveOrUpdate(group);
        if (headers != null && headers.size() > 0) {
            for (int i = 0; i < headers.size(); i++) {
                JSONObject jsonObject = headers.getJSONObject(i);
                if (instance != null)
                    jsonObject.put("reportGroupId", instance.getReportGroupId());
                if (StringUtils.isBlank(jsonObject.getString("reportGroupId")))
                    SaafToolUtils.validateJsonParms(jsonObject, "reportGroupId", "reportHeaderId");
                baseReportGroupHeaderServer.saveOrUpdatetGroupHeader(jsonObject, userSessionBean.getUserId());
                baseReportHeaderServer.saveOrUpdateHeader(jsonObject,userSessionBean);

            }
        }
        return instance;
    }


    /**
     * 查询报表组中的报表
     *
     * @param groupId 报表组id
     * @return
     */
    @Override
    public List<BaseReportHeaerDatasourceEntity_HI_RO> deleteAndFindReportGroupDetail(Integer groupId) {
        if (groupId == null)
            return Collections.emptyList();
        List<BaseReportGroupHeaderEntity_HI> groupDetail = baseReportGroupHeaderDAO_HI.findByProperty("reportGroupId", groupId);
        if (groupDetail.size() == 0)
            return Collections.emptyList();
        List<BaseReportHeaerDatasourceEntity_HI_RO> headers = new ArrayList<>();
        for (BaseReportGroupHeaderEntity_HI obj : groupDetail) {
            Map<String,Object> map=new HashMap<>();
            StringBuilder sql=new StringBuilder(BaseReportHeaerDatasourceEntity_HI_RO.query);
            sql.append(" and  brh.report_header_id=:reportHeaderId ");
            map.put("reportHeaderId",obj.getReportHeaderId());
            List<BaseReportHeaerDatasourceEntity_HI_RO> header = baseReportHeaerDatasourceDAO_HI_RO.findList(sql,map);
            //删除错乱数据
            if (header.size()==0)
                baseReportGroupHeaderDAO_HI.delete(obj);
            else
                headers.addAll(header);
        }
        return headers;
    }


    /**
     * 删除报表组中映射的报表
     *
     * @param reportGroupId 报表组Id
     * @param ids 的表Id集合
     */
    @Override
    public void deleteReportHeaderInGroup(Integer reportGroupId,String[] ids) {
        if(reportGroupId==null)
            return;
        if (ids == null || ids.length == 0)
            return;

        StringBuffer sb = new StringBuffer();
        sb.append("from BaseReportGroupHeaderEntity_HI where reportGroupId=:reportGroupId and reportHeaderId in (:reportHeaderId)");

        Map<String,Object> paramsMap = new HashMap<>();
        paramsMap.put("reportGroupId",reportGroupId);
        List<Integer> reportHeaderIds = new ArrayList<>();
        for(String id : ids) {
            reportHeaderIds.add(Integer.parseInt(id));
        }
        paramsMap.put("reportHeaderId",reportHeaderIds);
        List<BaseReportGroupHeaderEntity_HI> entities = baseReportGroupHeaderDAO_HI.findList(sb,paramsMap);
        if(entities!=null && !entities.isEmpty()) {
            for (BaseReportGroupHeaderEntity_HI entity : entities) {
                baseReportGroupHeaderDAO_HI.delete(entity);
            }
        }
    }


    /**
     * 删除整个报表组
     *
     * @param ids
     */
    @Override
    public void delete(String[] ids) {
        if (ids == null || ids.length == 0)
            return;
        for (String id : ids) {
            if (StringUtils.isBlank(id))
                continue;
            List<BaseReportGroupHeaderEntity_HI> list = baseReportGroupHeaderDAO_HI.findByProperty("reportGroupId", Integer.valueOf(id));
            if (list.size() > 0)
                baseReportGroupHeaderDAO_HI.delete(list);
            baseReportGroupDAO_HI.delete(Integer.valueOf(id));
        }

    }


}
