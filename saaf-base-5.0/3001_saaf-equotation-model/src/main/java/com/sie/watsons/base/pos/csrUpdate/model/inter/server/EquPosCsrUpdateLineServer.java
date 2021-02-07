package com.sie.watsons.base.pos.csrUpdate.model.inter.server;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.util.SaafBeanUtils;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.pos.csrUpdate.model.entities.EquPosCsrUpdateLineEntity_HI;
import com.sie.watsons.base.pos.csrUpdate.model.entities.readonly.EquPosCsrUpdateLineEntity_HI_RO;
import com.sie.watsons.base.pos.csrUpdate.model.inter.IEquPosCsrUpdateLine;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Component("equPosCsrUpdateLineServer")
public class EquPosCsrUpdateLineServer extends BaseCommonServer<EquPosCsrUpdateLineEntity_HI> implements IEquPosCsrUpdateLine {
    private static final Logger LOGGER = LoggerFactory.getLogger(EquPosCsrUpdateLineServer.class);

    @Autowired
    private ViewObject<EquPosCsrUpdateLineEntity_HI> equPosCsrUpdateLineDAO_HI;
    @Autowired
    private BaseViewObject<EquPosCsrUpdateLineEntity_HI_RO> equPosCsrUpdateLineDAO_HI_RO;
    @Autowired
    private EquPosSupplierInfoDAO_HI supplierInfoDao;

    public EquPosCsrUpdateLineServer() {
        super();
    }

    @Override
    public JSONObject findCsrUpdateLinePagination(JSONObject jsonObject, Integer pageIndex, Integer pageRows) {
        StringBuffer sqlBuffer = new StringBuffer(EquPosCsrUpdateLineEntity_HI_RO.QUERY_LINE_SQL);
        Map<String, Object> map = new HashMap<String, Object>(16);
        // 头id
        if(jsonObject.get("updateHeadId")!=null){
            SaafToolUtils.parperParam(jsonObject, "t.update_head_id", "updateHeadId", sqlBuffer, map, "=");
        }
        if(jsonObject.get("supplierName")!=null){
            SaafToolUtils.parperParam(jsonObject, "si.supplier_name", "supplierName", sqlBuffer, map, "like");
        }
        // 排序
        sqlBuffer.append(" order by t.last_update_date desc");
        Pagination<EquPosCsrUpdateLineEntity_HI_RO> pagination = equPosCsrUpdateLineDAO_HI_RO.findPagination(sqlBuffer, map, pageIndex, pageRows);
        return JSONObject.parseObject(JSONObject.toJSONString(pagination));
    }

    @Override
    public void saveCsrUpdateLineList(Integer updateHeadId, List<EquPosCsrUpdateLineEntity_HI> lineData, Integer userId) throws Exception {
        if (lineData.isEmpty()) {
            return;
        }
        for (int i = 0; i < lineData.size(); i++) {
            EquPosCsrUpdateLineEntity_HI entityHi = lineData.get(i);
            for (int n = i + 1; n < lineData.size(); n++) {
                EquPosCsrUpdateLineEntity_HI entity2 = lineData.get(n);
                String supplierNumber2 = entity2.getSupplierNumber();
                if (entityHi.getSupplierNumber() != null && supplierNumber2 != null && supplierNumber2.equals(entityHi.getSupplierNumber())) {
                    throw new Exception("第" + (i + 1) + "行的" + entityHi.getSupplierNumber() + "和第" + (n + 1) + "行的重复了，请修改后重新保存");
                }
            }
            // 校验是否有供应商档案号对应的供应商
            List<EquPosSupplierInfoEntity_HI> supplierInfoList = supplierInfoDao.findByProperty("supplierNumber", entityHi.getSupplierNumber());
            if (supplierInfoList == null || supplierInfoList.size() <= 0) {
                throw new Exception("第" + (i + 1) + "行,输入的的供应商档案号在供应商信息表中不存在,修改后请重新保存");
            }
//            if (entityHi.getScore().compareTo(BigDecimal.ZERO) < 0 || entityHi.getScore().compareTo(new BigDecimal("100")) > 0) {
//                throw new Exception("分数需要是大于等于0.0小于等于100.0的数字,请修改后再重新保存");
//            }
            if (entityHi.getUpdateLineId() != null) {
                // 将有id的对象从游离态变成持久态,直接触发更新操作
                EquPosCsrUpdateLineEntity_HI entityHiById = getById(entityHi.getUpdateLineId());
                SaafBeanUtils.copyUnionProperties(entityHi, entityHiById);
                entityHi = entityHiById;
            }
            // 将无id的对象通过save方法从瞬时态变成游离态
            entityHi.setUpdateHeadId(updateHeadId);
            entityHi.setOperatorUserId(userId);
            saveOrUpdate(entityHi);
        }
    }

