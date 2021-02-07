/**
 * Created by Ly on 2018/8/25.
 */
'use strict';
define(['app', 'angularFileUpload','webconfig'], function (app,angularFileUpload,webconfig) {
    app.useModule('angularFileUpload');//按需加载模块
    app.controller('feeSubjectFormCtrl', ['$scope', '$location', 'URLAPI', 'httpServer', 'SIE.JS', '$timeout','limitNumLength',
        function ($scope, $location, URLAPI, httpServer, JS, $timeout,limitNumLength) {
            //  附件上传组件 的状态。当前表单为 detail时不可以上传，其它状态可以上传
            $scope.fileViewOnly = $scope.action ==='detail' ? 0:1;
            $scope.urlParams = angular.copy($location.$$search)
            $scope.respId = $scope.urlParams.respId;
            $scope.orgId = $location.$$search.orgId;
            $scope.userType = $location.$$search.userType;
            $scope.channelType = $location.$$search.channelType;
            $scope.action = $location.$$search.action;
            $scope.actType = $location.$$search.actType;
            $scope.formParams.channelType = $scope.channelType;
            $scope.editDisabled = false; //页面内容可编辑状态
            $scope.systemSource = $location.$$search.system;

            $scope.fileType = webconfig.fileFormat.emsFileType;
            //test
            //$scope.formParams.expenseAccountName='（能）租赁费-省区';
            //$scope.formParams.expenseAccount='1081';

            if ($location.$$search.action === 'new') {
                //新增
                $scope.formParams.responsibilityId = $location.$$search.respId;
                $scope.formParams.encumbranceStatus = 'create';
                $scope.formParams.sourceType = 'EMS';
            }

            $scope.closeReason = {
                show: false,
                remark: '',
                submit: function () {
                    // if($scope.closeReason.remark==""){
                    //     JS.alert('请先填写关闭理由！','warning');return;
                    // }
                    if ($scope.closeReason.remark=='') {
                        JS.alert('请填写申请关闭理由!', 'error', '确定');
                        return;
                    }else{
                    JS.confirm('提示：','费用申请关闭后，申请中未报销的金额将不能再进行报销！确定关闭费用申请吗？','确定',function() {


                        var params = {
                            id: $scope.formParams.encumbranceHId,
                            closeReason: $scope.closeReason.remark,
                            userId: $scope.userData.userId
                        };
                        httpServer.post(URLAPI.closeFeeSubject, {
                            params: JSON.stringify(params)
                        }, function (res) {
                            console.log(res)
                            if (res.status === 'S') {
                                $scope.closeReason.show = false;
                                JS.alert(res.msg);
                            } else {
                                JS.alert(res.msg, 'error', '确定');
                            }
                        }, function (err) {
                            JS.alert('请求失败', 'error', '确定');
                        })
                    })
                }
                }
            };

            /**
             * 限制金额小数点长度
             * @param item
             * @param key
             * @param len
             */
            $scope.changeAmount = function(item,key,len) {
                limitNumLength(item,key,len);
            }

            /**
             *生成费用费用期间
             */
            $scope.periodList = [];
            $scope.findExpensePeriodList = function () {
             /*   var year = new Date().getFullYear(),
                    month = new Date().getMonth() + 1;
                for (var i = 0; i < 13; i++) {
                    if (month == 0) {
                        year = year - 1;
                        month = 12;
                    }
                    var s = year + '-' + (month < 10 ? '0' + month : month);
                    $scope.periodList.push(s);
                    --month;
                }*/


              var param1 = '';
                if($scope.action == 'new' || $scope.action == 'draft' || $scope.action == 'refusal' ) {
                    param1 = JSON.stringify({respId: $scope.respId,periodType: 'P',isFilter:'Y'});
                }else{
                    param1 = JSON.stringify({respId: $scope.respId,periodType: 'P'});
                }

                httpServer.post(URLAPI.expensePeriodList, {
                    params: param1,
                    pageIndex: 1,
                    pageRows: 10000
                }, function(res) {
                    if(res.status === 'S') {
                        $scope.periodList = res.data;
                    }else {
                        SIEJS.alert(res.msg, 'error', '确定');
                    }
                }, function(err) {
                    SIEJS.alert('查询渠道数据失败','error', '确定');
                })

            }
            $scope.findExpensePeriodList();

            /**
             * 确定选中人员
             */
            $scope.selectPersonFun = function () {
                console.log($scope.personItem)
                $scope.formParams.applyPersonName = $scope.personItem.personName;
                $scope.formParams.applyPersonId = $scope.personItem.personId;
                $scope.formParams.channelName = $scope.personItem.channelName;
                $scope.formParams.channelType = $scope.personItem.channelId;
                $scope.formParams.departmentName = $scope.personItem.departmentName;
                $scope.formParams.departmentId = $scope.personItem.departmentId;
                $scope.formParams.applyPositionId = $scope.personItem.positionId;
                $scope.formParams.applyPositionName = $scope.personItem.positionName;

                //审批流程区别
                $scope.formParams.roleType = 'person';

                //清空经销商
                $scope.formParams.custAccountName = '';
                $scope.formParams.custAccountId = '';

                //限制费用类型为现金
                $scope.formParams.expenseType = '10';
                $scope.feeTypeDisabled = true;
            }

            /**
             * 确定选中经销商
             */
            $scope.dealerSelectFun = function () {
                console.log($scope.dealerItem)
                $scope.formParams.custAccountName = $scope.dealerItem.customerName;
                $scope.formParams.custAccountId = $scope.dealerItem.customerId;
                $scope.formParams.channelName = $scope.dealerItem.channelName;
                $scope.formParams.channelType = $scope.dealerItem.channelId;
                $scope.formParams.departmentName = $scope.dealerItem.departmentName;
                $scope.formParams.departmentId = $scope.dealerItem.departmentId;

                //审批流程区别
                $scope.formParams.roleType = 'customer';

                //清空人员
                $scope.formParams.applyPersonId = '';
                $scope.formParams.applyPersonName = '';
                $scope.formParams.applyPositionId = '';
                $scope.formParams.applyPositionName = '';
                $scope.feeTypeDisabled = false;
            }

            /**
             * 清空选择的经销商
             */
            $scope.dealerEmptyFun = function () {
                $scope.formParams.dealerName = '';
                $scope.formParams.dealerId = '';
                $scope.formParams.channelName = '';
                $scope.formParams.departmentName = '';
                $scope.formParams.departmentId = '';
                $scope.formParams.custAccountName = '';
                $scope.formParams.custAccountId = '';
            }

            /**
             * 确定选中费用科目
             */
            $scope.expSelectFun = function () {
                console.log($scope.expSubjectItem)
                $scope.formParams.expenseAccountName = $scope.expSubjectItem.expenseAccountName;
                $scope.formParams.expenseAccount = $scope.expSubjectItem.expenseAccountId;
                $scope.formParams.typeCode = $scope.expSubjectItem.typeCode?$scope.expSubjectItem.typeCode:'';
            }

            /**
             * 清空选择的费用科目
             */
            $scope.expEmptyFun = function () {
                $scope.formParams.expenseAccountName = '';
                $scope.formParams.expenseAccount = '';
            }

            $scope.changeExpenseType = function(item) {
                if($scope.formParams.applyPersonName && item!=10) {
                    $scope.formParams.expenseType = '';
                    JS.alert('当费用主体为人员时，申请的费用类型只能是【现金】','error','确定');
                }
            }

            /**
             * 保存草稿
             */
            $scope.draftEvent.cusValidator = function () {

                if (!$scope.formParams.applyPersonId && !$scope.formParams.custAccountId) {
                    JS.alert('人员和经销商必须选一个！', 'error', '确定');
                    return;
                }
                $scope.formParams.responsibilityId = $scope.respId;

                if($scope.action == 'new' || $scope.action == 'draft') {
                    $scope.formParams.encumbranceAmount = $scope.formParams.commitAmount;
                }

                //添加附件对象
                $scope.formParams.files = $scope.fileData;
                $scope.formParams.fileIds = [];
                for (var i in $scope.fileData) {
                    $scope.formParams.fileIds.push($scope.fileData[i].fileId)
                }

                httpServer.post(URLAPI.feeApplicationSave, {
                    params: JSON.stringify($scope.formParams)
                }, function (res) {
                    console.log(res)
                    if (res.status === 'S') {
                        $scope.formParams.encumbranceCode = res.data.id;
                        $scope.formParams.encumbranceHId = res.data.id;
                        $scope.formParams.orgId = res.data.orgId;
                        JS.alert(res.msg);
                    } else {
                        JS.alert(res.msg, 'error', '确定');
                    }
                }, function (err) {
                    JS.alert('保存草稿失败', 'error', '确定');
                })
                return false;
            }

            //提交前处理
            $scope.submitEvent.cusValidator = function () {
                if($scope.action == 'new' || $scope.action == 'draft') {
                    $scope.formParams.encumbranceAmount = $scope.formParams.commitAmount;
                }

                $scope.formParams.buttonStatus = "commit";
                $scope.formParams.responsibilityId = $scope.formParams.respId = $scope.respId;
                if ($scope.formParams.applyPersonId) {
                    $scope.params.applyPersonId = $scope.formParams.applyPersonId;
                    $scope.params.applyPositionId = $scope.formParams.applyPositionId;
                }
                $scope.params.roleType = $scope.formParams.roleType;

                $scope.formParams.orgId = $scope.orgId;
                $scope.formParams.channelType = $scope.formParams.channelType;
                $scope.formParams.commitUserId = $scope.formParams.personId;
                $scope.formParams.commitUserType = $scope.userType;
                $scope.formParams.processCode = $location.$$search.processDefinitionKey;
                $scope.formParams.bussiessType = $scope.formParams.typeCode;

                $scope.formParams.encumbranceHId = $scope.formParams.billNo?$scope.formParams.billNo:$scope.formParams.encumbranceHId;

              /*  $scope.properties.name = 'opinion';
                $scope.properties.type = 'string';
                $scope.properties.value = $scope.formParams.opinion;*/
// debugger;
                $scope.variables.push({name:'typeCode',type:'string'});
                $scope.variables.push({name:'encumbranceAmount',type:'long'});
                $scope.variables.push({name:'applyPersonId',type:'int'});
                $scope.variables.push({name:'custAccountId',type:'int'});
                $scope.variables.push({name:'orgId',type:'string'});
                $scope.variables.push({name:'channelType',type:'string'});
                $scope.variables.push({name:'departmentId',type:'int'});
                $scope.variables.push({name:'commitUserId',type:'int'});
                $scope.variables.push({name:'commitUserType',type:'string'});
                $scope.variables.push({name:'processCode',type:'string'});
                $scope.variables.push({name:'bussiessType',type:'string'});
                $scope.variables.push({name:'expenseType',type:'string'});
                $scope.variables.push({name:'commitAmount',type:'long'});
                $scope.variables.push({name:'opinion',type:'string'});
                $scope.variables.push({name:'expenseAccount',type:'int'});

                //添加附件对象
                $scope.formParams.files = $scope.fileData;
                $scope.formParams.fileIds = [];
                for (var i in $scope.fileData) {
                    $scope.formParams.fileIds.push($scope.fileData[i].fileId)
                }

                return true;
            }

            /**
             * 监听保存操作和提交的返回数据，并赋值给表单，避免表单重复提交
             */
            // $scope.$watch('businessFormParams',function() {
            //     if($scope.businessFormParams && $scope.businessFormParams.data.billNo) {
            //         $scope.formParams.encumbranceHId = $scope.businessFormParams.data.billNo;
            //     }
            // },true)

            //获取查看数据后响应ajax回调
            $scope.$on("businessLoad", function (e, data) {
                if ($scope.formParams.applyPersonId) {
                    $scope.feeTypeDisabled = true;
                }

                angular.forEach($scope.formParams.files, function(item, index) {
                    item.fileName = item.sourceFileName;
                    item.fileSize = item.fileSize + 'MB';
                })
                $scope.fileData = $scope.formParams.files;
            });

            $scope.pageView.form = function () {
                debugger;
                if ("approval" == $scope.action || ('commit' == $scope.formParams.encumbranceStatus && 'edit' == $scope.actType)) {
                    //setTimeout(function () {
                    if (!$scope.formParams.applyPersonId) {
                        $("#expenseType").attr('disabled', false);
                    }
                    $('#encumbranceAmount').removeAttr('readonly');
                    $("#feeopinion").attr('disabled', false);
                    // }, 200)
                }
                ;
                if ('pass' == $scope.formParams.encumbranceStatus) {
                    $scope.closeReason.show = true;
                    // setTimeout(function () {
                    $("#closeReason").find('input,textarea,button').attr('disabled', false);
                    // }, 300)
                }

                if($scope.action != 'detail' && $scope.action != 'version') {
                    $timeout(function() {
                        $('#expenseType').removeAttr('disabled');
                    })
                }
                if($scope.action != 'detail' && $scope.action != 'version'){
                    $timeout(function() {
                        $('#encumbranceAmount').removeAttr('readonly');
                    })
                }
            }

            //费用申请-审批验证及保存金额与类型
            $scope.passEvent.beforFunction = function (status) {
              //  if ($scope.passEventActtion) return true;

                if (!$scope.formParams.encumbranceAmount) {
                    JS.alert("申请金额不能为空！", 'error', '确定');
                    return;
                } else if ($scope.formParams.encumbranceAmount > $scope.formParams.commitAmount) {
                    JS.alert("申请金额不能大于提交金额！", 'error', '确定');
                    return;
                }
                if (!$scope.formParams.expenseType) {
                    JS.alert("请选择费用类型！", 'error', '确定');
                    return;
                }
                var apParams = {
                    encumbranceHId: $scope.formParams.encumbranceHId,
                    expenseType: $scope.formParams.expenseType,
                    commitAmount: $scope.formParams.commitAmount,
                    encumbranceAmount: $scope.formParams.encumbranceAmount
                }
                httpServer.post(URLAPI.findApplicationHApprove, {
                    params: JSON.stringify(apParams)
                }, function (res) {
                    console.log(res)
                    if (res.status === 'S') {
                        // $scope.approvalEvent('Y', false, '审批');
                        $scope.showBtnApproval('Y', false, '审批');
                        $scope.passEventActtion = true;
                        //$("#approvalFormModal").modal('show');
                    } else {
                        JS.alert(res.msg, 'error', '确定');
                        $scope.passEventActtion= false;
                    }
                }, function (err) {
                    JS.alert('请求失败', 'error', '确定');
                });

            }

            $scope.selectHeaderFun = function(row,index) {
                $scope.rowLists = row.reimburseLineData;
                angular.forEach($scope.formParams.reimburseData,function(item) {
                    item.radioValue = false;
                })
                row.radioValue = true;
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




            /**
             * create hmx 2018年9月19日
             * @type {Array}
             */
            $scope.fileData = []; // 上传文件
            $scope.fileFilter = {
                code: 'UPLOAD_FILE_FILTER',
                key: 'imageType'
            }


        }])
})
