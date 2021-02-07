package com.sie.watsons.base.supplement.services;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.supplement.model.entities.TtaSideAgrtLineEntity_HI;
import com.sie.watsons.base.supplement.model.entities.readonly.TtaAttachmentDownloadEntity_HI_RO;
import com.sie.watsons.base.supplement.model.inter.ITtaAttachmentDownload;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.common.model.inter.IBaseCommon;

@RestController
@RequestMapping("/ttaAttachmentDownloadService")
public class TtaAttachmentDownloadService extends CommonAbstractService{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaAttachmentDownloadService.class);

	@Autowired
	private ITtaAttachmentDownload ttaAttachmentDownloadServer;

	@Override
	public IBaseCommon getBaseCommonServer(){
		return this.ttaAttachmentDownloadServer;
	}

	/**
	 * 查询下载信息
	 * @param params
	 * @param pageIndex
	 * @param pageRows
	 * @return
	 */
	@RequestMapping(value = "ttaAttachmentDownloadFind",method = RequestMethod.POST)
	public String ttaAttachmentDownloadFind(@RequestParam(required = false) String params,
											@RequestParam(required = false, defaultValue = "1") Integer pageIndex,
											@RequestParam(required = false, defaultValue = "5") Integer pageRows){
		try{
			JSONObject jsonObject = parseObject(params);
			Pagination<TtaAttachmentDownloadEntity_HI_RO> findList = ttaAttachmentDownloadServer.findAttachmentDownloadPagination(jsonObject, pageIndex, pageRows);
			JSONObject results = JSONObject.parseObject(JSON.toJSONString(findList));
			results.put(SToolUtils.STATUS, SUCCESS_STATUS);
			results.put(SToolUtils.MSG, "成功");
			return results.toString();
		}catch (Exception e){
			LOGGER.error(e.getMessage(),e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}

}