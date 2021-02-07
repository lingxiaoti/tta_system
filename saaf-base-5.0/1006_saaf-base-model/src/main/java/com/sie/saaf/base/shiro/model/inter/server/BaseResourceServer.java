package com.sie.saaf.base.shiro.model.inter.server;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.shiro.model.entities.BaseResourceEntity_HI;
import com.sie.saaf.base.shiro.model.entities.BaseResponsibilityRoleEntity_HI;
import com.sie.saaf.base.shiro.model.entities.readonly.BaseMenuResource_HI_RO;
import com.sie.saaf.base.shiro.model.entities.readonly.BaseRoleResource_HI_RO;
import com.sie.saaf.base.shiro.model.inter.IBaseResource;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("baseResourceServer")
public class BaseResourceServer extends BaseCommonServer<BaseResourceEntity_HI> implements IBaseResource {
//    private static final Logger LOGGER = LoggerFactory.getLogger(BaseResourceServer.class);
    @Autowired
    private ViewObject<BaseResourceEntity_HI> baseResourceDAO_HI;
    @Autowired
    private BaseViewObject<BaseRoleResource_HI_RO> baseRoleResourceDAO_HI_RO;
    @Autowired
    private BaseViewObject<BaseMenuResource_HI_RO> baseMenuResourceDAO_HI_RO;
    @Autowired
    private ViewObject<BaseResponsibilityRoleEntity_HI> baseResponsibilityRoleDAO_HI;

    public BaseResourceServer() {
        super();
    }

    /**
     * 校验编码
     *
     * @author ZhangJun
     * @createTime 2018/3/16
     * @description 校验编码
     * 校验规则：同一菜单下，不能存在相同的编码资源
     */
    @Override
    public JSONObject validCodeOrName(JSONObject queryParamJSON) {
        String resourceCode = queryParamJSON.getString("resourceCode");
        Integer menuId = queryParamJSON.getInteger("menuId");

        JSONObject result = new JSONObject();
        //验证名称或编码是否已存在
        Map<String, Object> params = new HashMap<>();
        StringBuffer sql = new StringBuffer("from BaseResourceEntity_HI where menuId=:menuId and resourceCode=:resourceCode");
        params.put("menuId", menuId);
        params.put("resourceCode", resourceCode);
        if (queryParamJSON.containsKey("resourceId") && StringUtils.isNotBlank("resourceId")) {
            //如果是新增，则校验除自己以外不能有相同的编码
            Integer resourceId = queryParamJSON.getInteger("resourceId");
            sql.append(" and resourceId!=:resourceId");
            params.put("resourceId",resourceId);
        }

        List<BaseResourceEntity_HI> list = baseResourceDAO_HI.findList(sql, params);
        if (list != null && !list.isEmpty()) {
            result.put("validFlag", false);
            result.put("msg", "该功能下已存在相同的编码，请确认后再操作！");
            return result;
        }

        result.put("validFlag", true);

        return result;
    }

    /**
     * 分页查询数据
     *
     * @param queryParamJSON JSON参数<br>
     *                       {<br>
     *                       menuId:菜单Id<br>
     *                       resourceCode:资源编码<br>
     *                       resourceType:资源类型<br>
     *                       resourceName:资源名称<br>
     *                       }
     * @param pageIndex      页码
     * @param pageRows       每页查询记录数
     * @return 分页数据列表<br>
     * { <br>
     * count: 总记录数,<br>
     * curIndex: 当前页索引,<br>
     * data: [{<br>
     * resourceId:资源标识<br>
     * menuId:菜单Id，节点标识 对应到功能<br>
     * resourceCode:资源编号<br>
     * buttonUrl:按钮对应的执行方法地址<br>
     * orderNo:排序号<br>
     * resourceType:类型标识(按钮、方法、字段名、代码片段)<br>
     * resourceName:资源名称<br>
     * resourceDesc:资源描述<br>
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
     * @author ZhangJun
     * @creteTime 2017/12/14
     */
    @Override
    public Pagination<BaseResourceEntity_HI> findPagination(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
        return super.findPagination(queryParamJSON, pageIndex, pageRows);
    }

