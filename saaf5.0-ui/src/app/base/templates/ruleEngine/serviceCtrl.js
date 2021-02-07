'use strict';
define(["app",'ztree'], function (app) {
    app.controller('serviceCtrl', ['$scope', '$parse', '$filter', '$attrs', "SIE.JS", 'httpServer', 'URLAPI', 'carouseDel', function saafTableController($scope, $parse, $filter, $attrs, JS, httpServer, URLAPI, carouseDel) {

        //查询参数
        $scope.params = {}
        //表格对象
        $scope.serverTable = {}

        //验证静态和动态参数变量必须存在于数据公式中
        $scope.validatePramInFormula = function () {
            if($scope.current.actionFormula.indexOf('[')==-1||$scope.current.actionFormula.indexOf(']')==-1) {
                JS.alert('公式的变量名必须用中括号[]包裹','warning');
                $scope.current.actionFormula=''
            }
            var params = $scope.current.actionDynamicParam + "," + $scope.current.actionStaticParam;
            //拆解公式中的参数
            var left = $scope.current.actionFormula.split('[');
            var formulaParams = [];
            for(var i=0; i<left.length; i++) {
                if(left[i].indexOf(']')>0) {
                    var formulaParam = left[i].split(']')[0];
                    if(params.indexOf(formulaParam)==-1) {
                        JS.alert(formulaParam+'必须是存在与动态变量或静态变量中','warning');
                        $scope.current.actionFormula='';
                    }
                }
            }

        }

        //点击新增
        $scope.addAction = function () {
            $scope.current = {'actionRequestMethod':'post',actionExecuteFormula:''};
            $('#myModal').modal('toggle');
        }

        //

        //模态框保存
        $scope.save = function() {
            httpServer.post(URLAPI.saveRuleAction, {params: JSON.stringify($scope.current)}, function (res) {
                if (res.status == "S") {
                    $scope.serverTable.getData();
                    JS.alert(res.msg, 'success', '确定');
                    $('#myModal').modal('toggle');
                } else {
                    JS.alert(res.msg, 'error', '确定')
                }
            })
        }

        //修改
        $scope.editAction = function (item) {
            $scope.current = item;
            $('#myModal').modal('toggle');
        }

        //删除
        $scope.deleteAction = function (item) {
            JS.confirm('删除服务', '是否确定删除该服务', '确定', function () {
                httpServer.post(URLAPI.deleteRuleAction, {
                    params: JSON.stringify({
                        executeActionId: item.actionId
                    })
                }, function (res) {
                    if ('S' == res.status) {
                        $scope.serverTable.getData();
                        JS.alert('删除成功');
                    } else {
                        JS.alert(res.msg, 'error', '确定');
                    }
                }, function (error) {
                    JS.alert('删除失败', 'error', '确定');
                    console.error(error);
                })
            })
        }

    }]);
});
