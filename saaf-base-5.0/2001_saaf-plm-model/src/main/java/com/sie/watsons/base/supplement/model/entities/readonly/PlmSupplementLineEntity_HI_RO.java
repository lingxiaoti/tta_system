package com.sie.watsons.base.supplement.model.entities.readonly;

import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Version;
import javax.persistence.Transient;

/**
 * PlmSupplementLineEntity_HI_RO Entity Object
 * Mon Sep 23 17:32:30 CST 2019  Auto Generate
 */

public class PlmSupplementLineEntity_HI_RO {

    public static final String OPEN_LINES_SQL ="select psh.sup_code supCode,psl.plm_supplement_line_id plmSupplementLineId,psl.head_id headId,psl.STORE store,psl.STORE_TYPE storeType,psl.meter,psl.SUPPLEMENT_STATUS supplementStatus," +
            "PSL.RMS_CODE rmsCode,PSL.EXE_DATE exeDate,PSL.EXPIRE_DATE expireDate," +
            "psl.STOP_REASON AS stopReason  " +
            " from plm_supplement_line psl \n" +
            "LEFT JOIN  plm_supplement_head psh \n" +
            "on PSH.PLM_SUPPLEMENT_HEAD_ID = PSL.HEAD_ID  \n" +
            "where \n" +
            " psh.ORDER_STATUS = '2' \n" +
            "and psl.EXE_STATUS = '0' \n" +
            "and PSL.EXE_DATE < sysdate+1 \n";
//            "and PSL.LAST_UPDATE_DATE + 30 > SYSDATE " +
//            "and ((PSL.EXE_DATE < sysdate and  (PSL.EXPIRE_DATE is null or sysdate > PSL.EXPIRE_DATE) and psl.SUPPLEMENT_STATUS in ('0','2','4')) " +
//            "and (((psl.SUPPLEMENT_STATUS in ('0','2','4')) " +
//            "or (psl.SUPPLEMENT_STATUS in ('1','3','5'))) ";

    public static final String STOP_LINES_SQL ="select psh.sup_code supCode,psl.plm_supplement_line_id plmSupplementLineId,psl.head_id headId,psl.STORE store,psl.meter,psl.SUPPLEMENT_STATUS supplementStatus,PSL.RMS_CODE rmsCode,PSL.EXE_DATE exeDate,PSL.EXPIRE_DATE expireDate, psl.STOP_REASON AS stopReason   from plm_supplement_line psl \n" +
			"LEFT JOIN  plm_supplement_head psh \n" +
			"on PSH.PLM_SUPPLEMENT_HEAD_ID = PSL.HEAD_ID  \n" +
			"where \n" +
			" psh.ORDER_STATUS = '2' \n" +
			"and psl.EXPIRE_STATUS = '0' \n" +
			"and ((PSL.EXPIRE_DATE is not null and PSL.EXPIRE_DATE < sysdate+1 and psl.SUPPLEMENT_STATUS in ('0','2','4')))";

    //查出某单据下货品存在重复的数据
    public static final String INSIDE_LINES_SQL ="select psh.sup_code supCode,psl.plm_supplement_line_id plmSupplementLineId,psl.head_id headId,psl.STORE store,psl.meter,psl.SUPPLEMENT_STATUS supplementStatus," +
            "PSL.RMS_CODE rmsCode,PSL.EXE_DATE exeDate,PSL.EXPIRE_DATE expireDate, " +
            "psl.STOP_REASON AS stopReason  " +
            " from plm_supplement_head psh \n" +
            "LEFT JOIN plm_supplement_line psl  \n" +
            "on PSH.PLM_SUPPLEMENT_HEAD_ID = PSL.HEAD_ID  \n" +
            "where \n" +
            " psh.PLM_SUPPLEMENT_HEAD_ID = ':headId'  " +
            "and psl.RMS_CODE in (select p.RMS_CODE  \n" +
            "from plm_supplement_line p  " +
            "where p.HEAD_ID = psh.PLM_SUPPLEMENT_HEAD_ID  " +
            "GROUP BY p.RMS_CODE " +
            "having count(p.RMS_CODE) >1)  ";

