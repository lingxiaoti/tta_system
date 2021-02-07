package com.sie.saaf.sso.aop;//package com.sie.saaf.sso.aop;
//
//import com.alibaba.fastjson.JSONObject;
//import com.sie.saaf.commmon.constant.CloudInstanceNameConstants;
//import com.sie.saaf.commmon.util.SaafToolUtils;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.reflect.MethodSignature;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//
//import java.lang.reflect.Method;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//
///**
// * 记录所有请求的请求参数及返回结果至数据库  仅测试环境启用
// */
//@Aspect
//@Component
//public class RequestLogAspect {
//
//    private static final Logger log = LoggerFactory.getLogger(RequestLogAspect.class);
//
//    private static final ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
//
//
//    @Around("execution(* com.sie.saaf.*.*.services.*.*(..))") //指定拦截器规则
//    public Object Interceptor(ProceedingJoinPoint pjp) throws Throwable {
//
//        MethodSignature signature = (MethodSignature) pjp.getSignature();
//        Method method = signature.getMethod(); //获取被拦截的方法
//        StringBuilder requetUrl = new StringBuilder();
//        StringBuilder requestMethod = new StringBuilder();
//        RequestMapping servicesRequestMapping = method.getDeclaringClass().getAnnotation(RequestMapping.class);
//        if (servicesRequestMapping != null && servicesRequestMapping.value() != null && servicesRequestMapping.value().length > 0) {
//            requetUrl.append(servicesRequestMapping.value()[0]);
//        }
//        RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
//        if (requestMapping != null) {
//            if (requestMapping.value() != null && requestMapping.value().length != 0)
//                requetUrl.append(requestMapping.value()[0]);
//            if (requestMapping.path() != null && requestMapping.path().length != 0)
//                requetUrl.append(requestMapping.path()[0]);
//            if (requestMapping.method() != null) {
//                for (RequestMethod rmethod : requestMapping.method())
//                    requestMethod.append("/").append(rmethod.name());
//            }
//        }
//        if (requetUrl.toString().contains("baseRequestUrlService") || requetUrl.toString().contains("ueditorService") || requetUrl.toString().contains("fileUploadService")){
//            return pjp.proceed();
//        }
//
//        String[] params = signature.getParameterNames();
//        Object[] args = pjp.getArgs();
//        JSONObject param = new JSONObject();
//        if (params != null && args != null && params.length == args.length) {
//            for (int i = 0; i < params.length; i++) {
//                param.put(params[i], args[i]);
//            }
//        }
//
//        Object object = pjp.proceed();
//        saveRequest(requetUrl.toString(), requestMethod.toString(), param.toJSONString(), object);
//
//        return object;
//    }
//
//    private void saveRequest(String requetUrl, String requestMethod, String param, Object object) {
//        try {
//
//            Runnable runnable = new Runnable() {
//                @Override
//                public void run() {
//                    JSONObject jsonObject = new JSONObject().fluentClear()
//                            .fluentPut("requestUrl", requetUrl.toString())
//                            .fluentPut("requestMethod", requestMethod.toString())
//                            .fluentPut("requestBody", param)
//                            .fluentPut("response", object);
//                    SaafToolUtils.preaseServiceResultJSON(CloudInstanceNameConstants.INSTANCE_BASE + "/baseRequestUrlService/log", new JSONObject().fluentPut("params", jsonObject.toJSONString()));
//                }
//            };
//            pool.submit(runnable);
//        } catch (Exception e) {
//            log.error(e.getMessage(), e);
//        }
//
//
//    }
//}
