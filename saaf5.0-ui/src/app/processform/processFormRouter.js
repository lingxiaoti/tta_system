define(["app"], function (app) {
    var path = appName.toLocaleLowerCase();

    app.config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {

        $stateProvider
        //发起流程后 显示流程页面
            .state('showProcess',
                {
                    url: '/' + path + '/showProcess',
                    templateUrl: 'app/processform//showProcess.html',
                    controllerUrl: '../app/processform//showProcessCtrl'
                })
            //生产单-OMS
            .state('showProcess.pickingApplyInfoDetail',
                {
                    url: '/pickingApplyInfoDetail',
                    templateUrl: 'app/ims/templates/imsorder/pickingApplyInfoDetail.html',
                    controllerUrl: '../app/ims/templates/imsorder/pickingApplyInfoDetailCtrl'
                })
            //销售订单详情
            .state('showProcess.salesOrderDetail',
                {
                    url: '/salesOrderDetail',
                    templateUrl: 'app/ims/templates/imsdealer/salesOrderDetail.html',
                    controllerUrl: '../app/ims/templates/imsdealer/salesOrderDetailCtrl'
                })
            //生产单-OEM
            .state('showProcess.pickingApplyInfoNxDetail',
                {
                    url: '/pickingApplyInfoNxDetail',
                    templateUrl: 'app/ims/templates/imsorder/pickingApplyInfoNxDetail.html',
                    controllerUrl: '../app/ims/templates/imsorder/pickingApplyInfoNxDetailCtrl'
                })
            //报价单-OMS
            .state('showProcess.soPiHeadersDetail',
                {
                    url: '/soPiHeadersDetail',
                    templateUrl: 'app/ims/templates/imsorder/soPiHeadersDetail.html',
                    controllerUrl: '../app/ims/templates/imsorder/soPiHeadersDetailCtrl'
                })
            //报价单-OEM
            .state('showProcess.soPiHeadersNxDetail',
                {
                    url: '/soPiHeadersNxDetail',
                    templateUrl: 'app/ims/templates/imsorder/soPiHeadersNxDetail.html',
                    controllerUrl: '../app/ims/templates/imsorder/soPiHeadersNxDetailCtrl'
                })

            //信用证
            .state('showProcess.crdBlcLcAdd',
                {
                    url: '/crdBlcLcAdd',
                    templateUrl: 'app/ims/templates/crd/crdBlcLcAdd.html',
                    controllerUrl: '../app/ims/templates/crd/crdBlcLcAddCtrl'
                })
            //信用证修改件
            .state('showProcess.crdBlcLCmodifyAdd',
                {
                    url: '/crdBlcLCmodifyAdd',
                    templateUrl: 'app/ims/templates/crd/crdBlcLCmodifyAdd.html',
                    controllerUrl: '../app/ims/templates/crd/crdBlcLCmodifyAddCtrl'
                })

            //信用证特批件
            .state('showProcess.crdBlcLcSaDetail',
                {
                    url: '/crdBlcLcSaDetail',
                    templateUrl: 'app/ims/templates/crd/crdBlcLcSaDetail.html',
                    controllerUrl: '../app/ims/templates/crd/crdBlcLcSaDetailCtrl'
                })

            //信用申请
            .state('showProcess.creditAllotDetail',
                {
                    url: '/creditAllotDetail',
                    templateUrl: 'app/ims/templates/crd/creditAllotDetail.html',
                    controllerUrl: '../app/ims/templates/crd/creditAllotDetailCtrl'
                })

            //返厂维修申请
            .state('showProcess.csReturnApplyDetail',
                {
                    url: '/csReturnApplyDetail',
                    templateUrl: 'app/ims/templates/cs/csReturnApplyDetail.html',
                    controllerUrl: '../app/ims/templates/cs/csReturnApplyDetailCtrl'
                })


            //费用结算批
            .state('showProcess.LgFeeBatchHeadersDetail',
                {
                    url: '/LgFeeBatchHeadersDetail',
                    templateUrl: 'app/ims/templates/lg/LgFeeBatchHeadersDetail.html',
                    controllerUrl: '../app/ims/templates/lg/LgFeeBatchHeadersDetailCtrl'
                })
            //出货通知书_OMS
            .state('showProcess.shipAdviceDetail',
                {
                    url: '/shipAdviceDetail',
                    templateUrl: 'app/ims/templates/delv/shipAdviceDetail.html',
                    controllerUrl: '../app/ims/templates/delv/shipAdviceDetailCtrl'
                })
            //出货通知书_OEM
            .state('showProcess.oemShipAdviceDetail',
                {
                    url: '/oemShipAdviceDetail',
                    templateUrl: 'app/ims/templates/delv/oemShipAdviceDetail.html',
                    controllerUrl: '../app/ims/templates/delv/oemShipAdviceDetailCtrl'
                })

            //杂项单据
            .state('showProcess.sundryOrderDetail',
                {
                    url: '/sundryOrderDetail',
                    templateUrl: 'app/ims/templates/imsorder/sundryOrderDetail.html',
                    controllerUrl: '../app/ims/templates/imsorder/sundryOrderDetailCtrl'
                })

            //杂项单据
            .state('showProcess.allotApplyDetail',
                {
                    url: '/allotApplyDetail',
                    templateUrl: 'app/ims/templates/imsorder/allotApplyDetail.html',
                    controllerUrl: '../app/ims/templates/imsorder/allotApplyDetailCtrl'
                })


            //固定资产验收单表单
            /* .state('showProcess.pickingApplyInfoDetail',
                 {
                     url: '/pickingApplyInfoDetail',
                     templateUrl: 'app/processform/formHtml/asset/purchaseCheckListApplyForm.html',
                     controllerUrl: '../app/processform/formHtml/asset/purchaseCheckListApplyFormCtrl'
                 })*/
            //固定资产报废单表单
            .state('showProcess.assetScrapApplyForm',
                {
                    url: '/assetScrapApplyForm',
                    templateUrl: 'app/processform/formHtml/asset/assetScrapApplyForm.html',
                    controllerUrl: '../app/processform/formHtml/asset/assetScrapApplyFormCtrl'
                })


            //固定资产调剂单表单
            .state('showProcess.fixedAssetsAdjustApplyForm',
                {
                    url: '/fixedAssetsAdjustApplyForm',
                    templateUrl: 'app/processform/formHtml/asset/fixedAssetsAdjustApplyForm.html',
                    controllerUrl: '../app/processform/formHtml/asset/fixedAssetsAdjustApplyFormCtrl'
                })
            //固定资产报修单表单
            .state('showProcess.maintenanceApplyForm',
                {
                    url: '/maintenanceApplyForm',
                    templateUrl: 'app/processform/formHtml/asset/maintenanceApplyForm.html',
                    controllerUrl: '../app/processform/formHtml/asset/maintenanceApplyFormCtrl'
                })
            //办公用品新增表单
            .state('showProcess.officeSuppliesApplyForm',
                {
                    url: '/officeSuppliesApplyForm',
                    templateUrl: 'app/processform/formHtml/officeArticles/officeSuppliesApplyForm.html',
                    controllerUrl: '../app/processform/formHtml/officeArticles/officeSuppliesApplyFormCtrl'
                })
            //礼品领用新增
            .state('showProcess.giftApplyForm',
                {
                    url: '/giftApplyForm',
                    templateUrl: 'app/processform/formHtml/giftApply/giftApplyForm.html',
                    controllerUrl: '../app/processform/formHtml/giftApply/giftApplyFormCtrl'
                })
            //盖章新增
            .state('showProcess.sealApplyForm',
                {
                    url: '/sealApplyForm',
                    templateUrl: 'app/processform/formHtml/templates/sealApplyForm.html',
                    controllerUrl: '../app/processform/formHtml/templates/sealApplyFormCtrl'
                })
            //借支新增
            .state('showProcess.debitApplyForm',
                {
                    url: '/debitApplyForm',
                    templateUrl: 'app/processform/formHtml/templates/debitApplyForm.html',
                    controllerUrl: '../app/processform/formHtml/templates/debitApplyFormCtrl'
                })
            //差旅报销新增
            .state('showProcess.travelApplyForm',
                {
                    url: '/travelApplyForm',
                    templateUrl: 'app/processform/formHtml/templates/travelApplyForm.html',
                    controllerUrl: '../app/processform/formHtml/templates/travelApplyFormCtrl'
                })
            //差旅报销审核（核销金额）
            .state('showProcess.travelApplyVerification',
                {
                    url: '/travelApplyVerification',
                    templateUrl: 'app/processform/formHtml/templates/travelApplyVerification.html',
                    controllerUrl: '../app/processform/formHtml/templates/travelApplyVerificationCtrl'
                })
            //差销商转款新增
            .state('showProcess.dealerApplyForm',
                {
                    url: '/dealerApplyForm',
                    templateUrl: 'app/processform/formHtml/templates/dealerApplyForm.html',
                    controllerUrl: '../app/processform/formHtml/templates/dealerApplyFormCtrl'
                })
            //退款新增
            .state('showProcess.refundApplyForm',
                {
                    url: '/refundApplyForm',
                    templateUrl: 'app/processform/formHtml/templates/refundApplyForm.html',
                    controllerUrl: '../app/processform/formHtml/templates/refundApplyFormCtrl'
                })
            //退款新增
            .state('showProcess.bankfundsApplyForm',
                {
                    url: '/bankfundsApplyForm',
                    templateUrl: 'app/processform/formHtml/templates/bankfundsApplyForm.html',
                    controllerUrl: '../app/processform/formHtml/templates/bankfundsApplyFormCtrl'
                })
            //会议管理新增
            .state('showProcess.meetingApplyForm',
                {
                    url: '/meetingApplyForm',
                    templateUrl: 'app/processform/formHtml/templates/meetingApplyForm.html',
                    controllerUrl: '../app/processform/formHtml/templates/meetingApplyFormCtrl'
                })
            //请假单新增
            .state('showProcess.leaveApplyForm',
                {
                    url: '/leaveApplyForm',
                    templateUrl: 'app/processform/formHtml/timeApply/leaveApplyForm.html',
                    controllerUrl: '../app/processform/formHtml/timeApply/leaveApplyFormCtrl'
                })
            //出差单新增
            .state('showProcess.businessApplyForm',
                {
                    url: '/businessApplyForm',
                    templateUrl: 'app/processform/formHtml/templates/businessApplyForm.html',
                    controllerUrl: '../app/processform/formHtml/templates/businessApplyFormCtrl'
                })
            //出差单二
            .state('showProcess.businessApplyFormTwo',
                {
                    url: '/businessApplyFormTwo',
                    templateUrl: 'app/processform/formHtml/templates/businessApplyFormTwo.html',
                    controllerUrl: '../app/processform/formHtml/templates/businessApplyFormCtrl'
                })
            //加班单新增
            .state('showProcess.overtimeApplyForm',
                {
                    url: '/overtimeApplyForm',
                    templateUrl: 'app/processform/formHtml/timeApply/overtimeApplyForm.html',
                    controllerUrl: '../app/processform/formHtml/timeApply/overtimeApplyFormCtrl'
                })
            //合同单新增
            .state('showProcess.contractApplyForm',
                {
                    url: '/contractApplyForm',
                    templateUrl: 'app/processform/formHtml/templates/contractApplyForm.html',
                    controllerUrl: '../app/processform/formHtml/templates/contractApplyFormCtrl'
                })
































            /* lijiayao start */
            /* 福利申请 start */
            //生育单新增
            .state('showProcess.maternityBenefitsApplyForm',
                {
                    url: '/maternityBenefitsApplyForm',
                    templateUrl: 'app/processform/formHtml/welfareApply/maternityBenefitsApplyForm.html',
                    controllerUrl: '../app/processform/formHtml/welfareApply/maternityBenefitsApplyFormCtrl'
                })
            //生育审批
            .state('showProcess.maternityBenefitsForm',
                {
                    url: '/maternityBenefitsForm',
                    templateUrl: 'app/processform/formHtml/welfareApply/maternityBenefitsForm.html',
                    controllerUrl: '../app/processform/formHtml/welfareApply/maternityBenefitsFormCtrl'
                })

            // 住房补贴单新增
            .state('showProcess.housingSubsidiesApplyForm',
                {
                    url: '/housingSubsidiesApplyForm',
                    templateUrl: 'app/processform/formHtml/welfareApply/housingSubsidiesApplyForm.html',
                    controllerUrl: '../app/processform/formHtml/welfareApply/housingSubsidiesApplyFormCtrl'
                })

            // 关怀单新增
            .state('showProcess.careApplyForm',
                {
                    url: '/careApplyForm',
                    templateUrl: 'app/processform/formHtml/welfareApply/careApplyForm.html',
                    controllerUrl: '../app/processform/formHtml/welfareApply/careApplyFormCtrl'
                })

            // 自驾车单新增
            .state('showProcess.selfDriveSubsidiesApplyForm',
                {
                    url: '/selfDriveSubsidiesApplyForm',
                    templateUrl: 'app/processform/formHtml/welfareApply/selfDriveSubsidiesApplyForm.html',
                    controllerUrl: '../app/processform/formHtml/welfareApply/selfDriveSubsidiesApplyFormCtrl'
                })
            /* 福利申请 end */

            /* 生产管理 start */
            // 车间准入单新增
            .state('showProcess.accessApplyForm',
                {
                    url: '/accessApplyForm',
                    templateUrl: 'app/processform/formHtml/productionManagementApply/accessApplyForm.html',
                    controllerUrl: '../app/processform/formHtml/productionManagementApply/accessApplyFormCtrl'
                })

            // 车间门禁单新增
            .state('showProcess.entranceGuardApplyForm',
                {
                    url: '/entranceGuardApplyForm',
                    templateUrl: 'app/processform/formHtml/productionManagementApply/entranceGuardApplyForm.html',
                    controllerUrl: '../app/processform/formHtml/productionManagementApply/entranceGuardApplyFormCtrl'
                })

            // 车间实习单新增
            .state('showProcess.internshipApplyForm',
                {
                    url: '/internshipApplyForm',
                    templateUrl: 'app/processform/formHtml/productionManagementApply/internshipApplyForm.html',
                    controllerUrl: '../app/processform/formHtml/productionManagementApply/internshipApplyFormCtrl'
                })

            // 车间报修单新增
            .state('showProcess.repairApplyForm',
                {
                    url: '/repairApplyForm',
                    templateUrl: 'app/processform/formHtml/productionManagementApply/repairApplyForm.html',
                    controllerUrl: '../app/processform/formHtml/productionManagementApply/repairApplyFormCtrl'
                })
            // 车间维修
            .state('showProcess.repairResultsApplyForm',
                {
                    url: '/repairResultsApplyForm',
                    templateUrl: 'app/processform/formHtml/productionManagementApply/repairResultsApplyForm.html',
                    controllerUrl: '../app/processform/formHtml/productionManagementApply/repairResultsApplyFormCtrl'
                })
            // 车间评估
            .state('showProcess.repairResultsppApplyForm',
                {
                    url: '/repairResultsppApplyForm',
                    templateUrl: 'app/processform/formHtml/productionManagementApply/repairResultsppApplyForm.html',
                    controllerUrl: '../app/processform/formHtml/productionManagementApply/repairResultsppApplyFormCtrl'
                })
            // 车间评分
            .state('showProcess.repairScoreApplyForm',
                {
                    url: '/repairScoreApplyForm',
                    templateUrl: 'app/processform/formHtml/productionManagementApply/repairScoreApplyForm.html',
                    controllerUrl: '../app/processform/formHtml/productionManagementApply/repairScoreApplyFormCtrl'
                })

            /* 生产管理 end */

            /* 市场活动 start */
            // 广告费用单
            .state('showProcess.advertisingFeeApplyForm',
                {
                    url: '/advertisingFeeApplyForm',
                    templateUrl: 'app/processform/formHtml/marketManagementApply/advertisingFeeApplyForm.html',
                    controllerUrl: '../app/processform/formHtml/marketManagementApply/advertisingFeeApplyFormCtrl'
                })
            //广告费用单报销页
            .state('showProcess.advertisingFeeBxApplyForm',
                {
                    url: '/advertisingFeeBxApplyForm',
                    templateUrl: 'app/processform/formHtml/marketManagementApply/advertisingFeeBxApplyForm.html',
                    controllerUrl: '../app/processform/formHtml/marketManagementApply/advertisingFeeBxApplyFormCtrl'
                })
            //广告费用单审批页
            .state('showProcess.advertisingFeeBxTooApplyForm',
                {
                    url: '/advertisingFeeBxTooApplyForm',
                    templateUrl: 'app/processform/formHtml/marketManagementApply/advertisingFeeBxTooApplyForm.html',
                    controllerUrl: '../app/processform/formHtml/marketManagementApply/advertisingFeeBxTooApplyFormCtrl'
                })
            // 市场活动费用单
            .state('showProcess.marketActivityFeeApplyForm',
                {
                    url: '/marketActivityFeeApplyForm',
                    templateUrl: 'app/processform/formHtml/marketManagementApply/marketActivityFeeApplyForm.html',
                    controllerUrl: '../app/processform/formHtml/marketManagementApply/marketActivityFeeApplyFormCtrl'
                })
            //市场活动费用单报销页
            .state('showProcess.marketActivityFeeBxApplyForm',
                {
                    url: '/marketActivityFeeBxApplyForm',
                    templateUrl: 'app/processform/formHtml/marketManagementApply/marketActivityFeeBxApplyForm.html',
                    controllerUrl: '../app/processform/formHtml/marketManagementApply/marketActivityFeeBxApplyFormCtrl'
                })
            //市场活动费用单审批页
            .state('showProcess.marketActivityFeeBxToApplyForm',
                {
                    url: '/marketActivityFeeBxToApplyForm',
                    templateUrl: 'app/processform/formHtml/marketManagementApply/marketActivityFeeBxToApplyForm.html',
                    controllerUrl: '../app/processform/formHtml/marketManagementApply/marketActivityFeeBxToApplyFormCtrl'
                })
            // 活动产品核销单
            .state('showProcess.productVerificationApplyForm',
                {
                    url: '/productVerificationApplyForm',
                    templateUrl: 'app/processform/formHtml/marketManagementApply/productVerificationApplyForm.html',
                    controllerUrl: '../app/processform/formHtml/marketManagementApply/productVerificationApplyFormCtrl'
                })

            // 促销品单
            .state('showProcess.promotionProductsFeeApplyForm',
                {
                    url: '/promotionProductsFeeApplyForm',
                    templateUrl: 'app/processform/formHtml/marketManagementApply/promotionProductsFeeApplyForm.html',
                    controllerUrl: '../app/processform/formHtml/marketManagementApply/promotionProductsFeeApplyFormCtrl'
                })
            //营销媒体费用申请单
            .state('showProcess.markMedApplyForm',
                {
                    url: '/markMedApplyForm',
                    templateUrl: 'app/processform/formHtml/marketManagementApply/markMedApplyForm.html',
                    controllerUrl: '../app/processform/formHtml/marketManagementApply/markMedApplyFormCtrl'
                })
            //营销媒体费用报销表单
            .state('showProcess.markMedBxApplyForm',
                {
                    url: '/markMedBxApplyForm',
                    templateUrl: 'app/processform/formHtml/marketManagementApply/markMedBxApplyForm.html',
                    controllerUrl: '../app/processform/formHtml/marketManagementApply/markMedBxApplyFormCtrl'
                })
            //营销媒体费用审批页
            .state('showProcess.markMedBxTooApplyForm',
                {
                    url: '/markMedBxTooApplyForm',
                    templateUrl: 'app/processform/formHtml/marketManagementApply/markMedBxTooApplyForm.html',
                    controllerUrl: '../app/processform/formHtml/marketManagementApply/markMedBxTooApplyFormCtrl'
                })
            //总部促销品
            .state('showProcess.promotionPrdFeeBxApplyForm',
                {
                    url: '/promotionPrdFeeBxApplyForm',
                    templateUrl: 'app/processform/formHtml/marketManagementApply/promotionPrdFeeBxApplyForm.html',
                    controllerUrl: '../app/processform/formHtml/marketManagementApply/promotionPrdFeeBxApplyFormCtrl'
                })
            //总部促销品审批页
            .state('showProcess.promotionPrdFeeBxTooApplyForm',
                {
                    url: '/promotionPrdFeeBxTooApplyForm',
                    templateUrl: 'app/processform/formHtml/marketManagementApply/promotionPrdFeeBxTooApplyForm.html',
                    controllerUrl: '../app/processform/formHtml/marketManagementApply/promotionPrdFeeBxTooApplyFormCtrl'
                })
            //省区促销品表单
            .state('showProcess.promotionSalProductsApplyForm',
                {
                    url: '/promotionSalProductsApplyForm',
                    templateUrl: 'app/processform/formHtml/marketManagementApply/promotionSalProductsApplyForm.html',
                    controllerUrl: '../app/processform/formHtml/marketManagementApply/promotionSalProductsApplyFormFormCtrl'
                })
            //省区促销品报销表单
            .state('showProcess.promotionSalBxApplyForm',
                {
                    url: '/promotionSalBxApplyForm',
                    templateUrl: 'app/processform/formHtml/marketManagementApply/promotionSalBxApplyForm.html',
                    controllerUrl: '../app/processform/formHtml/marketManagementApply/promotionSalBxApplyFormCtrl'
                })
            //省区促销品报销表单审批页
            .state('showProcess.promotionSalBxTooApplyForm',
                {
                    url: '/promotionSalBxTooApplyForm',
                    templateUrl: 'app/processform/formHtml/marketManagementApply/promotionSalBxTooApplyForm.html',
                    controllerUrl: '../app/processform/formHtml/marketManagementApply/promotionSalBxTooApplyFormCtrl'
                })

            //小型广告费用单报销页
            .state('showProcess.smallAdvFeeBxApplyForm',
                {
                    url: '/smallAdvFeeBxApplyForm',
                    templateUrl: 'app/processform/formHtml/marketManagementApply/smallAdvFeeBxApplyForm.html',
                    controllerUrl: '../app/processform/formHtml/marketManagementApply/smallAdvFeeBxApplyFormCtrl'
                })
            //小型广告费用单审批页
            .state('showProcess.smallAdvFeeBxTooApplyForm',
                {
                    url: '/smallAdvFeeBxTooApplyForm',
                    templateUrl: 'app/processform/formHtml/marketManagementApply/smallAdvFeeBxTooApplyForm.html',
                    controllerUrl: '../app/processform/formHtml/marketManagementApply/smallAdvFeeBxTooApplyFormCtrl'
                })

            //小型活动费用单报销页
            .state('showProcess.smallActFeeBxApplyForm',
                {
                    url: '/smallActFeeBxApplyForm',
                    templateUrl: 'app/processform/formHtml/marketManagementApply/smallActFeeBxApplyForm.html',
                    controllerUrl: '../app/processform/formHtml/marketManagementApply/smallActFeeBxApplyFormCtrl'
                })
            //小型活动费用单审批页
            .state('showProcess.smallActFeeBxTooApplyForm',
                {
                    url: '/smallActFeeBxTooApplyForm',
                    templateUrl: 'app/processform/formHtml/marketManagementApply/smallActFeeBxTooApplyForm.html',
                    controllerUrl: '../app/processform/formHtml/marketManagementApply/smallActFeeBxTooApplyFormCtrl'
                })

            /* 市场活动 end */



























            /* lijiayao end */

            /*husaiqiang start*/
            //导购编制申请表单
            .state('showProcess.guideApplyForm',
                {
                    url: '/guideApplyForm',
                    templateUrl: 'app/processform/formHtml/guide/guideApplyForm.html',
                    controllerUrl: '../app/processform/formHtml/guide/guideApplyFormCtrl'
                })
            //打卡异常申请表单
            .state('showProcess.signExpForm',
                {
                    url: '/signExpForm',
                    templateUrl: 'app/processform/formHtml/attendance/signExpForm.html',
                    controllerUrl: '../app/processform/formHtml/attendance/signExpFormCtrl'
                })
            //排班申请表单
            .state('showProcess.schedualApplyForm',
                {
                    url: '/schedualApplyForm',
                    templateUrl: 'app/processform/formHtml/attendance/schedualApplyForm.html',
                    controllerUrl: '../app/processform/formHtml/attendance/schedualApplyFormCtrl'
                })
            //排班调整申请表单
            .state('showProcess.schedualAdjustApplyForm',
                {
                    url: '/schedualAdjustApplyForm',
                    templateUrl: 'app/processform/formHtml/attendance/schedualAdjustApplyForm.html',
                    controllerUrl: '../app/processform/formHtml/attendance/schedualAdjustApplyFormCtrl'
                })
            /*husaiqiang end*/


            //批次修改  add by Ly 2018年8月21日
            .state('showProcess.budgetBatchSave',
                {
                    url: '/budgetBatchSave',
                    templateUrl: 'app/processform/formHtml/templates/ems/budgetBatchSave.html',
                    controllerUrl: '../app/processform/formHtml/templates/ems/budgetBatchSaveCtrl'
                })


            //费用申请  add by hemingxing 2018年8月16日
            //费用申请表单
            .state('showProcess.feeSubjectForm',
                {
                    url: '/feeSubjectForm',
                    templateUrl: 'app/processform/formHtml/templates/ems/feeSubjectForm.html',
                    controllerUrl: '../app/processform/formHtml/templates/ems/feeSubjectFormCtrl'
                })
            //费用报销表单（需申请）
            .state('showProcess.needToApplyForm',
                {
                    url: '/needToApplyForm',
                    templateUrl: 'app/processform/formHtml/templates/ems/needToApplyForm.html',
                    controllerUrl: '../app/processform/formHtml/templates/ems/needToApplyFormCtrl'
                })
            //费用报销表单（不需申请）
            .state('showProcess.noNeedToApplyForm',
                {
                    url: '/noNeedToApplyForm',
                    templateUrl: 'app/processform/formHtml/templates/ems/noNeedToApplyForm.html',
                    controllerUrl: '../app/processform/formHtml/templates/ems/noNeedToApplyFormCtrl'
                })

            //商业发票
            .state('showProcess.invoiceBizApplyHeaderDetail',
                {
                    url: '/invoiceBizApplyHeaderDetail',
                    templateUrl: 'app/ims/templates/inv/invoiceBizApplyHeaderDetail.html',
                    controllerUrl: '../app/ims/templates/inv/invoiceBizApplyHeaderDetailCtrl'
                })
            //商业发票放单申请
            .state('showProcess.invCiReleaseApplyInfoDetail',
                {
                    url: '/invCiReleaseApplyInfoDetail',
                    templateUrl: 'app/ims/templates/inv/invCiReleaseApplyInfoDetail.html',
                    controllerUrl: '../app/ims/templates/inv/invCiReleaseApplyInfoDetailCtrl'
                })
            //物料申领单
            .state('showProcess.csItemApplyDetail',
                {
                    url: '/csItemApplyDetail',
                    templateUrl: 'app/ims/templates/cs/csItemApplyDetail.html',
                    controllerUrl: '../app/ims/templates/cs/csItemApplyDetailCtrl'
                })
            //配件计划
            .state('showProcess.partPlanDetail',
                {
                    url: '/csPartPlanDetail',
                    templateUrl: 'app/ims/templates/cs/csPartPlanDetail.html',
                    controllerUrl: '../app/ims/templates/cs/csPartPlanDetailCtrl'
                })

            //返还发货计划
            .state('showProcess.csReturnPlanDetail',
                {
                    url: '/csReturnPlanDetail',
                    templateUrl: 'app/ims/templates/cs/csReturnPlanDetail.html',
                    controllerUrl: '../app/ims/templates/cs/csReturnPlanDetailCtrl'
                })

            //服务部品申请
            .state('showProcess.ServiceComponApplyForm',
                {
                    url: '/ServiceComponApplyForm',
                    templateUrl: 'app/base/templates/componentRequest/componentRequestDetail.html',
                    controllerUrl: '../app/base/templates/componentRequest/componentRequestDetailCtrl'
                })

            //条款管理详情
            .state('showProcess.ClauseManageForm',
            {
                url: '/ClauseManageForm',
                templateUrl: 'app/base/templates/tta/clause/clauseManageD.html',
                controllerUrl: '../app/base/templates/tta/clause/clauseManageDCtrl'
            })
            //TTA标准模板申请详情
            .state('showProcess.ttaStdApplyHeaderDForm',
                {
                    url: '/ttaStdApplyHeaderDForm',
                    templateUrl: 'app/base/templates/tta/ttasastdtplfield/ttaStdApplyHeaderD.html',
                    controllerUrl: '../app/base/templates/tta/ttasastdtplfield/ttaStdApplyHeaderDCtrl'
                })
            //TTA独家协议(非标准)详情
            .state('showProcess.ttaSoleNonStandarHeaderForm',
                {
                    url: '/ttaSoleNonStandarHeaderForm',
                    templateUrl: 'app/base/templates/tta/exclusive/ttaSoleNonStandarHeaderD.html',
                    controllerUrl: '../app/base/templates/tta/exclusive/ttaSoleNonStandarHeaderDCtrl'
                })
            //TTA补充协议(非标准)详情
            .state('showProcess.ttaSaNonStandarHeaderForm',
                {
                    url: '/ttaSaNonStandarHeaderForm',
                    templateUrl: 'app/base/templates/tta/supplement/ttaSaNonStandarHeaderD.html',
                    controllerUrl: '../app/base/templates/tta/supplement/ttaSaNonStandarHeaderDCtrl'
                })
            //TTA独家协议(标准)详情
            .state('showProcess.ttaSoleStandarHeaderForm',
                {
                    url: '/ttaSoleStandarHeaderForm',
                    templateUrl: 'app/base/templates/tta/exclusive/exclusiveDetail.html',
                    controllerUrl: '../app/base/templates/tta/exclusive/exclusiveDetailCtrl'
                })
            //TTA补充协议(标准)详情
            .state('showProcess.ttaSaStandarHeaderForm',
                {
                    url: '/ttaSaStandarHeaderForm',
                    templateUrl: 'app/base/templates/tta/supplement/supplementAgreementStandardDetail.html',
                    controllerUrl: '../app/base/templates/tta/supplement/supplementAgreementStandardDetailCtrl'
                })
            //合同审核特批申请
            .state('showProcess.ttaContractSpecialHeaderForm',
                {
                    url: '/ttaContractSpecialHeaderForm',
                    templateUrl: 'app/base/templates/tta/contract/ttaContractSpecialHeaderD.html',
                    controllerUrl: '../app/base/templates/tta/contract/ttaContractSpecialHeaderDCtrl'
                })

            //条款名目详情
            .state('showProcess.ClauseItemManageForm',
            {
                url: '/ClauseItemManageForm',
                templateUrl: 'app/base/templates/tta/clauseItemManage/clauseItemManageD.html',
                controllerUrl: '../app/base/templates/tta/clauseItemManage/clauseItemManageDCtrl'
            })

            //Proposal补充协议
            .state('showProcess.supplementDetailForm',
                {
                    url: '/supplementDetailForm',
                    templateUrl: 'app/base/templates/tta/supplement/supplementDetail.html',
                    controllerUrl: '../app/base/templates/tta/supplement/supplementDetailCtrl'
                })
            //独家信息协议
            .state('showProcess.exclusiveDetailForm',
                {
                    url: '/exclusiveDetailForm',
                    templateUrl: 'app/base/templates/tta/exclusive/exclusiveDetail.html',
                    controllerUrl: '../app/base/templates/tta/exclusive/exclusiveDetailCtrl'
                })


            .state('showProcess.posWarehousingForm',
                {
                    url: '/posWarehousingForm',
                    templateUrl: 'app/equ/templates/Equotation/warehousing/equPosWarehousingEdit.html',
                    controllerUrl: '../app/equ/templates/Equotation/warehousing/equPosWarehousingEditCtrl'
                })

            .state('showProcess.creditAuditChangeForm',
                {
                    url: '/creditAuditChangeForm',
                    templateUrl: 'app/equ/templates/Equotation/creditAuditChange/equPosCreditAuditChangeEdit.html',
                    controllerUrl: '../app/equ/templates/Equotation/creditAuditChange/equPosCreditAuditChangeEditCtrl'
                })

            //工单费用申请
            .state('showProcess.IncidentFeeForm',
                {
                    url: '/IncidentFeeForm',
                    templateUrl: 'app/base/templates/incidents/incidentsDetail.html',
                    controllerUrl: '../app/base/templates/incidents/incidentsDetailCtrl'
                })

            //proposal
            .state('showProcess.ProposalForm',
                {
                    url: '/ProposalForm',
                    templateUrl: 'app/base/templates/tta/proposal/proposalDetail.html',
                    controllerUrl: '../app/base/templates/tta/proposal/proposalDetailCtrl'
                })


            .state('showProcess.elecSignDetailForm',
                {
                    url: '/elecSignDetailForm',
                    templateUrl: 'app/base/templates/tta/elecSign/elecSignDetail.html',
                    controllerUrl: '../app/base/templates/tta/elecSign/elecSignDetailCtrl'
                })

            //reprotProcess
            .state('showProcess.TtaReportProcessForm',
                {
                    url: '/TtaReportProcessForm',
                    templateUrl: 'app/base/templates/tta/report/reportProcessHeaderD.html',
                    controllerUrl: '../app/base/templates/tta/report/reportProcessHeaderDCtrl'
                })
                //productAdd 
                .state('showProcess.productForm',
                {
                    url: '/productForm',
                    templateUrl: 'app/base/templates/proMng/finishProcurement/finishProcurement.html',
                    controllerUrl: '../app/base/templates/proMng/finishProcurement/finishProcurementCtrl'
                })
                //productupdate
              .state('showProcess.productupdateForm',
                {
                    url: '/productupdateForm',
                    templateUrl: 'app/base/templates/product/productupdate.html',
                    controllerUrl: '../app/base/templates/product/productupdateCtrl'
                })
            
            .state('showProcess.ponApprovalFrom',
                {
                    url: '/ponApprovalFrom',
                    templateUrl: 'app/equ/templates/Equotation/information/equPonQuotationApprovalEdit.html',
                    controllerUrl: '../app/equ/templates/Equotation/information/equPonQuotationApprovalEditCtrl'
                })




            // .state('showProcess.qualificationForm',
            //     {
            //         url: '/qualificationForm',
            //         templateUrl: 'app/equ/templates/Equotation/qualificationreview/qualificationDetail.html',
            //         controllerUrl: '../app/equ/templates/Equotation/qualificationreview/qualificationDetailCtrl'
            //     })

            //EQU_资质审查流程审批页面
            .state('showProcess.qualificationForm',
                {
                    url: '/qualificationForm',
                    templateUrl: 'app/equ/templates/Equotation/qualificationreview/qualificationDetail.html',
                    controllerUrl: '../app/equ/templates/Equotation/qualificationreview/qualificationDetailCtrl'
                })

            //EQU_供应商档案变更流程审批页面
            .state('showProcess.supplierArchivesChangeForm',
                {
                    url: '/supplierArchivesChangeForm',
                    templateUrl: 'app/equ/templates/Equotation/archivesChange/equPosArchivesChangeDetail.html',
                    controllerUrl: '../app/equ/templates/Equotation/archivesChange/equPosArchivesChangeDetailCtrl'
                })

            //EQU_立项流程审批页面
            .state('showProcess.projectApproveForm',
                {
                    url: '/projectApproveForm',
                    templateUrl: 'app/equ/templates/Equotation/project/equPonProjectDetail.html',
                    controllerUrl: '../app/equ/templates/Equotation/project/equPonProjectDetailCtrl'
                })

            //EQU_评分流程审批页面
            .state('showProcess.scoringApproveForm',
                {
                    url: '/scoringApproveForm',
                    templateUrl: 'app/processform/formHtml/scoring/equPonScoringDetail.html',
                    controllerUrl: '../app/processform/formHtml/scoring/equPonScoringDetailCtrl'
                })


            //信用审核审批
            .state('showProcess.creditAuditForm',
                {
                    url: '/creditAuditForm',
                    templateUrl: 'app/equ/templates/Equotation/creditAudit/equPosCreditAuditEdit.html',
                    controllerUrl: '../app/equ/templates/Equotation/creditAudit/equPosCreditAuditEditCtrl'
                })

            //供应商暂停/淘汰审批
            .state('showProcess.supplierSuspendForm',
                {
                    url: '/supplierSuspendForm',
                    templateUrl: 'app/equ/templates/Equotation/suspend/equPosSuspendEdit.html',
                    controllerUrl: '../app/equ/templates/Equotation/suspend/equPosSuspendEditCtrl'
                })
        // /app/local/nginx/html/equotation/app/equ/templates/Equotation/suspend
            //供应商黑名单审批
            .state('showProcess.posBlacklistForm',
                {
                    url: '/posBlacklistForm',
                    templateUrl: 'app/equ/templates/Equotation/blacklist/equPosBlacklistEdit.html',
                    controllerUrl: '../app/equ/templates/Equotation/blacklist/equPosBlacklistEditCtrl'
                })

            //评分标准审批
            .state('showProcess.ponStandardsForm',
                {
                    url: '/ponStandardsForm',
                    templateUrl: 'app/equ/templates/Equotation/standards/equPonStandardsEdit.html',
                    controllerUrl: '../app/equ/templates/Equotation/standards/equPonStandardsEditCtrl'
                })

                // QA专属审批
                .state('showProcess.qaExclusiveApproval',
                {
                    url: '/qaExclusiveApproval',
                    templateUrl: 'app/base/templates/proMng/qaExclusiveApproval/qaExclusiveApproval.html',
                    controllerUrl: '../app/base/templates/proMng/qaExclusiveApproval/qaExclusiveApprovalCtrl'
                })

                //EQU_供应商临时特批流程审批页面
            .state('showProcess.tempSpecialForm',
                {
                    url: '/tempSpecialForm',
                    templateUrl: 'app/equ/templates/Equotation/tempSpecial/editTempSpecial.html',
                    controllerUrl: '../app/equ/templates/Equotation/tempSpecial/editTempSpecialCtrl'
                })
            //EQU_供应商恢复流程审批页面
            .state('showProcess.posRecoverForm',
                {
                    url: '/posRecoverForm',
                    templateUrl: 'app/equ/templates/Equotation/recover/equPosRecoverEdit.html',
                    controllerUrl: '../app/equ/templates/Equotation/recover/equPosRecoverEditCtrl'
                })
                
                //补货属性流程审批页面
                .state('showProcess.replenishmentForm',
                {
                    url: '/replenishmentForm',
                    templateUrl: 'app/base/templates/proMng/replenishment/newApplicationForm.html',
                    controllerUrl: '../app/base/templates/proMng/replenishment/newApplicationFormCtrl'
                })
                
                
                
    }]);
});
