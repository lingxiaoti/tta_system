/**
 * Created by baijianlong on 2018/10/31.
 */
'use strict';
define(['app','moment','angularFileUpload','../../../ems/service/http','webconfig'], function (app,moment,angularFileUpload,http,webconfig) {
    app.useModule('angularFileUpload');//按需加载模块
    app.controller('smallActFeeBxTooApplyFormCtrl', function ($scope, $location, $rootScope, $state, $stateParams, SIEJS,httpServer,URLAPI,pageResp,limitNumLength,$timeout) {
        $scope.params = {};
        $scope.storeParams = {};
        $scope.customerParams = {};
        $scope.expenseTypeSwitch=true;
        //费用期间参数+费用科目参数
        $scope.newParams = {};

        $scope.urlParams = angular.copy($location.$$search)

        $scope.respId = pageResp.get().responsibilityId;
        $scope.orgId = pageResp.get().orgBean.orgId;
        $scope.channelType = pageResp.get().positionBean.channel;
        $scope.userType = $scope.urlParams.userType;
        $scope.bcUploadUrl = 'uploadFile2JsonObject';
        $scope.bankParams = {orgId: $scope.urlParams.orgId};

        //费用报销行的部门、报销项目默认值
        $scope.rowsDefaultValue = {dept: [],expense: []};

        $scope.fileType = webconfig.fileFormat.emsFileType;


        $scope.respCallback=function(respId,respList,curentResp){
            $scope.expensePeriodLovParams={
                "respId":$scope.ctrlRespId,
                "periodType":'P'
            }
            if (curentResp.proFileBeans.length != 0) {
                for (var i = 0; i < curentResp.proFileBeans.length; i++) {
                    if (curentResp.proFileBeans[i].profileCode == "BRC_OU") {
                        $scope.newParams.orgId = curentResp.proFileBeans[i].profileValue;
                        $scope.customerParams = {orgId:curentResp.proFileBeans[i].profileValue};
                    }
                }
            }
            $scope.departmentParams={
                responsibilityId:$scope.ctrlRespId
            }
            //费用科目
            $scope.getRespParams = {
                orgId:$scope.newParams.orgId,
                sourceType:'OA_MARKET_MANAGE',
                sourceAtttribute1:'60',
                enabledFlag:'Y'
            }
        }
        //切换费用主体，清空费用人员或费用经销商信息
        $scope.yesOrNo = function(){
            if($scope.formParams.costMain == 'Y'){
                $scope.formParams.custAccountId = '';
                $scope.formParams.custAccountName = '';
                $scope.formParams.expenseType = '10';
                $scope.expenseTypeSwitch=true

            }else{
                $scope.formParams.costUserId = '';
                $scope.formParams.costUserName = '';
                $scope.expenseTypeSwitch=false
                $scope.formParams.expenseType = '10';
            }
            $scope.formParams.departmentName = '';
            $scope.formParams.departmentCode = '';
            $scope.formParams.channelCode = '';
            $scope.formParams.channelType = '';
            //$scope.cleanRowData()
        }

        //费用人员 展示弹窗、获取数据
        $scope.getCostUser = function(key,value,item){
            $scope.storeParams = {
                orgId:$scope.formParams.orgId,
                personId:item[0].personId
            }
            console.log('费用人员 展示弹窗、获取数据')
            //$scope.cleanRowData();
        }

        $scope.getExpenseAccount=function(key,value,item){
            $scope.formParams.expenseAccountName=value
            $scope.formParams.expenseAccount=key;
            console.log('getExpenseAccount')
            //$scope.cleanRowData();
        }
        //费用经销商 展示弹窗、获取数据
        $scope.getCustAccount = function(key,value,item){
            $scope.formParams.custAccountId = item[0].customerId;
            $scope.formParams.custAccountName = item[0].customerName;
            $scope.formParams.departmentName = item[0].departmentName;
            $scope.formParams.departmentCode = item[0].departmentId;
            $scope.formParams.channelCode = item[0].channelCode;
            $scope.formParams.channelType = item[0].channelName;
            $scope.storeParams = {
                orgId:$scope.$parent.$parent.orgId,
                custAccountId:item[0].customerId
            }
            //$scope.cleanRowData();
            // console.log($scope.storeParams)
        }

        //人员下属门店
        $scope.getStoreByPerson = function(key,value,item){
            $scope.formParams.itemCompain[0].storeName = item[0].storeName;
            $scope.formParams.itemCompain[0].storeCode = item[0].storeCode;
        }
        //经销商下属门店
        $scope.getStoreByCustAccount = function(key,value,item){
            $scope.formParams.itemCompain[0].storeName = item[0].storeName;
            $scope.formParams.itemCompain[0].storeCode = item[0].storeCode;
        }
        //获取城市
        $scope.citySave1=function(){
            var data = angular.copy($scope.citys)
            var cityStr='cost';
            var str= cityStr+'Address';
            var RegionId= cityStr+'RegionId';
            var country=cityStr+'Country'//国
            var province=cityStr+'Province'//省
            var city=cityStr+'City'//市
            var area=$scope.cityStr+'Area'//区

            $scope.formParams.itemCompain[0][str]=data.cityName;
            $scope.formParams.itemCompain[0][RegionId]=data.id;
            $scope.formParams.itemCompain[0][country]=data.id[0];
            $scope.formParams.itemCompain[0][province]=data.id[1];
            $scope.formParams.itemCompain[0][city]=data.id[2];
            $scope.formParams.itemCompain[0][area]=data.id[3];
            $scope.formParams.itemCompain[0][country + 'Str']=data.name[0];
            $scope.formParams.itemCompain[0][province + 'Str']=data.name[1];
            $scope.formParams.itemCompain[0][city + 'Str']=data.name[2];
            $scope.formParams.itemCompain[0][area + 'Str']=data.name[3];
            console.log($scope.formParams)
            $('#citysModal1').modal('hide');
        }
        // 上账经销商信息
        $scope.getIndex = function(index){
            $scope.rowIndex = index;
            console.log(index)
        }
        $scope.getCustAccount2 = function(key,value,item){
            $scope.formParams.accountLineData[$scope.rowIndex].dealerCode = item[0].customerId;
            $scope.formParams.accountLineData[$scope.rowIndex].dealerName = item[0].customerName;
        }

        var date = new Date();
        var year = date.getFullYear();
        var month = date.getMonth()+1;
        var today = year+"-"+month;
        if($scope.action == 'new'){
            setTimeout(function(){
                $scope.formParams.itemCompain = [];
                $scope.formParams.itemCompain[0] = {};
                $scope.formParams.itemCompain[0].outHire = 'Y';
                $scope.formParams.costMain = 'Y';
                $scope.formParams.expenseType = '10';
                $scope.formParams.orgId = $scope.$parent.$parent.orgId;
                $scope.formParams.expensePeriod = today;
                $scope.storeParams = {
                    orgId:$scope.$parent.$parent.orgId,
                    personId:$scope.formParams.personId
                }
                //新增
                $scope.formParams.orgId = $scope.orgId;
                $scope.formParams.respId = $location.$$search.respId;
                $scope.formParams.reimburseStatus = 'create';
                $scope.formParams.isPostPaperReceipts = 'Y';
                $scope.formParams.transferApSum = 'N';
                $scope.formParams.transferCaSum = 'Y';
                //$scope.formParams.reimburseLineData = [];

                //$scope.findExpensePeriodList();

                $scope.createRow();
            }, 1000);
        }
        $scope.pageView.getBusinessFormCallback = function(res){
            if (res.status === 'S') {
                var data=res.data;
                $scope.positionByPersonParams.orgId = data.orgId;
                $scope.positionByPersonParams.personId = data.personId;
                $scope.positionByPersonParams.deptId = data.deptId;
            }
        };
        //提交前回调
        $scope.submitEvent.cusValidator = function(){
            //提交金额 = 本次付款金额
            //$scope.formParams.encumbranceAmount = Number($scope.formParams.itemPromotionTotal[0].thisAmountTotal);
            console.log($scope.formParams)
            return true
        }
        //保存前回调
        $scope.draftEvent.cusValidator = function(){
            //提交金额 = 本次付款金额
            //$scope.formParams.encumbranceAmount = Number($scope.formParams.itemPromotionTotal[0].thisAmountTotal);
            console.log($scope.formParams)

            return true
        }
        //审批流更新表单更换接口
        //$(function () {
        //    if($scope.action == "approval"){
        //        $("#processForm").attr("data-api-update","updateOaMarketingCostSalesByApproval");
        //    }
        //});

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
        $scope.totalStr= function (attr,kye) {
            var number=0;
            attr.map(function (item,index) {
                number+=number+Number(item[key])
            })
            return number;
        }
        /**
         * 添加报销行
         */
        $scope.createRow = function () {
            var obj = {
                departmentName: '',
                departmentId: '',
                expenseItemName: '',
                expenseItemId: '',
                personName: '',
                personId: '',
                accountName: '',
                custAccountId: '',
                thirdParty: '',
                reimburseAmount: '',
                startDate: '',
                endDate: '',
                bankName: '',
                bankBranchName: '',
                bankAccount: '',
                bankBool: true
            }
            if($scope.rowsDefaultValue.dept.length == 1) {
                obj.departmentName = $scope.rowsDefaultValue.dept[0].departmentName;
                obj.departmentId = $scope.rowsDefaultValue.dept[0].departmentId;
            }
            if($scope.rowsDefaultValue.expense.length == 1) {
                obj.expenseItemDesc = $scope.rowsDefaultValue.expense[0].expenseItemDesc;
                obj.expenseItemId = $scope.rowsDefaultValue.expense[0].expenseItemId;
            }
            $scope.formParams.reimburseLineData.push(obj);
        }

        /**
         * 删除费用报销行
         * @param item
         * @param index
         */
        $scope.deleteRow = function (item, index) {
            SIEJS.confirm('删除报销行', '是否确定删除？', '确定', function () {
                if (item.bcReimburseLId) {
                    httpServer.post(URLAPI.deleteReimburseL, {
                        params: JSON.stringify({bcReimburseLId: item.bcReimburseLId})
                    }, function (res) {
                        if (res.status === 'S') {
                            $scope.formParams.reimburseLineData.splice(index, 1);
                        }
                    }, function (err) {
                        SIEJS.alert('删除失败', 'error', '确定');
                    })
                } else {
                    $scope.formParams.reimburseLineData.splice(index, 1);
                }
            })
        }

        /**
         * change费用报销类型
         */
        $scope.changeExpenseTypeFun = function (key) {
            if (key == '10' && $scope.formParams.encumbranceExpenseType && $scope.formParams.encumbranceExpenseType != '10') {
                $scope.formParams.expenseType = '';
                SIEJS.alert('申请费用类型非现金，报销费用类型不能选择现金', 'warning', '确定');
                return;
            }

            //重置报销行信息的人员、经销商、第三方
            // angular.forEach($scope.formParams.reimburseLineData, function (item, index) {
            //     item.personId = '';
            //     item.personName = '';
            //     item.custAccountId = '';
            //     item.accountName = '';
            //     item.secondaryInventoryName = '';
            //     item.thirdParty = '';
            // })
        }

        //$scope.$watch('formParams.expenseType',function(nval,lval) {
        //    if(nval && lval && nval != lval ) {
        //        $scope.changeExpenseTypeFun(nval);
        //    }
        //})

        /**
         * 确定选中报销行部门
         */
        $scope.selDeptFun = function () {
            var row = $scope.formParams.reimburseLineData[$scope.formParams.selectRowIndex];
            row.departmentId = $scope.deptModel.departmentId;
            row.departmentName = $scope.deptModel.departmentName;
        }

        /**
         * 清空选中报销行部门
         */
        $scope.empDeptFun = function() {
            var row = $scope.formParams.reimburseLineData[$scope.formParams.selectRowIndex];
            row.departmentId = '';
            row.departmentName = '';
        }

        /**
         * 确定选中报销行人员
         */
        $scope.selUserFun = function () {
            var row = $scope.formParams.reimburseLineData[$scope.formParams.selectRowIndex];
            row.personId = $scope.userModel.personId;
            row.personName = $scope.userModel.personName;

            row.custAccountId = '';
            row.accountName = '';
            row.secondaryInventoryName = '';
            row.thirdParty = '';
        }

        /**
         * 确定选中报销科目
         */
        $scope.expenseSelectFun = function () {
            var row = $scope.formParams.reimburseLineData[$scope.formParams.selectRowIndex];
            row.expenseItem = $scope.expenseItem.expenseItem;
            row.expenseItemDesc = $scope.expenseItem.expenseItemDesc;
            row.expenseItemName=$scope.expenseItem.expenseItemDesc;
        }

        /**
         * 清空选中报销科目
         */
        $scope.expenseEmptyFun = function() {
            var row = $scope.formParams.reimburseLineData[$scope.formParams.selectRowIndex];
            row.expenseItem = '';
            row.expenseItemDesc = '';
        }

        /**
         * 确定选中报销行经销商
         */
        $scope.dealerSelectFun = function () {
            var row = $scope.formParams.reimburseLineData[$scope.formParams.selectRowIndex];
            row.custAccountId = $scope.dealerItem.customerId;
            row.accountName = $scope.dealerItem.customerName;
            row.secondaryInventoryName = $scope.dealerItem.subInvName;

            row.personId = '';
            row.personName = '';
            row.thirdParty = '';
        }

        /**
         * 填写第三方事件
         */
        $scope.thirdPartyFun = function () {
            var row = $scope.formParams.reimburseLineData[$scope.formParams.selectRowIndex];
            row.personId = '';
            row.personName = '';

            row.custAccountId = '';
            row.accountName = '';
            row.secondaryInventoryName = '';
        }

        /**
         * 确定选中银行
         */
        $scope.bankSelectFun = function () {
            var row = $scope.formParams.reimburseLineData[$scope.formParams.selectRowIndex];
            row.bankName = $scope.bankItem.bankName;
            row.bankBranchName = $scope.bankItem.bankBranchName;
            row.bankAccount = $scope.bankItem.bankAccount;
        }

        /**
         * 清空选中银行
         */
        $scope.bankEmptyFun = function() {
            var row = $scope.formParams.reimburseLineData[$scope.formParams.selectRowIndex];
            row.bankName = '';
            row.bankBranchName = '';
            row.bankAccount = '';
        }

        /**
         * 导入报销行回调
         * @param res
         */
        $scope.upCallBack = function (res) {
            if (res.status === 'S') {
                angular.forEach(res.data, function (item, index) {
                    $scope.formParams.reimburseLineData = $scope.formParams.reimburseLineData ? $scope.formParams.reimburseLineData : [];
                    $scope.formParams.reimburseLineData.push(item);
                })
            }
        }


        /**
         * 获取报销行的部门的默认值，当只有一个部门的时候即可赋默认部门值
         */
        $scope.getDeptList = function() {
            httpServer.post(URLAPI.findBaseDeptInfoByParent,{
                params: JSON.stringify({
                    respId: $scope.urlParams.respId,
                    orgId: $scope.orgId,
                    departmentId: $scope.formParams.departmentId,
                    sysFlag: $scope.flag
                })
            },function(res) {
                if(res.status === 'S' && res.data.length == 1) {
                    $scope.rowsDefaultValue.dept = res.data;
                }
            },function(err) {

            })
        }
        /**
         * 获取报销行的报销项目的默认值，当只有一个报销项目的时候即可赋默认报销项目值
         */
        $scope.getExpenseList = function() {
            httpServer.post(URLAPI.findExpenseItemPagination,{
                params: JSON.stringify({
                    orgId: $scope.orgId,
                    expenseAccountId: $scope.formParams.expenseAccount
                })
            },function(res) {
                console.log(res,'+++')
                if(res.status === 'S' && res.data.length == 1) {
                    $scope.rowsDefaultValue.expense = res.data;
                }
            },function(err) {

            })
        }
        $scope.addParams = function() {
            var row = $scope.formParams.reimburseLineData[$scope.formParams.selectRowIndex];
            console.log(row)
            $scope.bankParams.personId = row.personId;
            $scope.bankParams.custAccountId = row.custAccountId;
            $scope.bankParams.thirdParty = row.thirdParty;

            $timeout(function() {
                $('#bankInfo').modal('show')
            },300)
            // $scope.bankParams = {orgId:urlParams.orgId}
        }
        /**
         * 判断银行按钮是否可点击
         */
        $scope.$watch('formParams.reimburseLineData',function(nval,lval) {
            if(nval && nval != lval) {
                angular.forEach($scope.formParams.reimburseLineData,function(item,index) {
                    if(item.personName || item.accountName || item.thirdParty) {
                        item.bankBool = false;
                    }else {
                        item.bankBool = true;
                    }
                })
            }
        },true)
        /**
         * 限制金额小数点长度
         * @param item
         * @param key
         * @param len
         */
        $scope.changeRowAmount = function(item,key,len) {
            limitNumLength(item,key,len)
            console.log('changeRowAmount')
            $scope.sumAmount()
        }

        /**
         * 汇总金额
         */
        $scope.sumAmount = function () {
            var sum = 0;
            angular.forEach($scope.formParams.reimburseLineData, function (item, index) {
                sum += item.reimburseAmount;
            })
            if($scope.action!='approval'){
                $scope.formParams.commitAmount = sum;
            }
            $scope.formParams.expenseAmount = sum;
        }
        /**
         * 清空行数据
         */
        $scope.cleanRowData = function () {
            /*var obj = {
                departmentName: '',
                departmentId: '',
                expenseItemName: '',
                expenseItemId: '',
                personName: '',
                personId: '',
                accountName: '',
                custAccountId: '',
                thirdParty: '',
                reimburseAmount: '',
                startDate: '',
                endDate: '',
                bankName: '',
                bankBranchName: '',
                bankAccount: '',
                bankBool: true
            }
            $scope.formParams.reimburseLineData=[obj]*/

        }
        $scope.expenseCallback= function () {
            if($scope.action!='detail'&&$scope.action!='draft'){
                //$scope.cleanRowData()
            }
        }
        /**
         * 下载模版
         */
        $scope.downloadTmp = function (code) {
            var dParams={
                type: code
            }
            httpServer.post(URLAPI.downloadReimburse, {
                params: JSON.stringify(dParams)
            }, function (res) {
                if ( res.status =='S') {
                    var _url=location.protocol+'//'+location.host+res.data;
                    window.open(_url);
                } else {
                    SIEJS.alert(res.msg, 'error', '确定');
                }
            }, function (error) {
                SIEJS.alert('请求失败', 'error', '确定');
            })
        }

        //提交前回调
        $scope.submitEvent.cusValidator = function(){
            angular.forEach($scope.formParams.reimburseLineData,function (value,key) {
                if(!value.personName && !value.accountName && !value.thirdParty){
                    SIEJS.alert('请检查报销行信息，人员、经销商、第三方必填一项', 'error','确定');
                    return false;
                }
            })
            return true;
        }

    });
});
