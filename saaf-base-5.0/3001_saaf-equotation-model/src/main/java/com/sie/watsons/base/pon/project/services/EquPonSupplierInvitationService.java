package com.sie.watsons.base.pon.project.services;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.watsons.base.pon.project.model.entities.EquPonSupplierInvitationEntity_HI;
import com.sie.watsons.base.pon.project.model.inter.IEquPonSupplierInvitation;
import com.yhg.base.utils.SToolUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/equPonSupplierInvitationService")
public class EquPonSupplierInvitationService extends CommonAbstractService{
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPonSupplierInvitationService.class);

	@Autowired
	private IEquPonSupplierInvitation equPonSupplierInvitationServer;

	@Override
	public IBaseCommon getBaseCommonServer(){
		return this.equPonSupplierInvitationServer;
	}

	/**
	 * 报价管理-邀请供应商查询，分页查询
	 * @param params 参数：密级Entity中的字段
	 * @param pageIndex
	 * @param pageRows
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findSupplierInvitation")
	public String findSupplierInvitation(@RequestParam(required = false) String params,
								  @RequestParam(required = false,defaultValue = "1") Integer pageIndex,
								  @RequestParam(required = false,defaultValue = "999") Integer pageRows) {
		try {
			JSONObject paramsJONS =this.parseJson(params);
			JSONObject result  =equPonSupplierInvitationServer.findSupplierInvitation(paramsJONS,pageIndex,pageRows);
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
	 * 报价管理-邀请供应商保存
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "saveSupplierInvitation")
	public String saveSupplierInvitation(@RequestParam(required = false) String params) {
		try {
			JSONObject jsonObject = parseObject(params);
			EquPonSupplierInvitationEntity_HI instance = equPonSupplierInvitationServer.saveSupplierInvitation(jsonObject);
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
	 * 报价管理-邀请供应商删除
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "deleteSupplierInvitation")
	public String deleteSupplierInvitation(@RequestParam(required = false) String params) {
		try {
			JSONObject jsonObject = parseObject(params);
			equPonSupplierInvitationServer.deleteSupplierInvitation(jsonObject);
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 1, null).toString();
		}  catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * 报价管理-退出供应商报价
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "quitSupplierInvitation")
	public String quitSupplierInvitation(@RequestParam(required = false) String params) {
		try {
			JSONObject jsonObject = parseObject(params);
			equPonSupplierInvitationServer.quitSupplierInvitation(jsonObject);
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 1, null).toString();
		}  catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		}
	}
    /**
     * 发送报价邀请
     */
    @RequestMapping(method = RequestMethod.POST, value = "btnSendInvitation")
    public String btnSendInvitation(@RequestParam(required = true) String params) {
        try {
            Integer userId = getSessionUserId();
            JSONObject paramsJONS =this.parseJson(params);
            JSONObject result  =equPonSupplierInvitationServer.updateSendInvitation(paramsJONS,userId);
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
     * 报价管理-报价管理拒绝立项查询
     * @param params 参数：密级Entity中的字段
     * @param pageIndex
     * @param pageRows
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "findRejectSupplierInvitation")
    public String findRejectSupplierInvitation(@RequestParam(required = false) String params,
                                         @RequestParam(required = false,defaultValue = "1") Integer pageIndex,
                                         @RequestParam(required = false,defaultValue = "10") Integer pageRows) {
        try {
            JSONObject paramsJONS =this.parseJson(params);
            paramsJONS.put("userId", getSessionUserId());
            JSONObject result  =equPonSupplierInvitationServer.findRejectSupplierInvitation(paramsJONS,pageIndex,pageRows);
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
}