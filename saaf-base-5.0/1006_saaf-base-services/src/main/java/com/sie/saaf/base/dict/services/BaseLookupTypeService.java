package com.sie.saaf.base.dict.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.dict.model.entities.BaseLookupTypesEntity_HI;
import com.sie.saaf.base.dict.model.entities.readonly.BaseLookupTypeEntity_HI_RO;
import com.sie.saaf.base.dict.model.inter.IBaseLookupTypes;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.common.util.SaafToolUtils;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.paging.Pagination;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * @author huangtao
 * @description 数据字典头表
 * @createTime 2017年12月11日 21:08:17
 */

@RestController
@RequestMapping("/baseLookupTypeService")
public class BaseLookupTypeService extends CommonAbstractService {
    private static final Logger log = LoggerFactory.getLogger(BaseLookupTypeService.class);

    @Autowired
    private IBaseLookupTypes baseLookupTypesServer;

    /**
     * @param params    ｛
     *                  parentTypeId       父节点id
     *                  lookupTypeId       主键ID
     *                  lookupType         数据字典类型
     *                  systemCode         系统编码
     *                  meaning            说明
     *                  customizationLevel 系统级别或是用户级别
     *                  ｝
     * @param pageIndex
     * @param pageRows
     * @description 数据字典头表分页查询
     */
    @RequestMapping(method = RequestMethod.POST, value = "find")
    public String find(@RequestParam(required = false) String params,
                       @RequestParam(required = false) String pageIndex,
                       @RequestParam(required = false) String pageRows) {
        try {
            JSONObject jsonObject =  new JSONObject();

            if (StringUtils.isNotBlank(params))
                jsonObject = JSON.parseObject(params);
            jsonObject= SaafToolUtils.cleanNull(jsonObject,"systemCode","customizationLevel");
            Pagination<BaseLookupTypeEntity_HI_RO> pagination= baseLookupTypesServer.findBaseLookupTypesPagination(jsonObject, Integer.valueOf(Objects.toString(pageIndex, "-1")), Integer.valueOf(Objects.toString(pageRows, "-1")));
            jsonObject = (JSONObject) JSON.toJSON(pagination);
            jsonObject.put(SToolUtils.STATUS, "S");
            jsonObject.put(SToolUtils.MSG, SUCCESS_MSG);
            return jsonObject.toString();
        } catch (IllegalArgumentException e) {
            log.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
        }
    }

    @Override
    public IBaseCommon<?> getBaseCommonServer() {
        return null;
    }

    /**
     * @param params ｛
     *               parentTypeId       父节点id
     *               lastUpdateDate     更新日期
     *               versionNum         版本号
     *               description        描述
     *               lookupTypeId       主键ID
     *               lookupType         数据字典类型
     *               systemCode         系统编码
     *               createdBy          创建人
     *               meaning            说明
     *               customizationLevel 系统级别或是用户级别
     *               ｝
     * @description 数据字典头表保存/修改
     */
    @RequestMapping(method = RequestMethod.POST, value = "saveOrUpdate")
    public String saveOrUpdate(@RequestParam(required = true) String params) {
        try {
            int userId = -1;
            JSONObject jsonObject = JSON.parseObject(params);
            BaseLookupTypesEntity_HI instance = baseLookupTypesServer.saveOrUpdateBaseLookupType(jsonObject, userId);
            return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, instance).toString();
        } catch (IllegalArgumentException e) {
            log.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
        }
    }

    /**
     * @param params-id 主键
     * @description 删除数据字典头表
     */
    @RequestMapping(method = RequestMethod.POST, value = "delete")
    public String delete(@RequestParam(required = false) String params) {
        try {
            if (StringUtils.isBlank(params)) {
                return SToolUtils.convertResultJSONObj("F", "缺少参数:id", 0, null).toString();
            }
            JSONObject jsonObject = JSON.parseObject(params);
            String[] ids = jsonObject.getString("id").split(",");
            for (String id : ids) {
                baseLookupTypesServer.delete(Integer.parseInt(id));
            }
            return SToolUtils.convertResultJSONObj("S",SUCCESS_MSG , 0, null).toString();
        } catch (IllegalArgumentException e) {
            log.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
        }  catch (Exception e) {
            log.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
        }
    }


}
