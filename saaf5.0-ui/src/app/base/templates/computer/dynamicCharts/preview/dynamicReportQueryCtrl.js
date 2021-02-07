/**
 * Created by houxingzhang on 2018-01-24.
 */
'use strict';
define(["app"], function (app) {
    app.controller('dynamicReportQueryCtrl', function ($scope, $rootScope, $controller, httpServer, URLAPI, $timeout, $stateParams, $filter, tableXlsExport) {

        $rootScope.moreInput();
        $scope.queryParams = {};// 查询条件
        $scope.legend = []; // 表格的字段列
        $scope.queryParam = {reportHeaderCode: $stateParams.reportHeaderCode, queryParams: $scope.queryParams};
        $scope.paramColumnData = [];// 查询条件的INPUT

        // 查询头表获取字段行表
        $scope.getFields = function () {
            httpServer.post(URLAPI.dynamicReportLine, {
                    'params': JSON.stringify($scope.queryParam),
                    'pageIndex': '1',
                    'pageRows': '1000'
                },
                function (res) {
                    if (res.status === 'S' && res.count > 0) {
                        for (var n  in res.data) {
                            var item = res.data[n];

                            // 是否 显示到dataTable 的列上
                            if (item.columnDisplayFlag === 'Y') {
                                $scope.legend.push({'value': item.columnCode, 'name': item.columnName});
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

                            //$scope.paramColumnData.push(item);
                        }

                        $scope.queryFlag = res.data[0].queryFlag;
                        if($scope.queryFlag === 'Y'){
                            $scope.dynamicReportTablePreview.search();
                        }
                    }
                });
        };

        // 初始化
        $scope.init = function () {
            $scope.getFields();// 获取字段行
        }();

        $scope.exportExcel = function () {
            tableXlsExport("dynamicReportTablePreview", $scope.queryParam, $scope.dynamicReportTablePreview.count);
        }

        /**
         * 将包含表达式内容的字符，转换成日期，并按照相应的计算逻辑计算并返回结果;表达式包含：{year}、{month}、{sysdate}、{systime}
         * 例如：{sysdate}-1*50，结果为今天往前50天
         * 调用方法：$scope.getNewDate("{sysdate}-1*50");
         * @param valueStr 需转换字符串,包含表达式及计算值例如：{sysdate}-1*50
         * @return
         */
        $scope.getNewDate = function(valueStr){
            if(!valueStr){
                return null;
            }
            var str;
            var num;
            var dateFormat;
            var date = new Date();
            if(valueStr.indexOf("{year}")!=-1){//{year}替换成当前年份，并按年进行计算，例如：{year}-1为减一年、{year}+1*10为加10年
                dateFormat = "yyyy";
                str = valueStr.replace("{year}", "")+"+0";
                num = eval(str);
                date.setFullYear(date.getFullYear()+num);
            }else if(valueStr.indexOf("{month}")!=-1){//{month}替换成当前月份，并按月进行计算，例如：{month}-1为减一个月、{month}+1*10为加10个月
                dateFormat = "yyyyMM";
                str = valueStr.replace("{month}", "")+"+0";
                num = eval(str);
                date.setMonth(date.getMonth() + num);
            }else if(valueStr.indexOf("{sysdate}")!=-1){//{sysdate}替换成当前时间，并按天进行计算，例如：{sysdate}-1为减一天、{sysdate}+1*10为加10天
                dateFormat = "yyyy-MM-dd";
                str = valueStr.replace("{sysdate}", "")+"+0";
                num = eval(str);
                date.setDate(date.getDate() + num);
            }else if(valueStr.indexOf("{systime}")!=-1){//{sysime}替换成当前时间，并按秒进行计算，例如：{systime}-1为减一秒钟、{systime}+1*10为加10秒钟
                dateFormat = "yyyy-MM-dd HH:mm:ss";
                str = valueStr.replace("{systime}", "")+"+0";
                num = eval(str);
                date.setSeconds(date.getSeconds() + num);
            }else if(valueStr.indexOf("{yearFirstDay}")!=-1){//{yearFirstDay}替换当年第一天，并按天进行计算，例如：{yearFirstDay}-1为减一天、{yearFirstDay}+1*10为加10天
                dateFormat = "yyyy-MM-dd";
                str = valueStr.replace("{yearFirstDay}", "")+"+0";
                num = eval(str);
                date=Date.parse(date.getFullYear()+'/1/1');
                date=new Date(date);
                date.setDate(date.getDate() + num);
            }else if(valueStr.indexOf("{monthFirstDay}")!=-1){//{monthFirstDay}替换当月第一天，并按天进行计算，例如：{monthFirstDay}-1为减一天、{monthFirstDay}+1*10为加10天
                dateFormat = "yyyy-MM-dd";
                str = valueStr.replace("{monthFirstDay}", "")+"+0";
                num = eval(str);
                date=Date.parse(date.getFullYear()+'/'+(date.getMonth()+1)+'/1');
                date=new Date(date);
                date.setDate(date.getDate() + num);
            }
            else{
                return valueStr;
            }
            return $filter('date')(date,dateFormat);
        }

    });
});