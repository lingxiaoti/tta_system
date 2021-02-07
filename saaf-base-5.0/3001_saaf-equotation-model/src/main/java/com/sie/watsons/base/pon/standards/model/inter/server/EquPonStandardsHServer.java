package com.sie.watsons.base.pon.standards.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.model.inter.server.GenerateCodeServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.pon.standards.model.entities.EquPonStandardsHEntity_HI;
import com.sie.watsons.base.pon.standards.model.entities.EquPonStandardsLEntity_HI;
import com.sie.watsons.base.pon.standards.model.entities.readonly.EquPonStandardsHEntity_HI_RO;
import com.sie.watsons.base.pon.standards.model.entities.readonly.EquPonStandardsLEntity_HI_RO;
import com.sie.watsons.base.pon.standards.model.inter.IEquPonStandardsH;
import com.sie.watsons.base.utils.CommonUtils;
import com.sie.watsons.base.utils.ResultUtils;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisCluster;

@Component("equPonStandardsHServer")
public class EquPonStandardsHServer extends BaseCommonServer<EquPonStandardsHEntity_HI> implements IEquPonStandardsH {
    private static final Logger LOGGER = LoggerFactory.getLogger(EquPonStandardsHServer.class);

    @Autowired
    private ViewObject<EquPonStandardsHEntity_HI> equPonStandardsHDAO_HI;

    @Autowired
    private ViewObject<EquPonStandardsLEntity_HI> equPonStandardsLDAO_HI;

    @Autowired
    private BaseViewObject<EquPonStandardsHEntity_HI_RO> equPonStandardsHDAO_HI_RO;

    @Autowired
    private BaseViewObject<EquPonStandardsLEntity_HI_RO> equPonStandardsLDAO_HI_RO;

    @Autowired
    private GenerateCodeServer generateCodeServer;

    @Autowired
    private JedisCluster jedisCluster;

    public EquPonStandardsHServer() {
        super();
    }

    @Override
    public Pagination<EquPonStandardsHEntity_HI_RO> findPonStandardsInfo(String params, Integer pageIndex, Integer pageRows) {
        JSONObject jsonParam = JSONObject.parseObject(params);
        LOGGER.info("------jsonParam------" + jsonParam.toString());
        StringBuffer queryString = new StringBuffer(
                EquPonStandardsHEntity_HI_RO.QUERY_LIST_SQL);
        Map<String, Object> map = new HashMap<String, Object>();
        SaafToolUtils.parperParam(jsonParam, "t.standards_Name", "standardsName", queryString, map, "like");
        SaafToolUtils.parperParam(jsonParam, "T.standards_Code", "standardsCode", queryString, map, "like");
        SaafToolUtils.parperParam(jsonParam, "to_number(T.standards_Status)", "standardsStatus", queryString, map, "=");
        SaafToolUtils.parperParam(jsonParam, "bu.user_name", "createdByName", queryString, map, "like");
        JSONObject dateParam = new JSONObject();
        dateParam.put("creationDate_gte",jsonParam.getString("creationDateFrom"));
        dateParam.put("creationDate_lte",jsonParam.getString("creationDateTo"));
        if("13".equals(jsonParam.getString("deptCode"))){
            queryString.append(" and t.dept_code in ('13','0E') ");
        }else{
            dateParam.put("deptCode",jsonParam.getString("deptCode"));
        }
        SaafToolUtils.parperHbmParam(EquPonStandardsHEntity_HI_RO.class, dateParam, queryString, map);
        SaafToolUtils.parperParam(jsonParam, "T.standards_Id", "selectId", queryString, map, "<>");

        // 排序
        queryString.append(" order by t.creation_date desc");
        Pagination<EquPonStandardsHEntity_HI_RO> returnList = equPonStandardsHDAO_HI_RO.findPagination(queryString, map, pageIndex, pageRows);

        return returnList;
    }

