package com.sie.saaf.business.services;

import com.sie.saaf.business.model.inter.ITtaObject;
import com.sie.saaf.business.model.inter.ITtaUserInterface;
import com.sie.saaf.business.model.inter.server.TtaUserInterfaceServer;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
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
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.util.*;

/**
 * @author hmb
 * @date 2019/8/15 16:04
 * 用户信息接口表,需要定时同步:每天凌晨一点跑
 */
@RestController
@RequestMapping("/ttaUserInterfaceService")
public class TtaUserInterfaceService extends CommonAbstractService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TtaUserInterfaceService.class);

    @Autowired
    private ITtaUserInterface iTtaUserInterface;

    @Override
    public IBaseCommon getBaseCommonServer() {
        return this.iTtaUserInterface;
    }

    /**
     *批量保存用户信息,------>EP系统提供的文件:User Interface.txt
     * @return
     */
    @RequestMapping("/saveBatchTtaUserInterfaceInfo")
    public String saveBatchTtaUserInterfaceInfo() {
        ITtaUserInterface ttaUserInterfaceServer = (ITtaUserInterface) SpringBeanUtil.getBean("ttaUserInterfaceServer");
        FTPClient ftpClient = null;
        FileInputStream ins = null;
        BufferedReader reader = null;
        try {
            //ftpClient = FTPUtil.getFTPClient(ResourceUtils.getFtpHost(), ResourceUtils.getFtpPassword(), ResourceUtils.getFtpUserName(), ResourceUtils.getFtpPort());
            //ins = ftpClient.retrieveFileStream("/TTA/ReportTo_20190628.txt");
            //DateUtil.getCurDateStr("yyyy")
            String filePath = "C:\\Users\\EDZ\\Desktop\\User Interface.txt";

            //注释不用
            //File filePath = new File(ResourceUtils.getBasePath() + "ITF_RMS2TTA_PURCHASE.txt");
            //ins = new BufferedInputStream(new FileInputStream(filePath));
            //this.batchSaveDataList("user_interface_in", ins , "\\|", 1000);

            File file = new File(filePath);
            ins = new FileInputStream(file);
            reader = new BufferedReader(new InputStreamReader(ins, "utf-8"));
            List<Map<String, Object>> dataList = SaafToolUtils.fileDataAssembleList(reader, "\\|");
            ttaUserInterfaceServer.deleteTtaUserInterface();
            ttaUserInterfaceServer.saveJdbcBatchObject("user_interface_in",dataList);
            ttaUserInterfaceServer.callProUpdateTtaUserInterface();

            //更新base_users表的数据
            //ttaUserInterfaceServer.updateUserInterfaceInfo();
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, 0, null).toString();
        } catch (Exception e) {
            LOGGER.error(this.getClass() + ".saveBatchTtaShopMarket保存文件数据异常：{}", e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, ERROR_MSG, 0, null).toString();
        } finally {
            SaafToolUtils.close(ftpClient, ins, reader);
        }
    }

    /**
     * 批量保存数据
     * @param table
     * @param bis
     * @return
     * @throws UnsupportedEncodingException
     * @throws IOException
     */
    private void batchSaveDataList(String table, BufferedInputStream bis, String separator, int rownumber) {
        int count = 0;
        BufferedReader reader = null;
        try {
            ITtaObject  ttaObjectServer = (ITtaObject) SpringBeanUtil.getBean("ttaObjectServer");
            reader = new BufferedReader(new InputStreamReader(bis, "UTF-8"), 50 * 1024 * 1024);
            int idx = -1;
            String line = null;
            List<Map<String, Object>> dataList = new  ArrayList<>();
            List<String> headList = null; //头部参数信息
            while (reader.ready()) {
                line = reader.readLine();
                if (StringUtils.isBlank(line)) {
                    continue;
                }
                idx ++;
                if (idx == 0) {
                    String[] keyArray = line.split(separator);
                    headList = Arrays.asList(keyArray);
                    continue;
                }
                if ("|".equalsIgnoreCase(String.valueOf(line.charAt(line.length() - 1)))) {
                    line += null;
                }
                String[] dataArray = line.split(separator);
                Map<String, Object> params = new HashMap<String, Object>();
                for (int index = 0; index < headList.size(); index ++) {
                    if (StringUtils.isBlank(dataArray[index]) || "null".equalsIgnoreCase(dataArray[index])) {
                        dataArray[index] = null;
                    } else {
                        String value = (dataArray[index] + "");
                        if (value.indexOf(".") == 0) {
                            value = "0" + value; //如果是格式,需变换( .12 --> 0.12)
                        }
                        dataArray[index] = value;
                    }
                    params.put(headList.get(index), dataArray[index]);
                }
                SaafToolUtils.setDefaultParams(params);
                dataList.add(params);
                if (dataList.size() % rownumber == 0) {
                    count ++;
                    ttaObjectServer.saveJdbcBatchObject(table, dataList);
                    LOGGER.info("当前操作的表:{}，batchSaveDataList 批量保存数:{}", table, rownumber * count);
                    dataList = new ArrayList<>();
                }
            }
            LOGGER.info("当前操作的表:{}，batchSaveDataList 批量保存数:{}", table, rownumber);
            ttaObjectServer.saveJdbcBatchObject(table, dataList);
        } catch (Exception e) {
            LOGGER.error(this.getClass() + ".文件操作数据异常：{}", e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception e) {
                    LOGGER.error(this.getClass() + ".文件流关闭数据异常：{}", e);
                }
            }
        }
    }


}
