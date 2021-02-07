package com.sie.saaf.bpm.model.inter.server;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.bpm.model.entities.ActBpmTaskUrgeConfigEntity_HI;
import com.sie.saaf.bpm.model.entities.readonly.ActBpmTaskEntity_HI_RO;
import com.sie.saaf.bpm.model.entities.readonly.ActBpmTaskUrgeConfigEntity_HI_RO;
import com.sie.saaf.bpm.model.inter.IActBpmCategory;
import com.sie.saaf.bpm.model.inter.IActBpmModel;
import com.sie.saaf.bpm.model.inter.IActBpmTaskUrgeConfig;
import com.sie.saaf.bpm.utils.ConvertUtil;
import com.sie.saaf.common.util.SaafToolUtils;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.text.SimpleDateFormat;
import java.util.*;


@Component("actBpmTaskUrgeConfigServer")
public class ActBpmTaskUrgeConfigServer implements IActBpmTaskUrgeConfig {

	private static final Logger LOGGER = LoggerFactory.getLogger(ActBpmTaskUrgeConfigServer.class);

	@Autowired
	private ViewObject<ActBpmTaskUrgeConfigEntity_HI> bpmTaskUrgeConfigDAO_HI;

	@Autowired
	private BaseViewObject<ActBpmTaskUrgeConfigEntity_HI_RO> bpmTaskUrgeConfigDAO_HI_RO;

	@Autowired
	private BaseViewObject<ActBpmTaskEntity_HI_RO> bpmTaskDAO_HI_RO;

	@Autowired
	private IActBpmCategory bpmCategoryServer;

	@Autowired
	private IActBpmModel bpmModelServer;

	public ActBpmTaskUrgeConfigServer() {
		super();
	}


	/**
	 * 催办设置查询
	 * @author sie-laoqunzhao 2018-07-05
	 * @param queryParamJSON
	 * {
	 * categoryId 流程分类ID
	 * processDefinitionKey 流程定义Key，条件=
	 * startDate 起始时间，格式yyyy-MM-dd
	 * endDate 截止时间，格式yyyy-MM-dd
	 * disabled 是否禁用
	 * deleteFlag 删除标记，1.已删除，0.未删除
	 * systemCode 系统编码
	 * ouIds 事业部ID[]
	 * }
	 * @param pageIndex 页码索引
	 * @param pageRows 每页记录数
	 */
	@Override
	public Pagination<ActBpmTaskUrgeConfigEntity_HI_RO> findUrgeConfig(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		StringBuffer sql = new StringBuffer(ActBpmTaskUrgeConfigEntity_HI_RO.QUERY_ALL_CONFIG_SQL);
		//流程分类
		String categoryId = StringUtils.trimToNull(queryParamJSON.getString("categoryId"));
		if(categoryId != null) {
			List<Integer> categoryIds = bpmCategoryServer.getCategoryIds(Integer.parseInt(categoryId));
			sql.append(" and cat.category_id in (" + StringUtils.join(categoryIds, ",") + ")");
		}
		String processDefinitionKey = StringUtils.trimToNull(queryParamJSON.getString("cateprocessDefinitionKeygoryId"));
		if(processDefinitionKey != null) {
			sql.append(" and cfg.proc_def_key = :processDefinitionKey)");
			paramsMap.put("processDefinitionKey", processDefinitionKey);
		}
		if(StringUtils.isNotBlank(queryParamJSON.getString("disabled"))) {
			sql.append(" and cfg.disabled =:disabled");
			paramsMap.put("disabled", "true".equals(queryParamJSON.getString("disabled")));
		}
		if(StringUtils.isNotBlank(queryParamJSON.getString("deleteFlag"))) {
			sql.append(" and cfg.delete_flag =:deleteFlag");
			paramsMap.put("deleteFlag", queryParamJSON.getIntValue("deleteFlag"));
		}
		String startDate = StringUtils.trimToNull(queryParamJSON.getString("startDate"));
		if(startDate != null) {
			sql.append(" and cfg.end_time >= :startDate");
			paramsMap.put("startDate", ConvertUtil.stringToDateYMD(startDate));
		}
		String endDate = StringUtils.trimToNull(queryParamJSON.getString("endDate"));
		if(endDate != null) {
			sql.append(" and cfg.start_time < :endDate");
			Calendar c = new GregorianCalendar();
			c.setTime(ConvertUtil.stringToDateYMD(endDate));
			c.add(Calendar.DATE, 1);//日期向后移一天
			paramsMap.put("endDate", c.getTime());
		}
		//系统代码查询
		String systemCode = StringUtils.trimToNull(queryParamJSON.getString("systemCode"));
		List<Integer> ouIds = new ArrayList<>();
		if(queryParamJSON.containsKey("ouIds")) {
			JSONArray ouIdsJSON = queryParamJSON.getJSONArray("ouIds");
			for(int i=0; i<ouIdsJSON.size(); i++) {
				ouIds.add(ouIdsJSON.getInteger(i));
			}
		}
		if(systemCode != null || !ouIds.isEmpty()) {
			sql.append(" and exists(select 1 from act_bpm_permission where proc_def_key=modell.key_ ");
			if(systemCode != null) {
				sql.append(" and upper(system_code) = :systemCode ");
				paramsMap.put("systemCode", systemCode.toUpperCase());
			}
			if(!ouIds.isEmpty()) {
				sql.append(" and ou_id in (" + StringUtils.join(ouIds, ",") + ")");
			}
			sql.append(")");
		}
		sql.append(" order by modell.name_");
		Pagination<ActBpmTaskUrgeConfigEntity_HI_RO> pagination = bpmTaskUrgeConfigDAO_HI_RO.findPagination(sql.toString(), paramsMap, pageIndex, pageRows);
		return pagination;
	}

