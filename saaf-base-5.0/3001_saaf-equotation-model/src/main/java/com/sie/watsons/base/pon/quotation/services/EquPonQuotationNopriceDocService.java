package com.sie.watsons.base.pon.quotation.services;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.watsons.base.pon.quotation.model.entities.readonly.EquPonQuotationNopriceDocEntity_HI_RO;
import com.sie.watsons.base.pon.quotation.model.inter.IEquPonQuotationNopriceDoc;
import com.sie.watsons.base.pos.utils.CommonMethods;
import com.yhg.base.utils.SToolUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/equPonQuotationNopriceDocService")
public class EquPonQuotationNopriceDocService extends CommonAbstractService{
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPonQuotationNopriceDocService.class);

	@Autowired
	private IEquPonQuotationNopriceDoc equPonQuotationNopriceDocServer;
    @Autowired
    private CommonMethods commonMethods;

	@Override
	public IBaseCommon getBaseCommonServer(){
		return this.equPonQuotationNopriceDocServer;
	}

    @PostMapping("/findQuotationNopriceInfo")
    public String findQuotationNopriceInfo(@RequestParam("params") String params) {
        try {
            JSONObject jsonObject = parseObject(params);
            List<EquPonQuotationNopriceDocEntity_HI_RO> quotationNopriceInfo = equPonQuotationNopriceDocServer.findQuotationNopriceInfo(jsonObject);
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, 0, quotationNopriceInfo).toString();
        } catch (IllegalArgumentException e) {
            LOGGER.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, ERROR_MSG, 0, null).toString();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, "服务异常," + e.getMessage(), 0, null).toString();
        }
    }

    /**
     * 上传报价非价格文件附件
     * @param
     * @return 
     */
    @PostMapping("/uploadNonPriceFileCommon")
    public String uploadNonPriceFileCommon(@RequestParam("file") MultipartFile file, HttpServletRequest request, HttpServletResponse response) {
        // 业务模块
        Integer userId = getSessionUserId();
        return commonMethods.fileUpload(file, request, response, "EQU_PON_QUOTATION_NOPRICE_DOC"
                , "EquPonQuotationNopriceDocService",userId);
    }
}