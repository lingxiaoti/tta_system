'use strict';
define(['app', 'pinyin', 'ztree','XLSX'], function (app, pinyin, ztree,XLSX) {
    app.controller('ttaOiBillLineCtrl', function ($scope, $filter, $location, $rootScope, $state, $stateParams, SIEJS, httpServer, URLAPI, $http, iframeTabAction, setNewRow) {


        $scope.btnDel = function () {
            var selectRowList = $scope.dataTable.selectRowList;
            if (!selectRowList.length) {
                SIEJS.alert("请选择要删除的数据!", "error", "确定");
                return;
            }
            SIEJS.confirm('您确认要删除吗？', '', '确定', function () {
                var ids = "";
                for (var i = 0; i < selectRowList.length; i++) {
                    ids = ids + selectRowList[i].dmFullLineId + ",";
                }
                httpServer.post(
                    URLAPI.deleteImportTTADFInfo,
                    {
                        'params': JSON.stringify({
                            "dmFullLineIds": ids
                        })
                    },
                    function (res) {
                        if (res.status.toLowerCase() == 'e') {
                            SIEJS.alert(res.msg, "error");
                        } else {
                            SIEJS.alert(res.msg, "success");
                            $scope.dataTable.search(1);

                        }
                    }
                )
            });
        }


        //新增
        $scope.daoru = function () {
            $("#fileUpLoad").val('');
            $('#excelUp').modal('toggle');
        }

        //上传附件
        $scope.uploadSave = function (invalid) {
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
            fd.append('file', file);
           //fd.append("proposalId", getId());
            $http.post(URLAPI.saveImportDmInfo, fd, {
                transformRequest: angular.identity,
                headers: {
                    'Certificate': sessionStorage[appName + '_certificate'] || localStorage[appName + '_certificate'] || 'nothing',
                    'Content-Type': undefined
                }
            }).success(function (response) {
                $('#excelUp').modal('toggle');
                if (response.status == 'S') {
                    $scope.dataTable.search();
                    SIEJS.alert(response.msg, 'success', '确定');
                } else {
                    SIEJS.alert("导入失败,错误原因：" + response.msg, 'error', '确定');
                }
            }).error(function(response) {
                SIEJS.alert("导入失败!", 'error', '确定');
            });
        }



    });
});
