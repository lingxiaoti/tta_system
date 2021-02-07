/**
 * Created by dengdunxin on 2018/1/8.
 */
'use strict';
define(['app', 'pinyin', 'ztree', 'angularFileUpload','XLSX','GooFlow'], function (app, pinyin, ztree, angularFileUpload,XLSX,GooFlow) {
    app.useModule('angularFileUpload'); //按需加载弹出层模块
    app.controller('supplementAgreementStandardDetailCtrl', function ($filter, httpServer, URLAPI, Base64, FileUploader,$window, userSave, $timeout, $scope, $http, $location, $rootScope, $state, $stateParams,$compile, setNewRow, SIEJS,validateForm) {
        $scope.userData = JSON.parse(localStorage.getItem(appName + '_successLoginInfo')) || JSON.parse(sessionStorage[appName + '_successLoginInfo']);//用户信息
        $scope.isShowFlag = $scope.userData.userType == '45' ? 1 : 0;//用户类型:45-->BIC
        $scope.tabAction = 'supplementAgreementStandardDetailBusiness';//默认显示业务表单
        $scope.id = $stateParams.id;
        $scope.params = {};
        $scope.project= {
            status: 'A',//制作中
            saTeyp: 'standard',
        };
        $scope.supplementAgreementParams = {};
        $scope.fieldlDataList = [];
        $scope.dynamicFieldlDataList = [];//动态字段集合
        $rootScope.dynamicFieldlDataList = [];
        $scope.clause={};
        $scope.processStatus = "DRAFT";// status 流程状态  DRAFT.草稿   APPROVAL.审批中  ALLOW.审批通过  REFUSAL.审批驳回 CLOSED.已关闭
        $scope.relSupplierParams ={
            //functionId:'tta_side_agrt_header'//业务模块
        };
        $scope.attachmentParams = {};
        $scope.tableInfo = {};
        $scope.selectIdx = 0;

        /**********************************工作流配置 start**************************************/
        //获取url参数对象
        $scope.urlParams = urlToObj(location.hash);

        //先判断是否业务form是否有id 再判断流程form的id
        if ($stateParams.id) {
            $scope.id = $stateParams.id;
        } else if ($scope.urlParams.businessKey) {
            $scope.id = $scope.urlParams.businessKey;

        }

        //如果表单的ID与页面的头ID不一致，需要做兼容处理
        //url参数转对象
        function urlToObj(url) {
            var index = url.lastIndexOf('?');
            var params = url.substring(index + 1);
            var arr = params.split('&');
            var obj = {};
            arr.forEach(function (item) {
                obj[item.split('=')[0]] = item.split('=')[1];
            });
            return obj;
        }

        //获取单据ID
        function getId() {
            return $scope.urlParams.businessKey ? $scope.urlParams.businessKey : $scope.id;
        }

        //流程图参数
        $scope.processImageParams = {
            token: sessionStorage.getItem(window.appName + '_certificate') || localStorage.getItem(window.appName + '_certificate'),
            id: 'processImg',
            instanceId: $scope.urlParams.processInstanceId,
            key: 'TTA_SA_STANDAR_HEADER.-999' //流程唯一标识，在流程管理->流程设计->设计 流程中获取，流程配置时修改为对应表单的流程唯一标识
        };

        $scope.tabChange = function (name) {
            $scope.tabAction = name;
            if (!$scope.processImgLoad) {
                $scope.getProcessInfo(function () {
                    var p = $scope.processImageParams;
                    $timeout(function () {
                        processImageDraw(p.token, p.id, p.instanceId, p.key); //  绘制流程图
                        $scope.processImgLoad = true;
                    }, 100)
                });
            }
        };
        // 获取流程信息
        $scope.getProcessInfo = function (callback) {
            httpServer.post(URLAPI.processGet, {
                    'params': JSON.stringify({
                        processDefinitionKey: $scope.processImageParams.key,
                        businessKey: $scope.id
                    })
                },
                function (res) {
                    if (res.status === 'S') {
                        $scope.processImageParams.instanceId = res.data.processInstanceId;
                    }
                    callback && callback(res);
                });
        };


        $scope.submitApproval = function(){
            $("#submitApproval").attr("disabled","true");//提交前先禁用提交按钮
            var saStdHeaderId =  $scope.project.saStdHeaderId;
            if (saStdHeaderId == undefined && saStdHeaderId == null){
                SIEJS.alert("您还未保存此单据,请重新保存,再重新提交", "warning", "确定");
                return;
            }
            if($scope.project.status != undefined && $scope.project.status !='A'){
                SIEJS.alert("您当前单据状态不在制作中,不能提交审批,请在制作再提交审批", "warning", "确定");
                return;
            }
            $scope.extend = {
                "tasks_assignees": []
            };
            $scope.clause.isSkipApprove = $scope.project.isSkipApprove;//是否需要GM审批
            //$scope.clause.isSkipApprove = 'Y';//需要GM审批
            $scope.clause.deptCode = $scope.project.deptCode;//加入部门

            $scope.variables = []; //流程变量
            angular.forEach($scope.clause, function (value, key) {
                $scope.variables.push({
                    name: key,
                    type: 'string',
                    value: value
                });
            });

            $scope.properties = {
                "menucode": "HTGL",
                "i": ""
            };
            $scope.paramsBpm = {
                "extend": $scope.extend,
                "variables": $scope.variables,
                "properties": $scope.properties,
                "responsibilityId": "990021",
                "respId": "990021",
                "processDefinitionKey": "TTA_SA_STANDAR_HEADER.-999", //流程唯一标识，需修改为对应业务表单流程唯一标识
                "saveonly": false,
                "businessKey": $scope.project.saStdHeaderId, //单据ID
                "title": "补充协议标准" + $scope.project.saStdCode, //流程标题，修改为当前业务信息
                "positionId": 0,
                "orgId": 0,
                "userType": "20",
                "billNo": $scope.project.saStdCode
            };

            httpServer.post(URLAPI.processStart, {
                'params': JSON.stringify($scope.paramsBpm)
            }, function (res) {
                if (res.status == 'S') {
                    $scope.search(getId());
                    SIEJS.alert("提交成功", "success", "确定");
                } else {
                    $("#submitApproval").removeAttr("disabled");
                    SIEJS.alert(res.msg, "error", "确定");
                }
            }, function (err) {
                //$scope.btnC('0',false);
                $("#submitApproval").removeAttr("disabled");
                SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                // $("#TJSP").removeAttr("disabled");
            });

        };
        /**********************************工作流配置 end****************************************/

        //选择供应商
        $scope.getVendorCode = function () {
            $('#supplierCode').modal('toggle')
        };


        $scope.getDynamicVendorCode = function (val) {
            $scope.selectIdx = val;
            $('#dynamicSupplierCode').modal('toggle')
        };

        $scope.dynamicSupplierReturn = function(key, value, currentList) {
            $scope.dynamicFieldlDataList[$scope.selectIdx].filedValue = currentList[0].supplierName  + "_(" + currentList[0].supplierCode + ")";
        };
        //点击供应商弹出框选择
        $scope.selectSupplierReturn = function (key, value, currentList) {
            $scope.project.vendorCode = currentList[0].supplierCode;
            $scope.project.vendorName = currentList[0].supplierName;
        };

        //节点点击事件
        $scope.nodeClick = function(treeNode){
            //console.log(treeNode);
            $("#supplementExpandSubId").empty();
            //console.log($scope.supplementAgreementList.selectNodeId);
            if (!treeNode['saStdTplDefHeaderId']) {
                SIEJS.alert("请先选择补充协议模板", "warning", "确定");
                return;
            }
            //查询补充协议拓展信息
            httpServer.post(URLAPI.ttaSupplementExpandFind, {
                'params': JSON.stringify({
                    saStdTplDefHeaderId: treeNode['saStdTplDefHeaderId'],
                    saStdHeaderId:$scope.project.saStdHeaderId
                })
            }, function (res) {
                if (res.status == 'S') {
                    $scope.fieldlDataList = res.data;
                    $scope.createDomElement($scope.fieldlDataList);
                } else {
                    SIEJS.alert(res.msg, "error", "确定");
                    console.log("加载补充协议拓展信息失败")
                }
            }, function (err) {
                SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
            });

            $scope.ttaSaTplTabLineSearch(treeNode['saStdTplDefHeaderId']);
        };

        $scope.vrayTable = function(res){
            $scope.tableInfo.table= res.data;
            $scope.tableInfo.col = null ;
            $scope.tableInfo.row = null ;
            if (res.data && res.data.length > 0) {
                $scope.tableInfo.row = res.data.length;//行的长度
                if (res.data[0].length > 0) {//某一行
                    $scope.tableInfo.col = res.data[0].length;//列的长度
                    for (var i = 0; i<$scope.tableInfo.col; i++ ) {
                        $scope.ttaSaTplTabLineHeaderTable.push(String.fromCharCode(65 + i));
                    }
                }
            }
        };

        //查询标准模板的表格配置数据
        $scope.ttaSaTplTabLineSearch = function (saStdTplDefHeaderId) {
            $scope.ttaSaTplTabLineHeaderTable = [] ;
            httpServer.post(URLAPI.supplementAgreementTtaSaTplTabLineList, {
                    'params': JSON.stringify({
                        'saStdTplDefHeaderId':saStdTplDefHeaderId,
                        'headerInfo':$scope.project
                    })
                },
                function (res) {
                    if (res.status == 'S') {
                        $scope.vrayTable(res);
                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                },
                function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                }
            );
        };

        //保存补充协议标准表格信息
        $scope.btnSaveSaTable = function () {
            debugger;
            //console.log($scope.tableInfo.table);
            //console.log(JSON.stringify($scope.tableInfo.table));
            httpServer.post(URLAPI.supplementAgreementSaveOrUpdateAllTabLine, {
                    'params': JSON.stringify({
                        tableData:$scope.tableInfo.table,
                        saStdHeaderId:$scope.project.saStdHeaderId
                    })
                },
                function (res) {
                    if (res.status == 'S') {
                        $scope.searchSupplementAgreementSaTabLine();
                        SIEJS.alert(res.msg, 'success');
                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                },
                function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                }
            );
        };

        //查询补充协议标准表格信息
        $scope.searchSupplementAgreementSaTabLine = function () {
            $scope.ttaSaTplTabLineHeaderTable = [] ;
            httpServer.post(URLAPI.supplementAgreementSandardTtaSaTplTabLineFind, {
                    'params': JSON.stringify({
                        saStdHeaderId: $scope.id
                    })
                },
                function (res) {
                    if (res.status == 'S') {
                        $scope.vrayTable(res);
                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                },
                function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                }
            );
        };

        $scope.findPointValue = function(target, array) {
            let i = array.length - 1; // y坐标
            let j = 0; // x坐标
            return  $scope.compare(target, array, i, j);
        };

        $scope.compare = function(target, array, i, j) {
            if (array[i] === undefined || array[i][j] === undefined) {
                return "0";
            }
            const tempObject = array[i][j];
            const tempVal = array[i][j].pointIdentiCode;
            if (target === tempVal) {
                return tempObject.gridPointValue;
            } else if (target > tempVal) {
                return  $scope.compare(target, array, i, j+1);
            } else if (target < tempVal) {
                return  $scope.compare(target, array, i-1, j);
            }
        };

        //改变事件
        $scope.rowColChange = function(colValue){
            $scope.countTabLinePointValue(colValue);
        };

        function isArrayFn (o) {
            return Object.prototype.toString.call(o) === '[object Array]';
        }

        String.prototype.regexReplace = function(str,value) {
            str = str.toString();
            var reg = new RegExp(str,"g");
            return this.replace(reg,value);
        };

        //对输入空值,默认为0
        $scope.hs = function(x) {
            var f = parseFloat(x);
            if (isNaN(f)) {
                return 0;
            }
            return f;
        };

        //计算每个格子的内容
        $scope.countTabLinePointValue = function(value){
            var colValues = value;
            var joinCode = "{" + colValues.pointIdentiCode + "}";
            var regex = /\{(.+?)\}/g;   //{123}
            var rowLength = $scope.tableInfo.table.length;
            for (var rowIdx = 0;rowIdx < rowLength;rowIdx++) {
                var colsEle = $scope.tableInfo.table[rowIdx];
                for (var colIdx = 0 ;colIdx < colsEle.length;colIdx++) {
                    var colJsonObject = $scope.tableInfo.table[rowIdx][colIdx];//代表每个格子
                    if (!colJsonObject.pointValue || !colJsonObject.pointExpression) continue;
                    var pointExpression = colJsonObject.pointExpression;//替换某些参数之后公式表达式
                    var matchArray = pointExpression.match(regex);
                    if (matchArray && isArrayFn(matchArray)) {
                        if (matchArray.length > 0 && matchArray.indexOf(joinCode) > -1) {//如果当前输入的格子坐标在表格数据中找到,需要替换参数
                            pointExpression = pointExpression.regexReplace(joinCode,parseFloat($scope.hs(value.gridPointValue)));
                            //处理余下的公式
                            var newMatchArr = matchArray.filter(function (item) {
                                return item !== joinCode;
                            });
                            for (var i = 0; i < newMatchArr.length; i++) {
                                var arrElement = newMatchArr[i];
                                arrElement = arrElement.replace(/\{|\}/g,"");
                                var retrunVal = $scope.findPointValue(arrElement,$scope.tableInfo.table);
                                if (!retrunVal) {
                                    retrunVal = "0";
                                }
                                pointExpression = pointExpression.regexReplace("{" + arrElement + "}",parseFloat(retrunVal));
                            }
                            var executeVal = $scope.$eval(pointExpression);
                            colJsonObject.gridPointValue = executeVal;
                        }

                    }
                }
            }

            //最后再统计计算一遍格子的值
            $scope.countTotalTabLinePointValue();
        };

        //统计计算每个格子的值
        $scope.countTotalTabLinePointValue = function(){
            if (!$scope.tableInfo.table) return;
            var regex = /\{(.+?)\}/g;   //{123}
            for (var rowIndex = 0; rowIndex < $scope.tableInfo.table.length;rowIndex++ ) {
                var row = $scope.tableInfo.table[rowIndex];//行数据
                for (var colIndex = 0; colIndex < row.length; colIndex++) {
                    var col = $scope.tableInfo.table[rowIndex][colIndex];//列数据
                    if (!col.pointValue || !col.pointExpression) continue;
                    var pointExpression = col.pointExpression;//格子的表达式(计算公式)
                    var matchAttr = pointExpression.match(regex);
                    //console.log("查看匹配的字符串的值---->")
                    //console.log(matchAttr.toString());
                    if (matchAttr && angular.isArray(matchAttr))  {
                        if (matchAttr.length > 0) {
                            for (var m = 0 ; m < matchAttr.length; m++) {
                                var matchEle = matchAttr[m];
                                matchEle = matchEle.replace(/\{|\}/g,"");
                                var retrunVal = $scope.findPointValue(matchEle,$scope.tableInfo.table);
                                if (!retrunVal) {
                                    retrunVal = "0";
                                }
                                pointExpression = pointExpression.regexReplace("{" + matchEle + "}",parseFloat(retrunVal));
                            }

                            var executeVal = $scope.$eval(pointExpression);
                            col.gridPointValue = executeVal;
                        }
                    }
                }
            }
        };

        //创建dom树
        $scope.createDomElement = function(dataList){
            //debugger;
            $scope.dynamicFieldlDataList = dataList;
            $scope.dynamicFieldlDataList.map(function (item, index) {
                var flag = item.filedName === '乙方' || item.filedName ===  '丙方';
                var fieldType = item.filedType || 'TEXT';
                var node = '<div class="form-group col-xs-4 col-md-3">';
                if (flag) {
                    node += '<div class="input-group input-group-xs">\n' +
                        '   <label class="input-group-addon bn"><span\n' +
                        '           class="w100"><i class="red">*</i>' + item.filedName + ':</span></label>\n' +
                        '   <input type="text" class="form-control radius3" required\n' +
                        '          name="vendorCode_"' + index + '\t disabled\n' +
                        '          data-required-msg="供应商编号"\n' +
                        '          ng-model="dynamicFieldlDataList['+index+'][\'filedValue\']">\n' +
                        '   <span class="input-group-btn ng-scope">\n' +
                        '            <button class="btn btn-default" ng-click="getDynamicVendorCode('+ index + ')" type="button">\n' +
                        '                <i class="fa fa-search"></i>\n' +
                        '            </button>\n' +
                        '        </span>\n' +
                        '</div>';
                }
                switch (fieldType) {
                    case 'TEXT':
                        if (flag) {
                            break;
                        }
                        if (item.dicCode) {
                            node = node + '<div class="input-group input-group-xs ">' +
                                '       <label class="input-group-addon bn">' +
                                '            <span class="w100"><i class="red">* </i>' + item.filedName + '</span>' +
                                '        </label>' +
                                '<div lookup-code="' + item.dicCode + '"' +
                                '       ng-model="dynamicFieldlDataList['+index+'][\'filedValue\']"' +
                                '       ng-required="false"'+
                                '       data-required-msg="内容为必填" ' +
                                '       data-error-msg="内容不能为空"' +
                                '       id="project_' + item.saStdFieldCfgLineId + '"' +
                                '       name="project_' + item.saStdFieldCfgLineId + '"' +
                                '       ng-disabled="false" ' +
                                '       ng-readonly="false"' +
                                '></div></div>';
                        } else {
                            node = node + '<div class="input-group input-group-xs">\n' +
                                '                               <label class="input-group-addon bn"><span\n' +
                                '                                       class="w100"><i class="red">*</i>'+item.filedName+':</span>' +
                                '                               </label>\n' +
                                '                               <input type="text" class="form-control radius3"\n' +
                                '                                      id="project_'+item.saStdFieldCfgLineId+'"\n' +
                                '                                      name="project_'+item.saStdFieldCfgLineId+'"\n' +
                                '                                      required \n' +
                                '                                      data-required-msg="必填内容"' +
                                '                                      data-required-msg="输入框值不能为空"' +
                                '                                      placeholder="请输入文本值,长度限制为300"' +
                                '                                      title="请输入文本值,长度限制为300"' +
                                '                                      ng-model="dynamicFieldlDataList['+index+'][\'filedValue\']"' +
                                '                                      maxlength="300">\n' +
                                '                                      '+
                                '           </div>';
                        }
                        break;
                    case 'DATE':
                        if (flag) {
                            break;
                        }
                        var format = "yyyy-mm-dd";//默认选择年月日
                        var minView = 2;
                        var startView = 2;
                        switch (item.filedName) {
                            case "年":
                                format = "yyyy";
                                minView = 4;
                                startView = 4;
                                break;
                            case "月":
                                format = "mm";
                                minView = 3;
                                startView = 3;
                                break;
                            case "日":
                                format = "dd";
                                minView = 2;
                                startView = 2;
                                break;
                        }
                        item.isToday = false;
                        item.required = true;
                        item.readonly = true;

                        node = node + '<div class="input-group input-group-xs ">' +
                            '       <label class="input-group-addon bn">' +
                            '            <span class="w100"><i class="red">* </i>' + item.filedName + '</span>' +
                            '        </label>' +
                            '<input type="text" class="form-control radius3 input-xs" ' +
                            '               date-time-picker ' +
                            '               data-is-today="' + item.isToday + '"' +
                            '               data-date-format="' + format + '"' +
                            '               data-min-view="' + minView + '"' +
                            '               data-start-view="' + startView + '"' +
                            '               ng-model="dynamicFieldlDataList['+index+'][\'filedValue\']"' +
                            '               ng-required="' + (item.required || false) + '" ' +
                            '               data-required-msg="日期不能为空" ' +
                            '               data-error-msg="日期不能为空"' +
                            '               id="project_' + item.saStdFieldCfgLineId + '"' +
                            '               name="project_' + item.saStdFieldCfgLineId + '"' +
                            '               ng-disabled="' + (item.disabled || false) + '" ' +
                            '               ng-readonly="' + (item.readonly || false) + '"' +
                            '               style="background-color: white;"' +
                            '>' +
                            '</div>';
                        break;
                    case 'NUMBER':
                        if (flag) {
                            break;
                        }
                        item.disabled = false;
                        item.readonly = false;
                        node = node + '<div class="input-group input-group-xs ">' +
                            '       <label class="input-group-addon bn">' +
                            '            <span class="w100"><i class="red">* </i>' + item.filedName + '</span>' +
                            '        </label>' +
                            '  <input   type="text" class="form-control radius3"' +
                            '       maxlength="300"' +
                            '       ng-model="dynamicFieldlDataList['+index+'][\'filedValue\']"' +
                            '       data-required-msg="输入框内容为必填" ' +
                            '       data-error-msg="输出口内容不能为空"' +
                            '       id="project_' + item.saStdFieldCfgLineId + '"' +
                            '       name="project_' + item.saStdFieldCfgLineId + '"' +
                            '       placeholder="请输入数字,保留两位小数" ' +
                            '       title="请输入数字,保留两位小数"' +
                            '       to-change-number' +
                            '       ng-disabled="' + (item.disabled || false) + '" ' +
                            '       ng-readonly="' + (item.readonly || false) + '"';
                        node = node + '></div>';
                        break;
                }
                node = node + '</div>';
                $("#supplementExpandSubId").append(node);
            });
            $compile($("#supplementExpandSubId"))($scope);

        };


        //保存
        $scope.btnSave = function (formInvalid) {
            if (!validateForm(formInvalid)) {
                return;
            }
            //console.log($scope.dynamicFieldlDataList);
            httpServer.post(URLAPI.ttaSupplementAgreementSave, {
                    params: JSON.stringify({
                        supplementAgreementHead:$scope.project,
                        dynamicFieldlDataList:$scope.dynamicFieldlDataList
                    })
                },
                function (res) {
                    if (res.status == 'S') {
                        $scope.id = res.data.saStdHeaderId;
                        $scope.search();
                        $scope.searchProposalLine();
                        $scope.searchSaStdFieldLine();
                        SIEJS.alert(res.msg, 'success');
                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                }, function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                })
        };

        //作废
        $scope.btnDiscard = function () {
            $("#supplementDiscardId").attr("disabled",true);
            var saStdHeaderId =  $scope.project.saStdHeaderId;
            if (saStdHeaderId == undefined && saStdHeaderId == null){
                SIEJS.alert("您还未保存此单据,不能进行作废操作", "warning", "确定");
                return;
            }
            //单据状态
            if($scope.project.status != undefined && $scope.project.status != 'A'){
                SIEJS.alert("您当前单据状态不在制作中,不能作废", "warning", "确定");
                return;
            }
            SIEJS.confirm('提示', '您确定要作废'+$scope.project.saStdCode +'这条单据吗？', '确定', function () {
                httpServer.post(URLAPI.ttaSupplementAgreementDisSicrad,
                    {params: JSON.stringify($scope.project)},
                    function (res) {
                        if (res.status == 'S') {
                            $scope.search();
                            SIEJS.alert(res.msg, 'success', '确定');
                        } else {
                            SIEJS.alert(res.msg, "error", "确定");
                        }
                    }, function (err) {
                        console.error(err);
                        $("#supplementDiscardId").removeAttr("disabled");
                        SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                    });
            });
        };

        //变更
        $scope.btnChange = function () {
            var saStdHeaderId =  $scope.project.saStdHeaderId;
            if (saStdHeaderId == undefined && saStdHeaderId == null){
                SIEJS.alert("您还未保存此单据,不能进行变更操作", "warning", "确定");
                return;
            }
            //单据状态
            if($scope.project.status != undefined && $scope.project.status != 'C'){
                SIEJS.alert("您当前单据状态不在审批通过中,不能进行变更操作", "warning", "确定");
                return;
            }

            SIEJS.confirm('变更', '是否确定变更？', '确定', function () {
                httpServer.post(URLAPI.ttaSupplementAgreementChange, {
                        'params': JSON.stringify($scope.project)
                    },
                    function (res) {
                        if (res.status == 'S') {
                            var xFlag = res.result.xFlag;
                            var xMsg = res.result.xMsg;
                            if (xFlag != 1) {
                                SIEJS.alert(xMsg, "error", "确定");
                                return;
                            }
                            //$scope.rowData = res.data;
                            //setNewRow($scope.dataTable.selectRow,$scope.rowData);// 更新成功，只更新当前行，不必再去重载当前表格
                            $scope.id = res.data.saStdHeaderId;
                            $scope.search();
                            $scope.searchProposalLine();
                            $scope.searchSaStdFieldLine();
                            $scope.searchSupplementAgreementSaTabLine();
                            SIEJS.alert(res.msg, 'success');
                        } else {
                            SIEJS.alert(res.msg, "error", "确定");
                        }
                    },
                    function (err) {
                        SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                    }
                );

            })

        };

        //合同打印
        $scope.btnPrint = function(){
            httpServer.post(URLAPI.supplementAgreementStandardPrint, {
                    'params': JSON.stringify($scope.project)
                },
                function (res) {
                    if (res.status == 'S') {
                        var url = URLAPI.ttaSideAgrtHeaderDownLoad + '?fileId=' + res.data;
                        window.open(url, [""], [""]);//避开因同源策略而造成的拦截
                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                },
                function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                }
            );
        };
        //var n = arrayDeleteItem($scope.dataTable.list, item, 'poposalId');
        //$scope.dataTable.count = $scope.dataTable.count - n;
        //删除poposal
        $scope.btnDelProposalSupplier = function () {
            SIEJS.confirm('提示', '确定要删除所选的信息吗？', '确定', function () {
                var item = $scope.dataTable.selectRow;
                httpServer.post(URLAPI.contractProposalVendorDelete, {
                    'params': JSON.stringify({
                        id: item.saStdProposalLine
                    })
                }, function (res) {
                    if (res.status == 'S') {
                        $scope.dataTable.selectRow = null;
                        $scope.searchProposalLine();
                        SIEJS.alert(res.msg, "success", "确定");
                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                }, function (err) {
                    console.error(err);
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                });

            });
        };

        //搜索
        $scope.search = function () {
            httpServer.post(URLAPI.ttaSupplementAgreementFind, {
                'params': JSON.stringify({
                    saStdHeaderId: $scope.id
                })
            }, function (res) {
                if (res.status == 'S') {
                    $scope.project = res.data;
                } else {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                    console.log("查询失败");
                }
            }, function (err) {
                SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
            });
        };

        //新增poposal
        $scope.btnPoposalSupplier = function () {
            $scope.proposalSupplierList.search(1);
        };

        //新增poposal弹框保存
        $scope.setProposalSupplierList = function (key, value, currentList) {
            if ($scope.project.saStdHeaderId == undefined || $scope.project.saStdHeaderId == null) {
                SIEJS.alert("头信息未保存,请先保存头信息再进行操作", 'warning');
                return;
            }
            httpServer.post(URLAPI.saveContractProposalVendor, {
                'params': JSON.stringify({
                    project:$scope.project,
                    selectRow:currentList.length == 0 ? $scope.proposalSupplierList.selectRow : currentList[0]
                })
            }, function (res) {
                if (res.status == 'S') {
                    $scope.searchProposalLine();
                    SIEJS.alert(res.msg, 'success', '确定');
                } else {
                    SIEJS.alert(res.msg, "error", "确定");
                }
            }, function (err) {
                SIEJS.alert("保存失败! " + err.msg, "error", "确定");
            });
        };

        //查询Proposal供应商
        $scope.searchProposalLine = function(){
            $scope.params.saStdHeaderId = $scope.id;
            $timeout(function () {
                $scope.dataTable.search(1);
            },200)
        };

        //查询补充协议拓展信息
        $scope.searchSaStdFieldLine = function(){
            $("#supplementExpandSubId").empty();
            httpServer.post(URLAPI.findSupplementExpandInfo, {
                'params': JSON.stringify({
                    saStdHeaderId: $scope.id
                })
            }, function (res) {
                if (res.status == 'S') {
                    $scope.fieldlDataList = res.data;
                    $scope.createDomElement($scope.fieldlDataList);
                    $rootScope.dynamicFieldlDataList = $scope.dynamicFieldlDataList;
                } else {
                    SIEJS.alert(res.msg, "error", "确定");
                    console.log("加载补充协议拓展信息失败")
                }
            }, function (err) {
                SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
            });
        };

        $scope.isSkipApproveFun = function () {
            httpServer.post(URLAPI.updateSupplementSkipStatus, {
                'params': JSON.stringify({
                    supplementAgreementHead : getId(),
                    isSkipApprove : $scope.project.isSkipApprove
                })
            }, function (res) {
                if (res.status == 'S') {
                    console.log(JSON.stringify(res.data));
                    $scope.project.isSkipApprove = res.data.isSkipApprove;
                    SIEJS.alert('操作成功', "success", "确定");
                }
            }, function (err) {
                SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
            });
        };

        $scope.getContractVendorProposal = function () {
            $scope.proposalSupplierList.search();
            $("#proposalSupplierLov").modal('toggle');
        };

        if ($scope.id) {
            $scope.search();
            $scope.searchProposalLine();
            $scope.searchSaStdFieldLine();
            $scope.searchSupplementAgreementSaTabLine();
        }
    });
});