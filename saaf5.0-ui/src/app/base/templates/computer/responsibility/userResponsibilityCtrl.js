/**
 * Created by houxingzhang on 2018-01-08.
 */
'use strict';
define(['app'], function (app) {
    app.controller('userResponsibilityCtrl', function ($scope, $timeout, SIEJS, $location, $rootScope, $state, $stateParams,
                                                       userRespList, userRespSave) {
        //do something here

        $scope.respParams={
            systemCode: appName,
            isEfficacious:true
        }
        $scope.selectRowList = {};// 当前已选择的行列表

        $scope.resplist = {};// 职责列表
        $scope.userlist = {};// 用户列表
        $scope.userTable = {}
        /*监听用户列表选择的值*/
        $scope.$watch('userTable.selectRowList', function (newVal, oldVal) {
            if (newVal === oldVal || !newVal) return;

            $timeout(function () {
                $scope.selectRowList = {
                    data: newVal,
                    count: newVal.length
                }
            })
        }, true);

        // 移除
        $scope.delrow = function (item) {

/*            if (item.respUserId){

            }else{


            }*/

            var id = item.userId;
            var index = -1;
            for (var n in  $scope.selectRowList.data) {
                var row = $scope.selectRowList.data[n];
                if (row.userId === id) {
                    index = n;
                    break;
                }
            }
            $scope.selectRowList.data.splice(index, 1);// 删除当前选中


        }

        // 保存数据
        $scope.btnSave = function () {

            if (!$scope.resplist.key) {
                SIEJS.alert('请选择职责', 'warning');
                return;
            }
            if ($scope.selectRowList.data.length === 0) {
                SIEJS.alert('请分配用户', 'warning');
                return;
            }

            var userId=[];
            for (var n in $scope.selectRowList.data){
                var item = $scope.selectRowList.data[n];
                userId.push(item.userId);
            }

            var p = {
                params: JSON.stringify({
                    responsibilityIds: [$scope.resplist.key],
                    userIds: userId,
                    actionType: 0
                })
            };

            console.log(p);//查看一下参数
            // 保存入库
            userRespSave(p, function (res) {
              /*  $scope.selectRowList = {
                    data: [],
                    count: 0
                };*/
                $scope.userTable.search(1);// 用户列表重载
            });
        }

        $scope.changeSystemCode=function(){
            $scope.resTable.search(1);
            $scope.userTable.selectRowList=[];
            $scope.resTable.selectRow=null;
        }

        // 根据职责查询用户
        $scope.userRespList = function (item) {
            var p = {
                params: JSON.stringify({
                    responsibilityId: item.responsibilityId
                })
            }
            userRespList(p, function (res) {
                if (res.status==='S'){
                    $scope.userTable.selectRowList = res.data;
                    $scope.userTable.search(1,null,false);// 重载
                }
            });
        }

    });
});
