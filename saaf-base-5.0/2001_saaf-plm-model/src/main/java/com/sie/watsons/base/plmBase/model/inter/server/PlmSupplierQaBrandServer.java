package com.sie.watsons.base.plmBase.model.inter.server;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.sie.watsons.base.plmBase.model.inter.IPlmSupplierQaNonObInfo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.plmBase.model.entities.PlmSupplierQaBrandEntity_HI;
import com.sie.watsons.base.plmBase.model.entities.PlmSupplierQaNonObInfoEntity_HI;
import com.sie.watsons.base.plmBase.model.entities.readonly.PlmSupplierQaBrandEntity_HI_RO;
import com.sie.watsons.base.plmBase.model.inter.IPlmSupplierQaBrand;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;

@Component("plmSupplierQaBrandServer")
public class PlmSupplierQaBrandServer extends
        BaseCommonServer<PlmSupplierQaBrandEntity_HI> implements
        IPlmSupplierQaBrand {
    private static final Logger LOGGER = LoggerFactory
            .getLogger(PlmSupplierQaBrandServer.class);
    @Autowired
    private ViewObject<PlmSupplierQaBrandEntity_HI> plmSupplierQaBrandDAO_HI;
    @Autowired
    private BaseViewObject<PlmSupplierQaBrandEntity_HI_RO> plmSupplierQaBrandDAO_HI_RO;
    @Autowired
    private ViewObject<PlmSupplierQaNonObInfoEntity_HI> plmSupplierQaNonObInfoDAO_HI;

    @Autowired
    private IPlmSupplierQaNonObInfo plmSupplierQaNonObInfoServer;

    public PlmSupplierQaBrandServer() {
        super();
    }

    @Override
    public Pagination<PlmSupplierQaBrandEntity_HI_RO> findPlmSupplierQaBrandInfo(
            JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
        StringBuffer sql = new StringBuffer(
                PlmSupplierQaBrandEntity_HI_RO.QUERY_SQL);
        Map<String, Object> paramsMap = new HashMap<>();
        com.sie.saaf.common.util.SaafToolUtils.parperHbmParam(
                PlmSupplierQaBrandEntity_HI_RO.class, queryParamJSON, sql,
                paramsMap);
        sql.append(" order by psqb.LAST_UPDATE_DATE desc");
        Pagination<PlmSupplierQaBrandEntity_HI_RO> pagination = plmSupplierQaBrandDAO_HI_RO
                .findPagination(sql, paramsMap, pageIndex, pageRows);
        return pagination;
    }

    public List<PlmSupplierQaBrandEntity_HI> savePlmSupplierQaBrandInfo(
            JSONObject queryParamJSON) {

        // if (queryParamJSON.getJSONArray("plmSupplierQaBrandList").size()==1)
        // {
        // PlmSupplierQaBrandEntity_HI data =
        // JSON.toJavaObject(queryParamJSON.getJSONArray("plmSupplierQaBrandList").getJSONObject(0),
        // PlmSupplierQaBrandEntity_HI.class);
        // plmSupplierQaBrandDAO_HI.saveOrUpdate(data);
        // List<PlmSupplierQaBrandEntity_HI> dataList = new ArrayList<>();
        // dataList.add(data);
        // return dataList;
        // }

        if (queryParamJSON.get("plmSupplierQaBrandList") instanceof JSONArray) {
            List<PlmSupplierQaBrandEntity_HI> dataList = JSON.parseArray(
                    queryParamJSON.getJSONArray("plmSupplierQaBrandList")
                            .toString(), PlmSupplierQaBrandEntity_HI.class);
            Set<String> qaBrandSet = new HashSet<>();
            Set<String> qaBrandEnSet = new HashSet<>();
            for (PlmSupplierQaBrandEntity_HI data : dataList) {
                data.setOperatorUserId(queryParamJSON.getInteger("varUserId"));
                if (!SaafToolUtils.isNullOrEmpty(data.getBrandCnName())) {
                    qaBrandSet.add(data.getBrandCnName());
                }
                if (!SaafToolUtils.isNullOrEmpty(data.getBrandEnName())) {
                    qaBrandEnSet.add(data.getBrandEnName());
                }
                if (SaafToolUtils.isNullOrEmpty(data
                        .getPlmSupplierQaNonObInfoId())) {
                    data.setPlmSupplierQaNonObInfoId(queryParamJSON
                            .getInteger("plmSupplierQaNonObInfoId"));
                }
                plmSupplierQaBrandDAO_HI.saveOrUpdate(data);
            }
            // 保存行数据生产商、经销商名至头表
            if (dataList.size() != 0) {
                List<PlmSupplierQaNonObInfoEntity_HI> headerList = plmSupplierQaNonObInfoDAO_HI
                        .findByProperty("plmSupplierQaNonObInfoId", dataList
                                .get(0).getPlmSupplierQaNonObInfoId());
                if (headerList.size() == 0)
                    throw new IllegalArgumentException("未获取头表信息，请刷新重试！");
                PlmSupplierQaNonObInfoEntity_HI data = headerList.get(0);
                // data.setBrandCnName(qaBrandSet.toString()
                // .substring(1, qaBrandSet.toString().length() - 1)
                // .replace(" ", ""));
                // data.setBrandEnName(qaBrandEnSet.toString()
                // .substring(1, qaBrandEnSet.toString().length() - 1)
                // .replace(" ", ""));
                plmSupplierQaNonObInfoDAO_HI.saveOrUpdate(data);
            }
            return dataList;
        }

        return null;

    }

    @Override
    public Integer deletePlmSupplierQaBrandInfo(JSONObject queryParamJSON) throws Exception {
        if (SaafToolUtils.isNullOrEmpty(queryParamJSON
                .getJSONArray("plmSupplierQaBrandList"))) {
            Integer plmSupplierQaNonObInfoId = queryParamJSON.getInteger("plmSupplierQaNonObInfoId");
            if (null != plmSupplierQaNonObInfoId) {
                // 校验资质组删除
                saveCheckSupplierQaDelete(plmSupplierQaNonObInfoId);
            }
            PlmSupplierQaBrandEntity_HI entity = JSON.parseObject(
                    queryParamJSON.toString(),
                    PlmSupplierQaBrandEntity_HI.class);
            plmSupplierQaBrandDAO_HI.delete(entity);
            return 1;
        }
        List<PlmSupplierQaBrandEntity_HI> dataList = JSON.parseArray(
                queryParamJSON.getJSONArray("plmSupplierQaBrandList")
                        .toString(), PlmSupplierQaBrandEntity_HI.class);
        dataList.forEach(row->{
            // 校验资质组删除
            try {
                saveCheckSupplierQaDelete(row.getPlmSupplierQaNonObInfoId());
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        });
        plmSupplierQaBrandDAO_HI.deleteAll(dataList);
        return dataList.size();
    }

    /**
     * 校验资质组删除
     *
     * @param plmSupplierQaNonObInfoId
     * @throws Exception
     */
    @Override
    public void saveCheckSupplierQaDelete(Integer plmSupplierQaNonObInfoId) throws Exception {
        PlmSupplierQaNonObInfoEntity_HI entity = plmSupplierQaNonObInfoDAO_HI.getById(plmSupplierQaNonObInfoId);
        JSONObject jq = new JSONObject();
        jq.put("supplierCode", entity.getSupplierCode());
        JSONObject re = plmSupplierQaNonObInfoServer.getProductSupplier(jq.toJSONString());
        Integer cc = re.getInteger("data");
        if (cc > 0) {
            throw new Exception("该供应商的资质组有在新增商品过程中使用到,不允许删除!");
        }
    }

}
