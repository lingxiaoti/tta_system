package com.sie.watsons.base.pos.scoreUpdate.model.inter.server;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.util.SaafBeanUtils;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.pos.enums.ScoreUpdateEum;
import com.sie.watsons.base.pos.scoreUpdate.model.entities.EquPosScoreUpdateLineEntity_HI;
import com.sie.watsons.base.pos.scoreUpdate.model.entities.readonly.EquPosScoreUpdateLineEntity_HI_RO;
import com.sie.watsons.base.pos.scoreUpdate.model.inter.IEquPosScoreUpdateLine;
import com.sie.watsons.base.pos.supplierinfo.model.dao.EquPosSupplierInfoDAO_HI;
import com.sie.watsons.base.pos.supplierinfo.model.entities.EquPosSupplierInfoEntity_HI;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Component("equPosScoreUpdateLindServer")
public class EquPosScoreUpdateLineServer extends BaseCommonServer<EquPosScoreUpdateLineEntity_HI> implements IEquPosScoreUpdateLine {
    private static final Logger LOGGER = LoggerFactory.getLogger(EquPosScoreUpdateLineServer.class);

    @Autowired
    private ViewObject<EquPosScoreUpdateLineEntity_HI> equPosScoreUpdateLineDAO_HI;
    @Autowired
    private BaseViewObject<EquPosScoreUpdateLineEntity_HI_RO> equPosScoreUpdateLineDAO_HI_RO;
    @Autowired
    private EquPosSupplierInfoDAO_HI supplierInfoDao;

    public EquPosScoreUpdateLineServer() {
        super();
    }

    @Override
    public JSONObject findScoreUpdateLinePagination(JSONObject jsonObject, Integer pageIndex, Integer pageRows) {
        StringBuffer sqlBuffer = new StringBuffer(EquPosScoreUpdateLineEntity_HI_RO.QUERY_LINE_SQL);
        Map<String, Object> map = new HashMap<String, Object>(16);
        // 头id
        if(jsonObject.get("updateHeadId")!=null){
            SaafToolUtils.parperParam(jsonObject, "t.update_head_id", "updateHeadId", sqlBuffer, map, "=");
        }
        if(jsonObject.get("supplierName")!=null){
            SaafToolUtils.parperParam(jsonObject, "si.supplier_name", "supplierName", sqlBuffer, map, "like");
        }

//        jsonObject.put("updateHeadId", updateHeadId);
//        SaafToolUtils.parperParam(jsonObject, "t.update_head_id", "updateHeadId", sqlBuffer, map, "=");
        // 排序
        sqlBuffer.append(" order by t.last_update_date desc");
        Pagination<EquPosScoreUpdateLineEntity_HI_RO> pagination = equPosScoreUpdateLineDAO_HI_RO.findPagination(sqlBuffer, map, pageIndex, pageRows);
        return JSONObject.parseObject(JSONObject.toJSONString(pagination));
    }

