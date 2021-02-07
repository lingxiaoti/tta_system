package com.sie.saaf.common.util;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ListObjectsRequest;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import com.sie.saaf.common.bean.AttachmentBean;
import com.sie.saaf.common.constant.CommonConstants;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;
import sun.net.ftp.FtpClient;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FTPUtil {
	private static Logger logger = Logger.getLogger(FTPUtil.class);
	/** 
	 * Description: 向FTP服务器上传文件 
	 * @Version1.0 Jul 27, 2008 4:31:09 PM by 崔红保（cuihongbao@d-heaven.com）创建 
	 * @param url FTP服务器hostname 
	 * @param port FTP服务器端口 
	 * @param username FTP登录账号 
	 * @param password FTP登录密码 
	 * @param path FTP服务器保存目录 
	 * @param filename 上传到FTP服务器上的文件名 
	 * @param inputStream 输入流
	 * @return 成功返回true，否则返回false 
	 */  
	public static boolean uploadFile(String url,int port,String username, String password, String path, String filename, InputStream inputStream ) {  
	    boolean success = false;  
	 /*   FTPClient ftp = new FTPClient();  */
	    FtpClient ftp =null; 
	   OutputStream os = null; 
	    try {  
	    	ftp =FtpClient.create(); 
	    	ftp.connect(new InetSocketAddress(url, port));      
	       ftp.login(username, password.toCharArray());//登录  
	       ftp.setBinaryType();   
	       //ftp.
	       
           String remoteFile = path+"/"+filename;
           os = ftp.putFileStream(remoteFile,true); 
           //创建一个缓冲区 
           byte[] bytes = new byte[1024]; 
           int c; 
           while ((c = inputStream.read(bytes)) != -1) { 
               os.write(bytes, 0, c); 
           } 
           //System.out.println("upload success"); 
	        success = true;  
	    } catch (Exception e) {  
	    	logger.error(e.getMessage());
	    } finally {
	    	
                if(inputStream != null){ 
                	try {
						inputStream.close();
					} catch (IOException e) {
						logger.error(e.getMessage());
					} 
                }
                
                   if(os !=null){
                	   try {
						os.close();
					} catch (IOException e) {
						logger.error(e.getMessage());
					}  
                   }
                        
                  
	    	  if(ftp != null) {
				  try {
					  ftp.close();
				  } catch (IOException e) {
					  logger.error(e.getMessage());
				  }
			  }
	    }  
	    return success;    
}
	
	
	/** 
	 * Description: 向FTP服务器上传文件 
	 * @Version1.0 Jul 27
	 * @param path FTP服务器保存目录
	 * @param filename 上传到FTP服务器上的文件名 
	 * @return 成功返回true，否则返回false
	 */  
	public static boolean uploadOssClientFile(String bucketName, OSSClient client, String path, String filename, InputStream inputStream ) {
	    boolean success = false;  
	 /*   FTPClient ftp = new FTPClient();  */
	 /*   FtpClient ftp =null; 
	   OutputStream os = null; */
	    try {  
	      /* ftp =FtpClient.create(); 
	       ftp.connect(new InetSocketAddress(url, port));      
	       ftp.login(username, password.toCharArray());//登录  
	       ftp.setBinaryType();   */
	       //ftp.
	       
         /*  String remoteFile = path+"/"+filename;
           os = ftp.putFileStream(remoteFile,true); */
        /*   //创建一个缓冲区 
           byte[] bytes = new byte[1024]; 
           int c; 
           while ((c = inputStream.read(bytes)) != -1) { 
               os.write(bytes, 0, c); 
           } */
           //System.out.println("upload success");
	    	client.putObject(bucketName, path+"/"+filename, inputStream);
	    	
	        success = true;  
	    } catch (Exception e) {  
	    	logger.error(e.getMessage());
	    } 
	    return success;    
}
		
	/** 
	 * Description: 从FTP服务器下载文件 
	 * @Version1.0 Jul 27, 2008 5:32:36 PM by 崔红保（cuihongbao@d-heaven.com）创建 
	 * @param url FTP服务器hostname 
	 * @param port FTP服务器端口 
	 * @param username FTP登录账号 
	 * @param password FTP登录密码 
	 * @param remotePath FTP服务器上的相对路径 
	 * @param fileName 要下载的文件名 
	 * @param localPath 下载后保存到本地的路径 
	 * @param realName 下载后的文件名
	 * @return 
	 */ 
//	public static long downFile(String url, int port,String username, String password, String remotePath,String fileName,ByteArrayOutputStream os) {  
//	    long result = 0;  
//	    FtpClient ftp =null;
//       InputStream is = null;
//	    try { 
//	       ftp =FtpClient.create(); 
//	       ftp.connect(new InetSocketAddress(url, port));
//	       ftp.setConnectTimeout(20000);
//	       ftp.login(username, password.toCharArray());//登录  
//	       ftp.setBinaryType();    
//            is = ftp.getFileStream(remotePath+"/"+fileName); 
//            byte[] bytes = new byte[1024*8];  
//            int c;  
//            while ((c = is.read(bytes)) != -1) {  
//                os.write(bytes, 0, c);  
//                result = result + c;  
//            }  
//          
//        } catch (Exception ex) { 
//
//            throw new RuntimeException(ex); 
//        } finally{
//        	try {
//				ftp.close();
//			} catch (IOException e) {
//				logger.error(e.getMessage());
//			}
//        }
//	    return result;  
// 
//	}
	
	/**
     * 下载文件
     * @param hostname FTP服务器地址
     * @param port   FTP服务器端口号
     * @param username   FTP登录帐号
     * @param password   FTP登录密码
     * @param pathname   FTP服务器文件目录
     * @param filename   文件名称
     * @return
     */
    public static boolean downloadFileNew(String hostname, int port, String username, String password, String pathname, String filename, OutputStream out){
        boolean flag = false;
        FTPClient ftpClient = null;
        try {
            //连接FTP服务器
            ftpClient = FTPUtil.getFTPClient(hostname, password, username,port);  
            ftpClient.setControlEncoding("UTF-8"); // 中文支持  
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);  
            ftpClient.enterLocalPassiveMode();  
            //验证FTP服务器是否登录成功
            int replyCode = ftpClient.getReplyCode();
            if(!FTPReply.isPositiveCompletion(replyCode)){
                return flag;
            }
            //切换FTP目录
            ftpClient.changeWorkingDirectory(pathname);
            FTPFile[] ftpFiles = ftpClient.listFiles();
            for(FTPFile file : ftpFiles){
                if(filename.equalsIgnoreCase(file.getName())){
                    ftpClient.retrieveFile(file.getName(), out);
                }
            }
            ftpClient.logout();
            flag = true;
        } catch (Exception e) {
			logger.error(e.getMessage(),e);
        } finally{
            if(ftpClient.isConnected()){
                try {
                    ftpClient.logout();
                } catch (IOException e) {
                 
                }
            }
        }
        return flag;
    }
    
    
	/**
     * 下载文件
     * @param pathname   FTP服务器文件目录
     * @param filename   文件名称
     * @return
     */
    public static boolean downloadOssFileNew(OSSClient client, String bucketName, String pathname, String filename, OutputStream out){
        boolean flag = false;
        boolean flagOss = true; 
        String maker = "";
   
        try {
            if(pathname.startsWith("/")){
            	pathname = pathname.substring(1,pathname.length());
            }
        	 
       
            do
            {
            // 构造ListObjectsRequest请求
             ListObjectsRequest listObjectsRequest = new ListObjectsRequest(bucketName);
             listObjectsRequest.setPrefix(pathname);
             listObjectsRequest.setMarker(maker);
                //listObjectsRequest.setMaxKeys(1000);
             ObjectListing listing = client.listObjects(listObjectsRequest);
                
             byte[] bytes = new byte[1024*8];  
       
            for (OSSObjectSummary objectSummary : listing.getObjectSummaries()) {
            	 if((pathname+"/"+filename).equalsIgnoreCase(objectSummary.getKey())){
            		 OSSObject ossObject = client.getObject(bucketName, objectSummary.getKey());
            		 InputStream in = ossObject.getObjectContent();
            	      int c;  
                      while ((c = in.read(bytes)) != -1) {  
                    	  out.write(bytes, 0, c);  
                     }
                      in.close();
            		 break;
            	 }
            }
            maker = listing.getNextMarker();
            flagOss = listing.isTruncated();
            
            } while(flagOss);

            flag = true;
        } catch (Exception e) {
			logger.error(e.getMessage(),e);
        }
        return flag;
    }
    
    
    /**
     * 下载文件
     * @param hostname FTP服务器地址
     * @param port   FTP服务器端口号
     * @param username   FTP登录帐号
     * @param password   FTP登录密码
     * @param pathname   FTP服务器文件目录
     * @param filename   文件名称
     * @return
     */
    public static boolean downloadFileNomalNew(String hostname, int port, String username, String password, String pathname, String filename, OutputStream out){
        boolean flag = false;
        FTPClient ftpClient = null;
        try {
            //连接FTP服务器
            ftpClient = FTPUtil.getFTPClient(hostname, password, username,port);  
            ftpClient.setControlEncoding("UTF-8"); // 中文支持  
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);  
            ftpClient.enterLocalPassiveMode();  
            //验证FTP服务器是否登录成功
            int replyCode = ftpClient.getReplyCode();
            if(!FTPReply.isPositiveCompletion(replyCode)){
                return flag;
            }
            //切换FTP目录
            ftpClient.changeWorkingDirectory(pathname);
            ftpClient.retrieveFile(filename, out);
            ftpClient.logout();
            flag = true;
        } catch (Exception e) {
			logger.error(e.getMessage(),e);
        } finally{
            if(ftpClient.isConnected()){
                try {
                    ftpClient.logout();
                } catch (IOException e) {
                 
                }
            }
        }
        return flag;
    }
    
    /**
     * 下载文件
     * @param pathname   FTP服务器文件目录
     * @param filename   文件名称
     * @return
     */
    public static boolean downloadOssFileNomalNew(OSSClient client, String bucketName, String pathname, String filename, OutputStream out){
        boolean flag = false;
    /*    FTPClient ftpClient = null;*/
        if(pathname.startsWith("/")){
        	pathname = pathname.substring(1,pathname.length());
        }
        try {
       	 OSSObject ossObject = client.getObject(bucketName, pathname+"/"+filename);
		 InputStream in = ossObject.getObjectContent();
	      int c; 
	      byte[] bytes = new byte[1024*8];   
          while ((c = in.read(bytes)) != -1) {  
        	  out.write(bytes, 0, c);  
         }
          in.close();
            flag = true;
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
        return flag;
    }
    
    
   
    public static InputStream downloadOssFileZipNew(OSSClient client, String bucketName, String pathname, String filename){

        InputStream in = null;  
        logger.info("开始读取绝对路径" + pathname + "文件!"); 
        if(pathname.startsWith("/")){
        	pathname = pathname.substring(1,pathname.length());
        }
        try {  
			/*if (ftpClient == null || ftpClient.isConnected() == false) {
				ftpClient = FTPUtil.getFTPClient(hostname, password, username, port);
			} 
			
            ftpClient.setControlEncoding("UTF-8"); // 中文支持  
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);  
            ftpClient.enterLocalPassiveMode();  
            ftpClient.changeWorkingDirectory(pathname);  
            in = ftpClient.retrieveFileStream(filename);  */
        	OSSObject ossObject = client.getObject(bucketName, pathname+"/"+filename);
        	in = ossObject.getObjectContent();
        }  catch (Exception e) {
			logger.error(e.getMessage(),e);
        }
        return in;
    }  
    
    public static InputStream downloadFileZipNew(String hostname, int port, String username, String password, String pathname, String filename,FTPClient ftpClient){

        InputStream in = null;  
        logger.info("开始读取绝对路径" + pathname + "文件!");  
        try {  
			if (ftpClient == null || ftpClient.isConnected() == false) {
				ftpClient = FTPUtil.getFTPClient(hostname, password, username, port);
			} 
			
            ftpClient.setControlEncoding("UTF-8"); // 中文支持  
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);  
            ftpClient.enterLocalPassiveMode();  
            ftpClient.changeWorkingDirectory(pathname);  
            in = ftpClient.retrieveFileStream(filename);  
            
        } catch (FileNotFoundException e) {  
            logger.error("没有找到" + pathname + "文件");
        } catch (SocketException e) {  
            logger.error("连接FTP失败.");  
        } catch (IOException e) {
            logger.error("文件读取错误。");
        }
        return in;
    }  
    
    /** 
     * 获取FTPClient对象 
     * @param ftpHost FTP主机服务器 
     * @param ftpPassword FTP 登录密码 
     * @param ftpUserName FTP登录用户名 
     * @param ftpPort FTP端口 默认为21 
     * @return 
     */  
    public static FTPClient getFTPClient(String ftpHost, String ftpPassword,  
            String ftpUserName, int ftpPort) {  
        FTPClient ftpClient = null;  
        try {  
            ftpClient = new FTPClient();  
            ftpClient.connect(ftpHost, ftpPort);// 连接FTP服务器  
            ftpClient.login(ftpUserName, ftpPassword);// 登陆FTP服务器  
            if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {  
                logger.info("未连接到FTP，用户名或密码错误。");  
                ftpClient.disconnect();  
            } else {  
                logger.info("FTP连接成功。");  
            }  
        } catch (SocketException e) {  
            logger.info("FTP的IP地址可能错误，请正确配置。");
        } catch (IOException e) {  
            logger.info("FTP的端口错误,请正确配置。");
        }  
        return ftpClient;  
    }  
	/** 
	 * Description: 从FTP服务器下载文件 
	 * @Version1.0 ray 
	 * @param remotePath FTP服务器上的相对路径
	 * @param fileName 要下载的文件名 
	 * @return
	 */ 
	public static InputStream getInputStream(FtpClient ftp,String remotePath,String fileName) {  
	    long result = 0;  
       InputStream is = null;
	    try { 
	       ftp.setBinaryType();    
           is = ftp.getFileStream(remotePath+"/"+fileName); 
        } catch (Exception ex) { 
        	logger.error(ex.getMessage());
            throw new RuntimeException(ex); 
        }
	    return is;  
 
	}
	/** 
	 * Description: 从FTP服务器下载文件 
	 * @Version1.0 Jul 27, 2008 5:32:36 PM by 崔红保（cuihongbao@d-heaven.com）创建 
	 * @param url FTP服务器hostname 
	 * @param port FTP服务器端口 
	 * @param username FTP登录账号 
	 * @param password FTP登录密码 
	 * @param path FTP服务器上的相对路径 
	 * @return 
	 */ 
	public static boolean makeDir(String url, int port,String username, String password,String path){
		boolean success = false;   
		FTPClient ftp = new FTPClient(); 
		    try {  
		        int reply;  
		        ftp.connect(url, port);  
		        //如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器  
		        ftp.login(username, password);//登录  
		        reply = ftp.getReplyCode();  
		        if (!FTPReply.isPositiveCompletion(reply)) {  
		            ftp.disconnect();  
		            return success;  
		        } 
		        success = ftp.makeDirectory(path);
		        }catch (IOException e) {  
			        logger.error(e.getMessage());  
			    } finally {  
			        if (ftp.isConnected()) {  
			            try {  
			                ftp.disconnect();  
			            } catch (IOException ioe) {  
			            }  
			        }  
			    }
		    return success;	
	}
	
	
	
	
