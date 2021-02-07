define(["app", "echarts"], function (app, echarts) {
    app.controller('monitorPriceInfoCtrl', function ($scope, $rootScope, $state, $stateParams, httpServer, URLAPI, SIEJS) {

        $scope.params = {};
        $scope.userId = JSON.parse(sessionStorage[appName + '_successLoginInfo']).userId;
        $scope.monitorBidPriceData = {};
        $scope.bidList = {};
        $scope.monitorBidPriceData.data = {};
        $scope.bidHeadersTable = {};
        $scope.monitorBidPriceData.data.series = [];
        $scope.monitorBidPriceData.data.legend = [];
        var informationId = $stateParams.informationId
        var witnessFlag = $stateParams.witnessFlag

        //获取竞标头
        $scope.getAuctionHeaderInfo = function () {
            $scope.params.informationId = informationId;
            $scope.params.searchType = 'monitor';
            httpServer.post(URLAPI.findInformationIdDatail, {
                params: JSON.stringify($scope.params)
            }, function (res) {
                if (res.status == "S") {
                    $scope.params = res.data;
                    //监控报价的按键控制. 1供应商必须是截止状态,2 在立项没有确认供应商的报价
                    //
                    // if('Y'==witnessFlag && 'Y'==res.msg){
                    //     $scope.monitoringFlag=true;
                    // }
                    // // 在报价截止时间之后,供应商确认之前可以编辑.
                    // if($scope.params.supplierConfirmFlag=='Y'){
                    //     $scope.monitoringFlag=true;
                    // }
                    $scope.queryMonitorBidPrice();
                    //修改按键显示隐藏
                    //查询各个子信息
                }
            }, function (error) {
                console.error(error);
            });

        };
        $scope.getAuctionHeaderInfo();
        //

        $scope.monitoring = function (row) {
            SIEJS.confirm('确定', '确认允许修改报价？', '确定', function () {
                httpServer.post(URLAPI.updateSupplierQuotation, {
                    params: JSON.stringify(row)
                }, function (res) {
                    if (res.status == "S") {
                        row.docStatus = 'MODIFYING';
                        SIEJS.alert("操作成功", "success", "确定");
                    } else {
                        SIEJS.alert(res.msg, 'error', '确定');
                        console.error(res);
                    }
                }, function (error) {
                    console.error(error);
                });
            })
        }
        $scope.showBidList = function (index) {
            $scope.monitorBidPriceData[index].isSelected = !$scope.monitorBidPriceData[index].isSelected;
        }

        //查询监控报价
        $scope.queryMonitorBidPrice = function () {

            httpServer.post(URLAPI.findBidSupplierList, {
                params: JSON.stringify($scope.params)
            }, function (res) {
                if (res.status == "S") {
                    $scope.monitorBidPriceData = res.data;
                    console.log($scope.monitorBidPriceData)
                    $scope.monitorBidPriceData.isSelected = true;
                    //修改按键显示隐藏
                    //查询各个子信息
                }
            }, function (error) {
                console.error(error);
            });


        };

    })
})
