package com.sie.saaf.base.user.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.accredit.annotation.Permission;
import com.sie.saaf.base.user.model.inter.IBasePdaInvCfg;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ZhangJun
 * @createTime 2018-03-15 09:56
 * @description
 */
@RestController
@RequestMapping("/basePdaInvCfgService")
public class BasePdaInvCfgService extends CommonAbstractService {
	private static final Logger logger = LoggerFactory.getLogger(BasePdaInvCfgService.class);
	@Autowired
	private IBasePdaInvCfg basePdaInvCfgServer;

	@Override
	public IBaseCommon<?> getBaseCommonServer() {
		return basePdaInvCfgServer;
	}

	/**
	 * 根据Id查询数据
	 * @param params 参数id
	 * {
	 *     id:主键Id
	 * }
	 * @return
	 * @author ZhangJun
	 * @creteTime 2018/3/15
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findById")
	public String findById(String params) {
		return super.findById(params);
	}

	/**
	 * 查找数据
	 * @param params
	 * @return
	 * @author ZhangJun
	 * @createTime 2018/3/15
	 * @description 查找数据
	 */
	@RequestMapping(method = RequestMethod.POST,value="findList")
	public String findList(@RequestParam(required=false) String params){
		return super.findList(params);
	}

	/**
	 * 查找数据
	 * @param params
	 * @param pageIndex
	 * @param pageRows
	 * @return
	 * @author ZhangJun
	 * @createTime 2018/3/15
	 */
	@Permission(menuCode = "PDAYHZKWH")
	@RequestMapping(method = RequestMethod.POST, value = "findPagination")
	public String findPagination(@RequestParam(required = false) String params,
			@RequestParam(required = false,defaultValue = "1") Integer pageIndex,
			@RequestParam(required = false,defaultValue = "10") Integer pageRows) {
		try{
			JSONObject queryParamJSON = parseObject(params);
			queryParamJSON.put("operationOrgIds",baseAccreditCacheServer.getOrgId(getSessionUserId()));
			Pagination findList = this.basePdaInvCfgServer.findPdaInvROPagination(queryParamJSON,pageIndex,pageRows);

			JSONObject results = JSONObject.parseObject(JSON.toJSONString(findList));
			results.put(SToolUtils.STATUS, SUCCESS_STATUS);
			results.put(SToolUtils.MSG, "成功");
			return results.toString();
		}catch (Exception e){
			logger.error(e.getMessage(),e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * 保存或更新数据
	 * @param params
	 * @return
	 * @author ZhangJun
	 * @createTime 2018/3/15
	 */
	@RequestMapping(method = RequestMethod.POST, value = "save")
	public String saveOrUpdate(@RequestParam(required = true) String params){
		return super.saveOrUpdate(params);
	}

	/**
	 * 删除数据
	 * @param params 参数id
	 * {
	 *     id:需要删除的数据Id，如果需要删除多个，则用;分隔
	 * }
	 * @return
	 * @author ZhangJun
	 * @createTime 2018/3/15
	 */
	@RequestMapping(method = RequestMethod.POST, value = "delete")
	public String delete(@RequestParam(required = false) String params) {
		return super.delete(params);
	}


    /**
     * basePdaInvCfgSync 初始化同步
     * @param params 无参
     * @return
     * @author ZhangJun
     * @createTime 2018/3/15
     */
    @RequestMapping(method = RequestMethod.POST, value = "basePdaInvCfgSync")
    public String basePdaInvCfgSync(@RequestParam(required = false) String params) {
        try{
            JSONObject queryParamJSON = parseObject(params);

            String result = this.basePdaInvCfgServer.basePdaInvCfgSync(queryParamJSON);

            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, result, 0, null).toString();
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }

}
