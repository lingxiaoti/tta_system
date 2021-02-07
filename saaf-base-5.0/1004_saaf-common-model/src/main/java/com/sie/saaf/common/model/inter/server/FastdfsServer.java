package com.sie.saaf.common.model.inter.server;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.bean.ResultFileEntity;
import com.sie.saaf.common.bean.UserSessionBean;
import com.sie.saaf.common.configration.FastdfsConfigration;
import com.sie.saaf.common.model.entities.BaseAttachmentEntity_HI;
import com.sie.saaf.common.model.enums.lookupCodeValusEnum;
import com.sie.saaf.common.model.inter.IBaseAttachment;
import com.sie.saaf.common.model.inter.IFastdfs;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.hibernate.annotations.Synchronize;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author ZhangJun
 * @createTime 2018-11-05 5:50 PM
 * @description
 */
@Component
public class FastdfsServer extends FileUploadServer implements IFastdfs {
	private static final Logger LOGGER = LoggerFactory.getLogger(FastdfsServer.class);

	@Autowired
	private FastdfsConfigration fastdfsConfigration;

	@Autowired
	private IBaseAttachment baseAttachmentServer;

	public static Set<String> FOR_SET;
	static {
		FOR_SET = new HashSet<>();
		FOR_SET.add("bmp");
		FOR_SET.add("dib");
		FOR_SET.add("jpg");
		FOR_SET.add("jpeg");
		FOR_SET.add("jpe");
		FOR_SET.add("jfif");
		FOR_SET.add("gif");
		FOR_SET.add("tif");
		FOR_SET.add("tiff");
		FOR_SET.add("png");
		FOR_SET.add("ico");
		FOR_SET.add("heic");
		FOR_SET.add("webp");
		FOR_SET.add("pdf");
		FOR_SET.add("txt");
		FOR_SET.add("doc");
		FOR_SET.add("docx");
		FOR_SET.add("xls");
		FOR_SET.add("xlsx");
		FOR_SET.add("ppt");
		FOR_SET.add("pptx");
		FOR_SET.add("psd");
		FOR_SET.add("pdd");
		FOR_SET.add("svg");
		FOR_SET.add("msg");
		FOR_SET.add("html");
		FOR_SET.add("rar");
		FOR_SET.add("zip");
		FOR_SET.add("xml");
		FOR_SET.add("csv");
	}

	private ThreadLocal<StorageClient> storageClient = new ThreadLocal<StorageClient>() {
		@Override
		protected StorageClient initialValue() {

			try {
				Properties props = new Properties();
				props.put("fastdfs.tracker_servers",StringUtils.join(fastdfsConfigration.getTrackerServers(),","));
				props.put("fastdfs.connect_timeout_in_seconds",fastdfsConfigration.getConnectTimeoutInSeconds());
				props.put("fastdfs.network_timeout_in_seconds",fastdfsConfigration.getNetworkTimeoutInSeconds());
				props.put("fastdfs.charset",fastdfsConfigration.getCharset());
				props.put("fastdfs.http_anti_steal_token",fastdfsConfigration.getHttpAntiStealToken());
				props.put("fastdfs.http_secret_key",fastdfsConfigration.getHttpSecretKey());
				props.put("fastdfs.http_tracker_http_port",fastdfsConfigration.getHttpTrackerHttpPort());

				ClientGlobal.initByProperties(props);

				TrackerClient trackerClient = new TrackerClient();
				TrackerServer trackerServer = ClientGlobal.getG_tracker_group().getConnection();//trackerClient.getConnection();

				return new StorageClient(trackerServer, null);
			} catch (Exception e) {
				LOGGER.error("FastDFS Client Init Fail!",e);
			}

			return new StorageClient();
		}
	};

