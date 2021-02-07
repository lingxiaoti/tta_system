'use strict';
define(['app', 'pinyin', 'ztree','XLSX','fixedReport'], function (app, pinyin, ztree,XLSX, fixedReport) {
    app.controller('ttaProcessOSDCheckingReportCtrl', function ($scope, $filter, $location, $rootScope, $state, $stateParams, SIEJS, httpServer,validateForm, URLAPI, iframeTabAction, setNewRow,$http,$timeout,lookupCode,saafLoading,
                                                                                                   processBatchComplete, processGetTaskUrl, processTaskComplete, processEntrustApproval) {
        $scope.buttonFlag = true ;
        $scope.userType = JSON.parse(sessionStorage[appName + '_successLoginInfo']).userType;
        $scope.dataSummary = {};
        $scope.batchId = $stateParams.batchId;
        $scope.params = {"batchCode":$scope.batchId, flag:"process"};
        $scope.splitDataTable = [] ;
        $scope.currentList2 = [] ;
        $scope.firstText = "请选择";
        $scope.btnFreeze = function (){
            var fixedTable1 = fixReportTable(document.getElementById('fixedId'),6);
        };

        $scope.tab = {
            active: 0,
            nav: ['审批汇总', '审批明细'],
            click: function (i) {
                $scope.tab.active = i
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

        $scope.search = function (){
            $scope.dataSummary.search();
            $timeout(function () {
             //   $scope.osdTable.search(1);
            });
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
        $scope.$watch('osdTable.data', function (newVal, oldVal) {
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
            if($scope.osdTable.length == 0){
                SIEJS.alert('当前页面没有数据,不允许提交审批', 'error', '确定');
                return;
            }else if($scope.osdTable[0].headerStatus != 'DS01'){
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
                "businessKey": $scope.osdTable[0].id, //单据ID
                "title": "DM_REPORT_审批" + $scope.osdTable[0].batchCode, //流程标题，修改为当前业务信息
                "positionId": 0,
                "orgId": 0,
                "userType": "20",
                "billNo": $scope.osdTable[0].batchCode
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
            if(!validateForm(form)){
                return;
            }
            $scope.saveAll =  $scope.osdTable.data ;
            httpServer.post(URLAPI.saveOrUpdateALLTtaDm,
                {params: JSON.stringify({
                        save:$scope.saveAll
                    })},
                function (res) {
                    if (res.status == "S") {
                        SIEJS.alert('保存', 'success', '确定');
                        $scope.osdTable.getData() ;
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
        	 $("#osdTableId").freeze({ height:500,width:1416, rows: 2, cols: 8 });

            if (!$scope.osdTable.data || !$scope.osdTable.data.selectRowData) {
                SIEJS.alert("请选择一行数据进行修改!", "error", "确定");
                return;
            }
            $scope.currentRow = $scope.osdTable.data.selectRowData;
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
         * 选取豁免原因_1
         * @param index
         * @param currentValue
         * @param lookUpData
         */
        $scope.purchaseFun = function (index,currentValue,lookUpData){
            var row = $scope.osdTable.data[index] ;
            var values = lookUpData.filter(function (x) {
                return  x.lookupCode  == currentValue;
            });
            if($scope.lookupCodeParty){
                //获取 豁免原因_1
                row.exemptionReasonList = $scope.lookupCodeParty.filter(function (x) {
                    return x.lookupType == 'TTA_DM_REASON' && x.parentLookupValuesId == values[0].lookupValuesId;
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
            if($scope.lookupCodeParty){
                //获取 豁免原因_2
                row.exemptionReasonList2 = $scope.lookupCodeParty.filter(function (x) {
                    return x.lookupType == 'TTA_DM_REASON1' && x.parentLookupValuesId == values[0].lookupValuesId;
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

            if (!$scope.osdTable.data.selectRowData){
                SIEJS.alert("请选择一行", "error", "确定");
                return ;
            }
            var pLins = [];
            pLins.push($scope.osdTable.data.selectRowData) ;
            httpServer.post(URLAPI.saveOrUpdateTransferALLTtaDm,
                {params: JSON.stringify({
                        lines:pLins,
                        personId:currentList[0].userId
                    })},
                function (res) {
                    if (res.status == "S") {
                        SIEJS.alert('转办成功', 'success', '确定');
                        $scope.osdTable.getData() ;
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
                        $scope.osdTable.getData() ;
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
        $scope.callback = function (row,index,flag){
            if('upload' == flag){
                var obj = document.getElementById('cWReportUploadFile') ;
                obj.outerHTML=obj.outerHTML;
                $('#addFileUpload').modal('toggle');
                $scope.cWUploadRow = row ;
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
            var file = document.getElementById('cWReportUploadFile').files[0];
            if (!file) {
                SIEJS.alert("请选择上传文件", 'error', '确定');
                return;
            }
            var fileName = file.name;
            var sideAgrtHId = $scope.cWUploadRow.dmId;
            debugger;
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
                    $scope.cWUploadRow.sourceFileName = response.data[0].sourceFileName ;
                    $scope.cWUploadRow.fileId = response.data[0].fileId ;
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
            SIEJS.confirm('提示', '确定要删除所选的信息吗？', '确定', function () {
                var p = {
                    fileId: row.fileId //文件id
                };
                httpServer.post(URLAPI.ttaSideAgrtHeaderFileDelete, {
                    'params': JSON.stringify(p)
                }, function (res) {
                    if (res.status == 'S') {
                        $scope.cWUploadRow.sourceFileName = null ;
                        $scope.cWUploadRow.fileId = null ;
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
            var row = $scope.osdTable.data[index];
            if (!row.collect) {
                row.receiveAmount = null;
            } else if  (row.collect == 'TTA') {
                row.receiveAmount = Math.round(row.chargeMoney / 4 * row.effectiveAreaCnt);
            } else {
                row.receiveAmount = Math.round(row.chargeMoney/4* row.effectiveAreaCnt*(1 + row.additionRate));
            }
        };

        $scope.afterGetData =function () {
            for(var i = 0; i < $scope.osdTable.data.length; i ++) {
                //获取采购行动
                var currentValue = $scope.osdTable.data[i].purchase ;
                var lookUpData = $scope.lookupCodeParty.filter(function (x) {
                    return x.lookupType == 'TTA_DM_ACTION' && x.lookupCode == currentValue;
                });
                //获取豁免原因1
                if($scope.lookupCodeParty){
                    //获取 豁免原因_1
                    $scope.osdTable.data[i].exemptionReasonList = $scope.lookupCodeParty.filter(function (x) {
                        return x.lookupType == 'TTA_DM_REASON' && x.parentLookupValuesId == lookUpData[0].lookupValuesId;
                    });
                }

                //获取 豁免原因_2
                var currentValue2 = $scope.osdTable.data[i].exemptionReason ;
                var values2  = $scope.lookupCodeParty.filter(function (x) {
                    return x.lookupType == 'TTA_DM_REASON' &&  x.lookupCode  == currentValue2;
                });
                if($scope.lookupCodeParty){
                    $scope.osdTable.data[i].exemptionReasonList2 = $scope.lookupCodeParty.filter(function (x) {
                        return x.lookupType == 'TTA_DM_REASON1' && x.parentLookupValuesId == values2[0].lookupValuesId;
                    });
                }
            }
           // $scope.getHeight();
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
                for (var n in $scope.dataSummary.data) {
                    var row = $scope.dataSummary.data[n];
                    if (/*!$scope.setCheckedDisabled(row)*/ true) { // 非被禁用
                        row.checked = true;
                        var isPush = false;
                        for (var m in $scope.currentList2) {
                            var list = $scope.currentList2[m];
                            if (row.groupCode === list.groupCode) {
                                isPush = true;
                                break;
                            }
                        }
                        if (!isPush) $scope.currentList2.push(row);
                    }

                }
            } else {// 返选
                $scope.selectedAll = false;
                for (var n in $scope.dataSummary.data) {
                    var row = $scope.dataSummary.data[n];
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
        $scope.btnCreate = function (){
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
                            debugger;
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

        /*********************** process start **********************/

        $scope.clickColumns = function (item) {
            var p = {
                taskId: item.taskId
            };
            processGetTaskUrl({
                params: JSON.stringify(p)
            }, function (res) {

                if (res.status === 'S') {
                    console.log(res.data.url);
                    if (res.data.isStart === true) { // 草稿
                        iframeTabAction('审批驳回：' + item.bpm_title, 'showProcess/' + decodeURIComponent(res.data.url) +
                            '&action=refusal')

                    } else {
                        iframeTabAction('待审批流程：' + item.bpm_title, 'showProcess/' + decodeURIComponent(res.data.url) +
                            '&action=approval')

                    }

                } else {
                    SIEJS.alert(res.msg, 'error');
                }
            })
        };

        // 委托审批 按钮
        $scope.btnEntrustApproval = function () {
            $("#entrustPerson").modal('show');

        };

        //  委托代办 保存
        $scope.submitEntrustApprova = function () {

            SIEJS.confirm('您将委托给：' + $scope.showParams.userName, '', '确定', function (y) {
                if (y) {
                    $scope.addParams.taskIds = $scope.currentModel.key;

                    processEntrustApproval($scope.addParams, function (res) {
                        if (res.status === 'S') {
                            $("#entrustPerson").modal('hide');

                            $scope.dataTable.seach(1);

                        }
                    })
                }

            })

        };

        // 批量审批
        $scope.batchApproval=function(){
            $scope.showBtnApproval('Y','审批');
        };

        // 批量驳回
        $scope.batchRejected=function(){
            $scope.showBtnApproval('RJ','驳回');
        };


        // 显示审核弹窗
        $scope.showBtnApproval = function (type, txt) {
        
         //校验相同部门的数据是否全部提交，全部提交才能审批start
            var list = $scope.dataSummary.selectRowList;
            if (list == null || list.length == 0) {
                SIEJS.alert("没有选中,请选择！", 'error');
                return;
            }
            httpServer.post(
                URLAPI.findOsdCheckGroupCount, {
                    'params': JSON.stringify({"paramsList": list, batchId: $scope.batchId})
                },
                function (res) {
                    if (res.status == 'S') {
                        $("#approvalFormModal").modal('show');
            			$scope.apporvalParamsType = type;
            			$scope.apporvalParamsBtn = txt;
            			$scope.showParams = {};
            			$scope.addParams = {};
                        $scope.addParams.opinion = '审批通过';
                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                },
                function (err) {
                    SIEJS.alert('数据获取失败！', "error");
                }
            );
            //校验相同部门的数据是否全部提交，全部提交才能审批end
            
            /*
			//加了检查部门审批的代码后，将此代码注释
            $("#approvalFormModal").modal('show');

            $scope.apporvalParamsType = type;
            $scope.apporvalParamsBtn = txt;

            $scope.showParams = {};
            $scope.addParams = {};
			*/
        };



        // 审核功能
        $scope.btnApproval = function () {

            var taskIds = [];
            var rowArr = ($scope.currentModel.key + "").split(",");
            for (var j = 0; j <　rowArr.length; j ++) {
                taskIds.push(rowArr[j]);
            }
            debugger;
            $scope.variables = []; //流程变量
            $scope.propcessVariables = {
                "reportType": 'OSD',
                "isGroupByMonth": "N"
            };
            angular.forEach($scope.propcessVariables, function (value, key) {
                $scope.variables.push({
                    name: key,
                    type: 'string',
                    value: value
                });
            });

            //  进行任务办理
            $scope.apporvalParams = {
                taskIds: taskIds, // $scope.currentModel.key,
                properties: {
                    opinion: $scope.addParams.opinion + '',
                    option: $scope.apporvalParamsType
                },
                variables: $scope.variables
            };

            processBatchComplete($scope.apporvalParams, function (res) {
                if (res.status === 'S') {
                    $("#approvalFormModal").modal('hide');
                    $scope.dataSummary.cancel();
                    //$scope.dataSummary.search(1);
                    $scope.search();
                    swal({
                        title: ($scope.apporvalParamsBtn  +'成功')|| "提交成功",
                        text: '',
                        type: "success",
                        showConfirmButton: false,
                        timer: 1500
                    });

                }else {
                    SIEJS.alert($scope.apporvalParamsBtn + '失败','error','确定', res.msg);
                }

            })
        };

        // 快捷回复
        $scope.toOpinion = function () {
            $scope.addParams.opinion = $scope.showParams.opinion + '';
        };

        /*********************** process end **********************/



    });
});