    @Override
    public JSONObject findPonStandardsDatail(String params) {
        JSONObject jsonParam = JSONObject.parseObject(params);
        JSONObject retuenJson = new JSONObject();
        StringBuffer queryString = new StringBuffer(
                EquPonStandardsHEntity_HI_RO.QUERY_LIST_SQL);
        Map<String, Object> map = new HashMap<String, Object>();
        if(jsonParam.get("id")!=null){
            jsonParam.put("standardsId",jsonParam.getInteger("id"));
        }
        SaafToolUtils.parperParam(jsonParam, "t.standards_id", "standardsId", queryString, map, "=");
        EquPonStandardsHEntity_HI_RO hEntity = (EquPonStandardsHEntity_HI_RO) equPonStandardsHDAO_HI_RO.get(queryString, map);

        StringBuffer queryLineString = new StringBuffer(
                EquPonStandardsLEntity_HI_RO.QUERY_LINE_SQL);
        Map<String, Object> lineMap = new HashMap<String, Object>();
//        standardsId
        lineMap.put("standardsId", jsonParam.getInteger("standardsId"));
        List<EquPonStandardsLEntity_HI_RO> lineList = equPonStandardsLDAO_HI_RO.findList(queryLineString, lineMap);
        String previousNumber;
        String lineNumber = "";
//        查询完信息后在重新组装（主要是修改编号）
        for (int i = 0; i < lineList.size(); i++) {
            EquPonStandardsLEntity_HI_RO line = lineList.get(i);
            if (i == 0) {
                line.setLineNumber("1");
            } else {
                EquPonStandardsLEntity_HI_RO previousLine = lineList.get(i - 1);
                previousNumber = previousLine.getLineNumber();
                //如果是同一级别,就截取最末数字加一
                if (previousLine.getLineLv() == line.getLineLv()) {
                    int one = previousNumber.lastIndexOf(".");
                    //截取最末数字加1
                    Integer a = Integer.parseInt(previousNumber.substring((one + 1), previousNumber.length())) + 1;
                    lineNumber = previousNumber.substring(0, one + 1) + a;
                }
                //如果是更低级,直接在原来的基础上加.1
                else if (previousLine.getLineLv() < line.getLineLv()) {
                    lineNumber = previousNumber + ".1";
                }//如果是回到高级,则截取对应字段再加一
                else if (previousLine.getLineLv() > line.getLineLv()) {
                    Integer lineLv = line.getLineLv();
                    String[] lineNumbers = previousNumber.split("\\.");
                    if (lineLv == 1) {
                        lineNumber = String.valueOf(Integer.parseInt(lineNumbers[0]) + 1);
                    } else {
                        for (int j = 0; j < lineLv - 1; j++) {
                            if (j == 0) {
                                lineNumber = lineNumbers[j];
                            } else {
                                lineNumber = lineNumber + "." + lineNumbers[j];
                            }
                        }
                        //拼接编号
                        lineNumber = lineNumber + "." + String.valueOf(Integer.parseInt(lineNumbers[lineLv - 1]) + 1);
                    }
                }
                line.setLineNumber(lineNumber);
            }
        }
        EquPonStandardsLEntity_HI_RO maxLineLv = new EquPonStandardsLEntity_HI_RO();
        maxLineLv.setLineLv(1);
        if(lineList.size()>0){
            maxLineLv = lineList.stream().max((stu1,stu2)->Integer.compare(stu1.getLineLv(),stu2.getLineLv())).get();
        }
        hEntity.setTableLv(maxLineLv.getLineLv());
        retuenJson.put("hEntity", hEntity);
        retuenJson.put("lineData", lineList);
        return retuenJson;
    }

    @Override
    public JSONObject findPonStandardsDatailByCode(JSONObject params) {
        StringBuffer sql = null;
        Map<String, Object> map = new HashMap<>();
        Integer standardsId = params.getInteger("standardsId");
        sql = new StringBuffer(EquPonStandardsLEntity_HI_RO.QUERY_LINE_SQL);
        map.put("standardsId", standardsId);
        params.remove("standardsId");
        SaafToolUtils.parperHbmParam(EquPonStandardsHEntity_HI_RO.class, params, sql, map);
        Pagination<EquPonStandardsLEntity_HI_RO> pagination = equPonStandardsLDAO_HI_RO.findPagination(sql, map,1,10);
        return JSONObject.parseObject(JSONObject.toJSONString(pagination));
    }


