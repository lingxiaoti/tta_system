'use strict';
define(['app', 'pinyin', 'ztree'], function (app, pinyin, ztree) {
    //app.useModule('angularFileUpload');//按需加载弹出层模块
    app.controller('ttaContractProcessCtrl', function ($scope, $filter, $location, $rootScope, $state, $stateParams, SIEJS, httpServer, URLAPI, iframeTabAction, setNewRow,lookupCode,$timeout) {
        $scope.tab = {
            active: 0,
            nav: ['业务合同书流程进度', '独家协议流程进度'],
            click: function (i) {
                $scope.tab.active = i
            }
        };

        $scope.queryProcess = function(i) {
            $scope.tab.active = i;
        };

        $scope.searchParam = {};
        var date=new Date;
        $scope.searchParam.year = date.getFullYear();
        $scope.processField = [] ;
        $scope.processChildField = [] ;
        $scope.proposalProcessTable = {};
        $scope.exclusiveProcessField = [];
        $scope.exclusiveProcessChildField = [];

        $scope.processChildFieldValue = [
            "FILE_TYPE_NAME"
            ,"ORDER_NO"
            ,"CONTRACT_YEAR"
            ,"CONTRACT_NO"
            ,"ELEC_ORDER_NO"
            ,"IS_MAST"
            ,"VENDOR_CODE"
            ,"VENDOR_NAME"
            ,"BRAND"
            ,"MAJOR_DEPT_DESC"
            ,"SALE_TYPE_NAME"
            ,"AGREEMENT_EDITION"
            ,"PROPOSAL_ORDER_NBR"
        ] ;

        //第二个报表
        $scope.processChildFieldValue2 = [
            "AGRT_TYPE_NAME",//合同类型
            "E_CONTRACT_YEAR",//年度
            "SOLE_AGRT_CODE",//合同编号
            "IS_MAJOR",//主/从供应商
            "VENDOR_NBR",//供应商编号
            "VENDOR_NAME_E",//供应商名称
            "BRAND_E",//品牌_中文
            //"MAJOR_DEPT_DESC",//合同主要负责部门
            "E_MAJOR_DEPT_DESC",//合同主要负责部门
            "PROPOSAL_ORDER_NBR",//Proposal编号
            "PROPOSAL_STATUS"//Proposal审批状态
        ];
        $scope.processChildFieldValue2.push("CONTRACT_SITUATION");//合同情况
        $scope.processChildFieldValue2.push("SOLE_AGRT_CODE_E");//独家协议编号
        $scope.processChildFieldValue2.push("SOLE_STATUS_NAME");// 单据状态
        $scope.processChildFieldValue2.push("CONTRACT_ACCEPT_DATE");//独家协议接收时间
        $scope.processChildFieldValue2.push("SALE_START_DATE");//独家销售起始时间
        $scope.processChildFieldValue2.push("SALE_END_DATE");//独家销售结束时间
        $scope.processChildFieldValue2.push("SYS_START_DATE");//系统实际起始时间
        $scope.processChildFieldValue2.push("SYS_END_DATE");//系统实际结束时间
        $scope.processChildFieldValue2.push("PRODUCT_TYPE");//独家产品类型
        $scope.processChildFieldValue2.push("SALE_REGION");//独家销售地域
        $scope.processChildFieldValue2.push("IS_NEW_SOLE");//新品是否直接成为独家
        $scope.processChildFieldValue2.push("SOLE_IS_SPECIAL");//是否独家产品主要成分显著不同
        $scope.processChildFieldValue2.push("IS_AUTO_DEFERRAL");//是否独家期限自动顺延
        $scope.processChildFieldValue2.push("CHANNEL_TYPE");//独家渠道类别
        $scope.processChildFieldValue2.push("IS_EC_CHANNEL");//是否包含电商渠道
        $scope.processChildFieldValue2.push("IS_EXCEPTION");//是否包含独家渠道例外情形
        $scope.processChildFieldValue2.push("EXCEPTION_REMARK");//独家渠道例外情形具体描述
        $scope.processChildFieldValue2.push("IS_TAKE_BACK_SELL");//是否追回销售
        $scope.processChildFieldValue2.push("IS_SIGN_ACCOUNT");//是否签订转户协议
        $scope.processChildFieldValue2.push("IS_END_ARGT");//独家协议是否已结束
        $scope.processChildFieldValue2.push("IS_PEB");//是否PEB
        $scope.processChildFieldValue2.push("IS_COVERED");//双方是否已盖章
        $scope.processChildFieldValue2.push("E_CONTRACT_OUTPUT_DATE");//合同输出日期
        $scope.processChildFieldValue2.push("E_BIC_REGISTER");//BIC登记日期
        $scope.processChildFieldValue2.push("E_FINANCE_REGISTER");//财务登记日期
        $scope.processChildFieldValue2.push("E_LEGA_REGISTER");//法务登记日期


        //废弃(不使用)2020.11.17
        $scope.exclusiveProcessChildFieldValue=[
            "AGRT_TYPE_NAME"
            ,"E_CONTRACT_YEAR"
            ,"SOLE_AGRT_CODE"
            ,"IS_MAJOR"
            ,"VENDOR_NBR"
            ,"VENDOR_NAME_E"
            ,"BRAND_E"
            ,"MAJOR_DEPT_DESC"
            ,"PROPOSAL_ORDER_NBR"
            ,"PROPOSAL_STATUS"
        ];

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
                return x.lookupType == 'TTA_ELEC_PROCESS_NODE' ;//TTA_ELEC_PROCESS_NODE，TTA_PROPOSAL_PROCESS_NODE
            });

            //TTA_EXCLUSIVE_PROCESS_NODE
            $scope.exclusiveProcessField = $scope.lookupCodeParty.filter(function (x) {
                return x.lookupType == 'TTA_EXCLUSIVE_PROCESS_NODE' ;//TTA_EXCLUSIVE_PROCESS_NODE
            });

        }
        function sortLookupCode(a,b){
            return a.lookupCode * 1-b.lookupCode * 1 ;
        }
        $scope.processField.sort(sortLookupCode);//排序
        $scope.processChildFieldValue.push('\'发起人'+ 'A\'');
        $scope.processChildFieldValue.push('\'发起人' + 'C\'');
        for (var i = 0 ; i<$scope.processField.length; i++) {
            $scope.processChildFieldValue.push("'" + $scope.processField[i].meaning + 'B\'');
            $scope.processChildFieldValue.push("'" + $scope.processField[i].meaning + 'C\'');
            $scope.processChildField.push({"id":i + "_" + "1","name":"审批"});
            $scope.processChildField.push({"id":i + "_" + "2" ,"name":"日期"});
        }
        $scope.processChildFieldValue.push("CONTRACT_OUTPUT_DATE");//合同输出日期
        $scope.processChildFieldValue.push("BIC_REGISTER");//BIC登记日期
        $scope.processChildFieldValue.push("FINANCE_REGISTER");//财务登记日期
        $scope.processChildFieldValue.push("LEGA_REGISTER");//法务登记日期
        $scope.processChildFieldValue.push("PARTY_A_SIGN_TIME");//甲方签署时间
        $scope.processChildFieldValue.push("PARTY_B_SIGN_TIME");//乙方签署时间
        $scope.processChildFieldValue.push("PARTY_C_SIGN_TIME");//丙方签署时间
        $scope.processChildFieldValue.push("BILL_STATUS_NAME");//电子签头部单据状态
        $scope.processChildFieldValue.push("CONTRACT_DATE");//合同年度
        $scope.processChildFieldValue.push("IS_ELEC_FLAG");//是否电子签章
        $scope.processChildFieldValue.push("IS_GM_APPROVE");//是否需要GM审批
        $scope.processChildFieldValue.push("IS_SPECIAL");//是否有合同审批特批申请
        $scope.processChildFieldValue.push("IS_CONVERSION");//甲乙是否换签署位
        $scope.processChildFieldValue.push("STAMP_STATUS_NAME");//合同盖章状

        //把报表1的字段值添加的报表2中
        //angular.forEach($scope.processChildFieldValue, function (data, index) {
        //    $scope.processChildFieldValue2.push(data);
        //});

        //*********************如下为独家协议进度相关的 start****************************************
        $scope.exclusiveProcessField.sort(sortLookupCode);
        $scope.exclusiveProcessChildFieldValue.push('\'发起人'+ 'A\'');
        $scope.exclusiveProcessChildFieldValue.push('\'发起人' + 'C\'');

        $scope.processChildFieldValue2.push('\'发起人'+ 'D\'');
        $scope.processChildFieldValue2.push('\'发起人' + 'F\'');
        for (var j = 0 ; j<$scope.exclusiveProcessField.length; j++) {
            $scope.exclusiveProcessChildFieldValue.push("'" + $scope.exclusiveProcessField[j].meaning + 'B\'');
            $scope.exclusiveProcessChildFieldValue.push("'" + $scope.exclusiveProcessField[j].meaning + 'C\'');
            $scope.exclusiveProcessChildField.push({"id":i + "_" + "1","name":"审批"});
            $scope.exclusiveProcessChildField.push({"id":i + "_" + "2" ,"name":"日期"});

            $scope.processChildFieldValue2.push("'" + $scope.exclusiveProcessField[j].meaning + 'E\'');
            $scope.processChildFieldValue2.push("'" + $scope.exclusiveProcessField[j].meaning + 'F\'');
        }
        $scope.processChildFieldValue2.push("ELEC_ORDER_NO");//电子签章单号
        $scope.processChildFieldValue2.push("PARTY_A_SIGN_TIME");//甲方签署时间
        $scope.processChildFieldValue2.push("PARTY_B_SIGN_TIME");//乙方签署时间
        $scope.processChildFieldValue2.push("PARTY_C_SIGN_TIME");//丙方签署时间
        $scope.processChildFieldValue2.push("BILL_STATUS_NAME");//电子签头部单据状态
        $scope.processChildFieldValue2.push("CONTRACT_DATE");//合同年度
        $scope.processChildFieldValue2.push("IS_ELEC_FLAG");//是否电子签章
        $scope.processChildFieldValue2.push("IS_GM_APPROVE");//是否需要GM审批
        $scope.processChildFieldValue2.push("IS_SPECIAL");//是否有合同审批特批申请
        $scope.processChildFieldValue2.push("IS_CONVERSION");//甲乙是否换签署位
        $scope.processChildFieldValue2.push("STAMP_STATUS_NAME");//合同盖章状态
        $scope.processChildFieldValue2.push("CONTRACT_OUTPUT_DATE");//合同输出日期
        $scope.processChildFieldValue2.push("BIC_REGISTER");//BIC登记日期
        $scope.processChildFieldValue2.push("FINANCE_REGISTER");//财务登记日期
        $scope.processChildFieldValue2.push("LEGA_REGISTER");//法务登记日期
        $scope.processChildFieldValue2.push('\'发起人'+ 'A\'');
        $scope.processChildFieldValue2.push('\'发起人' + 'C\'');
        for (var k = 0 ; k<$scope.processField.length; k++) {
            $scope.processChildFieldValue2.push("'" + $scope.processField[k].meaning + 'B\'');
            $scope.processChildFieldValue2.push("'" + $scope.processField[k].meaning + 'C\'');
        }


        //***如下废弃(2020.11.17)
        $scope.exclusiveProcessChildFieldValue.push("E_CONTRACT_OUTPUT_DATE");//合同输出日期
        $scope.exclusiveProcessChildFieldValue.push("BIC_REGISTER");//BIC登记日期
        $scope.exclusiveProcessChildFieldValue.push("FINANCE_REGISTER");//财务登记日期
        $scope.exclusiveProcessChildFieldValue.push("LEGA_REGISTER");//法务登记日期
        $scope.exclusiveProcessChildFieldValue.push("CONTRACT_SITUATION");//合同情况
        $scope.exclusiveProcessChildFieldValue.push("SOLE_AGRT_CODE_E");//独家协议编号
        $scope.exclusiveProcessChildFieldValue.push("SOLE_STATUS_NAME");// 单据状态
        $scope.exclusiveProcessChildFieldValue.push("CONTRACT_ACCEPT_DATE");//独家协议接收时间
        $scope.exclusiveProcessChildFieldValue.push("SALE_START_DATE");//独家销售起始时间
        $scope.exclusiveProcessChildFieldValue.push("SALE_END_DATE");//独家销售结束时间
        $scope.exclusiveProcessChildFieldValue.push("SYS_START_DATE");//系统实际起始时间
        $scope.exclusiveProcessChildFieldValue.push("SYS_END_DATE");//系统实际结束时间
        $scope.exclusiveProcessChildFieldValue.push("PRODUCT_TYPE");//独家产品类型
        $scope.exclusiveProcessChildFieldValue.push("SALE_REGION");//独家销售地域
        $scope.exclusiveProcessChildFieldValue.push("IS_NEW_SOLE");//新品是否直接成为独家
        $scope.exclusiveProcessChildFieldValue.push("SOLE_IS_SPECIAL");//是否独家产品主要成分显著不同
        $scope.exclusiveProcessChildFieldValue.push("IS_AUTO_DEFERRAL");//是否独家期限自动顺延
        $scope.exclusiveProcessChildFieldValue.push("CHANNEL_TYPE");//独家渠道类别
        $scope.exclusiveProcessChildFieldValue.push("IS_EC_CHANNEL");//是否包含电商渠道
        $scope.exclusiveProcessChildFieldValue.push("IS_EXCEPTION");//是否包含独家渠道例外情形
        $scope.exclusiveProcessChildFieldValue.push("EXCEPTION_REMARK");//独家渠道例外情形具体描述
        $scope.exclusiveProcessChildFieldValue.push("IS_TAKE_BACK_SELL");//是否追回销售
        $scope.exclusiveProcessChildFieldValue.push("IS_SIGN_ACCOUNT");//是否签订转户协议
        $scope.exclusiveProcessChildFieldValue.push("IS_END_ARGT");//独家协议是否已结束
        $scope.exclusiveProcessChildFieldValue.push("IS_PEB");//是否PEB
        $scope.exclusiveProcessChildFieldValue.push("IS_COVERED");//双方是否已盖章
        //***如上废弃(2020.11.17)
        //*******************独家协议进度相关end**********************************


        $scope.dataExport = function() {
            tableToExcel('dataTableId2', '导出数据')
        };
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
        };
        function tableToExcel(tableid, sheetName) {
            var container = document.getElementById('dataTableId2');
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
        }


        //*************************************proposalSummary js start ***********************************************************//
        $scope.dataTable = {};
        $scope.params = {
            systemCode:window.appName,
            reportType:"1" //第1个报表【业务合同书流程进度】
        };
        $scope.exclusiveParams = {
            reportType : "2"//第2个报表【独家协议流程进度（非标&标准）】
        };

        $scope.processNodeList = [];
        $scope.processDataList = [];
        $scope.searchDataTable = function () {
            //第二部分数据

            //第一部分数据
            $scope.params.lookupType = 'TTA_ELEC_PROCESS_NODE';
            httpServer.post(URLAPI.findNodeList,
                // {'params': JSON.stringify({lookupType: 'TTA_ELEC_PROCESS_NODE'})} ,
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
        };
        $scope.searchDataTable();
        //1.第一部分汇总数据导出
        $scope.summaryDataExport = function() {
            summaryTableToExcel('dataTableId', '导出数据')
        };
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


        //*************************************独家货品报表 start********************************************************************************//
        $scope.exclusiveItemParams = {};
        $scope.selectSupplierReturn = function (key, value, currentList) {
            $scope.exclusiveItemParams.vendorNbr = currentList[0].supplierCode;
            $scope.exclusiveItemParams.vendorName = currentList[0].supplierName;
        };
        //*************************************独家货品报表 end********************************************************************************//

        //*************************************独家产品housekeeping start********************************************************************************//
        $scope.exclusiveItemHousekeepTable = {};
        //*************************************独家产品housekeeping end********************************************************************************//
    });
});
