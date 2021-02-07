'use strict';
define(['app'], function (app) {
   app.controller('ytdSceneExportRecordCtrl', function ($scope, $filter, $location, $rootScope, $state, $stateParams, SIEJS, httpServer, URLAPI, iframeTabAction,$compile,$timeout, validateForm) {
        $scope.params = {};
        $scope.params.reportType = $stateParams.sceneType == 'OI' ? "20" :"";
        $scope.currentData = {};
        $scope.groupData = [];

        //查询部门信息
       if ($stateParams.sceneType && ($stateParams.sceneType === "OI" )){
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

        //生成
       $scope.btnCreate = function() {
           $scope.currentData.queryType = null;
           $scope.currentData.groupDimensionality = null;
           $scope.currentData.endDate = null;
            var scene = $stateParams.sceneType;
            if (scene === 'OI') {
                $scope.currentData.type = "20";
                $scope.modelTitle= "OI分摊数据YTD生成";
                $("#selectTtaReportOi").modal('toggle');
            } else if (scene === 'SALES') {

            }
       };

       $scope.selectOiReturn = function(form) {
           if (!validateForm(form)) {
               return;
           }
           httpServer.post(
               URLAPI.saveQueque, {
                   'params': JSON.stringify( $scope.currentData)
               },
               function (res) {
                   if (res.status == 'S') {
                       $('#selectTtaReportOi').modal('toggle');
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
        };

       $scope.btnDel = function () {
           var row = $scope.dataTable.selectRow;
           SIEJS.confirm('您确认要删除吗？', '', '确定', function () {
               httpServer.post(
                   URLAPI.deleteReportAttGenRecord,
                   {
                       'params': JSON.stringify({
                           "reportAttGenRecordId": row.reportAttGenRecordId
                       })
                   },
                   function (res) {
                       if (res.status.toLowerCase() == 'e') {
                           $scope.dataTable.search(1);
                           SIEJS.alert(res.msg, "error");
                       } else {
                           SIEJS.alert(res.msg, "success");
                           $scope.dataTable.search(1);
                       }
                   }
               )
           });
       }

   });

});
