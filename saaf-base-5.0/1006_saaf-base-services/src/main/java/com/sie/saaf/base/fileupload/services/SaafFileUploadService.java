package com.sie.saaf.base.fileupload.services;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.fileupload.model.entities.readonly.SaafFileUploadEntity_HI_RO;
import com.sie.saaf.base.fileupload.model.inter.ISaafFileUpload;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/saafFileUploadService")
public class SaafFileUploadService extends CommonAbstractService {
	private static final Logger LOGGER = LoggerFactory.getLogger(SaafFileUploadService.class);
	@Autowired
	private ISaafFileUpload saafFileUploadServer;

    public IBaseCommon getBaseCommonServer() {
        return saafFileUploadServer;
    }

	/**
	 * 查询
	 * 
	 * @param params
	 * @param
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "getSaafFileUploadList")
	public String getSaafFileUploadList(@RequestParam(required = false) String params, @RequestParam(required = true) Integer pageIndex,
                                        @RequestParam(required = true) Integer pageRows) {
		try {
			JSONObject jsonParam = this.parseObject(params);
			Pagination<SaafFileUploadEntity_HI_RO> respList = saafFileUploadServer.findSaafFileUploadList(jsonParam, pageIndex, pageRows);
            JSONObject json= JSON.parseObject(JSON.toJSONString(respList));
            json.put(SToolUtils.STATUS, "S");
            json.put(SToolUtils.MSG, SUCCESS_MSG);
			return json.toString();
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("查询上传文件信息失败:" + e);
			return SToolUtils.convertResultJSONObj("E", "查询上传文件信息失败!" + e, 0, null).toString();
		}
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "saveSaafFileUpload")
    public String saveSaafFileUpload(@RequestParam(required = false)
        String params) {
		try {
            JSONObject jsonParam = this.parseObject(params);
            JSONObject processJson = saafFileUploadServer.saveSaafFileUpload(jsonParam);
            if ("E".equals(processJson.get("status"))) {
                return SToolUtils.convertResultJSONObj("E", processJson.getString("msg"), 0, null).toString();
            } else {
                return SToolUtils.convertResultJSONObj("S", "文件上传成功!", 1, processJson.get("data")).toString();
            }
        }  catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("文件上传失败:" + e);
            return SToolUtils.convertResultJSONObj("E", "文件上传失败!" + e, 0, null).toString();
        }
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "deleteSaafFileUpload")
    public String deleteSaafFileUpload(@RequestParam(required = false)
        String params) {
		try {
            JSONObject jsonParam = this.parseObject(params);
            JSONObject processJson = saafFileUploadServer.deleteSaafFileUpload(jsonParam);
            if ("E".equals(processJson.get("status"))) {
                return SToolUtils.convertResultJSONObj("E", processJson.getString("msg"), 0, null).toString();
            } else {
                return SToolUtils.convertResultJSONObj("S", "文件删除成功!", 1, processJson.get("data")).toString();
            }
        }catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("文件删除失败:" + e);
            return SToolUtils.convertResultJSONObj("E", "文件删除失败!" + e, 0, null).toString();
        }
	}

}
