package com.sie.saaf.bpm.model.inter.server;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.bpm.model.entities.ActBpmCategoryEntity_HI;
import com.sie.saaf.bpm.model.entities.readonly.ActBpmCategoryEntity_HI_RO;
import com.sie.saaf.bpm.model.entities.readonly.ActBpmModelEntity_HI_RO;
import com.sie.saaf.bpm.model.inter.IActBpmCategory;
import com.sie.saaf.bpm.model.inter.IActBpmModel;
import com.sie.saaf.common.constant.CommonConstants;
import com.sie.saaf.common.util.SaafToolUtils;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component("actBpmCategoryServer")
public class ActBpmCategoryServer implements IActBpmCategory {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ActBpmCategoryServer.class);
	
	@Autowired
	private ViewObject<ActBpmCategoryEntity_HI> bpmCategoryDAO_HI;
	
	@Autowired
	private BaseViewObject<ActBpmCategoryEntity_HI_RO> bpmCategoryDAO_HI_RO;
	
	@Autowired
	private IActBpmModel bpmModelServer;

	public ActBpmCategoryServer() {
		super();
	}
	
	
	/**
	 * 查询流程分类
	 * @author laoqunzhao 2018-04-27
	 * categoryId 分类ID
	 * @return 流程分类
	 */
	@Override
	public ActBpmCategoryEntity_HI get(Integer categoryId) {
		return bpmCategoryDAO_HI.getById(categoryId);
	}

	/**
	 * 根据流程key查询流程分类
	 * @author laoqunzhao 2018-09-27
	 * processKey 流程key
	 * @return 流程分类
	 */
	@Override
	public ActBpmCategoryEntity_HI getByProcessKey(String processKey){
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put("processKey", processKey);
		properties.put("deleteFlag", 0);
		List<ActBpmCategoryEntity_HI>  list = bpmCategoryDAO_HI.findByProperty(properties);
		return list == null || list.isEmpty()?null : list.get(0);
	}

	/**
	 * 查询流程分类列表
	 * @author laoqunzhao 2018-04-27
	 * @param queryParamJSON
	 * {
	 * parentId 父节点ID
	 * deleteFlag 是否删除   0.否  1.是
	 * categoryName 分类名称
	 * systemCode 系统编码
	 * ouIds 事业部ID[]
	 * }
	 * @return 流程分类列表
	 */
	@Override
	public List<ActBpmCategoryEntity_HI_RO> findCategories(JSONObject queryParamJSON) {
		//转换为Map结构
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		StringBuffer sql = new StringBuffer(ActBpmCategoryEntity_HI_RO.QUERY_ALL_CATEGORY_SQL);
		SaafToolUtils.parperParam(queryParamJSON, "parent_id", "parentId", sql, paramsMap, "=");
		SaafToolUtils.parperParam(queryParamJSON, "delete_flag", "deleteFlag", sql, paramsMap, "=");
		SaafToolUtils.parperParam(queryParamJSON, "category_name", "categoryName", sql, paramsMap, "fulllike");
        //系统编码码查询
        String systemCode = StringUtils.trimToNull(queryParamJSON.getString("systemCode"));
        List<Integer> ouIds = new ArrayList<>();
        if(queryParamJSON.containsKey("ouIds")) {
            JSONArray ouIdsJSON = queryParamJSON.getJSONArray("ouIds");
            for(int i=0; i<ouIdsJSON.size(); i++) {
                ouIds.add(ouIdsJSON.getInteger(i));
            }
        }
        if(systemCode != null || !ouIds.isEmpty()) {
            sql.append(" and exists(select 1 from act_re_model model inner join act_bpm_permission perm on perm.proc_def_key=model.key_ where model.category_=cate.category_id");
            if(systemCode != null) {
                sql.append(" and upper(perm.system_code) = :systemCode ");
                paramsMap.put("systemCode", systemCode.toUpperCase());
            }
            if(!ouIds.isEmpty()) {
                sql.append(" and perm.ou_id in (" + StringUtils.join(ouIds, ",") + ")");
            }
            sql.append(")");
        }
        sql.append(" order by category_code asc ");
        return bpmCategoryDAO_HI_RO.findList(sql.toString(), paramsMap);
	}

	/**
	 * 保存流程分类
	 * @author laoqunzhao 2018-04-27
	 * @param paramsJSON
	 * {
     * categoryId 节点ID
     * categoryName　分类名称
     * parentId　父节点ID
     * processKey 流程标识
     * }
     * @param userId 操作人ID
	 * @throws Exception 
	 */
	@Override
	public ActBpmCategoryEntity_HI save(JSONObject paramsJSON, Integer userId) throws Exception {
		ActBpmCategoryEntity_HI instance = SaafToolUtils.setEntity(ActBpmCategoryEntity_HI.class, paramsJSON, bpmCategoryDAO_HI, userId);
		Assert.isTrue(StringUtils.isNotBlank(instance.getCategoryName()),"流程分类名称不能为空！");
		//没指定父节点添加顶层节点
		instance.setParentId(null==instance.getParentId()?-1 :instance.getParentId());
		instance.setDeleteFlag(0);
		//判断分类是否存在
		boolean existed = categoryExist(instance.getCategoryId(), instance.getParentId(), instance.getCategoryName());
		Assert.isTrue(!existed, "流程分类名称已存在！");
		Assert.isTrue(!processKeyExist(instance.getCategoryId(), instance.getProcessKey()), "流程标识不能重复！");
		ActBpmCategoryEntity_HI instanceOld = (instance.getCategoryId() == null)?
				null :bpmCategoryDAO_HI.getById(instance.getCategoryId());
		if(StringUtils.isNotBlank(instance.getProcessKey())){
			instance.setProcessKey(instance.getProcessKey().toUpperCase().trim());
		}
        //更新节点
        if(instanceOld != null) {
        	Integer parentIdOld = instanceOld.getParentId();
            bpmCategoryDAO_HI.update(instance);
        }else {
        	//新增节点
        	bpmCategoryDAO_HI.save(instance);
        }
		//设置节点编码
		updateCategoryCode(instance);
        //保存完成，在事务中确认分类唯一、流程标识唯一
      	existed = categoryExist(instance.getCategoryId(), instance.getParentId(), instance.getCategoryName());
        Assert.isTrue(!existed, "流程分类名称已存在！");
      	Assert.isTrue(!processKeyExist(instance.getCategoryId(), instance.getProcessKey()), "流程标识不能重复！");
        LOGGER.info("save bpm category:" + paramsJSON.toString());
        return instance;
	}

	/**
	 * 标记删除流程分类
	 * @author laoqunzhao 2018-04-27
	 * @param paramsJSON
	 * {
     * categoryIds JSONArray分类ID
     * }
     * @param userId 操作人ID
	 */
	@Override
	public void delete(JSONObject paramsJSON, Integer userId) {
	    JSONArray categoryIds = paramsJSON.getJSONArray("categoryIds");
        if(categoryIds != null && !categoryIds.isEmpty()){
            for(int i=0; i<categoryIds.size(); i++){
                int id = categoryIds.getIntValue(i);

				ActBpmCategoryEntity_HI instance = bpmCategoryDAO_HI.getById(id);
				if(instance == null) {
					continue;
				}
				Assert.isTrue(!categoryInUsing(id), "该流程分类下已存在流程，不能删除！");
				instance.setDeleteFlag(CommonConstants.DELETE_TRUE);
				instance.setOperatorUserId(userId);
				bpmCategoryDAO_HI.update(instance);
				//删除子节点
				List<Integer> subIds = getCategoryIds(id);
				if(subIds != null && !subIds.isEmpty()){
					for(Integer subId : subIds){
						ActBpmCategoryEntity_HI instance_ = bpmCategoryDAO_HI.getById(subId);
						if(instance_ == null) {
							continue;
						}
						instance_.setDeleteFlag(CommonConstants.DELETE_TRUE);
						instance_.setOperatorUserId(userId);
						bpmCategoryDAO_HI.update(instance_);
					}
				}

			}
            LOGGER.info("delete bpm category:" + paramsJSON.toString());
		}
	}
	
	/**
     * 物理删除流程分类
     * @author laoqunzhao 2018-04-27
     * @param paramsJSON JSONObject
     * categoryIds JSONArray分类ID
     */
	@Override
	public void destory(JSONObject paramsJSON) {
		JSONArray categoryIds = paramsJSON.getJSONArray("categoryIds");
        if(null!=categoryIds && !categoryIds.isEmpty()){
            for(int i=0; i<categoryIds.size(); i++){
                int id = categoryIds.getIntValue(i);
				bpmCategoryDAO_HI.delete(id);
			}
            LOGGER.info("destory bpm category:" + paramsJSON.toString());
		}
	}
	
	/**
	 * 判断节点是否存在
	 * @author laoqunzhao 2018-04-27
	 * @param categoryId 节点ID
	 * @param parentId 父节点ID
	 * @param categoryName 分类名称
	 * @return
	 */
	@Override
	public boolean categoryExist(Integer categoryId, Integer parentId, String categoryName) {
	    Map<String, Object> properties = new HashMap<String, Object>();
	    //properties.put("parentId", parentId);
	    properties.put("categoryName", categoryName);
	    properties.put("deleteFlag", 0);
	    List<ActBpmCategoryEntity_HI>  list = bpmCategoryDAO_HI.findByProperty(properties);
	    if(null!=list && !list.isEmpty()) {
	        for(ActBpmCategoryEntity_HI instance: list) {
	            if(!instance.getCategoryId().equals(categoryId)) {
	                return true;
	            }
	        }
	    }
	    return false;
	}
	
	/**
	 * 根据节点ID获取所有子节点（包括节点本身）
	 * @author laoqunzhao 2018-04-27
	 * @param categoryId 节点ID
	 * @return
	 */
	@Override
	public List<Integer> getCategoryIds(Integer categoryId){
	    List<Integer> categoryIds = new ArrayList<Integer>();
	    String hql = "from ActBpmCategoryEntity_HI bean where "
	            + "exists(select 1 from ActBpmCategoryEntity_HI bean1 where bean.categoryCode like concat(bean1.categoryCode,'.%') and bean1.categoryId=" + categoryId +")";
	    List<ActBpmCategoryEntity_HI> list = bpmCategoryDAO_HI.findList(hql);
	    categoryIds.add(categoryId);
	    if(list != null && !list.isEmpty()) {
	        for(ActBpmCategoryEntity_HI instance:list) {
	            categoryIds.add(instance.getCategoryId());
	        }
	    }
        return categoryIds;
	}
	
	/**
	 * 更新节点及其子节点的编码
	 * @author laoqunzhao 2018-04-27
	 * @param instance ActBpmCategoryEntity_HI
	 */
	private void updateCategoryCode(ActBpmCategoryEntity_HI instance ) {
	    if(-1 == instance.getParentId()) {
	    	//顶层节点编码为本身ID
	    	instance.setCategoryCode(String.valueOf(instance.getCategoryId()));
	    }else {
	    	//非顶层节点编码为父节点编辑+.+本身ID
	        ActBpmCategoryEntity_HI parent = bpmCategoryDAO_HI.getById(instance.getParentId());
	        instance.setCategoryCode(parent.getCategoryCode()+ "." + instance.getCategoryId());
	    }
	    bpmCategoryDAO_HI.update(instance);
	    List<ActBpmCategoryEntity_HI> list = bpmCategoryDAO_HI.findByProperty("parentId", instance.getCategoryId());
	    if(null!=list && !list.isEmpty()) {
	        for(ActBpmCategoryEntity_HI entity: list) {
	            entity.setOperatorUserId(instance.getLastUpdatedBy());
	            updateCategoryCode(entity);
	        }
	    }
	}
	
	/**
	 * 判断流程分类是否已被流程引用
	 * @author laoqunzhao 2018-05-04
	 * @param categoryId 流程分类ID
	 * @return true.是  false.否
	 */
	private boolean categoryInUsing(Integer categoryId) {
		JSONObject queryParamsJSON = new JSONObject();
		queryParamsJSON.put("categoryId", categoryId);
		List<ActBpmModelEntity_HI_RO> models = bpmModelServer.findModels(queryParamsJSON, 1, 1).getData();
		return models != null && !models.isEmpty()? true : false;
	}
	
	/**
	 * 判断流程标识是否已存在
	 * @author laoqunzhao 2018-09-04
	 * @param categoryId 节点ID
	 * @param processKey 流程标识
	 * @return true.是  false.否
	 */
	private boolean processKeyExist(Integer categoryId, String processKey) {
		if(StringUtils.isBlank(processKey)) {
			return false;
		}
	    Map<String, Object> properties = new HashMap<String, Object>();
	    properties.put("processKey", processKey);
	    properties.put("deleteFlag", 0);
	    List<ActBpmCategoryEntity_HI>  list = bpmCategoryDAO_HI.findByProperty(properties);
	    if(null!=list && !list.isEmpty()) {
	        for(ActBpmCategoryEntity_HI instance: list) {
	            if(!instance.getCategoryId().equals(categoryId)) {
	                return true;
	            }
	        }
	    }
	    return false;
	}

	
}
