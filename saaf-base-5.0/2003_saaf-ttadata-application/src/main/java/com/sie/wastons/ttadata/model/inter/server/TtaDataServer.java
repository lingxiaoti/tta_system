package com.sie.wastons.ttadata.model.inter.server;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.wastons.sql.SqlTemplateUtil;
import com.sie.wastons.ttadata.model.entities.readyonly.ActBpmListEntity_HI_RO;
import com.sie.wastons.ttadata.model.entities.readyonly.UserInfoEntity_RO;
import com.sie.wastons.ttadata.model.entities.readyonly.VendorInfoEntity_RO;
import com.sie.wastons.ttadata.model.inter.ITtaData;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class TtaDataServer implements ITtaData {


    @Autowired
    private BaseViewObject<JSONObject> commonDAO_HI_DY;

    @Autowired
    private BaseViewObject<VendorInfoEntity_RO> vendorDAO_HI_RO;
    @Autowired
    private BaseViewObject<UserInfoEntity_RO> userInfoDAO_HI_RO;
    @Autowired
    private BaseViewObject<ActBpmListEntity_HI_RO> actBpmListDAO_HI_RO;


    /**
     * 查询快码
     *
     * @param loookupType
     * @return
     */
//    @Cacheable(key = "#loookupType", value = "cacheDic", depict = "字典", firstCache = @FirstCache(expireTime = 1), secondaryCache = @SecondaryCache(expireTime = 1, timeUnit = TimeUnit.MINUTES))
    public Map<String, String> getLookUpValuesMap(String loookupType) {

        return getLookUpValuesMap(loookupType, null);
    }


//    @Cacheable(key = "#loookupType", value = "cacheDicTrun", depict = "字典", firstCache = @FirstCache(expireTime = 1), secondaryCache = @SecondaryCache(expireTime = 1, timeUnit = TimeUnit.MINUTES))
    public Map<String, String> getLookUpValuesMapOverturn(String loookupType) {
        if (StringUtils.isBlank(loookupType)) {
            return Collections.emptyMap();
        }
        List list = commonDAO_HI_DY.findList("SELECT " +
          "LOOKUP_CODE," +
          "MEANING " +
          "FROM BASE_LOOKUP_VALUES " +
          "WHERE DELETE_FLAG = 0 \n" +
          "AND trunc(nvl(START_DATE_ACTIVE,sysdate)) <= trunc(sysdate) \n" +
          "AND trunc(nvl(END_DATE_ACTIVE,sysdate))>=trunc(sysdate)  \n" +
          "AND ENABLED_FLAG = 'Y' AND LOOKUP_TYPE=?", loookupType);
        Map<String, String> result = new HashMap<>();
        for (Object obj : list) {
            if (!(obj instanceof Map)) {
                continue;
            }
            Map<String, Object> map = (Map<String, Object>) obj;
            result.put(map.get("MEANING").toString(), map.get("LOOKUP_CODE").toString());
        }
        return result;
    }

    /**
     * 查询快码
     *
     * @param loookupType
     * @param systemCode
     * @return
     */
    public Map<String, String> getLookUpValuesMap(String loookupType, String systemCode) {
        String sql = "SELECT " +
                     "LOOKUP_CODE," +
                     "MEANING," +
                     "DESCRIPTION " +
                     "FROM BASE_LOOKUP_VALUES \n" +
                     "WHERE DELETE_FLAG = 0 \n" +
                     "AND trunc(nvl(START_DATE_ACTIVE,sysdate)) <= trunc(sysdate) \n" +
                     "AND trunc(nvl(END_DATE_ACTIVE,sysdate))>=trunc(sysdate)  \n" +
                     "AND ENABLED_FLAG = 'Y' AND LOOKUP_TYPE=?";
        List list = StringUtils.isBlank(systemCode) ? commonDAO_HI_DY.findList(sql, loookupType) :
                commonDAO_HI_DY.findList(sql + " AND SYSTEM_CODE=?", loookupType, systemCode);
        Map<String, String> result = new HashMap<>();
        for (Object obj : list) {
            if (!(obj instanceof Map)) {
                continue;
            }
            Map<String, Object> map = (Map<String, Object>) obj;
            result.put(map.get("LOOKUP_CODE").toString(), map.get("MEANING").toString());
        }
        return result;
    }


//    @Cacheable(key = "#vendorCode", value = "cacheVendorExist", depict = "供应商是否存在", firstCache = @FirstCache(expireTime = 10), secondaryCache = @SecondaryCache(expireTime = 10, timeUnit = TimeUnit.MINUTES))
    public boolean vendorIsExist(String vendorCode) {
        if (StringUtils.isBlank(vendorCode)) {
            return false;
        }
        return commonDAO_HI_DY.findList("SELECT SUPPLIER_CODE AS VENDOR_NBR FROM TTA_SUPPLIER WHERE SUPPLIER_CODE=?", vendorCode).size() > 0;
    }


    public Pagination<VendorInfoEntity_RO> findVendorInfo(VendorInfoEntity_RO obj, Integer pageIndex, Integer pageRows) throws IllegalAccessException {
        return SqlTemplateUtil.findSqlPagination(vendorDAO_HI_RO, obj, "SELECT SUPPLIER_CODE  AS VENDOR_NBR,SUPPLIER_NAME AS VENDOR_NAME,STATUS FROM TTA_SUPPLIER WHERE 1=1 ", pageIndex, pageRows);
    }


//    @Cacheable(key = "#vendorCode", value = "cacheVendorName", depict = "供应商名称", firstCache = @FirstCache(expireTime = 10), secondaryCache = @SecondaryCache(expireTime = 10, timeUnit = TimeUnit.MINUTES))
    public String getVendorName(String vendorCode) {
        if (StringUtils.isBlank(vendorCode)) {
            return null;
        }

        VendorInfoEntity_RO vendorInfoBean = vendorDAO_HI_RO.get("SELECT SUPPLIER_NAME AS VENDOR_NAME FROM TTA_SUPPLIER WHERE SUPPLIER_CODE=?", vendorCode);
        return Optional.ofNullable(vendorInfoBean).map(VendorInfoEntity_RO::getVendorName).orElse(null);
    }


//    @Cacheable(key = "#vendorName", value = "cacheVendorCode", depict = "供应商编码", firstCache = @FirstCache(expireTime = 10), secondaryCache = @SecondaryCache(expireTime = 10, timeUnit = TimeUnit.MINUTES))
    public List<String> getVendorCode(String vendorName) {
        if (StringUtils.isBlank(vendorName)) {
            return Collections.emptyList();
        }

        List<String> a = vendorDAO_HI_RO.findList("SELECT DISTINCT SUPPLIER_CODE AS VENDOR_NBR FROM TTA_SUPPLIER WHERE SUPPLIER_NAME like ?", "%" + vendorName + "%")
                .stream()
                .map(VendorInfoEntity_RO::getVendorNbr)
                .collect(Collectors.toList());
        System.out.println(a);
        return a;
    }

    public JSONObject getActivitiesHistoric(String billNo) {
        ActBpmListEntity_HI_RO vendorInfoBean = actBpmListDAO_HI_RO.get("SELECT PROC_INST_ID,LIST_ID  FROM ACT_BPM_LIST WHERE BILL_NO=?", billNo);
        return JSONObject.parseObject(JSONObject.toJSONString(vendorInfoBean));
    }

    public JSONObject getUnionDepartmentInfo(String params) {
        JSONObject paramsJson = JSONObject.parseObject(params);
        StringBuffer sql = new StringBuffer(UserInfoEntity_RO.QUERY_SQL_UNION_DEPT);
        Integer pageRows = 5;
        Integer pageIndex = paramsJson.getInteger("pageIndex");

        if (StringUtils.isNotBlank(paramsJson.getString("code"))){
            String code = paramsJson.getString("code");
            sql.append(" and t.code = '" + code + "'");
        }
        if (StringUtils.isNotBlank(paramsJson.getString("nameCn"))){
            String nameCn = paramsJson.getString("nameCn");
            sql.append(" and t.name_cn like '%" + nameCn + "%'");
        }
        if (StringUtils.isNotBlank(paramsJson.getString("nameEn"))){
            String nameEn = paramsJson.getString("nameEn");
            sql.append(" and t.name_en like '%" + nameEn + "%'");
        }
        List<UserInfoEntity_RO> a = userInfoDAO_HI_RO.findList("select count(1) userId from (" + sql + ")");
        UserInfoEntity_RO aa = a.get(0);
        List<UserInfoEntity_RO> b = userInfoDAO_HI_RO.findList("select ceil( count(1)/5) userId from (" + sql + ")");
        UserInfoEntity_RO bb = b.get(0);
        System.out.println(sql);
        Pagination<UserInfoEntity_RO> paginationa =  userInfoDAO_HI_RO.findPagination(sql, "select 1 from DUAL", pageIndex, pageRows);
        paginationa.setCount(aa.getUserId());
        paginationa.setCurIndex(pageIndex);
        paginationa.setNextIndex(pageIndex+1);
        paginationa.setPagesCount(bb.getUserId());
        paginationa.setPageSize(5);
        return JSONObject.parseObject(JSONObject.toJSONString(paginationa));
    }


    public JSONObject getLookUpValuesForCategory(String lookupType, String lookupCode) {
        List list = commonDAO_HI_DY.findList("SELECT LOOKUP_CODE,MEANING,DESCRIPTION FROM BASE_LOOKUP_VALUES WHERE  DELETE_FLAG = 0 and LOOKUP_TYPE=? AND LOOKUP_CODE=? ", lookupType, lookupCode);
        Assert.notEmpty(list, "根据快码类型和快码编号" + lookupType + "," + lookupCode + "查询快码信息为空");
        JSONObject jsonObject =   JSONObject.parseObject(JSONObject.toJSONString(list.get(0)))  ;
        JSONObject resultJson = new JSONObject();
        resultJson.put("lookupCode", jsonObject.getString("LOOKUP_CODE"));
        resultJson.put("meaning", jsonObject.getString("MEANING"));
        resultJson.put("description", jsonObject.getString("DESCRIPTION"));
        return resultJson;
    }

    public JSONObject findScoringMemberLov(String params ) {
        JSONObject paramsJson = JSONObject.parseObject(params);
        StringBuffer sql = new StringBuffer(UserInfoEntity_RO.QUERY_SQL_SCORING_MEMBER);
        Map<String, Object> map = new HashMap<>(4);
        Integer pageRows = 5;
        Integer pageIndex = paramsJson.getInteger("pageIndex");
        SaafToolUtils.parperParam(paramsJson, "p.employee_number", "employeeNumber", sql, map, "like");
        SaafToolUtils.parperParam(paramsJson, "p.person_name", "personName", sql, map, "like");
//        SaafToolUtils.parperParam(paramsJson, "p.dept_no", "deptNo", sql, map, "=");
        // 英文名模糊查询
        if (StringUtils.isNotBlank(paramsJson.getString("personNameEn"))){
            String personNameEnUpper = paramsJson.getString("personNameEn").toUpperCase();
            sql.append(" and upper(p.person_name_en) like '%"+personNameEnUpper+"%'");
        }
        if (StringUtils.isNotBlank(paramsJson.getString("deptNo"))){
            String deptNo = paramsJson.getString("deptNo");
            sql.append(" and p.dept_no = '" + deptNo + "'");
        }
        if (StringUtils.isNotBlank(paramsJson.getString("deptNo_in"))){
            String deptNo = paramsJson.getString("deptNo_in");
            sql.append(" and p.dept_no in " + deptNo);
        }
        List<UserInfoEntity_RO> a = userInfoDAO_HI_RO.findList("select count(1) userId from (" + sql + ")",map);
        UserInfoEntity_RO aa = a.get(0);
        List<UserInfoEntity_RO> b = userInfoDAO_HI_RO.findList("select ceil( count(1)/5) userId from (" + sql + ")",map);
        UserInfoEntity_RO bb = b.get(0);
        System.out.println(sql);
        Pagination<UserInfoEntity_RO> paginationa =  userInfoDAO_HI_RO.findPagination(sql, "select 1 from DUAL", map, pageIndex, pageRows);
        paginationa.setCount(aa.getUserId());
        paginationa.setCurIndex(pageIndex);
        paginationa.setNextIndex(pageIndex+1);
        paginationa.setPagesCount(bb.getUserId());
        paginationa.setPageSize(5);
        return JSONObject.parseObject(JSONObject.toJSONString(paginationa));
    }

    public JSONObject findInterfaceAccessControl(String params ) {
        JSONObject paramsJson = JSONObject.parseObject(params);
        StringBuffer sql = new StringBuffer(UserInfoEntity_RO.QUERY_SQL_INTERFACE_ACCESS_CONTROL);
        Map<String, Object> map = new HashMap<>();
        SaafToolUtils.parperParam(paramsJson, "rr.responsibility_id", "responsibilityId", sql, map, "=");
        SaafToolUtils.parperParam(paramsJson, "bm.menu_code", "menuCode", sql, map, "=");

        Pagination<UserInfoEntity_RO> paginationa =  userInfoDAO_HI_RO.findPagination(sql,  map, 1, 999999);
        return JSONObject.parseObject(JSONObject.toJSONString(paginationa));
    }


    @Override
    public List<UserInfoEntity_RO> findProccessUsersByuserId(Integer userId,String jobCode){
        if (userId==null){
            return Collections.emptyList();
        }
        StringBuilder sql=new StringBuilder(UserInfoEntity_RO.QUERY_PROCCESS_USER_NODE).append(" AND U.USER_ID=?");
        List<UserInfoEntity_RO> list= userInfoDAO_HI_RO.findList(sql,userId);
        List<UserInfoEntity_RO> result=new ArrayList<>();
        Set<Integer> userids=new HashSet<>();
        List<UserInfoEntity_RO> tList=new ArrayList<>();
        int count=50;
        while (list.size()>0 && --count>0){
            for (UserInfoEntity_RO user:list){
                if (user.getUserId()==null){
                    continue;
                }
                List<UserInfoEntity_RO> nextList=userInfoDAO_HI_RO.findList(sql,user.getUserId());
                for (UserInfoEntity_RO next:nextList){
                    if (!userids.add(user.getUserId())){//避免死循环
                        continue;
                    }
                    tList.add(next);
                }
                if (result.stream().noneMatch(u->user.getUserId()-u.getUserId()==0)){
                    result.add(user);
                }
            }

            if ("next".equals(jobCode)){//只查一级
                return result;
            }
            list.clear();
            list.addAll(tList);
            tList.clear();
        }
        if (StringUtils.isBlank(jobCode)){
            return result;
        }

        List<UserInfoEntity_RO> userList = new ArrayList<>();
        for (int i = 0; i < result.size(); i++) {
            UserInfoEntity_RO user = result.get(i);
            // jobCode 包含Manager，但不包含Senior、General
            if ("Trading Manager".equals(jobCode)) {
                String userJobCode = user.getJobCode();
                if (userJobCode.contains("Manager") && !userJobCode.contains("Senior") && !userJobCode.contains("General Manager")) {
                    userList.add(user);
                }
            }

            // jobCode 包含senior和Manager
            if ("Senior Trading Manager".equals(jobCode)) {
                String userJobCode = user.getJobCode();
                if (userJobCode.contains("Manager") && userJobCode.contains("Senior") && !userJobCode.contains("General Manager")) {
                    userList.add(user);
                }
            }

            // jobCode 包含Controller或者Assistant Trading Director 优先判断Controller，有则返回停止判断，没有判断Assistant Trading Director
            if ("Trading Controller".equals(jobCode)) {
                String userJobCode = user.getJobCode();
                if (userJobCode.contains("Controller")) {
                    userList.add(user);
                }

                // 如果是result最后一个也不包含Controller，则判断Assistant Trading Director
                if ((result.indexOf(user) == result.size() - 1) && userList.size() == 0) {
                    for (UserInfoEntity_RO entity: result) {
                        if ("Assistant Trading Director".equals(entity.getJobCode())) {
                            userList.add(entity);
                        }
                    }
                }
            }
        }

        if ("Trading Manager".equals(jobCode) || "Senior Trading Manager".equals(jobCode) || "Trading Controller".equals(jobCode)) {
            return userList;
        } else {
            return result.stream().filter(obj->jobCode.equals(obj.getJobCode())).collect(Collectors.toList());
        }
    }






}
