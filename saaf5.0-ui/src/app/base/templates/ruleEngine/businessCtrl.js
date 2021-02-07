/**
 * Created by hemingxing on 2017/08/01.
 */
define(["app"], function (app) {
    app.controller('businessCtrl', ['$scope', 'httpServer', 'URLAPI', 'SIE.JS', '$timeout', function ($scope, httpServer, URLAPI, JS, $timeout) {

        $scope.ruleParams = {};
        $scope.ruleTable = {};

        $scope.businessParams = {};
        $scope.businessTable = {};

        $scope.valueSetTable = {};
        $scope.valueSetParams = {};

        $scope.btnStatus = false;
        $scope.business = {};
        $scope.rule = {};

        $scope.ruleDimDataType = [];
        $scope.getruleViewTypeData = [];
        $scope.ruleDimValueFromData = [];
        $scope.ruleDimReferenceFromData = [];
        $scope.ruleDimReferenceCodeData = [];

        $scope.ruleDimTargetSourceIsSQL = false;
        $scope.ruleDimTargetSourceInServer = false;
        //批量
        $scope.ruleModal = '新增维度';
        $scope.businessModalTitle = '新增业务类型';

        $scope.init = function () {
            // getBUSINESSTYPE();
            // getruleDimReferenceFrom();
            getBusinessLineMapptype();
            // getruleViewType();
            // getDimValueFrom();
        }

        $scope.addSta = function () {
            $scope.btnStatus = true;
        }

        //获取参考值by lookup
        // $scope.getRuleDimReferenceCode=function (callBack) {
        //     var salf = this;
        //     salf.__proto__.callBack = callBack;
        //     httpServer.post(URLAPI.saafLookupServlet,  {
        //         params: JSON.stringify({
        //             lookupType:'DIM_VALUE'
        //         })
        //     }, function (res) {
        //         if (res.status == "S") {
        //             $scope.ruleDimReferenceCodeData=res.data;
        //             if($scope.actionStatus) {
        //                 angular.forEach($scope.ruleDimReferenceCodeData, function(value, key) {
        //                     salf.callBack(value, key);
        //                 });
        //             }
        //         }else {
        //             JS.alert(res.msg,'error','确定');
        //             console.error(res);
        //         }
        //     }, function (error) {
        //         console.error(error);
        //     });
        // };
        //获取参考值by webservice
        $scope.getWebservice = function () {
            httpServer.post(URLAPI.saafWebserviceInfoServicesQuery, {
                params: JSON.stringify({
                    var_equal_businessLineCode: $scope.ruleParams.var_equal_ruleBusinessLineCode
                })
            }, function (res) {
                if (res.status == "S") {
                    $scope.ruleDimReferenceCodeData = res.data;
                } else {
                    JS.alert(res.msg, 'error', '确定');
                    console.error(res);
                }
            }, function (error) {
                console.error(error);
            });
        };

        //change参考值来源
        $scope.ruleDimReferenceFromChange = function () {
            if ($scope.rule.ruleDimReferenceFrom == 'webservice') {
                $scope.getWebservice();
            } else if ($scope.rule.ruleDimReferenceFrom == 'valueSet') {
                $('#valueSetHeaderModal').modal('show');
            }
        };

        //回车搜索业务线
        //已取消
        // $scope.searchKeyup = function(event) {
        //     if(event.keyCode == 13) {
        //         $scope.businessTable.search();
        //     }
        // }

        //根据业务类型code查维度
        $scope.queryRule = function () {
            $scope.ruleTable.getData();
        };

        //用户选中维度行数据
        // $scope.selectRulel = function(item) {
        //     console.log(item)
        //     // if($scope.btnStatus) {
        //         $scope.actionStatus = true;
        //     // }
        //     $scope.rule = angular.copy(item);
        // }

        //新增业务类型
        $scope.addBusinessLine = function () {
            $scope.business = {};
            $scope.businessModalTitle = '新增业务类型';
            $("#businessModal").modal("toggle");
        };

        //修改业务类型
        $scope.updateBusinessLine = function (item) {
            $scope.business = angular.copy(item);
            $scope.businessModalTitle = '修改业务类型';
            $("#businessModal").modal("toggle");
        }

        //删除业务类型
        $scope.deleteBusinessLine = function (item) {
            //$scope.businessTable.delete()
            if(item) {
                JS.confirm('删除业务类型','是否确定删除该业务类型？','确定',function() {
                    httpServer.post(URLAPI.deleteBusinessLine,{
                        params: JSON.stringify({
                            ruleBusinessLineId: item.ruleBusinessLineId
                        })
                    },function(res) {
                        if(res.status == 'S') {
                            $scope.businessTable.getData();
                            JS.alert('删除成功');
                        }else {
                            console.error(res);
                            JS.alert(res.msg,'error','确定');
                        }
                    },function(error) {
                        console.error(error);
                    })
                })
            }
        }

        //新增维度
        $scope.addRule = function () {
            var d = new Date();
            console.log(d);
            var inDate = d.getFullYear() + "-" + (d.getMonth() + 1 < 10 ? '0' + (d.getMonth()+1) : (d.getMonth()+1)) + "-" + (d.getDate() < 10 ? '0' + d.getDate() : d.getDate());
            $scope.rule = {ruleDimDataType: 'String', effectDate: inDate};
            $scope.ruleModal = '新增维度';
            $("#ruleModal").modal("toggle");
            $("#ruleDimTargetSource").attr('placeholder', '');
        };

        //响应值来源触发对应输入类型
        $scope.changeRuleDimValueFromData = function (str) {
            $scope.rule.ruleDimTargetSource = '';
            if (str == 'redis' || str == 'defaultValue') {
                $scope.ruleDimTargetSourceIsSQL = false;
                $scope.ruleDimTargetSourceInServer = false;
            } else if (str == 'webservice') {
                $scope.ruleDimTargetSourceIsSQL = false;
                $scope.ruleDimTargetSourceInServer = true;
                httpServer.post(URLAPI.saafWebserviceInfoServicesQuery, {
                    params: JSON.stringify({
                        var_equal_businessLineCode: $scope.ruleParams.var_equal_ruleBusinessLineCode
                    })
                }, function (res) {
                    if (res.status == 'S') {
                        $scope.webServerArr = res.data;
                    } else {
                        JS.alert(res.msg, 'error', '确定');
                        console.error(res);
                    }
                }, function (error) {
                    console.error(error);
                })
            } else if (str == 'sql') {
                $scope.ruleDimTargetSourceIsSQL = true;
                $scope.ruleDimTargetSourceInServer = false;
            }
            // else {
            //     $("#ruleDimTargetSource").attr('placeholder','');
            // }
        }

        //选择值集类型
        $scope.selectedValueSetHeader = function (item) {
            $scope.formValueSetHeader = item;
        }

        //提交值集
        $scope.submitValueSetHeader = function () {
            $scope.rule.ruleDimReferenceCode = $scope.formValueSetHeader.querySetNum;
            $('#valueSetHeaderModal').modal('hide');
        }

        //修改维度
        $scope.edit = function (item) {
            if (item) {
                $scope.rule = angular.copy(item);
                if ($scope.rule.ruleDimReferenceFrom == 'lookup') {
                    // $scope.getRuleDimReferenceCode(function(value, key) {
                    //     if(value.lookupCode == $scope.rule.ruleDimReferenceCode) {
                    //         $scope.ruleDimReferenceCode = value;
                    //     }
                    // });
                } else {
                    $scope.getWebservice(
                        // function(value, key) {
                        // console.log(value)
                        // if(value.webserviceCode == $scope.rule.ruleDimReferenceCode) {
                        //     $scope.rule.ruleDimReferenceCode = value;
                        // }
                        // }
                    );
                }
                if ($scope.rule.hasOwnProperty('ruleDimValueFrom')) {
                    if ('sql' == $scope.rule.ruleDimValueFrom) {
                        $scope.ruleDimTargetSourceIsSQL = true;
                        $scope.ruleDimTargetSourceInServer = false;
                    } else if ('webservice' == $scope.rule.ruleDimValueFrom) {
                        $scope.ruleDimTargetSourceIsSQL = false;
                        $scope.ruleDimTargetSourceInServer = true;
                        $scope.getWebservice();
                    } else {
                        $scope.ruleDimTargetSourceIsSQL = false;
                        $scope.ruleDimTargetSourceInServer = false;
                    }
                }

                $scope.ruleModal = '修改维度';
                $("#ruleModal").modal("toggle");
            }
        }

        //删除维度
        $scope.remove = function (item) {
            //$scope.ruleTable.delete();z
            if(item) {
                JS.confirm('删除维度类型','是否确定删除该业维度？','确定',function() {
                    httpServer.post(URLAPI.deleteRule,{
                        params: JSON.stringify({
                            ruleDimId: item.ruleDimId
                        })
                    },function(res) {
                        if(res.status == 'S') {
                            $scope.ruleTable.getData();
                            JS.alert('删除成功');
                        }else {
                            console.error(res);
                            JS.alert(res.msg,'error','确定');
                        }
                    },function(error) {
                        console.error(error);
                    })
                })
            }
        }


        //获取匹配类型
        function getBusinessLineMapptype() {
            $scope.ruleBusinessLineMapptypeArr = [];
            httpServer.post(URLAPI.getBusinessLineMapptype, {
                params: JSON.stringify({})
            }, function (res) {
                if (res.status == "S") {
                    for (var pro in res.data) {
                        $scope.ruleBusinessLineMapptypeArr.push({
                            name: res.data[pro],
                            code: pro
                        });
                    }

                } else {
                    JS.alert(res.msg, 'error', '确定');
                    console.error(res);
                }
            }, function (error) {
                console.error(error);
            });
        };

        //获取显示类型
        //已不需要
        // function getruleViewType() {
        //     httpServer.post(URLAPI.getruleViewType,  {
        //         params: JSON.stringify({})
        //     }, function (res) {
        //         if (res.status == "S") {
        //             console.log(res.data,'匹配类型2')
        //             for(var pro in res.data){
        //                 $scope.getruleViewTypeData.push({
        //                     name:res.data[pro],
        //                     code:pro
        //                 });
        //             }
        //         }else {
        //             JS.alert(res.msg,'error','确定');
        //             console.error(res);
        //         }
        //     }, function (error) {
        //         console.error(error);
        //     });
        // };

        //获取值来源
        //已用快码替代
        // function getDimValueFrom() {
        //     httpServer.post(URLAPI.getDimValueFrom,  {
        //         params: JSON.stringify({})
        //     }, function (res) {
        //         if (res.status == "S") {
        //             for(var pro in res.data){
        //                 $scope.ruleDimValueFromData.push({
        //                     name:res.data[pro],
        //                     code:pro
        //                 });
        //             }
        //         }else {
        //             JS.alert(res.msg,'error','确定');
        //             console.error(res);
        //         }
        //     }, function (error) {
        //         console.error(error);
        //     });
        // };

        $scope.ruleDimDataType = [
            {
                ruleDimDataType: '字符串',
                ruleDimDataCode: 'String'
            },
            {
                ruleDimDataType: '浮点型',
                ruleDimDataCode: 'Double'
            },
            {
                ruleDimDataType: '日期',
                ruleDimDataCode: 'Date'
            },
            {
                ruleDimDataType: '整型',
                ruleDimDataCode: 'Integer'
            }
        ];

        //保存业务类型
        $scope.confirmBusiness = function (formStatus) {
            if (!formStatus) {
                // angular.forEach($scope.ruleBusinessLineTypeArr, function(value, key) {
                //     if($scope.business.ruleBusinessLineType == value.lookupCode) {
                //         $scope.business.ruleBusinessLineTypeMeaning = value.meaning;
                //     }
                // });
                angular.forEach($scope.ruleBusinessLineMapptypeArr, function (value, key) {
                    if ($scope.business.ruleBusinessLineMapptype == value.code) {
                        $scope.business.ruleBusinessLineMapptypeMeaning = value.name;
                    }
                })
                httpServer.post(URLAPI.saveOrUpdate, {
                    params: JSON.stringify({
                        businessline: $scope.business
                    })
                }, function (res) {
                    if (res.status == "S") {
                        $("#businessModal").modal("hide");
                        $scope.businessTable.getData();
                        JS.alert('保存成功');
                        $scope.btnStatus = true;
                    } else {
                        JS.alert(res.msg, 'error', '确定');
                        console.error(res);
                    }
                }, function (error) {
                    console.error(error);
                });
            } else {
                JS.alert('请填写完整表单', 'error', '确定');
            }
        };

        //保存维度
        $scope.confirmRule = function (formStatus) {
            if (!formStatus) {
                $scope.rule.ruleBusinessLineCode = $scope.ruleParams.var_equal_ruleBusinessLineCode;
                httpServer.post(URLAPI.saveOrUpdateRule, {
                    params: JSON.stringify($scope.rule)
                }, function (res) {
                    if (res.status == "S") {
                        $('#ruleModal').modal('hide');
                        $scope.ruleTable.getData();
                        JS.alert('保存成功');
                    } else {
                        JS.alert(res.msg, 'error', '确定');
                        console.error(res);
                    }
                }, function (error) {
                    console.error(error);
                });
            } else {
                JS.alert('请填写完整表单', 'error', '确定');
            }
        };

        $scope.businessTableClickRow = function (item) {
            // businessTable.data.selectRow = item.ruleBusinessLineId;
            $scope.ruleParams.var_equal_ruleBusinessLineCode = item.ruleBusinessLineCode;
            $scope.addSta();
            $scope.ruleTable.getData()
        }
    }])
})
