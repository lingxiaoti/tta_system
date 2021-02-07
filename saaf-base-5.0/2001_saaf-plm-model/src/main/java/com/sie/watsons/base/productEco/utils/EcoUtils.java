package com.sie.watsons.base.productEco.utils;

import ch.qos.logback.classic.db.names.ColumnName;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.api.model.inter.server.PlmApiServer;
import com.sie.watsons.base.api.model.utils.DrugColumn;
import com.sie.watsons.base.product.model.dao.PlmProductSupplierInfoDAO_HI;
import com.sie.watsons.base.product.model.entities.*;
import com.sie.watsons.base.product.model.entities.readonly.PlmProductDrugEntity_HI_RO;
import com.sie.watsons.base.productEco.model.entities.PlmProductPriceEcoEntity_HI;
import com.sie.watsons.base.productEco.model.entities.PlmProductSupplierInfoEcoEntity_HI;
import com.sie.watsons.base.redisUtil.ResultConstant;
import com.sie.watsons.base.redisUtil.ResultUtils;
import com.sie.watsons.base.redisUtil.csvUtils;
import com.yhg.hibernate.core.utils.ObjectUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class EcoUtils {
    private static final Logger LOGGER = LoggerFactory
            .getLogger(EcoUtils.class);
    private static SimpleDateFormat requestDateFormat = new SimpleDateFormat(
            "yyyyMMddhhmm");

    private static SimpleDateFormat requestKeyDateFormat = new SimpleDateFormat(
            "yyyyMMdd");
    private static SimpleDateFormat updateDateFormat = new SimpleDateFormat(
            "yyyy/MM/dd");

    /**
     * @param map        UDA map
     * @param columnName 字段名称
     * @param newValue   新值
     * @param employeeNo 员工号  最后更新人
     * @param requestId  请求ID
     * @param rmsCode    编码
     * @param toPlace
     * @return
     * @throws Exception
     */
    public static JSONObject getUpdateUdaMethod(Map map,
                                                String columnName, String newValue, String employeeNo, BigInteger requestId,
                                                String rmsCode, String oldValue, String toPlace) throws Exception {
        String src = (String) map.get(columnName);
        if("place".equals(columnName)){
            newValue =  toPlace;
        }
        JSONObject json = new JSONObject(true);
        if (!SaafToolUtils.isNullOrEmpty(src)) {
            String[] strings = src.split("\\|");
            json.put("request_id", requestId);
            if(StringUtils.isEmpty(oldValue) && !StringUtils.isEmpty(newValue))
            {
                json.put("update_type", "A");
            }else if(!StringUtils.isEmpty(oldValue) && !StringUtils.isEmpty(newValue)){
                json.put("update_type", "U");
            }else if(!StringUtils.isEmpty(oldValue) && StringUtils.isEmpty(newValue)){
                json.put("update_type", "D");
            }
            json.put("uda_type", strings[1]);
            json.put("uda_id", Integer.parseInt(strings[0]));
            // json.put("uda_value", Integer.parseInt(entity.getNewValue()));
            if(!(!StringUtils.isEmpty(oldValue) && StringUtils.isEmpty(newValue))) {
                json.put("uda_value", newValue);
            }else {

                json.put("uda_value", "");
            }
            json.put("item", rmsCode);
            json.put("item_type", "I");
            json.put("update_datetime", requestDateFormat.format(new Date()));
            json.put("last_update_id", employeeNo);
            return json;
        } else {
            return json;
        }
    }

    /**
     * 封装售价数据
     *
     * @param priceList  售价List
     * @param requestId  请求ID 2
     * @param rmsCode
     * @param employeeNo 员工号
     */
    public static void getPackagingPriceChangeJson(
            List<PlmProductPriceEcoEntity_HI> priceList,
            JSONArray jsonArrayPriceChange,
            BigInteger requestId,
            String rmsCode, String employeeNo) {
//        JSONObject updateTypeJson = ResultConstant.PLM_PRODUCT_UPDATETYPE;
        JSONObject reasonJson = ResultConstant.PLM_PRODUCT_UPDATESESON;
        for (PlmProductPriceEcoEntity_HI price : priceList) {
//            PlmProductPriceEntity_HI price = plmProductPriceServer
//                    .getById(businessId);
//            List<PlmProductUpdatepropertisEntity_HI> dd = restMap
//                    .get(businessId);
            JSONObject json = new JSONObject(true);
            json.put("request_id", requestId);
            // json.put("update_type", "R");
            json.put("supplier", 0);
            json.put("item", rmsCode);
            json.put("effective_date",
                    requestKeyDateFormat.format( price.getUpdatePriceDate()==null?getThursday():
                            (Integer.valueOf(requestKeyDateFormat.format(price.getUpdatePriceDate()))
                                    .compareTo(Integer.valueOf(requestKeyDateFormat.format(new Date()))) > 0
                                    ?price.getUpdatePriceDate():getThursday() )));
            json.put("zone_group_id", Integer.valueOf(price.getPriceGroupCode()));
            json.put("zone_id", Integer.valueOf(price.getAreaId()));
            json.put("value", Double.valueOf(price.getUnitPrice()==null?"0":price.getUnitPrice()).intValue());
            json.put("change_type",price.getUpdateType());
//            json.put("change_type",updateTypeJson.getString(price.getUpdateType()));
            json.put("reason", Integer.valueOf(price.getUpdateSeson()));
            json.put("change_desc", price.getSeson()==null?reasonJson.getString(price.getUpdateSeson()):price.getSeson());
            json.put("item_type", "I");
            json.put("update_datetime", requestDateFormat.format(new Date()));
            json.put("last_update_id", employeeNo);
            SaafToolUtils.validateJsonParms(json, "zone_group_id", "value",
                    "change_type", "reason", "change_desc"); // 效验

            jsonArrayPriceChange.add(json);
        }

    }

    /**
     *  公共调用 JSONArray 方法
     * @param jsonArray   json 数组
     * @param url     接口
     */
    public static void commonForUrl(JSONArray jsonArray,String url) {
        JSONObject sumJson = new JSONObject();
        // sumJson.put("data_row", 2);
        sumJson.put("data_set", jsonArray);
        LOGGER.info(url + " 请求接口参数："
                + sumJson.toJSONString());
        // 请求接口
        JSONObject response = ResultUtils.doPost(
                url, sumJson);
        LOGGER.info(url + " 请求接口返回参数："
                + response);
    }

    public static Date tomorrow(Date today) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + 1);
        return calendar.getTime();
    }

    public static void getPackagingCostChangeJson(List<PlmProductSupplierInfoEcoEntity_HI> supplierEcos, JSONArray jsonCostChangeChange, BigInteger requestId, String rmsCode, String employeeNo) {

        JSONObject reasonJson = ResultConstant.PLM_PRODUCT_UPDATEPRICE;
        for (PlmProductSupplierInfoEcoEntity_HI supplier : supplierEcos) {
            JSONObject json = new JSONObject(true);
            json.put("request_id", requestId);
            // json.put("update_type", "C");
            json.put("supplier", supplier.getSupplierCode());
            json.put("effective_date",
                    requestKeyDateFormat.format(supplier.getUpdatePriceDate()==null?tomorrow(new Date()):
                            (Integer.valueOf(requestKeyDateFormat.format(supplier.getUpdatePriceDate()))
                                    .compareTo(Integer.valueOf(requestKeyDateFormat.format(new Date()))) > 0
                                    ?supplier.getUpdatePriceDate():tomorrow(new Date()))));
            json.put("update_datetime", requestDateFormat.format(new Date()));
            json.put("last_update_id", employeeNo);
            json.put("item", rmsCode);
            json.put("change_type", "NP"); // 暂时默认NP(待确定)
            json.put("value", supplier.getPrice());
            json.put("reason", supplier.getUpdateSeson() == null ? "1": supplier.getUpdateSeson());
            json.put("change_desc", supplier.getSeson()== null ? reasonJson.getString(supplier.getUpdateSeson()): supplier.getSeson());
            json.put("item_type", "I");
            SaafToolUtils.validateJsonParms(json, "value", "reason",
                    "change_desc"); // 效验
            jsonCostChangeChange.add(json);
        }
    }
    public static String getTimeStr() {
        String timeStr = "";
        Date date = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
        timeStr = sf.format(date);
        return timeStr;
    }
    public static boolean allfieldIsNUll(Object o){
        try{
            for(Field field:o.getClass().getDeclaredFields()){
                field.setAccessible(true);//把私有属性公有化
                Object object = field.get(o);
                if(object instanceof CharSequence){
                    if(!ObjectUtils.isEmpty((String)object)){
                        return false;
                    }
                }else{
                    if(!Objects.isNull(object)){
                        return false;
                    }
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return true;
    }

    private  static boolean isNUll(Object object) {
        return object==null;
    }

    public static String getPlace(String place) {

        String placeall="";
        if(place.equals(""))
        {
            return "见外包装()";
        }
        String[] placeList=place.split(",");
        for(String child:placeList)
        {
            if (child.indexOf("-") <= 0) {  //国外名称
                placeall+=child;
            }
            else
            {
                String country = child.split("-")[0];
                String provide = child.split("-")[1];

                String city ="";
                if(child.split("-").length>2){
                    city=child.split("-")[2];
                }else {
                    //直辖市
                    city=child.split("-")[1];
                }

                if(city.equals("北京")||city.equals("天津")||city.equals("上海")||
                        city.equals("重庆")||city.equals("香港")||city.equals("台湾")||city.equals("澳门"))
                {
                    placeall+=city;
                }else
                {
                    String placename="";
                    if ("河北".equals(provide)) {
                        placename="冀" + city;
                    }
                    if ("山西".equals(provide)) {
                        placename="晋" + city;
                    }
                    if ("内蒙古".equals(provide)) {
                        placename="蒙" + city;
                    }
                    if ("辽宁".equals(provide)) {
                        placename="辽" + city;
                    }
                    if ("吉林".equals(provide)) {
                        placename="吉" + city;
                    }
                    if ("黑龙江".equals(provide)) {
                        placename="黑" + city;
                    }
                    if ("江苏".equals(provide)) {
                        placename="苏" + city;
                    }
                    if ("浙江".equals(provide)) {
                        placename="浙" + city;
                    }
                    if ("安徽".equals(provide)) {
                        placename="皖" + city;
                    }
                    if ("福建".equals(provide)) {
                        placename="闽" + city;
                    }
                    if ("江西".equals(provide)) {
                        placename="赣" + city;
                    }
                    if ("山东".equals(provide)) {
                        placename= "鲁" + city;
                    }
                    if ("河南".equals(provide)) {
                        placename="豫" + city;
                    }
                    if ("湖北".equals(provide)) {
                        placename="鄂" + city;
                    }
                    if ("湖南".equals(provide)) {
                        placename="湘" + city;
                    }
                    if ("广东".equals(provide)) {
                        placename= "粤" + city;
                    }
                    if ("广西".equals(provide)) {
                        placename= "桂" + city;
                    }
                    if ("海南".equals(provide)) {
                        placename="琼" + city;
                    }
                    if ("四川".equals(provide)) {
                        placename= "川" + city;
                    }
                    if ("贵州".equals(provide)) {
                        placename= "贵" + city;
                    }
                    if ("云南".equals(provide)) {
                        placename="云" + city;
                    }
                    if ("重庆".equals(provide)) {
                        placename="渝" + city;
                    }
                    if ("西藏".equals(provide)) {
                        placename="藏" + city;
                    }
                    if ("陕西".equals(provide)) {
                        placename="陕" + city;
                    }
                    if ("甘肃".equals(provide)) {
                        placename="甘" + city;
                    }
                    if ("青海".equals(provide)) {
                        placename="青" + city;
                    }
                    if ("宁夏".equals(provide)) {
                        placename="宁" + city;
                    }
                    if ("新疆".equals(provide)) {
                        placename="新" + city;
                    }

                    placeall+=placename;
                }


            }
        }
        if(placeList.length>1)
        {
//            return "见外包装("+placeall+")";
            return "见外包装";
        }
        else {
            return placeall;
        }

    }

    /**
     *
     * 判断是否需要变更生效日期数据
     */
    public static void AddMapForUpdatePriceDate(Object object, List<Map<String, String>> updatePriceDateMap, Date now) {
        if (object instanceof PlmProductPriceEcoEntity_HI) {
            //售价修改表
            PlmProductPriceEcoEntity_HI priceEn = (PlmProductPriceEcoEntity_HI) object;
            if (Integer.valueOf(requestKeyDateFormat.format(priceEn.getUpdatePriceDate()))
                    .compareTo(Integer.valueOf(requestKeyDateFormat.format(now))) <= 0) {
                Map<String, String> map = new HashMap<>();
                map.put("tableName", "PLM_PRODUCT_PRICE_ECO");
                map.put("updatePriceDate", updateDateFormat.format(getThursday()));
                map.put("columnName", "UPDATE_PRICE_DATE");
                map.put("keyName", "line_id");
                map.put("ID", priceEn.getLineId().toString());
                updatePriceDateMap.add(map);
            }
        }
        else if (object instanceof PlmProductSupplierInfoEcoEntity_HI) {
            PlmProductSupplierInfoEcoEntity_HI supperEn = (PlmProductSupplierInfoEcoEntity_HI) object;
            if (Integer.valueOf(requestKeyDateFormat.format(supperEn.getUpdatePriceDate()))
                    .compareTo(Integer.valueOf(requestKeyDateFormat.format(now))) <= 0) {
                Map<String, String> map = new HashMap<>();
                map.put("tableName", "PLM_PRODUCT_SUPPLIER_INFO_ECO");
                map.put("updatePriceDate", updateDateFormat.format(tomorrow(new Date())));
                map.put("columnName", "UPDATE_PRICE_DATE");
                map.put("keyName", "line_id");
                map.put("ID", supperEn.getLineId().toString());
                updatePriceDateMap.add(map);
            }
        }
    }

    private static Date getThursday() {
        Calendar calendar = Calendar.getInstance();
        Date today = new Date();
        calendar.setTime(today);//
        int weekDay = calendar.get(Calendar.DAY_OF_WEEK);
        if (weekDay == 1) {
            weekDay = 7;
        } else {
            weekDay = weekDay - 1;
        }
        if(weekDay>4){
            //获取下周4
            calendar.setFirstDayOfWeek(Calendar.MONDAY);
            calendar.add(Calendar.WEEK_OF_MONTH, 1);
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
            return calendar.getTime();
        }else {
            //获取本周4
            calendar.setFirstDayOfWeek(Calendar.MONDAY);
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
            return calendar.getTime();
        }
    }
}
