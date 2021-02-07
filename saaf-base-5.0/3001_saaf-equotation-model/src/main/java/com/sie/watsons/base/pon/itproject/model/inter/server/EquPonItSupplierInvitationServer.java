package com.sie.watsons.base.pon.itproject.model.inter.server;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.pon.itproject.model.entities.EquPonItProjectInfoEntity_HI;
import com.sie.watsons.base.pon.itproject.model.entities.EquPonItSupplierInvitationEntity_HI;
import com.sie.watsons.base.pon.itproject.model.entities.readonly.EquPonItSupplierInvitationEntity_HI_RO;
import com.sie.watsons.base.pon.itproject.model.inter.IEquPonItSupplierInvitation;
import com.sie.watsons.base.pon.itquotation.model.entities.EquPonQuotationInfoItEntity_HI;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import com.sie.watsons.base.pon.itquotation.model.inter.IEquPonQuotationInfoIt;
import com.sie.watsons.base.pos.enums.CommonEum;
import com.sie.watsons.base.pos.supplierinfo.model.entities.EquPosSupplierContactsEntity_HI;
import com.sie.watsons.base.utils.EmailUtil;
import com.yhg.hibernate.core.dao.ViewObject;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.Map;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
@Component("equPonItSupplierInvitationServer")
public class EquPonItSupplierInvitationServer extends BaseCommonServer<EquPonItSupplierInvitationEntity_HI> implements IEquPonItSupplierInvitation{
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPonItSupplierInvitationServer.class);

	@Autowired
	private ViewObject<EquPonItSupplierInvitationEntity_HI> equPonItSupplierInvitationDAO_HI;
    @Autowired
    private BaseViewObject<EquPonItSupplierInvitationEntity_HI_RO> equPonItSupplierInvitationDAO_HI_RO;
	@Autowired
    private ViewObject<EquPonItProjectInfoEntity_HI> ponItProjectInfoDao;
	@Autowired
    private ViewObject<EquPonQuotationInfoItEntity_HI> equPonQuotationInfoDao;
	@Autowired
    private IEquPonQuotationInfoIt equPonQuotationInfoItService;
    @Autowired
    private ViewObject<EquPosSupplierContactsEntity_HI> supplierContactsDao;

	@Autowired
	private BaseViewObject<EquPonItSupplierInvitationEntity_HI_RO> equPonItSupplierInvitationEntity_HI_RO;

	public EquPonItSupplierInvitationServer() {
		super();
	}

	/**
	 * IT报价管理-邀请供应商查询，分页查询
	 *
	 * @param queryParamJSON 参数：密级Entity中的字段
	 * @param pageIndex
	 * @param pageRows
	 * @return
	 */
	@Override
	public JSONObject findItSupplierInvitation(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {

		StringBuffer sql = new StringBuffer(EquPonItSupplierInvitationEntity_HI_RO.QUERY_SQL);
		Map<String, Object> map = new HashMap<>();
		SaafToolUtils.parperHbmParam(EquPonItSupplierInvitationEntity_HI_RO.class, queryParamJSON, sql, map);
		sql.append(" order by t.invitation_id asc");
		Pagination<EquPonItSupplierInvitationEntity_HI_RO> pagination = equPonItSupplierInvitationEntity_HI_RO.findPagination(sql, map, pageIndex, pageRows);
		return JSONObject.parseObject(JSONObject.toJSONString(pagination));
	}

	/**
	 * IT报价管理-邀请供应商删除
	 *
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public void deleteItSupplierInvitation(JSONObject params) throws Exception {
		this.deleteById(params.getInteger("id"));
	}

//    @Override
//    public JSONObject btnSendInvitationIt(JSONObject paramsJONS, Integer userId) throws Exception {
//        // 无论单据修改与否同一个立项单据不能生成两次报价邀请,即发送报价时间不为空也发送报价邀请则抛出异常提示
//        EquPonItProjectInfoEntity_HI projectEntity = ponItProjectInfoDao.getById(paramsJONS.getInteger("projectId"));
//        Date sentDate = projectEntity.getSendQuotationInvitationDate();
//        if (!ObjectUtils.isEmpty(sentDate)) {
//            throw new IllegalArgumentException("该立项单据已经生成报价邀请单,请勿重复发起报价邀请");
//        }
//        List<EquPonItSupplierInvitationEntity_HI_RO> list = paramsJONS.getJSONArray("supplierInvitationInfoIt").toJavaList(EquPonItSupplierInvitationEntity_HI_RO.class);
//        if (CollectionUtils.isEmpty(list)) {
//            throw new IllegalArgumentException("邀请的供应商列表不能为空");
//        }
//        // 如果最高立项单据版本>01,部门为IT，说明立项单据修改,触发报价单更新操作
//        if(projectEntity.getProjectVersion().compareTo("01") > 0 && CommonEum.IT.getValue().equals(projectEntity.getDeptCode())){
//            equPonQuotationInfoItService.updateQuotationForUpdateItProject(projectEntity);
//        }
//
//        // 解析邀请供应商,过滤掉退出的供应商,并发送邮件
//        list = list.stream().filter(e -> "N".equals(e.getIsQuit())).collect(Collectors.toList());
//        try {
//            this.generateMailContent(list);
//        } catch (Exception e) {
//            LOGGER.info("发送邮件失败，请联系管理员");
//        }
//        // 更新立项单据的发送邀请时间
//        projectEntity.setSendQuotationInvitationDate(new Date());
//        projectEntity.setOperatorUserId(userId);
//        ponItProjectInfoDao.update(projectEntity);
//        return paramsJONS;
//    }
//
//    /**
//     * 封装发送邮件内容 it
//     */
//    private void generateMailContent(List<EquPonItSupplierInvitationEntity_HI_RO> list) throws Exception {
//        for (EquPonItSupplierInvitationEntity_HI_RO entityHi : list) {
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
//                    .append("http://124.172.191.124:8080/equotation/")
//                    .append("\">进入系统,请点击该链接</a></p>\n" +
//                            "<p><strong>重要提示！请妥善保管该系统的帐号及写码,保证报价文件扫描件与原件一致,承诺通过该账号提交报价文件的行为均视为其贵司的行为且对其具有约束力.</strong></p>\n" +
//                            "</body>\n" +
//                            "</html>");
//            //发送邮件
//            EmailUtil.sendMailFromWatsons(entityHi.getEmailAddress(),"供应商邀请邮件详情",sb.toString());
//        }
//    }
    @Override
    public JSONObject btnSendInvitationIt(JSONObject paramsJONS, Integer userId) throws Exception {
        // 无论单据修改与否同一个立项单据不能生成两次报价邀请,即发送报价时间不为空也发送报价邀请则抛出异常提示
        EquPonItProjectInfoEntity_HI projectEntity = ponItProjectInfoDao.getById(paramsJONS.getInteger("projectId"));
        Date sentDate = projectEntity.getSendQuotationInvitationDate();
        if (!ObjectUtils.isEmpty(sentDate)) {
            throw new IllegalArgumentException("该立项单据已经生成报价邀请单,请勿重复发起报价邀请");
        }
        List<EquPonItSupplierInvitationEntity_HI_RO> list = paramsJONS.getJSONArray("supplierInvitationInfo").toJavaList(EquPonItSupplierInvitationEntity_HI_RO.class);
        if (CollectionUtils.isEmpty(list)) {
            throw new IllegalArgumentException("邀请的供应商列表不能为空");
        }
        // 如果最高立项单据版本>01,部门为IT，说明立项单据修改,触发报价单更新操作
        if(projectEntity.getProjectVersion().compareTo("01") > 0 && !"0E".equals(projectEntity.getDeptCode())){
            equPonQuotationInfoItService.updateQuotationForUpdateItProject(projectEntity);
        }

        // 解析邀请供应商,过滤掉退出的供应商,并发送邮件
        list = list.stream().filter(e -> "N".equals(e.getIsQuit())).collect(Collectors.toList());
        try {
            this.generateMailContent(list,projectEntity);
        } catch (Exception e) {
            LOGGER.info("发送邮件失败，请联系管理员");
        }
        // 更新立项单据的发送邀请时间
        projectEntity.setSendQuotationInvitationDate(new Date());
        projectEntity.setOperatorUserId(userId);
        ponItProjectInfoDao.update(projectEntity);

        // 更新报价邀请供应商visit_flag为Y
        List<EquPonItSupplierInvitationEntity_HI> invitationEntityHiList = equPonItSupplierInvitationDAO_HI.findByProperty("projectId", paramsJONS.getInteger("projectId"));
        if (CollectionUtils.isEmpty(invitationEntityHiList)){
            throw new Exception("根据立项id"+paramsJONS.getInteger("projectId")+"查询供应商邀请列表为空");
        }else{
            for (EquPonItSupplierInvitationEntity_HI entityHi : invitationEntityHiList) {
                // 如果供应商没有退出则更新为Y
                if (!("Y".equals(entityHi.getIsQuit()))){
                    entityHi.setInviteFlag("Y");
                    entityHi.setOperatorUserId(userId);
                    equPonItSupplierInvitationDAO_HI.update(entityHi);
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
                List<EquPonItProjectInfoEntity_HI> projectList = ponItProjectInfoDao.findByProperty(queryMap);
                if(projectList.size() > 0){
                    EquPonItProjectInfoEntity_HI projectObj = projectList.get(0);
                    projectObj.setSendQuotationInvitationDate(new Date());
                    projectObj.setOperatorUserId(userId);
                    ponItProjectInfoDao.update(projectObj);
                }
            }
        }

        return paramsJONS;
    }

    /**
     * 封装发送邮件内容 it
     */
    private void generateMailContent(List<EquPonItSupplierInvitationEntity_HI_RO> list,EquPonItProjectInfoEntity_HI projectEntity) throws Exception {
        for (EquPonItSupplierInvitationEntity_HI_RO entityHi : list) {
            // 组装发送邮件的内容
            StringBuffer sb = new StringBuffer();
            sb.append("<!DOCTYPE html>\n" +
                    "<html lang=\"en\">\n" +
                    "<head>\n" +
                    "    <meta charset=\"UTF-8\">\n" +
                    "    <title>Title</title>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    " <p>\n" +
                    "    Dear Sir/ Madam:")
                    .append("</p>\n" +
                            "<p>\n" +
                            "    因公司业务发展,现需要对屈臣氏\n" +
                            "    <ins style=\"color:#FF0000;\"><strong>")
                    .append(projectEntity.getProjectName())
                    .append("</strong></ins>\n" +
                            "    项目邀请贵公司进行报价.报价要求见请登录系统查看,\"\n" +
//                            "    <ins style=\"color:#FF0000;\"><strong>")
//                    .append(projectEntity.getProjectName()).append("</strong></ins>\n" +
//                    "    -报价邀请\"(内附详细需求文档),\n" +
                    "必须按要求提供详细资料及证明文件,否则视为不满足对应项目.\n" +
                    "</p>\n" +
                    "<p>下面内容请一定要提供:</p>\n" +
                    "<p><strong>1.除报价邀请文件需提交的\"营业执照、组织机构代码证、国、地税证复印件\" 还请提供:</strong></p>\n" +
                    "<p><strong>&nbsp;&nbsp;&nbsp;&nbsp;a:税务登记证;</strong></p>\n" +
                    "<p><strong>&nbsp;&nbsp;&nbsp;&nbsp;b:公司资质证明,公司介绍等;</strong></p>\n" +
                    "<p><strong>&nbsp;&nbsp;&nbsp;&nbsp;c:发票要求:增值税发票及对应税率,如果不能提供,请提供特别说明;</strong></p>\n" +
                    "<p><strong>&nbsp;&nbsp;&nbsp;&nbsp;d:公司简介.</strong></p>\n" +
                    "<p><strong>2.所有报价文件应加盖公司公章（特别是报价页面）并扫描上传到系统指定的附件中.</strong></p>\n" +
                    "<p><strong>3.报价资料和解决方案加盖骑缝章</strong></p>\n" +
                    "<p><strong>4.所有需要盖章的都请一定要使用你们公司公章,其他章将视为无效.</strong></p>\n" +
                    "<p>注意:</p>\n" +
                    "<p>&nbsp;&nbsp;&nbsp;&nbsp;1.请不要在此邮件直接回复电子报价及报价相关资料;</p>\n" +
                    "<p>&nbsp;&nbsp;&nbsp;&nbsp;2.报价资料和解决方案需要分开提供,在系统上分开上传到对应的报价资料和非报价资料中.</p>\n" +
                    "<p>请在收到报价邀请邮件后,用对应的帐号和密码（项目协调人会另行提供）登录到系统上,如果确认参与该项目报价,则点击\"参与报价\"按钮,如果放弃该项目报价邀请,则点击\"不参与报价\",以作回应</p>\n" +
                    "<p><a style=\"color: #3aa2eb;\" href=\"")
                    .append("https://supplierportal.watsonsvip.com.cn/portal/#/entrance")
                    .append("\">进入系统,请点击该链接</a></p>\n" +
                            "<p><strong>重要提示！请妥善保管该系统的帐号及密码,保证报价文件扫描件与原件一致,承诺通过该账号提交报价文件的行为均视为其贵司的行为且对其具有约束力.</strong></p>\n" +
                            "</body>\n" +
                            "</html>");
            //发送邮件
            EmailUtil.sendMailFromWatsons(entityHi.getEmailAddress(),"屈臣氏<" + projectEntity.getProjectName() + ">项目报价邀请",sb.toString(),projectEntity.getDeptCode());
        }
    }

    @Override
    public JSONObject findRejectSupplierInvitationIt(JSONObject paramsJONS, Integer pageIndex, Integer pageRows) {
        // 通过userId从供应商联系人中查询供应商和联系人id,通过供应商id和联系人id查询是否有立项单据
        Integer userId = paramsJONS.getInteger("userId");
        List<EquPosSupplierContactsEntity_HI> contactList = supplierContactsDao.findByProperty("userId", userId);
        Assert.notEmpty(contactList, "当前用户在供应商联系人表中未维护,请联系管理员");
        Integer supplierId = contactList.get(0).getSupplierId();
        paramsJONS.put("supplierId", supplierId);
        StringBuffer sql = new StringBuffer(EquPonItSupplierInvitationEntity_HI_RO.QUERY_REJECT_PROJECT_IT);
        Map<String, Object> map = new HashMap<>(4);
        SaafToolUtils.parperParam(paramsJONS, "pi.project_number", "projectNumber", sql, map, "like");
        SaafToolUtils.parperParam(paramsJONS, "pi.project_name", "projectName", sql, map, "like");
        SaafToolUtils.parperParam(paramsJONS, "t.supplier_id", "supplierId", sql, map, "=");
        sql.append(" order by t.invitation_id asc");
        Pagination<EquPonItSupplierInvitationEntity_HI_RO> pagination = equPonItSupplierInvitationDAO_HI_RO.findPagination(sql, map, pageIndex, pageRows);
        return JSONObject.parseObject(JSONObject.toJSONString(pagination));
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
        List<EquPonItSupplierInvitationEntity_HI> entityList = equPonItSupplierInvitationDAO_HI.findByProperty("invitationId", invitationId);
        List<EquPonItProjectInfoEntity_HI> projectList = ponItProjectInfoDao.findByProperty("projectId", projectId);
        List<EquPonItProjectInfoEntity_HI> allVersionProjectList = ponItProjectInfoDao.findByProperty("sourceProjectNumber", projectList.get(0).getSourceProjectNumber());
        if (entityList.size() > 0) {
            EquPonItSupplierInvitationEntity_HI entity = entityList.get(0);
            entity.setIsQuit("Y");
            entity.setReason(reason);
            entity.setOperatorUserId(userId);
            this.saveOrUpdate(entity);

            //更新供应商状态为已终止
            supplierId = entity.getSupplierId();
            if (supplierId > 0) {
                //遍历立项所有版本，终止相关联的报价单
                for (int i = 0; i < allVersionProjectList.size(); i++) {
                    EquPonItProjectInfoEntity_HI projectEntity = allVersionProjectList.get(i);

                    //根据立项单据号，查询供应商报价行
                    Map queryMap = new HashMap();
                    queryMap.put("supplierId", supplierId);
                    queryMap.put("projectId", projectEntity.getProjectId());
                    List<EquPonQuotationInfoItEntity_HI> quotationDetailList = equPonQuotationInfoDao.findByProperty(queryMap);
                    //校验报价单状态是否已完成
                    for (int j = 0; j < quotationDetailList.size(); j++) {
                        EquPonQuotationInfoItEntity_HI quotationDetailEntity = quotationDetailList.get(j);
                        quotationDetailEntity.setDocStatus("BREAK");
                        quotationDetailEntity.setOperatorUserId(userId);
                        equPonQuotationInfoDao.saveEntity(quotationDetailEntity);
                    }
                }
            }
        }
    }
}
