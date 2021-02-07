/**
 * Created by lip on 2019/5/8.
 */
'use strict';
define(['app', 'pinyin', 'ztree', 'angularFileUpload'], function (app, pinyin, ztree, angularFileUpload) {
    app.useModule('angularFileUpload');//按需加载弹出层模块
    app.controller('ruleCtrl',['$scope', 'httpServer','SIE.JS', 'URLAPI', 'SIE.JS','$stateParams','$timeout','arrayFindItemIndex','$filter',
        function($scope,httpServer,JS,URLAPI,SIEJS,$stateParams,$timeout,arrayFindItemIndex,$filter) {
        var id = $stateParams.id;
        $scope.ruleHeaderDataTable = {}; //规则头数据
        $scope.ruleHeaderModalDataTable = {}; //规则头弹出窗口
        $scope.ruleLineModalDataTable = {}; //规则行弹出窗口
        $scope.params = {};
        $scope.relSupplierInfo = {};
        $scope.excludeParams = {};
        $scope.current = {};
        $scope.childParams = {};
        $scope.ruleListDataTable = {};
        $scope.currentLine = {};


        $scope.queryHeaderRule = function() {
            $scope.ruleHeaderDataTable.search(1);
            $scope.params.ruleId = null;
            $scope.ruleListDataTable.search();
        }


        $scope.dbClickCallBack = function(item) {
            console.log(JSON.stringify(item));
            httpServer.post(URLAPI.findRuleLinePagination, {
                    params: JSON.stringify({ruleId: item.ruleId})
                },
                function (res) {
                    if (res.status == 'S') {
                        $scope.params.ruleId = item.ruleId;
                        $scope.ruleListDataTable.search();
                    } else {
                        SIEJS.alert(res.msg, 'error', "查询失败!");
                    }
                },
                function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                }
            );
        }

        //新增规则信息
        $scope.btnNew = function () {
            $("th>input[type=checkbox]").prop("checked", false);
            $scope.ruleHeaderModalDataTable.selectRowList = []; //移除自动选中
            $scope.ruleHeaderModalDataTable.search();
            $('#addRuleHeaderModal').modal('toggle');
        }

        //修改头部信息
        $scope.btnModify = function () {
            $scope.current = angular.copy($scope.ruleHeaderDataTable.selectRow);
            $('#updateRuleHeaderModal').modal('toggle')

        }

        //保存参数信息
        $scope.saveQuestionChoiceParams = function () {
            var list = $scope.ruleHeaderModalDataTable.selectRowList;
            if (list == null || list.length == 0) {
                SIEJS.alert("没有选中,请选择！", 'error');
                return;
            }
          var arr = [];
            for (var idx in list) {
                var json = list[idx];
                json.isEnable = 'N';
                json.resultValue = 'Y';
                arr.push(json);
            }
            httpServer.post(URLAPI.saveCheckRuleList, {
                params: JSON.stringify({"checkList" : arr})
            }, function (res) {
                $('#addRuleHeaderModal').modal('toggle');
                if (res.status == 'S') {
                    $scope.ruleHeaderDataTable.search();
                    SIEJS.alert(res.msg, 'success');
                } else {
                    SIEJS.alert(res.msg, 'error');
                }
            }, function (error) {
                $('#addRuleHeaderModal').modal('toggle');
                SIEJS.alert(res.msg, 'error');
            })
        }


        $scope.confirmSave = function () {
            console.log("$scope.current:" + JSON.stringify($scope.current));
            httpServer.post(URLAPI.saveRuleHeader, {
                params: JSON.stringify($scope.current)
            }, function (res) {
                if (res.status == 'S') {
                    $scope.ruleHeaderDataTable.selectRow.isEnable =  $scope.current.isEnable;
                    $scope.ruleHeaderDataTable.selectRow.resultValue =  $scope.current.resultValue;
                    SIEJS.alert(res.msg, "success", "保存成功");
                } else {
                    SIEJS.alert(res.msg, 'error', "保存失败");
                }
            }, function (error) {
                SIEJS.alert("操作失败", 'error');
            })
            $('#updateRuleHeaderModal').modal('toggle');
        }


        //新增下级
        $scope.btnNewChild = function () {
            $scope.childParams.ruleId = $scope.ruleHeaderDataTable.selectRow.ruleId;
            $("th>input[type=checkbox]").prop("checked", false);
            $scope.ruleLineModalDataTable.selectRowList = []; //移除自动选中
            $scope.ruleLineModalDataTable.search();
            $('#addRuleLineModal').modal('toggle');
        }


        $scope.saveChildParams = function () {
            var list = $scope.ruleLineModalDataTable.selectRowList;
            if (list == null || list.length == 0) {
                SIEJS.alert("没有选中,请选择！", 'error');
                return;
            }
            var ruleId = $scope.ruleHeaderDataTable.selectRow.ruleId;
            console.log("selectRow：" + JSON.stringify($scope.ruleHeaderDataTable.selectRow))
            var arr = [];
            for (var idx in list) {
                var json = list[idx];
                json.ruleId = ruleId;
                json.isEnable = 'Y';
                arr.push(json);
            }
            httpServer.post(URLAPI.saveChildRule, {
                params: JSON.stringify({"checkList" : arr})
            }, function (res) {
                $('#addRuleLineModal').modal('toggle');
                if (res.status == 'S') {
                    $scope.params.ruleId = ruleId;
                    $scope.ruleListDataTable.search();
                    SIEJS.alert(res.msg, 'success');
                } else {
                    SIEJS.alert(res.msg, 'error');
                }
            }, function (error) {
                $('#addRuleLineModal').modal('toggle');
                SIEJS.alert(res.msg, 'error');
            })
        }

        //修改下级
        $scope.btnModifyChild = function() {
            $scope.currentLine = angular.copy($scope.ruleListDataTable.selectRow);
            $('#updateRuleLineModal').modal('toggle')
        }

        //确认更新状态
        $scope.updateChildStatus = function () {
            console.log("updateChildRuleStatus:" + JSON.stringify($scope.currentLine));
            httpServer.post(URLAPI.updateChildRuleStatus, {
                params: JSON.stringify($scope.currentLine)
            }, function (res) {
                if (res.status == 'S') {
                    $scope.ruleListDataTable.selectRow.isEnable =  $scope.currentLine.isEnable;
                    SIEJS.alert(res.msg, "success", "保存成功");
                } else {
                    SIEJS.alert(res.msg, 'error', "保存失败");
                }
            }, function (error) {
                SIEJS.alert("操作失败", 'error');
            })
            $('#updateRuleLineModal').modal('toggle');
        }

    }])
});
