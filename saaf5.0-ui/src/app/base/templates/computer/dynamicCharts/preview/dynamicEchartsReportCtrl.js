/**
 * Created by houxingzhang on 2018-01-25.
 */
'use strict';
define(['app', 'echarts'], function (app, echarts) {
    app.controller('dynamicEchartsReportCtrl', function ($scope, $http, $location, $rootScope, $state, $stateParams, httpServer, URLAPI, SortBy) {

        $scope.queryParams = {};// 查询条件
        $scope.legend = []; // 表格的字段列
        $scope.queryParam = {reportHeaderCode: $stateParams.reportHeaderCode, queryParams: $scope.queryParams};


        // 初始化
        $scope.init = function () {
            $scope.getHeader();
        };
        // 查询头表获取字段行表
        $scope.getFields = function () {
            httpServer.post(URLAPI.dynamicReportLine, {
                    'params': JSON.stringify($scope.queryParam),
                    'pageIndex': '1',
                    'pageRows': '1000'
                },
                function (res) {

                    if (res.status === 'S') {
                        $scope.legend = [];
                        $scope.paramColumnData = [];// 查询条件的INPUT
                        $scope.columns = [];// 列名;

                        res.data.sort(SortBy('orderNo', 'asc'));

                        for (var n  in res.data) {
                            var item = res.data[n];


                            // 是否 显示到dataTable 的列上
                            if (item.columnDisplayFlag === 'Y') {
                                $scope.legend.push({
                                    'value': item.columnCode,
                                    'name': item.columnName,
                                    'index': item.orderNo
                                });
                                $scope.columns.push(item.columnCode);
                            }

                            // 是否是查询条件
                            if (item.conditionFlag === 'Y') {
                                $scope.paramColumnData.push(item);

                                if (item.rangeFlag == 'Y') {
                                    item.columnCodeTo = item.columnCode + '_lte';
                                    item.columnCode = item.columnCode + '_gte';
                                    $scope.queryParams[item.columnCodeTo] = $scope.getNewDate(item.paramDefaultValue);
                                } else {
                                    item.columnCode = item.conditionExpression && item.conditionExpression != '=' ? item.columnCode + item.conditionExpression : item.columnCode;
                                }
                                $scope.queryParams[item.columnCode] = $scope.getNewDate(item.paramDefaultValue);
                            }

                        }
                        $scope.getEchartData($scope.headerData);// 获取字段行
                    }
                });
        };

        // echart数据转换
        $scope.getEchartData = function (header) {
            httpServer.post(URLAPI.dynamicReportTablePreview, {
                    'params': JSON.stringify($scope.queryParam),
                    'pageIndex': '1',
                    'pageRows': '1000'
                },
                function (res) {
                    if (res.status === 'S') {
                        $scope.echartData = [];
                        for (var n  in res.data) {
                            var item = res.data[n];
                            var line = {};
                            var i = 1;
                            for (var y in $scope.columns) {
                                line['column' + i] = item[$scope.columns[y]];
                                i++;
                            }
                            $scope.echartData.push(line)
                        }
                        $scope.createEchart(header, $scope.echartData);
                    }
                });
        };

        $scope.getHeader = function () {
            httpServer.post(URLAPI.dynamicReportList,
                {
                    'params': JSON.stringify($scope.queryParam),
                    'pageIndex': 1,
                    'pageRows': 1
                },
                function (res) {
                    if (res.data.length > 0) {

                        $scope.headerData=res.data[0];
                        $scope.getFields();


                    }
                }, function (err) {
                    SIEJS.alert("查询失败", err.msg, "error");
                });
        };

        // 创建echart
        $scope.createEchart = function (header, line) {
            if (line.length > 0) {
                var jsStr = 'var dom = document.getElementById("container");\n';
                jsStr += 'var myChart = echarts.init(dom);\n';
                jsStr += 'var option = null;\n';
                jsStr += 'var dataList = ' + JSON.stringify(line) + ";\n";
                if (header.chartsTitle) {
                    jsStr += 'var title = ' + header.chartsTitle + ';\n';
                }
                if (header.dimensions) {
                    jsStr += 'var dimensions = ' + header.dimensions + ';\n';
                }
                if (header.dimensionLength) {
                    jsStr += 'var dimensionLength = ' + header.dimensionLength + ';\n';
                }
                jsStr += header.dataConversionScript + '\n';
                jsStr += header.webScript + '\n';
                if (header.webScript.indexOf('myChart.setOption') < 0) {
                    jsStr += 'if (option && typeof option === "object") {\n';
                    jsStr += '    myChart.setOption(option, true);\n';
                    jsStr += '}';
                }
                //console.log(jsStr);
                eval(jsStr);
            }
        };

        $scope.init();//

    });
});
