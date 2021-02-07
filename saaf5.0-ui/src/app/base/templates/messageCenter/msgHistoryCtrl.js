'use strict';
define(['app'], function (app) {
    app.controller('msgHistoryCtrl', function ($scope, $location, $rootScope, $state, $stateParams, SIEJS,msgHistoryDelete) {
        $scope.params={};
        $scope.userRespList=JSON.parse(localStorage[window.appName + '_successLoginInfo']).userRespList;
        $scope.buData=[];
        angular.forEach($scope.userRespList,function(data,index){
            if(data.systemCode==window.appName){
                $scope.buData=data.orgBeans;
            }
        })
        $scope.btnDel=function(){
            if ($scope.dataTable.selectRowList.length == 0){
                SIEJS.alert('请选择一条数据','warning');
                return;
            }
            var idList = [];
            $scope.dataTable.selectRowList.map(function (item, index) {
                idList.push({
                    id: item.msgHistoryId
                })
            });

            SIEJS.confirm('删除', '是否确定删除？', '确定', function () {
                msgHistoryDelete({
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
        $scope.btnSave=function(){

        }
        $scope.save=function(invalid){
            if (invalid){
                SIEJS.alert('请检查必填项','warning');
                return;
            }
            log($scope.addParams)
        }


    });
});