    /**
     * 保存一条数据
     *
     * @param queryParamJSON JSON参数 <br>
     *                       {<br>
     *                       resourceId:资源标识（更新数据时必填）<br>
     *                       menuId:菜单Id，节点标识 对应到功能<br>
     *                       resourceCode:资源编号<br>
     *                       buttonUrl:按钮对应的执行方法地址<br>
     *                       orderNo:排序号<br>
     *                       resourceType:类型标识(按钮、方法、字段名、代码片段)<br>
     *                       resourceName:资源名称<br>
     *                       resourceDesc:资源描述<br>
     *                       versionNum:版本号（更新数据时必填）<br>
     *                       operatorUserId:操作用户<br>
     *                       }
     * @return BaseResourceEntity_HI对象
     * @author ZhangJun
     * @creteTime 2017/12/14
     */
    @Override
    public BaseResourceEntity_HI saveOrUpdate(JSONObject queryParamJSON) {
        return super.saveOrUpdate(queryParamJSON);
    }

    /**
     * 设置查询条件
     *
     * @param queryParamJSON 入参
     * @param paramsMap      sql或hql参数
     * @param sql            sql或hql
     * @param isHql          是否HQL查询，如果是HQL查询，自动将查询字段转换为对象属性
     */
    @Override
    protected void changeQuerySql(JSONObject queryParamJSON, Map<String, Object> paramsMap, StringBuffer sql, boolean isHql) {
        SaafToolUtils.parperParam(queryParamJSON, "baseResource.menu_id", "menuId", sql, paramsMap, "=", isHql);
        SaafToolUtils.parperParam(queryParamJSON, "baseResource.resource_code", "resourceCode", sql, paramsMap, "like", isHql);
        SaafToolUtils.parperParam(queryParamJSON, "baseResource.resource_type", "resourceType", sql, paramsMap, "=", isHql);
        SaafToolUtils.parperParam(queryParamJSON, "baseResource.resource_name", "resourceName", sql, paramsMap, "like", isHql);
        SaafToolUtils.parperParam(queryParamJSON, "baseResource.resource_desc", "resourceDesc", sql, paramsMap, "like", isHql);
        SaafToolUtils.parperParam(queryParamJSON, "baseMenu.menu_name", "menuName", sql, paramsMap, "like", isHql);
        SaafToolUtils.parperParam(queryParamJSON, "baseMenu.system_code", "systemCode", sql, paramsMap, "=", isHql);
    }

    /**
     * 根据菜单查询资源
     *
     * @param menuId 菜单Id
     * @return 资源列表<br>
     * [{<br>
     * resourceId:资源标识<br>
     * menuId:菜单Id，节点标识 对应到功能<br>
     * resourceCode:资源编号（与功能按钮编码对应）<br>
     * buttonUrl:按钮对应的执行方法地址<br>
     * orderNo:排序号<br>
     * resourceType:类型标识(按钮、方法、字段名、代码片段)<br>
     * resourceName:资源名称<br>
     * resourceDesc:资源描述<br>
     * creationDate:创建日期<br>
     * createdBy:创建人<br>
     * lastUpdatedBy:更新人<br>
     * lastUpdateDate:更新日期<br>
     * versionNum:版本号<br>
     * }]
     * @author ZhangJun
     * @creteTime 2017/12/14
     */
    @Override
    public List<BaseResourceEntity_HI> findBaseResourceByMenuId(Integer menuId) {
        JSONObject queryParamJSON = new JSONObject();
        queryParamJSON.put("menuId", menuId);
        Pagination<BaseResourceEntity_HI> findPagination = this.findPagination(queryParamJSON, 1, 100);
        return findPagination.getData();
    }

