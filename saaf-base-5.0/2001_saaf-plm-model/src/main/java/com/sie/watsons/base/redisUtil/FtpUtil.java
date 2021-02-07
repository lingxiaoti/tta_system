package com.sie.watsons.base.redisUtil;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;
import java.util.Map;

import com.sie.watsons.base.api.model.inter.server.PlmApiServer;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ftp上传下载工具类
 * <p>Title: FtpUtil</p>
 * <p>Description: </p>
 * <p>Company: www.itcast.com</p>
 * @author    入云龙
 * @date    2015年7月29日下午8:11:51
 * @version 1.0
 */
public class FtpUtil {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(FtpUtil.class);
    /**
     * 获取FTPClient对象
     *
     * @param ftpHost     FTP主机服务器
     * @param ftpPassword FTP 登录密码
     * @param ftpUserName FTP登录用户名
     * @param ftpPort     FTP端口 默认为21
     * @return
     */
    public static FTPClient getFTPClient(String ftpHost, String ftpUserName,
                                         String ftpPassword, int ftpPort) {
        LOGGER.info("登录ftp服务器: "+ftpHost+"," + ftpUserName+","+ftpPassword+","+ftpPort);
        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient = new FTPClient();
            ftpClient.connect(ftpHost, ftpPort);// 连接FTP服务器
            ftpClient.login(ftpUserName, ftpPassword);// 登陆FTP服务器
            if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
                LOGGER.info("未连接到FTP，用户名或密码错误。");
                ftpClient.disconnect();
            } else {
                LOGGER.info("FTP连接成功。");
            }
        } catch (SocketException e) {
            e.printStackTrace();
            LOGGER.info("FTP的IP地址可能错误，请正确配置。");
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.info("FTP的端口错误,请正确配置。");
        }
        return ftpClient;
    }

    /**
     * Description: 向FTP服务器上传文件
     * @param ftpHost FTP服务器hostname
     * @param ftpUserName 账号
     * @param ftpPassword 密码
     * @param ftpPort 端口
     * @param ftpPath  FTP服务器中文件所在路径 格式： ftptest/aa
     * @param fileName ftp文件名称
     * @param input 文件流
     * @return 成功返回true，否则返回false
     */

    public static boolean uploadFile(String ftpHost, String ftpUserName,
                                     String ftpPassword, int ftpPort, String ftpPath,
                                     String fileName,InputStream input) {
        boolean success = false;
        FTPClient ftpClient = null;
        try {
            int reply;
            ftpClient = getFTPClient(ftpHost, ftpUserName, ftpPassword, ftpPort);
            reply = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftpClient.disconnect();
                return success;
            }
            ftpClient.setControlEncoding("UTF-8"); // 中文支持
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftpClient.enterLocalPassiveMode();
            ftpClient.changeWorkingDirectory(ftpPath);
            LOGGER.info("上传ftp服务器");
            success = ftpClient.storeFile(fileName, input);

            input.close();
            ftpClient.logout();
//            success = true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (IOException ioe) {
                }
            }
        }
        return success;
    }

    /**
     * Description: 向FTP服务器上传多个文件
     * @param ftpHost FTP服务器hostname
     * @param ftpUserName 账号
     * @param ftpPassword 密码
     * @param ftpPort 端口
     * @param ftpPath  FTP服务器中文件所在路径 格式： ftptest/aa
     * @param fileInfoMap  <fileName,filePath></> ftp文件名称

     * @return 成功返回true，否则返回false
     */

    public static boolean uploadFileforMult(String ftpHost, String ftpUserName,
                                            String ftpPassword, int ftpPort, String ftpPath,
                                            Map<String,String> fileInfoMap) {
        boolean success = false;
        FTPClient ftpClient = null;
        try {
            int reply;
            ftpClient = getFTPClient(ftpHost, ftpUserName, ftpPassword, ftpPort);
            reply = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftpClient.disconnect();
                return success;
            }
            ftpClient.setControlEncoding("UTF-8"); // 中文支持
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftpClient.enterLocalPassiveMode();
            ftpClient.changeWorkingDirectory(ftpPath);
            for (Map.Entry<String,String> info:fileInfoMap.entrySet()) {
                LOGGER.info("上传ftp服务器: "+ info.getKey());
                FileInputStream input = new FileInputStream(info.getValue());
                success = ftpClient.storeFile(info.getKey(), input);
                input.close();
                new File(info.getValue()).delete();
            }
            ftpClient.logout();
//            success = true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (IOException ioe) {
                }
            }
        }
        return success;
    }


    /**
     * Description: 从FTP服务器下载文件
     * @param host FTP服务器hostname
     * @param port FTP服务器端口
     * @param username FTP登录账号
     * @param password FTP登录密码
     * @param remotePath FTP服务器上的相对路径
     * @param fileName 要下载的文件名
     * @param localPath 下载后保存到本地的路径
     * @return
     */
    public static boolean downloadFile(String host, int port, String username, String password, String remotePath,
                                       String fileName, String localPath) {
        boolean result = false;
        FTPClient ftp = new FTPClient();
        try {
            int reply;
            ftp.connect(host, port);
            // 如果采用默认端口，可以使用ftp.connect(host)的方式直接连接FTP服务器
            ftp.login(username, password);// 登录
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                return result;
            }
            ftp.changeWorkingDirectory(remotePath);// 转移到FTP服务器目录
            FTPFile[] fs = ftp.listFiles();
            for (FTPFile ff : fs) {
                if (ff.getName().equals(fileName)) {
                    File localFile = new File(localPath + "/" + ff.getName());
                    OutputStream is = new FileOutputStream(localFile);
                    ftp.retrieveFile(ff.getName(), is);
                    is.close();
                }
            }

            ftp.logout();
            result = true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException ioe) {
                }
            }
        }
        return result;
    }

    public static InputStream downloadFile(String host, int port, String username, String password, String remotePath) {
        FTPClient ftp = new FTPClient();
        try {
            int reply;
            ftp.connect(host, port);
            // 如果采用默认端口，可以使用ftp.connect(host)的方式直接连接FTP服务器
            boolean logSuc = ftp.login(username, password);// 登录
            LOGGER.info("FTP logging ========================================{}", logSuc);
            reply = ftp.getReplyCode();
            LOGGER.info("FTP reply ========================================{}", !FTPReply.isPositiveCompletion(reply));

            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                return null;
            }
//            ftp.changeWorkingDirectory(remotePath);// 转移到FTP服务器目录
            InputStream in = ftp.retrieveFileStream(remotePath);
//            FTPFile[] fs = ftp.listFiles();
//            for (FTPFile ff : fs) {
//                if (ff.getName().equals(fileName)) {
//                    File localFile = new File(localPath + "/" + ff.getName());
//                    OutputStream is = new FileOutputStream(localFile);
//                    ftp.retrieveFile(ff.getName(), is);
//                    is.close();
//                }
//            }
            LOGGER.info("FTP logging out ========================================{}", ftp.logout());
            return in;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException ioe) {
                }
            }
        }
        return null;
    }

    public static void main(String[] args) {
        try {
            FileInputStream in=new FileInputStream(new File("E:\\test.csv"));
            boolean flag = uploadFile("10.82.96.76", "gspkc", "123@abc#",21,  "/FTP","test.csv",  in);
            System.out.println(flag);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}