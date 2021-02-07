package com.sie.saaf.common.util;

import com.alibaba.fastjson.JSON;
import com.yhg.base.utils.ConfigurationFileUtils;
import com.yhg.base.utils.PropertiesUtils;
import com.yhg.fastdfs.core.bean.FastDFSFileEntity;
import com.yhg.fastdfs.core.bean.ResutlFileEntity;
import com.yhg.fastdfs.core.utils.FileManagerUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class FastDFSUploadUtil {
	private static final Logger log = LoggerFactory
			.getLogger(FastDFSUploadUtil.class);
	private static final String configFilePath = "/fdfs_client.properties";
	private FileManagerUtils fileManager = FileManagerUtils
			.getInstance(configFilePath);

	public FastDFSUploadUtil() {
		super();
	}

	/**
	 * 上传文件
	 * @author ZhangJun
	 * @createTime 2018/6/28
	 * @description 上传文件
	 */
	public ResutlFileEntity upload(String filename, byte[] stream) throws IOException{
		String ext = filename.substring(filename.lastIndexOf(".") + 1);
		FastDFSFileEntity DFSFileEntity = new FastDFSFileEntity(filename,
				stream, ext);
		ResutlFileEntity resutlFileEntity = fileManager
				.uploadFile2FastDFS(DFSFileEntity);
		return resutlFileEntity;
	}

	/**
	 * 上传文件
	 * @author ZhangJun
	 * @createTime 2018/6/28
	 * @description 上传文件
	 */
	public ResutlFileEntity upload(String filename, InputStream stream) throws IOException{
		String ext = filename.substring(filename.lastIndexOf(".") + 1);
		FastDFSFileEntity DFSFileEntity = new FastDFSFileEntity(filename,
				InputStreamToByte(stream), ext);
		ResutlFileEntity resutlFileEntity = fileManager
				.uploadFile2FastDFS(DFSFileEntity);
		return resutlFileEntity;
	}

	/**
	 * 通过下载的url删除文件
	 * @author ZhangJun
	 * @createTime 2018/6/28
	 * @description 通过下载的url删除文件
	 */
	public ResutlFileEntity deleteByUrl(String url){
		if(StringUtils.isBlank(url)){
			return null;
		}

		String groupName = "",remoteFileName = "";


		try {
			String fdfsClientConfigFilePath = ConfigurationFileUtils.getConfigurationInputStream(configFilePath).getPath();
			fdfsClientConfigFilePath = URLDecoder.decode(fdfsClientConfigFilePath, "UTF-8");
			String httpType = PropertiesUtils.readValue(fdfsClientConfigFilePath, "access.http.type");
			String addressURL = PropertiesUtils.readValue(fdfsClientConfigFilePath, "access.address");
			String accessPort = PropertiesUtils.readValue(fdfsClientConfigFilePath, "access.http.port");

			String contentPath = httpType + addressURL + ":" + accessPort + "/";
			url = url.substring(url.indexOf(contentPath)+contentPath.length());
			groupName = url.substring(0,url.indexOf("/"));

			url = url.substring(url.indexOf(groupName)+groupName.length());
			remoteFileName = url.substring(1);

			return delete(groupName,remoteFileName);
		} catch (UnsupportedEncodingException e) {
			log.error("",e);
			return null;
		}

	}

	/**
	 * 从文件服务器中删除文件
	 * @param groupName 组名
	 * @param remoteFileName 远程文件名
	 * @return
	 */
	public ResutlFileEntity delete(String groupName,String remoteFileName) {
		if (StringUtils.isBlank(groupName)
				|| StringUtils.isBlank(remoteFileName))
			return null;
		try {
			FileManagerUtils fileManager = FileManagerUtils
					.getInstance(configFilePath);
			ResutlFileEntity resutlFileEntity = fileManager.deleteFileFromFastDSF(groupName, remoteFileName);
			return resutlFileEntity;
		} catch (Exception e) {
			log.error("", e);
			return null;
		}
	}

	/**
	 * 将InputStream转换成byte数组
	 *
	 * @param in
	 *            InputStream
	 * @return byte[]
	 * @throws IOException
	 */
	private byte[] InputStreamToByte(InputStream in) throws IOException {

		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] data = new byte[2048];
		int count = -1;
		while ((count = in.read(data, 0, 2048)) != -1)
			outStream.write(data, 0, count);

		return outStream.toByteArray();
	}

	public static void main(String[] args) throws IOException {
		FastDFSUploadUtil util = new FastDFSUploadUtil();
		//InputStream stream = new FileInputStream(new File("/Users/Rocky/Documents/Project/sie/workspace/GenFormApp/WebContent/genform/gen/test.txt"));
//		JSONObject obj = util.uploadFile("test.txt", stream);
//		ResutlFileEntity obj = util.delete("group1", "M00/00/00/CtM3C1rumtOAMP6EAAAe0T1eevA36.xlsx");
		ResutlFileEntity obj = util.deleteByUrl("http://10.211.55.11:80/group1/M00/00/00/CtM3C1rurl2AO2PwAAAe0G2HfX839.xlsx");
		System.out.println(JSON.toJSONString(obj));
	}
	
}