	/**
	 * 文件上传
	 * @param inputStream 文件流
	 * @param fileName 文件名
	 * @return {@link ResultFileEntity}
	 * @author ZhangJun
	 * @createTime 2018/11/5
	 * @description 文件上传
	 */
	@Override
	public ResultFileEntity fileUpload(InputStream inputStream, String fileName) throws IOException {
		long start=System.currentTimeMillis();
		long fileSize= inputStream.available();
		String suffix = "";
		if (StringUtils.isNotBlank(fileName) && fileName.lastIndexOf(".") >= 0) {
			suffix = fileName.substring(fileName.lastIndexOf(".")+1);
		}

		if (checkSuf(suffix)) {
			throw new IllegalArgumentException("不合法的文件");
		}

		NameValuePair[] meta_list = new NameValuePair[3];
		meta_list[0] = new NameValuePair("width", fastdfsConfigration.getThumbImage().getWidth());
		meta_list[1] = new NameValuePair("heigth", fastdfsConfigration.getThumbImage().getHeight());
		meta_list[2] = new NameValuePair("author", String.valueOf(getUserId()));


		StorageClient client = storageClient.get();
		String[] uploadResults = null;
		try{
			uploadResults = client.upload_file(IOUtils.toByteArray(inputStream), suffix, meta_list);
		} catch (IOException e){
			LOGGER.error("IO Exception when uploadind the file: {}", fileName, e);
		} catch (Exception e){
			LOGGER.error("Non IO Exception when uploadind the file: {}", fileName, e);
		}
		if (uploadResults == null) {
			LOGGER.error("upload file fail, error code: {}", client.getErrorCode(), null);
		}
		String groupName = uploadResults[0];
		String remoteFileName = uploadResults[1];

		String httpType = fastdfsConfigration.getAccess().getHttpType();
		String accessPort = fastdfsConfigration.getAccess().getHttpPort();
		String addressURL = fastdfsConfigration.getAccess().getAddress();

		String accessPath = httpType + addressURL + ":" + accessPort + "/" + groupName + "/" + remoteFileName;
		LOGGER.info("upload file successfully!  group_name: " + groupName + ", remoteFileName: " + remoteFileName);

		LOGGER.info("文件上传用时{}s，大小:{}MB",(System.currentTimeMillis()-start)/1000D,fileSize/1024D/1024D);
		ResultFileEntity resultFileEntity = new ResultFileEntity(fileName,suffix, accessPath, accessPath, fileSize ,new Date(), groupName, remoteFileName);
		//保存附件表
		BaseAttachmentEntity_HI instance=new BaseAttachmentEntity_HI();
		instance.setDeleteFlag(0);
		instance.setFileStoreSystem(1);
		instance.setSourceFileName(resultFileEntity.getSourceFileName());
		instance.setFileSize(new BigDecimal(fileSize));
		if (StringUtils.isNotBlank(suffix)){
			suffix=suffix.replace(".","");
			instance.setFileType(suffix.toLowerCase());
		}
		instance.setFilePath(accessPath);
		instance.setBucketName(groupName);
		instance.setPhyFileName(remoteFileName);
		instance.setOperatorUserId(getUserId());
		baseAttachmentServer.saveBaseAttachmentInfo(instance);
		resultFileEntity.setFileId(instance.getFileId());
		resultFileEntity.setCreatedBy(String.valueOf(getUserId()));
		resultFileEntity.setCreatedByUser(getUserFullName());
		resultFileEntity.setCreationDate(new Date());
		resultFileEntity.setFileStoreSystem(instance.getFileStoreSystem());
		return resultFileEntity;
	}

	private boolean checkSuf(String suffix) {
		if (!FOR_SET.contains(suffix)) {
			return true;
		}
		return false;
	}

