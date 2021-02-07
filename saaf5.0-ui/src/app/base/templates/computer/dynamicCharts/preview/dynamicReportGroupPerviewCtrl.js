/**
 * Created by houxingzhang on 2018-01-30.
 */
'use strict';
define(['app', 'echarts'], function (app, echarts) {
    app.controller('dynamicReportGroupPerviewCtrl', function ($scope, $compile, $http, $location, $timeout,
                                                              SIEJS, $rootScope, $state, $stateParams, httpServer, URLAPI, SortBy) {
        $scope.params = {
            reportGroupId: $stateParams.id
        };

        $scope.legend = {}; // 表格的字段列
        $scope.columns = [];// Echart列名;
        // 查询条件
        $scope.queryParam = {};
        $scope.queryParams = {};// 查询条件
        // 重新查询
        $scope.search = function () {

            for (var n in $scope.queryParam) {
                if (n.indexOf('TABLE') > -1) {
                    $scope[n].search();
                }

            }

        }


        // 查询头表获取字段行表
        $scope.getFields = function (headerData, code, reportType, pageSize) {
            httpServer.post(URLAPI.dynamicReportLine, {
                    'params': JSON.stringify({reportHeaderCode: code}),
                    'pageIndex': '1',
                    'pageRows': '1000'
                },
                function (res) {

                    if (res.status === 'S') {
                        $scope.legend[reportType + code] = [];
                        $scope.paramColumnData = [];// 查询条件的INPUT


                        // 排序
                        res.data.sort(SortBy('orderNo', 'asc'));

                        for (var n  in res.data) {
                            var item = res.data[n];
                            // 是否 显示到dataTable 的列上
                            if (item.columnDisplayFlag === 'Y') {
                                $scope.legend[reportType + code].push({
                                    'value': item.columnCode,
                                    'name': item.columnName,
                                    'index': item.orderNo
                                });
                                if (reportType === 'CHART')
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

                        if (reportType === 'TABLE') {
                            $scope.createTable(headerData, code, reportType, pageSize)
                        }

                        if (reportType === 'CHART') {
                            $scope.getEchartData(headerData, code, reportType);// 　echart数据转换  并生成　echart
                        }

                    }
                });
        };

        // echart数据转换  并生成　echart
        $scope.getEchartData = function (header, code, reportType) {
            httpServer.post(URLAPI.dynamicReportTablePreview, {
                    'params': JSON.stringify({reportHeaderCode: code}),
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

        $scope.getHeader = function (code, reportType, pageSize) {
            httpServer.post(URLAPI.dynamicReportList,
                {
                    'params': JSON.stringify({reportHeaderCode: code}),
                    'pageIndex': 1,
                    'pageRows': 1
                },
                function (res) {
                    if (res.data.length > 0) {
                        $scope.getFields(res.data[0], code, reportType, pageSize);
                    }
                }, function (err) {
                    SIEJS.alert("查询失败", err.msg, "error");
                });
        };

        // 创建echart
        $scope.createEchart = function (header, line) {
            if (line.length > 0) {
                var jsStr = 'var dom = document.createElement("div");\n';
                jsStr += 'dom.style.border="1px solid #eee ";\n';
                jsStr += 'dom.style.padding="15px";\n';
                jsStr += 'dom.style.height="300px";\n';
                jsStr += 'document.getElementById("container").appendChild(dom);\n';
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


                if (!header.webScript || !header.dataConversionScript) {
                    SIEJS.alert(header.reportDesc + ' 配置错误', 'error', '确定', '报表类型为：' + header.reportTypeMeaning + '，\n请配置“webScript”与“dataConversionScript”选项');
                    return;
                }


                jsStr += header.dataConversionScript + '\n';
                jsStr += header.webScript + '\n';

                if (header.webScript.indexOf('myChart.setOption') < 0) {
                    jsStr += 'if (option && typeof option === "object") {\n';
                    jsStr += '    myChart.setOption(option, true);\n';
                    jsStr += '}';
                }

                eval(jsStr);
            }
        };

        // 创建 dataTable
        $scope.createTable = function (header, code, reportType, pageSize) {
            pageSize = pageSize || 5;
            var dom = '<div style="margin-bottom: 15px;" base-data-table="' + reportType + code + '"  id="' + reportType + code + '" ' +
                'ng-params="queryParam.' + reportType + code + '" data-url="dynamicReportTablePreview"  ' +
                'data-legend=\'' + JSON.stringify($scope.legend[reportType + code]) + '\'' +
                'data-page-size="' + pageSize +
                '"></div>';
            $("#container").append($compile(dom)($scope));
        };

        // 初始化
        $scope.init = function () {
            httpServer.post(URLAPI.dynamicReportGroupDetail, {params: JSON.stringify($scope.params)}, function (res) {
                if (res.status === 'S') {
                    $scope.groupData = res;
                    for (var n in res.data[0].groupDetail) {
                        var item = res.data[0].groupDetail[n];
                        $scope.queryParam[item.reportType + item.reportHeaderCode] = {
                            reportHeaderCode: item.reportHeaderCode,
                            queryParams: $scope.queryParams
                        };

                        $scope.getHeader(item.reportHeaderCode, item.reportType, item.pageSize);// 获取单个报表
                    }
                }
            })
        }

        $scope.init();

    });
});
