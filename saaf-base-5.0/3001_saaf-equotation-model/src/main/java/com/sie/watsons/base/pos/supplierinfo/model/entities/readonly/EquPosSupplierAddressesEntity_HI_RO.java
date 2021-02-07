package com.sie.watsons.base.pos.supplierinfo.model.entities.readonly;

import javax.persistence.Version;
import java.sql.Clob;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.sie.watsons.base.pos.archivesChange.beforeChange.model.entities.readonly.EquPosBgqCapacityEntity_HI_RO;
import com.sie.watsons.base.pos.archivesChange.beforeChange.model.entities.readonly.EquPosBgqProductionInfoEntity_HI_RO;

import javax.persistence.Transient;

/**
 * EquPosSupplierAddressesEntity_HI_RO Entity Object
 * Thu Sep 05 18:12:52 CST 2019  Auto Generate
 */

public class EquPosSupplierAddressesEntity_HI_RO {
	//供应商地址信息查询
	public  static  final String  QUERY_SQL =
			"SELECT sa.supplier_address_id supplierAddressId\n" +
					"      ,sa.supplier_id supplierId\n" +
					"      ,sa.address_name addressName\n" +
					"      ,sa.country\n" +
					"      ,sa.province\n" +
					"      ,sa.city\n" +
					"      ,sa.county\n" +
					"      ,sa.dept_code deptCode\n" +
					"      ,sa.detail_address detailAddress\n" +
					"      ,sa.version_num versionNum\n" +
					"      ,sa.creation_date creationDate\n" +
					"      ,sa.created_by createdBy\n" +
					"      ,sa.last_updated_by lastUpdatedBy\n" +
					"      ,sa.last_update_date lastUpdateDate\n" +
					"      ,sa.last_update_login lastUpdateLogin\n" +
					"      ,sa.attribute_category attributeCategory\n" +
					"      ,sa.attribute1\n" +
					"      ,sa.attribute2\n" +
					"      ,sa.attribute3\n" +
					"      ,sa.attribute4\n" +
					"      ,sa.attribute5\n" +
					"      ,sa.attribute6\n" +
					"      ,sa.attribute7\n" +
					"      ,sa.attribute8\n" +
					"      ,sa.attribute9\n" +
					"      ,sa.attribute10\n" +
					"FROM   equ_pos_supplier_addresses sa where 1 = 1 ";