    @Override
    public JSONObject savePonStandards(JSONObject jsonObject, int userId) throws Exception {
        EquPonStandardsHEntity_HI jsonEntity = JSON.parseObject(jsonObject.toJSONString(), EquPonStandardsHEntity_HI.class);
        EquPonStandardsHEntity_HI saveHEntity;
        JSONObject returnJson = new JSONObject();
        String action = jsonObject.getString("action");
//        this.demo();
        if (jsonObject.get("standardsId") == null) {
            saveHEntity = jsonEntity;
            String code = generateCodeServer.getSupplierSuspendCode("PFBZ", 4, true, true);
            saveHEntity.setStandardsCode(code);
            if (saveHEntity.getCreatedBy() == null) {
                saveHEntity.setCreatedBy(userId);
                saveHEntity.setCreationDate(new Date());
            }

            saveHEntity.setOperatorUserId(userId);
            equPonStandardsHDAO_HI.save(saveHEntity);
        } else {
//            saveHEntity = equPonStandardsHDAO_HI.getById(jsonObject.getInteger("standardsId"));
            jsonObject.put("operatorUserId", userId);
            saveHEntity = this.saveOrUpdate(jsonObject);
        }

        //只有保存的时候对行数据进行保存
        JSONArray lineData = jsonObject.getJSONArray("line");
        EquPonStandardsLEntity_HI lineEntity = new EquPonStandardsLEntity_HI();
        EquPonStandardsLEntity_HI previousLine = new EquPonStandardsLEntity_HI();
        List<EquPonStandardsLEntity_HI> submitList = new ArrayList<>();
        if ("save".equals(action) || "submit".equals(action)) {
            for (int i = 0; i < lineData.size(); i++) {
                JSONObject newLine = lineData.getJSONObject(i);
                lineEntity = JSON.parseObject(newLine.toJSONString(), EquPonStandardsLEntity_HI.class);
                if (newLine.get("standardsLId") == null) {
                    if (newLine.getInteger("lineLv") > 1 && lineEntity.getLineLv() == previousLine.getLineLv()) {
                        lineEntity.setParentId(previousLine.getParentId());
                    }//当前行比上一行级别高
                    else if (newLine.getInteger("lineLv") > 1 && lineEntity.getLineLv() > previousLine.getLineLv()) {
                        lineEntity.setParentId(previousLine.getStandardsLId());
                    }//当前行比上一行级别低
                    else if (newLine.getInteger("lineLv") > 1 && lineEntity.getLineLv() < previousLine.getLineLv()) {
                        Integer parendLv = lineEntity.getLineLv()-1;
                        List<EquPonStandardsLEntity_HI> setList = submitList.stream().filter(s -> parendLv.toString().equals(s.getLineLv().toString())).collect(Collectors.toList());
                        lineEntity.setParentId(setList.get(setList.size()-1).getStandardsLId());
                    }
                    lineEntity.setStandardsId(saveHEntity.getStandardsId());
                }
                if (lineEntity.getCreatedBy() == null) {
                    lineEntity.setCreatedBy(userId);
                    lineEntity.setCreationDate(new Date());
                }
                lineEntity.setOperatorUserId(userId);
                equPonStandardsLDAO_HI.saveEntity(lineEntity);
                previousLine = lineEntity;
                submitList.add(previousLine);
            }
        }
        //提交之前验证数据
        if ("submit".equals(action)) {
//            验证第一级总和为100
            try{
                List<EquPonStandardsLEntity_HI> setList = submitList.stream().filter(s -> "1".equals(s.getLineLv().toString())).collect(Collectors.toList());
                int sum = setList.stream().mapToInt(EquPonStandardsLEntity_HI::getWeight).sum();
                if(sum!=100){
                    returnJson.put("standardsId", saveHEntity.getStandardsId());
                    returnJson.put("mes","一级权重的和不为100");
                    returnJson.put("status", "E");
                    return returnJson;
                }
//          验证权重和是否相等
                for (EquPonStandardsLEntity_HI line : submitList) {
                    List<EquPonStandardsLEntity_HI> lineList = submitList.stream().filter(s -> line.getStandardsLId().equals(s.getParentId())).collect(Collectors.toList());
                    if(lineList.size()>0){
                        sum = lineList.stream().mapToInt(EquPonStandardsLEntity_HI::getWeight).sum();
                        int lineWeight = line.getWeight();
                        if(sum!=lineWeight){
                            returnJson.put("standardsId", saveHEntity.getStandardsId());
                            returnJson.put("mes", "评分区间: "+ line.getGradingDivision()+"的权重与下级权重和值和不相等");
                            returnJson.put("status", "E");
                            return returnJson;
                        }
                    }
                }
            }catch (Exception e){
                returnJson.put("standardsId", saveHEntity.getStandardsId());
                returnJson.put("mes","提交报错,请检验必填项");
                returnJson.put("status", "E");
                return returnJson;
            }

        }


        switch (action) {
            case "approve":
                saveHEntity.setStandardsStatus("30");
//                if(saveHEntity.getParentStandardsCode()!=null){
//                    Map map = new HashMap();
//                    map.put("standardsCode",saveHEntity.getParentStandardsCode());
//
//                    List<EquPonStandardsHEntity_HI> list = equPonStandardsHDAO_HI.findList("from EquPonStandardsHEntity_HI where standardsCode = :standardsCode",map);
//                    if(list.size()>0){
//                        EquPonStandardsHEntity_HI entityHi =  list.get(0);
//                        entityHi.setStandardsStatus("50");
//                        entityHi.setOperatorUserId(userId);
//                        equPonStandardsHDAO_HI.save(entityHi);
//                    }
//                }
                break;
            case "cancel":
                saveHEntity.setStandardsStatus("50");
                break;
            case "reject":
                saveHEntity.setStandardsStatus("40");
                break;
        }
        saveHEntity.setOperatorUserId(userId);
        equPonStandardsHDAO_HI.save(saveHEntity);
        returnJson.put("standardsId", saveHEntity.getStandardsId());
        returnJson.put("status", "S");
        return returnJson;
    }

