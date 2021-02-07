'use strict';
define(['app', 'pinyin', 'ztree'], function (app, pinyin, ztree) {
    //app.useModule('angularFileUpload');//按需加载弹出层模块
    app.controller('ttaProposalStatusCtrl', function ($scope, $filter, $location, $rootScope, $state, $stateParams, SIEJS, httpServer, URLAPI, iframeTabAction, setNewRow,lookupCode,$timeout) {
        $scope.searchParam = {};
        var date=new Date;
        $scope.searchParam.year = date.getFullYear();
        $scope.processField = [] ;
        $scope.processChildField = [] ;
        $scope.proposalProcessTable = {};
        $scope.processChildFieldValue = [ "YEAR"
            ,"IS_SUBMIT_PROPOSAL"
            ,"NO_SUBMIT_REASON"
            ,"IS_SUBMIT_CONTRACT"
            ,"ISMAJOR"
            ,"SUPPLIER_CODE"
            ,"SUPPLIER_NAME"
            ,"BRANDNAME"
            ,"OWNER_GROUP_NAME"
            ,"TERMS_CLASS_NAME"
            ,"SALETYPENAME"
            ,"AGREEMENT_EDITION"
            ,"ORDERNOVC"
            ,"PROPOSALSTATUSNAME"] ;
        //获取字典信息
        $scope.lookupCodeParty = window.parent.saafrootScope.lookupCode || $rootScope.lookupCode;
        if (!$scope.lookupCodeParty) {
            lookupCode(function (res) {
                $scope.lookupCodeParty = $rootScope.lookupCode = res.data;
            });
        }

        if ($scope.lookupCodeParty) {
            //流程字段名称
            $scope.processField = $scope.lookupCodeParty.filter(function (x) {
                return x.lookupType == 'TTA_PROPOSAL_PROCESS_NODE' ;
            });

        }
        function sortLookupCode(a,b){
            return a.lookupCode * 1-b.lookupCode * 1 ;
        }
        $scope.processField.sort(sortLookupCode);
        $scope.processChildFieldValue.push('\'发起人'+ 'A\'');
        $scope.processChildFieldValue.push('\'发起人' + 'C\'');
        for (var i = 0 ; i<$scope.processField.length; i++) {
            $scope.processChildFieldValue.push("'" + $scope.processField[i].meaning + 'B\'');
            $scope.processChildFieldValue.push("'" + $scope.processField[i].meaning + 'C\'');
            $scope.processChildField.push({"id":i + "_" + "1","name":"审批"});
            $scope.processChildField.push({"id":i + "_" + "2" ,"name":"日期"});
        }
        $scope.processChildFieldValue.push("CONTRACT_DATE");
        $scope.dataExport = function() {
            tableToExcel('dataTableId2', '导出数据')
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
            var container = document.getElementById('dataTableId2')
            var thead = container.querySelector('thead');
            var tbody = container.querySelector('tbody');
            var thTr =thead.querySelectorAll('tr');
            var tbTr =tbody.querySelector('tr');
            var theadTr = [].slice.call(thTr);
            var paramsObject = [] ;
            var paramsColWidth = [38, 83, 270, 83, 75, 100, 191, 66, 126, 146, 223, 59, 153, 114, 77, 123, 98, 123, 98, 123, 102, 123, 59, 123, 98, 123, 98, 123, 100, 123, 199, 123, 98, 123, 45, 45, 211, 123, 59, 123, 67, 123, 98, 122, 121] ;
            var tbtds =tbTr.querySelectorAll('td');
            var tbtdsArray = [].slice.call(tbtds);
            if (paramsColWidth.length == 0) {
                tbtdsArray.forEach(function (td) {
                    paramsColWidth.push(td.getBoundingClientRect().width);
                });
            }

            theadTr.forEach(function(tr) {
                var qths =tr.querySelectorAll('th');
                var ths = [].slice.call(qths);
                var thsObject = [] ;
                ths.forEach(function (th) {
                    var object = {};
                    if (th.colSpan) {
                        object.colSpan = th.colSpan;
                    }
                    if (th.rowSpan){
                        object.rowSpan = th.rowSpan;
                    }
                    object.width = th.getBoundingClientRect().width;
                    object.height = th.getBoundingClientRect().height;
                    object.backgroundColor = th.style.backgroundColor;
                    object.color = th.style.color;
                    object.text = th.innerHTML;
                    thsObject.push(object);
                });
                paramsObject.push(thsObject);
            });

            $scope.searchParam.paramsObject = paramsObject ;
            $scope.searchParam.paramsColWidth = paramsColWidth;

                httpServer.post(URLAPI.ttaProposalStatusExport, {
                        'params': JSON.stringify($scope.searchParam)
                    },
                    function (res) {
                        if (res.status == 'S') {
                            var url = URLAPI.ttaSideAgrtHeaderDownLoad + '?fileId=' + res.data;
                            window.open(url, [""], [""]);//避开因同源策略而造成的拦截
                        } else {
                            SIEJS.alert(res.msg, "error", "确定");
                        }
                    },
                    function (err) {
                        SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                    }
                );

            // var uri = 'data:application/vnd.ms-excel;base64,';
            // var template = '<html xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel"' +
            //     'xmlns="http://www.w3.org/TR/REC-html40"><head><meta charset="UTF-8"><!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet>'
            //     + '<x:Name>{worksheet}</x:Name><x:WorksheetOptions><x:DisplayGridlines/></x:WorksheetOptions></x:ExcelWorksheet></x:ExcelWorksheets>'
            //     + '</x:ExcelWorkbook></xml><![endif]-->' +
            //     ' <style type="text/css">' +
            //     'table th {' +
            //     'border: 1px solid #ffffff;' +
            //     ' }' +
            //     '</style>' +
            //     '</head><body ><table class="excelTable">{table}</table></body></html>';
            // if (!tableid.nodeType) tableid = document.getElementById(tableid);
            // var ctx = {worksheet: sheetName || 'Worksheet', table: tableid.innerHTML};
            // //document.getElementById("excelOut").href = uri + base64(format(template, ctx));
            // window.open(uri + base64(format(template, ctx)));
        }


        //*************************************proposalSummary js start ***********************************************************//
        $scope.dataTable = {};
        $scope.params = {
            systemCode:window.appName
        }
        $scope.processNodeList = [];
        $scope.processDataList = [];
        $scope.searchDataTable = function () {
            //第二部分数据

            //第一部分数据
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
        };
        $scope.proposalStatusAfterGetData  = function () {
            $scope.setTtaContractLine('proposalStatusParentId');
            $scope.getHeight();
        };
        $scope.setTtaContractLine = function (id){
                var w = jQuery(window);
                var _top = jQuery("#" + id).offset().top * 1;
                var _h = w.innerHeight();
                jQuery("#" + id).css("height", (_h - _top) + "px");
                w.bind('resize', function () {
                    jQuery("#" + id).css("height", (w.innerHeight() - _top) + "px");
                    $timeout(function () {
                        $scope.$apply();
                    }, 100)
                });

            return (w.innerHeight() - _top);
        } ;
        $scope.getHeight = function (){
            //获取div 的实际高度
            $timeout(function () {
                var divFix = document.getElementById('proposalStatusId') ;
                var divParentFixed = document.getElementById('proposalStatusParentId') ;
                var rect = divFix.getBoundingClientRect();
                var rectParent = divParentFixed.getBoundingClientRect();
                if ((rect.height/rectParent.height) >= 0.9){
                    divFix.style.height = 90+'%' ;
                }else{
                    divFix.style.height = 90+'%' ;
                }
            }, 200)
        }
        $scope.searchDataTable();
        //1.第一部分汇总数据导出
        $scope.summaryDataExport = function() {
            summaryTableToExcel('dataTableId', '导出数据')
        }
        function summaryTableToExcel(tableid, sheetName) {
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
        //*************************************proposalSummary js end ***********************************************************//


    });
});