    public static final String CHECKING_SQL ="select psl.STORE store,psl.meter,psl.SUPPLEMENT_STATUS supplementStatus," +
            "PSL.RMS_CODE rmsCode,PSL.EXE_DATE exeDate,PSL.EXPIRE_DATE expireDate " +
            " from plm_supplement_head psh \n" +
            "LEFT JOIN plm_supplement_line psl  \n" +
            "on PSH.PLM_SUPPLEMENT_HEAD_ID = PSL.HEAD_ID  \n" +
            "where \n" +
            " psh.ORDER_STATUS = '1'";

	public static final String SQL = "SELECT\n" +
			"\tPSL.PLM_SUPPLEMENT_LINE_ID AS plmSupplementLineId,\n" +
			"\tPSL.RMS_CODE AS rmsCode,\n" +
			"\tPSL.PRODUCT_NAME AS productName,\n" +
			"\tPRH.PRODUCT_ENAME AS productEname,\n" +
			"\tPSL.region AS region,\n" +
			"\tPSL.WAREHOUSE AS warehouse,\n" +
			"\tPSL.METER AS meter,\n" +
			"\tPSL.POG_CODE AS pogCode,\n" +
			"\tPSL.store AS store,\n" +
			"\tPSL.SUPPLEMENT_STATUS AS supplementStatus,\n" +
			"\tPSL.STOP_REASON AS stopReason,\n" +
			"\tPSL.STORE_TYPE AS storeType,\n" +
			"\tPSL.EXE_DATE AS exeDate,\n" +
			"\tPSL.EXPIRE_DATE AS expireDate,\n" +
			"\tPSL.SOUTH_ITEM_LIST AS southItemList,\n" +
			"\tPSL.WEST_ITEM_LIST AS westItemList,\n" +
			"\tPSL.EAST_ITEM_LIST AS eastItemList,\n" +
			"\tPSL.NORTH_ITEM_LIST AS northItemList,\n" +
			"\tPSL.remarks as remarks,\n" +
			"\tPRH.BRANDNAME_CN as brandnameCn,\n" +
			"\tPRH.BRANDNAME_EN as brandnameEn,\n" +
			"\tPRH.DEPARTMENT as department,\n" +
			"\tPRH.CLASSES as classes,\n" +
			"\tPRH.SUB_CLASS as subClass,\n" +
			"\tPRH.SESION_PRODUCT as sesionProduct,\n" +
			"\tPRH.PRODUCT_RETURN as productReturn,\n" +
			"\tPRH.GROUP_BRAND as groupBrand,\n" +
//			"\tPSL.CONSULT_DATE as consultDate,\n" +
//			"\tPRH.CONSULT_ENDDATE as consultEnddate,\n" +
			"\t(select ppb.barcode from PLM_PRODUCT_BARCODE_TABLE ppb\n" +
            "where prh.product_head_id=ppb.product_head_id and ppb.is_main='Y') barcode \n" +
			"FROM\n" +
			"\tPLM_SUPPLEMENT_LINE PSL\n" +
			"LEFT JOIN PLM_PRODUCT_HEAD PRH\n" +
			"ON PSL.RMS_CODE=PRH.RMS_CODE\n" +
			"where 1=1  ";