	/**
	 * 查询需催办的任务
	 * @author sie-laoqunzhao 2018-07-05
	 * @param processDefinitionKey 流程定义Key，条件=
	 */
	@Override
	public List<ActBpmTaskEntity_HI_RO> getToUrgeTasks(String processDefinitionKey){
		if(StringUtils.isBlank(processDefinitionKey)) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		ActBpmTaskUrgeConfigEntity_HI config = getByProcessDefinitionKey(processDefinitionKey);
		//没有设置自动催办或已失效，返回null
		if(config == null || config.getDisabled() || new Date().before(config.getStartTime())
				|| new Date().after(config.getEndTime()) && !sdf.format(new Date()).equals(sdf.format(config.getEndTime()))) {
			return null;
		}
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		StringBuffer sql = new StringBuffer(ActBpmTaskEntity_HI_RO.QUERY_ALL_TASK_SQL);
		//流程KEY
		sql.append(" and upper(bpm.proc_def_key) = :processDefinitionKey")
				.append(" and task.create_time_ < to_date(:taskTime,'YYYY-MM-DD')")
				.append(" and not exists(select 1 from act_bpm_communicate cmc where cmc.task_id = task.id_ and cmc.creation_date > to_date(:urgeTime,'YYYY-MM-DD'))");
		paramsMap.put("processDefinitionKey", processDefinitionKey.toUpperCase());
		//任务时间,计算超时时间点
		String taskTime = sdf.format(new Date(new Date().getTime() - config.getTimeout() * 24 * 3600 * 1000));
		paramsMap.put("taskTime", taskTime);
		//催办时间，计算上一次催办时间点
		String urgeTime = sdf.format(new Date(new Date().getTime() - config.getTimegap() * 3600 * 1000));
		paramsMap.put("urgeTime", urgeTime);
		sql.append(" order by task.id_ asc ");
		return bpmTaskDAO_HI_RO.findList(sql.toString(), paramsMap);
	}



	/**
	 * 根据主键查询催办设置
	 * @author sie-laoqunzhao 2018-07-05
	 * @param configId 主键
	 * @return ActBpmTaskUrgeConfigEntity_HI
	 */
	@Override
	public ActBpmTaskUrgeConfigEntity_HI getById(int configId) {
		return bpmTaskUrgeConfigDAO_HI.getById(configId);
	}

	/**
	 * 根据流程定义KEY查询催办设置
	 * @author sie-laoqunzhao 2018-07-05
	 * @param processDefinitionKey 流程定义KEY
	 * @return ActBpmTaskUrgeConfigEntity_HI
	 */
	@Override
	public ActBpmTaskUrgeConfigEntity_HI getByProcessDefinitionKey(String processDefinitionKey) {
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put("processDefinitionKey", processDefinitionKey);
		properties.put("deleteFlag", 0);
		List<ActBpmTaskUrgeConfigEntity_HI> instances = bpmTaskUrgeConfigDAO_HI.findByProperty(properties);
		return instances == null || instances.isEmpty()?null : instances.get(0);
	}


	/**
	 * 保存催办设置
	 * @author sie-laoqunzhao 2018-07-05
	 * @param paramsJSON JSON格式entity
	 * {
	 * configId 主键
	 * startTime 开始时间
	 * endTime 结束时间
	 * processDefinitionKey 流程定义KEY
	 * timeout 超时催办天数
	 * timegap 催办间隔小时
	 * disabled 是否禁用
	 * operatorUserId 操作人ID
	 * }
	 * @param userId 操作人ID
	 * @throws Exception
	 */
	@Override
	public void saveOrUpdate(JSONObject paramsJSON, int userId) throws Exception {
		//将json转换成entity
		ActBpmTaskUrgeConfigEntity_HI instance  = SaafToolUtils.setEntity(ActBpmTaskUrgeConfigEntity_HI.class, paramsJSON, bpmTaskUrgeConfigDAO_HI, userId);
		Assert.isTrue(StringUtils.isNotBlank(instance.getProcessDefinitionKey()), "流程标识不能为空！");
		Assert.isTrue(instance.getStartTime() != null && instance.getEndTime() != null, "有效时间不能为空！");
		Assert.notNull(instance.getTimeout(), "超时时间不能为空！");
		Assert.notNull(instance.getTimegap(), "提醒间隔不能为空！");
		Assert.notNull(bpmModelServer.getByKey(instance.getProcessDefinitionKey()), "流程定义不存在！");
		if(instance.getConfigId() != null){

		}
		ActBpmTaskUrgeConfigEntity_HI config = getByProcessDefinitionKey(instance.getProcessDefinitionKey());
		if(config != null){
			Assert.isTrue(instance.getConfigId() == null || config.getConfigId().equals(instance.getConfigId()), "催办设置已存在！");
			if(instance.getConfigId() == null){
				config.setDisabled(instance.getDisabled() == null? false: instance.getDisabled());
				config.setDeleteFlag(0);
				config.setEndTime(instance.getEndTime());
				config.setOperatorUserId(userId);
				config.setStartTime(instance.getStartTime());
				config.setTimegap(instance.getTimegap());
				config.setTimeout(instance.getTimeout());
				bpmTaskUrgeConfigDAO_HI.saveOrUpdate(config);
			}
		}else{
			instance.setDisabled(instance.getDisabled() == null? false: instance.getDisabled());
			instance.setDeleteFlag(0);
			bpmTaskUrgeConfigDAO_HI.saveOrUpdate(instance);
		}
		LOGGER.info("saved bpm urge config:" + paramsJSON.toString());
	}

