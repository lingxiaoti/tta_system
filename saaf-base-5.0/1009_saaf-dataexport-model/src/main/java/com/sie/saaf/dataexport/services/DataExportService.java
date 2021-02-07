package com.sie.saaf.dataexport.services;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.saaf.dataexport.bean.XlsExportBean;
import com.sie.saaf.dataexport.model.inter.IDataExport;
import com.yhg.base.utils.SToolUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/dataExportService")
public class DataExportService extends CommonAbstractService {

    private static final Logger log= LoggerFactory.getLogger(DataExportService.class);

    @Autowired
    private IDataExport dataExportServer;

    /**
     *  开始导出
     * @param params
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "export")
    public String export(XlsExportBean params, HttpServletRequest request, HttpServletResponse response){
        try {
            if (StringUtils.isBlank(params.getToken()))
                params.setToken( request.getHeader(params.getToakenAttributeName()));
            SaafToolUtils.validateEntityParams(params,"token","labelName","attributeNames","requestUrl");
            dataExportServer.startExport(params);
            return SToolUtils.convertResultJSONObj("S","导出成功",0,params).toJSONString();
        }catch (IllegalArgumentException e){
            log.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj("M",e.getMessage(),0,null).toJSONString();
        }catch (Exception e){
            log.error(e.getMessage(),e);
            return SToolUtils.convertResultJSONObj("M","导出失败",0,null).toJSONString();
        }
    }

    /**
     * 查询导出结果
     * @param params
     * @return
     */
    @RequestMapping(value = "getExportResult")
    public String export(XlsExportBean params){
        try {
            if (StringUtils.isBlank(params.getToken()))
                params.setToken( request.getHeader(params.getToakenAttributeName()));
            SaafToolUtils.validateEntityParams(params,"exportId");
            JSONObject result= dataExportServer.getExportResult(params.getExportId());
            if (result==null) {
                return SToolUtils.convertResultJSONObj("W", "导出中。。。", 0, null).toJSONString();
            }else if (result.isEmpty()){
                return SToolUtils.convertResultJSONObj("E","导出失败",0,null).toJSONString();
            }
            return SToolUtils.convertResultJSONObj("S","导出成功",0,result).toJSONString();
        }catch (IllegalArgumentException e){
            log.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj("M",e.getMessage(),0,null).toJSONString();
        }catch (Exception e){
            log.error(e.getMessage(),e);
            return SToolUtils.convertResultJSONObj("M","导出失败",0,null).toJSONString();
        }
    }



    @Override
    public IBaseCommon<?> getBaseCommonServer() {
        return null;
    }
}
