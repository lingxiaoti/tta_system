package com.sie.watsons.base.pon.itquotation.services;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.watsons.base.pon.itquotation.model.entities.readonly.EquPonQuoContentItEntity_HI_RO;
import com.sie.watsons.base.pon.itquotation.model.inter.IEquPonQuoContentIt;
import com.yhg.base.utils.SToolUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/equPonQuoContentItService")
public class EquPonQuoContentItService extends CommonAbstractService {
private static final Logger LOGGER = LoggerFactory.getLogger(EquPonQuoContentItService.class);
	@Autowired
	private IEquPonQuoContentIt equPonQuoContentItServer;
	public EquPonQuoContentItService() {
		super();
	}

    @Override
    public IBaseCommon<?> getBaseCommonServer() {
        return null;
    }

    @PostMapping("/findQuoContentIt")
    public String findQuoContentIt(@RequestParam("params") String params) {
        try {
            JSONObject jsonObject = parseObject(params);
            List<EquPonQuoContentItEntity_HI_RO> list = equPonQuoContentItServer.findQuoContentIt(jsonObject);
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, 0, list).toString();
        } catch (IllegalArgumentException e) {
            LOGGER.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, ERROR_MSG, 0, null).toString();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, "服务异常," + e.getMessage(), 0, null).toString();
        }
    }
}
