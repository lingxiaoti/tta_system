/**
 * Created by hemingxing on 2017/08/02.
 */
define(['app'], function (app) {
    app.controller('expressionCtrl', ['$scope', 'httpServer', 'URLAPI', '$timeout', 'SIE.JS', function ($scope, httpServer, URLAPI, $timeout, JS) {
        $scope.businessTable = {};
        $scope.businessParams = {};

        $scope.expressionTable = {};
        $scope.expressionParams = {};

        $scope.resultTable = {};
        $scope.resultParams = {};

        $scope.expressionListStatus = false;
        $scope.expressionContextStatus = false;
        $scope.actionStatus = false;

        $scope.submitStatus = false;

        $scope.showExp = true;

        var placeholderArr = new Set();//占位符数组
        var conjunctionArr = new Set();//连接词数组

        $scope.newExp = {};
        $scope.newExpRuleArr = [];

        //表达式数学公式静态参数集合
        $scope.ruleFormulaStaticParam = []

        //$scope.businessParams.var_like_ruleBusinessLineName = '2017-09-28 00:00:01'

        $scope.selectBusiness = function (item) {
            //是否显示权重
            if (item.ruleBusinessLineMapptype === 'weight') {
                $scope.showRuleWeight = true;
            } else {
                $scope.showRuleWeight = false;
            }
            //是否显示业务点
            if (item.ruleBusinessLineMapptype === 'businessPoint') {
                $scope.showBusinessPoint = true;
            } else {
                $scope.showBusinessPoint = false;
            }
            $scope.expressionParams = {var_like_ruleBusinessLineCode: item.ruleBusinessLineCode};
            $scope.expressionTable.getData();
            $scope.ruleBusinessLineCode = item.ruleBusinessLineCode;
            $scope.expressionListStatus = true;
            $scope.actionStatus = false;
            $scope.expressionContextStatus = false;
            $scope.queryRuleByBusinessCode(item.ruleBusinessLineCode);
            $scope.resultParams.var_equal_ruleBusinessLineCode = item.ruleBusinessLineCode;
            $scope.queryRule(item.ruleBusinessLineCode);
        }

        $scope.changeExoression = function (item) {

            placeholderArr.clear();
            conjunctionArr.clear();
            $scope.expressionContext = angular.copy(item);
            $scope.expressionContextStatus = true;

            $scope.actionStatus = true;
            $scope.queryRuleByBusinessCodeAndExpCode($scope.expressionParams.var_like_ruleBusinessLineCode, item);
            $scope.resultParams.ruleExpCode = item.ruleExpCode;
            //保留旧的权重，和业务点，防止更新报错
            $scope.oldParam = {
                ruleBusinessPoint: item.ruleBusinessPoint, ruleExpWeight: item.ruleExpWeight
            };
            //表达式json转成相关的对象
            $scope.ruleFormulaStaticParam = [];
            if (item.ruleFormulaStaticParam) {
                var ruleFormulaStaticParam = item.ruleFormulaStaticParam.split(',');
                angular.forEach(ruleFormulaStaticParam, function (key) {
                    var staticParam = new Object();
                    staticParam.key = key;
                    $scope.ruleFormulaStaticParam.push(staticParam);
                })
            }
            if (item.ruleFormulaStaticValue) {
                $scope.ruleFormulaStaticValueJson = JSON.parse(item.ruleFormulaStaticValue);
            }
            //console.log(item);
            $scope.resultTable.getData();
            creatRuleNode();
        }


        //获取操作符
        $scope.getOpreateType = function () {
            httpServer.post(URLAPI.getOpreateType, {
                params: JSON.stringify({})
            }, function (res) {
                if (res.status == "S") {
                    $scope.getOpreateTypeData = res.data;
                } else {
                    JS.alert(res.msg, 'error', '确定');
                    console.error(res);
                }
            }, function (error) {
                console.error(error);
            });
        };
        $scope.getOpreateType();

        //根据业务线code查维度
        $scope.queryRuleByBusinessCode = function (ruleBusinessLineCode) {
            httpServer.post(URLAPI.queryRule, {
                params: JSON.stringify({
                    var_equal_ruleBusinessLineCode: ruleBusinessLineCode
                }),
                pageIndex: -1,
                pageRows: -1
            }, function (res) {
                if (res.status == "S") {
                    //console.log(res.data)
                    $scope.newExpRuleArr = res.data;
                } else {
                    JS.alert(res.msg, 'error', '确定');
                    console.error(res);
                }
            }, function (error) {
                console.error(error);
            });
        }

        //回车搜索业务线
        //已取消2017-08-17
        // $scope.searchKeyupBusiness = function(event) {
        //     if(event.keyCode == 13) {
        //         $scope.businessTable.getData();
        //         $scope.expressionListStatus = false;
        //         $scope.expressionContextStatus = false;
        //     }
        //
        // }
        //
        // $scope.searchKeyupExpression = function(event) {
        //     if(event.keyCode == 13) {
        //         $scope.expressionTable.getData();
        //         $scope.expressionContextStatus = false;
        //     }
        // }


        //根据业务线code和表达式code查维度
        $scope.queryRuleByBusinessCodeAndExpCode = function (ruleBusinessLineCode, item) {
            var temParams = {
                var_equal_ruleBusinessLineCode: ruleBusinessLineCode,
                var_equal_ruleExpCode: item.ruleExpCode
            }
            httpServer.post(URLAPI.ruleExpressiondimServiceAll, {
                params: JSON.stringify(temParams),
                pageIndex: -1,
                pageRows: -1
            }, function (res) {
                if (res.status == "S") {

                    $scope.expDimData = res.data;
                    if (res.data.length > 0) {
                        $scope.blurSimpleExp();
                    }
                } else {
                    JS.alert(res.msg, 'error', '确定');
                    console.error(res);
                }
            }, function (error) {
                console.error(error);
            });

        };


        //新增表达式
        $scope.addExpression = function () {
            $scope.expressionContext = {};
            placeholderArr.clear();
            conjunctionArr.clear();
            $scope.ruleArr = [];

            if ($scope.newExpRuleArr.length > 0) {
                $scope.expDimData = $scope.newExpRuleArr;
                // $("#expressionModal").modal('show');
                $scope.expressionContextStatus = true;
                $scope.actionStatus = true;
            } else {
                JS.alert('没有维度数据，请先维护维度');
            }
        };

        //删除表达式
        $scope.remove = function () {
            if ($scope.expressionContext.hasOwnProperty('ruleExpId')) {
                JS.confirm('删除表达式', '是否确定删除该表达式？', '确定', function () {
                    httpServer.post(URLAPI.ruleExpressionServiceDelete, {
                        params: JSON.stringify({
                            ruleExpId: $scope.expressionContext.ruleExpId
                        })
                    }, function (res) {
                        if (res.status == 'S') {
                            $scope.expressionTable.getData();
                            $scope.expressionContextStatus = false;
                            JS.alert('删除成功');
                        } else {
                            JS.alert(res.msg, 'error', '确定');
                            console.error(res);
                        }
                    }, function (error) {
                        console.error(error);
                    })
                })
            }
        }

        //相应维度的设置（new,当维度中的操作符和值发生改变时，需要改变对完整的规则表达式进行调整）
        $scope.getRuleExpDetail = function (item) {
            if (!$scope.expressionContext.ruleSimpleExp || $scope.expressionContext.ruleSimpleExp.length == 0) return;
            $scope.expressionContext.ruleExp = "SELECT 'Y' result from dual where " + $scope.expressionContext.ruleSimpleExp;
            //首先先验证唯独值类型是否符合预期的维度返回类型（暂不做验证，并对时间类型进行相关的处理）

            //循环数组生成真正的sql
            angular.forEach($scope.expDimData, function (ele) {
                //当维度中的的操作符和值均不为空时才进行操作
                if (ele.ruleDimValue && ele.ruleDimOperators) {
                    var statement = '';
                    var dimValue = angular.copy(ele.ruleDimValue);
                    //如果是Date数据类型，需要对数据类型进行处理
                    var dimValueArr = dimValue.split(',');
                    dimValue = '';
                    for (var i = 0; i < dimValueArr.length; i++) {
                        if (i == dimValueArr.length - 1) {
                            dimValue = dimValue + " '" + dimValueArr[i] + "'"
                        } else {
                            dimValue = dimValue + " '" + dimValueArr[i] + "',"
                        }
                    }

                    // }
                    if (ele.ruleDimOperators === 'in' || ele.ruleDimOperators === 'not in') {
                        statement = '(( ' + ele.ruleDimTargetSource + ' ) ' + ele.ruleDimOperators + ' ( ' + dimValue + ' ))';
                    } else if (ele.ruleDimOperators === 'between##and') {
                        //当是between时，需要对输入两个参数，以逗号隔开

                        var ruleValues = dimValue.split(',');
                        if (ruleValues.length != 2) {
                            JS.alert('当操作符是两者之间时，必须输出两个参数值，以逗号隔开', 'warning');
                            return;
                        } else {
                            statement = '(( ' + ele.ruleDimTargetSource + ' ) between ' + ruleValues[0] + ' and ' + ruleValues[1] + ')';
                        }
                    } else {
                        statement = '(( ' + ele.ruleDimTargetSource + ' ) ' + ele.ruleDimOperators + '  ' + dimValue + ' )';
                    }
                    //进行占位符替占拼装完整的sql
                    $scope.expressionContext.ruleExp = $scope.expressionContext.ruleExp.replace(new RegExp('#' + ele.ruleDimCode + '#', 'gm'), statement);
                }
            })
            //解析完整sql中的参数并作去重
            var params = $scope.resolveParamsFromExp($scope.expressionContext.ruleExp);
            $scope.expressionContext.ruleExpParams = JSON.stringify(params).replace('[', '').replace(']', '').replace(new RegExp('"', 'gm'), '');

        }

        //过滤表达式解析必填字段
        $scope.resolveParamsFromExp = function (expression) {
            var leftList = expression.split("[");
            //未去重的参数列表
            var orginalParamsList = [];
            for (var i = 0; i < leftList.length; i++) {
                if (leftList[i].indexOf(']') > 0) {
                    var ringht = leftList[i].split(']');
                    if (ringht.length > 0) {
                        orginalParamsList.push(ringht[0]);
                    }
                }
            }

            //去重
            var paramsList = []
            if (orginalParamsList.length > 0) {
                paramsList = [orginalParamsList[0]];
                //过滤重复元素
                for (var i = 1; i < orginalParamsList.length; i++) {
                    paramsList.indexOf(orginalParamsList[i]) < 0 ? paramsList.push(orginalParamsList[i]) : '';
                }
            }
            return paramsList;
        }

        //响应维度设置
        $scope.getRuleAllExpDetail = function (ruleDimOperators, index) {
            // //控制输入提示
            if (ruleDimOperators) {
                var ele = $($('#showTable').find('tbody').find('tr').find('td:last').find('input')[index]);
                if ('in' == ruleDimOperators || 'not in' == ruleDimOperators) {
                    ele.attr('data-original-title', '多个值用英文逗号分开');
                    $(document).tooltip({selector: '[data-toggle="tooltip"]'});
                } else if ('between##and' == ruleDimOperators) {
                    ele.attr('data-original-title', '输入格式：xxx##xxxx');
                    $(document).tooltip({selector: '[data-toggle="tooltip"]'});
                } else {
                    ele.removeAttr('data-original-title');
                    $(document).tooltip({selector: '[data-toggle="tooltip"]'});
                }

                if ($scope.expDimData[index].hasOwnProperty('ruleDimOperators') && $scope.expDimData[index].hasOwnProperty('ruleDimValue') &&
                    $scope.expDimData[index].ruleDimValue != undefined && $scope.expDimData[index].ruleDimValue != '' &&
                    $scope.expDimData[index].ruleDimValue != null) {
                    placeholderArr.add($scope.expDimData[index]);
                    judgJion();
                } else if ($scope.expDimData[index].hasOwnProperty('ruleDimOperators') && $scope.expDimData[index].hasOwnProperty('ruleDimValue') &&
                    $scope.expDimData[index].ruleDimValue != undefined && $scope.expDimData[index].ruleDimValue != '' &&
                    $scope.expDimData[index].ruleDimValue != null && $scope.expDimData[index].ruleDimValue == 0) {
                    placeholderArr.add($scope.expDimData[index]);
                    judgJion();
                } else {

                    resetRule(index);
                }
            } else {
                resetRule(index);
            }
            creatRuleNode();//
        };


        ///  侯兴章 2017年10月31日02时00分33秒
        function resetRule(index, rule) {
            // 条件维度值 条为空的时候，要清掉对应连接符
            var _placeholder = rule || $scope.expDimData[index].placeholder;
            var _exp = angular.copy($scope.expressionContext.ruleSimpleExp);
            if (!_exp) return;
            var _index = _exp.indexOf(_placeholder);//
            var reg;

            if (_exp.trim().length > _placeholder.length) {
                if (_index === 0) { // 当前连接符是第一个条件
                    reg = new RegExp("^" + _placeholder + "\\s+\\w{2,3}\\s+", "gim");
                } else {
                    reg = new RegExp("\\s+\\w{2,3}\\s+" + _placeholder, "gim");
                }

            } else {
                reg = new RegExp(_placeholder, "gim");
            }

            $scope.expressionContext.ruleSimpleExp = _exp.replace(reg, '');

            if (placeholderArr.has($scope.expDimData[index])) { // 删除当前条件

                placeholderArr.delete($scope.expDimData[index]);
            }
            $scope.blurSimpleExp();
        }

        /// 删除连接符节点 侯兴章
        $scope.removeRuleNote = function (item) {

            var deleteObj;
            for (var i in $scope.expDimData) {
                if ($scope.expDimData[i].placeholder === item.rule) {
                    deleteObj = $scope.expDimData[i]
                    break;
                }
            }
            if (placeholderArr.has(deleteObj)) { // 删除当前条件
                placeholderArr.delete(deleteObj);
            }

            resetRule(null, item.rule);
            creatRuleNode();

            $scope.expDimData.map(function (exp) {
                if (exp.placeholder === item.rule) {
                    exp.ruleDimValue = '';
                    exp.ruleDimOperators = ''
                }
            })


        };

        // 改变连接符的逻辑
        $scope.changeLogic = function (item) {
            var _rule = item.rule === 'and' ? 'or' : 'and';
            var node = $scope.ruleArr[parseInt(item.index) + 1];
            var r1 = item.rule + ' ' + node.rule;
            item.rule = _rule;

            var r2 = _rule + ' ' + node.rule;
            var _exp = angular.copy($scope.expressionContext.ruleSimpleExp);
            var reg = new RegExp(r1, "gim");
            _exp = _exp.replace(reg, r2);


            $scope.expressionContext.ruleSimpleExp = _exp.replace(reg, r2);
            $scope.blurSimpleExp();

        };
        // 创建规则节点  侯兴章
        function creatRuleNode() {
            $scope.ruleArr = [];
            var _exp = angular.copy($scope.expressionContext.ruleSimpleExp);
            if (!_exp) return;
            var _expArr = _exp.split(' ');
            for (var n in _expArr) {
                $scope.ruleArr.push({index: n, rule: _expArr[n]})
            }

        }

        $scope.keyupBlurSimpleExp = function (event) {
            if (event.keyCode == 13) {
                $scope.blurSimpleExp();
            }
        }

        //确定连接符并生成完整表达式
        $scope.blurSimpleExp = function () {
            if ($scope.expressionContext.ruleSimpleExp) {
                $scope.expressionContext.ruleAllExp = 'select \'Y\' from dual where (';
                var splitSimpleExpArr = $scope.expressionContext.ruleSimpleExp.split(' ');
                var reg = /[\u4E00-\u9FA5\uF900-\uFA2D]/;
                for (var i = 0; i < splitSimpleExpArr.length; i++) {
                    var str = splitSimpleExpArr[i];
                    if (reg.test(str)) {
                        JS.alert('出错啦，连接符不能有中文', 'error', '确定');
                        return;
                    } else {
                        if (i % 2 == 0) {
                            var obj;
                            if (str.indexOf('(') >= 0 && str.indexOf('#') >= 0) {
                                obj = buildValue(str.replace(/\(/g, ''));
                                if ('between##and' != obj.ruleDimOperators) {
                                    $scope.expressionContext.ruleAllExp += '(' + obj.ruleDimCode + ' ' + obj.ruleDimOperators + ' ' + returnStr(obj);
                                } else {
                                    $scope.expressionContext.ruleAllExp += '(' + returnStr(obj);
                                }
                            } else if (str.indexOf(')') >= 0 && str.indexOf('#') >= 0) {
                                obj = buildValue(str.replace(/\)/g, ''));
                                if ('between##and' != obj.ruleDimOperators) {
                                    $scope.expressionContext.ruleAllExp += obj.ruleDimCode + ' ' + obj.ruleDimOperators + ' ' + returnStr(obj) + ')';
                                } else {
                                    $scope.expressionContext.ruleAllExp += returnStr(obj) + ')';
                                }
                            } else {
                                obj = buildValue(str)
                                if ('between##and' != obj.ruleDimOperators) {
                                    $scope.expressionContext.ruleAllExp += obj.ruleDimCode + ' ' + obj.ruleDimOperators + ' ' + returnStr(obj);
                                } else {
                                    $scope.expressionContext.ruleAllExp += returnStr(obj);
                                }
                            }
                        } else {
                            $scope.expressionContext.ruleAllExp += ' ' + str + ' ';
                        }
                    }
                }
                $scope.expressionContext.ruleAllExp += ')';
            } else {
                $scope.expressionContext.ruleAllExp = '';
            }
        }
        function buildValue(placeholder) {
            var obj = {};
            angular.forEach($scope.expDimData, function (value) {
                if (placeholder == value.placeholder) {
                    obj = value;
                }
            })
            return obj;

        }


        //响应getRuleAllExp方法，判断维度填写完整是否完整，并组建连接符
        function judgJion() {
            //连接连接符
            if (!$scope.expressionContext.hasOwnProperty('ruleSimpleExp') || $scope.expressionContext.ruleSimpleExp == ''
                || $scope.expressionContext.ruleSimpleExp == null || $scope.expressionContext.ruleSimpleExp == undefined) {
                angular.forEach(placeholderArr, function (value, key) {
                    $scope.expressionContext.ruleSimpleExp = value.placeholder;
                })
                $scope.blurSimpleExp();
            } else {
                angular.forEach(placeholderArr, function (value) {
                    if ($scope.expressionContext.ruleSimpleExp == '') {
                        $scope.expressionContext.ruleSimpleExp = value.placeholder;
                    } else if ($scope.expressionContext.ruleSimpleExp.indexOf(value.placeholder) < 0) {
                        $scope.expressionContext.ruleSimpleExp += ' and ' + value.placeholder;
                    }
                });
                $scope.blurSimpleExp();
            }
        }

        // 清空规则
        $scope.emptyRule = function (item, index) {
            item.ruleDimValue = '';// 清空值
            item.ruleDimOperators = '';//
            $scope.getRuleAllExpDetail('', index)

        }

        function returnStr(obj) {
            var valArr = [];
            if ('Double' == obj.ruleDimDataType || 'Integer' == obj.ruleDimDataType) {
                return obj.ruleDimValue;
            } else {
                if ('between##and' == obj.ruleDimOperators) {
                    valArr = obj.ruleDimValue.split('##');
                    return '(' + obj.ruleDimCode + ' between \'' + valArr[0] + '\' and \'' + valArr[1] + '\')'
                } else if ('in' == obj.ruleDimOperators || 'not in' == obj.ruleDimOperators) {
                    valArr = obj.ruleDimValue.split(',');
                    for (var v = 0; v < valArr.length; v++) {
                        valArr[v] = '\'' + valArr[v] + '\''
                    }
                    return '(' + valArr.join(',') + ')';
                } else {
                    return '\'' + obj.ruleDimValue + '\'';
                }
            }
        }

        // 接收值集提交广播
        $scope.$on('formValueSet', function (event, data) {
            console.log('值集：', JSON.parse(data));
            var data = JSON.parse(data);
            var arr = [];
            angular.forEach(data.selectRowData, function (value, key) {
                arr.push(value[data.getColumn]);
            })
            $scope.expDimData[data.index].ruleDimValue = arr.join(',');
            $scope.getRuleAllExpDetail($scope.expDimData[data.index].ruleDimOperators, data.index);
        })

        //保存编辑
        $scope.saveExpression = function (formStatus) {
            if (!formStatus) {
                if (!$scope.expressionContext.hasOwnProperty('ruleBusinessLineCode')) {
                    $scope.expressionContext.ruleBusinessLineCode = $scope.expressionParams.var_like_ruleBusinessLineCode;
                }
                httpServer.post(URLAPI.saveOrUpdateAll, {
                    params: JSON.stringify({
                        expression: $scope.expressionContext,
                        dimExpressions: $scope.expDimData
                    })
                }, function (res) {
                    if (res.status == "S") {
                        $scope.expressionTable.getData();
                        $scope.actionStatus = false;
                        $scope.expressionContextStatus = false;
                        JS.alert('保存成功');
                    } else {
                        JS.alert(res.msg, 'error', '确定');
                        console.error(res);
                    }
                }, function (error) {
                    console.error(error);
                });
            }
        }

        /**
         * ----------------------执行结果操作-------------------------
         */

        $scope.resultModalTitle = '执行服务编辑';

        $scope.ruleBusTargetTypeData = [];
        $scope.ruleData = [];

        $scope.targetTypeStatusIsConfirmed = true;

        //操作符
        $scope.calcData = [
            {name: '加', code: '+'},
            {name: '减', code: '-'},
            {name: '乘', code: '*'},
            {name: '除', code: '/'},
            {name: '等于', code: '='}
        ];

        //获取影响类型
        $scope.getTargetType = function () {
            httpServer.post(URLAPI.getTargetType, {
                params: JSON.stringify({})
            }, function (res) {
                if (res.status == "S") {
                    for (var pro in res.data) {
                        $scope.ruleBusTargetTypeData.push({
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
        $scope.getTargetType();

        //获取维度
        $scope.queryRule = function (ruleBusinessLineCode) {
            httpServer.post(URLAPI.queryRule, {
                params: JSON.stringify({
                    var_equal_ruleBusinessLineCode: ruleBusinessLineCode
                }),
                pageIndex: -1,
                pageRows: -1
            }, function (res) {
                if (res.status == "S") {
                    $scope.ruleData = res.data;
                } else {
                    JS.alert(res.msg, 'error', '确定');
                    console.error(res);
                }
            }, function (error) {
                console.error(error);
            });
        };

        //新增执行结果
        $scope.addResult = function () {
            $scope.actionStataicPrams = [];
            $scope.actionStaticValueJson = {};
            $scope.ruleAction = {};
            $scope.actionStaticValueJson = {};
            $scope.ruleAction.actionStataicPrams = [];
            $scope.resultModalTitle = '服务实例编辑';
            $('#resultModal').modal('show');
        }

        //change影响类型
        $scope.changeTargetType = function (str) {
            if (str == 'confirmed') {
                $scope.targetTypeStatusIsConfirmed = true;
            } else if (str == 'serviceURL') {
                $scope.targetTypeStatusIsConfirmed = false;
                httpServer.post(URLAPI.saafWebserviceInfoServicesQuery, {
                    params: JSON.stringify({
                        var_equal_businessLineCode: $scope.expressionParams.var_like_ruleBusinessLineCode
                    })
                }, function (res) {
                    if (res.status == 'S') {
                        $scope.targetSourceArr = res.data;
                    } else {
                        JS.alert(res.msg, 'error', '确定');
                        console.error(res);
                    }
                }, function (error) {
                    console.error(error);
                })
            }
        }

        //编辑执行结果
        $scope.updateResult = function (item) {
            $scope.result = angular.copy(item);
            $scope.resultModalTitle = '修改执行结果';
            $scope.changeTargetType(item.ruleBusTargetType);
            for (var i = 0; i < $scope.ruleData.length; i++) {
                if ($scope.ruleData[i].ruleDimCode == item.ruleBusDim) {
                    $scope.result.ruleObj = $scope.ruleData[i];
                    break;
                }
            }

            $('#resultModal').modal('show');
        }

        //删除执行结果
        $scope.removeResult = function (item) {
            if (item) {
                JS.confirm('删除执行结果', '是否确定删除该执行结果？', '确定', function () {
                    httpServer.post(URLAPI.deleteRuleMappingBusinessService, {
                        params: JSON.stringify({
                            ruleMappBusId: item.ruleMappBusId
                        })
                    }, function (res) {
                        if (res.status == 'S') {
                            $scope.resultTable.getData();
                            JS.alert('删除成功');
                        } else {
                            JS.alert(res.msg, 'error', '确定');
                            console.error(res);
                        }
                    }, function (error) {
                        console.error(error);
                    })
                })
            }
        }

        //提交执行结果表单
        $scope.confirmResultl = function (formStatus) {
            if (!formStatus) {
                $scope.result.ruleBusName = $scope.result.ruleObj.ruleDimName;
                $scope.result.ruleBusDim = $scope.result.ruleObj.ruleDimCode;
                $scope.result.ruleExcCode = $scope.resultParams.var_equal_ruleExcCode;
                if ($scope.result.ruleBusTargetType == 'serviceURL') {
                    if ($scope.result.hasOwnProperty('ruleBusDimOperator')) {
                        $scope.result.ruleBusDimOperator = '';
                    }
                    if ($scope.result.hasOwnProperty('ruleBusDimValue')) {
                        $scope.result.ruleBusDimValue = '';
                    }
                } else if ($scope.result.ruleBusTargetType == 'confirmed') {
                    if ($scope.result.hasOwnProperty('ruleBusTargetSource')) {
                        $scope.result.ruleBusTargetSource = '';
                    }
                }
                httpServer.post(URLAPI.saveRuleMappingBusinessService, {
                    params: JSON.stringify($scope.result)
                }, function (res) {
                    if (res.status == "S") {
                        $('#resultModal').modal('hide');
                        $scope.resultTable.getData();
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
        }
        //弹窗服务对象
        $scope.ruleAction = {};
        //服务弹窗
        $scope.serchruleActionLov = function () {
            //弹窗服务对象

            $scope.ruleAction.actionStaticValue = '';
            $scope.ruleActionLov.clear();
            $scope.ruleActionLov.search();
        }

        //静态参数
        $scope.actionStataicPrams = [];
        //静态参数生成json对象
        $scope.actionStaticValueJson = {};
        //服务弹窗回掉
        $scope.ruleActionCallBack = function (key, value, curreList) {
            if (curreList.length == 1) {
                $scope.ruleAction.actionName = curreList[0].actionName;
                $scope.ruleAction.actionUrl = curreList[0].actionUrl;
                $scope.ruleAction.actionDynamicParam = curreList[0].actionDynamicParam;
                $scope.ruleAction.actionStaticParam = curreList[0].actionStaticParam;
                $scope.ruleAction.actionId = curreList[0].actionId;
                $scope.ruleAction.ruleExpCode = $scope.resultParams.ruleExpCode;
                $scope.ruleAction.actionExecuteFormula = curreList[0].actionExecuteFormula;
                $scope.ruleAction.actionFormula = curreList[0].actionFormula;
                $scope.ruleAction.actionReturnParam = curreList[0].actionReturnParam;
                $scope.ruleAction.actionReturnParamType = curreList[0].actionReturnParamType;
                $scope.actionStataicPrams = [];
                $scope.actionStaticValueJson = {};
                if ($scope.ruleAction.actionStaticParam) {
                    var actionStataicPramKeys = $scope.ruleAction.actionStaticParam.split(',');
                    angular.forEach(actionStataicPramKeys, function (key) {
                        var staticParam = new Object();
                        staticParam.key = key;
                        $scope.actionStataicPrams.push(staticParam);
                    })
                }
            }
        }

        //解析静态参数为json字符串
        $scope.resolveStaticParams = function () {
            $scope.ruleAction.actionStaticValue = JSON.stringify($scope.actionStaticValueJson);
        }

        $scope.saveRuleActionInstance = function () {
            $scope.ruleAction.ruleExpId = $scope.expressionContext.ruleExpId;
            httpServer.post(URLAPI.saveRuleActionInstance, {params: JSON.stringify($scope.ruleAction)}, function (res) {
                if (res.status == "S") {
                    $scope.resultTable.getData();
                    JS.alert(res.msg, 'success', '确定');
                    $('#resultModal').modal('hide');
                } else {
                    JS.alert(res.msg, 'error', '确定')
                }
            })
        }

        //服务更新操作
        $scope.updateRuleAction = function (item) {
            $scope.actionStataicPrams = [];
            $scope.actionStaticValueJson = {};
            $scope.ruleAction = angular.copy(item);
            if ($scope.ruleAction.actionStaticParam) {
                var actionStataicPramKeys = $scope.ruleAction.actionStaticParam.split(',');
                angular.forEach(actionStataicPramKeys, function (key) {
                    var staticParam = new Object();
                    staticParam.key = key;
                    $scope.actionStataicPrams.push(staticParam);
                })
            }
            if(item.actionStaticValue) {
                $scope.actionStaticValueJson = JSON.parse(item.actionStaticValue);
            }
            $('#resultModal').modal('show');
        }

        $scope.deleteRuleAction = function (item) {
            JS.confirm('删除服务实例', '是否确定删除该服务实例', '确定', function () {
                httpServer.post(URLAPI.deleteRuleActionInstance, {
                    params: JSON.stringify({
                        actionInstanceId: item.actionInstanceId
                    })
                }, function (res) {
                    if ('S' == res.status) {
                        $scope.resultTable.getData();
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

        //验证业务点在业务线中是唯一的
        $scope.validateBusinessPoint = function (key) {
            if (key && key !== $scope.oldParam.ruleBusinessPoint) {
                httpServer.post(URLAPI.validateBusinessPoint, {
                    params: JSON.stringify({
                        ruleBusinessPoint: $scope.expressionContext.ruleBusinessPoint,
                        ruleBusinessLineCode: $scope.ruleBusinessLineCode
                    })
                }, function (res) {
                    if ('S' == res.status) {
                        if (!res.data) {
                            JS.alert('该业务线中已存在该业务点，请重新输入', 'warning');
                            $scope.expressionContext.ruleBusinessPoint = '';
                        }
                    } else {
                        JS.alert(res.msg, 'error', '确定');
                    }
                }, function (error) {
                    JS.alert('删除失败', 'error', '确定');
                    console.error(error);
                })
            }
        }

        //验证权重在业务线中是唯一的
        $scope.validateRuleExpWeight = function () {
            if ($scope.validateRuleExpWeight && $scope.validateRuleExpWeight !== $scope.oldParam.ruleExpWeight) {
                httpServer.post(URLAPI.validateRuleExpWeight, {
                    params: JSON.stringify({
                        ruleExpWeight: $scope.expressionContext.ruleExpWeight,
                        ruleBusinessLineCode: $scope.ruleBusinessLineCode
                    })
                }, function (res) {
                    if ('S' == res.status) {
                        if (!res.data) {
                            JS.alert('该业务线已存在该权重,请重新输入', 'warning')
                            $scope.expressionContext.ruleExpWeight = '';
                        }
                    } else {
                        JS.alert(res.msg, 'error', '确定');
                    }
                }, function (error) {
                    console.error(error);
                })
            }
        }

        //表达式数学公式生成相关的数组供填写静态参数值
        $scope.createExpressionStaticParam = function () {
            $scope.ruleFormulaStaticValueJson = {}
            var staticPram = $scope.expressionContext.ruleFormulaStaticParam;
            if (!staticPram) return;
            var expressionFormulaStaticParams = staticPram.toString().split(",");
            if (expressionFormulaStaticParams.length == $scope.ruleFormulaStaticParam.length) return;
            angular.forEach(expressionFormulaStaticParams, function (item) {
                var staticParam = new Object();
                staticParam.key = item;
                $scope.ruleFormulaStaticParam.push(staticParam);
            })
        }

        //解析json为string
        $scope.resolveruleFormulaStaticParams = function () {
            $scope.expressionContext.ruleFormulaStaticValue = JSON.stringify($scope.ruleFormulaStaticValueJson);
        }

        //表达式中检验数学表达式是否符合规则
        $scope.validatePramInExpressionFormula = function () {
            if ($scope.expressionContext.ruleFormula.indexOf('[') == -1 || $scope.expressionContext.ruleFormula.indexOf(']') == -1) {
                JS.alert('公式的变量名必须用中括号[]包裹', 'warning');
                $scope.expressionContext.ruleFormula = ''
            }
            var params = $scope.expressionContext.ruleFormulaDynamicParam + "," + $scope.expressionContext.ruleFormulaStaticParam;
            //拆解公式中的参数
            var left = $scope.expressionContext.ruleFormula.split('[');
            var formulaParams = [];
            for (var i = 0; i < left.length; i++) {
                if (left[i].indexOf(']') > 0) {
                    var formulaParam = left[i].split(']')[0];
                    if (params.indexOf(formulaParam) == -1) {
                        JS.alert(formulaParam + '必须是存在与动态变量或静态变量中', 'warning');
                        $scope.expressionContext.ruleFormula = '';
                    }
                }
            }
        }

    }])
})