	public static final String SQL2 = "SELECT\n" +
			"\tPSL.PLM_SUPPLEMENT_LINE_ID AS plmSupplementLineId,\n" +
			"\tPSL.RMS_CODE AS rmsCode,\n" +
			"\tPSL.PRODUCT_NAME AS productName,\n" +
			"\tPSL.region AS region,\n" +
			"\tPSL.WAREHOUSE AS warehouse,\n" +
			"\tPSL.METER AS meter,\n" +
			"\tPSL.POG_CODE AS pogCode,\n" +
			"\tPSL.store AS store,\n" +
			"\tPSL.prior_supplier AS priorSupplier,\n" +
			"\tPSL.SUPPLEMENT_STATUS AS supplementStatus,\n" +
			"\tPSL.STOP_REASON AS stopReason,\n" +
			"\tPSL.STORE_TYPE AS storeType,\n" +
			"\tPSL.EXE_DATE AS exeDate,\n" +
			"\tPSL.EXPIRE_DATE AS expireDate,\n" +
			"\tPSL.SOUTH_ITEM_LIST AS southItemList,\n" +
			"\tPSL.WEST_ITEM_LIST AS westItemList,\n" +
			"\tPSL.EAST_ITEM_LIST AS eastItemList,\n" +
			"\tPSL.NORTH_ITEM_LIST AS northItemList,\n" +
			"\tPSL.remarks as remarks,\n" +
			"\tPSL.created_by as createdBy,\n" +
			"\tPSL.creation_date as creationDate,\n" +
			"\tPSL.last_updated_by as lastUpdatedBy,\n" +
			"\tPSL.last_update_date as lastUpdateDate,\n" +
			"\tPSL.last_update_login as lastUpdateLogin,\n" +
			"\tPSL.version_num as versionNum,\n" +
			"\tPSL.exe_status as exeStatus,\n" +
			"\tPSL.expire_status as expireStatus,\n" +
			"\tPSL.consult_productno as consultProductno,\n" +
			"\tPSL.consult_date as consultDate,\n" +
			"\tPSL.consult_enddate as consultEnddate,\n" +
			"\tPSL.sesion_product as sesionProduct,\n" +
			"\tPSL.head_id as headId\n" +
			"FROM\n" +
			"\tPLM_SUPPLEMENT_LINE PSL\n" +
			"where 1=1  ";

	public static final String SQL3 = "SELECT\n" +
			"\tPSL.PLM_SUPPLEMENT_LINE_ID AS plmSupplementLineId,\n" +
			"\tPSL.RMS_CODE AS rmsCode,\n" +
			"\tPSL.PRODUCT_NAME AS productName,\n" +
			"\tPSL.region AS region,\n" +
			"\tPSL.WAREHOUSE AS warehouse,\n" +
			"\tPSL.METER AS meter,\n" +
			"\tPSL.POG_CODE AS pogCode,\n" +
			"\tPSL.store AS store,\n" +
			"\tPSL.prior_supplier AS priorSupplier,\n" +
			"\tPSL.SUPPLEMENT_STATUS AS supplementStatus,\n" +
			"\tPSL.STOP_REASON AS stopReason,\n" +
			"\tPSL.STORE_TYPE AS storeType,\n" +
			"\tPSL.EXE_DATE AS exeDate,\n" +
			"\tPSL.EXPIRE_DATE AS expireDate,\n" +
			"\tPSL.SOUTH_ITEM_LIST AS southItemList,\n" +
			"\tPSL.WEST_ITEM_LIST AS westItemList,\n" +
			"\tPSL.EAST_ITEM_LIST AS eastItemList,\n" +
			"\tPSL.NORTH_ITEM_LIST AS northItemList,\n" +
			"\tPSL.remarks as remarks,\n" +
			"\tPSL.created_by as createdBy,\n" +
			"\tPSL.creation_date as creationDate,\n" +
			"\tPSL.last_updated_by as lastUpdatedBy,\n" +
			"\tPSL.last_update_date as lastUpdateDate,\n" +
			"\tPSL.last_update_login as lastUpdateLogin,\n" +
			"\tPSL.version_num as versionNum,\n" +
			"\tPSL.exe_status as exeStatus,\n" +
			"\tPSL.expire_status as expireStatus,\n" +
			"\tPSL.consult_productno as consultProductno,\n" +
			"\tPSL.consult_date as consultDate,\n" +
			"\tPSL.consult_enddate as consultEnddate,\n" +
			"\tPSL.sesion_product as sesionProduct,\n" +
			"\tPSL.head_id as headId,\n"
			+ " psh.sup_code as supCode,"
			+ " psh.ORDER_TYPE as orderType,"
			+ " psh.ORDER_STATUS as orderStatus,"
			+ " psh.CREATOR as creator,"
			+ " psh.CREATED_BY as createdBy,"
			+ " psh.ORG_CODE as orgCode"
			+ " psh.CREATION_DATE as creationDate"
			+ "FROM\n"
			+ "\tPLM_SUPPLEMENT_LINE PSL\n"
			+ " LEFT JOIN plm_supplement_head psh "
			+ " on psh.PLM_SUPPLEMENT_HEAD_ID = psl.HEAD_ID  "
			+ " where 1=1 ";

