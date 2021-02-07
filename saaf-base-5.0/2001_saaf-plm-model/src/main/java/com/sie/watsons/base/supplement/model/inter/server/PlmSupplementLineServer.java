package com.sie.watsons.base.supplement.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.bean.ResultFileEntity;
import com.sie.saaf.common.model.inter.IFastdfs;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.model.inter.server.GenerateCodeServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.product.model.entities.PlmProductHeadEntity_HI;
import com.sie.watsons.base.product.model.inter.IPlmProductHead;
import com.sie.watsons.base.product.model.inter.server.X509TrustUtiil;
import com.sie.watsons.base.redisUtil.ResultConstant;
import com.sie.watsons.base.redisUtil.ResultUtils;
import com.sie.watsons.base.supplement.model.entities.PlmSupplementHeadEntity_HI;
import com.sie.watsons.base.supplement.model.entities.PlmSupplementLineEntity_HI;
import com.sie.watsons.base.supplement.model.entities.PlmSupplementLineFileEntity_HI;
import com.sie.watsons.base.supplement.model.entities.readonly.*;
import com.sie.watsons.base.supplement.model.inter.IPlmSupplementHead;
import com.sie.watsons.base.supplement.model.inter.IPlmSupplementLine;
import com.sie.watsons.base.supplement.model.inter.IPlmSupplementLineFile;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.net.ssl.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Component("plmSupplementLineServer")
public class PlmSupplementLineServer extends BaseCommonServer<PlmSupplementLineEntity_HI> implements IPlmSupplementLine{
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmSupplementLineServer.class);
	@Autowired
	private ViewObject<PlmSupplementLineEntity_HI> plmSupplementLineDAO_HI;
	@Autowired
	private BaseViewObject<PlmSupplementLineEntity_HI_RO> plmSupplementLineDAO_HI_RO;
    @Autowired
    private BaseViewObject<PlmSupplementLineRegionEntity_HI_RO> plmSupplementLineRegionEntity_HI_RO;

    @Autowired
    private BaseViewObject<PlmSupplementLinePogEntity_HI_RO> plmSupplementLinePogEntity_HI_RO;

    @Autowired
    private BaseViewObject<PlmSupplementLineWareEntity_HI_RO> plmSupplementLineWareEntity_HI_RO;
    @Autowired
    private BaseViewObject<PlmSupShopEntity_HI_RO> plmSupShopDAO_HI_RO;

	@Autowired
	private BaseViewObject<PlmSupWarehouseEntity_HI_RO> plmSupWarehouseDAO_HI_RO;
