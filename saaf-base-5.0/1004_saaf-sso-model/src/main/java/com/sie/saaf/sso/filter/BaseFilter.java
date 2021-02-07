package com.sie.saaf.sso.filter;


import Edge.DES.DES;
import com.alibaba.fastjson.JSON;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.saaf.sso.model.inter.ISsoServer;
import com.yhg.base.utils.SToolUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisCluster;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;

public abstract class BaseFilter implements Filter {
    private static final Logger log= LoggerFactory.getLogger(BaseFilter.class);

    @Autowired
    ISsoServer ssoServer;

    @Autowired
    private JedisCluster jedisCluster;

    private Set<String> whiteList =new HashSet<>();

    List<String> originList = Arrays.asList("localhost","127.0.0.1","124.172.191.124","10.82.28.126","10.82.24.174","124.172.191.133","10.82.31.206","10.82.18.204");

    //Employee portal 跳转到tta系统的连接
    protected final static String EMPLOYEEPORTALTOTTA_LIST_URL = "/baseLoginService/loginTta";

    @Override
    public void init(FilterConfig arg0) throws ServletException {
        whiteList.add("baseUsersService/createPassword");
        whiteList.add("service/model");
        whiteList.add("service/editor");
        whiteList.add("bpmModelService/export");
        whiteList.add("dataExportService/export");
        whiteList.add("bpmModelService/findModels");
        whiteList.add("download");
        whiteList.add("fileDownload");
        whiteList.add("freeGoodsExcelEmport");
        whiteList.add("plmApiService/gainSalesOutlets");
        whiteList.add("plmApiService/obtainGoodsData");
        loadWhiteList();
    }

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        httpRequest.setCharacterEncoding("UTF-8");
        boolean isOrigin = true;
        HttpServletRequest req = (HttpServletRequest) request;
        String origin = req.getHeader("Origin");
        if (StringUtils.isNotBlank(origin)){
            for (String str : originList ){
                if(origin.contains(str)){
                    isOrigin = false;
                    ((HttpServletResponse) response).addHeader("Access-Control-Allow-Origin", origin);
                }
            }
            if(isOrigin){
                ((HttpServletResponse) response).addHeader("Access-Control-Allow-Origin", "http://localhost:80");
            }
        }
        ((HttpServletResponse) response).addHeader("x-frame-options", "SAMEORIGIN");
        ((HttpServletResponse) response).addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
        ((HttpServletResponse) response).addHeader("Access-Control-Allow-Headers", "X-Requested-With,X_Requested_With,X-PINGOTHER, Origin, XRequested-With, Content-Type, Accept, TokenCode,certificate,Cookie,x-imagetype,timestamp,pdasign,appsign,EmployyProtal");
        ((HttpServletResponse) response).setHeader("Access-Control-Allow-Credentials", "true");
        ((HttpServletResponse) response).addHeader("Access-Control-Max-Age", "1728000");
        ((HttpServletResponse) response).setHeader("Content-type", "text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");

        //不拦截option请求
        if (((HttpServletRequest) request).getMethod().equals("OPTIONS")){
            chain.doFilter(request, response);
            return;
        }
        //判断是否ep系统跳转过来的地址
        String requestURI = ((HttpServletRequest) request).getRequestURI();
        if(requestURI.contains(EMPLOYEEPORTALTOTTA_LIST_URL)){
            String empno = ((HttpServletRequest) request).getParameter("empno");
            String language = ((HttpServletRequest) request).getParameter("Language");
            String key = ((HttpServletRequest) request).getParameter("key");
            String check = ((HttpServletRequest) request).getParameter("check");

            if (StringUtils.isBlank(empno) && StringUtils.isBlank(key) && StringUtils.isBlank(check)) {
                PrintWriter out = response.getWriter();
                out.print(SToolUtils.convertResultJSONObj("timeout", "登录TTA系统信息有误,请联系IT部门", 0, null));
                out.flush();
                out.close();
                return;
            }

            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            //获取客户端地址
            String ipAddr = getIpAddr(httpServletRequest);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String currentDate = sdf.format(new Date());
            //String concat = ipAddr + "|" + currentDate;
            String concat = currentDate;
            log.info("格式化日期: " + concat);
            String emCheck = "";
            //解密check参数
            try {
                emCheck = DES.DecryptString(check,key);
                log.info("解密后的信息: " + emCheck);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                PrintWriter out = response.getWriter();
                out.print(SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null));
                out.flush();
                out.close();
            }

