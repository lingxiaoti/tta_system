package com.sie.watsons.base.pon.project.model.inter.server;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.util.SaafDateUtils;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.pon.project.model.entities.EquPonProductSpecsEntity_HI;
import com.sie.watsons.base.pon.project.model.entities.EquPonProjectAttachmentEntity_HI;
import com.sie.watsons.base.pon.project.model.entities.EquPonProjectInfoEntity_HI;
import com.sie.watsons.base.pon.project.model.entities.EquPonSupplierInvitationEntity_HI;
import com.sie.watsons.base.pon.project.model.entities.readonly.EquPonSupplierInvitationEntity_HI_RO;
import com.sie.watsons.base.pon.project.model.inter.IEquPonSupplierInvitation;
import com.sie.watsons.base.pon.quotation.model.entities.EquPonQuotationInfoEntity_HI;
import com.sie.watsons.base.pon.quotation.model.inter.IEquPonQuotationInfo;
import com.sie.watsons.base.pos.enums.CommonEum;
import com.sie.watsons.base.pos.supplierinfo.model.entities.EquPosSupplierContactsEntity_HI;
import com.sie.watsons.base.utils.EmailUtil;
import com.sie.watsons.base.utils.ResultUtils;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.*;
import java.util.stream.Collectors;

@Component("equPonSupplierInvitationServer")
public class EquPonSupplierInvitationServer extends BaseCommonServer<EquPonSupplierInvitationEntity_HI> implements IEquPonSupplierInvitation {
    private static final Logger LOGGER = LoggerFactory.getLogger(EquPonSupplierInvitationServer.class);

    @Autowired
    private ViewObject<EquPonSupplierInvitationEntity_HI> equPonSupplierInvitationDAO_HI;
    @Autowired
    private BaseViewObject<EquPonSupplierInvitationEntity_HI_RO> equPonSupplierInvitationEntity_HI_RO;
    @Autowired
    private IEquPonQuotationInfo iEquPonQuotationInfo;
    @Autowired
    private ViewObject<EquPonProjectInfoEntity_HI> ponProjectDao;
    @Autowired
    private ViewObject<EquPonProductSpecsEntity_HI> productSpecsDao;
    @Autowired
    private ViewObject<EquPonQuotationInfoEntity_HI> equPonQuotationInfoDao;
    @Autowired
    private ViewObject<EquPonProjectAttachmentEntity_HI> equPonProjectAttachmentDao;
    @Autowired
    private ViewObject<EquPosSupplierContactsEntity_HI> supplierContactsDao;

    public EquPonSupplierInvitationServer() {
        super();
    }

