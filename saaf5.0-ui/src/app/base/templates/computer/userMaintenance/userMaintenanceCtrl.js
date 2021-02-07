/**
 * Created by 31502 on 9/9 0009.
 */
define(["app","webconfig"],function(app,webconfig){
    app.controller('userMaintenanceCtrl', function($scope,$rootScope,$state,$stateParams,httpServer,URLAPI) {

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
        $scope.export=function(){

            var dataLenged=$scope.$eval($("div[base-data-table]").attr('data-legend'))
            var url=URLAPI[$("div[base-data-table]").attr('data-url')]
            var labelName=[]
            var name=[]
            for(var i=0;i<dataLenged.length;i++){
                labelName.push(dataLenged[i]['name'])
                name.push(dataLenged[i]['value'])
            }
            var p={
                exportURL:url,
                params: {},
                xlsParams: {"sheetName":'ABC',labelName:labelName,name:name},
                pageIndex: 1,
                pageRows: webconfig.exportPageRows,
                exportExcel: 'Y',
                appName:appName
            }
            angular.forEach(window.parent.saafrootScope.saafHeadTab, function (data,index) {
                if(data.active){
                    p.xlsParams.sheetName=data.name
                }
            })
            //{"sheetName":"执行事务补偿","labelName":["角色名称2","角色编号","系统编码","角色描述"],"name":["userName","row.userFullName","systemCode","roleDesc"]}

            console.log($scope.$parent)
            sessionStorage.setItem("downloadParams",JSON.stringify(p))
        }


    });


})
