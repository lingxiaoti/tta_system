package com.sie.saaf.app.filter;

import com.sie.saaf.sso.filter.BaseFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

@Component
@ServletComponentScan
@WebFilter(urlPatterns = "/*", filterName = "cookieFilter")
public class CookieFilter extends BaseFilter {
    private static final Logger log = LoggerFactory.getLogger(CookieFilter.class);

    @Override
    protected void customDoFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
//        try {
//            //PDA请求的签名验证
//            HttpServletRequest httpServletRequest = ((HttpServletRequest)request);
//            String params = httpServletRequest.getParameter("params");
//            JSONObject queryParamJSON = JSONObject.parseObject(params);
//            if(queryParamJSON!=null) {
//				if (queryParamJSON.containsKey("signed")) {
//					String signed = queryParamJSON.getString("signed");
//					String sequenceId = queryParamJSON.getString("sequenceId");
//					String deviceId = queryParamJSON.getString("deviceId");
//
//					if (StringUtils.isBlank(deviceId)) {
//						deviceId = "-1";
//					}
//					String sdMD5 = DigestUtils.md5(sequenceId.concat(StringUtils.trimToEmpty(deviceId)));             //加密,用于作比较
//					String signedNew = new String(Base64.encodeBase64(sdMD5.getBytes()));
//					if (!StringUtils.equals(signed, signedNew)) {
//						PrintWriter out = response.getWriter();
//						out.print(SToolUtils.convertResultJSONObj("E", "MD5(序列号+设备ID) != 消息签名", 0, null));
//						out.flush();
//						out.close();
//						return;
//					}
//				}
//			}
//        } catch (IOException e) {
//            log.error(e.getMessage(),e);
//        }
        super.customDoFilter(request, response, chain);
    }
}