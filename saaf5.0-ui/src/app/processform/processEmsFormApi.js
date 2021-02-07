/**
 * Created by Acer on 2018/8/20.
 */
/**
 * Created by Administrator on 2018/5/28.
 */
define(['webconfig'], function (webconfig) {

    return {
      // pickingApplyLineList: webconfig.url.baseServer + 'pickingApplyLineService/findPagination', //提货申请单行表
      // findModifyPickInfo: webconfig.url.baseServer + 'pickingApplyLineService/findModifyPickInfo',//查看变更新信息
      // pickingApplyHeader: webconfig.url.baseServer + 'pickingApplyHeaderService/findHeader', //提货申请头表详情
      // pickingApplyHeaderSave: webconfig.url.baseServer + 'pickingApplyHeaderService/saveOrUpdate', //生产单头表更新
      // findCustomerInfoInfo: webconfig.url.baseServer + 'customerInfoService/findCustomerInfoInfo',//查询客户头表
      // findSoPiLinesPiInfo: webconfig.url.baseServer + 'soPiLinesService/findSoPiLinesPiInfo',//引入PI信息 报价表体数据

        budgetBatchForm: webconfig.url.emsOralceServer + 'oracleCuxBcBudgetBatchHService/commitCuxBcBudgetBatch',  //费用批次提交
        budgetBatchSave: webconfig.url.emsOralceServer + 'oracleCuxBcBudgetBatchHService/saveCuxBcBudgetBatch',  //费用批次新增或者更新
        findPosition: webconfig.url.baseServer + 'baseOrgStructureService/findPositionByPersonId',  //根据人员查询职位
        findChannelByUserList: webconfig.url.emsSever + 'emsBaseEncumbranceService/findChannelByUserList',  //获取当前职责权限下的渠道类型
        findBatchHeadAndLine: webconfig.url.emsSever + 'cuxBcBudgetBatchHService/findCuxBcBudgetBatchHeadAndLine',  //查看批次数据
        batchDownload: webconfig.url.emsSever + 'cuxBcBudgetBatchHService/download',  //模版下载
       	batchUploadFile: webconfig.url.emsOralceServer + 'oracleCuxBcBudgetBatchHService/uploadFileToJsonObject',  //预算批次导入
       	
       	
       //费用申请
       findApplicationDetail: webconfig.url.emsSever + 'cuxBcEncumbranceHService/findOneOfCuxBcEncumbranceHList',  //查看费用申请
       findApplicationHApprove: webconfig.url.emsOralceServer + 'oracleCuxBcEncumbranceHService/chcekCuxBcEncumbranceHApprove',  //费用申请-审批验证及保存金额与类型
       feeApplicationSave: webconfig.url.emsOralceServer + 'oracleCuxBcEncumbranceHService/saveCuxBcEncumbranceH',  //费用申请保存
       feeApplicationForm: webconfig.url.emsOralceServer + 'oracleCuxBcEncumbranceHService/commitCuxBcEncumbranceH',  //费用申请提交
       
       
       //---------------hemingxing 2018年8月21日--------------------------
        saveOrUpdateCuxBcEncumbranceHApply: webconfig.url.emsSever + 'cuxBcEncumbranceHService/saveOrUpdateCuxBcEncumbranceHApply', //费用申请新增、修改

        saveCuxBcReimburse: webconfig.url.emsOralceServer + 'oracleCuxBcReimburseService/saveCuxBcReimburse', //费用报销新增、修改
        findReimburseDetails: webconfig.url.emsSever + 'cuxBcReimburseService/findReimburseDetails', //通过bcEncumbranceHId获取费用报销
        commitCuxBcReimburse: webconfig.url.emsOralceServer + 'oracleCuxBcReimburseService/commitCuxBcReimburse', //提交费用报销
        deleteReimburseL: webconfig.url.emsOralceServer + 'oracleCuxBcReimburseService/deleteReimburseL', //删除费用报销行

        uploadFile2JsonObject: webconfig.url.emsOralceServer + 'oracleCuxBcReimburseService/uploadFile2JsonObject', //导入费用报销并校验

        checkAndUpdate: webconfig.url.emsOralceServer + 'oracleCuxBcReimburseService/checkAndUpdate', //审批时校验表单数据
        downloadReimburse: webconfig.url.emsSever + 'cuxBcReimburseService/download', //费用报销模板

        ems_post_downReimbursePrint: webconfig.url.emsSever + 'cuxBcReimburseService/downReimburseWord', //费用报销打印
        ems_get_downReimbursePrint: webconfig.url.emsSever + 'cuxBcReimburseService/getDownReimburseWord',


        // /*EMS 获取费用科目控件API*/
        // findEncumbranceExpAccPagination: webconfig.url.emsSever + 'emsBaseEncumbranceService/findEncumbranceExpAccPagination',
        // expensePeriodList: webconfig.url.emsSever + 'emsBaseEncumbranceService/findExpensePeriodList',  //期间控件
        // findExpenseTypes: webconfig.url.emsSever + 'emsBaseEncumbranceService/findExpenseTypes',
        // //控件api
        // personList: webconfig.url.baseServer + 'baseOrgStructureService/findAccessPersonPagination',  //头人员列表
        // personLineList: webconfig.url.emsSever + 'emsBaseOrganizationService/findReimLPersonPagination',  //行人员列表
        // applyCost: webconfig.url.emsSever + 'emsBaseEncumbranceService/findEncumbrancePagination',  //费用申请控件
        // /*EMS 获取部门控件API*/
        // findBaseDeptInfoByUserType: webconfig.url.baseServer + 'baseOrgStructureService/findBaseDeptInfoByUserType',
        // /*EMS 获取行部门控件API*/
        // findBaseDeptInfoByParent: webconfig.url.baseServer + 'baseOrgStructureService/findBaseDeptInfoByParent',
        // /*EMS 获取头经销商控件API*/
        // findAccessCustomerHeadPagination: webconfig.url.emsSever + 'emsBaseOrganizationService/findAccessCustomerHeadPagination',
        // /*EMS 获取行经销商控件API*/
        // findAccessCustomerLinePagination: webconfig.url.emsSever +  'emsBaseOrganizationService/findAccessCustomerLinePagination',
        //
        // /*EMS 获取人员*/
        // findPersonPagination: webconfig.url.baseServer +  'baseOrgStructureService/findPersonPagination',
        // /*EMS 查询银行账号控件API*/
        // findBankInfoPagination: webconfig.url.emsSever + 'cuxBcReimburseService/findBankInfoPagination',
        // /*EMS 费用项目控件API*/
        // findExpenseItemPagination: webconfig.url.emsSever + 'cuxBcExpenseItemService/findExpenseItemPagination',

    }
})