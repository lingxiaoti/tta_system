'use strict';
define(['app', 'pinyin', 'ztree'], function (app, pinyin, ztree) {
    app.controller('plmDeptListInfoCtrl', function ($scope, $filter, $location, $rootScope, $state, $stateParams, SIEJS, httpServer, URLAPI, iframeTabAction, setNewRow) {

        $scope.params = {};
        $scope.headerData = {};

        $scope.btnDetail = function (data) {
            iframeTabAction('部门分类详情：'+data.plmDeptName, 'plmDeptListDetail/' + data.plmDeptListId);
        }

        // $scope.btnNew = function () {
        //     $scope.headerData = {};
        //     $('#locationListDetailId').modal('show');
        // };
        //
        // $scope.btnModify = function () {
        //     $scope.headerData = $scope.dataTable.selectRow;
        //     $('#locationListDetailId').modal('show');
        //
        // };
        //
        // $scope.btnDel = function(){
        //     SIEJS.confirm('删除', '是否确定删除？', '确定', function () {
        //         httpServer.post(URLAPI.deletePlmLocationListInfo, {
        //             'params': JSON.stringify($scope.dataTable.selectRow)
        //         }, function (res) {
        //             if ('S' === res.status) {
        //                 SIEJS.alert('操作成功');
        //                 $scope.dataTable.search(1);
        //
        //             } else {
        //                 SIEJS.alert(res.msg, 'error', '确定');
        //             }
        //         }, function (error) {
        //             SIEJS.alert('保存失败', 'error', '确定');
        //         })
        //     })
        // };
        //
        // $scope.save = function () {
        //
        //     if($scope.headerData.specialProductType===undefined||$scope.headerData.specialProductType===''){
        //         SIEJS.alert('特殊商品类型未填','error','确定');
        //         return;
        //     }
        //     if($scope.headerData.specialProductTypeName===undefined||$scope.headerData.specialProductTypeName===''){
        //         SIEJS.alert('特殊商品类型描述未填','error','确定');
        //         return;
        //     }
        //
        //     httpServer.post(URLAPI.savePlmLocationListInfo, {
        //         'params': JSON.stringify($scope.headerData)
        //     }, function (res) {
        //         if ('S' === res.status) {
        //             SIEJS.alert('操作成功');
        //             $('#locationListDetailId').modal('hide');
        //             $scope.dataTable.search(1);
        //
        //         } else {
        //             SIEJS.alert(res.msg, 'error', '确定');
        //         }
        //     }, function (error) {
        //         SIEJS.alert('保存失败', 'error', '确定');
        //     })
        // }








    });
});
