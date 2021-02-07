package com.sie.saaf.base.report.model.inter.server;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.report.model.entities.BaseReportDatasourceEntity_HI;
import com.sie.saaf.base.report.model.entities.BaseReportHeaderEntity_HI;
import com.sie.saaf.base.report.model.inter.IBaseReportDatasource;
import com.sie.saaf.common.constant.CommonConstants;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.apache.commons.dbcp.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("baseReportDatasourceServer")
public class BaseReportDatasourceServer extends BaseCommonServer<BaseReportDatasourceEntity_HI> implements IBaseReportDatasource {
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseReportDatasourceServer.class);
    @Autowired
    private ViewObject<BaseReportDatasourceEntity_HI> baseReportDatasourceDAO_HI;

    @Autowired
    private ViewObject<BaseReportHeaderEntity_HI> baseReportHeaderDAO_HI;

    @Autowired
    private BasicDataSource dataSource;

    /**
     * 数据源创建后保存起来
     */
    private Map<Integer, BasicDataSource> dataSourceMap = new HashMap<>();

    /**
     * 数据源版本
     */
    private Map<Integer, Integer> dataSourceVersionMap = new HashMap<>();

    public BaseReportDatasourceServer() {
        super();
        LOGGER.info("BaseReportDatasourceServer 初始化。。。");
    }

    /**
     * @param queryParamJSON{ dsId 主键
     *                        dsName 数据源名字
     *                        dsType 数据源类型：DataBase、webServiceSOAP、webServiceRestful
     *                        dsAccessType 数据源访问类别
     *                        dsAccessProtocal 访问协议：http/https
     *                        dsIp 数据源所在的IP地址
     *                        dsPort 服务端口
     *                        dsWebserverAddress 数据源访问地址
     *                        dsWebserverUser 服务访问的用户名
     *                        dsWebserverPassword 服务访问的密码
     *                        dsWebserverToken 服务访问的密钥
     *                        dsDbInstanceName 数据库实例名
     *                        }
     * @return
     * @description 数据源查询
     */
    public List<BaseReportDatasourceEntity_HI> findBaseReportDatasourceInfo(JSONObject queryParamJSON) {
        Map<String, Object> queryParamMap = SToolUtils.fastJsonObj2Map(queryParamJSON);
        List<BaseReportDatasourceEntity_HI> findListResult = baseReportDatasourceDAO_HI.findList("from BaseReportDatasourceEntity_HI", queryParamMap);
        return findListResult;
    }


    /**
     * @param paramsJSON { dsId 主键
     *                   dsName 数据源名字
     *                   dsType 数据源类型：DataBase、webServiceSOAP、webServiceRestful
     *                   dsAccessType 数据源访问类别
     *                   dsAccessProtocal 访问协议：http/https
     *                   dsIp 数据源所在的IP地址
     *                   dsPort 服务端口
     *                   dsWebserverAddress 数据源访问地址
     *                   dsWebserverUser 服务访问的用户名
     *                   dsWebserverPassword 服务访问的密码
     *                   dsWebserverToken 服务访问的密钥
     *                   dsDbInstanceName 数据库实例名
     *                   }
     * @param userId
     * @return
     * @throws Exception
     * @description 数据源新增修改
     */
    @Override
    public BaseReportDatasourceEntity_HI saveOrUpdate(JSONObject paramsJSON, int userId) throws Exception {

        BaseReportDatasourceEntity_HI instance = SaafToolUtils.setEntity(BaseReportDatasourceEntity_HI.class, paramsJSON, baseReportDatasourceDAO_HI, userId);
        if (instance.getDsId() == null) {
            //新增
            SaafToolUtils.validateJsonParms(paramsJSON, "dsType", "dsName");
            if (CommonConstants.DATASOURCE_TYPE_DATABASE.equals(instance.getDsType())) {
                SaafToolUtils.validateJsonParms(paramsJSON, "dsIp", "dsPort", "dsWebserverUser", "dsWebserverPassword", "dsDbInstanceName");
                //验证数据源
                try {
                    BasicDataSource dataSource = createDatasource(instance.getDsAccessType(), instance.getDsIp(), instance.getDsPort(), instance.getDsDbInstanceName(), instance.getDsWebserverUser(), instance.getDsWebserverPassword());
                    dataSource.getConnection().close();
                    dataSource.close();
                }catch (Exception e){
                    throw new IllegalArgumentException("数据源无法连接");
                }
            } else {
                SaafToolUtils.validateJsonParms(paramsJSON, "dsWebserverAddress");
                String protocal = paramsJSON.getString("dsWebserverAddress").toLowerCase().contains("https") ? "https" : "http";
                instance.setDsAccessProtocal(protocal);
            }
        } else {
            //修改
            SaafToolUtils.validateEntityParams(instance, "versionNum");
            removeDatasource(instance.getDsId());
        }

        //拼接完整数据源访问地址
        if (CommonConstants.DATASOURCE_TYPE_DATABASE.equals(instance.getDsType())) {
            String url = getFullUrl(instance.getDsAccessType(), instance.getDsIp(), instance.getDsPort(), instance.getDsDbInstanceName());
            instance.setDsWebserverAddress(url);
        }
        baseReportDatasourceDAO_HI.saveOrUpdate(instance);

        return instance;
    }


    /**
     * @param queryParamJSON {
     *                       dsId 主键
     *                       dsName 数据源名字
     *                       dsType 数据源类型：DataBase、webServiceSOAP、webServiceRestful
     *                       dsAccessType 数据源访问类别
     *                       dsAccessProtocal 访问协议：http/https
     *                       dsIp 数据源所在的IP地址
     *                       dsDbInstanceName 数据库实例名
     *                       }
     * @param pageIndex
     * @param pageRows
     * @return
     * @description 数据源分页查询
     */
    @Override
    public Pagination<BaseReportDatasourceEntity_HI> findBaseUserSystemInfoWithPage(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
        StringBuffer sql = new StringBuffer();
        sql.append("from BaseReportDatasourceEntity_HI where 1=1 ");
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        SaafToolUtils.parperHbmParam(BaseReportDatasourceEntity_HI.class, queryParamJSON, sql, paramsMap);
        SaafToolUtils.changeQuerySort(queryParamJSON, sql, "creationDate", false);
        Pagination<BaseReportDatasourceEntity_HI> findList = baseReportDatasourceDAO_HI.findPagination(sql, paramsMap, pageIndex, pageRows);
        return findList;
    }


    /**
     * 获取数据源
     *
     * @param dsId
     * @return
     * @throws Exception
     */
    @Override
    public BasicDataSource getDatasource(Integer dsId) throws SQLException {
        Assert.notNull(dsId, "dsId  is required");
        if (dsId-1==0)
            return dataSource;
        BasicDataSource dataSource = dataSourceMap.get(dsId);
        Integer version = dataSourceVersionMap.get(dsId);
        BaseReportDatasourceEntity_HI instance = baseReportDatasourceDAO_HI.getById(dsId);
        if (instance == null)
            throw new IllegalArgumentException("数据源id[#]不存在".replace("#", dsId + ""));
        if (dataSource != null && version != null) {
            if (instance.getVersionNum() - version == 0) {
                return dataSource;
            }
            //数据源过期时关闭数据源
            removeDatasource(dsId);
        }
        dataSource = createDatasource(instance.getDsAccessType(), instance.getDsIp(), instance.getDsPort(), instance.getDsDbInstanceName(), instance.getDsWebserverUser(), instance.getDsWebserverPassword());
        dataSourceMap.put(instance.getDsId(), dataSource);
        dataSourceVersionMap.put(instance.getDsId(), instance.getVersionNum());
        return dataSource;
    }

    /**
     * 关闭数据源
     *
     * @param dsId
     */
    private void removeDatasource(Integer dsId) {
        try {
            if (dsId == null)
                return;
            BasicDataSource dataSource = dataSourceMap.get(dsId);
            dataSourceMap.remove(dsId);
            dataSourceVersionMap.remove(dsId);
            if (dataSource != null)
                dataSource.close();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }


    /**
     * 创建数据源
     *
     * @param dataType 数据库类型
     * @param ip       ip
     * @param port     端口
     * @param sid      实例名
     * @param userName 用户名
     * @param pwd      密码
     * @return DataSource
     */
    public BasicDataSource createDatasource(String dataType, String ip, String port, String sid, String userName, String pwd) {
        BasicDataSource ds = new BasicDataSource();
        String driver = CommonConstants.DATASOURCE_DRIVER.get(dataType);
        if (driver == null)
            throw new IllegalArgumentException("不支持的数据源类型[#]".replace("#", dataType));
        ds.setDriverClassName(driver);
        ds.setUrl(getFullUrl(dataType, ip, port, sid));
        ds.setUsername(userName);
        ds.setPassword(pwd);
        ds.setMaxIdle(20);
        ds.setInitialSize(10);
        ds.setValidationQuery("select 1 from dual");
        ds.setMaxActive(500);
        ds.setValidationQueryTimeout(1000);
        //等待获取连接池连接时间
        ds.setMaxWait(20000);
        //移除无引用连接
        ds.setRemoveAbandoned(true);
        //当连接空闲时是否测试
        ds.setTestWhileIdle(true);
        //空闲检测周期
        ds.setTimeBetweenEvictionRunsMillis(3600000 * 6);

        return ds;
    }





    /**
     * 拼接完整数据库连接地址
     *
     * @param dataType 数据库类型
     * @param ip       ip
     * @param port     端口
     * @param sid      实例名
     * @return
     */
    private String getFullUrl(String dataType, String ip, String port, String sid) {
        String urlTemplate = "#protocol##ip#:#port##separator##sid#";
        if (CommonConstants.DATASOURCE_ACCESS_TYPE_ORACLE.equals(dataType)) {
            urlTemplate = urlTemplate.replace("#protocol#", "jdbc:oracle:thin:@");
            urlTemplate = urlTemplate.replace("#separator#", ":");
        } else if (CommonConstants.DATASOURCE_ACCESS_TYPE_MYSQL.equals(dataType)) {
            urlTemplate = urlTemplate.replace("#protocol#", "jdbc:mysql://");
            urlTemplate = urlTemplate.replace("#separator#", "/");
        } else {
            throw new IllegalArgumentException("不支持的数据源类型[#]".replace("#", dataType));
        }
        urlTemplate = urlTemplate.replace("#ip#", ip);
        urlTemplate = urlTemplate.replace("#port#", port);
        urlTemplate = urlTemplate.replace("#sid#", sid);
        return urlTemplate;
    }


    public void executeRepostSql(Integer reportHeaderId) {
        Assert.notNull(reportHeaderId, " reportHeaderId is required");
        BaseReportHeaderEntity_HI report = baseReportHeaderDAO_HI.getById(reportHeaderId);
        Assert.notNull(report, "报表头id[#]不存在".replace("#", reportHeaderId + ""));


    }

    public void excuteNativeSql(String sql) {


    }


}
