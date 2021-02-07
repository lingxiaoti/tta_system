package com.sie.watsons.base.api.services;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.watsons.base.api.model.inter.IPlmSupProduct;
import com.sie.watsons.base.api.model.inter.IPlmSupProductImport;
import com.sie.watsons.base.product.model.entities.readonly.PlmProductHeadEntity_HI_RO;
import com.yhg.base.utils.SToolUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2019/12/16/016.
 */
@RestController
@RequestMapping("/plmSupProductService")
public class PlmSupProductService extends CommonAbstractService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PlmSupProductService.class);
    @Autowired
    private IPlmSupProduct plmSupProductServer;

    @Autowired
    private IPlmSupProductImport plmSupProductImportServer;

    public PlmSupProductService() {
        super();
    }

    @Override
    public IBaseCommon<?> getBaseCommonServer() {
        return null;
    }

    /**
     * 生成csv文件

     *     }
     * @return
     * @author your name
     * @creteTime Tue Oct 15 13:46:14 CST 2019
     */
    @RequestMapping(method = RequestMethod.POST, value = "productToCSV")
    public String productToCSV() {
//        public String productToCSV(@RequestParam(required = true) String params) {
        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
        String date= sdf.format(new Date());
        List<PlmProductHeadEntity_HI_RO> listMain = plmSupProductServer.findByQuery();
        List<PlmProductHeadEntity_HI_RO> listCon = plmSupProductServer.findByQuery2();
        //查询新增货品
        try{
            if(CollectionUtils.isEmpty(listMain)){
                return "没有新增货品！";
            }
            JSONObject queryParamJSON = new JSONObject();
            queryParamJSON.put("creationDate",date);
            queryParamJSON.put("creationDate","2020-03-24");
            plmSupProductServer.productToMainCSV(queryParamJSON,listMain);
            plmSupProductServer.productToSupsCSV(queryParamJSON,listMain);
            plmSupProductServer.productToBarCodeCSV(queryParamJSON,listMain);
            plmSupProductServer.productToPriceCSV(queryParamJSON,listMain);
            plmSupProductServer.productToConsCSV(queryParamJSON,listCon);
            plmSupProductServer.productToCvwCSV(queryParamJSON,listCon);
            plmSupProductServer.productToDrugCSV(queryParamJSON,listMain);
            queryParamJSON.put(SToolUtils.STATUS, "S");
            queryParamJSON.put(SToolUtils.MSG, SUCCESS_MSG);
            return queryParamJSON.toString();
        }catch (IllegalArgumentException e){
            LOGGER.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj("M", e.getMessage(), 0, null).toString();
        }catch (Exception e){
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
        }
    }
    /**
     * 生成csv可销售属性
     * @return
     * @author wjg
     * @creteTime Tue Oct 15 13:46:14 CST 2019
     */
    @RequestMapping(method = RequestMethod.POST, value = "productSalePropertiesToCSV")
    public String productSalePropertiesToCSV() {
//        public String productToCSV(@RequestParam(required = true) String params) {
        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
        String date= sdf.format(new Date());
        try{
            JSONObject queryParamJSON = new JSONObject();
//            queryParamJSON.put("creationDate",date);
//            queryParamJSON.put("creationDate","2020-03-24");
            plmSupProductServer.productSalePropertiesToCSV(queryParamJSON);
            queryParamJSON.put(SToolUtils.STATUS, "S");
            queryParamJSON.put(SToolUtils.MSG, SUCCESS_MSG);
            return queryParamJSON.toString();
        }catch (IllegalArgumentException e){
            LOGGER.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj("M", e.getMessage(), 0, null).toString();
        }catch (Exception e){
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
        }
    }

    public static void main(String[] args) throws Exception {
        JSONObject queryParamJSON = new JSONObject();
        queryParamJSON.put("orderStatus", 6);
        queryParamJSON.put("creationDate", "2019-12-16");
        System.out.print(queryParamJSON.toJSONString());
    }

    @RequestMapping(method = RequestMethod.POST, value = "csvGetResult")
    public String csvGetResult(@RequestParam(required = true) String params) {

        try{
            JSONObject queryParamJSON = parseObject(params);
            String timeStr = queryParamJSON.getString("timeStr");
            String result =  plmSupProductServer.csvGetResult("",timeStr);
            if("S".equals(result)){
                queryParamJSON.put(SToolUtils.STATUS, "S");
                queryParamJSON.put(SToolUtils.MSG, SUCCESS_MSG);
            }else {
                queryParamJSON.put(SToolUtils.STATUS, "E");
                queryParamJSON.put(SToolUtils.MSG, ERROR_MSG);
            }
            return queryParamJSON.toString();
        }catch (IllegalArgumentException e){
            LOGGER.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj("M", e.getMessage(), 0, null).toString();
        }catch (Exception e){
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
        }
    }


}
