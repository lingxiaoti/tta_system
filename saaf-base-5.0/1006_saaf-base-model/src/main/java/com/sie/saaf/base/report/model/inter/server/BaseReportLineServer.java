package com.sie.saaf.base.report.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.report.model.entities.BaseReportHeaderEntity_HI;
import com.sie.saaf.base.report.model.entities.BaseReportLineEntity_HI;
import com.sie.saaf.base.report.model.entities.readonly.BaseReportLineEntity_HI_RO;
import com.sie.saaf.base.report.model.inter.IBaseReportDatasource;
import com.sie.saaf.base.report.model.inter.IBaseReportHeader;
import com.sie.saaf.base.report.model.inter.IBaseReportLine;
import com.sie.saaf.common.bean.UserSessionBean;
import com.sie.saaf.common.constant.CommonConstants;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.sql.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component("baseReportLineServer")
public class BaseReportLineServer extends BaseCommonServer<BaseReportLineEntity_HI> implements IBaseReportLine {
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseReportLineServer.class);
    public static final Map<String, String> SQL_DATA_TYPES = new HashMap<>();
    static {
        //数据库字段类型
        SQL_DATA_TYPES.put("TINYINT", "NUMBER");
        SQL_DATA_TYPES.put("SMALLINT", "NUMBER");
        SQL_DATA_TYPES.put("MEDIUMINT", "NUMBER");
        SQL_DATA_TYPES.put("INTEGER", "NUMBER");
        SQL_DATA_TYPES.put("INT", "NUMBER");
        SQL_DATA_TYPES.put("BIGINT", "NUMBER");
        SQL_DATA_TYPES.put("FLOAT", "NUMBER");
        SQL_DATA_TYPES.put("DOUBLE", "NUMBER");
        SQL_DATA_TYPES.put("DECIMAL", "NUMBER");
        SQL_DATA_TYPES.put("DATE", "DATE");
        SQL_DATA_TYPES.put("DATETIME", "DATETIME");
        SQL_DATA_TYPES.put("TIMESTAMP", "DATETIME");
    }
    @Autowired
    private ViewObject<BaseReportLineEntity_HI> baseReportLineDAO_HI;

    @Autowired
    private IBaseReportHeader baseReportHeaderServer;

    @Autowired
    private IBaseReportDatasource baseReportDatasourceServer;

    @Autowired
    private BaseViewObject<BaseReportLineEntity_HI_RO> baseReportLineDAO_HI_RO;

    public BaseReportLineServer() {
        super();
    }

    public List<BaseReportLineEntity_HI> findBaseReportLineInfo(JSONObject queryParamJSON) {
        StringBuffer sql = new StringBuffer("from BaseReportLineEntity_HI where 1=1");
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        SaafToolUtils.parperHbmParam(BaseReportLineEntity_HI.class, queryParamJSON, sql, paramsMap);
        SaafToolUtils.changeQuerySort(new JSONObject(),sql," orderNo ",true);
        List<BaseReportLineEntity_HI> findListResult = baseReportLineDAO_HI.findList(sql, paramsMap);
        return findListResult;
    }


    @Override
    public Pagination<BaseReportLineEntity_HI_RO> find(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
        StringBuffer sql = new StringBuffer(BaseReportLineEntity_HI_RO.query);
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        SaafToolUtils.parperHbmParam(BaseReportLineEntity_HI_RO.class, queryParamJSON, sql, paramsMap);
        StringBuffer countSql = SaafToolUtils.getSimpleSqlCountString(sql,"count(1)");
        SaafToolUtils.changeQuerySort(new JSONObject(),sql," brl.order_no ",false);
        Pagination<BaseReportLineEntity_HI_RO> findList = baseReportLineDAO_HI_RO.findPagination(sql,countSql, paramsMap, pageIndex, pageRows);
        return findList;
    }

    public Object saveBaseReportLineInfo(JSONObject queryParamJSON) {
        BaseReportLineEntity_HI baseReportLineEntity_HI = JSON.parseObject(queryParamJSON.toString(), BaseReportLineEntity_HI.class);
        Object resultData = baseReportLineDAO_HI.save(baseReportLineEntity_HI);
        return resultData;
    }


    /**
     * 通过报表头定义的sql 自动生成报表行
     * @param params
     * {
     *     reportHeaderId:报表头id
     * }
     * @param userSessionBean
     * @return
     */
    @Override
    public  List<BaseReportLineEntity_HI> saveReportLinesByDatasource(JSONObject params, UserSessionBean userSessionBean) throws SQLException {
        Assert.notNull(userSessionBean, "请重新登录");
        SaafToolUtils.validateJsonParms(params,"reportHeaderId");
        BaseReportHeaderEntity_HI reportHeader = baseReportHeaderServer.getById(params.getInteger("reportHeaderId"));
        Assert.notNull(reportHeader, "报表头id[#]不存在".replace("#", params.getInteger("reportHeaderId") + ""));
        Assert.notNull(reportHeader.getQuerySql(),"头表查询sql不能为空");
        BasicDataSource dataSource = baseReportDatasourceServer.getDatasource(reportHeader.getDsId());
        Assert.notNull(dataSource, "数据源异常");
        List<BaseReportLineEntity_HI> lines = generateColumns(dataSource, reportHeader,userSessionBean);
        List<BaseReportLineEntity_HI> list= baseReportLineDAO_HI.findByProperty("reportHeaderId",params.getInteger("reportHeaderId"));
        baseReportLineDAO_HI.delete(list);
        baseReportLineDAO_HI.save(lines);
        return lines;
    }


    @Override
    public BaseReportLineEntity_HI saveOrUpdate(JSONObject params, UserSessionBean userSessionBean) throws Exception {
        Assert.notNull(userSessionBean, "请重新登录");
        BaseReportLineEntity_HI instance = SaafToolUtils.setEntity(BaseReportLineEntity_HI.class, params, baseReportLineDAO_HI, userSessionBean.getUserId());
        if (instance.getReportLineId() == null) {
            //新增
            SaafToolUtils.validateJsonParms(params, "reportHeaderId","columnCode");
            BaseReportHeaderEntity_HI reportHeader = baseReportHeaderServer.getById(instance.getReportHeaderId());
            Assert.notNull(reportHeader, "报表头id[#]不存在".replace("#", instance.getReportHeaderId() + ""));
            instance.setReportHeaderCode(reportHeader.getReportHeaderCode());

            JSONObject jsonObject=new JSONObject();
            jsonObject.put("reportHeaderId",instance.getReportHeaderId());
            jsonObject.put("columnCode",instance.getColumnCode());

            List<BaseReportLineEntity_HI>  lineList= baseReportLineDAO_HI.findByProperty(jsonObject);
            if (lineList.size()>0)
                throw new IllegalArgumentException("列编码[#]重复".replace("#",instance.getColumnCode()));
        } else {
            //修改
            SaafToolUtils.validateJsonParms(params, "versionNum");

        }
        if (userSessionBean!=null){
            instance.setOperatorUserId(userSessionBean.getUserId());
        }
        baseReportLineDAO_HI.saveOrUpdate(instance);
        return  instance;
    }


    @Override
    public List<BaseReportLineEntity_HI> saveOrUpdateList(JSONArray jsonArray, UserSessionBean userSessionBean) throws Exception {
        if (jsonArray==null || jsonArray.size()==0)
            return new ArrayList<>();
        List<BaseReportLineEntity_HI> list=new ArrayList<>();
        for (int i=0;i<jsonArray.size();i++){
            list.add(saveOrUpdate(jsonArray.getJSONObject(i),userSessionBean));
        }
        return list;
    }

    @Override
    public List<BaseReportLineEntity_HI> generateColumns(BasicDataSource basicDataSource,BaseReportHeaderEntity_HI header,UserSessionBean userSessionBean) {
        String sql = baseReportHeaderServer.replaceSystemVariable(header.getQuerySql(), (JSONObject) JSON.toJSON(userSessionBean));

        if (basicDataSource == null || StringUtils.isBlank(sql))
            return Collections.emptyList();

        //这里只获取sql字段名, 添加非真条件避免大数据量查询
        String reg = "[\\n\\r\\s]where[\\n\\r\\s]";
        Pattern pattern = Pattern.compile(reg, Pattern.CASE_INSENSITIVE);
        Matcher m = pattern.matcher(sql);
        if (m.find() == false) {
            sql += " where 1=2 ";
        } else {
            sql += " and 1=2 ";
        }

        try (
                Connection connection = basicDataSource.getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql + " and 1=2")
        ) {
            ResultSetMetaData data = resultSet.getMetaData();
            List<BaseReportLineEntity_HI> result = new ArrayList<>();
            for (int i = 1; i <= data.getColumnCount(); i++) {
                BaseReportLineEntity_HI instance = new BaseReportLineEntity_HI();
                String columName = data.getColumnName(i);
                String columType=data.getColumnTypeName(i);

                String columnTypeName = SQL_DATA_TYPES.get(columType);
                if (columnTypeName == null)
                    columnTypeName = "STRING";
                if (StringUtils.isNotBlank(data.getColumnLabel(i)))
                    columName = data.getColumnLabel(i);


                //获取字段注释
                ResultSet rs = connection.getMetaData().getColumns(data.getCatalogName(i), data.getSchemaName(i), data.getTableName(i), data.getColumnName(i));
                while (rs.next()) {
                    instance.setColumnName(rs.getString("REMARKS"));
                }
                //没有字段注释取字段名
                if (StringUtils.isBlank(instance.getColumnName())){
                    instance.setColumnName(columName);
                }

                if (StringUtils.isBlank(instance.getColumnDataType())){
                    instance.setColumnDataType(columnTypeName);
                    instance.setParamDisplayType(columnTypeName);
                }
                instance.setParamDisplayName(instance.getColumnName());
                instance.setReportHeaderId(header.getReportHeaderId());
                instance.setColumnCode(columName);
                instance.setColumnDisplayFlag(CommonConstants.ENABLED_TRUE);
                instance.setParamRequiredFlag(CommonConstants.ENABLED_FALSE);
                instance.setReportHeaderCode(header.getReportHeaderCode());
                instance.setOrderNo(i*10);
                instance.setReportLineDesc(header.getReportDesc());
                instance.setOperatorUserId(userSessionBean.getUserId());
                result.add(instance);
            }
            return result;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return Collections.emptyList();
    }


    @Override
    public void delete(String[] ids){
        if (ids==null || ids.length==0)
            return;
        for (String id:ids){
           baseReportLineDAO_HI.delete(Integer.valueOf(id));
        }
    }


}
