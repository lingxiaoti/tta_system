package com.sie.watsons.base.plmBase.model.inter.server;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.rmi.ServerException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.sie.saaf.base.shiro.model.entities.BaseProfileEntity_HI;
import com.sie.saaf.base.shiro.model.inter.IBaseProfile;
import com.sie.watsons.base.api.ConfigModel;
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.model.inter.server.GenerateCodeServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.saaf.common.util.SpringBeanUtil;
import com.sie.watsons.base.plmBase.model.entities.PlmSupplierQaBrandEntity_HI;
import com.sie.watsons.base.plmBase.model.entities.PlmSupplierQaDealerEntity_HI;
import com.sie.watsons.base.plmBase.model.entities.PlmSupplierQaNonObInfoEntity_HI;
import com.sie.watsons.base.plmBase.model.entities.readonly.PlmSupplierQaNonObInfoEntity_HI_RO;
import com.sie.watsons.base.plmBase.model.inter.IPlmSupplierQaBrand;
import com.sie.watsons.base.plmBase.model.inter.IPlmSupplierQaDealer;
import com.sie.watsons.base.plmBase.model.inter.IPlmSupplierQaNonObInfo;
import com.sie.watsons.base.product.model.inter.IPlmProductSupplierInfo;
import com.sie.watsons.base.supplier.model.entities.readonly.TtaSupplierEntity_HI_RO;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;