    /**
     * 根据角色Id查询角色所分配的资源
     *
     * @param roleId    角色Id
     * @param pageIndex 页码
     * @param pageRows  每页查询记录数
     * @return 分页资源列表<br>
     * { <br>
     * count: 总记录数,<br>
     * curIndex: 当前页索引,<br>
     * data: [{<br>
     * resourceId:资源标识<br>
     * menuId:菜单Id，节点标识 对应到功能<br>
     * resourceCode:资源编号（与功能按钮编码对应）<br>
     * buttonUrl:按钮对应的执行方法地址<br>
     * orderNo:排序号<br>
     * resourceType:类型标识(按钮、方法、字段名、代码片段)<br>
     * resourceName:资源名称<br>
     * resourceDesc:资源描述<br>
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
     * @author ZhangJun
     * @creteTime 2017/12/14
     */
    @Override
    public Pagination<BaseRoleResource_HI_RO> findBaseResourceByRoleId(Integer roleId, Integer pageIndex, Integer pageRows) {
        StringBuffer sb = new StringBuffer();
        sb.append(BaseRoleResource_HI_RO.QUERY_RESOURCE_SQL);

        JSONObject queryParamJSON = new JSONObject();
        queryParamJSON.put("roleId", roleId);

        Map<String, Object> queryParamMap = new HashMap<String, Object>();
        changeQuerySql(queryParamJSON, queryParamMap, sb, false);
        SaafToolUtils.parperParam(queryParamJSON, "baseRoleResource.role_id", "roleId", sb, queryParamMap, "=", false);

        Pagination<BaseRoleResource_HI_RO> findList = baseRoleResourceDAO_HI_RO.findPagination(sb, SaafToolUtils.getSqlCountString(sb), queryParamMap, pageIndex, pageRows);

        return findList;
    }

    @Override
    public Pagination<BaseMenuResource_HI_RO> findResourcePagination(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {

        StringBuffer sb = new StringBuffer(BaseMenuResource_HI_RO.QUERY_RESOURCE_MENU_SQL);
        Map<String, Object> paramsMap = new HashMap<>();
        changeQuerySql(queryParamJSON, paramsMap, sb, false);
        changeQuerySort(queryParamJSON, sb, "baseResource.last_update_date", false);
        Pagination<BaseMenuResource_HI_RO> findList = baseMenuResourceDAO_HI_RO.findPagination(sb,SaafToolUtils.getSqlCountString(sb), paramsMap, pageIndex, pageRows);

        return findList;
    }

    /**
     * 根据菜单Id，职责Id查询资源
     *
     * @param menuId 菜单Id
     * @param respId 职责Id
     * @return {@link BaseRoleResource_HI_RO}
     * @author ZhangJun
     * @createTime 2018/2/2
     * @description 根据菜单Id，职责Id查询资源
     */
    @Override
    public List<BaseRoleResource_HI_RO> findBaseResourceByRespMenuId(Integer menuId, Integer respId) {

        StringBuffer sb = new StringBuffer();
        sb.append(BaseRoleResource_HI_RO.QUERY_RESOURCE_SQL);
        JSONObject queryParamJSON = new JSONObject();
        if(!Integer.valueOf(Integer.MIN_VALUE).equals(respId)) {
            List<BaseResponsibilityRoleEntity_HI> respRoles = baseResponsibilityRoleDAO_HI.findList("from BaseResponsibilityRoleEntity_HI where responsibilityId=?", respId);
            List<Integer> roleIds = new ArrayList<>();
            for (BaseResponsibilityRoleEntity_HI respRole : respRoles) {
                roleIds.add(respRole.getRoleId());
            }
            queryParamJSON.put("roleId", StringUtils.join(roleIds, ","));
        }

        queryParamJSON.put("menuId", menuId);
        Map<String, Object> queryParamMap = new HashMap<String, Object>();
        SaafToolUtils.parperParam(queryParamJSON, "baseRoleResource.role_id", "roleId", sb, queryParamMap, "in", false);
        SaafToolUtils.parperParam(queryParamJSON, "baseResource.menu_id", "menuId", sb, queryParamMap, "=", false);
        sb.append(" GROUP BY baseResource.resource_id ");
        List<BaseRoleResource_HI_RO> findList = baseRoleResourceDAO_HI_RO.findList(sb, queryParamMap);

        return findList;
    }
}
