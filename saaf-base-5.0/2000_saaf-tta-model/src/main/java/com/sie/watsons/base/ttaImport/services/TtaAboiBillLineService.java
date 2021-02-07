package com.sie.watsons.base.ttaImport.services;

import com.alibaba.fastjson.JSON;
import com.sie.watsons.base.ttaImport.model.entities.readonly.TtaAboiBillLineEntity_HI_RO;
import com.sie.watsons.base.ttaImport.model.entities.readonly.TtaSroiBillLineEntity_HI_RO;
import com.sie.watsons.base.ttaImport.model.inter.ITtaAboiBillLine;

import com.alibaba.fastjson.JSONObject;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.paging.Pagination;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.common.model.inter.IBaseCommon;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/ttaAboiBillLineService")
public class TtaAboiBillLineService extends CommonAbstractService{
    private static final Logger LOGGER = LoggerFactory.getLogger(TtaAboiBillLineService.class);

    @Autowired
    private ITtaAboiBillLine ttaAboiBillLineServer;

    @Override
    public IBaseCommon getBaseCommonServer(){
        return this.ttaAboiBillLineServer;
    }

    /**
     * 批量导入Aboi数据
     * @param file
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,value = "saveImportAboiInfo")
    public String saveImportTaxInfo(@RequestParam("file") MultipartFile file){
        try{
            Assert.notNull(file,"No choose file,upload failed");
            String params = "";
            JSONObject jsonObject = parseObject(params);
            int size = ttaAboiBillLineServer.saveImportABOIInfo(jsonObject,file);
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "总共"+size+"条数据导入成功", size, null).toString();
        }catch (Exception e){
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
        }
    }

    /**
     *
     * @param params JSON参数，查询条件的JSON格式
     * @param pageIndex 页码
     * @param pageRows 每页查询记录数
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,value = "findPagination")
    public String findPagination(@RequestParam(required = false) String params,
                                 @RequestParam(required = false,defaultValue = "1") Integer pageIndex,
                                 @RequestParam(required = false,defaultValue = "10") Integer pageRows){
        try{
            JSONObject jsonObject = parseObject(params);
            Pagination<TtaAboiBillLineEntity_HI_RO> result = ttaAboiBillLineServer.findABOIInfo(jsonObject,pageIndex,pageRows);
            jsonObject = (JSONObject) JSON.toJSON(result);
            jsonObject.put(SToolUtils.STATUS,"S");
            jsonObject.put(SToolUtils.MSG,SUCCESS_MSG);
            return jsonObject.toString();
        }catch (IllegalArgumentException e){
            LOGGER.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
        }catch (Exception e){
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
        }
    }

    /**
     * 单条删除或者批量删除
     * @param params
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,value = "deleteImportAboiInfo")
    public String deleteImportTaxInfo(@RequestParam(required = false) String params){
        try{
            JSONObject jsonObject = parseObject(params);
            JSONObject result = ttaAboiBillLineServer.deleteImportABOIInfo(jsonObject);
            return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, result).toString();
        }catch (IllegalArgumentException e){
            LOGGER.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
        }catch (Exception e){
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
        }
    }

}