	/**
	 * 文件上传
	 * @param inputStream  输入流
	 * @param fileName 文件名
	 * @param functionId 业务模块
	 * @param bussnessId 业务主键id
	 * @return 2019.7.19 新增
	 * @throws IOException
	 */
	@Override
	public ResultFileEntity fileUpload(InputStream inputStream, String fileName, String functionId, Long bussnessId, Integer userId) throws IOException {
		long start=System.currentTimeMillis();
		long fileSize= inputStream.available();//文件大小

		String suffix = "";
		if (StringUtils.isNotBlank(fileName) && fileName.lastIndexOf(".") >= 0) {
			suffix = fileName.substring(fileName.lastIndexOf(".")+1);//截取文件后缀名
		}

		NameValuePair[] meta_list = new NameValuePair[3];
		meta_list[0] = new NameValuePair("width", fastdfsConfigration.getThumbImage().getWidth());
		meta_list[1] = new NameValuePair("heigth", fastdfsConfigration.getThumbImage().getHeight());
		meta_list[2] = new NameValuePair("author", String.valueOf(getUserId()));


		StorageClient client = storageClient.get();
		String[] uploadResults = null;
		try{
			uploadResults = client.upload_file(IOUtils.toByteArray(inputStream), suffix, meta_list);
		} catch (IOException e){
			LOGGER.error("IO Exception when uploadind the file: {}", fileName, e);
		} catch (Exception e){
			LOGGER.error("Non IO Exception when uploadind the file: {}", fileName, e);
		}
		if (uploadResults == null) {
			LOGGER.error("upload file fail, error code: {}", client.getErrorCode(), null);
		}
		String groupName = uploadResults[0];
		String remoteFileName = uploadResults[1];

		String httpType = fastdfsConfigration.getAccess().getHttpType();
		String accessPort = fastdfsConfigration.getAccess().getHttpPort();
		String addressURL = fastdfsConfigration.getAccess().getAddress();

		String accessPath = httpType + addressURL + ":" + accessPort + "/" + groupName + "/" + remoteFileName;
		LOGGER.info("upload file successfully!  group_name: " + groupName + ", remoteFileName: " + remoteFileName);

		LOGGER.info("文件上传用时{}s，大小:{}MB",(System.currentTimeMillis()-start)/1000D,fileSize/1024D/1024D);
		ResultFileEntity resultFileEntity = new ResultFileEntity(fileName,suffix, accessPath, accessPath, fileSize ,new Date(), groupName, remoteFileName);
		//保存附件表
		BaseAttachmentEntity_HI instance=new BaseAttachmentEntity_HI();
		instance.setDeleteFlag(0);
		instance.setFileStoreSystem(1);
		instance.setSourceFileName(resultFileEntity.getSourceFileName());
		instance.setFileSize(new BigDecimal(fileSize));
		instance.setFunctionId(functionId);//业务模块
		instance.setBusinessId(bussnessId);//业务主键id

		if (StringUtils.isNotBlank(suffix)){
			suffix=suffix.replace(".","");
			instance.setFileType(suffix.toLowerCase());
		}
		instance.setFilePath(accessPath);
		instance.setBucketName(groupName);
		instance.setPhyFileName(remoteFileName);
		instance.setOperatorUserId(userId);
		instance.setCreatedBy(userId);
		baseAttachmentServer.saveBaseAttachmentInfo(instance);
		resultFileEntity.setFileId(instance.getFileId());
		resultFileEntity.setCreatedBy(String.valueOf(userId));
		resultFileEntity.setCreatedByUser(getUserFullName());
		resultFileEntity.setCreationDate(new Date());
		resultFileEntity.setFileStoreSystem(instance.getFileStoreSystem());
		return resultFileEntity;
	}

	/**
	 * 删除文件
	 * @param filePath 访问地址
	 * @author ZhangJun
	 * @createTime 2018/11/16
	 * @description 删除文件
	 */
	@Override
	public ResultFileEntity delete(String filePath){

		String httpType = fastdfsConfigration.getAccess().getHttpType();
		String accessPort = fastdfsConfigration.getAccess().getHttpPort();
		String addressURL = fastdfsConfigration.getAccess().getAddress();

		String serverAddress = httpType + addressURL + ":" + accessPort + "/";
		filePath = filePath.replaceAll(serverAddress,"");
		String bucketName = filePath.substring(0,filePath.indexOf("/"));
		String remoteFileName = filePath.substring(filePath.indexOf("/")+1,filePath.length());

		return this.delete(bucketName,remoteFileName);
	}

	/**
	 * 删除文件
	 * @param bucketName groupName
	 * @param remoteFileName remoteFileName
	 * @return {@link ResultFileEntity}
	 * @author ZhangJun
	 * @createTime 2018/11/5
	 * @description 删除文件
	 */
	@Override
	public ResultFileEntity delete(String bucketName, String remoteFileName){
		ResultFileEntity fileEntity = new ResultFileEntity();
		fileEntity.setBucketName(bucketName);
		fileEntity.setRemoteFileName(remoteFileName);
		try{
			StorageClient client = this.storageClient.get();
			int i = client.delete_file(bucketName, remoteFileName);
			LOGGER.info("删除成功：" + i);
		}catch (Exception e){
			LOGGER.error(e.getMessage(), e);
		}
		return fileEntity;
	}