    @Override
    public void deletePonStandardsLine(JSONObject jsonObject, int userId) {
        Integer standardsLId = jsonObject.getInteger("standardsLId");
        equPonStandardsLDAO_HI.delete(standardsLId);
    }

    @Override
    public void deleteCopyStandardsDatail(JSONObject jsonObject) {
        if(jsonObject.get("deleteId")!=null){
            Integer standardsId = jsonObject.getInteger("deleteId");
            Map map = new HashMap();
            map.put("standardsId",standardsId);
            List<EquPonStandardsLEntity_HI> deleteList =  equPonStandardsLDAO_HI.findList("from EquPonStandardsLEntity_HI WHERE standardsId = :standardsId",map);
            equPonStandardsLDAO_HI.delete(deleteList);
        }
    }

    @Override
    public JSONObject returnCopyDatail(JSONObject returnJson) {
        JSONArray lineList = returnJson.getJSONArray("lineData");
        JSONArray reJson = new JSONArray();
        for (Object o : lineList) {
            String a = JSONObject.toJSONString(o);
            JSONObject b =  JSONObject.parseObject(a);
            b.remove("standardsId");
            b.remove("standardsLId");
            reJson.add(b);
        }
        returnJson.put("lineData",reJson);
        return returnJson;
    }

    @Override
    public Integer deletePonStandards(JSONObject jsonObject, int userId) {
        Integer standardsId = jsonObject.getInteger("standardsId");
        equPonStandardsHDAO_HI.delete(standardsId);
        jsonObject.put("deleteId",jsonObject.getString("standardsId"));
        this.deleteCopyStandardsDatail(jsonObject);

        //单据删除时，判断单据状态如果为驳回，则查询单据的流程id，返回前端，用于终止流程
        String standardsStatus = jsonObject.getString("standardsStatus");
        String standardsCode = jsonObject.getString("standardsCode");
        if("40".equals(standardsStatus)){
            //查询流程信息，提取流程id
            JSONObject b = new JSONObject();
            b.put("code", standardsCode);
            JSONObject resultJSON = ResultUtils.getServiceResult("http://1002-saaf-api-gateway/api/ttadataServer" + "/ttadata/ttaBaseDataService/v2/getActivitiesHistoric", b);
            //根据流程id，终止流程
            Integer listId = resultJSON.getInteger("listId");
            return listId;
        }
        return null;
    }

    /**
     * 评分标准审批回调接口
     * @param parseObject
     * @return
     * @throws Exception
     */
    @Override
    public EquPonStandardsHEntity_HI standardsApprovalCallback(JSONObject parseObject, int userId) throws Exception {
        Integer headerId = parseObject.getIntValue("id");//单据Id
        EquPonStandardsHEntity_HI entityHeader = equPonStandardsHDAO_HI.getById(headerId);
        String orderStatus = null;//状态
        switch (parseObject.getString("status")) {
            case "REFUSAL":
                orderStatus = "40";
                break;
            case "ALLOW":
                orderStatus = "30";
                if(entityHeader.getParentStandardsCode()!=null){
                    Map map = new HashMap();
                    map.put("standardsCode",entityHeader.getParentStandardsCode());
                    List<EquPonStandardsHEntity_HI> list = equPonStandardsHDAO_HI.findList("from EquPonStandardsHEntity_HI where standardsCode = :standardsCode",map);
                    if(list.size()>0){
                        EquPonStandardsHEntity_HI entityHi =  list.get(0);
                        entityHi.setStandardsStatus("50");
                        entityHi.setOperatorUserId(userId);
                        equPonStandardsHDAO_HI.save(entityHi);
                    }
                }
                break;
            case "DRAFT":
                orderStatus = "20";
                break;
            case "APPROVAL":
                orderStatus = "20";
                break;
            case "CLOSED":
                orderStatus = "50";
                break;
        }

        //流程节点审批通过,邮件通知owner
        CommonUtils.processApprovalEmailToOwner(parseObject,entityHeader.getCreatedBy(),entityHeader.getStandardsCode());

        entityHeader.setStandardsStatus(orderStatus);
        entityHeader.setOperatorUserId(userId);
        equPonStandardsHDAO_HI.saveEntity(entityHeader);
        return entityHeader;
    }

    @Override
    public JSONObject savePonStandardsSubmit(JSONObject jsonObject, int userId) {
        EquPonStandardsHEntity_HI jsonEntity = JSON.parseObject(jsonObject.toJSONString(), EquPonStandardsHEntity_HI.class);
        EquPonStandardsHEntity_HI saveHEntity;
        saveHEntity = equPonStandardsHDAO_HI.getById(jsonObject.getInteger("standardsId"));
        saveHEntity.setStandardsStatus("20");
        saveHEntity.setOperatorUserId(userId);
        equPonStandardsHDAO_HI.save(saveHEntity);
        return null;
    }
}
