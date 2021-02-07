package com.sie.saaf.common.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Arrays;
import java.util.Date;

/**
 * @author huagntao
 */
public class ResultFileEntity {
    private Long fileId;
    private String sourceFileName;
    private String fileName;
    private String fileType;
    private String filePath;
    private String accessPath;
    private Integer fileStoreSystem;
    private Long fileSize;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date uploadDate;
    private String uploadAuthor;
    private String bucketName;
    private String remoteFileName;
    private byte[] fileContent;
    private String createdBy;
    private String createdByUser;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date creationDate;

    public ResultFileEntity() {
    }

    public ResultFileEntity(String sourceFileName, String filePath, String accessPath, Date uploadDate, String bucketName, String remoteFileName) {
        this.sourceFileName = sourceFileName;
        this.fileName = sourceFileName;
        this.filePath = filePath;
        this.accessPath = accessPath;
        this.uploadDate = uploadDate;
        this.bucketName = bucketName;
        this.remoteFileName = remoteFileName;
    }

    public ResultFileEntity(String sourceFileName, String fileType, String filePath, String accessPath, Date uploadDate, String bucketName, String remoteFileName) {
        this.sourceFileName = sourceFileName;
        this.fileName = sourceFileName;
        this.fileType = fileType;
        this.filePath = filePath;
        this.accessPath = accessPath;
        this.uploadDate = uploadDate;
        this.bucketName = bucketName;
        this.remoteFileName = remoteFileName;
    }


    public ResultFileEntity(String sourceFileName, String fileType, String filePath, String accessPath, Long fileSize, Date uploadDate, String bucketName, String remoteFileName) {
        this.sourceFileName = sourceFileName;
        this.fileName = sourceFileName;
        this.fileType = fileType;
        this.filePath = filePath;
        this.accessPath = accessPath;
        this.fileSize = fileSize;
        this.uploadDate = uploadDate;
        this.bucketName = bucketName;
        this.remoteFileName = remoteFileName;
    }

    public String getSourceFileName() {
        return sourceFileName;
    }

    public void setSourceFileName(String sourceFileName) {
        this.sourceFileName = sourceFileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getAccessPath() {
        return accessPath;
    }

    public void setAccessPath(String accessPath) {
        this.accessPath = accessPath;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    public String getUploadAuthor() {
        return uploadAuthor;
    }

    public void setUploadAuthor(String uploadAuthor) {
        this.uploadAuthor = uploadAuthor;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getRemoteFileName() {
        return remoteFileName;
    }

    public void setRemoteFileName(String remoteFileName) {
        this.remoteFileName = remoteFileName;
    }

    public byte[] getFileContent() {
        return fileContent;
    }

    public void setFileContent(byte[] fileContent) {
        this.fileContent = fileContent;
    }

    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedByUser() {
        return createdByUser;
    }

    public void setCreatedByUser(String createdByUser) {
        this.createdByUser = createdByUser;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Integer getFileStoreSystem() {
        return fileStoreSystem;
    }

    public void setFileStoreSystem(Integer fileStoreSystem) {
        this.fileStoreSystem = fileStoreSystem;
    }

    @Override
    public String toString() {
        return "ResultFileEntity{" +
                "fileId=" + fileId +
                ", sourceFileName='" + sourceFileName + '\'' +
                ", fileName='" + fileName + '\'' +
                ", fileType='" + fileType + '\'' +
                ", filePath='" + filePath + '\'' +
                ", accessPath='" + accessPath + '\'' +
                ", fileStoreSystem=" + fileStoreSystem +
                ", fileSize=" + fileSize +
                ", uploadDate=" + uploadDate +
                ", uploadAuthor='" + uploadAuthor + '\'' +
                ", bucketName='" + bucketName + '\'' +
                ", remoteFileName='" + remoteFileName + '\'' +
                ", fileContent=" + Arrays.toString(fileContent) +
                ", createdBy='" + createdBy + '\'' +
                ", createdByUser='" + createdByUser + '\'' +
                ", creationDate=" + creationDate +
                '}';
    }
}