	//停补取当前，方位取未来
	public static final String SQL_ITEM = "select DISTINCT rms_id as rmsCode,VS.area_en as region, \n" +
			"pps.location as store from PLM_PRODUCT_SUPPLIERPLACEINFO pps \n" +
			"LEFT JOIN vmi_shop vs on PPS.location =VS.vs_code \n" +
			"where loc_type = 'S'  \n" +
			"and VS.vh_pre_code is not null \n" +
			"and pps.status = '0' \n" +
            "and rms_id = ':rmsId'";

	public static final String SQL_METER = "select \n" +
            "vp.METER AS metre,\n" +
            "vp.POG_CODE AS pogCode,\n" +
            "vpi.item_code AS rmsCode \n" +
            "from vmi_pog vp \n" +
            "left join vmi_pog_item vpi \n" +
            "on vp.key_id=vpi.key_id \n" +
            "where 1=1 ";

	private Integer plmSupplementLineId;
    private String rmsCode;
    private String productName;
    private String productEname;
    private String region;
    private String warehouse;
    private String meter;
    private Integer headId;
    //因为从vmi_real_time取来是int, 所以额外加的
    private Integer metre;
    private String pogCode;
    private String store;
    private String storeDesc;
    private String priorSupplier;
    private String supplementStatus;
	private String supplementStatusDesc;
    private String stopReason;
    private String stopReasonDesc;
    private String storeType;
    private String storeTypeDesc;
	private String brandnameCn;
	private String brandnameEn;
	private String department;
	private String classes;
	private String subClass;
	private String sesionProduct;
	private String sesionProductDesc;
	private String productReturn;
	private String groupBrand;
	@JSONField(format="yyyy-MM-dd")
	private String consultDate;
	@JSONField(format="yyyy-MM-dd")
	private String consultEnddate;
	private String barcode;
	@JSONField(format="yyyy-MM-dd")
    private Date exeDate;
	@JSONField(format="yyyy-MM-dd")
	private Date expireDate;
    private String southItemList;
    private String westItemList;
    private String eastItemList;
    private String northItemList;
    private String remarks;
    private Integer createdBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer versionNum;
    private Integer operatorUserId;
    private String exeStatus;
    private String expireStatus;
    private String consultProductno;

    private String supCode;

    public String getSupCode() {
        return supCode;
    }

    public void setSupCode(String supCode) {
        this.supCode = supCode;
    }

    public Integer getHeadId() {
        return headId;
    }

    public void setHeadId(Integer headId) {
        this.headId = headId;
    }

    public void setPlmSupplementLineId(Integer plmSupplementLineId) {
		this.plmSupplementLineId = plmSupplementLineId;
	}

	
	public Integer getPlmSupplementLineId() {
		return plmSupplementLineId;
	}