	public  static  final String  QUERY_SQL_SUPPLIER_ADDRESS_REPORT_FORM = "SELECT t.supplier_id             supplierId\n" +
			"      ,t.supplier_number         supplierNumber\n" +
			"      ,t.supplier_name           supplierName\n" +
			"      ,t.supplier_short_name     supplierShortName\n" +
			"      ,t.supplier_in_charge_name supplierInChargeName\n" +
			"      ,t.supplier_status         supplierStatus\n" +
			"      ,t.supplier_type           supplierType\n" +
			"      ,t.country\n" +
			"      ,t.major_customer          majorCustomer\n" +
			"      ,t.company_description     companyDescription\n" +
			"      ,t.license_num             licenseNum\n" +
			"      ,t.category_count          categoryCount\n" +
			"      ,t.expire_days             expireDays\n" +
			"      ,t.address_name            addressName\n" +
			"      ,t.country_name            countryName\n" +
			"      ,t.province\n" +
			"      ,t.city\n" +
			"      ,t.county\n" +
			"      ,t.detail_address          detailAddress\n" +
			"      ,t.company_area            companyArea\n" +
			"      ,t.employee_profile        employeeProfile\n" +
			"      ,t.major_customers         majorCustomers\n" +
			"      ,t.sale_channel            saleChannel\n" +
			"      ,t.product_scope           productScope\n" +
			"      ,t.main_raw_materials      mainRawMaterials\n" +
			"      ,t.production_equipment    productionEquipment\n" +
			"      ,t.production_capacity     productionCapacity\n" +
			"      ,t.remark\n" +
			"FROM   (SELECT psi.supplier_id\n" +
			"              ,psi.supplier_number\n" +
			"              ,psi.supplier_name\n" +
			"              ,psi.supplier_short_name\n" +
			"              ,d.supplier_in_charge_name\n" +
			"              ,d.supplier_status\n" +
			"              ,d.supplier_type\n" +
			"              ,psi.country\n" +
			"              ,d.major_customer\n" +
			"              ,d.company_description\n" +
			"              ,sc.license_num\n" +
			"              ,(select count(1)\n" +
			"                from   equ_pos_supplier_product_cat t\n" +
			"                where  t.supplier_id = psi.supplier_id\n" +
			"                and    t.dept_code = d.dept_code\n" +
			"                and    t.category_id = :serviceScope) category_count\n" +
			"              ,(select nvl(MIN(TRUNC(nvl(ca.invalid_date, sysdate)) -\n" +
			"                               TRUNC(SYSDATE)),\n" +
			"                           9999999999)\n" +
			"                from   equ_pos_credentials_attachment ca\n" +
			"                where  ca.supplier_id = psi.supplier_id\n" +
			"                and    ca.deptment_code <> '0E'\n" +
			"                and    ca.is_product_factory = 'N'\n" +
			"                AND    ca.file_type is null\n" +
			"                and    ca.invalid_date >= sysdate) expire_days\n" +
			"              ,addr.address_name\n" +
			"              ,addr.country country_name\n" +
			"              ,addr.province\n" +
			"              ,addr.city\n" +
			"              ,addr.county\n" +
			"              ,addr.detail_address\n" +
			"              ,os.company_area\n" +
			"              ,os.employee_profile\n" +
			"              ,os.major_customers\n" +
			"              ,os.sale_channel\n" +
			"              ,pci.product_scope\n" +
			"              ,pci.main_raw_materials\n" +
			"              ,pci.production_equipment\n" +
			"              ,pci.production_capacity\n" +
			"              ,pci.remark\n" +
			"        FROM   equ_pos_supplier_info        psi\n" +
			"              ,equ_pos_supp_info_with_dept  d\n" +
			"              ,equ_pos_supplier_credentials sc\n" +
			"              ,equ_pos_supplier_addresses    addr\n" +
			"              ,equ_pos_operational_status    os\n" +
			"              ,equ_pos_capacity_info         pci\n" +
			"        WHERE  psi.supplier_id = d.supplier_id(+)\n" +
			"        AND    psi.supplier_id = sc.supplier_id(+)\n" +
			"        and    psi.supplier_id = addr.supplier_id(+)\n" +
			"        and    addr.supplier_address_id = os.supplier_address_id(+)\n" +
			"        and    addr.supplier_address_id = pci.supplier_address_id(+)\n" +
			"        AND    d.dept_code <> '0E'\n" +
			"        AND    sc.dept_code(+) <> '0E'\n" +
			"        and    addr.dept_code(+) <> '0E') t\n" +
			"where  1 = 1 ";

	private Integer supplierAddressId;
    private Integer supplierId;
    private String addressName;
    private String country;
    private String province;
    private String city;
    private String county;
    private String detailAddress;
    private String deptCode;
    private Integer versionNum;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private String attributeCategory;
    private String attribute1;
    private String attribute2;
    private String attribute3;
    private String attribute4;
    private String attribute5;
    private String attribute6;
    private String attribute7;
    private String attribute8;
    private String attribute9;
    private String attribute10;
    private Integer operatorUserId;

	private String supplierNumber;
	private String supplierName;
	private String supplierShortName;
	private String supplierInChargeName;
	private String supplierStatus;
	private String supplierType;
	private String countryName;
	private String majorCustomer;
	private Clob companyDescription;

	private Integer companyArea;
	private String employeeProfile;
	private String majorCustomers;
	private String saleChannel;
	private String productScope;
	private String mainRawMaterials;
	private String productionEquipment;
	private String productionCapacity;
	private String remark;

    private EquPosProductionInfoEntity_HI_RO oemProductionInfoParams;
    private List<EquPosCapacityInfoEntity_HI_RO> oemCapacityInfoParams;
	private EquPosProductionInfoEntity_HI_RO oemProductionInfoBgqParams;
	private List<EquPosCapacityInfoEntity_HI_RO> oemCapacityInfoBgqParams;

    private EquPosOperationalStatusEntity_HI_RO itOperationalInfoParams;
	private List<EquPosCapacityInfoEntity_HI_RO> itCapacityInfoParams;

	private List<EquPosCredentialsAttachmentEntity_HI_RO> surEnvironmentDataTable;
	private List<EquPosCredentialsAttachmentEntity_HI_RO> doorPlateDataTable;
	private List<EquPosCredentialsAttachmentEntity_HI_RO> receptionDataTable;
	private List<EquPosCredentialsAttachmentEntity_HI_RO> companyAreaDataTable;
	private List<EquPosCredentialsAttachmentEntity_HI_RO> officeSpaceDataTable;
	private List<EquPosCredentialsAttachmentEntity_HI_RO> employeeProfileDataTable;

