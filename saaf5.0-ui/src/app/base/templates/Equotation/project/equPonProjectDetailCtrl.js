/**
 * Created by sie_panshibang on 2019/9/4.
 */
define(["app", "angularFileUpload",'XLSX', "pinyin","GooFlow"], function (app, angularFileUpload,XLSX, pinyin) {
    app.useModule('angularFileUpload');//按需加载弹出层模块
    app.controller('equPonProjectDetailCtrl', ['$scope', '$parse', '$filter', '$attrs', "SIE.JS", 'httpServer', 'URLAPI', '$stateParams', 'iframeTabAction','$http','SIEJS','$timeout','$controller', function saafTableController($scope, $parse, $filter, $attrs, JS, httpServer, URLAPI, $stateParams, iframeTabAction,$http,SIEJS,$timeout,$controller) {
        $controller('processBase', {$scope:$scope}); // 继承基础的流程控制器
        if ($stateParams.projectId){
            var id = $stateParams.projectId;
        }else if ($scope.urlParams.businessKey){
            var id = $scope.urlParams.businessKey;
        }
        $scope.changeFlag = $stateParams.changeFlag;
        $scope.params = {};
        $scope.program = {};
        $scope.initProgram = {};
        $scope.flags = {};
        //立项资料
        $scope.projectInfoDataTable = [];
        //立项资料(旧版本)
        $scope.initProjectInfoDataTable = [];
        //产品规格
        $scope.productSpecsDataTable = [];
        //产品规格(旧版本)
        $scope.initProductSpecsDataTable = [];
        //邀请供应商列表
        $scope.supplierInvitationDataTable = [];
        //邀请供应商列表(旧版本)
        $scope.initSupplierInvitationDataTable = [];
        //非价格文件
        $scope.nonPriceFileDataTable = [];
        //非价格文件(旧版本)
        $scope.initNonPriceFileDataTable = [];
        //非价格文件(固定附件)
        $scope.nonPriceSelectFileDataTable = [];
        //非价格文件(固定附件)(旧版本)
        $scope.initNonPriceSelectFileDataTable = [];
        //价格文件
        $scope.priceFileDataTable = [];
        //价格文件(旧版本)
        $scope.initPriceFileDataTable = [];
        //评分小组
        $scope.scoringTeamDataTable = [];
        //评分小组(旧版本)
        $scope.initScoringTeamDataTable = [];
        //见证小组
        $scope.witnessTeamDataTable = [];
        //见证小组(旧版本)
        $scope.initWitnessTeamDataTable = [];

        $scope.rowsIndex = 5;

        $scope.readonlyFlag = 'N';
        $scope.showFlag = 'Y';
        $scope.sendInvitationReadonlyFlag = 'Y';

        //当前登录人所属部门
        $scope.program.deptCode = JSON.parse(sessionStorage[appName + '_successLoginInfo']).deptCode;
        $scope.program.deptName = JSON.parse(sessionStorage[appName + '_successLoginInfo']).deptName;
        var departmentName = JSON.parse(sessionStorage[appName + '_successLoginInfo']).deptName;
        $scope.program.projectStatus = '10';
        $scope.program.projectStatusMeaning = '拟定';
        $scope.program.departmentName = $scope.program.deptName;
        //当前登录人类型
        $scope.flags.userTpye = JSON.parse(sessionStorage[appName + '_successLoginInfo']).userType;
        $scope.flags.userId = JSON.parse(sessionStorage[appName + '_successLoginInfo']).userId;


        //保存/提交前校验
        $scope.validate = function () {
            //校验立项资料
            if ($scope.projectInfoDataTable.length == 0) {
                JS.alert('请添加立项资料行', 'error', '确定');
                return true;
            }else{
                for (var i = 0; i < $scope.projectInfoDataTable.length; i++) {
                    if ($scope.projectInfoDataTable[i].attachmentName == '' || $scope.projectInfoDataTable[i].attachmentName == undefined) {
                        JS.alert('立项资料填写不完整', 'error', '确定');
                        return true;
                    }
                    if ($scope.projectInfoDataTable[i].fileName == '' || $scope.projectInfoDataTable[i].fileName == undefined) {
                        JS.alert('立项资料填写不完整', 'error', '确定');
                        return true;
                    }
                }
            }

            //校验3.1产品规格
            if ($scope.productSpecsDataTable.length == 0) {
                JS.alert('请添加产品规格行', 'error', '确定');
                return true;
            }else{
                for (var i = 0; i < $scope.productSpecsDataTable.length; i++) {
                    if ($scope.productSpecsDataTable[i].productName == '' || $scope.productSpecsDataTable[i].productName == undefined) {
                        JS.alert('产品规格填写不完整', 'error', '确定');
                        return true;
                    }
                    if ($scope.productSpecsDataTable[i].targetCost == '' || $scope.productSpecsDataTable[i].targetCost == undefined) {
                        JS.alert('产品规格填写不完整', 'error', '确定');
                        return true;
                    }
                    if ($scope.productSpecsDataTable[i].annualSalesQuantity == '' || $scope.productSpecsDataTable[i].annualSalesQuantity == undefined) {
                        JS.alert('产品规格填写不完整', 'error', '确定');
                        return true;
                    }
                    if ($scope.productSpecsDataTable[i].annualSalesAmount == '' || $scope.productSpecsDataTable[i].annualSalesAmount == undefined) {
                        JS.alert('产品规格填写不完整', 'error', '确定');
                        return true;
                    }
                }
            }

            //校验邀请供应商列表
            if ($scope.supplierInvitationDataTable.length == 0) {
                JS.alert('请添加邀请供应商列表行', 'error', '确定');
                return true;
            }else{
                for (var i = 0; i < $scope.supplierInvitationDataTable.length; i++) {
                    if ($scope.supplierInvitationDataTable[i].supplierName == '' || $scope.supplierInvitationDataTable[i].supplierName == undefined) {
                        JS.alert('邀请供应商列表填写不完整', 'error', '确定');
                        return true;
                    }
                    if ($scope.supplierInvitationDataTable[i].contactName == '' || $scope.supplierInvitationDataTable[i].contactName == undefined) {
                        JS.alert('邀请供应商列表填写不完整', 'error', '确定');
                        return true;
                    }
                    if ($scope.supplierInvitationDataTable[i].oiPercent == undefined) {
                        JS.alert('邀请供应商列表OI%数值不能为空', 'error', '确定');
                        return true;
                    }
                    if ($scope.supplierInvitationDataTable[i].oiPercent<0.00 || $scope.supplierInvitationDataTable[i].oiPercent >100.00) {
                        JS.alert('OI%数值要求在0.00到100.0之间', 'error', '确定');
                        return true;
                    }
                }
            }

            //校验非价格文件
            var count = 0;
            for(var i = 0; i < $scope.nonPriceSelectFileDataTable.length; i++){
                if($scope.nonPriceSelectFileDataTable[i].selectedFlag == 'Y'){
                    count = count + 1;
                }
            }
            if(count == 0){
                JS.alert('至少需要勾选一个非价格文件！', 'error', '确定');
                return true;
            }

            if($scope.nonPriceFileDataTable.length == 0){
                JS.alert('请添加非价格文件行', 'error', '确定');
                return true;
            }else{
                for (var i = 0; i < $scope.nonPriceFileDataTable.length; i++) {
                    if ($scope.nonPriceFileDataTable[i].attachmentName == '' || $scope.nonPriceFileDataTable[i].attachmentName == undefined) {
                        JS.alert('非价格文件填写不完整', 'error', '确定');
                        return true;
                    }
                    if ($scope.nonPriceFileDataTable[i].fileName == '' || $scope.nonPriceFileDataTable[i].fileName == undefined) {
                        JS.alert('非价格文件填写不完整', 'error', '确定');
                        return true;
                    }
                }
            }

            //校验价格文件
            if ($scope.priceFileDataTable.length == 0) {
                JS.alert('请添加价格文件行', 'error', '确定');
                return true;
            }else{
                for (var i = 0; i < $scope.priceFileDataTable.length; i++) {
                    if ($scope.priceFileDataTable[i].attachmentName == '' || $scope.priceFileDataTable[i].attachmentName == undefined) {
                        JS.alert('价格文件填写不完整', 'error', '确定');
                        return true;
                    }
                    if ($scope.priceFileDataTable[i].fileName == '' || $scope.priceFileDataTable[i].fileName == undefined) {
                        JS.alert('价格文件填写不完整', 'error', '确定');
                        return true;
                    }
                }
            }

            for(var i = 0; i < $scope.witnessTeamDataTable.length; i++){
                var row = $scope.witnessTeamDataTable[i];
                if(row.defaultMemberName == null || row.defaultMemberName == undefined || row.defaultMemberName == ''){
                    JS.alert('见证小组默认人员不能为空', 'error', '确定');
                    return true;
                }
            }
        }

        /********************保存立项单据信息********************/
        $scope.btnSave = function () {
            //公共
            $scope.params.projectInfo = $scope.program; //立项单据
            $scope.params.projectFileInfo = $scope.projectInfoDataTable; //立项资料
            $scope.params.productSpecsInfo = $scope.productSpecsDataTable; //产品规格
            $scope.params.supplierInvitationInfo = $scope.supplierInvitationDataTable;//邀请供应商列表
            $scope.params.nonPriceFileInfo = $scope.nonPriceFileDataTable;//非价格文件
            $scope.params.nonPriceSelectFileInfo = $scope.nonPriceSelectFileDataTable;//非价格文件(选择文件)
            $scope.params.priceFileInfo = $scope.priceFileDataTable;//价格文件
            $scope.params.scoringTeamInfo = $scope.scoringTeamDataTable;//评分小组
            $scope.params.witnessTeamInfo = $scope.witnessTeamDataTable;//见证小组

            httpServer.post(URLAPI.saveProjectInfo, {
                'params': JSON.stringify($scope.params)
            }, function (res) {
                if ('S' == res.status) {
                    $scope.program = res.data;
                    $scope.changeFlag = 'N';
                    $scope.search($scope.program.projectId);
                    if($scope.program.projectStatus != '20'){
                        JS.alert('保存成功');
                    }
                } else {
                    JS.alert(res.msg, 'error', '确定');
                }
            }, function (error) {
                console.error(error);
                JS.alert('保存失败', 'error', '确定');
            })
        }

        //导入回调方法:刷新数据
        $scope.returnFlash = function () {
            $scope.search();
        };

        $scope.showScoringStander = function(){
            var scoringStandardId = $scope.program.scoringStandardId;
            if(null != scoringStandardId && scoringStandardId != undefined){
                iframeTabAction('评分标准详情', 'equPonStandardsEdit/' + scoringStandardId)
            }
        };

        /********************查询立项资料信息**********************/
        $scope.searchProjectFileInfo = function (type) {
            httpServer.post(URLAPI.findProjectAttachment, {
                params: JSON.stringify({
                    projectId: $scope.program.projectId,
                    fileType: "ProjectInfo"
                })
            }, function (res) {
                if (res.status == 'S') {
                    $scope.projectInfoDataTable = res.data;
                    $scope.initProjectInfoDataTable = angular.copy($scope.projectInfoDataTable);
                    if(type == '1'){
                        for(var i = 0; i < $scope.projectInfoDataTable.length; i++){
                            $scope.projectInfoDataTable[i].sourceId = $scope.projectInfoDataTable[i].attachmentId;
                            $scope.projectInfoDataTable[i].attachmentId = '';
                        }
                    }
                }
            }, function (error) {
                console.error(error)
            })
        };

        /********************查询立项资料信息(变更前)**********************/
        $scope.searchParentProjectFileInfo = function () {
            httpServer.post(URLAPI.findProjectAttachment, {
                params: JSON.stringify({
                    projectId: $scope.initProgram.projectId,
                    fileType: "ProjectInfo"
                })
            }, function (res) {
                if (res.status == 'S') {
                    $scope.initProjectInfoDataTable = res.data;
                    for(var i = 0; i < $scope.projectInfoDataTable.length; i++){
                        var row = $scope.projectInfoDataTable[i];
                        $scope.attachmentNameChangeEvent(row);
                        $scope.descriptionChangeEvent(row);
                        $scope.attachmentChangeEvent(row);
                    }
                }
            }, function (error) {
                console.error(error)
            })
        };

        /********************查询产品规格信息**********************/
        $scope.searchProductSpecsInfo = function (type) {
            httpServer.post(URLAPI.findProductSpecs, {
                params: JSON.stringify({
                    projectId: $scope.program.projectId
                })
            }, function (res) {
                if (res.status == 'S') {
                    $scope.productSpecsDataTable = res.data;
                    $scope.initProductSpecsDataTable = angular.copy($scope.productSpecsDataTable);
                    if(type == '1'){
                        for(var i = 0; i < $scope.productSpecsDataTable.length; i++){
                            $scope.productSpecsDataTable[i].sourceId = $scope.productSpecsDataTable[i].specsId;
                            $scope.productSpecsDataTable[i].specsId = '';
                        }
                    }
                }
            }, function (error) {
                console.error(error)
            })
        };

        /********************查询产品规格信息(变更前)**********************/
        $scope.searchParentProductSpecsInfo = function () {
            httpServer.post(URLAPI.findProductSpecs, {
                params: JSON.stringify({
                    projectId: $scope.initProgram.projectId
                })
            }, function (res) {
                if (res.status == 'S') {
                    $scope.initProductSpecsDataTable = res.data;
                    for(var i = 0; i < $scope.productSpecsDataTable.length; i++){
                        var row = $scope.productSpecsDataTable[i];
                        $scope.productNameChangeEvent(row);
                        $scope.targetCostChangeEvent(row);
                        $scope.annualSalesQuantityChangeEvent(row);
                        $scope.annualSalesAmountChangeEvent(row);
                    }
                }
            }, function (error) {
                console.error(error)
            })
        };

        /********************查询邀请供应商列表********************/
        $scope.searchSupplierInvitationInfo = function (type) {
            httpServer.post(URLAPI.findSupplierInvitation, {
                params: JSON.stringify({
                    projectId: $scope.program.projectId
                })
            }, function (res) {
                if (res.status == 'S') {
                    $scope.supplierInvitationDataTable = res.data;
                    $scope.initSupplierInvitationDataTable = angular.copy($scope.supplierInvitationDataTable);
                    if(type == '1'){
                        for(var i = 0; i < $scope.supplierInvitationDataTable.length; i++){
                            $scope.supplierInvitationDataTable[i].sourceId = $scope.supplierInvitationDataTable[i].invitationId;
                            $scope.supplierInvitationDataTable[i].invitationId = '';
                        }
                    }
                }
            }, function (error) {
                console.error(error)
            })
        };

        /********************查询邀请供应商列表(变更前)**********************/
        $scope.searchParentSupplierInvitationInfo = function () {
            httpServer.post(URLAPI.findSupplierInvitation, {
                params: JSON.stringify({
                    projectId: $scope.initProgram.projectId
                })
            }, function (res) {
                if (res.status == 'S') {
                    $scope.initSupplierInvitationDataTable = res.data;
                    for(var i = 0; i < $scope.supplierInvitationDataTable.length; i++){
                        var row = $scope.supplierInvitationDataTable[i];
                        $scope.supplierNameChangeEvent(row);
                        $scope.associateFactoryNameChangeEvent(row);
                        $scope.contactNameChangeEvent(row);
                    }
                }
            }, function (error) {
                console.error(error)
            })
        };

        /********************查询非价格文件信息********************/
        $scope.searchNonPriceFileInfo = function (type) {
            httpServer.post(URLAPI.findProjectAttachment, {
                params: JSON.stringify({
                    projectId: $scope.program.projectId,
                    fileType: "NonPriceFile"
                })
            }, function (res) {
                if (res.status == 'S') {
                    $scope.nonPriceFileDataTable = res.data;
                    $scope.initNonPriceFileDataTable = angular.copy($scope.nonPriceFileDataTable);
                    if(type == '1'){
                        for(var i = 0; i < $scope.nonPriceFileDataTable.length; i++){
                            $scope.nonPriceFileDataTable[i].sourceId = $scope.nonPriceFileDataTable[i].attachmentId;
                            $scope.nonPriceFileDataTable[i].attachmentId = '';
                        }
                    }
                }
            }, function (error) {
                console.error(error)
            })
        };

        /********************查询非价格文件信息(变更前)**********************/
        $scope.searchParentNonPriceFileInfo = function () {
            httpServer.post(URLAPI.findProjectAttachment, {
                params: JSON.stringify({
                    projectId: $scope.initProgram.projectId,
                    fileType: "NonPriceFile"
                })
            }, function (res) {
                if (res.status == 'S') {
                    $scope.initNonPriceFileDataTable = res.data;
                    for(var i = 0; i < $scope.nonPriceFileDataTable.length; i++){
                        var row = $scope.nonPriceFileDataTable[i];
                        $scope.attachmentName3ChangeEvent(row);
                        $scope.description3ChangeEvent(row);
                        $scope.attachment3ChangeEvent(row);
                    }
                }
            }, function (error) {
                console.error(error)
            })
        };

        /********************查询非价格文件(选择)信息********************/
        $scope.searchNonPriceSelectFileInfo = function (type) {
            httpServer.post(URLAPI.findProjectAttachment, {
                params: JSON.stringify({
                    projectId: $scope.program.projectId,
                    fileType: "NonPriceSelectFile"
                })
            }, function (res) {
                if (res.status == 'S') {
                    $scope.nonPriceSelectFileDataTable = res.data;
                    $scope.initNonPriceSelectFileDataTable = angular.copy($scope.nonPriceSelectFileDataTable);
                    if(type == '1'){
                        for(var i = 0; i < $scope.nonPriceSelectFileDataTable.length; i++){
                            $scope.nonPriceSelectFileDataTable[i].sourceId = $scope.nonPriceSelectFileDataTable[i].attachmentId;
                            $scope.nonPriceSelectFileDataTable[i].attachmentId = '';
                        }
                    }
                }
            }, function (error) {
                console.error(error)
            })
        };

        /********************查询非价格文件信息(变更前)**********************/
        $scope.searchParentNonPriceSelectFileInfo = function () {
            httpServer.post(URLAPI.findProjectAttachment, {
                params: JSON.stringify({
                    projectId: $scope.initProgram.projectId,
                    fileType: "NonPriceSelectFile"
                })
            }, function (res) {
                if (res.status == 'S') {
                    $scope.initNonPriceSelectFileDataTable = res.data;
                    for(var i = 0; i < $scope.nonPriceSelectFileDataTable.length; i++){
                        var row = $scope.nonPriceSelectFileDataTable[i];
                        $scope.selectedFlagChangeEvent(row);
                        $scope.description2ChangeEvent(row);
                    }
                }
            }, function (error) {
                console.error(error)
            })
        };

        /********************查询价格文件信息********************/
        $scope.searchPriceFileInfo = function (type) {
            httpServer.post(URLAPI.findProjectAttachment, {
                params: JSON.stringify({
                    projectId: $scope.program.projectId,
                    fileType: "PriceFile"
                })
            }, function (res) {
                if (res.status == 'S') {
                    $scope.priceFileDataTable = res.data;
                    $scope.initPriceFileDataTable = angular.copy($scope.priceFileDataTable);
                    if(type == '1'){
                        for(var i = 0; i < $scope.priceFileDataTable.length; i++){
                            $scope.priceFileDataTable[i].sourceId = $scope.priceFileDataTable[i].attachmentId;
                            $scope.priceFileDataTable[i].attachmentId = '';
                        }
                    }
                }
            }, function (error) {
                console.error(error)
            })
        };

        /********************查询价格文件信息(变更前)**********************/
        $scope.searchParentPriceFileInfo = function () {
            httpServer.post(URLAPI.findProjectAttachment, {
                params: JSON.stringify({
                    projectId: $scope.initProgram.projectId,
                    fileType: "PriceFile"
                })
            }, function (res) {
                if (res.status == 'S') {
                    $scope.initPriceFileDataTable = res.data;
                    for(var i = 0; i < $scope.priceFileDataTable.length; i++){
                        var row = $scope.priceFileDataTable[i];
                        $scope.attachmentName4ChangeEvent(row);
                        $scope.description4ChangeEvent(row);
                        $scope.attachment4ChangeEvent(row);
                    }
                }
            }, function (error) {
                console.error(error)
            })
        };

        /********************查询评分小组信息********************/
        $scope.searchScoringTeamInfo = function (type) {
            httpServer.post(URLAPI.findScoringTeam, {
                params: JSON.stringify({
                    projectId: $scope.program.projectId
                })
            }, function (res) {
                if (res.status == 'S') {
                    $scope.scoringTeamDataTable = res.data;
                    $scope.initScoringTeamDataTable = angular.copy(res.data);
                    if(type == '1'){
                        for(var i = 0; i < $scope.scoringTeamDataTable.length; i++){
                            $scope.scoringTeamDataTable[i].sourceId = $scope.scoringTeamDataTable[i].teamId;
                            $scope.scoringTeamDataTable[i].teamId = '';
                        }
                    }
                }
            }, function (error) {
                console.error(error)
            })
        };

        /********************查询见证小组信息********************/
        $scope.searchwitnessTeamInfo = function (type) {
            httpServer.post(URLAPI.findWitnessTeam, {
                params: JSON.stringify({
                    projectId: $scope.program.projectId
                })
            }, function (res) {
                if (res.status == 'S') {
                    $scope.witnessTeamDataTable = res.data;
                    $scope.initWitnessTeamDataTable = angular.copy(res.data);
                    if(type == '1'){
                        for(var i = 0; i < $scope.witnessTeamDataTable.length; i++){
                            $scope.witnessTeamDataTable[i].sourceId = $scope.witnessTeamDataTable[i].teamId;
                            $scope.witnessTeamDataTable[i].teamId = '';
                        }
                    }
                }
            }, function (error) {
                console.error(error)
            })
        };

        //初始化非价格文件选择文件
        $scope.initNonPriceSelectFile = function () {
            httpServer.post(URLAPI.findProjectAttachment, {
                params: JSON.stringify({
                    fileType:'FixedFile'
                })
            }, function (res) {
                if (res.status == 'S') {
                    for(var i = 0; i < res.data.length; i++){
                        $scope.addNonPriceSelectFile();
                        $scope.nonPriceSelectFileDataTable[i].attachmentName = res.data[i].attachmentName;
                        $scope.nonPriceSelectFileDataTable[i].description = res.data[i].description;
                        $scope.nonPriceSelectFileDataTable[i].fileId = res.data[i].fileId;
                        $scope.nonPriceSelectFileDataTable[i].fileName = res.data[i].fileName;
                        $scope.nonPriceSelectFileDataTable[i].filePath = res.data[i].filePath;
                        $scope.nonPriceSelectFileDataTable[i].fileType = 'NonPriceSelectFile';
                        $scope.nonPriceSelectFileDataTable[i].index = res.data[i].index;
                    }
                }
            }, function (error) {
                console.error(error)
            })
        }

        //查询立项相关信息
        $scope.search = function (id) {
            httpServer.post(URLAPI.findProjectInfo, {
                params: JSON.stringify({
                    projectId: id
                })
            }, function (res) {
                if (res.status == 'S') {
                    $scope.program = res.data[0];
                    $scope.program.departmentName = departmentName


                    //变更标志为Y,查询父级立项单据数据，并进行复制
                    if($scope.changeFlag == 'Y'){
                        $scope.searchProjectFileInfo("1");
                        $scope.searchProductSpecsInfo("1");
                        $scope.searchSupplierInvitationInfo("1");
                        $scope.searchNonPriceFileInfo("1");
                        $scope.searchNonPriceSelectFileInfo("1");
                        $scope.searchPriceFileInfo("1");
                        $scope.searchScoringTeamInfo("1");
                        $scope.searchwitnessTeamInfo("1");
                        $scope.initProgram = angular.copy($scope.program);
                        $scope.program.projectId = '';
                        $scope.setProjectVersion($scope.program.projectVersion);
                        $scope.program.isChangeProject = 'Y';
                        $scope.program.canChangeFlag = 'Y';
                        $scope.program.projectStatus = '10';
                        $scope.program.projectStatusMeaning = '拟定';
                        $scope.program.sendQuotationInvitationDate = '';
                        $scope.program.parentProjectNumber = $scope.program.projectNumber;
                        $scope.program.projectNumber = $scope.program.sourceProjectNumber + '-' + $scope.program.projectVersion;

                    }else{
                        if($scope.program.projectStatus == '10' || $scope.program.projectStatus == '40'){
                            $scope.readonlyFlag = 'N';
                        }else{
                            $scope.readonlyFlag = 'Y';
                        }


                        if($scope.program.createdBy == $scope.flags.userId){
                            $scope.showFlag = 'Y';
                        }else{
                            $scope.showFlag = 'N';
                        }

                        if($scope.program.sendQuotationInvitationDate == undefined || $scope.program.sendQuotationInvitationDate == null){
                            $scope.sendInvitationReadonlyFlag = 'N';
                        }else{
                            $scope.sendInvitationReadonlyFlag = 'Y';
                        }

                        $scope.searchProjectFileInfo("2");
                        $scope.searchProductSpecsInfo("2");
                        $scope.searchSupplierInvitationInfo("2");
                        $scope.searchNonPriceFileInfo("2");
                        $scope.searchNonPriceSelectFileInfo("2");
                        $scope.searchPriceFileInfo("2");
                        $scope.searchScoringTeamInfo("2");
                        $scope.searchwitnessTeamInfo("2");
                        //如果当前单据为变更立项单据
                        if($scope.program.isChangeProject == 'Y'){
                            //查找变更父级立项单据信息
                            httpServer.post(URLAPI.findProjectInfo, {
                                params: JSON.stringify({
                                    projectNumber: $scope.program.parentProjectNumber
                                })
                            }, function (res) {
                                if (res.status == 'S') {
                                    $scope.initProgram = res.data[0];
                                    $scope.searchParentProjectFileInfo();
                                    $scope.searchParentProductSpecsInfo();
                                    $scope.searchParentSupplierInvitationInfo();
                                    $scope.searchParentNonPriceFileInfo();
                                    $scope.searchParentNonPriceSelectFileInfo();
                                    $scope.searchParentPriceFileInfo();
                                    // $scope.searchParentScoringTeamInfo();
                                    // $scope.searchParentwitnessTeamInfo();

                                    //对比，不一样则设置为红色
                                    $scope.projectNameChangeEvent();
                                    $scope.relevantCatetoryChangeEvent();
                                    $scope.projectCategoryChangeEvent();
                                    $scope.seriesNameChangeEvent();
                                    $scope.projectPurchaseAmountChangeEvent();
                                    $scope.projectCycleFromChangeEvent();
                                    $scope.projectCycleToChangeEvent();
                                    $scope.quotationDeadlineChangeEvent();
                                    $scope.scoringStandardChangeEvent();
                                    $scope.presentCooperationSupplierChangeEvent();
                                    $scope.sensoryTestTypesChangeEvent();
                                    $scope.remarkChangeEvent();
                                }
                            }, function (error) {
                                console.error(error)
                            })
                        }else{
                            //如果当前单据为新增变更单据
                            // if(changeFlag == 'Y'){
                            //     $scope.searchProjectFileInfo("1");
                            //     $scope.searchProductSpecsInfo("1");
                            //     $scope.searchSupplierInvitationInfo("1");
                            //     $scope.searchNonPriceFileInfo("1");
                            //     $scope.searchNonPriceSelectFileInfo("1");
                            //     $scope.searchPriceFileInfo("1");
                            //     $scope.initProgram = angular.copy($scope.program);
                            //     $scope.program.projectId = '';
                            //     $scope.setProjectVersion($scope.program.projectVersion);
                            //     $scope.program.isChangeProject = 'Y';
                            //     $scope.program.canChangeFlag = 'N';
                            //     $scope.program.projectStatus = '10';
                            //     $scope.program.projectStatusMeaning = '拟定';
                            //     $scope.program.parentProjectNumber = $scope.program.projectNumber;
                            //     $scope.program.projectNumber = $scope.program.sourceProjectNumber + '-' + $scope.program.projectVersion;
                            // }
                        }
                    }
                    //查询各个子信息
                    $scope.historicParam = {}
                    $scope.historicParam.code = $scope.program.projectNumber
                    httpServer.post(URLAPI.getActivitiesHistoric, {
                        params: JSON.stringify($scope.historicParam)
                    }, function (resa) {
                        if (resa.status == "S") {
                            $scope.processInstanceParams = {
                                processInstanceId: resa.data
                            };
                            $scope.taskTable.search();
                        }
                    }, function (error) {
                    });
                }
            }, function (error) {
                console.error(error)
            })
        }

        //增加立项资料
        $scope.addProjectInfo = function () {
            var projectDetailArray = {
                attachmentName: "",
                description: "",
                fileId: "",
                fileName: "",
                filePath: "",
                fileType: "ProjectInfo",
                index: $scope.rowsIndex + 1
            };
            $scope.rowsIndex = $scope.rowsIndex + 1;
            $scope.projectInfoDataTable.push(projectDetailArray);
        };

        //增加产品规格
        $scope.addProductSpecsInfo = function(){
            var productSpecsArray = {
                productName: "",
                targetCost: "",
                annualSalesQuantity: "",
                annualSalesAmount: "",
                index: $scope.rowsIndex + 1
            };
            $scope.rowsIndex = $scope.rowsIndex + 1;
            $scope.productSpecsDataTable.push(productSpecsArray);
        }

        //增加邀请供应商列表
        $scope.addSupplierInvitation = function(){
            var supplierInvitationArray = {
                oiPercent:"",
                supplierId:"",
                associateFactory:"",
                quotationContact:"",
                supplierName: "",
                supplierNumber: "",
                associateFactoryName: "",
                associateFactoryNumber: "",
                contactName:"",
                isQuit:"N",
                reason:"",
                projectVersion: $scope.program.projectVersion,
                index: $scope.rowsIndex + 1
            };
            $scope.rowsIndex = $scope.rowsIndex + 1;
            $scope.supplierInvitationDataTable.push(supplierInvitationArray);
        }

        //增加非价格文件
        $scope.addNonPriceFile = function () {
            var projectDetailArray = {
                attachmentName: "",
                description: "",
                fileId: "",
                fileName: "",
                filePath: "",
                fileType: "NonPriceFile",
                index: $scope.rowsIndex + 1
            };
            $scope.rowsIndex = $scope.rowsIndex + 1;
            $scope.nonPriceFileDataTable.push(projectDetailArray);
        };

        //增加非价格文件
        $scope.addNonPriceSelectFile = function () {
            var projectDetailArray = {
                attachmentName: "",
                description: "",
                fileId: "",
                fileName: "",
                filePath: "",
                fileType: "NonPriceSelectFile",
                index: $scope.rowsIndex + 1
            };
            $scope.rowsIndex = $scope.rowsIndex + 1;
            $scope.nonPriceSelectFileDataTable.push(projectDetailArray);
        };

        //增加价格文件
        $scope.addPriceFile = function () {
            var projectDetailArray = {
                attachmentName: "",
                description: "",
                fileId: "",
                fileName: "",
                filePath: "",
                fileType: "PriceFile",
                index: $scope.rowsIndex + 1
            };
            $scope.rowsIndex = $scope.rowsIndex + 1;
            $scope.priceFileDataTable.push(projectDetailArray);
        };

        //初始化评分小组
        $scope.initScoringTeam = function () {
            var scoringTeam1 = {
                memberNumber: JSON.parse(sessionStorage[appName + '_successLoginInfo']).employeeNumber,
                memberName: JSON.parse(sessionStorage[appName + '_successLoginInfo']).userFullName,
                description: "",
                memberRole: "项目负责人",
                fixFlag:"Y"
            };
            var scoringTeam2 = {
                memberNumber: "",
                memberName: "",
                description: "",
                memberRole: "QA评分",
                fixFlag:"N"
            };
            $scope.scoringTeamDataTable.push(scoringTeam1);
            $scope.scoringTeamDataTable.push(scoringTeam2);
        }

        //初始化见证小组
        $scope.initWitnessTeam = function () {
            var WitnessTeam1 = {
                roleName: "IA",
                deptName: "IA",
                defaultMember: "",
                witnessMember: "",
                defaultMemberName: "",
                witnessMemberName: ""
            };
            var WitnessTeam2 = {
                roleName: "Security",
                deptName: "Security",
                defaultMember: "",
                witnessMember: "",
                defaultMemberName: "",
                witnessMemberName: ""
            };
            var WitnessTeam3 = {
                roleName: "QA",
                deptName: "QA",
                defaultMember: "",
                witnessMember: "",
                defaultMemberName: "",
                witnessMemberName: ""
            };
            $scope.witnessTeamDataTable.push(WitnessTeam1);
            $scope.witnessTeamDataTable.push(WitnessTeam2);
            $scope.witnessTeamDataTable.push(WitnessTeam3);
        }

        /********************判断是新增还是修改********************/
        if (id == "" || id == undefined) {
            $scope.initNonPriceSelectFile();
            $scope.initScoringTeam();
            $scope.initWitnessTeam();
            $scope.program.projectVersion = '01';
        } else {
            $scope.search(id);
        };

        //选择评分标准
        $scope.selectScoringStandard = function (value) {
            $scope.current.standardsStatus = '30';
            $scope.current.standardsStatusName = '生效';
            $scope.current.deptCode = JSON.parse(sessionStorage[appName + '_successLoginInfo']).deptCode;
            $scope.current.department = JSON.parse(sessionStorage[appName + '_successLoginInfo']).deptCode;
            $('#selectScoringStandardLov').modal('toggle')
        }

        //评分标准查询-LOV
        $scope.findScoringStandard = function(){
            $scope.lovDataTable.getData();
        }

        $scope.selectRow = function (row,lovDataTable) {
            for(var i = 0;i<lovDataTable.length;i++){
                lovDataTable[i].isSelect = 'N'
            }
            $scope.currentList = row
            $scope.isLovSelect = 'Y';
            $scope.params.radioValue = row.standardsCode;
            row.isSelect = 'Y';
        }

        //选择评分标准-回调
        $scope.selectScoringStandardReturn = function () {
            $scope.program.scoringStandard = $scope.currentList.standardsCode;
            $scope.program.scoringStandardId = $scope.currentList.standardsId;
        }

        //上传-立项资料-附件
        $scope.uploadProjectInfoFile = function(){
            var row = $scope.projectInfoDataTable[$scope.projectInfoDataTable.selectRow];
            var file = document.querySelector('#projectInfoFile' + row.index).files[0];
            $scope.uploadFile(row,file);
            $scope.attachmentChangeEvent(row);
        }

        //上传-非价格文件-附件
        $scope.uploadNonPriceFile = function(){
            var row = $scope.nonPriceFileDataTable[$scope.nonPriceFileDataTable.selectRow];
            var file = document.querySelector('#nonPriceFile' + row.index).files[0];
            $scope.uploadFile(row,file);
            $scope.attachment3ChangeEvent(row);
        }

        //上传-价格文件-附件
        $scope.uploadPriceFile = function(){
            var row = $scope.priceFileDataTable[$scope.priceFileDataTable.selectRow];
            var file = document.querySelector('#priceFile' + row.index).files[0];
            $scope.uploadFile(row,file);
            $scope.attachment4ChangeEvent(row);
        }

        //删除-立项资料行
        $scope.deleteProjectInfoFile = function(row,index){
            JS.confirm('删除','确定删除立项资料？','确定',function() {
                if (row.attachmentId == null || row.attachmentId == "") {
                    $scope.projectInfoDataTable.splice(index, 1);             //新增列没有ID直接删除
                    JS.alert("操作成功!", "已成功删除数据！", "success");
                }
                else {
                    //修改列有ID删除该行
                    httpServer.post(URLAPI.deleteProjectAttachment,{
                        'params': JSON.stringify({
                            "id": row.attachmentId
                        })
                    }, function (res) {
                        if (res.status === 'S') {
                            JS.alert("删除成功!", "success", "确定");
                            $scope.projectInfoDataTable.splice(index, 1);
                        } else {
                            JS.alert(res.msg, "error", "确定");
                        }
                    });
                }
            })
        }

        //删除-产品规格行
        $scope.deleteProjectSpecsInfo = function(row,index){
            JS.confirm('删除','确定删除产品规格？','确定',function() {
                if (row.specsId == null || row.specsId == "") {
                    $scope.productSpecsDataTable.splice(index, 1);             //新增列没有ID直接删除
                    JS.alert("操作成功!", "已成功删除数据！", "success");
                }
                else {
                    //修改列有ID删除该行
                    httpServer.post(URLAPI.deleteProductSpecs,{
                        'params': JSON.stringify({
                            "id": row.specsId
                        })
                    }, function (res) {
                        if (res.status === 'S') {
                            JS.alert("删除成功!", "success", "确定");
                            $scope.productSpecsDataTable.splice(index, 1);
                        } else {
                            JS.alert(res.msg, "error", "确定");
                        }
                    });
                }
            })
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
                        "reason":row.reason
                    })
                }, function (res) {
                    if (res.status === 'S') {
                        JS.alert('退出成功！');
                        $scope.searchSupplierInvitationInfo("2");
                        $scope.searchParentSupplierInvitationInfo();
                    } else {
                        JS.alert(res.msg, "error", "确定");
                    }
                });
            })
        }

        //删除-供应商列表行
        $scope.deleteSupplierInvitation = function(row,index){
            JS.confirm('删除','确定删除供应商邀请行？','确定',function() {
                if (row.invitationId == null || row.invitationId == "") {
                    $scope.supplierInvitationDataTable.splice(index, 1);             //新增列没有ID直接删除
                    JS.alert('删除成功！');
                }
                else {
                    //修改列有ID删除该行
                    httpServer.post(URLAPI.deleteSupplierInvitation,{
                        'params': JSON.stringify({
                            "id": row.invitationId
                        })
                    }, function (res) {
                        if (res.status === 'S') {
                            JS.alert("删除成功!", "success", "确定");
                            $scope.supplierInvitationDataTable.splice(index, 1);
                        } else {
                            JS.alert(res.msg, "error", "确定");
                        }
                    });
                }
            })
        }

        //删除-非价格文件行
        $scope.deleteNonPriceFile = function(row,index){
            JS.confirm('删除','确定删除非价格文件行？','确定',function() {
                if (row.attachmentId == null || row.attachmentId == "") {
                    $scope.nonPriceFileDataTable.splice(index, 1);             //新增列没有ID直接删除
                    JS.alert("操作成功!", "已成功删除数据！", "success");
                }
                else {
                    //修改列有ID删除该行
                    httpServer.post(URLAPI.deleteProjectAttachment,{
                        'params': JSON.stringify({
                            "id": row.attachmentId
                        })
                    }, function (res) {
                        if (res.status === 'S') {
                            JS.alert("删除成功!", "success", "确定");
                            $scope.nonPriceFileDataTable.splice(index, 1);
                        } else {
                            JS.alert(res.msg, "error", "确定");
                        }
                    });
                }
            })
        }

        //删除-价格文件行
        $scope.deletePriceFile = function(row,index){
            JS.confirm('删除','确定删除价格文件行？','确定',function() {
                if (row.attachmentId == null || row.attachmentId == "") {
                    $scope.priceFileDataTable.splice(index, 1);             //新增列没有ID直接删除
                    JS.alert("操作成功!", "已成功删除数据！", "success");
                }
                else {
                    //修改列有ID删除该行
                    httpServer.post(URLAPI.deleteProjectAttachment,{
                        'params': JSON.stringify({
                            "id": row.attachmentId
                        })
                    }, function (res) {
                        if (res.status === 'S') {
                            JS.alert("删除成功!", "success", "确定");
                            $scope.priceFileDataTable.splice(index, 1);
                        } else {
                            JS.alert(res.msg, "error", "确定");
                        }
                    });
                }
            })
        }

        //选择现合作供应商
        $scope.selectpresentCooperationSupplier = function (value) {
            $scope.selectSupplierParams = {
                "deptCode" : $scope.program.deptCode
            };
            $('#selectpresentCooperationSupplierLov').modal('toggle')
        };

        //选择现合作供应商回调
        $scope.selectpresentCooperationSupplierReturn = function (key, value, currentList) {
            $scope.program.presentCooperationSupplierNumber = currentList[0].supplierNumber;
            $scope.program.presentCooperationSupplier = currentList[0].supplierName;
        }


        //邀请供应商列表-选择供应商
        $scope.getSupplierInfo = function (value) {
            $scope.selectSupplierParams = {
                "deptCode" : $scope.program.deptCode,
                "supplierStatus" : 'QUALIFIED,TEMPLATE'
            };
            $('#selectSupplierLov').modal('toggle')
        };

        //邀请供应商列表-选择供应商-回调
        $scope.selectSupplierReturn = function (key, value, currentList) {
            var row = $scope.supplierInvitationDataTable[$scope.supplierInvitationDataTable.selectRow];
            row.supplierId = currentList[0].supplierId;
            row.supplierNumber = currentList[0].supplierNumber;
            row.supplierName = currentList[0].supplierName;

            $scope.supplierNameChangeEvent(row);
        }

        //邀请供应商列表-选择关联工厂
        $scope.getAssociateFactoryInfo = function (row) {
            $scope.selectFactoryParams = {
                "supplierId" : row.supplierId,
                "deptCode":$scope.program.deptCode
            };
            $('#selectFactoryLov').modal('toggle')
        };

        //邀请供应商列表-选择关联工厂-回调
        $scope.selectFactoryReturn = function (key, value, currentList) {
            var row = $scope.supplierInvitationDataTable[$scope.supplierInvitationDataTable.selectRow];
            row.associateFactory = currentList[0].supplierId;
            row.associateFactoryNumber = currentList[0].supplierNumber;
            row.associateFactoryName = currentList[0].supplierName;
            $scope.associateFactoryNameChangeEvent(row);
        }

        //邀请供应商列表-选择报价联系人
        $scope.getSupplierContactInfo = function (row) {
            $scope.selectQuotationContactParams = {
                "supplierId" : row.supplierId,
                "deptCode":$scope.program.deptCode
            };
            $('#selectQuotationContactLov').modal('toggle')
        };

        //邀请供应商列表-选择报价联系人-回调
        $scope.selectQuotationContactReturn = function (key, value, currentList) {
            var row = $scope.supplierInvitationDataTable[$scope.supplierInvitationDataTable.selectRow];
            row.quotationContact = currentList[0].supplierContactId;
            row.contactName = currentList[0].contactName;
            $scope.contactNameChangeEvent(row);
        }

        //评分小组-选择成员
        $scope.getScoringTeamMember = function (value) {
            // $scope.selectCategoryParams = {
            //     "deptCode" : $scope.program.deptCode
            // };
            $('#selectScoringMemberLov').modal('toggle')
        };

        //评分小组-选择成员-回调
        $scope.selectScoringMemberReturn = function (key, value, currentList) {
            var row = $scope.scoringTeamDataTable[$scope.scoringTeamDataTable.selectRow];
            row.memberName = currentList[0].personName;
            row.memberNumber = currentList[0].employeeNumber;
        }

        //选择brand team 负责人
        $scope.selectBrandTeamPerson = function (value) {
            $scope.selectBrandTeamParams = {
                "departmentId": $scope.program.deptId
            };
            $('#selectBrandTeamManagerLov').modal('toggle')
        }

        //选择brand team 负责人回调
        $scope.selectBrandTeamManagerReturn = function (key, value, currentList) {
            $scope.program.brandTeamPersonId = currentList[0].employeeNumber;
            $scope.program.brandTeamUserId = currentList[0].userId;
            $scope.program.brandTeamPersonName = currentList[0].personName;
        }

        //见证小组-选择默认人员
        $scope.getWitnessTeamDefaultMember = function (roleName) {
            // $scope.selectCategoryParams = {
            //     "deptCode" : $scope.program.deptCode
            // };
            // var row = $scope.witnessTeamDataTable[$scope.witnessTeamDataTable.selectRow];
            if(roleName == 'IA') {
                $('#selectWitnessIADefaultMemberLov').modal('toggle')
            }else if(roleName == 'Security'){
                $('#selectWitnessSecurityDefaultMemberLov').modal('toggle')
            }else if(roleName == 'QA'){
                $('#selectWitnessQADefaultMemberLov').modal('toggle')
            }
        };

        //见证小组-选择默认人员-回调
        $scope.selectWitnessDefaultMemberReturn = function (key, value, currentList) {
            var row = $scope.witnessTeamDataTable[$scope.witnessTeamDataTable.selectRow];
            row.defaultMember = currentList[0].employeeNumber;
            row.defaultMemberName = currentList[0].personName;
debugger;
            if(row.deptName == 'QA'){
                $scope.program.qaUserNumber = currentList[0].employeeNumber;
                $scope.program.qaUserId = currentList[0].userId;
            }else if(row.deptName == 'IA'){
                $scope.program.iaUserNumber = currentList[0].employeeNumber;
                $scope.program.iaUserId = currentList[0].userId;
            }else if(row.deptName == 'Security'){
                $scope.program.securityUserNumber = currentList[0].employeeNumber;
                $scope.program.securityUserId = currentList[0].userId;
            }
        }

        //见证小组-选择见证人
        $scope.getWitnessTeamMember = function (roleName) {
            // $scope.selectCategoryParams = {
            //     "deptCode" : $scope.program.deptCode
            // };
            // var row = $scope.witnessTeamDataTable[$scope.witnessTeamDataTable.selectRow];
            if(roleName == 'IA') {
                $('#selectWitnessIAMemberLov').modal('toggle')
            }else if(roleName == 'Security'){
                $('#selectWitnessSecurityMemberLov').modal('toggle')
            }else if(roleName == 'QA'){
                $('#selectWitnessQAMemberLov').modal('toggle')
            }
        };

        //见证小组-选择见证人-回调
        $scope.selectWitnessMemberReturn = function (key, value, currentList) {
            var row = $scope.witnessTeamDataTable[$scope.witnessTeamDataTable.selectRow];
            row.witnessMember = currentList[0].employeeNumber;
            row.witnessMemberName = currentList[0].personName;
        }


        //上传附件
        $scope.uploadFile = function (row,file) {
            var fd = new FormData();
            if (!file) {
                SIEJS.alert("请选择上传文件", 'error', '确定');
                return;
            }
            var fileName = file.name;
            var id = row.fileId;
            if(!id){
                id = '0';
            }
            fd.append('bussnessId', id);
            fd.append('file', file);
            fd.append("fileName", fileName);
            $http.post(URLAPI.saveEquFileUpload, fd, {
                transformRequest: angular.identity,
                headers: {
                    'Certificate': sessionStorage[appName + '_certificate'] || localStorage[appName + '_certificate'] || 'nothing',
                    'Content-Type': undefined
                }
            }).success(function (response) {
                console.log(response);
                SIEJS.alert(response.msg, 'success', '确定');
                row.fileId = response.data[0].fileId;
                row.fileName = response.data[0].fileName;
                row.filePath = response.data[0].filePath;
            }).error(function(response) {
                SIEJS.alert(response.msg, 'error', '确定');
            });

        }

        //重新上传-附件
        $scope.changeFile = function (row) {
            row.fileId = null;
            row.filePath = null;
            row.fileName = null;
        }

        //上传立项单据附件
        $scope.uploadProjectFile = function () {
            var fd = new FormData();
            var file = document.querySelector('#file').files[0];
            if (!file) {
                SIEJS.alert("请选择上传文件", 'error', '确定');
                return;
            }
            var fileName = file.name;
            var id = $scope.program.fileId;
            if (!id) {
                id = '0';
            }
            fd.append('bussnessId', id);
            fd.append('file', file);
            fd.append("fileName", fileName);
            $http.post(URLAPI.saveEquFileUpload, fd, {
                transformRequest: angular.identity,
                headers: {
                    'Certificate': sessionStorage[appName + '_certificate'] || localStorage[appName + '_certificate'] || 'nothing',
                    'Content-Type': undefined
                }
            }).success(function (response) {
                console.log(response);
                SIEJS.alert(response.msg, 'success', '确定');
                $scope.program.fileId = response.data[0].fileId;
                $scope.program.fileName = response.data[0].fileName;
                $scope.program.filePath = response.data[0].filePath;
            }).error(function (response) {
                SIEJS.alert(response.msg, 'error', '确定');
            });

        }

        $scope.changeProjectFile = function (row) {
            $scope.program.fileId = null;
            $scope.program.filePath = null;
            $scope.program.fileName = null;
        }

        $scope.setProjectVersion = function(version){
            var t = (parseInt(version) + 1) + '';
            if(t.length == 2){
                $scope.program.projectVersion = t;
            }else{
                $scope.program.projectVersion = '0' + t ;
            }

        }

        /**
         * 变更立项资料
         */
        $scope.btnChangeLX = function(){
            iframeTabAction('立项变更', 'equPonProjectDetail/' + $scope.program.projectId + "/Y");
            // $scope.initProgram = angular.copy($scope.program);
            // $scope.program.projectId = '';
            // $scope.setProjectVersion($scope.program.projectVersion);
            // $scope.program.isChangeProject = 'Y';
            // $scope.program.canChangeFlag = 'N';
            // $scope.program.projectStatus = '10';
            // $scope.program.projectStatusMeaning = '拟定';
            // $scope.program.parentProjectNumber = $scope.program.projectNumber;
            // $scope.program.projectNumber = $scope.program.sourceProjectNumber + '-' + $scope.program.projectVersion;
            //
            // //立项资料
            // $scope.initProjectInfoDataTable = angular.copy($scope.projectInfoDataTable);
            // for(var i = 0; i < $scope.projectInfoDataTable.length; i++){
            //     $scope.projectInfoDataTable[i].sourceId = $scope.projectInfoDataTable[i].attachmentId;
            //     $scope.projectInfoDataTable[i].attachmentId = '';
            // }
            //
            // //产品规格
            // $scope.initProductSpecsDataTable = angular.copy($scope.productSpecsDataTable);
            // for(var i = 0; i < $scope.productSpecsDataTable.length; i++){
            //     $scope.productSpecsDataTable[i].sourceId = $scope.productSpecsDataTable[i].specsId;
            //     $scope.productSpecsDataTable[i].specsId = '';
            // }
            //
            // //邀请供应商列表
            // $scope.initSupplierInvitationDataTable = angular.copy($scope.supplierInvitationDataTable);
            // for(var i = 0; i < $scope.supplierInvitationDataTable.length; i++){
            //     $scope.supplierInvitationDataTable[i].sourceId = $scope.supplierInvitationDataTable[i].invitationId;
            //     $scope.supplierInvitationDataTable[i].invitationId = '';
            // }
        }

        //项目名称值修改事件
        $scope.projectNameChangeEvent = function(){
            if($scope.program.isChangeProject == 'Y'){
                if($scope.program.projectName != $scope.initProgram.projectName){
                    $('#projectName').css('color', 'red');
                }else{
                    $('#projectName').css('color', 'black');
                }
            }
        }

        //项目品类值修改事件
        $scope.relevantCatetoryChangeEvent = function(){
            if($scope.program.isChangeProject == 'Y'){
                if($scope.program.relevantCatetory != $scope.initProgram.relevantCatetory){
                    $('#relevantCatetory').css('color', 'red');
                }else{
                    $('#relevantCatetory').css('color', 'black');
                }
            }
        }

        //项目类型值修改事件
        $scope.projectCategoryChangeEvent = function(){
            if($scope.program.projectCategory == '10'){
                $scope.program.presentCooperationSupplier = '';
            }
            if($scope.program.isChangeProject == 'Y'){
                if($scope.program.projectCategory != $scope.initProgram.projectCategory){
                    $('#projectCategory').css('color', 'red');
                }else{
                    $('#projectCategory').css('color', 'black');
                }
            }
        }

        //系列名称值修改事件
        $scope.seriesNameChangeEvent = function(){
            if($scope.program.isChangeProject == 'Y'){
                if($scope.program.seriesName != $scope.initProgram.seriesName){
                    $('#seriesName').css('color', 'red');
                }else{
                    $('#seriesName').css('color', 'black');
                }
            }
        }


        //项目采购金额值修改事件
        $scope.projectPurchaseAmountChangeEvent = function(){
            if($scope.program.isChangeProject == 'Y'){
                if($scope.program.projectPurchaseAmount != $scope.initProgram.projectPurchaseAmount){
                    $('#projectPurchaseAmount').css('color', 'red');
                }else{
                    $('#projectPurchaseAmount').css('color', 'black');
                }
            }
        }

        //项目周期从值修改事件
        $scope.projectCycleFromChangeEvent = function(){
            if($scope.program.isChangeProject == 'Y'){
                if($scope.program.projectCycleFrom != $scope.initProgram.projectCycleFrom){
                    $('#projectCycleFrom').css('color', 'red');
                }else{
                    $('#projectCycleFrom').css('color', 'black');
                }
            }
        }

        //项目周期至值修改事件
        $scope.projectCycleToChangeEvent = function(){
            if($scope.program.isChangeProject == 'Y'){
                if($scope.program.projectCycleTo != $scope.initProgram.projectCycleTo){
                    $('#projectCycleTo').css('color', 'red');
                }else{
                    $('#projectCycleTo').css('color', 'black');
                }
            }
        }

        //报价截至时间值修改事件
        $scope.quotationDeadlineChangeEvent = function(){
            if($scope.program.isChangeProject == 'Y'){
                if($scope.program.quotationDeadline != $scope.initProgram.quotationDeadline){
                    $('#quotationDeadline').css('color', 'red');
                }else{
                    $('#quotationDeadline').css('color', 'black');
                }
            }
        }

        //评分标准值修改事件
        $scope.scoringStandardChangeEvent = function(){
            if($scope.program.isChangeProject == 'Y'){
                if($scope.program.scoringStandard != $scope.initProgram.scoringStandard){
                    $('#scoringStandard').css('color', 'red');
                }else{
                    $('#scoringStandard').css('color', 'black');
                }
            }
        }

        //项目现合作供应商值修改事件
        $scope.presentCooperationSupplierChangeEvent = function(){
            if($scope.program.isChangeProject == 'Y'){
                if($scope.program.presentCooperationSupplier != $scope.initProgram.presentCooperationSupplier){
                    $('#presentCooperationSupplier').css('color', 'red');
                }else{
                    $('#presentCooperationSupplier').css('color', 'black');
                }
            }
        }

        //感官测试类型值修改事件
        $scope.sensoryTestTypesChangeEvent = function(){
            if($scope.program.isChangeProject == 'Y'){
                if($scope.program.sensoryTestTypes != $scope.initProgram.sensoryTestTypes){
                    $('#sensoryTestTypes').css('color', 'red');
                }else{
                    $('#sensoryTestTypes').css('color', 'black');
                }
            }
        }

        //备注说明值修改事件
        $scope.remarkChangeEvent = function(){
            if($scope.program.isChangeProject == 'Y'){
                if($scope.program.remark != $scope.initProgram.remark){
                    $('#remark').css('color', 'red');
                }else{
                    $('#remark').css('color', 'black');
                }
            }
        }

        //立项资料-文件名称-校验是否修改
        $scope.attachmentNameChangeEvent = function(row){
            if($scope.program.isChangeProject == 'Y'){
                var id = row.sourceId;
                var attachmentName = row.attachmentName;
                var redFlag = false;
                var count = 0;
                if(id != null && id != "" && id != undefined){
                    //修改，判断，符合条件标红
                    for(var i = 0; i < $scope.initProjectInfoDataTable.length; i++){
                        var checkRow = $scope.initProjectInfoDataTable[i];
                        if(checkRow.attachmentId == id){
                            if(checkRow.attachmentName != attachmentName){
                                $('#attachmentName' + row.index).css('color', 'red');
                                redFlag = true;
                                break;
                            }else{
                                $('#attachmentName' + row.index).css('color', 'black');
                            }
                        }
                        count = count + 1;
                    }
                    if(count == $scope.initProjectInfoDataTable.length && redFlag){
                        $('#attachmentName' + row.index).css('color', 'red');
                    }
                }else{
                    //新增，直接标红
                    $('#attachmentName' + row.index).css('color', 'red');
                }
            }
        }

        //立项资料-说明-校验是否修改
        $scope.descriptionChangeEvent = function(row){
            if($scope.program.isChangeProject == 'Y'){
                var id = row.sourceId;
                var description = row.description;
                var redFlag = false;
                var count = 0;
                if(id != null && id != "" && id != undefined){
                    //修改，判断，符合条件标红
                    for(var i = 0; i < $scope.initProjectInfoDataTable.length; i++){
                        var checkRow = $scope.initProjectInfoDataTable[i];
                        if(checkRow.attachmentId == id){
                            if(checkRow.description != description){
                                $('#description' + row.index).css('color', 'red');
                                redFlag = true;
                                break;
                            }else{
                                $('#description' + row.index).css('color', 'black');
                            }
                        }
                        count = count + 1;
                    }
                    if(count == $scope.initProjectInfoDataTable.length && redFlag){
                        $('#description' + row.index).css('color', 'red');
                    }
                }else{
                    //新增，直接标红
                    $('#description' + row.index).css('color', 'red');
                }
            }
        }

        //立项资料-附件-校验是否修改
        $scope.attachmentChangeEvent = function(row){
            if($scope.program.isChangeProject == 'Y'){
                var id = row.sourceId;
                var fileId = row.fileId;
                var redFlag = false;
                var count = 0;
                if(id != null && id != "" && id != undefined){
                    //修改，判断，符合条件标红
                    for(var i = 0; i < $scope.initProjectInfoDataTable.length; i++){
                        var checkRow = $scope.initProjectInfoDataTable[i];
                        if(checkRow.attachmentId == id){
                            if(checkRow.fileId != fileId){
                                row.isChange = 'Y';
                                redFlag = true;
                                break;
                            }else{
                                row.isChange = 'N';
                            }
                        }
                        count = count + 1;
                    }
                    if(count == $scope.initProjectInfoDataTable.length && redFlag){
                        row.isChange = 'Y';
                    }
                }else{
                    //新增，直接标红
                    row.isChange = 'Y';
                }
            }
        }

        //产品规格-产品名称-校验是否修改
        $scope.productNameChangeEvent = function(row){
            if($scope.program.isChangeProject == 'Y'){
                var id = row.sourceId;
                var productName = row.productName;
                var redFlag = false;
                var count = 0;
                if(id != null && id != "" && id != undefined){
                    //修改，判断，符合条件标红
                    for(var i = 0; i < $scope.initProductSpecsDataTable.length; i++){
                        var checkRow = $scope.initProductSpecsDataTable[i];
                        if(checkRow.specsId == id){
                            if(checkRow.productName != productName){
                                $('#productName' + row.index).css('color', 'red');
                                redFlag = true;
                                break;
                            }else{
                                $('#productName' + row.index).css('color', 'black');
                            }
                        }
                        count = count + 1;
                    }
                    if(count == $scope.initProductSpecsDataTable.length && redFlag){
                        $('#productName' + row.index).css('color', 'red');
                    }
                }else{
                    //新增，直接标红
                    $('#productName' + row.index).css('color', 'red');
                }
            }
        }

        //产品规格-目标成本(元)-校验是否修改
        $scope.targetCostChangeEvent = function(row){
            if($scope.program.isChangeProject == 'Y'){
                var id = row.sourceId;
                var targetCost = row.targetCost;
                var redFlag = false;
                var count = 0;
                if(id != null && id != "" && id != undefined){
                    //修改，判断，符合条件标红
                    for(var i = 0; i < $scope.initProductSpecsDataTable.length; i++){
                        var checkRow = $scope.initProductSpecsDataTable[i];
                        if(checkRow.specsId == id){
                            if(checkRow.targetCost != targetCost){
                                $('#targetCost' + row.index).css('color', 'red');
                                redFlag = true;
                                break;
                            }else{
                                $('#targetCost' + row.index).css('color', 'black');
                            }
                        }
                        count = count + 1;
                    }
                    if(count == $scope.initProductSpecsDataTable.length && redFlag){
                        $('#targetCost' + row.index).css('color', 'red');
                    }
                }else{
                    //新增，直接标红
                    $('#targetCost' + row.index).css('color', 'red');
                }
            }
        }

        //产品规格-预估年销售量-校验是否修改
        $scope.annualSalesQuantityChangeEvent = function(row){
            if($scope.program.isChangeProject == 'Y'){
                var id = row.sourceId;
                var annualSalesQuantity = row.annualSalesQuantity;
                var redFlag = false;
                var count = 0;
                if(id != null && id != "" && id != undefined){
                    //修改，判断，符合条件标红
                    for(var i = 0; i < $scope.initProductSpecsDataTable.length; i++){
                        var checkRow = $scope.initProductSpecsDataTable[i];
                        if(checkRow.specsId == id){
                            if(checkRow.annualSalesQuantity != annualSalesQuantity){
                                $('#annualSalesQuantity' + row.index).css('color', 'red');
                                redFlag = true;
                                break;
                            }else{
                                $('#annualSalesQuantity' + row.index).css('color', 'black');
                            }
                        }
                        count = count + 1;
                    }
                    if(count == $scope.initProductSpecsDataTable.length && redFlag){
                        $('#annualSalesQuantity' + row.index).css('color', 'red');
                    }
                }else{
                    //新增，直接标红
                    $('#annualSalesQuantity' + row.index).css('color', 'red');
                }
            }
        }

        //产品规格-预估年销售金额-校验是否修改
        $scope.annualSalesAmountChangeEvent = function(row){
            if($scope.program.isChangeProject == 'Y'){
                var id = row.sourceId;
                var annualSalesAmount = row.annualSalesAmount;
                var redFlag = false;
                var count = 0;
                if(id != null && id != "" && id != undefined){
                    //修改，判断，符合条件标红
                    for(var i = 0; i < $scope.initProductSpecsDataTable.length; i++){
                        var checkRow = $scope.initProductSpecsDataTable[i];
                        if(checkRow.specsId == id){
                            if(checkRow.annualSalesAmount != annualSalesAmount){
                                $('#annualSalesAmount' + row.index).css('color', 'red');
                                redFlag = true;
                                break;
                            }else{
                                $('#annualSalesAmount' + row.index).css('color', 'black');
                            }
                        }
                        count = count + 1;
                    }
                    if(count == $scope.initProductSpecsDataTable.length && redFlag){
                        $('#annualSalesAmount' + row.index).css('color', 'red');
                    }
                }else{
                    //新增，直接标红
                    $('#annualSalesAmount' + row.index).css('color', 'red');
                }
            }
        }

        //邀请供应商列表-供应商名称-校验是否修改
        $scope.supplierNameChangeEvent = function(row){
            if($scope.program.isChangeProject == 'Y'){
                var id = row.sourceId;
                var supplierName = row.supplierName;
                var redFlag = false;
                var count = 0;
                if(id != null && id != "" && id != undefined){
                    //修改，判断，符合条件标红
                    for(var i = 0; i < $scope.initSupplierInvitationDataTable.length; i++){
                        var checkRow = $scope.initSupplierInvitationDataTable[i];
                        if(checkRow.invitationId == id){
                            if(checkRow.supplierName != supplierName){
                                $('#supplierName' + row.index).css('color', 'red');
                                redFlag = true;
                                break;
                            }else{
                                $('#supplierName' + row.index).css('color', 'black');
                            }
                        }
                        count = count + 1;
                    }
                    if(count == $scope.initSupplierInvitationDataTable.length && redFlag){
                        $('#supplierName' + row.index).css('color', 'red');
                    }
                }else{
                    //新增，直接标红
                    $('#supplierName' + row.index).css('color', 'red');
                }
            }
        }

        //邀请供应商列表-关联工厂-校验是否修改
        $scope.associateFactoryNameChangeEvent = function(row){
            if($scope.program.isChangeProject == 'Y'){
                var id = row.sourceId;
                var associateFactoryName = row.associateFactoryName;
                var redFlag = false;
                var count = 0;
                if(id != null && id != "" && id != undefined){
                    //修改，判断，符合条件标红
                    for(var i = 0; i < $scope.initSupplierInvitationDataTable.length; i++){
                        var checkRow = $scope.initSupplierInvitationDataTable[i];
                        if(checkRow.invitationId == id){
                            if(checkRow.associateFactoryName != associateFactoryName){
                                $('#associateFactoryName' + row.index).css('color', 'red');
                                redFlag = true;
                                break;
                            }else{
                                $('#associateFactoryName' + row.index).css('color', 'black');
                            }
                        }
                        count = count + 1;
                    }
                    if(count == $scope.initSupplierInvitationDataTable.length && redFlag){
                        $('#associateFactoryName' + row.index).css('color', 'red');
                    }
                }else{
                    //新增，直接标红
                    $('#associateFactoryName' + row.index).css('color', 'red');
                }
            }
        }

        //邀请供应商列表-报价联系人-校验是否修改
        $scope.contactNameChangeEvent = function(row){
            if($scope.program.isChangeProject == 'Y'){
                var id = row.sourceId;
                var contactName = row.contactName;
                var redFlag = false;
                var count = 0;
                if(id != null && id != "" && id != undefined){
                    //修改，判断，符合条件标红
                    for(var i = 0; i < $scope.initSupplierInvitationDataTable.length; i++){
                        var checkRow = $scope.initSupplierInvitationDataTable[i];
                        if(checkRow.invitationId == id){
                            if(checkRow.contactName != contactName){
                                $('#contactName' + row.index).css('color', 'red');
                                redFlag = true;
                                break;
                            }else{
                                $('#contactName' + row.index).css('color', 'black');
                            }
                        }
                        count = count + 1;
                    }
                    if(count == $scope.initSupplierInvitationDataTable.length && redFlag){
                        $('#contactName' + row.index).css('color', 'red');
                    }
                }else{
                    //新增，直接标红
                    $('#contactName' + row.index).css('color', 'red');
                }
            }
        }

        //非价格文件-选择文件-校验是否修改
        $scope.selectedFlagChangeEvent = function(row){
            if($scope.program.isChangeProject == 'Y'){
                var id = row.sourceId;
                var selectedFlag = row.selectedFlag;
                var redFlag = false;
                var count = 0;
                if(id != null && id != "" && id != undefined){
                    //修改，判断，符合条件标红
                    for(var i = 0; i < $scope.initNonPriceSelectFileDataTable.length; i++){
                        var checkRow = $scope.initNonPriceSelectFileDataTable[i];
                        if(checkRow.attachmentId == id){
                            if(checkRow.selectedFlag != selectedFlag){
                                $('#attachmentName2' + row.index).css('color', 'red');
                                redFlag = true;
                                break;
                            }else{
                                $('#attachmentName2' + row.index).css('color', 'black');
                            }
                        }
                        count = count + 1;
                    }
                    if(count == $scope.initSupplierInvitationDataTable.length && redFlag){
                        $('#attachmentName2' + row.index).css('color', 'red');
                    }
                }else{
                    //新增，直接标红
                    $('#attachmentName2' + row.index).css('color', 'red');
                }
            }
        }

        //非价格文件-选择文件-说明-校验是否修改
        $scope.description2ChangeEvent = function(row){
            if($scope.program.isChangeProject == 'Y'){
                var id = row.sourceId;
                var description = row.description;
                var redFlag = false;
                var count = 0;
                if(id != null && id != "" && id != undefined){
                    //修改，判断，符合条件标红
                    for(var i = 0; i < $scope.initNonPriceSelectFileDataTable.length; i++){
                        var checkRow = $scope.initNonPriceSelectFileDataTable[i];
                        if(checkRow.attachmentId == id){
                            if(checkRow.description != description){
                                $('#description2' + row.index).css('color', 'red');
                                redFlag = true;
                                break;
                            }else{
                                $('#description2' + row.index).css('color', 'black');
                            }
                        }
                        count = count + 1;
                    }
                    if(count == $scope.initSupplierInvitationDataTable.length && redFlag){
                        $('#description2' + row.index).css('color', 'red');
                    }
                }else{
                    //新增，直接标红
                    $('#description2' + row.index).css('color', 'red');
                }
            }
        }

        //非价格文件-文件名称-校验是否修改
        $scope.attachmentName3ChangeEvent = function(row){
            if($scope.program.isChangeProject == 'Y'){
                var id = row.sourceId;
                var attachmentName = row.attachmentName;
                var redFlag = false;
                var count = 0;
                if(id != null && id != "" && id != undefined){
                    //修改，判断，符合条件标红
                    for(var i = 0; i < $scope.initNonPriceFileDataTable.length; i++){
                        var checkRow = $scope.initNonPriceFileDataTable[i];
                        if(checkRow.attachmentId == id){
                            if(checkRow.attachmentName != attachmentName){
                                $('#attachmentName3' + row.index).css('color', 'red');
                                redFlag = true;
                                break;
                            }else{
                                $('#attachmentName3' + row.index).css('color', 'black');
                            }
                        }
                        count = count + 1;
                    }
                    if(count == $scope.initNonPriceFileDataTable.length && redFlag){
                        $('#attachmentName3' + row.index).css('color', 'red');
                    }
                }else{
                    //新增，直接标红
                    $('#attachmentName3' + row.index).css('color', 'red');
                }
            }
        }

        //非价格文件-说明-校验是否修改
        $scope.description3ChangeEvent = function(row){
            if($scope.program.isChangeProject == 'Y'){
                var id = row.sourceId;
                var description = row.description;
                var redFlag = false;
                var count = 0;
                if(id != null && id != "" && id != undefined){
                    //修改，判断，符合条件标红
                    for(var i = 0; i < $scope.initNonPriceFileDataTable.length; i++){
                        var checkRow = $scope.initNonPriceFileDataTable[i];
                        if(checkRow.attachmentId == id){
                            if(checkRow.description != description){
                                $('#description3' + row.index).css('color', 'red');
                                redFlag = true;
                                break;
                            }else{
                                $('#description3' + row.index).css('color', 'black');
                            }
                        }
                        count = count + 1;
                    }
                    if(count == $scope.initNonPriceFileDataTable.length && redFlag){
                        $('#description3' + row.index).css('color', 'red');
                    }
                }else{
                    //新增，直接标红
                    $('#description3' + row.index).css('color', 'red');
                }
            }
        }

        //非价格文件-附件-校验是否修改
        $scope.attachment3ChangeEvent = function(row){
            if($scope.program.isChangeProject == 'Y'){
                var id = row.sourceId;
                var fileId = row.fileId;
                var redFlag = false;
                var count = 0;
                if(id != null && id != "" && id != undefined){
                    //修改，判断，符合条件标红
                    for(var i = 0; i < $scope.initNonPriceFileDataTable.length; i++){
                        var checkRow = $scope.initNonPriceFileDataTable[i];
                        if(checkRow.attachmentId == id){
                            if(checkRow.fileId != fileId){
                                row.isChange = 'Y';
                                redFlag = true;
                                break;
                            }else{
                                row.isChange = 'N';
                            }
                        }
                        count = count + 1;
                    }
                    if(count == $scope.initNonPriceFileDataTable.length && redFlag){
                        row.isChange = 'Y';
                    }
                }else{
                    //新增，直接标红
                    row.isChange = 'Y';
                }
            }
        }

        //价格文件-文件名称-校验是否修改
        $scope.attachmentName4ChangeEvent = function(row){
            if($scope.program.isChangeProject == 'Y'){
                var id = row.sourceId;
                var attachmentName = row.attachmentName;
                var redFlag = false;
                var count = 0;
                if(id != null && id != "" && id != undefined){
                    //修改，判断，符合条件标红
                    for(var i = 0; i < $scope.initPriceFileDataTable.length; i++){
                        var checkRow = $scope.initPriceFileDataTable[i];
                        if(checkRow.attachmentId == id){
                            if(checkRow.attachmentName != attachmentName){
                                $('#attachmentName4' + row.index).css('color', 'red');
                                redFlag = true;
                                break;
                            }else{
                                $('#attachmentName4' + row.index).css('color', 'black');
                            }
                        }
                        count = count + 1;
                    }
                    if(count == $scope.initPriceFileDataTable.length && redFlag){
                        $('#attachmentName4' + row.index).css('color', 'red');
                    }
                }else{
                    //新增，直接标红
                    $('#attachmentName4' + row.index).css('color', 'red');
                }
            }
        }

        //价格文件-说明-校验是否修改
        $scope.description4ChangeEvent = function(row){
            if($scope.program.isChangeProject == 'Y'){
                var id = row.sourceId;
                var description = row.description;
                var redFlag = false;
                var count = 0;
                if(id != null && id != "" && id != undefined){
                    //修改，判断，符合条件标红
                    for(var i = 0; i < $scope.initPriceFileDataTable.length; i++){
                        var checkRow = $scope.initPriceFileDataTable[i];
                        if(checkRow.attachmentId == id){
                            if(checkRow.description != description){
                                $('#description4' + row.index).css('color', 'red');
                                redFlag = true;
                                break;
                            }else{
                                $('#description4' + row.index).css('color', 'black');
                            }
                        }
                        count = count + 1;
                    }
                    if(count == $scope.initPriceFileDataTable.length && redFlag){
                        $('#description4' + row.index).css('color', 'red');
                    }
                }else{
                    //新增，直接标红
                    $('#description4' + row.index).css('color', 'red');
                }
            }
        }

        //价格文件-附件-校验是否修改
        $scope.attachment4ChangeEvent = function(row){
            if($scope.program.isChangeProject == 'Y'){
                var id = row.sourceId;
                var fileId = row.fileId;
                var redFlag = false;
                var count = 0;
                if(id != null && id != "" && id != undefined){
                    //修改，判断，符合条件标红
                    for(var i = 0; i < $scope.initPriceFileDataTable.length; i++){
                        var checkRow = $scope.initPriceFileDataTable[i];
                        if(checkRow.attachmentId == id){
                            if(checkRow.fileId != fileId){
                                row.isChange = 'Y';
                                redFlag = true;
                                break;
                            }else{
                                row.isChange = 'N';
                            }
                        }
                        count = count + 1;
                    }
                    if(count == $scope.initPriceFileDataTable.length && redFlag){
                        row.isChange = 'Y';
                    }
                }else{
                    //新增，直接标红
                    row.isChange = 'Y';
                }
            }
        };
        // 发送报价邀请
        $scope.btnSendInvitation = function () {
            $scope.params.projectId = $scope.program.projectId; //立项单据id
            $scope.params.supplierInvitationInfo = $scope.supplierInvitationDataTable;//邀请供应商列表
            httpServer.post(URLAPI.btnSendInvitation, {
                'params': JSON.stringify($scope.params)
            }, function (res) {
                if ('S' == res.status) {
                    $scope.sendInvitationReadonlyFlag = "Y";
                    JS.alert('发送报价邀请成功');
                } else {
                    JS.alert(res.msg, 'error', '确定');
                }
            }, function (error) {
                console.error(error);
                JS.alert('发送报价邀请失败', 'error', '确定');
            })
        }

        //终止
        $scope.btnTerminate = function(){
            //修改立项单据终止标志为"Y"
            httpServer.post(URLAPI.terminateProjectInfo,{
                'params': JSON.stringify({
                    "projectId": $scope.program.projectId
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

        /**********************************工作流配置1**************************************/
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
            return $scope.urlParams.businessKey ? $scope.urlParams.businessKey : $scope.program.projectId;
        }

        //流程图参数
        $scope.processImageParams = {
            token: sessionStorage.getItem(window.appName + '_certificate')||localStorage.getItem(window.appName + '_certificate'),
            id: 'processImg',
            instanceId: $scope.urlParams.processInstanceId,
            key: 'EQU_LXXGOEM.-999' //流程唯一标识，在流程管理->流程设计->设计 流程中获取，流程配置时修改为对应表单的流程唯一标识
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
                        businessKey:$scope.program.projectId
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
            //校验
            if($scope.validate()){
                return;
            }
            if($scope.program.projectStatus != '10' && $scope.program.projectStatus != '40'){
                JS.alert('状态不是拟定或驳回，不允许提交审批', 'error', '确定');
                return;
            }
            // if($scope.program.isChangeProject != 'Y'){
            //     //自审批通过
            //     //公共
            //     $scope.program.projectStatus = '30';
            //     $scope.params.projectInfo = $scope.program; //立项单据
            //     $scope.params.projectFileInfo = $scope.projectInfoDataTable; //立项资料
            //     $scope.params.productSpecsInfo = $scope.productSpecsDataTable; //产品规格
            //     $scope.params.supplierInvitationInfo = $scope.supplierInvitationDataTable;//邀请供应商列表
            //     $scope.params.nonPriceFileInfo = $scope.nonPriceFileDataTable;//非价格文件
            //     $scope.params.nonPriceSelectFileInfo = $scope.nonPriceSelectFileDataTable;//非价格文件(选择文件)
            //     $scope.params.priceFileInfo = $scope.priceFileDataTable;//价格文件
            //     $scope.params.scoringTeamInfo = $scope.scoringTeamDataTable;//评分小组
            //     $scope.params.witnessTeamInfo = $scope.witnessTeamDataTable;//见证小组
            //
            //     httpServer.post(URLAPI.saveProjectInfo, {
            //         'params': JSON.stringify($scope.params)
            //     }, function (res) {
            //         if ('S' == res.status) {
            //             $scope.program = res.data;
            //             $scope.changeFlag = 'N';
            //             $scope.search($scope.program.projectId);
            //             JS.alert('提交成功');
            //         } else {
            //             JS.alert(res.msg, 'error', '确定');
            //         }
            //     }, function (error) {
            //         console.error(error);
            //         JS.alert('提交失败', 'error', '确定');
            //     })
            //     return;
            // }
            $scope.params.projectInfo = $scope.program; //立项单据
            $scope.params.projectFileInfo = $scope.projectInfoDataTable; //立项资料
            $scope.params.productSpecsInfo = $scope.productSpecsDataTable; //产品规格
            $scope.params.supplierInvitationInfo = $scope.supplierInvitationDataTable;//邀请供应商列表
            $scope.params.nonPriceFileInfo = $scope.nonPriceFileDataTable;//非价格文件
            $scope.params.nonPriceSelectFileInfo = $scope.nonPriceSelectFileDataTable;//非价格文件(选择文件)
            $scope.params.priceFileInfo = $scope.priceFileDataTable;//价格文件
            $scope.params.scoringTeamInfo = $scope.scoringTeamDataTable;//评分小组
            $scope.params.witnessTeamInfo = $scope.witnessTeamDataTable;//见证小组

            var title = "立项审批流程";
            if($scope.program.projectVersion != '01'){
                title = "立项变更审批流程";
            }

            httpServer.post(URLAPI.saveProjectInfo, {
                'params': JSON.stringify($scope.params)
            }, function (res) {
                if ('S' == res.status) {
                    $scope.program = res.data;
                    $scope.search($scope.program.projectId);

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
                        "menucode": "EQU_LXXGOEM",
                        "opinion": ""
                    };
                    $scope.paramsBpm={
                        "extend":$scope.extend,
                        "variables":$scope.variables,
                        "properties":$scope.properties,
                        "responsibilityId": "990021",
                        "respId": "990021",
                        "processDefinitionKey": "EQU_LXXGOEM.-999", //流程唯一标识，需修改为对应业务表单流程唯一标识
                        "saveonly": false,
                        "businessKey": $scope.program.projectId, //单据ID
                        "title": title, //流程标题
                        "positionId": 0,
                        "orgId": 0,
                        "userType": "30",
                        "billNo": $scope.program.projectNumber
                    }
                    httpServer.post(URLAPI.bpmStart, {
                        'params': JSON.stringify($scope.paramsBpm)
                    }, function (res) {
                        if (res.status == 'S') {
                            $scope.search($scope.program.projectId);
                            $scope.program.projectStatus = '20';
                            $scope.btnSave();
                            JS.alert('提交成功');
                        }
                        else {
                            JS.alert(res.msg, "error", "确定");
                        }
                    }, function (err) {
                        JS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                    });

                } else {
                    JS.alert(res.msg, 'error', '确定');
                }
            }, function (error) {
                console.error(error);
                JS.alert('提交失败', 'error', '确定');
            })
        }
    }]);
})