package com.sie.saaf.base.user.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.user.model.entities.readonly.BasePositionOrg_HI_RO;
import com.sie.saaf.base.user.model.entities.readonly.BasePositionPerson_HI_RO;
import com.sie.saaf.base.user.model.inter.IBasePosition;
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

import java.util.List;

/**
 * @author ZhangJun
 * @creteTime 2017-12-15
 */
@RestController
@RequestMapping("/basePositionService")
public class BasePositionService extends CommonAbstractService {

	private static final Logger logger = LoggerFactory.getLogger(BasePositionService.class);

	@Autowired
	private IBasePosition basePositionServer;

	@Override
	public IBaseCommon getBaseCommonServer() {
		return this.basePositionServer;
	}


	/**
	 * 保存或更新数据
	 * @param params {<br>
	 *                positionId:职务Id,（更新时必填）<br>
	 *            orgId:组织Id,<br>
	 *            positionName:职务名称,<br>
	 *            sourceSystemId:源系统Id,<br>
	 *            startDate:生效日期,<br>
	 *            endDate:失效日期,<br>
	 *            enabled:是否启用,<br>
	 *            operatorUserId:操作者<br>
	 *                versionNum: 版本号（更新时必填）<br>
	 *            }
	 * @return {
	 * status:操作是否成功,E:失败，S:成功
	 * msg:成功或者失败后消息
	 * count:成功的记录数
	 * data:成功的数据
	 * }
	 * @author ZhangJun
	 * @creteTime 2017/12/15
	 */
	@RequestMapping(method = RequestMethod.POST, value = "save")
	@Override
	public String saveOrUpdate(@RequestParam(required = true) String params){
		return super.saveOrUpdate(params);
	}

	/**
	 * 查找数据
	 * @param params
	 * @param pageIndex
	 * @param pageRows
	 * @return {
	 * status:操作是否成功,E:失败，S:成功
	 * msg:成功或者失败后消息
	 * count:成功的记录数
	 * data:成功的数据
	 * }
	 * @author ZhangJun
	 * @creteTime 2017/12/15
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findPagination")
	@Override
	public String findPagination(@RequestParam(required = false) String params,
								 @RequestParam(required = false,defaultValue = "1") Integer pageIndex,
								 @RequestParam(required = false,defaultValue = "10") Integer pageRows) {
		return super.findPagination(params,pageIndex,pageRows);
	}


	/**
	 * 删除数据
	 * @param params 参数id
	 * {
	 *     id:需要删除的数据Id，如果需要删除多个，则用;分隔
	 * }
	 * @return {
	 * status:操作是否成功,E:失败，S:成功
	 * msg:成功或者失败后消息
	 * count:成功的记录数
	 * data:成功的数据
	 * }
	 * @author ZhangJun
	 * @creteTime 2017/12/15
	 */
	@RequestMapping(method = RequestMethod.POST, value = "delete")
	@Override
	public String delete(@RequestParam(required = false) String params) {
		return super.delete(params);
	}