    @Override
    public void saveScoreUpdateLineList(Integer updateHeadId, List<EquPosScoreUpdateLineEntity_HI> lineData, Integer userId) throws Exception {

        if (lineData.isEmpty()) {
            return;
        }

        for (int i = 0; i < lineData.size(); i++) {
            EquPosScoreUpdateLineEntity_HI entityHi = lineData.get(i);
            for (int n = i + 1; n < lineData.size(); n++) {
                EquPosScoreUpdateLineEntity_HI entity2 = lineData.get(n);
                String supplierNumber2 = entity2.getSupplierNumber();
                if (entityHi.getSupplierNumber() != null && supplierNumber2 != null && supplierNumber2.equals(entityHi.getSupplierNumber())){
                    throw new Exception("第"+(i+1)+"行的"+entityHi.getSupplierNumber()+"和第"+(n+1)+"行的重复了，请修改后再重新保存");
                }
            }
            // 校验是否有供应商档案号对应的供应商
            List<EquPosSupplierInfoEntity_HI> supplierInfoList = supplierInfoDao.findByProperty("supplierNumber", entityHi.getSupplierNumber());
            if(supplierInfoList == null || supplierInfoList.size()<=0){
                throw new Exception("第"+(i+1)+"行,输入的的供应商档案号在供应商信息表中不存在,请修改后再重新保存");
            }
//            if (org.apache.commons.lang.StringUtils.isNotEmpty(entityHi.getResult())) {
//                boolean b = entityHi.getResult().equals(ScoreUpdateEum.GREEN.getValue()) || entityHi.getResult().equals(ScoreUpdateEum.RED.getValue()) || entityHi.getResult().equals(ScoreUpdateEum.YELLOW.getValue());
//                if (!b) {
//                    throw new Exception("结果不是红灯黄灯或绿灯其中一种,请修改后再重新保存");
//                }
//            }
            if (entityHi.getScore().compareTo(BigDecimal.ZERO) < 0 || entityHi.getScore().compareTo(new BigDecimal("100")) > 0) {
                throw new Exception("分数需要是大于0.0小于等于100.0的数字,请修改后再重新保存");
            }
            if (entityHi.getUpdateLineId() != null) {
                // 将有id的对象从游离态变成持久态,直接触发更新操作
                EquPosScoreUpdateLineEntity_HI entityHiById = equPosScoreUpdateLineDAO_HI.getById(entityHi.getUpdateLineId());
                SaafBeanUtils.copyUnionProperties(entityHi, entityHiById);
                entityHi = entityHiById;
            }
//            switch (entityHi.getResult()) {
//                case "红灯":
//                    entityHi.setResult(ScoreUpdateEum.RED.getCode());
//                    break;
//                case "黄灯":
//                    entityHi.setResult(ScoreUpdateEum.YELLOW.getCode());
//                    break;
//                case "绿灯":
//                    entityHi.setResult(ScoreUpdateEum.GREEN.getCode());
//                    break;
//            }
            // 将无id的对象通过save方法从瞬时态变成游离态
            entityHi.setUpdateHeadId(updateHeadId);
            entityHi.setOperatorUserId(userId);
            saveOrUpdate(entityHi);
        }
    }