//	public PlmSupplementLineServer() {
//		super();
//	}
	@Autowired
	private IPlmSupplementLine plmSupplementLineDao;
    @Autowired
    private IPlmSupplementHead plmSupplementHeadDao;
	@Autowired
	private IPlmSupplementLineFile plmSupplementLineFileDao;
	@Autowired
	private IPlmProductHead plmProductHeadServer;

    @Autowired
    private ViewObject<PlmSupplementHeadEntity_HI> plmSupplementHeadDAO_HI;

    @Autowired
    private IFastdfs fastdfsServer;

    @Autowired
    private GenerateCodeServer generateCodeServer;

    @Autowired
    private redis.clients.jedis.JedisCluster jedis;

    public static Set<String> WC_SET;
    public static Set<String> AREA_SET;
    static {
        WC_SET = new HashSet<>();
        WC_SET.add("9901");
        WC_SET.add("9902");
        WC_SET.add("9903");
        WC_SET.add("9906");
        WC_SET.add("9907");
        WC_SET.add("9908");
        WC_SET.add("991");
        WC_SET.add("992");
        WC_SET.add("994");
        WC_SET.add("995");
        WC_SET.add("996");
        WC_SET.add("997");
        WC_SET.add("998");
        WC_SET.add("999");

        AREA_SET = new HashSet<>();
        AREA_SET.add("E");
        AREA_SET.add("N");
        AREA_SET.add("S");
        AREA_SET.add("W");
    }

    public PlmSupplementLineServer() {
        super();
//        LOGGER.info("导出类初始化");
//        for (int i=0;i<8;i++){
//            Runnable runnable=new Runnable() {
//                @Override
//                public void run() {
//                    while (true){
//                        try {
//                            LOGGER.info("{}开始处理，当前任务数:{}",Thread.currentThread().getName(),queue.size());
//                            JSONObject top = queue.take();
//                            saveImportSupLine2(top);
//                        }catch (Exception e){
//                            LOGGER.error(e.getMessage(),e);
//                        }
//                    }
//                }
//            };
//            concurrentExportPool.submit(runnable);
//        }
    }

    /**
     * 实现redis keys 模糊查询
     * @author hq
     * @param pattern
     * @return
     */
    private Set<String> redisKeys(String pattern){
        Set<String> keys = new HashSet<>();
        //获取所有连接池节点
        Map<String, JedisPool> nodes = jedis.getClusterNodes();
        //遍历所有连接池，逐个进行模糊查询
        for(String k : nodes.keySet()){
            JedisPool pool = nodes.get(k);
            //获取Jedis对象，Jedis对象支持keys模糊查询
            Jedis connection = pool.getResource();
            try {
                keys.addAll(connection.keys(pattern));
            } catch(Exception e){
            } finally{
                //一定要关闭连接！
                connection.close();
            }
        }
        return keys;
    }
    /**
     * 根据类型找到当前货品对应的停补店铺
     *
     * @param
     * @return
     */
    public List<PlmSupShopEntity_HI_RO> getShopsByType(PlmSupplementLineEntity_HI entity) {
        Map<String, Object> queryParamMap = new HashMap<String, Object>();
        String sqlStr = "";
        String meter = "";
        if (entity.getMeter() == null) {
            return null;
        }
        String[] meterList = entity.getMeter().split("\\|");
        for(int i = 0;i<meterList.length;i++){
            meter = meter+"'"+meterList[i] +"',";
        }
        if(meterList.length>0){
            meter= meter.substring(0,meter.length()-1);
        }
        StringBuffer sql = null;
        if("0".equals(entity.getStore())){
            sqlStr = PlmSupShopEntity_HI_RO.WARE_SQL;
        }
        if("1".equals(entity.getStore())){
            sqlStr = PlmSupShopEntity_HI_RO.REGION_SQL;
        }
        if("2".equals(entity.getStore())){
            sqlStr = PlmSupShopEntity_HI_RO.POG_SQL;
        }
        if("3".equals(entity.getStore())){
            sqlStr = PlmSupShopEntity_HI_RO.STORE_SQL;
        }
        sqlStr = sqlStr.replace(":rmsId",entity.getRmsCode());
        sqlStr = sqlStr.replace(":area",meter);
        sql = new StringBuffer(sqlStr);
        SaafToolUtils.parperHbmParam(PlmSupShopEntity_HI_RO.class, new JSONObject(), sql, queryParamMap);
        List<PlmSupShopEntity_HI_RO> findListResult = plmSupShopDAO_HI_RO.findList(sql, queryParamMap);
        return findListResult;
    }

    //查重
    private Map<String, String> checkShop(PlmSupplementLineEntity_HI line,List<PlmSupShopEntity_HI_RO> shops) throws ParseException {
        //获取审批单据中该货品对应所有的信息
        Set<String> shopKeys = redisKeys("*"+line.getRmsCode());
//        String code = jedis.get("201912040056100413802");
//        String code2 = jedis.get("201912050041100413802");

//        supplementLineOpen();

        Map<String, String> infoMap = new HashMap<>();

        JSONObject param = new JSONObject();

//        param.put("id",line.getHeadId());
//        param.put("state","2");
//        saveForWorkflow(param);

        for(PlmSupShopEntity_HI_RO shop:shops){
            for(String shopKey:shopKeys){
                //获取redis审核中店铺
//                Map<String, String> shopMap = (Map<String, String>) JSON.parse(shopKey);
//                String checkShop = shopMap.get(shop.getShopCode());
                String checkShopList = jedis.get(shopKey);
                Map<String, String> shopMap = (Map<String, String>) JSON.parse(checkShopList);
                String checkShop = shopMap.get(shop.getShopCode());
                String shopStr = "";
                String status = "";
                String exeDate = "";//生效时间
                String expireDate = "";//失效时间
                //如果有值则认为有重复
                if(null!=checkShop){
                    //shopStr = checkShop.split("\\|")[0].split(":")[0];
                    status = checkShop.substring(0,1);
                    exeDate = checkShop.split("\\|")[1].split(" ")[0];
                    if(checkShop.split("\\|")[2]!=null) {
                        expireDate = checkShop.split("\\|")[2].split(" ")[0];
                    }
                    //  if(shopStr.indexOf(shop.getShopCode())>=0){
                    //都是开通状态时
                    if(status.equals(line.getSupplementStatus())&&"0".equals(status)){
                        SimpleDateFormat sdf =  new SimpleDateFormat("yyyy-MM-dd" );
                        String date1 = sdf.format(line.getExeDate());
                        String date2 = sdf.format(line.getExpireDate());
                        if(!exeDate.equals(date1)||!expireDate.equals(date2)){
                            infoMap.put("rmsCode",line.getRmsCode());
                            infoMap.put("area",infoMap.get("area")==null?"":infoMap.get("area")+","+shop.getArea());
                        }
                    }
                    if(("1".equals(status)&&"0".equals(line.getSupplementStatus())||("0".equals(status)&&"1".equals(line.getSupplementStatus())))){
                        SimpleDateFormat sdf =  new SimpleDateFormat("yyyy-MM-dd" );
                        Date date1 = sdf.parse(exeDate);
                        Date date2 = sdf.parse(expireDate);
                        if(line.getExpireDate().getTime()<date1.getTime()||line.getExeDate().getTime()>date2.getTime()){

                        }else{
                            infoMap.put("rmsCode",line.getRmsCode());
                            infoMap.put("area",infoMap.get("area")==null?"":infoMap.get("area")+","+shop.getArea());
                        }

                        //    }
                    }
                }
            }
        }

        return infoMap;
    }
	@Override
	public Pagination<PlmSupplementLineEntity_HI_RO> findPlmSupplementLineInfo(JSONObject queryParamJSON, Integer pageIndex,
																		   Integer pageRows) {
		StringBuffer sql = new StringBuffer(PlmSupplementLineEntity_HI_RO.SQL);
		Map<String, Object> queryParamMap = new HashMap<String, Object>();
		SaafToolUtils.parperHbmParam(PlmSupplementLineEntity_HI_RO.class, queryParamJSON, sql, queryParamMap);
		Pagination<PlmSupplementLineEntity_HI_RO> findListResult = plmSupplementLineDAO_HI_RO.findPagination(sql, queryParamMap, pageIndex, pageRows);
		return findListResult;
	}

    @Override
    public Pagination<PlmSupplementLineRegionEntity_HI_RO> findPlmSupplementLineRegion(JSONObject queryParamJSON, Integer pageIndex,
                                                                                       Integer pageRows) {
        String rmsId = queryParamJSON.getString("rmsId");
        String regionEn = queryParamJSON.getString("regionEn_like");
        String sqlStr = PlmSupplementLineRegionEntity_HI_RO.SQL;
        sqlStr = sqlStr.replace(":rmsId",rmsId);
        if(null==regionEn||"".equals(regionEn)){
            sqlStr = sqlStr.replace("and LOWER(VS.area_en) like LOWER('%:regionEn%')","");
        }else{
            if ("e".equals(regionEn.toLowerCase())) {
                regionEn = "East Area";
            } else
            if ("s".equals(regionEn.toLowerCase())) {
                regionEn = "South Area";
            } else
            if ("w".equals(regionEn.toLowerCase())) {
                regionEn = "West Area";
            } else
            if ("n".equals(regionEn.toLowerCase())) {
                regionEn = "North Area";
            } else {
                Pagination<PlmSupplementLineRegionEntity_HI_RO> findListResult = new Pagination<>();
                findListResult.setData(new ArrayList<>());
                return findListResult;
            }
            sqlStr = sqlStr.replace(":regionEn",regionEn);
        }

        StringBuffer sql = new StringBuffer(sqlStr);
        Map<String, Object> queryParamMap = new HashMap<String, Object>();
      //  SaafToolUtils.parperHbmParam(PlmSupplementLineRegionEntity_HI_RO.class, queryParamJSON, sql, queryParamMap);
        Pagination<PlmSupplementLineRegionEntity_HI_RO> findListResult = plmSupplementLineRegionEntity_HI_RO.findPagination(sql, queryParamMap, pageIndex, pageRows);
        return findListResult;
    }

    @Override
    public Pagination<PlmSupplementLinePogEntity_HI_RO> findPlmSupplementLinePog(JSONObject queryParamJSON, Integer pageIndex,
                                                                                       Integer pageRows) {
        String rmsId = queryParamJSON.getString("rmsId");
        String sqlStr = PlmSupplementLinePogEntity_HI_RO.SQL;
        sqlStr = sqlStr.replace(":rmsId",rmsId);

        StringBuffer sql = new StringBuffer(sqlStr);
        Map<String, Object> queryParamMap = new HashMap<String, Object>();
        //  SaafToolUtils.parperHbmParam(PlmSupplementLineRegionEntity_HI_RO.class, queryParamJSON, sql, queryParamMap);
        Pagination<PlmSupplementLinePogEntity_HI_RO> findListResult = plmSupplementLinePogEntity_HI_RO.findPagination(sql, queryParamMap, pageIndex, pageRows);
        return findListResult;
    }

    @Override
    public Pagination<PlmSupplementLineWareEntity_HI_RO> findPlmSupplementLineWare(JSONObject queryParamJSON, Integer pageIndex,
                                                                                   Integer pageRows) {
        String rmsId = queryParamJSON.getString("rmsId");
        String wareCode = queryParamJSON.getString("wareCode_like");
        String sqlStr = PlmSupplementLineWareEntity_HI_RO.SQL;
        if(rmsId==null){
            sqlStr = sqlStr.replace("and rms_id = ':rmsId'","");
        }else{
            sqlStr = sqlStr.replace(":rmsId",rmsId);
        }
        if(null==wareCode||"".equals(wareCode)){
            sqlStr = sqlStr.replace("and LOWER(vh_pre_code) like LOWER('%:wareCode%') ","");
        }else{
            sqlStr = sqlStr.replace(":wareCode",wareCode);
        }

        StringBuffer sql = new StringBuffer(sqlStr);
        Map<String, Object> queryParamMap = new HashMap<String, Object>();
        //SaafToolUtils.parperHbmParam(PlmSupplementLineWareEntity_HI_RO.class, queryParamJSON, sql, queryParamMap);
        Pagination<PlmSupplementLineWareEntity_HI_RO> findListResult = plmSupplementLineWareEntity_HI_RO.findPagination(sql, queryParamMap, pageIndex, pageRows);
        return findListResult;
    }

	@Override
	public void removePlmLine(JSONObject queryParamJSON) throws Exception {
//		PlmSupplementLineEntity_HI entity = JSON.parseObject(queryParamJSON.toString(), PlmSupplementLineEntity_HI.class);
        JSONObject param = new JSONObject();
        param.put("plmSupplementHeadId", queryParamJSON.getString("plmSupplementLineId"));
        if (verify(param)) {
            throw new Exception("不能执行导入操作！");
        }
		plmSupplementLineDao.deleteById(queryParamJSON.getString("plmSupplementLineId"));
	}

	@Override
	public Object findPlmSupplementLineDetail(JSONObject queryParamJSON) {
		JSONObject param = new JSONObject();
		String lineId = queryParamJSON.getString("plmSupplementLineId");
		param.put("plmSupplementLineId", lineId);
		PlmSupplementLineEntity_HI_RO head = getHead(queryParamJSON);
		param.remove("plmSupplementLineId");
		String itemName = head.getRmsCode();
		if (itemName != null && !"".equals(itemName)){
			param.put("locator", "W");
			param.put("itemCode", itemName);
			List<PlmSupWarehouseEntity_HI_RO> warList = getWarList(param);
			param.put("locator", "S");
			List<PlmSupWarehouseEntity_HI_RO> regList = getRegList(param);
			Map<String, List<PlmSupWarehouseEntity_HI_RO>> map = merge(regList);
			List<PlmSupWarehouseEntity_HI_RO> regions = processRegMap(map);
			param.put("lineId", head.getPlmSupplementLineId());
			param.remove("locator");
			param.remove("itemName");
//			List<PlmSupplementLineFileEntity_HI> fileList = getFileList(param);
			queryParamJSON.put("line", JSON.toJSON(head));
			queryParamJSON.put("warehouse", JSON.toJSON(warList));
			queryParamJSON.put("region", JSON.toJSON(regions));
//			queryParamJSON.put("file", JSON.toJSON(fileList));
		}

		return queryParamJSON;
	}

	public Object findMeter(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows){
		StringBuffer sql = new StringBuffer(PlmSupplementLineEntity_HI_RO.SQL_METER);
		Map<String, Object> queryParamMap = new HashMap<String, Object>();
		SaafToolUtils.parperHbmParam(PlmSupplementLineEntity_HI_RO.class, queryParamJSON, sql, queryParamMap);
		Pagination<PlmSupplementLineEntity_HI_RO> findListResult = plmSupplementLineDAO_HI_RO.findPagination(sql, queryParamMap, pageIndex, pageRows);
		List<PlmSupplementLineEntity_HI_RO> data = findListResult.getData();
        for (PlmSupplementLineEntity_HI_RO datum : data) {
            datum.setMeter(datum.getMetre().toString());
        }
        return findListResult;
	}

	public JSONObject  importSupLine(JSONObject queryParamJSON) throws Exception {
		JSONArray dataArray = queryParamJSON.getJSONArray("data");
		JSONObject infoObject = queryParamJSON.getJSONObject("info");
		SaafToolUtils.validateJsonParms(infoObject, "plmSupplementHeadId");

        JSONObject query = new JSONObject();
        String orderType = "";
        query.put("plmSupplementHeadId",infoObject.getString("plmSupplementHeadId"));
        List<PlmSupplementHeadEntity_HI> header = getHeader(query);
        if (header == null || header.size() == 0) {
            throw new IllegalArgumentException("申请单未找到！");
        }
        orderType = header.get(0).getOrderType();


//		Integer plmSupplementHeadId = infoObject.getInteger("plmSupplementHeadId");
		ResultUtils.getLookUpValue("PLM_SUP_STOP_REASON");
		ResultUtils.getLookUpValue("PLM_SUP_STORE_TYPE");
		ResultUtils.getLookUpValue("PLM_SUP_STATUS");
        ResultUtils.getLookUpValue("PLM_SUP_STATUS_WC");
        ResultUtils.getLookUpValue("PLM_SUP_EFFECTIVE_WAY");
		JSONObject reason = ResultConstant.PLM_SUP_STOP_REASON;
		JSONObject storeType = ResultConstant.PLM_SUP_STORE_TYPE;
		JSONObject status = ResultConstant.PLM_SUP_STATUS;
        JSONObject statusWc = ResultConstant.PLM_SUP_STATUS_WC;
        JSONObject efway = ResultConstant.PLM_SUP_EFFECTIVE_WAY;
		JSONArray returnArray = new JSONArray();
		JSONArray errArray = new JSONArray();
		PlmSupplementLineEntity_HI entity = null;
        Pagination<PlmProductHeadEntity_HI> pagination;
		PlmProductHeadEntity_HI product = null;
		for (int i = 0; i < dataArray.size(); i++) {
			String errMsg = "";
			JSONObject data = dataArray.getJSONObject(i);
			entity = new PlmSupplementLineEntity_HI();
            if (SaafToolUtils.isNullOrEmpty(data.getString("rmsCode"))) {
                throw new IllegalArgumentException("货号未输入！");
            }
			if (SaafToolUtils.isNullOrEmpty(data.getString("rmsCode"))) {
				throw new IllegalArgumentException("货号未输入！");
			}
			if (SaafToolUtils.isNullOrEmpty(data.getString("exeDate"))) {
				throw new IllegalArgumentException("生效时间未输入！");
			}
            if (SaafToolUtils.isNullOrEmpty(data.getString("store"))) {
                throw new IllegalArgumentException("生效方式未输入！");
            }

            String exeDate = data.getString("exeDate");
            String[] dates = exeDate.split("-");
//            if(dates.length!=3){
//                setErrMsg(errArray, errMsg, "生效时间格式错误！", data.get("ROW_NUM"));
//                continue;
//            }else{
//                if(Integer.parseInt(dates[0])<1900||Integer.parseInt(dates[0])>3000){
//                    setErrMsg(errArray, errMsg, "生效时间格式错误！", data.get("ROW_NUM"));
//                    continue;
//                }
//                if(Integer.parseInt(dates[1])<1||Integer.parseInt(dates[1])>12){
//                    setErrMsg(errArray, errMsg, "生效时间格式错误！", data.get("ROW_NUM"));
//                    continue;
//                }
//                if(Integer.parseInt(dates[2])<1||Integer.parseInt(dates[2])>31){
//                    setErrMsg(errArray, errMsg, "生效时间格式错误！", data.get("ROW_NUM"));
//                    continue;
//                }
//            }
//            if (SaafToolUtils.isNullOrEmpty(data.getString("meter"))) {
//                throw new IllegalArgumentException("生效地点未输入！");
//            }


//			if (!SaafToolUtils.isNullOrEmpty(data.getString("region")) || !SaafToolUtils.isNullOrEmpty(data.getString("warehouse"))) {
//				if (!SaafToolUtils.isNullOrEmpty(data.getString("meter")) || !SaafToolUtils.isNullOrEmpty(data.getString("pogCode"))){
//					setErrMsg(errArray, errMsg, "货号：" + data.getString("rmsCode").trim() + "仓库、区域不能与米数、pogCode同时存在！", data.get("ROW_NUM"));
//					continue;
//				}
//			}
//			if (!SaafToolUtils.isNullOrEmpty(data.getString("meter")) || !SaafToolUtils.isNullOrEmpty(data.getString("pogCode"))) {
//				if (!SaafToolUtils.isNullOrEmpty(data.getString("region")) || !SaafToolUtils.isNullOrEmpty(data.getString("warehouse"))){
//					setErrMsg(errArray, errMsg, "货号：" + data.getString("rmsCode").trim() + "仓库、区域不能与米数、pogCode同时存在！", data.get("ROW_NUM"));
//					continue;
//				}
//			}
			//停止补货未填停补原因
			if (!SaafToolUtils.isNullOrEmpty(data.getString("supplementStatus"))){
				if (data.getString("supplementStatus").trim().indexOf("停")>=0){
					if (SaafToolUtils.isNullOrEmpty(data.getString("stopReason"))){
						setErrMsg(errArray, errMsg, "货号：" + data.getString("rmsCode").trim() + "停止补货请填写停补原因！", data.get("ROW_NUM"), data);
						continue;
					}
                    if (!SaafToolUtils.isNullOrEmpty(data.getDate("expireDate"))){
                        setErrMsg(errArray, errMsg, "货号：" + data.getString("rmsCode").trim() + "停补时不能填写失效时间！", data.get("ROW_NUM"), data);
                        continue;
                    }
				}
			}
			//只有选择仓库时才能选择存货方式
			if (!"仓库".equals(data.getString("store"))) {
				if (!SaafToolUtils.isNullOrEmpty(data.getString("storeType"))) {
					setErrMsg(errArray, errMsg, "货号：" + data.getString("rmsCode").trim() + "不能填入存货方式！", data.get("ROW_NUM"), data);
					continue;
				}

			}
            if ("仓库".equals(data.getString("store"))) {
                if (!SaafToolUtils.isNullOrEmpty(data.getString("meter"))) {
                    String met = data.getString("meter");
                    String[] arr = met.split("\\|");
                    for (String wc : arr) {
                        if (!WC_SET.contains(wc.trim())) {
                            setErrMsg(errArray, errMsg, "仓库：" + data.getString("meter").trim() + "不合法！", data.get("ROW_NUM"), data);
                            continue;
                        }
                    }
                }
            }
            if ("区域".equals(data.getString("store"))) {
                if (!SaafToolUtils.isNullOrEmpty(data.getString("meter"))) {
                    String met = data.getString("meter");
                    String[] arr = met.split("\\|");
                    for (String area : arr) {
                        if (!AREA_SET.contains(area.trim())) {
                            setErrMsg(errArray, errMsg, "区域：" + data.getString("meter").trim() + "不合法！", data.get("ROW_NUM"), data);
                            continue;
                        }
                    }
                }
            }
            //store数据存储为生效方式，0仓库，1区域，2Pog，3店铺
            //存在问题
//			if (!SaafToolUtils.isNullOrEmpty(data.getString("store"))) {
//				if ("店铺".equals(data.getString("store"))) {
//					setErrMsg(errArray, errMsg, "货号：" + data.getString("rmsCode").trim() + "已经选择店铺，不能填写仓库、区域、规格和POG！", data.get("ROW_NUM"));
//					continue;
//				}
//			}
			if (SaafToolUtils.isNullOrEmpty(data.getString("supplementStatus")) && SaafToolUtils.isNullOrEmpty(data.getString("storeType"))) {
				setErrMsg(errArray, errMsg, "货号：" + data.getString("rmsCode").trim() + "补货状态和存货方式须填入其一！", data.get("ROW_NUM"), data);
				continue;
			}
			//米数和pog不一致
			if (!SaafToolUtils.isNullOrEmpty(data.getString("pogCode")) && !SaafToolUtils.isNullOrEmpty(data.getString("meter"))){
//				JSONObject o = new JSONObject();
//				o.put("metre", data.getString("meter"));
//				o.put("pogCode", data.getString("pogCode"));
//				Pagination<PlmSupplementLineEntity_HI_RO> l = (Pagination<PlmSupplementLineEntity_HI_RO>) findMeter(o, 1, 1);
//				if (l == null || l.getCount() == 0){
//					setErrMsg(errArray, errMsg, "货号：" + data.getString("rmsCode").trim() + "请输入正确的米数和pogCode！", data.get("ROW_NUM"));
//					continue;
//				}
			}
			//查看货品是否存在
			else {
				JSONObject param = new JSONObject();
				param.put("rmsCode", data.getString("rmsCode").trim());
                Integer deptId = queryParamJSON.getInteger("userDept");
//                param.put("userDept", deptId);
//				param.put("orderStatus", "6");
				pagination = findProductList2(param);
				if (pagination.getData().size() == 0){
					setErrMsg(errArray, errMsg, data.getString("rmsCode").trim() + "未找到", data.get("ROW_NUM"), data);
                    continue;
                }
                product = pagination.getData().get(0);
            }
//			data.put("plmDevelopmentInfoId", plmDevelopmentInfoId);
//			data.put("plmProjectId", plmProjectId);
			data.put("rmsCode", data.getString("rmsCode")
					.trim());
			entity.setRmsCode(data.getString("rmsCode")
					.trim());
			data.put("productName",
					data.getString("productName"));
			entity.setProductName(data.getString("productName"));

			data.put("meter", data.getString("metre").trim());
			entity.setMeter(data.getString("metre").trim());
//			data.put("pogCode", data.getString("pogCode").trim());
//			entity.setPogCode(data.getString("pogCode").trim());

            data.put("store", data.getString("store").trim());
            String storeKey = getKey(efway, data.getString("store").trim());
            entity.setStore(storeKey);

			data.put("supplementStatus", data.getString("supplementStatus").trim());
			String supStatusKey = getKey(status, data.getString("supplementStatus").trim());
            if(supStatusKey==null){
                supStatusKey = getKey(statusWc, data.getString("supplementStatus").trim());
            }
			entity.setSupplementStatus(supStatusKey);
			data.put("stopReason", data.getString("stopReason").trim());
			String stopReasonKey = getKey(reason, data.getString("stopReason").trim());

            if(stopReasonKey==null){
                if(data.getString("stopReason").trim().indexOf("Bottom")>=0){
                    stopReasonKey = "9";
                }
                if(data.getString("stopReason").trim().indexOf("其他")>=0&&data.getString("stopReason").trim().indexOf("附件")>=0&&data.getString("stopReason").trim().indexOf("备注")>=0){
                    stopReasonKey = "8";
                }
                if(data.getString("stopReason").trim().indexOf("SMS")>=0&&data.getString("stopReason").trim().indexOf("Stop")>=0&&data.getString("stopReason").trim().indexOf("order")>=0){
                    stopReasonKey = "10";
                }
            }
			entity.setStopReason(stopReasonKey);
			data.put("storeType", data.getString("storeType"));
			if (data.getString("storeType") != null){

                String storeTypeKey = getKey(storeType, data.getString("storeType").trim());
                entity.setStoreType(storeTypeKey);
            }

            //日期问题处理：
            //生效，失效日期格式书写是否正确
            //生效，失效日期是否合法存在
            //生效日期是否大于等于今天
            //生效日期是否小于失效日期

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String sDate = sdf.format(new Date());
            Date date = null;
            String exeDateStr = null;
            Calendar cal = Calendar.getInstance();
            try {
                exeDateStr = data.getString("exeDate");
                date = sdf.parse(sDate);
                cal.setTime(date);
                cal.add(Calendar.DATE, 1);
                date = cal.getTime();
            } catch (Exception e) {
                setErrMsg(errArray, errMsg, "生效日期格式错误！" , data.get("ROW_NUM"), data);
                continue;
            }

            if(!check(exeDateStr)){
                setErrMsg(errArray, errMsg, "生效日期格式错误！" , data.get("ROW_NUM"), data);
                continue;
            }
            int sta = 0;
            try{
                String datestr = data.getString("exeDate").toString();
                String[] list = datestr.split("-");
                if(list.length==3&&list[2].length()==1){
                    datestr = list[0]+"-"+list[1]+"-0"+list[2];
                    data.put("exeDate",datestr);
                }
                sta = date.compareTo(data.getDate("exeDate"));
            }catch (Exception e){
                setErrMsg(errArray, errMsg, "生效日期格式错误！" , data.get("ROW_NUM"), data);
                continue;
            }



            if(sta==1){
                setErrMsg(errArray, errMsg, "生效日期不能早于今日！" , data.get("ROW_NUM"), data);
                continue;
            }

			data.put("exeDate", data.getString("exeDate").trim());
            entity.setExeDate(data.getDate("exeDate"));

            try{
                if (data.getString("expireDate") != null && !"".equals(data.getString("expireDate"))){
                    data.put("expireDate", data.getString("expireDate"));
                    entity.setExpireDate(data.getDate("expireDate"));

                    if(!entity.getExpireDate().after(entity.getExeDate())&&!data.getString("exeDate").equals(data.getString("expireDate"))){
                        setErrMsg(errArray, errMsg, "货号：" + data.getString("rmsCode").trim() + "失效日期必须在生效日期之后！", data.get("ROW_NUM"), data);
                        continue;
                    }
                }
            }catch (Exception e) {
                setErrMsg(errArray, errMsg, "失效日期格式错误！" , data.get("ROW_NUM"), data);
                continue;
            }

           if(!"".equals(data.getString("expireDate"))){
               String expireDateStr = data.getString("expireDate");
               try {
                   date = sdf.parse(sDate);
               } catch (Exception e) {
                   setErrMsg(errArray, errMsg, "失效日期格式错误！" , data.get("ROW_NUM"), data);
                   continue;
               }

               if(!check(expireDateStr)){
                   setErrMsg(errArray, errMsg, "失效日期格式错误！" , data.get("ROW_NUM"), data);
                   continue;
               }
               try{
                   String datestr = data.getString("expireDate").toString();
                   String[] list = datestr.split("-");
                   if(list.length==3&&list[2].length()==1){
                       datestr = list[0]+"-"+list[1]+"-0"+list[2];
                       data.put("expireDate",datestr);
                   }
                   date.compareTo(data.getDate("expireDate"));
               }catch (Exception e){
                   setErrMsg(errArray, errMsg, "失效日期格式错误！" , data.get("ROW_NUM"), data);
                   continue;
               }
           }


            data.put("remarks", data.getString("remarks").trim());
            entity.setRemarks(data.getString("remarks").trim());


            String store = storeKey;
            String supplementStatus = supStatusKey;
            String stopReason = stopReasonKey;

            String stop =data.getString("stopReason");
            if(stop.indexOf("其他")>=0&&stop.indexOf("附件")>=0&&stop.indexOf("备注")>=0){
                stopReason = "8";
            }

            //仓库
            if("0".equals(store)){
                if("2".equals(supplementStatus)||"3".equals(supplementStatus)||"4".equals(supplementStatus)||"5".equals(supplementStatus)){
                    if("2".equals(supplementStatus)||"4".equals(supplementStatus)){
                        if(!("6".equals(stopReason)||"8".equals(stopReason))){
                            setErrMsg(errArray, errMsg, "货号：" + data.getString("rmsCode").trim() + "原因填写异常！", data.get("ROW_NUM"), data);
                            continue;
                        }
                    }
                    if("3".equals(supplementStatus)||"5".equals(supplementStatus)){
                        if(!("6".equals(stopReason)||"8".equals(stopReason)||"9".equals(stopReason)||"10".equals(stopReason))){
                            setErrMsg(errArray, errMsg, "货号：" + data.getString("rmsCode").trim() + "原因填写异常！", data.get("ROW_NUM"), data);
                            continue;
                        }
                    }
                }else{
                    setErrMsg(errArray, errMsg, "货号：" + data.getString("rmsCode").trim() + "补货状态填写异常！", data.get("ROW_NUM"), data);
                    continue;
                }
            }
            //非仓库
            if(!"0".equals(store)){
                if("1".equals(store)){
                    if(!("0".equals(supplementStatus)||"1".equals(supplementStatus))){
                        setErrMsg(errArray, errMsg, "货号：" + data.getString("rmsCode").trim() + "补货状态填写异常！", data.get("ROW_NUM"), data);
                        continue;
                    }else{
                        if("0".equals(supplementStatus)){
                            if(!("3".equals(stopReason)||"4".equals(stopReason)||"5".equals(stopReason)||"6".equals(stopReason)||"8".equals(stopReason))){
                                setErrMsg(errArray, errMsg, "货号：" + data.getString("rmsCode").trim() + "原因填写异常！", data.get("ROW_NUM"), data);
                                continue;
                            }
                        }
                        if("1".equals(supplementStatus)){
                            if(!("1".equals(stopReason)||"2".equals(stopReason)||"3".equals(stopReason)||"4".equals(stopReason)||"5".equals(stopReason)||"6".equals(stopReason)||"8".equals(stopReason))){
                                setErrMsg(errArray, errMsg, "货号：" + data.getString("rmsCode").trim() + "原因填写异常！", data.get("ROW_NUM"), data);
                                continue;
                            }
                        }
                    }
                }
                //店铺
                if("3".equals(store)){
                    if(!("0".equals(supplementStatus)||"1".equals(supplementStatus))){
                        setErrMsg(errArray, errMsg, "货号：" + data.getString("rmsCode").trim() + "补货状态填写异常！", data.get("ROW_NUM"), data);
                        continue;
                    }else{
                        //开通
                        if("0".equals(supplementStatus)){
                            if(!"8".equals(stopReason)){
                                setErrMsg(errArray, errMsg, "货号：" + data.getString("rmsCode").trim() + "原因填写异常！", data.get("ROW_NUM"), data);
                                continue;
                            }
                        }
                        //店铺停补
                        if("1".equals(supplementStatus)){
                            if(("7".equals(stopReason)&&!"1".equals(orderType))){
                                setErrMsg(errArray, errMsg, "货号：" + data.getString("rmsCode").trim() + "原因填写异常，非陈列图订单！", data.get("ROW_NUM"), data);
                                continue;
                            }
                            if(!("7".equals(stopReason)||"8".equals(stopReason))){
                                setErrMsg(errArray, errMsg, "货号：" + data.getString("rmsCode").trim() + "原因填写异常！", data.get("ROW_NUM"), data);
                                continue;
                            }
                        }
                    }
                }
                if("2".equals(store)){
                    if(!"1".equals(supplementStatus)){
                        setErrMsg(errArray, errMsg, "货号：" + data.getString("rmsCode").trim() + "补货状态填写异常！", data.get("ROW_NUM"), data);
                        continue;
                    }else{
                        if(!"7".equals(stopReason)){
                            setErrMsg(errArray, errMsg, "货号：" + data.getString("rmsCode").trim() + "原因填写异常！", data.get("ROW_NUM"), data);
                            continue;
                        }
                    }
                }
            }

            //店铺冲突校验
//            PlmSupplementLineEntity_HI line = new PlmSupplementLineEntity_HI();
//            line.setStore(storeKey);
//            line.setMeter(data.getString("meter"));
//            line.setRmsCode(data.getString("rmsCode"));
//            line.setExeDate(data.getDate("exeDate"));
//            line.setExpireDate(data.getDate("expireDate"));
//            line.setSupplementStatus(supStatusKey);
//            List<PlmSupShopEntity_HI_RO> shops = getShopsByType(line);

            JSONObject queryJSON = new JSONObject();
            queryJSON.put("rmsId",data.getString("rmsCode"));

            String areaList = "";
            //0仓库，1区域，2Pog，3店铺
            if("0".equals(storeKey)){
                Pagination<PlmSupplementLineWareEntity_HI_RO> ware = findPlmSupplementLineWare(queryJSON,1,10000);
                for(int j=0;j<ware.getData().size();j++){
                    areaList = areaList +","+ware.getData().get(j).getWareCode();
                }
            }
            if("1".equals(storeKey)){
                Pagination<PlmSupplementLineRegionEntity_HI_RO> region = findPlmSupplementLineRegion(queryJSON,1,100);
                for(int j=0;j<region.getData().size();j++){
                    areaList = areaList +","+region.getData().get(j).getRegionEn();
                }
            }
            if("2".equals(storeKey)){
                Pagination<PlmSupplementLinePogEntity_HI_RO> pog = findPlmSupplementLinePog(queryJSON,1,200);
                for(int j=0;j<pog.getData().size();j++){
                    areaList = areaList +","+pog.getData().get(j).getPogCode();
                }
            }
            if("3".equals(storeKey)){
                StringBuffer sql = null;
                String sqlStr = PlmSupShopEntity_HI_RO.STORE_SQL;
                sqlStr = sqlStr.replace(":rmsId",data.getString("rmsCode"));
                sqlStr = sqlStr.replace("and pps.location in ( :area )","");
                sql = new StringBuffer(sqlStr);
                SaafToolUtils.parperHbmParam(PlmSupShopEntity_HI_RO.class, new JSONObject(), sql, new HashMap<String, Object>());
                List<PlmSupShopEntity_HI_RO> findListResult = plmSupShopDAO_HI_RO.findList(sql, new HashMap<String, Object>());
                for(int j=0;j<findListResult.size();j++){
                    areaList = areaList +","+findListResult.get(j).getShopCode();
                }
            }
            JSONArray arr = new JSONArray();
            arr.add(data);
            JSONObject obj = new JSONObject();
            obj.put("lines", arr);
            JSONObject itemList = plmSupplementHeadDao.findPlmSupItem(obj);
            setItemList(itemList, entity);
            returnArray.add(data);
            if (entity.getStore().equals("1")) {
                if (!isAreaValid(entity)) {
                    setErrMsg(errArray, errMsg, "货号：" + data.getString("rmsCode").trim() + "区域填写异常，请填写生效范围的区域！", data.get("ROW_NUM"), data);
                    continue;
                }
            }
            if(data.getString("metre")!=null){
                String[] meterList = data.getString("metre").split("\\|");
                //检验相应地点是否存在
                for(int k = 0;k<meterList.length;k++){
                    if(areaList.indexOf(meterList[k])<0){
                        setErrMsg(errArray, errMsg, "货号：" + data.getString("rmsCode").trim() + "生效地点："+meterList[k]+"不存在", data.get("ROW_NUM"), data);
                        continue;
                    }
                }
            }



//            Pagination<PlmSupplementLineWareEntity_HI_RO> ware = findPlmSupplementLineWare(queryJSON,1,10000);
//            Pagination<PlmSupplementLineRegionEntity_HI_RO> region = findPlmSupplementLineRegion(queryJSON,1,100);
//            Pagination<PlmSupplementLinePogEntity_HI_RO> pog = findPlmSupplementLinePog(queryJSON,1,200);

//            StringBuffer sql = null;
//            String sqlStr = PlmSupShopEntity_HI_RO.STORE_SQL;
//            sqlStr = sqlStr.replace(":rmsId",data.getString("rmsCode"));
//            sql = new StringBuffer(sqlStr);
//            SaafToolUtils.parperHbmParam(PlmSupShopEntity_HI_RO.class, new JSONObject(), sql, new HashMap<String, Object>());
//            List<PlmSupShopEntity_HI_RO> findListResult = plmSupShopDAO_HI_RO.findList(sql, new HashMap<String, Object>());


//            Map<String, String> confliShop = checkShop(line,shops);
//            if (confliShop != null && confliShop.size() != 0) {
//                StringBuffer str = new StringBuffer();
//                for (Map.Entry<String, String> entry : confliShop.entrySet()){
//                    str.append("店铺" + entry.getKey() + "，冲突：" + entry.getValue());
//                    str.append("，");
//                }
//                setErrMsg(errArray, errMsg, "货号：" + data.getString("rmsCode").trim() + str, data.get("ROW_NUM"));
//                continue;
//            }


			if (product.getProductName() != null && !"".equals(product.getProductName())){
                entity.setProductName(product.getProductName());
            }

//			if(!errMsg.equals("")){
//				JSONObject errRow = new JSONObject();
//				errRow.put("ERR_MESSAGE", errMsg);
//				errRow.put("ROW_NUM",data.get("ROW_NUM"));
//				errArray.add(errRow);
//			}
            entity.setHeadId(infoObject.getInteger("plmSupplementHeadId"));
            entity.setExeStatus("0");
            entity.setExpireStatus("0");
            Date conDate = product.getConsultDate();
            if (conDate != null) {
                entity.setConsultDate(product.getConsultDate().toString());
            }
            Date endDate = product.getConsultEnddate();
            if (endDate != null) {
                entity.setConsultEnddate(product.getConsultEnddate().toString());
            }
            if (product.getSesionProduct() != null) {
                entity.setSesionProduct(product.getSesionProduct());
            }
            if (product.getConsultProductno() != null) {
                entity.setConsultProductno(product.getConsultProductno());
            }
            plmSupplementLineDAO_HI.save(entity);
        }
        if(errArray.size()!=0){
            throw new Exception(errArray.toJSONString());
        }

//		List<PlmDevelopmentIngredientsEntity_HI> deleteArray = plmDevelopmentIngredientsDAO_HI
//				.findByProperty("plmDevelopmentInfoId", plmDevelopmentInfoId);
//		plmDevelopmentIngredientsDAO_HI.deleteAll(deleteArray);

		queryParamJSON.put("lines", returnArray);
		return queryParamJSON;
	}

    private boolean isAreaValid(PlmSupplementLineEntity_HI entity) {
        Set<String> set = new HashSet<>();
        if (entity.getNorthItemList() != null) {
            set.add("N");
        }
        if (entity.getSouthItemList() != null) {
            set.add("S");
        }
        if (entity.getEastItemList() != null) {
            set.add("E");
        }
        if (entity.getWestItemList() != null) {
            set.add("W");
        }
        String arr[] = entity.getMeter().split("\\|");
        for (String str: arr) {
            if (!set.contains(str.toUpperCase())) {
                return false;
            }
        }
        return true;
    }

    public JSONObject importSupLine3(JSONObject queryParamJSON) throws IOException,Exception {
//        JSONObject infoObject = queryParamJSON.getJSONObject("info");
//        if (verify(infoObject)) {
//            throw new Exception("不能执行的操作！");
//        }
        queryParamJSON.put("uploadStatus", "N");
        queryParamJSON.put("key", generateCodeServer.generateCode("UPLD", new SimpleDateFormat(
                "yyyyMMdd"), 4));
        jedis.set(queryParamJSON.getString("key"), queryParamJSON.toJSONString());
        String filepath = queryParamJSON.getString("filepath");
        File file = this.getNetUrl(filepath);
        InputStream is = new FileInputStream(file);
        Workbook wb = new HSSFWorkbook(is);
        Sheet head = wb.getSheetAt(0); // 头信息
        JSONArray arr = new JSONArray();
        int rownum = head.getLastRowNum(); // 总行数
        for (int i = 1; i <= rownum; i++) {
            Row curent = head.getRow(i);

            HSSFCell rmsCode = (HSSFCell) curent.getCell(0); // 渠道
            if (rmsCode == null) {
                continue;
            }
            rmsCode.setCellType(CellType.STRING);
            HSSFCell stor = (HSSFCell) curent.getCell(1); // 渠道
//            if (stor != null) {
//                stor.setCellType(CellType.STRING);
//            }
            HSSFCell meter = (HSSFCell) curent.getCell(2); // 渠道
            meter.setCellType(CellType.STRING);
            HSSFCell suppleStatus = (HSSFCell) curent.getCell(3); // 渠道
            HSSFCell stoReason = (HSSFCell) curent.getCell(4); // 渠道
            HSSFCell storType = (HSSFCell) curent.getCell(5); // 渠道
            HSSFCell exDate = (HSSFCell) curent.getCell(6); // 渠道
            HSSFCell expDate = (HSSFCell) curent.getCell(7); // 渠道
            HSSFCell remark = (HSSFCell) curent.getCell(8); // 渠道
            if (remark != null) {
                remark.setCellType(CellType.STRING);
            }
            String ex = getStringCellValue(exDate);
            if ("".equals(ex)) {
                ex = "";
            } else {
                ex = ex.replaceAll("/", "-");
            }
            String exp = getStringCellValue(expDate);
            if ("".equals(exp)) {
                exp = "";
            } else {
                exp = exp.replaceAll("/", "-");
            }
            JSONObject data = new JSONObject();
            data.put("rmsCode", rmsCode == null? "":rmsCode.getStringCellValue());
            data.put("store", stor == null ? "": stor.getStringCellValue());
            data.put("meter", meter == null ? "": meter.getStringCellValue());
            data.put("supplementStatus", suppleStatus == null ? "": suppleStatus.getStringCellValue());
            data.put("stopReason", stoReason == null? "": stoReason.getStringCellValue());
            data.put("storeType", storType == null? "":storType.getStringCellValue());
            data.put("exeDate", ex);
            data.put("expireDate", exp);
            data.put("remarks", remark == null ? "": remark.getStringCellValue());
            arr.add(data);
        }
        queryParamJSON.put("lines", arr);
        return queryParamJSON;
    }

    private boolean verify(JSONObject queryParamJSON) {
        JSONObject query = new JSONObject();
        LOGGER.info("==================id : {}===================", queryParamJSON.getString("plmSupplementHeadId"));
        query.put("plmSupplementHeadId",queryParamJSON.getString("plmSupplementHeadId"));
        List<PlmSupplementHeadEntity_HI> list = getHeader(query);
        LOGGER.info("==================size : {}===================", list.size());
        if (list != null && list.size() > 0) {
            PlmSupplementHeadEntity_HI entity = list.get(0);
            if (entity == null) {
                LOGGER.info("==================entity null==================");
                return true;
            }
            LOGGER.info("==================entity.getOrderStatus(): {}==================", entity.getOrderStatus());
            if (!entity.getOrderStatus().equals("0")) {
                return true;
            } else {
                return false;
            }
        }
//        Integer id = infoObject.getString("plmSupplementHeadId");
        return false;
    }

    @Transactional
    public JSONArray saveImportSupLine2(JSONObject queryParamJSON) throws Exception {

//        File file = new File(filepath);
//        JSONObject reason = ResultConstant.PLM_SUP_STOP_REASON;
//        if (reason == null) {
//            ResultUtils.getLookUpValue("PLM_SUP_STOP_REASON");
//        }
//        JSONObject storeType = ResultConstant.PLM_SUP_STORE_TYPE;
//        if (storeType == null) {
//            ResultUtils.getLookUpValue("PLM_SUP_STORE_TYPE");
//        }
//        JSONObject status = ResultConstant.PLM_SUP_STATUS;
//        if (status == null) {
//            ResultUtils.getLookUpValue("PLM_SUP_STATUS");
//        }
//        JSONObject statusWc = ResultConstant.PLM_SUP_STATUS_WC;
//        if (statusWc == null) {
//            ResultUtils.getLookUpValue("PLM_SUP_STATUS_WC");
//        }
//        JSONObject efway = ResultConstant.PLM_SUP_EFFECTIVE_WAY;
//        if (efway == null) {
//            ResultUtils.getLookUpValue("PLM_SUP_EFFECTIVE_WAY");
//        }
        ResultUtils.getLookUpValue("PLM_SUP_STOP_REASON");
        JSONObject reason = ResultConstant.PLM_SUP_STOP_REASON;
        if (reason == null) {
        }
        ResultUtils.getLookUpValue("PLM_SUP_STORE_TYPE");
        JSONObject storeType = ResultConstant.PLM_SUP_STORE_TYPE;
        if (storeType == null) {
        }
        ResultUtils.getLookUpValue("PLM_SUP_STATUS");
        JSONObject status = ResultConstant.PLM_SUP_STATUS;
        if (status == null) {
        }
        ResultUtils.getLookUpValue("PLM_SUP_STATUS_WC");
        JSONObject statusWc = ResultConstant.PLM_SUP_STATUS_WC;
        if (statusWc == null) {
        }
        ResultUtils.getLookUpValue("PLM_SUP_EFFECTIVE_WAY");
        JSONObject efway = ResultConstant.PLM_SUP_EFFECTIVE_WAY;
        if (efway == null) {
        }
//        JSONArray dataArray = queryParamJSON.getJSONArray("data");
        JSONObject infoObject = queryParamJSON.getJSONObject("info");
        JSONArray array = queryParamJSON.getJSONArray("lines");
//        SaafToolUtils.validateJsonParms(infoObject, "plmSupplementHeadId");

        JSONObject query = new JSONObject();
        String orderType = "";
        query.put("plmSupplementHeadId",infoObject.getString("plmSupplementHeadId"));
        List<PlmSupplementHeadEntity_HI> header = getHeader(query);
        if (header == null || header.size() == 0) {
            throw new IllegalArgumentException("申请单未找到！");
        }
        orderType = header.get(0).getOrderType();

//        JSONArray returnArray = new JSONArray();
        JSONArray errArray = new JSONArray();
        PlmSupplementLineEntity_HI entity = null;
        Pagination<PlmProductHeadEntity_HI> pagination;
        PlmProductHeadEntity_HI product = null;
        try {
//            String filepath = queryParamJSON.getString("filepath");
//            File file = this.getNetUrl(filepath);
//            InputStream is = new FileInputStream(file);
//            Workbook wb = new HSSFWorkbook(is);
//            Sheet head = wb.getSheetAt(0); // 头信息
//
//            int rownum = head.getLastRowNum(); // 总行数
            for (int i = 0; i < array.size(); i++) {
//                Row curent = head.getRow(i);
//
//                HSSFCell rmsCode = (HSSFCell) curent.getCell(0); // 渠道
//                rmsCode.setCellType(CellType.STRING);
//                HSSFCell stor = (HSSFCell) curent.getCell(1); // 渠道
//                HSSFCell meter = (HSSFCell) curent.getCell(2); // 渠道
//                meter.setCellType(CellType.STRING);
//                HSSFCell suppleStatus = (HSSFCell) curent.getCell(3); // 渠道
//                HSSFCell stoReason = (HSSFCell) curent.getCell(4); // 渠道
//                HSSFCell storType = (HSSFCell) curent.getCell(5); // 渠道
//                HSSFCell exDate = (HSSFCell) curent.getCell(6); // 渠道
//                HSSFCell expDate = (HSSFCell) curent.getCell(7); // 渠道
//                HSSFCell remark = (HSSFCell) curent.getCell(8); // 渠道
//                String ex = getStringCellValue(exDate);
//                if ("".equals(ex)) {
//                    ex = "";
//                } else {
//                    ex = ex.replaceAll("/", "-");
//                }
//                String exp = getStringCellValue(expDate);
//                if ("".equals(exp)) {
//                    exp = "";
//                } else {
//                    exp = exp.replaceAll("/", "-");
//                }
//                JSONObject data = new JSONObject();
//                data.put("rmsCode", rmsCode);
//                data.put("store", stor);
//                data.put("meter", meter);
//                data.put("supplementStatus", suppleStatus);
//                data.put("stopReason", stoReason);
//                data.put("storeType", storType);
//                data.put("exeDate", ex);
//                data.put("expireDate", exp);
//                data.put("remarks", remark);
                JSONObject data = array.getJSONObject(i);
                String errMsg = "";
                entity = new PlmSupplementLineEntity_HI();
                if (SaafToolUtils.isNullOrEmpty(data.getString("rmsCode"))) {
                    throw new IllegalArgumentException("货号未输入！");
                }
                if (SaafToolUtils.isNullOrEmpty(data.getString("rmsCode"))) {
                    throw new IllegalArgumentException("货号未输入！");
                }
                if (SaafToolUtils.isNullOrEmpty(data.getString("exeDate"))) {
                    throw new IllegalArgumentException("生效时间未输入！");
                }
                if (SaafToolUtils.isNullOrEmpty(data.getString("store"))) {
                    throw new IllegalArgumentException("生效方式未输入！");
                }

                String exeDate = data.getString("exeDate");
                String[] dates = exeDate.split("-");

                //停止补货未填停补原因
                if (!SaafToolUtils.isNullOrEmpty(data.getString("supplementStatus"))){
                    if (data.getString("supplementStatus").trim().indexOf("停")>=0){
                        if (SaafToolUtils.isNullOrEmpty(data.getString("stopReason"))){
                            setErrMsg(errArray, errMsg, "货号：" + data.getString("rmsCode").trim() + "停止补货请填写停补原因！", data.get("ROW_NUM"), data);
                            continue;
                        }
                        if (!SaafToolUtils.isNullOrEmpty(data.getDate("expireDate"))){
                            setErrMsg(errArray, errMsg, "货号：" + data.getString("rmsCode").trim() + "停补时不能填写失效时间！", data.get("ROW_NUM"), data);
                            continue;
                        }
                    }
                }
                //只有选择仓库时才能选择存货方式
                if (!"仓库".equals(data.getString("store"))) {
                    if (!SaafToolUtils.isNullOrEmpty(data.getString("storeType"))) {
                        setErrMsg(errArray, errMsg, "货号：" + data.getString("rmsCode").trim() + "不能填入存货方式！", data.get("ROW_NUM"), data);
                        continue;
                    }

                }
                if ("仓库".equals(data.getString("store"))) {
                    if (!SaafToolUtils.isNullOrEmpty(data.getString("meter"))) {
                        String met = data.getString("meter");
                        String[] arr = met.split("\\|");
                        boolean isCon = false;
                        for (String wc : arr) {
                            if (!WC_SET.contains(wc.trim())) {
                                setErrMsg(errArray, errMsg, "仓库：" + data.getString("meter").trim() + "不合法！", data.get("ROW_NUM"), data);
                                isCon = true;
                                break;
                            }
                        }
                        if (isCon == true) {
                            continue;
                        }
                    }
                }
                if ("区域".equals(data.getString("store"))) {
                    if (!SaafToolUtils.isNullOrEmpty(data.getString("meter"))) {
                        String met = data.getString("meter");
                        String[] arr = met.split("\\|");
                        boolean isCon = false;
                        for (String area : arr) {
                            if (!AREA_SET.contains(area.trim().toUpperCase())) {
                                setErrMsg(errArray, errMsg, "区域：" + data.getString("meter").trim() + "不合法！", data.get("ROW_NUM"), data);
                                isCon = true;
                                break;
                            }
                        }
                        if (isCon == true) {
                            continue;
                        }
                    }
                }
                if (SaafToolUtils.isNullOrEmpty(data.getString("supplementStatus")) && SaafToolUtils.isNullOrEmpty(data.getString("storeType"))) {
                    setErrMsg(errArray, errMsg, "货号：" + data.getString("rmsCode").trim() + "补货状态和存货方式须填入其一！", data.get("ROW_NUM"), data);
                    continue;
                }
                //米数和pog不一致
                if (!SaafToolUtils.isNullOrEmpty(data.getString("pogCode")) && !SaafToolUtils.isNullOrEmpty(data.getString("meter"))){
                }
                //查看货品是否存在
                else {
                    JSONObject param = new JSONObject();
                    param.put("rmsCode", data.getString("rmsCode").trim());
                    Integer deptId = queryParamJSON.getInteger("userDept");
                    pagination = findProductList2(param);
                    if (pagination.getData().size() == 0){
                        setErrMsg(errArray, errMsg,  data.getString("rmsCode").trim() + "未找到", data.get("ROW_NUM"), data);
                        continue;
                    }
                    product = pagination.getData().get(0);
                }
                data.put("rmsCode", data.getString("rmsCode")
                        .trim());
                entity.setRmsCode(data.getString("rmsCode")
                        .trim());
                data.put("productName",
                        data.getString("productName"));
                entity.setProductName(data.getString("productName"));

                data.put("meter", data.getString("meter"));
                entity.setMeter(data.getString("meter"));
                data.put("store", data.getString("store").trim());
                String storeKey = getKey(efway, data.getString("store").trim());
                entity.setStore(storeKey);

                data.put("supplementStatus", data.getString("supplementStatus").trim());
                String supStatusKey = getKey(status, data.getString("supplementStatus").trim());
                if(supStatusKey==null){
                    supStatusKey = getKey(statusWc, data.getString("supplementStatus").trim());
                }
                entity.setSupplementStatus(supStatusKey);
                data.put("stopReason", data.getString("stopReason").trim());
                String stopReasonKey = getKey(reason, data.getString("stopReason").trim());

                if(stopReasonKey==null){
                    if(data.getString("stopReason").trim().indexOf("Bottom")>=0){
                        stopReasonKey = "9";
                    }
                    if(data.getString("stopReason").trim().indexOf("其他")>=0&&data.getString("stopReason").trim().indexOf("附件")>=0&&data.getString("stopReason").trim().indexOf("备注")>=0){
                        stopReasonKey = "8";
                    }
                    if(data.getString("stopReason").trim().indexOf("SMS")>=0&&data.getString("stopReason").trim().indexOf("Stop")>=0&&data.getString("stopReason").trim().indexOf("order")>=0){
                        stopReasonKey = "10";
                    }
                }
                entity.setStopReason(stopReasonKey);
                data.put("storeType", data.getString("storeType"));
                if (data.getString("storeType") != null){

                    String storeTypeKey = getKey(storeType, data.getString("storeType").trim());
                    entity.setStoreType(storeTypeKey);
                }

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String sDate = sdf.format(new Date());
                Date date = null;
                String exeDateStr = null;
                Calendar cal = Calendar.getInstance();
                try {
                    exeDateStr = data.getString("exeDate");
                    date = sdf.parse(sDate);
                    cal.setTime(date);
                    cal.add(Calendar.DATE, 1);
                    date = cal.getTime();
                } catch (Exception e) {
                    setErrMsg(errArray, errMsg, "生效日期格式错误！" , data.get("ROW_NUM"), data);
                    continue;
                }

                if(!check(exeDateStr)){
                    setErrMsg(errArray, errMsg, "生效日期格式错误！" , data.get("ROW_NUM"), data);
                    continue;
                }
                int sta = 0;
                try{
                    String datestr = data.getString("exeDate").toString();
                    String[] list = datestr.split("-");
                    if(list.length==3&&list[2].length()==1){
                        datestr = list[0]+"-"+list[1]+"-0"+list[2];
                        data.put("exeDate",datestr);
                    }
                    sta = date.compareTo(data.getDate("exeDate"));
                }catch (Exception e){
                    setErrMsg(errArray, errMsg, "生效日期格式错误！" , data.get("ROW_NUM"), data);
                    continue;
                }



                if(sta==1){
                    setErrMsg(errArray, errMsg, "生效日期不能早于今日！" , data.get("ROW_NUM"), data);
                    continue;
                }

                data.put("exeDate", data.getString("exeDate").trim());
                entity.setExeDate(data.getDate("exeDate"));

                try{
                    if (data.getString("expireDate") != null && !"".equals(data.getString("expireDate"))){
                        data.put("expireDate", data.getString("expireDate"));
                        entity.setExpireDate(data.getDate("expireDate"));

                        if(!entity.getExpireDate().after(entity.getExeDate())&&!data.getString("exeDate").equals(data.getString("expireDate"))){
                            setErrMsg(errArray, errMsg, "货号：" + data.getString("rmsCode").trim() + "失效日期必须在生效日期之后！", data.get("ROW_NUM"), data);
                            continue;
                        }
                    }
                }catch (Exception e) {
                    setErrMsg(errArray, errMsg, "失效日期格式错误！" , data.get("ROW_NUM"), data);
                    continue;
                }

                if(!"".equals(data.getString("expireDate"))){
                    String expireDateStr = data.getString("expireDate");
                    try {
                        date = sdf.parse(sDate);
                    } catch (Exception e) {
                        setErrMsg(errArray, errMsg, "失效日期格式错误！" , data.get("ROW_NUM"), data);
                        continue;
                    }

                    if(!check(expireDateStr)){
                        setErrMsg(errArray, errMsg, "失效日期格式错误！" , data.get("ROW_NUM"), data);
                        continue;
                    }
                    try{
                        String datestr = data.getString("expireDate").toString();
                        String[] list = datestr.split("-");
                        if(list.length==3&&list[2].length()==1){
                            datestr = list[0]+"-"+list[1]+"-0"+list[2];
                            data.put("expireDate",datestr);
                        }
                        date.compareTo(data.getDate("expireDate"));
                    }catch (Exception e){
                        setErrMsg(errArray, errMsg, "失效日期格式错误！" , data.get("ROW_NUM"), data);
                        continue;
                    }
                }

                data.put("remarks", data.getString("remarks"));
                entity.setRemarks(data.getString("remarks"));


                String store = storeKey;
                String supplementStatus = supStatusKey;
                String stopReason = stopReasonKey;

                String stop =data.getString("stopReason");
                if(stop.indexOf("其他")>=0&&stop.indexOf("附件")>=0&&stop.indexOf("备注")>=0){
                    stopReason = "8";
                }

                //仓库
                if("0".equals(store)){
                    if("2".equals(supplementStatus)||"3".equals(supplementStatus)||"4".equals(supplementStatus)||"5".equals(supplementStatus)){
                        if("2".equals(supplementStatus)||"4".equals(supplementStatus)){
                            if(!("6".equals(stopReason)||"8".equals(stopReason))){
                                setErrMsg(errArray, errMsg, "货号：" + data.getString("rmsCode").trim() + "原因填写异常！", data.get("ROW_NUM"), data);
                                continue;
                            }
                        }
                        if("3".equals(supplementStatus)||"5".equals(supplementStatus)){
                            if(!("6".equals(stopReason)||"8".equals(stopReason)||"9".equals(stopReason)||"10".equals(stopReason))){
                                setErrMsg(errArray, errMsg, "货号：" + data.getString("rmsCode").trim() + "原因填写异常！", data.get("ROW_NUM"), data);
                                continue;
                            }
                        }
                    }else{
                        setErrMsg(errArray, errMsg, "货号：" + data.getString("rmsCode").trim() + "补货状态填写异常！", data.get("ROW_NUM"), data);
                        continue;
                    }
                }
                //非仓库
                if(!"0".equals(store)){
                    if("1".equals(store)){
                        if(!("0".equals(supplementStatus)||"1".equals(supplementStatus))){
                            setErrMsg(errArray, errMsg, "货号：" + data.getString("rmsCode").trim() + "补货状态填写异常！", data.get("ROW_NUM"), data);
                            continue;
                        }else{
                            if("0".equals(supplementStatus)){
                                if(!("3".equals(stopReason)||"4".equals(stopReason)||"5".equals(stopReason)||"6".equals(stopReason)||"8".equals(stopReason))){
                                    setErrMsg(errArray, errMsg, "货号：" + data.getString("rmsCode").trim() + "原因填写异常！", data.get("ROW_NUM"), data);
                                    continue;
                                }
                            }
                            if("1".equals(supplementStatus)){
                                if(!("1".equals(stopReason)||"2".equals(stopReason)||"3".equals(stopReason)||"4".equals(stopReason)||"5".equals(stopReason)||"6".equals(stopReason)||"8".equals(stopReason))){
                                    setErrMsg(errArray, errMsg, "货号：" + data.getString("rmsCode").trim() + "原因填写异常！", data.get("ROW_NUM"), data);
                                    continue;
                                }
                            }
                        }
                    }
                    //店铺
                    if("3".equals(store)){
                        if(!("0".equals(supplementStatus)||"1".equals(supplementStatus))){
                            setErrMsg(errArray, errMsg, "货号：" + data.getString("rmsCode").trim() + "补货状态填写异常！", data.get("ROW_NUM"), data);
                            continue;
                        }else{
                            //开通
                            if("0".equals(supplementStatus)){
                                if(!"8".equals(stopReason)){
                                    setErrMsg(errArray, errMsg, "货号：" + data.getString("rmsCode").trim() + "原因填写异常！", data.get("ROW_NUM"), data);
                                    continue;
                                }
                            }
                            //店铺停补
                            if("1".equals(supplementStatus)){
                                if(("7".equals(stopReason)&&!"1".equals(orderType))){
                                    setErrMsg(errArray, errMsg, "货号：" + data.getString("rmsCode").trim() + "原因填写异常，非陈列图订单！", data.get("ROW_NUM"), data);
                                    continue;
                                }
                                if(!("7".equals(stopReason)||"8".equals(stopReason))){
                                    setErrMsg(errArray, errMsg, "货号：" + data.getString("rmsCode").trim() + "原因填写异常！", data.get("ROW_NUM"), data);
                                    continue;
                                }
                            }
                        }
                    }
                    if("2".equals(store)){
                        if(!"1".equals(supplementStatus)){
                            setErrMsg(errArray, errMsg, "货号：" + data.getString("rmsCode").trim() + "补货状态填写异常！", data.get("ROW_NUM"), data);
                            continue;
                        }else{
                            if(!"7".equals(stopReason)){
                                setErrMsg(errArray, errMsg, "货号：" + data.getString("rmsCode").trim() + "原因填写异常！", data.get("ROW_NUM"), data);
                                continue;
                            }
                        }
                    }
                }
                JSONObject queryJSON = new JSONObject();
                queryJSON.put("rmsId",data.getString("rmsCode"));

                String areaList = "";
                //0仓库，1区域，2Pog，3店铺
                if("0".equals(storeKey)){
                    Pagination<PlmSupplementLineWareEntity_HI_RO> ware = findPlmSupplementLineWare(queryJSON,1,10000);
                    for(int j=0;j<ware.getData().size();j++){
                        areaList = areaList +","+ware.getData().get(j).getWareCode();
                    }
                }
                if("1".equals(storeKey)){
                    Pagination<PlmSupplementLineRegionEntity_HI_RO> region = findPlmSupplementLineRegion(queryJSON,1,100);
                    for(int j=0;j<region.getData().size();j++){
                        areaList = areaList +","+region.getData().get(j).getRegionEn();
                    }
                }
                if("2".equals(storeKey)){
                    Pagination<PlmSupplementLinePogEntity_HI_RO> pog = findPlmSupplementLinePog(queryJSON,1,200);
                    for(int j=0;j<pog.getData().size();j++){
                        areaList = areaList +","+pog.getData().get(j).getPogCode();
                    }
                }
                if("3".equals(storeKey)){
                    StringBuffer sql = null;
                    String sqlStr = PlmSupShopEntity_HI_RO.STORE_SQL;
                    sqlStr = sqlStr.replace(":rmsId",data.getString("rmsCode"));
                    sqlStr = sqlStr.replace("and pps.location in ( :area )","");
                    sql = new StringBuffer(sqlStr);
                    SaafToolUtils.parperHbmParam(PlmSupShopEntity_HI_RO.class, new JSONObject(), sql, new HashMap<String, Object>());
                    List<PlmSupShopEntity_HI_RO> findListResult = plmSupShopDAO_HI_RO.findList(sql, new HashMap<String, Object>());
                    for(int j=0;j<findListResult.size();j++){
                        areaList = areaList +","+findListResult.get(j).getShopCode();
                    }
                }
                if(data.getString("metre")!=null){
                    String[] meterList = data.getString("metre").split("\\|");
                    boolean isCon = false;
                    //检验相应地点是否存在
                    for(int k = 0;k<meterList.length;k++){
                        if(areaList.indexOf(meterList[k])<0){
                            setErrMsg(errArray, errMsg, "货号：" + data.getString("rmsCode").trim() + "生效地点："+meterList[k]+"不存在", data.get("ROW_NUM"), data);
                            isCon = true;
                            break;
                        }
                    }
                    if (isCon == true) {
                        continue;
                    }
                }
                if (product.getProductName() != null && !"".equals(product.getProductName())){
                    entity.setProductName(product.getProductName());
                }
                JSONObject obj = new JSONObject();
                JSONArray arr = new JSONArray();
                arr.add(data);
                obj.put("lines", arr);
                JSONObject itemList = plmSupplementHeadDao.findPlmSupItem(obj);
                setItemList(itemList, entity);
                if (entity.getStore().equals("1")) {
                    if (!isAreaValid(entity)) {
                        setErrMsg(errArray, errMsg, "货号：" + data.getString("rmsCode").trim() + "区域填写异常，请填写生效范围的区域！", data.get("ROW_NUM"), data);
                        continue;
                    }
                }
                entity.setHeadId(infoObject.getInteger("plmSupplementHeadId"));
//                returnArray.add(data);
                entity.setExeStatus("0");
                entity.setExpireStatus("0");
                Date conDate = product.getConsultDate();
                if (conDate != null) {
                    entity.setConsultDate(product.getConsultDate().toString());
                }
                Date endDate = product.getConsultEnddate();
                if (endDate != null) {
                    entity.setConsultEnddate(product.getConsultEnddate().toString());
                }
                if (product.getSesionProduct() != null) {
                    entity.setSesionProduct(product.getSesionProduct());
                }
                if (product.getConsultProductno() != null) {
                    entity.setConsultProductno(product.getConsultProductno());
                }
                plmSupplementLineDAO_HI.save(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (errArray.size() != 0) {
            return errArray;
        }

//        if(errArray.size()!=0){
//            String filep = export(errArray);
////            String filep = "lalalalalalalalala.xls";
//            LOGGER.info("uploadfilepath ==================== " + filep);
//            queryParamJSON.put("uploadfilepath", filep);
//            queryParamJSON.put("uploadStatus", "E");
//            jedis.set(queryParamJSON.getString("key"), queryParamJSON.toJSONString());
////            throw new Exception(errArray.toJSONString());
//            return queryParamJSON;
//        }
//        queryParamJSON.put("uploadStatus", "S");
//        jedis.set(queryParamJSON.getString("key"), queryParamJSON.toJSONString());
////        queryParamJSON.put("lines", returnArray);
//        return queryParamJSON;
        return null;
    }

    @Override
    public void saveImportSupLineMulTh(JSONObject queryParamJSON) {
        JSONArray errArray = queryParamJSON.getJSONArray("err");
        if(errArray.size()!=0){
            String filep = export(errArray);
//            String filep = "lalalalalalalalala.xls";
            LOGGER.info("uploadfilepath ==================== " + filep);
            queryParamJSON.put("uploadfilepath", filep);
            queryParamJSON.put("uploadStatus", "E");
            jedis.set(queryParamJSON.getString("key"), queryParamJSON.toJSONString());
            return;
//            throw new Exception(errArray.toJSONString());
        }

        queryParamJSON.put("uploadStatus", "S");
        jedis.set(queryParamJSON.getString("key"), queryParamJSON.toJSONString());
//        queryParamJSON.put("lines", returnArray);
    }

    @Override
    public JSONObject getUploadPath(JSONObject queryParamJSON) {
        String key = jedis.get(queryParamJSON.getString("key"));
        JSONObject object = JSON.parseObject(key);
        if (!object.getString("uploadStatus").equals("N")) {
            jedis.del(queryParamJSON.getString("key"));
        }
        return object;
    }

    private String export(JSONArray errArray) {
        InputStream inputStream = plmSupErrorResultToExcel(errArray);
        try {
            ResultFileEntity fileEntity = fastdfsServer.fileUpload(inputStream, "export.xls");
            return fileEntity.getAccessPath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public InputStream plmSupErrorResultToExcel(JSONArray array) {
        String[] heads={"错误信息","货号","生效方式","生效地点","补货状态","开通/停补原因","存货方式","生效日期","失效日期","备注"};
//		String[] heads={"ERR_MESSAGE","rmsCode","productName","store","meter","supplementStatus","stopReason"};
        Workbook workbook = new HSSFWorkbook();// 创建一个Excel文件
        Sheet sheet = workbook.createSheet();// 创建一个Excel的Sheet
        Row titleRow = sheet.createRow(0);//创建表头
        for(int i=0;i<heads.length;i++){//表头赋值
            titleRow.createCell(i).setCellValue(heads[i]);
        }
        if (array.size() > 0) {//单元格赋值
            for (int i = 0; i < array.size(); i++) {
                Row row = sheet.createRow(i + 1);
                JSONObject json = array.getJSONObject(i); // 遍历 jsonarray
                for(int j=0;j<heads.length;j++){
                    if("错误信息".equals(heads[j])){
                        row.createCell(j).setCellValue(ObjectUtils.isEmpty(json.get("ERR_MESSAGE"))?"": json.get("ERR_MESSAGE").toString());//赋值
                    }else if("货号".equals(heads[j])){
                        row.createCell(j).setCellValue(ObjectUtils.isEmpty(json.get("rmsCode"))?"": json.get("rmsCode").toString());//赋值
                    }else if("生效方式".equals(heads[j])){
                        row.createCell(j).setCellValue(ObjectUtils.isEmpty(json.get("store"))?"": json.get("store").toString());//赋值
                    }else if("生效地点".equals(heads[j])){
                        row.createCell(j).setCellValue(ObjectUtils.isEmpty(json.get("meter"))?"": json.get("meter").toString());//赋值
                    }else if("补货状态".equals(heads[j])){
                        row.createCell(j).setCellValue(ObjectUtils.isEmpty(json.get("supplementStatus"))?"": json.get("supplementStatus").toString());//赋值
                    }else if("开通/停补原因".equals(heads[j])){
                        row.createCell(j).setCellValue(ObjectUtils.isEmpty(json.get("stopReason"))?"": json.get("stopReason").toString());//赋值
                    }else if("存货方式".equals(heads[j])){
                        row.createCell(j).setCellValue(ObjectUtils.isEmpty(json.get("storeType"))?"": json.get("storeType").toString());//赋值
                    }else if("生效日期".equals(heads[j])){
                        row.createCell(j).setCellValue(ObjectUtils.isEmpty(json.get("exeDate"))?"": json.get("exeDate").toString());//赋值
                    }else if("失效日期".equals(heads[j])){
                        row.createCell(j).setCellValue(ObjectUtils.isEmpty(json.get("expireDate"))?"": json.get("expireDate").toString());//赋值
                    }else if("备注".equals(heads[j])){
                        row.createCell(j).setCellValue(ObjectUtils.isEmpty(json.get("remarks"))?"": json.get("remarks").toString());//赋值
                    }
                }
            }
        }

        ByteArrayOutputStream bos = null ;
        try {
            bos= new ByteArrayOutputStream();
            workbook.write(bos);
            byte[] bytes = bos.toByteArray();
            bos.close();
            return new ByteArrayInputStream(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getStringCellValue(HSSFCell cell) {
        if (cell == null) {
            return "";
        }
        String strCell = "";
        switch (cell.getCellTypeEnum()) {
            case STRING:
                strCell = cell.getStringCellValue();
                break;
            case NUMERIC:
                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                    Date date = cell.getDateCellValue();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                    return sdf.format(date);
                }

                strCell = String.valueOf(cell.getNumericCellValue());
                if (strCell.contains(".")) {
                    strCell = strCell.substring(0, strCell.indexOf("."));
                }

                break;
            case BOOLEAN:
                strCell = String.valueOf(cell.getBooleanCellValue());
                break;
            case BLANK:
                strCell = "";
                break;
            default:
                strCell = "";
                break;
        }

        if (strCell.equals("") || strCell == null) {
            return "";
        }
        if (cell == null) {
            return "";
        }
        return strCell;
    }

    public static File getNetUrl(String netUrl) {
        // 判断http和https
        File file = null;
        if (netUrl.startsWith("https://")) {
            file = getNetUrlHttps(netUrl);
        } else {
            file = getNetUrlHttp(netUrl);
        }
        return file;
    }

    public static File getNetUrlHttps(String fileUrl) {
        // 对本地文件进行命名
        String file_name = fileUrl;
        File file = null;

        DataInputStream in = null;
        DataOutputStream out = null;
        try {
            file = File.createTempFile("net_url", file_name);

            SSLContext sslcontext = SSLContext.getInstance("SSL", "SunJSSE");
            sslcontext.init(null, new TrustManager[]{new X509TrustUtiil()},
                    new java.security.SecureRandom());
            URL url = new URL(fileUrl);

            HostnameVerifier ignoreHostnameVerifier = new HostnameVerifier() {
                @Override
                public boolean verify(String s, SSLSession sslsession) {
                    // logger.warn("WARNING: Hostname is not matched for cert.");
                    return true;
                }
            };
            HttpsURLConnection
                    .setDefaultHostnameVerifier(ignoreHostnameVerifier);
            HttpsURLConnection.setDefaultSSLSocketFactory(sslcontext
                    .getSocketFactory());
            HttpsURLConnection urlCon = (HttpsURLConnection) url
                    .openConnection();
            urlCon.setConnectTimeout(6000);
            urlCon.setReadTimeout(6000);
            int code = urlCon.getResponseCode();
            if (code != HttpURLConnection.HTTP_OK) {
                throw new Exception("文件读取失败");
            }
            // 读文件流
            in = new DataInputStream(urlCon.getInputStream());
            out = new DataOutputStream(new FileOutputStream(file));
            byte[] buffer = new byte[2048];
            int count = 0;
            while ((count = in.read(buffer)) > 0) {
                out.write(buffer, 0, count);
            }
            out.close();
            in.close();
        } catch (Exception e) {
            // log.error("远程图片获取错误：" + fileUrl);
            e.printStackTrace();
        } finally {
            try {
                if (null != out) {
                    out.close();
                }
                if (null != in) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }

        return file;
    }

    public static File getNetUrlHttp(String netUrl) {
        // 对本地文件命名
        String fileName = netUrl;
        File file = null;

        URL urlfile;
        InputStream inStream = null;
        OutputStream os = null;
        try {
            file = File.createTempFile("net_url", URLEncoder.encode(fileName));
//            file = new File(fileName);
            // 下载
            urlfile = new URL(netUrl);
            inStream = urlfile.openStream();
            os = new FileOutputStream(file);

            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = inStream.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
        } catch (Exception e) {
            // log.error("远程图片获取错误：" + netUrl);
            e.printStackTrace();
        } finally {
            try {
                if (null != os) {
                    os.close();
                }
                if (null != inStream) {
                    inStream.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return file;
    }

    //反向通过value取到快码表
    private String getKey(JSONObject codes, String supplementStatus) {
	    if (codes != null){
	        for (int i = 0; i < 8; i ++){
	            String num = String.valueOf(i);
                String value = codes.getString(num);
	            if (value != null){
	                if (value.trim().equals(supplementStatus)){
                        return num;
                    }
                }
            }
        }
	    return null;
    }

     boolean check (String str) {
        SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");//括号内为日期格式，y代表年份，M代表年份中的月份（为避免与小时中的分钟数m冲突，此处用M），d代表月份中的天数
        try {
            sd.setLenient(false);//此处指定日期/时间解析是否不严格，在true是不严格，false时为严格
            sd.parse(str);//从给定字符串的开始解析文本，以生成一个日期
        }
        catch (Exception e) {
            return false;
        }
        return true;
    }

    private void setErrMsg(JSONArray errArray, String errMsg, String rmsCode, Object row_num, JSONObject data) {
		errMsg += rmsCode;
		JSONObject errRow = new JSONObject();
		errRow.put("ERR_MESSAGE", errMsg);
		errRow.put("ROW_NUM", row_num);
        errRow.put("rmsCode", data.getString("rmsCode"));
        errRow.put("store", data.getString("store"));
        errRow.put("meter", data.getString("meter"));
        errRow.put("supplementStatus", data.getString("supplementStatus"));
        errRow.put("stopReason", data.getString("stopReason"));
        errRow.put("storeType", data.getString("storeType"));
        errRow.put("exeDate", data.getString("exeDate"));
        errRow.put("expireDate", data.getString("expireDate"));
        errRow.put("remarks", data.getString("remarks"));
		errArray.add(errRow);
	}

	private void setItemList(JSONObject itemList, PlmSupplementLineEntity_HI entity) {
	    JSONArray arr = itemList.getJSONArray("lines");
	    if (arr != null && arr.size() > 0){
	        JSONObject obj = arr.getJSONObject(0);
	        String south = obj.getString("southItemList");
	        if (south != null && !"".equals(south)){
	            entity.setSouthItemList(south);
            }
            String north = obj.getString("northItemList");
            if (north != null && !"".equals(south)){
                entity.setNorthItemList(north);
            }
            String west = obj.getString("westItemList");
            if (west != null && !"".equals(south)){
            	entity.setWestItemList(west);
            }
            String east = obj.getString("eastItemList");
            if (east != null && !"".equals(east)){
            	entity.setEastItemList(east);
            }
        }
    }

    private PlmSupplementLineEntity_HI_RO getHead(JSONObject queryParamJSON) {
		StringBuffer sql = new StringBuffer(PlmSupplementLineEntity_HI_RO.SQL);
		Map<String, Object> queryParamMap = new HashMap<String, Object>();
		SaafToolUtils.parperHbmParam(PlmSupplementLineEntity_HI_RO.class, queryParamJSON, sql, queryParamMap);
		List<PlmSupplementLineEntity_HI_RO> findListResult = plmSupplementLineDAO_HI_RO.findList(sql, queryParamMap);
		return findListResult.get(0);
	}

	public Pagination<PlmSupplementLineEntity_HI> getLines(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
		StringBuffer sql = new StringBuffer(" from PlmSupplementLineEntity_HI where 1=1 ");
		Map<String, Object> queryParamMap = new HashMap<String, Object>();
		SaafToolUtils.parperHbmParam(PlmSupplementLineEntity_HI.class, queryParamJSON, sql, queryParamMap);
		Pagination<PlmSupplementLineEntity_HI> findListResult = plmSupplementLineDAO_HI.findPagination(sql, queryParamMap, pageIndex, pageRows);
		return findListResult;
	}

	private List<PlmSupplementLineFileEntity_HI> getFileList(JSONObject queryParamJSON) {
		return plmSupplementLineFileDao.getFiles(queryParamJSON);
	}

    private List<PlmSupplementHeadEntity_HI> getHeader(JSONObject queryParamJSON) {
        StringBuffer sql = new StringBuffer(" from PlmSupplementHeadEntity_HI where 1=1 ");
        Map<String, Object> queryParamMap = new HashMap<String, Object>();
        SaafToolUtils.parperHbmParam(PlmSupplementHeadEntity_HI.class, queryParamJSON, sql, queryParamMap);
        return plmSupplementHeadDAO_HI.findList(sql, queryParamMap);
    }

	private List<PlmSupWarehouseEntity_HI_RO> getWarList(JSONObject queryParamJSON) {
		StringBuffer sql = new StringBuffer(PlmSupWarehouseEntity_HI_RO.SQL_WAREHOUSE);
		Map<String, Object> queryParamMap = new HashMap<String, Object>();
		SaafToolUtils.parperHbmParam(PlmSupWarehouseEntity_HI_RO.class, queryParamJSON, sql, queryParamMap);
		List<PlmSupWarehouseEntity_HI_RO> findListResult = plmSupWarehouseDAO_HI_RO.findList(sql, queryParamMap);
		return findListResult;
	}

	private List<PlmSupWarehouseEntity_HI_RO> getRegList(JSONObject queryParamJSON) {
		StringBuffer sql = new StringBuffer(PlmSupWarehouseEntity_HI_RO.SQL_REGION);
		Map<String, Object> queryParamMap = new HashMap<String, Object>();
		SaafToolUtils.parperHbmParam(PlmSupWarehouseEntity_HI_RO.class, queryParamJSON, sql, queryParamMap);
		List<PlmSupWarehouseEntity_HI_RO> findListResult = plmSupWarehouseDAO_HI_RO.findList(sql, queryParamMap);
		return findListResult;
	}

	//	@Override
//	public Pagination<PlmSupplementLineEntity_HI_RO> findPlmSupplementLineInfo(JSONObject queryParamJSON, Integer pageRows, Integer pageIndex) {
//		return null;
//	}

	public Object savePlmSupplementLineInfo(JSONObject queryParamJSON) {
		PlmSupplementLineEntity_HI plmSupplementLineEntity_HI = JSON.parseObject(queryParamJSON.toString(), PlmSupplementLineEntity_HI.class);
		Object resultData = plmSupplementLineDAO_HI.save(plmSupplementLineEntity_HI);
		return resultData;
	}

	private Map<String, List<PlmSupWarehouseEntity_HI_RO>> merge(List<PlmSupWarehouseEntity_HI_RO> newShops) {
		Map<String, List<PlmSupWarehouseEntity_HI_RO>> map = null;
		if (newShops != null && newShops.size() > 0) {
			map = new HashMap<>();
			for (int k = 0; k < newShops.size(); k++) {
				PlmSupWarehouseEntity_HI_RO entity = newShops.get(k);
				if (entity != null) {
					if (map.get(entity.getRegion()) == null) {
						List<PlmSupWarehouseEntity_HI_RO> entityList = new ArrayList<>();
						entityList.add(entity);
						map.put(entity.getRegion(), entityList);
					} else {
						List<PlmSupWarehouseEntity_HI_RO> existedList = map.get(entity.getRegion());
						existedList.add(entity);
					}
				}
			}
		} else {
			return null;
		}
		return map;
	}


	private List<PlmSupWarehouseEntity_HI_RO> processRegMap(Map<String, List<PlmSupWarehouseEntity_HI_RO>> map) {
		List<PlmSupWarehouseEntity_HI_RO> results = null;
		if (map != null){
			results = new ArrayList<>();
			for (List<PlmSupWarehouseEntity_HI_RO> list : map.values()){
				PlmSupWarehouseEntity_HI_RO en = null;
				if (list != null && list.size() > 0){
					en = new PlmSupWarehouseEntity_HI_RO();
					int qty = 0;
					int qtyOnWay = 0;
					String region = list.get(0).getRegion();
					for (PlmSupWarehouseEntity_HI_RO ro : list) {
						qty += ro.getQty();
						qtyOnWay += ro.getQtyOnWay();
					}
					if (region == null || "".equals(region)) continue;
					en.setInvCode(region);
					en.setRegion(region);
					en.setQty(qty);
					en.setQtyOnWay(qtyOnWay);
					results.add(en);
				}
			}
		}
		return results;
	}

    private Pagination<PlmProductHeadEntity_HI> findProductList2(JSONObject param) {
        Pagination<PlmProductHeadEntity_HI> list = plmProductHeadServer.findPagination(param, 1, 1);
        return list;
    }
}
