package com.sie.saaf.base.user.model.inter.server;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.user.model.entities.readonly.BaseUserPDAProOrgChannel_HI_RO;
import com.sie.saaf.base.user.model.inter.IBaseUserPDAProOrgChannel;
import com.yhg.hibernate.core.dao.BaseViewObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author xiangyipo
 * @date 2018/2/8
 */


@Component
public class BaseUserPDAProOrgChannelServer implements IBaseUserPDAProOrgChannel {
    @Autowired
    BaseViewObject<BaseUserPDAProOrgChannel_HI_RO> baseUserPDAProOrgChannelDAO_HI_RO;

    public BaseUserPDAProOrgChannelServer() { super(); }



    /**
     * 用于产品信息的同步
     * @param {
     *       isUpdate :是否更新
     *       userId  : 用户Id
     *       deviceId : 设备id
     *       ifId : 接口ID
     * }
     * @retur data {
     *              itemCount:品规数量
     *              items[{//品规信息
     *                  codeHead:单品码头
     *                  itemCode:品规编码
     *                  itemInCode:品规内部码
     *                  organizationId:组织编码
     *                  itemType:产品类型
     *                  itemName:产品名称
     *                  itemDesc:产品简述
     *                  boxSpec:装箱规格
     *                  tracSpec:托盘规格
     *
     *           }]
     *        }
     * @author xiangyipo
     * @date 2018/3/5
     */
    @Override
    public BaseUserPDAProOrgChannel_HI_RO findBaseUserPDAChannelProductSyn(JSONObject params) {
            Date lastDate = params.getDate("lastDate");
            int userId = params.getInteger("userId");
            String isUpdate = params.getString("isUpdate");
            int rowCount = 0,rowNum = -1,rowNm = -1;
            JSONObject queryParamJSON = new JSONObject();
            queryParamJSON.put("userId", userId);
            queryParamJSON.put("lastDate",lastDate);
            /*判断是否需要更新*/
            if ("Y".equals(isUpdate)) {
                JSONObject queryJSON = new JSONObject();
                queryJSON.put("userId", userId);
                queryJSON.put("lastDate",lastDate);
                /*queryJSON.put("businessTableName","base_product_info");//产品
                queryJSON.put("enabled","Y");//有效的
                queryJSON.put("accessType",6);*/
                List<BaseUserPDAProOrgChannel_HI_RO> list = baseUserPDAProOrgChannelDAO_HI_RO.findList(BaseUserPDAProOrgChannel_HI_RO.QUERY_CHANNEL_IS_UPDATE_SQL, queryJSON);
                if(list != null && list.size() != 0){
                    rowCount = Integer.parseInt(list.get(0).getOrgId());
                }
            }

            BaseUserPDAProOrgChannel_HI_RO baseUserPDAProOrgEntities = new BaseUserPDAProOrgChannel_HI_RO();
            List<BaseUserPDAProOrgChannel_HI_RO> items = new ArrayList<>();
            Integer count = 0;

            // 用于电商子库，只要用户配了子库10120，则返回指定的品规 优先电商子库
            queryParamJSON.remove("lastDate");
            List<BaseUserPDAProOrgChannel_HI_RO> list = baseUserPDAProOrgChannelDAO_HI_RO.findList(BaseUserPDAProOrgChannel_HI_RO.QUERY_USER_IF_10120_WAREHOUSE_SQL, queryParamJSON);
            rowNum = list.get(0).getCount();
            if(rowNum > 0){

                List<BaseUserPDAProOrgChannel_HI_RO> baseUserPDAProOrgChannelList = baseUserPDAProOrgChannelDAO_HI_RO.findList(BaseUserPDAProOrgChannel_HI_RO.QUERY_USER_10120_WAREHOUSE_SQL, queryParamJSON);
                if(baseUserPDAProOrgChannelList != null && !baseUserPDAProOrgChannelList.isEmpty()) {
                    items.addAll(baseUserPDAProOrgChannelList);
                    count += baseUserPDAProOrgChannelList.size();
                }
            }

            //判断是否为库存组织为262的成品仓,针对能力多,1897组织,美纳多事业部,KA组织,美优高组织,电商组织 由公司总部统一发货
            List<BaseUserPDAProOrgChannel_HI_RO> baseUserPDAProOrgChannelEntities = baseUserPDAProOrgChannelDAO_HI_RO.findList(BaseUserPDAProOrgChannel_HI_RO.QUERY_USER_IF_1897_WAREHOUSE_SQL, queryParamJSON);
            rowNm = baseUserPDAProOrgChannelEntities.get(0).getCount();
            if(rowNm > 0 && rowNum == 0){

                List<BaseUserPDAProOrgChannel_HI_RO> channelEntities = baseUserPDAProOrgChannelDAO_HI_RO.findList(BaseUserPDAProOrgChannel_HI_RO.QUERY_USER_1897_WAREHOUSE_SQL, queryParamJSON);
                if(channelEntities != null && !channelEntities.isEmpty()) {
                    items.addAll(channelEntities);
                    count += channelEntities.size();
                }
            }
            //用于海普诺凯,纽莱可等其他事业部 PDA产品同步
            if(rowNm == 0 && rowNum == 0 && (rowCount == 0 || isUpdate.equals("Y"))){
                StringBuffer querySQL = new StringBuffer(BaseUserPDAProOrgChannel_HI_RO.QUERY_USER_ELSE_WAREHOUSE_SQL);
                if (StringUtils.isNotBlank(params.getString("cssAppFlag")) && StringUtils.equals(params.getString("cssAppFlag"), "Y")) {
                    querySQL.append(" AND F.ORG_ID = :orgId");
                    queryParamJSON.put("orgId", params.getInteger("orgId"));
                }
                List<BaseUserPDAProOrgChannel_HI_RO> baseUserPDAchannelEntities = baseUserPDAProOrgChannelDAO_HI_RO.findList(querySQL, queryParamJSON);
                if(baseUserPDAchannelEntities != null && !baseUserPDAchannelEntities.isEmpty()) {
                    items.addAll(baseUserPDAchannelEntities);
                    count += baseUserPDAchannelEntities.size();
                }
            }
            baseUserPDAProOrgEntities.setItems(items);
            baseUserPDAProOrgEntities.setCount(count);

            return baseUserPDAProOrgEntities;
    }
}
