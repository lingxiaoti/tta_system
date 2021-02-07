/**
 * Created by lip on 2019/5/8.
 */
'use strict';
define(['app', 'pinyin', 'ztree', 'angularFileUpload'], function (app, pinyin, ztree, angularFileUpload) {
    app.useModule('angularFileUpload');//按需加载弹出层模块
    app.controller('feedeptDetailCtrl', function ($scope, $filter, $location, $rootScope, $state, $stateParams, SIEJS, httpServer, URLAPI, iframeTabAction, setNewRow) {
        var id = $stateParams.id;


        $scope.params = {};
        $scope.treeParams  = {feedeptId:"-1"};
        $scope.rootParentId = 0;

        $scope.treeCurrentItem ={}; // 当前树选中节点
        $scope.lineInfo = null; // 当前行信息
        $scope.feeDeptDInfo = null;


        $scope.feedeptDTableParams = {}; //

        $scope.zTreeData = {};

        //查询单据信息
        $scope.search = function () {

            $scope.feedeptId = id;

            httpServer.post(URLAPI.feedeptHFindById, {
                    'params': JSON.stringify({feedeptId: $scope.feedeptId})
                },
                function (res) {
                    if (res.status == 'S') {
                        $scope.btnC(res.data.status)
                        $scope.params = res.data;
                        $scope.treeParams.feedeptId =$scope.feedeptId;
                        $scope.feedeptLTree.reload();


                        $scope.Ztree();
                        $scope.btnNewL();
                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                },
                function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                }
            );
        }


        $scope.Ztree = function () {

          //  $scope.feedeptId = id;

            httpServer.post(URLAPI.feedeptLFind, {
                    'params': JSON.stringify({feedeptId: $scope.feedeptId})
                    ,pageRows: 10000,
                    pageIndex: 1
                },
                function (res) {
                    if (res.status == 'S') {
                        $scope.zTreeData.treeData = res.data;

                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                },
                function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                }
            );
        }

        $scope.selectL = function () {

         //   $scope.feedeptId = id;

            httpServer.post(URLAPI.feedeptLFindById, {
                    'params': JSON.stringify({feedeptLineId: $scope.lineInfo.feedeptLineId})
                },
                function (res) {
                    if (res.status == 'S') {
                        $scope.lineInfo = res.data;
                        if($scope.lineInfo.parentLineId=='0'){

                            $scope.lineInfo.parentLineCode = '－－－顶层条款编号－－－';
                        }

                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                },
                function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                }
            );
        }



        //id不为空
        if ($stateParams.id) {

            //查询头信息
            $scope.search();
        } else {
            $scope.params.status ='A';
            $scope.params.isAlert ='N';
            $scope.params.versionCode ='001';
        }


        $scope.btnSave = function (invalid) {

            $("#btnId").attr("disabled", "true");

            if (invalid) {
                SIEJS.alert('请检查必填项', 'error', '确定');
                return;
            }

            httpServer.post(URLAPI.feedeptHSave, {
                    'params': JSON.stringify($scope.params)
                },
                function (res) {
                    if (res.status == 'S') {
                        $("#btnId").removeAttr("disabled");
                        id = res.data.feedeptId;
                        $scope.search();
                        SIEJS.alert(res.msg, 'success');
                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                },
                function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                    $("#btnId").removeAttr("disabled");
                }
            );


        }


        $scope.btnSubmit = function (invalid) {

            if (invalid) {
                SIEJS.alert('请检查必填项', 'error', '确定');
                return;
            }

            httpServer.post(URLAPI.feedeptHApprove, {
                    'params': JSON.stringify($scope.params)
                },
                function (res) {
                    if (res.status == 'S') {
                        id = res.data.feedeptId;
                        $scope.search();
                        SIEJS.alert(res.msg, 'success');
                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                },
                function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                }
            );


        }






        // 树点击节点回调
        $scope.treeClick = function (item) {
            $scope.treeCurrentItem = item; // 当前树选中节点
            $scope.lineInfo =  item;
            if($scope.lineInfo.parentLineId=='0'){

                $scope.lineInfo.parentLineCode = '－－－顶层条款编号－－－';
            }

            orgTableSearch();
        }

        function orgTableSearch() {
                    $scope.feedeptDTableParams = {
                        feedeptLineId: $scope.treeCurrentItem.feedeptLineId
                    };
                 //   $scope.oldOrgTableParams = angular.copy($scope.orgTableParams); /// 记录参数
                 //   $timeout(function () {
                        $scope.feedeptDTable.search(1);//
                //    });

        }


        // 拖曳更新层级关系
        //$scope.updateLevel = function (treeId, treeNodes, targetNode) {
        //    var params = treeNodes[0];
        //  //  params.oldParentOrgId = treeNodes[0].parentLineId;
        //    params.parentOrgId = targetNode.feedeptLineId;
        //
        //    organizationSave({params: JSON.stringify(params)}, function (res) {
        //        if (res.status === 'S') {
        //         //   $("#deptModal").modal('hide');
        //            $scope.feedeptLTree.reload();
        //        }
        //    })
        //}


        $scope.lineSave = function (invalid) {


            if (invalid) {
                SIEJS.alert('请检查必填项', 'error', '确定');
                return;
            }

            httpServer.post(URLAPI.feedeptLSave, {
                    'params': JSON.stringify($scope.params)
                },
                function (res) {
                    if (res.status == 'S') {
                        id = res.data.feedeptId;
                        $scope.search();
                        SIEJS.alert(res.msg, 'success');
                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                },
                function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                }
            );

        }


        $scope.btnNewL = function () {
            if($scope.feedeptId==""||$scope.feedeptId==null) {
                SIEJS.alert('头表信息不能为空！', 'warning', '确定');
                return;
            }

            $scope.lineInfo = {
            };
            $scope.lineInfo = {
                "parentLineId":0,
                "parentLineCode":"－－－顶层条款编号－－－",
                "feedeptId":$scope.feedeptId,
                "ifEffect": "Y"
            };
        }


        $scope.btnSaveL = function (invalid) {


            if (invalid) {
                SIEJS.alert('请检查必填项', 'error', '确定');
                return;
            }

            if ($scope.lineInfo.feedeptLineId === $scope.lineInfo.parentLineId) {
                SIEJS.alert('上级节点不能设置为自身节点', 'warning', '确定');
                return;
            }

            httpServer.post(URLAPI.feedeptLSave, {
                    'params': JSON.stringify($scope.lineInfo)
                },
                function (res) {
                    if (res.status == 'S') {
                        $scope.lineInfo.feedeptLineId =  res.data.feedeptLineId;
                        $scope.selectL();
                        $scope.feedeptLTree.reload();
                        $scope.Ztree();
                        SIEJS.alert(res.msg, 'success');
                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                },
                function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                }
            );

        }

        $scope.btnDelL = function () {


            if ($scope.lineInfo.feedeptLineId ) {
                SIEJS.confirm('删除', '是否确定删除？', '确定', function () {
                    var lineId = $scope.lineInfo.feedeptLineId;
                    httpServer.post(URLAPI.feedeptLDel, {
                        params: JSON.stringify({ids:lineId })
                    }, function (res) {
                        if ('S' == res.status) {
                            $scope.search();
                            $scope.lineInfo = {
                            };
                            SIEJS.alert('删除成功');
                        } else {
                            SIEJS.alert(res.msg, 'error', '确定');
                        }
                    }, function (error) {
                        console.error(error);
                        SIEJS.alert('删除失败', 'error', '确定');
                    })

                })
            }



        }



        //选择物料
        $scope.getItemCode = function () {
            //  $scope.areaComponent = e;
            $('#itemLov').modal('toggle')
        };

        //点击确认按钮
        $scope.selectItemLovReturn = function (key, value, currentList) {
            $scope.lineInfo.itemNbr = currentList[0].itemNbr;
            $scope.lineInfo.itemDescEn = currentList[0].itemDescEn;
            $scope.lineInfo.itemDescCn = currentList[0].itemDescCn;

        }



        //选择收费部门
        $scope.getFellDeptDCode = function () {
            //  $scope.areaComponent = e;
            $('#prandFeeDeptD').modal('toggle')
        };

        //点击确认按钮
        $scope.selectFellDeptDCodeReturn = function (key, value, currentList) {
            $scope.params.deptCode = currentList[0].departmentCode;
            $scope.params.deptDesc  = currentList[0].departmentName;
            $scope.params.deptId = currentList[0].departmentId;
        }

        //新增信息
        $scope.addFeedeptD = function () {
            if($scope.lineInfo.feedeptLineId==null){
                SIEJS.alert('请选择一行行明细！', 'warning', '确定');
                return;
            }

            $scope.feeDeptDInfo = {feedeptLineId: $scope.lineInfo.feedeptLineId,feedeptId: $scope.lineInfo.feedeptId,unit: $scope.lineInfo.unit};
            $('#feeDeptDLov').modal('toggle');
        }




        //修改单位信息
        $scope.editFeedeptD = function () {

            //$scope.feeDeptDInfo = $scope.feedeptDTable.selectRowList[0];
            $scope.feeDeptDInfo = angular.copy($scope.feedeptDTable.selectRow);//当前行
            $('#feeDeptDLov').modal('toggle');
        }

        //保存单位信息
        $scope.saveFeeDeptD = function (invalid) {

            if (invalid) {
                SIEJS.alert('请检查必填项', 'error', '确定');
                return;
            }

             var arr = $scope.feedeptDTable.list;
             var unit  = $scope.feeDeptDInfo.unit;
            var deptCode  = $scope.feeDeptDInfo.deptCode;
            if(arr!=null) {
                for (var i = 0; i < arr.length; i++) {
                    var unittemp = arr[i].unit;
/*                    if (unittemp != unit) {
                        SIEJS.alert('行明细单位不一致，请修改！', 'warning', '确定');
                        return;
                    }*/
                    if(deptCode == arr[i].deptCode &&  unit == unittemp ) {
                        if (!$scope.feeDeptDInfo.feedeptDetailId || $scope.feeDeptDInfo.feedeptDetailId !=arr[i].feedeptDetailId) {
                            SIEJS.alert('部门必须匹配不同单位,不可重复，请修改！', 'warning', '确定');
                            return;
                         }
                    }
                }
            }



            httpServer.post(URLAPI.feedeptDSave,
                {params: JSON.stringify($scope.feeDeptDInfo)},
                function (res) {
                    if (res.status == "S") {
                        $scope.feedeptDTable.search(); //查询司机信息
                        SIEJS.alert("处理成功", "success", "确定");
                        $('#feeDeptDLov').modal('hide');
                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                },
                function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                    $("#saveButton").attr("disabled", true);
                }
            );
        }
        //删除单位信息
        $scope.delFeedeptD = function () {
            var selectRowList = $scope.feedeptDTable.selectRowList;
            if (!selectRowList.length) {
                SIEJS.alert("请选择要删除的数据!", "error", "确定");
                return;
            }
            var ids = "";
            for (var i = 0; i < selectRowList.length; i++) {
                ids = ids + selectRowList[i].feedeptDetailId + ",";
            }


            SIEJS.confirm('提示', '确定要删除所选的信息吗？', '确定', function () {
                httpServer.post(URLAPI.feedeptDDel,
                    {params: JSON.stringify({ids: ids})},
                    function (res) {
                        if (res.status == "S") {
                            $scope.feedeptDTable.selectRowList = [];
                            $scope.feedeptDTable.search();
                            SIEJS.alert("处理成功", "success", "确定");
                        } else {
                            SIEJS.alert(res.msg, "error", "确定");
                        }
                    },
                    function (err) {
                        SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                        $("#btnArrival").attr("disabled", true);
                    }
                );
            });
        }


        //变更单据
        $scope.btnChange = function () {


            SIEJS.confirm('变更', '是否确定变更？', '确定', function () {
                httpServer.post(URLAPI.feedeptHChange, {
                        'params': JSON.stringify({feedeptId: $scope.feedeptId})
                    },
                    function (res) {
                        if (res.status == 'S') {
                            var feedeptId = res.data.feedeptId;
                            var xFlag = res.data.xFlag;
                            var xMsg = res.data.xMsg;
                            if (xFlag != 1) {
                                SIEJS.alert(xMsg, "error", "确定");
                                return;
                            }

                            iframeTabAction('部门协定收费标准变更', 'feedeptDetail/' + feedeptId);

                        } else {
                            SIEJS.alert(res.msg, "error", "确定");
                        }
                    },
                    function (err) {
                        SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                    }
                );
            });

        }

        //复制
        $scope.btnCopy = function () {


            SIEJS.confirm('复制', '是否确定复制？', '确定', function () {
            httpServer.post(URLAPI.feedeptHCopy, {
                    'params': JSON.stringify({feedeptId: $scope.feedeptId})
                },
                function (res) {
                    if (res.status == 'S') {
                        var feedeptId = res.data.feedeptId;
                        var xFlag = res.data.xFlag;
                        var xMsg = res.data.xMsg;
                        if(xFlag!=1){
                            SIEJS.alert(xMsg, "error", "确定");
                            return;
                        }

                        iframeTabAction('部门协定收费标准复制', 'feedeptDetail/' + feedeptId);

                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                },
                function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                }
            );
            });
        }

        //选择部门
        $scope.getDeptCode = function () {
            //  $scope.areaComponent = e;
            if(! $scope.params.deptId){
                SIEJS.alert('请先选择设置的部门', "error", "确定");
                return ;
            }
            $('#selectDeptId').modal('toggle')
        };

        //点击确认按钮
        $scope.selectDeptReturn = function (key, value, currentList) {
            $scope.feeDeptDInfo.deptCode = currentList[0].departmentCode;
            $scope.feeDeptDInfo.deptDesc = currentList[0].departmentName;
        }

        $scope.btnC = function (value) {
            if ("A" != value) {
                $("#feedeptForm input").attr("disabled", "true");
                $("#feedeptForm select").attr("disabled", "true");
                $("#feedeptForm textarea").attr("disabled", "true");
                $("#feedeptForm button").attr("disabled", "true");

                $("#lineForm input").attr("disabled", "true");
                $("#lineForm select").attr("disabled", "true");
                $("#lineForm textarea").attr("disabled", "true");
                $("#lineForm button").attr("disabled", "true");

                $("#lineFormId button").attr("disabled", "true");

                $("#btnId button").attr("disabled", "true");
                if ('B' == value  || 'E' ==value) {
                    $("#btnOtherId button").attr("disabled", "true");
                }else{
                    $("#btnOtherId button").removeAttr("disabled");
                }
            } else if('A' ==value){
                $("#btnOtherId button").attr("disabled", "true");
            }
        }

    });








});
