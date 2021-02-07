/**
 * Created by sie_chubaodong on 2019/9/4.
 */
define(["app"], function (app) {
    app.controller('waitQuotationInfoCtrl', ['$scope', '$state','URLAPI','$stateParams', 'iframeTabAction', "SIE.JS", 'httpServer', function ($scope, $state,URLAPI,$stateParams,iframeTabAction, JS, httpServer) {
        $scope.dataTable = {};
        $scope.dataTable.data = [];
        $scope.params = {};
        $scope.row = {'choiceFlag':'0'};
        $scope.params.supplierId = JSON.parse(sessionStorage[appName + '_successLoginInfo']).supplierId;
        $scope.program = {};
        // 编辑--跳转功能
        $scope.toReadOnlyForProjectInfo = function (row) {
            iframeTabAction('供应商报价管理(待报价)OEM界面', 'readOnlyProjectInfo/' + row.projectId);
        };

        /**
         * 数据校验
         */
        $scope.dataChecked = function () {
            debugger;
            var tempDataTable = $scope.dataTable.data;
            var lineData = [];
            var count = 0;
            if (tempDataTable.length < 1) {
                JS.alert("没有数据，请选择条件搜索！！", "warning", "确定");
                return true;
            }
            //只能处理单据行状态为“true”的数据
            for (var i = 0; i < tempDataTable.length; i++) {
                var tempObj = tempDataTable[i];
                if (tempObj.selectFlag == true) {
                    count++;
                    continue;
                }
            }
            if (count == 0) {
                JS.alert("请至少勾选一行数据", "warning", "确定");
                return true;
            }
        };

        // 确认参与
        $scope.confirmParticipation = function (row,index) {
            // 校验必选
            if($scope.dataChecked()){
                return;
            }
            $scope.dataTable.data.selectRowData.supplierId=JSON.parse(sessionStorage[appName + '_successLoginInfo']).supplierId;
            if($scope.dataTable.data.selectRowData != null && $scope.dataTable.data.selectRow != null){
                JS.confirm("确认参与报价？", null, "确定", function () {
                    httpServer.post(URLAPI.confirmParticipation, {
                        'params': JSON.stringify($scope.dataTable.data.selectRowData)
                    }, function (res) {
                        if (res.status == 'S') {
                            console.log(res);
                            var quotationId = res.data;
                            $scope.dataTable.getData();
                            JS.alert('确认参与成功,页面跳转中');
                            // 确认参与成功后跳转到报价中页面
                            iframeTabAction('报价管理报价详情', 'editQuotationInfo/' + 'QUOTATION'+'/'+ quotationId);
                        } else {
                            JS.alert(res.msg, 'error', '确定');
                        }
                    }, function (error) {
                        console.error(error)
                    })
                })
            }else{
                JS.alert('请先选择参与报价的立项单据！', 'error');
            }
        };

        // 拒绝参与
        $scope.rejectParticipation = function () {
            $scope.dataTable.data.selectRowData.rejectionReason = $scope.program.rejectionReason;
            $scope.dataTable.data.selectRowData.supplierId=JSON.parse(sessionStorage[appName + '_successLoginInfo']).supplierId;
            if($scope.dataTable.data.selectRowData != null && $scope.dataTable.data.selectRow != null){
                // JS.confirm("确认拒绝参与报价？", null, "确定", function () {
                httpServer.post(URLAPI.rejectParticipation, {
                    'params': JSON.stringify($scope.dataTable.data.selectRowData)
                }, function (res) {
                    if (res.status == 'S') {
                        $scope.dataTable.getData();
                        JS.alert('拒绝参与成功');
                    } else {
                        JS.alert(res.msg, 'error', '确定');
                    }
                }, function (error) {
                    console.error(error)
                })
                // }
                // )
            }else{
                JS.alert('请先选择拒绝参与报价的立项单据行！', 'error');
            }
        };

        $scope.reject = function () {
            // 校验必选
            if($scope.dataChecked()){
                return;
            }
            $("#rejectionFormModal").modal('show');
        };

        $scope.rejectConfirm = function () {
            //校验驳回原因必填
            if ($scope.program.rejectionReason == '' || $scope.program.rejectionReason == undefined) {
                JS.alert('请填写拒绝参与原因！', 'error', '确定');
                return false;
            }
            $("#rejectionFormModal").modal('hide');
            $scope.rejectParticipation();
        }
    }]);
});



