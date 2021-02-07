package com.sie.saaf.business.services;

import com.alibaba.fastjson.JSON;
import com.sie.saaf.business.model.entities.TtaMarketInEntity_HI;
import com.sie.saaf.business.model.entities.UserInterfaceInEntity_HI;
import com.sie.saaf.business.model.inter.ITtaMarketIn;
import com.sie.saaf.business.model.inter.IUserInterfaceIn;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.util.FTPUtil;
import com.sie.saaf.common.util.SpringBeanUtil;
import com.sie.saaf.schedule.utils.ResourceUtils;
import com.sie.saaf.schedule.utils.SaafToolUtils;
import com.yhg.base.utils.DateUtil;
import com.yhg.base.utils.SToolUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.util.*;

import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.common.model.inter.IBaseCommon;

@RestController
@RequestMapping("/userInterfaceInService")
public class UserInterfaceInService extends CommonAbstractService{
	private static final Logger LOGGER = LoggerFactory.getLogger(UserInterfaceInService.class);

	@Autowired
	private IUserInterfaceIn userInterfaceInServer;

	@Override
	public IBaseCommon getBaseCommonServer(){
		return this.userInterfaceInServer;
	}

	/**
	 * 保存用户信息表信息
	 */
	@RequestMapping("/saveBatchuserInterfaceIn")
	public void saveBatchuserInterfaceIn(/*JobExecutionContext context*/) {
		IUserInterfaceIn userInterfaceInServerr = (IUserInterfaceIn) SpringBeanUtil.getBean("userInterfaceInServer");
        FTPClient ftpClient = null;
        InputStream ins = null;
        BufferedReader reader = null;
        try {
            ftpClient = FTPUtil.getFTPClient(ResourceUtils.getUftpHost(), ResourceUtils.getUftpPassword(), ResourceUtils.getUftpUserName(), ResourceUtils.getUftpPort());
            ins = ftpClient.retrieveFileStream("/TTA/TTA_UserList.txt");

            //ins = new FileInputStream(new File("C:\\Users\\EDZ\\Desktop\\User Interface.txt"));
            //ins = new FileInputStream(new File("C:\\Users\\EDZ\\Desktop\\TTA_UserList.txt"));
            reader = new BufferedReader(new InputStreamReader(ins, "UTF-8"));
            List<Map<String, Object>> dataList = SaafToolUtils.fileDataAssembleList(reader,"\\|");
            //1.把文件中数据转化封装成set集合
            LinkedHashSet<UserInterfaceInEntity_HI> reportList = new LinkedHashSet(JSON.parseArray(SaafToolUtils.toJson(dataList), UserInterfaceInEntity_HI.class));
            //2.查询所有数据
            List<UserInterfaceInEntity_HI> findList = userInterfaceInServer.findList(new JSONObject());
            Map<String, UserInterfaceInEntity_HI> recordMap = new HashMap<>();
            for (UserInterfaceInEntity_HI entity_hi : findList) {
                recordMap.put(entity_hi.getEmployeeNo(), entity_hi);
            }
            //3.比对所有数据
            reportList.removeAll(findList);
            //4.差异数据可能是新增或者更新操作
            for (UserInterfaceInEntity_HI entity_hi : reportList) {
                UserInterfaceInEntity_HI entity = recordMap.get(entity_hi.getEmployeeNo());
                //做更新操作必须保证有id和版本号
                if (entity != null) {
                    entity_hi.setVersionNum(entity.getVersionNum());
                    entity_hi.setLastUpdateDate(new Date());
                    entity_hi.setCreationDate(entity.getCreationDate());
                }
            }
            //5.更新或保存差异数据
            LOGGER.info("用户信息差异数据条目数：" +  reportList.size());
            userInterfaceInServer.saveOrUpdateBatchUserInterfaceIn(reportList);
            //调存储过程,暂时注释,不用这种方式
            //userInterfaceInServer.callProUpdateTtaUserInterfaceIn();
            //更新base_users的数据 2019.8.16注释掉,不用
            /*userInterfaceInServer.updateUserInterfaceIn();*/
             } catch (Exception e) {
            LOGGER.error("用户信息批量保存文件数据异常：{}", e);
        } finally {
            SaafToolUtils.close(ftpClient, ins, reader);
        }
	}


}