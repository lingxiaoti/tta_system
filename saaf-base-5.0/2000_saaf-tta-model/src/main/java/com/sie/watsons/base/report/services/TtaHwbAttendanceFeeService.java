package com.sie.watsons.base.report.services;

import com.alibaba.fastjson.JSON;
import com.sie.saaf.base.user.model.entities.readonly.BaseUsersPerson_HI_RO;
import com.sie.watsons.base.report.model.entities.TtaHwbAttendanceFeeEntity_HI;
import com.sie.watsons.base.report.model.entities.readonly.TtaHwbAttendanceFeeEntity_HI_RO;
import com.sie.watsons.base.report.model.inter.ITtaHwbAttendanceFee;

import com.alibaba.fastjson.JSONObject;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.paging.Pagination;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.common.model.inter.IBaseCommon;

@RestController
@RequestMapping("/ttaHwbAttendanceFeeService")
public class TtaHwbAttendanceFeeService extends CommonAbstractService{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaHwbAttendanceFeeService.class);

	@Autowired
	private ITtaHwbAttendanceFee ttaHwbAttendanceFeeServer;

	@Override
	public IBaseCommon getBaseCommonServer(){
		return this.ttaHwbAttendanceFeeServer;
	}
    /**
     * @param params    {
     *                  }
     * @param pageIndex
     * @param pageRows
     * @return
     * @description 查询列表（带分页）
     */
    @RequestMapping(method = RequestMethod.POST, value = "find")
    public String find(@RequestParam(required = false) String params,
                       @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
                       @RequestParam(required = false, defaultValue = "10") Integer pageRows) {
        try {

            JSONObject jsonObject = new JSONObject();
            if (StringUtils.isNotBlank(params)) {
                jsonObject = JSON.parseObject(params);
            }
            Pagination<TtaHwbAttendanceFeeEntity_HI_RO> result = ttaHwbAttendanceFeeServer.find(jsonObject, pageIndex, pageRows);
            System.out.print("ttaHwbAttendanceFeeService============");
            jsonObject = (JSONObject) JSON.toJSON(result);
            jsonObject.put(SToolUtils.STATUS, "S");
            jsonObject.put(SToolUtils.MSG, SUCCESS_MSG);
            return jsonObject.toString();
        } catch (IllegalArgumentException e) {
            LOGGER.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
        }
    }


    /**
     * @param params    {
     *                  }
     * @param pageIndex
     * @param pageRows
     * @return
     * @description 查询列表（带分页）
     */
    @RequestMapping(method = RequestMethod.POST, value = "findUser")
    public String findUser(@RequestParam(required = false) String params,
                       @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
                       @RequestParam(required = false, defaultValue = "5") Integer pageRows) {
        try {

            JSONObject jsonObject = new JSONObject();
            if (StringUtils.isNotBlank(params)) {
                jsonObject = JSON.parseObject(params);
            }
            Pagination<BaseUsersPerson_HI_RO> result = ttaHwbAttendanceFeeServer.findUser(jsonObject, pageIndex, pageRows);
            System.out.print("ttaHwbAttendanceFeeService============");
            jsonObject = (JSONObject) JSON.toJSON(result);
            jsonObject.put(SToolUtils.STATUS, "S");
            jsonObject.put(SToolUtils.MSG, SUCCESS_MSG);
            return jsonObject.toString();
        } catch (IllegalArgumentException e) {
            LOGGER.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
        }
    }
    /**
     * @param params
     * @description 保存/修改
     */
    @RequestMapping(method = RequestMethod.POST, value = "dataImport")
    public String dataImport(@RequestParam(required = true) String params) {
        try {
            int userId = getSessionUserId();
            JSONObject jsonObject = JSON.parseObject(params);
            TtaHwbAttendanceFeeEntity_HI instance = ttaHwbAttendanceFeeServer.saveOrUpdate(jsonObject, userId);
            return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, instance).toString();
        } catch (IllegalArgumentException e) {
            LOGGER.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
        }
    }

    /**
     * @param params
     * @description 保存/修改
     */
    @RequestMapping(method = RequestMethod.POST, value = "saveOrUpdate")
    public String saveOrUpdate(@RequestParam(required = true) String params) {
        try {
            int userId = getSessionUserId();
            JSONObject jsonObject = JSON.parseObject(params);
            TtaHwbAttendanceFeeEntity_HI instance = ttaHwbAttendanceFeeServer.saveOrUpdate(jsonObject, userId);
            return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, instance).toString();
        } catch (IllegalArgumentException e) {
            LOGGER.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
        }
    }
}