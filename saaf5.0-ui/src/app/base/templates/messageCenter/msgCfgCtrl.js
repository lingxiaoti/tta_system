'use strict';
define(['app'],function(app){
    app.controller('msgCfgCtrl',  function($scope,$location,$rootScope,$state,$stateParams,SIEJS,msgCfgSave,msgCfgDelete) {
        $scope.params={};
        $scope.userRespList=JSON.parse(localStorage[window.appName + '_successLoginInfo']).userRespList;
        $scope.buData=[];
        angular.forEach($scope.userRespList,function(data,index){
            if(data.systemCode==window.appName){
                $scope.buData=data.orgBeans;
            }
        })
        $scope.btnModify=function(){
            $scope.modalTitle='修改';
            if (!$scope.dataTable.selectRow){
                SIEJS.alert('请选择一条数据','warning');
                return;
            }
            var row = $scope.dataTable.selectRow;
            $scope.addParams = angular.copy(row);
            $('#msgCfgModal').modal('toggle');
        };
        $scope.btnNew=function(){
            $scope.addParams = {};
            $scope.modalTitle='新增';
            $scope.addParams['msgCfgId']='';
            $scope.addParams['enabledFlag'] = '0';
            $scope.addParams['blacklistFlag'] = 'N';
            $scope.addParams['compensateFlag'] = 'N';
            $scope.addParams['isDelete'] = '0';
            $scope.addParams['versionNum'] = '0';
            $('#msgCfgModal').modal('toggle');
        }
        $scope.btnDel=function(){
            if ($scope.dataTable.selectRowList.length == 0){
                SIEJS.alert('请选择一条数据','warning');
                return;
            }
            var idList = [];
            $scope.dataTable.selectRowList.map(function (item, index) {
                idList.push({
                    id: item.msgCfgId
                })
            });

            SIEJS.confirm('删除', '是否确定删除？', '确定', function () {
                msgCfgDelete({
                    'params': JSON.stringify(
                        {idDetails: idList}
                    )
                }, function (res) {
                    if(res.status=='S'){
                        $scope.dataTable.search(1,null,true); // 清除之前选择的数据
                        $scope.dataTable.selectRowList=[];
                        SIEJS.alert('删除成功','success','确定');

                    }else{
                        SIEJS.alert('删除失败','error');
                    }

                });
            })
        };
        $scope.btnSave=function(){

            msgCfgSave({params: JSON.stringify($scope.addParams)}, function (res) {
                if (res.status == "S") {
                    $('#msgCfgModal').modal('toggle')
                    SIEJS.alert(res.msg, 'success', '确定');
                    $scope.dataTable.search()
                } else {
                    SIEJS.alert(res.msg, 'error', '确定');

                }

            })
        }
        $scope.save=function(invalid){
            if (invalid){
                SIEJS.alert('请检查必填项','warning');
                return;
            }
            log($scope.addParams)
        }
        // 显示消息源搜索LOV
        $scope.showMsgSource = function () {
            $("#msgSourceCfgSave").modal('show');
        }
        // 显示渠道搜索LOV
        $scope.showChannel = function () {
            $("#findChannelSave").modal('show');
        }
        // 显示消息模板搜索LOV
        $scope.showTemple = function () {
            $("#msgTempleCfgSave").modal('show');
        }
        $scope.msgTypeChange = function () {
            $scope.addParams.msgSourceName = "";
            $scope.addParams.msgSourceCode = "";
        }


    });
});
