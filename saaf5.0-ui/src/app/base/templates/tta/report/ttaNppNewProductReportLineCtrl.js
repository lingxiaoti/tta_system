'use strict';
define(['app', 'pinyin', 'ztree','XLSX','fixedReport'], function (app, pinyin, ztree,XLSX,fixedReport) {
    app.controller('ttaNppNewProductReportLineCtrl', function ($scope, $filter, $location,$timeout, $rootScope, $state, $stateParams, SIEJS, httpServer,validateForm, URLAPI, iframeTabAction, setNewRow,$http,lookupCode) {
        $scope.buttonFlag = true ;
        $scope.userType = JSON.parse(sessionStorage[appName + '_successLoginInfo']).userType;//用户类型:45-->BIC
        $scope.dataTable = {};
        $scope.batchId = $stateParams.batchId;
        $scope.params = {"batchCode":$scope.batchId} ;
        $scope.currentList2 = [] ;
        $scope.ttaNppfileDataTable ={};
        $scope.fileDataParams = {};
        $scope.currentPurcahseReply = {};
        //拆分数据table
        $scope.splitDataTable = [] ;
        $scope.firstText = "请选择";
        $scope.currentExemptionReasonList = [];
        $scope.currentExemptionReasonList2 = [];

        /*监听currentList2*/
        $scope.$watch('dataTable.data', function (newVal, oldVal) {
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

        $scope.btnFreeze = function (){
            var fixedTable1 = fixReportTable(document.getElementById('fixedId'),6);
        };

        $scope.search = function (){
            $scope.dataTable.getData() ;
            //$scope.getHeight();
        };

        //获取字典信息
        $scope.lookupCodeParty = window.parent.saafrootScope.lookupCode || $rootScope.lookupCode;
        if (!$scope.lookupCodeParty) {
            lookupCode(function (res) {
                $scope.lookupCodeParty = $rootScope.lookupCode = res.data;
            });
        }


        ///////////////////审批 start/////////////////////////
        $scope.submitApproval = function (form) {
            if($scope.dataTable.length == 0){
                SIEJS.alert('当前页面没有数据,不允许提交审批', 'error', '确定');
                return;
            }else if($scope.dataTable[0].headerStatus != 'DS01'){
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
                "businessKey": $scope.dataTable[0].id, //单据ID
                "title": "NPP_REPORT_审批" + $scope.dataTable[0].batchCode, //流程标题，修改为当前业务信息
                "positionId": 0,
                "orgId": 0,
                "userType": "20",
                "billNo": $scope.dataTable[0].batchCode
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

        ///////////////////审批 end/////////////////////////
        //保存(勾选)
        $scope.btnSaveChecked = function(type){
            if($scope.currentList2.length == 0){
                SIEJS.alert('当前未选中请先选择数据', "error");
                return ;
            }
            $scope.btnSave($scope.currentList2,type);
        };

        //保存(当前页)
        $scope.btnSaveCur = function (type) {
            $scope.btnSave($scope.dataTable.data,type);
        };

        /**
         * 保存行
         */
        $scope.btnSave = function (data,type){
            $scope.saveAll = data;
            httpServer.post(URLAPI.saveOrUpdateALLTtaNpp,
                {params: JSON.stringify({
                        save:$scope.saveAll
                    })},
                function (res) {
                    if (res.status == "S") {
                        if ('create' === type) {
                            $scope.btnCreateEnd();
                        }else {
                            SIEJS.alert('保存', 'success', '确定');
                        }
                        $scope.dataTable.getData() ;
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

        $scope.btnCreateEnd = function(){
            httpServer.post(
                URLAPI.saveOrUpdateTtaReportProcessHeader,
                {
                    'params': JSON.stringify({"lines": $scope.currentList2,"reprotTypeName":'NPP'})
                },
                function (res) {
                    if (res.status == 'S') {
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
        };

        $scope.selectGroupReturn = function (key, value, currentList) {
            //$scope.params.groupCode = currentList[0].groupCode;
            //$scope.params.groupDesc = currentList[0].groupDesc;
            if (currentList && currentList.length > 0) {
                $scope.params.groupCode = '';
                $scope.params.groupDesc = '';
                for (var idx = 0; idx < currentList.length; idx ++) {
                    $scope.params.groupCode += currentList[idx].groupCode;
                    $scope.params.groupDesc += currentList[idx].groupDesc;
                    if (currentList.length != idx + 1) {
                        $scope.params.groupCode +=  ",";
                        $scope.params.groupDesc +=  ",";
                    }
                }
            }
        };

        $scope.selectDeptReturn = function (key, value, currentList) {
            $scope.params.deptCode = currentList[0].departmentCode;
            $scope.params.deptDesc = currentList[0].departmentName;
        };

        $scope.selectBrandReturn = function (key, value, currentList) {
            $scope.params.brandCode = currentList[0].brandCode;
            $scope.params.brandCn = currentList[0].brandCn;
        };

        $scope.selectItemLovReturn = function(key, value, currentList) {
            $scope.params.itemCode = currentList[0].itemNbr;
            $scope.params.itemDescCn = currentList[0].itemDescCn;
        };

        /**
         * 选取豁免原因_1
         * @param index
         * @param currentValue
         * @param lookUpData
         */
        $scope.purchaseFun = function (index,currentValue,lookUpData){
            var row = $scope.dataTable.data[index] ;
            //差异金额小于0并且采购行动为收取
            if (row.difference < 0 && row.purchaseAct === 'A01') {
                SIEJS.alert("因“差异（不含加成）”金额小于零,采购行动不能选“收取”", "error", "确定");
                return;
            }
            row.unconfirmAmount = row.unconfirmAmount === undefined ? 0 : row.unconfirmAmount;
            //采购行动为“收取”或者“折扣收取”的，采购确认金额需大于0
            if (row.purchaseAct && (row.purchaseAct === 'A01' || row.purchaseAct === 'A10') && row.unconfirmAmount <= 0) {
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
                    return x.lookupType == 'TTA_NPP_REASON' && x.parentLookupValuesId == values[0].lookupValuesId;
                });
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
            if($scope.lookupCodeParty && values.length >0){
                //获取 豁免原因_2
                row.exemptionReasonList2 = $scope.lookupCodeParty.filter(function (x) {
                    return x.lookupType == 'TTA_NPP_REASON1' && x.parentLookupValuesId == values[0].lookupValuesId;
                });
            }

        };

        $scope.afterGetData =function () {
            for(var i = 0; i < $scope.dataTable.data.length; i ++) {
                //获取采购行动
                var currentValue = $scope.dataTable.data[i].purchaseAct ;
                var lookUpData = $scope.lookupCodeParty.filter(function (x) {
                    return currentValue && x.lookupType == 'TTA_NPP_ACTION' && x.lookupCode == currentValue;
                });
                //获取豁免原因1
                if($scope.lookupCodeParty && lookUpData.length >0 ){
                    //获取 豁免原因_1
                    $scope.dataTable.data[i].exemptionReasonList = $scope.lookupCodeParty.filter(function (x) {
                        return x.lookupType == 'TTA_NPP_REASON' && x.parentLookupValuesId == lookUpData[0].lookupValuesId;
                    });
                }

                //获取 豁免原因_2
                var currentValue2 = $scope.dataTable.data[i].exemptionReason ;
                var values2  = $scope.lookupCodeParty.filter(function (x) {
                    return currentValue2 && x.lookupType == 'TTA_NPP_REASON' &&  x.lookupCode  == currentValue2;
                });
                if($scope.lookupCodeParty && values2.length >0){
                    $scope.dataTable.data[i].exemptionReasonList2 = $scope.lookupCodeParty.filter(function (x) {
                        return x.lookupType == 'TTA_NPP_REASON1' && x.parentLookupValuesId == values2[0].lookupValuesId;
                    });
                }
            }

            //$scope.getHeight();
        };

        $scope.btnDel = function () {
            var row = $scope.dataTable.selectRow;
            SIEJS.confirm('您确认要删除吗？', '', '确定', function () {
                httpServer.post(
                    URLAPI.deleteImportPogInfo,

                    {
                        'params': JSON.stringify({
                            "pogSpaceLineId": row.pogSpaceLineId,
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

        /////////////////////////上传start////////////////////////
        $scope.callback = function (row,index,flag){
            if('upload' == flag){
                var obj = document.getElementById('nPPReportUploadFile') ;
                obj.outerHTML=obj.outerHTML;
                $('#addFileUpload').modal('toggle');
                $scope.nppUploadRow = row ;
            }

        };

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
            var file = document.getElementById('nPPReportUploadFile').files[0];
            if (!file) {
                SIEJS.alert("请选择上传文件", 'error', '确定');
                return;
            }
            var fileName = file.name;
            var nppId = $scope.nppUploadRow.nppId;
            if (nppId == undefined || nppId == null){
                SIEJS.alert("未选中行数据,上传失败", 'error', '确定');
            }

            fd.append('sideAgrtHId', nppId);
            fd.append('file', file);
            fd.append("fileName", fileName);
            fd.append("functionId", "tta_npp_checking_report");

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
                    $scope.ttaNppfileDataTable.search(1);
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

        $scope.fileInfoFunc = function (row) {
            $scope.fileDataParams.functionId = 'tta_npp_checking_report';
            $scope.fileDataParams.businessId = row.nppId;
            $scope.ttaNppfileDataTable.search(1);
            $('#excelUp2').modal('toggle');
        };

        //下载附件事件
        $scope.downFileEvent = function (row) {
            if (row.fileId == undefined || row.fileId == null){
                SIEJS.alert("当前没有选中一行数据,不能下载",'warning','确定');
                return;
            }
            var url = URLAPI.ttaSideAgrtHeaderDownLoad + '?fileId=' + row.fileId;
            window.open(url, [""], [""]);//避开因同源策略而造成的拦截
        };

        //删除附件
        $scope.delUploadFile = function () {
            var selectRow = $scope.ttaNppfileDataTable.selectRow;
            SIEJS.confirm('提示', '确定要删除所选的信息吗？', '确定', function () {
                var p = {
                    fileId: selectRow.fileId //文件id
                };
                httpServer.post(URLAPI.ttaSideAgrtHeaderFileDelete, {
                    'params': JSON.stringify(p)
                }, function (res) {
                    if (res.status == 'S') {
                        $scope.ttaNppfileDataTable.search(1);
                        SIEJS.alert(res.msg, 'success', '确定');
                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                }, function (err) {
                    SIEJS.alert("删除失败! " + err.msg, "error", "确定");
                });

            });
        };

        /////////////////////////上传end////////////////////////

        /////////////////////拆分逻辑操作start////////////////////////////////////
        /**
         * 拆分弹窗
         */
        $scope.nPPwindowSplit = function () {
            if (!$scope.dataTable.data || !$scope.dataTable.data.selectRowData) {
                SIEJS.alert("请选择一行数据进行修改!", "error", "确定");
                return;
            }
            $scope.currentRow = $scope.dataTable.data.selectRowData;

            $scope.splitDataTable =
                [
                    {   unconfirmAmount:$scope.currentRow.unconfirmAmount,//需采购确认的金额(不含加成)
                        vendorNbr:$scope.currentRow.vendorNbr,//供应商编码
                        vendorName:$scope.currentRow.vendorName//供应商名称
                    }
                ] ;
            $('#nPPSplit').modal('toggle');
        };

        /**
         * 新增一行
         */
        $scope.btnNewRow = function (){
            var splitRow = {
                receiveAmount:'',//应收金额
                vendorNbr:'',
                vendorName:''

            };
            $scope.splitDataTable.push(splitRow);
        };

        /**
         * 选择供应商
         * @param value
         */
        $scope.getVendorL = function (value) {

            $('#nPPReportListId').modal('toggle') ;
        };

        /**
         * 确认选择供应商
         * @param key
         * @param value
         * @param currentList
         */
        $scope.selectSupplierReturn = function (key, value, currentList) {
            var row = $scope.splitDataTable[$scope.splitDataTable.selectRow];
            row.vendorNbr = currentList[0].supplierCode;
            row.vendorName = currentList[0].supplierName;
        };

        //删除行
        $scope.btnDelRow = function () {

            var index=$scope.splitDataTable.selectRow;

            SIEJS.confirm('删除', '是否确定删除？', '确定', function () {

                $scope.splitDataTable.splice(index, 1);             //新增列没有ID直接删除
                SIEJS.alert("操作成功!", "已成功删除数据！", "success");
                $scope.$apply();

            })

        };

        /**
         * 供应商拆分
         */
        $scope.saveWindow = function (){
            httpServer.post(URLAPI.saveOrUpdateSplitALLTtaNpp,
                {params: JSON.stringify({
                        save:$scope.splitDataTable,
                        currentRow:$scope.currentRow
                    })},
                function (res) {
                    if (res.status == "S") {
                        $('#nPPSplit').modal('toggle');
                        SIEJS.alert('拆分成功', 'success', '确定');
                        $scope.dataTable.getData() ;
                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                },
                function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                    $("#saveButton3").attr("disabled", true);
                }
            );
        };

        //////////////////////拆分逻辑操作 end///////////////////////////////////////

        /**
         * 选择转办人
         */
        $scope.btnBz =  function (){
            $('#lovnPPReportFind').modal('toggle') ;
        };

        /**
         * 转办人确认
         */
        $scope.selectTransferReturn = function (key, value, currentList){
            /*if (!$scope.dataTable.data.selectRowData){
                SIEJS.alert("请选择一行", "error", "确定");
                return ;
            }*/
            if ($scope.currentList2 && $scope.currentList2.length === 0) {
                SIEJS.alert("请先选中数据", "error", "确定");
                return ;
            }

            var pLins = [];
            //pLins.push($scope.dataTable.data.selectRowData) ;
            pLins.push($scope.currentList2) ;
            httpServer.post(URLAPI.saveOrUpdateTransferALLTtaNpp,
                {params: JSON.stringify({
                        lines:pLins,
                        personId:currentList[0].userId
                    })},
                function (res) {
                    if (res.status == "S") {
                        SIEJS.alert('转办成功', 'success', '确定');
                        $scope.dataTable.getData() ;
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
         * 选择调整人
         */
        $scope.getNameL =  function (){
            $('#lovNPPReportAdjustByFind').modal('toggle') ;
        };

        /**
         * 调整人确认
         */
        $scope.selectdjustByReturn = function (key, value, currentList) {
            var curRow  = $scope.dataTable.data.selectRowData;
            curRow.adjustBy = currentList[0].userId;
            curRow.adjustByName = currentList[0].userFullName;
        };

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
                if (item.nppId === $scope.currentList2[i].nppId) {
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
                $scope.currentList2.splice(currentIndex, 1); // 删除当前选择的数据　－－－－－－－－－－－－－－－－－－－－－－－－
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
                for (var n in $scope.dataTable.data) {
                    var row = $scope.dataTable.data[n];
                    if (true) { // 非被禁用
                        row.checked = true;
                        var isPush = false;
                        for (var m in $scope.currentList2) {
                            var list = $scope.currentList2[m];
                            if (row.nppId === list.nppId) {
                                isPush = true;
                                break;
                            }
                        }
                        if (!isPush) $scope.currentList2.push(row);
                    }

                }
            } else {// 返选
                $scope.selectedAll = false;
                for (var n in $scope.dataTable.data) {
                    var row = $scope.dataTable.data[n];
                    row.checked = false;
                    var index = $scope.currentList2.indexOf(row);
                    $scope.currentList2.splice(index--, 1);// for 循环使用 splice 删除数组请 把下标 --
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
                if ((rect.height/rectParent.height) >= 0.9){
                    divFix.style.height = 90+'%' ;
                }else{
                    divFix.style.height = 'auto';
                }
                $scope.btnFreeze();
            }, 200)
        };

        //跳转
        $scope.getRow = function (row){
            iframeTabAction('流程单据：'+ row.processCode,'/reportProcessHeaderD/' + row.processReId);
        };

        $scope.btnCreate = function (form){
            if($scope.currentList2.length == 0){
                SIEJS.alert('当前未选中请先选择数据', "error");
                return ;
            }
            var values = $scope.currentList2.filter(function (x) {
                return  x.processCode;
            });
            if(values.length > 0){
                SIEJS.alert('当前选中包含已经生成流程单据的【'+values[0].activity+'】', "error");
                return ;
            }
            for (var i = 0; i < $scope.currentList2.length; i++) {
                var row = $scope.currentList2[i];
                row.unconfirmAmount = row.unconfirmAmount === undefined ? 0 : row.unconfirmAmount;
                //采购行动为“收取”或者“折扣收取”的，采购确认金额需大于0
                if (row.purchaseAct && (row.purchaseAct === 'A01' || row.purchaseAct === 'A10') && row.unconfirmAmount <= 0) {
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

        $scope.clearVendor = function() {
            $timeout(function () {
                var curRow  = $scope.dataTable.data.selectRowData;
                curRow.priorVendorCode = '';
                curRow.priorVendorName = '';
            }, 0);
        };

        $scope.getVendorRow = function (value) {
            $('#nPPReportLineId').modal('toggle') ;
        };

        $scope.selectSupplierLineReturn = function (key, value, currentList) {
            var curRow  = $scope.dataTable.data.selectRowData;
            curRow.priorVendorCode = currentList[0].supplierCode;
            curRow.priorVendorName = currentList[0].supplierName;

            httpServer.post(URLAPI.findChangeVenderByNpp,
                {params: JSON.stringify(curRow)},
                function (res) {
                    if (res.status == "S") {
                        //$scope.splitDataTable[$scope.splitDataTable.selectRow] = res.data;
                        var resultData =  res.data;
                        curRow.receiveAmount = resultData.receiveAmount;//应收金额(不含加成)
                        curRow.receiveAmountAdd = resultData.receiveAmountAdd;//应收金额(含加成)
                        curRow.chargeMethod = resultData.chargeMethod;//收费方式
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

        //采购批量回复
        $scope.CGPLHF = function () {
            if ($scope.currentList2 && $scope.currentList2.length == 0) {
                SIEJS.alert('当前未选中数据请先选择数据', "error");
                return;
            }
            $('#nppPurchaseReplyModal').modal('toggle');
        };

        $scope.saveNppPurchaseReply = function (form) {
            if (!validateForm(form)) {
                return;
            }

            $scope.currentList2 = [];
            for (var n in $scope.dataTable.data) {
                var row = $scope.dataTable.data[n];
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
                row.purchaseAct = $scope.currentPurcahseReply.purchaseAct;
                row.exemptionReason = $scope.currentPurcahseReply.exemptionReason;
                row.exemptionReason2 = $scope.currentPurcahseReply.exemptionReason2;
            }

            for(var i = 0; i < $scope.currentList2.length; i ++) {
                //获取采购行动
                var currentValue = $scope.dataTable.data[i].purchaseAct ;
                var lookUpData = $scope.lookupCodeParty.filter(function (x) {
                    return currentValue && x.lookupType == 'TTA_NPP_ACTION' && x.lookupCode == currentValue;
                });
                //获取豁免原因1
                if($scope.lookupCodeParty && lookUpData.length >0 ){
                    //获取 豁免原因_1
                    $scope.dataTable.data[i].exemptionReasonList = $scope.lookupCodeParty.filter(function (x) {
                        return x.lookupType == 'TTA_NPP_REASON' && x.parentLookupValuesId == lookUpData[0].lookupValuesId;
                    });
                }

                //获取 豁免原因_2
                var currentValue2 = $scope.dataTable.data[i].exemptionReason ;
                var values2  = $scope.lookupCodeParty.filter(function (x) {
                    return currentValue2 && x.lookupType == 'TTA_NPP_REASON' &&  x.lookupCode  == currentValue2;
                });
                if($scope.lookupCodeParty && values2.length >0){
                    $scope.dataTable.data[i].exemptionReasonList2 = $scope.lookupCodeParty.filter(function (x) {
                        return x.lookupType == 'TTA_NPP_REASON1' && x.parentLookupValuesId == values2[0].lookupValuesId;
                    });
                }
            }
            $('#nppPurchaseReplyModal').modal('toggle');
        };

        //选择采购行动
        $scope.purchaseReplyFun = function (key) {
            console.log(key);
            if (!key) return;
            var values = $scope.lookupCodeParty.filter(function (x) {
                return  x.lookupCode  == key  && x.lookupType == 'TTA_NPP_ACTION';
            });
            if($scope.lookupCodeParty){
                //获取 豁免原因_1
                $scope.currentExemptionReasonList = $scope.lookupCodeParty.filter(function (x) {
                    return x.lookupType == 'TTA_NPP_REASON' && x.parentLookupValuesId == values[0].lookupValuesId;
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
                    return x.lookupType == 'TTA_NPP_REASON1' && x.parentLookupValuesId == values[0].lookupValuesId;
                });
            }
        };

        $scope.countDifferenceAmt = function (row) {
            row.unconfirmAmount = $scope.formatAmount(row.unconfirmAmount);
            row.receiveAmountAdd = $scope.formatAmount(row.receiveAmountAdd);
            row.unconfirmAmountA = (row.collectSelect == 'TTA' ? (row.unconfirmAmount * (100+ (row.additionRate ? row.additionRate : 0) *1)/100).toFixed(0) : row.unconfirmAmount );
            //如“采购确认收取金额（含加成）”-“应收金额（含加成）”大于0，为0，否则等于“采购确认收取金额（含加成）”-“应收金额（含加成）”
            row.difference = row.unconfirmAmountA * 1 - row.receiveAmountAdd*1 < 0 ? (row.unconfirmAmountA * 1 - row.receiveAmountAdd*1).toFixed(0) : 0;
        };

        $scope.blurEvent = function(row){
            row.unconfirmAmount = row.unconfirmAmount === undefined ? 0 : row.unconfirmAmount;
            //采购行动为“收取”或者“折扣收取”的，采购确认金额需大于0
            if (row.purchaseAct && (row.purchaseAct === 'A01' || row.purchaseAct === 'A10') && row.unconfirmAmount <= 0) {
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
