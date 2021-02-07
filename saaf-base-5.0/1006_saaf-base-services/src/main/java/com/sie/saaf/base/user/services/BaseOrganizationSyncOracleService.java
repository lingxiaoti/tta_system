package com.sie.saaf.base.user.services;

import com.sie.saaf.base.user.model.inter.IBaseOrganizationSync;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.yhg.base.utils.SToolUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Chenzg
 * @creteTime 2018-04-13
 */
@RestController
@RequestMapping("/baseOrganizationSyncOracleService")
public class BaseOrganizationSyncOracleService extends CommonAbstractService {
    private static final Logger logger = LoggerFactory.getLogger(BaseOrganizationSyncOracleService.class);

    @Autowired
    private IBaseOrganizationSync baseOrganizationSyncServer;

    @Override
    public IBaseCommon<?> getBaseCommonServer() {
        return baseOrganizationSyncServer;
    }


    @RequestMapping(method = RequestMethod.POST, value = "sync")
    public String sync(@RequestParam(required = false) String params) {
        try {
            this.baseOrganizationSyncServer.syncOrganizations();
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "同步成功", 0, null).toString();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }
}
