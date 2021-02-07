package com.sie.watsons.base.api.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.watsons.base.product.model.entities.readonly.PlmProductHeadEntity_HI_RO;

import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2019/12/13/013.
 */
public interface IPlmSupProduct {

     void productToMainCSV(JSONObject queryParamJSON, List<PlmProductHeadEntity_HI_RO> listMain) throws IOException ;

    void productToSupsCSV(JSONObject queryParamJSON, List<PlmProductHeadEntity_HI_RO> listMain) throws IOException ;

    void productToBarCodeCSV(JSONObject queryParamJSON, List<PlmProductHeadEntity_HI_RO> listMain) throws IOException ;

    void productToPriceCSV(JSONObject queryParamJSON, List<PlmProductHeadEntity_HI_RO> listMain) throws Exception;

    void productToConsCSV(JSONObject queryParamJSON, List<PlmProductHeadEntity_HI_RO> listCon) throws IOException ;

    void productToCvwCSV(JSONObject queryParamJSON, List<PlmProductHeadEntity_HI_RO> listCon) throws IOException ;

    void productToDrugCSV(JSONObject queryParamJSON, List<PlmProductHeadEntity_HI_RO> listMain) throws IOException ;

    /**
     * 根据  PlmProductHeadEntity_HI_RO.QUERY 查询数据
     * @return
     */
    List<PlmProductHeadEntity_HI_RO> findByQuery();
    /**
     * 根据  PlmProductHeadEntity_HI_RO.QUERY2 查询数据
     * @return
     */
    List<PlmProductHeadEntity_HI_RO> findByQuery2();

    /**
     * 获取结果
     * @param params
     */
    String csvGetResult(String params,String timeStr) throws IOException;

    /**
     * 生成可销售属性
     * @param queryParamJSON
     */
    void productSalePropertiesToCSV(JSONObject queryParamJSON) throws Exception;


    /**
     * 上传服务器csv
     * @param user
     * @param password
     * @param host
     * @param localFile
     * @param path
     * @param port 端口号
     * @param open 协议 sftp 和 ftp
     * @return
     */
    Boolean uploadFile(String user, String password, String host,
                       String localFile, String path,Integer port,String open);

}