	/**
	 * 查询员工职位
	 *
	 * @author ZhangJun
	 * @creteTime 2017-12-11
	 * @param params JSON参数 {
	 *            personId:人员Id
	 *            }
	 * @return 员工职位列表 <br>
	 *         [{<br>
	 *         creationDate: 创建时间<br>
	 *         enabled: 是否启用,<br>
	 *         endDate: 失效时间,<br>
	 *         lastUpdateDate: 更新时间,<br>
	 *         orgId: 组织Id,<br>
	 *         positionId: 职位Id,<br>
	 *         positionName: 职位名称,<br>
	 *         sourceSystemId: 源系统Id,<br>
	 *         startDate: 生效时间,<br>
	 *         versionNum: 版本号<br>
	 *         }]
	 */
	@RequestMapping(method = RequestMethod.POST,value="findBasePositionsByPersonId")
	public String findBasePositionsByPersonId(@RequestParam(required=false) String params){
	    try{
			JSONObject queryParamJSON = parseObject(params);
	        List<BasePositionPerson_HI_RO> findList = this.basePositionServer.findBasePositionsByPersonId(queryParamJSON.getInteger("personId"));
	        return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", findList.size(), findList).toString();
	    }catch(Exception e){
	        logger.error(e.getMessage(),e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
	    }
	}

	/**
	 * 查询部门下设置的职位
	 *
	 * @author ZhangJun
	 * @creteTime 2017-12-11
	 * @param params JSON参数{
	 * orgId:组织机构Id
	 * }
	 *
	 * @return 部门下设置的职位列表 <br>
	 *         [{<br>
	 *         creationDate: 创建时间<br>
	 *         enabled: 是否启用,<br>
	 *         endDate: 失效时间,<br>
	 *         lastUpdateDate: 更新时间,<br>
	 *         orgId: 组织Id,<br>
	 *         positionId: 职位Id,<br>
	 *         positionName: 职位名称,<br>
	 *         sourceSystemId: 源系统Id,<br>
	 *         startDate: 生效时间,<br>
	 *         versionNum: 版本号<br>
	 *         }]
	 */
	@RequestMapping(method = RequestMethod.POST,value="findBasePositionsByOrgId")
	public String findBasePositionsByOrgId(@RequestParam(required=false) String params,
										   @RequestParam(required = false,defaultValue = "1") Integer pageIndex,
										   @RequestParam(required = false,defaultValue = "10") Integer pageRows){
		try{
			JSONObject queryParamJSON = parseObject(params);
			Pagination<BasePositionOrg_HI_RO> findList = this.basePositionServer.findBasePositionsByOrgId(queryParamJSON.getInteger("orgId"),pageIndex,pageRows);
			JSONObject results = JSONObject.parseObject(JSON.toJSONString(findList));
			results.put(SToolUtils.STATUS, SUCCESS_STATUS);
			results.put(SToolUtils.MSG, "成功");
			return results.toString();
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * 关联base_person_organization分页查询职位
	 *
	 * @author ZhangJun
	 * @creteTime 2017-12-11
	 * @param params
	 *            查询参数<br>
	 *            {<br>
	 *            orgId:组织Id,<br>
	 *            positionName:职位名称,<br>
	 *            sourceSystemId:源系统Id,<br>
	 *            enabled:是否启用,<br>
	 *            startDate:生效时间,<br>
	 *            endDate:失效时间<br>
	 *            }<br>
	 * @param pageIndex
	 *            页码
	 * @param pageRows
	 *            每页查询记录数
	 * @return 根据条件查询的职位列表 <br>
	 *         { <br>
	 *         count: 总记录数,<br>
	 *         curIndex: 当前页索引,<br>
	 *         data: [{<br>
	 *         personId:人员Id,<br>
	 *         creationDate: 创建时间<br>
	 *         enabled: 是否启用,<br>
	 *         endDate: 失效时间,<br>
	 *         lastUpdateDate: 更新时间,<br>
	 *         orgId: 组织Id,<br>
	 *         positionId: 职位Id,<br>
	 *         positionName: 职位名称,<br>
	 *         sourceSystemId: 源系统Id,<br>
	 *         startDate: 生效时间,<br>
	 *         versionNum: 版本号<br>
	 *         }],<br>
	 *         firstIndex: 首页索引,<br>
	 *         lastIndex: 尾页索引,<br>
	 *         nextIndex: 下一页索引,<br>
	 *         pageSize: 每页记录数,<br>
	 *         pagesCount: 总页数,<br>
	 *         preIndex: 上一页索引<br>
	 *         }
	 */
	@RequestMapping(method = RequestMethod.POST,value="findBasePositionsJoinPersonOrg")
	public String findBasePositionsJoinPersonOrg(@RequestParam(required=false) String params,
												 @RequestParam(required=false,defaultValue = "1")Integer pageIndex,
												 @RequestParam(required = false,defaultValue = "10")Integer pageRows){
	    try{
			JSONObject queryParamJSON = parseObject(params);
			Pagination<BasePositionPerson_HI_RO> findList = this.basePositionServer.findBasePositionsJoinPersonOrg(queryParamJSON,pageIndex,pageRows);
			JSONObject results = JSONObject.parseObject(JSON.toJSONString(findList));
			results.put(SToolUtils.STATUS, SUCCESS_STATUS);
			results.put(SToolUtils.MSG, "成功");
			return results.toString();
	    }catch(Exception e){
	        logger.error(e.getMessage(),e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
	    }
	}
}
