/**
 * Created by houxingzhang on 2018-01-23.
 */
'use strict';
define(["app", 'pinyin'], function (app, pinyin) {
    app.controller('dynamicChartsDetailCtrl', function ($scope, $rootScope, $controller, httpServer,
                                                        arrayDeleteItem, URLAPI, SIEJS, $timeout, $state, $stateParams) {
        //$controller('homeCtrl',{$scope:$scope});

        $scope.headerData = {};
        $scope.headerIsNew = true;
        if ($stateParams.reportHeaderId > 0) {
            $scope.headerIsNew = false;
            $scope.queryParam = {reportHeaderId: $stateParams.reportHeaderId};
            httpServer.post(URLAPI.dynamicReportList,
                {
                    'params': JSON.stringify($scope.queryParam),
                    'pageIndex': 1,
                    'pageRows': 1
                },
                function (res) {
                    if (res.data.length > 0) {
                        $scope.headerData = res.data[0];
                        $scope.executeQueryLine();
                    }
                }, function (err) {
                    SIEJS.alert("查询失败", err.msg, "error");
                });
        }

        // 新增字段行
        $scope.createLineRow = function () {
            $scope.isNew = true;

            $scope.current = {
                reportHeaderId: $scope.headerData.reportHeaderId,
                columnDataType: "STRING",
                paramRequiredFlag: 'N',
                columnDisplayFlag: 'N',
                paramDisplayType:'STRING'
            };
            // $('#editModal').modal('toggle');
        }

        // 更新字段行
        $scope.updateLineRow = function (currentRow) {
            $scope.current = currentRow;

            //$scope.updateCurrentRowParent4Modal($scope.current, $('#editModal'));
            $('#editModal').modal('show');
            $scope.isNew = false;
        }

        // 简拼
        $scope.jianpin = function () {
            $scope.headerData.reportHeaderCode = pinyin.shouxiePinyin($scope.headerData.reportDesc)
        }

        // 保存信息
        $scope.btnSave = function (invalid, callback) {
            if (invalid) {
                SIEJS.alert('请检查必填项', "error", "确定");
                return;
            }
            $("#headerSave").attr("disabled");

            httpServer.save(URLAPI.dynamicReportSave, {
                'params': JSON.stringify({
                    header: $scope.headerData,
                    line: $scope.reportLineData || []
                })
            }, function (res) {

                if (res.status == 'S') {
                    $scope.headerData.reportHeaderId = res.data.header.reportHeaderId;// 保存ID
                    $scope.headerIsNew = false;
                    $scope.queryParam = {reportHeaderId: $scope.headerData.reportHeaderId};

                    if (callback) callback();


                } else {
                    SIEJS.alert(res.msg, "error", "确定");
                }
                $("#headerSave").removeAttr("disabled");
            }, function (err) {
                SIEJS.alert("保存失败! " + err.msg, "error", "确定");
                $("#headerSave").removeAttr("disabled");
            });
        }

        // 保存行
        $scope.saveLine = function (invalid) {
            if (invalid) {
                SIEJS.alert('请检查必填项', "error", "确定");
                return;
            }
            $('#editModal').modal('hide');
            if ($scope.isNew) {
                $scope.reportLineData.push($scope.current);// 增到字段列表
                log($scope.current);
            }
        };

        // 单击行
        $scope.setRow = function (item, index) {
            $scope.rowIndex = index;
            $scope.currentRow = item;

        }



        $scope.deleteLines = function () {
            var id = $scope.currentRow.reportLineId;
            httpServer.remove(URLAPI.dynamicReportLineDelete,
                {
                    'params': JSON.stringify({
                        id: id
                    })
                },
                function (res) {
                    if (res.status == 'S') {
                        arrayDeleteItem($scope.reportLineData, $scope.current);
                    }
                });
        }

        // 查询栏目
        $scope.executeQueryLine = function () {
            //$scope.dataTable.search();

            httpServer.post(URLAPI.dynamicReportLine, {params: JSON.stringify($scope.queryParam)}, function (res) {
                $scope.reportLineData = res.data;
            })
        }

        // 生成栏位
        $scope.generateColumns = function (invalid) {
            $scope.btnSave(invalid, function () {
                httpServer.post(URLAPI.dynamicReportCreatLine,
                    {'params': JSON.stringify({reportHeaderId: $scope.headerData.reportHeaderId})},
                    function (res) {
                        if (res.status === 'S') {
                            SIEJS.alert("栏位生成成功!", "success", "确定");
                            $scope.executeQueryLine();
                        } else {
                            SIEJS.alert("栏位生成失败!" + res.msg, "error", "确定");
                        }
                    }, function (err) {
                        SIEJS.alert("栏位生成失败！" + err.msg, "error", "确定");
                    });
            })


        }

        /*//查询条件选择LOV则快码为N
        $scope.clearLookupType = function () {
            $scope.current.lookupFlag = 'N';
        }

        //查询条件选择快码则LOV为N
        $scope.clearLov = function () {
            $scope.current.listFlag = 'N';
        }

        //当查询存在必填参数时，不允许默认执行查询，将默认查询设置为N
        $scope.clearQueryFlag = function () {
            $scope.headerData.queryFlag = 'N';
        }
*/
        // 预览报表
        $scope.previewTable = function () {
            var url = 'dynamicCharts/preview/dynamicReportQuery/' + $scope.headerData.reportHeaderCode;
            $rootScope.goto('报表预览', url);
        }

        $scope.previewEcharts = function () {
            var url = 'dynamicCharts/preview/dynamicEchartsReport/' + $scope.headerData.reportHeaderCode;
            $rootScope.goto('图表预览', url);
        }

        // 获取数据源
        $scope.dataSource = function () {
            httpServer.post(URLAPI.dynamicReportDataList, {}, function (res) {
                if (res.status === 'S') {
                    $scope.dataSourceList = res.data;
                }
            })
        };

        $scope.dataSource();

        // 更新是否与的值
        $scope.changeBoolean = function (item,key) {

            item[key] = item[key] === 'N' || !item[key] ? 'Y' : 'N';
        }

    });
});