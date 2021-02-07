package com.sie.saaf.common.bean;

import java.util.List;

public class InspectionReportEntity {
	String org_id;
	String channel_type;
	String user_type;
	String item_code;
	String item_desc;
	String manufacture_date;
	String manufacture_batch;
	String examination_date;
	String report_type;
	String report_type_desc;
	String report_no;
	String contract_no;
	String test_organization;
	String report_name;
	String status;
	String statusName;
	String barcode_batch;
	String report_id;
	String varification_date;
	String varification_by;
	String is_valid;
	String creation_date;
	String created_by;
	String last_update_date;
	String last_updated_by;
	String last_update_login;
	String item_desc_encode;
	List<InspectionReportFileEntity> images;

	public String getOrg_id() {
		return org_id;
	}

	public void setOrg_id(String org_id) {
		this.org_id = org_id;
	}

	public String getChannel_type() {
		return channel_type;
	}

	public void setChannel_type(String channel_type) {
		this.channel_type = channel_type;
	}

	public String getUser_type() {
		return user_type;
	}

	public void setUser_type(String user_type) {
		this.user_type = user_type;
	}

	public String getItem_code() {
		return item_code;
	}

	public void setItem_code(String item_code) {
		this.item_code = item_code;
	}

	public String getItem_desc_encode() {
		return item_desc;
	}

	public String getItem_desc() {
		return item_desc;
	}

	public void setItem_desc(String item_desc) {
		this.item_desc = item_desc;
	}

	public String getManufacture_date() {
		return manufacture_date;
	}

	public void setManufacture_date(String manufacture_date) {
		this.manufacture_date = manufacture_date;
	}

	public String getManufacture_batch() {
		return manufacture_batch;
	}

	public void setManufacture_batch(String manufacture_batch) {
		this.manufacture_batch = manufacture_batch;
	}

	public String getExamination_date() {
		return examination_date;
	}

	public void setExamination_date(String examination_date) {
		this.examination_date = examination_date;
	}

	public String getReport_type() {
		return report_type;
	}

	public void setReport_type(String report_type) {
		this.report_type = report_type;
	}

	public String getReport_type_desc() {
		return report_type_desc;
	}

	public void setReport_type_desc(String report_type_desc) {
		this.report_type_desc = report_type_desc;
	}

	public String getReport_no() {
		return report_no;
	}

	public void setReport_no(String report_no) {
		this.report_no = report_no;
	}

	public String getContract_no() {
		return contract_no;
	}

	public void setContract_no(String contract_no) {
		this.contract_no = contract_no;
	}

	public String getTest_organization() {
		return test_organization;
	}

	public void setTest_organization(String test_organization) {
		this.test_organization = test_organization;
	}

	public String getReport_name() {
		return report_name;
	}

	public void setReport_name(String report_name) {
		this.report_name = report_name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getBarcode_batch() {
		return barcode_batch;
	}

	public void setBarcode_batch(String barcode_batch) {
		this.barcode_batch = barcode_batch;
	}

	public String getReport_id() {
		return report_id;
	}

	public void setReport_id(String report_id) {
		this.report_id = report_id;
	}

	public String getVarification_date() {
		return varification_date;
	}

	public void setVarification_date(String varification_date) {
		this.varification_date = varification_date;
	}

	public String getVarification_by() {
		return varification_by;
	}

	public void setVarification_by(String varification_by) {
		this.varification_by = varification_by;
	}

	public String getIs_valid() {
		return is_valid;
	}

	public void setIs_valid(String is_valid) {
		this.is_valid = is_valid;
	}

	public String getCreation_date() {
		return creation_date;
	}

	public void setCreation_date(String creation_date) {
		this.creation_date = creation_date;
	}

	public String getCreated_by() {
		return created_by;
	}

	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}

	public String getLast_update_date() {
		return last_update_date;
	}

	public void setLast_update_date(String last_update_date) {
		this.last_update_date = last_update_date;
	}

	public String getLast_updated_by() {
		return last_updated_by;
	}

	public void setLast_updated_by(String last_updated_by) {
		this.last_updated_by = last_updated_by;
	}

	public String getLast_update_login() {
		return last_update_login;
	}

	public void setLast_update_login(String last_update_login) {
		this.last_update_login = last_update_login;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public List<InspectionReportFileEntity> getImages() {
		return images;
	}

	public void setImages(List<InspectionReportFileEntity> images) {
		this.images = images;
	}
}
