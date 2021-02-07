'use strict';
define(["app"], function (app) {
    app.controller('loginCtrl', function (httpServer, $http, $scope, $state, $location, $rootScope, URLAPI, SIEJS, Base64,arrayFindObj,Cookies,systemJump,trimStr) {

        // 是否是 portal 登录进来的
        $scope.isPortalLogin= !!sessionStorage.successLoginInfo;

        // sessionStorage.successLoginInfo 这个是入口首页（门户）登录成功的信息
        if (sessionStorage.successLoginInfo || sessionStorage[appName + '_successLoginInfo']) {
            if (sessionStorage.successLoginInfo && !sessionStorage[appName + '_successLoginInfo']) {
                sessionStorage[appName + '_successLoginInfo'] = sessionStorage.successLoginInfo;
            }
            var obj = JSON.parse(sessionStorage[appName + '_successLoginInfo']);
            $rootScope.userRespList=arrayFindObj(obj.userRespList,appName,'systemCode',true);
            $state.go('main');


        }else{
            systemJump();
        }


        document.onkeyup = function (e) {

            if (e && e.keyCode == 13) {
                $scope.loginSubmit();
            }
        };

        if (window.localStorage.hasOwnProperty(appName + '_loginSaveUer'))
            $scope.username = window.localStorage[appName + '_loginSaveUer'];
        $scope.check = '';
        $scope.imageSrc = "imageValidate?d=" + new Date().getTime();
        $scope.reloadVerifyCode = function () {
            $scope.imageSrc = "imageValidate?d=" + new Date().getTime();
        };

        $scope.loginFlag = false;
        $scope.loginSubmit = function (invalid) {
            if(invalid){
                SIEJS.alert('请检查必填项','warning');
                return;
            }
            $scope.loginFlag = true;

            httpServer.post(
                URLAPI.login,
                {
                    "params": JSON.stringify({
                        "userName": trimStr($scope.params.username),
                        "pwd": Base64.encode($scope.params.password)

                    })
                },
                function (res) {
                    $rootScope.spinnerFlag = false;

                    if (res.status === "S") {
                        sessionStorage[appName + '_successLoginInfo'] = sessionStorage[appName + '_successLoginInfo'] = JSON.stringify(res.data);

                        Cookies.setCookie(appName  + '_certificate', res.data.certificate);// 设置Cookies
                        if ($scope.remenberMe)   window.localStorage[appName + '_loginSaveUer'] = $scope.username;
                        //window.location.href = '#/main';

                        var obj = JSON.parse(sessionStorage[appName + '_successLoginInfo']);
                        $rootScope.userRespList=arrayFindObj(obj.userRespList,appName,'systemCode',true);

                        $state.go('main')
                    }
                    else if (res.status === "E") {
                        SIEJS.alert(res.msg, "warning", "确定");
                        $scope.loginFlag = false;
                    }
                    else {
                        SIEJS.alert("登陆异常，请联系管理员！", "warning", "确定");
                        $scope.loginFlag = false;
                    }
                    $scope.reloadVerifyCode();
                },
                function (err) {
                    $scope.loginFlag = false;
                    SIEJS.alert("连接超时", "warning", "确定");
                },
                true)

        };


    })

});


