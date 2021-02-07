/**
 * Created by dengdunxin on 2018/1/18.
 */
'use strict';
define(['app', 'pinyin', 'ztree'], function (app, pinyin) {
    app.controller('resourceCtrl', function ($timeout, $filter, resourceSave, resourceDel, $scope, $http, $location,
                                             buttonList, $rootScope, $state, $stateParams, setNewRow, SIEJS, iframeTabAction) {

        $scope.params = {
            systemCode:window.appName
        }
        $scope.btnNew = function () {
            $scope.isModify=false;
            $scope.funcData={};
            $scope.addParams = {
                resourceType:'10',
                systemCode:appName
            }
            $('#addResource').modal('toggle')
        }

        $scope.menuParams={
            systemCode:appName
        }

        $scope.changeSystemCode=function(item){
           $timeout(function(){
               if ($scope.menuParams.systemCode){
                   $scope.menuId.search();
                   $scope.addParams.menuName="";
                   $scope.addParams.menuId=null;
               }

           })
        }

        $scope.funcData={};
        $scope.selectedBtn = function (a,b,item) {

           $scope.funcData.bbdname=item[0].bbdName;
            $scope.addParams.resourceName = item[0].bbdName;
            $scope.addParams.resourceCode = item[0].bbdCode;
            $scope.addParams.resIcon = item[0].resIcon;
        }

        // 获取基础按钮 列表

    /*    buttonList({}, function (res) {
            $scope.buttonList = res.data;
        })
*/

        $scope.btnModify = function () {
            $scope.isModify=true;
            var row = $scope.dataTable.selectRow;
            $scope.addParams = angular.copy(row);
            $scope.funcData.bbdname=row.resourceName;

            $('#addResource').modal('toggle')
        }

        $scope.btnDel = function (item) {
            item = item || $scope.dataTable.selectRow;

            if (item) {
                SIEJS.confirm('删除', '是否确定删除？', '确定', function () {

                    resourceDel({params: JSON.stringify({id: item.resourceId})}, function (res) {
                        if ('S' == res.status) {
                            $scope.dataTable.search();
                            SIEJS.alert('删除成功');
                        } else {
                            SIEJS.alert(res.msg, 'error', '确定');
                        }
                    }, function (error) {
                        console.error(error);
                        SIEJS.alert('删除失败', 'error', '确定');
                    })

                })
            }
        }


        $scope.save = function (invalid) {

            if(invalid){
                SIEJS.alert('请检查必填项', 'error', '确定');
                return;
            }
            //$scope.addParams.buttonUrl=$scope.addParams.resourceCode;//  buttonURL 与 code一样

            //$scope.addParams.resourceType = parseInt($scope.addParams.resourceType)

            resourceSave({params: JSON.stringify($scope.addParams)},
                function (res) {
                    if ('S' == res.status) {
                        $scope.dataTable.search();
                        $('#addResource').modal('toggle')

                    }
                } )
        }

    });
});
