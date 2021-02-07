'use strict';
define(['app', 'pinyin', 'ztree','XLSX','fixedReport'], function (app, pinyin, ztree,XLSX,fixedReport) {
    app.controller('monthlyCheckingCtrl', function ($scope, $filter, $location, $timeout,$rootScope, $state, $stateParams, SIEJS, httpServer,validateForm, URLAPI, iframeTabAction, setNewRow,$http,lookupCode,tableXlsExport,saafLoading) {

        $scope.buttonFlag = true ;
        $scope.userType = JSON.parse(sessionStorage[appName + '_successLoginInfo']).userType;
        $scope.cWTable = {};
        $scope.ABOIdDataTable = {};
        $scope.ttaOsdfileDataTable= {};
        $scope.fileDataParams = {};
        $scope.aboiParams = {};
        $scope.AddData = {};
        $scope.currentList2 = [] ;
        $scope.batchId = $stateParams.batchId;
        $scope.params = {"batchCode":$scope.batchId,"orderBy":'receiveAmount'} ;
        $scope.splitDataTable = [] ;
        $scope.firstText = "请选择";
        $scope.search = function (){
            $scope.cWTable.getData() ;
            $scope.getHeight() ;
        };
        $scope.btnFreeze = function (){
            var fixedTable1 = fixReportTable(document.getElementById('fixedId'),6);
        };
        $scope.tabChangeInfo = function (name) {
            $scope.tabAction = name;
            if (name=='cWInfo') {
                if(! $scope.cWTable.data){
                    $scope.cWTable.getData() ;
                }

            }else{
                $scope.findcWSummary();
            }
        };


        $scope.currentPurcahseReply = {};
        $scope.currentExemptionReasonList = [];
        $scope.currentExemptionReasonList2 = [];

//采购批量回复
        $scope.CGPLHF = function () {
            $('#nppPurchaseReplyModal').modal('toggle');
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
                    return currentValue && x.lookupType == 'TTA_OSD_ACTION' && x.lookupCode == currentValue;
                });
                //获取豁免原因1
                if($scope.lookupCodeParty && lookUpData.length >0 ){
                    //获取 豁免原因_1
                    $scope.cWTable.data[i].exemptionReasonList = $scope.lookupCodeParty.filter(function (x) {
                        return x.lookupType == 'TTA_OSD_REASON' && x.parentLookupValuesId == lookUpData[0].lookupValuesId;
                    });
                }

                //获取 豁免原因_2
                var currentValue2 = $scope.cWTable.data[i].exemptionReason ;
                var values2  = $scope.lookupCodeParty.filter(function (x) {
                    return currentValue2 && x.lookupType == 'TTA_OSD_REASON' &&  x.lookupCode  == currentValue2;
                });
                if($scope.lookupCodeParty && values2.length >0){
                    $scope.cWTable.data[i].exemptionReasonList2 = $scope.lookupCodeParty.filter(function (x) {
                        return x.lookupType == 'TTA_OSD_REASON1' && x.parentLookupValuesId == values2[0].lookupValuesId;
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
                return  x.lookupCode  == key && x.lookupType == 'TTA_OSD_ACTION';
            });
            if($scope.lookupCodeParty){
                //获取 豁免原因_1
                $scope.currentExemptionReasonList = $scope.lookupCodeParty.filter(function (x) {
                    return x.lookupType == 'TTA_OSD_REASON' && x.parentLookupValuesId == values[0].lookupValuesId;
                });
                debugger;
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
                    return x.lookupType == 'TTA_OSD_REASON1' && x.parentLookupValuesId == values[0].lookupValuesId;
                });
            }
        };

        /*监听currentList2*/
        // $scope.$watch('cWTable.data', function (newVal, oldVal) {
        //     if (newVal === oldVal || !newVal || !$scope.flag2){
        //         if(!$scope.flag2){
        //             $scope.flag2 = true ;
        //         }
        //         return;
        //     }
        //     $timeout(function () {
        //         $scope.currentList2 = [] ;
        //     })
        // }, true);
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
                    URLAPI.deleteTtaImportCwInfo,

                    {
                        'params': JSON.stringify({
                            "osdId": row.osdId,
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
        $scope.btnExport2 = function () {
            $("#AddModal").modal('show');
        };
        $scope.addInfo = function () {
            var name = 'dataTable' ;
            var params = $scope.AddData ;
            var count = $scope.dataTable.count;
            tableXlsExport(name, params, count);
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

        //新增
        $scope.daoru = function () {
            $('#excelTtaCwChecking').modal('toggle');
            $scope.buttonFlag = true ;
        };


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
                "title": "CW_REPORT_审批" + $scope.cWTable[0].batchCode, //流程标题，修改为当前业务信息
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
        $scope.btnSave = function (data,type){

            $scope.saveAll =  data ;
            httpServer.post(URLAPI.saveOrUpdateTtaOsdMajorAll,
                {params: JSON.stringify({
                        save:$scope.saveAll
                    })},
                function (res) {
                    if (res.status == "S") {
                        if ('create' == type) {
                            $scope.btnCreateEnd();
                        }else {
                            SIEJS.alert('保存', 'success', '确定');
                        }
                        $scope.search();
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

        $scope.btnCreateEnd = function () {
            httpServer.post(
                URLAPI.saveOrUpdateTtaReportProcessHeader,
                {
                    'params': JSON.stringify({"lines": $scope.currentList2, "reprotTypeName": 'OSD'})
                },
                function (res) {
                    if (res.status == 'S') {
                        iframeTabAction('流程单据：' + res.data.batchCode, '/reportProcessHeaderD/' + res.data.reportProcessHeaderId);
                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                },
                function (err) {
                    SIEJS.alert('数据获取失败！', "error");
                }
            );
        };

        /**
         * 拆分
         */
        $scope.windowSplit = function (row){
            if (!$scope.cWTable.data || !$scope.cWTable.data.selectRowData) {
                SIEJS.alert("请选择一行数据进行修改!", "error", "确定");
                return;
            }
            $scope.currentRow = $scope.cWTable.data.selectRowData;

            $scope.splitDataTable =
                [
                    {   noUnconfirmAmount:$scope.currentRow.noUnconfirmAmount,
                        priorVendorCode:$scope.currentRow.priorVendorCode,
                        priorVendorName:$scope.currentRow.priorVendorName,
                        additionRate:$scope.currentRow.additionRate
                    }
                ] ;
            $('#cwSplit').modal('toggle');
        } ;
        $scope.clearVendor = function() {
            $timeout(function () {
                var curRow  = $scope.cWTable.data.selectRowData;
                curRow.priorVendorCode = '';
                curRow.priorVendorName = '';
            }, 0);

        };
        $scope.getVendorRow = function (value) {
            $('#cWReportLineId').modal('toggle') ;
        };
        $scope.selectSupplierLineReturn = function (key, value, currentList) {
            var curRow  = $scope.cWTable.data.selectRowData;
            curRow.priorVendorCode = currentList[0].supplierCode;
            curRow.priorVendorName = currentList[0].supplierName;
            httpServer.post(URLAPI.ttaOsdfindAmountList,
                {params: JSON.stringify({
                        groupCode:curRow.groupCode,
                        year:curRow.osdYear,
                        oldYear:curRow.osdYear-2,
                        supplierCode:curRow.priorVendorCode,
                        deptDesc:curRow.deptDesc,
                        promotionLocation:curRow.promotionLocation,
                        storesNum:curRow.storesNum
                    })},
                function (res) {
                    if (res.status == "S") {
                        var data = res.data;
                        if (data && data.length > 0) {
                            var row = res.data[0];
                            curRow.chargeStandards = row.chargeStandards;//收取方式
                            curRow.chargeMoney = row.chargeMoney;//收费标准
                            curRow.chargeUnitName = row.chargeUnitName;//收取单位
                            curRow.noReceiveAmount = row.noReceiveAmount;//应收金额(不含加成)
                            curRow.receiveAmount = row.receiveAmount;//应收金额(含加成)
                            curRow.unconfirmAmount = row.unconfirmAmount;//采购确认金额(含加成)
                            curRow.noUnconfirmAmount = row.noUnconfirmAmount;//采购确认金额(不含加成)
                            curRow.additionRate  = row.additionRate;//加成比例
                        }
                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                },
                function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                }
            );
        };
        /**
         * 新增一行
         */
        $scope.btnNewRow = function (){
            var splitRow = {
                noUnconfirmAmount:'',
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
            row.additionRate = currentList[0].additionRate;
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
            //采购行动为“收取”或者“折扣收取”的，采购确认金额需大于0
            row.noUnconfirmAmount = row.noUnconfirmAmount === undefined ? 0 : row.noUnconfirmAmount;
            if (row.purchase && (row.purchase === 'A01' || row.purchase === 'A10') && row.noUnconfirmAmount <= 0){
                SIEJS.alert("采购行动为“收取”或者“折扣收取”的，采购确认金额需大于0", "error", "确定");
                return;
            }
            row.exemptionReason  = '';
            row.exemptionReason2 = '';
            var values = lookUpData.filter(function (x) {
                return  x.lookupCode  == currentValue;
            });
            if($scope.lookupCodeParty && values.length >0 ){
                //获取 豁免原因_1
                row.exemptionReasonList = $scope.lookupCodeParty.filter(function (x) {
                    return x.lookupType == 'TTA_OSD_REASON' && x.parentLookupValuesId == values[0].lookupValuesId;
                });
            }

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
                row.unconfirmAmount = (row.noUnconfirmAmount * (100+ row.additionRate *1)/100).toFixed(0);
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
                    return x.lookupType == 'TTA_OSD_REASON1' && x.parentLookupValuesId == values[0].lookupValuesId;
                });
            }

        };

        /**
         * 选择转办人
         */
        $scope.btnBz =  function (){
            $('#lovOsdReportFind').modal('toggle') ;
        };

        /**
         * 选择调整人
         */
        $scope.getNameL =  function (){
            $('#lovOsdReportAdjustByFind').modal('toggle') ;
        };
        /**
         * 转办人确认
         */
        $scope.selectTransferReturn = function (key, value, currentList) {

            if (!$scope.cWTable.data.selectRowData) {
                SIEJS.alert("请选择一行", "error", "确定");
                return;
            }
            var pLins = [];
            pLins.push($scope.cWTable.data.selectRowData);
            httpServer.post(URLAPI.saveOrUpdateTtaOsdMajorTransferALL,
                {
                    params: JSON.stringify({
                        lines: pLins,
                        personId: currentList[0].userId
                    })
                },
                function (res) {
                    if (res.status == "S") {
                        SIEJS.alert('转办成功', 'success', '确定');
                        $scope.cWTable.getData();
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
         * 调整人确认
         */
        $scope.selectdjustByReturn = function (key, value, currentList) {
            var curRow  = $scope.cWTable.data.selectRowData;
            curRow.adjustBy = currentList[0].userId;
            curRow.adjustByName = currentList[0].userFullName;
        };
        /**
         * 供应商拆分
         */
        $scope.saveWindow = function () {
            httpServer.post(URLAPI.saveOrUpdateTtaOsdMajorSplitALL,
                {
                    params: JSON.stringify({
                        save: $scope.splitDataTable,
                        currentRow: $scope.currentRow
                    })
                },
                function (res) {
                    if (res.status == "S") {
                        $('#cwSplit').modal('toggle');
                        SIEJS.alert('拆分成功', 'success', '确定');
                        $scope.search();
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

            var index = $scope.splitDataTable.selectRow;

            SIEJS.confirm('删除', '是否确定删除？', '确定', function () {

                $scope.splitDataTable.splice(index, 1);             //新增列没有ID直接删除
                SIEJS.alert("操作成功!", "已成功删除数据！", "success");
                $scope.$apply();

            })

        };


        /*************************** 上传 start ****************/
        $scope.callback = function (row, index, flag) {
            if ('upload' == flag) {
                var obj = document.getElementById('cWReportUploadFile');
                obj.outerHTML = obj.outerHTML;
                $('#addFileUpload').modal('toggle');
                $scope.cWUploadRow = row;
            }

        };
        //上传附件
        $scope.uploadFile = function (form, params) {

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
            var sideAgrtHId = $scope.cWUploadRow.osdId;
            if (sideAgrtHId == undefined || sideAgrtHId == null) {
                SIEJS.alert("头信息为空,上传失败", 'error', '确定');
            }

            fd.append('sideAgrtHId', sideAgrtHId);
            fd.append('file', file);
            fd.append("fileName", fileName);
            fd.append("functionId", "tta_osd_monthly_checking_report");

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
                    $scope.ttaOsdfileDataTable.search(1);
                    SIEJS.alert(response.msg, 'success', '确定');

                } else {
                    loading.hide();
                    SIEJS.alert(response.msg, 'error', '确定');
                }
            }).error(function (response) {
                loading.hide();
                SIEJS.alert("上传失败", 'error', '确定');
            });
        };
        $scope.fileInfoFunc = function (row) {
            $scope.fileDataParams.functionId = 'tta_osd_monthly_checking_report';
            $scope.fileDataParams.businessId = row.osdId;
           $scope.ttaOsdfileDataTable.search(1);
            $('#excelUp2').modal('toggle');
            //console.log(msg);
            // if (row.fileId == undefined || row.fileId == null) {
            //     SIEJS.alert("当前没有选中一行数据,不能下载", 'warning', '确定');
            //     return;
            // }
            // var url = URLAPI.ttaSideAgrtHeaderDownLoad + '?fileId=' + row.fileId;
            // window.open(url, [""], [""]);//避开因同源策略而造成的拦截
        };

        //删除附件
        $scope.delUploadFile = function () {
            var item = $scope.ttaOsdfileDataTable.selectRow;
            SIEJS.confirm('提示', '确定要删除所选的信息吗？', '确定', function () {
                var p = {
                    fileId: item.fileId //文件id
                };
                httpServer.post(URLAPI.ttaSideAgrtHeaderFileDelete, {
                    'params': JSON.stringify(p)
                }, function (res) {
                    if (res.status == 'S') {

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
        //下载附件事件
        $scope.downFileEvent = function (m) {
            //console.log(msg);
            if (m.fileId == undefined || m.fileId == null){
                SIEJS.alert("当前没有选中一行数据,不能下载",'warning','确定');
                return;
            }
            var url = URLAPI.ttaSideAgrtHeaderDownLoad + '?fileId=' + m.fileId;
            window.open(url, [""], [""]);//避开因同源策略而造成的拦截
        };

        /***************************上传end ****************/
        /**
         * 多选
         * @param item
         * @param e
         * @param t
         */
        $scope.check = function (item, e, t) {
            $scope.flag2 = false;
            var checked = e.currentTarget.checked; // 当前选中状态
            var currentIsPush = true;
            var currentIndex = -1;
            for (var i = 0; i < $scope.currentList2.length; i++) {
                if (item.osdId === $scope.currentList2[i].osdId) {
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
            $scope.flag2 = false;
            if (e.target.checked) {// 全选
                $scope.selectedAll = true;
                for (var n in $scope.cWTable.data) {
                    var row = $scope.cWTable.data[n];
                    if (/*!$scope.setCheckedDisabled(row)*/ true) { // 非被禁用
                        row.checked = true;
                        var isPush = false;
                        for (var m in $scope.currentList2) {
                            var list = $scope.currentList2[m];
                            if (row.osdId === list.osdId) {
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


        $scope.btnCreate = function (form) {
            if ($scope.currentList2.length == 0) {
                SIEJS.alert('当前未选中请先选择数据', "error");
                return;
            }
            var values = $scope.currentList2.filter(function (x) {
                return x.processCode;
            });
            if (values.length > 0) {
                SIEJS.alert('当前选中包含已经生成流程单据的【' + values[0].content + '】', "error");
                return;
            }

            for (var i = 0; i < $scope.currentList2.length; i++) {
                 var row = $scope.currentList2[i];
                row.noUnconfirmAmount = row.noUnconfirmAmount === undefined ? 0 : row.noUnconfirmAmount;
                //采购行动为“收取”或者“折扣收取”的，采购确认金额需大于0
                if (row.purchase && (row.purchase === 'A01' || row.purchase === 'A10') && row.noUnconfirmAmount <= 0) {
                    SIEJS.alert("您选择的产品编号为" + row.itemCode + "那一行数据的采购行动为“收取”或者“折扣收取”，采购确认金额需大于0", "error", "确定");
                    return;
                }
            }

            if(!validateForm(form)){
                return;
            }
            SIEJS.confirm('您确认要生成吗？', '', '确定', function () {
                $scope.btnSaveChecked('create');
            });

        };

        //跳转
        $scope.getRow = function (row){
            iframeTabAction('流程单据：'+ row.processCode,'/reportProcessHeaderD/' + row.processReId);
        };

        $scope.btnSaveChecked = function (type) {
            if($scope.currentList2.length == 0){
                SIEJS.alert('当前未选中请先选择数据', "error");
                return ;
            }
            $scope.btnSave($scope.currentList2,type);
        };

        $scope.btnSaveCur = function (type) {
            $scope.btnSave($scope.cWTable.data,type);
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
                        return  x.lookupType == 'TTA_OSD_ACTION'  || x.lookupType =='TTA_OSD_REASON' || x.lookupType =='TTA_OSD_REASON1';
                    });

                    var actionParams = totalParams.filter(function (x) {
                        return x.lookupType == 'TTA_OSD_ACTION' ;
                    });

                    var reasonParams = totalParams.filter(function (x) {
                        return x.lookupType == 'TTA_OSD_REASON' ;
                    });

                    var reasonOneParams = totalParams.filter(function (x) {
                        return x.lookupType == 'TTA_OSD_REASON1' ;
                    });
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

        /**
         * 查询Summary
         */
        $scope.findcWSummary = function () {
            var totalObject = {
                'groupDesc': 'total',
                'chargeableBasedOnRateCard': 0,
                'needBuyerFeedback': 0,
                'ytdActualCharge': 0,
                'planToCharge': 0,
                'toBeWaive': 0
            };
            httpServer.post(URLAPI.findTtaCwSummaryList, {
                'params': JSON.stringify({'batchCode': $scope.cWTable.data[0].batchCode})
            }, function (res) {
                if (res.status == 'S') {
                    for (var i = 0; i < res.data.length; i++) {
                        totalObject.chargeableBasedOnRateCard += Number(res.data[i].chargeableBasedOnRateCard);
                        totalObject.needBuyerFeedback += Number(res.data[i].needBuyerFeedback);
                        totalObject.ytdActualCharge += Number(res.data[i].ytdActualCharge);
                        totalObject.planToCharge += Number(res.data[i].planToCharge);
                        totalObject.toBeWaive += Number(res.data[i].toBeWaive);

                    }
                    res.data.push(totalObject);
                    $scope.ttacWSummaryTable = res.data;
                } else {
                    SIEJS.alert(res.msg, "error", "确定");
                }
            });
        };

        /**
         * 导出选择批次号
         */
     /*   $scope.btnExportTemplate = function (){
            $('#findReportExportOsdId').modal('toggle');
        };*/

        $scope.btnExportBatch = function (){
            $('#findReportExportOsdId').modal('toggle');
        };

        /**
         * 导出模板
         */
       /* $scope.selectReportExportOsdReturn = function (key, value, currentList) {
            $scope.aboiParams = angular.copy($scope.params);
            $scope.aboiParams.batchCode = '';
            angular.forEach(currentList, function (item, index, array) {
                //$scope.contractParams.salesArea = $scope.contractParams.salesArea+item.meaning+",";
                $scope.aboiParams.batchCode += item.batchId + ",";
            });
            $scope.aboiParams.batchCode = $scope.aboiParams.batchCode.substring(0, $scope.aboiParams.batchCode.lastIndexOf(','));
            var name = 'ABOIdDataTable';
            var params = $scope.aboiParams;
            var count = $scope.ABOIdDataTable.count;
            tableXlsExport(name, params, count);
        };*/


        /**
         * 批次导出
         */
        $scope.selectReportExportOsdReturn = function (key, value, currentList) {
            $scope.aboiParams = angular.copy($scope.params);
            $scope.aboiParams.batchCode = '';
            angular.forEach(currentList, function (item, index, array) {
                //$scope.contractParams.salesArea = $scope.contractParams.salesArea+item.meaning+",";
                $scope.aboiParams.batchCode += item.batchId + ",";
            });
            $scope.aboiParams.batchCode = $scope.aboiParams.batchCode.substring(0, $scope.aboiParams.batchCode.lastIndexOf(','));
            var name = 'dataTable';
            var params = $scope.aboiParams;
            var count = $scope.dataTable.count;
            tableXlsExport(name, params, count);
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

        //清除值
        $scope.paramsCancel = function (value) {
            $scope.params.deptCode = '';
            $scope.params.deptDesc = '';
        };
        //勾选部门
        $scope.getDeptCode = function (e) {
            $('#ttaOsdDeptCodeId').modal('toggle')
        };
        $scope.selectDeptFeeAReturn = function (key, value, currentList) {
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
        //新增
        $scope.btnExportIn = function () {
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
            $http.post(URLAPI.updateImportOsdInfo, fd, {
                transformRequest: angular.identity,
                headers: {
                    'Certificate': sessionStorage[appName + '_certificate'] || localStorage[appName + '_certificate'] || 'nothing',
                    'Content-Type': undefined
                }
            }).success(function (response) {
                saafLoading.hide();
                $('#excelUp').modal('toggle');
                if (response.status == 'S') {
                    $scope.search();
                    SIEJS.alert(response.msg, 'success', '确定');
                } else {
                    SIEJS.alert("导入失败!", 'error', '确定');
                }
            }).error(function(response) {
                saafLoading.hide();
                SIEJS.alert("导入失败!", 'error', '确定');
            });
        };

        $scope.countDifferenceAmt = function (row) {
            row.noUnconfirmAmount = $scope.formatAmount(row.noUnconfirmAmount);
            row.receiveAmount = $scope.formatAmount(row.receiveAmount);
            row.unconfirmAmount = (row.collect == 'TTA' ? (row.noUnconfirmAmount * (100+ (row.additionRate?row.additionRate:0) *1)/100).toFixed(0) : row.noUnconfirmAmount );
            //如“采购确认收取金额（含加成）”-“应收金额（含加成）”大于0，为0，否则等于“采购确认收取金额（含加成）”-“应收金额（含加成）”
            row.difference = row.unconfirmAmount * 1 - row.receiveAmount*1 < 0 ? (row.unconfirmAmount * 1 - row.receiveAmount*1).toFixed(0) : 0;
        };

        //失去焦点事件
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
