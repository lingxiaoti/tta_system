/**
 * Created by dengdunxin on 2018/1/18.
 */
'use strict';
define(['app', 'pinyin', 'ztree'], function (app, pinyin,ztree) {
    app.controller('proposalSummaryCtrl', function ($timeout, $filter, resourceSave, resourceDel, $scope, $http, $location,
                                             buttonList, $rootScope, $state, $stateParams, setNewRow, SIEJS, httpServer, URLAPI, iframeTabAction) {

        $scope.dataTable = {};
        $scope.params = {
            systemCode:window.appName
        }

        $scope.processNodeList = [];
        $scope.processDataList = [];
        $scope.searchDataTable = function () {
            $scope.params.lookupType = 'TTA_PROPOSAL_PROCESS_NODE';
            httpServer.post(URLAPI.findNodeList,
               // {'params': JSON.stringify({lookupType: 'TTA_PROPOSAL_PROCESS_NODE'})} ,
                {'params': JSON.stringify($scope.params)},
                function (res) {
                    if (res.status == 'S') {
                        $scope.processNodeList = res.data.nodeHeadList;
                        $scope.processDataList = res.data.nodeDataList;
                        var dataList = $scope.processDataList;
                        //对processDataList 数据进行重构并计算
                        for (var i = 0; i < dataList.length - 1; i++) {
                            var rowDataList = dataList[i];//一行记录
                            var inThePendingApproved = 0; //1:计算In the pending approved的数据
                            for (var j =0 ; j < rowDataList.length; j ++) {
                                //对列记录进行数据重构,1:计算In the pending approved的数据,4~17
                                if (j >= 4 && j <= 17) {
                                    inThePendingApproved += parseInt(rowDataList[j].nodeValue);
                                }
                               if (j == 22) {
                                   //累加并填充到In the pending approved列
                                   rowDataList[19].nodeValue = inThePendingApproved;
                                    //计算第4列(In the preparation)  3=1-2-19-20
                                    rowDataList[3].nodeValue = parseInt(rowDataList[1].nodeValue) - parseInt(rowDataList[2].nodeValue) - parseInt(rowDataList[19].nodeValue) - parseInt(rowDataList[20].nodeValue);
                                    //Rate% for proposal completion 角标22 = Proposal approved 角标20/Proposal need buyer sumbit to BIC 角标1
                                    //计算列的值Rate% for proposal completiony
                                    rowDataList[22].nodeValue = parseFloat(rowDataList[1].nodeValue) == 0 ? "Infinity" : ((parseFloat(rowDataList[20].nodeValue)* 100 /parseFloat(rowDataList[1].nodeValue)).toFixed(2) + '%');
                                    //In the pending for Buyer Submition 角标18 = pending to submit 角标2 + In the preparation 角标3
                                    rowDataList[18].nodeValue = parseFloat(rowDataList[2].nodeValue) + parseFloat(rowDataList[3].nodeValue);
                                    // Rate% for In the pending for Buyer Submition-角标21=In the pending for Buyer Submition-角标18/Proposal need buyer sumbit to BIC-角标1
                                    rowDataList[21].nodeValue = parseFloat(rowDataList[1].nodeValue) == 0 ? "Infinity" : ((parseFloat(rowDataList[18].nodeValue) * 100 /parseFloat(rowDataList[1].nodeValue)).toFixed(2) + '%');
                                }
                            }
                        }

                        //计算指定列的数值，需要计算第2列到第21列。
                        var lastRowData =dataList[dataList.length - 1];//将统计的数据设置到最后一列。
                        for(var idx = 1; idx <= 20; idx ++){
                            var colValue = 0;
                            for (var rowIdx = 0; rowIdx < dataList.length - 1; rowIdx++) {
                                colValue += parseInt(dataList[rowIdx][idx].nodeValue);
                            }
                            lastRowData[idx].nodeValue =colValue;
                        }
                        // Rate% for In the pending for Buyer Submition_角标21 = 角标18/Proposal need buyer sumbit to BIC_角标1
                        lastRowData[21].nodeValue = parseInt(lastRowData[1].nodeValue) == 0 ? "Infinity" : ((parseInt(lastRowData[18].nodeValue) * 100 / parseInt(lastRowData[1].nodeValue)).toFixed(2) + '%');
                        //Rate% for proposal completion_22角标 = Proposal  approved_角标20/Proposal need buyer sumbit to BIC_角标1
                        lastRowData[22].nodeValue =  parseInt(lastRowData[1].nodeValue) == 0 ? "Infinity" : ((parseInt(lastRowData[20].nodeValue) * 100 / parseInt(lastRowData[1].nodeValue)).toFixed(2) + '%');
                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                }, function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                }
            );
        }
        $scope.searchDataTable();


        var ipts ;
        $('#tbodyId').not('[readonly]').change(function () {
            $('#tb tr').each(function () {
                ipts = $(this).find('td').eq(2);
                alert(ipts);
            });
        });
        $scope.dataExport = function() {
            tableToExcel('dataTableId', '导出数据')
        }
        //base64转码
        var base64 = function (s) {
            return window.btoa(unescape(encodeURIComponent(s)));
        };
        //替换table数据和worksheet名字
        var format = function (s, c) {
            return s.replace(/{(\w+)}/g,
                function (m, p) {
                    return c[p];
                });
        }
        function tableToExcel(tableid, sheetName) {
            var uri = 'data:application/vnd.ms-excel;base64,';
            var template = '<html xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel"' +
                'xmlns="http://www.w3.org/TR/REC-html40"><head><meta charset="UTF-8"><!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet>'
                + '<x:Name>{worksheet}</x:Name><x:WorksheetOptions><x:DisplayGridlines/></x:WorksheetOptions></x:ExcelWorksheet></x:ExcelWorksheets>'
                + '</x:ExcelWorkbook></xml><![endif]-->' +
                ' <style type="text/css">' +
                'table th {' +
                'border: 1px solid #ffffff;' +
                ' }' +
                '</style>' +
                '</head><body ><table class="excelTable">{table}</table></body></html>';
            if (!tableid.nodeType) tableid = document.getElementById(tableid);
            var ctx = {worksheet: sheetName || 'Worksheet', table: tableid.innerHTML};
            //document.getElementById("excelOut").href = uri + base64(format(template, ctx));
            window.open(uri + base64(format(template, ctx)));
        }

    });
});
