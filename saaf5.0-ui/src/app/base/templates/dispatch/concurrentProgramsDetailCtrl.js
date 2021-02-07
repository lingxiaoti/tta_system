define(["app", "ztree"], function (app, ztree) {
    app.controller('concurrentProgramsDetailCtrl', ['SIE.JS', '$scope', 'httpServer', 'URLAPI', '$state', '$stateParams', function (JS, $scope, httpServer, URLAPI, $state, $stateParams) {

        $scope.dataTable = {};
        $scope.searchParams = {};
        $scope.params={};

        $scope.queryJobInstTree = {};
        $scope.paramsInstTree = {};

        $scope.queryJobRespList = {};
        $scope.paramsResp = {};

        $scope.findRemainderJobResp = {};
        $scope.paramsDuty = {};

        $scope.isAddParams = false;

        $scope.init = function () {
            if ($stateParams.hasOwnProperty('jobId') && $stateParams.jobId != '' && $stateParams.jobId != null && $stateParams.jobId != undefined) {
                getProgramContent($stateParams.jobId);
                $scope.searchParams.jobId = $stateParams.jobId;
                $scope.paramsInstTree = {jobId: $stateParams.jobId, platformCode: "SAAF"};
                $scope.paramsDuty = {
                    jobId: $stateParams.jobId
                };
                $scope.params = {
                    jobId: $stateParams.jobId
                };
                $scope.paramsResp = {platformCode: "SAAF", jobId: $stateParams.jobId}
                setTimeout(function () {
                    $scope.queryJobInstTree.getData();
                    $scope.queryJobRespList.getData();
                });

            } else {
                $scope.program = {};
                $scope.isAddParams = false;
            }
        }


        function getProgramContent(id) {

            httpServer.post(URLAPI.jobFind, {
                params: JSON.stringify({
                    jobId: id
                })
            }, function (res) {
                if (res.data.length > 0) {
                    $scope.program = res.data[0];
                    $scope.dataTable.getData();
                    $scope.isAddParams = true;
                }
            }, function (error) {
                console.error(error)
            })


        }

        //查询并发程序
        // $scope.findConcurrentPrograms = function () {
        //     httpServer.getData(URLAPI.jobFind, 'POST', {
        //         'params': JSON.stringify(
        //             {jobId: $scope.jobId}
        //         ), 'pageIndex': 1, 'pageRows': 1
        //     }, function (res) {
        //         if (res.count == 1) {
        //             $scope.system = res.data[0].system;
        //             $scope.module = res.data[0].module;
        //             $scope.jobType = res.data[0].jobType;
        //             $scope.jobName = res.data[0].jobName;
        //             $scope.executableName = res.data[0].executableName;
        //             $scope.description = res.data[0].description;
        //
        //             if ($scope.jobType == 'WEBSERVICE') {
        //                 $scope.method_webservice = res.data[0].method;
        //             } else if ($scope.jobType == 'JAVA') {
        //                 $scope.method_java = res.data[0].method;
        //             }
        //         }
        //         else {
        //             JS.alert(res.msg, "error", "确定");
        //         }
        //     }, function (err) {
        //         JS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
        //     });
        // }
        // //执行查询
        // if ($scope.jobId != undefined && $scope.jobId != -1) {
        //     $scope.findConcurrentPrograms();
        // }

        //提交并发程序表单（新增或编辑）
        $scope.saveConcurrentPrograms = function (formStatus) {
            if (!formStatus) {
                if ($scope.program.jobType == 'PACKAGE') {
                    $scope.program.method = '';
                }


                httpServer.post(URLAPI.saveJob, {
                    params: JSON.stringify($scope.program)
                }, function (res) {
                    if ('S' == res.status) {
                        //  $scope.program = angular.copy(res.data);
                        $scope.isAddParams = true;
                        $scope.searchParams = res.data;
                        $scope.paramsDuty['jobId']=res.data.jobId
                        $scope.params['jobId']=res.data.jobId
                        $scope.paramsResp = {platformCode: "SAAF", jobId: res.data.jobId}
                        $scope.paramsInstTree = {platformCode: "SAAF", jobId: res.data.jobId};
                        JS.alert('保存成功');
                    } else {
                        JS.alert(res.msg, 'error', '确定');
                    }
                }, function (error) {
                    console.error(error);
                    JS.alert('保存失败', 'error', '确定');
                })
            } else {
                JS.alert('请填写完整表达按', 'error', '确定');
            }
        }

        //新增参数按钮 单击事件
        $scope.addParam = function () {
            $scope.modalTitle = '新增参数';
            $('#paramsModal').modal('show');
            $scope.params = {   //add by husaiqiang
                isEnabled:'true',
                jobId: $scope.searchParams.jobId
            }
            //$scope.params.isEnabled = 'true';
        }

        //提交并发参数表单
        $scope.confirmParams = function (formStatus) {
            if (!formStatus) {
                console.info($scope.params);
                httpServer.post(URLAPI.saveJobParam, {
                    params: JSON.stringify($scope.params)
                }, function (res) {
                    if ('S' == res.status) {
                        $('#paramsModal').modal('hide');
                        $scope.dataTable.getData();
                        JS.alert('保存成功');
                    } else {
                        JS.alert(res.msg, 'error', '确定');
                    }
                }, function (error) {
                    JS.alert('保存失败', 'error', '确定');
                    console.error(error);
                })
            } else {
                JS.alert('请填写完整表达按', 'error', '确定');
            }
        }

        //修改并发参数
        $scope.edit = function (obj) {
            $scope.modalTitle = '修改参数';
            $scope.params = obj;
            $('#paramsModal').modal('show');
        }

        //删除参数
        $scope.remove = function (obj) {
            if (obj) {
                JS.confirm('删除并发参数', '是否确定删除该并发参数', '确定', function () {
                    httpServer.post(URLAPI.removeJobParam, {
                        params: JSON.stringify({
                            paramId: obj.paramId
                        })
                    }, function (res) {
                        if ('S' == res.status) {
                            $scope.dataTable.getData();
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
        }

        //监听　ztree数据
        $scope.$watch('zTreeData.getNodesIdKey', function (val) {
            setTree(val);
        }, true);
        //分配组织
        function setTree(val) {
            var ids = val;
            var json = $scope.zTreeData.Json;
            if (ids != undefined && json != null) {
                var ztree = $.fn.zTree.getZTreeObj('existing');
                var setting = ztree.setting;
                var nodes = [];
                for (var i = 0; i < json.length; i++) {
                    if (ids.indexOf(json[i].orgId) >= 0) {
                        json[i].font = {'color': 'blue'};
                        nodes.push(json[i])
                    }
                }

                findParent(nodes, json);
                var treeObj = $.fn.zTree.init($("#existing"), setting, nodes);
                treeObj.expandAll(true);
            }
        }

        //为右边没有父节点的，添加上父节点
        function findParent(rows, json) {
            $.each(rows, function (n, row) {
                autoAddParent(row);
            })
            function autoAddParent(row) {
                if (!hasParent(row)) {
                    addParent(row)
                }
            }

            function hasParent(row) {
                for (var i = 0, flag = false, length = rows.length; i < length; i++) {
                    if (row.parentOrgId == 0) {
                        flag = true;
                        break;
                    }
                    if (rows[i].orgId == row.parentOrgId) {
                        flag = true;
                        break;
                    }
                }
                return flag;
            }

            function addParent(row) {
                for (var i = 0, length = json.length; i < length; i++) {
                    if (json[i].orgId == row.parentOrgId) {
                        //rows.splice(i, 0, json[i]);
                        rows.push(json[i]);
                        autoAddParent(json[i]);
                        break;
                    }
                }
            }
        }

        //弹出组织分配模态框
        $scope.getOrgData = function () {
            httpServer.post(
                URLAPI.queryJobInstTree,
                {
                    'params': JSON.stringify({"jobId": $scope.searchParams.jobId, "platformCode": "SAAF"})
                },
                function (res) {
                    console.log(res)
                    if (res.status.toLowerCase() == 'e') {
                        JS.alert(res.msg, "error");
                    }
                    setSelected(res.data);
                },
                function (err) {
                    JS.alert('数据获取失败！', "error");
                }
            );

            function setSelected(_existing) {
                var _json = $scope.zTreeData.Json;
                for (var _n = 0; _n < _json.length; _n++) {
                    _json[_n].checked = false;
                    for (var _i = 0; _i < _existing.length; _i++) {
                        if (_json[_n].orgId == _existing[_i].orgId) {
                            _json[_n].checked = true;
                        }
                    }
                }
                $scope.zTreeData.updataId = ( $scope.zTreeData.updataId == null ? 0 : $scope.zTreeData.updataId++);//更新的标识
            }

            $('#userModal').modal('toggle')
        }

        //保存组织树
        $scope.saveInstTree = function () {
            var svaeData = {};
            svaeData.jobId = $scope.searchParams.jobId;
                svaeData.jobInstData = [];


            $.each($scope.zTreeData.getNodesIdKey, function (n1, value1) {
                    svaeData.jobInstData.push(
                        {
                            "varPlatformCode": "SAAF",
                            "jobId": $scope.searchParams.jobId,
                            "orgId": value1,
                        })
                }
            )

            httpServer.post(
                URLAPI.saveJobInst,
                {
                    'params': JSON.stringify(svaeData)
                },
                function (res) {
                    if (res.status.toLowerCase() == 's') {
                        JS.alert("保存成功", 'success', '确定')
                    } else {
                        JS.alert(res.msg, "error");
                    }
                    $('#userModal').modal('toggle');
                    $scope.queryJobInstTree.getData();
                },
                function (err) {
                    JS.alert('数据获取失败！', "error");
                }
            );
        }

        //弹出职责新增
        var respSelectData = [];
        var respSelectRowData = [];
        $scope.getDutyData = function () {
            $scope.findRemainderJobResp.getData();
            $('#lovDutyModal').modal('toggle');
            respSelectData = [];
        }
        //职责全选
        $scope.respSelectedAll = function (flag, datas) {
            respSelectData = [];
            respSelectRowData = [];
            if (flag) {
                $.each(datas, function (n, row) {
                    respSelectData.push(row.responsibilityId);
                    respSelectRowData.push(row);
                })
            }
        }
        //职责多选框点击选中
        $scope.respSelectedAction = function (event, id, row) {
            if (!$scope.respIsSelected(id)) {
                respSelectData.push(id);

                respSelectRowData.push(row);

            }
            else {
                var index = $.inArray(id, respSelectData);
                respSelectRowData.splice(index, 1);
                respSelectData.splice(index, 1);
            }
        }
        //判断多选框  是否被选中
        $scope.respIsSelected = function (id) {
            var index = $.inArray(id, respSelectData);
            if (index < 0) return false;
            else return true;
        }

        //保存职责
        $scope.saveResp = function () {
            console.log(respSelectRowData)
            var jobRespSaveData = [];
            $.each(respSelectRowData, function (n, value) {
                jobRespSaveData.push(
                    {
                        "jobId": $scope.searchParams.jobId,
                        "varPlatformCode": value.platformCode,
                        "respId": value.responsibilityId,
                        "userRespName": value.responsibilityName
                    }
                )
            })
            httpServer.post(URLAPI.saveJobResp, {
                'params': JSON.stringify({jobRespData: jobRespSaveData})
            }, function (res) {
                if (res.status == 'S') {
                    JS.alert('保存成功', "success", "确定");
                    respSelectRowData = [];
                    respSelectData = [];
                }
                else if (res.hasOwnProperty('msg')) {
                    JS.alert(res.msg, "error", "确定");
                    respSelectRowData = [];
                    respSelectData = [];
                }
                else {
                    respSelectRowData = [];
                    respSelectData = [];
                    JS.alert('保存有误', "error", "确定");
                }
                $scope.queryJobRespList.getData();
            }, function (err) {
                respSelectRowData = [];
                respSelectData = [];
                swal('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员!!');
            });
        }

        //删除职责
        $scope.deleteRes = function (id) {
            JS.confirm('删除职责', '是否确定删除该职责', '确定', function () {
                httpServer.post(URLAPI.deleteJobResp, {
                    'params': JSON.stringify({
                        jobRespId: id
                    })
                }, function (res) {
                    if (res.status == 'S') {
                        JS.alert(res.msg, "success", "确定");
                    }
                    else {
                        JS.alert(res.msg, "error", "确定");
                    }
                    $scope.queryJobRespList.getData();
                }), function (error) {
                    JS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                    console.error(error);
                }
            });
        }

        $scope.init()
    }]);
});