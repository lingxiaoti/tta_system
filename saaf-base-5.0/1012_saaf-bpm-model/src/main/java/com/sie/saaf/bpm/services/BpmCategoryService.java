package com.sie.saaf.bpm.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.bpm.model.entities.ActBpmCategoryEntity_HI;
import com.sie.saaf.bpm.model.entities.ActBpmTaskDelegateConfigEntity_HI;
import com.sie.saaf.bpm.model.entities.readonly.ActBpmCategoryEntity_HI_RO;
import com.sie.saaf.bpm.model.entities.readonly.ActBpmModelEntity_HI_RO;
import com.sie.saaf.bpm.model.inter.IActBpmCategory;
import com.sie.saaf.bpm.model.inter.IActBpmModel;
import com.sie.saaf.bpm.model.inter.IActBpmTaskDelegateConfig;
import com.sie.saaf.bpm.utils.VerifyUtil;
import com.sie.saaf.common.bean.ProFileBean;
import com.sie.saaf.common.model.inter.IBaseAccreditCache;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.common.util.SaafToolUtils;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.paging.Pagination;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/bpmCategoryService")
public class BpmCategoryService extends CommonAbstractService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BpmCategoryService.class);
	
	@Autowired
	private IActBpmCategory bpmCategoryServer;
	
	@Autowired
	private IActBpmModel bpmModelServer;
	
	@Autowired
	private IBaseAccreditCache baseAssreditServer;
	
	@Autowired
	private IActBpmTaskDelegateConfig bpmTaskDelegateConfigServer;

	public BpmCategoryService() {
		super();
	}
	
	/**
     * 流程分类查询
     * @author laoqunzhao 2018-04-27
     * @param params
     * {
     * parentId 父ID，第一层为-1
     * categoryName 分类名称
     * deleteFlag 删除标记，1.已删除，0.未删除
     * attachProcess 流程分类下附上流程  true.是   false.否
     * ouId 事业部ID
     * responsibilityId 职责ID
     * systemCode 系统编码
     * delegateConfigId 流程委托审批设置ID,标识已设置的分类、流程
     * delegateShow 用于显示，不带出多余的分类，与委托审批关联  true.是  false.否
     * }
     */
	@RequestMapping(method= RequestMethod.POST,value="findCategories")
	public String findCategories(@RequestParam(required = false) String params) {
	    try {
    		JSONObject queryParamJSON = super.parseObject(params);
    		if(queryParamJSON!=null && !queryParamJSON.containsKey("deleteFlag")){
    			queryParamJSON.put("deleteFlag", 0);//只查询未删除的数据
    		}
    		//获取BU，如有只查询当前BU下的数据
    		Integer ouId = queryParamJSON.containsKey("ouId")? queryParamJSON.getInteger("ouId") : null;
    		if(ouId == null && StringUtils.isNotBlank(queryParamJSON.getString("responsibilityId"))) {
    			Integer responsibilityId = queryParamJSON.getInteger("responsibilityId");
    			ProFileBean proFileBean  = baseAssreditServer.getOrg(super.getSessionUserId(), responsibilityId);
    			ouId = proFileBean == null? null : Integer.valueOf(proFileBean.getProfileValue());
    		}
    		if(ouId != null) {
    			queryParamJSON.put("ouIds", new JSONArray().fluentAdd(ouId));
    		}
    		//查询分类
    		List<ActBpmCategoryEntity_HI_RO> result = bpmCategoryServer.findCategories(queryParamJSON);
    		if(result != null && !result.isEmpty()) {
    			//分类列表附上流程
    			if("true".equals(queryParamJSON.getString("attachProcess"))) {
    				result = attachProcess(result, queryParamJSON);
    			}
    		}
    		if(StringUtils.isNotBlank(queryParamJSON.getString("delegateConfigId"))) {
    			result = doDelegate(result, queryParamJSON.getInteger("delegateConfigId"), "true".equals(queryParamJSON.getString("delegateShow")));
    		}
    		return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "成功", result==null?0:result.size(), result).toString();
	    } catch (Exception e) {
	        LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, "查询失败！", 0, null).toString();
	    }
	}
	
	
	
	/**
	 * 保存流程分类
	 * @author laoqunzhao 2018-04-27
     * @param params JSONObject
     * categoryId 节点ID
     * categoryName　分类名称
     * parentId　父节点ID
     * processKey 流程标识 
     * @return
     */
	@RequestMapping(method= RequestMethod.POST,value="save")
	public String save(@RequestParam(required = true) String params){
		try {
			JSONObject paramsJSON = super.parseObject(params);
			VerifyUtil.verifyJSON(paramsJSON, "categoryName" ,true, 32, "流程分类名称");
			VerifyUtil.verifyJSON(paramsJSON, "processKey" ,false, 32, "流程标识");
			ActBpmCategoryEntity_HI instance = bpmCategoryServer.save(paramsJSON, getSessionUserId());
			JSONObject data = (JSONObject) JSON.toJSON(instance);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "成功", 0, data).toString();
		} catch (IllegalArgumentException e) {
        	LOGGER.error(e.getMessage(), e);
        	return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        } catch (Exception e) {
        	LOGGER.error(e.getMessage(), e);
        	return SToolUtils.convertResultJSONObj(ERROR_STATUS, "保存失败！", 0, null).toString();
        }
	}
	
	/**
     * 删除流程分类
     * @author laoqunzhao 2018-04-27
     * @param params
     * {
     * categoryIds 分类ID[]
     * }
     * @return
     */
	@RequestMapping(method= RequestMethod.POST,value="delete")
	public String delete(@RequestParam(required = true) String params){
		try {
			JSONObject paramsJSON = super.parseObject(params);
			SaafToolUtils.validateJsonParms(paramsJSON, "categoryIds");
			bpmCategoryServer.delete(paramsJSON, super.getSessionUserId());
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "成功", 0, null).toString();
		} catch (IllegalArgumentException e) {
        	LOGGER.error(e.getMessage(), e);
        	return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        } catch (Exception e) {
        	LOGGER.error(e.getMessage(), e);
        	return SToolUtils.convertResultJSONObj(ERROR_STATUS, "删除失败！", 0, null).toString();
        }
	}
	
	@Override
	public IBaseCommon<?> getBaseCommonServer() {
		return null;
	}
	
	/**
	 * 在流程分类后附上流程模型
	 * @param categories 流程分类列表
	 * @param queryParamJSON
	 * {
     * ouIds 事业部ID[]
     * systemCode 系统编码
     * }
	 * @return List<ActBpmCategoryEntity_HI_RO>
	 */
	private List<ActBpmCategoryEntity_HI_RO> attachProcess(List<ActBpmCategoryEntity_HI_RO> categories, JSONObject queryParamJSON){
		Pagination<ActBpmModelEntity_HI_RO> pagination = bpmModelServer.findModels(queryParamJSON, 1, Integer.MAX_VALUE);
		if(pagination.getData() != null && !pagination.getData().isEmpty()) {
			//将结果集转成Map结构，方便取值
			Map<String, List<ActBpmModelEntity_HI_RO>> modelsMap = new HashMap<String, List<ActBpmModelEntity_HI_RO>>();
			for(ActBpmModelEntity_HI_RO model : pagination.getData()) {
				if(StringUtils.isBlank(model.getCategoryId())) {
					continue;
				}
				List<ActBpmModelEntity_HI_RO> models = modelsMap.get(model.getCategoryId());
				if(models == null) {
					models = new ArrayList<ActBpmModelEntity_HI_RO>();
				}
				models.add(model);
				modelsMap.put(model.getCategoryId(), models);
			}
			int index = -11;//流程加入到分类树索引，用负数表示区别于分类
			//将流程加入到分类树中
			for(int i = categories.size()-1; i >= 0; i--) {
				ActBpmCategoryEntity_HI_RO categorie = categories.get(i);
				List<ActBpmModelEntity_HI_RO> models = modelsMap.get(String.valueOf(categorie.getCategoryId()));
				if(models != null && !models.isEmpty()) {
					for(int j = 0; j < models.size(); j++) {
						ActBpmModelEntity_HI_RO model = models.get(j);
						ActBpmCategoryEntity_HI_RO categoryModel = new ActBpmCategoryEntity_HI_RO();
						categoryModel.setCategoryId(index --);
						categoryModel.setCategoryCode(model.getKey());
						categoryModel.setCategoryName(model.getName());
						categoryModel.setProcessKey(model.getKey());
						categoryModel.setParentId(Integer.parseInt(model.getCategoryId()));
						categories.add(i + j + 1, categoryModel);
					}
				}
			}
		}
		return categories;
	}
	
	/**
	 * 处理委托设置分类，标记选中分类、移除无效分类
	 * @param categories 流程分类列表
	 * @param delegateConfigId
	 * @param delegateShow
	 * @return List<ActBpmCategoryEntity_HI_RO>
	 */
	private List<ActBpmCategoryEntity_HI_RO> doDelegate(List<ActBpmCategoryEntity_HI_RO> categories, Integer delegateConfigId, boolean delegateShow) {
		ActBpmTaskDelegateConfigEntity_HI config = bpmTaskDelegateConfigServer.getById(delegateConfigId);
		if(config == null || StringUtils.isBlank(config.getCategoryIds()) && StringUtils.isBlank(config.getProcessDefinitionKeys())) {
			return delegateShow? null :categories;
		}
		List<Integer> checkedCategoryIds = new ArrayList<Integer>();//委托设置的所有分类，包括子类
		Map<Integer, ActBpmCategoryEntity_HI_RO> categoryMap = new HashMap<Integer, ActBpmCategoryEntity_HI_RO>();//记录分类ID与分类的关系
		List<String> checkedProcessDefinitionKeys = new ArrayList<String>();//委托设置的所有流程KEY
		if(StringUtils.isNotBlank(config.getCategoryIds())) {
			String[] categoryIds_ = config.getCategoryIds().split(",");
			for(String categoryId: categoryIds_) {
				checkedCategoryIds.addAll(bpmCategoryServer.getCategoryIds(Integer.parseInt(categoryId)));
			}
		}
		if(StringUtils.isNotBlank(config.getProcessDefinitionKeys())) {
			checkedProcessDefinitionKeys.addAll(Arrays.asList(config.getProcessDefinitionKeys().split(",")));
		}
		//获取分类ID与分类编码的关系
		for(ActBpmCategoryEntity_HI_RO category : categories) {
			categoryMap.put(category.getCategoryId(), category);
			//选中分类
			if(category.getCategoryId() > 0 && checkedCategoryIds.contains(category.getCategoryId())) {
				category.setChecked(true);	
			}else if(category.getCategoryId() < 0 && checkedProcessDefinitionKeys.contains(category.getCategoryCode()) || checkedCategoryIds.contains(category.getParentId())) {
				category.setChecked(true);	
				checkedCategoryIds.add(category.getCategoryId());
			}
		}
		//移除未选中的分类
		if(delegateShow) {
			for(int i = categories.size()-1; i >= 0; i--) {
				if(!categories.get(i).isChecked()) {
					String categoryCode = categories.get(i).getCategoryCode();
					//判断是否有子类或者流程被选中
					boolean isCheckedParent = false;
					for(Integer categoryId : checkedCategoryIds) {
						ActBpmCategoryEntity_HI_RO category = categoryMap.get(categoryId);
						if(category == null || !category.isChecked()) {
							continue;
						}
						//分类
						if(category.getCategoryId()>0) {
							if(category.getCategoryCode() != null && category.getCategoryCode().startsWith(categoryCode)) {
								isCheckedParent = true;
								break;
							}
						}else if(categoryMap.get(category.getParentId()) != null){
							//流程
							ActBpmCategoryEntity_HI_RO category_ = categoryMap.get(category.getParentId());
							if(category_.getCategoryCode() != null && category_.getCategoryCode().startsWith(categoryCode)) {
								isCheckedParent = true;
								break;
							}
						}
					}
					if(!isCheckedParent) {
						categories.remove(i);
					}
				}
			}
		}
		return categories;
	}

	
}