//	public static void main(String[] args) {
//		String url = CommonConstants.IMPORT_IP;
//		String port = CommonConstants.IMPORT_PORT;
//		String username = CommonConstants.IMPORT_ADMIN_USER;
//		String password = CommonConstants.IMPORT_ADMIN_PASSWORD;
//		Calendar now = Calendar.getInstance();
//		boolean status = FTPUtil.makeDir(url, Integer.parseInt(port),username, password,"/"+now.get(Calendar.YEAR));
//		System.out.println(status);
//		status =FTPUtil.makeDir(url, Integer.parseInt(port), username, password, "/"+(now.get(Calendar.MONTH) + 1));
//		System.out.println(status);
//		status = FTPUtil.makeDir(url, Integer.parseInt(port), username, password, "/"+now.get(Calendar.DAY_OF_MONTH));
//		System.out.println(status);
//	}
	

	/**
	 * 打包批量下载文件
	 * 
	 * @param myAttachmentBean
	 * @param response
	 * @param fileName
	 * @throws Exception
	 */
	public static void batchDownFileZip(List<AttachmentBean> myAttachmentBean, HttpServletResponse response, String fileName, boolean original_filename) throws Exception {

		ServletOutputStream os = response.getOutputStream();
		response.setContentType("application/x-download");// 设置response内容的类型为附件类型
		response.setHeader("Content-disposition", "attachment;filename=" + fileName);// 设置输出头，即要输出的文件名称
		// List rs = myAttachmentBean;//得到要打包的文件
		ZipOutputStream out = new ZipOutputStream(os);// 用于ZIP打包
		out.setEncoding("utf-8");

		FTPClient ftpClient = null;
		String url = CommonConstants.IMPORT_IP;
		String port = CommonConstants.IMPORT_PORT;
		String username = CommonConstants.IMPORT_ADMIN_USER;
		String password = CommonConstants.IMPORT_ADMIN_PASSWORD;
		ftpClient = FTPUtil.getFTPClient(url, password, username, Integer.parseInt(port));
		ftpClient.setControlEncoding("UTF-8"); // 中文支持
		ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
		ftpClient.enterLocalPassiveMode();
		Map<String, Integer> mm = new HashMap<String, Integer>();
		for (AttachmentBean file : myAttachmentBean) {
			InputStream input = null;
			try {
				ftpClient.changeWorkingDirectory("/");
				// ftpClient.changeToParentDirectory();
				ftpClient.changeWorkingDirectory(file.getFilePath());
				input = ftpClient.retrieveFileStream(file.getPhyFileName());
			} catch (FileNotFoundException e) {
				logger.error("没有找到" + file.getPhyFileName() + "文件");
			} catch (SocketException e) {
				logger.error("首次连接FTP失败." + file.getPhyFileName() + "文件下载失败,进行重试下载当前文件");

				try {
					input = ftpClient.retrieveFileStream(file.getPhyFileName());
				} catch (SocketException e2) {
					logger.error("第1次,重试连接FTP失败." + file.getPhyFileName() + "文件下载失败");

					try {
						input = ftpClient.retrieveFileStream(file.getPhyFileName());
					} catch (SocketException e3) {
						logger.error("第2次,重试连接FTP失败." + file.getPhyFileName() + "文件下载失败");

						try {
							input = ftpClient.retrieveFileStream(file.getPhyFileName());
						} catch (SocketException e4) {
							logger.error("第3次,重试连接FTP失败." + file.getPhyFileName() + "文件下载失败");
						}

					}

				}

			} catch (IOException e) {
				logger.error("文件读取错误。" + file.getPhyFileName() + "文件下载失败");
			}

			if (input != null) {

				String filename = file.getFileDesc() + "_" + file.getSourceFileName();
				if (original_filename == false) {
					if (mm.get(file.getFileDesc()) == null) {
						mm.put(file.getFileDesc(), 0);
						filename = file.getFileDesc()+"."+file.getFileType();
					} else {
						Integer index = mm.get(file.getFileDesc());
						filename = file.getFileDesc() + "(" + (index + 1) + ")"+"."+file.getFileType();
					}
 
				}

				out.putNextEntry(new ZipEntry(filename));// 建立打包中的文件名称(报告名称)

				byte[] content = new byte[1024 * 8];
				int len;
				while ((len = input.read(content)) != -1) {
					out.write(content, 0, len);
				}
				input.close();
				ftpClient.completePendingCommand();
			}

		}

		try {
			ftpClient.disconnect();
		} catch (IOException e) {
			logger.error(e.getMessage(),e);
		}
		try {
			out.close();
			os.flush(); // 将缓冲区中的数据全部写出
			os.close(); // 关闭流
		} catch (IOException e) {
			logger.error(e.getMessage(),e);
		}

	}
	/**
	 * 打包批量下载文件
	 * 
	 * @param myAttachmentBean
	 * @param response
	 * @param fileName
	 * @throws Exception
	 */
	public static void batchDownOssFileZip(OSSClient client, String bucketName, List<AttachmentBean> myAttachmentBean, HttpServletResponse response, String fileName, boolean original_filename) throws Exception {

		ServletOutputStream os = response.getOutputStream();
		response.setContentType("application/x-download");// 设置response内容的类型为附件类型
		response.setHeader("Content-disposition", "attachment;filename=" + fileName);// 设置输出头，即要输出的文件名称
		// List rs = myAttachmentBean;//得到要打包的文件
		ZipOutputStream out = new ZipOutputStream(os);// 用于ZIP打包
		out.setEncoding("utf-8");

		Map<String, Integer> mm = new HashMap<String, Integer>();
		for (AttachmentBean file : myAttachmentBean) {
			InputStream input = null;
			String pathname ="";
			try {
				if(!StringUtils.isEmpty(file.getFilePath())){
				if(file.getFilePath().startsWith("/")){
					pathname = file.getFilePath().substring(1,file.getFilePath().length());
				}
				}
				
				OSSObject ossObject = client.getObject(bucketName, pathname+"/"+file.getPhyFileName());
				input = ossObject.getObjectContent();
				
			} catch (Exception e) {
				logger.error("首次连接OSS失败." + file.getPhyFileName() + "文件下载失败,进行重试下载当前文件");

				try {
					OSSObject ossObject = client.getObject(bucketName, file.getFilePath()+"/"+file.getPhyFileName());
					input = ossObject.getObjectContent();
				} catch (Exception e2) {
					logger.error("第1次,重试连接OSS失败." + file.getPhyFileName() + "文件下载失败");

					try {
						OSSObject ossObject = client.getObject(bucketName, file.getFilePath()+"/"+file.getPhyFileName());
						input = ossObject.getObjectContent();
					} catch (Exception e3) {
						logger.error("第2次,重试连接FTP失败." + file.getPhyFileName() + "文件下载失败");

						try {
							OSSObject ossObject = client.getObject(bucketName, file.getFilePath()+"/"+file.getPhyFileName());
							input = ossObject.getObjectContent();
						} catch (Exception e4) {
							logger.error("第3次,重试连接OSS失败." + file.getPhyFileName() + "文件下载失败");
						}

					}

				}

			}

			if (input != null) {

				String filename = file.getFileDesc() + "_" + file.getSourceFileName();
				if (original_filename == false) {
					if (mm.get(file.getFileDesc()) == null) {
						mm.put(file.getFileDesc(), 0);
						filename = file.getFileDesc()+"."+file.getFileType();
					} else {
						Integer index = mm.get(file.getFileDesc());
						filename = file.getFileDesc() + "(" + (index + 1) + ")"+"."+file.getFileType();
					}
 
				}

				out.putNextEntry(new ZipEntry(filename));// 建立打包中的文件名称(报告名称)

				byte[] content = new byte[1024 * 8];
				int len;
				while ((len = input.read(content)) != -1) {
					out.write(content, 0, len);
				}
				input.close();
			
			}

		}

		try {
			out.close();
			os.flush(); // 将缓冲区中的数据全部写出
			os.close(); // 关闭流
		} catch (IOException e) {
			logger.error(e.getMessage(),e);
		}

	}
 
	}
