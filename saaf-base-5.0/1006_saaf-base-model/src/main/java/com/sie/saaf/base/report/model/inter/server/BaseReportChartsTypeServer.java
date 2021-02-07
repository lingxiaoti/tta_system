package com.sie.saaf.base.report.model.inter.server;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.report.model.entities.BaseReportChartsTypeEntity_HI;
import com.sie.saaf.base.report.model.inter.IBaseReportChartsType;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author huangtao
 * @createTime 2017年12月15日 11:34:50
 * @description Charts 图表库 server
 */
@Component("baseReportChartsTypeServer")
public class BaseReportChartsTypeServer extends BaseCommonServer<BaseReportChartsTypeEntity_HI> implements IBaseReportChartsType {
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseReportChartsTypeServer.class);
    @Autowired
    private ViewObject<BaseReportChartsTypeEntity_HI> baseReportChartsTypeDAO_HI;

    public BaseReportChartsTypeServer() {
        super();
    }

    /**
     * @param queryParamJSON {
     *                       chartsId 主键ID
     *                       chartsCode charts编码
     *                       chartsName charts名称
     *                       chartsScript charts脚本
     *                       dimensions dimensions
     *                       dimensionLength dimension_length
     *                       dataConversionScript 数据转换脚本
     *                       description 描述
     *                       attributeCategory attribute_category
     *                       chartsType charts类型
     *                       demoUrl 参考例子链接
     *                       chartsTitle charts标题
     *                       }
     * @return
     */
    public List<BaseReportChartsTypeEntity_HI> findBaseReportChartsTypeInfo(JSONObject queryParamJSON) {
        Map<String, Object> queryParamMap = SToolUtils.fastJsonObj2Map(queryParamJSON);
        List<BaseReportChartsTypeEntity_HI> findListResult = baseReportChartsTypeDAO_HI.findList("from BaseReportChartsTypeEntity_HI", queryParamMap);
        return findListResult;
    }


    /**
     * @param paramsJSON {
     *                   chartsId 主键ID
     *                   chartsCode charts编码
     *                   chartsName charts名称
     *                   chartsScript charts脚本
     *                   dimensions dimensions
     *                   dimensionLength dimension_length
     *                   dataConversionScript 数据转换脚本
     *                   description 描述
     *                   attributeCategory attribute_category
     *                   chartsType charts类型
     *                   demoUrl 参考例子链接
     *                   chartsTitle charts标题
     *                   }
     * @param userId
     * @return
     * @throws Exception
     */
    @Override
    public BaseReportChartsTypeEntity_HI saveOrUpdate(JSONObject paramsJSON, int userId) throws Exception {

        BaseReportChartsTypeEntity_HI instance = SaafToolUtils.setEntity(BaseReportChartsTypeEntity_HI.class, paramsJSON, baseReportChartsTypeDAO_HI, userId);
        if (instance.getChartsId() == null) {
            //新增
            SaafToolUtils.validateJsonParms(paramsJSON, "chartsCode",  "chartsType");
            List<BaseReportChartsTypeEntity_HI> list = baseReportChartsTypeDAO_HI.findByProperty("chartsCode", instance.getChartsCode());
            if (list.size() > 0)
                throw new IllegalArgumentException("chartsCode[#]已存在".replace("#", instance.getChartsCode()));
        } else {
            //修改
            SaafToolUtils.validateEntityParams(instance, "versionNum");
        }
        baseReportChartsTypeDAO_HI.saveOrUpdate(instance);
        return instance;
    }

    /**
     * @param queryParamJSON {
     *                       chartsId 主键ID
     *                       chartsCode charts编码
     *                       chartsName charts名称
     *                       chartsScript charts脚本
     *                       dimensions dimensions
     *                       dimensionLength dimension_length
     *                       dataConversionScript 数据转换脚本
     *                       description 描述
     *                       attributeCategory attribute_category
     *                       chartsType charts类型
     *                       demoUrl 参考例子链接
     *                       chartsTitle charts标题
     *                       }
     * @param pageIndex
     * @param pageRows
     * @return
     */

    public Pagination<BaseReportChartsTypeEntity_HI> findBaseUserSystemInfoWithPage(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
        StringBuffer sql = new StringBuffer();
        sql.append("from BaseReportChartsTypeEntity_HI where 1=1 ");
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        SaafToolUtils.parperHbmParam(BaseReportChartsTypeEntity_HI.class, queryParamJSON, sql, paramsMap);
        SaafToolUtils.changeQuerySort(queryParamJSON, sql, "creationDate", false);
        Pagination<BaseReportChartsTypeEntity_HI> findList = baseReportChartsTypeDAO_HI.findPagination(sql, paramsMap, pageIndex, pageRows);
        return findList;
    }


}