	public void setRmsCode(String rmsCode) {
		this.rmsCode = rmsCode;
	}

	
	public String getRmsCode() {
		return rmsCode;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	
	public String getProductName() {
		return productName;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	
	public String getRegion() {
		return region;
	}

	public void setWarehouse(String warehouse) {
		this.warehouse = warehouse;
	}

	
	public String getWarehouse() {
		return warehouse;
	}

	public void setMeter(String meter) {
		this.meter = meter;
	}

	
	public String getMeter() {
		return meter;
	}

	public void setPogCode(String pogCode) {
		this.pogCode = pogCode;
	}

	
	public String getPogCode() {
		return pogCode;
	}

	public void setStore(String store) {
		this.store = store;
	}

	
	public String getStore() {
		return store;
	}

	public void setPriorSupplier(String priorSupplier) {
		this.priorSupplier = priorSupplier;
	}

	
	public String getPriorSupplier() {
		return priorSupplier;
	}

	public void setSupplementStatus(String supplementStatus) {
		this.supplementStatus = supplementStatus;
	}

	
	public String getSupplementStatus() {
		return supplementStatus;
	}

	public void setStopReason(String stopReason) {
		this.stopReason = stopReason;
	}

	
	public String getStopReason() {
		return stopReason;
	}

	public void setStoreType(String storeType) {
		this.storeType = storeType;
	}

	
	public String getStoreType() {
		return storeType;
	}

	public void setExeDate(Date exeDate) {
		this.exeDate = exeDate;
	}

	
	public Date getExeDate() {
		return exeDate;
	}

	public void setSouthItemList(String southItemList) {
		this.southItemList = southItemList;
	}

	
	public String getSouthItemList() {
		return southItemList;
	}

	public void setWestItemList(String westItemList) {
		this.westItemList = westItemList;
	}

	
	public String getWestItemList() {
		return westItemList;
	}

	public void setEastItemList(String eastItemList) {
		this.eastItemList = eastItemList;
	}

	
	public String getEastItemList() {
		return eastItemList;
	}

	public void setNorthItemList(String northItemList) {
		this.northItemList = northItemList;
	}

	
	public String getNorthItemList() {
		return northItemList;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	
	public String getRemarks() {
		return remarks;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	
	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	
	public Date getCreationDate() {
		return creationDate;
	}

	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	
	public Integer getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateLogin(Integer lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}

	
	public Integer getLastUpdateLogin() {
		return lastUpdateLogin;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	
	public Integer getVersionNum() {
		return versionNum;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	public Integer getMetre() {
		return metre;
	}

	public void setMetre(Integer metre) {
		this.metre = metre;
	}

	public String getBrandnameCn() {
		return brandnameCn;
	}

	public void setBrandnameCn(String brandnameCn) {
		this.brandnameCn = brandnameCn;
	}

	public String getBrandnameEn() {
		return brandnameEn;
	}

	public void setBrandnameEn(String brandnameEn) {
		this.brandnameEn = brandnameEn;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getClasses() {
		return classes;
	}

	public void setClasses(String classes) {
		this.classes = classes;
	}

	public String getSubClass() {
		return subClass;
	}

	public void setSubClass(String subClass) {
		this.subClass = subClass;
	}

	public String getSesionProduct() {
		return sesionProduct;
	}

	public void setSesionProduct(String sesionProduct) {
		this.sesionProduct = sesionProduct;
	}

	public String getProductReturn() {
		return productReturn;
	}

	public void setProductReturn(String productReturn) {
		this.productReturn = productReturn;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getGroupBrand() {
		return groupBrand;
	}

	public void setGroupBrand(String groupBrand) {
		this.groupBrand = groupBrand;
	}

	public String getProductEname() {
		return productEname;
	}

	public void setProductEname(String productEname) {
		this.productEname = productEname;
	}

	public Date getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}

	public String getExeStatus() {
		return exeStatus;
	}

	public void setExeStatus(String exeStatus) {
		this.exeStatus = exeStatus;
	}

	public String getExpireStatus() {
		return expireStatus;
	}

	public void setExpireStatus(String expireStatus) {
		this.expireStatus = expireStatus;
	}

	public String getSupplementStatusDesc() {
		if (supplementStatus != null && !"".equals(supplementStatus)) {
			if ("0".equals(supplementStatus)) {
				supplementStatusDesc = "开通店铺";
			}
			if ("1".equals(supplementStatus)) {
				supplementStatusDesc = "停补店铺";
			}
			if ("2".equals(supplementStatus)) {
				supplementStatusDesc = "开通仓库";
			}
			if ("3".equals(supplementStatus)) {
				supplementStatusDesc = "停补仓库";
			}
			if ("4".equals(supplementStatus)) {
				supplementStatusDesc = "开通仓+店";
			}
			if ("5".equals(supplementStatus)) {
				supplementStatusDesc = "停补仓+店";
			}
		}
		return supplementStatusDesc;
	}

	public String getStoreDesc() {
		if (store != null && !"".equals(store)) {
			if ("0".equals(store)) {
				storeDesc = "仓库";
			}
			if ("1".equals(store)) {
				storeDesc = "区域";
			}
			if ("2".equals(store)) {
				storeDesc = "pogCode";
			}
			if ("3".equals(store)) {
				storeDesc = "店铺";
			}
		}
		return storeDesc;
	}

	public String getConsultProductno() {
		return consultProductno;
	}

	public void setConsultProductno(String consultProductno) {
		this.consultProductno = consultProductno;
	}

	public String getConsultDate() {
		return consultDate;
	}

	public void setConsultDate(String consultDate) {
		this.consultDate = consultDate;
	}

	public String getConsultEnddate() {
		return consultEnddate;
	}

	public void setConsultEnddate(String consultEnddate) {
		this.consultEnddate = consultEnddate;
	}

	public String getStopReasonDesc() {
		if (stopReason != null && !"".equals(stopReason)) {
			if ("1".equals(stopReason)) {
				stopReasonDesc = "1.删图(TBD)";
			}
			if ("2".equals(stopReason)) {
				stopReasonDesc = "2.供应商停产/终止合作(TBD)";
			}
			if ("3".equals(stopReason)) {
				stopReasonDesc = "3.质量问题（附件&备注）";
			}
			if ("4".equals(stopReason)) {
				stopReasonDesc = "4.旧包装清货（附件）";
			}
			if ("5".equals(stopReason)) {
				stopReasonDesc = "5.进口货-有海关问题（附件）";
			}
			if ("6".equals(stopReason)) {
				stopReasonDesc = "6.季节性货品（附件）";
			}
			if ("7".equals(stopReason)) {
				stopReasonDesc = "7.店铺级别下图/撤柜";
			}
			if ("8".equals(stopReason)) {
				stopReasonDesc = "8.其他（附件&备注）";
			}
			if ("9".equals(stopReason)) {
				stopReasonDesc = "Bottom 2%";
			}
			if ("10".equals(stopReason)) {
				stopReasonDesc = "SMS stop order";
			}
		}
		return stopReasonDesc;
	}

	public String getStoreTypeDesc() {
		if (storeType != null && !"".equals(storeType)) {
			if ("0".equals(storeType)) {
				storeTypeDesc = "W(仓货)";
			}
			if ("1".equals(storeType)) {
				storeTypeDesc = "D(DSD)";
			}
			if ("2".equals(storeType)) {
				storeTypeDesc = "C(直流)";
			}
			if ("3".equals(storeType)) {
				storeTypeDesc = "W&D";
			}
		}
		return storeTypeDesc;
	}

	public String getSesionProductDesc() {
		if (sesionProduct != null && !"".equals(sesionProduct)) {
			if ("0".equals(sesionProduct)) {
				sesionProductDesc = "No";
			}
			if ("1".equals(sesionProduct)) {
				sesionProductDesc = "summer Only";
			}
			if ("2".equals(sesionProduct)) {
				sesionProductDesc = "summer & All Year";
			}
			if ("3".equals(sesionProduct)) {
				sesionProductDesc = "CNY Only";
			}
			if ("4".equals(sesionProduct)) {
				sesionProductDesc = "CNY & All Year";
			}
			if ("5".equals(sesionProduct)) {
				sesionProductDesc = "Winter Only";
			}
			if ("6".equals(sesionProduct)) {
				sesionProductDesc = "Winter & All Year";
			}
			if ("7".equals(sesionProduct)) {
				sesionProductDesc = "Christmas";
			}
		}
		return sesionProductDesc;
	}
}
