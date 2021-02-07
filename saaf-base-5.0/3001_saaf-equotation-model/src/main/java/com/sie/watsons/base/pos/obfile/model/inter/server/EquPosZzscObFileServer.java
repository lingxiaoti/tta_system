package com.sie.watsons.base.pos.obfile.model.inter.server;

import com.alibaba.fastjson.JSONObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
//import com.mongodb.client.MongoDatabase;
//import com.mongodb.client.gridfs.GridFSBucket;
//import com.mongodb.client.gridfs.GridFSBuckets;
//import com.mongodb.client.gridfs.model.GridFSFile;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.sie.saaf.common.bean.ResultFileEntity;
import com.sie.saaf.common.model.inter.IFastdfs;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.watsons.base.pos.obfile.model.entities.EquPosZzscObFileEntity_HI;
import com.sie.watsons.base.pos.obfile.model.inter.IEquPosZzscObFile;
import com.sie.watsons.base.pos.qualificationreview.model.entities.EquPosZzscCredentialAttachEntity_HI;
import com.sie.watsons.base.pos.supplierinfo.model.entities.EquPosSupplierCredentialsEntity_HI;
import com.sie.watsons.base.pos.supplierinfo.model.entities.EquPosSupplierInfoEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
//import org.bson.Document;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("equPosZzscObFileServer")
public class EquPosZzscObFileServer extends BaseCommonServer<EquPosZzscObFileEntity_HI> implements IEquPosZzscObFile{
    private static final Logger LOGGER = LoggerFactory.getLogger(EquPosZzscObFileServer.class);

    @Autowired
    private ViewObject<EquPosZzscObFileEntity_HI> equPosZzscObFileDAO_HI;

    @Autowired
    private IFastdfs fastdfsServer;

    @Autowired
    private ViewObject<EquPosSupplierInfoEntity_HI> equPosSupplierInfoDAO_HI;

    public EquPosZzscObFileServer() {
        super();
    }

    @Autowired
    private ViewObject<EquPosSupplierCredentialsEntity_HI> equPosSupplierCredentialsDAO_HI;

    @Autowired
    private ViewObject<EquPosZzscCredentialAttachEntity_HI> equPosZzscCredentialAttachDAO_HI;

    @Override
    public JSONObject saveObFactoryFile() throws IOException {

        List<EquPosZzscObFileEntity_HI> dataList =
         equPosZzscObFileDAO_HI.findList("from  EquPosZzscObFileEntity_HI where fileId is null ");

        //MongoDb connection
        MongoClientURI uri = new MongoClientURI("mongodb://spusers2:7343%40sp#@10.82.28.209:27017/database");
        MongoClient mongoClient = new MongoClient(uri);
        MongoDatabase myDatabase = mongoClient.getDatabase("database");
        GridFSBucket gridFSBucket = GridFSBuckets.create(myDatabase, "FactoryFile");
        for (EquPosZzscObFileEntity_HI data : dataList) {
            System.out.println(data.getObFileId());
            if (StringUtils.isBlank(data.getMongoId())) {
                System.out.println("00000000000000");
            } else {
                GridFSFile gridFSFile = gridFSBucket.find(new Document().append("_id", data.getMongoId().trim())).first();
                if (gridFSFile == null) {
                    System.out.println("~~~~~~~~~~~~~~~~~~");
                } else {
                    String fileName = gridFSFile.getExtraElements().get("file_name").toString();
                    InputStream inputStream = gridFSBucket.openDownloadStream(gridFSFile.getId());
                    try{
                        ResultFileEntity fileEntity = fastdfsServer.fileUpload(inputStream, fileName);
                        data.setFilePath(fileEntity.getFilePath()+"?attname="+fileName);
                        data.setFileName(fileName);
                        data.setFileId(fileEntity.getFileId().intValue());
                        data.setOperatorUserId(1);
                        equPosZzscObFileDAO_HI.saveOrUpdate(data);
                    }catch (Exception e){
                        System.out.println("-----------------------------");
                        System.out.println("导入报错id=="+data.getObFileId());
                        System.out.println("-----------------------------");
                    }

                }
            }
        }
        JSONObject dataObject = new JSONObject();
        dataObject.put("data","Operation Complete");
        return dataObject;
    }

