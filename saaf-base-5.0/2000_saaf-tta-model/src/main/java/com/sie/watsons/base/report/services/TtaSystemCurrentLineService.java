package com.sie.watsons.base.report.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.sie.saaf.common.bean.UserSessionBean;
import com.sie.watsons.base.report.model.entities.readonly.TtaSystemCurrentLineEntity_HI_RO;
import com.sie.watsons.base.report.model.inter.ITtaSystemCurrentLine;

import com.alibaba.fastjson.JSONObject;
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
import java.util.List;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.common.model.inter.IBaseCommon;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisCluster;

@RestController
@RequestMapping("/ttaSystemCurrentLineService")
public class TtaSystemCurrentLineService extends CommonAbstractService{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaSystemCurrentLineService.class);

	@Autowired
	private ITtaSystemCurrentLine ttaSystemCurrentLineServer;

	@Autowired
	private JedisCluster jedisCluster;

	@Override
	public IBaseCommon getBaseCommonServer(){
		return this.ttaSystemCurrentLineServer;
	}

    /**
     * 批量导入 SystemCurrent的数据
     * @param file
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,value = "saveImportInfo")
    public String saveImportInfo(@RequestParam("file") MultipartFile file){
        try{
            String params = "";
            JSONObject jsonObject = parseObject(params);
            UserSessionBean sessionBean = getUserSessionBean();
            int size = ttaSystemCurrentLineServer.saveImportInfo(jsonObject,file,sessionBean);
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "总共"+size+"条数据导入成功", size, null).toString();
        }catch (Exception e){
            jedisCluster.setex(getUserSessionBean().getCertificate(),3600,"{status:'E',currentStage:'失败',orderNum:"+"'"+e.getMessage()+"+'}");
            return SToolUtils.convertResultJSONObj(e.getMessage().startsWith("[{") ? "ERR_IMPORT" : "E", e.getMessage(), 0, JSONArray.parseArray(e.getMessage())).toString();
        }
    }


    /**
     *
     * @param params JSON参数，查询条件的JSON格式
     * @param pageIndex 页码
     * @param pageRows 每页查询记录数
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,value = "findPagination")
    public String findPagination(@RequestParam(required = false) String params,
                                 @RequestParam(required = false,defaultValue = "1") Integer pageIndex,
                                 @RequestParam(required = false,defaultValue = "10") Integer pageRows){
        try{
            JSONObject jsonObject = parseObject(params);
            Pagination<TtaSystemCurrentLineEntity_HI_RO> result = ttaSystemCurrentLineServer.findInfo(jsonObject,pageIndex,pageRows);
            jsonObject = (JSONObject) JSON.toJSON(result);
            jsonObject.put(SToolUtils.STATUS,"S");
            jsonObject.put(SToolUtils.MSG,SUCCESS_MSG);
            return jsonObject.toString();
        }catch (IllegalArgumentException e){
            LOGGER.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
        }catch (Exception e){
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
        }
    }

    /**
     *
     * @param params
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,value = "deleteImportInfo")
    public String deleteImportInfo(@RequestParam(required = false) String params){
        try{
            JSONObject jsonObject = parseObject(params);
            JSONObject result = ttaSystemCurrentLineServer.deleteImportInfo(jsonObject);
            return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, result).toString();
        }catch (IllegalArgumentException e){
            LOGGER.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
        }catch (Exception e){
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
        }
    }

}