/**
 * Created by 31502 on 9/9 0009.
 */
define(["app"],function(app){
    app.controller('exportCtrl', function($scope,$rootScope,$state,$stateParams,httpServer) {

        $scope.params={};
        $scope.userTable = {};




        //表格单击
        $scope.click = function(index){
            $scope.userId = $scope.userTable.data.data[index].userId;
            alert($scope.userId);
        }

        //修改
        $scope.edit = function(){
            if($scope.userTable.data.selectRow==null) {
                JS.alert('请您先选中要修改的行!!');
                return;
            }
            userId = $scope.userTable.data[$scope.userTable.data.selectRow].userId ;
            $state.go("home.userMaintenanceDetail",{userId:userId,status:'e'});
        }


    });


})
