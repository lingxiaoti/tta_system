/**
 * Created by hemingxing on 2017/08/04.
 */
define(['app'], function(app) {
    app.controller('doResultCtrl', ['$scope','httpServer','URLAPI','SIE.JS','$timeout', function($scope,httpServer,URLAPI,JS,$timeout) {

        $scope.businessTable = {};
        $scope.businessParams = {};

        $scope.expressionTable = {};
        $scope.expressionParams = {};

        $scope.resultTable = {};
        $scope.resultParams = {};

        $scope.expressionListStatus = false;
        $scope.resultTableStatus = false;
        $scope.actionStatus = false;
        $scope.targetTypeStatusIsConfirmed = true;

        $scope.ruleBusTargetTypeData = [];
        $scope.ruleData = [];

        $scope.resultModalTitle = '新增执行结果';

        //操作符
        $scope.calcData=[
            {name:'加',code:'+'},
            {name:'减',code:'-'},
            {name:'乘',code:'*'},
            {name:'除',code:'/'},
            {name:'等于',code:'='}
        ];

        //获取影响类型
        $scope.getTargetType=function () {
            httpServer.post(URLAPI.getTargetType,  {
                params: JSON.stringify({})
            }, function (res) {
                if (res.status == "S") {
                    for(var pro in res.data){
                        $scope.ruleBusTargetTypeData.push({
                            name:res.data[pro],
                            code:pro
                        });
                    }
                }else {
                    JS.alert(res.msg,'error','确定');
                    console.error(res);
                }
            }, function (error) {
                console.error(error);
            });
        };

        $scope.getTargetType();

        //已用过滤器替代
        // $scope.searchKeyupBusiness = function(event) {
        //     if(event.keyCode == 13) {
        //         $scope.businessTable.getData();
        //         $scope.expressionListStatus = false;
        //         $scope.resultTableStatus = false;
        //     }
        // }

        //已取消2017-08-17
        // $scope.searchKeyupExpression = function(event) {
        //     if(event.keyCode == 13) {
        //         $scope.expressionTable.getData();
        //         $scope.resultTableStatus = false;
        //     }
        // }

        //获取维度
        $scope.queryRule=function (ruleBusinessLineCode) {
            httpServer.post(URLAPI.queryRule,  {
                params: JSON.stringify({
                    var_equal_ruleBusinessLineCode: ruleBusinessLineCode
                }),
                pageIndex:-1,
                pageRows:-1
            }, function (res) {
                if (res.status == "S") {
                    $scope.ruleData=res.data;
                }else {
                    JS.alert(res.msg,'error','确定');
                    console.error(res);
                }
            }, function (error) {
                console.error(error);
            });
        };

        $scope.selectBusiness = function(item) {
            $scope.expressionParams.var_like_ruleBusinessLineCode = item.ruleBusinessLineCode;
            $scope.expressionTable.getData();
            $scope.expressionListStatus = true;
            $scope.queryRule(item.ruleBusinessLineCode);
            $scope.resultTableStatus = false;
            $scope.actionStatus = false;
        }

        $scope.changeResult = function(item) {
            $scope.resultParams.var_equal_ruleBusinessLineCode = $scope.expressionParams.var_like_ruleBusinessLineCode;
            $scope.resultParams.var_equal_ruleExcCode = item.ruleExpCode;
            $scope.resultTable.getData();
            $scope.resultTableStatus = true;
            $scope.actionStatus = true;
        }

        //新增执行结果
        $scope.addResult = function() {
            $scope.result = {};
            $scope.resultModalTitle = '新增执行结果';
            $('#resultModal').modal('show');
        }

        //change影响类型
        $scope.changeTargetType = function(str) {
            if(str == 'confirmed') {
                $scope.targetTypeStatusIsConfirmed = true;
            }else if(str == 'serviceURL') {
                $scope.targetTypeStatusIsConfirmed = false;
                httpServer.post(URLAPI.saafWebserviceInfoServicesQuery,{
                    params: JSON.stringify({
                        var_equal_businessLineCode:$scope.expressionParams.var_like_ruleBusinessLineCode
                    })
                },function(res) {
                    if(res.status == 'S') {
                        $scope.targetSourceArr = res.data;
                    }else {
                        JS.alert(res.msg,'error','确定');
                        console.error(res);
                    }
                },function(error) {
                    console.error(error);
                })
            }
        }

        //编辑执行结果
        $scope.update = function(item) {
            $scope.result = angular.copy(item);
            $scope.resultModalTitle = '修改执行结果';
            $scope.changeTargetType(item.ruleBusTargetType);
            for(var i = 0;i < $scope.ruleData.length;i++) {
                if($scope.ruleData[i].ruleDimCode == item.ruleBusDim) {
                    $scope.result.ruleObj = $scope.ruleData[i];
                    break;
                }
            }

            $('#resultModal').modal('show');
        }

        //删除执行结果
        $scope.remove = function(item) {
            if(item) {
                JS.confirm('删除执行结果','是否确定删除该执行结果？','确定',function() {
                    httpServer.post(URLAPI.deleteRuleMappingBusinessService,{
                        params: JSON.stringify({
                            ruleMappBusId: item.ruleMappBusId
                        })
                    },function(res) {
                        if(res.status == 'S') {
                            $scope.resultTable.getData();
                            JS.alert('删除成功');
                        }else {
                            JS.alert(res.msg,'error','确定');
                            console.error(res);
                        }
                    },function(error) {
                        console.error(error);
                    })
                })
            }
        }

        //提交执行结果表单
        $scope.confirmResultl = function(formStatus) {
            if(!formStatus) {
                $scope.result.ruleBusName = $scope.result.ruleObj.ruleDimName;
                $scope.result.ruleBusDim = $scope.result.ruleObj.ruleDimCode;
                $scope.result.ruleExcCode = $scope.resultParams.var_equal_ruleExcCode;
                if($scope.result.ruleBusTargetType == 'serviceURL') {
                    if($scope.result.hasOwnProperty('ruleBusDimOperator')) {
                        $scope.result.ruleBusDimOperator = '';
                    }
                    if($scope.result.hasOwnProperty('ruleBusDimValue')) {
                        $scope.result.ruleBusDimValue = '';
                    }
                }else if($scope.result.ruleBusTargetType == 'confirmed') {
                    if($scope.result.hasOwnProperty('ruleBusTargetSource')) {
                        $scope.result.ruleBusTargetSource = '';
                    }
                }
                httpServer.post(URLAPI.saveRuleMappingBusinessService,  {
                    params: JSON.stringify($scope.result)
                }, function (res) {
                    if (res.status == "S") {
                        $('#resultModal').modal('hide');
                        $scope.resultTable.getData();
                        JS.alert('保存成功');
                    }else {
                        JS.alert(res.msg,'error','确定');
                        console.error(res);
                    }
                }, function (error) {
                    console.error(error);
                });
            }else {
                JS.alert('请填写完整表单','error','确定');
            }
        }
    }])
})
