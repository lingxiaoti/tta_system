package com.sie.watsons.base.pon.itproject.model.inter.server;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.pon.itproject.model.entities.EquPonItBusinessContactEntity_HI;
import com.sie.watsons.base.pon.itproject.model.entities.readonly.EquPonItBusinessContactEntity_HI_RO;
import com.sie.watsons.base.pon.itproject.model.inter.IEquPonItBusinessContact;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("equPonItBusinessContactServer")
public class EquPonItBusinessContactServer extends BaseCommonServer<EquPonItBusinessContactEntity_HI> implements IEquPonItBusinessContact{
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPonItBusinessContactServer.class);

	@Autowired
	private ViewObject<EquPonItBusinessContactEntity_HI> equPonItBusinessContactDAO_HI;
    @Autowired
    private BaseViewObject<EquPonItBusinessContactEntity_HI_RO> EquPonItBusinessContactDAO_HI_RO;

	public EquPonItBusinessContactServer() {
		super();
	}

    @Override
    public List<EquPonItBusinessContactEntity_HI_RO> findItBusinessContact(JSONObject jsonObject) {
        // 校验非空
	    SaafToolUtils.validateJsonParms(jsonObject,"projectId");
        StringBuffer sb = new StringBuffer(EquPonItBusinessContactEntity_HI_RO.QUERY_SQL);
        sb.append(" and t.projectId = "+jsonObject.getInteger("projectId"));
        Map<String, Object> hashMap = new HashMap<>(4);
        List<EquPonItBusinessContactEntity_HI_RO> list = EquPonItBusinessContactDAO_HI_RO.findList(sb, hashMap);
        return list;
    }

    /**
     * IT报价管理-业务项目联系人查询，分页查询
     *
     * @param queryParamJSON 参数：密级Entity中的字段
     * @param pageIndex
     * @param pageRows
     * @return
     */
    public JSONObject findItBusinessContactInfo(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
        StringBuffer sql = new StringBuffer(EquPonItBusinessContactEntity_HI_RO.QUERY_LIST_SQL);
        Map<String, Object> map = new HashMap<>();
        SaafToolUtils.parperHbmParam(EquPonItBusinessContactEntity_HI_RO.class, queryParamJSON, sql, map);
        Pagination<EquPonItBusinessContactEntity_HI_RO> pagination = EquPonItBusinessContactDAO_HI_RO.findPagination(sql, map, pageIndex, pageRows);
        return JSONObject.parseObject(JSONObject.toJSONString(pagination));
    }

    /**
     * IT报价管理-业务项目联系人删除
     * @param params 参数：密级Entity中的字段
     * @return
     */
    @Override
    public void deleteItBusinessContact(JSONObject params) throws Exception {
        this.deleteById(params.getInteger("id"));
    }
}
