/**
 * Created by Ly on 2018/8/21.
 */
'use strict';
define(['app', "angularFileUpload","webconfig"], function (app,angularFileUpload,webconfig) {
    app.useModule('angularFileUpload');//按需加载模块
    app.controller('budgetBatchSaveCtrl', ['$scope', '$location', 'URLAPI', 'httpServer', 'SIE.JS', '$stateParams', '$window', 'limitNumLength', 'pageResp',
        function ($scope, $location, URLAPI, httpServer, JS, $stateParams, $window, limitNumLength, pageResp) {
            $scope.action = $location.$$search.action;
            //  附件上传组件 的状态。当前表单为 detail时不可以上传，其它状态可以上传
            $scope.fileViewOnly = $scope.action ==='detail' ? 0:1;
            $scope.respId = $location.$$search.respId;
            $scope.fileType = webconfig.fileFormat.emsFileType;

            // if($window.localStorage["EMS_successLoginInfo"]){
            //     var userInfo=JSON.parse(localStorage.getItem(appName + '_successLoginInfo'));
            //     //console.log(userInfo)
            // }
            $scope.bcUploadUrl = 'batchUploadFile';
            $scope.edit = false; //页面内容可编辑状态
            if ('new' != $scope.action) {
                $scope.edit = true;
            }
            $scope.formParams.bcBudgetBatchStatus = 'create';
            $scope.formParams.respId = $scope.respId;
            $scope.formParams.sourceType = 'EMS';

            var personId = $scope.userData.personId;
            //data 放必填字段
            $scope.dataTableAdd = {
                legend: ['渠道', '部门', '日期', '费用科目', '调整金额', '备注', '操作'],
                data: [{
                    channelType: '', departmentName: '', expenseDate: '', expenseAccountName: '',
                    adjustAmount: ''
                }],
                rowIndex: '',
                positionData: [],
                channelHList: [],
                channelLineList: [],
                addRow: function () {
                    this.data.push({
                        channelType: '', departmentName: '', expenseDate: '', expenseAccountName: '',
                        adjustAmount: ''
                    });
                },
                delete: function (index) {
                    this.data.splice(index, 1);
                    $scope.dataTableAdd.sums(this.data);
                },
                rowBtnClick: function (index) {
                    this.rowIndex = index;
                    console.log(this.rowIndex);
                },
                getPosition: function () {
                    var params = {};

                    debugger;

                    if ($scope.userType == '10') {

                        if ('draft' == $scope.action || 'refusal' == $scope.action) {
                        params = {
                            personId: $scope.formParams.substitutePersonId, //userInfo.personId
                            budgetMaintain: 'Y',
                            userType: 10
                        };
                        }else{
                            params = {
                                personId: personId, //userInfo.personId
                                budgetMaintain: 'Y',
                                userType: 10
                            };

                        }
                    } else {

                        if ('draft' == $scope.action || 'refusal' == $scope.action) {
                                params = {
                                personId:  $scope.formParams.substitutePersonId, //userInfo.personId
                                budgetMaintain: 'Y',
                                orgId: $scope.orgId,
                                userType: 20
                            };
                        }else{
                            params = {
                                personId: personId, //userInfo.personId
                                budgetMaintain: 'Y',
                                orgId: $scope.orgId,
                                userType: 20
                            };
                        }
                    }
                    httpServer.post(URLAPI.findPosition, {
                        params: JSON.stringify(params)
                    }, function (res) {
                        if (res.status == 'S') {
                            $scope.dataTableAdd.positionData = res.data;
                        } else {
                            JS.alert(res.msg, 'error', '确定');
                        }
                    }, function (error) {
                        JS.alert('请求失败', 'error', '确定');
                    })
                },
                findChannelHList: function () {
                    var params = {
                        respId: $scope.respId
                    }
                    httpServer.post(URLAPI.findChannelByUserList, {
                        params: JSON.stringify(params)
                    }, function (res) {
                        if (res.status == 'S') {
                            $scope.dataTableAdd.channelHList = res.data;
                            if ('new' == $scope.action) {
                                $scope.formParams.channelType = res.data[0].channelCode;
                            }
                            if ($scope.formParams.channelType) {
                                $scope.dataTableAdd.changeHChannel();
                            }

                        } else {
                            JS.alert(res.msg, 'error', '确定');
                        }
                    }, function (error) {
                        JS.alert('请求失败', 'error', '确定');
                    })
                },
                changeHChannel: function (code) {
                    if ($scope.formParams.channelType == 90) {
                        $scope.dataTableAdd.channelLineList = angular.copy($scope.dataTableAdd.channelHList);
                        angular.forEach($scope.dataTableAdd.channelLineList, function (data, index) {
                            if (data.channelCode == 90) {
                                $scope.dataTableAdd.channelLineList.splice(index, 1)
                            }
                        })
                    } else {
                        angular.forEach($scope.dataTableAdd.channelHList, function (data, index) {
                            if (data.channelCode == $scope.formParams.channelType) {
                                $scope.dataTableAdd.channelLineList.push(data);
                            }
                        })

                    }

                },
                sums: function (dataList) {
                    $scope.formParams.sumsCount = 0;
                    angular.forEach(dataList, function (data, index) {
                        if (data.adjustAmount) {
                            $scope.formParams.sumsCount = $scope.dataTableAdd.accAdd($scope.formParams.sumsCount, data.adjustAmount);
                        }

                    })
                },
                accAdd: function accAdd(arg1, arg2) {
                    var r1, r2, m;
                    try {
                        r1 = arg1.toString().split(".")[1].length
                    } catch (e) {
                        r1 = 0
                    }
                    try {
                        r2 = arg2.toString().split(".")[1].length
                    } catch (e) {
                        r2 = 0
                    }
                    m = Math.pow(10, Math.max(r1, r2))
                    return (arg1 * m + arg2 * m) / m
                }

            };

            //oa通用选择部门回调
            $scope.departSelect = function (key, value, currentList) {
                $scope.dataTableAdd.data[$scope.dataTableAdd.rowIndex].departmentName = value;
                $scope.dataTableAdd.data[$scope.dataTableAdd.rowIndex].departmentId = key;
            };
            //oa通用选择部门回调
            $scope.cancelDtSelect = function () {
                $scope.dataTableAdd.data[$scope.dataTableAdd.rowIndex].departmentName = '';
                $scope.dataTableAdd.data[$scope.dataTableAdd.rowIndex].departmentId = '';
            }

            // $scope.dataTableAdd.findChannelHList();
            // $scope.dataTableAdd.getPosition();


            //获取查看数据后响应ajax回调
            $scope.$on("businessLoad", function (e, data) {
                if (data) {
                    var getFormData = angular.copy($scope.formParams);
                    for (var k in getFormData.budgetBatch) {
                        $scope.formParams[k] = getFormData.budgetBatch[k];
                    }
                    $scope.dataTableAdd.data = $scope.formParams.budgetBatchLines;
                    $scope.dataTableAdd.sums($scope.dataTableAdd.data);
                    if ('detail' == $scope.action || 'approval' == $scope.action) {
                        //查看，审批通过，审批中渠道不可编辑
                        $scope.dataTableAdd.channelHList.push({
                            channelCode: $scope.formParams.channelType,
                            channelType: $scope.formParams.channelType,
                            channelName: $scope.formParams.channelName
                        });
                        $scope.dataTableAdd.channelLineList = $scope.dataTableAdd.channelHList;
                    } else {
                        $scope.dataTableAdd.findChannelHList();
                    }
                    $scope.userType = $scope.formParams.userType;
                    personId = $scope.formParams.personId;
                    $scope.dataTableAdd.getPosition();

                    angular.forEach($scope.formParams.files, function (item, index) {
                        item.fileName = item.sourceFileName;
                        item.fileSize = item.fileSize + 'MB';
                    })
                    $scope.fileData = $scope.formParams.files;
                }
            });

            /* $scope.$on("changeCurrentResp", function (e, m) {
                 // console.log($scope.orgId,'orgId***')
                 $scope.orgId = $scope.orgId;

                 if ('new' == $scope.action) {
                     debugger;
                     $scope.dataTableAdd.getPosition();
                     $scope.dataTableAdd.findChannelHList();
                 } else {
                     $scope.apiDetailParams.respId = $scope.respId;
                     $scope.apiDetailParams.sysFlag = $location.$$search.sysFlag ? $location.$$search.sysFlag : '';
                 }
             })*/
            var resp = pageResp.get();
            console.log(resp,'___')


            if ('new' == $scope.action) {
                $scope.userType = '';
                if (resp) {
                    $scope.orgId = resp.orgBean.orgId;
                    angular.forEach(resp.proFileBeans, function (data, index) {
                        if (data.profileCode == 'USER_TYPE') {
                            $scope.userType = data.profileValue;
                        }
                    });
                }
                $scope.dataTableAdd.getPosition();
                $scope.dataTableAdd.findChannelHList();
            } else {
                $scope.apiDetailParams.respId = $scope.respId;
                $scope.apiDetailParams.sysFlag = $location.$$search.sysFlag ? $location.$$search.sysFlag : '';
            }

            $scope.submit = function () {
                if (!$scope.formParams.channelType) {
                    JS.alert('请选择渠道', 'error', '确定');
                    return;
                }
                if (!$scope.formParams.periodType) {
                    JS.alert('请选择期间类型', 'error', '确定');
                    return;
                }

                if (!$scope.formParams.positionId) {
                    JS.alert('请选择职位', 'error', '确定');
                    return;
                }

                var breakFor = -1, zeroVal = false, aTooLong = false;
                angular.forEach($scope.dataTableAdd.data, function (data, index) {
                    if (breakFor == -1 && !zeroVal) {
                        for (var i in data) {
                            if (i != 'bcBudgetBatchLDesc' && !data[i]) {
                                breakFor = index + 1;
                                return;
                            }
                            if (i == 'adjustAmount' && data[i] == 0) {
                                breakFor = index + 1;
                                zeroVal = true;
                                return;
                            }
                            if (i == 'adjustAmount' && data[i] && data[i].toString().length > 15) {
                                console.log(data[i].toString().length);
                                breakFor = index + 1;
                                aTooLong = true;
                                return;
                            }
                        }
                    }

                })
                if (zeroVal) {
                    JS.alert('录入批次行：第' + breakFor + '行中的调整金额不能等于0！', 'error', '确定');
                    return;
                }
                if (aTooLong) {
                    JS.alert('录入批次行：第' + breakFor + '行中的调整金额数字不能大于15位！', 'error', '确定');
                    return;
                }

                if (breakFor != -1) {
                    JS.alert('录入批次行：第' + breakFor + '行中有标*的列信息未填写！', 'error', '确定');
                    return;
                }

                $scope.formParams.budgetBatchLines = $scope.dataTableAdd.data;

                httpServer.post(URLAPI.budgetBatchSave, {
                    params: JSON.stringify($scope.formParams)
                }, function (res) {
                    if (res.status == 'S') {
                        $scope.formParams.bcBudgetBatchHId = res.bcBudgetBatchHId;
                        JS.alert('保存成功');
                    } else {
                        JS.alert(res.msg, 'error', '确定');
                    }
                }, function (error) {
                    JS.alert('请求失败', 'error', '确定');
                })


            }


            /* 保存
                       必须要返回 true 或 false
                   */
            $scope.draftEvent.cusValidator = function () {
                $scope.formParams.buttonStatus = "save";
                $scope.formParams.budgetBatchLines = $scope.dataTableAdd.data;
                $scope.formParams.respId = $scope.respId;
                $scope.formParams.files = $scope.fileData;
                $scope.formParams.fileIds = [];

                for (var i in $scope.fileData) {
                    $scope.formParams.fileIds.push($scope.fileData[i].fileId)
                }
                $scope.submit();
                //debugger
                return false;
            }


            /* 若需要在提交之前再进行其它的业务判断请在业务表单页面进行 $scope.btnSubmit.cusValidator()
                        必须要返回 true 或 false
                    */
            $scope.submitEvent.cusValidator = function () {
                $scope.formParams.buttonStatus = "commit";
                $scope.formParams.budgetBatchLines = $scope.dataTableAdd.data;
                $scope.formParams.respId = $scope.respId;
                $scope.formParams.files = $scope.fileData;
                $scope.formParams.fileIds = [];

                $scope.formParams.orgId = $scope.orgId;
                // $scope.formParams.channelType = $scope.formParams.channelType;
                $scope.formParams.amount = $scope.formParams.sumsCount;
                $scope.formParams.commitUserId = $scope.formParams.personId;
                $scope.formParams.commitUserType = $scope.ctrlUserType;
                $scope.formParams.processCode = $location.$$search.processDefinitionKey;

                $scope.formParams.bcBudgetBatchHId = $scope.formParams.billNo?$scope.formParams.billNo:$scope.formParams.bcBudgetBatchHId;

                $scope.variables.push({name: 'orgId', type: 'int'});
                $scope.variables.push({name: 'channelType', type: 'string'});
                $scope.variables.push({name: 'amount', type: 'long'});
                $scope.variables.push({name: 'commitUserId', type: 'int'});
                $scope.variables.push({name: 'commitUserType', type: 'string'});
                $scope.variables.push({name: 'processCode', type: 'string'});

                for (var i in $scope.fileData) {
                    $scope.formParams.fileIds.push($scope.fileData[i].fileId)
                }
                //debugger
                return true;
            }
            /**
             * 监听保存操作和提交的返回数据，并赋值给表单，避免表单重复提交
             */
            // $scope.$watch('businessFormParams',function() {
            //     if($scope.businessFormParams && $scope.businessFormParams.data.billNo) {
            //         $scope.formParams.bcBudgetBatchHId = $scope.businessFormParams.data.billNo;
            //     }
            // },true)


            //下载模版
            $scope.downloadTmp = function () {
                var dParams = {
                    type: 'BUDGET_BATCH'
                }
                httpServer.post(URLAPI.batchDownload, {
                    params: JSON.stringify(dParams)
                }, function (res) {
                    if (res.status == 'S') {
                        //console.log(res);
                        var _url = location.protocol + '//' + location.host + res.data;
                        window.open(_url);
                    } else {
                        JS.alert(res.msg, 'error', '确定');
                    }
                }, function (error) {
                    JS.alert('请求失败', 'error', '确定');
                })

            }


            $scope.uploadcb = function (res) {
                $scope.dataTableAdd.data = [];
                angular.forEach(res.data, function (data, index) {
                    var aRow = {
                        channelType: data.channelType,
                        departmentId: data.departmentId,
                        departmentName: data.departmentId ? data.departmentName : '',
                        expenseDate: data.expenseDate,
                        expenseAccount: data.expenseAccount,
                        expenseAccountName: data.expenseAccount ? data.expenseAccountName : '',
                        adjustAmount: data.adjustAmount,
                        bcBudgetBatchLDesc: data.bcBudgetBatchLDesc.substring(0, 100)
                    };
                    $scope.dataTableAdd.data.push(aRow);
                });
                //$scope.dataTableAdd.data=$scope.dataTableAdd.data.concat(res.data);
                if (res.msg && res.msg.indexOf('成功') > -1) {
                    JS.alert(res.msg);
                } else {
                    //JS.alert(res.msg, 'error', '确定');
                    $scope.importMsg = '导入的文件信息：' + res.msg;
                }

            }

            $scope.channelTypeLineChange = function (data) {
                if ($scope.dataTableAdd.rowIndex === '') return;
                $scope.dataTableAdd.data[$scope.dataTableAdd.rowIndex].departmentName = '';
                $scope.dataTableAdd.data[$scope.dataTableAdd.rowIndex].departmentId = '';
                $scope.applicant.search(1);
            }

            $scope.changeAdjustAmount = function (item, key, len) {
                limitNumLength(item, key, len)
            }


            /**
             * create hmx 2018年9月18日
             * @type {Array}
             */
            $scope.fileData = []; // 上传文件
            $scope.fileFilter = {
                code: 'UPLOAD_FILE_FILTER',
                key: 'imageType'
            }


        }])
})
