'use strict';
define(['app', 'pinyin', 'ztree','XLSX','fixedReport'], function (app, pinyin, ztree,XLSX,fixedReport) {
    //app.useModule('angularFileUpload');//按需加载弹出层模块
    app.controller('freeGoodsCtrl', function ($scope, $filter, $location,$timeout, $rootScope, $state, $stateParams, SIEJS, httpServer, URLAPI, iframeTabAction, setNewRow,$http,lookupCode,tableXlsExport,saafLoading) {
        $scope.params = {};
        $scope.logoImg = [];
        $scope.batchId = $stateParams.batchId;
        $scope.params.batchId = $stateParams.batchId;
        $scope.fGTable = {};
        $scope.firstText = "请选择";
        $scope.currentList2 = [] ;

        //获取当前用户信息
        var userLoginInfo = JSON.parse(sessionStorage[appName + '_successLoginInfo']);
        $scope.userType =  userLoginInfo.userType;

        //获取字典信息
        $scope.lookupCodeParty = window.parent.saafrootScope.lookupCode || $rootScope.lookupCode;
        if (!$scope.lookupCodeParty) {
            lookupCode(function (res) {
                $scope.lookupCodeParty = $rootScope.lookupCode = res.data;
            });
        }

        $scope.search = function(){
            $scope.fGTable.getData();
            $scope.getHeight() ;
        };

        $scope.btnFreeze = function (){
            var fixedTable1 = fixReportTable(document.getElementById('fixedId'),4);
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

        $scope.afterGetData = function(){
            $scope.currentList2 = [] ;
            if ($scope.fGTable.data) {
                var totalParams =[] ;
                var actionParams = [];
                var reasonParams = [];
                //var reasonOneParams  = [];
                if($scope.lookupCodeParty){
                    var totalParams =  $scope.lookupCodeParty.filter(function (x) {
                        return  x.lookupType == 'TTA_FG_ACTION'  || x.lookupType =='TTA_FG_REASON';
                    });

                    var actionParams = totalParams.filter(function (x) {
                        return x.lookupType == 'TTA_FG_ACTION' ;
                    });

                    var reasonParams = totalParams.filter(function (x) {
                        return x.lookupType == 'TTA_FG_REASON' ;
                    });

                   /* var reasonOneParams = totalParams.filter(function (x) {
                        return x.lookupType == 'TTA_OSD_REASON1' ;
                    });*/
                }


                for (var i = 0; i < $scope.fGTable.data.length; i++) {
                    //获取采购行动
                    var currentValue = $scope.fGTable.data[i].purchaseAct;

                    var lookUpData = actionParams.filter(function (x) {
                        return currentValue  && x.lookupCode == currentValue;
                    });
                    //获取豁免原因1
                    if ( lookUpData.length > 0) {
                        //获取 豁免原因_1
                        $scope.fGTable.data[i].exemptionReasonList = reasonParams.filter(function (x) {
                            return   x.parentLookupValuesId == lookUpData[0].lookupValuesId;
                        });
                    }

                    //获取 豁免原因_2
                    var currentValue2 = $scope.fGTable.data[i].exemptionReason;
                    var values2 = reasonParams.filter(function (x) {
                        return currentValue2  && x.lookupCode == currentValue2;
                    });
/*                    if ( values2.length > 0) {
                        $scope.fGTable.data[i].exemptionReasonList2 = reasonOneParams.filter(function (x) {
                            return  x.parentLookupValuesId == values2[0].lookupValuesId;
                        });
                    }*/
                }
            }
            $scope.getHeight();
        };

        $scope.btnModify=function(){
            if(!$scope.dataTable.selectRow){
                SIEJS.alert('请选择数据！', 'warning', '确定');
                return ;
            }

            $scope.addParams = {};
            $scope.modalTitle='编辑';
            $scope.addParams.borrowAdjAmt = $scope.dataTable.selectRow.borrowAdjAmt;
            $scope.addParams.aboiRecevieAmt = $scope.dataTable.selectRow.aboiRecevieAmt;
            $scope.addParams.caRecevieAmt = $scope.dataTable.selectRow.caRecevieAmt;
            $scope.addParams.purchaseAct = $scope.dataTable.selectRow.purchaseAct;
            $('#msgCfgModal').modal('toggle');
        };

        $scope.changePerson = function () {
            if($scope.currentList2.length == 0){
                SIEJS.alert('请先选择行数据！', 'warning', '确定');
                return ;
            }
            $('#userLov').modal('toggle');
        };

        $scope.selecUserReturn = function (key, value, currentList) {
            if($scope.currentList2.length == 0){
                SIEJS.alert("请先选择行数据", "error", "确定");
                return;
            }
            //debugger;
            for (var i = 0; i <$scope.currentList2.length; i++) {
                var currentRow = $scope.currentList2[i];
                currentRow.changePerson = userLoginInfo.userId;//转办人id
                currentRow.changePersonName = userLoginInfo.userFullName;//转办人名称
                currentRow.transferInPerson = currentList[0].userId;//被转办人id
                currentRow.transferInPersonName = currentList[0].userFullName;//被转办人名称
            }
        };


        $scope.btnSave=function(){
            $scope.saveAll = $scope.fGTable.data;
            httpServer.post(URLAPI.freeGoodsSave, {
                params: JSON.stringify({
                    saveAll: $scope.saveAll
                    }
                )
            }, function (res) {
                if ('S' == res.status) {
                    $scope.search();
                    SIEJS.alert('保存成功',"success",'确定');
                } else {
                    SIEJS.alert(res.msg, 'error', '确定');
                }
            }, function (error) {
                console.error(error);
                SIEJS.alert('保存失败', 'error', '确定');
            })

        };


        //提交
        $scope.submitApproval = function (form) {
            if($scope.dataTable.selectRow.headerStatus != 'DS01'){
                SIEJS.alert('状态不是制单中，不允许提交审批', 'error', '确定');
                return;
            }

            $scope.extend = {
                "tasks_assignees": []
            };
            $scope.variables = []; //流程变量
           /* angular.forEach($scope.dataTable.selectRow, function (value, key) {
                $scope.variables.push({
                    name: key,
                    type: 'string',
                    value: value
                });
            });*/

            $scope.propcessVariables = {
                "reportType": 'FG',
                "isGroupByMonth": "Y"
            };

            angular.forEach($scope.propcessVariables, function (value, key) {
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
                "businessKey": $scope.dataTable.selectRow.batchId, //单据ID
                "title": "Free Goods Checking Report 审批" + $scope.dataTable.selectRow.batchId, //流程标题，修改为当前业务信息
                "positionId": 0,
                "orgId": 0,
                "userType": "20",
                "billNo": $scope.dataTable.selectRow.batchId
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

        //excel下载
        $scope.btnExport = function () {
            if ($scope.params.batchId == undefined ||  $scope.params.batchId == '') {
                SIEJS.alert("缺失批次,导出失败", "error", "确定");
                return;
            }

            var url = URLAPI.freeGoodsExcelEmport + '?batchId=' + $scope.params.batchId;
            window.open(url, [""], [""]);//避开因同源策略而造成的拦截
        };

        /**
         * 选取豁免原因_1
         * @param index
         * @param currentValue
         * @param lookUpData
         */
        $scope.purchaseFun = function (index,currentValue,lookUpData){
            var row = $scope.fGTable.data[index] ;
            row.exemptionReason  = '';
            row.exemptionReason2 = '';
            var values = lookUpData.filter(function (x) {
                return  x.lookupCode  == currentValue;
            });
            if($scope.lookupCodeParty && values.length >0 ){
                //获取 豁免原因_1
                row.exemptionReasonList = $scope.lookupCodeParty.filter(function (x) {
                    return x.lookupType == 'TTA_FG_REASON' && x.parentLookupValuesId == values[0].lookupValuesId;
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
/*            var values  = list.filter(function (x) {
                return  x.lookupCode  == value;
            });
            row.exemptionReason2 = '';
            if($scope.lookupCodeParty && values.length >0){
                //获取 豁免原因_2
                row.exemptionReasonList2 = $scope.lookupCodeParty.filter(function (x) {
                    return x.lookupType == 'TTA_OSD_REASON1' && x.parentLookupValuesId == values[0].lookupValuesId;
                });
            }*/

        };

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
                if (item.ttaFreeGoodsId === $scope.currentList2[i].ttaFreeGoodsId) {
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
                for (var n in $scope.fGTable.data) {
                    var row = $scope.fGTable.data[n];
                    if (/*!$scope.setCheckedDisabled(row)*/ true) { // 非被禁用
                        row.checked = true;
                        var isPush = false;
                        for (var m in $scope.currentList2) {
                            var list = $scope.currentList2[m];
                            if (row.ttaFreeGoodsId === list.ttaFreeGoodsId) {
                                isPush = true;
                                break;
                            }
                        }
                        if (!isPush) $scope.currentList2.push(row);
                    }

                }
            } else {// 返选
                $scope.selectedAll = false;
                for (var n in $scope.fGTable.data) {
                    var row = $scope.fGTable.data[n];
                    row.checked = false;
                    var index = $scope.currentList2.indexOf(row);
                    $scope.currentList2.splice(index--, 1);// for 循环使用 splice 删除数组请 把下标 --
                }
            }

        };


    });
});
