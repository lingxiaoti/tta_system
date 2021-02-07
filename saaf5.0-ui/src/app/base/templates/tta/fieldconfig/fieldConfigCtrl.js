/**
 * Created by dengdunxin on 2018/1/18.
 */
'use strict';
define(['app', 'pinyin', 'ztree'], function (app, pinyin) {
    app.controller('fieldConfigCtrl', function ($timeout, $filter, resourceSave, resourceDel, $scope, $http, $location,
                                             buttonList, $rootScope, $state, $stateParams, setNewRow, SIEJS, httpServer, URLAPI, iframeTabAction) {

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



        $scope.funcData={};
        $scope.selectedBtn = function (a,b,currentList) {
            $scope.addParams.sourceFieldName = currentList[0].sourceFieldName;
            $scope.addParams.sourceFieldRemark = currentList[0].sourceFieldRemark;
        }


        //选择来源字段
        $scope.buttonLov = function () {
            //  $scope.areaComponent = e;
            $('#buttonLov').modal('toggle')
        };


        $scope.btnModify = function () {
            $scope.isModify=true;
            var row = $scope.dataTable.selectRow;
            $scope.addParams = angular.copy(row);
            $('#addResource').modal('toggle')
        }

   /*     $scope.btnDel = function (item) {
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
        }*/

        //
        $scope.save = function (invalid) {
            if(invalid){
                SIEJS.alert('请检查必填项', 'error', '确定');
                return;
            }
            httpServer.post(URLAPI.saveOrUpdateField, {
                    'params': JSON.stringify($scope.addParams)
                },
                function (res) {
                    if (res.status == 'S') {
                        SIEJS.alert('更新成功');
                        $scope.dataTable.search();
                        $('#addResource').modal('toggle');
                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                },
                function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                }
            );
        }
        ///

    });
});
