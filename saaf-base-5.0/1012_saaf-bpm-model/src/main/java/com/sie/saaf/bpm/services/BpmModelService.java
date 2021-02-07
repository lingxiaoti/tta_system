package com.sie.saaf.bpm.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.accredit.annotation.Permission;
import com.sie.saaf.bpm.model.entities.ActBpmCategoryEntity_HI;
import com.sie.saaf.bpm.model.entities.readonly.ActBpmModelEntity_HI_RO;
import com.sie.saaf.bpm.model.entities.readonly.ActUserTaskEntity_RO;
import com.sie.saaf.bpm.model.inter.*;
import com.sie.saaf.bpm.utils.VerifyUtil;
import com.sie.saaf.common.bean.UserSessionBean;
import com.sie.saaf.common.model.entities.BaseAttachmentEntity_HI;
import com.sie.saaf.common.model.entities.readonly.BaseAttachmentEntity_HI_RO;
import com.sie.saaf.common.model.inter.IBaseAttachment;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.model.inter.IFastdfs;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.common.util.SaafToolUtils;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.paging.Pagination;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.UserTask;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.impl.util.IoUtil;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.activiti.image.ProcessDiagramGenerator;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/bpmModelService")
public class BpmModelService extends CommonAbstractService {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    private RepositoryService repositoryService;
    
    @Autowired
    private ProcessEngineConfiguration processEngineConfiguration;
    
    @Autowired
    private IActBpmModel bpmModelServer;

    @Autowired
    private IActBpmProcess bpmProcessServer;
    
    @Autowired
    private IActBpmList bpmListServer;
        
    @Autowired
	private IActBpmPermission bpmPermissionServer;

    @Autowired
    private IFastdfs fastdfsServer;

    @Autowired
	private IBaseAttachment baseAttachmentServer;

    @Autowired
    private IActBpmCategory bpmCategoryServer;
    

