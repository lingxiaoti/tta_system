package com.sie.watsons.base.pon.itproject.services;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.watsons.base.pon.itproject.model.entities.readonly.EquPonItBusinessContactEntity_HI_RO;
import com.sie.watsons.base.pon.itproject.model.inter.IEquPonItBusinessContact;
import com.yhg.base.utils.SToolUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/equPonItBusinessContactService")
public class EquPonItBusinessContactService extends CommonAbstractService{
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPonItBusinessContactService.class);

	@Autowired
	private IEquPonItBusinessContact equPonItBusinessContactServer;

	@Override
	public IBaseCommon getBaseCommonServer(){
		return this.equPonItBusinessContactServer;
	}

    @PostMapping("/findItBusinessContact")
    public String findQuoContentIt(@RequestParam("params") String params) {
        try {
            JSONObject jsonObject = parseObject(params);
            List<EquPonItBusinessContactEntity_HI_RO> list = equPonItBusinessContactServer.findItBusinessContact(jsonObject);
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, 0, list).toString();
        } catch (IllegalArgumentException e) {
            LOGGER.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, ERROR_MSG, 0, null).toString();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, "服务异常," + e.getMessage(), 0, null).toString();
        }
    }

    /**
     * 报价管理-业务项目联系人查询，分页查询
     * @param params 参数：密级Entity中的字段
     * @param pageIndex
     * @param pageRows
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "findItBusinessContactInfo")
    public String findItBusinessContactInfo(@RequestParam(required = false) String params,
                                    @RequestParam(required = false,defaultValue = "1") Integer pageIndex,
                                    @RequestParam(required = false,defaultValue = "10") Integer pageRows) {
        try {
            JSONObject paramsJONS =this.parseJson(params);
            JSONObject result  =equPonItBusinessContactServer.findItBusinessContactInfo(paramsJONS,pageIndex,pageRows);
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
     * 报价管理-业务项目联系人删除
     * @param params 参数：密级Entity中的字段
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "deleteItBusinessContact")
    public String deleteItBusinessContact(@RequestParam(required = false) String params) {
        try {
            JSONObject jsonObject = parseObject(params);
            equPonItBusinessContactServer.deleteItBusinessContact(jsonObject);
            return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 1, null).toString();
        }  catch (IllegalArgumentException e) {
            LOGGER.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
        }
    }
}