package com.sie.watsons.base.productEco.model.inter.server;

import com.alibaba.fastjson.JSONObject;

import java.rmi.ServerException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import com.sie.saaf.common.services.GenerateCodeService;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.product.model.dao.PlmProductHeadDAO_HI;
import com.sie.watsons.base.product.model.entities.*;
import com.sie.watsons.base.product.model.entities.readonly.PlmProductHeadEntity_HI_RO;
import com.sie.watsons.base.product.model.entities.readonly.PlmProductSalePropertiesEntity_HI_RO;
import com.sie.watsons.base.product.model.inter.IPlmDataAclHeader;
import com.sie.watsons.base.product.model.inter.IPlmDataAclLine;
import com.sie.watsons.base.productEco.config.ProductEcoEnum;
import com.sie.watsons.base.productEco.model.dao.PlmProductHeadEcoDAO_HI;
import com.sie.watsons.base.productEco.model.entities.*;
import com.sie.watsons.base.productEco.model.entities.readonly.*;
import com.sie.watsons.base.productEco.utils.EcoUtils;
import com.sie.watsons.base.redisUtil.InvalidEntityUtil.VtorProfile;
import com.sie.watsons.base.redisUtil.InvalidEntityUtil.VtorUtil;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import jodd.util.ObjectUtil;

import org.apache.commons.collections4.CollectionUtils;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.productEco.model.inter.IPlmProductHeadEco;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import org.springframework.util.ObjectUtils;

