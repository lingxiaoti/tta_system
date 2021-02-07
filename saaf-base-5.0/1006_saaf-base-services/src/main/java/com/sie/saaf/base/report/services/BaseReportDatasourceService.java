package com.sie.saaf.base.report.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.accredit.annotation.Permission;
import com.sie.saaf.base.report.model.entities.BaseReportDatasourceEntity_HI;
import com.sie.saaf.base.report.model.inter.IBaseReportDatasource;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
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

import java.util.Objects;

/**
 * @author huangtao
 * @description 数据源配置
 * @createTime 2017年12月14日 14:53:46
 */

@RestController
@RequestMapping("/baseReportDatasourceService")
public class BaseReportDatasourceService extends CommonAbstractService {
    private static final Logger log = LoggerFactory.getLogger(BaseReportDatasourceService.class);

    @Autowired
    private IBaseReportDatasource baseReportDatasourceServer;


    /**
     * @param params    {
     *                  dsId 主键
     *                  dsName 数据源名字
     *                  dsType 数据源类型：DataBase、webServiceSOAP、webServiceRestful
     *                  dsAccessType 数据源访问类别
     *                  dsAccessProtocal 访问协议：http/https
     *                  dsIp 数据源所在的IP地址
     *                  dsDbInstanceName 数据库实例名
     *                  }
     * @param pageIndex
     * @param pageRows
     * @return
     * @description 数据源分页查询
     */
    @RequestMapping(method = RequestMethod.POST, value = "find")
    public String find(@RequestParam(required = false) String params,
                       @RequestParam(required = false, defaultValue = "-1") String pageIndex,
                       @RequestParam(required = false, defaultValue = "-1") String pageRows) {
        try {
            JSONObject jsonObject = new JSONObject();

            if (StringUtils.isNotBlank(params))
                jsonObject = JSON.parseObject(params);

            Pagination<BaseReportDatasourceEntity_HI> pagination = baseReportDatasourceServer.findBaseUserSystemInfoWithPage(jsonObject, Integer.valueOf(Objects.toString(pageIndex, "-1")), Integer.valueOf(Objects.toString(pageRows, "-1")));
            jsonObject = (JSONObject) JSON.toJSON(pagination);
            jsonObject.put(SToolUtils.STATUS, "S");
            jsonObject.put(SToolUtils.MSG, SUCCESS_MSG);
            return jsonObject.toString();
        } catch (IllegalArgumentException e) {
            log.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
        }
    }

    @Override
    public IBaseCommon<?> getBaseCommonServer() {
        return null;
    }

    /**
     * @param params { dsId 主键
     *               dsName 数据源名字
     *               dsType 数据源类型：DataBase、webServiceSOAP、webServiceRestful
     *               dsAccessType 数据源访问类别
     *               dsAccessProtocal 访问协议：http/https
     *               dsIp 数据源所在的IP地址
     *               dsPort 服务端口
     *               dsWebserverAddress 数据源访问地址
     *               dsWebserverUser 服务访问的用户名
     *               dsWebserverPassword 服务访问的密码
     *               dsWebserverToken 服务访问的密钥
     *               dsDbInstanceName 数据库实例名
     *               }
     * @return
     * @throws Exception
     * @description 数据源新增修改
     */
    @Permission(menuCode = "dataSource")
    @RequestMapping(method = RequestMethod.POST, value = "saveOrUpdate")
    public String saveOrUpdate(@RequestParam(required = true) String params) {
        try {
            int userId = -1;
            JSONObject jsonObject = JSON.parseObject(params);
            BaseReportDatasourceEntity_HI instance = baseReportDatasourceServer.saveOrUpdate(jsonObject, userId);
            return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, instance).toString();
        } catch (IllegalArgumentException e) {
            log.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
        }
    }

    /**
     * @param params {id 主键}
     * @description 删除
     */
    @RequestMapping(method = RequestMethod.POST, value = "delete")
    public String delete(@RequestParam(required = false) String params) {
        try {
            if (StringUtils.isBlank(params)) {
                return SToolUtils.convertResultJSONObj("F", "缺少参数:id", 0, null).toString();
            }
            JSONObject jsonObject = JSON.parseObject(params);
            String[] ids = jsonObject.getString("id").split(",");
            for (String id : ids) {
                //id 为1  表示当前系统数据源，不给删
                if ("1".equals(id))
                    continue;
                baseReportDatasourceServer.deleteById(Integer.parseInt(id));
            }
            return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, null).toString();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
        }
    }


}
