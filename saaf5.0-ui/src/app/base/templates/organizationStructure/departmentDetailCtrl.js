/**
 * Created by hmx on 2018/9/6.
 */
'use strict';
define(['app'],function(app) {
    app.controller('departmentDetailCtrl',['$scope', 'httpServer', 'URLAPI', 'SIE.JS','$stateParams','$timeout',
        function($scope,httpServer,URLAPI,SIEJS,$stateParams,$timeout) {

            $scope.respId = $stateParams.respId;
            $scope.centerName = '';
            /**
             * 获取部门
             * @param id
             */
            $scope.getDepartmentDetail = function(id) {
                httpServer.post(URLAPI.findDeptPagination, {
                    params: JSON.stringify({
                        respId: $stateParams.respId,
                        departmentId: id
                    })
                },function(res) {
                    console.log(res)
                    if(res.status === 'S') {
                        $scope.department = res.data[0];
                        $scope.centerFunc();
                    }else {
                        SIEJS.alert(res.msg,'error','确定');
                    }
                },function(err) {
                    SIEJS.alert('获取数据失败1','error','确定');
                })
            }

       /*     $scope.centerFunc = function () {
                httpServer.post(URLAPI.findCuxBcFinCostcenterList, {
                    params: JSON.stringify({
                        respId: $stateParams.respId,
                        glAccount: $scope.department.costCenter
                    })
                },function(res) {
                    if(res.status === 'S') {
                        console.log(res.data);
                        $scope.centerName = res.data[0].glAccountDesc;
                    }else {
                        SIEJS.alert(res.msg,'error','确定');
                    }
                },function(err) {
                    SIEJS.alert('获取数据失败2','error','确定');
                })
            }*/
            /**
             * 保存
             */
            $scope.btnSave = function() {
                $scope.department.respId = $stateParams.respId;

                if($scope.department.parentDepartmentId == undefined || $scope.department.parentDepartmentId == null) {
                    $scope.department.departmentLevel = 0;
                }

                httpServer.post(URLAPI.saveOrUpdateBaseDepartment,{
                    params: JSON.stringify($scope.department)
                },function(res) {
                    console.log(res);
                    if(res.status === 'S') {
                        // $scope.department = res.data;
                        $scope.getDepartmentDetail(res.data.departmentId);
                        SIEJS.alert('保存成功');
                    }else {
                        SIEJS.alert(res.msg,'error','确定');
                    }
                },function(err) {
                    SIEJS.alert('保存失败','error','确定');
                })
            }

            /**
             * 确定选择上级部门
             * @param key
             * @param value
             * @param currentList
             */
            $scope.selectUpDept = function(key, value, currentList) {
                $scope.department.parentDepartmentName = value;
                $scope.department.parentDepartmentId = key;
                $scope.department.departmentLevel = parseInt(currentList[0].departmentLevel) + 1;
            }
            /**
             * 确定选择成本中心
             * @param key
             * @param value
             * @param currentList
             */
            $scope.selectCost = function(key, value, currentList) {
                $scope.department.costCenter = key;
                $scope.centerName = value;
            }
            /**
             * 清除选择上级部门
             */
            $scope.clearDeptSel = function() {
                console.log(123)
                $scope.department.parentDepartmentName = '';
                $scope.department.parentDepartmentId = '';
                $scope.department.departmentLevel = 0;
            }
            /**
             * 清除成本中心
             */
            $scope.clearCostSel = function() {
                $scope.department.costCenter = '';
                $scope.centerName = '';
            }


            /**
             * 切换是否生效
             * @param str
             */
            $scope.changeEnableFlag = function(str) {
                $scope.department.enableFlag = str;
                // if(str === 'Y') {
                //     $scope.department.dateTo = '2099-12-31';
                // }else {
                //     var date = new Date();
                //     var thisDate = date.getFullYear() + '-'
                //         + ((date.getMonth() + 1)>10?(date.getMonth() + 1):('0'+(date.getMonth()+1))) + '-'
                //         + (date.getDate()>10?date.getDate():('0'+date.getDate()));
                //
                //     $scope.department.dateTo = thisDate;
                // }
            }

            /**
             * 监听失效时间  修改是否生效状态
             */
            // $scope.watchDateTo = $scope.$watch('department.dateTo', function(nval,lval) {
            //     if(nval != lval) {
            //         var date = new Date();
            //         var thisDate = date.getFullYear() + '/'
            //             + ((date.getMonth() + 1)>10?(date.getMonth() + 1):('0'+(date.getMonth()+1))) + '/'
            //             + (date.getDate()>10?date.getDate():('0'+date.getDate()));
            //
            //         var setDate = new Date($scope.department.dateTo.replace("-","/"));
            //         if(setDate <= new Date(thisDate)) {
            //             $scope.department.enableFlag = 'N';
            //         }
            //     }
            // },true)

            $scope.$on("$destroy", function () {
                $scope.watchDateTo(); //　销毁监听
            })


            if($stateParams.itemId != '-1') {
                $scope.getDepartmentDetail($stateParams.itemId);
            }else {
                $scope.department = {
                    enableFlag: 'Y',
                    dateTo: '2099-12-31'
                }
            }
        }])
})