    /**
     * 报价管理-邀请供应商查询，分页查询
     *
     * @param queryParamJSON 参数：密级Entity中的字段
     * @param pageIndex
     * @param pageRows
     * @return
     */
    @Override
    public JSONObject findSupplierInvitation(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {

        StringBuffer sql = new StringBuffer(EquPonSupplierInvitationEntity_HI_RO.QUERY_SQL);
        Map<String, Object> map = new HashMap<>();
        SaafToolUtils.parperHbmParam(EquPonSupplierInvitationEntity_HI_RO.class, queryParamJSON, sql, map);
        sql.append(" order by t.invitation_id asc");
        Pagination<EquPonSupplierInvitationEntity_HI_RO> pagination = equPonSupplierInvitationEntity_HI_RO.findPagination(sql, map, pageIndex, pageRows);
        JSONObject result  = JSONObject.parseObject(JSONObject.toJSONString(pagination));
        List<String> incomingParam = new ArrayList<>();
        List<String> efferentParam = new ArrayList<>();
        List<String> typeParam = new ArrayList<>();
        incomingParam.add("supplierStatusName");
        incomingParam.add("factoryStatusName");
        efferentParam.add("supplierStatusName");
        efferentParam.add("factoryStatusName");
        typeParam.add("EQU_SUPPLIER_STATUS");
        typeParam.add("EQU_SUPPLIER_STATUS");
        JSONArray data = result.getJSONArray("data");
        data = ResultUtils.getLookUpValues( data , incomingParam, efferentParam, typeParam);
        result.put("data",data);
        return result;
    }

    /**
     * 报价管理-邀请供应商保存
     *
     * @param params 参数：密级Entity中的字段
     * @return
     */
    @Override
    public EquPonSupplierInvitationEntity_HI saveSupplierInvitation(JSONObject params) throws Exception {
        return this.saveOrUpdate(params);
    }

    /**
     * 报价管理-邀请供应商删除
     *
     * @param params 参数：密级Entity中的字段
     * @return
     */
    @Override
    public void deleteSupplierInvitation(JSONObject params) throws Exception {
        this.deleteById(params.getInteger("id"));
    }

    /**
     * 报价管理-退出供应商报价
     *
     * @param params 参数：密级Entity中的字段
     * @return
     */
    @Override
    public void quitSupplierInvitation(JSONObject params) throws Exception {
        Integer invitationId = params.getInteger("id");
        Integer projectId = params.getInteger("projectId");
        String reason = params.getString("reason");
        Integer userId = params.getInteger("varUserId");
        Integer supplierId = 0;
        List<EquPonSupplierInvitationEntity_HI> entityList = equPonSupplierInvitationDAO_HI.findByProperty("invitationId", invitationId);
        List<EquPonProjectInfoEntity_HI> projectList = ponProjectDao.findByProperty("projectId", projectId);
        List<EquPonProjectInfoEntity_HI> allVersionProjectList = ponProjectDao.findByProperty("sourceProjectNumber", projectList.get(0).getSourceProjectNumber());
        if (entityList.size() > 0) {
            EquPonSupplierInvitationEntity_HI entity = entityList.get(0);
            entity.setIsQuit("Y");
            entity.setReason(reason);
            entity.setOperatorUserId(userId);
            this.saveOrUpdate(entity);

            //更新供应商状态为已终止
            supplierId = entity.getSupplierId();
            if (supplierId > 0) {
                //遍历立项所有版本，终止相关联的报价单
                for (int i = 0; i < allVersionProjectList.size(); i++) {
                    EquPonProjectInfoEntity_HI projectEntity = allVersionProjectList.get(i);

                    //根据立项单据号，查询供应商报价行
                    Map queryMap = new HashMap();
                    queryMap.put("supplierId", supplierId);
                    queryMap.put("projectId", projectEntity.getProjectId());
                    List<EquPonQuotationInfoEntity_HI> quotationDetailList = equPonQuotationInfoDao.findByProperty(queryMap);
                    //校验报价单状态是否已完成
                    for (int j = 0; j < quotationDetailList.size(); j++) {
                        EquPonQuotationInfoEntity_HI quotationDetailEntity = quotationDetailList.get(j);
                        quotationDetailEntity.setDocStatus("BREAK");
                        quotationDetailEntity.setOperatorUserId(userId);
                        equPonQuotationInfoDao.saveEntity(quotationDetailEntity);
                    }
                }
            }
        }
    }

    @Override
    public JSONObject updateSendInvitation(JSONObject paramsJONS, Integer userId) throws Exception {
        // 无论单据修改与否同一个立项单据不能生成两次报价邀请,即发送报价时间不为空也发送报价邀请则抛出异常提示
        LOGGER.info("执行报价邀请，调用参数"+paramsJONS.toJSONString());
        EquPonProjectInfoEntity_HI projectEntity = ponProjectDao.getById(paramsJONS.getInteger("projectId"));
        Date sentDate = projectEntity.getSendQuotationInvitationDate();
        if (!ObjectUtils.isEmpty(sentDate)) {
            throw new IllegalArgumentException("该立项单据已经生成报价邀请单,请勿重复发起报价邀请");
        }
        List<EquPonSupplierInvitationEntity_HI_RO> list = paramsJONS.getJSONArray("supplierInvitationInfo").toJavaList(EquPonSupplierInvitationEntity_HI_RO.class);
        if (CollectionUtils.isEmpty(list)) {
            throw new IllegalArgumentException("邀请的供应商列表不能为空");
        }
        // 如果最高立项单据版本>01,部门为OEM，说明立项单据修改,触发报价单更新操作
        if (projectEntity.getProjectVersion().compareTo("01") > 0 && "0E".equals(projectEntity.getDeptCode())) {
            LOGGER.info("立项版本大于01时，更新调用参数"+projectEntity.toString());
            iEquPonQuotationInfo.updateQuotationForUpdateProject(projectEntity);
        }

        // 解析邀请供应商,过滤掉退出的供应商,并发送邮件
        list = list.stream().filter(e -> "N".equals(e.getIsQuit())).collect(Collectors.toList());
        try {
            // 10是配方类，20是非配方类
            String categoryType = projectEntity.getCategoryType();
			for(int i = 0; i < list.size(); i++){
				List<EquPonSupplierInvitationEntity_HI_RO> paramsList = new ArrayList();
				EquPonSupplierInvitationEntity_HI_RO supplierInvitationEntity = list.get(i);
				paramsList.add(supplierInvitationEntity);


                StringBuffer sql = new StringBuffer(EquPonSupplierInvitationEntity_HI_RO.QUERY_SUPPLIER_INVITATION_HIS);
                Map<String, Object> map = new HashMap<>();
                map.put("varProjectId",projectEntity.getProjectId());
                map.put("varSupplierId",supplierInvitationEntity.getSupplierId());
                List<EquPonSupplierInvitationEntity_HI_RO> pagination = equPonSupplierInvitationEntity_HI_RO.findList(sql, map);

				
				if (pagination.size() == 0) {
					// 如果是配方类，对应的品类是SC、PC、CC
					if ("10".equals(categoryType)) {
						// 发送邮件给供应商
						this.generateMailForQuotationInvitation1(paramsList, projectEntity, paramsJONS, false);
						// 发送邮件给leader
//						this.generateMailForQuotationInvitation1(paramsList, projectEntity, paramsJONS, true);
					}
					// 如果是非配方类，对应的品类是GM-Health
					if ("20".equals(categoryType)) {
						// 发送邮件给供应商
						this.generateMailForQuotationInvitation2(paramsList, projectEntity, paramsJONS, false);
						// 发送邮件给leader
//						this.generateMailForQuotationInvitation2(paramsList, projectEntity, paramsJONS, true);
					}
				}else{
					this.generateProjectChangeMailContent(paramsList, projectEntity, paramsJONS);
				}
			}
            if ("10".equals(categoryType)) {
                // 发送邮件给leader
                this.generateMailForQuotationInvitation1(null, projectEntity, paramsJONS, true);
            }
            if ("20".equals(categoryType)) {
                // 发送邮件给leader
                this.generateMailForQuotationInvitation2(null, projectEntity, paramsJONS, true);
            }
        } catch (Exception e) {
            LOGGER.info("发送邮件失败，请联系管理员");
        }
        // 更新立项单据的发送邀请时间
        projectEntity.setSendQuotationInvitationDate(new Date());
        projectEntity.setOperatorUserId(userId);
        ponProjectDao.update(projectEntity);
        // 更新报价邀请供应商visit_flag为Y
        List<EquPonSupplierInvitationEntity_HI> invitationEntityHiList = equPonSupplierInvitationDAO_HI.findByProperty("projectId", paramsJONS.getInteger("projectId"));
        if (CollectionUtils.isEmpty(invitationEntityHiList)){
            throw new Exception("根据立项id"+paramsJONS.getInteger("projectId")+"查询供应商邀请列表为空");
        }else{
            for (EquPonSupplierInvitationEntity_HI entityHi : invitationEntityHiList) {
                // 如果供应商没有退出则更新为Y
                if (!("Y".equals(entityHi.getIsQuit()))){
                    entityHi.setInviteFlag("Y");
                    entityHi.setOperatorUserId(userId);
                    equPonSupplierInvitationDAO_HI.update(entityHi);
                }
            }
        }
        //如果当前非01版本，更新当前版本以前的历史版本立项发送邀请时间，目的是使当前版本以前的历史版本立项不能再发送报价邀请
        if(!"01".equals(projectEntity.getProjectVersion())){
            Integer versionCount = Integer.parseInt(projectEntity.getProjectVersion());
            String projectSourceNumber = projectEntity.getSourceProjectNumber();
            for(int i = versionCount - 1; i >= 1; i--){
                String versonNumber = String.valueOf(i).length() == 1 ? "0" + i : String.valueOf(i);
                Map queryMap = new HashMap();
                queryMap.put("sourceProjectNumber",projectSourceNumber);
                queryMap.put("projectVersion",versonNumber);
                List<EquPonProjectInfoEntity_HI> projectList = ponProjectDao.findByProperty(queryMap);
                if(projectList.size() > 0){
                    EquPonProjectInfoEntity_HI projectObj = projectList.get(0);
                    projectObj.setSendQuotationInvitationDate(new Date());
                    projectObj.setOperatorUserId(userId);
                    ponProjectDao.update(projectObj);
                }
            }
        }

        return paramsJONS;
    }

    private void generateProjectChangeMailContent(List<EquPonSupplierInvitationEntity_HI_RO> list, EquPonProjectInfoEntity_HI projectEntity,JSONObject paramsJONS) {
        String parentProjectNumber = projectEntity.getParentProjectNumber();
        List<EquPonProjectInfoEntity_HI> parentProjectList = ponProjectDao.findByProperty("projectNumber", parentProjectNumber);

        EquPonProjectInfoEntity_HI parentProjectEntity = parentProjectList.get(0);
        Date parentQuotationDeadLine = parentProjectEntity.getQuotationDeadline();
        Date quotationDeadLine = projectEntity.getQuotationDeadline();

        Integer parentProjectId = parentProjectEntity.getProjectId();
        Integer projectId = projectEntity.getProjectId();
        Map queryMap = new HashMap();
        queryMap.put("projectId", projectId);
        queryMap.put("fileType", "NonPriceFile");
        queryMap.put("selectedFlag", "Y");
        List<EquPonProjectAttachmentEntity_HI> projectAttachmentList = equPonProjectAttachmentDao.findByProperty(queryMap);
        Map queryMap2 = new HashMap();
        queryMap2.put("projectId", parentProjectId);
        queryMap2.put("fileType", "NonPriceFile");
        queryMap2.put("selectedFlag", "Y");
        List<EquPonProjectAttachmentEntity_HI> parentProjectAttachmentList = equPonProjectAttachmentDao.findByProperty(queryMap2);

        String msg = "";
        for (int i = 0; i < projectAttachmentList.size(); i++) {
            EquPonProjectAttachmentEntity_HI projectAttachmentEntity = projectAttachmentList.get(i);
            for (int j = 0; j < parentProjectAttachmentList.size(); j++) {
                EquPonProjectAttachmentEntity_HI parentProjectAttachmentEntity = parentProjectAttachmentList.get(j);
                if (projectAttachmentEntity.getSourceId().intValue() == parentProjectAttachmentEntity.getAttachmentId()) {
                    if (projectAttachmentEntity.getFileId().intValue() != parentProjectAttachmentEntity.getFileId().intValue()) {
                        msg = msg + "非价格文件" + projectAttachmentEntity.getAttachmentName() + "已发生变更;" + "<br />";
                    }
                }
            }
        }
        String subject = "立项修改通知";
        if (!"".equals(msg) || parentQuotationDeadLine.getTime() != quotationDeadLine.getTime()) {
            for (int i = 0; i < list.size(); i++) {
                EquPonSupplierInvitationEntity_HI_RO supplierInvitationEntity = list.get(i);
                StringBuffer sb = new StringBuffer();
                sb.append("<p>");
                sb.append("尊敬的").append(supplierInvitationEntity.getContactName());
                sb.append("，<br /><br />");
                sb.append("您参与的竞价项目").append(projectEntity.getProjectName()).append("已作如下变更：<br />");
                if (parentQuotationDeadLine.getTime() != quotationDeadLine.getTime()) {
                    sb.append("报价截止时间调整为：").append(quotationDeadLine).append(";<br />");
                }
                if (!"".equals(msg)) {
                    sb.append(msg);
                }
                sb.append("<br />");
                sb.append("详情请登陆屈臣氏电子采购系统查阅。<br />");
                sb.append("<br />");
                sb.append("感谢您的合作。<br />");
                sb.append("<br />");
                sb.append("屈臣氏电子采购系统<br />");
                sb.append("<br />");
                sb.append("（本邮件由系统自动发送，请不要回复）");
                sb.append("</p>");
                sb.append("<p>");
                sb.append("<br />");
                sb.append("</p>");

                EmailUtil.sendMailFromWatsons(supplierInvitationEntity.getEmailAddress(), subject, sb.toString(),projectEntity.getDeptCode());
            }
        }
        // 给leader发送消息
        StringBuffer sb = new StringBuffer();
        sb.append("<p>");
        sb.append("尊敬的").append(paramsJONS.getString("userFullName"));
        sb.append("，<br /><br />");
        sb.append("您参与的竞价项目").append(projectEntity.getProjectName()).append("已作如下变更：<br />");
        if (parentQuotationDeadLine.getTime() != quotationDeadLine.getTime()) {
            sb.append("报价截止时间调整为：").append(quotationDeadLine).append(";<br />");
        }
        if (!"".equals(msg)) {
            sb.append(msg);
        }
        sb.append("<br />");
        sb.append("详情请登陆屈臣氏电子采购系统查阅。<br />");
        sb.append("<br />");
        sb.append("感谢您的合作。<br />");
        sb.append("<br />");
        sb.append("屈臣氏电子采购系统<br />");
        sb.append("<br />");
        sb.append("（本邮件由系统自动发送，请不要回复）");
        sb.append("</p>");
        sb.append("<p>");
        sb.append("<br />");
        sb.append("</p>");
        EmailUtil.sendMailFromWatsons(paramsJONS.getString("email"), subject, sb.toString(),projectEntity.getDeptCode());
    }

    /**
     * 发送报价邀请邮件，针对品类是品类是SC、PC、CC
     *
     * @return
     */
    private void generateMailForQuotationInvitation1(List<EquPonSupplierInvitationEntity_HI_RO> list, EquPonProjectInfoEntity_HI projectEntity, JSONObject paramsJONS, Boolean isLeader) {
        String subject = projectEntity.getProjectName() + "竞价邀请";
        if (isLeader) {
            String mailContent = this.quotationInvitationMailContent1(null, projectEntity, paramsJONS, true);
            //发送邮件
            EmailUtil.sendMailFromWatsons(paramsJONS.getString("email"), subject, mailContent,projectEntity.getDeptCode());
        } else {
            for (EquPonSupplierInvitationEntity_HI_RO entityHiRo : list) {
                String mailContent = this.quotationInvitationMailContent1(entityHiRo, projectEntity, paramsJONS, false);
                //发送邮件
                EmailUtil.sendMailFromWatsons(entityHiRo.getEmailAddress(), subject, mailContent,projectEntity.getDeptCode());
            }
        }
    }

    // 报价邀请邮件1内容
    private String quotationInvitationMailContent1(EquPonSupplierInvitationEntity_HI_RO entityHiRo, EquPonProjectInfoEntity_HI projectEntity, JSONObject paramsJONS, Boolean isLeader) {
        // 系列，上市年月，报价截止日期，产品数量
        List<EquPonProductSpecsEntity_HI> productList = productSpecsDao.findByProperty("projectId", projectEntity.getProjectId());
        Assert.notEmpty(productList, "根据立项id" + projectEntity.getProjectId() + "获取产品的数量为空");
        // 产品数量
        int productSize = productList.size();
//            String seriesName = projectEntity.getSeriesName() == null ? " ":projectEntity.getSeriesName();
        String seriesName = projectEntity.getProjectName();
        String quotationDeadlineStr = SaafDateUtils.convertDateToString(projectEntity.getQuotationDeadline(), SaafDateUtils.DEFAULT_PATTERN);
        String projectCycleFrom = null;
        if (projectEntity.getProjectCycleFrom() != null) {
            projectCycleFrom = SaafDateUtils.convertDateToString(projectEntity.getProjectCycleFrom(), "yyyy-MM");
        }
        StringBuffer sb = new StringBuffer();
        sb.append("<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>SC,PC,CC项目品类竞价邀请邮件正文</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<p>Dear ");
        if (isLeader) {
            sb.append(paramsJONS.getString("userFullName"));
        } else {
            sb.append(entityHiRo.getContactName());
        }
        sb.append(":</p>\n" +
                "\n" +
                "<p style=\"color:#FF0000;\">我司拟对\n" +
                "    <ins>")
                .append(seriesName)
                .append("系列</ins>\n" +
                        "    产品进行报价邀请,计划在\n" +
                        "    <ins>")
                .append(projectCycleFrom)
                .append("</ins>\n" +
                        "    上市.请在收到此报价邀请文件后,\n" +
                        "    及时登录到E-quotation系统上,若确认参与该项目报价,则点击\"参与报价\",若放弃\n" +
                        "    该项目报价,则点击\"放弃报价\",以作回应.\n" +
                        "</p>\n" +
                        "\n" +
                        "<p>产品数量:")
                .append(productSize)
                .append(" SKU</p>\n" +
                        "<p>报价截止日期:")
                .append(quotationDeadlineStr)
                .append("</p>\n" +
                        "<p style=\"color:#FF0000;\">投标文件:报价截止日期前上传至E-quotation系统中</p>\n" +
                        "<p style=\"color:#FF0000;\">投标样品:报价截止日期前送至屈臣氏,快递以屈臣氏收发室签收时间为准,亲自递送\n" +
                        "    以屈臣氏采购签收时间为准.</p>\n" +
                        "\n" +
                        "<p><strong>注意事项:</strong></p>\n" +
                        "<p>│报价邀请为一轮分两部分进行,第一部分为感官测试样品和非报价类文件,\n" +
                        "    第二部分为报价类文件.<span style=\"color:#FF0000;\">附件12和附件3.1-3.9、3.11需打印出来盖章,并在E-quotation系统\n" +
                        "    上扫描上传.</span>附件3.10、3.12及3.13只需熟读知悉即可.</p>\n" +
                        "<p>│第一部分需要提供的文件为附件12及附件3.1、3.4-3.11,务必按要求填写好,\n" +
                        "    <ins>请注意\n" +
                        "        不要包含任何与报价相关的信息,<span style=\"color:#FF0000;\">并注意不能修改附件文件名.</span></ins>\n" +
                        "</p>\n" +
                        "<p>1)请确保提供的感官测试样品均已按我司提供的报价邀请文件附件3.1《产品规格》\n" +
                        "    中的\"建议主要成分\"添加了相应的成分.</p>\n" +
                        "<p>2)请确保贵司提供的文件里面标注的产品规格都是严格按我司提供的3.1《产品规格》\n" +
                        "    中的容量进行标注.</p>\n" +
                        "<p>3)按我司要求免费提供每款两份样品(包材以及料体)进行包材质量和功能性评估.</p>\n" +
                        "<p>4)供应商需按附件3.7关于包材及原料选择的声明的附录《屈臣氏自有品牌产品原材\n" +
                        "    料/包材供应商信息明细》要求,每个单品就原料和包材信息填写一份明细.</p>\n" +
                        "<p>5)必须在报价截止日期前按要求提供文件及样品,文件包括(全部加盖公司公章):</p>\n" +
                        "<p>&nbsp;&nbsp;&nbsp;&nbsp;a)文件目录</p>\n" +
                        "<p>&nbsp;&nbsp;&nbsp;&nbsp;b)报价邀请文件</p>\n" +
                        "<p>&nbsp;&nbsp;&nbsp;&nbsp;c)附件 12及附件3.1、3.4-3.11</p>\n" +
                        "<p>&nbsp;&nbsp;&nbsp;&nbsp;d)产品执行标准及检测报告</p>\n" +
                        "<p>&nbsp;&nbsp;&nbsp;&nbsp;e)供应商认为需要的进一步的公司介绍(说明公司服务能力、客户群),承诺条件\n" +
                        "    或其他文件.</p>\n" +
                        "<p>|第二部分需要提供的文件为报价邀请文件附件3.2及3.3,务必根据附件3.1产品规格\n" +
                        "    的指引,估价并认真填写.请注意\n" +
                        "    <ins>第一部分和第二部分文件同时提交.</ins>\n" +
                        "</p>\n" +
                        "<p style=\"color:#FF0000;\">1)请注意在大额报价中的成本计算采用两种报价(填写于3.2报价确认函),即不含\n" +
                        "    合同费用不含税的报价和含2%免费货不含税的报价.</p>\n" +
                        "<p style=\"color:#FF0000;\">│批量导入文件数据后,必须上传对应的盖有公章的纸质版文件的扫描件.</p>\n" +
                        "<p>│请在制作出感官测试样品当日开始进行配方稳定性测试,同时开始相应的兼容性测试,\n" +
                        "    并按照附件3.11稳定性测试报告样式填写稳定性测试数据,盖章后上传扫描版到报价\n" +
                        "    系统上.</p>\n" +
                        "<p>│此系列产品需做TRA测试等等-具体的测试明细请仔细查看报价邀请文件中的明细.</p>\n" +
                        "<p>│产品配方及质地要求请仔细查看附件3.1产品规格的说明</p>\n" +
                        "<p>│除了和唇部有接触的产品,其他产品配方中必须添加苦味剂,否则TRA不能过.\n" +
                        "    (如果添加苦味剂导致影响产品体系和产品功效的,请提出.)</p>\n" +
                        "<p>│提供样品时可以在箱内附上贵司包装设计,如被选上,可以酌情加分.</p>\n" +
                        "<p>│<span style=\"color:#FF0000;\">贵司提供的感官测试样板的配方需符合附件3.1产品规格中的功效要求,如果贵司获选,\n" +
                        "    需要针对这些功效宣称做第三方功效测试.</span></p>\n" +
                        "<p>│供应商对有关本次报价邀请的相关事宜有任何疑问,可以邮件书面形式提出,提问截止\n" +
                        "    时间为")
                .append(quotationDeadlineStr)
                .append("前.</p>\n" +
                        "<p>│此项目做上海一个城市的消费者感官测试,样板的准备指引会另外发邮件通知.</p>\n" +
                        "\n" +
                        "<p><strong>1.请对本邮件所有内容保密,未经许可不得向第三方泄露.</strong></p>\n" +
                        "<p><strong><span style=\"color:#FF0000;\">2.请妥善保管E-quotation系统的账号及密码,保证报价文件扫描件与原件一致,承诺\n" +
                        "    通过该账号提交报价文件的行为均视为其贵司的行为且对其具约束力.</span></strong></p>\n" +
                        "</body>\n" +
                        "</html>");
        return sb.toString();
    }

    // 报价邀请邮件2内容
    private String quotationInvitationMailContent2(EquPonSupplierInvitationEntity_HI_RO entityHiRo, EquPonProjectInfoEntity_HI projectEntity, JSONObject paramsJONS, Boolean isLeader) {

        // 系列，上市年月，报价截止日期，产品数量
        List<EquPonProductSpecsEntity_HI> productList = productSpecsDao.findByProperty("projectId", projectEntity.getProjectId());
        Assert.notEmpty(productList, "根据立项id" + projectEntity.getProjectId() + "获取产品的数量为空");
        // 产品数量
        int productSize = productList.size();
//            String seriesName = projectEntity.getSeriesName() == null ? " ":projectEntity.getSeriesName();
        String seriesName = projectEntity.getProjectName();
        String quotationDeadlineStr = SaafDateUtils.convertDateToString(projectEntity.getQuotationDeadline(), SaafDateUtils.DEFAULT_PATTERN);
        String projectCycleFrom = null;
        if (projectEntity.getProjectCycleFrom() != null) {
            projectCycleFrom = SaafDateUtils.convertDateToString(projectEntity.getProjectCycleFrom(), "yyyy-MM");
        }

        StringBuffer sb = new StringBuffer();
        sb.append("<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>GM,Health项目品类竞价邀请邮件正文</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<p>Dear ");
        if (isLeader) {
            sb.append(paramsJONS.getString("userFullName"));
        } else {
            sb.append(entityHiRo.getContactName());
        }
        sb.append(":</p>\n" +
                "\n" +
                "<p style=\"color:#FF0000;\">我司拟对\n" +
                "    <ins>")
                .append(seriesName)
                .append("系列</ins>\n" +
                        "    产品进行报价邀请,计划在\n" +
                        "    <ins>")
                .append(projectCycleFrom)
                .append("</ins>\n" +
                        "    上市.请在收到此报价邀请文件后,\n" +
                        "    及时登录到E-quotation系统上,若确认参与该项目报价,则点击\"参与报价\",若放弃\n" +
                        "    该项目报价,则点击\"放弃报价\",以作回应.\n" +
                        "</p>\n" +
                        "\n" +
                        "<p>产品数量:")
                .append(productSize)
                .append(" SKU</p>\n" +
                        "<p>报价截止日期:")
                .append(quotationDeadlineStr)
                .append("</p>\n" +
                        "<p style=\"color:#FF0000;\">投标文件:报价截止日期前上传至E-quotation系统中</p>\n" +
                        "<p style=\"color:#FF0000;\">投标样品:报价截止日期前送至屈臣氏,快递以屈臣氏收发室签收时间为准,亲自递送\n" +
                        "    以屈臣氏采购签收时间为准.</p>\n" +
                        "\n" +
                        "<p><strong>注意事项:</strong></p>\n" +
                        "<p>│报价邀请为一轮分两部分进行,第一部分为感官测试样品和非报价类文件,\n" +
                        "    第二部分为报价类文件.<span style=\"color:#FF0000;\">附件12和附件3.1-3.5、3.7、3.8需打印出来盖章,并在E-quotation系统\n" +
                        "    上扫描上传.</span>附件3.9只需熟读知悉即可.</p>\n" +
                        "<p>│第一部分需要提供的文件为附件12及附件3.1、3.3、3.5、3.7、3.8,务必按要求填写好,\n" +
                        "    <ins>请注意\n" +
                        "        不要包含任何与报价相关的信息,<span style=\"color:#FF0000;\">并注意不能修改附件文件名.</span></ins>\n" +
                        "</p>\n" +
                        "<p>1)请确保提供的感官测试样品均已按我司提供的报价邀请文件附件3.1《产品规格》\n" +
                        "    中的\"建议主要成分\"添加了相应的成分.</p>\n" +
                        "<p>2)请确保贵司提供的文件里面标注的产品规格都是严格按我司提供的3.1《产品规格》\n" +
                        "    中的容量进行标注.</p>\n" +
                        "<p>3)必须在报价截止日期前按要求提供文件及样品,文件包括(全部加盖公司公章):</p>\n" +
                        "<p>&nbsp;&nbsp;&nbsp;&nbsp;a)文件目录</p>\n" +
                        "<p>&nbsp;&nbsp;&nbsp;&nbsp;b)报价邀请文件</p>\n" +
                        "<p>&nbsp;&nbsp;&nbsp;&nbsp;c)附件 12及附件3.1、3.3、3.5、3.7、3.8</p>\n" +
                        "<p>&nbsp;&nbsp;&nbsp;&nbsp;d)供应商认为需要的进一步的公司介绍(说明公司服务能力、客户群),承诺条件\n" +
                        "    或其他文件.</p>\n" +
                        "<p>|第二部分需要提供的文件为报价邀请文件附件3.2及3.4,务必根据附件3.1产品规格\n" +
                        "    的指引,估价并认真填写.请注意\n" +
                        "    <ins>第一部分和第二部分文件同时提交.</ins>\n" +
                        "</p>\n" +
                        "<p style=\"color:#FF0000;\">1)请注意在大额报价中的成本计算采用两种报价(填写于3.2报价确认函),即不含\n" +
                        "    合同费用不含税的报价和含2%免费货不含税的报价.</p>\n" +
                        "<p style=\"color:#FF0000;\">│批量导入文件数据后,必须上传对应的盖有公章的纸质版文件的扫描件.</p>\n" +
                        "<p>│提供样品时可以在箱内附上贵司包装设计,如被选上,可以酌情加分.</p>\n" +
                        "<p>│供应商对有关本次报价邀请的相关事宜有任何疑问,可以邮件书面形式提出,提问截止\n" +
                        "    时间为")
                .append(quotationDeadlineStr)
                .append("前.</p>\n" +
                        "<p>│此项目做上海一个城市的消费者感官测试,样板的准备指引会另外发邮件通知.</p>\n" +
                        "\n" +
                        "<p><strong>1.请对本邮件所有内容保密,未经许可不得向第三方泄露.</strong></p>\n" +
                        "<p><strong><span style=\"color:#FF0000;\">2.请妥善保管E-quotation系统的账号及密码,保证报价文件扫描件与原件一致,承诺\n" +
                        "    通过该账号提交报价文件的行为均视为其贵司的行为且对其具约束力.</span></strong></p>\n" +
                        "</body>\n" +
                        "</html>");
        return sb.toString();
    }

    /**
     * 发送报价邀请邮件，针对品类是品类是GM-Health
     *
     * @return
     */
    private void generateMailForQuotationInvitation2(List<EquPonSupplierInvitationEntity_HI_RO> list, EquPonProjectInfoEntity_HI projectEntity, JSONObject paramsJONS, Boolean isLeader) throws Exception {
        String subject = projectEntity.getProjectName() + "竞价邀请";
        if (isLeader) {
            String mailContent = this.quotationInvitationMailContent2(null, projectEntity, paramsJONS, true);
            //发送邮件
            EmailUtil.sendMailFromWatsons(paramsJONS.getString("email"), subject, mailContent,projectEntity.getDeptCode());
        } else {
            for (EquPonSupplierInvitationEntity_HI_RO entityHiRo : list) {
                String mailContent = this.quotationInvitationMailContent2(entityHiRo, projectEntity, paramsJONS, false);
                //发送邮件
                EmailUtil.sendMailFromWatsons(entityHiRo.getEmailAddress(), subject, mailContent,projectEntity.getDeptCode());
            }
        }
    }

    /**
     * 封装发送邮件内容 it
     */
//    private void generateMailContent(List<EquPonSupplierInvitationEntity_HI_RO> list) throws Exception {
//        for (EquPonSupplierInvitationEntity_HI_RO entityHi : list) {
//            // 组装发送邮件的内容
//            StringBuffer sb = new StringBuffer();
//            sb.append("<!DOCTYPE html>\n" +
//                    "<html lang=\"en\">\n" +
//                    "<head>\n" +
//                    "    <meta charset=\"UTF-8\">\n" +
//                    "    <title>Title</title>\n" +
//                    "</head>\n" +
//                    "<body>\n" +
//                    " <p>\n" +
//                    "    Dear ")
//                    .append(entityHi.getContactName())
//                    .append("</p>\n" +
//                            "<p>\n" +
//                            "    因公司业务发展,现需要对屈臣氏\n" +
//                            "    <ins style=\"color:#FF0000;\"><strong>")
//                    .append(entityHi.getSupplierName())
//                    .append("</strong></ins>\n" +
//                            "    项目邀请贵公司进行报价.报价要求见附件\"\n" +
//                            "    <ins style=\"color:#FF0000;\"><strong>")
//                    .append(entityHi.getSupplierName()).append("</strong></ins>\n" +
//                    "    -报价邀请\"(内附详细需求文档),\n" +
//                    "    必须按要求提供详细资料及证明文件,否则视为不满足对应项目.\n" +
//                    "</p>\n" +
//                    "<p>下面内容请一定要提供:</p>\n" +
//                    "<p><strong>1.除报价邀请文件需提交的\"营业执照、组织机构代码证、国、地税证复印件\" 还请提供:</strong></p>\n" +
//                    "<p><strong>&nbsp;&nbsp;&nbsp;&nbsp;a:税务登记证;</strong></p>\n" +
//                    "<p><strong>&nbsp;&nbsp;&nbsp;&nbsp;b:公司资质证明,公司介绍等;</strong></p>\n" +
//                    "<p><strong>&nbsp;&nbsp;&nbsp;&nbsp;c:发票要求:增值税发票及对应税率,如果不能提供,请提供特别说明;</strong></p>\n" +
//                    "<p><strong>&nbsp;&nbsp;&nbsp;&nbsp;d:公司简介.</strong></p>\n" +
//                    "<p><strong>2.所有报价文件应加盖公司公章（特别是报价页面）并扫描上传到系统指定的附件中.</strong></p>\n" +
//                    "<p><strong>3.报价资料和解决方案加盖骑缝章</strong></p>\n" +
//                    "<p><strong>4.所有需要盖章的都请一定要使用你们公司公章,其他章将视为无效.</strong></p>\n" +
//                    "<p>注意:</p>\n" +
//                    "<p>&nbsp;&nbsp;&nbsp;&nbsp;1.请不要在此邮件直接回复电子报价及报价相关资料;</p>\n" +
//                    "<p>&nbsp;&nbsp;&nbsp;&nbsp;2.报价资料和解决方案需要分开提供,在系统上分开上传到对应的报价资料和非报价资料中.</p>\n" +
//                    "<p>请在收到报价邀请文件后,用对应的帐号和密码（项目协调人会另行提供）登录到系统上,如果确认参与该项目报价,则点击\"参与报价\"按钮,如果放弃该项目报价邀请,则点击\"不参与报价\",以作回应</p>\n" +
//                    "<p><a style=\"color: #3aa2eb;\" href=\"")
////                    .append("http://http://wtccn-vm-sdst/portal/login.aspx")
//                    .append("https://supplierportal.watsonsvip.com.cn/portal/#/entrance")
//                    .append("\">进入系统,请点击该链接</a></p>\n" +
//                            "<p><strong>重要提示！请妥善保管该系统的帐号及写码,保证报价文件扫描件与原件一致,承诺通过该账号提交报价文件的行为均视为其贵司的行为且对其具有约束力.</strong></p>\n" +
//                            "</body>\n" +
//                            "</html>");
//            //发送邮件
//            EmailUtil.sendMailFromWatsons(entityHi.getEmailAddress(), "供应商邀请邮件详情", sb.toString());
//        }
//    }

    /**
     * 封装发送邮件内容
     */
//    private JSONObject generateMailContent1(EquPonSupplierInvitationEntity_HI_RO entityHi) throws Exception {
//        // 组装发送邮件的内容
//        JSONObject jsonObject = new JSONObject();
//        StringBuffer sb = new StringBuffer();
//        sb.append("<!DOCTYPE html>\n" +
//                "<html lang=\"en\">\n" +
//                "<head>\n" +
//                "    <meta charset=\"UTF-8\">\n" +
//                "    <title>Title</title>\n" +
//                "</head>\n" +
//                "<body>\n" +
//                " <p>\n" +
//                "    Dear Sir/ Madam:")
//                .append(entityHi.getContactName())
//                .append("</p>\n" +
//                        "<p>\n" +
//                        "    因公司业务发展,现需要对屈臣氏\n" +
//                        "    <ins style=\"color:#FF0000;\"><strong>")
//                .append(entityHi.getSupplierName())
//                .append("</strong></ins>\n" +
//                        "    项目邀请贵公司进行报价.报价要求见附件\"\n" +
//                        "    <ins style=\"color:#FF0000;\"><strong>")
//                .append(entityHi.getSupplierName()).append("</strong></ins>\n" +
//                "    -报价邀请\"(内附详细需求文档),\n" +
//                "    必须按要求提供详细资料及证明文件,否则视为不满足对应项目.\n" +
//                "</p>\n" +
//                "<p>下面内容请一定要提供:</p>\n" +
//                "<p><strong>1.除报价邀请文件需提交的\"营业执照、组织机构代码证、国、地税证复印件\" 还请提供:</strong></p>\n" +
//                "<p><strong>&nbsp;&nbsp;&nbsp;&nbsp;a:税务登记证;</strong></p>\n" +
//                "<p><strong>&nbsp;&nbsp;&nbsp;&nbsp;b:公司资质证明,公司介绍等;</strong></p>\n" +
//                "<p><strong>&nbsp;&nbsp;&nbsp;&nbsp;c:发票要求:增值税发票及对应税率,如果不能提供,请提供特别说明;</strong></p>\n" +
//                "<p><strong>&nbsp;&nbsp;&nbsp;&nbsp;d:公司简介.</strong></p>\n" +
//                "<p><strong>2.所有报价文件应加盖公司公章（特别是报价页面）并扫描上传到系统指定的附件中.</strong></p>\n" +
//                "<p><strong>3.报价资料和解决方案加盖骑缝章</strong></p>\n" +
//                "<p><strong>4.所有需要盖章的都请一定要使用你们公司公章,其他章将视为无效.</strong></p>\n" +
//                "<p>注意:</p>\n" +
//                "<p>&nbsp;&nbsp;&nbsp;&nbsp;1.请不要在此邮件直接回复电子报价及报价相关资料;</p>\n" +
//                "<p>&nbsp;&nbsp;&nbsp;&nbsp;2.报价资料和解决方案需要分开提供,在系统上分开上传到对应的报价资料和非报价资料中.</p>\n" +
//                "<p>请在收到报价邀请文件后,用对应的帐号和密码（项目协调人会另行提供）登录到系统上,如果确认参与该项目报价,则点击\"参与报价\"按钮,如果放弃该项目报价邀请,则点击\"不参与报价\",以作回应</p>\n" +
//                "<p><a style=\"color: #3aa2eb;\" href=\"")
////                .append("http://http://wtccn-vm-sdst/portal/login.aspx")
//                .append("https://supplierportal.watsonsvip.com.cn/portal/#/entrance")
//                .append("\">进入系统,请点击该链接</a></p>\n" +
//                        "<p><strong>重要提示！请妥善保管该系统的帐号及写码,保证报价文件扫描件与原件一致,承诺通过该账号提交报价文件的行为均视为其贵司的行为且对其具有约束力.</strong></p>\n" +
//                        "</body>\n" +
//                        "</html>");
//        //消息内容
//        jsonObject.put("message", sb.toString());
//        //消息主题
//        jsonObject.put("msgSubject", "发送邮件");
//        //请求id和请求时间
//        jsonObject.put("requestId", UUID.randomUUID().toString().replace("-", ""));
//        jsonObject.put("requestTime", new Date());
//        //业务类型
//        jsonObject.put("bizType", "EMAIL");
//        //发送消息功能的消息用户名
//        jsonObject.put("userName", "OAUser261");
//        //发送消息功能的消息密码
//        jsonObject.put("password", "OAUser261");
//        //配置id
//        jsonObject.put("msgCfgId", 20);
//        //实时消息字段(Y/N)
//        jsonObject.put("synchro", "Y");
//        // 发送邮箱 todo 先写死
//        jsonObject.put("sendTo", "1561763825@qq.com");
////        String msgUrl = paramJSON.getString("msgUrl");//消息url
////        Integer userOrgId = paramJSON.getInteger("orgId");//消息url
//        return jsonObject;
//    }

    @Override
    public JSONObject findRejectSupplierInvitation(JSONObject paramsJONS, Integer pageIndex, Integer pageRows) {
        // 通过userId从供应商联系人中查询供应商和联系人id,通过供应商id和联系人id查询是否有立项单据
        Integer userId = paramsJONS.getInteger("userId");
        List<EquPosSupplierContactsEntity_HI> contactList = supplierContactsDao.findByProperty("userId", userId);
        Assert.notEmpty(contactList, "当前用户在供应商联系人表中未维护,请联系管理员");
        Integer supplierId = contactList.get(0).getSupplierId();
        paramsJONS.put("supplierId", supplierId);
        StringBuffer sql = new StringBuffer(EquPonSupplierInvitationEntity_HI_RO.QUERY_REJECT_PROJECT);
        Map<String, Object> map = new HashMap<>(4);
        SaafToolUtils.parperParam(paramsJONS, "pi.project_number", "projectNumber", sql, map, "like");
        SaafToolUtils.parperParam(paramsJONS, "pi.project_name", "projectName", sql, map, "like");
        SaafToolUtils.parperParam(paramsJONS, "t.supplier_id", "supplierId", sql, map, "=");
        sql.append(" order by t.invitation_id asc");
        Pagination<EquPonSupplierInvitationEntity_HI_RO> pagination = equPonSupplierInvitationEntity_HI_RO.findPagination(sql, map, pageIndex, pageRows);
        return JSONObject.parseObject(JSONObject.toJSONString(pagination));
    }
}
