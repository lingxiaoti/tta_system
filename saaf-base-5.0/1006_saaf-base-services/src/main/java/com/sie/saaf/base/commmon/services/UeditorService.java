package com.sie.saaf.base.commmon.services;


import com.baidu.ueditor.ActionEnter;
import com.baidu.ueditor.define.BaseState;
import com.baidu.ueditor.define.State;
import com.sie.saaf.common.bean.ResultFileEntity;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.model.inter.IFastdfs;
import com.sie.saaf.common.model.inter.server.AliOssServer;
import com.sie.saaf.common.services.CommonAbstractService;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * 百度富文本编辑器 请求中心
 */
@RestController
@RequestMapping("/ueditorService")
public class UeditorService extends CommonAbstractService {
    private static final Logger log = LoggerFactory.getLogger(UeditorService.class);

    @Autowired
    private IFastdfs fastdfsServer;

    @CrossOrigin(origins = "*", maxAge = 3600)
    @RequestMapping(value = "/actionCenter", method = {RequestMethod.GET, RequestMethod.POST})
    public String actionCenter(@RequestParam(required = false) String host,
                               @RequestParam(required = false) Boolean simpleUploader,
                               @RequestParam(required = false) String action,
                               @RequestParam(required = false) String callback,
                               @RequestParam(value = "upfile", required = false) MultipartFile[] files) throws Exception {

        if ("config".equals(action)) {
            ActionEnter actionEnter = new ActionEnter(request, action, "");
            return actionEnter.exec(callback);
        }
        try {
            State state = new BaseState(true);
            List<ResultFileEntity> list = new ArrayList<>();
            Assert.notNull(files, "上传内容为空");
            Assert.notEmpty(files, "上传内容为空");
            for (MultipartFile file : files) {
                Assert.notNull(file, "上传内容为空");
                ResultFileEntity fileEntity = fastdfsServer.fileUpload(file.getInputStream(),file.getOriginalFilename());
                list.add(fileEntity);
                state.putInfo("name", fileEntity.getFileName());
                state.putInfo("original", fileEntity.getFileName());
                state.putInfo("type", fileEntity.getFileType());
                state.putInfo("url", fileEntity.getAccessPath());
            }
            if(BooleanUtils.isTrue(simpleUploader) && StringUtils.isNotBlank(host)) {
                //当文件只有一个的时候，重定向到指定的页面
                host = URLDecoder.decode(host, "utf-8");
                host = host + "?json=" + URLEncoder.encode(state.toJSONString(), "utf-8").replaceAll("\\+","");
                response.sendRedirect(host);
            }
            return state.toJSONString();
        } catch (IllegalArgumentException e) {
            log.warn(e.getMessage());
            return new BaseState(false, e.getMessage()).toJSONString();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new BaseState(false, e.getMessage()).toJSONString();
        }


    }


    @Override
    public IBaseCommon<?> getBaseCommonServer() {
        return null;
    }
}