	/**
	 * 删除文件
	 * @param fileId 附件Id
	 * @param userSessionBean 登录用户Session
	 * @author ZhangJun
	 * @createTime 2018/11/5
	 * @description 删除文件
	 */
	@Override
	public void delete(Long fileId, UserSessionBean userSessionBean) {
		BaseAttachmentEntity_HI obj = baseAttachmentServer.findBaseAttachmentInfo(fileId);
		if (null == obj) {
			throw new IllegalArgumentException("文件不存在，删除失败");
		} else if (obj.getCreatedBy().equals(String.valueOf(userSessionBean.getUserId()))) {
			throw new IllegalArgumentException("非本人上传的附件，无法删除");
		}
		delete(obj.getBucketName(),obj.getPhyFileName());
		baseAttachmentServer.deleteBaseAttachment(fileId);
	}

	/**
	 * 取得输入流
	 * @param bucketName groupName
	 * @param remoteFileName remoteFileName
	 * @return {@link InputStream}
	 * @author ZhangJun
	 * @createTime 2018/11/5
	 * @description 取得输入流
	 */
	@Override
	public InputStream getInputStream(String bucketName, String remoteFileName){
		try{
			StorageClient client = this.storageClient.get();
			byte[] bytes = client.download_file(bucketName, remoteFileName);
			InputStream is = new ByteArrayInputStream(bytes);
			return is;
		}catch (Exception e){
			LOGGER.error(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * 下载
	 * @param fileId 附件Id
	 * @param request 请求
	 * @param response 响应
	 * @author ZhangJun
	 * @createTime 2018/11/5
	 * @description 下载
	 */
	@Override
	public void download(Long fileId, HttpServletRequest request, HttpServletResponse response) throws Exception{
		BaseAttachmentEntity_HI obj = baseAttachmentServer.findBaseAttachmentInfo(fileId);
		if (null == obj) {
			throw new IllegalArgumentException("文件不存在，下载失败");
		}

		String fileName = obj.getSourceFileName(); // 文件的默认保存名
		String userAgent = request.getHeader("User-Agent");

		// 针对IE或者以IE为内核的浏览器（针对中文名图片下载后文件名乱码）
		if (userAgent.contains("MSIE") || userAgent.contains("Trident")) {
			fileName = java.net.URLEncoder.encode(fileName, "UTF-8");
		} else {
			// 非IE浏览器的处理：
			fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
		}

		// 读到流中
		InputStream inStream = getInputStream(obj.getBucketName(),obj.getPhyFileName());
		// 设置输出的格式
		response.reset();
		response.setHeader("Content-disposition",String.format("attachment; filename=\"%s\"", fileName));
		response.setContentType("multipart/form-data");
		response.setCharacterEncoding("UTF-8");
		// 循环取出流中的数据
		byte[] b = new byte[100];
		int len;
		try {
			while ((len = inStream.read(b)) > 0) {
				response.getOutputStream().write(b, 0, len);
			}
			inStream.close();
		} catch (IOException e) {
			LOGGER.error(e.getMessage(),e);
		}
	}

	@Override
	public InputStream plmErrorResultToExcel(JSONArray array) {
//		String[] heads={"stopReason","ERR_MESSAGE","meter","supplementStatus","store","rmsCode","productName"};
//		String[] heads={"ERR_MESSAGE","rmsCode","productName","store","meter","supplementStatus","stopReason"};
		String[] heads={"错误信息","货品","生效地点"};
//		String[] heads={"ERR_MESSAGE","rmsCode","meter"};
		SXSSFWorkbook workbook = new SXSSFWorkbook();// 创建一个Excel文件
		SXSSFSheet sheet = workbook.createSheet();// 创建一个Excel的Sheet
		SXSSFRow titleRow = sheet.createRow(0);//创建表头
		String time = getTimeStr();
		for(int i=0;i<heads.length;i++){//表头赋值
			titleRow.createCell(i).setCellValue(heads[i]);
		}
		if (array.size() > 0) {//单元格赋值
			for (int i = 0; i < array.size(); i++) {
				SXSSFRow row = sheet.createRow(i + 1);
				JSONObject json = array.getJSONObject(i); // 遍历 jsonarray
				for(int j=0;j<heads.length;j++){
//					if("stopReason".equals(heads[j])){
//						row.createCell(j).setCellValue(ObjectUtils.isEmpty(json.get(heads[j]))?"": getMeaningForValue("PLM_SUP_STOP_REASON",json.get(heads[j]).toString()) );//取枚举值
//					}else if("supplementStatus".equals(heads[j])){
//						row.createCell(j).setCellValue(ObjectUtils.isEmpty(json.get(heads[j]))?"": getMeaningForValue("PLM_SUP_STATUS_ALL",json.get(heads[j]).toString()) );//取枚举值
//					}else if("store".equals(heads[j])){
//						row.createCell(j).setCellValue(ObjectUtils.isEmpty(json.get(heads[j]))?"": getMeaningForValue("PLM_SUP_EFFECTIVE_WAY",json.get(heads[j]).toString()) );//取枚举值
//					}else {
//						row.createCell(j).setCellValue(ObjectUtils.isEmpty(json.get(heads[j]))?"": json.get(heads[j]).toString());//赋值
//					}
					if("错误信息".equals(heads[j])){
						row.createCell(j).setCellValue(ObjectUtils.isEmpty(json.get("ERR_MESSAGE"))?"": json.get("ERR_MESSAGE").toString());//赋值
					}else if("货品".equals(heads[j])){
						row.createCell(j).setCellValue(ObjectUtils.isEmpty(json.get("rmsCode"))?"": json.get("rmsCode").toString());//赋值
					}else if("生效地点".equals(heads[j])){
						row.createCell(j).setCellValue(ObjectUtils.isEmpty(json.get("meter"))?"": json.get("meter").toString());//赋值
					}

				}
			}
		}

		ByteArrayOutputStream bos = null ;
//		FileInputStream is =null ;
		try {
//            FileOutputStream fos = new FileOutputStream("E://ss.xls");
//            File file = new File("E://ss.xls");plmSendEmailService
//is=new FileInputStream("ER"+time+".xls");
//is.reset();
            bos= new ByteArrayOutputStream();
//            workbook.write(fos);
			workbook.write(bos);
            byte[] bytes = bos.toByteArray();
//			bos.close();
//			fos.close();
            bos.close();
//			MultipartFile multipartFile = new MockMultipartFile("ER"+time+".xls","ER"+time+".xls","",new ByteArrayInputStream(bytes));

//            return new FileInputStream(file);
			return new ByteArrayInputStream(bytes);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public InputStream plmSupErrorResultToExcel(JSONArray array) {
				String[] heads={"ERR_MESSAGE","warehouseCode","supplierCode","productCode","storeWay","exploRevDate","supDelDate","orderFreq","orderDate","warRevDate"};
//		String[] heads={"ERR_MESSAGE","rmsCode","productName","store","meter","supplementStatus","stopReason"};
		SXSSFWorkbook workbook = new SXSSFWorkbook();// 创建一个Excel文件
		SXSSFSheet sheet = workbook.createSheet();// 创建一个Excel的Sheet
		SXSSFRow titleRow = sheet.createRow(0);//创建表头
		String time = getTimeStr();
		for(int i=0;i<heads.length;i++){//表头赋值
			titleRow.createCell(i).setCellValue(heads[i]);
		}
		if (array.size() > 0) {//单元格赋值
			for (int i = 0; i < array.size(); i++) {
				SXSSFRow row = sheet.createRow(i + 1);
				JSONObject json = array.getJSONObject(i); // 遍历 jsonarray
				for(int j=0;j<heads.length;j++){
						row.createCell(j).setCellValue(ObjectUtils.isEmpty(json.get(heads[j]))?"": json.get(heads[j]).toString());//赋值
				}
			}
		}

		ByteArrayOutputStream bos = null ;
//		FileInputStream is =null ;
		try {
//            FileOutputStream fos = new FileOutputStream("E://ss.xls");
//            File file = new File("E://ss.xls");plmSendEmailService
//is=new FileInputStream("ER"+time+".xls");
//is.reset();
			bos= new ByteArrayOutputStream();
//            workbook.write(fos);
			workbook.write(bos);
			byte[] bytes = bos.toByteArray();
//			bos.close();
//			fos.close();
			bos.close();
//			MultipartFile multipartFile = new MockMultipartFile("ER"+time+".xls","ER"+time+".xls","",new ByteArrayInputStream(bytes));

//            return new FileInputStream(file);
			return new ByteArrayInputStream(bytes);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String getTimeStr() {
		String timeStr = "";
		Date date = new Date();
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
		timeStr = sf.format(date);
		return timeStr;
	}
	public String getMeaningForValue(String type ,String values) {
		EnumSet<lookupCodeValusEnum> enumSet = EnumSet.allOf(lookupCodeValusEnum.class);
		for ( lookupCodeValusEnum e:enumSet){
			if (e.getType().equals(type) && e.getValues().equals(values)){
				return e.getMeaning();
			}
		}
		return "";
	}


}
