/**
 * Created by Administrator on 2018/3/27.
 */
'use strict';
define(['app'], function (app) {
   app.controller('reportExportRecordCtrl', function ($scope, $filter, $location, $rootScope, $state, $stateParams, SIEJS, httpServer, URLAPI, iframeTabAction,$compile,$timeout, validateForm) {

        $scope.params = {};
        $scope.params.reportType = $stateParams.reportType;
        $scope.currentData = {};
        $scope.groupData = [];

        //查询部门信息
       if ($stateParams.reportType && ($stateParams.reportType == "6" || $stateParams.reportType == "7" ||
           $stateParams.reportType == "8" || $stateParams.reportType == "9" || $stateParams.reportType == "10")){
           httpServer.post(
               URLAPI.findGroup, {
                   'params': JSON.stringify({
                       departmentType:"30"
                   })
               },
               function (res) {
                   if (res.status == 'S') {
                       $scope.groupData = res.data;
                   }
                   else {
                       SIEJS.alert(res.msg, "error", "确定");
                   }
               },
               function (err) {
                   SIEJS.alert('数据获取失败！', "error");
               }
           );
       }

        $scope.btnNew = function () {
            $('#addSourceType').modal('show');
        };
       //生成excel
       $scope.addInfoFour = function () {
           $scope.AddData.type = '4';
           httpServer.post(
               URLAPI.findTtaVSActualAchieveRateReport,
               {
                   'params': JSON.stringify($scope.AddData)
               },
               function (res) {
                   if (res.status.toLowerCase() == 'e') {
                       SIEJS.alert(res.msg, "error");
                   } else {
                       SIEJS.alert("生成中，请稍后刷新", "success");
                       $scope.AddData = null;
                   }

                   $("#AddModal").modal('hide');
               },
               function (err) {
                   SIEJS.alert('新增失败！', "error", "确定");
               }
           );


       };

       //生成excel reprot 12
       $scope.addInfoTwelve = function () {
           $scope.AddDataTwelve.type = '12';
           httpServer.post(
               URLAPI.saveQueque,
               {
                   'params': JSON.stringify($scope.AddDataTwelve)
               },
               function (res) {
                   if (res.status.toLowerCase() == 'e') {
                       SIEJS.alert(res.msg, "error");
                   } else {
                       SIEJS.alert("生成中，请稍后刷新", "success");
                       $scope.AddDataTwelve = null;
                       $scope.dataTable.search(1);
                   }

                   $("#AddModal12").modal('hide');
               },
               function (err) {
                   SIEJS.alert('新增失败！', "error", "确定");
               }
           );


       };
        /*
        $scope.btnModify = function () {
            if (!$scope.dataTable.selectRow) {
                SIEJS.alert('请选择一条数据', 'warning');
                return;
            }
        };
        $scope.btnDel = function () {
            if (!$scope.dataTable.selectRow) {
                SIEJS.alert('请选择一条数据', 'warning');
                return;
            }
        };
        $scope.save = function (invalid) {
            if (invalid) {
                SIEJS.alert('请检查必填项', 'warning');
                return;
            }
            log($scope.addParams)
        }
        */


        //生成
       $scope.btnCreate = function() {
           $scope.currentData.queryGroupByVimTop = null;
           $scope.currentData.queryType = null;
           $scope.currentData.queryGroupByVimTop = null;
           $scope.currentData.endDate = null;
            var report = $stateParams.reportType;
            if(report == "1") {
                $('#selectTtaReportOne').modal('toggle'); // 第1个OI报表数据新增
            } else if (report == "2") {
                $('#selectTtaOiGP').modal('toggle'); // 第2个OI报表数据新增
            } else if (report == "3") {

            } else if (report == "4") {
                $("#AddModal").modal('show');
            } else if (report == '6' || report == '7' || report == '8' || report == '9' || report == '10') {
                switch (report) {
                    case "6":
                        $scope.currentData.type = "6";
                        $scope.currentData.oiFeeType = "4";
                        $scope.modelTitle= "6. YTD  ABOI YOY Comparison_sample";
                        break;
                    case "7":
                        $scope.currentData.type = "7";
                        $scope.currentData.oiFeeType = "3";
                        $scope.modelTitle= "7. YTD  BEOI YOY Comparison_sample";
                        break;
                    case "8":
                        $scope.currentData.type = "8";
                        $scope.currentData.oiFeeType = "5";
                        $scope.modelTitle= "8. YTD  SROI YOY Comparison_sample";
                        break;
                    case "9":
                        $scope.currentData.type = "9";
                        $scope.currentData.oiFeeType = "6";
                        $scope.modelTitle= "9. YTD  OtherOI YOY Comparison_sample";
                        break;
                    case "10":
                        $scope.currentData.type = "10";
                        $scope.currentData.oiFeeType = "9";
                        $scope.modelTitle= "10. YTD  Total OI YOY Comparison_sample";
                        break;
                }
                $("#selectTtaReportSix").modal('toggle');
            } else if (report == "12") {
                $("#AddModal12").modal('show');
            }
       };

       /**
        * 第2个报表 2.Top Supplier Report_sample
        */
       $scope.selectSecendReturn = function(key) {
           $scope.currentData.type = "2";
           httpServer.post(
               URLAPI.saveQueque, {
                   'params': JSON.stringify( $scope.currentData)
               },
               function (res) {
                   if (res.status == 'S') {
                       $('#selectTtaOiGP').modal('toggle'); // 第5个OI报表数据新增
                       SIEJS.alert(res.msg, 'success', '确定');
                       $scope.dataTable.search(1);
                   }
                   else {
                       SIEJS.alert(res.msg, "error", "确定");
                   }
               },
               function (err) {
                   SIEJS.alert('数据获取失败！', "error");
               }
           );
       };

       //1. All Supplier Performance Report_sample.xlsx
       $scope.selectFirstReturn = function(key) {
           $scope.currentData.type = "1";
           httpServer.post(
               URLAPI.saveQueque, {
                   'params': JSON.stringify( $scope.currentData)
               },
               function (res) {
                   if (res.status == 'S') {
                       $('#selectTtaReportOne').modal('toggle'); // 第5个OI报表数据新增
                       SIEJS.alert(res.msg, 'success', '确定');
                       $scope.dataTable.search(1);
                   }
                   else {
                       SIEJS.alert(res.msg, "error", "确定");
                   }
               },
               function (err) {
                   SIEJS.alert('数据获取失败！', "error");
               }
           );
       };

       $scope.selectSixReturn = function(form) {
           if (!validateForm(form)) {
               return;
           }
           httpServer.post(
               URLAPI.checkOiReportYearMonth, {
                   'params': JSON.stringify( $scope.currentData)
               },
               function (res) {
                   if (res.status == 'S') {
                       var yearMonth = res.data.yearMonth;
                       if (yearMonth && yearMonth !== "") {
                           SIEJS.confirm("提示", '系统中不存在【' + yearMonth+ '】月份的数据,是否继续？', '确定', function () {
                               $scope.saveQueque();
                           });
                       } else {
                           $scope.saveQueque();
                       }
                       //$('#selectTtaReportSix').modal('toggle');
                       //SIEJS.alert(res.msg, 'success', '确定');
                       //$scope.dataTable.search(1);
                   } else {
                       SIEJS.alert(res.msg, "error", "确定");
                   }
               },
               function (err) {
                   SIEJS.alert('数据获取失败！', "error");
               }
           );
       };

       $scope.saveQueque = function(){
           httpServer.post(
               URLAPI.saveQueque, {
                   'params': JSON.stringify( $scope.currentData)
               },
               function (res) {
                   if (res.status == 'S') {
                       $('#selectTtaReportSix').modal('toggle');
                       SIEJS.alert(res.msg, 'success', '确定');
                       $scope.dataTable.search(1);
                   } else {
                       SIEJS.alert(res.msg, "error", "确定");
                   }
               },
               function (err) {
                   SIEJS.alert('数据获取失败！', "error");
               }
           );
       };

       //选择供应商
       $scope.getSupplierCode = function () {
           $('#supplierCode').modal('toggle')
       };

       $scope.selectSupplierReturn = function (key, value, currentList) {
           $scope.currentData.vendorNbr = currentList[0].supplierCode;
           $scope.currentData.vendorName = currentList[0].supplierName;
       };

       //下载附件事件
       $scope.downFileEvent = function (row) {
           //console.log(msg);
           if (row.fileId == undefined || row.fileId == null){
               SIEJS.alert("当前没有选中一行数据,不能下载",'warning','确定');
               return;
           }
           var url = URLAPI.ttaSideAgrtHeaderDownLoad + '?fileId=' + row.fileId;
           window.open(url, [""], [""]);//避开因同源策略而造成的拦截
       };

        // 显示详情
        $scope.btnDetail=function(item){
            item = item || $scope.dataTable.selectRow;
            httpServer.post(
                URLAPI.findReportAttGenById,{'params': JSON.stringify({reportAttGenRecordId: item.reportAttGenRecordId})},
                function (res) {
                    if (res.status == 'S') {
                        $scope.logDetail = res.data;
                        $("#lookLog").modal("show");
                    }else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                },function (err) {
                    SIEJS.alert('数据获取失败！', "error");
                }
            );
        }
   });

});
