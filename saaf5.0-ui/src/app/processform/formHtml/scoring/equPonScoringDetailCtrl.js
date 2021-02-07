/**
 * Created by sie_panshibang on 2019/9/4.
 */
define(["app", "angularFileUpload",'XLSX', "pinyin","GooFlow"], function (app, angularFileUpload,XLSX, pinyin) {
    app.useModule('angularFileUpload');//按需加载弹出层模块
    app.controller('equPonScoringDetailCtrl', ['$scope', '$parse', '$filter', '$attrs', "SIE.JS", 'httpServer', 'URLAPI', '$stateParams', 'iframeTabAction','$http','SIEJS','$timeout','$controller', function saafTableController($scope, $parse, $filter, $attrs, JS, httpServer, URLAPI, $stateParams, iframeTabAction,$http,SIEJS,$timeout,$controller) {
        $controller('processBase', {$scope:$scope}); // 继承基础的流程控制器
        if ($stateParams.scoringId){
            var id = $stateParams.scoringId;
        }else if ($scope.urlParams.businessKey){
            var id = $scope.urlParams.businessKey;
        }
        $scope.params = {};
        $scope.program = {};
        $scope.flags = {};
        $scope.supplierInvitationDataTable = [];
        $scope.factoryAuditColumns = [];
        $scope.newConceptRromSupplierColumns = [];
        $scope.componentSupportColumns = [];
        $scope.effectiveAccurateFeedbackColumns = [];
        $scope.paymentTermsColumns = [];
        $scope.currentSupplierPerformanceColumns = [];
        $scope.commercialContractColumns = [];
        $scope.supplierSpendProfileColumns = [];
        $scope.scoringDetailTable = [];
        $scope.invitationSupplierTable = [];
        $scope.panelTestColumns = [];
        $scope.productSpecsDataTable = [];
        $scope.productSpecsFixedDataTable = [];

        $scope.costColumns = [];
        $scope.costProductQuationDataTable = [];
        $scope.costProductScoreDataTable = [];
        $scope.costProductTotalCostParamter = {};
        $scope.costProductTotalScoreParamter = {};

        $scope.panelTestTotalParamter = {};
        $scope.nonPriceCalColumns = [];
        $scope.nonPriceCalTable = [];
        $scope.nonPriceCalTotalParamter = {};
        $scope.nonPriceCalRankParamter = {};

        $scope.quotationCalTotalParamter = {};
        $scope.quotationCalRankParamter = {};

        $scope.supplierInvitationListColumns = [];
        $scope.firstRountParamter = {};
        $scope.secondRountParamter = {};
        $scope.operationRemarkParamter = {};

        $scope.rowsIndex = 5;
        $scope.readonly = 'N';
        $scope.panelTestReadonly = 'Y';
        $scope.factoryAuditReadonly = 'Y';

        //当前登录人所属部门
        $scope.program.deptCode = JSON.parse(sessionStorage[appName + '_successLoginInfo']).deptCode;
        $scope.program.deptName = JSON.parse(sessionStorage[appName + '_successLoginInfo']).deptName;

        $scope.program.scoringStatus = '10';
        $scope.program.scoringStatusMeaning = '拟定';
        $scope.program.departmentName = $scope.program.deptName;
        //当前登录人类型
        $scope.flags.userTpye = JSON.parse(sessionStorage[appName + '_successLoginInfo']).userType;
        $scope.flags.userId = JSON.parse(sessionStorage[appName + '_successLoginInfo']).userId;

        $scope.flags.dataListOptions = '';
        $scope.flags.dataMessage = '';

        $scope.setParams = function(){
            $scope.scoringDetailTable = [];
            if($scope.program.supplierConfirmFlag == 'Y'){
                //Cost-报价
                for(var i = 0; i < $scope.costColumns.length; i++){
                    for(var j = 0; j < $scope.costProductQuationDataTable.length; j++){
                        var supplierNumber = $scope.costColumns[i].supplierNumber;
                        var supplierId = $scope.costColumns[i].supplierId;
                        var json = $scope.costProductQuationDataTable[j][supplierId];
                        var obj = {
                            scoringType: 'costQuotation',
                            scoringItem: $scope.costProductQuationDataTable[j].productName,
                            supplierId: $scope.panelTestColumns[i].supplierId,
                            annualSalesQuantity: $scope.costProductQuationDataTable[j].annualSalesQuantity,
                            quotationPrice: $scope.costProductQuationDataTable[j][supplierNumber],
                            scoringDetailId : json == undefined ? '' : json.scoringDetailId,
                            versionNum : json == undefined ? '' : json.versionNum,
                            scoringId : json == undefined ? '' : json.scoringId,
                            creationDate : json == undefined ? '' : json.creationDate,
                            createdBy : json == undefined ? '' : json.createdBy,
                            lastUpdatedBy : json == undefined ? '' : json.lastUpdatedBy,
                            lastUpdateDate : json == undefined ? '' : json.lastUpdateDate,
                            lastUpdateLogin : json == undefined ? '' : json.lastUpdateLogin
                        };
                        $scope.scoringDetailTable.push(obj);
                    }
                }
                //Cost-采购金额
                for(var i = 0; i < $scope.costColumns.length; i++){
                    //采购金额汇总行
                    var supplierNumber = $scope.costColumns[i].supplierNumber;
                    var supplierId = $scope.costColumns[i].supplierId;
                    var json = $scope.costProductTotalCostParamter[supplierId];
                    var obj = {
                        scoringType: 'costTotalCost',
                        scoringItem: 'totalCost',
                        supplierId: $scope.costColumns[i].supplierId,
                        purchaseVolume: $scope.costProductTotalCostParamter[supplierNumber],
                        scoringDetailId : json == undefined ? '' : json.scoringDetailId,
                        versionNum : json == undefined ? '' : json.versionNum,
                        scoringId : json == undefined ? '' : json.scoringId,
                        creationDate : json == undefined ? '' : json.creationDate,
                        createdBy : json == undefined ? '' : json.createdBy,
                        lastUpdatedBy : json == undefined ? '' : json.lastUpdatedBy,
                        lastUpdateDate : json == undefined ? '' : json.lastUpdateDate,
                        lastUpdateLogin : json == undefined ? '' : json.lastUpdateLogin
                    };
                    $scope.scoringDetailTable.push(obj);

                    //分数行
                    var supplierNumber = $scope.costColumns[i].supplierNumber;
                    var supplierId = $scope.costColumns[i].supplierId;
                    var json = $scope.costProductTotalScoreParamter[supplierId];
                    var obj = {
                        scoringType: 'costTotalScore',
                        scoringItem: 'totalScore',
                        supplierId: $scope.costColumns[i].supplierId,
                        score: $scope.costProductTotalScoreParamter[supplierNumber],
                        scoringDetailId : json == undefined ? '' : json.scoringDetailId,
                        versionNum : json == undefined ? '' : json.versionNum,
                        scoringId : json == undefined ? '' : json.scoringId,
                        creationDate : json == undefined ? '' : json.creationDate,
                        createdBy : json == undefined ? '' : json.createdBy,
                        lastUpdatedBy : json == undefined ? '' : json.lastUpdatedBy,
                        lastUpdateDate : json == undefined ? '' : json.lastUpdateDate,
                        lastUpdateLogin : json == undefined ? '' : json.lastUpdateLogin
                    };
                    $scope.scoringDetailTable.push(obj);

                    for(var j = 0; j < $scope.costProductScoreDataTable.length; j++){
                        var supplierNumber = $scope.costColumns[i].supplierNumber;
                        var supplierId = $scope.costColumns[i].supplierId;
                        var json = $scope.costProductScoreDataTable[j][supplierId];
                        var obj = {
                            scoringType: 'costScoring',
                            scoringItem: $scope.costProductScoreDataTable[j].productName,
                            supplierId: $scope.panelTestColumns[i].supplierId,
                            purchaseVolume: $scope.costProductScoreDataTable[j][supplierNumber],
                            scoringDetailId : json == undefined ? '' : json.scoringDetailId,
                            versionNum : json == undefined ? '' : json.versionNum,
                            scoringId : json == undefined ? '' : json.scoringId,
                            creationDate : json == undefined ? '' : json.creationDate,
                            createdBy : json == undefined ? '' : json.createdBy,
                            lastUpdatedBy : json == undefined ? '' : json.lastUpdatedBy,
                            lastUpdateDate : json == undefined ? '' : json.lastUpdateDate,
                            lastUpdateLogin : json == undefined ? '' : json.lastUpdateLogin
                        };
                        $scope.scoringDetailTable.push(obj);
                    }
                }

                //Panel Test
                for(var i = 0; i < $scope.panelTestColumns.length; i++){
                    for(var j = 0; j < $scope.productSpecsDataTable.length; j++){
                        var supplierNumber = $scope.panelTestColumns[i].supplierNumber;
                        var supplierId = $scope.panelTestColumns[i].supplierId;
                        var json = $scope.productSpecsDataTable[j][supplierId];
                        var obj = {
                            scoringType: 'panelTest',
                            scoringItem: $scope.productSpecsDataTable[j].productName,
                            supplierId: $scope.panelTestColumns[i].supplierId,
                            score: $scope.productSpecsDataTable[j][supplierNumber],
                            highestScore: $scope.productSpecsDataTable[j].highestScore,
                            benchmark: $scope.productSpecsDataTable[j].benchmark,
                            scoringDetailId : json == undefined ? '' : json.scoringDetailId,
                            versionNum : json == undefined ? '' : json.versionNum,
                            scoringId : json == undefined ? '' : json.scoringId,
                            creationDate : json == undefined ? '' : json.creationDate,
                            createdBy : json == undefined ? '' : json.createdBy,
                            lastUpdatedBy : json == undefined ? '' : json.lastUpdatedBy,
                            lastUpdateDate : json == undefined ? '' : json.lastUpdateDate,
                            lastUpdateLogin : json == undefined ? '' : json.lastUpdateLogin
                        };
                        $scope.scoringDetailTable.push(obj);
                    }
                }

                //Panel Test(Calculation)
                for(var i = 0; i < $scope.panelTestColumns.length; i++){
                    //汇总行
                    var supplierNumber = $scope.panelTestColumns[i].supplierNumber;
                    var supplierId = $scope.panelTestColumns[i].supplierId;
                    var json = $scope.panelTestTotalParamter[supplierId];
                    var obj = {
                        scoringType: 'panelTestTotalScore',
                        scoringItem: 'totalScore',
                        supplierId: $scope.panelTestColumns[i].supplierId,
                        score: $scope.panelTestTotalParamter[supplierNumber],
                        annualSalesAmount: $scope.panelTestTotalParamter.annualSalesAmount,
                        participation: $scope.panelTestTotalParamter.participation,
                        benchmark: $scope.panelTestTotalParamter.benchmark,
                        scoringDetailId : json == undefined ? '' : json.scoringDetailId,
                        versionNum : json == undefined ? '' : json.versionNum,
                        scoringId : json == undefined ? '' : json.scoringId,
                        creationDate : json == undefined ? '' : json.creationDate,
                        createdBy : json == undefined ? '' : json.createdBy,
                        lastUpdatedBy : json == undefined ? '' : json.lastUpdatedBy,
                        lastUpdateDate : json == undefined ? '' : json.lastUpdateDate,
                        lastUpdateLogin : json == undefined ? '' : json.lastUpdateLogin
                    };
                    $scope.scoringDetailTable.push(obj);

                    for(var j = 0; j < $scope.productSpecsFixedDataTable.length; j++){
                        var supplierNumber = $scope.panelTestColumns[i].supplierNumber;
                        var supplierId = $scope.panelTestColumns[i].supplierId;
                        var json = $scope.productSpecsFixedDataTable[j][supplierId];
                        var obj = {
                            scoringType: 'panelTestCalculation',
                            scoringItem: $scope.productSpecsFixedDataTable[j].productName,
                            supplierId: $scope.panelTestColumns[i].supplierId,
                            score: $scope.productSpecsFixedDataTable[j][supplierNumber],
                            annualSalesAmount: $scope.productSpecsFixedDataTable[j].annualSalesAmount,
                            participation: $scope.productSpecsFixedDataTable[j].participation,
                            benchmark: $scope.productSpecsFixedDataTable[j].benchmark,
                            scoringDetailId : json == undefined ? '' : json.scoringDetailId,
                            versionNum : json == undefined ? '' : json.versionNum,
                            scoringId : json == undefined ? '' : json.scoringId,
                            creationDate : json == undefined ? '' : json.creationDate,
                            createdBy : json == undefined ? '' : json.createdBy,
                            lastUpdatedBy : json == undefined ? '' : json.lastUpdatedBy,
                            lastUpdateDate : json == undefined ? '' : json.lastUpdateDate,
                            lastUpdateLogin : json == undefined ? '' : json.lastUpdateLogin
                        };
                        $scope.scoringDetailTable.push(obj);
                    }
                }
                //非价格资料计算
                for(var i = 0; i < $scope.nonPriceCalColumns.length; i++){
                    // 汇总行
                    var supplierNumber = $scope.nonPriceCalColumns[i].supplierNumber;
                    var supplierId = $scope.nonPriceCalColumns[i].supplierId;
                    var json = $scope.nonPriceCalTotalParamter[supplierId];
                    var obj = {
                        scoringType: 'nonPriceCalTotalScore',
                        scoringItem: 'totalScore',
                        supplierId: $scope.nonPriceCalColumns[i].supplierId,
                        score: $scope.nonPriceCalTotalParamter[supplierNumber],
                        lineNumber:$scope.nonPriceCalTotalParamter.lineNumber,
                        lineLv:$scope.nonPriceCalTotalParamter.lineLv,
                        weighting:$scope.nonPriceCalTotalParamter.weight,
                        scoringDetailId : json == undefined ? '' : json.scoringDetailId,
                        versionNum : json == undefined ? '' : json.versionNum,
                        scoringId : json == undefined ? '' : json.scoringId,
                        creationDate : json == undefined ? '' : json.creationDate,
                        createdBy : json == undefined ? '' : json.createdBy,
                        lastUpdatedBy : json == undefined ? '' : json.lastUpdatedBy,
                        lastUpdateDate : json == undefined ? '' : json.lastUpdateDate,
                        lastUpdateLogin : json == undefined ? '' : json.lastUpdateLogin
                    };
                    $scope.scoringDetailTable.push(obj);

                    // 排名行
                    var supplierNumber = $scope.nonPriceCalColumns[i].supplierNumber;
                    var supplierId = $scope.nonPriceCalColumns[i].supplierId;
                    var json = $scope.nonPriceCalRankParamter[supplierId];
                    var obj = {
                        scoringType: 'nonPriceCalRanking',
                        scoringItem: 'totalRanking',
                        supplierId: $scope.nonPriceCalColumns[i].supplierId,
                        score: '',
                        lineNumber:'',
                        lineLv:'',
                        weighting:'',
                        scoringRanking:$scope.nonPriceCalRankParamter[supplierNumber],
                        scoringDetailId : json == undefined ? '' : json.scoringDetailId,
                        versionNum : json == undefined ? '' : json.versionNum,
                        scoringId : json == undefined ? '' : json.scoringId,
                        creationDate : json == undefined ? '' : json.creationDate,
                        createdBy : json == undefined ? '' : json.createdBy,
                        lastUpdatedBy : json == undefined ? '' : json.lastUpdatedBy,
                        lastUpdateDate : json == undefined ? '' : json.lastUpdateDate,
                        lastUpdateLogin : json == undefined ? '' : json.lastUpdateLogin
                    };
                    $scope.scoringDetailTable.push(obj);

                    // 报价结果-汇总行
                    var supplierNumber = $scope.nonPriceCalColumns[i].supplierNumber;
                    var supplierId = $scope.nonPriceCalColumns[i].supplierId;
                    var json = $scope.quotationCalTotalParamter[supplierId];
                    var obj = {
                        scoringType: 'quotationTotalResult',
                        scoringItem: 'totalScore',
                        supplierId: $scope.nonPriceCalColumns[i].supplierId,
                        score: $scope.quotationCalTotalParamter[supplierNumber],
                        lineNumber:$scope.quotationCalTotalParamter.lineNumber,
                        lineLv:$scope.quotationCalTotalParamter.lineLv,
                        weighting:$scope.quotationCalTotalParamter.weight,
                        scoringDetailId : json == undefined ? '' : json.scoringDetailId,
                        versionNum : json == undefined ? '' : json.versionNum,
                        scoringId : json == undefined ? '' : json.scoringId,
                        creationDate : json == undefined ? '' : json.creationDate,
                        createdBy : json == undefined ? '' : json.createdBy,
                        lastUpdatedBy : json == undefined ? '' : json.lastUpdatedBy,
                        lastUpdateDate : json == undefined ? '' : json.lastUpdateDate,
                        lastUpdateLogin : json == undefined ? '' : json.lastUpdateLogin
                    };
                    $scope.scoringDetailTable.push(obj);

                    // 报价结果-排名行
                    var supplierNumber = $scope.nonPriceCalColumns[i].supplierNumber;
                    var supplierId = $scope.nonPriceCalColumns[i].supplierId;
                    var json = $scope.quotationCalRankParamter[supplierId];
                    var obj = {
                        scoringType: 'quotationTotalResult',
                        scoringItem: 'totalRanking',
                        supplierId: $scope.nonPriceCalColumns[i].supplierId,
                        score: '',
                        lineNumber:'',
                        lineLv:'',
                        weighting:'',
                        scoringRanking:$scope.quotationCalRankParamter[supplierNumber],
                        scoringDetailId : json == undefined ? '' : json.scoringDetailId,
                        versionNum : json == undefined ? '' : json.versionNum,
                        scoringId : json == undefined ? '' : json.scoringId,
                        creationDate : json == undefined ? '' : json.creationDate,
                        createdBy : json == undefined ? '' : json.createdBy,
                        lastUpdatedBy : json == undefined ? '' : json.lastUpdatedBy,
                        lastUpdateDate : json == undefined ? '' : json.lastUpdateDate,
                        lastUpdateLogin : json == undefined ? '' : json.lastUpdateLogin
                    };
                    $scope.scoringDetailTable.push(obj);

                    for(var j = 0; j < $scope.nonPriceCalTable.length; j++){
                        var supplierNumber = $scope.nonPriceCalColumns[i].supplierNumber;
                        var supplierId = $scope.nonPriceCalColumns[i].supplierId;
                        var json = $scope.nonPriceCalTable[j][supplierId];
                        var obj = {
                            scoringType: 'nonPriceCal',
                            scoringItem: $scope.nonPriceCalTable[j].gradingDivision,
                            supplierId: $scope.nonPriceCalColumns[i].supplierId,
                            score: $scope.nonPriceCalTable[j][supplierNumber],
                            lineNumber:$scope.nonPriceCalTable[j].lineNumber,
                            lineLv:$scope.nonPriceCalTable[j].lineLv,
                            weighting:$scope.nonPriceCalTable[j].weight,
                            scoringDetailId : json == undefined ? '' : json.scoringDetailId,
                            versionNum : json == undefined ? '' : json.versionNum,
                            scoringId : json == undefined ? '' : json.scoringId,
                            creationDate : json == undefined ? '' : json.creationDate,
                            createdBy : json == undefined ? '' : json.createdBy,
                            lastUpdatedBy : json == undefined ? '' : json.lastUpdatedBy,
                            lastUpdateDate : json == undefined ? '' : json.lastUpdateDate,
                            lastUpdateLogin : json == undefined ? '' : json.lastUpdateLogin
                        };
                        $scope.scoringDetailTable.push(obj);
                    }
                }
                //Factory audit
                for(var i = 0; i < $scope.factoryAuditColumns.length; i++){
                    var obj = {
                        scoringType: 'factoryAudit',
                        scoringItem:'defaultItem',
                        supplierId: $scope.factoryAuditColumns[i].supplierId,
                        score: $scope.factoryAuditColumns[i].defaultItem,
                        scoringDetailId : $scope.factoryAuditColumns[i].scoringDetailId,
                        versionNum : $scope.factoryAuditColumns[i].versionNum,
                        scoringId : $scope.factoryAuditColumns[i].scoringId,
                        creationDate : $scope.factoryAuditColumns[i].creationDate,
                        createdBy : $scope.factoryAuditColumns[i].createdBy,
                        lastUpdatedBy : $scope.factoryAuditColumns[i].lastUpdatedBy,
                        lastUpdateDate : $scope.factoryAuditColumns[i].lastUpdateDate,
                        lastUpdateLogin : $scope.factoryAuditColumns[i].lastUpdateLogin
                    };
                    $scope.scoringDetailTable.push(obj);
                }

                //New concept from supplier
                for(var i = 0; i < $scope.newConceptRromSupplierColumns.length; i++){
                    var obj = {
                        scoringType: 'newConceptRromSupplier',
                        scoringItem:'defaultItem',
                        supplierId: $scope.newConceptRromSupplierColumns[i].supplierId,
                        score: $scope.newConceptRromSupplierColumns[i].defaultItem,
                        scoringDetailId : $scope.newConceptRromSupplierColumns[i].defaultScoringDetailId,
                        versionNum : $scope.newConceptRromSupplierColumns[i].defaultVersionNum,
                        scoringId : $scope.newConceptRromSupplierColumns[i].scoringId,
                        creationDate : $scope.newConceptRromSupplierColumns[i].creationDate,
                        createdBy : $scope.newConceptRromSupplierColumns[i].createdBy,
                        lastUpdatedBy : $scope.newConceptRromSupplierColumns[i].lastUpdatedBy,
                        lastUpdateDate : $scope.newConceptRromSupplierColumns[i].lastUpdateDate,
                        lastUpdateLogin : $scope.newConceptRromSupplierColumns[i].lastUpdateLogin
                    };
                    $scope.scoringDetailTable.push(obj);

                    var obj = {
                        scoringType: 'newConceptRromSupplier',
                        scoringItem:'totalScore',
                        supplierId: $scope.newConceptRromSupplierColumns[i].supplierId,
                        score: $scope.newConceptRromSupplierColumns[i].totalScore,
                        scoringDetailId : $scope.newConceptRromSupplierColumns[i].totalScoringDetailId,
                        versionNum : $scope.newConceptRromSupplierColumns[i].totalVersionNum,
                        scoringId : $scope.newConceptRromSupplierColumns[i].scoringId,
                        creationDate : $scope.newConceptRromSupplierColumns[i].creationDate,
                        createdBy : $scope.newConceptRromSupplierColumns[i].createdBy,
                        lastUpdatedBy : $scope.newConceptRromSupplierColumns[i].lastUpdatedBy,
                        lastUpdateDate : $scope.newConceptRromSupplierColumns[i].lastUpdateDate,
                        lastUpdateLogin : $scope.newConceptRromSupplierColumns[i].lastUpdateLogin
                    };
                    $scope.scoringDetailTable.push(obj);
                }

                //Component support
                for(var i = 0; i < $scope.componentSupportColumns.length; i++){
                    var obj = {
                        scoringType: 'componentSupport',
                        scoringItem:'innerPackage',
                        supplierId: $scope.componentSupportColumns[i].supplierId,
                        score: $scope.componentSupportColumns[i].innerPackage,
                        scoringDetailId : $scope.componentSupportColumns[i].innerScoringDetailId,
                        versionNum : $scope.componentSupportColumns[i].innerVersionNum,
                        scoringId : $scope.componentSupportColumns[i].scoringId,
                        // scoringType : $scope.componentSupportColumns[i].scoringType,
                        // scoringItem : $scope.componentSupportColumns[i].innerScoringItem,
                        creationDate : $scope.componentSupportColumns[i].creationDate,
                        createdBy : $scope.componentSupportColumns[i].createdBy,
                        lastUpdatedBy : $scope.componentSupportColumns[i].lastUpdatedBy,
                        lastUpdateDate : $scope.componentSupportColumns[i].lastUpdateDate,
                        lastUpdateLogin : $scope.componentSupportColumns[i].lastUpdateLogin
                    };
                    $scope.scoringDetailTable.push(obj);

                    var obj = {
                        scoringType: 'componentSupport',
                        scoringItem:'outerPackage',
                        supplierId: $scope.componentSupportColumns[i].supplierId,
                        score: $scope.componentSupportColumns[i].outerPackage,
                        scoringDetailId : $scope.componentSupportColumns[i].outerScoringDetailId,
                        versionNum : $scope.componentSupportColumns[i].outerVersionNum,
                        scoringId : $scope.componentSupportColumns[i].scoringId,
                        // scoringType : $scope.componentSupportColumns[i].scoringType,
                        // scoringItem : $scope.componentSupportColumns[i].outerScoringItem,
                        creationDate : $scope.componentSupportColumns[i].creationDate,
                        createdBy : $scope.componentSupportColumns[i].createdBy,
                        lastUpdatedBy : $scope.componentSupportColumns[i].lastUpdatedBy,
                        lastUpdateDate : $scope.componentSupportColumns[i].lastUpdateDate,
                        lastUpdateLogin : $scope.componentSupportColumns[i].lastUpdateLogin
                    };
                    $scope.scoringDetailTable.push(obj);

                    var obj = {
                        scoringType: 'componentSupport',
                        scoringItem:'totalScore',
                        supplierId: $scope.componentSupportColumns[i].supplierId,
                        score: $scope.componentSupportColumns[i].totalScore,
                        scoringDetailId : $scope.componentSupportColumns[i].totalScoringDetailId,
                        versionNum : $scope.componentSupportColumns[i].totalVersionNum,
                        scoringId : $scope.componentSupportColumns[i].scoringId,
                        // scoringType : $scope.componentSupportColumns[i].scoringType,
                        // scoringItem : $scope.componentSupportColumns[i].totalScoringItem,
                        creationDate : $scope.componentSupportColumns[i].creationDate,
                        createdBy : $scope.componentSupportColumns[i].createdBy,
                        lastUpdatedBy : $scope.componentSupportColumns[i].lastUpdatedBy,
                        lastUpdateDate : $scope.componentSupportColumns[i].lastUpdateDate,
                        lastUpdateLogin : $scope.componentSupportColumns[i].lastUpdateLogin
                    };
                    $scope.scoringDetailTable.push(obj);
                }

                //Effective & accurate feedback
                for(var i = 0; i < $scope.effectiveAccurateFeedbackColumns.length; i++){
                    var obj = {
                        scoringType: 'effectiveAccurateFeedback',
                        scoringItem:'defaultItem',
                        supplierId: $scope.effectiveAccurateFeedbackColumns[i].supplierId,
                        score: $scope.effectiveAccurateFeedbackColumns[i].defaultItem,
                        scoringDetailId : $scope.effectiveAccurateFeedbackColumns[i].scoringDetailId,
                        versionNum : $scope.effectiveAccurateFeedbackColumns[i].versionNum,
                        scoringId : $scope.effectiveAccurateFeedbackColumns[i].scoringId,
                        creationDate : $scope.effectiveAccurateFeedbackColumns[i].creationDate,
                        createdBy : $scope.effectiveAccurateFeedbackColumns[i].createdBy,
                        lastUpdatedBy : $scope.effectiveAccurateFeedbackColumns[i].lastUpdatedBy,
                        lastUpdateDate : $scope.effectiveAccurateFeedbackColumns[i].lastUpdateDate,
                        lastUpdateLogin : $scope.effectiveAccurateFeedbackColumns[i].lastUpdateLogin
                    };
                    $scope.scoringDetailTable.push(obj);
                }

                //Payment terms
                for(var i = 0; i < $scope.paymentTermsColumns.length; i++){
                    var obj = {
                        scoringType: 'paymentTerms',
                        scoringItem:'paymentTerms',
                        supplierId: $scope.paymentTermsColumns[i].supplierId,
                        score: $scope.paymentTermsColumns[i].paymentTerms,
                        scoringDetailId : $scope.paymentTermsColumns[i].scoringDetailId,
                        versionNum : $scope.paymentTermsColumns[i].versionNum,
                        scoringId : $scope.paymentTermsColumns[i].scoringId,
                        creationDate : $scope.paymentTermsColumns[i].creationDate,
                        createdBy : $scope.paymentTermsColumns[i].createdBy,
                        lastUpdatedBy : $scope.paymentTermsColumns[i].lastUpdatedBy,
                        lastUpdateDate : $scope.paymentTermsColumns[i].lastUpdateDate,
                        lastUpdateLogin : $scope.paymentTermsColumns[i].lastUpdateLogin
                    };
                    $scope.scoringDetailTable.push(obj);
                }

                //Current supplier performance
                for(var i = 0; i < $scope.currentSupplierPerformanceColumns.length; i++){
                    var obj = {
                        scoringType: 'currentSupplierPerformance',
                        scoringItem:'defaultItem',
                        supplierId: $scope.currentSupplierPerformanceColumns[i].supplierId,
                        score: $scope.currentSupplierPerformanceColumns[i].defaultItem,
                        scoringDetailId : $scope.currentSupplierPerformanceColumns[i].scoringDetailId,
                        versionNum : $scope.currentSupplierPerformanceColumns[i].versionNum,
                        scoringId : $scope.currentSupplierPerformanceColumns[i].scoringId,
                        creationDate : $scope.currentSupplierPerformanceColumns[i].creationDate,
                        createdBy : $scope.currentSupplierPerformanceColumns[i].createdBy,
                        lastUpdatedBy : $scope.currentSupplierPerformanceColumns[i].lastUpdatedBy,
                        lastUpdateDate : $scope.currentSupplierPerformanceColumns[i].lastUpdateDate,
                        lastUpdateLogin : $scope.currentSupplierPerformanceColumns[i].lastUpdateLogin
                    };
                    $scope.scoringDetailTable.push(obj);
                }

                //Commercial contract
                for(var i = 0; i < $scope.commercialContractColumns.length; i++){
                    var obj = {
                        scoringType: 'commercialContract',
                        scoringItem:'defaultItem',
                        supplierId: $scope.commercialContractColumns[i].supplierId,
                        score: $scope.commercialContractColumns[i].defaultItem,
                        scoringDetailId : $scope.commercialContractColumns[i].scoringDetailId,
                        versionNum : $scope.commercialContractColumns[i].versionNum,
                        scoringId : $scope.commercialContractColumns[i].scoringId,
                        creationDate : $scope.commercialContractColumns[i].creationDate,
                        createdBy : $scope.commercialContractColumns[i].createdBy,
                        lastUpdatedBy : $scope.commercialContractColumns[i].lastUpdatedBy,
                        lastUpdateDate : $scope.commercialContractColumns[i].lastUpdateDate,
                        lastUpdateLogin : $scope.commercialContractColumns[i].lastUpdateLogin
                    };
                    $scope.scoringDetailTable.push(obj);
                }

                //Supplier spend profile
                for(var i = 0; i < $scope.supplierSpendProfileColumns.length; i++){
                    var obj = {
                        scoringType: 'supplierSpendProfile',
                        scoringItem:'defaultItem',
                        supplierId: $scope.supplierSpendProfileColumns[i].supplierId,
                        score: $scope.supplierSpendProfileColumns[i].defaultItem,
                        scoringDetailId : $scope.supplierSpendProfileColumns[i].scoringDetailId,
                        versionNum : $scope.supplierSpendProfileColumns[i].versionNum,
                        scoringId : $scope.supplierSpendProfileColumns[i].scoringId,
                        creationDate : $scope.supplierSpendProfileColumns[i].creationDate,
                        createdBy : $scope.supplierSpendProfileColumns[i].createdBy,
                        lastUpdatedBy : $scope.supplierSpendProfileColumns[i].lastUpdatedBy,
                        lastUpdateDate : $scope.supplierSpendProfileColumns[i].lastUpdateDate,
                        lastUpdateLogin : $scope.supplierSpendProfileColumns[i].lastUpdateLogin
                    };
                    $scope.scoringDetailTable.push(obj);
                }

                $scope.invitationSupplierTable = [];
                for(var i = 0; i < $scope.supplierInvitationListColumns.length; i++){
                    var supplierNumber = $scope.supplierInvitationListColumns[i].supplierNumber;
                    var supplierId = $scope.supplierInvitationListColumns[i].supplierId;
                    var json = $scope.operationRemarkParamter[supplierId];
                    json.reason = $scope.operationRemarkParamter[supplierNumber];
                    $scope.invitationSupplierTable.push(json);
                }
            }

            $scope.params.scoringHeaderInfo = $scope.program;
            $scope.params.scoringDetailInfo = $scope.scoringDetailTable;
            $scope.params.invitationSupplierInfo = $scope.invitationSupplierTable;
        }

        /********************保存立项单据信息********************/
        $scope.btnSave = function () {
            //拼装参数
            $scope.setParams();
            httpServer.post(URLAPI.saveScoringInfo, {
                'params': JSON.stringify($scope.params)
            }, function (res) {
                if ('S' == res.status) {
                    $scope.program = res.data;
                    $scope.search($scope.program.scoringId);
                    JS.alert('保存成功');
                } else {
                    JS.alert(res.msg, 'error', '确定');
                }
            }, function (error) {
                console.error(error);
                JS.alert('保存失败', 'error', '确定');
            })
        }

        //查询评分单据相关信息
        $scope.search = function (id) {
            httpServer.post(URLAPI.findScoringInfoForFlow, {
                params: JSON.stringify({
                    scoringId: id
                })
            }, function (res) {
                if (res.status == 'S') {
                    $scope.program = res.data[0];

                    $scope.processInstanceParams = {
                        processInstanceId: $scope.program.procInstId
                    };
                    $scope.taskTable.search();

                    //查询邀请供应商列表
                    $scope.searchSupplierInvitationInfo();
                    debugger;
                    if($scope.program.scoringStatus == '10' || $scope.program.scoringStatus == '40'){
                        $scope.readonly = 'N';
                    }else{
                        $scope.readonly = 'Y';
                    }

                    // if(($scope.program.scoringStatus == '30' && $scope.program.sensoryTestTypes == '10') || ($scope.readonly && $scope.program.sensoryTestTypes == '20')){
                    //     $scope.panelTestReadonly = 'Y';
                    // }else{
                    //     $scope.panelTestReadonly = 'N';
                    // }

                    if($scope.program.scoringStatus == '30'){
                        if($scope.program.sensoryTestTypes == '10' && $scope.program.createdBy != $scope.flags.userId && $scope.program.panelTestFlag != 'Y'){
                            $scope.panelTestReadonly = 'N';
                        }else if($scope.program.sensoryTestTypes == '20' && $scope.program.createdBy == $scope.flags.userId){
                            $scope.panelTestReadonly = 'N';
                        }else{
                            $scope.panelTestReadonly = 'Y';
                        }
                    }else{
                        $scope.panelTestReadonly = 'Y';
                    }

                    if($scope.program.createdBy == $scope.flags.userId){
                        $scope.factoryAuditReadonly = 'Y';
                    }else{
                        $scope.factoryAuditReadonly = 'N';
                    }
                }
            }, function (error) {
                console.error(error)
            })
        }

        /********************查询邀请供应商列表**********************/
        $scope.searchSupplierInvitationInfo = function () {
            httpServer.post(URLAPI.findSupplierInvitation, {
                params: JSON.stringify({
                    projectId: $scope.program.projectId
                })
            }, function (res) {
                if (res.status == 'S') {
                    $scope.supplierInvitationDataTable = res.data;
                    if($scope.program.supplierConfirmFlag == 'Y'){

                        // var options = '{';
                        // options = options + 'itemDescription:{headName:\'item description\'},';
                        // options = options + 'highestScore:{headName:\'Highest Score\'},';
                        // options = options + 'benchmark:{headName:\'benchmark\'},';
                        //
                        // var message = '';

                            $scope.costColumns = [];
                        for(var i = 0; i < $scope.supplierInvitationDataTable.length; i++){
                            //Panel test
                            if($scope.supplierInvitationDataTable[i].isQuit != 'Y'){
                                var obj = {
                                    supplierId: $scope.supplierInvitationDataTable[i].supplierId,
                                    supplierName: $scope.supplierInvitationDataTable[i].supplierName,
                                    supplierNumber:$scope.supplierInvitationDataTable[i].supplierNumber
                                };
                                $scope.costColumns.push(obj);
                            }
                            // //设置paneltest导入所需信息
                            // if(i == $scope.supplierInvitationDataTable.length - 1){
                            //     options = options + 'supplier' + i +':{headName:\'' + $scope.supplierInvitationDataTable[i].supplierName + '\'}}';
                            //     message = message + $scope.supplierInvitationDataTable[i].supplierName;
                            // }else{
                            //     options = options + 'supplier' + i +':{headName:\'' + $scope.supplierInvitationDataTable[i].supplierName + '\'},';
                            //     message = message + $scope.supplierInvitationDataTable[i].supplierName + '/';
                            // }
                        }

                        // $scope.flags.dataListOptions = eval('(' + options + ')');
                        // $scope.flags.dataMessage = eval('(' + message + ')');

                        $scope.panelTestColumns = [];
                        for(var i = 0; i < $scope.supplierInvitationDataTable.length; i++){
                            //Panel test
                            if($scope.supplierInvitationDataTable[i].isQuit != 'Y'){
                                var obj = {
                                    supplierId: $scope.supplierInvitationDataTable[i].supplierId,
                                    supplierName: $scope.supplierInvitationDataTable[i].supplierName,
                                    supplierNumber:$scope.supplierInvitationDataTable[i].supplierNumber
                                };
                                $scope.panelTestColumns.push(obj);
                            }
                        }

                        $scope.nonPriceCalColumns = [];
                        for(var i = 0; i < $scope.supplierInvitationDataTable.length; i++){
                            //Panel test
                            if($scope.supplierInvitationDataTable[i].isQuit != 'Y'){
                                var obj = {
                                    supplierId: $scope.supplierInvitationDataTable[i].supplierId,
                                    supplierName: $scope.supplierInvitationDataTable[i].supplierName,
                                    supplierNumber:$scope.supplierInvitationDataTable[i].supplierNumber
                                };
                                $scope.nonPriceCalColumns.push(obj);
                            }
                        }

                        $scope.supplierInvitationListColumns = [];
                        for(var i = 0; i < $scope.supplierInvitationDataTable.length; i++){
                            var supplierId = $scope.supplierInvitationDataTable[i].supplierId;
                            var supplierName = $scope.supplierInvitationDataTable[i].supplierName;
                            var supplierNumber = $scope.supplierInvitationDataTable[i].supplierNumber;
                            var isQuit = $scope.supplierInvitationDataTable[i].isQuit;
                            var reason = $scope.supplierInvitationDataTable[i].reason;
                            var obj = {
                                supplierId: supplierId,
                                supplierName: supplierName,
                                supplierNumber:supplierNumber
                            };
                            $scope.supplierInvitationListColumns.push(obj);

                            $scope.firstRountParamter[supplierNumber] = isQuit == 'Y' ? 'N' : 'Y';
                            $scope.secondRountParamter[supplierNumber] = isQuit == 'Y' ? 'N' : 'Y';
                            $scope.operationRemarkParamter[supplierNumber] = reason;
                            $scope.operationRemarkParamter[supplierId] = $scope.supplierInvitationDataTable[i];
                        }

                        //非价格资料计算
                        $scope.nonPriceCalTable = [];
                        httpServer.post(URLAPI.findPonStandardsDatail, {
                            params: JSON.stringify({
                                standardsId: $scope.program.standardsId
                            })
                        }, function (res) {
                            if (res.status == 'S') {
                                $scope.standardsTemp = res.data.lineData;
                                httpServer.post(URLAPI.findScoringDetail, {
                                    params: JSON.stringify({
                                        scoringId: $scope.program.scoringId,
                                        scoringType: 'nonPriceCal'
                                    })
                                }, function (res) {
                                    if (res.status == 'S') {
                                        for(var i = 0; i < $scope.standardsTemp.length; i++){
                                            var obj = {};
                                            for(var j = 0; j < res.data.length; j++){
                                                if($scope.standardsTemp[i].gradingDivision + $scope.standardsTemp[i].lineNumber == res.data[j].scoringItem + res.data[j].lineNumber){
                                                    obj.gradingDivision = res.data[j].scoringItem;
                                                    obj.lineNumber = res.data[j].lineNumber;
                                                    obj.weight = res.data[j].weighting;
                                                    obj.lineLv = res.data[j].lineLv;
                                                    obj[res.data[j].supplierNumber] = res.data[j].score;
                                                    obj[res.data[j].supplierId] = res.data[j];
                                                }
                                            }
                                            $scope.nonPriceCalTable.push(obj);
                                        }
                                    }
                                }, function (error) {
                                    console.error(error)
                                })
                            }
                        }, function (error) {
                            console.error(error)
                        })

                        //非价格计算 汇总行查询
                        $scope.nonPriceCalTotalParamter = {};
                        httpServer.post(URLAPI.findScoringDetail, {
                            params: JSON.stringify({
                                scoringId: $scope.program.scoringId,
                                scoringItem: 'totalScore',
                                scoringType: 'nonPriceCalTotalScore'
                            })
                        }, function (res) {
                            if (res.status == 'S') {
                                for(var i = 0; i < res.data.length; i++){
                                    $scope.nonPriceCalTotalParamter.productName = res.data[i].scoringItem;
                                    $scope.nonPriceCalTotalParamter.weight = res.data[i].weighting;
                                    $scope.nonPriceCalTotalParamter[res.data[i].supplierNumber] = res.data[i].score;
                                    $scope.nonPriceCalTotalParamter[res.data[i].supplierId] = res.data[i];
                                }
                            }
                        }, function (error) {
                            console.error(error)
                        })

                        //非价格计算 排名行查询
                        $scope.nonPriceCalRankParamter = {};
                        httpServer.post(URLAPI.findScoringDetail, {
                            params: JSON.stringify({
                                scoringId: $scope.program.scoringId,
                                scoringItem: 'totalRanking',
                                scoringType: 'nonPriceCalRanking'
                            })
                        }, function (res) {
                            if (res.status == 'S') {
                                for(var i = 0; i < res.data.length; i++){
                                    $scope.nonPriceCalRankParamter.productName = res.data[i].scoringItem;
                                    $scope.nonPriceCalRankParamter[res.data[i].supplierNumber] = res.data[i].scoringRanking;
                                    $scope.nonPriceCalRankParamter[res.data[i].supplierId] = res.data[i];
                                }
                            }
                        }, function (error) {
                            console.error(error)
                        })

                        //报价结果计算 汇总行查询
                        $scope.quotationCalTotalParamter = {};
                        httpServer.post(URLAPI.findScoringDetail, {
                            params: JSON.stringify({
                                scoringId: $scope.program.scoringId,
                                scoringItem: 'totalScore',
                                scoringType: 'quotationTotalResult'
                            })
                        }, function (res) {
                            if (res.status == 'S') {
                                for(var i = 0; i < res.data.length; i++){
                                    $scope.quotationCalTotalParamter.productName = res.data[i].scoringItem;
                                    $scope.quotationCalTotalParamter.weight = res.data[i].weighting;
                                    $scope.quotationCalTotalParamter[res.data[i].supplierNumber] = res.data[i].score;
                                    $scope.quotationCalTotalParamter[res.data[i].supplierId] = res.data[i];
                                }
                            }
                        }, function (error) {
                            console.error(error)
                        })

                        //报价结果计算 排名行查询
                        $scope.quotationCalRankParamter = {};
                        httpServer.post(URLAPI.findScoringDetail, {
                            params: JSON.stringify({
                                scoringId: $scope.program.scoringId,
                                scoringItem: 'totalRanking',
                                scoringType: 'quotationTotalResult'
                            })
                        }, function (res) {
                            if (res.status == 'S') {
                                for(var i = 0; i < res.data.length; i++){
                                    $scope.quotationCalRankParamter.productName = res.data[i].scoringItem;
                                    $scope.quotationCalRankParamter[res.data[i].supplierNumber] = res.data[i].scoringRanking;
                                    $scope.quotationCalRankParamter[res.data[i].supplierId] = res.data[i];
                                }
                            }
                        }, function (error) {
                            console.error(error)
                        })

                        //Cost-供应商报价查询
                        $scope.costProductQuationDataTable = [];
                        httpServer.post(URLAPI.findProductSpecs, {
                            params: JSON.stringify({
                                projectId: $scope.program.projectId
                            })
                        }, function (res) {
                            if (res.status == 'S') {
                                for(var i = 0; i < res.data.length; i++){
                                    httpServer.post(URLAPI.findScoringDetail, {
                                        params: JSON.stringify({
                                            scoringId: $scope.program.scoringId,
                                            scoringItem: res.data[i].productName,
                                            scoringType: 'costQuotation'
                                        })
                                    }, function (res) {
                                        if (res.status == 'S') {
                                            var obj = {};
                                            for(var i = 0; i < res.data.length; i++){
                                                obj.productName = res.data[i].scoringItem;
                                                obj.annualSalesQuantity = res.data[i].annualSalesQuantity;
                                                obj[res.data[i].supplierNumber] = res.data[i].quotationPrice;
                                                obj[res.data[i].supplierId] = res.data[i];
                                            }
                                            $scope.costProductQuationDataTable.push(obj);
                                        }
                                    }, function (error) {
                                        console.error(error)
                                    })
                                }
                            }
                        }, function (error) {
                            console.error(error)
                        })

                        //Cost-供应商采购额查询
                        $scope.costProductScoreDataTable = [];
                        httpServer.post(URLAPI.findProductSpecs, {
                            params: JSON.stringify({
                                projectId: $scope.program.projectId
                            })
                        }, function (res) {
                            if (res.status == 'S') {
                                for(var i = 0; i < res.data.length; i++){
                                    httpServer.post(URLAPI.findScoringDetail, {
                                        params: JSON.stringify({
                                            scoringId: $scope.program.scoringId,
                                            scoringItem: res.data[i].productName,
                                            scoringType: 'costScoring'
                                        })
                                    }, function (res) {
                                        if (res.status == 'S') {
                                            var obj = {};
                                            for(var i = 0; i < res.data.length; i++){
                                                obj.productName = res.data[i].scoringItem;
                                                obj[res.data[i].supplierNumber] = res.data[i].purchaseVolume;
                                                obj[res.data[i].supplierId] = res.data[i];
                                            }
                                            $scope.costProductScoreDataTable.push(obj);
                                        }
                                    }, function (error) {
                                        console.error(error)
                                    })
                                }
                            }
                        }, function (error) {
                            console.error(error)
                        })

                        //Cost-供应商采购总额查询
                        $scope.costProductTotalCostParamter = {};
                        httpServer.post(URLAPI.findScoringDetail, {
                            params: JSON.stringify({
                                scoringId: $scope.program.scoringId,
                                scoringItem: 'totalCost',
                                scoringType: 'costTotalCost'
                            })
                        }, function (res) {
                            if (res.status == 'S') {
                                for(var i = 0; i < res.data.length; i++){
                                    $scope.costProductTotalCostParamter.productName = res.data[i].scoringItem;
                                    $scope.costProductTotalCostParamter[res.data[i].supplierNumber] = res.data[i].purchaseVolume;
                                    $scope.costProductTotalCostParamter[res.data[i].supplierId] = res.data[i];
                                }
                            }
                        }, function (error) {
                            console.error(error)
                        })

                        //Cost-供应商分数查询
                        $scope.costProductTotalScoreParamter = {};
                        httpServer.post(URLAPI.findScoringDetail, {
                            params: JSON.stringify({
                                scoringId: $scope.program.scoringId,
                                scoringItem: 'totalScore',
                                scoringType: 'costTotalScore'
                            })
                        }, function (res) {
                            if (res.status == 'S') {
                                for(var i = 0; i < res.data.length; i++){
                                    $scope.costProductTotalScoreParamter.productName = res.data[i].scoringItem;
                                    $scope.costProductTotalScoreParamter[res.data[i].supplierNumber] = res.data[i].score;
                                    $scope.costProductTotalScoreParamter[res.data[i].supplierId] = res.data[i];
                                }
                            }
                        }, function (error) {
                            console.error(error)
                        })

                        //查询产品列表(panelTest)
                        $scope.productSpecsDataTable = [];
                        httpServer.post(URLAPI.findProductSpecs, {
                            params: JSON.stringify({
                                projectId: $scope.program.projectId
                            })
                        }, function (res) {
                            if (res.status == 'S') {
                                for(var i = 0; i < res.data.length; i++){
                                    httpServer.post(URLAPI.findScoringDetail, {
                                        params: JSON.stringify({
                                            scoringId: $scope.program.scoringId,
                                            scoringItem: res.data[i].productName,
                                            scoringType: 'panelTest'
                                        })
                                    }, function (res) {
                                        if (res.status == 'S') {
                                            var obj = {};
                                            for(var i = 0; i < res.data.length; i++){
                                                obj.productName = res.data[i].scoringItem;
                                                obj.highestScore = res.data[i].highestScore;
                                                obj.benchmark = res.data[i].benchMark;
                                                obj[res.data[i].supplierNumber] = res.data[i].score;
                                                obj[res.data[i].supplierId] = res.data[i];
                                            }
                                            $scope.productSpecsDataTable.push(obj);
                                        }
                                    }, function (error) {
                                        console.error(error)
                                    })
                                }
                            }
                        }, function (error) {
                            console.error(error)
                        })

                        //panel test calculation 行查询(panelTestCalculation)
                        $scope.productSpecsFixedDataTable = [];
                        httpServer.post(URLAPI.findProductSpecs, {
                            params: JSON.stringify({
                                projectId: $scope.program.projectId
                            })
                        }, function (res) {
                            if (res.status == 'S') {
                                for(var i = 0; i < res.data.length; i++){
                                    httpServer.post(URLAPI.findScoringDetail, {
                                        params: JSON.stringify({
                                            scoringId: $scope.program.scoringId,
                                            scoringItem: res.data[i].productName,
                                            scoringType: 'panelTestCalculation'
                                        })
                                    }, function (res) {
                                        if (res.status == 'S') {
                                            var obj = {};
                                            for(var i = 0; i < res.data.length; i++){
                                                obj.productName = res.data[i].scoringItem;
                                                obj.annualSalesAmount = res.data[i].annualSalesAmount;
                                                obj.participation = res.data[i].participation;
                                                obj.benchmark = res.data[i].benchMark;
                                                obj[res.data[i].supplierNumber] = res.data[i].score;
                                                obj[res.data[i].supplierId] = res.data[i];
                                            }
                                            $scope.productSpecsFixedDataTable.push(obj);
                                        }
                                    }, function (error) {
                                        console.error(error)
                                    })
                                }
                            }
                        }, function (error) {
                            console.error(error)
                        })

                        //panel test calculation 汇总行查询(panelTestCalculation)
                        $scope.panelTestTotalParamter = {};
                        httpServer.post(URLAPI.findScoringDetail, {
                            params: JSON.stringify({
                                scoringId: $scope.program.scoringId,
                                scoringItem: 'totalScore',
                                scoringType: 'panelTestTotalScore'
                            })
                        }, function (res) {
                            if (res.status == 'S') {
                                for(var i = 0; i < res.data.length; i++){
                                    $scope.panelTestTotalParamter.productName = res.data[i].scoringItem;
                                    $scope.panelTestTotalParamter.annualSalesAmount = res.data[i].annualSalesAmount;
                                    $scope.panelTestTotalParamter.participation = res.data[i].participation;
                                    $scope.panelTestTotalParamter.benchmark = res.data[i].benchMark;
                                    $scope.panelTestTotalParamter[res.data[i].supplierNumber] = res.data[i].score;
                                    $scope.panelTestTotalParamter[res.data[i].supplierId] = res.data[i];
                                }
                            }
                        }, function (error) {
                            console.error(error)
                        })
                    }
                    //如果供应商列表已确认，则查询每个页签的分数
                    if($scope.program.supplierConfirmFlag == 'Y'){
                        $scope.searchScoringDetail();
                    }
                }
            }, function (error) {
                console.error(error)
            })
        };

        /********************查询页签列表**********************/
        $scope.searchScoringDetail = function () {
            $scope.factoryAuditColumns = [];
            $scope.newConceptRromSupplierColumns = [];
            $scope.componentSupportColumns = [];
            $scope.effectiveAccurateFeedbackColumns = [];
            $scope.paymentTermsColumns = [];
            $scope.currentSupplierPerformanceColumns = [];
            $scope.commercialContractColumns = [];
            $scope.supplierSpendProfileColumns = [];

            for(var i = 0; i < $scope.supplierInvitationDataTable.length; i++){
                if($scope.supplierInvitationDataTable[i].isQuit != 'Y'){
                    httpServer.post(URLAPI.findScoringDetail, {
                        params: JSON.stringify({
                            scoringId: $scope.program.scoringId,
                            supplierId: $scope.supplierInvitationDataTable[i].supplierId
                        })
                    }, function (res) {
                        if (res.status == 'S') {
                            //Factory Audit
                            var obj1 = {
                                supplierId: '',
                                column: '',
                                defaultItem: '',
                                scoringDetailId:'',
                                versionNum : '',
                                scoringId : '',
                                scoringType:'',
                                scoringItem:'',
                                creationDate:'',
                                createdBy:'',
                                lastUpdatedBy:'',
                                lastUpdateDate:'',
                                lastUpdateLogin:''
                            };

                            //New connept from supplier
                            var obj2 = {
                                supplierId: '',
                                column: '',
                                defaultItem: '',
                                defaultScoringDetailId:'',
                                totalScoringDetailId:'',
                                totalScore:'',
                                scoringDetailId:'',
                                defaultVersionNum : '',
                                totalVersionNum : '',
                                scoringId : '',
                                scoringType:'',
                                scoringItem:'',
                                creationDate:'',
                                createdBy:'',
                                lastUpdatedBy:'',
                                lastUpdateDate:'',
                                lastUpdateLogin:''
                            };

                            //Component support
                            var obj3 = {
                                supplierId: '',
                                column: '',
                                innerPackage: '',
                                innerScoringDetailId:'',
                                outerPackage:'',
                                outerScoringDetailId:'',
                                totalScore:'',
                                totalScoringDetailId:'',
                                scoringDetailId:'',
                                innerVersionNum : '',
                                outerVersionNum : '',
                                totalVersionNum : '',
                                scoringId : '',
                                scoringType:'',
                                scoringItem:'',
                                creationDate:'',
                                createdBy:'',
                                lastUpdatedBy:'',
                                lastUpdateDate:'',
                                lastUpdateLogin:''
                            };

                            //Effective & accurate feedback
                            var obj4 = {
                                supplierId: '',
                                column: '',
                                defaultItem: '',
                                scoringDetailId:'',
                                versionNum : '',
                                scoringId : '',
                                scoringType:'',
                                scoringItem:'',
                                creationDate:'',
                                createdBy:'',
                                lastUpdatedBy:'',
                                lastUpdateDate:'',
                                lastUpdateLogin:''
                            };

                            //Payment terms
                            var obj5 = {
                                supplierId: '',
                                column: '',
                                paymentTerms: '',
                                scoringDetailId:'',
                                versionNum : '',
                                scoringId : '',
                                scoringType:'',
                                scoringItem:'',
                                creationDate:'',
                                createdBy:'',
                                lastUpdatedBy:'',
                                lastUpdateDate:'',
                                lastUpdateLogin:''
                            };

                            //Current supplier performance
                            var obj6 = {
                                supplierId: '',
                                column: '',
                                defaultItem: '',
                                scoringDetailId:'',
                                versionNum : '',
                                scoringId : '',
                                scoringType:'',
                                scoringItem:'',
                                creationDate:'',
                                createdBy:'',
                                lastUpdatedBy:'',
                                lastUpdateDate:'',
                                lastUpdateLogin:''
                            };

                            //Commercial contract
                            var obj7 = {
                                supplierId: '',
                                column: '',
                                defaultItem: '',
                                scoringDetailId:'',
                                versionNum : '',
                                scoringId : '',
                                scoringType:'',
                                scoringItem:'',
                                creationDate:'',
                                createdBy:'',
                                lastUpdatedBy:'',
                                lastUpdateDate:'',
                                lastUpdateLogin:''
                            };

                            //Supplier spend profile
                            var obj8 = {
                                supplierId: '',
                                column: '',
                                defaultItem: '',
                                scoringDetailId:'',
                                versionNum : '',
                                scoringId : '',
                                scoringType:'',
                                scoringItem:'',
                                creationDate:'',
                                createdBy:'',
                                lastUpdatedBy:'',
                                lastUpdateDate:'',
                                lastUpdateLogin:''
                            };
                            for(var i = 0; i < res.data.length; i++){
                                switch (res.data[i].scoringType) {
                                    case 'factoryAudit':
                                        obj1.defaultItem = res.data[i].score;
                                        obj1.scoringDetailId = res.data[i].scoringDetailId;
                                        obj1.supplierId = res.data[i].supplierId;
                                        obj1.column = res.data[i].supplierName;
                                        obj1.versionNum = res.data[i].versionNum;
                                        obj1.scoringId = res.data[i].scoringId;
                                        obj1.scoringType = res.data[i].scoringType;
                                        obj1.scoringItem = res.data[i].scoringItem;
                                        obj1.creationDate = res.data[i].creationDate;
                                        obj1.createdBy = res.data[i].createdBy;
                                        obj1.lastUpdatedBy = res.data[i].lastUpdatedBy;
                                        obj1.lastUpdateDate = res.data[i].lastUpdateDate;
                                        obj1.lastUpdateLogin = res.data[i].lastUpdateLogin;
                                        break;
                                    case 'newConceptRromSupplier':
                                        if(res.data[i].scoringItem == 'defaultItem'){
                                            obj2.defaultItem = res.data[i].score;
                                            obj2.defaultScoringDetailId = res.data[i].scoringDetailId;
                                            obj2.defaultVersionNum = res.data[i].versionNum;
                                            obj2.defaultScoringItem = res.data[i].scoringItem;
                                        }else if(res.data[i].scoringItem == 'totalScore'){
                                            obj2.totalScore = res.data[i].score;
                                            obj2.totalScoringDetailId = res.data[i].scoringDetailId;
                                            obj2.totalVersionNum = res.data[i].versionNum;
                                            obj2.totalScoringItem = res.data[i].scoringItem;
                                        }
                                        obj2.supplierId = res.data[i].supplierId;
                                        obj2.column = res.data[i].supplierName;
                                        obj2.scoringId = res.data[i].scoringId;
                                        obj2.scoringType = res.data[i].scoringType;
                                        obj2.creationDate = res.data[i].creationDate;
                                        obj2.createdBy = res.data[i].createdBy;
                                        obj2.lastUpdatedBy = res.data[i].lastUpdatedBy;
                                        obj2.lastUpdateDate = res.data[i].lastUpdateDate;
                                        obj2.lastUpdateLogin = res.data[i].lastUpdateLogin;
                                        break;
                                    case 'componentSupport':
                                        if(res.data[i].scoringItem == 'innerPackage'){
                                            obj3.innerPackage = res.data[i].score;
                                            obj3.innerScoringDetailId = res.data[i].scoringDetailId;
                                            obj3.innerVersionNum = res.data[i].versionNum;
                                            obj3.innerScoringItem = res.data[i].scoringItem;
                                        }else if(res.data[i].scoringItem == 'outerPackage'){
                                            obj3.outerPackage = res.data[i].score;
                                            obj3.outerScoringDetailId = res.data[i].scoringDetailId;
                                            obj3.outerVersionNum = res.data[i].versionNum;
                                            obj3.outerScoringItem = res.data[i].scoringItem;
                                        }else if(res.data[i].scoringItem == 'totalScore'){
                                            obj3.totalScore = res.data[i].score;
                                            obj3.totalScoringDetailId = res.data[i].scoringDetailId;
                                            obj3.totalVersionNum = res.data[i].versionNum;
                                            obj3.totalScoringItem = res.data[i].scoringItem;
                                        }
                                        obj3.supplierId = res.data[i].supplierId;
                                        obj3.column = res.data[i].supplierName;
                                        obj3.scoringId = res.data[i].scoringId;
                                        obj3.scoringType = res.data[i].scoringType;
                                        obj3.creationDate = res.data[i].creationDate;
                                        obj3.createdBy = res.data[i].createdBy;
                                        obj3.lastUpdatedBy = res.data[i].lastUpdatedBy;
                                        obj3.lastUpdateDate = res.data[i].lastUpdateDate;
                                        obj3.lastUpdateLogin = res.data[i].lastUpdateLogin;
                                        break;
                                    case 'effectiveAccurateFeedback':
                                        obj4.defaultItem = res.data[i].score;
                                        obj4.scoringDetailId = res.data[i].scoringDetailId;
                                        obj4.supplierId = res.data[i].supplierId;
                                        obj4.column = res.data[i].supplierName;
                                        obj4.versionNum = res.data[i].versionNum;
                                        obj4.scoringId = res.data[i].scoringId;
                                        obj4.scoringType = res.data[i].scoringType;
                                        obj4.scoringItem = res.data[i].scoringItem;
                                        obj4.creationDate = res.data[i].creationDate;
                                        obj4.createdBy = res.data[i].createdBy;
                                        obj4.lastUpdatedBy = res.data[i].lastUpdatedBy;
                                        obj4.lastUpdateDate = res.data[i].lastUpdateDate;
                                        obj4.lastUpdateLogin = res.data[i].lastUpdateLogin;
                                        break;
                                    case 'paymentTerms':
                                        obj5.paymentTerms = res.data[i].score;
                                        obj5.scoringDetailId = res.data[i].scoringDetailId;
                                        obj5.supplierId = res.data[i].supplierId;
                                        obj5.column = res.data[i].supplierName;
                                        obj5.versionNum = res.data[i].versionNum;
                                        obj5.scoringId = res.data[i].scoringId;
                                        obj5.scoringType = res.data[i].scoringType;
                                        obj5.scoringItem = res.data[i].scoringItem;
                                        obj5.creationDate = res.data[i].creationDate;
                                        obj5.createdBy = res.data[i].createdBy;
                                        obj5.lastUpdatedBy = res.data[i].lastUpdatedBy;
                                        obj5.lastUpdateDate = res.data[i].lastUpdateDate;
                                        obj5.lastUpdateLogin = res.data[i].lastUpdateLogin;
                                        break;
                                    case 'currentSupplierPerformance':
                                        obj6.defaultItem = res.data[i].score;
                                        obj6.scoringDetailId = res.data[i].scoringDetailId;
                                        obj6.supplierId = res.data[i].supplierId;
                                        obj6.column = res.data[i].supplierName;
                                        obj6.versionNum = res.data[i].versionNum;
                                        obj6.scoringId = res.data[i].scoringId;
                                        obj6.scoringType = res.data[i].scoringType;
                                        obj6.scoringItem = res.data[i].scoringItem;
                                        obj6.creationDate = res.data[i].creationDate;
                                        obj6.createdBy = res.data[i].createdBy;
                                        obj6.lastUpdatedBy = res.data[i].lastUpdatedBy;
                                        obj6.lastUpdateDate = res.data[i].lastUpdateDate;
                                        obj6.lastUpdateLogin = res.data[i].lastUpdateLogin;
                                        break;
                                    case 'commercialContract':
                                        obj7.defaultItem = res.data[i].score;
                                        obj7.scoringDetailId = res.data[i].scoringDetailId;
                                        obj7.supplierId = res.data[i].supplierId;
                                        obj7.column = res.data[i].supplierName;
                                        obj7.versionNum = res.data[i].versionNum;
                                        obj7.scoringId = res.data[i].scoringId;
                                        obj7.scoringType = res.data[i].scoringType;
                                        obj7.scoringItem = res.data[i].scoringItem;
                                        obj7.creationDate = res.data[i].creationDate;
                                        obj7.createdBy = res.data[i].createdBy;
                                        obj7.lastUpdatedBy = res.data[i].lastUpdatedBy;
                                        obj7.lastUpdateDate = res.data[i].lastUpdateDate;
                                        obj7.lastUpdateLogin = res.data[i].lastUpdateLogin;
                                        break;
                                    case 'supplierSpendProfile':
                                        obj8.defaultItem = res.data[i].score;
                                        obj8.scoringDetailId = res.data[i].scoringDetailId;
                                        obj8.supplierId = res.data[i].supplierId;
                                        obj8.column = res.data[i].supplierName;
                                        obj8.versionNum = res.data[i].versionNum;
                                        obj8.scoringId = res.data[i].scoringId;
                                        obj8.scoringType = res.data[i].scoringType;
                                        obj8.scoringItem = res.data[i].scoringItem;
                                        obj8.creationDate = res.data[i].creationDate;
                                        obj8.createdBy = res.data[i].createdBy;
                                        obj8.lastUpdatedBy = res.data[i].lastUpdatedBy;
                                        obj8.lastUpdateDate = res.data[i].lastUpdateDate;
                                        obj8.lastUpdateLogin = res.data[i].lastUpdateLogin;
                                }
                            }

                            $scope.factoryAuditColumns.push(obj1);
                            $scope.newConceptRromSupplierColumns.push(obj2);
                            $scope.componentSupportColumns.push(obj3);
                            $scope.effectiveAccurateFeedbackColumns.push(obj4);
                            $scope.paymentTermsColumns.push(obj5);
                            $scope.currentSupplierPerformanceColumns.push(obj6);
                            $scope.commercialContractColumns.push(obj7);
                            $scope.supplierSpendProfileColumns.push(obj8);
                        }
                    }, function (error) {
                        console.error(error)
                    })
                }
            }
        };

        /********************判断是新增还是修改********************/
        if (id == "" || id == undefined) {

        } else {
            $scope.search(id);
        };

        //选择报价资料开启单据
        $scope.selectQuotationInformation = function (value) {
            // $scope.current.standardsStatus = '30';
            // $scope.current.standardsStatusName = '生效';
            $scope.current.deptCode = JSON.parse(sessionStorage[appName + '_successLoginInfo']).deptCode;
            // $scope.current.department = JSON.parse(sessionStorage[appName + '_successLoginInfo']).deptCode;
            $('#selectQuotationInformationLov').modal('toggle')
        }

        //选择报价资料开启单据查询-LOV
        $scope.findQuotationInformation = function(){
            $scope.lovDataTable.getData();
        }

        $scope.selectRow = function (row,lovDataTable) {
            for(var i = 0;i<lovDataTable.length;i++){
                lovDataTable[i].isSelect = 'N'
            }
            $scope.currentList = row
            $scope.isLovSelect = 'Y';
            $scope.params.radioValue = row.informationCode;
            row.isSelect = 'Y';
        }

        //选择报价资料开启单据-回调
        $scope.selectScoringStandardReturn = function () {
            debugger;
            $scope.program.informationCode = $scope.currentList.informationCode;
            $scope.program.informationId = $scope.currentList.informationId;
            $scope.program.projectId = $scope.currentList.projectId;
            $scope.program.projectName = $scope.currentList.projectName;
            $scope.program.relevantCatetory = $scope.currentList.relevantCatetory;
            $scope.program.projectCategory = $scope.currentList.projectCategory;
            $scope.program.relevantCatetoryMeaning = $scope.currentList.relevantCatetory;
            $scope.program.projectCategoryMeaning = $scope.currentList.projectCategoryName;
            $scope.program.seriesName = $scope.currentList.seriesName;
            $scope.program.projectPurchaseAmount = $scope.currentList.projectPurchaseAmount;
            $scope.program.projectCycle = $scope.currentList.projectCycle;
            $scope.program.quotationDeadline = $scope.currentList.quotationDeadline;
            $scope.program.standardsId = $scope.currentList.standardsId;
            $scope.program.scoringStandard = $scope.currentList.standardsCode;
            $scope.program.presentCooperationSupplier = $scope.currentList.presentCooperationSupplier;
            $scope.program.sensoryTestTypes = $scope.currentList.sensoryTestTypes;
            $scope.program.firstOpenDate = $scope.currentList.firstRoundTime;
            $scope.program.sensoryTestTypesMeaning = $scope.currentList.sensoryTestTypesName;
            $scope.program.remark = $scope.currentList.remark;

            //查询邀请供应商列表
            $scope.searchSupplierInvitationInfo();
        }

        //查询panel test 产品列表
        $scope.findProductSpecs = function () {
            httpServer.post(URLAPI.findProductSpecs, {
                params: JSON.stringify({
                    projectId: $scope.program.projectId
                })
            }, function (res) {
                if (res.status == 'S') {
                    //*******************************Panel test 页签产品数据初始化*****************************//
                    $scope.productSpecsDataTable = angular.copy(res.data);
                    $scope.productSpecsFixedDataTable = angular.copy(res.data);
                    var totalAnnualSalesAmount = 0;
                    for(var i = 0; i < $scope.productSpecsDataTable.length; i++){
                        totalAnnualSalesAmount = totalAnnualSalesAmount + $scope.productSpecsDataTable[i].annualSalesAmount;
                    }
                    for(var i = 0; i < $scope.productSpecsDataTable.length; i++){
                        $scope.productSpecsFixedDataTable[i].participation = ($scope.productSpecsDataTable[i].annualSalesAmount / totalAnnualSalesAmount * 100).toFixed(2) + '%';
                    }
                    $scope.panelTestTotalParamter.annualSalesAmount = totalAnnualSalesAmount;
                    $scope.panelTestTotalParamter.participation = '100%';

                    //*******************************Cost页签产品数据初始化*****************************//
                    $scope.costProductQuationDataTable = angular.copy(res.data);
                    $scope.costProductScoreDataTable = angular.copy(res.data);

                }
            }, function (error) {
                console.error(error)
            })
        }

        //查询非价格资料计算，评分项nonPriceCalTable
        $scope.findScoringStandards = function () {
            httpServer.post(URLAPI.findPonStandardsDatail, {
                    params: JSON.stringify({
                        standardsId: $scope.program.standardsId
                    })
                }, function (res) {
                if (res.status == 'S') {
                    $scope.nonPriceCalTable = angular.copy(res.data.lineData);
                    var totalWeighting = 0;
                    for(var i = 0; i < $scope.nonPriceCalTable.length; i++){
                        if($scope.nonPriceCalTable[i].lineLv == 2 && $scope.nonPriceCalTable[i].weight != undefined && $scope.nonPriceCalTable[i].gradingDivision != 'Cost'){
                            if($scope.program.sensoryTestTypes != '10' || $scope.nonPriceCalTable[i].gradingDivision != 'Panel test'){
                                totalWeighting = totalWeighting + $scope.nonPriceCalTable[i].weight;
                            }
                        }
                    }
                    $scope.nonPriceCalTotalParamter.weight = totalWeighting;
                }
            }, function (error) {
                console.error(error)
            })
        }

        $scope.findAllInvitationList = function () {
            $scope.supplierInvitationListColumns = [];
            for(var i = 0; i < $scope.supplierInvitationDataTable.length; i++){
                var supplierId = $scope.supplierInvitationDataTable[i].supplierId;
                var supplierName = $scope.supplierInvitationDataTable[i].supplierName;
                var supplierNumber = $scope.supplierInvitationDataTable[i].supplierNumber;
                var isQuit = $scope.supplierInvitationDataTable[i].isQuit;
                var reason = $scope.supplierInvitationDataTable[i].reason;
                var obj = {
                    supplierId: supplierId,
                    supplierName: supplierName,
                    supplierNumber:supplierNumber
                };
                $scope.supplierInvitationListColumns.push(obj);

                $scope.firstRountParamter[supplierNumber] = isQuit == 'Y' ? 'N' : 'Y';
                $scope.secondRountParamter[supplierNumber] = isQuit == 'Y' ? 'N' : 'Y';
                $scope.operationRemarkParamter[supplierNumber] = reason;
                $scope.operationRemarkParamter[supplierId] = $scope.supplierInvitationDataTable[i];
            }
        }

        //Panel test 下载导入模板
        $scope.btnExportTemplage = function() {
            httpServer.post(URLAPI.btnExportTemplage, {
                params: JSON.stringify({
                    projectId: $scope.program.projectId
                })
            }, function (res) {
                window.open(res.data.accessPath);
            }, function (err) {
                JS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
            });
        }

        //确认供应商列表
        $scope.supplierInvitationConfirm = function(){
            if($scope.program.informationCode == null || $scope.program.informationCode == "" || $scope.program.informationCode == undefined){
                SIEJS.alert("请先选择报价资料开启单号", 'error', '确定');
                return false;
            }
            //修改supplierConfirmFlag字段为Y,失效邀请供应商列表操作按钮
            JS.confirm('确认','确认后将不能再修改供应商列表，是否确认？','确定',function() {
                $scope.program.supplierConfirmFlag = 'Y';
                JS.alert("确认成功!", "success", "确定");
                //查询panel test 产品列表
                $scope.findProductSpecs();
                $scope.findScoringStandards();
                $scope.findAllInvitationList();
                //显示页签上的供应商列
                for(var i = 0; i < $scope.supplierInvitationDataTable.length; i++){
                    if($scope.supplierInvitationDataTable[i].isQuit != 'Y'){
                        //Factory Audit
                        var obj1 = {
                            supplierId: $scope.supplierInvitationDataTable[i].supplierId,
                            column: $scope.supplierInvitationDataTable[i].supplierName,
                            defaultItem: ''
                        };
                        $scope.factoryAuditColumns.push(obj1);

                        //New connept from supplier
                        var obj2 = {
                            supplierId: $scope.supplierInvitationDataTable[i].supplierId,
                            column: $scope.supplierInvitationDataTable[i].supplierName,
                            defaultItem: '',
                            totalScore:''
                        };
                        $scope.newConceptRromSupplierColumns.push(obj2);

                        //Component support
                        var obj3 = {
                            supplierId: $scope.supplierInvitationDataTable[i].supplierId,
                            column: $scope.supplierInvitationDataTable[i].supplierName,
                            innerPackage: '',
                            outerPackage:'',
                            totalScore:''
                        };
                        $scope.componentSupportColumns.push(obj3);

                        //Effective & accurate feedback
                        var obj4 = {
                            supplierId: $scope.supplierInvitationDataTable[i].supplierId,
                            column: $scope.supplierInvitationDataTable[i].supplierName,
                            defaultItem: ''
                        };
                        $scope.effectiveAccurateFeedbackColumns.push(obj4);

                        //Payment terms
                        var obj5 = {
                            supplierId: $scope.supplierInvitationDataTable[i].supplierId,
                            column: $scope.supplierInvitationDataTable[i].supplierName,
                            paymentTerms: ''
                        };
                        $scope.paymentTermsColumns.push(obj5);

                        //Panel test
                        var obj9 = {
                            supplierId: $scope.supplierInvitationDataTable[i].supplierId,
                            supplierName: $scope.supplierInvitationDataTable[i].supplierName,
                            supplierNumber:$scope.supplierInvitationDataTable[i].supplierNumber
                        };
                        $scope.panelTestColumns.push(obj9);

                        //非价格计算
                        var obj10 = {
                            supplierId: $scope.supplierInvitationDataTable[i].supplierId,
                            supplierName: $scope.supplierInvitationDataTable[i].supplierName,
                            supplierNumber:$scope.supplierInvitationDataTable[i].supplierNumber
                        };
                        $scope.nonPriceCalColumns.push(obj10);

                        //Cost
                        var obj11 = {
                            supplierId: $scope.supplierInvitationDataTable[i].supplierId,
                            supplierName: $scope.supplierInvitationDataTable[i].supplierName,
                            supplierNumber:$scope.supplierInvitationDataTable[i].supplierNumber
                        };
                        $scope.costColumns.push(obj11);

                        //查询供应商有效的基础分数（commercialContract，currentSupplierPerformance，supplierSpendProfile）
                        httpServer.post(URLAPI.findSupplierBaseScore, {
                            params: JSON.stringify({
                                supplierNumber: $scope.supplierInvitationDataTable[i].supplierNumber
                            })
                        }, function (res) {
                            if (res.status == 'S') {
                                var obj6 = {
                                    supplierId:res.data[0].supplierId,
                                    column: res.data[0].supplierName,
                                    defaultItem: res.data[0].commercialContractScore
                                };
                                $scope.commercialContractColumns.push(obj6);

                                var obj5 = {
                                    supplierId:res.data[0].supplierId,
                                    column: res.data[0].supplierName,
                                    defaultItem: res.data[0].currentPerformanceScore
                                };
                                $scope.currentSupplierPerformanceColumns.push(obj5);

                                var obj7 = {
                                    supplierId:res.data[0].supplierId,
                                    column: res.data[0].supplierName,
                                    defaultItem: res.data[0].spendProfileScore
                                };
                                $scope.supplierSpendProfileColumns.push(obj7);
                            }
                        }, function (error) {
                            console.error(error)
                        })
                    }
                }
            })
        }

        //非价格计算按钮事件
        $scope.nonPriceCalculate = function(){
            //校验分数是否填写完整
            //Panel test 感官类型为内部，校验必填

            // if($scope.program.supplierConfirmFlag != 'Y'){
            //
            // }

            if($scope.program.sensoryTestTypes == '20'){
                for(var i = 0; i < $scope.panelTestColumns.length; i++){
                    for(var j = 0; j < $scope.productSpecsDataTable.length; j++){
                        var score = $scope.productSpecsDataTable[j][$scope.panelTestColumns[i].supplierNumber];
                        if(score == null || score == '' || score == undefined){
                            JS.alert("Panel test评分填写不完整!", "error", "确定");
                            return false;
                        }
                    }
                }
            }
            //Factory audit
            for(var i = 0; i < $scope.factoryAuditColumns.length; i++){
                var defaultItem = $scope.factoryAuditColumns[i].defaultItem;
                if(defaultItem == null || defaultItem == '' || defaultItem == undefined){
                    JS.alert("Factory audit评分填写不完整!", "error", "确定");
                    return false;
                }
            }

            //New concept from supplier
            for(var i = 0; i < $scope.newConceptRromSupplierColumns.length; i++){
                var defaultItem = $scope.newConceptRromSupplierColumns[i].defaultItem;
                if(defaultItem == null || defaultItem == '' || defaultItem == undefined){
                    JS.alert("New concept from supplier评分填写不完整!", "error", "确定");
                    return false;
                }
            }

            //Component support
            for(var i = 0; i < $scope.componentSupportColumns.length; i++){
                var innerPackage = $scope.componentSupportColumns[i].innerPackage;
                var outerPackage = $scope.componentSupportColumns[i].outerPackage;
                if(innerPackage == null || innerPackage == '' || innerPackage == undefined){
                    JS.alert("Component support评分填写不完整!", "error", "确定");
                    return false;
                }
                if(outerPackage == null || outerPackage == '' || outerPackage == undefined){
                    JS.alert("Component support评分填写不完整!", "error", "确定");
                    return false;
                }
            }

            //Effective & accurate feedback
            for(var i = 0; i < $scope.effectiveAccurateFeedbackColumns.length; i++){
                var defaultItem = $scope.effectiveAccurateFeedbackColumns[i].defaultItem;
                if(defaultItem == null || defaultItem == '' || defaultItem == undefined){
                    JS.alert("Effective & accurate feedback评分填写不完整!", "error", "确定");
                    return false;
                }
            }

            //Payment terms
            for(var i = 0; i < $scope.paymentTermsColumns.length; i++){
                var paymentTerms = $scope.paymentTermsColumns[i].paymentTerms;
                if(paymentTerms == null || paymentTerms == '' || paymentTerms == undefined){
                    JS.alert("Payment terms评分填写不完整!", "error", "确定");
                    return false;
                }
            }

            //拼装参数
            $scope.setParams();
            httpServer.post(URLAPI.saveScoringInfo, {
                'params': JSON.stringify($scope.params)
            }, function (res) {
                if ('S' == res.status) {
                    // $scope.program = res.data;
                    httpServer.post(URLAPI.saveNonPriceCalculate,{
                        'params': JSON.stringify({
                            "scoringId": res.data.scoringId,
                            "projectId": $scope.program.projectId
                        })
                    }, function (res) {
                        if (res.status === 'S') {
                            $scope.search($scope.program.scoringId);
                            $scope.program.nonPriceCalculateFlag = 'Y';
                            JS.alert("非价格计算成功!", "success", "确定");
                        } else {
                            JS.alert(res.msg, "error", "确定");
                        }
                    });
                } else {
                    JS.alert(res.msg, 'error', '确定');
                }
            }, function (error) {
                console.error(error);
                JS.alert('非价格计算失败', 'error', '确定');
            })
        }

        //QA人员提交分数
        $scope.btnSubmitScore = function(){
            //Factory audit
            for(var i = 0; i < $scope.factoryAuditColumns.length; i++){
                var defaultItem = $scope.factoryAuditColumns[i].defaultItem;
                if(defaultItem == null || defaultItem == '' || defaultItem == undefined){
                    JS.alert("Factory audit评分填写不完整!", "error", "确定");
                    return false;
                }
            }
            if($scope.program.sensoryTestTypes == '10' && $scope.program.scoringStatus == '30'){
                //Panel Test
                for(var i = 0; i < $scope.panelTestColumns.length; i++){
                    for(var j = 0; j < $scope.productSpecsDataTable.length; j++){
                        var score = $scope.productSpecsDataTable[j][$scope.panelTestColumns[i].supplierNumber];
                        if(score == null || score == '' || score == undefined){
                            JS.alert("Panel test评分填写不完整!", "error", "确定");
                            return false;
                        }
                    }
                }
            }
            JS.confirm('提交','提交分数后将不能再修改，确认提交？','确定',function() {
                //拼装参数
                if($scope.program.factoryAuditFlag != 'Y' && $scope.program.scoringStatus == '10'){
                    $scope.program.factoryAuditFlag = 'Y';
                }else if($scope.program.panelTestFlag != 'Y' && $scope.program.scoringStatus == '30'){
                    $scope.program.panelTestFlag = 'Y';
                }

                $scope.setParams();
                // $scope.params.scoringHeaderInfo.factoryAuditFlag = 'Y';
                httpServer.post(URLAPI.saveScoringInfo, {
                    'params': JSON.stringify($scope.params)
                }, function (res) {
                    if ('S' == res.status) {
                        // if($scope.program.panelTestFlag != 'Y' && $scope.program.scoringStatus == '30'){
                        //     $scope.panelTestReadonly = 'Y';
                        // }
                        JS.alert("分数提交成功!", "success", "确定");
                    } else {
                        JS.alert(res.msg, 'error', '确定');
                    }
                }, function (error) {
                    console.error(error);
                    JS.alert('分数提交失败', 'error', '确定');
                })
            })
        }

        //报价总分计算
        $scope.quotationScoreCalculate = function(){
            //校验分数是否填写完整
            //Panel test 感官类型为内部，校验必填
            if($scope.program.sensoryTestTypes == '10'){
                for(var i = 0; i < $scope.panelTestColumns.length; i++){
                    for(var j = 0; j < $scope.productSpecsDataTable.length; j++){
                        var score = $scope.productSpecsDataTable[j][$scope.panelTestColumns[i].supplierNumber];
                        if(score == null || score == '' || score == undefined){
                            JS.alert("Panel test评分填写不完整!", "error", "确定");
                            return false;
                        }
                    }
                }
            }
            //拼装参数
            $scope.setParams();
            httpServer.post(URLAPI.saveScoringInfo, {
                'params': JSON.stringify($scope.params)
            }, function (res) {
                if ('S' == res.status) {
                    // $scope.program = res.data;
                    httpServer.post(URLAPI.saveQuotationScoreCalculate,{
                        'params': JSON.stringify({
                            'scoringId': $scope.program.scoringId,
                            'projectId': $scope.program.projectId,
                            'projectNumber':$scope.program.projectNumber,
                            'sensoryTestTypes': $scope.program.sensoryTestTypes
                        })
                    }, function (res) {
                        if (res.status === 'S') {
                            $scope.search($scope.program.scoringId);
                            JS.alert("报价总分计算成功!", "success", "确定");
                        } else {
                            JS.alert(res.msg, "error", "确定");
                        }
                    });
                } else {
                    JS.alert(res.msg, 'error', '确定');
                }
            }, function (error) {
                console.error(error);
                JS.alert('报价总分计算失败', 'error', '确定');
            })
        }

        $scope.newConceptRromSupplierChangeEvent = function(row){
            row.totalScore = row.defaultItem;
        }

        $scope.componentSupportChangeEvent = function(row){
            // row.totalScore = row.innerPackage + row.outerPackage;
            row.totalScore = $filter('number')(row.innerPackage + row.outerPackage, 2);
        }

        $scope.panelTestChangeEvent = function(row){
            var highestScore = 0;
            for(var i = 0; i < $scope.panelTestColumns.length; i++){
                var supplierNumber = $scope.panelTestColumns[i].supplierNumber;
                var currentScore = row[supplierNumber];
                if(currentScore != null && currentScore != '' && currentScore != undefined){
                    if(highestScore < row[supplierNumber]){
                        highestScore = currentScore;
                    }
                }
            }
            row.highestScore = highestScore;
        }

        //退出供应商
        $scope.quitSupplierInvitation = function(row,index){
            if(row.reason == '' || row.reason == null || row.reason == undefined){
                JS.alert("供应商退出报价【新增/退出原因】栏必填，请处理！", 'error', '确定');
                return;
            }
            JS.confirm('退出','确认【' + row.supplierName +'】退出报价？','确定',function() {
                httpServer.post(URLAPI.quitSupplierInvitation,{
                    'params': JSON.stringify({
                        "id": row.invitationId,
                        "reason" : row.reason
                    })
                }, function (res) {
                    if (res.status === 'S') {
                        $scope.searchSupplierInvitationInfo();
                        JS.alert("退出成功!", "success", "确定");
                    } else {
                        JS.alert(res.msg, "error", "确定");
                    }
                });
            })
        }

        $scope.btnMoniterQuotation = function () {
            iframeTabAction('监控报价', 'monitorPriceInfo/'+$scope.program.informationId)
        }

        //终止
        $scope.btnTerminate = function(){
            //修改评分单据终止标志未"Y"
            httpServer.post(URLAPI.terminateScoringInfo,{
                'params': JSON.stringify({
                    "scoringId": $scope.program.scoringId
                })
            }, function (res) {
                if (res.status === 'S') {
                    $scope.program.terminateFlag = "Y";
                    //提交流程
                    $scope.btnSubmit();
                } else {
                    JS.alert(res.msg, "error", "确定");
                }
            });
        }

        /**********************************工作流配置**************************************/
        function urlToObj(url) {
            var index = url.lastIndexOf('?');
            var params = url.substring(index+1);
            var arr = params.split('&');
            var obj = {};
            arr.forEach(function (item) {
                obj[item.split('=')[0]] = item.split('=')[1];
            });
            return obj;
        }
        //获取url参数对象
        $scope.urlParams = urlToObj(location.hash);

        //获取单据ID
        function getId() {
            return $scope.urlParams.businessKey ? $scope.urlParams.businessKey : $scope.program.scoringId;
        }

        //流程图参数
        $scope.processImageParams = {
            token: sessionStorage.getItem(window.appName + '_certificate')||localStorage.getItem(window.appName + '_certificate'),
            id: 'processImg',
            instanceId: $scope.urlParams.processInstanceId,
            key: 'PFSPLC.-999' //流程唯一标识，在流程管理->流程设计->设计 流程中获取，流程配置时修改为对应表单的流程唯一标识
        };
        // tab 切换
        $scope.tabAction = 'requestFundsBusiness'; //根据页面配置
        $scope.tabChange = function (name) {
            $scope.tabAction = name;
            if ((!$scope.processImgLoad) && (name == 'opinion')) {
                $scope.getProcessInfo(function () {
                    var p = $scope.processImageParams;
                    $timeout(function () {
                        processImageDraw(p.token, p.id, p.instanceId, p.key); //  绘制流程图
                        $scope.processImgLoad = true;
                    }, 100)
                });
            }
        };
        // 获取流程信息
        $scope.getProcessInfo = function (callback) {
            httpServer.post(URLAPI.processGet, {
                    'params': JSON.stringify({
                        processDefinitionKey: $scope.processImageParams.key,
                        businessKey:$scope.program.scoringId
                    })
                },
                function (res) {
                    if (res.status === 'S') {
                        $scope.processImageParams.instanceId = res.data.processInstanceId;
                    }
                    callback && callback(res);
                });
        };

        /**********************************工作流配置**************************************/

        //提交审批
        $scope.btnSubmit = function(){
            if($scope.program.scoringStatus != '10' && $scope.program.scoringStatus != '40'){
                JS.alert('状态不是拟定或驳回，不允许提交审批', 'error', '确定');
                return;
            }
            $scope.extend ={
                "tasks_assignees":[]
            }
            $scope.variables = []; //流程变量
            angular.forEach($scope.program, function (value, key) {
                $scope.variables.push({
                    name: key,
                    type: 'string',
                    value: value
                });
            });

            $scope.properties={
                "menucode": "PFSPLC",
                "opinion": ""
            };
            $scope.paramsBpm={
                "extend":$scope.extend,
                "variables":$scope.variables,
                "properties":$scope.properties,
                "responsibilityId": "990021",
                "respId": "990021",
                "processDefinitionKey": "PFSPLC.-999", //流程唯一标识，需修改为对应业务表单流程唯一标识
                "saveonly": false,
                "businessKey": $scope.program.scoringId, //单据ID
                "title": "评分审批流程", //流程标题
                "positionId": 0,
                "orgId": 0,
                "userType": "30",
                "billNo": $scope.program.scoringNumber
            }
            httpServer.post(URLAPI.bpmStart, {
                'params': JSON.stringify($scope.paramsBpm)
            }, function (res) {
                if (res.status == 'S') {
                    $scope.search($scope.program.scoringId);
                    JS.alert('提交成功');
                }
                else {
                    JS.alert(res.msg, "error", "确定");
                }
            }, function (err) {
                JS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
            });
        }
    }]);
})