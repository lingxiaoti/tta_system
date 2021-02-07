package com.sie.watsons.office.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.bean.ResultFileEntity;
import com.sie.saaf.common.model.entities.readonly.BaseAttachmentEntity_HI_RO;
import com.sie.saaf.common.model.inter.IBaseAttachment;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.model.inter.IFastdfs;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.saaf.common.util.Word2PdfUtil;
import com.sie.saaf.sso.model.inter.ISsoServer;
import com.sie.watsons.base.withdrawal.utils.ResourceUtils;
import com.sie.watsons.report.model.entities.TtaPrintRecordEntity_HI;
import com.sie.watsons.report.model.inter.ITtaPrintRecord;
import com.yhg.base.utils.SToolUtils;
import com.zhuozhengsoft.pageoffice.FileSaver;
import org.apache.commons.compress.utils.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

@RestController
@RequestMapping("/pageOfficeService")
public class PageOfficeService {
	private static final Logger LOGGER = LoggerFactory.getLogger(PageOfficeService.class);


	/**
	 * 添加PageOffice的服务器端授权程序Servlet（必须）
	 * @return
	 */
    @Autowired
    private IBaseAttachment baseAttachmentServer;

    @Autowired
    private IFastdfs fastdfsServer;

    @Autowired
    ISsoServer ssoServer;

	@Bean
	public ServletRegistrationBean servletRegistrationBean() {
		com.zhuozhengsoft.pageoffice.poserver.Server poserver = new com.zhuozhengsoft.pageoffice.poserver.Server();
		//设置PageOffice注册成功后,license.lic文件存放的目录
		String location  = ResourceUtils.getPageOfficecLicPath();
        File tmpFile = new File(location);
        if (!tmpFile.exists()) {
            tmpFile.mkdirs();
        }
		poserver.setSysPath(location);
		ServletRegistrationBean srb = new ServletRegistrationBean(poserver);
		srb.addUrlMappings("/pageOfficeService/poserver.zz");
		srb.addUrlMappings("/pageOfficeService/posetup.exe");
		srb.addUrlMappings("/pageOfficeService/pageoffice.js");
		srb.addUrlMappings("/pageOfficeService/jquery.min.js");
		srb.addUrlMappings("/pageOfficeService/pobstyle.css");
		srb.addUrlMappings("/pageOfficeService/sealsetup.exe");
		return srb;//
	}

    @RequestMapping(value = "openDoc", method = {RequestMethod.GET, RequestMethod.POST})
    public HttpServletResponse openDoc(@RequestParam String businessId,@RequestParam String Certificate,@RequestParam String type , HttpServletRequest request, HttpServletResponse response) {
        if (!ssoServer.authCookie(Certificate)) {
            throw new RuntimeException("无法打开文件，没有登录"+ Certificate + "-" +businessId);
        }
	    InputStream is = null;
        BaseAttachmentEntity_HI_RO tta_std_apply_header = baseAttachmentServer.findMaxBaseAttachmentInfo(Long.valueOf(businessId), type);
        if (!SaafToolUtils.isNullOrEmpty(tta_std_apply_header)) {
            // 读到流中
            is = fastdfsServer.getInputStream(tta_std_apply_header.getBucketName(), tta_std_apply_header.getPhyFileName());
        }

        try {
            response.reset();
            response.setContentType("application/msword");
            response.setHeader("Content-Disposition",
                    "attachment; filename=" + new String(tta_std_apply_header.getSourceFileName().getBytes(), "ISO-8859-1"));
            response.setHeader("Content-Length",String.valueOf(is.available()));
            OutputStream outputStream = response.getOutputStream();
            IOUtils.copy(is, outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e.getCause());
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return response;
    }

    @RequestMapping(value = "saveEdit", method = {RequestMethod.GET, RequestMethod.POST})
    public void saveEdit(@RequestParam String businessId,@RequestParam String Certificate,@RequestParam String type , HttpServletRequest request, HttpServletResponse response) {
        if (!ssoServer.authCookie(Certificate)) {
            throw new RuntimeException("无法打开文件，没有登录"+ Certificate + "-" +businessId);
        }
        try {
            BaseAttachmentEntity_HI_RO info = baseAttachmentServer.findMaxBaseAttachmentInfo(Long.valueOf(businessId), type);
            if (SaafToolUtils.isNullOrEmpty(type) || SaafToolUtils.isNullOrEmpty(businessId)) {
                response.getOutputStream().println("no access null type" + type + "businessId" + businessId );
                return;
            }
            FileSaver fs = new FileSaver(request, response);
            //fs.saveToFile("E:\\a.docx");
            ResultFileEntity resultFileEntity = fastdfsServer.fileUpload(fs.getFileStream(),info.getSourceFileName(), type, Long.valueOf(businessId), ssoServer.getUserSession(Certificate).getUserId());
            baseAttachmentServer.deleteById(info.getFileId());
            fastdfsServer.delete(info.getBucketName(), info.getPhyFileName());
            fs.close();
            response.flushBuffer();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param businessId
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "showPdf", method = {RequestMethod.GET, RequestMethod.POST})
    public void showPdf(@RequestParam String businessId,@RequestParam String Certificate,@RequestParam String type , HttpServletRequest request, HttpServletResponse response) {
        try {
            if (!ssoServer.authCookie(Certificate)) {
                throw new RuntimeException("无法打开文件，没有登录"+ Certificate + "-" +businessId);
            }
            Assert.notNull(businessId, "缺少参数 businessId");
            InputStream inputStream = null ;
            ByteArrayOutputStream byteOutputStream = null ;
            //补充协议
            BaseAttachmentEntity_HI_RO tta_std_apply_header = baseAttachmentServer.findMaxBaseAttachmentInfo(Long.valueOf(businessId), type);
            if (!SaafToolUtils.isNullOrEmpty(tta_std_apply_header)) {
                inputStream = fastdfsServer.getInputStream(tta_std_apply_header.getBucketName(), tta_std_apply_header.getPhyFileName());
                byteOutputStream = Word2PdfUtil.getDocToPdfOutputStream((ByteArrayInputStream)inputStream);
            }

            if (null != inputStream) {

                response.setContentType("application/pdf");
                //response.addHeader("x-frame-options", "AllowAll");
                OutputStream out;
                out = response.getOutputStream();
                out.write(byteOutputStream.toByteArray());
                out.flush();
                inputStream.close();
                out.close();
            }
        } catch (IllegalArgumentException e) {
            LOGGER.warn(e.getMessage());
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }
}