package com.sie.saaf.base.shiro.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.shiro.model.entities.BaseRoleEntity_HI;
import com.sie.saaf.base.shiro.model.inter.IBaseRole;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.common.util.SaafToolUtils;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.dao.ViewObject;
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
 * @creteTime 2017-12-18
 */
@RestController
@RequestMapping("/baseRoleService")
public class BaseRoleService extends CommonAbstractService {
	private static final Logger logger = LoggerFactory.getLogger(BaseRoleService.class);
	@Autowired
	private IBaseRole baseRoleServer;
	@Override
	public IBaseCommon getBaseCommonServer() {
		return baseRoleServer;
	}

//    @Autowired
//    private ViewObject<BaseRoleEntity_HI> baseRoleDAO_HI;

	/**
	 * 查找数据
	 * @param params
	 * @param pageIndex
	 * @param pageRows
	 * @return
	 * @author ZhangJun
	 * @creteTime 2017/12/18
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findPagination")
	public String findPagination(@RequestParam(required = false) String params,
			@RequestParam(required = false,defaultValue = "1") Integer pageIndex,
			@RequestParam(required = false,defaultValue = "10") Integer pageRows) {
		JSONObject jsonObject = parseObject(params);
		jsonObject= SaafToolUtils.cleanNull(jsonObject,"systemCode");
		params=jsonObject.toJSONString();
		return super.findPagination(params,pageIndex,pageRows);
	}

	/**
	 * 保存或更新数据
	 * @param params
	 * @return
	 * @author ZhangJun
	 * @creteTime 2017/12/18
	 */
	@RequestMapping(method = RequestMethod.POST, value = "save")
	public String saveOrUpdate(@RequestParam(required = true) String params){
        JSONObject jsonObject = parseObject(params);
        Integer    roleId     = jsonObject.getInteger("roleId");
        List<BaseRoleEntity_HI> list = baseRoleServer.check(jsonObject);

        if(list != null && list.size() > 0){
            if(roleId != null && roleId.compareTo(list.get(0).getRoleId()) == 0){
                    return super.saveOrUpdate(params);
            }else {
                return SToolUtils.convertResultJSONObj(ERROR_STATUS, "同一系统下，不允许存在相同名称或编号的角色", 0, null).toString();
            }
        }else {
            return super.saveOrUpdate(params);
        }
	}

	/**
	 * 删除数据
	 * @param params 参数id
	 * {
	 *     id:需要删除的数据Id，如果需要删除多个，则用;分隔
	 * }
	 * @return
	 * @author ZhangJun
	 * @creteTime 2017/12/18
	 */
	@RequestMapping(method = RequestMethod.POST, value = "delete")
	public String delete(@RequestParam(required = false) String params) {
		return super.delete(params);
	}

