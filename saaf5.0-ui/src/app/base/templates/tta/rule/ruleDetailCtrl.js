/**
 * Created by dengdunxin on 2018/1/8.
 */
'use strict';
define(['app', 'pinyin', 'ztree', 'angularFileUpload'], function (app, pinyin, ztree, angularFileUpload) {
    app.useModule('angularFileUpload'); //按需加载弹出层模块
    app.controller('ruleDetailCtrl', function ($filter, httpServer, URLAPI, Base64, $window, userSave, $timeout, $scope, $http, $location, $rootScope, $state, $stateParams, setNewRow, SIEJS) {
        $scope.tab = {
            active: 0,
            nav: ['参数信息', '文件模板'],
            click: function (i) {
                $scope.tab.active = i
            }
        }
        var rId = $stateParams.id;
        $scope.ruleParams = {}
        $scope.params = {}
        $scope.idModify = $stateParams.id;
        $scope.excludeParams = {}

        $scope.btnSave = function () {
            httpServer.post(URLAPI.saveOrUpdate, {
                params: JSON.stringify($scope.params)
            }, function (res) {
                if (res.status == 'S') {
                    rId = $stateParams.id = $scope.idModify = res.data.rulId;
                    $scope.excludeParams = {ruleId: rId};
                    $scope.ruleParams = {ruleId: rId};
                    $scope.params = res.data;
                    $scope.params.soleResouceType = $scope.params.soleResouceType.toString();
                    $scope.params.soleProductType = $scope.params.soleProductType.toString();
                    SIEJS.alert(res.msg, 'success');
                } else {
                    SIEJS.alert(res.msg, 'error');
                }
            }, function (error) {
                SIEJS.alert(res.msg, 'error');
            });
        }


        $scope.search = function (id) {
            var pramsId = id || parseInt(rId);
            $scope.ruleParams = {ruleId: pramsId};
            $scope.excludeParams = {ruleId: pramsId};
            httpServer.post(URLAPI.findRuleById, {
                params: JSON.stringify({
                    ruleId: pramsId,
                })
            }, function (res) {
                if (res.status == 'S') {
                    // 成功后执行
                    $scope.params = res.data;
                    $scope.params.soleResouceType = $scope.params.soleResouceType.toString();
                    $scope.params.soleProductType = $scope.params.soleProductType.toString();
                } else {
                    SIEJS.alert(res.msg, 'error');
                }
            }, function (error) {
                SIEJS.alert(res.msg, 'error');
            });

        }
        //修改后查询
        if (rId) {
            $scope.search(rId);
        }

        // 新增|修改 弹出新增参数窗口
        $scope.btnNew = function () {
            $("th>input[type=checkbox]").prop("checked", false);
            $scope.dataTable.selectRowList = []; //移除自动选中
            $scope.dataTable.search()
            $('#nameLov').modal('toggle');
        }

        // 删除参数信息
        $scope.btnDel = function () {
            //var row = $scope.rpTable.selectRow;
            var list = $scope.rpTable.selectRowList;
            if (!list || list.length == 0) {
                SIEJS.alert("没有选中,请选择！", 'error');
                return;
            }
            var ids = [];
            angular.forEach(list, function (v, k) {
                ids.push(v.ruleParamId);
            });
            SIEJS.confirm('移除规则参数', '确认删除？', '确定', function () {
                console.log("移除规则参数:" + JSON.stringify(ids));
                httpServer.post(URLAPI.deleteParamById, {
                    params: JSON.stringify({
                        ruleParamIds: ids
                    })
                }, function (res) {
                    if (res.status == 'S') {
                        $scope.rpTable.selectRowList = [];
                        $scope.rpTable.search($scope.params.ruleId);
                        SIEJS.alert(res.msg, 'success');
                    } else {
                        SIEJS.alert(res.msg, 'error');
                    }
                }, function (error) {
                    SIEJS.alert(res.msg, 'error');
                })
            })
        }

        //保存参数信息
        $scope.saveParams = function () {
            var list = $scope.dataTable.selectRowList;
            if (list == null || list.length == 0) {
                SIEJS.alert("没有选中,请选择！", 'error');
                return;
            }
            var arr = [];
            for (var idx in list) {
                var json = list[idx];
                json.ruleId = rId;
                arr.push(json);
            }
           console.log("arr:" + JSON.stringify(arr));
            httpServer.post(URLAPI.saveParams, {
                params: JSON.stringify(arr)
            }, function (res) {
                $('#nameLov').modal('toggle');
                if (res.status == 'S') {
                    $scope.rpTable.search($scope.params.ruleId);
                    SIEJS.alert(res.msg, 'success');
                } else {
                    SIEJS.alert(res.msg, 'error');
                }
            }, function (error) {
                $('#nameLov').modal('toggle');
                SIEJS.alert(res.msg, 'error');
            })
        }

        //  流程导出
        $scope.btnWordProcess = function () {
            var url = URLAPI.wordTempdownload + '?ruleId=' + rId;
            window.open(url, [""], [""]);//避开因同源策略而造成的拦截
        }

    });
});