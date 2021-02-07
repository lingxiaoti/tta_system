'use strict';
define(['app', 'pinyin', 'ztree','XLSX','fixedReport'], function (app, pinyin, ztree,XLSX, fixedReport) {
    app.controller('oiGpReportCtrl', function ($scope, $filter, $location, $rootScope, $state, $stateParams, SIEJS, httpServer,validateForm, URLAPI, iframeTabAction, setNewRow,$http,$timeout,lookupCode,saafLoading) {
        $scope.buttonFlag = true ;
        $scope.userType = JSON.parse(sessionStorage[appName + '_successLoginInfo']).userType;
        $scope.cWTable = {};
        $scope.batchCode = $stateParams.batchCode;
        $scope.params = {"batchCode":$scope.batchCode} ;
        $scope.splitDataTable = [] ;
        $scope.currentList2 = [] ;
        $scope.selectRowList= [] ;
        $scope.firstText = "请选择";
        $scope.btnFreeze = function (){
            var fixedTable1 = fixReportTable(document.getElementById('fixedId'),6);
        }

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
        }

        $scope.search = function (){
            $scope.cWTable.getData() ;
            //$scope.getHeight();
        }


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

        var exportTimer;
        /**
         * 批量删除
         * @constructor
         */
        $scope.PLSC = function () {
            $scope.paramsPL = {} ;
            $('#PLDelete').modal('toggle')
        }
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
            }
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
                "title": "GP_REPORT_审批" + $scope.cWTable[0].batchCode, //流程标题，修改为当前业务信息
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
        }


        /**
         * 保存行
         */
        $scope.btnSave = function (form){
            if(!validateForm(form)){
                return;
            }
            $scope.saveAll =  $scope.cWTable.data;//所有记录
            //$scope.saveAll = $scope.currentList2; //当前选择的记录
            httpServer.post(URLAPI.saveOrUpdateALLTtaGp,
                {params: JSON.stringify({
                        save: $scope.saveAll
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
        }

        //批量确认或者取消
        $scope.confirmData = function (confirmStatus) {
            if (!$scope.currentList2 || $scope.currentList2.length == 0) {
                SIEJS.alert("请选择一行", "error", "确定");
                return;
            }
            var selectRows = $scope.currentList2; //当前选择的记录
            for (var idx = 0; idx <selectRows.length; idx ++) {
                selectRows[idx].confirmStatus = confirmStatus;
            }
            console.log(selectRows);
            SIEJS.confirm('提示', '是否选中' + (confirmStatus == 'Y' ? "确认" : "取消确认") + "?", '确定', function () {
                httpServer.post(URLAPI.saveOrUpdateALLTtaGp,
                    {params: JSON.stringify({ save: selectRows, confirmStatus: confirmStatus})},
                    function (res) {
                        if (res.status == "S") {
                            $scope.cWTable.getData();
                            SIEJS.alert("处理成功", "success", "确定");
                        } else {
                            SIEJS.alert(res.msg, "error", "确定");
                        }
                    },
                    function (err) {
                        SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                    }
                );
            })
        }

        /**
         * 选择转办人
         */
        $scope.btnBz =  function (){
            $('#lovcWReportFind').modal('toggle') ;
        }

        /**
         * 转办人确认
         */
        $scope.selectTransferReturn = function (key, value, currentList){
            if (!$scope.cWTable.data.selectRowData){
                SIEJS.alert("请选择一行", "error", "确定");
                return ;
            } ;
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

        }

        $scope.changeCollectionMethod = function (index){
            var row = $scope.cWTable.data[index];
        }

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
                if (item.simulationId === $scope.currentList2[i].simulationId) {
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
                            if (row.simulationId === list.simulationId) {
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
        }



        $scope.headList = [];
        $scope.dataList = [];
        $scope.searchDataTable = function () {
            httpServer.post(URLAPI.findSummaryGpSimulation,
                {'params': JSON.stringify($scope.params)},
                function (res) {
                    if (res.status == 'S') {
                        $scope.headList = res.data.headList;
                        $scope.dataList = res.data.dataList;
                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                }, function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                }
            );
        };

        $scope.searchDataTable();

    });
});
