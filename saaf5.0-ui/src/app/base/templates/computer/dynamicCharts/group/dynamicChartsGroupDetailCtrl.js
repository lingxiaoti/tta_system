/**
 * Created by houxingzhang on 2018-01-30.
 */
'use strict';
define(['app'], function (app) {
    app.controller('dynamicChartsGroupDetailCtrl', function ($scope, httpServer, URLAPI, SIEJS, $location, arrayDeleteItem,
                                                             $rootScope, $state, $stateParams, Base64) {

        $scope.params = {};
        $scope.groupData = {
            data: []
        };//
        // 数据初始化
        $scope.init = function () {

            $scope.ID = $stateParams.id;
            if ($stateParams.id > 0) {
                $scope.params.reportGroupId = $stateParams.id;

                httpServer.post(URLAPI.dynamicReportGroupDetail, {params: JSON.stringify($scope.params)}, function (res) {
                    if (res.status === 'S') {
                        $scope.groupData = {
                            data: res.data[0].groupDetail,
                            count: res.data[0].count
                        };
                        $scope.headerData = angular.copy(res.data[0]);
                        delete $scope.headerData.groupDetail;


                    }
                })
            }
        };

        $scope.delete = function () {

            if ($scope.ID > 0) {
                $scope.dataTable.delete();// 表格删除
            } else {
                arrayDeleteItem($scope.groupData.data, $scope.dataTable.selectRow, 'reportHeaderId');
            }
        }
        // 新增字段行
        $scope.btnNewReport = function () {
            $scope.isNew = true;
            $scope.current = {
                columnDataType: "STRING",
                paramRequiredFlag: 'N',
                columnDisplayFlag: 'N',
                paramDisplayType: 'STRING'
            };
            if ($scope.headerData) $scope.current.reportHeaderId = $scope.headerData.reportHeaderId;
            $('#editModal').modal('toggle');
        };

        $scope.setReport = function (key, value, currentList) {
            $scope.current = currentList[0];
        };

        // 保存行到表里
        $scope.saveLine = function (invalid1, invalid2) {

            if (invalid1) {
                SIEJS.alert('请检验必填项', 'warning');
                return;
            }
            var isPush = true;
            for (var n in $scope.groupData.data) {
                var item = $scope.groupData.data[n];
                if (item.reportHeaderId === $scope.current.reportHeaderId && item.reportType === $scope.current.reportType) {
                    isPush = false;
                    break;
                }
            }
            if (isPush) {
                $scope.groupData.data.push($scope.current);

                $scope.current = {};
                if (!invalid2) {
                    $scope.save();// 保存到数据库
                }

                $("#editModal").modal('hide');
            } else {
                SIEJS.alert('当前报表已存在', 'warning');
            }
        };

        // 保存到数据库
        $scope.save = function (invalid) {

            if (invalid) {
                SIEJS.alert('请检验必填项', 'warning');
                return;
            }


            var rows = [];
            // 当前行表格 已添加的报表
            for (var n in $scope.groupData.data) {
                var item = $scope.groupData.data[n];
                var obj = {
                    reportHeaderId: item.reportHeaderId,
                    reportType: item.reportType,
                    pageSize: item.pageSize,
                    versionNum: item.versionNum
                };

                if ($scope.ID > 0) {
                    obj.reportGroupId = $scope.ID;
                }
                rows.push(obj)
            }

            var p = {
                group: $scope.headerData,
                header: rows
            };

            httpServer.save(URLAPI.dynamicReportGroupSave, {params: JSON.stringify(p)}, function (res) {
                if (res.status === 'S') {
                    $scope.init();// 重载数据
                    $scope.ID = res.data.reportGroupId;
                }
            })
        };

        $scope.preview = function () {
            $rootScope.goto($scope.headerData.menuTabName + '：预览', '/dynamicCharts/preview/dynamicReportGroupPerview/' + $stateParams.id)
        }


        $scope.init();

    });
});