@Component("plmSupplierQaNonObInfoServer")
public class PlmSupplierQaNonObInfoServer extends BaseCommonServer<PlmSupplierQaNonObInfoEntity_HI>
        implements IPlmSupplierQaNonObInfo {
    private static final Logger LOGGER = LoggerFactory.getLogger(PlmSupplierQaNonObInfoServer.class);
    @Autowired
    private ViewObject<PlmSupplierQaNonObInfoEntity_HI> plmSupplierQaNonObInfoDAO_HI;
    @Autowired
    private ViewObject<BaseProfileEntity_HI> baseProfileDAO_HI;
    @Autowired
    private ConfigModel configModel;
//    @Autowired
//    private IBaseProfile baseProfileServer;

    @Autowired
    private IPlmSupplierQaNonObInfo plmSupplierQaNonObInfoServer;

    @Autowired
    private BaseViewObject<PlmSupplierQaNonObInfoEntity_HI_RO> plmSupplierQaNonObInfoDAO_HI_RO;
    @Autowired
    private BaseViewObject<TtaSupplierEntity_HI_RO> ttaSupplierEntity_HI_RO;
    @Autowired
    private IPlmSupplierQaBrand plmSupplierQaBrandServer;
    @Autowired
    private IPlmProductSupplierInfo plmProductSupplierServer;

    @Autowired
    private IPlmSupplierQaDealer plmSupplierQaDealerServer;
    @Autowired
    private ViewObject<PlmSupplierQaDealerEntity_HI> plmSupplierQaDealerDAO_HI;
    @Autowired
    private ViewObject<PlmSupplierQaBrandEntity_HI> plmSupplierQaBrandDAO_HI;
    @Autowired
    private GenerateCodeServer generateCodeServer;

    public PlmSupplierQaNonObInfoServer() {
        super();
    }

    @Override
    public Pagination<PlmSupplierQaNonObInfoEntity_HI_RO> findPlmSupplierQaNonObInfoInfo(JSONObject queryParamJSON,
                                                                                         Integer pageIndex, Integer pageRows) {
        String UserType = queryParamJSON.getString("varUserType"); // 用户类型
        StringBuffer sql = new StringBuffer(PlmSupplierQaNonObInfoEntity_HI_RO.QUERY_SQL);
        if (queryParamJSON.containsKey("id")) {
            String id = queryParamJSON.getString("id");
            queryParamJSON.remove("id");
            queryParamJSON.put("plmSupplierQaNonObInfoId", new Integer(id.split("_")[0]));
        }

        if (queryParamJSON.containsKey("creatorName")) {
            queryParamJSON.remove("creatorName");
            String creatorName = queryParamJSON.getString("creatorName").toLowerCase();
            sql.append(" and lower(psqnoi.creatorName) like '%" + creatorName + "%'");
        } else if (queryParamJSON.containsKey("creatorName_like")) {
            String creatorName = queryParamJSON.getString("creatorName_like").toLowerCase();
            queryParamJSON.remove("creatorName_like");
            sql.append(" and lower(psqnoi.creator_name) like '%" + creatorName + "%'");
        }

        Map<String, Object> paramsMap = new HashMap<>();
        com.sie.saaf.common.util.SaafToolUtils.parperHbmParam(PlmSupplierQaNonObInfoEntity_HI_RO.class, queryParamJSON,
                sql, paramsMap);

        if (UserType.equals("60")) // 供应商用户 只查看自己的供应商资质文件
        {
//            String vendcode = queryParamJSON.getString("vendcode");
//            sql.append(" and psqnoi.SUPPLIER_CODE='" + vendcode + "'");
            JSONArray vendorCodeList = queryParamJSON.getJSONArray("vendorCodeList");
            if(vendorCodeList != null && !vendorCodeList.isEmpty()){//从session中获取供应商编码
                String vendorCode = "";
                for (Object object : vendorCodeList) {
                    vendorCode += "'"+object.toString()+"',";
                }
                vendorCode = vendorCode.substring(0, vendorCode.length()-1);
                String sqls = " and psqnoi.SUPPLIER_CODE IN ("+vendorCode+") ";
                sql.append(sqls);
            }
        }
        sql.append(" order by psqnoi.LAST_UPDATE_DATE desc");
        Pagination<PlmSupplierQaNonObInfoEntity_HI_RO> pagination = plmSupplierQaNonObInfoDAO_HI_RO.findPagination(sql,
                paramsMap, pageIndex, pageRows);
        return pagination;
    }

    @Override
    public Pagination<TtaSupplierEntity_HI_RO> findPlmSupplier(JSONObject queryParamJSON, Integer pageIndex,
                                                               Integer pageRows) {
        StringBuffer sql = new StringBuffer(PlmSupplierQaNonObInfoEntity_HI_RO.TTA_SUPPLIER);
        Map<String, Object> paramsMap = new HashMap<String, Object>();

        com.sie.saaf.common.util.SaafToolUtils.parperHbmParam(TtaSupplierEntity_HI_RO.class, queryParamJSON, sql,
                paramsMap);

        Pagination<TtaSupplierEntity_HI_RO> pagination = ttaSupplierEntity_HI_RO.findPagination(sql,
                SaafToolUtils.getSqlCountString(sql), paramsMap, pageIndex, pageRows);
        return pagination;
    }

    @Override
    public PlmSupplierQaNonObInfoEntity_HI savePlmSupplierQaNonObInfoInfo(JSONObject queryParamJSON)
            throws Exception {
        PlmSupplierQaNonObInfoEntity_HI entity = JSON.parseObject(queryParamJSON.toString(),
                PlmSupplierQaNonObInfoEntity_HI.class);
        entity.setOperatorUserId(queryParamJSON.getInteger("varUserId"));
        Integer headId = null;
        if (SaafToolUtils.isNullOrEmpty(entity.getPlmSupplierQaNonObInfoId())) {
            String supplierCode = entity.getSupplierCode();
            entity.setCreatorName(queryParamJSON.getString("curentName"));
              // 新增
//              Pagination<TtaSupplierEntity_HI_RO> ps = this.findPlmSupplier(new JSONObject().fluentPut("supplierCode",supplierCode), 1, 10);
//              List<TtaSupplierEntity_HI_RO> li = ps.getData();
//              if (li == null || li.isEmpty()){
//                throw new Exception("供应商编号输入有误");
//              }
//              entity.setQaGroupName("资质组[" + li.get(0).getSupplierName() + "]");
//            JSONObject jo = new JSONObject();
//            jo.put("supplierCode", supplierCode);
//            Pagination<TtaSupplierEntity_HI_RO> ps = this.findPlmSupplier(jo, 1, 10);
//            List<TtaSupplierEntity_HI_RO> li = ps.getData();
//            if (li.size() > 0) {
//                entity.setQaGroupName("资质组[" + li.get(0).getSupplierName() + "]");
//            } else {
//                entity.setQaGroupName("资质组[" + supplierCode + "]");
//            }

//            entity.setQaGroupCode("ZZ" + supplierCode);


            JSONObject query = new JSONObject().fluentPut("specialProductType",entity.getSpecialProductType()).fluentPut("qaGroupCode",entity.getQaGroupCode());
            List<PlmSupplierQaNonObInfoEntity_HI> queryList = plmSupplierQaNonObInfoServer.findList(query);
            if (!queryList.isEmpty()){
              throw new Exception("系统已存在对应的资质组和特殊商品类型组合，不能新增！");
            }
            JSONObject data = (JSONObject) JSONObject.toJSON(entity);
            PlmSupplierQaNonObInfoEntity_HI obj = plmSupplierQaNonObInfoServer.saveOrUpdate(data);
            headId = obj.getPlmSupplierQaNonObInfoId();

        } else {
            JSONObject jq = new JSONObject();
            //jq.put("supplierCode", entity.getSupplierCode());
            //JSONObject re = this.getProductSupplier(jq.toJSONString());
            //Integer cc = re.getInteger("data");
            jq.put("qaGroupCode", entity.getQaGroupCode());
            Integer cc = getProductSupplier(jq);
            if (cc > 0) {
                throw new Exception("该供应商的资质组有在新增商品过程中使用到,不允许发起修改流程!");
            }

            plmSupplierQaNonObInfoDAO_HI.saveOrUpdate(entity);
            headId = entity.getPlmSupplierQaNonObInfoId();
        }

        queryParamJSON.put("plmSupplierQaNonObInfoId", headId);

        if (!SaafToolUtils.isNullOrEmpty(queryParamJSON.getString("originSpecialProductType"))
                && !SaafToolUtils.isNullOrEmpty(entity.getSpecialProductType())
                && !queryParamJSON.getString("originSpecialProductType").equals(entity.getSpecialProductType())) {
            this.deleteAllRelatedRows(queryParamJSON);
        } else {
            this.saveRows(queryParamJSON);
        }
        entity.setPlmSupplierQaNonObInfoId(headId);
        return entity;
    }

    // 根据供应商编号查看是否有正在商品使用 该资质组
    @Override
    public JSONObject getProductSupplier(String params) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("params", params);
        JSONObject resultJson = getServiceResult(
                configModel.getPlmServer()+"/plmSupplierQaNonObInfoService/getProductSupplier", jsonObject);
        return resultJson;
    }

    public static JSONObject getServiceResult(String requestURL, JSONObject queryParamJSON) {
        Assert.isTrue(StringUtils.isNotBlank(requestURL), "requesturl is required");
        RestTemplate restTemplate = (RestTemplate) SpringBeanUtil.getBean("restTemplate");
        if (restTemplate == null) {
            throw new ExceptionInInitializerError("初始化异常");
        }

        MultiValueMap header = new LinkedMultiValueMap();
        Long timestamp = System.currentTimeMillis();
        header.add("timestamp", timestamp + "");
        header.add("pdasign", SaafToolUtils.buildSign(timestamp));
        JSONObject resultJSON = restSpringCloudPost(requestURL, queryParamJSON, header, restTemplate);
        if (resultJSON.getIntValue("statusCode") == 200) {
            if (JSON.parse(resultJSON.getString("data")) instanceof JSONArray) {
                return resultJSON;
            }
            JSONObject data = resultJSON.getJSONObject("data");
            return data;
        }
        return null;
    }

    public static JSONObject restSpringCloudPost(String requestURL, JSONObject postParam,
                                                 MultiValueMap<String, String> headerParams, RestTemplate restTemplate) {
        JSONObject resultJSONObject = new JSONObject();

        if (!headerParams.containsKey("Accept")) {
            headerParams.add("Accept", "application/json");
        }

        if (!headerParams.containsKey("Accept-Charset")) {
            headerParams.add("Accept-Charset", "utf-8");
        }

        if (!headerParams.containsKey("Content-Type")) {
            headerParams.add("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        }

        if (!headerParams.containsKey("Content-Encoding")) {
            headerParams.add("Content-Encoding", "UTF-8");
        }

        StringBuilder requestBodey = new StringBuilder();
        if (postParam != null && postParam.size() > 0) {
            Set<Map.Entry<String, Object>> entrySet = postParam.entrySet();
            Iterator iterator = entrySet.iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Object> entry = (Map.Entry) iterator.next();
                requestBodey.append(entry.getKey()).append("=");
                if (entry.getValue() != null) {
                    try {
                        requestBodey.append(URLEncoder.encode(entry.getValue().toString(), "utf-8"));
                    } catch (UnsupportedEncodingException e) {
                    }
                }
                if (iterator.hasNext())
                    requestBodey.append("&");
            }
        }

        HttpClientBuilder httpClientBuilder = HttpClients.custom();
        HttpClient httpClient = httpClientBuilder.build();
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(
                httpClient);

        if (postParam != null && StringUtils.isNotBlank(postParam.toJSONString())) {
            try {
                restTemplate.setRequestFactory(clientHttpRequestFactory);
                HttpEntity request = new HttpEntity(requestBodey.toString(), headerParams);
                ResponseEntity responseEntity = restTemplate.postForEntity(requestURL, request, String.class,
                        new Object[0]);
                HttpStatus strVlaue = responseEntity.getStatusCode();
                if (strVlaue.value() == 200) {
                    resultJSONObject.put("statusCode", strVlaue.value());
                }

                String body = (String) responseEntity.getBody();
                resultJSONObject.put("data", body);
            } catch (Exception var13) {
                resultJSONObject.put("msg", var13.getMessage());
            }
        }
        return resultJSONObject;
    }

    public void deleteAllRelatedRows(JSONObject deleteParams) {
        plmSupplierQaDealerDAO_HI.deleteAll(plmSupplierQaDealerDAO_HI.findByProperty("plmSupplierQaNonObInfoId",
                deleteParams.getInteger("plmSupplierQaNonObInfoId")));
        // plmSupplierQaBrandDAO_HI.deleteAll(plmSupplierQaBrandDAO_HI.findByProperty("plmSupplierQaNonObInfoId",deleteParams.getInteger("plmSupplierQaNonObInfoId")));
    }

    public void saveRows(JSONObject params) {
        if (!SaafToolUtils.isNullOrEmpty(params.getJSONArray("plmSupplierQaBrandList"))
                && params.getJSONArray("plmSupplierQaBrandList").size() != 0) {
            // JSONArray jason = params.getJSONArray("plmSupplierQaBrandList");

            plmSupplierQaBrandServer.savePlmSupplierQaBrandInfo(params);
        }
        if (!SaafToolUtils.isNullOrEmpty(params.getJSONArray("plmSupplierQaProducerList"))
                && params.getJSONArray("plmSupplierQaProducerList").size() != 0) {
            JSONObject inputParams = new JSONObject();
            inputParams.put("plmSupplierQaDealerList", params.getJSONArray("plmSupplierQaProducerList"));
            inputParams.put("plmSupplierQaNonObInfoId", params.getInteger("plmSupplierQaNonObInfoId"));
            plmSupplierQaDealerServer.savePlmSupplierQaDealerInfo(inputParams);
        }
        if (!SaafToolUtils.isNullOrEmpty(params.getJSONArray("plmSupplierQaDealerList"))
                && params.getJSONArray("plmSupplierQaDealerList").size() != 0) {
            plmSupplierQaDealerServer.savePlmSupplierQaDealerInfo(params);
        }
    }

    @Override
    public JSONObject findSupplierDetail(JSONObject param) {
        Integer infoid = null;
        if (param.containsKey("plmSupplierQaNonObInfoId")) {
            infoid = param.getInteger("plmSupplierQaNonObInfoId");
        }
        if (param.containsKey("id")) {
            infoid = new Integer(param.getString("id").split("_")[0]);
        }
        if (param.containsKey("qaGroupCode")) {
            JSONObject jq = new JSONObject();
            jq.put("qaGroupCode", param.getString("qaGroupCode"));
            List<PlmSupplierQaNonObInfoEntity_HI> nonli = this.findList(jq);
            if (nonli.size() > 0) {
                for (int i = 0; i < nonli.size(); i++) {
                    if (!nonli.get(i).getBillStatus().equals("CANCEL")) {
                        PlmSupplierQaNonObInfoEntity_HI productEntity = nonli.get(i);
                        JSONObject productJson = JSONObject.parseObject(JSONObject.toJSONString(productEntity));
                        JSONObject result = new JSONObject();
                        result.put("headInfo", productJson);
                        return result;
                    }
                }

            }
        }
        StringBuffer query = new StringBuffer(PlmSupplierQaNonObInfoEntity_HI_RO.QUERY_SQL);
        query.append(" and psqnoi.PLM_SUPPLIER_QA_NON_OB_INFO_ID = :infoId");
        Map<String, Object> paramProduct = new HashMap<>();
        paramProduct.put("infoId", infoid);
        PlmSupplierQaNonObInfoEntity_HI_RO productEntity = plmSupplierQaNonObInfoDAO_HI_RO.get(query, paramProduct);
        JSONObject productJson = JSONObject.parseObject(JSONObject.toJSONString(productEntity));
        JSONObject result = new JSONObject();
        result.put("headInfo", productJson);

        return result;
    }

    @Override
    public Integer getProductSupplier(JSONObject param) {
        StringBuffer query = new StringBuffer(PlmSupplierQaNonObInfoEntity_HI_RO.countsql);
        Map<String, Object> map = new HashMap<String, Object>();
        if(param.containsKey("supplierCode")){
            map.put("supplierCode", param.getString("supplierCode"));
            query.append(" and info.supplier_code=:supplierCode ");
        }
        if(param.containsKey("qaGroupCode")){
            map.put("qaGroupCode", param.getString("qaGroupCode"));
            query.append(" and info.group_id=:qaGroupCode ");
        }
        List<PlmSupplierQaNonObInfoEntity_HI_RO> list = plmSupplierQaNonObInfoDAO_HI_RO.findList(query, map);
        if (list.get(0) != null) {
            return list.get(0).getPlmSupplierQaNonObInfoId();
        }
        return null;

    }

    /**
     * 查询供应商
     *
     * @param parameters 查询参数
     * @param pageIndex  第几页
     * @param pageRows   查几行
     * @return 供应商列表
     */
    @Override
    public Pagination<PlmSupplierQaNonObInfoEntity_HI_RO> findVendor(JSONObject parameters, Integer pageIndex, Integer pageRows) throws Exception {
        Map<String, Object> profileMap = new HashMap<>(16);
        profileMap.put("profileCode", "VENDOR");
        profileMap.put("deleteFlag", 0);
        parameters.put("profileCode", "VENDOR");
//        List<JSONObject> profileSqlDatas = baseProfileServer.findProfileSqlDatas(parameters);
        List<BaseProfileEntity_HI> profises = baseProfileDAO_HI.findByProperty(profileMap);
        if (CollectionUtils.isEmpty(profises)) {
            throw new ServerException("没有可先'供应商',请配置'供应商'的profile文件! ");
        } else {
            String sourceSql = profises.get(0).getSourceSql();
            if (StringUtils.isBlank(sourceSql)) {
                throw new ServerException("没有可先'供应商',请配置'供应商'的 Profile SQL ! ");
            }
            return plmSupplierQaNonObInfoDAO_HI_RO.findPagination(sourceSql, new HashMap<>(16), pageIndex, pageRows);
        }
    }
}
