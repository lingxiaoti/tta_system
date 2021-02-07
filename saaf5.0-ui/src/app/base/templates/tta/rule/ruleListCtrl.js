'use strict';
define(['app', 'pinyin', 'ztree'], function (app, pinyin, ztree) {
    app.controller('ruleListCtrl', function ($timeout, httpServer, $filter, userDel, $scope, $http, $location, $rootScope, $state, $stateParams, setNewRow, SIEJS, iframeTabAction,URLAPI) {

        $scope.params = {isEnable: 'Y'};
        $scope.tab = {
            active: 0,
            nav: ['模板参数', '文件模板'],
            click: function (i) {
                $scope.tab.active = i
            }
        }

        $scope.downloadFile = function () {
            var url = URLAPI.wordTempdownload;
            window.open(url, [""], [""]);//避开因同源策略而造成的拦截
        }

        $scope.upload = function() {
            var f = $scope.myFile;
            var fd = new FormData();
            var file = document.querySelector('input[type=file]').files[0];
            if (!file) {
                SIEJS.alert("请选择上传文件", 'error', '确定');
                return;
            }
            var fileName = file.name.toUpperCase();
            //debugger;
           /* if(fileName.indexOf(".HTML") < 0 &&  fileName.indexOf(".HTM") < 0 ) {
                SIEJS.alert("请选择word转html或者htm的格式", 'error', '确定');
                return;
            }*/
            if(fileName.indexOf(".DOCX") < 0) {
                SIEJS.alert("请选择word docx格式", 'error', '确定');
                return;
            }
            fd.append('businessType', '1');
            fd.append('file', file);
            fd.append("fileName", fileName);
            $http.post(URLAPI.ruleFileUpload, fd, {
                transformRequest: angular.identity,
                headers: {
                    'Certificate': sessionStorage[appName + '_certificate'] || localStorage[appName + '_certificate'] || 'nothing',
                    'Content-Type': undefined
                }
            }).success(function (response) {
                SIEJS.alert(response.msg, 'success', '确定');
            }).error(function(response) {
                SIEJS.alert(response.msg, 'error', '确定');
            });
        };

        $scope.bcUploadUrl = 'batchUploadFile';
        
        // $scope.params = {}
        $scope.btnNew = function () {
            iframeTabAction('新增规则', 'ruleManageDetail/')
        }


        $scope.btnModify = function () {
            var row = $scope.dataTable.selectRow
            console.log(JSON.stringify(row));
            iframeTabAction('规则详情：'+row.ruleName, 'ruleManageDetail/' + row.rulId);
        }

        $scope.btnDel = function (item) {
            item = item || $scope.dataTable.selectRow;

            if (item) {
                SIEJS.confirm('删除', '是否确定删除？', '确定', function () {

                    userDel({params: JSON.stringify({id: item.ssoId})}, function (res) {
                        if ('S' == res.status) {
                            $scope.dataTable.search();
                            SIEJS.alert('删除成功');
                        } else {
                            SIEJS.alert(res.msg, 'error', '确定');
                        }
                    }, function (error) {
                        console.error(error);
                        SIEJS.alert('删除失败', 'error', '确定');
                    })

                })
            }
        }
    });
});
