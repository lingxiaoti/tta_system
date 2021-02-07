package com.sie.watsons.base.sync.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.util.HttpClientUtil;
import com.sie.saaf.common.util.SaafDateUtils;
import com.sie.watsons.base.api.ConfigModel;
import com.sie.watsons.base.productEco.model.dao.PlmProductHeadEcoDAO_HI;
import com.sie.watsons.base.sync.model.entities.PlmSyncItemFromRmsEntity_HI;
import com.sie.watsons.base.sync.model.entities.PlmSyncItemSuppFromRmsEntity_HI;
import com.sie.watsons.base.sync.model.entities.PlmSyncItemUdaFromRmsEntity_HI;
import com.sie.watsons.base.sync.model.inter.IPlmSyncItemFromRms;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.dao.ViewObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.CallableStatement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component("plmSyncItemFromRmsServer")
public class PlmSyncItemFromRmsServer extends BaseCommonServer<PlmSyncItemFromRmsEntity_HI> implements IPlmSyncItemFromRms{
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmSyncItemFromRmsServer.class);
	@Autowired
	private ViewObject<PlmSyncItemFromRmsEntity_HI> plmSyncItemFromRmsDAO_HI;

	@Autowired
	private ViewObject<PlmSyncItemSuppFromRmsEntity_HI> plmSyncItemSuppFromRmsDAO_HI;

	@Autowired
	private ViewObject<PlmSyncItemUdaFromRmsEntity_HI> plmSyncItemUdaFromRmsDAO_HI;

	@Autowired
	private ConfigModel configModel;

	@Autowired
	private PlmProductHeadEcoDAO_HI headDAO_HI;

	public PlmSyncItemFromRmsServer() {
		super();
	}

	public List<PlmSyncItemFromRmsEntity_HI> findPlmSyncItemFromRmsInfo(JSONObject queryParamJSON) {
		Map<String, Object> queryParamMap = SToolUtils.fastJsonObj2Map(queryParamJSON);
		List<PlmSyncItemFromRmsEntity_HI> findListResult = plmSyncItemFromRmsDAO_HI.findList("from PlmSyncItemFromRmsEntity_HI", queryParamMap);
		return findListResult;
	}
	public Object savePlmSyncItemFromRmsInfo(JSONObject queryParamJSON) {
		PlmSyncItemFromRmsEntity_HI plmSyncItemFromRmsEntity_HI = JSON.parseObject(queryParamJSON.toString(), PlmSyncItemFromRmsEntity_HI.class);
		Object resultData = plmSyncItemFromRmsDAO_HI.save(plmSyncItemFromRmsEntity_HI);
		return resultData;
	}

    @Override
    public void updateRMD(Date date) throws Exception {
        //同步item, UDA, 供应商数据从RMS，此处应该不用改什么
        //但如果有bug需要调试一下bug，具体要做的就是编写代码在callProc()方法里
        syncItem(date);
        syncUDA(date);
        syncSupp(date);
        //此处是作为参照，如何调用存储过程，请从郭占山那里拿到存储过程后
        //在类里面再加一个方法调用占山写的存储过程
        //callProc();
    }

    @Override
    public void callProc() throws Exception {
        headDAO_HI.callProc();
        //给contentBank进行推送MQ
        //this.rabbitSendMessage();
    }

    @Override
    public void rabbitSendMessage() {
      //  rabbitTemplate.sendAndReceive("","", new org.springframework.amqp.core.Message());
    }

    private void close(CallableStatement cs) {
        try {
            if (cs != null) {
                cs.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void syncSupp(Date date) throws IOException {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        String currentDate = null;
        //date为空，取昨天
        if (date == null) {
            Date nextDay = SaafDateUtils.getNextDay(new Date(), -1);
            currentDate = format.format(nextDay);
        } else {
            currentDate = format.format(date);
        }
        String supp = configModel.getUpdateSupp()
                + currentDate;
        String jsonStr = HttpClientUtil.send(supp);
        System.out.println(jsonStr);
        JSONObject json = JSON.parseObject(jsonStr);

        JSONArray arr = json.getJSONArray("data_set");
        for (int i = 0; i < arr.size(); i++) {
            JSONObject obj = arr.getJSONObject(i);
            PlmSyncItemSuppFromRmsEntity_HI en = new PlmSyncItemSuppFromRmsEntity_HI();
            en.setItem(obj.getString("item"));
            en.setSupplier(obj.getString("supplier"));
            en.setPrimarySuppInd(obj.getString("primary_supp_ind"));
            en.setWhConsignInd(obj.getString("wh_consign_ind"));
            en.setOriginCountryId(obj.getString("origin_country_id"));
            en.setUnitCost(obj.getString("unit_cost"));
            en.setSuppPackSize(obj.getString("supp_pack_size"));
            en.setInnerPackSize(obj.getString("inner_pack_size"));
            en.setRoundToInnerPct(obj.getString("round_to_inner_pct"));
            en.setRoundToCasePct(obj.getString("round_to_case_pct"));
            en.setRoundToLayerPct(obj.getString("round_to_layer_pct"));
            en.setRoundToPalletPct(obj.getString("round_to_pallet_pct"));
            en.setHi(obj.getString("hi"));
            en.setTi(obj.getString("ti"));
            en.setLastUpdateId(obj.getString("last_update_id"));
            en.setLastUpdateDatetime(obj.getSqlDate("last_update_datetime"));
            en.setProcessStatus("pending");
            plmSyncItemSuppFromRmsDAO_HI.saveOrUpdate(en);
        }
    }

    private void syncUDA(Date date) throws IOException {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        String currentDate = null;
        //date为空，取昨天
        if (date == null) {
            Date nextDay = SaafDateUtils.getNextDay(new Date(), -1);
            currentDate = format.format(nextDay);
        } else {
            currentDate = format.format(date);
        }
        String UDA = configModel.getUpdateUDA()
                + currentDate;
        String jsonStr = HttpClientUtil.send(UDA);
        System.out.println(jsonStr);
        JSONObject json = JSON.parseObject(jsonStr);

        JSONArray arr = json.getJSONArray("data_set");
        for (int i = 0; i < arr.size(); i++) {
            JSONObject obj = arr.getJSONObject(i);
            PlmSyncItemUdaFromRmsEntity_HI en = new PlmSyncItemUdaFromRmsEntity_HI();
            en.setItem(obj.getString("item"));
            en.setUdaType(obj.getString("uda_type"));
            en.setUdaId(obj.getString("uda_id"));
            en.setUdaValue(obj.getString("uda_value"));
            en.setLastUpdateId(obj.getString("last_update_id"));
            en.setLastUpdateDatetime(obj.getDate("last_update_datetime"));
            en.setProcessStatus("pending");
            plmSyncItemUdaFromRmsDAO_HI.saveOrUpdate(en);
        }
    }

    private void syncItem(Date date) throws IOException {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        String currentDate = null;
        //date为空，取昨天
        if (date == null) {
            Date nextDay = SaafDateUtils.getNextDay(new Date(), -1);
            currentDate = format.format(nextDay);
        } else {
            currentDate = format.format(date);
        }

        String item = configModel.getUpdateItem()
                + currentDate;

        // String getUserIdUrl =
        // "http://10.82.24.180/rms/master/item?update_date="+"20191030";
        String jsonStr = HttpClientUtil.send(item);
        System.out.println(jsonStr);
        JSONObject json = JSON.parseObject(jsonStr);

        JSONArray arr = json.getJSONArray("data_set");
        for (int i = 0; i < arr.size(); i++) {
            JSONObject obj = arr.getJSONObject(i);
            PlmSyncItemFromRmsEntity_HI entity = new PlmSyncItemFromRmsEntity_HI();
            entity.setItem(obj.getString("item"));
            entity.setItemDesc(obj.getString("item_desc"));
            entity.setGroupNo(obj.getString("group_no"));
            entity.setDept(obj.getString("dept"));
            entity.setClasses(obj.getString("class"));
            entity.setSubclass(obj.getString("subclass"));
            entity.setWastePct(obj.getString("waste_pct"));
            entity.setStatus(obj.getString("status"));
            entity.setLastUpdateDatetime(obj.getDate("last_update_datetime"));
            entity.setLastUpdateId(obj.getString("last_update_id"));
            entity.setProcessStatus("pending");
            plmSyncItemFromRmsDAO_HI.saveOrUpdate(entity);
        }
    }

    public static void main(String[] args) {
    }
}
