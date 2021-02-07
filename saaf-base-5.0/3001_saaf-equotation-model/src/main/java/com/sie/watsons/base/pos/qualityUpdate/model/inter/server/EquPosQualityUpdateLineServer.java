package com.sie.watsons.base.pos.qualityUpdate.model.inter.server;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.util.SaafBeanUtils;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.pos.category.model.dao.EquPosSupplierCategoryDAO_HI;
import com.sie.watsons.base.pos.category.model.entities.EquPosSupplierCategoryEntity_HI;
import com.sie.watsons.base.pos.enums.QualityUpdateEum;
import com.sie.watsons.base.pos.qualityUpdate.model.entities.EquPosQualityUpdateLineEntity_HI;
import com.sie.watsons.base.pos.qualityUpdate.model.entities.readonly.EquPosQualityUpdateLineEntity_HI_RO;
import com.sie.watsons.base.pos.qualityUpdate.model.inter.IEquPosQualityUpdateLine;
import com.sie.watsons.base.pos.supplierinfo.model.dao.EquPosSupplierInfoDAO_HI;
import com.sie.watsons.base.pos.supplierinfo.model.entities.EquPosSupplierInfoEntity_HI;
import com.sie.watsons.base.pos.supplierinfo.model.entities.readonly.EquPosSupplierProductCatEntity_HI_RO;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component("equPosQualityUpdateLindServer")
public class EquPosQualityUpdateLineServer extends BaseCommonServer<EquPosQualityUpdateLineEntity_HI> implements IEquPosQualityUpdateLine {
    private static final Logger LOGGER = LoggerFactory.getLogger(EquPosQualityUpdateLineServer.class);

    @Autowired
    private ViewObject<EquPosQualityUpdateLineEntity_HI> equPosQualityUpdateLineDAO_HI;
    @Autowired
    private BaseViewObject<EquPosQualityUpdateLineEntity_HI_RO> equPosQualityUpdateLineDAO_HI_RO;
    @Autowired
    private EquPosSupplierInfoDAO_HI supplierInfoDao;
    @Autowired
    private EquPosSupplierCategoryDAO_HI supplierCategoryDao;
    @Autowired
    private BaseViewObject<EquPosSupplierProductCatEntity_HI_RO> supplierProductCatDaoRo;

    public EquPosQualityUpdateLineServer() {
        super();
    }

    @Override
    public JSONObject findQualityUpdateLinePagination(JSONObject jsonObject, Integer pageIndex, Integer pageRows) {
        StringBuffer sqlBuffer = new StringBuffer(EquPosQualityUpdateLineEntity_HI_RO.QUERY_LINE_SQL);
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
        Pagination<EquPosQualityUpdateLineEntity_HI_RO> pagination = equPosQualityUpdateLineDAO_HI_RO.findPagination(sqlBuffer, map, pageIndex, pageRows);
        return JSONObject.parseObject(JSONObject.toJSONString(pagination));
    }