    @Override
    public int saveImportForScoreUpdate(String params, Integer userId) throws Exception {
         JSONObject jsonObject = JSONObject.parseObject(params);
        JSONArray jsonArray = jsonObject.getJSONArray("data");
        Integer updateHeadId = jsonObject.getJSONObject("info").getInteger("updateHeadId");
        if (updateHeadId == null){
            throw new IllegalArgumentException("请先保存后再导入数据");
        }
        JSONArray errList = new JSONArray();
        for (int i = 0; i < jsonArray.size(); i++) {
            int count = 0;
            JSONObject json = jsonArray.getJSONObject(i);
            JSONObject errJson = new JSONObject();
            String msgStr = "";
            try {
                // 匹配日期格式：yyyy-MM-dd
                String timeRegex = "(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|" +
                        "((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|" +
                        "((0[48]|[2468][048]|[3579][26])00))-02-29)$";

                Date beginValidDate = null;
                Date endValidDate = null;
                try{
                    beginValidDate = json.getDate("beginValidDate");
                    if (SaafToolUtils.isNullOrEmpty(json.getString("beginValidDate"))) {
                        msgStr += "有效期从不能为空-";
                    } else if (!Pattern.matches(timeRegex, json.getString("beginValidDate"))) {
                        msgStr += "有效期从格式不正确-";
                    } else {
                        json.put("beginValidDate", json.getString("beginValidDate"));
                    }
                }catch(Exception e){
                    msgStr += "有效期从格式不正确-";
                    count++;
                }
                try{
                    endValidDate = json.getDate("endValidDate");
                    if (SaafToolUtils.isNullOrEmpty(json.getString("endValidDate"))) {
                        msgStr += "有效期至不能为空-";
                    } else if (!Pattern.matches(timeRegex, json.getString("endValidDate"))) {
                        msgStr += "有效期至格式不正确-";
                    }  else {
                        json.put("endValidDate", json.getString("endValidDate"));
                    }
                }catch(Exception e){
                    msgStr += "有效期至格式不正确-";
                    count++;
                }

                if (json.getString("beginValidDate") != null && json.getString("endValidDate") != null && count == 0) {
                    if (json.getDate("beginValidDate").after(json.getDate("endValidDate"))) {
                        msgStr += "有效期从时间不能大于有效期至的时间-";
                    }
                }

                if (SaafToolUtils.isNullOrEmpty(json.getString("supplierName"))) {
                    msgStr += "供应商名称不能为空-";
                }
                String result = json.getString("result");
                BigDecimal score = new BigDecimal(0);

                try{
                    score = json.getBigDecimal("score");
                }catch(NumberFormatException ex){
                    msgStr += "分数只能为数字-";
                }

//                if (SaafToolUtils.isNullOrEmpty(json.getString("supplierNumber"))) {
//                    msgStr += "供应商档案号不能为空";
//                }
                // 校验导入数据是否有重复供应商名称
                String supplierName = json.getString("supplierName");
                for (int n = i + 1; n < jsonArray.size(); n++) {
                    JSONObject json2 = jsonArray.getJSONObject(n);
                    String supplierName2 = json2.getString("supplierName");
                    if (supplierName != null && supplierName2 != null && supplierName2.equals(supplierName)){
                        msgStr += "第"+(i+1)+"行的"+supplierName+"和第"+(n+1)+"行的重复了，请修改后重新导入-";
                    }
                }
                // 校验是否有供应商名称对应的供应商档案号是否一致
                List<EquPosSupplierInfoEntity_HI> supplierInfoList = supplierInfoDao.findByProperty("supplierName", json.getString("supplierName").trim());
                if (supplierInfoList == null || supplierInfoList.size() <= 0) {
                    msgStr += "第" + (i + 1) + "行,输入的的供应商名称在供应商信息表中不存在,修改后请重新导入-";
                } else {
                    json.put("supplierNumber", supplierInfoList.get(0).getSupplierNumber());
                }

                if (SaafToolUtils.isNullOrEmpty(score)) {
                    msgStr += "分数不能为空-";
                }
                if (score != null) {
                    if (score.compareTo(BigDecimal.ZERO) < 0 || score.compareTo(new BigDecimal("100")) > 0) {
                        msgStr += "分数需要是大于0.0小于等于100.0的数字-";
                    }
                }

                // 结果是红灯黄灯绿灯其中之一
                if (SaafToolUtils.isNullOrEmpty(result)) {
                    msgStr += "结果不能为空-";
                }
                if (org.apache.commons.lang.StringUtils.isNotEmpty(result)) {
                    boolean b = result.equals(ScoreUpdateEum.GREEN.getValue()) || result.equals(ScoreUpdateEum.RED.getValue()) || result.equals(ScoreUpdateEum.YELLOW.getValue());
                    if (!b) {
                        msgStr += "结果不是红灯黄灯或绿灯其中一种-";
                    }
                    switch (result) {
                        case "红灯":
                            json.put("result", ScoreUpdateEum.RED.getCode());
                            break;
                        case "黄灯":
                            json.put("result", ScoreUpdateEum.YELLOW.getCode());
                            break;
                        case "绿灯":
                            json.put("result", ScoreUpdateEum.GREEN.getCode());
                            break;
                    }
                }

                if (!"".equals(msgStr)) {
                    errJson.put("ROW_NUM", json.get("ROW_NUM"));
                    errJson.put("ERR_MESSAGE", msgStr.substring(0,msgStr.length() - 1));
                    errList.add(errJson);
                } else {
                    json.put("operatorUserId", userId);
                    json.put("updateHeadId", updateHeadId);
                    saveOrUpdate(json);
                }
            } catch (Exception e) {
                msgStr = "有异常,数据有误.";
                errJson.put("ROW_NUM", json.get("ROW_NUM"));
                errJson.put("ERR_MESSAGE", msgStr);
                errList.add(errJson);
                e.printStackTrace();
            }
        }
        if (!errList.isEmpty()) {
            throw new Exception(errList.toJSONString());
        }
        return jsonArray.size();
    }

    @Override
    public String deleteScoreUpdateLine(JSONObject jsonObject, int userId) {

        Integer updateLineId = jsonObject.getInteger("updateLineId");
        EquPosScoreUpdateLineEntity_HI entity = equPosScoreUpdateLineDAO_HI.getById(updateLineId);
        if (!ObjectUtils.isEmpty(entity)) {
            equPosScoreUpdateLineDAO_HI.delete(entity);
            return SToolUtils.convertResultJSONObj("S", "操作成功", 0, null).toString();
        } else {
            return SToolUtils.convertResultJSONObj("E", "根据删除id查询数据为空,操作失败", 0, null).toString();
        }
    }
}
