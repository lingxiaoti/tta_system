package com.sie.saaf.base.report.model.inter.server;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.report.model.entities.BaseReportHeaderEntity_HI;
import com.sie.saaf.base.report.model.entities.BaseReportLineEntity_HI;
import com.sie.saaf.base.report.model.entities.readonly.BaseReportHeaerDatasourceEntity_HI_RO;
import com.sie.saaf.base.report.model.inter.IBaseReportDatasource;
import com.sie.saaf.base.report.model.inter.IBaseReportHeader;
import com.sie.saaf.base.report.model.inter.IBaseReportLine;
import com.sie.saaf.common.bean.UserSessionBean;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.DynamicBaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component("baseReportHeaderServer")
public class BaseReportHeaderServer extends BaseCommonServer<BaseReportHeaderEntity_HI> implements IBaseReportHeader {
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseReportHeaderServer.class);
    @Autowired
    private ViewObject<BaseReportHeaderEntity_HI> baseReportHeaderDAO_HI;

    @Autowired
    private ViewObject<BaseReportLineEntity_HI> baseReportLineDAO_HI;

    @Autowired
    private IBaseReportLine baseReportLineServer;

    @Autowired
    private IBaseReportDatasource baseReportDatasourceServer;

    @Autowired
    private BaseViewObject<BaseReportHeaerDatasourceEntity_HI_RO> baseReportHeaerDatasourceDAO_HI_RO;

    @Autowired
    private DynamicBaseViewObject commonDAO_HI_DY;



    public BaseReportHeaderServer() {
        super();
    }

    public List<BaseReportHeaderEntity_HI> findBaseReportHeaderInfo(JSONObject queryParamJSON) {
        Map<String, Object> queryParamMap = SToolUtils.fastJsonObj2Map(queryParamJSON);
        List<BaseReportHeaderEntity_HI> findListResult = baseReportHeaderDAO_HI.findList("from BaseReportHeaderEntity_HI", queryParamMap);
        return findListResult;
    }


    /**
     * 保存修改报表头
     *
     * @param paramsJSON
     * @return
     * @throws Exception
     */
    @Override
    public BaseReportHeaderEntity_HI saveOrUpdateHeader(JSONObject paramsJSON, UserSessionBean userSessionBean) throws Exception {
        Assert.notNull(userSessionBean,"请重新登录");
        BaseReportHeaderEntity_HI instance = SaafToolUtils.setEntity(BaseReportHeaderEntity_HI.class, paramsJSON, baseReportHeaderDAO_HI, userSessionBean.getUserId());
        if (instance.getReportHeaderId() == null) {
            //新增
            SaafToolUtils.validateJsonParms(paramsJSON, "reportHeaderCode", "dsId", "querySql");
            List<BaseReportHeaderEntity_HI> list = baseReportHeaderDAO_HI.findByProperty("reportHeaderCode", paramsJSON.getString("reportHeaderCode"));
            if (list.size() > 0)
                throw new IllegalArgumentException("报表编码[#]已存在".replace("#", paramsJSON.getString("reportHeaderCode")));
            baseReportHeaderDAO_HI.saveOrUpdate(instance);
//            List<JSONObject> reports = findReportSql(instance.getReportHeaderId(), (JSONObject) JSON.toJSON(userSessionBean), new JSONObject(), 1, 1).getData();
            return instance;
        } else {
            //修改
            SaafToolUtils.validateEntityParams(instance, "versionNum");
        }
        baseReportHeaderDAO_HI.saveOrUpdate(instance);
        return instance;
    }




    /**
     * 使用系统数据源执行原生sql查询
     *
     * @param sql       sql
     * @param countSql  统计sql
     * @param pageIndex 当前页
     * @param pageRows  分页大小
     * @return
     */
    private Pagination<JSONObject> findNativeSqlPagination(String sql, String countSql, Integer pageIndex, Integer pageRows) {
        try {
            Pagination<JSONObject> pagination = commonDAO_HI_DY.findPagination(sql, countSql, pageIndex, pageRows);
            return pagination;
        } catch (Exception e) {
            throw new IllegalArgumentException("sql 语法错误");
        }
    }


    /**
     * 执行报表中定义的sql
     *
     * @param reportHeaderCode 报表头编码
     * @param sessionBean    用户session
     * @param queryParamJSON 查询参数
     * @param pageIndex      当前页
     * @param pageRows       分页大小
     * @return
     * @throws SQLException
     */
    @Override
    public Pagination<JSONObject> findReportSql(String reportHeaderCode, JSONObject sessionBean, JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) throws SQLException {
        Assert.notNull(reportHeaderCode, " reportHeaderCode is required");

        List<BaseReportHeaderEntity_HI> reportList = baseReportHeaderDAO_HI.findByProperty("reportHeaderCode",reportHeaderCode);
        Assert.notNull(reportList, "报表头reportHeaderCode[#]不存在".replace("#", reportHeaderCode + ""));
        BaseReportHeaderEntity_HI report = reportList.get(0);

        Map<String, Object> paramsMap = new HashMap<String, Object>();

        String sql = getQuerySql(report, sessionBean,paramsMap,queryParamJSON);
        String countSql = getCountSql(sql);

        //id为1表示使用系统数据源
        if (report.getDsId() == 1) {
            return commonDAO_HI_DY.findPagination(sql, countSql, paramsMap, pageIndex, pageRows);
        }
        return executeNativeSql(report.getDsId(), sql, paramsMap, pageIndex, pageRows);
    }


    /**
     * @param dsId      数据源id
     * @param sql       sql
     * @param pageIndex 当前页
     * @param pageRows  分页大小
     * @param map       查询参数
     * @return
     * @throws SQLException
     */
    @Override
    public Pagination<JSONObject> executeNativeSql(Integer dsId, String sql, Map<String, Object> map, Integer pageIndex, Integer pageRows) throws SQLException {

        if (map == null || map.isEmpty())
            return executeNativeSql(dsId, sql, pageIndex, pageRows);
        Object[] objects = new Object[map.size()];
        int i = 0;
        Set<String> keys = map.keySet();
        for (String key : keys) {
            sql = sql.replace(":" + key, "?");
            objects[i++] = map.get(key);
        }
        return executeNativeSql(dsId, sql, pageIndex, pageRows, objects);
    }

    /**
     * @param dsId      数据源id
     * @param sql       sql
     * @param pageIndex 当前页
     * @param pageRows  分页大小
     * @param args      查询参数
     * @return
     * @throws SQLException
     */
    @Override
    public Pagination<JSONObject> executeNativeSql(Integer dsId, String sql, Integer pageIndex, Integer pageRows, Object... args) throws SQLException {
        DataSource dataSource = baseReportDatasourceServer.getDatasource(dsId);

        StringBuilder sqlbuild = new StringBuilder(sql);
        String reg = "[\\n\\r\\s]where[\\n\\r\\s]";
        Pattern pattern = Pattern.compile(reg, Pattern.CASE_INSENSITIVE);
        Matcher m = pattern.matcher(sql);
        if (m.find() == false) {
            sqlbuild.append(" where 1=1 ");
        }

        String countSql = getCountSql(sql);
        //2020.7.3 添加
        sql = getPageSql(sql);
        LOGGER.info("计算总数的SQL:[%],查询SQL:[#]".replace("%",countSql).replace("#",sql));

        try (    // 过了try块会直接释放连接资源
                 Connection connection = dataSource.getConnection();
                 PreparedStatement pre = connection.prepareStatement(sql);
                 PreparedStatement countPre = connection.prepareStatement(countSql)
        ) {

            //是否分页
            if (pageIndex > 0 && pageRows > 0) {
                //mysql用法
                //args = ArrayUtils.add(args, (pageIndex - 1) * pageRows);
                //args = ArrayUtils.add(args, pageRows);
                //2020.7.3改成Oracle用法
                args = ArrayUtils.add(args, pageIndex * pageRows);//结束行
                args = ArrayUtils.add(args, (pageIndex - 1) * pageRows);//起始行
                LOGGER.info("动态SQL需要的占位符参数[#]".replace("#",ArrayUtils.toString(args)));
            }

            //参数替换占位符
            if (args != null && args.length > 0) {
                for (int i = 0; i < args.length; i++) {
                    pre.setObject(i + 1, args[i]);
                    //countPre.setObject(i + 1, args[i]);
                }
                //2020.7.3修改
                for (int i = 0; i < args.length - 2; i++) {//查询总数SQL分页占位符不需要,后两个元素不替换
                    countPre.setObject(i + 1, args[i]);
                }
            }

            ResultSet resultSet = pre.executeQuery();
            List<JSONObject> data = SaafToolUtils.resultSetToList(resultSet);
            Long count = Long.valueOf(data.size());
            if (pageIndex > 0 && pageRows > 0) {
                ResultSet rs = countPre.executeQuery();
                while (rs.next())
                    //count = rs.getLong(0);
                    count = rs.getLong("COUNT");
            }
            Pagination pagination = new Pagination((long) pageIndex, (long) pageRows,count);
            pagination.setData(data);
            return pagination;
        }
    }

    /**
     * 拼接报表sql
     *
     * @param instance
     * @param sessionBean
     * @return
     */
    private String getQuerySql(BaseReportHeaderEntity_HI instance, JSONObject sessionBean, Map<String,Object> map, JSONObject jsonObject) {
        Assert.notNull(instance.getQuerySql(), "querySql  is required");
        StringBuffer sql = new StringBuffer(instance.getQuerySql());
        String reg = "[\\n\\r\\s]where[\\n\\r\\s]";
        Pattern pattern = Pattern.compile(reg, Pattern.CASE_INSENSITIVE);

        if (StringUtils.isNotBlank(instance.getWhereClause())) {
            Matcher m = pattern.matcher(sql);
             m = pattern.matcher(instance.getWhereClause());
            if (m.find())
                sql.append(" ").append(instance.getWhereClause().trim());
            else
                sql.append(" where 1=1 and ").append(instance.getWhereClause().trim());
        }
        Matcher m = pattern.matcher(sql);
        if (m.find() == false) {
            sql.append(" where 1=1 ");
        }
        SaafToolUtils.parperHbmParam(null,jsonObject,sql,map);
        if (StringUtils.isNotBlank(instance.getOrderBy())) {
            sql.append(" ").append(instance.getOrderBy());
        }
        return replaceSystemVariable(sql.toString(), sessionBean);
    }

    /**
     * 拼接分页统计sql
     *
     * @param querySql    查询sql
     * @return
     */
    private String getCountSql(String querySql) {
        StringBuilder count = new StringBuilder();
        count.append("select count(1) as count from ( ").append(querySql).append(") cntemp");
        return count.toString();
    }

    /**
     * 获取分页SQL
     * @param querySql 查询sql
     * @return
     */
    private String getPageSql(String querySql){
        StringBuilder pageSql = new StringBuilder();
        //第一种方式 效率不高
        //pageSql.append("select * from ( select row_.*, rownum rownum_ from ("+querySql+") row_ ) where  rownum_ > ? and rownum_ <= ?");
        //第二种方式 效率较高
        pageSql.append("SELECT *\n" +
                "\n" +
                "  FROM (SELECT tt.*, ROWNUM AS rowno\n" +
                "\n" +
                "          FROM (\n" +
                querySql +
                "          ) tt\n" +
                "\n" +
                "         WHERE ROWNUM <= ?) row_\n" +
                "\n" +
                " WHERE row_.rowno > ?");
        return pageSql.toString();
    }


    /**
     * 替换sql 中系统变量
     *
     * @param sql
     * @param sessionBean
     * @return
     */
    @Override
    public String replaceSystemVariable(String sql, JSONObject sessionBean) {
        if (sessionBean == null || sessionBean.isEmpty() || StringUtils.isBlank(sql))
            return sql;
        String str = sql;
        Set<String> keys=sessionBean.keySet();
        for (String key:keys){
            if (sessionBean.getString(key)==null)
                continue;
            str = str.replace("{system.#}".replace("#",key), sessionBean.getString(key));
        }
        return str;
    }




    @Override
    public Pagination<BaseReportHeaerDatasourceEntity_HI_RO> find(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
        StringBuffer sql = new StringBuffer(BaseReportHeaerDatasourceEntity_HI_RO.query);
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        SaafToolUtils.parperHbmParam(BaseReportHeaerDatasourceEntity_HI_RO.class, queryParamJSON, sql, paramsMap);
        StringBuffer countSql = SaafToolUtils.getSimpleSqlCountString(sql,"count(1)");
        Pagination<BaseReportHeaerDatasourceEntity_HI_RO> findList = baseReportHeaerDatasourceDAO_HI_RO.findPagination(sql, countSql, paramsMap, pageIndex, pageRows);
        return findList;
    }



    @Override
    public void delete(String[] ids){
        if (ids==null || ids.length==0)
            return;
        for (String id:ids){
            List<BaseReportLineEntity_HI> list= baseReportLineDAO_HI.findByProperty("reportHeaderId",Integer.valueOf(id));
            if (list.size()>0)
                baseReportLineDAO_HI.delete(list);
            baseReportHeaderDAO_HI.delete(Integer.valueOf(id));
        }
    }


    public static void main(String[] args) {

    }


}
