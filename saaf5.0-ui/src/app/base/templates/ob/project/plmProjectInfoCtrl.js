'use strict';
define(['app', 'pinyin', 'ztree'], function (app, pinyin, ztree) {
    app.controller('plmProjectInfoCtrl', function ($scope, $filter, $location, $rootScope, $state, $stateParams, SIEJS, httpServer, URLAPI, iframeTabAction, setNewRow) {

        $scope.params = {};
        $scope.userType = JSON.parse(sessionStorage[appName + '_successLoginInfo']).userType;
        $scope.userId = JSON.parse(sessionStorage[appName + '_successLoginInfo']).userId;

        $scope.discardReason = "";



        $scope.restore = function(){
            $scope.params = {};
            if($scope.userType==='60'){
                $scope.params.supplierName = JSON.parse(sessionStorage[appName + '_successLoginInfo']).userName;
            }
        };
        $scope.restore();


        $scope.btnNew = function () {
            iframeTabAction('新增项目', 'plmProjectDetail/')
        };

        $scope.btnModify = function () {
            var row = $scope.dataTable.selectRow;
            iframeTabAction('项目详情：'+row.projectName, 'plmProjectDetail/' + row.plmProjectId)
        };

        $scope.btnDetail = function (data) {
            iframeTabAction('项目详情：'+data.projectName, 'plmProjectDetail/' + data.plmProjectId);
        };

        $scope.btnDiscard = function () {
            $scope.discardReason = "";
            $scope.findProductDetail();
        };

        $scope.btnDel = function(){
            if($scope.userId!==$scope.dataTable.selectRow.createdBy){
                SIEJS.alert('无权删除所选单据','error','确定');
                return;
            }

            SIEJS.confirm('删除', '是否确定删除？', '确定', function () {
                var params = angular.copy($scope.dataTable.selectRow);
                httpServer.post(URLAPI.deletePlmProjectInfoInfo, {
                        'params': JSON.stringify(params)
                    },
                    function (res) {
                        if (res.status == 'S') {
                            $scope.dataTable.search(1);
                            SIEJS.alert(res.msg, 'success','确定');
                        } else {
                            SIEJS.alert(res.msg, "error", "确定");
                        }
                    },
                    function (err) {
                        SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                    }
                );
            })

        };

        //查询是否存在产品明细行
        $scope.findProductDetail = function () {

            httpServer.post(URLAPI.findPlmProjectProductDetailInfo, {
                    'params': JSON.stringify({
                        productBillStatus_in:"COMPLETED,TOBEADDED",
                        plmProjectId:$scope.dataTable.selectRow.plmProjectId
                    })
                },
                function (res) {
                    if (res.status == 'S') {
                        if(res.data.length!==0){
                            SIEJS.alert("所选行有开发完成/开发完成待补充状态的产品，禁止作废", 'error', '确定');
                            return;
                        }
                        $('#discardModal').modal('show');
                        //$scope.discardProject();
                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                },
                function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                }
            );
        };

        $scope.discardProject = function () {
            SIEJS.confirm('作废', '是否确定作废？', '确定', function () {
                var params = angular.copy($scope.dataTable.selectRow);
                params.billStatus = 'ABANDONED';
                params.commandStatus = 'ABANDONED';
                // params.remarks = $scope.discardReason;
                if(params.remarks!==undefined&&params.remarks!=='') {
                    params.remarks += '; 作废原因：' + $scope.discardReason;
                } else {
                    params.remarks = '作废原因：' + $scope.discardReason;
                }

                httpServer.post(URLAPI.savePlmProjectInfoInfo, {
                        'params': JSON.stringify(params)
                    },
                    function (res) {
                        if (res.status == 'S') {
                            $('#discardModal').modal('hide');
                            $scope.dataTable.search(1);
                            SIEJS.alert(res.msg, 'success','确定');
                        } else {
                            SIEJS.alert(res.msg, "error", "确定");
                        }
                    },
                    function (err) {
                        SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                    }
                );
            });
        }




    });
});
