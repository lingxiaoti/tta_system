/**
 * Created by hmx on 2018/9/4.
 */
'use strict';
define(['app'],function(app) {
    app.controller('userGroupDeptBrandSearchCtrl',['$scope', 'httpServer', 'URLAPI', 'SIE.JS','iframeTabAction','$http',
        function($scope,httpServer,URLAPI,SIEJS,iframeTabAction,$http) {
            //新增
            $scope.daoru = function () {
                $('#excelTtaLine').modal('toggle');
                $scope.buttonFlag = true ;
            };
            var exportTimer;

            //上传附件
            $scope.save = function (invalid) {
                if(invalid){
                    SIEJS.alert('请检查必填项', 'error', '确定');
                    return;
                }
                var fd = new FormData();
                var file = document.getElementById('fileUpLoad').files[0];
                if (!file) {
                    SIEJS.alert("请选择上传文件", 'error', '确定');
                    return;
                }
                $scope.buttonFlag = false ;
                fd.append('file', file);
                exportTimer = setInterval(function () {
                    exportStatus(sessionStorage[appName + '_certificate'] || localStorage[appName + '_certificate'] || 'nothing');
                }, 5000);
                $http.post(URLAPI.saveTtaUserPowerImportInfo, fd, {
                    transformRequest: angular.identity,
                    headers: {
                        'Certificate': sessionStorage[appName + '_certificate'] || localStorage[appName + '_certificate'] || 'nothing',
                        'Content-Type': undefined
                    }
                }).success(function (response) {
                    SIEJS.alert(response.msg, 'success', '确定');
                }).error(function(response) {
                    SIEJS.alert(response.msg, 'error', '确定');
                    $scope.buttonFlag = true ;
                    clearInterval(exportTimer);
                });

            };

            // 查询导出结果
            function exportStatus(code) {
                var params = {
                    params:"{Certificate:\""+"TTA_USER_DATA_TYPE"+code+"\"}"

                };
                $.ajax({
                    url: URLAPI.getOiStatus,
                    type: 'post',
                    data: params,
                    timeout: 60000 * 5,
                    headers: {
                        "Content-Type": "application/x-www-form-urlencoded;charset=UTF-8",
                        "Certificate": sessionStorage[appName + '_certificate'] || localStorage[appName + '_certificate'] || 'nothing',
                    },
                    dataType: 'html',
                    success: function (res) {
                        var res = JSON.parse(res);
                        if (res.status === 'S') {
                            jQuery("#value1").text(res.data.currentStage);
                            jQuery("#value2").text(res.data.orderNum);
                            clearInterval(exportTimer);
                        } else  if (res.status === 'E' || res.status === 'M') {
                            jQuery("#value1").text(res.data.currentStage);
                            jQuery("#value2").text(res.data.orderNum);
                            clearInterval(exportTimer);
                        }else{
                            $scope.messageCurrentStage = res.data.currentStage ;
                            jQuery("#value1").text(res.data.currentStage);
                            jQuery("#value2").text(res.data.orderNum);
                            $scope.messageCurrentOrderNum = res.data.orderNum ;
                        }

                    },
                    error: function (e) {
                        clearInterval(exportTimer);
                    }
                });
            }
        }])
});