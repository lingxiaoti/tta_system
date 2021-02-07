'use strict';
define(['app'], function (app) {
    app.controller('msgReceiveCtrl', function ($scope, $location, $rootScope, $state, $stateParams, SIEJS,msgReceiveSaveOrUpdate,msgReceiveDelete) {
        $scope.params = {};
        $scope.btnNew = function () {
            $scope.modalTitle='新增';
            $scope.addParams={'sqlId':''}
            $scope.addParams['enabledFlag'] = '0';
            $scope.addParams['isDelete'] = '0';
            $('#msgReceiveModal').modal('show');
        };
        $scope.btnModify = function () {
            if (!$scope.dataTable.selectRow) {
                SIEJS.alert('请选择一条数据', 'warning');
                return;
            }
            $scope.modalTitle='修改';
            var row = $scope.dataTable.selectRow;
            $scope.addParams = angular.copy(row);
            $('#msgReceiveModal').modal('show');


        };
        $scope.btnSave=function(){
            msgReceiveSaveOrUpdate({params: JSON.stringify($scope.addParams)}, function (res) {
                if (res.status == "S") {
                    $('#msgReceiveModal').modal('toggle')
                    SIEJS.alert(res.msg, 'success', '确定');
                    $scope.dataTable.search()
                } else {
                    SIEJS.alert(res.msg, 'error', '确定');

                }

            })
        };
        $scope.btnDel = function () {
            if ($scope.dataTable.selectRowList.length == 0){
                SIEJS.alert('请选择一条数据','warning');
                return;
            }
            var idList = [];
            $scope.dataTable.selectRowList.map(function (item, index) {
                idList.push({
                    id: item.sqlId
                })
            });

            SIEJS.confirm('删除', '是否确定删除？', '确定', function () {
                msgReceiveDelete({
                    'params': JSON.stringify(
                        {idDetails: idList}
                    )
                }, function (res) {
                    if(res.status=='S'){
                        $scope.dataTable.search(1,null,true); // 清除之前选择的数据
                        $scope.dataTable.selectRowList=[];
                        SIEJS.alert('删除成功','success','确定');

                    }else{
                        SIEJS.alert('删除失败','error');
                    }

                });
            })
        };
        $scope.save = function (invalid) {
            if (invalid) {
                SIEJS.alert('请检查必填项', 'warning');
                return;
            }
            log($scope.addParams)
        }
    });
});