    @Override
    public void saveQualityUpdateLineList(Integer updateHeadId, List<EquPosQualityUpdateLineEntity_HI> lineData, Integer userId) throws Exception {

        if (lineData.isEmpty()) {
            return;
        }

        for (int i = 0; i < lineData.size(); i++) {
            // 校验导入数据是否有重复供应商档案号和品类组合
            EquPosQualityUpdateLineEntity_HI entityHi = lineData.get(i);
            for (int n = i + 1; n < lineData.size(); n++) {
                EquPosQualityUpdateLineEntity_HI entity2 = lineData.get(n);
                boolean b = entityHi.getSupplierNumber() != null && entity2.getSupplierNumber() != null &&
                        entityHi.getCategory() != null && entity2.getCategory() != null &&
                        entity2.getSupplierNumber().equals(entityHi.getSupplierNumber()) &&
                        entity2.getCategory().equals(entityHi.getCategory());
                if (b){
                    throw new Exception("第"+(i+1)+"行的供应商编号:"+entityHi.getSupplierNumber()+"和品类："+entityHi.getCategory()+"与第"+(n+1)+"行的重复了，请修改后重新保存");
                }
            }
            // 校验是否有供应商档案号对应的供应商
            List<EquPosSupplierInfoEntity_HI> supplierInfoList = supplierInfoDao.findByProperty("supplierNumber", entityHi.getSupplierNumber());
            if(supplierInfoList == null || supplierInfoList.size()<=0){
                throw new Exception("第"+(i+1)+"行,输入的的供应商档案号在供应商信息表中不存在,修改后请重新保存");
            }
            // 校验供应商品类表里是否有该品类
            List<EquPosSupplierCategoryEntity_HI> categoryList = supplierCategoryDao.findByProperty("categoryGroup", entityHi.getCategory());
            if(categoryList == null || categoryList.size()<=0){
                throw new Exception("第"+(i+1)+"行,输入的分类在供应商品分类表中不存在,修改后请重新保存");
            }
            Integer categoryMaintainId = categoryList.get(0).getCategoryMaintainId();
            entityHi.setCategoryMaintainId(categoryMaintainId);
            // 校验供应商档案中可合作品类是否包含有该品类
            StringBuffer sb = new StringBuffer(EquPosSupplierProductCatEntity_HI_RO.QUERY_SQL);
            sb.append(" and src.supplier_id = "+supplierInfoList.get(0).getSupplierId());
            List<EquPosSupplierProductCatEntity_HI_RO> productCatList = supplierProductCatDaoRo.findList(sb,Maps.newHashMap());
            if(CollectionUtils.isEmpty(productCatList)){
                throw new Exception("第"+(i+1)+"行,输入的的供应商档案号没有可合作品类可以选择,请修改后重新保存");
            }
            List<String> categoryGroupList = productCatList.stream().map(e -> e.getCategoryName()).collect(Collectors.toList());
            if(!categoryGroupList.contains(entityHi.getCategory())){
                throw new Exception("第"+(i+1)+"行,输入的品类不是供应商的可合作品类,请修改后重新保存");
            }
            // 校验该供应商和该品类在要导入的表中是否存在，如果存在则抛异常
//            HashMap<String, Object> paraMap = new HashMap<>(4);
//            paraMap.put("category", entityHi.getCategory());
//            paraMap.put("supplierNumber", entityHi.getSupplierNumber());
//            List<EquPosQualityUpdateLineEntity_HI> entityHiList = equPosQualityUpdateLineDAO_HI.findByProperty(paraMap);
//            if(entityHiList != null && entityHiList.size()>0){
//                throw new Exception("第"+(i+1)+"行,供应商档案号/品类在表中已存在，请处理！");
//            }
            if (entityHi.getScore() != null) {
                if (entityHi.getScore().compareTo(BigDecimal.ZERO) < 0 || entityHi.getScore().compareTo(new BigDecimal("100")) > 0) {
                    throw new Exception("分数需要是大于等于0.0小于等于100.0的数字,请修改后重新保存");
                }
            }
            if (entityHi.getUpdateLineId() != null) {
                // 将有id的对象从游离态变成持久态,直接触发更新操作
                EquPosQualityUpdateLineEntity_HI entityHiById = equPosQualityUpdateLineDAO_HI.getById(entityHi.getUpdateLineId());
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
    public int saveImportForQualityUpdate(String params,Integer userId) throws Exception {
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
                BigDecimal score = json.getBigDecimal("score");
                String result = json.getString("result");

//                if (SaafToolUtils.isNullOrEmpty(json.getString("supplierNumber"))) {
//                    msgStr += "供应商档案号不能为空";
//                }
                if (SaafToolUtils.isNullOrEmpty(json.getString("category"))) {
                    msgStr += "品类不能为空";
                }
                if (SaafToolUtils.isNullOrEmpty(score)) {
                    msgStr += "分数不能为空";
                }
                if (score != null) {
                    if (score.compareTo(BigDecimal.ZERO) < 0 || score.compareTo(new BigDecimal("100")) > 0) {
                        msgStr += "分数需要是大于等于0.0小于等于100.0的数字";
                    }
                }
                if (SaafToolUtils.isNullOrEmpty(json.getString("result"))) {
                    msgStr += "结果不能为空";
                }
                if (org.apache.commons.lang.StringUtils.isNotEmpty(result)) {
                    boolean b = result.equals(QualityUpdateEum.PASS.getValue()) || result.equals(QualityUpdateEum.NO_PASS.getValue()) || result.equals(QualityUpdateEum.NO_PASS_AGAIN.getValue());
                    if (!b) {
                        msgStr += "结果不是合格,不合格且有重审机会和不合格且无重审机会中其中一种";
                    }
                    switch (result) {
                        case "合格":
                            json.put("result", QualityUpdateEum.PASS.getCode());
                            break;
                        case "不合格且无重审机会":
                            json.put("result", QualityUpdateEum.NO_PASS.getCode());
                            break;
                        case "不合格且有重审机会":
                            json.put("result", QualityUpdateEum.NO_PASS_AGAIN.getCode());
                            break;
                    }
                }
                // 校验导入数据是否有重复供应商名称和品类组合
                String supplierName = json.getString("supplierName");
                String category = json.getString("category");
                for (int n = i + 1; n < jsonArray.size(); n++) {
                    JSONObject json2 = jsonArray.getJSONObject(n);
                    String supplierName2 = json2.getString("supplierName");
                    String category2 = json2.getString("category");
                    boolean b = supplierName != null && supplierName2 != null &&
                            category != null && category2 != null &&
                            supplierName2.equals(supplierName) &&
                            category2.equals(category);
                    if (b){
                        msgStr += "第"+(i+1)+"行的供应商名称:"+supplierName+"和品类："+category+"与第"+(n+1)+"行的重复了，请修改后重新导入";
                    }
                }
                // 校验供应商名称、品类是否有错误(与系统现有信息进行校验)，若存在则不允许导入，并且给出提示“**行供应商档案号/品类有误，请处理！
                // 校验是否有供应商名称对应的供应商
                List<EquPosSupplierInfoEntity_HI> supplierInfoList = supplierInfoDao.findByProperty("supplierName", json.getString("supplierName").trim());
                if(supplierInfoList == null || supplierInfoList.size()<=0){
                    msgStr += "第"+(i+1)+"行,输入的的供应商名称在供应商信息表中不存在,修改后请重新导入";
                } else {
                    json.put("supplierNumber", supplierInfoList.get(0).getSupplierNumber());
                    // 校验是否有该品类
                    List<EquPosSupplierCategoryEntity_HI> categoryList = supplierCategoryDao.findByProperty("categoryGroup", json.getString("category"));
                    if(categoryList == null || categoryList.size()<=0){
                        msgStr += "第"+(i+1)+"行,输入的分类在供应商品分类表中不存在,修改后请重新导入";
                    }else{
                        Integer categoryMaintainId = categoryList.get(0).getCategoryMaintainId();
                        json.put("categoryMaintainId", categoryMaintainId);
                    }
                    // 校验供应商档案中可合作品类是否包含有该品类
                    StringBuffer sb = new StringBuffer(EquPosSupplierProductCatEntity_HI_RO.QUERY_SQL);
                    sb.append(" and src.supplier_id = "+supplierInfoList.get(0).getSupplierId());
                    List<EquPosSupplierProductCatEntity_HI_RO> productCatList = supplierProductCatDaoRo.findList(sb,Maps.newHashMap());
                    if(CollectionUtils.isEmpty(productCatList)){
                        msgStr += "第"+(i+1)+"行,输入的的供应商下没有可合作品类可以选择,请修改后重新导入";
                    }
                    List<String> categoryGroupList = productCatList.stream().map(e -> e.getCategoryName()).collect(Collectors.toList());
                    if(!categoryGroupList.contains(json.getString("category"))){
                        msgStr += "第"+(i+1)+"行,输入的品类不是供应商的可合作品类,请修改后重新导入";
                    }
                }
                // 校验该供应商和该品类在要导入的表中是否存在，如果存在则抛异常
//                HashMap<String, Object> paraMap = new HashMap<>(4);
//                paraMap.put("category", json.getString("category"));
//                paraMap.put("supplierNumber", json.getString("supplierNumber"));
//                List<EquPosQualityUpdateLineEntity_HI> entityHiList = equPosQualityUpdateLineDAO_HI.findByProperty(paraMap);
//                if(entityHiList != null && entityHiList.size()>0){
//                    msgStr += "第"+(i+1)+"行,供应商档案号/品类在表中已存在，请处理！";
//                }

                if (!"".equals(msgStr)) {
                    errJson.put("ROW_NUM", json.get("ROW_NUM"));
                    errJson.put("ERR_MESSAGE", msgStr);
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
    public String deleteQualityUpdateLine(JSONObject jsonObject, int userId) {

        Integer updateLineId = jsonObject.getInteger("updateLineId");
        EquPosQualityUpdateLineEntity_HI entity = equPosQualityUpdateLineDAO_HI.getById(updateLineId);
        if (!ObjectUtils.isEmpty(entity)) {
            equPosQualityUpdateLineDAO_HI.delete(entity);
            return SToolUtils.convertResultJSONObj("S", "操作成功", 0, null).toString();
        } else {
            return SToolUtils.convertResultJSONObj("E", "根据删除id查询数据为空,操作失败", 0, null).toString();
        }
    }
}
