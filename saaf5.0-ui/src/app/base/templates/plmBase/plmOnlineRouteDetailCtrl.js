/**
 * Created by lip on 2019/5/8.
 */
'use strict';
define(['app', 'pinyin', 'ztree', 'angularFileUpload'], function (app, pinyin, ztree, angularFileUpload) {
    app.useModule('angularFileUpload');//按需加载弹出层模块
    app.controller('plmOnlineRouteDetailCtrl', function ($scope, $filter, $location, $rootScope, $state, $stateParams, SIEJS, httpServer, URLAPI, iframeTabAction, $compile, FileUploader, iframeMessage) {
        var id = $stateParams.id;

        $scope.headerData = {};
        var oldData = {};

        $scope.plmOnlineSubrouteDataTable = [];

        $scope.userType = JSON.parse(sessionStorage[appName + '_successLoginInfo']).userType;
        $scope.userName = JSON.parse(sessionStorage[appName + '_successLoginInfo']).userName;

        //查询单据信息
        $scope.search = function (string) {

            $scope.plmOnlineRouteId = id;

            httpServer.post(URLAPI.findPlmOnlineRouteInfo, {
                    'params': JSON.stringify({plmOnlineRouteId: $scope.plmOnlineRouteId})
                },
                function (res) {
                    if (res.status === 'S') {
                        $scope.headerData = res.data[0];
                        oldData = angular.copy($scope.headerData);

                        if(string!=='header') {
                            $scope.searchPlmOnlineSubroute();
                        }

                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                },
                function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                },false
            );
        };


        //id不为空，初始搜索
        if ($stateParams.id) {
            //查询头信息
            $scope.search();
        } else {
            $scope.headerData.billStatus = 'TODO';
            $scope.headerData.billStatusName = '制单中';
            $scope.headerData.creator = $scope.userName;
        }

        $scope.searchPlmOnlineSubroute = function () {
            httpServer.post(URLAPI.findPlmOnlineSubrouteInfo, {
                    'params': JSON.stringify({plmOnlineRouteId: $scope.plmOnlineRouteId})
                },
                function (res) {
                    if (res.status === 'S') {
                        $scope.plmOnlineSubrouteDataTable = res.data;

                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                },
                function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                },false
            );
        };

        $scope.btnSave = function(invalid){
            if (invalid) {
                SIEJS.alert('请检查必填项', 'error', '确定');
                return;
            }

            if($scope.headerData.billStatus ==='Y'){
                for(var i = 0; i < $scope.plmOnlineSubrouteDataTable.length; i++){
                    if($scope.plmOnlineSubrouteDataTable[i].subrouteName===undefined
                        ||$scope.plmOnlineSubrouteDataTable[i].subrouteName===''
                        ||$scope.plmOnlineSubrouteDataTable[i].subrouteName===null){
                        SIEJS.alert('行表区域名称未填！','error','确定');
                        return;
                    }
                }
            }

            $scope.saveFunction();
        };

        $scope.btnSubmit = function(invalid){
            if (invalid) {
                SIEJS.alert('请检查必填项', 'error', '确定');
                return;
            }

            for(var i = 0; i < $scope.plmOnlineSubrouteDataTable.length; i++){
                if($scope.plmOnlineSubrouteDataTable[i].subrouteName===undefined
                    ||$scope.plmOnlineSubrouteDataTable[i].subrouteName===''
                    ||$scope.plmOnlineSubrouteDataTable[i].subrouteName===null){
                    SIEJS.alert('行表区域名称未填！','error','确定');
                    return;
                }
            }

            $scope.saveFunction('submit');
        };

        // $scope.checkTableData = function(table){
        //     for(var i = 0; i < table.length; i++){
        //         for(var j = 1; j < arguments.length; j++){
        //             if(table[i][arguments[j]]===undefined||table[i][arguments[j]]===''||table[i][arguments[j]]===null){
        //                 SIEJS.alert('')
        //             }
        //         }
        //     }
        // };

        $scope.saveFunction = function(command){
            var params = angular.copy($scope.headerData);
            params.plmOnlineSubrouteList = angular.copy($scope.plmOnlineSubrouteDataTable);
            if(command==='submit'||$scope.headerData.billStatus==='Y'){
                params.billStatus = 'Y';
                params.billStatusName = '已生效';
                for(var i = 0 ; i < params.plmOnlineSubrouteList.length; i ++){
                    params.plmOnlineSubrouteList[i].billStatus = 'Y';
                    params.plmOnlineSubrouteList[i].billStatusName = '已生效'
                }
            }

            httpServer.post(URLAPI.savePlmOnlineRouteInfo, {
                    'params': JSON.stringify(params)
                },
                function (res) {
                    if (res.status === 'S') {
                        SIEJS.alert('操作成功','success','确定');
                        id = res.data.plmOnlineRouteId;

                        $scope.search();
                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                },
                function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                },false
            );
        };

        //失效线上渠道行
        $scope.closeSubroute = function(index){
            var data = angular.copy($scope.plmOnlineSubrouteDataTable[index]);
            data.billStatus = "N";
            data.billStatusName = '已失效';
            var list = [];
            list.push(data);
            var params = {};
            params.plmOnlineSubrouteList = list;
            $scope.saveSubrouteRow(params);
        };

        //生效线上渠道行
        $scope.startSubroute = function(index){
            var data = angular.copy($scope.plmOnlineSubrouteDataTable[index]);
            data.billStatus = "Y";
            data.billStatusName = '已生效';
            var list = [];
            list.push(data);
            var params = {};
            params.plmOnlineSubrouteList = list;
            $scope.saveSubrouteRow(params);
        };

        $scope.saveSubrouteRow = function(params){
            httpServer.post(URLAPI.savePlmOnlineSubrouteInfo, {
                    'params': JSON.stringify(params)
                },
                function (res) {
                    if (res.status === 'S') {
                        $scope.searchPlmOnlineSubroute();
                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                },
                function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                },false
            );
        };

        $scope.deleteSubroute = function (index) {
            var plmOnlineSubrouteId = $scope.plmOnlineSubrouteDataTable[index].plmOnlineSubrouteId;
            SIEJS.confirm('删除', '是否确定删除？', '确定', function () {
                if(plmOnlineSubrouteId===undefined||plmOnlineSubrouteId===''||plmOnlineSubrouteId===null){
                    $scope.plmOnlineSubrouteDataTable.splice(index, 1);             //新增列没有ID直接删除
                    SIEJS.alert("操作成功!已成功删除数据！", "success");
                    $scope.$apply();
                }
                else {
                    $scope.deleteSubrouteAction(index);
                }
            });
        };

        //删除行表操作
        $scope.deleteSubrouteAction = function(index){
            var params = $scope.plmOnlineSubrouteDataTable[index];
            httpServer.post(URLAPI.deletePlmOnlineSubrouteInfo, {
                    'params': JSON.stringify(params)
                },
                function (res) {
                    if (res.status === 'S') {
                        SIEJS.alert('操作成功！已成功删除数据！','success');
                        $scope.plmOnlineSubrouteDataTable.splice(index, 1);
                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                },
                function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                }
            );
        };

        $scope.addSubrouteRow = function () {
            // if($scope.headerData.billStatus === 'Y'){
            //     SIEJS.alert('已提交单据禁止修改','error','确定');
            //     return;
            // }
            var data = {};
            data.plmOnlineRouteId = $scope.headerData.plmOnlineRouteId;
            data.plmOnlineRouteCode = $scope.headerData.plmOnlineRouteCode;
            data.plmOnlineRouteName = $scope.headerData.plmOnlineRouteName;
            data.creator = $scope.userName;
            data.billStatus = 'TODO';
            data.billStatusName = '已生效';
            $scope.plmOnlineSubrouteDataTable.push(data);
        };

    });
});
