package com.sie.watsons.base.plmBase.model.entities.readonly;

import com.sie.watsons.base.plmBase.model.entities.PlmBrandMapEntity_HI;

/**
 * PlmBrandMapEntity_HI_RO Entity Object Fri May 22 16:25:08 CST 2020 Auto
 * Generate
 */

public class PlmBrandMapEntity_HI_RO extends PlmBrandMapEntity_HI {

	public static final String querySql = "\n"
			+ "SELECT pbm.brand_map_id as brandMapId\n"
			+ "      ,pbm.brand_cn_uda_id as brandCnUdaId\n"
			+ "      ,pbm.brand_cn_uda_value as brandCnUdaValue\n"
			+ "      ,pbm.brand_cn_uda_value_desc as brandCnUdaValueDesc\n"
			+ "      ,pbm.brand_en_uda_id as brandEnUdaId\n"
			+ "      ,pbm.brand_en_uda_value as brandEnUdaValue\n"
			+ "      ,pbm.brand_en_uda_value_desc as brandEnUdaValueDesc\n"
			+ "      ,pbm.version_num as versionNum\n"
			+ "      ,pbm.created_by as createdBy\n"
			+ "      ,pbm.creation_date as creationDate\n"
			+ "      ,pbm.last_updated_by as lastUpdatedBy\n"
			+ "      ,pbm.last_update_date as lastUpdateDate\n"
			+ "      ,pbm.last_update_login as lastUpdateLogin\n"
			+ "      ,pbm.bill_status as billStatus\n"
			+ "      ,pbm.bill_status_name as billStatusName\n"
			+ "      ,pbm.creator \n"
			+ "      ,pbm.process_reject as processReject"
			+ "      ,pbm.process_user as processUser \n"
			+ "      ,pbm.process_incident as processIncident \n"
			+ "      ,pbm.mother_company_uda_id as motherCompanyUdaId \n"
			+ "      ,pbm.mothercompany_uda_value as mothercompanyUdaValue \n"
			+ "      ,pbm.plm_mother_company as plmMotherCompany \n"
			+ "      ,pbm.groupbrand_id as groupbrandId \n"
			+ "      ,pbm.plm_group_brand as plmGroupBrand \n"
			+ "  FROM plm_brand_map pbm \n" + " WHERE 1 = 1 ";
}
