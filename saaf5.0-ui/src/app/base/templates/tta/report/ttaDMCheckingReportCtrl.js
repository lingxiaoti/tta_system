'use strict';
define(['app', 'pinyin', 'ztree','XLSX','fixedReport'], function (app, pinyin, ztree,XLSX, fixedReport) {
    app.controller('ttaDMCheckingReportCtrl', function ($scope, $filter, $location, $rootScope, $state, $stateParams, SIEJS, httpServer,validateForm, URLAPI, iframeTabAction, setNewRow,$http,$timeout,lookupCode,saafLoading,tableXlsExport) {
        $scope.buttonFlag = true ;
        $scope.userType = JSON.parse(sessionStorage[appName + '_successLoginInfo']).userType;
        $scope.isDisabled = $scope.userType != '45';
        $scope.cWTable = {};
        $scope.batchId = $stateParams.batchId;
        $scope.params = {"batchCode":$scope.batchId} ;
        $scope.splitDataTable = [] ;
        $scope.currentList2 = [] ;
        $scope.firstText = "请选择";
        $scope.fileDataParams = {};
        $scope.ttaOsdfileDataTable = {};
        $scope.btnFreeze = function (){
            var fixedTable1 = fixReportTable(document.getElementById('fixedId'),5);
        };
        $scope.AddData = {};

        $scope.currentPurcahseReply = {};
        $scope.currentExemptionReasonList = [];
        $scope.currentExemptionReasonList2 = [];

        //采购批量回复
        $scope.CGPLHF = function () {
            $('#nppPurchaseReplyModal').modal('toggle');
        };
        
        /**
         * 选择On top/TTA
         * @param index
         * @param currentValue
         * @param lookUpData
         */
        $scope.collectFun = function (index,currentValue,lookUpData){
            var reg = /^\d+$/;
            var row = $scope.cWTable.data[index] ;
            if('OnTop' == currentValue && reg.test(row.noUnconfirmAmount)){
                row.unconfirmAmount = row.noUnconfirmAmount;
            }else if('TTA' == currentValue && reg.test(row.noUnconfirmAmount)){
                row.unconfirmAmount = (row.noUnconfirmAmount * (100+ row.additionRate *100)/100).toFixed(0);
            }
        };

        $scope.fileInfoFunc = function (row) {
            $scope.fileDataParams.functionId = 'tta_dm_checking_report';
            $scope.fileDataParams.businessId = row.dmId;
            $scope.ttaOsdfileDataTable.search(1);
            $('#excelUp2').modal('toggle');
        };

        $scope.callback = function (row, index, flag) {
            if ('upload' == flag) {
                if (row.processStatus !='DS01' && $scope.isDisabled) {
                    SIEJS.alert('非制单中不能进行此操作', 'error', '确定');
                    return;
                }
                var obj = document.getElementById('cWReportUploadFile');
                obj.outerHTML = obj.outerHTML;
                $('#addFileUpload').modal('toggle');
                $scope.cWUploadRow = row;
            }
        };


        //清除值
        $scope.paramsCancel = function (value) {
            $scope.params.groupCode = '';
            $scope.params.groupDesc = '';
        };

        //勾选部门
        $scope.getDeptCode = function (e) {
            $('#deptFeeALov').modal('toggle')
        };

        $scope.selectDeptFeeAReturn = function (key, value, currentList) {
            if (currentList && currentList.length > 0) {
                $scope.params.groupCode = '';
                $scope.params.groupDesc = '';
                for (var idx = 0; idx < currentList.length; idx ++) {
                    $scope.params.groupCode += currentList[idx].departmentCode;
                    $scope.params.groupDesc += currentList[idx].departmentName;
                    if (currentList.length != idx + 1) {
                        $scope.params.groupCode +=  ",";
                        $scope.params.groupDesc +=  ",";
                    }
                }
            }
        };

        $scope.geCategory = function (e) {
            $('#categoryLov').modal('toggle')
        };
        $scope.paramsCancelCategory = function(){
            $scope.params.deptCode = '';
            $scope.params.deptDesc = '';
        };

        $scope.selecCategoryReturn = function (key, value, currentList) {
            if (currentList && currentList.length > 0) {
                $scope.params.deptCode = '';
                $scope.params.deptDesc = '';
                for (var idx = 0; idx < currentList.length; idx ++) {
                    $scope.params.deptCode += currentList[idx].departmentCode;
                    $scope.params.deptDesc += currentList[idx].departmentName;
                    if (currentList.length != idx + 1) {
                        $scope.params.deptCode +=  ",";
                        $scope.params.deptDesc +=  ",";
                    }
                }
            }
        };

        $scope.getHeight = function (){
            //获取div 的实际高度
            $timeout(function () {
                var divFix = document.getElementById('fixedId') ;
                var divParentFixed = document.getElementById('parentFixedId') ;
                var rect = divFix.getBoundingClientRect();
                var rectParent = divParentFixed.getBoundingClientRect();
                if ((rect.height/rectParent.height) >= 0.89){
                    divFix.style.height = 90+'%' ;
                }else{
                    divFix.style.height = 'auto';
                }
                $scope.btnFreeze();
            }, 200)
        };

        $scope.search = function (){
            $scope.cWTable.getData() ;
            // $scope.getHeight();
        };

        //新增
        $scope.daoru = function () {
            $("#fileUpLoad").val('');
            $('#excelUp').modal('toggle');
        };

        //上传附件,更新实收金额借计单编号
        $scope.uploadSave = function (invalid) {
            if(invalid){
                SIEJS.alert('请检查必填项', 'error', '确定');
                return;
            }
            saafLoading.show();
            var fd = new FormData();
            var file = document.getElementById('fileUpLoad').files[0];
            if (!file) {
                SIEJS.alert("请选择上传文件", 'error', '确定');
                return;
            }
            fd.append('file', file);
            //fd.append("proposalId", getId());
            $http.post(URLAPI.updateImportDmInfo, fd, {
                transformRequest: angular.identity,
                headers: {
                    'Certificate': sessionStorage[appName + '_certificate'] || localStorage[appName + '_certificate'] || 'nothing',
                    'Content-Type': undefined
                }
            }).success(function (response) {
                saafLoading.hide();
                $('#excelUp').modal('toggle');
                if (response.status == 'S') {
                    $scope.dataTable.search();
                    SIEJS.alert(response.msg, 'success', '确定');
                } else {
                    SIEJS.alert("导入失败!", 'error', '确定');
                }
            }).error(function(response) {
                saafLoading.hide();
                SIEJS.alert("导入失败!", 'error', '确定');
            });
        };


        /*监听currentList2, 如果下一页，则清除上一页内容*/
        $scope.$watch('cWTable.data', function (newVal, oldVal) {
            if (newVal === oldVal || !newVal || !$scope.flag2){
                if(!$scope.flag2){
                    $scope.flag2 = true ;
                }
                return;
            }
            $timeout(function () {
                $scope.currentList2 = [] ;
            })
        }, true);


        //获取字典信息
        $scope.lookupCodeParty = window.parent.saafrootScope.lookupCode || $rootScope.lookupCode;
        if (!$scope.lookupCodeParty) {
            lookupCode(function (res) {
                $scope.lookupCodeParty = $rootScope.lookupCode = res.data;
            });
        }
        $scope.btnDel = function () {
            var row = $scope.dataTable.selectRow;
            SIEJS.confirm('您确认要删除吗？', '', '确定', function () {
                httpServer.post(
                    URLAPI.deleteTtaImportDmInfo,

                    {
                        'params': JSON.stringify({
                            "dmId": row.dmId,
                            flag :'single'
                        })
                    },
                    function (res) {
                        if (res.status.toLowerCase() == 'e') {
                            SIEJS.alert(res.msg, "error");
                        } else {
                            SIEJS.alert(res.msg, "success");
                            $scope.dataTable.search(1);

                        }
                    }
                )
            });
        };
        var exportTimer;
        /**
         * 批量删除
         * @constructor
         */
        $scope.PLSC = function () {
            $scope.paramsPL = {} ;
            $('#PLDelete').modal('toggle')
        };
        // 查询导出结果
        function exportStatus(code) {
            var params = {
                params:"{Certificate:\""+code+"\"}"

            };
            $.ajax({
                url: URLAPI.getOiStatus,
                type: 'post',
                data: params,
                timeout: 60000 * 5,
                headers: {
                    "Content-Type": "application/x-www-form-urlencoded;charset=UTF-8",
                    "Certificate": sessionStorage[appName + '_certificate'] || localStorage[appName + '_certificate'] || 'nothing',
                },
                dataType: 'html',
                success: function (res) {
                    var res = JSON.parse(res);
                    if (res.status === 'S') {
                        jQuery("#value1").text(res.data.currentStage);
                        jQuery("#value2").text(res.data.orderNum);
                        clearInterval(exportTimer);
                    } else  if (res.status === 'E' || res.status === 'M') {
                        jQuery("#value1").text(res.data.currentStage);
                        jQuery("#value2").text(res.data.orderNum);
                        clearInterval(exportTimer);
                    }else{
                        $scope.messageCurrentStage = res.data.currentStage ;
                        jQuery("#value1").text(res.data.currentStage);
                        jQuery("#value2").text(res.data.orderNum);
                        $scope.messageCurrentOrderNum = res.data.orderNum ;
                    }

                },
                error: function (e) {
                    clearInterval(exportTimer);
                }
            });
        }


        //上传附件
        $scope.save = function (invalid) {
            if(invalid){
                SIEJS.alert('请检查必填项', 'error', '确定');
                return;
            }
            var fd = new FormData();
            var file = document.getElementById('fileUpLoad').files[0];
            if (!file) {
                SIEJS.alert("请选择上传文件", 'error', '确定');
                return;
            }
            $scope.buttonFlag = false ;
            fd.append('file', file);
            exportTimer = setInterval(function () {
                exportStatus(sessionStorage[appName + '_certificate'] || localStorage[appName + '_certificate'] || 'nothing');
            }, 5000);
            $http.post(URLAPI.saveTtaImportCwInfo, fd, {
                transformRequest: angular.identity,
                headers: {
                    'Certificate': sessionStorage[appName + '_certificate'] || localStorage[appName + '_certificate'] || 'nothing',
                    'Content-Type': undefined
                }
            }).success(function (response) {
                SIEJS.alert(response.msg, 'success', '确定');
            }).error(function(response) {
                SIEJS.alert(response.msg, 'error', '确定');
                $scope.buttonFlag = true ;
                clearInterval(exportTimer);
            });

        };

        //提交
        $scope.submitApproval = function (form) {
            if($scope.cWTable.length == 0){
                SIEJS.alert('当前页面没有数据,不允许提交审批', 'error', '确定');
                return;
            }else if($scope.cWTable[0].headerStatus != 'DS01'){
                SIEJS.alert('状态不是制单中，不允许提交审批', 'error', '确定');
                return;
            }

            $scope.extend = {
                "tasks_assignees": []
            };
            $scope.variables = []; //流程变量
            angular.forEach($scope.dataTable.selectRow, function (value, key) {
                $scope.variables.push({
                    name: key,
                    type: 'string',
                    value: value
                });
            });

            $scope.properties = {
                "menucode": "HTGL",
                "opinion": ""
            };
            $scope.paramsBpm = {
                "extend": $scope.extend,
                "variables": $scope.variables,
                "properties": $scope.properties,
                "responsibilityId": "990021",
                "respId": "990021",
                "processDefinitionKey": "REPORT.-999", //流程唯一标识，需修改为对应业务表单流程唯一标识
                "saveonly": false,
                "businessKey": $scope.cWTable[0].id, //单据ID
                "title": "DM_REPORT_审批" + $scope.cWTable[0].batchCode, //流程标题，修改为当前业务信息
                "positionId": 0,
                "orgId": 0,
                "userType": "20",
                "billNo": $scope.cWTable[0].batchCode
            };
            httpServer.post(URLAPI.processStart, {
                'params': JSON.stringify($scope.paramsBpm)
            }, function (res) {
                if (res.status == 'S') {
                    SIEJS.alert("提交成功", "success", "确定");
                }
                else {
                    SIEJS.alert(res.msg, "error", "确定");
                    $scope.getClauseh($scope.paramsBpm.businessKey,null);

                }
            }, function (err) {
                SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                // $("#TJSP").removeAttr("disabled");
            });
        };


        /**
         * 保存行
         */
        $scope.btnSave = function (form){
            /*if(!validateForm(form)){
                return;
            }*/
            $scope.saveAll =  $scope.cWTable.data ;
            httpServer.post(URLAPI.saveOrUpdateALLTtaDm,
                {params: JSON.stringify({
                        save:$scope.saveAll
                    })},
                function (res) {
                    if (res.status == "S") {
                        SIEJS.alert('保存', 'success', '确定');
                        $scope.cWTable.getData() ;
                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                },
                function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                    $("#saveButton").attr("disabled", true);
                }
            );
        };

        /**
         * 拆分
         */
        $scope.windowSplit = function (){
        	// $("#dmTableId").freeze({ height:500,width:1416, rows: 2, cols: 8 });

            if (!$scope.cWTable.data || !$scope.cWTable.data.selectRowData) {
                SIEJS.alert("请选择一行数据进行修改!", "error", "确定");
                return;
            }
            $scope.currentRow = $scope.cWTable.data.selectRowData;
            $scope.splitDataTable = [];
               /* [
                    {   unconfirmAmount: $scope.currentRow.unconfirmAmount,
                        priorVendorCode: $scope.currentRow.priorVendorCode,
                        priorVendorName: $scope.currentRow.priorVendorName
                    }
                ] ; */ //不包含被拆分供应商。
            $('#cwSplit').modal('toggle');
        } ;

        /**
         * 新增一行
         */
        $scope.btnNewRow = function (){
            var splitRow = {
                receiveAmount:'',
                priorVendorCode:'',
                priorVendorName:''

            };
            $scope.splitDataTable.push(splitRow);
        };

        /**
         * 选择供应商
         * @param value
         */
        $scope.getVendorL = function (value) {

            $('#cWReportListId').modal('toggle') ;
        };

        /**
         * 确认选择供应商
         * @param key
         * @param value
         * @param currentList
         */
        $scope.selectSupplierReturn = function (key, value, currentList) {
            var row = $scope.splitDataTable[$scope.splitDataTable.selectRow];
            row.priorVendorCode = currentList[0].supplierCode;
            row.priorVendorName = currentList[0].supplierName;
        };


        /**
         * 行明细供应商信息
         * @param value
         */
        $scope.getVendorRow = function (value) {
            $('#cWReportListRowId').modal('toggle') ;
        };
        /**
         * 行明细确认选择供应商
         * @param key
         * @param value
         * @param currentList
         */
        $scope.selectSupplierRowReturn = function (key, value, currentList) {
            //var row = $scope.splitDataTable[$scope.splitDataTable.selectRow];
            var curRow  = $scope.cWTable.data.selectRowData;
            curRow.priorVendorCode = currentList[0].supplierCode;
            curRow.priorVendorName = currentList[0].supplierName;

            httpServer.post(URLAPI.findChangeVender,
                {params: JSON.stringify(curRow)},
                function (res) {
                    if (res.status == "S") {
                        //$scope.splitDataTable[$scope.splitDataTable.selectRow] = res.data;
                        var resultData =  res.data;
                        curRow.receiveAmount = resultData.receiveAmount;
                        curRow.noReceiveAmount = resultData.noReceiveAmount;
                        curRow.chargeMoney = resultData.chargeMoney;
                        curRow.contractYear = resultData.contractYear;
                        curRow.contractTerms = resultData.contractTerms;
                        curRow.additionRate = resultData.additionRate;
                        curRow.chargeStandards = resultData.chargeStandards;

                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                },
                function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                }
            );
        };
        $scope.clearVendor = function() {
            var row = $scope.splitDataTable[$scope.splitDataTable.selectRow];
            var curRow  = $scope.cWTable.data.selectRowData;
            curRow.priorVendorCode = '';
            curRow.priorVendorName = '';
        };

        /**
         * 选取豁免原因_1
         * @param index
         * @param currentValue
         * @param lookUpData
         */
        $scope.purchaseFun = function (index,currentValue,lookUpData){
            var row = $scope.cWTable.data[index] ;
            //差异金额小于0并且采购行动为收取
            if (row.difference < 0 && row.purchase === 'A01') {
                SIEJS.alert("因“差异（不含加成）”金额小于零,采购行动不能选“收取”", "error", "确定");
                return;
            }
            row.noUnconfirmAmount = row.noUnconfirmAmount === undefined ? 0 : row.noUnconfirmAmount;
            //采购行动为“收取”或者“折扣收取”的，采购确认金额需大于0
            if (row.purchase && (row.purchase === 'A01' || row.purchase === 'A10') && row.noUnconfirmAmount <= 0) {
                SIEJS.alert("采购行动为“收取”或者“折扣收取”的，采购确认金额需大于0", "error", "确定");
                return;
            }
            row.exemptionReason  = '';
            row.exemptionReason2 = '';
            var values = lookUpData.filter(function (x) {
                return  x.lookupCode  == currentValue;
            });
            if($scope.lookupCodeParty && values.length >0){
                //获取 豁免原因_1
                row.exemptionReasonList = $scope.lookupCodeParty.filter(function (x) {
                    return x.lookupType == 'TTA_DM_REASON' && x.parentLookupValuesId == values[0].lookupValuesId;
                });
                if (!row.exemptionReasonList || row.exemptionReasonList.length == 0) {
                    row.exemptionReasonList = $scope.lookupCodeParty.filter(function (x) {
                        return x.lookupType == 'TTA_DM_ADJ_REASON' && x.parentLookupValuesId == values[0].lookupValuesId;
                    });
                }
            }

        };

        /**
         * 选取豁免原因2
         * @param row
         * @param list
         * @param value
         */
        $scope.exemptionReasonListChangeIn = function (row,list,value){
            var values  = list.filter(function (x) {
                return  x.lookupCode  == value;
            });
            row.exemptionReason2 = '';
            if($scope.lookupCodeParty && values.length >0){
                //获取 豁免原因_2
                row.exemptionReasonList2 = $scope.lookupCodeParty.filter(function (x) {
                    return x.lookupType == 'TTA_DM_REASON1' && x.parentLookupValuesId == values[0].lookupValuesId;
                });
            }

            if ((!row.exemptionReasonList2 || row.exemptionReasonList2.length == 0) && values.length >0) {
                row.exemptionReasonList2 = $scope.lookupCodeParty.filter(function (x) {
                    return x.lookupType == 'TTA_DM_ADJ_REASON1' && x.parentLookupValuesId == values[0].lookupValuesId;
                });
            }

        };

        /**
         * 选择转办人
         */
        $scope.btnBz =  function (){
            $('#lovcWReportFind').modal('toggle') ;
        };

        /**
         * 转办人确认
         */
        $scope.selectTransferReturn = function (key, value, currentList){

            if (!$scope.cWTable.data.selectRowData){
                SIEJS.alert("请选择一行", "error", "确定");
                return ;
            }
            var pLins = [];
            pLins.push($scope.cWTable.data.selectRowData) ;
            httpServer.post(URLAPI.saveOrUpdateTransferALLTtaDm,
                {params: JSON.stringify({
                        lines:pLins,
                        personId:currentList[0].userId
                    })},
                function (res) {
                    if (res.status == "S") {
                        SIEJS.alert('转办成功', 'success', '确定');
                        $scope.cWTable.getData() ;
                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                },
                function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                    $("#saveButton").attr("disabled", true);
                }
            );

        };
        /**
         * 供应商拆分
         */
        $scope.saveWindow = function (){
            httpServer.post(URLAPI.saveOrUpdateSplitALLTtaDm,
                {params: JSON.stringify({
                        save:$scope.splitDataTable,
                        currentRow:$scope.currentRow
                    })},
                function (res) {
                    if (res.status == "S") {
                        $('#cwSplit').modal('toggle');
                        SIEJS.alert('拆分成功', 'success', '确定');
                        $scope.cWTable.getData() ;
                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                },
                function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                    $("#saveButton").attr("disabled", true);
                }
            );
        };

        //删除表达式

        $scope.btnDelRow = function () {

            var index=$scope.splitDataTable.selectRow;

            SIEJS.confirm('删除', '是否确定删除？', '确定', function () {

                $scope.splitDataTable.splice(index, 1);             //新增列没有ID直接删除
                SIEJS.alert("操作成功!", "已成功删除数据！", "success");
                $scope.$apply();

            })

        };


        /*************************** 上传 start ****************/
       /* $scope.callback = function (row,index,flag){
            if('upload' == flag){
                var obj = document.getElementById('cWReportUploadFile') ;
                obj.outerHTML=obj.outerHTML;
                $('#addFileUpload').modal('toggle');
                $scope.cWUploadRow = row ;
            }

        }*/
        //上传附件
        $scope.uploadFile = function (form,params) {

            var loading = $("#saafLoading");
            if (loading.length === 0) {
                var loading = $('<div id="saafLoading"><div  style="position: absolute; top:50%;left:50%; text-align: center;margin: 0 auto; width:100px;"><img src="img/loading1.gif"></div></div>');
                loading.css({
                    position: "fixed",
                    top: 0,
                    width: "100%",
                    "z-index": 9000,
                    "height": "100%",
                    'display': 'none'
                });
                loading.prependTo(angular.element('body'));
            } else {
                loading.removeAttr('data-loading');
            }
            loading.show();
            var f = $scope.myFile;
            var fd = new FormData();
            var file = document.getElementById('cWReportUploadFile').files[0];
            if (!file) {
                SIEJS.alert("请选择上传文件", 'error', '确定');
                return;
            }
            var fileName = file.name;
            var sideAgrtHId = $scope.cWUploadRow.dmId;
            if (sideAgrtHId == undefined || sideAgrtHId == null){
                SIEJS.alert("头信息为空,上传失败", 'error', '确定');
            }

            fd.append('sideAgrtHId', sideAgrtHId);
            fd.append('file', file);
            fd.append("fileName", fileName);
            fd.append("functionId", "tta_dm_checking_report");

            $http.post(URLAPI.ttaSideAgrtHeaderUpload, fd, {
                transformRequest: angular.identity,
                headers: {
                    'Certificate': sessionStorage[appName + '_certificate'] || localStorage[appName + '_certificate'] || 'nothing',
                    'Content-Type': undefined
                }
            }).success(function (response) {
                if (response.status == 'S') {
                    $('#addFileUpload').modal('toggle');
                    loading.hide();
                    //$scope.cWUploadRow.sourceFileName = response.data[0].sourceFileName ;
                    //$scope.cWUploadRow.fileId = response.data[0].fileId ;
                    $scope.ttaOsdfileDataTable.search(1);
                    SIEJS.alert(response.msg, 'success', '确定');

                } else {
                    loading.hide();
                    SIEJS.alert(response.msg, 'error', '确定');
                }
            }).error(function(response) {
                loading.hide();
                SIEJS.alert("上传失败", 'error', '确定');
            });
        };
        //下载附件事件
        $scope.downFileEvent = function (row) {
            //console.log(msg);
            if (row.fileId == undefined || row.fileId == null){
                SIEJS.alert("当前没有选中一行数据,不能下载",'warning','确定');
                return;
            }
            var url = URLAPI.ttaSideAgrtHeaderDownLoad + '?fileId=' + row.fileId;
            window.open(url, [""], [""]);//避开因同源策略而造成的拦截
        };

        //删除附件
        $scope.delUploadFile = function (row) {
            var item = $scope.ttaOsdfileDataTable.selectRow;
            var cwTableRow = $scope.cWTable.data.selectRowData;
            if (cwTableRow.processStatus && cwTableRow.processStatus !== 'DS01') {
                SIEJS.alert('非制单中不能进行此操作', 'error', '确定');
                return;
            }

            SIEJS.confirm('提示', '确定要删除所选的信息吗？', '确定', function () {
                var p = {
                    //fileId: row.fileId //文件id
                    fileId: item.fileId //文件id
                };
                httpServer.post(URLAPI.ttaSideAgrtHeaderFileDelete, {
                    'params': JSON.stringify(p)
                }, function (res) {
                    if (res.status == 'S') {
                    	/*
                        	$scope.cWUploadRow.sourceFileName = null ;
                        	$scope.cWUploadRow.fileId = null ;
						*/
                        $scope.ttaOsdfileDataTable.search(1);
                        SIEJS.alert(res.msg, 'success', '确定');
                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                }, function (err) {
                    SIEJS.alert("删除失败! " + err.msg, "error", "确定");
                });

            });
        };

        $scope.changeCollectionMethod = function (index){
            // 收费方式为TTA，计费单位“为“/页” ，则应收金额(receiveAmount) =”计费金额(chargeMoney)“/4*“生效区域数”(effectiveAreaCnt)
            // 收费方式为OnTop，则应收金额 =”计费金额“/4*“生效区域数”*（1+加成比例 addition_rate ）
            var row = $scope.cWTable.data[index];
            if (!row.collect) {
                row.receiveAmount = null;
            } else if  (row.collect == 'TTA') {
                row.receiveAmount = Math.round(row.chargeMoney / 4 * row.effectiveAreaCnt);
            } else {
                row.receiveAmount = Math.round(row.chargeMoney/4* row.effectiveAreaCnt*(1 + row.additionRate));
            }
        };


        $scope.afterGetData = function () {
            $scope.currentList2 = [] ;
            if ($scope.cWTable.data) {
                var totalParams =[] ;
                var actionParams = [];
                var reasonParams = [];
                var reasonOneParams  = [];
                if($scope.lookupCodeParty){
                    var totalParams =  $scope.lookupCodeParty.filter(function (x) {
                        return  x.lookupType == 'TTA_DM_ACTION' || x.lookupType == 'TTA_DM_ADJ_ACTION'
                             || x.lookupType =='TTA_DM_REASON'  || x.lookupType == 'TTA_DM_ADJ_REASON'
                             || x.lookupType =='TTA_DM_REASON1' || x.lookupType == 'TTA_DM_ADJ_REASON1'
                    });
             		
                    actionParams = totalParams.filter(function (x) {
                        return x.lookupType == 'TTA_DM_ACTION' ;
                    });

                    var adjActionList = totalParams.filter(function (x) {
                        return x.lookupType == 'TTA_DM_ADJ_ACTION' ;
                    });

                    angular.forEach(adjActionList, function(item, index) {
                        actionParams.push(item);
                    });

                    reasonParams = totalParams.filter(function (x) {
                        return x.lookupType == 'TTA_DM_REASON' ;
                    });

                    var adjReason = totalParams.filter(function (x) {
                        return x.lookupType == 'TTA_DM_ADJ_REASON' ;
                    });
                    angular.forEach(adjReason, function(item, index) {
                        reasonParams.push(item);
                    });
                    
                    reasonOneParams = totalParams.filter(function (x) {
                        return x.lookupType == 'TTA_DM_REASON1' ;
                    });

                    var adjReason1 = totalParams.filter(function (x) {
                        return x.lookupType == 'TTA_DM_ADJ_REASON1' ;
                    });

                    angular.forEach(adjReason1, function(item, index) {
                        reasonOneParams.push(item);
                    })
                }
                for (var i = 0; i < $scope.cWTable.data.length; i++) {
                    //获取采购行动
                    var currentValue = $scope.cWTable.data[i].purchase;

                    var lookUpData = actionParams.filter(function (x) {
                        return currentValue  && x.lookupCode == currentValue;
                    });
                    //获取豁免原因1
                    if ( lookUpData.length > 0) {
                        //获取 豁免原因_1
                        $scope.cWTable.data[i].exemptionReasonList = reasonParams.filter(function (x) {
                            return   x.parentLookupValuesId == lookUpData[0].lookupValuesId;
                        });
                    }

                    //获取 豁免原因_2
                    var currentValue2 = $scope.cWTable.data[i].exemptionReason;
                    var values2 = reasonParams.filter(function (x) {
                        return currentValue2  && x.lookupCode == currentValue2;
                    });
                    if ( values2.length > 0) {
                        $scope.cWTable.data[i].exemptionReasonList2 = reasonOneParams.filter(function (x) {
                            return  x.parentLookupValuesId == values2[0].lookupValuesId;
                        });
                    }
                }
            }
           $scope.getHeight() ;
        };

        /***************************上传end ****************/

        /**
         * 多选
         * @param item
         * @param e
         * @param t
         */
        $scope.check = function (item, e, t) {
            $scope.flag2 = false ;
            var checked = e.currentTarget.checked; // 当前选中状态
            var currentIsPush = true;
            var currentIndex = -1;
            for (var i = 0; i < $scope.currentList2.length; i++) {
                if (item.dmId === $scope.currentList2[i].dmId) {
                    currentIsPush = false;
                    currentIndex = i;
                    break;
                }
            }

            if (checked && currentIsPush && t !== false) {
                item.checked = true; // 选中标识
                $scope.currentList2.push(item)
            }
            if (!checked && !currentIsPush) {
                item.checked = false; // 选中标识
                $scope.currentList2.splice(currentIndex, 1); // 删除当前选择的数据　－－－－－－－－－－－－－－－－－－－－－－－－２０１８－１－９
            }
            if (checked && !currentIsPush) {
                item.checked = true; // 选中标识
            }
        };


        // 全选按钮
        $scope.checkedAll = function (e) {
            $scope.flag2 = false ;
            if (e.target.checked) {// 全选
                $scope.selectedAll = true;
                for (var n in $scope.cWTable.data) {
                    var row = $scope.cWTable.data[n];
                    if (/*!$scope.setCheckedDisabled(row)*/ true) { // 非被禁用
                        row.checked = true;
                        var isPush = false;
                        for (var m in $scope.currentList2) {
                            var list = $scope.currentList2[m];
                            if (row.dmId === list.dmId) {
                                isPush = true;
                                break;
                            }
                        }
                        if (!isPush) $scope.currentList2.push(row);
                    }

                }
            } else {// 返选
                $scope.selectedAll = false;
                for (var n in $scope.cWTable.data) {
                    var row = $scope.cWTable.data[n];
                    row.checked = false;
                    var index = $scope.currentList2.indexOf(row);
                    $scope.currentList2.splice(index--, 1);// for 循环使用 splice 删除数组请 把下标 --
                }
            }

        };

        //跳转
        $scope.getRow = function (row){
            iframeTabAction('流程单据：'+ row.processCode,'/reportProcessHeaderD/' + row.processReId);
        };

        //生成
        $scope.btnCreate = function (form){
            $scope.currentList2 = [];
/*            for (var n in $scope.cWTable.data) {
                var row = $scope.cWTable.data[n];
                if (row.checked == true) {
                    $scope.currentList2.push(row);
                }
            }*/
            for (var i = 0; i < $scope.cWTable.data.length; i++) {
                var row = $scope.cWTable.data[i];
                if (row.checked != undefined && row.checked == true) {
                    $scope.currentList2.push(row);
                }
            }
            if($scope.currentList2.length == 0){
                SIEJS.alert('当前未选中请先选择数据', "error");
                return ;
            }
            var values = $scope.currentList2.filter(function (x) {
                return  x.processCode;
            });
            if(values.length > 0){
                SIEJS.alert('当前选中包含已经生成流程单据的【'+values[0].content+'】', "error");
                return ;
            }
            for (var i = 0; i < $scope.currentList2.length; i++) {
                var row = $scope.currentList2[i];
                row.noUnconfirmAmount = row.noUnconfirmAmount === undefined ? 0 : row.noUnconfirmAmount;
                //采购行动为“收取”或者“折扣收取”的，采购确认金额需大于0
                if (row.purchase && (row.purchase === 'A01' || row.purchase === 'A10') && row.noUnconfirmAmount <= 0) {
                    SIEJS.alert("您选择的产品编号为" + row.itemNbr + "那一行数据的采购行动为“收取”或者“折扣收取”，采购确认金额需大于0", "error", "确定");
                    return;
                }
            }
            if(!validateForm(form)){
                return;
            }
            /*
            ①“已收取”，需要填写借记单号，“借记单编号”不能为空
            ②“收取”且“差异（不含加成）”金额<0，“备注”不能为空
            ③"确认不收取”，“豁免原因_1”不能为空
            ④"折扣收取”，“豁免原因_1”不能为空
             */

            for (var idx = 0; idx < $scope.currentList2.length; idx ++) {
                var obj = $scope.currentList2[idx];
                if ((obj.purchase == 'A11' || obj.purchase == 'A10' || obj.purchase == 'A23') && !obj.exemptionReason) {
                    SIEJS.alert('当采购行动为确认不收取、折扣收取、计费标准变更-确认不收取，豁免原因_1不能为空！', "error");
                    return;
                }
            }
            SIEJS.confirm('您确认要生成吗？', '', '确定', function () {
                httpServer.post(
                    URLAPI.saveOrUpdateTtaReportProcessHeader,
                    {
                        'params': JSON.stringify({"lines": $scope.currentList2,"reprotTypeName":'DM'})
                    },
                    function (res) {
                        if (res.status == 'S') {
                            if (!res.data.batchCode) {
                                SIEJS.alert(res.msg, "error", "确定");
                            }
                            iframeTabAction('流程单据：'+ res.data.batchCode,'/reportProcessHeaderD/' + res.data.reportProcessHeaderId);
                        }
                        else {
                            SIEJS.alert(res.msg, "error", "确定");
                        }
                    },
                    function (err) {
                        SIEJS.alert('数据获取失败！', "error");
                    }
                );
            });
        };

        /**
         * 选择调整人
         */
        $scope.getNameL =  function (){
            $('#lovOsdReportAdjustByFind').modal('toggle') ;
        };


        /**
         * 调整人确认
         */
        $scope.selectdjustByReturn = function (key, value, currentList) {
            var curRow  = $scope.cWTable.data.selectRowData;
            curRow.adjustBy = currentList[0].userId;
            curRow.adjustByName = currentList[0].userFullName;
        };


        //按年份导出数据
        $scope.btnExport2 = function () {
            $("#AddModal").modal('show');
        };

        $scope.addInfo = function () {
            var name = 'dataTable' ;
            var params = $scope.AddData ;
            var count = $scope.dataTable.count;
            tableXlsExport(name, params, count);
        };

        /**
         * 导出选择批次号
         */
        $scope.btnExportBatch = function (){
            $('#findReportExportOsdId').modal('toggle');
        };
        /**
         * 导出批次导出
         */
        $scope.selectReportExportOsdReturn = function (key, value, currentList) {
            $scope.aboiParams = angular.copy($scope.params);
            $scope.aboiParams.batchCode = '';
            angular.forEach(currentList, function (item, index, array) {
                $scope.aboiParams.batchCode += item.batchId + ",";
            });
            $scope.aboiParams.batchCode = $scope.aboiParams.batchCode.substring(0, $scope.aboiParams.batchCode.lastIndexOf(','));
            var name = 'dataTable';
            var params = $scope.aboiParams;
            var count = $scope.dataTable.count;
            tableXlsExport(name, params, count);
        };


        $scope.saveNppPurchaseReply = function (form) {
            if (!validateForm(form)) {
                return;
            }

            $scope.currentList2 = [];
            for (var n in $scope.cWTable.data) {
                var row = $scope.cWTable.data[n];
                if (row.checked == true) {
                    $scope.currentList2.push(row);
                }
            }
            
            if ($scope.currentList2 && $scope.currentList2.length === 0) {
                SIEJS.alert('当前未选中请先选择数据', "error");
                return ;
            }
            for(var index in $scope.currentList2){
                var row = $scope.currentList2[index];
                row.purchase = $scope.currentPurcahseReply.purchaseAct;
                row.exemptionReason = $scope.currentPurcahseReply.exemptionReason;
                row.exemptionReason2 = $scope.currentPurcahseReply.exemptionReason2;
            }

            for(var i = 0; i < $scope.currentList2.length; i ++) {
                //获取采购行动
                var currentValue = $scope.cWTable.data[i].purchase;
                var lookUpData = $scope.lookupCodeParty.filter(function (x) {
                    return currentValue && x.lookupType == 'TTA_DM_ACTION' && x.lookupCode == currentValue;
                });
                //获取豁免原因1
                if($scope.lookupCodeParty && lookUpData.length >0 ){
                    //获取 豁免原因_1
                    $scope.cWTable.data[i].exemptionReasonList = $scope.lookupCodeParty.filter(function (x) {
                        return x.lookupType == 'TTA_DM_REASON' && x.parentLookupValuesId == lookUpData[0].lookupValuesId;
                    });
                }

                //获取 豁免原因_2
                var currentValue2 = $scope.cWTable.data[i].exemptionReason ;
                var values2  = $scope.lookupCodeParty.filter(function (x) {
                    return currentValue2 && x.lookupType == 'TTA_DM_REASON' &&  x.lookupCode  == currentValue2;
                });
                if($scope.lookupCodeParty && values2.length >0){
                    $scope.cWTable.data[i].exemptionReasonList2 = $scope.lookupCodeParty.filter(function (x) {
                        return x.lookupType == 'TTA_DM_REASON1' && x.parentLookupValuesId == values2[0].lookupValuesId;
                    });
                }
            }
            $('#nppPurchaseReplyModal').modal('toggle');
        };

        //选择采购行动
        $scope.purchaseReplyFun = function (key) {
            if (!key) return;
            $scope.currentPurcahseReply.exemptionReason2 = '';
            var values = $scope.lookupCodeParty.filter(function (x) {
                return  x.lookupCode  == key  && x.lookupType == 'TTA_DM_ACTION';
            });
            if($scope.lookupCodeParty){
                //获取 豁免原因_1
                $scope.currentExemptionReasonList = $scope.lookupCodeParty.filter(function (x) {
                    return x.lookupType == 'TTA_DM_REASON' && x.parentLookupValuesId == values[0].lookupValuesId;
                });
            }
        };

        //豁免原因1选择
        $scope.exemptionReasonListChangeByModal = function () {
            var values = $scope.currentExemptionReasonList.filter(function (x) {
                return x.lookupCode == $scope.currentPurcahseReply.exemptionReason;
            });
            if ($scope.lookupCodeParty) {
                //获取 豁免原因_2
                $scope.currentExemptionReasonList2 = $scope.lookupCodeParty.filter(function (x) {
                    return x.lookupType == 'TTA_DM_REASON1' && x.parentLookupValuesId == values[0].lookupValuesId;
                });
            }
        };
        //计算差异
        $scope.countDifferenceAmt = function (row) {
            row.noUnconfirmAmount = $scope.formatAmount(row.noUnconfirmAmount);
            row.receiveAmount = $scope.formatAmount(row.receiveAmount);
            row.noReceiveAmount = $scope.formatAmount(row.noReceiveAmount);
            row.unconfirmAmount = (row.collect == 'TTA' ? (row.noUnconfirmAmount * (100+ (row.additionRate?row.additionRate:0) *1 * 100)/100).toFixed(0) : row.noUnconfirmAmount );
            //如“采购确认收取金额（含加成）”-“应收金额（含加成）”大于0，为0，否则等于“采购确认收取金额（含加成）”-“应收金额（含加成）”
            row.difference = row.unconfirmAmount * 1 - row.receiveAmount*1 < 0 ? (row.unconfirmAmount * 1 - row.receiveAmount*1).toFixed(0) : 0;
            row.noDifference = row.noUnconfirmAmount * 1 - row.noReceiveAmount*1 < 0 ? (row.noUnconfirmAmount * 1 - row.noReceiveAmount*1).toFixed(0) : 0;
        };

        $scope.blurEvent = function(row){
            row.noUnconfirmAmount = row.noUnconfirmAmount === undefined ? 0 : row.noUnconfirmAmount;
            //采购行动为“收取”或者“折扣收取”的，采购确认金额需大于0
            if (row.purchase && (row.purchase === 'A01' || row.purchase === 'A10') && row.noUnconfirmAmount <= 0) {
                SIEJS.alert("采购行动为“收取”或者“折扣收取”的，采购确认金额需大于0", "error", "确定");
            }
        };

        //输入的金额有千分位分割符,去掉分割符
        $scope.formatAmount = function(amount) {
            var f = parseFloat(amount);
            if (isNaN(f)) {
                return 0;
            }
            amount = amount + "";
            return parseFloat(amount.replace(/,/g,""))
        };

    });
});
