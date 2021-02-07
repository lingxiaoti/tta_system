package com.sie.watsons.base.pos.supplierinfo.services;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.watsons.base.pos.supplierinfo.model.entities.EquPosSuppInfoWithDeptEntity_HI;
import com.sie.watsons.base.pos.supplierinfo.model.inter.IEquPosSuppInfoWithDept;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.dao.ViewObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/equPosSuppInfoWithDeptService")
public class EquPosSuppInfoWithDeptService extends CommonAbstractService{
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosSuppInfoWithDeptService.class);

	@Autowired
	private IEquPosSuppInfoWithDept equPosSuppInfoWithDeptServer;
	@Autowired
    private ViewObject<EquPosSuppInfoWithDeptEntity_HI> suppInfoWithDeptDao;

	@Override
	public IBaseCommon getBaseCommonServer(){
		return this.equPosSuppInfoWithDeptServer;
	}

	/**
	 * 供应商基础信息查询-区分部门
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findSupplierInfoWithDept")
	public String findSupplierInfoWithDept(@RequestParam(required = false) String params,
											@RequestParam(required = false, defaultValue = "1") Integer pageIndex,
											@RequestParam(required = false, defaultValue = "10") Integer pageRows) {
		try {
			JSONObject paramsJONS =this.parseJson(params);
			JSONObject result  =equPosSuppInfoWithDeptServer.findSupplierInfoWithDept(paramsJONS,pageIndex,pageRows);
			result.put(SToolUtils.STATUS, "S");
			result.put(SToolUtils.MSG, SUCCESS_MSG);
			return result.toString();
		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * 供应商基础信息保存-区分部门
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "saveSupplierInfoWithDept")
	public String saveSupplierInfoWithDept(@RequestParam(required = false) String params) {
		try {
			JSONObject jsonObject = parseObject(params);
			EquPosSuppInfoWithDeptEntity_HI instance = equPosSuppInfoWithDeptServer.saveSupplierInfoWithDept(jsonObject);
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 1, instance).toString();
		}  catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		}
	}

    /**
     * 供应商基本信息保存
     */
    @RequestMapping(method = RequestMethod.POST, value = "saveForSupplierFilesDetail")
    public String saveForSupplierFilesDetail(@RequestParam(required = false) String params) {
        try {
            JSONObject paramsJONS =this.parseJson(params);
            Integer userId = getSessionUserId();
            EquPosSuppInfoWithDeptEntity_HI entityHi = equPosSuppInfoWithDeptServer.saveForSupplierFilesDetail(paramsJONS, userId);
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, 0, entityHi).toString();
        } catch (IllegalArgumentException e) {
            LOGGER.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
        }
    }
}