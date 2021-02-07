package com.sie.watsons.base.ob.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.mongodb.*;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.sie.saaf.common.bean.ResultFileEntity;
import com.sie.saaf.common.model.inter.IFastdfs;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.model.inter.server.GenerateCodeServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.ob.model.entities.PlmDevelopmentInfoEntity_HI;
import com.sie.watsons.base.ob.model.entities.PlmProjectExceptionEntity_HI;
import com.sie.watsons.base.ob.model.entities.PlmObHistoryListEntity_HI;
import com.sie.watsons.base.ob.model.entities.PlmProjectProductDetailEntity_HI;
import com.sie.watsons.base.ob.model.entities.readonly.PlmObHistoryListEntity_HI_RO;
import com.sie.watsons.base.ob.model.entities.readonly.PlmProjectProductDetailEntity_HI_RO;
import com.sie.watsons.base.ob.model.inter.*;
import com.sie.watsons.base.redisUtil.ResultUtils;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.apache.commons.lang.StringUtils;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

@Component("plmObHistoryListServer")
public class PlmObHistoryListServer extends BaseCommonServer<PlmObHistoryListEntity_HI> implements IPlmObHistoryList{
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmObHistoryListServer.class);
	@Autowired
	private ViewObject<PlmObHistoryListEntity_HI> plmObHistoryListDAO_HI;
	@Autowired
	private BaseViewObject<PlmObHistoryListEntity_HI_RO> plmObHistoryListDAO_HI_RO;
	@Autowired
	private IFastdfs fastdfsServer;

	public PlmObHistoryListServer() {
		super();
	}


	@Override
	public Pagination<PlmObHistoryListEntity_HI_RO> findPlmObHistoryListInfo(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
		StringBuffer sql = new StringBuffer(PlmObHistoryListEntity_HI_RO.QUERY_SQL);
		Map<String, Object> paramsMap = new HashMap<>();
		SaafToolUtils.parperHbmParam(PlmObHistoryListEntity_HI_RO.class, queryParamJSON, sql, paramsMap);
//		sql.append(" order by ppi.last_update_date desc");
		Pagination<PlmObHistoryListEntity_HI_RO> pagination = plmObHistoryListDAO_HI_RO.findPagination(sql, paramsMap, pageIndex, pageRows);
		return pagination;
	}

	@Override
	public PlmObHistoryListEntity_HI savePlmObHistoryListInfo(JSONObject queryParamJSON) throws Exception {
		JSONObject currentObject = queryParamJSON;
		PlmObHistoryListEntity_HI plmObHistoryListEntity_HI = JSON.parseObject(currentObject.toString(), PlmObHistoryListEntity_HI.class);
		plmObHistoryListEntity_HI.setOperatorUserId(queryParamJSON.getInteger("varUserId"));
		PlmObHistoryListEntity_HI resultData = plmObHistoryListDAO_HI.saveEntity(plmObHistoryListEntity_HI);
		return resultData;
	}

	@Override
	public JSONObject importAndTransferObHistoryList() throws Exception{
		Map<String, Object> queryParamMap = new HashMap<>();
		List<PlmObHistoryListEntity_HI> dataList = plmObHistoryListDAO_HI.findList("from PlmObHistoryListEntity_HI where file_address is null and ROWNUM<1000 ", queryParamMap);
		//MongoDb connection
//		MongoClientURI uri = new MongoClientURI("mongodb://10.82.25.206:27017/");
//		MongoClientURI uri = new MongoClientURI("mongodb://10.82.28.209:27017/");

//		MongoCredential mc = new MongoCredential("spusers2", "database", "7343@sp#".toCharArray());
		MongoCredential.createCredential("spusers2", "database", "7343@sp#".toCharArray());
		ServerAddress serverAddress = new ServerAddress("10.82.28.209");
		MongoClient mongoClient = new MongoClient(serverAddress, MongoCredential.createCredential("spusers2", "database", "7343@sp#".toCharArray()), MongoClientOptions.builder().build());
		mongoClient.getCredentialsList();
		MongoDatabase myDatabase = mongoClient.getDatabase("database");
		GridFSBucket gridFSBucket = GridFSBuckets.create(myDatabase,"ProductFile");
		for(PlmObHistoryListEntity_HI data: dataList){
			if(StringUtils.isBlank(data.getMongoId())){
				data.setErrorRemarks("未获取MongoId!");
				plmObHistoryListDAO_HI.saveOrUpdate(data);
				continue;
			}
			GridFSFile gridFSFile = gridFSBucket.find(new Document().append("_id",data.getMongoId())).first();
			if(gridFSFile==null){
				data.setErrorRemarks("mongoId无对应文件！");
				plmObHistoryListDAO_HI.saveOrUpdate(data);
				continue;
			}
			//获取非默认写法文件名
			String fileName = gridFSFile.getExtraElements().get("file_name").toString();
			InputStream inputStream = gridFSBucket.openDownloadStream(gridFSFile.getId());
			ResultFileEntity fileEntity = fastdfsServer.fileUpload(inputStream, fileName);
			data.setFileAddress(fileEntity.getAccessPath());
			data.setOperatorUserId(1);
			plmObHistoryListDAO_HI.saveOrUpdate(data);
		}
		JSONObject dataObject = new JSONObject();
		dataObject.put("data","Operation Complete");
		return dataObject;
	}


}
