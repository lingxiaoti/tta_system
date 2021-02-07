/**
 * Created by hemingxing on 2017/08/06.
 */
define(['app'], function(app) {
    app.controller('buildPageCtrl', ['$scope','httpServer','URLAPI','SIE.JS','$timeout',function($scope,httpServer,URLAPI,JS,$timeout) {
        //--------------------------------------
        $scope.businessTable = {};
        $scope.businessParams = {};

        $scope.templateTable = {};
        $scope.templateParams = {};

        $scope.templateListStatus = false;
        $scope.pageContextStatus = false;


        $scope.selectBusiness = function(item) {
            $scope.templateParams.var_like_ruleBusinessLineCode = item.ruleBusinessLineCode;
            $scope.templateTable.getData();
            $scope.templateListStatus = true;
        }

        $scope.changeModel = function(item) {
            httpServer.getData(URLAPI.queryPageTempladData,
                'POST',
                {
                    params:JSON.stringify({
                        modelCode: item.modelCode
                    })
                },function(res) {
                    if(res.status == 'S') {
                        $scope.pageDataArr = res.data;
                        $scope.pageContextStatus = true;
                    }else {
                        JS.alert(res.msg,'error','确定');
                        console.error(res);
                    }
                },function(error) {
                    console.error(error);
                })
        }

        //回车搜索业务线
        $scope.searchKeyupBusiness = function(event) {
            if(event.keyCode == 13) {
                $scope.businessTable.getData();
                $scope.templateListStatus = false;
                $scope.pageContextStatus = false;
            }

        }

        //回车搜索模板
        $scope.searchKeyupModel = function(event) {
            if(event.keyCode == 13) {
                $scope.templateTable.getData();
                $scope.pageContextStatus = false;
            }

        }

        $scope.showModal=function (obj) {
            $scope.modalTit = obj.groupDetDimNameCn;
            if(obj.groupDetDimActionViewType=='dialogCheckTable') {
                if(obj.webserviceUrl) {
                    $scope.getPageDom(obj.webserviceUrl,1,-1,function (res) {
                        $scope.modalTable = res.data;
                    });
                }
                $('#changeModal').modal('show');
            }
        };

        $scope.submitPage=function (formStatus) {
            $scope.params={
                expression:{},
                dimExpressions:[]
            };
            $scope.params.expression.ruleBusinessLineCode=$scope.pageData.modelCode;
        };
    }])
})