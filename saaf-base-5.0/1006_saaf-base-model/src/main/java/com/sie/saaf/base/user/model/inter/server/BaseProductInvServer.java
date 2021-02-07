package com.sie.saaf.base.user.model.inter.server;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.user.model.entities.BaseProductInvEntity_HI;
import com.sie.saaf.base.user.model.entities.readonly.BaseProductInv_HI_RO;
import com.sie.saaf.base.user.model.inter.IBaseProductInv;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("baseProductInvServer")
public class BaseProductInvServer extends BaseCommonServer<BaseProductInvEntity_HI> implements IBaseProductInv {
//	private static final Logger LOGGER = LoggerFactory.getLogger(BaseProductInvServer.class);
	@Autowired
	private ViewObject<BaseProductInvEntity_HI> baseProductInvDAO_HI;
	@Autowired
	private BaseViewObject<BaseProductInv_HI_RO> baseProductInvDAO_HI_RO;

	public BaseProductInvServer() {
		super();
	}

	/**
	 * 关联产品及子库查询分页
	 * @author ZhangJun
	 * @createTime 2018/3/9
	 * @description 关联产品及子库查询分页
	 */
	@Override
	public Pagination<BaseProductInv_HI_RO> findROPagination(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows){
		String itemName_like = queryParamJSON.getString("itemName_like");
		if(StringUtils.isNotEmpty(itemName_like)){
			queryParamJSON.put("itemName_like",itemName_like.replaceAll(" ","%"));
		}
		StringBuffer sb = new StringBuffer();
		sb.append(BaseProductInv_HI_RO.QUERY_SQL);
		Map<String,Object> paramsMap = new HashMap<>();
		SaafToolUtils.parperHbmParam(BaseProductInv_HI_RO.class,queryParamJSON,sb,paramsMap);
		changeQuerySort(queryParamJSON,sb,"case when T.warehouseName LIKE '%成品仓%' then 0  else 1 end ,T.warehouseCode",false);
		Pagination<BaseProductInv_HI_RO> findList = baseProductInvDAO_HI_RO.findPagination(sb,SaafToolUtils.getSqlCountString(sb),paramsMap,pageIndex,pageRows);
		return findList;
	}

	/**
	 * 关联产品及子库查询分页
	 * @author ZhangJun
	 * @createTime 2018/3/9
	 * @description 关联产品及子库查询分页
	 */
	@Override
	public List<BaseProductInv_HI_RO> findROList(JSONObject queryParamJSON){
		StringBuffer sb = new StringBuffer();
		sb.append(BaseProductInv_HI_RO.QUERY_SQL);
		Map<String,Object> paramsMap = new HashMap<>();
		SaafToolUtils.parperHbmParam(BaseProductInv_HI_RO.class,queryParamJSON,sb,paramsMap);
		List<BaseProductInv_HI_RO> findList = baseProductInvDAO_HI_RO.findList(sb,paramsMap);
		return findList;
	}

	/**
	 * 保存或更新数据
	 * @param queryParamJSON {
	 *     itemCode:产品编码,
	 *     warehouseCodes:['xx','xx']子库编码集合,
	 *     productInvId:主键,
	 *     versionNum:版本号
	 * }
	 * @return
	 * @author ZhangJun
	 * @createTime 2018/3/9
	 */
	@Override
	public BaseProductInvEntity_HI saveAll(JSONObject queryParamJSON) {
		SaafToolUtils.validateJsonParms(queryParamJSON,"itemCode","warehouseCode");
		String itemCode = queryParamJSON.getString("itemCode");
		String warehouseCode = queryParamJSON.getString("warehouseCode");

		Integer operatorId = queryParamJSON.getInteger("operatorUserId");
		Integer productInvId = queryParamJSON.getInteger("productInvId");

		StringBuffer sql = new StringBuffer(" from BaseProductInvEntity_HI where 1=1 and itemCode = :itemCode and warehouseCode = :warehouseCode");

        Map<String,Object> paramsMap = new HashMap<>();

		paramsMap.put("itemCode",itemCode);
		paramsMap.put("warehouseCode",warehouseCode);

		List<BaseProductInvEntity_HI> entitys = baseProductInvDAO_HI.findList(sql,paramsMap);

        BaseProductInvEntity_HI entity = null;

        if(entitys.size() == 0) {

            entity = new BaseProductInvEntity_HI();
            entity.setItemCode(itemCode);
            entity.setWarehouseCode(warehouseCode);
            entity.setOperatorUserId(operatorId);

            baseProductInvDAO_HI.saveOrUpdate(entity);
        }else if(productInvId != null && productInvId.compareTo(entitys.get(0).getProductInvId()) == 0){
            entity = super.getById(productInvId);
            entity.setItemCode(itemCode);
            entity.setWarehouseCode(warehouseCode);
//            entity.setProductInvId(productInvId);
            entity.setOperatorUserId(operatorId);
            baseProductInvDAO_HI.saveOrUpdate(entity);
        }else {
            throw new RuntimeException("数据已存在");
        }

        return entity;
	}
}
