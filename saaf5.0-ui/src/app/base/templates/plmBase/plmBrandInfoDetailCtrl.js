/**
 * Created by lip on 2019/5/8.
 */
'use strict';
define(['app', 'pinyin', 'ztree', 'angularFileUpload'], function (app, pinyin, ztree, angularFileUpload) {
    app.useModule('angularFileUpload');//按需加载弹出层模块
    app.controller('plmBrandInfoDetailCtrl', function ($scope, $filter, $location, $rootScope, $state, $stateParams, SIEJS, httpServer, URLAPI, iframeTabAction, $compile, FileUploader, iframeMessage) {
        var id = $stateParams.id;

        $scope.headerData = {};

        $scope.userType = JSON.parse(sessionStorage[appName + '_successLoginInfo']).userType;
        $scope.userName = JSON.parse(sessionStorage[appName + '_successLoginInfo']).userName;
        $scope.userId = JSON.parse(sessionStorage[appName + '_successLoginInfo']).userId;

        //查询单据信息
        $scope.search = function () {

            $scope.plmBrandInfoId = id;

            httpServer.post(URLAPI.findPlmBrandInfoInfo, {
                    'params': JSON.stringify({plmBrandInfoId: $scope.plmBrandInfoId})
                },
                function (res) {
                    if (res.status === 'S') {
                        $scope.headerData = res.data[0];
                        $scope.plmBrandInfoId = res.data[0].plmBrandInfoId;


                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                },
                function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                }
            );
        };

        if(id===undefined||id===null||id===''){
            $scope.headerData.billStatus = 'TODO';
        }
        else {
            $scope.search();
        }

        /********************  按钮操作 start ********************/

        $scope.btnSave = function (invalid) {
            if (invalid) {
                SIEJS.alert('请检查必填项', 'error', '确定');
                return;
            }
            $scope.saveAction($scope.headerData);
        };

        $scope.saveAction = function(params, silent){

            for(var i = 0; i < $scope.billStatus.dataList.length; i++){
                if($scope.billStatus.dataList[i].lookupCode===params.billStatus){
                    params.billStatusName = $scope.billStatus.dataList[i].meaning;
                }
            }

            httpServer.post(URLAPI.savePlmBrandInfoInfo, {
                    'params': JSON.stringify(params)
                },
                function (res) {
                    if (res.status === 'S') {
                        $scope.headerData = res.data;
                        if(silent!=='silent')
                        SIEJS.alert('操作成功','success','确定');

                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                },
                function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                },false
            );
        };

        $scope.btnSubmit = function(invalid) {
            if (invalid) {
                SIEJS.alert('请检查必填项', 'error', '确定');
                return;
            }

            if(($scope.headerData.plmMotherCompany===undefined||$scope.headerData.plmMotherCompany==='')&&($scope.headerData.plmGroupBrand===undefined||$scope.headerData.plmGroupBrand==='')){
                SIEJS.alert('Mother Company和Group二者必填其一','error','确定');
                return;
            }

            var params = angular.copy($scope.headerData);
            if ($scope.userType === '45' && $scope.headerData.plmMotherCompany !== undefined && $scope.headerData.plmMotherCompany !== ''&&$scope.headerData.billStatus==='BIC_IN_APPROVAL') {
                params.billStatus = 'BA_IN_APPROVAL';
            }
            if ($scope.userType === '45' && ($scope.headerData.plmMotherCompany === undefined || $scope.headerData.plmMotherCompany === '')&&$scope.headerData.billStatus==='BIC_IN_APPROVAL') {
                params.billStatus = 'TA_IN_APPROVAL';
            }
            if ($scope.userType === '46' && ($scope.headerData.plmMotherCompany !== undefined || $scope.headerData.plmMotherCompany === '')&&$scope.headerData.billStatus==='BA_IN_APPROVAL') {
                params.billStatus = 'TA_IN_APPROVAL';
            }
            if ($scope.userType === '47') {
                params.billStatus = 'Y';
            }
            if($scope.headerData.billStatus==='TODO'){
                params.billStatus = 'BIC_IN_APPROVAL';
            }
            $scope.saveAction(params);
        };

        $scope.btnDiscard = function(){
            var params = angular.copy($scope.headerData);
            params.billStatus = 'N';
            $scope.saveAction(params);
        };

        $scope.reject = function(){
            var params = angular.copy($scope.headerData);
            params.billStatus = 'TODO';
            $scope.saveAction(params);
        };

        /********************  按钮操作 end ********************/


        /*****************弹窗返回 start********************/

        $scope.returnBicInfo = function (key, value, returnList) {
            if(returnList.length!==0){
                $scope.headerData.bic = returnList[0].userId;
                $scope.headerData.bicName = returnList[0].userName;
            }else {
                $scope.headerData.bic = '';
                $scope.headerData.bicName = '';
            }
        };

        $scope.returnBaInfo = function (key, value, returnList) {
            if(returnList.length!==0){
                $scope.headerData.ba = returnList[0].userId;
                $scope.headerData.baName = returnList[0].userName;
            }else {
                $scope.headerData.ba = '';
                $scope.headerData.baName = '';
            }
        };

        $scope.returnTaInfo = function (key, value, returnList) {
            if(returnList.length!==0){
                $scope.headerData.ta = returnList[0].userId;
                $scope.headerData.taName = returnList[0].userName;
            }else {
                $scope.headerData.ta = '';
                $scope.headerData.taName = '';
            }
        };

        setInterval(function () {
            $scope.saveAction($scope.headerData,'silent');
        },60000);

        /*****************弹窗返回  end ********************/


    });
});