	/**
	 * 保存催办设置
	 * @author sie-laoqunzhao 2018-07-05
	 * @param paramsJSON JSON格式entity
	 * {
	 * configId 主键
	 * startTime 开始时间
	 * endTime 结束时间
	 * processDefinitionKeys 流程定义KEY[]
	 * timeout 超时催办天数
	 * timegap 催办间隔小时
	 * disabled 是否禁用
	 * operatorUserId 操作人ID
	 * }
	 * @param userId 操作人ID
	 * @throws Exception
	 */
	@Override
	public void saveConfigs(JSONObject paramsJSON, int userId) throws Exception {
		JSONArray processDefinitionKeysJSON = paramsJSON.getJSONArray("processDefinitionKeys");
		for(int i=0; i<processDefinitionKeysJSON.size(); i++){
			if(processDefinitionKeysJSON.getString(i).endsWith(".-999")){
				paramsJSON.put("processDefinitionKey", processDefinitionKeysJSON.getString(i));
				saveOrUpdate(paramsJSON, userId);
			}
		}
		LOGGER.info("saved bpm urge config:" + paramsJSON.toString());
	}

	/**
	 * 更新催办设置状态
	 * @author sie-laoqunzhao 2018-07-05
	 * @param paramJSON
	 * {
	 * configIds JSONArray 代办ID
	 * disabled 是否禁用
	 * operatorUserId 操作人ID
	 * }
	 */
	@Override
	public void updateStatus(JSONObject paramJSON) {
		JSONArray configIds = paramJSON.getJSONArray("configIds");
		Integer operatorUserId = paramJSON.getInteger("operatorUserId");
		Boolean disabled = paramJSON.getBoolean("disabled");
		if(configIds!=null && !configIds.isEmpty()){
			for(int i=0; i<configIds.size(); i++){
				int id = configIds.getIntValue(i);
				ActBpmTaskUrgeConfigEntity_HI instance = bpmTaskUrgeConfigDAO_HI.getById(id);
				if(instance!=null) {
					instance.setDisabled(disabled);
					instance.setOperatorUserId(operatorUserId);
					bpmTaskUrgeConfigDAO_HI.update(instance);
				}
			}
		}
		LOGGER.info((disabled?"disabled":"enabled") + " bpm urge config:" + paramJSON.toString());
	}

	/**
	 * 标记删除催办设置
	 * @author sie-laoqunzhao 2018-07-05
	 * @param paramJSON
	 * {
	 * configIds JSONArray 代办ID
	 * }
	 */
	@Override
	public void delete(JSONObject paramJSON) {
		JSONArray configIds = paramJSON.getJSONArray("configIds");
		Integer operatorUserId = paramJSON.getInteger("operatorUserId");
		if(configIds!=null && !configIds.isEmpty()){
			for(int i=0; i<configIds.size(); i++){
				int id = configIds.getIntValue(i);
				ActBpmTaskUrgeConfigEntity_HI instance = bpmTaskUrgeConfigDAO_HI.getById(id);
				if(instance!=null) {
					instance.setDeleteFlag(1);
					instance.setOperatorUserId(operatorUserId);
					bpmTaskUrgeConfigDAO_HI.update(instance);
				}
			}
		}
		LOGGER.info("deleted bpm urge config:" + paramJSON.toString());
	}

	/**
	 * 物理删除代办设置
	 * @param paramJSON
	 * configIds JSONArray 代办ID
	 */
	@Override
	public void destory(JSONObject paramJSON) {
		JSONArray configIds = paramJSON.getJSONArray("configIds");
		if(configIds!=null && !configIds.isEmpty()){
			for(int i=0; i<configIds.size(); i++){
				int id = configIds.getIntValue(i);
				bpmTaskUrgeConfigDAO_HI.delete(id);
			}
		}
		LOGGER.info("destory bpm urge config: " + paramJSON.toString());
	}


}
