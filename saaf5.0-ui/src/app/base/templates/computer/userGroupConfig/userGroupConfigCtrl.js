'use strict';
define(['app', 'pinyin', 'ztree',], function (app, pinyin, ztree) {
    app.controller('userGroupConfigCtrl', function (tableXlsExport,$timeout, $filter, httpServer, URLAPI, $scope, $http, $location, $rootScope, $state, $stateParams, setNewRow, SIEJS,deleteUserGroupAssignsForGroup,saveUserGroupAssignsForGroup) {
        $scope.groupParams = {
            lookupType:'USER_GROUP',
            systemCode:'PUBLIC',
            deleteFlag:0
        }
        $scope.searchExitUser = function(row){
            $scope.exitUserParams = {
                userGroupCode:row.lookupCode
            }
            $scope.exitUserTable.search()
        }

        $scope.deleteExitUserLine = function(row,index){
            var params = {
                ids:[row.assignId],
                userGroupCode:$scope.groupTable.selectRow.lookupCode
            }
            $scope.exitUserTable.list.splice(index, 1)
            SIEJS.confirm('确认删除', '删除数据请谨慎？','确定',function(){
                if (row.assignId){
                    deleteUserGroupAssignsForGroup(params, function (res) {
                        if (res.status === 'S') {
                            SIEJS.alert('删除成功','success')
                            $scope.exitUserTable.list.splice(index, 1)
                        }else {
                            SIEJS.alert('删除失败','success')
                        }
                    })
                } else {
                    SIEJS.alert('删除成功','success')
                    $scope.exitUserTable.list.splice(index, 1)
                }

            });
        }
        $scope.userTableDataLoad = function () {
            $scope.groupTable.search()
        }
        $scope.btnSave = function(){
            var params = {
                userGroupCode:$scope.groupTable.selectRow.lookupCode,
                users:[]
            }
            var newUserArr = []
            var exitUserArr = []
            $scope.exitUserTable.list.map(function (value) {
                if (!value.assignId){
                    newUserArr.push({userId:value.userId})
                }else {
                    exitUserArr.push({userId:value.userId})
                }
            })
            newUserArr.map(function (value) {
                if (!exitUserArr.find(item => item.userId === value.userId)){
                    params.users.push(value)
                }
            })

            saveUserGroupAssignsForGroup(params, function (res) {
                if (res.status === 'S') {
                    SIEJS.alert('保存成功','success','确认','已自动过滤曾经添加的账号')
                    $scope.exitUserTable.search()
                }else {
                    SIEJS.alert('保存失败','warning')
                }
            })
        }
        $scope.addExitUser = function (row) {
            if (!$scope.exitUserTable.list){
                SIEJS.alert('请选择群组名称','warning')
                return
            }
            $scope.exitUserTable.list.push({userName:row.personName, userId:row.userId})
        }
    });

});