@Component("plmProductHeadEcoServer")
public class PlmProductHeadEcoServer extends BaseCommonServer<PlmProductHeadEcoEntity_HI> implements IPlmProductHeadEco{
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmProductHeadEcoServer.class);

	@Autowired
	private ViewObject<PlmProductHeadEcoEntity_HI> plmProductHeadEcoDAO_HI;
	@Autowired
	private ViewObject<PlmProductHeadEntity_HI> plmProductHeadDAO_HI;
    @Autowired
    private PlmProductHeadEcoDAO_HI headDAO_HI;
    @Autowired
    private ViewObject<PlmProductDrugEntity_HI> plmProductDrugDAO_HI;
    @Autowired
    private ViewObject<PlmProductDrugEcoEntity_HI> plmProductDrugEcoDAO_HI;
    @Autowired
    private ViewObject<PlmProductSupplierInfoEntity_HI> plmProductSupplierInfoDAO_HI;
    @Autowired
    private ViewObject<PlmProductSupplierInfoEcoEntity_HI> plmProductSupplierInfoEcoDAO_HI;

    @Autowired
    private ViewObject<PlmProductPriceEcoEntity_HI> plmProductPriceEcoDAO_HI;
    @Autowired
    private ViewObject<PlmProductPriceEntity_HI> plmProductPriceDAO_HI;

    @Autowired
    private ViewObject<PlmProductQaFileEcoEntity_HI> plmProductQaFileEcoDAO_HI;
    @Autowired
    private ViewObject<PlmProductQaFileEntity_HI> plmProductQaFileDAO_HI;

    @Autowired
    private ViewObject<PlmProductPicfileTableEcoEntity_HI> plmProductPicfileTableEcoDAO_HI;
    @Autowired
    private ViewObject<PlmProductMedicalinfoEcoEntity_HI> plmProductMedicalinfoEcoEntity_HI;
    @Autowired
    private ViewObject<PlmProductPicfileTableEntity_HI> plmProductPicfileTableDAO_HI;
    @Autowired
    private BaseViewObject<PlmProductHeadEcoEntity_HI_RO> plmProductHeadEcoDAO_HI_RO;
    @Autowired
    private GenerateCodeService generateCodeService;
    @Autowired
    private BaseViewObject<PlmProductSeqEcoEntity_HI_RO> plmProductSeqEcoEntity_HI_RO;
    
    @Autowired
	private ViewObject<PlmProductModifyCheckEntity_HI> plmProductModifyCheckDao_HI;

    @Autowired
    private IPlmDataAclHeader plmDataAclHeaderServer;// 权限表
    @Autowired
    private IPlmDataAclLine IPlmDataAclLineServer; // 权限行表

	public PlmProductHeadEcoServer() {
		super();
	}

	private  static final String[] status = {ProductEcoEnum.UPD_MAKING.getValues(),ProductEcoEnum.UPD_APPROVING.getValues(),ProductEcoEnum.UPD_RMS_CONFIG.getValues() };

	@Override
	public String addModifyProductHead(JSONObject param) throws Exception {
		//1 检验是否数据
		Integer productId = param.getInteger("productHeadId");
		Integer userId =  param.getInteger("varUserId");
        Integer orgId =  param.getInteger("varOrgId");
		//2 查询是否在审批的头ID
		Map<String,Object> findMap = new HashMap<>();
		findMap.put("productHeadId",productId);
		findMap.put("createdBy",userId);
		List<PlmProductHeadEcoEntity_HI> productEcos =  plmProductHeadEcoDAO_HI.findByProperty(findMap);
		if(CollectionUtils.isNotEmpty(productEcos)){
			//3 存在不为的数据 , 提示错误
			throw new Exception("当前有1张单据存在!");
		}
		//todo: 复制单据
		//1 复制主表单
		PlmProductHeadEntity_HI pHead = plmProductHeadDAO_HI.getById(productId);
        String ecoNo = generateCodeService.getEcoPlanCode();
		PlmProductHeadEcoEntity_HI newProductHeadEco = new PlmProductHeadEcoEntity_HI();
		BeanUtils.copyProperties(pHead,newProductHeadEco,"versionNum","createdBy");
		newProductHeadEco.setEcoStatus(ProductEcoEnum.UPD_MAKING.getValues());
		newProductHeadEco.setCreatedBy(userId);
        newProductHeadEco.setCreationDate(new Date());
        newProductHeadEco.setLastUpdateDate(new Date());
        newProductHeadEco.setLastUpdatedBy(userId);
        newProductHeadEco.setLastUpdateLogin(userId);
        newProductHeadEco.setEcoNo(ecoNo);
        newProductHeadEco.setEcoDeptId(orgId);
        PlmProductHeadEcoEntity_HI headEco = plmProductHeadEcoDAO_HI.insert(newProductHeadEco);
        //todo ： 查询出新增的信息
//        Map<String,Object> map = new HashMap<>();
//        map.put("createdBy",userId);
//        map.put("productHeadId",productId);
//        map.put("ecoStatus",ProductEcoEnum.UPD_MAKING.getValues());
//        List<PlmProductHeadEcoEntity_HI> productHeadEcos =  plmProductHeadEcoDAO_HI.findByProperty(map);
        Integer ecoId = headEco.getEcoId();

        //todo : 新增行表,且判断是否是药品
        if("1".equals(pHead.getOtherInfo())){
            copyDrugsEco(productId,userId,ecoId);
        }
        //todo: 供应商表
        copySupplierEco(productId,userId,ecoId);
        //todo: 零售价表
        copyPriceEco(productId,userId,ecoId);
        //todo：QA 文件表
        copyQaFileEco(productId,userId,ecoId);
        //todo：图片 picFile 文件表
        copypicFileEco(productId,userId,ecoId);
		return "S";
	}

    @Override
    public Integer getEcoIdByParams(JSONObject param) throws Exception {
        Map<String,Object> map = new HashMap<>();
        Integer productId = param.getInteger("productHeadId");
        Integer userId =  param.getInteger("varUserId");
        map.put("productHeadId",productId);
        map.put("createdBy",userId);
        List<PlmProductHeadEcoEntity_HI> productEcos = plmProductHeadEcoDAO_HI.findByProperty(map);
        if(CollectionUtils.isEmpty(productEcos)){
            //3 存在不为的数据,提示错误
            throw new Exception("当前用户没有生成过修改单");
        }
        //返回最近1条的记录
        List<PlmProductHeadEcoEntity_HI> newSortDescEcos = productEcos.stream().sorted(Comparator.comparing(PlmProductHeadEcoEntity_HI::getEcoId).reversed()).collect(Collectors.toList());
        return newSortDescEcos.get(0).getEcoId();
    }

    @Override
    public PlmProductParamEcoEntity_HI_RO findEcoInfoByEcoId(Integer ecoId) {
	    //查询出头对象
        PlmProductParamEcoEntity_HI_RO ecoInfoR = new PlmProductParamEcoEntity_HI_RO();
        StringBuffer sql = new StringBuffer(
                PlmProductHeadEcoEntity_HI_RO.QUERY_HEAD);
        sql.append(" and PRODUCT.eco_id=:ecoId ");
        Map<String,Object> map = new HashMap<>();
        map.put("ecoId",ecoId);
        List<PlmProductHeadEcoEntity_HI_RO> lists =plmProductHeadEcoDAO_HI_RO.findList(
                sql, map);
        if (lists.get(0) != null) {
         ecoInfoR.setHeadInfo(lists.get(0));
        }


        //PlmProductHeadEcoEntity_HI ecoInfo = plmProductHeadEcoDAO_HI.getById(ecoId);
        //ecoInfoR.setHeadInfo(ecoInfo);
        //根据ecoId查询供应商

        List<PlmProductDrugEcoEntity_HI> drugEcos = plmProductDrugEcoDAO_HI.findByProperty(map);
        List<PlmProductSupplierInfoEcoEntity_HI> supplierEcos = plmProductSupplierInfoEcoDAO_HI.findByProperty(map);
        List<PlmProductPriceEcoEntity_HI> priceEcos = plmProductPriceEcoDAO_HI.findByProperty(map);
        List<PlmProductQaFileEcoEntity_HI> qaFileEcos = plmProductQaFileEcoDAO_HI.findByProperty(map);
        List<PlmProductPicfileTableEcoEntity_HI> picfileEcos = plmProductPicfileTableEcoDAO_HI.findByProperty(map);
        List<PlmProductMedicalinfoEcoEntity_HI> medicalinfoEcos = plmProductMedicalinfoEcoEntity_HI.findByProperty(map);
        if(CollectionUtils.isNotEmpty(drugEcos)){
            PlmProductDrugEcoEntity_HI_RO drugEcoRo = new PlmProductDrugEcoEntity_HI_RO();
            PlmProductDrugEcoEntity_HI drug = drugEcos.get(0);
            BeanUtils.copyProperties(drug,drugEcoRo);
            ecoInfoR.setDrugEcos(drugEcoRo);
        }
        if(CollectionUtils.isNotEmpty(supplierEcos)){
            List<PlmProductSupplierInfoEcoEntity_HI_RO> supplierRoEcos = new ArrayList<>();
            supplierEcos.forEach(supplierEco->{
                PlmProductSupplierInfoEcoEntity_HI_RO supplierEcoRo= new PlmProductSupplierInfoEcoEntity_HI_RO();
                BeanUtils.copyProperties(supplierEco,supplierEcoRo);
                supplierRoEcos.add(supplierEcoRo);
            });
            ecoInfoR.setSupplierEcos(supplierRoEcos);
        }
        if(CollectionUtils.isNotEmpty(priceEcos)){
            List<PlmProductPriceEcoEntity_HI_RO> priceEcoRos = new ArrayList<>();
            priceEcos.forEach(priceEco->{
                PlmProductPriceEcoEntity_HI_RO priceEcoRo= new PlmProductPriceEcoEntity_HI_RO();
                BeanUtils.copyProperties(priceEco,priceEcoRo);
                priceEcoRos.add(priceEcoRo);
            });
            ecoInfoR.setPriceEcos(priceEcoRos);
        }
        if(CollectionUtils.isNotEmpty(qaFileEcos)){
            List<PlmProductQaFileEcoEntity_HI_RO> qaFileEcoRos = new ArrayList<>();
            qaFileEcos.forEach(qaFileEco->{
                PlmProductQaFileEcoEntity_HI_RO qaFileEcoRo= new PlmProductQaFileEcoEntity_HI_RO();
                BeanUtils.copyProperties(qaFileEco,qaFileEcoRo);
                qaFileEcoRos.add(qaFileEcoRo);
            });
            ecoInfoR.setQaFileEcos(qaFileEcoRos);
        }
        if(CollectionUtils.isNotEmpty(picfileEcos)){
            List<PlmProductPicfileTableEcoEntity_HI_RO> picfileEcoRos = new ArrayList<>();
            picfileEcos.forEach(picfileEco->{
                PlmProductPicfileTableEcoEntity_HI_RO picfileEcoRo= new PlmProductPicfileTableEcoEntity_HI_RO();
                BeanUtils.copyProperties(picfileEco,picfileEcoRo);
                picfileEcoRos.add(picfileEcoRo);
            });
            ecoInfoR.setPicfileTableEcos(picfileEcoRos);
        }
        if(CollectionUtils.isNotEmpty(medicalinfoEcos)) {
            PlmProductMedicalinfoEcoEntity_HI medicalinfo = medicalinfoEcos.get(0);
            PlmProductMedicalinfoEcoEntity_HI_RO medicalinfoRoEco = new PlmProductMedicalinfoEcoEntity_HI_RO();
            BeanUtils.copyProperties(medicalinfo, medicalinfoRoEco);
            ecoInfoR.setMedicalinfoEcos(medicalinfoRoEco);
        }
        return ecoInfoR;
    }

    @Override
    public String updateModifyProductHead(JSONObject data) throws Exception {

        Integer productId = data.getInteger("productHeadId");
        Integer userId =  data.getInteger("varUserId");
        Integer orgId =  data.getInteger("varOrgId");
        String employeeNumber =  data.getString("varEmployeeNumber");
        String varUserFullName =  data.getString("varUserFullName");
	    //1把date 转成对象？ varEmployeeNumber
        PlmProductParamEcoEntity_HI_RO ecoInfoR = JSONObject.parseObject(data.toJSONString(),PlmProductParamEcoEntity_HI_RO.class);
        PlmProductHeadEcoEntity_HI_RO ecoInfoRO = ecoInfoR.getHeadInfo();


        PlmProductHeadEcoEntity_HI ecoInfo = new PlmProductHeadEcoEntity_HI();
        BeanUtils.copyProperties(ecoInfoRO,ecoInfo);

        ecoInfo =  saveOrUpdateProductHeadEco(ecoInfo,userId,orgId,employeeNumber,varUserFullName);

//        plmProductHeadEcoDAO_HI.saveOrUpdate(ecoInfo);
        if(!ObjectUtils.isEmpty(ecoInfoR.getDrugEcos())&& ! EcoUtils.allfieldIsNUll(ecoInfoR.getDrugEcos())){
                PlmProductDrugEcoEntity_HI_RO drugEco =ecoInfoR.getDrugEcos();
                VtorUtil.validate(drugEco, new String[]{VtorProfile.INSERT.getName()});
                PlmProductDrugEcoEntity_HI drug = new PlmProductDrugEcoEntity_HI();
                BeanUtils.copyProperties(drugEco,drug);
                if(ObjectUtils.isEmpty(drug.getEcoId())){
                    drug.setEcoId(ecoInfo.getEcoId());
                }
                plmProductDrugEcoDAO_HI.saveOrUpdate(drug);

        }
        if(CollectionUtils.isNotEmpty(ecoInfoR.getSupplierEcos())){
            for (PlmProductSupplierInfoEcoEntity_HI_RO supplierRoEco : ecoInfoR.getSupplierEcos()) {
                VtorUtil.validate(supplierRoEco, new String[]{VtorProfile.INSERT.getName()});
                PlmProductSupplierInfoEcoEntity_HI supplierEco = new PlmProductSupplierInfoEcoEntity_HI();
                BeanUtils.copyProperties(supplierRoEco,supplierEco);
                if(ObjectUtils.isEmpty(supplierEco.getEcoId())){
                    supplierEco.setEcoId(ecoInfo.getEcoId());
                }
                if("ADD".equals(supplierEco.getAcdType()) && ObjectUtils.isEmpty(supplierEco.getId()) ){
                    //新增的时候获取序列号主键ID
                    Integer  Id = getAddLineBaseId(PlmProductSeqEcoEntity_HI_RO.QUERY_SUPPLIER);
                    supplierEco.setId(Id);
                }
                plmProductSupplierInfoEcoDAO_HI.saveOrUpdate(supplierEco);
            }
        }
        if(CollectionUtils.isNotEmpty(ecoInfoR.getPriceEcos())){
            for (PlmProductPriceEcoEntity_HI_RO priceRoEco : ecoInfoR.getPriceEcos()) {
                VtorUtil.validate(priceRoEco, new String[]{VtorProfile.INSERT.getName()});
                PlmProductPriceEcoEntity_HI priceEco = new PlmProductPriceEcoEntity_HI();
                BeanUtils.copyProperties(priceRoEco,priceEco);
                if(ObjectUtils.isEmpty(priceEco.getEcoId())){
                    priceEco.setEcoId(ecoInfo.getEcoId());
                }

                if("ADD".equals(priceEco.getAcdType())  && ObjectUtils.isEmpty(priceRoEco.getPriceId()) ){
                    //新增的时候获取序列号主键ID
                    Integer  Id = getAddLineBaseId(PlmProductSeqEcoEntity_HI_RO.QUERY_PRICE);
                    priceRoEco.setPriceId(Id);
                }
                plmProductPriceEcoDAO_HI.saveOrUpdate(priceEco);
            }
        }

        if(CollectionUtils.isNotEmpty(ecoInfoR.getQaFileEcos())){
            for (PlmProductQaFileEcoEntity_HI_RO qaFileRoEco : ecoInfoR.getQaFileEcos()) {
                VtorUtil.validate(qaFileRoEco, new String[]{VtorProfile.INSERT.getName()});
                PlmProductQaFileEcoEntity_HI qaFileEco = new PlmProductQaFileEcoEntity_HI();
                BeanUtils.copyProperties(qaFileRoEco,qaFileEco);
                if(ObjectUtils.isEmpty(qaFileEco.getEcoId())){
                    qaFileEco.setEcoId(ecoInfo.getEcoId());
                }

                if("ADD".equals(qaFileEco.getAcdType()) && ObjectUtils.isEmpty(qaFileEco.getQaFileId()) ){
                    //新增的时候获取序列号主键ID
                    Integer  Id = getAddLineBaseId(PlmProductSeqEcoEntity_HI_RO.QUERY_QAFILE);
                    qaFileEco.setQaId(Id);
                }
                plmProductQaFileEcoDAO_HI.saveOrUpdate(qaFileEco);
            }
        }

        if(CollectionUtils.isNotEmpty(ecoInfoR.getPicfileTableEcos())){
            for (PlmProductPicfileTableEcoEntity_HI_RO picfileTableRoEco : ecoInfoR.getPicfileTableEcos()) {
                VtorUtil.validate(picfileTableRoEco, new String[]{VtorProfile.INSERT.getName()});
                PlmProductPicfileTableEcoEntity_HI picfileTableEco = new PlmProductPicfileTableEcoEntity_HI();
                BeanUtils.copyProperties(picfileTableRoEco,picfileTableEco);
                if(ObjectUtils.isEmpty(picfileTableEco.getEcoId())){
                    picfileTableEco.setEcoId(ecoInfo.getEcoId());
                }
                if("ADD".equals(picfileTableEco.getAcdType()) && ObjectUtils.isEmpty(picfileTableEco.getPicfileId()) ){
                    //新增的时候获取序列号主键ID
                    Integer  Id = getAddLineBaseId(PlmProductSeqEcoEntity_HI_RO.QUERY_PICFILE);
                    picfileTableEco.setPicId(Id);
                }
                plmProductPicfileTableEcoDAO_HI.saveOrUpdate(picfileTableEco);
            }
        }
        if( !ObjectUtils.isEmpty(ecoInfoR.getMedicalinfoEcos())&& ! EcoUtils.allfieldIsNUll(ecoInfoR.getMedicalinfoEcos()) ){
            PlmProductMedicalinfoEcoEntity_HI_RO medicalinfoRoEco = ecoInfoR.getMedicalinfoEcos();
//            if(allfieldIsNUll(medicalinfoRoEco)){}
                VtorUtil.validate(medicalinfoRoEco, new String[]{VtorProfile.INSERT.getName()});
                PlmProductMedicalinfoEcoEntity_HI medicalinfoEco = new PlmProductMedicalinfoEcoEntity_HI();
                BeanUtils.copyProperties(medicalinfoRoEco,medicalinfoEco);
                if(ObjectUtils.isEmpty(medicalinfoEco.getEcoId())){
                    medicalinfoEco.setEcoId(ecoInfo.getEcoId());
                }
                plmProductMedicalinfoEcoEntity_HI.saveOrUpdate(medicalinfoEco);

        }
        return JSONObject.toJSONString(ecoInfo);
    }

    private Integer getAddLineBaseId(String sql) {
        Object seqRos = plmProductSeqEcoEntity_HI_RO.findList(sql);
        List<Map<String,Object>> seqRoMap = (List<Map<String,Object>>)seqRos;
        JSONObject seqRo = JSONObject.parseObject(JSONObject.toJSONString(seqRoMap.get(0))) ;
        Integer seqBatch= seqRo.getInteger("SEQ");
        return seqBatch;
    }

    @Override
    public Pagination<PlmProductHeadEcoEntity_HI_RO> findProductHeadEcoList(JSONObject param, Integer pageIndex, Integer pageRows) {
        String UserType = param.getString("varUserType"); // 用户类型
        String userName = param.getString("varUserName"); // 用户名
        String varUserId = param.getString("varUserId"); // 用户名
        boolean iscopy = false;
        StringBuffer query = new StringBuffer();
        query.append(PlmProductHeadEcoEntity_HI_RO.QUERY_HEAD);
        Map<String, Object> params = new HashMap<String, Object>();
        if (param.containsKey("productName")) {
            query.append(" and upper(product.product_name) like '%"
                    + param.getString("productName").toUpperCase() + "%' ");
        }
        if (param.containsKey("rmsCode") && param.getString("rmsCode") != null && !"".equals(param.getString("rmsCode"))) {
            query.append(" and product.rms_code like '%"
                    + param.getString("rmsCode") + "%' ");
        }
        SaafToolUtils.parperParam(param, "product.plm_code", "plmCode", query,
                params, "fulllike");
        SaafToolUtils.parperParam(param, "product.brandname_cn", "brandnameCn",
                query, params, "fulllike");
        SaafToolUtils.parperParam(param, "product.department", "department",
                query, params, "fulllike");
        SaafToolUtils.parperParam(param, "product.plm_code", "plmCode", query,
                params, "fulllike");
        SaafToolUtils.parperParam(param, "product.rms_code", "rmsCode", query,
                params, "fulllike");
//        SaafToolUtils.parperParam(param, "product.OTHER_INFO", "otherInfo", query,
//                params, "like");
        //
//        param.remove("otherInfo");
        param.remove("productName");
        param.remove("rmsCode");
        param.remove("plmCode");
        param.remove("brandnameCn");
        param.remove("department");
        if (param.containsKey("createdstr")) {
            String createdstr = param.getString("createdstr");
            param.remove("createdstr");
            query.append(" and ( upper(PRODUCT.createdstr) like '%"
                    + createdstr.toUpperCase()
                    + "%' or upper(PRODUCT.created_emp) like '"
                    + createdstr.toUpperCase()
                    + "%' or upper(PRODUCT.created_ename) like '"
                    + createdstr.toUpperCase() + "%') ");
        }

        if (param.containsKey("orderStatus")) {
            String orderStatus = param.getString("orderStatus");
            if (orderStatus.equals("0")) {
                param.remove("orderStatus");
                query.append(" and PRODUCT.order_status!='8'");
            }

        }

        if (param.containsKey("orderStatus")
                && param.getString("orderStatus") != null && !"".equals(param.getString("orderStatus")) ) {
            String orderStatus = param.getString("orderStatus");
            param.remove("orderStatus");
            query.append(" and PRODUCT.order_Status='" + orderStatus + "'");
        }
        if (param.containsKey("creationDate")
                && param.getDate("creationDate") != null ) {
            Date creationDate = param.getDate("creationDate");
            SimpleDateFormat sim = new SimpleDateFormat("YYYY-MM-dd");
            param.remove("creationDate");
            query.append(" and to_char(PRODUCT.creation_date,'yyyy-mm-dd')='"
                    + sim.format(creationDate) + "'");
        }
        if (param.containsKey("otherInfo")
                && param.getString("otherInfo") != null && !"".equals(param.getString("otherInfo"))) {
            String otherInfo = param.getString("otherInfo");
            param.remove("otherInfo");
            query.append(" and PRODUCT.OTHER_INFO='" + otherInfo + "'");
        }
        if (param.containsKey("markerChannel")
                && param.getString("markerChannel") != null  && !"".equals(param.getString("markerChannel"))) {
            String markerChannel = param.getString("markerChannel");
            param.remove("markerChannel");
            query.append(" and PRODUCT.marker_Channel='" + markerChannel + "'");
        }
        if (param.containsKey("purchaseType")
                && param.getString("purchaseType") != null   && !"".equals(param.getString("purchaseType"))) {
            String purchaseType = param.getString("purchaseType");
            param.remove("purchaseType");
            query.append(" and PRODUCT.purchase_Type='" + purchaseType + "'");
        }
        if (param.containsKey("productType")
                && param.getString("productType") != null  && !"".equals(param.getString("productType")) ) {
            String productType = param.getString("productType");
            param.remove("productType");
            query.append(" and PRODUCT.product_Type='" + productType + "'");
        }

        if(param.containsKey("ecoStatus"))
        {
            String ecostatus=param.getString("ecoStatus");
                 query.append(" and PRODUCT.eco_status='"+ecostatus+"' ");
                 param.remove("ecoStatus");
        }else{
              query.append(" and PRODUCT.eco_status!='CANCEL' ");
        }

//        if (iscopy) {
//            query.append(" and PRODUCT.USER_DEPT='"
//                    + param.getString("userDept") + "' ");
//        }

//        if (param.containsKey("nolist")) {
//            query.append(" and PRODUCT.RMS_CODE is not null ");
//            param.remove("nolist");
//        }
//                if (param.containsKey("nolist")) {
//            query.append(" and PRODUCT.RMS_CODE is not null ");
//
//            param.remove("nolist");
//        }
//        if(varUserId!=null){
//            query.append(" and PRODUCT.created_by = " + varUserId + "");
//            param.remove("varUserId");
//        }

        JSONObject deptparam = new JSONObject();
        deptparam.put("deptId", param.getInteger("varOrgId"));
        List<PlmDataAclHeaderEntity_HI> headli = plmDataAclHeaderServer
                .findList(deptparam);
        if (headli.size() > 0 && !userName.equals("ADMIN")
                && !UserType.equals("60") ) {
            String location = "";
            PlmDataAclHeaderEntity_HI headinfo = headli.get(0);
            Integer headid = headinfo.getHeadId();
            JSONObject lineparam = new JSONObject();
            lineparam.put("headId", headid);
            lineparam.put("enableFlag","Y");
            List<PlmDataAclLineEntity_HI> linedata = IPlmDataAclLineServer
                    .findList(lineparam);
            String sqlappend = " and (";
            for (PlmDataAclLineEntity_HI line : linedata) {
                // QUERY
                String acltype = line.getAclType();
                if (acltype.equals("QUERY")) { // 新建权限
                    String groupCode = line.getGroupCode();
                    if (groupCode.length() == 1 || groupCode.length() == 2) {
                        sqlappend += " PRODUCT.DEPARTMENT like '" + groupCode
                                + "%'  or ";
                    } else {
                        location += "'" + groupCode + "',";
                    }

                }
            }
            sqlappend += " 1=1";
            if (!location.equals("")) {
                sqlappend += " or PRODUCT.DEPARTMENT in("
                        + location.substring(0, location.length() - 1) + ")";

            }
            query.append(sqlappend.replace("or  1=1", "").replace("1=1 or", ""));
            query.append(")");
        }



        param.remove("userDept");
        com.sie.saaf.common.util.SaafToolUtils.parperHbmParam(
                PlmProductHeadEcoEntity_HI_RO.class, param, query, params);
        query.append(" order by PRODUCT.last_update_date desc");


        Pagination<PlmProductHeadEcoEntity_HI_RO> pagination =plmProductHeadEcoDAO_HI_RO
                .findPagination(query, SaafToolUtils.getSqlCountString(query),params, pageIndex, pageRows);
        // String result = issubmit.equals("0") ? "未提交" : "已提交";

        return pagination;

    }

    @Override
    public String diferenceCheckByEcoId(Integer ecoId) throws Exception {
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("eco_id",ecoId);
//        try {
//            Session session = headDAO_HI.getSessionFactory().getCurrentSession();
//            session.doWork(new Work() {
//                public void execute(Connection conn) throws SQLException {
//                    String sql = "{call pkg_product_modify_atteibute.check_product_head(?,?,?)}";
//                    //	conn.setAutoCommit(false);
//                    CallableStatement call = conn.prepareCall(sql);
//                    // 设置输入参数
//                    call.setString(1, "");
//                    // 设置输出参数
//                    call.registerOutParameter(2, Types.INTEGER);
//                    call.registerOutParameter(3, Types.INTEGER);
//                    call.execute();
//                    String x_msg = call.getString(2);
//                    String x_status  = call.getString(3);
//                    paramsMap.put("x_msg",x_msg);
//                    paramsMap.put("x_status",x_status);
//                    close(call);
//                }
//            });
//        } catch (Exception e) {
//            LOGGER.error(".callProName 调用存储过程名称:{},异常信息:{}", "pkg_product_modify_atteibute.check_product_head", e);
//        }
       paramsMap =  headDAO_HI.callPkg(paramsMap);
        return  JSONObject.toJSONString(paramsMap);
    }

    private void close(CallableStatement cs) {
        try {
            if (cs != null) {
                cs.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private PlmProductHeadEcoEntity_HI saveOrUpdateProductHeadEco(PlmProductHeadEcoEntity_HI ecoInfo, Integer userId, Integer orgId, String employeeNumber, String varUserFullName) throws Exception {

	    //校验实体类字段是否为空
        VtorUtil.validate(ecoInfo, new String[]{VtorProfile.INSERT.getName()});
	    if(ecoInfo.getEcoId()==null){
	        //新增
            String ecoNo = generateCodeService.getEcoPlanCode();
            PlmProductHeadEcoEntity_HI newProductHeadEco = new PlmProductHeadEcoEntity_HI();
            BeanUtils.copyProperties(ecoInfo,newProductHeadEco,"versionNum","createdBy");
            newProductHeadEco.setEcoStatus(ProductEcoEnum.UPD_MAKING.getValues());
            newProductHeadEco.setCreatedBy(userId);
            newProductHeadEco.setCreatedstr(varUserFullName);
            newProductHeadEco.setCreatedEmp(employeeNumber);
            newProductHeadEco.setCreationDate(new Date());
            newProductHeadEco.setLastUpdateDate(new Date());
            newProductHeadEco.setLastUpdatedBy(userId);
            newProductHeadEco.setLastUpdateLogin(userId);
            newProductHeadEco.setEcoNo(ecoNo);
            newProductHeadEco.setEcoDeptId(orgId);
            ecoInfo = plmProductHeadEcoDAO_HI.insert(newProductHeadEco);
        }else {
             plmProductHeadEcoDAO_HI.saveOrUpdate(ecoInfo);
        }

	    return ecoInfo;

    }

    private void copypicFileEco(Integer productId, Integer userId, Integer ecoId) {
        Map<String,Object> map = new HashMap<>();
        map.put("productHeadId",productId);
        List<PlmProductPicfileTableEntity_HI> picfileTables = plmProductPicfileTableDAO_HI.findByProperty(map);
        if(CollectionUtils.isNotEmpty(picfileTables)){
            for (PlmProductPicfileTableEntity_HI picfileTable : picfileTables) {
                PlmProductPicfileTableEcoEntity_HI picfileTableEco = new PlmProductPicfileTableEcoEntity_HI();
                BeanUtils.copyProperties(picfileTable,picfileTableEco,"versionNum","createdBy");
                picfileTableEco.setCreatedBy(userId);
                picfileTableEco.setCreationDate(new Date());
                picfileTableEco.setLastUpdateDate(new Date());
                picfileTableEco.setLastUpdatedBy(userId);
                picfileTableEco.setLastUpdateLogin(userId);
                picfileTableEco.setEcoId(ecoId);
                plmProductPicfileTableEcoDAO_HI.saveOrUpdate(picfileTableEco);
            }
        }
    }

    private void copyQaFileEco(Integer productId, Integer userId, Integer ecoId) {
        Map<String,Object> map = new HashMap<>();
        map.put("productHeadId",productId);
        List<PlmProductQaFileEntity_HI> qaFiles = plmProductQaFileDAO_HI.findByProperty(map);
        if(CollectionUtils.isNotEmpty(qaFiles)){
            for (PlmProductQaFileEntity_HI qaFile : qaFiles) {
                PlmProductQaFileEcoEntity_HI qaFileEco = new PlmProductQaFileEcoEntity_HI();
                BeanUtils.copyProperties(qaFile,qaFileEco,"versionNum","createdBy");
                qaFileEco.setCreatedBy(userId);
                qaFileEco.setCreationDate(new Date());
                qaFileEco.setLastUpdateDate(new Date());
                qaFileEco.setLastUpdatedBy(userId);
                qaFileEco.setLastUpdateLogin(userId);
                qaFileEco.setEcoId(ecoId);
                plmProductQaFileEcoDAO_HI.saveOrUpdate(qaFileEco);
            }
        }
    }

    private void copyPriceEco(Integer productId, Integer userId, Integer ecoId) {
        Map<String,Object> map = new HashMap<>();
        map.put("productHeadId",productId);
        List<PlmProductPriceEntity_HI> prices = plmProductPriceDAO_HI.findByProperty(map);
        if(CollectionUtils.isNotEmpty(prices)){
            for (PlmProductPriceEntity_HI price : prices) {
                PlmProductPriceEcoEntity_HI priceEco = new PlmProductPriceEcoEntity_HI();
                BeanUtils.copyProperties(price,priceEco,"versionNum","createdBy");
                priceEco.setCreatedBy(userId);
                priceEco.setCreationDate(new Date());
                priceEco.setLastUpdateDate(new Date());
                priceEco.setLastUpdatedBy(userId);
                priceEco.setLastUpdateLogin(userId);
                priceEco.setEcoId(ecoId);
                plmProductPriceEcoDAO_HI.saveOrUpdate(priceEco);
            }
        }

    }

    /**
     * 复制供应商信息
     * @param productId
     * @param userId
     * @param ecoId
     */
    private void copySupplierEco(Integer productId, Integer userId, Integer ecoId) {
        //根据productId 查询药品属性
        Map<String,Object> map = new HashMap<>();
        map.put("productHeadId",productId);
        List<PlmProductSupplierInfoEntity_HI> suppliers = plmProductSupplierInfoDAO_HI.findByProperty(map);
        if(CollectionUtils.isNotEmpty(suppliers)){
            for (PlmProductSupplierInfoEntity_HI supplier : suppliers) {
                PlmProductSupplierInfoEcoEntity_HI supperEco = new PlmProductSupplierInfoEcoEntity_HI();
                BeanUtils.copyProperties(supplier,supperEco,"versionNum","createdBy");
                supperEco.setCreatedBy(userId);
                supperEco.setCreationDate(new Date());
                supperEco.setLastUpdateDate(new Date());
                supperEco.setLastUpdatedBy(userId);
                supperEco.setLastUpdateLogin(userId);
                supperEco.setEcoId(ecoId);
                plmProductSupplierInfoEcoDAO_HI.saveOrUpdate(supperEco);
            }
        }
    }

    /**
     * 复制药品属性表
     * @param productId
     * @param userId
     * @param ecoId
     */
    private void copyDrugsEco(Integer productId, Integer userId, Integer ecoId) {
        //根据productId 查询药品属性
        Map<String,Object> map = new HashMap<>();
        map.put("productHeadId",productId);
        List<PlmProductDrugEntity_HI> drugs =  plmProductDrugDAO_HI.findByProperty(map);
        if(CollectionUtils.isNotEmpty(drugs)){
            for (PlmProductDrugEntity_HI durg: drugs) {
                PlmProductDrugEcoEntity_HI drugEco = new PlmProductDrugEcoEntity_HI();
                BeanUtils.copyProperties(durg,drugEco,"versionNum","createdBy");
                drugEco.setCreatedBy(userId);
                drugEco.setCreationDate(new Date());
                drugEco.setLastUpdateDate(new Date());
                drugEco.setLastUpdatedBy(userId);
                drugEco.setLastUpdateLogin(userId);
                drugEco.setEcoId(ecoId);
                plmProductDrugEcoDAO_HI.saveOrUpdate(drugEco);
            }
        }
    }

	@Override
	public void updateModify(String string) {
		plmProductModifyCheckDao_HI.executeSqlUpdate(string);
	}


}