	/**
	 * 根据菜单Id查询角色列表
	 *
	 * @param params JSON查询参数 {menuId:菜单Id}
	 *
	 * @return 菜单与权限关联视图列表<br>
	 *
	 * @author ZhangJun
	 * @creteTime 2017/12/13
	 */
	@RequestMapping(method = RequestMethod.POST,value="findBaseRoleByMenuId")
	public String findBaseRoleByMenuId(@RequestParam(required=false) String params){
	    try{
			JSONObject queryParamJSON = parseObject(params);
			Integer menuId = queryParamJSON.getInteger("menuId");
			List findList = this.baseRoleServer.findBaseRoleByMenuId(menuId);

	        return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", findList.size(), findList).toString();
	    }catch(Exception e){
	        logger.error(e.getMessage(),e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
	    }
	}

	/**
	 * 查询菜单与权限关联视图
	 *
	 * @param params JSON查询参数
	 * {<br>
	 * roleName:角色名称<br>
	 * roleCode:角色编码<br>
	 * systemCode:系统编码<br>
	 * startDateActive:生效时间<br>
	 * endDateActive:失效时间<br>
	 * menuId:菜单Id
	 * }
	 *
	 * @return 菜单与权限关联视图列表<br>
	 * [{<br>
	 * menuId:菜单Id,<br>
	 * menuParentId:上级菜单Id,<br>
	 * menuCode:菜单编码,<br>
	 * orderNo:排序号,<br>
	 * menuName:菜单名称,<br>
	 * menuDesc:菜单提示信息,<br>
	 * levelId:层级ID,<br>
	 * menuType:菜单类型：10-菜单节点；20-功能节点,<br>
	 * parameter:参数,<br>
	 * systemCode:系统编码,<br>
	 * imageLink:图片样式,<br>
	 * imageColor:图标颜色,<br>
	 * htmlUrl:HTML路由链接,<br>
	 * from_type:访问来源,<br>
	 * enableFlag:是否启用,<br>
	 * startDateActive:启用时间,<br>
	 * endDateActive:失效时间,<br>
	 * creationDate:创建日期,<br>
	 * createdBy:创建人,<br>
	 * lastUpdatedBy:更新人,<br>
	 * lastUpdateDate:更新日期,<br>
	 * versionNum:版本号,<br>
	 * roleId:角色Id,<br>
	 * roleName:角色名称,<br>
	 * roleCode:角色编号,<br>
	 * roleDesc:角色描述,<br>
	 * roleStartDateActive:角色生效时间,<br>
	 * roleEndDateActive:角色失效时间,<br>
	 * roleVersionNum;//角色版本号,<br>
	 * }]
	 * @author ZhangJun
	 * @creteTime 2017/12/13
	 */
	@RequestMapping(method = RequestMethod.POST,value="findBaseRoleJoinMenu")
	public String findBaseRoleJoinMenu(@RequestParam(required=false) String params){
	    try{
			JSONObject queryParamJSON = parseObject(params);
			List findList = this.baseRoleServer.findBaseRoleJoinMenu(queryParamJSON);

	        return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", findList.size(), findList).toString();
	    }catch(Exception e){
	        logger.error(e.getMessage(),e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
	    }
	}

	/**
	 * 根据资源id查询可访问该权限的角色列表
	 *
	 * @param params JSON参数{resourceId:资源Id}
	 * @param pageIndex 页码
	 * @param pageRows 每页查询记录数
	 *
	 * @return 角色列表<br>
	 * { <br>
	 * count: 总记录数,<br>
	 * curIndex: 当前页索引,<br>
	 * data: [{<br>
	 * roleCode: 角色编码,<br>
	 * roleDesc: 角色描述,<br>
	 * roleId: 角色Id,<br>
	 * roleName: 角色名称,<br>
	 * startDateActive: 生效时间,<br>
	 * systemCode: 失效时间<br>
	 * creationDate:创建日期<br>
	 * createdBy:创建人<br>
	 * lastUpdatedBy:更新人<br>
	 * lastUpdateDate:更新日期<br>
	 * versionNum:版本号<br>
	 * }],<br>
	 * firstIndex: 首页索引,<br>
	 * lastIndex: 尾页索引,<br>
	 * nextIndex: 下一页索引,<br>
	 * pageSize: 每页记录数,<br>
	 * pagesCount: 总页数,<br>
	 * preIndex: 上一页索引<br>
	 * }
	 *
	 * @author ZhangJun
	 * @creteTime 2017/12/14
	 */
	@RequestMapping(method = RequestMethod.POST,value="findBaseRoleByResourceId")
	public String findBaseRoleByResourceId(@RequestParam(required=false) String params,
										   @RequestParam(required = false,defaultValue = "1") Integer pageIndex,
										   @RequestParam(required = false,defaultValue = "10") Integer pageRows){
	    try{
			JSONObject queryParamJSON = parseObject(params);
	        Integer resourceId = queryParamJSON.getInteger("resourceId");

			Pagination findList = this.baseRoleServer.findBaseRoleByResourceId(resourceId,pageIndex,pageRows);

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
