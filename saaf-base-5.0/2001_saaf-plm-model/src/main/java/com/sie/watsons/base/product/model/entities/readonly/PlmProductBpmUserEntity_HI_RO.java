package com.sie.watsons.base.product.model.entities.readonly;

import com.alibaba.fastjson.annotation.JSONField;
import com.sie.watsons.base.product.model.entities.PlmProductBpmUserEntity_HI;

import java.util.Date;

/**
 * PlmProductFileEntity_HI_RO Entity Object Thu Aug 29 10:51:48 CST 2019 Auto
 * Generate
 */

public class PlmProductBpmUserEntity_HI_RO extends PlmProductBpmUserEntity_HI {

	public static final String querySql = "\n" +
			"SELECT pbu.seq_id\n" +
			"      ,pbu.process_node_code\n" +
			"      ,pbu.process_node_name\n" +
			"      ,pbu.user_id\n" +
			"      ,pbu.user_name\n" +
			"      ,pbu.user_full_name\n" +
			"      ,pbu.owner_user_id\n" +
			"      ,pbu.version_num\n" +
			"      ,pbu.creation_date\n" +
			"      ,pbu.created_by\n" +
			"      ,pbu.last_update_date\n" +
			"      ,pbu.last_updated_by\n" +
			"      ,pbu.last_update_login\n" +
			"  FROM plm_product_bpm_user pbu\n" +
			" WHERE 1 = 1 ";
}
