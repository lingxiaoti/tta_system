package com.sie.saaf.base.fileupload.model.inter.server;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.fileupload.model.entities.SaafFileUploadEntity_HI;
import com.sie.saaf.base.fileupload.model.entities.readonly.SaafFileUploadEntity_HI_RO;
import com.sie.saaf.base.fileupload.model.inter.ISaafFileUpload;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component("saafFileUploadServer")
public class SaafFileUploadServer extends BaseCommonServer<SaafFileUploadEntity_HI> implements ISaafFileUpload {
    @Autowired
    private ViewObject<SaafFileUploadEntity_HI> saafFileUploadDao_HI;

    @Autowired
    private BaseViewObject<SaafFileUploadEntity_HI_RO> saafFileUploadEntity_HI_RO;


    public Pagination<SaafFileUploadEntity_HI_RO> findSaafFileUploadList(JSONObject parameters, Integer pageIndex,
                                                                         Integer pageRows) throws Exception {
        try {
            StringBuffer hql = new StringBuffer();
            hql.append(SaafFileUploadEntity_HI_RO.QUERY_SQL);
            Map<String, Object> map = new HashMap<String, Object>();

            if (null != parameters.get("sourceCode") && !"".equals(parameters.get("sourceCode").toString())) {
                hql.append(" and vfu.SOURCE_CODE = :sourceCode ");
                map.put("sourceCode", parameters.get("sourceCode"));
            }

            if (null != parameters.get("sourceId") && !"".equals(parameters.get("sourceId").toString())) {
                hql.append(" and vfu.SOURCE_ID = :sourceId ");
                map.put("sourceId", parameters.get("sourceId"));
            }
//			用于传参查询
            SaafToolUtils.parperParam(parameters, "vfu.UPLOAD_ID", "uploadId", hql, map, "=");
            Pagination<SaafFileUploadEntity_HI_RO> rowSet = saafFileUploadEntity_HI_RO.findPagination(hql,SaafToolUtils.getSqlCountString(hql), map, pageIndex,
                    pageRows);
            return rowSet;
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    /**
     * 保存
     *
     * @param parameters
     * @return
     * @throws Exception
     */

    public JSONObject saveSaafFileUpload(JSONObject parameters) throws Exception {
        try {
            String sourceCode = "";
            Integer sourceId = null;
            int varUserId = SToolUtils.object2Int(parameters.get("varUserId"));

            JSONArray fileData = parameters.getJSONArray("fileData");

            for (int i = 0; i < fileData.size(); i++) {
                JSONObject json = fileData.getJSONObject(i);
                SaafFileUploadEntity_HI row = null;
                // 判断是否存在id
                if (null == json.get("uploadId") || "".equals(SToolUtils.object2String(json.get("uploadId")).trim())) {
                    // 判断是否新增
                    row = new SaafFileUploadEntity_HI();
                    row.setStartDateActive(new Date());
                    row.setCreatedBy(varUserId);
                    row.setCreationDate(new Date());
                } else {
                    row = saafFileUploadDao_HI.findByProperty("uploadId", SToolUtils.object2Int(json.get("uploadId"))).get(0);
                }

                if (null != json.get("fileName") && !"".equals(json.getString("fileName").trim())) {
                    row.setFileName(SToolUtils.object2String(json.get("fileName")));
                }
                if (null != json.get("fileSize") && !"".equals(json.getString("fileSize").trim())) {
                    row.setFileSize(new BigDecimal(SToolUtils.object2String(json.get("fileSize"))));
                }
                if (null != json.get("fileType") && !"".equals(json.getString("fileType").trim())) {
                    row.setFileType(SToolUtils.object2String(json.get("fileType")));
                }
                if (null != json.get("sourceCode") && !"".equals(json.getString("sourceCode").trim())) {
                    row.setSourceCode(SToolUtils.object2String(json.get("sourceCode")));
                }
                if (null != json.get("fileAddress") && !"".equals(json.getString("fileAddress").trim())) {
                    row.setFileAddress(SToolUtils.object2String(json.get("fileAddress")));
                }
                if (null != json.get("sourceId") && !"".equals(json.getString("sourceId").trim())) {
                    row.setSourceId(Integer.parseInt(SToolUtils.object2String(json.get("sourceId"))));
                }
                if (null != json.get("description") && !"".equals(json.getString("description").trim())) {
                    row.setDescription(SToolUtils.object2String(json.get("description")));
                }

                row.setStatus("NEW");
                row.setOperatorUserId(varUserId);
                saafFileUploadDao_HI.saveOrUpdate(row);
                sourceCode=row.getSourceCode();
                sourceId=row.getSourceId();
            }
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("sourceCode",sourceCode);
            jsonObject.put("sourceId",sourceId);
            Pagination<SaafFileUploadEntity_HI_RO> saafFileUploadList = findSaafFileUploadList(jsonObject, 1, -1);
            return SToolUtils.convertResultJSONObj("S", " 保存成功!", saafFileUploadList.getData().size(), saafFileUploadList.getData());

        } catch (Exception e) {
            throw new Exception(e);
        }

    }


    /**
     * 其他服务调用的接口
     *
     * @param parameters
     * @param sourceId
     * @return
     * @throws Exception
     */

    public JSONObject saveSaafFileUpload(JSONObject parameters, Integer sourceId) throws Exception {
        try {

            int varUserId = SToolUtils.object2Int(parameters.get("varUserId"));

            JSONArray fileData = parameters.getJSONArray("fileData");
            if (fileData == null) {
                return null;
            }
            for (int i = 0; i < fileData.size(); i++) {
                JSONObject json = fileData.getJSONObject(i);
                SaafFileUploadEntity_HI row = null;
                // 判断是否存在id
                if (null == json.get("uploadId") || "".equals(SToolUtils.object2String(json.get("uploadId")).trim())) {
                    // 判断是否新增
                    row = new SaafFileUploadEntity_HI();
                    row.setStartDateActive(new Date());
                    row.setCreatedBy(varUserId);
                    row.setCreationDate(new Date());
                } else {
                    row = saafFileUploadDao_HI.findByProperty("uploadId", SToolUtils.object2Int(json.get("uploadId"))).get(0);
                }

                if (null != json.get("fileName") && !"".equals(json.getString("fileName").trim())) {
                    row.setFileName(SToolUtils.object2String(json.get("fileName")));
                }
                if (null != json.get("fileSize") && !"".equals(json.getString("fileSize").trim())) {
                    row.setFileSize(new BigDecimal(SToolUtils.object2String(json.get("fileSize"))));
                }
                if (null != json.get("fileType") && !"".equals(json.getString("fileType").trim())) {
                    row.setFileType(SToolUtils.object2String(json.get("fileType")));
                }
                if (null != json.get("sourceCode") && !"".equals(json.getString("sourceCode").trim())) {
                    row.setSourceCode(SToolUtils.object2String(json.get("sourceCode")));
                }
                if (null != json.get("fileAddress") && !"".equals(json.getString("fileAddress").trim())) {
                    row.setFileAddress(SToolUtils.object2String(json.get("fileAddress")));
                }
                if (null != json.get("sourceId") && !"".equals(json.getString("sourceId").trim())) {
                    row.setSourceId(Integer.parseInt(SToolUtils.object2String(json.get("sourceId"))));
                } else {
                    row.setSourceId(sourceId);
                }
                if (null != json.get("description") && !"".equals(json.getString("description").trim())) {
                    row.setDescription(SToolUtils.object2String(json.get("description")));
                }

                row.setStatus("NEW");
                row.setOperatorUserId(varUserId);
                saafFileUploadDao_HI.saveOrUpdate(row);
            }
            return SToolUtils.convertResultJSONObj("S", " 保存成功!", 1, "");

        } catch (Exception e) {
            throw new Exception(e);
        }

    }

    /**
     * 删除
     *
     * @param parameters
     * @return
     * @throws Exception
     */

    public JSONObject deleteSaafFileUpload(JSONObject parameters) throws Exception {
        try {
            int varUserId = SToolUtils.object2Int(parameters.get("varUserId"));
            SaafFileUploadEntity_HI row = null;
            if (null == parameters.get("uploadId")
                    || "".equals(SToolUtils.object2String(parameters.get("uploadId")).trim())) {
                return SToolUtils.convertResultJSONObj("F", "参数错误!", 1, row);
            } else {
                List<SaafFileUploadEntity_HI> list = saafFileUploadDao_HI.findByProperty("uploadId",
                        SToolUtils.object2Int(parameters.get("uploadId")));
                if (list.size() <= 0) {
                    return SToolUtils.convertResultJSONObj("F", "未找到上传信息!", 1, row);
                }
                row = list.get(0);
                row.setStatus("DELETED");
                row.setOperatorUserId(varUserId);
                saafFileUploadDao_HI.saveOrUpdate(row);
            }
            return SToolUtils.convertResultJSONObj("S", " 删除成功!", 1, row);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }


}
