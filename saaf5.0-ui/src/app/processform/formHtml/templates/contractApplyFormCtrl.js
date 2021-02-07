/**
 * Created by Acer on 2018/8/18.
 */
'use strict';
define(['app','angularFileUpload','webconfig'], function (app,angularFileUpload,webconfig) {
    app.useModule('angularFileUpload');//按需加载弹出层模块

    app.controller('contractApplyFormCtrl', function ($scope, $location, $rootScope, $state, $stateParams, SIEJS, $compile, httpServer, URLAPI, $filter, $timeout, numberToZh, pageResp, Cookies) {
        $scope.params = {};
        $scope.isEdit=false;
        $scope.debitCusBankPaginatioLov={};
        $scope.alteration = $location.$$search.alteration?true:false;
        $scope.importSwitch = $location.$$search.importSwitch?true:false;//是否需要导入合同付款
        $scope.currentParams={};//导入参数
        $scope.currentTableParams={}//表格参数
        $scope.importTataTablet={};
        $scope.fileType = webconfig.fileFormat.testType;
        // 上传文件ID
        $scope.getFileIds = function(){
            $scope.formParams.fileIds = [];
            for(var i = 0; i<$scope.fileData.file.length;i++){
                $scope.formParams.fileIds.push($scope.fileData.file[i].fileId);
            }
        }

        //  附件上传组件 的状态。当前表单为 detail时不可以上传，其它状态可以上传
        $scope.fileViewOnly = $scope.action ==='detail'||$scope.action ==='version' ? 0:1;
        $scope.newParams = {};
        $scope.respCallback=function(respId, respList ,curentResp){
            var resp=pageResp.get();
            $scope.primaryPosition=curentResp.primaryPosition
            $scope.expensePeriodLovParams={
                "respId":$scope.ctrlRespId,
                "periodType":'P'
            }
            if (curentResp.proFileBeans.length != 0) {
                for (var i = 0; i < curentResp.proFileBeans.length; i++) {
                    if (curentResp.proFileBeans[i].profileCode == "BRC_OU") {
                        $scope.newParams.orgId = curentResp.proFileBeans[i].profileValue;
                    }
                }
            }
            $scope.ljyParams = {
                orgId:$scope.newParams.orgId,
                sourceType:'OA_ADM_MANAGE',
                sourceAtttribbute1:'10',
                enabledFlag:'Y'
            }
        }

        $('#clearance>label').css('padding','0 20px');
        $('#clearance').css('padding','0 20px');

        $scope.numberDx= function (n) {
            return numberToZh(n)
        }
        

        $scope.borrowMoneyStr=function(){
            var reg=/^[+]{0,1}(\d+)$|^[+]{0,1}(\d+\.\d+)$/;
            if(!reg.test(Number($scope.formParams.agmtSum))) return;
            $scope.formParams.borrowMoneyZh= $scope.numberDx(Number($scope.formParams.agmtSum))
            // 当框架合同金额不为零时，子合同金额小于框架合同剩余金额校验
            if($scope.formParams.agmtSumAc) {
                if($scope.formParams.agmtSumAmountAc < $scope.formParams.agmtSum) {
                    SIEJS.alert('合同金额应不大于可用金额', 'error', "确定");
                    $scope.formParams.agmtSum = ''
                    return
                }
            }
        }

        $scope.agmtSumCallback = function(){
            for(var i = 0;i<$scope.formParams.items.length;i++){
                if($scope.formParams.items[i].mSum){
                    $scope.mSumCallback(i);
                }
            }
        }
        // 付款金额回调
        $scope.mSumCallback=function(index,tips){
            if(!$scope.formParams.agmtSum) {
                SIEJS.alert('请输入合同金额','error');
                $scope.formParams.items[index].mSum = 0;
                $scope.formParams.items[index].standardMoney = '';
                return;
            }
            if(Number($scope.formParams.items[index].mSum)>Number($scope.formParams.agmtSum)){
                SIEJS.alert('付款金额不能大于总金额','error');
                $scope.formParams.items[index].mSum = 0;
                $scope.formParams.items[index].standardMoney = '';
                return;
            }
            $scope.formParams.items[index]['mPerStr'] = Math.floor(($scope.formParams.items[index].mSum/$scope.formParams.agmtSum)*10000)/100+'%';
            $scope.formParams.items[index]['mPer'] = Math.floor(($scope.formParams.items[index].mSum/$scope.formParams.agmtSum)*10000)/100;
            $scope.calculateStandardMoney(index)
        }
        // 计算本位币
        $scope.calculateStandardMoney = function(index) {
            if($scope.formParams.moneyType) {
                $scope.formParams.items[index]['standardMoney'] = (Number($scope.formParams.items[index].mSum)*1000)*(Number($scope.formParams.items[index].exchangeRate)*1000)/1000000;
            }
        }
        // 付款明细 删除按钮
        $scope.relief = function(index){
            SIEJS.confirm('您确认要删除吗？', '', '确定', function () {
                $scope.formParams.items.splice(index, 1);
            })
        }
        // 新增时付款明细，增加按钮
        $scope.addRows = function(){
            $scope.formParams.items.push({})
            $scope.changeCurrencyType()
        }
        

        // if($scope.action != 'version' ){
        //     $scope.pageView.getBusinessFormCallback=function(res){
        //         if (res.status === 'S') {
        //             var data=res.data;
        //             $scope.positionByPersonParams.orgId=data.orgId;
        //             $scope.positionByPersonParams.personId= data.personId;
        //             $scope.positionByPersonParams.deptId=data.deptId;
        //             $scope.borrowMoneyStr();
        //             $scope.fileData.file = data.fileList;//上传组件数据
        //             // $scope.getFileIds();
        //             angular.forEach($scope.formParams.items,function(data,index){
        //                 $scope.formParams.items[index]['mPerStr']=Math.floor(($scope.formParams.items[index].mSum/$scope.formParams.agmtSum)*10000)/100+'%';
        //                 $scope.formParams.items[index]['mPer']=Math.floor(($scope.formParams.items[index].mSum/$scope.formParams.agmtSum)*10000)/100;
        //                 if(data.costReqId){
        //                     $scope.formParams.items[index]['deleteShow']=true;
        //                 }
        //             })
        //             // var finAgmtVersion=Number($scope.formParams.secItems[$scope.formParams.secItems.length-1].finAgmtVersion)+1+'.0'
        //             // $scope.formParams.secItems.push({'finAgmtVersion':finAgmtVersion});
        //             // $scope.formParams.secItems[$scope.formParams.secItems.length-1].changeDate = $filter('date')(new Date(), 'yyyy-MM-dd HH:mm:ss');
        //         }
        //     };
        // }
        // 处理
        // $scope.$on('businessLoad', function (event, msg) {
        //     if(msg){
        //         $scope.borrowMoneyStr();
        //         angular.forEach($scope.formParams.items,function(data,index){
        //             $scope.formParams.items[index]['mPerStr']=Math.floor(($scope.formParams.items[index].mSum/$scope.formParams.agmtSum)*10000)/100+'%';
        //             $scope.formParams.items[index]['mPer']=Math.floor(($scope.formParams.items[index].mSum/$scope.formParams.agmtSum)*10000)/100;
        //             if(data.costReqId){
        //                 $scope.formParams.items[index]['deleteShow']=true;
        //             }
        //         })
        //         var finAgmtVersion=Number($scope.formParams.secItems[$scope.formParams.secItems.length-1].finAgmtVersion)+1+'.0'
        //         $scope.formParams.secItems.push({'finAgmtVersion':finAgmtVersion})
        //     }
        // });
        // 新增
        //费用期间 获取当前年月
        var date = new Date();
        var year = date.getFullYear();
        var month = date.getMonth()+1;
        var today = year+"-"+month;

        //获取费用期间
        setTimeout(function () {
            httpServer.post(URLAPI.ems_findExpensePeriodByOrgList, {
                params: JSON.stringify({orgId: $scope.newParams.orgId, periodType: 'P'}),
                pageIndex: 1,
                pageRows: 10000
            }, function (res) {
                if (res.status === 'S') {
                    $scope.expensePeriodList = res.data;
                } else {
                    SIEJS.alert(res.msg, 'error', '确定');
                }
            }, function (err) {
                SIEJS.alert('查询渠道数据失败', 'error', '确定');
            })
        },500);
        // 付款明细-增加按钮是否隐藏
        $scope.showOrNot = function (index, key) {
            if ($scope.formParams.framework == 'Y' && $scope.formParams.finAgmtType == 'MAIN') {
                $scope.isEdit = true;
            }else {
                $scope.isEdit = false;
            }
            // $scope.yesOrNo()
        }

        //切换是否框架合同选项，限制合同类型
        $scope.yesOrNo = function(){
            if($scope.formParams.framework == 'Y'){
                $scope.formParams.finAgmtType = '';
                $("select[id$='finAgmtType']").prop({
                    disabled:false
                })
                $scope.showOrNot()
            }else{
                $scope.formParams.finAgmtType = 'MAIN';
                $("select[id$='finAgmtType']").prop({
                    disabled:true
                })
                $scope.showOrNot()
            }
        }
        // 框架合同金额 agmtSum 
        $scope.checkAgmtSum = function() {
            if($scope.formParams.agmtSumAc) {
                $scope.formParams.tipsAc = '不限制'
            }else {
                $scope.formParams.tipsAc = '主合同未完成金额 ' + $scope.formParams.agmtSumAmountAc + ' 元'
            }
        }
        // 框架合同金额减去已有子合同金额合计 agmtSumAmount
        $scope.setRowInput = function(key,value,item) {
            $scope.formParams.mainAgmtNum = item[0].mainAgmtNum;
            $scope.formParams.mainAgmtVersion = item[0].mainAgmtVersion;
            $scope.formParams.agmtSumAc = item[0].agmtSum;
            $scope.formParams.agmtSumAmountAc = item[0].agmtSumAmount;
            $scope.checkAgmtSum()
        }
        // 改变发票类型，清空税率数据
        $scope.clearTaxRate = function() {
            $scope.formParams.taxRate = ''
        }
        // 改变币种，对子表币种进行赋值，控制子表汇率
        $scope.changeCurrencyType = function() {
            // 子表币种、汇率赋值
            if($scope.formParams.moneyType) {
                for(var i = 0;i < $scope.formParams.items.length;i++){
                    if($scope.formParams.moneyType == 'CNY') {
                        $scope.formParams.items[i].currencyType = '人民币';
                        $scope.formParams.items[i].exchangeRate = '1';
                    }else if($scope.formParams.moneyType == 'USD') {
                        $scope.formParams.items[i].currencyType = '美元';
                        $scope.formParams.items[i].exchangeRate = '6';
                    }
                }
            }
            // 汇率是否可编辑
            setTimeout(function() {
                if($scope.formParams.moneyType == 'CNY') {
                    $("input[id*='exchangeRate']").prop({disabled:true,'ng-readOnly':true,'ng-readonly':true})
                }else if($scope.formParams.moneyType == 'USD') {
                    $("input[id*='exchangeRate']").prop({disabled:false,'ng-readOnly':false,'ng-readonly':false})
                }
            }, 0);
            for(var i = 0;i < $scope.formParams.items.length;i++){
                if($scope.formParams.items[i].mSum) {
                    $scope.calculateStandardMoney(i);
                }
            }
        }
        if($scope.action == 'new') {
            $scope.formParams.items = [];
            $scope.fileData = []; // 上传文件
            $timeout(function(){
                $scope.personCusBankPaginatioParams = {
                    personId: $scope.formParams.personId
                }
                $scope.formParams.framework = 'N';// 新增时默认为非框架合同
                $scope.yesOrNo();
            },500)
        }
        // 查看历史版本
        // if($scope.action == 'version') {
        //     $scope.pageView.getBusinessFormCallback = function (res) {
        //         var data = res.data;
        //         $scope.fileData = []; // 上传文件
        //         $scope.fileData.file = data.fileList;//上传组件数据
        //         $scope.getFileIds();
        //         $scope.borrowMoneyStr();
        //         $scope.formDisabled();
        //     }
        // }
        // 合同变更
        // $scope.newIndex = '';
        // $scope.newFinAgmtVersion = '';
        // $scope.time = '';
        if($scope.action == 'outer'){

            $scope.$watch('formParams.secItems',function(nval,lval) {
                if(nval && nval != lval) {
                    var o = {
                        finAgmtVersion: $scope.formParams.secItems.length + 1 + ".0",
                        changeDate: $filter('date')(new Date(), 'yyyy-MM-dd HH:mm:ss'),
                    }
                    $scope.formParams.secItems.push(o);
                    // $scope.newIndex = $scope.formParams.secItems.length + 1;
                    // $scope.newFinAgmtVersion = $scope.newIndex + ".0";
                    // $scope.time = $filter('date')(new Date(), 'yyyy-MM-dd HH:mm:ss');
                }
            })

            var  str = '<div class="btn-group input-group-xs ">' +
                '<button class="btn btn-default btn-xs" type="button" ng-click="contractSave()" validate-form-btn="formArea">'+
                '<i class="fa fa-save"></i>保存'+
                '</button>'+
                '<button class="btn btn-default btn-xs" type="button" ng-click="contractSubmit()" validate-form-btn="formArea">'+
                '<i class="fa fa-undo"></i>提交审批'+
                '</button>'+
                '</div>';
            $("#showProcessButton").append(str);
            $compile($("#showProcessButton"))($scope);
            //$('#mainDiv').attr('date-api-update','updateChangeFinAgreement');
            $('#formParams_agmtName').prop({
                disabled:true
            });
            $('#formParams_agmtObjSecond').prop({
                disabled:true
            });
            $('#formParams_finAgmtType').prop({
                disabled:true
            });
            $scope.contractSave= function () {
                $scope.pageView.action = 'new';
                $scope.getFileIds();
                $scope.saveAndDraft(true,'保存','updateChangeFinAgreement');
                setTimeout(function () {
                    $scope.formParams.finAgmtInfoId = $scope.businessFormParams.finAgmtInfoId
                },2000)
            }
            $scope.contractSubmit = function () {
                $scope.pageView.action = 'new';
                $scope.getFileIds();
                $scope.saveAndDraft(false,'提交','updateChangeFinAgreement');
            }
        }
        $scope.showImportModel = function(){
            $scope.currentTableParams.id=$scope.formParams.finAgmtInfoId;
            $scope.importTataTablet.search();
            $scope.currentParams={};
            $scope.currentParams.expensePeriod = today;
            $('#meetingManagement').modal('show')
        };

        $scope.importSave = function(){
           var attr=JSON.parse(localStorage[window.appName+'_currentResp'])
            var loginInfo=''
            for(var i=0;i<attr.length;i++) {
                if(attr[i].url=='/oa/showProcess/contractApplyForm'){
                    loginInfo= attr[i].resp;
                }
            }
            if(loginInfo=='') {
                console.error('没有对应的resp')
                return;
            }

            var nMemo = "";
            if($scope.importTataTablet.selectRow.memo){
                nMemo = $scope.importTataTablet.selectRow.memo;
            }else {
                nMemo = "";
            }

            var p= {
                "applyPositionId":loginInfo.primaryPosition.positionId,
                "respId": $scope.formParams.respId,
                "departmentId":$scope.formParams.deptId,
                "commitAmount":$scope.importTataTablet.selectRow.mSum,
                // "departmentName":$scope.formParams.deptName,
                // "applyPersonName":$scope.formParams.personName,
                "expensePeriod":$scope.currentParams.expensePeriod,
                "expenseAccount":$scope.currentParams.expenseAccount,
                "expenseType":"10",
                "startDate":$scope.formParams.agdateStart,
                "applyPersonId":$scope.formParams.personId,
                "endDate":$scope.formParams.agdateEnd,
                "eventDesc":nMemo,
                "channelType":$scope.formParams.channelType,
                "personId":loginInfo.primaryPosition.personId,
                "sourceType":"OA_ADM_MANAGE",
                "sourceAtttribbute1":10,
                "sourceCode":$scope.formParams.finAgmtInfoId,
                "sourceId":$scope.formParams.finAgmtInfoId,
                // "buttonStatus":"commit",
                "thirdParty": "",
                "substitutePersonId": $scope.formParams.personId,
                "custAccountId": "",
                "varUserId": JSON.parse(localStorage.getItem(window.appName + '_successLoginInfo')).userId
            };

            /*angular.forEach($scope.importTataTablet.list,function(data,index){
                var p= {
                    "applyPositionId":loginInfo.primaryPosition.positionId,
                    "responsibilityId": $scope.formParams.respId,
                    "departmentId":$scope.formParams.deptId,
                    "commitAmount":data.mSum,
                    // "departmentName":$scope.formParams.deptName,
                    // "applyPersonName":$scope.formParams.personName,
                    "expensePeriod":$scope.currentParams.expensePeriod,
                    "expenseAccount":$scope.currentParams.expenseAccount,
                    "expenseType":"10",
                    "startDate":$scope.formParams.agdateStart,
                    "applyPersonId":$scope.formParams.personId,
                    "endDate":$scope.formParams.agdateEnd,
                    "eventDesc":data.memo,
                    "channelType":$scope.formParams.channelType,
                    "personId":loginInfo.primaryPosition.personId,
                    "sourceType":"CONTRACT",
                    "sourceCode":$scope.formParams.finAgmtInfoId,
                    "sourceId":$scope.formParams.finAgmtInfoId,
                    // "buttonStatus":"commit",
                    "thirdParty": "",
                    "substitutePersonId": $scope.formParams.personId,
                    "custAccountId": "",
                    "varUserId": JSON.parse(localStorage.getItem(window.appName + '_successLoginInfo')).userId
                };
                paramsAttrs.push(p)

            })*/
            //return;

            if(!$scope.importTataTablet.selectRow){
                SIEJS.alert('请选择条款明细', 'error', "确定");
                return;
            };

            httpServer.post(URLAPI.saveCommitAndPassCuxBcEncumbrance,{params: JSON.stringify(p)},function (res) {
                 if (res.status == "S") {
                     SIEJS.alert('保存成功', 'success', "确定");
                     var ps=$scope.importTataTablet.selectRow;
                     ps.costReqId = res.data.id;
                     var updataParams=[];
                     updataParams.push(ps);
                     httpServer.post(URLAPI.updateFinAgreementLine,{params: JSON.stringify({data:updataParams})},function (res) {
                         if (res.status == "S") {
                             SIEJS.alert('保存成功', 'success', "确定");
                             $scope.loadView();
                             $('#meetingManagement').modal('hide')
                         } else {
                             SIEJS.alert(res.msg, 'error', "确定");
                         }
                     })
                 } else {
                     SIEJS.alert(res.msg, 'error', "确定");
                 }
             })
            //feeApplicationForm
        }
        if($scope.importSwitch){
            var  str = '<div class="btn-group input-group-xs ">' +
                '<button class="btn btn-default btn-xs" type="button" ng-click="showImportModel()">'+
                '<i class="fa fa-save"></i>合同付款申请'+
                '</button>';
            $("#showProcessButton").append(str);
            $compile($("#showProcessButton"))($scope);
        }

        $scope.pageView.getBusinessFormCallback = function (res) {
            if (res.status === 'S') {
                $scope.borrowMoneyStr();
                var data = res.data;
                $scope.fileData.file = data.fileList?data.fileList:[];//上传组件数据
                $scope.getFileIds();
                $scope.showOrNot();
                if($scope.action == 'version') {
                    $timeout(function () {
                        $scope.formDisabled();
                    }, 500)
                }
                $scope.formParams.finAgmtInfoId ? $scope.formParams.finAgmtInfoId : "";
                // 非框架合同，禁用合同类型选择
                if($scope.formParams.framework == 'Y'){
                    $("select[id$='finAgmtType']").prop({
                        disabled:false
                    })
                }else{
                    $("select[id$='finAgmtType']").prop({
                        disabled:true
                    })
                }
                // 数据返回后，通过主合同编号 mainAgmtNum 查询 主合同金额 和 剩余可用金额
                if($scope.formParams.finAgmtType == 'SEED') {
                    httpServer.post(URLAPI.findMainFinAgreement, {
                        params: JSON.stringify({agmtNumLink:$scope.formParams.mainAgmtNum}),
                    }, function (res) {
                        if (res.status === 'S') {
                            $scope.formParams.agmtSumAc = res.data[0].agmtSum;
                            $scope.formParams.agmtSumAmountAc = res.data[0].agmtSumAmount;
                            $scope.checkAgmtSum()
                        } else {
                            SIEJS.alert(res.msg, 'error', '确定');
                        }
                    }, function (err) {
                        SIEJS.alert('查询主合同数据失败', 'error', '确定');
                    })
                }
            }
        }

        $scope.personCusBankCallback = function () {
            $scope.formParams.acctId = $scope.debitCusBankPaginatioLov.selectRow.primaryAcctOwnerPartyId;
            $scope.formParams.acctBankDeposit = $scope.debitCusBankPaginatioLov.selectRow.bankName;
            $scope.formParams.acctBankDepositId = $scope.debitCusBankPaginatioLov.selectRow.bankPartyId;

        }

        //审批流更新表单换接口
        $(function () {
            if($scope.action == "approval"){
                $("#processForm").attr("data-api-update","updateFinAgreementByApproval");
                $("#processForm").attr("data-api-get","findFinAgreementInfoByIdApproval");
            }
        });

        //流程审批，附件上传接口
        $scope.saveFile = function () {
            $scope.getFileIds();
            var p = {
                oaBusinessId :$scope.formParams.requestNum,
                oaFunctionId :$scope.formParams.oaFunctionId,
                fileIds:$scope.formParams.fileIds
            }
            httpServer.post(URLAPI.uploadFile, {
                params: JSON.stringify(p)
            },function(res) {
                if(res.status === 'S') {

                }else {
                    SIEJS.alert(res.msg,'error','确定');
                }
            },function(err) {
                SIEJS.alert('获取数据失败','error','确定');
            })
        }
        // 请求成功回调
        $scope.sucCallback = function(data) {
            var mark = true;
            var res = JSON.parse(data)
            if(res.status == 'S') {
                if($scope.formParams.finAgmtType == 'SEED' && $scope.formParams.agmtSumAc){
                    // 当前为子合同，合同金额应不大于可用金额
                    if($scope.formParams.agmtSum > res.data[0].agmtSumAmount) {
                        SIEJS.alert('合同金额应不大于可用金额', 'error', "确定");
                        $scope.formParams.agmtSum = '';
                        mark = false;
                    }else {
                        mark = true;
                    }
                }else if($scope.formParams.finAgmtType == 'MAIN') {
                    // 当前为主合同，合同金额应大于已有子合同金额
                    if($scope.formParams.agmtSum < res.data[0].soAagmtSum) {
                        SIEJS.alert('合同金额应大于已有子合同金额', 'error', "确定");
                        $scope.formParams.agmtSum = '';
                        mark = false;
                    }else {
                        mark = true;
                    }
                }
            }else if(res.status == 'E') {
                var msg = res.msg;
                SIEJS.alert(msg, 'error', "确定");
                mark = false;
            }
            return mark
        }
        // 请求失败回调
        $scope.errCallback = function(error){
            SIEJS.alert(error, 'error', "确定");
            return false;
        }
        // 同步请求方法
        $scope.syncRequest = function(url, params, successCallback, errorCallback) {
            var arr = {};
            //增加tokenCode 参数到Header
            var successLoginInfo = eval('(' + sessionStorage[appName + '_successLoginInfo'] + ')');
            var tokenCode = null;
            if ((successLoginInfo != null && typeof(successLoginInfo) != "undefined") && typeof(successLoginInfo.TokenCode) != "undefined") {
                tokenCode = successLoginInfo.TokenCode;
            } else {
                tokenCode = "INDEX_TOKEN_CODE";
            }
            //  请求参数
            var configObj = {
                headers: {
                    "Content-Type": "application/x-www-form-urlencoded;charset=UTF-8;",
                    "Certificate": Cookies.getCookie(appName + '_certificate') || sessionStorage[appName + '_certificate'] || localStorage[appName + '_certificate'] || 'nothing',
                    "TokenCode": tokenCode
                }, // 请求头
                method: 'post', // 数据的提交方式：get和post
                url: url, // 数据的提交路径
                async: false,  // 是否支持异步刷新，默认是true
                data: {
                    params:JSON.stringify(params)
                }, // 需要提交的数据
                success: function(result,status,xhr) {
                    arr.suc = successCallback(result)
                }, // 请求成功后的回调函数
                error: function(xhr,status,error) {
                    arr.err = errorCallback(error)
                }// 请求失败后的回调函数         
             }
            $.ajax(configObj)
            return arr
        }
        // 校验合同与原有主合同或子合同金额大小关系
        $scope.checkRelationship = function() {
            if($scope.action != 'outer') {
                if($scope.formParams.finAgmtType == 'SEED' && $scope.formParams.agmtSumAc) {
                    var markStr = $scope.syncRequest(URLAPI.findMainFinAgreement, {agmtNumLink:$scope.formParams.mainAgmtNum}, $scope.sucCallback, $scope.errCallback)
                }
            }else {
                if($scope.formParams.finAgmtType == 'SEED' && $scope.formParams.agmtSumAc) {
                    var markStr = $scope.syncRequest(URLAPI.findMainFinAgreement, {agmtNumLink:$scope.formParams.mainAgmtNum}, $scope.sucCallback, $scope.errCallback)
                }else if($scope.formParams.finAgmtType == 'MAIN') {
                    var markStr = $scope.syncRequest(URLAPI.findMainFinAgreement, {agmtNumLink:$scope.formParams.agmtNum}, $scope.sucCallback, $scope.errCallback)
                }
            }
            if(markStr.suc == true) {
                return true
            }else if(markStr.suc == false) {
                return false
            }else {
                return false
            }
        }
        // 校验合同金额与付款明细总和关系
        $scope.checkRelationshipSelf = function() {
            var number = 0;
            angular.forEach($scope.formParams.items, function(data,index) {
                number  = number + Number(data.mSum);
            })
            if(number > Number($scope.formParams.agmtSum)) {
                SIEJS.alert('付款金额总和须小于或等于合同金额','error')
                return false;
            }
            return true
        }
        // 保存前回调
        $scope.draftEvent.cusValidator = function(){
            var markStr = $scope.checkRelationshipSelf()
            if(!markStr) {
                return markStr
            }
            $scope.getFileIds()
            markStr = $scope.checkRelationship()
            return markStr
        }
        //提交前回调
        $scope.submitEvent.cusValidator=function(){
            var markStr = $scope.checkRelationshipSelf()
            if(!markStr) {
                return markStr
            }
            $scope.getFileIds()
            markStr = $scope.checkRelationship()
            return markStr
        }
        //通过前回调
        $scope.passEvent.cusValidator = function(){
            $scope.saveFile();
            cosole.__lookupGetter__()
            return true;
        }
        //驳回前回调
        $scope.refusalEvent.cusValidator = function(){
            $scope.saveFile();
            return true;
        }
        //驳回重审前回调
        $scope.refusaApprovallEvent.cusValidator = function(){
            $scope.saveFile();
            return true;
        }

        /*  $scope.draftEvent.cusValidator = function(){
                 console.log($scope.formParams)
                 return false
          }*/
        // 查看时 合同付款申请？？？
        // if($scope.action == 'detail'){
        //     var  str = '<div class="btn-group input-group-xs ">' +
        //         '<button class="btn btn-default btn-xs" type="button" ng-click="payApply()" validate-form-btn="formArea">'+
        //         '<i class="fa fa-save"></i>合同付款申请'+
        //         '</button>'+
        //         '</div>';
        //     $("#showProcessButton").append(str);
        //     $compile($("#showProcessButton"))($scope);
        //     $('#mainDiv').attr('date-api-update','updateChangeFinAgreement');
        //     $scope.payApply= function () {
        //         // $scope.action = 'new';
        //         //$scope.saveAndDraft(true,'保存成功','updateChangeFinAgreement');

        //     }
        // }
    });
});