    @Override
    public int saveImportForCsrUpdate(String params, Integer userId) throws Exception {
        JSONObject jsonObject = JSONObject.parseObject(params);
        JSONArray jsonArray = jsonObject.getJSONArray("data");
        Integer updateHeadId = jsonObject.getJSONObject("info").getInteger("updateHeadId");
        if (updateHeadId == null){
            throw new IllegalArgumentException("请先保存后再导入数据");
        }
        JSONArray errList = new JSONArray();
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject json = jsonArray.getJSONObject(i);
            JSONObject errJson = new JSONObject();
            String msgStr = "";
            try {
                // 匹配日期格式：yyyy-MM-dd
                String timeRegex = "(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|" +
                        "((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|" +
                        "((0[48]|[2468][048]|[3579][26])00))-02-29)$";

                if (SaafToolUtils.isNullOrEmpty(json.getString("beginValidDate"))) {
                    msgStr += "有效期从不能为空";
                } else if (!Pattern.matches(timeRegex, json.getString("beginValidDate"))) {
                    msgStr += "有效期从格式不正确";
                } else {
                    json.put("beginValidDate", json.getString("beginValidDate"));
                }
                if (SaafToolUtils.isNullOrEmpty(json.getString("endValidDate"))) {
                    msgStr += "有效期至不能为空";
                } else if (!Pattern.matches(timeRegex, json.getString("endValidDate"))) {
                    msgStr += "有效期至格式不正确";
                }  else {
                    json.put("endValidDate", json.getString("endValidDate"));
                }
                if (json.getString("beginValidDate") != null && json.getString("endValidDate") != null) {
                    if (json.getDate("beginValidDate").after(json.getDate("endValidDate"))) {
                        msgStr += "有效期从时间不能大于有效期至的时间";
                    }
                }
                if (SaafToolUtils.isNullOrEmpty(json.getString("supplierName"))) {
                    msgStr += "供应商名称不能为空";
                }
                String score = json.getString("score");

//                if (SaafToolUtils.isNullOrEmpty(json.getString("supplierNumber"))) {
//                    msgStr += "供应商档案号不能为空";
//                }
                if (SaafToolUtils.isNullOrEmpty(score)) {
                    msgStr += "分数不能为空";
                }
                if (score != null) {
                    switch (score) {
                        case "Excellent":
                            json.put("score", "10");
                            break;
                        case "Low risk":
                            json.put("score", "20");
                            break;
                        case "Medium risk":
                            json.put("score", "30");
                            break;
                        case "High risk":
                            json.put("score", "40");
                            break;
                        case "Reject":
                            json.put("score", "50");
                            break;
                        default:
                            msgStr += "分数不是Excellent、Low risk、Medium risk、High risk、Reject的其中一种，请修改后重新导入";
                    }
                }
                if (SaafToolUtils.isNullOrEmpty(json.getString("result"))) {
                    msgStr += "结果不能为空";
                }
                // 校验导入数据是否有重复供应商名称
                String supplierName = json.getString("supplierName");
                for (int n = i + 1; n < jsonArray.size(); n++) {
                    JSONObject json2 = jsonArray.getJSONObject(n);
                    String supplierName2 = json2.getString("supplierName");
                    if (supplierName != null && supplierName2 != null && supplierName2.equals(supplierName)){
                        msgStr += "第"+(i+1)+"行的"+supplierName+"和第"+(n+1)+"行的重复了，请修改后重新导入";
                    }
                }

                // 校验是否有供应商名称对应的供应商档案号是否一致
                List<EquPosSupplierInfoEntity_HI> supplierInfoList = supplierInfoDao.findByProperty("supplierName", json.getString("supplierName").trim());
                if (supplierInfoList == null || supplierInfoList.size() <= 0) {
                    msgStr += "第" + (i + 1) + "行,输入的的供应商名称在供应商信息表中不存在,修改后请重新导入";
                } else {
                    json.put("supplierNumber", supplierInfoList.get(0).getSupplierNumber());
                }
                if (!"".equals(msgStr)) {
                    errJson.put("ROW_NUM", json.get("ROW_NUM"));
                    errJson.put("ERR_MESSAGE", msgStr);
                    errList.add(errJson);
                } else {
                    json.put("operatorUserId", userId);
                    json.put("updateHeadId", updateHeadId);
                    super.saveOrUpdate(json);
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
    public String deleteCsrUpdateLine(JSONObject jsonObject, int userId) {

        Integer updateLineId = jsonObject.getInteger("updateLineId");
        EquPosCsrUpdateLineEntity_HI entity = equPosCsrUpdateLineDAO_HI.getById(updateLineId);
        if (!ObjectUtils.isEmpty(entity)) {
            equPosCsrUpdateLineDAO_HI.delete(entity);
            return SToolUtils.convertResultJSONObj("S", "操作成功", 0, null).toString();
        } else {
            return SToolUtils.convertResultJSONObj("E", "根据删除id查询数据为空,操作失败", 0, null).toString();
        }
    }

}
