package com.sie.watsons.base.redisUtil;

import com.jcraft.jsch.*;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.*;

public class csvUtils {

    public static Boolean uploadFile(String user, String password, String host,
                                     String localFile, String path,Integer port,String open) {
        try {
            JSch jsch = new JSch();
            Session session = jsch.getSession(user, host, port);
            session.setPassword(password);
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();
            ChannelSftp sftpChannel = (ChannelSftp) session.openChannel(open);
            sftpChannel.connect();

            sftpChannel.put(localFile, path);

            sftpChannel.disconnect();
            session.disconnect();
        } catch (JSchException | SftpException e) {
            System.out.println(e);
        }
        return true;
    }

    public static Boolean uploadFileFtp(String user, String password, String host,
                                        FileInputStream localFile, String path, Integer port, String open) throws IOException {
        boolean result = false;
        FTPClient ftp = new FTPClient();
        ftp.enterLocalActiveMode();
//        ftp.enterLocalPassiveMode();
        ftp.setControlEncoding("UTF-8");
//        ftp.changeWorkingDirectory(path);
        try {
//        ftp =FTPUtil.getFTPClient(host, password, user, port);
            int reply;
            ftp.connect(host, port);// 连接FTP服务器
            // 如果采用默认端口，可以使用ftp.connect(host)的方式直接连接FTP服务器
            ftp.login(user, password);// 登录
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                return result;
            }
            //设置上传文件的类型为二进制类型
            ftp.setFileType(FTP.BINARY_FILE_TYPE);
            //上传文件

            if (!ftp.storeFile(path, localFile)) {
                localFile.close();
                ftp.logout();
                return result;
            }
            localFile.close();
            ftp.getReply();
            ftp.logout();
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException ioe) {
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        try {
            FileInputStream in=new FileInputStream(new File("E:\\tns.txt"));
            boolean flag = uploadFile("gspkc", "123@abc#", "10.82.96.76", "E:\\tns.txt", "/FTP",21, "tns.txt");
            System.out.println(flag);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