            if (StringUtils.isBlank(emCheck)) {
                PrintWriter out = response.getWriter();
                out.print(SToolUtils.convertResultJSONObj("timeout", "该URL已失效,不允许打开子系统,请重新登录再打开!", 0, null));
                out.flush();
                out.close();
                return;
            }
            int i = emCheck.indexOf("|");
            emCheck = emCheck.substring(i+1);
            System.out.println("截取日期字符串: " + emCheck);

            if (!concat.equals(emCheck)) {
                PrintWriter out = response.getWriter();
                out.print(SToolUtils.convertResultJSONObj("timeout", "该URL已失效,不允许打开子系统,请重新登录再打开!", 0, null));
                out.flush();
                out.close();
                return;
            }

            paramsLog((HttpServletRequest) request);
            chain.doFilter(request, response);
            return;
        }

        try {
            customDoFilter(request,response,chain);
            paramsLog((HttpServletRequest) request);
            String certificate = ((HttpServletRequest) request).getHeader("Certificate");
            String timestamp=((HttpServletRequest) request).getHeader("timestamp");
            String pdasign=((HttpServletRequest) request).getHeader("pdasign");
            String appsign=((HttpServletRequest) request).getHeader("appsign");
            String errorMsg="登录信息已失效，请重新登录";
            boolean checkSign=true;

            //pda请求签名校验
            if (StringUtils.isNotBlank(timestamp) && StringUtils.isNotBlank(pdasign)){
                log.info("内部请求 timestap:{},pdasign:{}",timestamp,pdasign);
                if (ssoServer.validatePdaSign(timestamp,pdasign)){
                    chain.doFilter(request, response);
                    return;
                }
                errorMsg="签名错误";
                checkSign=false;
            }else if (StringUtils.isNotBlank(timestamp) && StringUtils.isNotBlank(appsign)){
                //app请求签名校验
                log.info("app请求 timestap:{},pdasign:{}",timestamp,appsign);
                errorMsg="签名错误";
                if (appsign.equals(SaafToolUtils.buildAPPSign(Long.valueOf(timestamp))) ){
                    if ( ssoServer.authCookie(certificate)){
                        chain.doFilter(request, response);
                        return;
                    }else {
                        errorMsg="请重新登录";
                    }
                }
                checkSign=false;
            }

            //登录校验
            String requestUrl=((HttpServletRequest)request).getRequestURI();
            loadWhiteList();
            for (String url: whiteList){
                //如果包含的就跳过
                if (requestUrl.contains(url)){
                    chain.doFilter(request, response);
                    return;
                }
            }
            boolean authCookie = ssoServer.authCookie(certificate);
            log.info("登录log信息certificate:{}, checkSign:{}, authCookie:{}", certificate, checkSign, authCookie);
            //不在白名单列表中的,验证cookie
            if (StringUtils.isBlank(certificate) || !checkSign || !authCookie) {
                PrintWriter out = response.getWriter();
                out.print(SToolUtils.convertResultJSONObj("timeout", errorMsg, 0, null));
                out.flush();
                out.close();
                return;
            }
            chain.doFilter(request,response);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    protected void customDoFilter(ServletRequest request, ServletResponse response, FilterChain chain){

    }

    private void paramsLog(HttpServletRequest request){
        try {

            String url =request.getRequestURI();
            if (StringUtils.isNotBlank(url) && url.contains("baseRequestUrlService/log")){
                return;
            }

            log.info("请求服务服务[{}]：{}",request.getMethod(),request.getRequestURI());
            Enumeration<String> enumeration= request.getParameterNames();
            String key=null;
            while (enumeration.hasMoreElements()){
                key=enumeration.nextElement();
                log.info("{}:{}",key,request.getParameter(key));
            }
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }
    }

    private void loadWhiteList(){
        //"baseSystemSsoService,baseLoginService,ueditorService,baseUsersService/passwordReminder,baseOrganizationService,baseUsersService,wxLoginService,/sleep,/health,/info,/websocket,service/editor,bpmModelService/findModels"
        String str= jedisCluster.hget("GLOBAL_REDIS_CACHE","FILTER_WHITE_LIST");
        //当前方法的作用 是判断是空的，空格，制表符，tab r如果是空的就直接返回
        if (StringUtils.isBlank(str)){
            return;
        }
        String[] strs=str.split(",");
        //这些字符都是以逗隔开的
        for (String string:strs){
            if (string==null){
                continue;
            }
            //加入到白名单
            whiteList.add(string.trim());
        }
        log.debug("拦截器白名单:{}",JSON.toJSON(whiteList));
    }

    private String getIpAddr(HttpServletRequest request) {
        log.info("获取ip:http_x_forwarded_for:{},x-forwarded-for:{}",request.getHeader("HTTP_X_FORWARDED_FOR"),request.getHeader("x-forwarded-for"));
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
        return ip;
    }
}
