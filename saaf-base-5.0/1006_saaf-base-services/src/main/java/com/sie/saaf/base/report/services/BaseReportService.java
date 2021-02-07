package com.sie.saaf.base.report.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.sie.saaf.base.report.model.entities.BaseReportHeaderEntity_HI;
import com.sie.saaf.base.report.model.entities.BaseReportLineEntity_HI;
import com.sie.saaf.base.report.model.entities.readonly.BaseReportHeaerDatasourceEntity_HI_RO;
import com.sie.saaf.base.report.model.entities.readonly.BaseReportLineEntity_HI_RO;
import com.sie.saaf.base.report.model.inter.IBaseReportHeader;
import com.sie.saaf.base.report.model.inter.IBaseReportLine;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.common.util.SaafToolUtils;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.paging.Pagination;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * @author huangtao
 *
 */

@RestController
@RequestMapping("/baseReportService")
public class BaseReportService extends CommonAbstractService {
    private static final Logger log = LoggerFactory.getLogger(BaseReportService.class);

    @Autowired
    private IBaseReportHeader baseReportHeaderServer;

    @Autowired
    private IBaseReportLine baseReportLineServer;

    /**
     * @param params    {
     *                  reportHeaderId; //主键ID
     *                  reportHeaderCode; //报表编码
     *                  reportGroupId; //报表组Id
     *                  webScript; //页面脚本
     *                  countSql; //统计的查询SQL
     *                  querySql; //报表查询的sql语句
     *                  dsId; //对应的数据源Id
     *                  whereClause; //查询条件
     *                  orderBy; //排序标识
     *                  queryFlag; //默认是否执行查询标识
     *                  countFlag; //是否计算
     *                  reportDesc; //描述
     *                  chartsTitle; //charts标题
     *                  chartsType; //charts类型
     *                  chartsCode; //charts编码
     *                  dataConversionScript; //数据转换脚本
     *                  <p/>
     *                  }
     * @param pageIndex
     * @param pageRows
     * @return
     * {
     *                  reportHeaderId; //主键ID
     *                  reportHeaderCode; //报表编码
     *                  reportGroupId; //报表组Id
     *                  webScript; //页面脚本
     *                  countSql; //统计的查询SQL
     *                  querySql; //报表查询的sql语句
     *                  dsId; //对应的数据源Id
     *                  whereClause; //查询条件
     *                  orderBy; //排序标识
     *                  queryFlag; //默认是否执行查询标识
     *                  countFlag; //是否计算
     *                  reportDesc; //描述
     *                  chartsTitle; //charts标题
     *                  chartsType; //charts类型
     *                  chartsCode; //charts编码
     *                  dataConversionScript; //数据转换脚本
     * }
     */
    @RequestMapping(method = RequestMethod.POST, value = "findReportHeader")
    public String findReportHeader(@RequestParam(required = false) String params,
                                   @RequestParam(required = false, defaultValue = "-1") String pageIndex,
                                   @RequestParam(required = false, defaultValue = "-1") String pageRows) {
        try {
            JSONObject jsonObject = new JSONObject();

            if (StringUtils.isNotBlank(params))
                jsonObject = JSON.parseObject(params);

            Pagination<BaseReportHeaerDatasourceEntity_HI_RO> pagination = baseReportHeaderServer.find(jsonObject, Integer.valueOf(Objects.toString(pageIndex, "-1")), Integer.valueOf(Objects.toString(pageRows, "-1")));
            jsonObject = (JSONObject) JSON.toJSON(pagination);
            jsonObject.put(STATUS, SUCCESS_STATUS);
            jsonObject.put(MSG, SUCCESS_MSG);
            return jsonObject.toString();
        } catch (IllegalArgumentException e) {
            log.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, "服务异常," + e.getMessage(), 0, null).toString();
        }
    }

    /**
     * 查询报表行
     *
     * @param params
     * {
     *      reportLineId; //主键ID
     *      reportHeaderId; //头表Id
     *      reportHeaderCode; //报表编码
     *      orderNo; //列排序编号
     *      columnCode; //列编码
     *      columnName; //列名称
     *      columnDisplayWidth; //列显示宽度
     *      columnDataType; //数据类型
     *      columnDisplayFlag; //列是否显示表示 Y表示显示 N表示不显示，默认为Y
     *      paramRequiredFlag; //查询条件是否必输 Y表示必输 N表示非必输，默认为N
     *      paramDisplayType; //查询条件显示类型
     *      paramDisplayName; //查询条件如果是dropdownlist/listofvalue/dropdowncheckbox界面显示的字段名
     *      paramUseCode; //查询条件如果是dropdownlist/listofvalue/dropdowncheckbox系统使用的字段名
     *      paramDataFromType; //查询条件如果是dropdownlist/listofvalue/dropdowncheckbox来源的渠道，lookup，profile
     *      paramDefaultValue; //查询条件的默认值
     *      countFlag; //count_flag
     *      sumFlag; //sum_flag
     *      avgFlag; //avg_flag
     *      reportLineDesc; //report_line_desc
     * }
     * @param pageIndex
     * @param pageRows
     * @return
     * {
     *      reportLineId; //主键ID
     *      reportHeaderId; //头表Id
     *      reportHeaderCode; //报表编码
     *      orderNo; //列排序编号
     *      columnCode; //列编码
     *      columnName; //列名称
     *      columnDisplayWidth; //列显示宽度
     *      columnDataType; //数据类型
     *      columnDisplayFlag; //列是否显示表示 Y表示显示 N表示不显示，默认为Y
     *      paramRequiredFlag; //查询条件是否必输 Y表示必输 N表示非必输，默认为N
     *      paramDisplayType; //查询条件显示类型
     *      paramDisplayName; //查询条件如果是dropdownlist/listofvalue/dropdowncheckbox界面显示的字段名
     *      paramUseCode; //查询条件如果是dropdownlist/listofvalue/dropdowncheckbox系统使用的字段名
     *      paramDataFromType; //查询条件如果是dropdownlist/listofvalue/dropdowncheckbox来源的渠道，lookup，profile
     *      paramDefaultValue; //查询条件的默认值
     *      countFlag; //count_flag
     *      sumFlag; //sum_flag
     *      avgFlag; //avg_flag
     *      reportLineDesc; //report_line_desc
     * }
     */
    @RequestMapping(method = RequestMethod.POST, value = "findReportLine")
    public String findReportLine(@RequestParam(required = false) String params,
                                 @RequestParam(required = false, defaultValue = "-1") Integer pageIndex,
                                 @RequestParam(required = false, defaultValue = "-1") Integer pageRows) {
        try {
            JSONObject jsonObject = new JSONObject();

            if (StringUtils.isNotBlank(params))
                jsonObject = JSON.parseObject(params);
            Pagination<BaseReportLineEntity_HI_RO> list = baseReportLineServer.find(jsonObject, pageIndex, pageRows);
            jsonObject = (JSONObject) JSON.toJSON(list);
            jsonObject.put(STATUS, SUCCESS_STATUS);
            jsonObject.put(MSG, SUCCESS_MSG);
            return jsonObject.toString();
        } catch (IllegalArgumentException e) {
            log.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, "服务异常," + e.getMessage(), 0, null).toString();
        }
    }


    /**
     * 报表头行信息修改、保存
     * @param params {
     *               header:{报表头信息}
     *               line:[报表行信息]
     *               }
     * @return
     * {
     *     header:{报表头信息}
     *     line:[报表行信息]
     * }
     */
    @RequestMapping(method = RequestMethod.POST, value = "saveOrUpdate")
    public String saveOrUpdate(@RequestParam(required = true) String params) {
        try {
            JSONObject jsonObject = JSON.parseObject(params);
            JSONObject result = new JSONObject();
            if (jsonObject.containsKey("header") && jsonObject.getJSONObject("header") != null) {
                BaseReportHeaderEntity_HI instance = baseReportHeaderServer.saveOrUpdateHeader(jsonObject.getJSONObject("header"),getUserSessionBean());
                result.put("header", instance);
            }
            if (jsonObject.containsKey("line") && jsonObject.getJSONArray("line") != null) {
                List<BaseReportLineEntity_HI> list = baseReportLineServer.saveOrUpdateList(jsonObject.getJSONArray("line"), getUserSessionBean());
                result.put("line", list);
            }
            return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, result).toString();
        } catch (IllegalArgumentException e) {
            log.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", "服务异常", 0, null).toString();
        }
    }


    /**
     * 自动生成行数据
     *
     * @param params
     * {
     *     reportHeaderId:头表id
     * }
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "autoSaveReportLine")
    public String autoSaveReportLine(@RequestParam(required = true) String params) {
        try {
            JSONObject jsonObject = JSON.parseObject(params);
            List<BaseReportLineEntity_HI> list = baseReportLineServer.saveReportLinesByDatasource(jsonObject, getUserSessionBean());
            return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, list.size(), list).toString();
        } catch (IllegalArgumentException e) {
            log.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", "服务异常", 0, null).toString();
        }
    }


    /**
     * 删除头
     *
     * @param params {
     *               id:主键
     *               }
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "deleteHeader")
    public String deleteHeader(@RequestParam(required = false) String params) {
        try {
            if (StringUtils.isBlank(params)) {
                return SToolUtils.convertResultJSONObj("F", "缺少参数:id", 0, null).toString();
            }
            JSONObject jsonObject = JSON.parseObject(params);
            String[] ids = jsonObject.getString("id").split(",");
            baseReportHeaderServer.delete(ids);
            return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, null).toString();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", "删除失败," + e.getMessage(), 0, null).toString();
        }
    }

    /**
     * 删除行
     *
     * @param params {
     *               id:主键
     *               }
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "deleteLine")
    public String deleteLine(@RequestParam(required = false) String params) {
        try {
            if (StringUtils.isBlank(params)) {
                return SToolUtils.convertResultJSONObj("F", "缺少参数:id", 0, null).toString();
            }
            JSONObject jsonObject = JSON.parseObject(params);
            String[] ids = jsonObject.getString("id").split(",");
            baseReportLineServer.delete(ids);
            return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, null).toString();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", "删除失败," + e.getMessage(), 0, null).toString();
        }
    }


    /**
     *
     *
     *
     * @param params
     * {
     *     queryParams:{} //查询参数
     *     reportHeaderId:报表头id
     *
     * }
     * @param pageIndex
     * @param pageRows
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,value = "findReportSql")
    public String findReportSql(@RequestParam String params,
                                @RequestParam(required = false, defaultValue = "-1") Integer pageIndex,
                                @RequestParam(required = false, defaultValue = "-1") Integer pageRows) {
        try {
            JSONObject jsonObject = JSON.parseObject(params);
            SaafToolUtils.validateJsonParms(jsonObject,"reportHeaderCode");
            Pagination<JSONObject> list = baseReportHeaderServer.findReportSql(jsonObject.getString("reportHeaderCode"), (JSONObject) JSON.toJSON(getUserSessionBean()),jsonObject.getJSONObject("queryParams"),pageIndex,pageRows);
            jsonObject = (JSONObject) JSON.toJSON(list);
            jsonObject.put(STATUS, SUCCESS_STATUS);
            jsonObject.put(MSG, SUCCESS_MSG);
            return jsonObject.toJSONString(jsonObject, SerializerFeature.WriteDateUseDateFormat);
        } catch (IllegalArgumentException e) {
            log.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", "服务异常", 0, null).toString();
        }
    }


    /**
     *
     * @param params
     * {
     *     queryParams:{} //查询参数
     *     reportHeaderId:报表头id
     * }
     * @param pageIndex
     * @param pageRows
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,value = "findReportSqlWithColumn")
    public String findReportSqlWithColumn(@RequestParam String params,
                                @RequestParam(required = false, defaultValue = "-1") Integer pageIndex,
                                @RequestParam(required = false, defaultValue = "-1") Integer pageRows) {
        try {
            JSONObject jsonObject = JSON.parseObject(params);
            SaafToolUtils.validateJsonParms(jsonObject,"reportHeaderCode");
            Pagination<JSONObject> pagination = baseReportHeaderServer.findReportSql(jsonObject.getString("reportHeaderCode"), (JSONObject) JSON.toJSON(getUserSessionBean()),jsonObject.getJSONObject("queryParams"),pageIndex,pageRows);
            List<JSONObject> jsonObjects=pagination.getData();
            List<JSONObject> jsonObjectList=new ArrayList<>();
            for (JSONObject json:jsonObjects){
                Set<String> keys=json.keySet();
                JSONObject itemJson=new JSONObject();
                for (String key:keys){
                    int i=1;
                    itemJson.put("column"+(i++),json.get(key));
                }
                jsonObjectList.add(itemJson);
            }
            jsonObject = (JSONObject) JSON.toJSON(pagination);
            jsonObject.put(STATUS, SUCCESS_STATUS);
            jsonObject.put(MSG, SUCCESS_MSG);
            jsonObject.put(DATA,jsonObjectList);
            return jsonObject.toJSONString(jsonObject, SerializerFeature.WriteDateUseDateFormat);
        } catch (IllegalArgumentException e) {
            log.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", "服务异常", 0, null).toString();
        }
    }


    @Override
    public IBaseCommon<?> getBaseCommonServer() {
        return null;
    }
}
