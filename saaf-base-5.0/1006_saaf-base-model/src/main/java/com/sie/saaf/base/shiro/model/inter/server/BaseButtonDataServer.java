package com.sie.saaf.base.shiro.model.inter.server;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.shiro.model.entities.BaseButtonDataEntity_HI;
import com.sie.saaf.base.shiro.model.inter.IBaseButtonData;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("baseButtonDataServer")
public class BaseButtonDataServer extends BaseCommonServer<BaseButtonDataEntity_HI> implements IBaseButtonData {
//    private static final Logger LOGGER = LoggerFactory.getLogger(BaseButtonDataServer.class);
    @Autowired
    private ViewObject<BaseButtonDataEntity_HI> baseButtonDataDAO_HI;

    public BaseButtonDataServer() {
        super();
    }

    /**
     * 校验按钮编码
     *
     * @author ZhangJun
     * @createTime 2018/3/16
     * @description 校验按钮编码
     * 校验规则：新增时编码不能相同，修改时，除自己以外不能存在相同编码
     */
    @Override
    public JSONObject validNameOrCode(JSONObject jsonParam) {
        String bbdCode = jsonParam.getString("bbdCode");
        JSONObject result = new JSONObject();
        //校验编码是否已经存在，不校验名称
        Map<String, Object> params = new HashMap<>();
        StringBuffer sql = new StringBuffer(" from BaseButtonDataEntity_HI where 1=1 ");
        params.put("bbdCode", bbdCode);
        sql.append(" and ( bbdCode=:bbdCode or bbdName=:bbdName ) ");
        String bbdName=jsonParam.getString("bbdName");
        params.put("bbdName", bbdName);
        if (jsonParam.containsKey("bbdId") && StringUtils.isNotBlank("bbdId")) {
            //如果是新增，则校验除自己以外是否存在相同编码
            Integer bbdId = jsonParam.getInteger("bbdId");
            sql.append(" and bbdId != :bbdId");
            params.put("bbdId",bbdId);
        }
        List<BaseButtonDataEntity_HI> list = baseButtonDataDAO_HI.findList(sql, params);
        if (list != null && !list.isEmpty()) {
            result.put("validFlag", false);
            result.put("msg", "已存在相同的按钮名称或编码，请确认后再操作！");
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
     * data: [{
     * bbdId:主键ID,（更新数据时必填）<br>
     * bbdName:功能按钮名称,<br>
     * bbdCode:功能按钮编码,<br>
     * creationDate:创建日期<br>
     * createdBy:创建人<br>
     * lastUpdatedBy:更新人<br>
     * lastUpdateDate:更新日期<br>
     * versionNum:版本号,（更新数据时必填）<br>
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
    public Pagination<BaseButtonDataEntity_HI> findPagination(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
        Map<String, Object> queryParamMap = new HashMap<>();
        StringBuffer sb = new StringBuffer(" from BaseButtonDataEntity_HI where 1=1 ");
        SaafToolUtils.parperHbmParam(BaseButtonDataEntity_HI.class, queryParamJSON, "bbdName", sb, queryParamMap, "like");
        SaafToolUtils.parperHbmParam(BaseButtonDataEntity_HI.class, queryParamJSON, "bbdCode", sb, queryParamMap, "like");
        Pagination<BaseButtonDataEntity_HI> findListResult = baseButtonDataDAO_HI.findPagination(sb, queryParamMap, pageIndex, pageRows);

        return findListResult;
    }

    /**
     * 保存一条数据
     *
     * @param queryParamJSON JSON参数 <br>
     *                       {<br>
     *                       bbdId:主键ID,（更新数据时必填）<br>
     *                       bbdName:功能按钮名称,<br>
     *                       bbdCode:功能按钮编码,<br>
     *                       resIcon:图标,<br>
     *                       versionNum:版本号,（更新数据时必填）<br>
     *                       }
     * @return BaseButtonDataEntity_HI对象
     * @author ZhangJun
     * @creteTime 2017/12/14
     */
    @Override
    public BaseButtonDataEntity_HI saveOrUpdate(JSONObject queryParamJSON) {
        return super.saveOrUpdate(queryParamJSON);
    }

}
