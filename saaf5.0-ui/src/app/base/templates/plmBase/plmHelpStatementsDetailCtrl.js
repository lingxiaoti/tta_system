/**
 * Created by lip on 2019/5/8.
 */
'use strict';
define(['app', 'pinyin', 'ztree', 'angularFileUpload'], function (app, pinyin, ztree, angularFileUpload) {
    app.useModule('angularFileUpload');//按需加载弹出层模块
    app.controller('plmHelpStatementsDetailCtrl', function ($scope, $filter, $location, $rootScope, $state, $stateParams, SIEJS, httpServer, URLAPI, iframeTabAction, $compile, FileUploader, iframeMessage) {
        var id = $stateParams.id;

        $scope.headerData = {};

        $scope.userType = JSON.parse(sessionStorage[appName + '_successLoginInfo']).userType;
        $scope.userName = JSON.parse(sessionStorage[appName + '_successLoginInfo']).userName;

        //查询单据信息
        $scope.search = function () {

            $scope.plmHelpStatementsId = id;

            httpServer.post(URLAPI.findPlmHelpStatementsInfo, {
                    'params': JSON.stringify({plmHelpStatementsId: $scope.plmHelpStatementsId})
                },
                function (res) {
                    if (res.status === 'S') {
                        $scope.headerData = res.data[0];
                        $scope.plmHelpStatementsId = res.data[0].plmHelpStatementsId;


                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                },
                function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                }
            );
        };

        if(id===undefined||id===null||id===''){
            $scope.headerData.billStatusName = '制单中';
        }
        else {
            $scope.search();
        }

        /********************  按钮操作 start ********************/

        $scope.btnSave = function (invalid) {
            if (invalid) {
                SIEJS.alert('请检查必填项', 'error', '确定');
                return;
            }
            $scope.saveAction($scope.headerData);
        };

        $scope.saveAction = function(params, silent){

            // for(var i = 0; i < $scope.billStatus.dataList.length; i++){
            //     if($scope.billStatus.dataList[i].lookupCode===params.billStatus){
            //         params.billStatusName = $scope.billStatus.dataList[i].meaning;
            //     }
            // }

            httpServer.post(URLAPI.savePlmHelpStatementsInfo, {
                    'params': JSON.stringify(params)
                },
                function (res) {
                    if (res.status === 'S') {
                        $scope.headerData = res.data;
                        id = $scope.headerData.plmHelpStatementsId;
                        if(silent!=='silent')
                            SIEJS.alert('操作成功','success','确定');

                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                },
                function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                },false
            );
        };

        $scope.btnSubmit = function(invalid) {
            if (invalid) {
                SIEJS.alert('请检查必填项', 'error', '确定');
                return;
            }

            var params = angular.copy($scope.headerData);
            params.billStatusName = '已生效';
            $scope.saveAction(params);
        };

        $scope.btnDiscard = function(){
            var params = angular.copy($scope.headerData);
            params.billStatusName = '已失效';
            $scope.saveAction(params);
        };

        /********************  按钮操作 end ********************/



    });
});
