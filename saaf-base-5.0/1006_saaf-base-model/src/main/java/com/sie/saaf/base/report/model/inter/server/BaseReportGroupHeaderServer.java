package com.sie.saaf.base.report.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.report.model.entities.BaseReportGroupHeaderEntity_HI;
import com.sie.saaf.base.report.model.inter.IBaseReportGroupHeader;
import com.sie.saaf.common.util.SaafToolUtils;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.dao.ViewObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("baseReportGroupHeaderServer")
public class BaseReportGroupHeaderServer implements IBaseReportGroupHeader {
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseReportGroupHeaderServer.class);
    @Autowired
    private ViewObject<BaseReportGroupHeaderEntity_HI> baseReportGroupHeaderDAO_HI;

    public BaseReportGroupHeaderServer() {
        super();
    }

    public List<BaseReportGroupHeaderEntity_HI> findBaseReportGroupHeaderInfo(JSONObject queryParamJSON) {
        Map<String, Object> queryParamMap = SToolUtils.fastJsonObj2Map(queryParamJSON);
        List<BaseReportGroupHeaderEntity_HI> findListResult = baseReportGroupHeaderDAO_HI.findList("from BaseReportGroupHeaderEntity_HI where 1=1 ", queryParamMap);
        return findListResult;
    }

    public Object saveBaseReportGroupHeaderInfo(JSONObject queryParamJSON) {
        BaseReportGroupHeaderEntity_HI baseReportGroupHeaderEntity_HI = JSON.parseObject(queryParamJSON.toString(), BaseReportGroupHeaderEntity_HI.class);
        Object resultData = baseReportGroupHeaderDAO_HI.save(baseReportGroupHeaderEntity_HI);
        return resultData;
    }

    @Override
    public BaseReportGroupHeaderEntity_HI saveOrUpdatetGroupHeader(JSONObject paramsJSON, int userId) throws Exception {
        BaseReportGroupHeaderEntity_HI instance = SaafToolUtils.setEntity(BaseReportGroupHeaderEntity_HI.class, paramsJSON, baseReportGroupHeaderDAO_HI, userId);
        if (instance.getReportGroupHeaderId()==null){
            SaafToolUtils.validateJsonParms(paramsJSON,"reportHeaderId","reportGroupId");
            Map<String,Object> map=new HashMap<>();
            map.put("reportHeaderId",instance.getReportHeaderId());
            map.put("reportGroupId",instance.getReportGroupId());
            List<BaseReportGroupHeaderEntity_HI> list=baseReportGroupHeaderDAO_HI.findByProperty(map);
            if (list.size()>0)
                return list.get(0);
        }else {
            SaafToolUtils.validateJsonParms(paramsJSON,"versionNum");
        }
        baseReportGroupHeaderDAO_HI.saveOrUpdate(instance);
        return instance;
    }

}
