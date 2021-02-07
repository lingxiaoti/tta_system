/**
 * Created by hmx on 2018/9/4.
 */
'use strict';
define(['app'],function(app) {
    app.controller('ttaSaStdTplFieldCfgCtrl',['$scope', 'httpServer', 'URLAPI', 'SIE.JS','$stateParams','$timeout','validateForm',
        function($scope,httpServer,URLAPI,SIEJS,$stateParams,$timeout,validateForm) {
            $scope.ttaSaStdTplFieldCfgTable = {};
            $scope.ttaSaStdTplFieldCfgTable.data = [] ;

            //增加按钮
            $scope.btnNew = function () {
                var expressionArray = {
                    isEnable:'N',
                    filedType:'TEXT'
                };
                $scope.ttaSaStdTplFieldCfgTable.data.push(expressionArray);
            };

            //选择字典
            $scope.getDicCode = function () {
                $('#selectDicCodeId').modal('toggle');
            };

            //清除值
            $scope.dicCodeCancel = function (row){
                row.dicCode = "";

            };

            //点击确认按钮
            $scope.selectDicCodeReturn = function (key, value, currentList) {
                var row = $scope.ttaSaStdTplFieldCfgTable.data[$scope.ttaSaStdTplFieldCfgTable.data.selectRow];
                row.dicCode = currentList[0].lookupType;
            };

            $scope.btnSave = function (form){
                if(!validateForm(form)){
                    return;
                }
                httpServer.post(URLAPI.ttaSaStdTplFieldCfgSaveOrUpdateAll,
                    {params: JSON.stringify({"list":$scope.ttaSaStdTplFieldCfgTable.data})},
                    function (res) {
                        if (res.status == "S") {
                            $scope.ttaSaStdTplFieldCfgTable.getData($scope.ttaSaStdTplFieldCfgTable.curIndex);
                            SIEJS.alert(res.msg, 'success', '确定');
                        } else {
                            SIEJS.alert(res.msg, "error", "确定");
                        }
                    },
                    function (err) {
                        SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                        $("#saveButton").attr("disabled", true);
                    }
                );
            };
            $timeout(function () {
                $scope.ttaSaStdTplFieldCfgTable.getData()
            }, 200);

        }])
});
