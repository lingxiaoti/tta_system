package com.sie.saaf.base.user.services;

import com.sie.saaf.base.user.model.inter.IBaseUserPassWordInit;
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
 * @createTime 2018-04-25 10:17
 * @description 初始化密码及邮件通知
 */
@RestController
@RequestMapping("/baseUserPassWordInitService")
public class BaseUserPassWordInitService {
    private static final Logger logger = LoggerFactory.getLogger(BaseUserPassWordInitService.class);

    @Autowired
    private IBaseUserPassWordInit baseUserPassWordInit;

    @RequestMapping(method = RequestMethod.GET,value="init")
    public String init(@RequestParam(required=false) String userName){
        try{
            baseUserPassWordInit.initPassWord(userName);
            baseUserPassWordInit.sendEmailBatch(userName);
            return SToolUtils.convertResultJSONObj("S", "操作成功", 0, null).toString();
        }catch(Exception e){
            logger.error(e.getMessage(),e);
            return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
        }
    }

    @RequestMapping(method = RequestMethod.GET,value="init82")
    public String init82(@RequestParam(required=false) String userName){
        try{
            baseUserPassWordInit.initPassWord82(userName);
            return SToolUtils.convertResultJSONObj("S", "操作成功", 0, null).toString();
        }catch(Exception e){
            logger.error(e.getMessage(),e);
            return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
        }
    }
}