	public void setSupplierAddressId(Integer supplierAddressId) {
		this.supplierAddressId = supplierAddressId;
	}

	
	public Integer getSupplierAddressId() {
		return supplierAddressId;
	}

	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}

	
	public Integer getSupplierId() {
		return supplierId;
	}

	public void setAddressName(String addressName) {
		this.addressName = addressName;
	}

	
	public String getAddressName() {
		return addressName;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	
	public String getCountry() {
		return country;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	
	public String getProvince() {
		return province;
	}

	public void setCity(String city) {
		this.city = city;
	}

	
	public String getCity() {
		return city;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	
	public String getCounty() {
		return county;
	}

	public void setDetailAddress(String detailAddress) {
		this.detailAddress = detailAddress;
	}

	
	public String getDetailAddress() {
		return detailAddress;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	
	public Integer getVersionNum() {
		return versionNum;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	
	public Integer getCreatedBy() {
		return createdBy;
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

	public void setAttributeCategory(String attributeCategory) {
		this.attributeCategory = attributeCategory;
	}

	
	public String getAttributeCategory() {
		return attributeCategory;
	}

	public void setAttribute1(String attribute1) {
		this.attribute1 = attribute1;
	}

	
	public String getAttribute1() {
		return attribute1;
	}

	public void setAttribute2(String attribute2) {
		this.attribute2 = attribute2;
	}

	
	public String getAttribute2() {
		return attribute2;
	}

	public void setAttribute3(String attribute3) {
		this.attribute3 = attribute3;
	}

	
	public String getAttribute3() {
		return attribute3;
	}

	public void setAttribute4(String attribute4) {
		this.attribute4 = attribute4;
	}

	
	public String getAttribute4() {
		return attribute4;
	}

	public void setAttribute5(String attribute5) {
		this.attribute5 = attribute5;
	}

	
	public String getAttribute5() {
		return attribute5;
	}

	public void setAttribute6(String attribute6) {
		this.attribute6 = attribute6;
	}

	
	public String getAttribute6() {
		return attribute6;
	}

	public void setAttribute7(String attribute7) {
		this.attribute7 = attribute7;
	}

	
	public String getAttribute7() {
		return attribute7;
	}

	public void setAttribute8(String attribute8) {
		this.attribute8 = attribute8;
	}

	
	public String getAttribute8() {
		return attribute8;
	}

	public void setAttribute9(String attribute9) {
		this.attribute9 = attribute9;
	}

	
	public String getAttribute9() {
		return attribute9;
	}

	public void setAttribute10(String attribute10) {
		this.attribute10 = attribute10;
	}

	
	public String getAttribute10() {
		return attribute10;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	public EquPosProductionInfoEntity_HI_RO getOemProductionInfoParams() {
		return oemProductionInfoParams;
	}

	public void setOemProductionInfoParams(EquPosProductionInfoEntity_HI_RO oemProductionInfoParams) {
		this.oemProductionInfoParams = oemProductionInfoParams;
	}

	public List<EquPosCapacityInfoEntity_HI_RO> getOemCapacityInfoParams() {
		return oemCapacityInfoParams;
	}

	public void setOemCapacityInfoParams(List<EquPosCapacityInfoEntity_HI_RO> oemCapacityInfoParams) {
		this.oemCapacityInfoParams = oemCapacityInfoParams;
	}

	public EquPosOperationalStatusEntity_HI_RO getItOperationalInfoParams() {
		return itOperationalInfoParams;
	}

	public void setItOperationalInfoParams(EquPosOperationalStatusEntity_HI_RO itOperationalInfoParams) {
		this.itOperationalInfoParams = itOperationalInfoParams;
	}

	public List<EquPosCapacityInfoEntity_HI_RO> getItCapacityInfoParams() {
		return itCapacityInfoParams;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public void setItCapacityInfoParams(List<EquPosCapacityInfoEntity_HI_RO> itCapacityInfoParams) {
		this.itCapacityInfoParams = itCapacityInfoParams;
	}

	public List<EquPosCredentialsAttachmentEntity_HI_RO> getSurEnvironmentDataTable() {
		return surEnvironmentDataTable;
	}

	public void setSurEnvironmentDataTable(List<EquPosCredentialsAttachmentEntity_HI_RO> surEnvironmentDataTable) {
		this.surEnvironmentDataTable = surEnvironmentDataTable;
	}

	public List<EquPosCredentialsAttachmentEntity_HI_RO> getDoorPlateDataTable() {
		return doorPlateDataTable;
	}

	public void setDoorPlateDataTable(List<EquPosCredentialsAttachmentEntity_HI_RO> doorPlateDataTable) {
		this.doorPlateDataTable = doorPlateDataTable;
	}

	public List<EquPosCredentialsAttachmentEntity_HI_RO> getReceptionDataTable() {
		return receptionDataTable;
	}

	public void setReceptionDataTable(List<EquPosCredentialsAttachmentEntity_HI_RO> receptionDataTable) {
		this.receptionDataTable = receptionDataTable;
	}

	public List<EquPosCredentialsAttachmentEntity_HI_RO> getCompanyAreaDataTable() {
		return companyAreaDataTable;
	}

	public void setCompanyAreaDataTable(List<EquPosCredentialsAttachmentEntity_HI_RO> companyAreaDataTable) {
		this.companyAreaDataTable = companyAreaDataTable;
	}

	public List<EquPosCredentialsAttachmentEntity_HI_RO> getOfficeSpaceDataTable() {
		return officeSpaceDataTable;
	}

	public void setOfficeSpaceDataTable(List<EquPosCredentialsAttachmentEntity_HI_RO> officeSpaceDataTable) {
		this.officeSpaceDataTable = officeSpaceDataTable;
	}

	public List<EquPosCredentialsAttachmentEntity_HI_RO> getEmployeeProfileDataTable() {
		return employeeProfileDataTable;
	}

	public void setEmployeeProfileDataTable(List<EquPosCredentialsAttachmentEntity_HI_RO> employeeProfileDataTable) {
		this.employeeProfileDataTable = employeeProfileDataTable;
	}

	public EquPosProductionInfoEntity_HI_RO getOemProductionInfoBgqParams() {
		return oemProductionInfoBgqParams;
	}

	public void setOemProductionInfoBgqParams(EquPosProductionInfoEntity_HI_RO oemProductionInfoBgqParams) {
		this.oemProductionInfoBgqParams = oemProductionInfoBgqParams;
	}

	public List<EquPosCapacityInfoEntity_HI_RO> getOemCapacityInfoBgqParams() {
		return oemCapacityInfoBgqParams;
	}

	public void setOemCapacityInfoBgqParams(List<EquPosCapacityInfoEntity_HI_RO> oemCapacityInfoBgqParams) {
		this.oemCapacityInfoBgqParams = oemCapacityInfoBgqParams;
	}

	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	public String getSupplierNumber() {
		return supplierNumber;
	}

	public void setSupplierNumber(String supplierNumber) {
		this.supplierNumber = supplierNumber;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getSupplierShortName() {
		return supplierShortName;
	}

	public void setSupplierShortName(String supplierShortName) {
		this.supplierShortName = supplierShortName;
	}

	public String getSupplierInChargeName() {
		return supplierInChargeName;
	}

	public void setSupplierInChargeName(String supplierInChargeName) {
		this.supplierInChargeName = supplierInChargeName;
	}

	public String getSupplierStatus() {
		return supplierStatus;
	}

	public void setSupplierStatus(String supplierStatus) {
		this.supplierStatus = supplierStatus;
	}

	public String getSupplierType() {
		return supplierType;
	}

	public void setSupplierType(String supplierType) {
		this.supplierType = supplierType;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getMajorCustomer() {
		return majorCustomer;
	}

	public void setMajorCustomer(String majorCustomer) {
		this.majorCustomer = majorCustomer;
	}

	public Clob getCompanyDescription() {
		return companyDescription;
	}

	public void setCompanyDescription(Clob companyDescription) {
		this.companyDescription = companyDescription;
	}

	public Integer getCompanyArea() {
		return companyArea;
	}

	public void setCompanyArea(Integer companyArea) {
		this.companyArea = companyArea;
	}

	public String getEmployeeProfile() {
		return employeeProfile;
	}

	public void setEmployeeProfile(String employeeProfile) {
		this.employeeProfile = employeeProfile;
	}

	public String getMajorCustomers() {
		return majorCustomers;
	}

	public void setMajorCustomers(String majorCustomers) {
		this.majorCustomers = majorCustomers;
	}

	public String getSaleChannel() {
		return saleChannel;
	}

	public void setSaleChannel(String saleChannel) {
		this.saleChannel = saleChannel;
	}

	public String getProductScope() {
		return productScope;
	}

	public void setProductScope(String productScope) {
		this.productScope = productScope;
	}

	public String getMainRawMaterials() {
		return mainRawMaterials;
	}

	public void setMainRawMaterials(String mainRawMaterials) {
		this.mainRawMaterials = mainRawMaterials;
	}

	public String getProductionEquipment() {
		return productionEquipment;
	}

	public void setProductionEquipment(String productionEquipment) {
		this.productionEquipment = productionEquipment;
	}

	public String getProductionCapacity() {
		return productionCapacity;
	}

	public void setProductionCapacity(String productionCapacity) {
		this.productionCapacity = productionCapacity;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
