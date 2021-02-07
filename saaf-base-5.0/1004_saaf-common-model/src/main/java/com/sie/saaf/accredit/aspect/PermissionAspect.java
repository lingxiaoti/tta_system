package com.sie.saaf.accredit.aspect;


import com.alibaba.fastjson.JSON;
import com.sie.saaf.accredit.annotation.Permission;
import com.sie.saaf.common.bean.UserSessionBean;
import com.sie.saaf.common.exception.PermissionException;
import com.sie.saaf.common.model.inter.server.BaseAccreditCacheServer;
import com.yhg.redis.framework.JedisClusterCore;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;


/**
 *
 * 用于API级权限验证
 * author: duzh
 * date: 2018-07-12
 *
 */
@Component
@Aspect
@Order
public class PermissionAspect extends JedisClusterCore {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(PermissionAspect.class);

    /**
     * 切点表达式,拦截带有Permission注解的方法
     */
    @Pointcut("@annotation(com.sie.saaf.accredit.annotation.Permission)")
    public void controllerAspect(){};

    @Autowired
    private BaseAccreditCacheServer accreditCacheServer;

    /**
     * 在这里定义前置切面
     * 使用Around，用于权限未通过时终止方法执行
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("controllerAspect()")
    public Object beforeMethod(ProceedingJoinPoint joinPoint) throws Throwable {

        /**
         * 在Aspect得到request
         */
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        /**
         * 得到当前用户
         */
        String certificate = request.getHeader("Certificate");

        if (StringUtils.isBlank(certificate)) {
            throw new PermissionException("登录已失效");
        }

        String key = "cookie_" + certificate;
        String result = jedisCluster.hget(key,"sessionInfo");
        UserSessionBean userSessionBean = JSON.toJavaObject(JSON.parseObject(result), UserSessionBean.class);
        if (userSessionBean==null || userSessionBean.getUserId()==null)
            throw new PermissionException("登录已失效");
        Integer userId = userSessionBean.getUserId();

        /**
         * 得到当前URL对应的功能【菜单】及资源
         */
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature)signature;
        Method method = methodSignature.getMethod();
        Permission annotation = method.getAnnotation(Permission.class);
        String pcMenuCode = annotation.menuCode();
        String resourceCode = annotation.resourceCode();
        String appMenuCode=annotation.appMenuCode();

        String menuCode=userSessionBean.isFromApp()?appMenuCode:pcMenuCode;


        /**
         * 当前URL未指定功能
         */
        if(StringUtils.isBlank(menuCode)) {
            throw new PermissionException("后台接口权限配置错误");
        }

        /**
         * 判断用户是否有权限访问此功能
         */
        Integer responsibilityId =  accreditCacheServer.getOprResp(certificate,menuCode);
        request.setAttribute("currentOprRespId",responsibilityId);

        if(!accreditCacheServer.menuRespCacheExist(userId,responsibilityId,menuCode)){
            throw new PermissionException("当前用户没有权限访问此功能");
        }

        /**
         * 判断用户是否有权限方法此资源
         */
        if(StringUtils.isNotBlank(resourceCode)) {
            if(!accreditCacheServer.resourceCacheExist(menuCode,responsibilityId,resourceCode)) {
                throw new PermissionException("当前用户没有权限执行此操作");
            }
        }
        return joinPoint.proceed();
    }


}