    /**
     * 流程模型查询
     * @author laoqunzhao 2018-04-28
     * @param params
     * {
     * categoryId 流程分类ID
	 * name 名称
	 * key KEY
	 * deployed 是否已部署
	 * systemCode 系统编码
	 * roleKey 角色KEY
     * }
     * @param pageIndex 页码索引
     * @param pageRows 每页记录数
     */
	@Permission(menuCode = "LCSJ")
    @RequestMapping(method= RequestMethod.POST,value="findModels")
    public String findModels(@RequestParam(required = false) String params, 
    		@RequestParam(required = false, defaultValue = "1") Integer pageIndex,
    		@RequestParam(required = false, defaultValue = "10") Integer pageRows) {
        try {
            JSONObject paramJSON = super.parseObject(params);
            UserSessionBean user = super.getUserSessionBean();
            if(!"Y".equals(user.getIsadmin())) {
            	Integer orgId = super.getOrgId();
            	List<Integer> ouIds = new ArrayList<Integer>();
            	if(orgId != null) {
            		ouIds.add(orgId);
            	}else {
            		ouIds.add(-1);//没有OU权限加-1限制
            	}
        		JSONArray ouIdsJSON = (JSONArray)JSON.toJSON(ouIds);
        		paramJSON.put("ouIds", ouIdsJSON);
            }
            Pagination<ActBpmModelEntity_HI_RO> pagination = bpmModelServer.findModels(paramJSON, pageIndex, pageRows);
            JSONObject result = (JSONObject) JSONObject.toJSON(pagination);
            result.put(STATUS, SUCCESS_STATUS);
            result.put(MSG, "成功");
            return result.toString();
        } catch (Exception e) {
        	LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }

    /**
     * 保存流程模型
     * @author laoqunzhao 2018-04-28
     * @param params
     * {
     * modelId 模型ID
     * categoryId 分类ID
     * key 流程KEY
     * name 流程名称
     * description 描述说明
     * systemCode 系统编码
     * ouIds 事业部ID[]
     * }
     */
	@Permission(menuCode = "LCSJ")
    @RequestMapping(method= RequestMethod.POST,value="save")
    public String save(@RequestParam String params) {
        try {
            JSONObject paramsJSON = super.parseObject(params);
            //VerifyUtil.verifyJSON(paramsJSON, "key" ,true, 32, "流程唯一标识");
            VerifyUtil.verifyJSON(paramsJSON, "name" ,true, 64, "流程名称");
            VerifyUtil.verifyJSON(paramsJSON, "systemCode" ,true, 30, "系统编码");
            ActBpmCategoryEntity_HI category = bpmCategoryServer.get(paramsJSON.getInteger("categoryId"));
            Assert.notNull(category, "分类不存在！");
            Assert.isTrue(StringUtils.isNotBlank(category.getProcessKey()), "当前分类下不可创建流程！");
            Assert.isTrue(paramsJSON.containsKey("ouIds") && !paramsJSON.getJSONArray("ouIds").isEmpty(), "BU不能为空！");
            //澳优逻辑：一个流程只能选择一个BU,同一个BU下流程名称不能相同
            Assert.isTrue(paramsJSON.getJSONArray("ouIds").size() == 1, "只能选择一个BU！");
            String modelId = paramsJSON.getString("modelId");
            String key = paramsJSON.getString("key");
            UserSessionBean user = super.getUserSessionBean();
            if(StringUtils.isNotBlank(modelId)) {
            	ActBpmModelEntity_HI_RO model = bpmModelServer.getById(modelId);
            	Assert.notNull(model, "流程不存在！");
            	key = model.getKey();
            }else {
            	//编码 processKey + ouId
            	key = category.getProcessKey() + "." + paramsJSON.getJSONArray("ouIds").getIntValue(0);
                paramsJSON.put("key", key);
            }
            //非管理员判断数据权限
            if(!"Y".equals(user.getIsadmin()) && StringUtils.isNotBlank(modelId)) {
            	Integer ouId = super.getOrgId();
            	List<Integer> ouIds = new ArrayList<Integer>();
            	if(ouId != null) {
            		ouIds.add(ouId);
            	}
            	Assert.isTrue(bpmPermissionServer.hasPermission(key, ouIds), "没权限操作！");
            }
            //澳优逻辑：BU+流程名称不能重复
            for(int i = 0; i < paramsJSON.getJSONArray("ouIds").size(); i++) {
            	int ouId = paramsJSON.getJSONArray("ouIds").getIntValue(i);
            	List<ActBpmModelEntity_HI_RO> models = bpmModelServer.findByName(paramsJSON.getString("name"), ouId);
            	Assert.isTrue(models == null || models.isEmpty() || models.size() == 1 && models.get(0).getModelId().equals(modelId), "流程名称已存在！");
            }
            Model modelData = bpmModelServer.save(paramsJSON);
            JSONObject result = new JSONObject();
        	result.put("modelId", modelData.getId());
            result.put("key", modelData.getKey());
            result.put("name", modelData.getName());
        	return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "成功", 0, result).toString();
        } catch (IllegalArgumentException e) {
        	LOGGER.error(e.getMessage(), e);
        	return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        } catch (Exception e) {
        	LOGGER.error(e.getMessage(), e);
        	return SToolUtils.convertResultJSONObj(ERROR_STATUS, "保存失败！", 0, null).toString();
        }
    }
	
	
	 /**
     * 复制流程模型
     * @author laoqunzhao 2018-09-06
     * @param params
     * {
     * modelId 模型ID
     * categoryId 分类ID
     * key 流程KEY
     * name 流程名称
     * description 描述说明
     * systemCode 系统编码
     * ouIds 事业部ID[]
     * }
     */
	@Permission(menuCode = "LCSJ")
    @RequestMapping(method= RequestMethod.POST,value="copy")
    public String copy(@RequestParam String params) {
        try {
            JSONObject paramsJSON = super.parseObject(params);
            SaafToolUtils.validateJsonParms(paramsJSON, "modelId", "categoryId");
            //VerifyUtil.verifyJSON(paramsJSON, "key" ,true, 32, "流程标识");
            VerifyUtil.verifyJSON(paramsJSON, "name" ,true, 64, "流程名称");
            VerifyUtil.verifyJSON(paramsJSON, "systemCode" ,true, 30, "系统编码");
            ActBpmCategoryEntity_HI category = bpmCategoryServer.get(paramsJSON.getInteger("categoryId"));
            Assert.notNull(category, "分类不存在！");
            Assert.isTrue(StringUtils.isNotBlank(category.getProcessKey()), "当前分类下不可创建流程！");
            Assert.isTrue(paramsJSON.containsKey("ouIds") && !paramsJSON.getJSONArray("ouIds").isEmpty(), "BU不能为空！");
            //澳优逻辑：一个流程只能选择一个BU,同一个BU下流程名称不能相同
            Assert.isTrue(paramsJSON.getJSONArray("ouIds").size() == 1, "只能选择一个BU！");
            UserSessionBean user = getUserSessionBean();
            //非管理员判断数据权限
            if(!"Y".equals(user.getIsadmin())) {
                Integer ouId = getOrgId();
                List<Integer> ouIds = new ArrayList<Integer>();
                if(ouId != null) {
                    ouIds.add(ouId);
                }
                Assert.isTrue(bpmPermissionServer.hasPermission(paramsJSON.getString("key"), ouIds), "没权限操作！");
            }
            String modelId = paramsJSON.getString("modelId");
            String key = category.getProcessKey() + "." + paramsJSON.getJSONArray("ouIds").getIntValue(0);
            paramsJSON.put("key", key);
            //澳优逻辑：BU+流程名称不能重复
            for(int i = 0; i < paramsJSON.getJSONArray("ouIds").size(); i++) {
            	int ouId = paramsJSON.getJSONArray("ouIds").getIntValue(i);
            	List<ActBpmModelEntity_HI_RO> models = bpmModelServer.findByName(paramsJSON.getString("name"), ouId);
            	Assert.isTrue(models == null || models.isEmpty(), "流程名称已存在！");
            }
            Model modelData = bpmModelServer.copy(paramsJSON);
            JSONObject result = new JSONObject();
        	result.put("modelId", modelData.getId());
            result.put("key", modelData.getKey());
            result.put("name", modelData.getName());
        	return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "成功", 0, result).toString();
        } catch (IllegalArgumentException e) {
        	LOGGER.error(e.getMessage(), e);
        	return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        } catch (Exception e) {
        	LOGGER.error(e.getMessage(), e);
        	return SToolUtils.convertResultJSONObj(ERROR_STATUS, "保存失败！", 0, null).toString();
        }
    }


    /**
     * 根据ModelId部署流程
     * @author laoqunzhao 2018-04-28
     * @param params
     * {
     * modelId 模型ID
     * }
     */
	@Permission(menuCode = "LCSJ")
    @RequestMapping(method= RequestMethod.POST,value="deploy")
    public String deploy(@RequestParam String params) {
        try {
            JSONObject paramsJSON = super.parseObject(params);
            SaafToolUtils.validateJsonParms(paramsJSON, "modelId");
            String modelId = paramsJSON.getString("modelId");
            ActBpmModelEntity_HI_RO model = bpmModelServer.getById(modelId);
            Assert.notNull(model, "流程不存在！");
            UserSessionBean user = getUserSessionBean();
            //非管理员判断数据权限
            if(!"Y".equals(user.getIsadmin())) {
            	Integer ouId = getOrgId();
            	List<Integer> ouIds = new ArrayList<Integer>();
            	if(ouId != null) {
            		ouIds.add(ouId);
            	}
            	Assert.isTrue(bpmPermissionServer.hasPermission(model.getKey(), ouIds), "没权限操作！");
            }
            Deployment deployment = bpmModelServer.deploy(modelId);
            JSONObject deploymentJson = new JSONObject();
            deploymentJson.put("id", deployment.getId());
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "成功", 0, deploymentJson).toString();
        } catch (Exception e) {
        	LOGGER.error(e.getMessage(), e);
        	return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }
    
    /**
     * 根据ModelId查询流程及权限
     * @author laoqunzhao 2018-06-28
     * @param params JSONObject
     * modelId 模型ID
     */
    @RequestMapping(method= RequestMethod.POST,value="get")
    public String get(@RequestParam String params) {
        try {
            JSONObject paramsJSON = super.parseObject(params);
            SaafToolUtils.validateJsonParms(paramsJSON, "modelId");
            ActBpmModelEntity_HI_RO model = bpmModelServer.getById(paramsJSON.getString("modelId"));
            Assert.notNull(model, "流程不存在！");
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "成功", 0, model).toString();
        } catch (IllegalArgumentException e) {
        	LOGGER.error(e.getMessage(), e);
        	return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        } catch (Exception e) {
        	LOGGER.error(e.getMessage(), e);
        	return SToolUtils.convertResultJSONObj(ERROR_STATUS, "失败！", 0, null).toString();
        }
    }

   
    /**
     * 删除流程定义
     * @author laoqunzhao 2018-04-28
     * @param params JSONObject
     * modelId 流程模型ID
     * @return
     */
    @Permission(menuCode = "LCSJ")
    @RequestMapping(method= RequestMethod.POST,value="delete")
    public String delete(@RequestParam String params) {
       try {
           JSONObject paramsJSON = super.parseObject(params);
           SaafToolUtils.validateJsonParms(paramsJSON, "modelId");
           ActBpmModelEntity_HI_RO model = bpmModelServer.getById(paramsJSON.getString("modelId"));
           Assert.notNull(model, "流程不存在！");
           UserSessionBean user = getUserSessionBean();
           //非管理员判断数据权限
           if(!"Y".equals(user.getIsadmin())) {
        	    Integer ouId = getOrgId();
	           	List<Integer> ouIds = new ArrayList<Integer>();
	           	if(ouId != null) {
	           		ouIds.add(ouId);
	           	}
	           	Assert.isTrue(bpmPermissionServer.hasPermission(model.getKey(), ouIds), "没权限操作！");
           }
           Assert.isTrue(!bpmListServer.hasSubmited(model.getKey()), "流程已发起不可删除！");
    	   bpmModelServer.delete(paramsJSON.getString("modelId"));
    	   return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "成功", 0, null).toString();
       } catch (IllegalArgumentException e) {
       		LOGGER.error(e.getMessage(), e);
       		return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
       } catch (Exception e) {
       		LOGGER.error(e.getMessage(), e);
       		return SToolUtils.convertResultJSONObj(ERROR_STATUS, "删除失败！", 0, null).toString();
       }
    }
    
    /**
     * 获取流程用户任务节点
     * @author laoqunzhao 2018-04-28
     * @param params JSONObject
     * modelId 流程模型ID
     * processDefinitionId 流程定义ID，processDefinitionId不为空modlId可为空
     */
    @RequestMapping(method= RequestMethod.POST,value="findUserTasks")
    public String findUserTasks(@RequestParam String params, 
    		@RequestParam(required = false, defaultValue = "1") Integer pageIndex,
    		@RequestParam(required = false, defaultValue = "10") Integer pageRows) {
       try {
           JSONObject paramJSON = super.parseObject(params);
           String modelId = paramJSON.getString("modelId");
           String processDefinitionId = paramJSON.getString("processDefinitionId");
           Pagination<ActUserTaskEntity_RO> pagination = new Pagination<ActUserTaskEntity_RO>(pageIndex ,pageRows);
           List<UserTask> list = bpmModelServer.findUserTasks(modelId, processDefinitionId, true);
           List<ActUserTaskEntity_RO> actUserTasks = new ArrayList<ActUserTaskEntity_RO>();
		   if(list != null && !list.isEmpty()) {
				for(UserTask userTask: list) {
					ActUserTaskEntity_RO actUserTask = new ActUserTaskEntity_RO();
					actUserTask.setId(userTask.getId());
					actUserTask.setName(userTask.getName());
					actUserTasks.add(actUserTask);
				}
		   }
           pagination.setCount(actUserTasks.size());
           //不分页
           if(pageRows==null || pageRows==-1) {
               pagination.setData(actUserTasks);
           }else {//模拟分页
               if(actUserTasks.size()>((pageIndex-1)*pageRows)) {
                   List<ActUserTaskEntity_RO> curlist = actUserTasks.subList((pageIndex-1)*pageRows,
                           list.size()>pageIndex*pageRows?pageIndex*pageRows:list.size());
                   pagination.setData(curlist);
               }
           }
           JSONObject result = (JSONObject) JSONObject.toJSON(pagination);
           result.put(STATUS, SUCCESS_STATUS);
           result.put(MSG, "成功");
           return result.toString();
       }catch(Exception e) {
    	   LOGGER.error(e.getMessage(), e);
           return SToolUtils.convertResultJSONObj(ERROR_STATUS, "失败！", 0, null).toString();
       }
    }
    
    /**
     * 获取指定节点后的用户任务节点
     * @author laoqunzhao 2018-05-21
     * @param params JSONObject
     * modelId 流程模型ID
     * processDefinitionId 流程定义ID，processDefinitionId不为空modlId可为空
     * taskDefinitionId 任务定义ID
     */
    @RequestMapping(method= RequestMethod.POST,value="findFollowUserTasks")
    public String findFollowUserTasks(@RequestParam String params, 
    		@RequestParam(required = false, defaultValue = "1") Integer pageIndex,
    		@RequestParam(required = false, defaultValue = "10") Integer pageRows) {
       try {
           JSONObject paramJSON = super.parseObject(params);
           String modelId = paramJSON.getString("modelId");
           String processDefinitionId = paramJSON.getString("processDefinitionId");
           String taskDefinitionId = paramJSON.getString("taskDefinitionId");
           Pagination<ActUserTaskEntity_RO> pagination = new Pagination<ActUserTaskEntity_RO>(pageIndex ,pageRows);
           List<UserTask> list = bpmModelServer.findFollowUserTasks(modelId, processDefinitionId, taskDefinitionId);
           List<ActUserTaskEntity_RO> actUserTasks = new ArrayList<ActUserTaskEntity_RO>();
		   if(list != null && !list.isEmpty()) {
				for(UserTask userTask: list) {
					ActUserTaskEntity_RO actUserTask = new ActUserTaskEntity_RO();
					actUserTask.setId(userTask.getId());
					actUserTask.setName(userTask.getName());
					actUserTasks.add(actUserTask);
				}
		   }
           pagination.setCount(actUserTasks.size());
           //不分页
           if(pageRows==null || pageRows==-1) {
               pagination.setData(actUserTasks);
           }else {//模拟分页
               if(actUserTasks.size()>((pageIndex-1)*pageRows)) {
                   List<ActUserTaskEntity_RO> curlist = actUserTasks.subList((pageIndex-1)*pageRows,
                           list.size()>pageIndex*pageRows?pageIndex*pageRows:list.size());
                   pagination.setData(curlist);
               }
           }
           if(pagination.getData() == null){
               pagination.setData(new ArrayList<ActUserTaskEntity_RO>());
           }
           JSONObject result = (JSONObject) JSONObject.toJSON(pagination);
           result.put(STATUS, SUCCESS_STATUS);
           result.put(MSG, "成功");
           return result.toString();
       }catch(Exception e) {
    	   LOGGER.error(e.getMessage(), e);
           return SToolUtils.convertResultJSONObj(ERROR_STATUS, "失败", 0, null).toString();
       }
    }
    
    
    /**
     * 导出model对象为指定类型
     * @author laoqunzhao 2018-04-28
     * @param params JSONObject
     * modelId 模型ID
     * processDefinitionId 流程定义ID
     * type 导出文件类型(xml\image)
     */
    @RequestMapping(method= RequestMethod.GET,value="export")
    public void export(@RequestParam String params) {
        try {
            JSONObject paramsJSON = super.parseObject(params);
            SaafToolUtils.validateJsonParms(paramsJSON, "type");
            String modelId = paramsJSON.getString("modelId");
            String processDefinitionId = paramsJSON.getString("processDefinitionId");
            String type = paramsJSON.getString("type");
            if(StringUtils.isBlank(processDefinitionId)){
                SaafToolUtils.validateJsonParms(paramsJSON, "modelId");
            }
            BpmnModel bpmnModel = StringUtils.isNotBlank(modelId)? bpmModelServer.getBpmnModel(modelId): bpmProcessServer.getBpmnModel(processDefinitionId);
            Assert.notNull(bpmnModel, "流程未定义，无法导出！");
            String fileName = "";
            byte[] exportBytes = null;
            String mainProcessId = bpmnModel.getMainProcess().getId();
            if (type.equals("xml")) {
                Document doc = bpmModelServer.exportModel(modelId, processDefinitionId);
                fileName = mainProcessId + ".bpmn20.xml";
                response.setContentType("application/octet-stream");
                response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
                OutputFormat format = OutputFormat.createPrettyPrint();   //格式：有空格换行
                XMLWriter writer=new XMLWriter(response.getOutputStream(),format);//创建写出对象
                writer.write(doc);//写出Document对象
                writer.close();//关闭流

            } else {
                ProcessDiagramGenerator diagramGenerator = processEngineConfiguration.getProcessDiagramGenerator();
                InputStream ins = diagramGenerator.generateDiagram(bpmnModel, "png", processEngineConfiguration.getActivityFontName(),
                                    processEngineConfiguration.getLabelFontName(),processEngineConfiguration.getAnnotationFontName(), processEngineConfiguration.getClassLoader());
                exportBytes = IoUtil.readInputStream(ins,null);
                fileName = mainProcessId + ".png";
                ByteArrayInputStream in = new ByteArrayInputStream(exportBytes);
                IOUtils.copy(in, response.getOutputStream());
                response.setContentType("image/png");
            }
            response.flushBuffer();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    /**
     * 导入流程模型
     * @author laoqunzhao 2018-09-06
     * @param params
     * {
     * categoryId 分类ID
     * systemCode 系统编码
     * ouIds 事业部ID[]
     * fileId 文件上传ID
     * }
     */
    @Permission(menuCode = "LCSJ")
    @RequestMapping(method= RequestMethod.POST,value="import")
    public String importModel(@RequestParam String params) {
        try {
            JSONObject paramsJSON = super.parseObject(params);
            SaafToolUtils.validateJsonParms(paramsJSON, "categoryId", "systemCode", "ouIds", "fileId");
            VerifyUtil.verifyJSON(paramsJSON, "systemCode" ,true, 30, "系统编码");
            Assert.isTrue(paramsJSON.containsKey("ouIds") && !paramsJSON.getJSONArray("ouIds").isEmpty(), "BU不能为空！");
            //澳优逻辑：一个流程只能选择一个BU,同一个BU下流程名称不能相同
            Assert.isTrue(paramsJSON.getJSONArray("ouIds").size() == 1, "只能选择一个BU！");
            //非管理员判断数据权限
            UserSessionBean user = getUserSessionBean();
            if(!"Y".equals(user.getIsadmin())) {
                Integer ouId = getOrgId();
                Integer ouId_ = paramsJSON.getJSONArray("ouIds").getInteger(0);
                Assert.isTrue(ouId != null && ouId.equals(ouId_), "没权限操作！");
            }
            ActBpmCategoryEntity_HI category = bpmCategoryServer.get(paramsJSON.getInteger("categoryId"));
            Assert.notNull(category, "流程分类不存在！");
            Assert.isTrue(StringUtils.isNotBlank(category.getProcessKey()), "当前分类下不可导入流程！");
            // 创建saxReader对象
            SAXReader reader = new SAXReader();
            // 通过read方法读取一个文件 转换成Document对象

			BaseAttachmentEntity_HI attachment = baseAttachmentServer.findBaseAttachmentInfo(paramsJSON.getLong("fileId"));
            InputStream ins = fastdfsServer.getInputStream(attachment.getBucketName(),attachment.getPhyFileName());
            Assert.notNull(ins, "读取流程定义文件失败！");
            String key = category.getProcessKey() + "." + paramsJSON.getJSONArray("ouIds").getInteger(0);
            String xmlStr = null;
            try{
                Document document = reader.read(ins);
                Element root = document.getRootElement();
                Element processElement = root.element("process");
                processElement.remove(processElement.attribute("id"));
                processElement.addAttribute("id", key);
                xmlStr = document.asXML();
            }catch (Exception e){
                LOGGER.error(e.getMessage(), e);
                throw new IllegalArgumentException("流程文件解析失败！");
            }

            Model modelData = bpmModelServer.importModel(paramsJSON, xmlStr);
            JSONObject result = new JSONObject();
            result.put("modelId", modelData.getId());
            result.put("key", modelData.getKey());
            result.put("name", modelData.getName());
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "成功", 0, result).toString();
        } catch (IllegalArgumentException e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, "导入失败！", 0, null).toString();
        }
    }
    
    /**
     * 查询流程图GooFlow格式数据
     * @author laoqunzhao 2018-09-10
     * @param params{
     *   key 流程唯一标识
     * }
     */
    @RequestMapping(method= RequestMethod.POST,value="getGooflowData")
    public String getGooflowData(@RequestParam String params) {
        try {
            JSONObject paramJSON = super.parseObject(params);
            String key = paramJSON.getString("key");
            JSONObject result = bpmModelServer.getGooflowModelJSON(key);
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "成功", 0, result).toString();
        }catch(Exception e) {
    	   LOGGER.error(e.getMessage(), e);
           return SToolUtils.convertResultJSONObj(ERROR_STATUS, "失败", 0, null).toString();
       }
    }

	@Override
	public IBaseCommon<?> getBaseCommonServer() {
		return null;
	}

}