    @Override
    public JSONObject saveFastDFSFile() {
        List<EquPosZzscObFileEntity_HI> dataList = equPosZzscObFileDAO_HI.findList("from  EquPosZzscObFileEntity_HI WHERE attribute1 is null ");

        //MongoDb connection
        for (EquPosZzscObFileEntity_HI data : dataList) {
            if (StringUtils.isBlank(data.getMongoId())) {
                System.out.println("00000000000000");
            } else {
                if (data.getFilePath() == null) {
                    System.out.println("~~~~~~~~~~~~~~~~~~");
                } else {
                    String fileName = data.getFileName();
                    //保存附件信息到供应商表里面  分为除了生产许可证,营业执照外,其他的附件都算其他.
                    String supplierName = data.getSupplierName();
                    String flieType = data.getFileType();
                    System.out.println("-------------------------------");
                    System.out.println(supplierName);
                    System.out.println(fileName);
                    System.out.println("-------------------------------");
                    Map<String, Object> map = new HashMap<>();
                    //获取供应商信息
                    List<EquPosSupplierInfoEntity_HI> equPosSupplierInfoEntityHi = equPosSupplierInfoDAO_HI.findList("FROM EquPosSupplierInfoEntity_HI WHERE supplierName = '" + supplierName + "'");
                    try {
                        map.put("supplierId", equPosSupplierInfoEntityHi.get(0).getSupplierId());
                        if (flieType.indexOf("营业执照") != -1) {
                            //					保存营业执照信息
                            List<EquPosSupplierCredentialsEntity_HI> supplierCredentialsEntityHis = equPosSupplierCredentialsDAO_HI.findList("from EquPosSupplierCredentialsEntity_HI WHERE supplierId = :supplierId AND deptCode = '0E' ", map);
                            if (supplierCredentialsEntityHis.size() > 0) {
                                EquPosSupplierCredentialsEntity_HI supplierCredentialsEntityHi = supplierCredentialsEntityHis.get(0);
                                supplierCredentialsEntityHi.setLicenseFileName(fileName);
                                supplierCredentialsEntityHi.setLicenseFileId(data.getFileId());
                                System.out.print("");
                                supplierCredentialsEntityHi.setLicenseFilePath(data.getFilePath());
                                supplierCredentialsEntityHi.setOperatorUserId(1);
                                equPosSupplierCredentialsDAO_HI.save(supplierCredentialsEntityHi);
                            }
                        } else if (flieType.indexOf("生产许可证") != -1) {
                            //  保存生产许可证
                            EquPosZzscCredentialAttachEntity_HI credentialAttachEntityHi = new EquPosZzscCredentialAttachEntity_HI();
                            List<EquPosZzscCredentialAttachEntity_HI> credentialAttachEntityHis = equPosZzscCredentialAttachDAO_HI.findList("" +
                                    "FROM EquPosZzscCredentialAttachEntity_HI where deptmentCode = '0E' AND attachmentName = '生产许可证' AND fileType = 'FIXED' AND filePath IS NULL and supplierId = :supplierId", map);
                            if (credentialAttachEntityHis.size() > 0) {
                                credentialAttachEntityHi = credentialAttachEntityHis.get(0);
                            } else {
                                credentialAttachEntityHi.setFileType("productionPermit");
                                credentialAttachEntityHi.setFixFlag("N");
                            }
                            credentialAttachEntityHi.setOperatorUserId(1);
                            credentialAttachEntityHi.setAttachmentName("生产许可证");
                            credentialAttachEntityHi.setSupplierId(equPosSupplierInfoEntityHi.get(0).getSupplierId());
                            credentialAttachEntityHi.setFileName(fileName);
                            credentialAttachEntityHi.setFilePath(data.getFilePath());
                            credentialAttachEntityHi.setFileId(data.getFileId());
                            credentialAttachEntityHi.setDeptmentCode("0E");
                            credentialAttachEntityHi.setIsProductFactory("Y");
                            equPosZzscCredentialAttachDAO_HI.save(credentialAttachEntityHi);
                        } else {
                            //其他直接保存
                            EquPosZzscCredentialAttachEntity_HI credentialAttachEntityHi = new EquPosZzscCredentialAttachEntity_HI();
                            credentialAttachEntityHi.setOperatorUserId(1);
                            credentialAttachEntityHi.setAttachmentName(flieType);
                            credentialAttachEntityHi.setSupplierId(equPosSupplierInfoEntityHi.get(0).getSupplierId());
                            credentialAttachEntityHi.setFileName(fileName);
                            credentialAttachEntityHi.setFileId(data.getFileId());
                            credentialAttachEntityHi.setFilePath(data.getFilePath());
                            credentialAttachEntityHi.setDeptmentCode("0E");
                            credentialAttachEntityHi.setIsProductFactory("Y");
                            credentialAttachEntityHi.setFixFlag("N");
                            credentialAttachEntityHi.setFileType("other");
                            credentialAttachEntityHi.setFileId(data.getFileId());
                            equPosZzscCredentialAttachDAO_HI.save(credentialAttachEntityHi);
                        }
                    } catch (Exception e) {
                        System.out.println("报错,可能是供应商为空");
                    }
                    data.setAttribute1("Y");
                    data.setOperatorUserId(1);
                    equPosZzscObFileDAO_HI.saveOrUpdate(data);
                }
            }

        }
        JSONObject dataObject = new JSONObject();
        dataObject.put("data","AAAA");
        return dataObject;
    }

    public static void main(String[] args) {
//        d89ae969b4b247f181dada6df1905d93
//        mongodb://root:123456@localhost:27017/
        MongoClientURI uri = new MongoClientURI("mongodb://spusers2:7343%40sp#@10.82.28.209:27017/database");
        MongoClient mongoClient = new MongoClient(uri);
        MongoDatabase myDatabase = mongoClient.getDatabase("database");
        GridFSBucket gridFSBucket = GridFSBuckets.create(myDatabase, "FactoryFile");
        GridFSFile gridFSFile = gridFSBucket.find(new Document().append("_id", "d89ae969b4b247f181dada6df1905d93")).first();
        if (gridFSFile == null) {
            System.out.println("没有附件");
        } else {
            String fileName = gridFSFile.getExtraElements().get("file_name").toString();
            System.out.println("  : " + fileName);
        }
    }
}

