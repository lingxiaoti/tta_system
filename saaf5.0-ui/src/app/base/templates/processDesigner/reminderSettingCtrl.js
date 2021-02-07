/**
 * Created by Administrator on 2018/7/2.
 */
'use strict';
define(['app', 'ztree'], function (app) {
    app.controller('reminderSettingCtrl', function ($scope, httpServer, $timeout, $location, $rootScope, $state, $stateParams, SIEJS
        , processTaskUrgeConfigSave, processTaskUrgeConfigUpdate) {
        $scope.params = {};
        $scope.addParams = {};
        $scope.showParams = {};
        $scope.current = {};
        $scope.rangeTreeParams = {
            attachProcess: true
        };
        $scope.btnNew = function () {
            $scope.rangeTreeParams = {
                attachProcess: true
            };
            $scope.addParams = {};
            $scope.showParams = {};

            $('#entrustApproval').modal('show');
            $("#rangeTree").find('input,button').attr('disabled', false);//上级分类选择
        };
        $scope.btnModify = function () {
            if (!$scope.dataTable.selectRow) {
                SIEJS.alert('请选择一条数据', 'warning');
                return;
            }

            $('#entrustApproval').modal('show');
            $scope.addParams = angular.copy($scope.dataTable.selectRow);
            $scope.showParams.value = $scope.addParams.processName;
            $("#rangeTree").find('input,button').attr('disabled', true);//禁用上级分类选择
            $("#rangeTree").find('.ztree-select-box').addClass('none');//禁用上级分类选择

        };
        $scope.btnDel = function () {
            $scope.dataTable.delete();
        };

        $scope.clickRow = function (item) {
            var txt = item.disabled ? '生效' : '失效';
            var icon = item.disabled ? 'fa-refresh' : 'fa-ban';
            $("#btnInvalid").html('<i class="fa ' + icon + '" aria-hidden="true"></i>' + txt);
        }
        // 失效按钮
        $scope.btnInvalid = function () {
            var row = $scope.dataTable.selectRow;

            var txt = row.enabled ? '失效' : '生效';
            SIEJS.confirm('您确定把当前设置' + txt + '吗？', '', '确定', function (b) {

                if (b) {
                    var p = {
                        configIds: [row.configId],
                        disabled: !row.disabled

                    }
                    processTaskUrgeConfigUpdate(p, function (res) {
                        if (res.status === 'S') {
                            $scope.dataTable.search(1);
                            $scope.rangeTree.search();// 重置树
                            $('#entrustApproval').modal('hide');
                        }
                    })
                }
            })


        }

        //z-tree-select回调 选择范围
        $scope.setRangeVal = function (nodes, keys, values) {
            console.log(nodes);
            //console.log(keys);
            //console.log(values);
            var processDefinitionKeys = [];
            angular.forEach(nodes, function (data, index) {
                if (data.processKey) {
                    processDefinitionKeys.push(data.processKey);
                }
            });
            console.log(processDefinitionKeys);
            $scope.addParams.processDefinitionKeys = processDefinitionKeys;
        }

        $scope.save = function (invalid) {
            var node = $scope.current.range;
            if (node) {
                if (node.categoryId > 0) {
                    SIEJS.alert('当前流程范围节点不是流程实例节点', 'warning', '确定', '请为当前分类节点添加流程');
                    return;
                }
                $scope.addParams.processDefinitionKey = node.categoryCode;
            }

            log($scope.addParams);

            processTaskUrgeConfigSave($scope.addParams, function (res) {
                if (res.status === 'S') {
                    $scope.dataTable.search(1);
                    $scope.rangeTree.search();// 重置树
                    $('#entrustApproval').modal('hide');
                }
            })


        }

        //列表数据重载回调
        $scope.processListLoad = function (oScope, data) {
            $scope.dataTable.selectRowList = [];
        }

        // 测试的内容
        $scope.errorNum = 0;
        $scope.setTimeout = function () {
            httpServer.post('http://localhost:86/ashx/timeout.ashx?s=1', {
                __timeout: 10,
                __errorRepeated: 3
            }, function (res, status, headers, config) {
                log('请求成功' + JSON.stringify(res))
            })
        }


    });
});
