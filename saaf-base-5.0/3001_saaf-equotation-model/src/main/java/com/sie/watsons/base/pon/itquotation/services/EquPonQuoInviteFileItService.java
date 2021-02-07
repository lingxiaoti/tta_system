package com.sie.watsons.base.pon.itquotation.services;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.watsons.base.pon.itquotation.model.entities.readonly.EquPonQuoInviteFileItEntity_HI_RO;
import com.sie.watsons.base.pon.itquotation.model.inter.IEquPonQuoInviteFileIt;
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
@RequestMapping("/equPonQuoInviteFileItService")
public class EquPonQuoInviteFileItService extends CommonAbstractService {
    private static final Logger LOGGER = LoggerFactory.getLogger(EquPonQuoInviteFileItService.class);
    @Autowired
    private IEquPonQuoInviteFileIt equPonQuoInviteFileItServer;
    @Autowired
    private CommonMethods commonMethods;
    public EquPonQuoInviteFileItService() {
        super();
    }

    @Override
    public IBaseCommon<?> getBaseCommonServer() {
        return null;
    }

    @PostMapping("/findQuoInviteFileIt")
    public String findQuoInviteFileIt(@RequestParam("params") String params) {
        try {
            JSONObject jsonObject = parseObject(params);
            List<EquPonQuoInviteFileItEntity_HI_RO> list = equPonQuoInviteFileItServer.findQuoInviteFileIt(jsonObject);
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
     * 上传报价非价格文件附件
     * @param
     * @return
     */
    @PostMapping("/uploadQuoInviteFileItCommon")
    public String uploadQuoInviteFileItCommon(@RequestParam("file") MultipartFile file, HttpServletRequest request, HttpServletResponse response) {
        // 业务模块
        Integer userId = getSessionUserId();
        return commonMethods.fileUpload(file, request, response, "EQU_PON_QUO_INVITE_FILE_IT"
                , "EquPonQuoInviteFileItService",userId);
    }
}
