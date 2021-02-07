/**
 * Created by Acer on 2018/8/20.
 */
/**
 * Created by Administrator on 2018/5/28.
 */
define(['webconfig'], function (webconfig) {

    return {
        //保存固定资产采购申请单
        saveOrUpdateAssetApply: webconfig.url.oaServer + 'oaAssetApplyService/saveOrUpdateAssetApply',
        //更新固定资产采购申请单
        updateAssetApply: webconfig.url.oaServer + 'oaAssetApplyService/updateAssetApply',
        //固定资产采购申请单详情
        findAssetApplyDetailById: webconfig.url.oaServer + 'oaAssetApplyService/findAssetApplyDetailById',
        //删除固定资产采购申请单明细信息
        deleteAssetApplyItem: webconfig.url.oaServer + 'oaAssetApplyItemService/deleteAssetApplyItem',

        //固定资产验收单详情
        findAssetCheckDetailById: webconfig.url.oaServer + 'oaAssetCheckService/findAssetCheckDetailById',
        //固定资产验收单保存更新
        saveOrUpdateAssetCheck: webconfig.url.oaServer + 'oaAssetCheckService/saveOrUpdateAssetCheck',

        //固定资产报废单保存
        saveOrUpdateScrapApply: webconfig.url.oaServer + 'oaAssetScrapService/saveOrUpdateScrapApply',
        //固定资产报废单更新
        updateScrapApply: webconfig.url.oaServer + 'oaAssetScrapService/updateScrapApply',
        ////固定资产报废单详情
        findAssetScrapDetailById: webconfig.url.oaServer + 'oaAssetScrapService/findAssetScrapDetailById',
        //固定资产报废单更新
        //updateScrapApply: webconfig.url.oaServer + 'oaAssetScrapService/updateScrapApply',
        

        //固定资产调剂单详情
        findAssetAdjustDetailById: webconfig.url.oaServer + 'oaAssetAdjustService/findAssetAdjustDetailById',
        //固定资产调剂单保存
        saveOrUpdateAdjustApply: webconfig.url.oaServer + 'oaAssetAdjustService/saveOrUpdateAdjustApply',
        //固定资产调剂单更新
        updateAdjustApply: webconfig.url.oaServer + 'oaAssetAdjustService/updateAdjustApply',
        //删除固定资产调剂单明细信息
        deleteAssetAdjustItem: webconfig.url.oaServer + 'oaAssetAdjustItemService/deleteAssetAdjustItem',

        //固定资产报修单单详情
        findAssetMaintenanceDetailById: webconfig.url.oaServer + 'oaAssetMaintenanceService/findAssetMaintenanceDetailById',
        //固定资产报修单保存
        saveOrUpdateMaintenanceApply: webconfig.url.oaServer + 'oaAssetMaintenanceService/saveOrUpdateMaintenanceApply',
        //固定资产报修单更新
        updateMaintenanceApply: webconfig.url.oaServer + 'oaAssetMaintenanceService/updateMaintenanceApply',
        //删除固定资产报修单明细信息
        deleteAssetMaintenanceItem: webconfig.url.oaServer + 'oaAssetMaintenanceItemService/deleteAssetMaintenanceItem',


        //办公用品领用列表
        findOfficeSplHPagination: webconfig.url.oaServer + 'oaAdmOfficeSplHService/findOfficeSplHPagination',
        //办公用品领用详情
        findOfficeSplHDetailById: webconfig.url.oaServer + 'oaAdmOfficeSplHService/findOfficeSplHDetailById',
        //办公用品礼品列表
        findOfficeAdmGiftPagination: webconfig.url.oaServer + 'oaAdmGiftService/findAdmGiftPagination',
        //办公用品新增
        saveUpdateOffice: webconfig.url.oaServer + 'oaAdmOfficeSplHService/saveOrUpdateOfficeSplH',
        //办公用品更新
        updateOfficeSplH: webconfig.url.oaServer + 'oaAdmOfficeSplHService/updateOfficeSplH',
        //办公用品详情
        findOfficeDetail: webconfig.url.oaServer + 'oaAdmOfficeSplHService/findOfficeSplHDetailById',
        //办公用品礼品列表
        findOfficeAdmGif: webconfig.url.oaServer + 'oaAdmGiftService/findAdmGiftPagination',
        //办公用品礼品新增
        officeGifUpdate: webconfig.url.oaServer + 'oaAdmGiftService/saveOrUpdateAdmGift',
        //办公用品礼品失效
        officeAdmGifInvalid: webconfig.url.oaServer + 'oaAdmGiftService/updateAdmGiftInvalid',
        //办公用品领用
        officeStatus: webconfig.url.oaServer + 'oaAdmOfficeSplHService/updateOfficeSplStatus',


        //礼品列表
        findGiftApplyData: webconfig.url.oaServer + 'oaGiftApplyService/findGiftApplyPagination',
        //礼品保存
        saveGiftApply: webconfig.url.oaServer + 'oaGiftApplyService/saveOrUpdateGiftApply',
        //礼品更新
        updateGiftApply: webconfig.url.oaServer + 'oaGiftApplyService/updateGiftApply',
        //礼品详情查询
        findGiftApplyDetailById: webconfig.url.oaServer + 'oaGiftApplyService/findGiftApplyDetailById',
        //礼品领用
        updateGiftApplyCollarStatus: webconfig.url.oaServer + 'oaGiftApplyService/updateGiftApplyCollarStatus',
        //礼品领用 保存
        giftSaveOrUpdateGiftApply: webconfig.url.oaServer + 'oaGiftApplyService/saveOrUpdateGiftApply',


        //盖章申请
        findSealApplyPagination: webconfig.url.oaServer + 'oaSealApplyService/findSealApplyPagination',
        //盖章保存
        saveSealUpdateSealApply: webconfig.url.oaServer + 'oaSealApplyService/saveOrUpdateSealApply',
        //盖章更新
        updateSealApply: webconfig.url.oaServer + 'oaSealApplyService/updateSealApply',
        //盖章详情
        saveFindSealApplyDetailById: webconfig.url.oaServer + 'oaSealApplyService/findSealApplyDetailById',

        //借支申请
        findDebitBorrowApplyPagination: webconfig.url.oaServer + 'oaBorrowApplyService/findBorrowApplyPagination',
        //借支保存
        debitSaveOrUpdateBorrowApply: webconfig.url.oaServer + 'oaBorrowApplyService/saveOrUpdateBorrowApply',
        //借支更新
        updateBorrowApply: webconfig.url.oaServer + 'oaBorrowApplyService/updateBorrowApply',
        //借支详情
        findBorrowApplyDetailById: webconfig.url.oaServer + 'oaBorrowApplyService/findBorrowApplyDetailById',

        //差旅列表查询
        findApTrvPagination: webconfig.url.oaServer + 'oaApTrvHeadService/findApTrvPagination',
        //差旅报销单保存
        travelSaveOrUpdateApTrv: webconfig.url.oaServer + 'oaApTrvHeadService/saveOrUpdateApTrv',
        //差旅报销单更新
        updateApTrv: webconfig.url.oaServer + 'oaApTrvHeadService/updateApTrv',
        //差旅报销单详情
        findApTrvDetailById: webconfig.url.oaServer + 'oaApTrvHeadService/findApTrvDetailById',

        //经销商转款列表查询
        findApDetrvPagination: webconfig.url.oaServer + 'oaApDetrvHeadService/findApDetrvPagination',
        //经销商转款详情
        findApDetrvDetailById: webconfig.url.oaServer + 'oaApDetrvHeadService/findApDetrvDetailById',
        //经销商转款保存
        saveOrUpdateApDetrv: webconfig.url.oaServer + 'oaApDetrvHeadService/saveOrUpdateApDetrv',
        //经销商转款更新
        updateApDetrv: webconfig.url.oaServer + 'oaApDetrvHeadService/updateApDetrv',
        //经销商信息接口
        findCustomerPagination: webconfig.url.baseServer + 'baseCustomerService/findCustomerPagination',
        //经销商SKU
        ProductInfoLovByResp: webconfig.url.baseServer + 'baseProductInfoService/findProductInfo',

        //退款申请列表查询
        findApRefundPagination: webconfig.url.oaServer + 'oaApRefundService/findApRefundPagination',
        //退款申请详情
        findApRefundDetailById: webconfig.url.oaServer + 'oaApRefundService/findApRefundDetailById',
        //退款申请保存
        saveOrUpdateApRefund: webconfig.url.oaServer + 'oaApRefundService/saveOrUpdateApRefund',
        //退款申请更新
        updateApRefund: webconfig.url.oaServer + 'oaApRefundService/updateApRefund',

        //银行间资金列表页
        findApTransferPagination: webconfig.url.oaServer + 'oaApTransferService/findApTransferPagination',
        //银行间资金详情
        findApTransferDetailById: webconfig.url.oaServer + 'oaApTransferService/findApTransferDetailById',
        //银行间资金划转保存
        saveOrUpdateApTransfer: webconfig.url.oaServer + 'oaApTransferService/saveOrUpdateApTransfer',
        //银行间资金划转更新
        updateApTransfer: webconfig.url.oaServer + 'oaApTransferService/updateApTransfer',
        //银行帐号查询
        findApTransferBankAccount: webconfig.url.oaServer + 'oaApTransferService/findApTransferBankAccount',

        //经销商编码
        findAccessCustomerPagination: webconfig.url.baseServer + 'baseOrgStructureService/findAccessCustomerPagination',
        //经销商编码
        findVendorPagination: webconfig.url.baseServer + '/baseVendorsService/findVendorPagination',
        // 流程抄送  查询可用的节点，在驳回或重新提交时选择节点进行跳转。
        processFindTaskNodes: webconfig.url.processServer + 'bpmHistoryService/findTaskNodes',

        //会议室使用情况
        findMeetingRoomInfoUsage  : webconfig.url.oaServer + 'oaMeetingRoomInfoService/findMeetingRoomInfoUsage',
        //会议室管理
        //会议室设备列表
        findMeetingRoomInfoPagination  : webconfig.url.oaServer + 'oaMeetingRoomInfoService/findMeetingRoomInfoPagination',
        //会议室设备列表
        findMeetingFacilityInfoPagination  : webconfig.url.oaServer + 'oaMeetingFacilityInfoService/findMeetingFacilityInfoPagination',
        //会议室详情
        findMeetingRoomInfoDetailById  : webconfig.url.oaServer + 'oaMeetingRoomInfoService/findMeetingRoomInfoDetailById',
        //会议室保存更新
        saveOrUpdateMeetingRoomInfo  : webconfig.url.oaServer + 'oaMeetingRoomInfoService/saveOrUpdateMeetingRoomInfo',
        //会议室失效
        updateMeetingRoomInfoInvalid  : webconfig.url.oaServer + 'oaMeetingRoomInfoService/updateMeetingRoomInfoInvalid',

        //会议管理
        //1.1会议申请列表查询
        findMeetingApplyPagination  : webconfig.url.oaServer + 'oaMeetingApplyService/findMeetingApplyPagination',
        //1.2会议申请详情
        findMeetingApplyDetailById  : webconfig.url.oaServer + 'oaMeetingApplyService/findMeetingApplyDetailById',
        //1.3会议申请保存
        saveOrUpdateMeetingApply  : webconfig.url.oaServer + 'oaMeetingApplyService/saveOrUpdateMeetingApply',
        //1.3会议申请更新
        updateMeetingApply  : webconfig.url.oaServer + 'oaMeetingApplyService/updateMeetingApply',

        //请假申请列表
        findAttendTimeoffPagination: webconfig.url.oaServer + 'oaAttendTimeoffService/findAttendTimeoffPagination',
        //请假申请详情
        findAttendTimeoffDetailById: webconfig.url.oaServer + 'oaAttendTimeoffService/findAttendTimeoffDetailById',
        //请假保存
        saveOrUpdateAttendTimeoff: webconfig.url.oaServer + 'oaAttendTimeoffService/saveOrUpdateAttendTimeoff',
        //请假更新
        updateAttendTimeoff: webconfig.url.oaServer + 'oaAttendTimeoffService/updateAttendTimeoff',
        //请假节余
        findAttendTimeoffExpriedNumType: webconfig.url.oaServer + 'oaAttendTimeoffService/findAttendTimeoffExpriedNumType',

        //加班申请列表
        findWorkOverTimePagination: webconfig.url.oaServer + 'oaAttendTimeoffService/findWorkOverTimePagination',
        //加班申请详情
        findWorkOverTimeDetailById: webconfig.url.oaServer + 'oaAttendTimeoffService/findWorkOverTimeDetailById',
        //加班保存
        saveOrUpdateWorkOverTime: webconfig.url.oaServer + 'oaAttendTimeoffService/saveOrUpdateWorkOverTime',
        //加班更新
        updateWorkOverTime: webconfig.url.oaServer + 'oaAttendTimeoffService/updateWorkOverTime',
        //加班时长计算和加班类型
        findWorkOverTimeType: webconfig.url.oaServer + 'oaAttendTimeoffService/findWorkOverTimeType',
        //计算请假天数
        findAttendTimeoffType: webconfig.url.oaServer + 'oaAttendTimeoffService/findAttendTimeoffType',
        //假期结余列表查询
        findAttendTimeoffinfoPagination: webconfig.url.oaServer + 'oaAttendTimeoffinfoService/findAttendTimeoffinfoPagination',
        //合同管理 新增
        saveFinAgreement: webconfig.url.oaServer + 'oaFinAgreementService/saveFinAgreement',
        //合同详情
        findFinAgreementInfoById: webconfig.url.oaServer + 'oaFinAgreementService/findFinAgreementInfoById',
        //合同修改
        updateFinAgreement: webconfig.url.oaServer + 'oaFinAgreementService/updateFinAgreement',
        //合同终止
        updateFinAgreementExcuteStatus: webconfig.url.oaServer + 'oaFinAgreementService/updateFinAgreementExcuteStatus',
        //合同变更
        updateChangeFinAgreement: webconfig.url.oaServer + 'oaFinAgreementService/updateChangeFinAgreement',
        //合同历史版本列表查询
        findHisFinAgreementPagination: webconfig.url.oaServer + 'oaFinAgreementService/findHisFinAgreementPagination',
        //合同历史版本详情查询
        findHisFinAgreementInfo: webconfig.url.oaServer + 'oaFinAgreementService/findHisFinAgreementInfo',
        //主合同编号查询
        findMainFinAgreement: webconfig.url.oaServer + 'oaFinAgreementService/findMainFinAgreement',
        //合同付款申请导入查询
        findFinAgreementLinePagination: webconfig.url.oaServer + 'oaFinAgreementService/findFinAgreementLinePagination',
        //合同付款申请导入查询 更新操作
        updateFinAgreementLine: webconfig.url.oaServer + 'oaFinAgreementService/updateFinAgreementLine',
        //合同费用导入操作
        // commitBatchCuxBcEncumbranceH: webconfig.url.emsOralceServer + 'oracleCuxBcEncumbranceHService/commitBatchCuxBcEncumbranceH',
        commitCuxBcEncumbrance: webconfig.url.emsOralceServer + 'oracleCuxBcEncumbranceHService/commitCuxBcEncumbrance',
        //合同付款申请
        saveCommitAndPassCuxBcEncumbrance: webconfig.url.emsOralceServer + 'oracleCuxBcEncumbranceHService/saveCommitAndPassCuxBcEncumbrance',




        // 最后50条回复
        processHistoricOpinions: webconfig.url.processServer + 'bpmHistoryService/findHistoricOpinions',
        findPersonCusBankPagination: webconfig.url.baseServer + 'baseOrgStructureService/findPersonCusBankPagination',//查询员工供应商银行账号信息
        // 内部员工
        processFindInternalUsers: webconfig.url.baseServer + '/baseUsersService/findInternalUsers',
        findAllInfoOnlyOnePosition: webconfig.url.baseServer + '/baseOrgStructureService/findAllInfoOnlyOnePosition',
        /* 组织架构 */ 
        findPositionByPerson: webconfig.url.baseServer + 'baseOrgStructureService/findPositionPagination',// 组织架构--根据人员查询职位
        findUserInfoByPositionId: webconfig.url.baseServer + 'baseOrgStructureService/findUserInfoByPositionId',//根据职位查询人员
        orgFindDeptInfo: webconfig.url.baseServer + 'baseOrgStructureService/findDeptInfo',//查询部门信息（分页）
        

        findPaginationPersonInfo: webconfig.url.baseServer + 'baseOrgStructureService/findPaginationPersonInfo', //查询部门信息（分页）
        findCuxBcFinCostcenterList: webconfig.url.emsSever + 'cuxBcExpenseItemService/findCuxBcFinCostcenterList', //成本中心
        //会议室申请参会人员接口
        //findAllInfoOnlyOnePosition: webconfig.url.baseServer + 'baseOrgStructureService/findAllInfoOnlyOnePosition',
        //车间实习申请新增-实习职位接口更换
        findBasePositionNewPagination: webconfig.url.baseServer + 'basePositionNewService/findBasePositionNewPagination',
        findPersonInfo: webconfig.url.baseServer + 'baseOrgStructureService/findPersonInfo',//根据人员名称模糊查询人员信息或者根据人员ID精准查询人员信息
        findBaseJobPagination: webconfig.url.baseServer + 'baseJobService/findBaseJobPagination',//查询职务信息
        findAllInfoOnlyOnePosition: webconfig.url.baseServer + 'baseOrgStructureService/findAllInfoOnlyOnePosition', //查询职位信息
        /* lijiayao start */

        /* 福利模块 start */
        //生育福利列表
        findOaHrBirthPagination: webconfig.url.oaServer + 'oaHrBirthService/findOaHrBirthPagination',
        //生育福利报表
        oaHrBirthService_findOaHrBirthReportPagination: webconfig.url.oaServer + 'oaHrBirthService/findOaHrBirthReportPagination',
        //生育福利保存
        saveOrUpdateOaHrBirth: webconfig.url.oaServer + 'oaHrBirthService/saveOrUpdateOaHrBirth',
        //生育福利更新
        updateOaHrBirth: webconfig.url.oaServer + 'oaHrBirthService/updateOaHrBirth',
        //生育福利详情查询
        findOaHrBirthDetailById: webconfig.url.oaServer + 'oaHrBirthService/findOaHrBirthDetailById',
        //生育福利产品SKU
        findPaginationHrBirth: webconfig.url.baseServer + 'baseProductInfoService/findPagination',

        //住房补贴列表
        findOaHrHSubsidyPagination: webconfig.url.oaServer + 'oaHrHSubsidyService/findOaHrHSubsidyPagination',
        //住房补贴保存
        saveOrUpdateOaHrHSubsidy: webconfig.url.oaServer + 'oaHrHSubsidyService/saveOrUpdateOaHrHSubsidy',
        //住房补贴更新
        updateOaHrHSubsidy: webconfig.url.oaServer + 'oaHrHSubsidyService/updateOaHrHSubsidy',
        //住房补贴详情查询
        findOaHrHSubsidyDetailById: webconfig.url.oaServer + 'oaHrHSubsidyService/findOaHrHSubsidyDetailById',

        //关怀申请列表
        findOaHrCarePagination: webconfig.url.oaServer + 'oaHrCareService/findOaHrCarePagination',
        //关怀申请保存
        saveOrUpdateOaHrCare: webconfig.url.oaServer + 'oaHrCareService/saveOrUpdateOaHrCare',
        //关怀申请更新
        updateOaHrCare: webconfig.url.oaServer + 'oaHrCareService/updateOaHrCare',
        //关怀申请详情查询
        findOaHrCareDetailById: webconfig.url.oaServer + 'oaHrCareService/findOaHrCareDetailById',

        //自驾车补贴申请列表
        findOaHrCarSelfDrivePagination: webconfig.url.oaServer + 'oaHrCarSelfDriveService/findOaHrCarSelfDrivePagination',
        //自驾车补贴申请保存
        saveOrUpdateOaHrCarSelfDrive: webconfig.url.oaServer + 'oaHrCarSelfDriveService/saveOrUpdateOaHrCarSelfDrive',
        //自驾车补贴申请更新
        updateOaHrCarSelfDrive: webconfig.url.oaServer + 'oaHrCarSelfDriveService/updateOaHrCarSelfDrive',
        //自驾车补贴申请详情查询
        findOaHrCarSelfDriveDetailById: webconfig.url.oaServer + 'oaHrCarSelfDriveService/findOaHrCarSelfDriveDetailById',
        /* 福利模块 end */

        /* 生产管理模块 start */
        //车间准入申请列表
        findOaWorkshopEntryPagination: webconfig.url.oaServer + 'oaWorkshopEntryService/findOaWorkshopEntryPagination',
        //车间准入申请新增
        saveOaWorkshopEntry: webconfig.url.oaServer + 'oaWorkshopEntryService/saveOaWorkshopEntry',
        //车间准入申请编辑
        updateOaWorkshopEntry: webconfig.url.oaServer + 'oaWorkshopEntryService/updateOaWorkshopEntry',
        //车间准入申请详情查询
        findOaWorkshopEntryById: webconfig.url.oaServer + 'oaWorkshopEntryService/findOaWorkshopEntryById',

        //车间门禁申请列表
        findOaWorkshopEntranceGuardPagination: webconfig.url.oaServer + 'oaWorkshopEntranceGuardService/findOaWorkshopEntranceGuardPagination',
        //车间门禁申请新增
        saveOaWorkshopEntranceGuard: webconfig.url.oaServer + 'oaWorkshopEntranceGuardService/saveOaWorkshopEntranceGuard',
        //车间门禁申请编辑
        updateOaWorkshopEntranceGuard: webconfig.url.oaServer + 'oaWorkshopEntranceGuardService/updateOaWorkshopEntranceGuard',
        //车间门禁申请详情查询
        findOaWorkshopEntranceGuardById: webconfig.url.oaServer + 'oaWorkshopEntranceGuardService/findOaWorkshopEntranceGuardById',

        //车间实习申请列表
        findOaWorkshopPracticePagination: webconfig.url.oaServer + 'oaWorkshopPracticeService/findOaWorkshopPracticePagination',
        //车间实习申请新增
        saveOaWorkshopPractice: webconfig.url.oaServer + 'oaWorkshopPracticeService/saveOaWorkshopPractice',
        //车间实习申请编辑
        updateOaWorkshopPractice: webconfig.url.oaServer + 'oaWorkshopPracticeService/updateOaWorkshopPractice',
        //车间实习申请详情查询
        findOaWorkshopPracticeById: webconfig.url.oaServer + 'oaWorkshopPracticeService/findOaWorkshopPracticeById',
        
        //车间报修申请列表
        findOaWorkshopRepairPagination: webconfig.url.oaServer + 'oaWorkshopRepairService/findOaWorkshopRepairPagination',
        //车间报修申请新增
        saveOaWorkshopRepair: webconfig.url.oaServer + 'oaWorkshopRepairService/saveOaWorkshopRepair',
        //车间报修申请编辑PC
        updateOaWorkshopRepairPc: webconfig.url.oaServer + 'oaWorkshopRepairService/updateOaWorkshopRepairPc',
        //车间报修申请详情查询
        findOaWorkshopRepairById: webconfig.url.oaServer + 'oaWorkshopRepairService/findOaWorkshopRepairById',
      
        /* 生产管理模块 end */

        /* 考勤管理模块 start */
        //出差申请列表
        findOaTravelPagination: webconfig.url.oaServer + 'oaTravelService/findOaTravelPagination',
        //出差申请新增
        saveOrUpdateOaTravel: webconfig.url.oaServer + 'oaTravelService/saveOrUpdateOaTravel',
        //出差申请更新
        updateOaTravel: webconfig.url.oaServer + 'oaTravelService/updateOaTravel',
        //出差申请详情查询
        findOaTravelDetailById: webconfig.url.oaServer + 'oaTravelService/findOaTravelDetailById',
        //出差天数查询
        findOaTravelType: webconfig.url.oaServer + 'oaTravelService/findOaTravelType',
        /* 考勤管理模块 end */

        /* 市场管理模块 start */
        //费用科目URL
        findEncumbranceExpAccPagination: webconfig.url.emsSever + 'emsBaseEncumbranceService/findEncumbranceExpAccPagination',
        //广告费用申请列表
        findOaMarketingCostAdPagination: webconfig.url.oaServer + 'oaMarketingCostAdService/findOaMarketingCostAdPagination',
        //广告费用申请详情查询
        findOaMarketingCostAdDetailById: webconfig.url.oaServer + 'oaMarketingCostAdService/findOaMarketingCostAdDetailById',
        //广告费用申请新增
        saveOaMarketingCostAd: webconfig.url.oaServer + 'oaMarketingCostAdService/saveOaMarketingCostAd',
        //广告费用申请更新
        updateOaMarketingCostAd: webconfig.url.oaServer + 'oaMarketingCostAdService/updateOaMarketingCostAd',



        //市场活动申请列表
        findOaMarketingCostCompainPagination: webconfig.url.oaServer + 'oaMarketingCostCompainService/findOaMarketingCostCompainPagination',
        //市场活动申请详情查询
        findOaMarketingCostCompainDetailById: webconfig.url.oaServer + 'oaMarketingCostCompainService/findOaMarketingCostCompainDetailById',
        //市场活动申请新增
        saveOaMarketingCostCompain: webconfig.url.oaServer + 'oaMarketingCostCompainService/saveOaMarketingCostCompain',
        //市场活动申请更新
        updateOaMarketingCostCompain: webconfig.url.oaServer + 'oaMarketingCostCompainService/updateOaMarketingCostCompain',

        //活动产品核销申请列表
        findCampaignWriteoffProductPagination: webconfig.url.oaServer + 'oaCampaignWriteoffProductService/findCampaignWriteoffProductPagination',
        //活动产品核销申请详情查询
        findCampaignWriteoffProductDetailById: webconfig.url.oaServer + 'oaCampaignWriteoffProductService/findCampaignWriteoffProductDetailById',
        //活动产品核销申请新增
        saveCampaignWriteoffProduct: webconfig.url.oaServer + 'oaCampaignWriteoffProductService/saveCampaignWriteoffProduct',
        //活动产品核销申请更新
        updateCampaignWriteoffProduct: webconfig.url.oaServer + 'oaCampaignWriteoffProductService/updateCampaignWriteoffProduct',

        //促销品费用申请列表
        findOaMarketingCostSalesPagination: webconfig.url.oaServer + 'oaMarketingCostSalesService/findOaMarketingCostSalesPagination',
        //促销品费用申请详情查询
        findOaMarketingCostSalesDetailById: webconfig.url.oaServer + 'oaMarketingCostSalesService/findOaMarketingCostSalesDetailById',
        //促销品费用申请新增
        saveOaMarketingCostSales: webconfig.url.oaServer + 'oaMarketingCostSalesService/saveOaMarketingCostSales',
        //促销品费用申请更新
        updateOaMarketingCostSales: webconfig.url.oaServer + 'oaMarketingCostSalesService/updateOaMarketingCostSales',

        /*  市场活动费用报销 广告费用、市场活动报销、促销品费用报销  start*/
        //广告费用核算申请列表
        findOaMarketingCostAdRePagination: webconfig.url.oaServer + 'oaMarketingCostAdReService/findOaMarketingCostAdRePagination',
        //广告费用报销查询广告费用申请单
        bx_findOaMarketingCostAdList: webconfig.url.oaServer + 'oaMarketingCostAdReService/findOaMarketingCostAdList',
        //查询广告费用申请单详情
        bx_findOaMarketingCostAdDetailById: webconfig.url.oaServer + 'oaMarketingCostAdReService/findOaMarketingCostAdDetailById',
        //广告费用报销详情
        bx_findOaMarketingCostAdReDetailById: webconfig.url.oaServer + 'oaMarketingCostAdReService/findOaMarketingCostAdReDetailById',
        //广告费用报销保存
        bx_saveOaMarketingCostAdRe: webconfig.url.oaServer + 'oaMarketingCostAdReService/saveOaMarketingCostAdRe',
        //广告费用报销更新
        bx_updateOaMarketingCostAdRe: webconfig.url.oaServer + 'oaMarketingCostAdReService/updateOaMarketingCostAdRe',
        //广告费用报销审批流更新
        bx_updateOaMarketingCostAdReByApproval: webconfig.url.oaServer + 'oaMarketingCostAdReService/updateOaMarketingCostAdReByApproval',

        //市场活动费用核算申请列表
        findOaMarketingCostCompainRePagination: webconfig.url.oaServer + 'oaMarketingCostCompainReService/findOaMarketingCostCompainRePagination',
        //市场活动费用报销查询市场活动费用申请单
        bx_findOaMarketingCostCompainPagination: webconfig.url.oaServer + 'oaMarketingCostCompainReService/findOaMarketingCostCompainPagination',
        //查询市场活动费用申请单详情
        bx_findOaMarketingCostCompainDetailById: webconfig.url.oaServer + 'oaMarketingCostCompainReService/findOaMarketingCostCompainDetailById',
        //市场活动费用报销详情
        bx_findOaMarketingCostCompainReDetailById: webconfig.url.oaServer + 'oaMarketingCostCompainReService/findOaMarketingCostCompainReDetailById',
        //市场活动费用报销保存
        bx_saveOaMarketingCostCompainRe: webconfig.url.oaServer + 'oaMarketingCostCompainReService/saveOaMarketingCostCompainRe',
        //市场活动费用报销更新
        bx_updateOaMarketingCostCompainRe: webconfig.url.oaServer + 'oaMarketingCostCompainReService/updateOaMarketingCostCompainRe',
        //市场活动费用报销审批流更新
        bx_updateOaMarketingCostCompainReByApproval: webconfig.url.oaServer + 'oaMarketingCostCompainReService/updateOaMarketingCostCompainReByApproval',

        //省区促销品费用报销核算申请列表
        findOaMarketingCostSalesRePagination: webconfig.url.oaServer + 'oaMarketingCostSalesReService/findOaMarketingCostSalesRePagination',
        //省区促销品费用报销--查询促销品费用报销申请单
        bx_findOaMarketingCostSalesPagination: webconfig.url.oaServer + 'oaMarketingCostSalesReService/findOaMarketingCostSalesPagination',
        //省区查询促销品费用报销申请单详情
        bx_findOaMarketingCostSalesDetailById: webconfig.url.oaServer + 'oaMarketingCostSalesReService/findOaMarketingCostSalesDetailById',
        //省区促销品费用报销报销详情
        bx_findOaMarketingCostSalesReDetailById: webconfig.url.oaServer + 'oaMarketingCostSalesReService/findOaMarketingCostSalesReDetailById',
        //省区促销品费用报销报销保存
        bx_saveOaMarketingCostSalesRe: webconfig.url.oaServer + 'oaMarketingCostSalesReService/saveOaMarketingCostSalesRe',
        //省区促销品费用报销报销更新
        bx_updateOaMarketingCostSalesRe: webconfig.url.oaServer + 'oaMarketingCostSalesReService/updateOaMarketingCostSalesRe',
        //省区促销品费用报销报销审批流更新
        bx_updateOaMarketingCostSalesReByApproval: webconfig.url.oaServer + 'oaMarketingCostSalesReService/updateOaMarketingCostSalesReByApproval',


        //总部促销品费用报销核算申请列表
        findOaMarketingCostSalesHeadRePagination: webconfig.url.oaServer + 'oaMarketingCostSalesHeadReService/findOaMarketingCostSalesHeadRePagination',
        //总部查询促销品费用报销申请单详情
        bx_findOaMarketingCostSalesHeadReDetailById: webconfig.url.oaServer + 'oaMarketingCostSalesHeadReService/findOaMarketingCostSalesHeadReDetailById',
        //总部促销品费用报销报销保存
        bx_saveOaMarketingCostSalesHeadRe: webconfig.url.oaServer + 'oaMarketingCostSalesHeadReService/saveOaMarketingCostSalesHeadRe',
        //总部促销品费用报销报销更新
        bx_updateOaMarketingCostSalesHeadRe: webconfig.url.oaServer + 'oaMarketingCostSalesHeadReService/updateOaMarketingCostSalesHeadRe',
        //总部促销品费用报销报销审批流更新
        bx_updateOaMarketingCostSalesHeadReByApproval: webconfig.url.oaServer + 'oaMarketingCostSalesHeadReService/updateOaMarketingCostSalesHeadReByApproval',

        //营销媒体费用申请列表查询
        findOaMarketingCostMediaPagination: webconfig.url.oaServer + 'oaMarketingCostMediaService/findOaMarketingCostMediaPagination',
        //营销媒体费用申请详情
        findOaMarketingCostMediaDetailById: webconfig.url.oaServer + 'oaMarketingCostMediaService/findOaMarketingCostMediaDetailById',
        //营销媒体费用申请保存
        saveOaMarketingCostMedia: webconfig.url.oaServer + 'oaMarketingCostMediaService/saveOaMarketingCostMedia',
        //营销媒体费用保存更新
        updateOaMarketingCostMedia: webconfig.url.oaServer + 'oaMarketingCostMediaService/updateOaMarketingCostMedia',
        //营销媒体费用审批中保存
        updateOaMarketingCostMediaByApproval: webconfig.url.oaServer + 'oaMarketingCostMediaService/updateOaMarketingCostMediaByApproval',

        //营销媒体费用报销申请列表查询
        findOaMarketingCostMediaRePagination: webconfig.url.oaServer + 'oaMarketingCostMediaReService/findOaMarketingCostMediaRePagination',
        //营销媒体费用报销单号查询
        findOaMarketingCostMediaList: webconfig.url.oaServer + 'oaMarketingCostMediaReService/findOaMarketingCostMediaList',
        //营销媒体费用报销详情
        findOaMarketingCostMediaReDetailById: webconfig.url.oaServer + 'oaMarketingCostMediaReService/findOaMarketingCostMediaReDetailById',
        //营销媒体费用核销保存
        saveOaMarketingCostMediaRe: webconfig.url.oaServer + 'oaMarketingCostMediaReService/saveOaMarketingCostMediaRe',
        //营销媒体费用保存更新
        updateOaMarketingCostMediaRe: webconfig.url.oaServer + 'oaMarketingCostMediaReService/updateOaMarketingCostMediaRe',
        //营销媒体费用审批中保存
        updateOaMarketingCostMediaReByApproval: webconfig.url.oaServer + 'oaMarketingCostMediaReService/updateOaMarketingCostMediaReByApproval',

        //小型活动费用报销 报销列表
        findOaSmallMarketingCostCompainRePagination: webconfig.url.oaServer + 'oaSamllMarketingCostCompainReService/findOaSmallMarketingCostCompainRePagination',
        //小型活动费用报销 报销详情
        findOaSmallMarketingCostCompainReDetailById: webconfig.url.oaServer + 'oaSamllMarketingCostCompainReService/findOaSmallMarketingCostCompainReDetailById',
        //小型活动费用报销 报销保存
        saveOaSmallMarketingCostCompainRe: webconfig.url.oaServer + 'oaSamllMarketingCostCompainReService/saveOaSmallMarketingCostCompainRe',
        //小型活动费用报销 报销更新
        updateOaSmallMarketingCostCompainRe: webconfig.url.oaServer + 'oaSamllMarketingCostCompainReService/updateOaSmallMarketingCostCompainRe',
        //小型活动费用报销 报销审批流更新
        updateOaSmallMarketingCostCompainReByApproval: webconfig.url.oaServer + 'oaSamllMarketingCostCompainReService/updateOaSmallMarketingCostCompainReByApproval',
        //小型活动费用报销 报销流程回调
        updateOaSmallMarketingCostCompainReStatus: webconfig.url.oaServer + 'oaSamllMarketingCostCompainReService/updateOaSmallMarketingCostCompainReStatus',

        //小型广告费用报销 报销列表
        findOaSmallMarketingCostAdRePagination: webconfig.url.oaServer + 'oaSmallMarketingCostAdReService/findOaSmallMarketingCostAdRePagination',
        //小型广告费用报销 报销详情
        findOaSmallMarketingCostAdReDetailById: webconfig.url.oaServer + 'oaSmallMarketingCostAdReService/findOaSmallMarketingCostAdReDetailById',
        //小型广告费用报销 报销保存
        saveOaSmallMarketingCostAdRe: webconfig.url.oaServer + 'oaSmallMarketingCostAdReService/saveOaSmallMarketingCostAdRe',
        //小型广告费用报销 报销更新
        updateOaSmallMarketingCostAdRe: webconfig.url.oaServer + 'oaSmallMarketingCostAdReService/updateOaSmallMarketingCostAdRe',
        //小型广告费用报销 报销审批流更新
        updateOaSmallMarketingCostAdReByApproval: webconfig.url.oaServer + 'oaSmallMarketingCostAdReService/updateOaSmallMarketingCostAdReByApproval',
        //小型广告费用报销 报销流程回调
        updateOaSmallMarketingCostAdReStatus: webconfig.url.oaServer + 'oaSmallMarketingCostAdReService/updateOaSmallMarketingCostAdReStatus',

        /* 市场活动费用报销 广告费用、市场活动报销、促销品费用报销  end*/


        //根据业务员查门店信息
        findStorePaginationByPerson: webconfig.url.baseServer + 'baseInvStoreContactTService/findStorePaginationByPerson',
        //根据经销商查门店信息
        findStorePagination: webconfig.url.baseServer + 'baseInvStoreTService/findStorePagination',
        //列表页人员控件
        findPersonNewPagination: webconfig.url.baseServer + 'baseOrgStructureService/findPersonNewPagination',
        // 查询人员接口
        baseOrgStructureService_findAccessPersonPagination: webconfig.url.baseServer + 'baseOrgStructureService/findAccessPersonPagination',
        // 查询部门接口
        baseOrgStructureService_findBaseDeptInfoByUserType: webconfig.url.baseServer + 'baseOrgStructureService/findBaseDeptInfoByUserType',
        /* 市场管理模块 end */

        /* lijiayao end */

       /*husaiqiang start*/

        //打卡异常申请详情
        findSignExpReqApplyDetail: webconfig.url.cccAppServer + 'cccSignExpReqService/findSignExpReqInfo',

        //排班申请明细
        findSchedulReqDetail: webconfig.url.cccAppServer + 'cccSchedualReqService/findSchedulReqDetail',

        //排班调整申请明细
        findAdjustmentReqDetail: webconfig.url.cccAppServer + 'cccAdjustmentReqService/findAdjustmentReqDetail',

        //导购编制申请详情
        findGuideInfo: webconfig.url.cccAppServer + 'oaHrGuideRService/findGuideInfo',
        //导购编制申请提交
        saveGuideInfo: webconfig.url.cccAppServer + 'oaHrGuideRService/save',

        /*husaiqiang end*/

        /*baijianlong start*/

        //办公用品领用更新-流程中心调用的更新接口
        updateOfficeSplHByApproval: webconfig.url.oaServer + 'oaAdmOfficeSplHService/updateOfficeSplHByApproval',
        //合同修改-流程中心调用的更新接口
        updateFinAgreementByApproval: webconfig.url.oaServer + 'oaFinAgreementService/updateFinAgreementByApproval',
        //流程中查看合同详情
        findFinAgreementInfoByIdApproval: webconfig.url.oaServer + '/oaFinAgreementService/findFinAgreementInfoByIdApproval',
        //礼品领用更新-流程中心调用的更新接口
        updateGiftApplyByApproval: webconfig.url.oaServer + 'oaGiftApplyService/updateGiftApplyByApproval',
        //会议申请更新--流程中心调用的更新接口
        updateMeetingApplyByApproval: webconfig.url.oaServer + 'oaMeetingApplyService/updateMeetingApplyByApproval',
        //盖章申请单更新--流程中心调用的更新接口
        updateSealApplyByApproval: webconfig.url.oaServer + 'oaSealApplyService/updateSealApplyByApproval',
        //出差申请单更新-流程中心调用的更新接口
        updateOaTravelByApproval: webconfig.url.oaServer + 'oaTravelService/updateOaTravelByApproval',
        //固定资产采购申请单更新-流程中心调用的更新接口
        updateAssetApplyByApproval: webconfig.url.oaServer + 'oaAssetApplyService/updateAssetApplyByApproval',
        //固定资产调剂单更新-流程中心调用的更新接口
        updateAdjustApplyByApproval: webconfig.url.oaServer + 'oaAssetAdjustService/updateAdjustApplyByApproval',
        //固定资产验收单更新--流程中心调用的更新接口
        saveOrUpdateAssetCheckByApproval: webconfig.url.oaServer + 'oaAssetCheckService/saveOrUpdateAssetCheckByApproval',
        //固定资产报修单更新--流程中心调用的更新接口
        updateMaintenanceApplyByApproval: webconfig.url.oaServer + 'oaAssetMaintenanceService/updateMaintenanceApplyByApproval',
        //固定资产报废单更新--流程中心调用的更新接口
        updateScrapApplyByApproval: webconfig.url.oaServer + 'oaAssetScrapService/updateScrapApplyByApproval',
        //加班申请单更新--流程中心调用的更新接口
        updateWorkOverTimeByApproval: webconfig.url.oaServer + 'oaAttendTimeoffService/updateWorkOverTimeByApproval',
        //请假申请单更新--流程中心调用的更新接口
        updateAttendTimeoffByApproval: webconfig.url.oaServer + 'oaAttendTimeoffService/updateAttendTimeoffByApproval',
        //更新经销商转款申请单--流程中心调用的更新接口
        updateApDetrvByApproval: webconfig.url.oaServer + 'oaApDetrvHeadService/updateApDetrvByApproval',
        //退款申请单更新--流程中心调用的更新接口
        updateApRefundByApproval: webconfig.url.oaServer + 'oaApRefundService/updateApRefundByApproval',
        //银行间划款申请单保存更新--流程中心调用的更新接口
        updateApTransferByApproval: webconfig.url.oaServer + 'oaApTransferService/updateApTransferByApproval',
        //更新差旅报销单--流程中心调用的更新接口
        updateApTrvByApproval: webconfig.url.oaServer + 'oaApTrvHeadService/updateApTrvByApproval',
        //借支申请单更新--流程中心调用的更新接口
        updateBorrowApplyByApproval: webconfig.url.oaServer + 'oaBorrowApplyService/updateBorrowApplyByApproval',
        //住房补贴申请单审批流更新
        updateOaHrHSubsidyByApproval: webconfig.url.oaServer + 'oaHrHSubsidyService/updateOaHrHSubsidyByApproval',
        //自驾车标准申请单审批流更新
        updateOaHrCarSelfDriveByApproval: webconfig.url.oaServer + 'oaHrCarSelfDriveService/updateOaHrCarSelfDriveByApproval',
        //关怀申请单审批流更新
        updateOaHrCareByApproval: webconfig.url.oaServer + 'oaHrCareService/updateOaHrCareByApproval',
        //生育福利申请单审批流更新
        updateOaHrBirthByApproval: webconfig.url.oaServer + 'oaHrBirthService/updateOaHrBirthByApproval',
        //活动产品核销审批流更新
        updateCampaignWriteoffProductByApproval: webconfig.url.oaServer + 'oaCampaignWriteoffProductService/updateCampaignWriteoffProductByApproval',
        //广告费用审批流更新
        updateOaMarketingCostAdByApproval: webconfig.url.oaServer + 'oaMarketingCostAdService/updateOaMarketingCostAdByApproval',
        //市场活动费用审批流更新
        updateOaMarketingCostCompainByApproval: webconfig.url.oaServer + 'oaMarketingCostCompainService/updateOaMarketingCostCompainByApproval',
        //促销品费用审批流更新
        updateOaMarketingCostSalesByApproval: webconfig.url.oaServer + 'oaMarketingCostSalesService/updateOaMarketingCostSalesByApproval',
        //生产部流程-车间门禁申请审批流更新
        updateOaWorkshopEntranceGuardByApproval: webconfig.url.oaServer + 'oaWorkshopEntranceGuardService/updateOaWorkshopEntranceGuardByApproval',
        //产部流程-车间准入记录审批流更新
        updateOaWorkshopEntryByApproval: webconfig.url.oaServer + 'oaWorkshopEntryService/updateOaWorkshopEntryByApproval',
        //生产部流程-车间实习申请单审批流更新
        updateOaWorkshopPracticeByApproval: webconfig.url.oaServer + 'oaWorkshopPracticeService/updateOaWorkshopPracticeByApproval',
        //生产部流程-车间报修申请审批流更新
        updateOaWorkshopRepairPcByApproval: webconfig.url.oaServer + 'oaWorkshopRepairService/updateOaWorkshopRepairPcByApproval',
        //Oa系统公共附件上传接口
        uploadFile: webconfig.url.oaServer + 'oaFileUploudServer/uploadFile'
        /*baijianlong end*/

    }
})