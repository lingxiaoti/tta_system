'use strict';
define(['app', 'pinyin', 'ztree'], function (app, pinyin, ztree) {
    app.controller('proposalCenterCtrl', function ($scope, $filter, $location, $rootScope, $state, $stateParams, SIEJS, httpServer, URLAPI, iframeTabAction, setNewRow) {

        $scope.params = {};
        $scope.logoImg = [];
        $scope.showinfo = false;//初始化禁用变更按钮
        $scope.isOrderMake = false;//制单中才显示
        $scope.showcutinfo = false;//初始化禁用切换上一版本按钮


        $scope.btnNew = function () {
            iframeTabAction('新增proposal', 'proposalDetail/')
        };


        $scope.btnModify = function () {
            var row = $scope.dataTable.selectRow;

            iframeTabAction('proposal详情：'+row.orderNbr, 'proposalDetail/' + row.proposalId)
        };

        $scope.btnDetail = function () {
            var row = $scope.dataTable.selectRow;
            iframeTabAction('proposal详情：'+row.orderNbr, 'proposalDetail/' + row.proposalId + "?onlyShow=1")
        };

        $scope.btnDeptFee = function () {
            var row = $scope.dataTable.selectRow;

            iframeTabAction('proposal部门协定标准详情：'+row.orderNbr, 'deptFeeDetail/' + row.proposalId)
        }




        $scope.btnDel = function (proposal) {
            proposal = proposal || $scope.dataTable.selectRow;

            if (proposal) {
                SIEJS.confirm('删除', '是否确定删除？', '确定', function () {

                    httpServer.post(URLAPI.proposalDel, {
                        params: JSON.stringify({ids: proposal.proposalId})
                    }, function (res) {
                        if ('S' == res.status) {
                            $scope.dataTable.search();
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
        };

        $scope.btnDiscard = function (proposal) {
            proposal = proposal || $scope.dataTable.selectRow;

            if(proposal.status!='A'){
                SIEJS.alert("制单状态才能作废");
            }

            if (proposal) {
                SIEJS.confirm('作废', '是否确定作废？', '确定', function () {

                    httpServer.post(URLAPI.proposalChangeStatus, {
                        params: JSON.stringify({proposalId: proposal.proposalId,status:proposal.status,toStatus:"D"})
                    }, function (res) {
                        if ('S' == res.status) {
                            $scope.dataTable.search();
                            SIEJS.alert('作废成功');
                        } else {
                            SIEJS.alert(res.msg, 'error', '确定');
                        }
                    }, function (error) {
                        console.error(error);
                        SIEJS.alert('作废失败', 'error', '确定');
                    })

                })
            }
        };

        //变更
        $scope.btnChange = function (proposal) {

            var proposal = proposal || $scope.dataTable.selectRow;

            if(proposal.status != 'C'){
                SIEJS.alert("单据状态需要是审核通过才能变更");
                return;
            }
            if (proposal) {
                SIEJS.confirm('变更', '是否确定变更？', '确定', function () {
                    httpServer.post(URLAPI.proposalBuilderChange, {
                            'params': JSON.stringify($scope.dataTable.selectRow)
                        },
                        function (res) {
                            if (res.status == 'S') {
                                //scope.dataTable.search();

                                var xFlag = res.result.xFlag;
                                var xMsg = res.result.xMsg;
                                if (xFlag != 1) {
                                    SIEJS.alert(xMsg, "error", "确定");
                                    return;
                                }
                                $scope.rowData = res.data;
                                setNewRow($scope.dataTable.selectRow,$scope.rowData);// 更新成功，只更新当前行，不必再去重载当前表格
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
            }
        };

        //点击行的回调函数 item选中的行数据
        $scope.clickRowCallback = function (item) {
            var item = item || $scope.dataTable.selectRow;

            //变更
            //审核通过
            if(item.status =='C'){
                $scope.showinfo = true;
            }else {
                $scope.showinfo = false;
            }

            if (item.status == 'A') {
                $scope.isOrderMake = true;
            } else {
                $scope.isOrderMake = false;
            }

            //切换上一版本
            var versionC = item.originProposalId ? "1" : "0";
            var isChange = item['isChange'] ? 'Y':'N';
            //切换上一版本的条件: 版本变更状态是生效的,并且是变更的,制单中,有上一版本的proposalid
            if (item['versionStatus'] == '1' &&  isChange =='Y' && item.status == 'A' && versionC == '1'){
                $scope.showcutinfo = true;
            }else {
                $scope.showcutinfo = false;
            }
        };
        
        //切换上一版本
        $scope.btnCutVersion = function (proposal) {
            var proposal = proposal || $scope.dataTable.selectRow;
            var versionA = proposal.originProposalId ? "1" : "0";
            if(proposal.status != 'A' && proposal['isChange'] !='Y'){
                SIEJS.alert("单据状态需为制单中并且是变更版本才能切换上一版本");
                return;
            }

            if (versionA != "1") {
                SIEJS.alert("需要有上一版本才能切换上一版本");
                return;
            }

            SIEJS.confirm('恢复上一版本', '是否确定恢复上一版本？',"确定",function () {

                httpServer.post(URLAPI.proposalBuilderCutVersion, {
                        'params': JSON.stringify($scope.dataTable.selectRow)
                    },
                    function (res) {
                        if (res.status == 'S') {
                            var proposalId = res.result.proposalId;
                            var xFlag = res.result.xFlag;
                            var xMsg = res.result.xMsg;
                            if (xFlag != 1) {
                                SIEJS.alert(xMsg, "error", "确定");
                                return;
                            }

                            SIEJS.alert("恢复成功", 'success');
                            //id = proposalId;
                            //$scope.search();
                            //scope.dataTable.search();

                            $scope.rowData = res.data;
                            setNewRow($scope.dataTable.selectRow,$scope.rowData);

                        } else {
                            SIEJS.alert(res.msg, "error", "确定");
                        }
                    },
                    function (err) {
                        SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                    }
                );
            });
        };

        //--------------------

    });
});
