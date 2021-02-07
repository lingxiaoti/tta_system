/**
 * Created by Administrator on 2018/5/11.
 */
'use strict';
define(['app'], function (app) {
    app.controller('processProductListCtrl', function ($scope, $location,$timeout, $rootScope, $state, $stateParams, SIEJS,httpServer,URLAPI) {
        $scope.roleListTable = {};
        $scope.roleListParams = {};
        $scope.roleList = {};
        $scope.expressionTypes = ['ASSIGN','SQL','URL'];
        $scope.btnNew = function () {
            $('#addSourceType').modal('show');
            $scope.action = 'add';
        };
        $scope.btnModify = function () {
            if (!$scope.dataTable.selectRow) {
                SIEJS.alert('请选择一条数据', 'warning');
                return;
            }
            $scope.action = 'modify';
        };
        $scope.btnDel = function () {
            if (!$scope.roleListTable.selectRow) {
                SIEJS.alert('请选择一条数据', 'warning');
                return;
            }
            $scope.submitbtnDel()
        };
        $scope.save = function (invalid) {

            if (invalid) {
                SIEJS.alert('请检查必填项', 'warning');
                return;
            }
            log($scope.addParams)
        }

        //点击角色行事件
        $scope.clickRoleListRow = function(item) {
        	$scope.RoleProcessTable.selectRowList=[];
            $scope.role = angular.copy(item);
            //alert($scope.role.useridList);
            $scope.action='detail';

            $scope.myProcessParams =  {
                userType:$scope.role.userType
            }

            $scope.RoleProcessTable.search(1);
  
            
            
          
            //$scope.clickrow(item);
            
        }
        
        //$scope.clickrow=function(item)
        //{
        	//$scope.RoleProcessTable.search(1); 
        //}
        

        //选择表达式类型
        $scope.changeExpressionType = function () {
            $scope.role.handlerExpression = '';

        }

        //新增角色
        $scope.btnNew = function() {
            $scope.action='new';
            $scope.roleListTable.cancel();

            $scope.role = {
                handlerExpressionType: 'SQL',
                //roleKey: (new Date()).getTime()
            }
        }

        //选择操作人
        $scope.showUserModal = function () {
            angular.element('#userFormModal').modal('show');
            $scope.userTable.search();
        }

        //确定选中操作人
        $scope.confirmUser = function() {
            $scope.role.handlerExpression = $scope.selectUserList.value.join(',');
            angular.element('#userFormModal').modal('hide');
        }

        //保存
        $scope.btnSave = function() {
        	var userIdstr="";
            angular.forEach($scope.RoleProcessTable.selectRowList,function(obj,rowindex){
          	 userIdstr+=obj.userId+",";
            });
            var useridlist=userIdstr.substring(0,userIdstr.length-1);
            var id=$scope.role.id;
            var name=$scope.role.name;
            httpServer.post(URLAPI.updateProcessList,{
                params: JSON.stringify({
                   id:id,
                   userlist:useridlist
                })
            },function(res) {
                if(res.status === 'S') {
                    SIEJS.alert('操作成功');
                   
                    $scope.RoleProcessTable.search(1);
                    $scope.roleListTable.search(1);
                      
                }else {
                    SIEJS.alert(res.msg,'warning');
                }
            },function(err) {
                console.error(err);
                SIEJS.alert('服务请求异常', 'error');
            })
            
        }

        //删除角色
        $scope.submitbtnDel = function() {
            SIEJS.confirm('删除角色','是否确定删除该角色？','确定',function() {
                log($scope.p)
                httpServer.post(URLAPI.processRoleDelete,{
                    params: JSON.stringify({
                        roleIds: [$scope.roleListTable.selectRow.roleId]
                    })
                },function(res) {
                    if(res.status === 'S') {
                        SIEJS.alert('删除成功');
                        $scope.roleListTable.search();
                        $scope.role = false;
                    }else {
                        SIEJS.alert(res.msg,'warning');
                    }
                },function(err) {
                    console.error(err);
                    SIEJS.alert('服务请求异常', 'error');
                })
            })
        }

        $scope.submitRole = function(data) {
            httpServer.post(URLAPI.processRoleSave, {
                params: JSON.stringify(data)
            },function(res) {
                if(res.status === 'S') {
                    $scope.roleListTable.search();
                    $scope.role = false;
                    SIEJS.alert('保存成功');
                }else {
                    SIEJS.alert(res.msg,'error');
                }
            },function(err) {
                console.error(err);
                SIEJS.alert('服务请求异常', 'error');
            })
        }

        $scope.loadNum = 0;
        $scope.loadData= function(table) {
            if ($scope.loadNum===0){
                table.rowClick(0,null);
         
                
            }
            $scope.loadNum ++;

        }
        
        $scope.loadData1= function(table) {
        	  if($scope.role.useridList)
          	{
          	 if($scope.RoleProcessTable.list){
               var userliststr=$scope.role.useridList.replace(/\s*/g,"");
    
                  angular.forEach($scope.RoleProcessTable.list,function(obj,rowindex){
                             if(userliststr.toString().indexOf(obj.userId.toString())!=-1)
                          	   { 
                            	 obj.checked=true;
                          	   $scope.RoleProcessTable.selectRowList.push(obj);
                          	  
                          	   }

                     });
              	
          	 }
          	} 

        }
        



    });
});
