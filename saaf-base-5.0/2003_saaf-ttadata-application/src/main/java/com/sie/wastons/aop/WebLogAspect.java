package com.sie.wastons.aop;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.io.InputStreamSource;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Closeable;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Enumeration;

@Slf4j
@Component
@Aspect
public class WebLogAspect {

    private ThreadLocal<Long> startTime = new ThreadLocal<>();

    private ThreadLocal<String> requesturl = new ThreadLocal<>();

    @Pointcut("@target(org.springframework.web.bind.annotation.RestController)")
    public void webLog1() {
    }

    @Pointcut(" execution(public * com.sie.wastons..services..*.*(..))")
    public void webLog2() {
    }


    @Before("webLog1() && webLog2()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        // 接收到请求，记录请求内容
        try {
            if (!log.isInfoEnabled())
                return;
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = attributes.getRequest();
            startTime.set(System.currentTimeMillis());
            requesturl.set(request.getRequestURL().toString());
            StringBuilder sb = new StringBuilder();
            Enumeration<String> headers = request.getHeaderNames();
            while (headers.hasMoreElements()) {
                String name = headers.nextElement();
                sb.append(name).append(":").append(request.getHeader(name));
                if (headers.hasMoreElements()) {
                    sb.append(",");
                }
            }
            if (log.isInfoEnabled()) {
                log.info("header:[\n{}\n]", sb);
            }

            String ip = request.getHeader("http_x_forwarded_for");
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("x-forwarded-for");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
            }


            // 记录下请求内容
            log.info("URL :{} ", requesturl.get());
            log.info("HTTP_METHOD : {}", request.getMethod());
            log.info("IP : {}", request.getRemoteAddr());
            log.info("CLASS_METHOD : {}", joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
            Object[] args = joinPoint.getArgs();

            if (args == null)
                return;
            sb.delete(0, sb.length());

            Signature signature = joinPoint.getSignature();
            MethodSignature methodSignature = (MethodSignature) signature;
            String[] strings = methodSignature.getParameterNames();
            if (strings == null || strings.length != args.length)
                return;
            for (int i = 0; i < strings.length; i++) {
                sb.append(strings[i]).append(":");
                Object arg = args[i];
                if (arg == null) {
                    sb.append("null").append("\n");
                } else if (arg instanceof Closeable || arg instanceof InputStreamSource || arg instanceof InputStreamSource[] || arg instanceof InputStream[] || arg instanceof HttpServletRequest || arg instanceof HttpServletResponse) {
                    sb.append(args.getClass().getName()).append("\n");
                } else {
                    sb.append(JSON.toJSON(arg)).append("\n");
                }
            }
            log.info("ARGS :[\n{}] ", sb);
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }
    }

    @AfterReturning(returning="returnVal", pointcut="webLog1() && webLog2()")
    public void doafterReturning(JoinPoint joinPoint,Object returnVal){
        if (log.isInfoEnabled()){
            if (returnVal instanceof String || returnVal instanceof JSONObject){
                log.info("{},返回:{}",requesturl.get(),returnVal);
            }else if ( returnVal instanceof Serializable){
                log.info("{},返回:{}",requesturl.get(), JSON.toJSON(returnVal));
            }
            startTime.remove();
            requesturl.remove();
        }
    }


    @After(value = "webLog1() && webLog2()")
    public void doafter(JoinPoint joinPoint) throws Throwable {
        if (log.isInfoEnabled() && startTime.get() != null) {
            log.info("{},执行用时:{} s", requesturl.get(), (System.currentTimeMillis() - startTime.get()) / 1000d);
        }
    }

}
