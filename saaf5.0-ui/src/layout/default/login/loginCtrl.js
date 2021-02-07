'use strict';
define(["app"], function (app) {
    app.controller('loginCtrl', function (httpServer, $http, $scope, $state, $location, $rootScope, URLAPI, SIEJS,
                                          Base64, MD5, arrayFindObj, Cookies, systemJump, clearStore,trimStr) {
        $scope.params={};
        $scope.loginStatus = {
            autoLogin: false,
            error: 6, // 最多密码错误次数
            baseInfo: ''
        };

        $scope.showLoginFrame = true;

        /**
         * 获取url参数
         */
        function getQueryString(url, name) {
            if (url) {
                url = decodeURIComponent(url);
            } else {
                url = decodeURIComponent($location.url());
            }
            url = url.substring(url.lastIndexOf("?"));
            var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
            var r = decodeURIComponent(url).substr(1).match(reg);
            if(r!=null)return  r[2];
            return null;
        }

        //console.log(getQueryString($location.url(),"Language"));
        var empno = getQueryString($location.url(),"empno");
        var key = getQueryString($location.url(),"key");
        var language  = getQueryString($location.url(),"Language");
        var check  = getQueryString($location.url(),"check");

        if (empno == null && key == null) { //TTA系统
            window.isEmployeePortal = 1;
        } else {//employee portal系统
            $scope.isPortalLogin = true;
            $scope.showLoginFrame = true;
            window.isEmployeePortal = 2;
        }

        var empToTtaParams = {
            empno:empno,
            Language:language,
            key:key,
            check:check
        };

        //根据不同的链接进入不同的请求
        if (window.isEmployeePortal === 2) {
            //增加tokenCode 参数到Header
            var successLoginInfo = eval('(' + sessionStorage[appName + '_successLoginInfo'] + ')');
            var tokenCode = null;
            if (( successLoginInfo != null && typeof(successLoginInfo) != "undefined") && typeof(successLoginInfo.TokenCode) != "undefined") {
                tokenCode = successLoginInfo.TokenCode;
            } else {
                tokenCode = "INDEX_TOKEN_CODE";
            }

            $http({
                url: URLAPI.employeeLoginTta,
                method:'post',
                data: $.param(empToTtaParams),
                //withCredentials: true,//带证书
                headers: {
                    "Content-Type": "application/x-www-form-urlencoded;charset=UTF-8;",
                    "Certificate": Cookies.getCookie(appName + '_certificate') || sessionStorage[appName + '_certificate'] || localStorage[appName + '_certificate'] || 'nothing',
                    "TokenCode": tokenCode
                }
                ,  timeout: 1800 * 1000  //超时
            })
                .success(function(res, status, headers, config){
                    //debugger;
                    /*成功信息*/
                    console.log("响应状态:" + status);//状态成功,200
                    if(res.status == 'timeout'){//错误信息
                        console.log("错误信息: " + res.msg);
                        //var errors = encodeURIComponent(res.msg);
                        //$state.go('error',{msg:errors});
                        SIEJS.alert(res.msg, "warning", "确定");
                    }else if (res.status == 'S') {
                        Cookies.delCookie(appName + '_loginError'); // 删除错误次数
                        var obj = res.data;
                        window.isEmployeePortal = obj.loadResource;//系统来源
                        if (obj.isadmin === 'Y') {
                            $rootScope.userRespList = [{
                                responsibilityName: '超级管理员',
                                responsibilityId: -10000,
                                isAdmin: true
                            }];
                        } else {
                            $rootScope.userRespList = arrayFindObj(obj.userRespList, appName, 'systemCode', true); // 查找当前系统的职责
                        }

                        if (!$rootScope.userRespList || $rootScope.userRespList.length === 0) {
                            SIEJS.alert('您没有当前系统访问权限，请联系管理员', 'warning');
                            //var errors = encodeURIComponent('您没有当前系统访问权限，请联系管理员');
                            //$state.go('error',{msg:errors});
                            return;
                        }

                        localStorage[appName + '_successLoginInfo'] = sessionStorage[appName + '_successLoginInfo'] = JSON.stringify(res.data);
                        //Cookies.setCookie(appName + '_certificate', res.data.certificate);// 设置Cookies
                        localStorage[appName + '_certificate'] = res.data.certificate;
                        sessionStorage[appName + '_certificate'] = res.data.certificate;
                        localStorage.lastUserName = res.data.userName.toUpperCase();// 最后登录的用户
                        localStorage.lastAppName = window.appName.toUpperCase();// 最后登录的系统
                        $rootScope.userName= res.data.userName.toUpperCase();// 当前用户
                        $rootScope.currentResp = $rootScope.userRespList[0];

                        for (var n in localStorage) {
                            if (n.indexOf('successLoginInfo') > -1) { // 更新所有子系统的 successLoginInfo
                                localStorage[n] = JSON.stringify(res.data);
                                sessionStorage[n] = JSON.stringify(res.data);
                            }
                        }

                        //跳转页面
                        var uul = $location;
                        if ($location.search().returnURL){
                            var gorouter =   window.appName.toLocaleLowerCase() +'/' + $location.url().split('?')[1].replace('returnURL=','');
                            gorouter = decodeURIComponent(gorouter);
                            $location.url(gorouter);
                        }else{
                            $location.url(window.appName.toLocaleLowerCase() +'/main');
                        }

                    } else { //错误信息

                        //不用这种方式跳转
                        //var w = window.location;
                        //w.href = w.origin + w.pathname + '#/' + appName.toLowerCase() + '/error?action=error';
                        //var errors = encodeURIComponent(res.msg);
                        //$state.go('error',{msg:errors});
                        SIEJS.alert(res.msg, "warning", "确定");
                    }
                })
                .error(function(response, status, headers, config){
                    /*失败信息*/
                    console.log("调用登录.....");
                    SIEJS.alert("系统跳转服务出错,请重新登录再打开,如重试多次失败,请联系管理员!", "warning", "确定");
                });

        }


        $scope.styleBack = {
            'background-image': 'url("./img/' + (  window.domain ? window.domain.imagePath : 'sie') + '/back.jpg")',
            'background-size': ' cover'
        };

        // $scope.loginBack={
        //     'background-image': 'url("./img/' + (  window.domain ? window.domain.imagePath : 'sie') + '/loginBack.jpg")',
        //     'background-size': ' cover'
        // }

        $scope.logoImg = './img/' + ( window.domain ? window.domain.imagePath : 'sie') + '/logo.png';


        if ($location.$$search.action !== 'logout') {
            //debugger;
            // localStorage.successLoginInfo 这个是入口首页（门户）登录成功的信息
            if (localStorage.successLoginInfo || sessionStorage[appName + '_successLoginInfo']) {
                //$state.go('main');
                var w = window.location;
                w.href = w.origin + w.pathname + '#/' + appName.toLowerCase() + '/main';
            } else {
                systemJump();
            }
        }

        /* document.onkeyup = function (e) {
             if (e && e.keyCode == 13) {
                 $scope.loginSubmit();
             }
         };*/


        $scope.check = '';
        $scope.imageSrc = "imageValidate?d=" + new Date().getTime();
        $scope.reloadVerifyCode = function () {
            $scope.imageSrc = "imageValidate?d=" + new Date().getTime();
        };

        $scope.loginFlag = false;
        $scope.errnumber = parseInt(Cookies.getCookie(appName + '_loginError')) || $scope.loginStatus.error;


        $scope.changePassWord = function () {
            SIEJS.confirm('忘记密码','是否确定重置密码','确定',function() {
                var userName =$scope.params.userName;
                if(userName ==undefined||userName ==null|userName==""){
                    SIEJS.alert('操作失败，请输入用户名', "error", "确定");
                }
                httpServer.post(URLAPI.userChangePassword,
                    {params: JSON.stringify({
                        userName: userName
                    })},
                    function (res) {
                        if (res.status == "S") {
                            SIEJS.alert("密码已重置，请到邮箱查收", "success", "确定");
                        } else {
                            SIEJS.alert(res.msg, "error", "确定");
                        }
                    },
                    function (err) {
                        SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                        $("#saveButton").attr("disabled", true);
                    }
                );})
        }

        $scope.changePassWord = function () {
            SIEJS.confirm('忘记密码','是否确定重置密码','确定',function() {
                var userName =$scope.params.userName;
                if(userName ==undefined||userName ==null|userName==""){
                    SIEJS.alert('操作失败，请输入用户名', "error", "确定");
                }
                httpServer.post(URLAPI.userChangePassword,
                    {params: JSON.stringify({
                            userName: userName
                        })},
                    function (res) {
                        if (res.status == "S") {
                            SIEJS.alert("密码已重置，请到邮箱查收", "success", "确定");
                        } else {
                            SIEJS.alert(res.msg, "error", "确定");
                        }
                    },
                    function (err) {
                        SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                        $("#saveButton").attr("disabled", true);
                    }
                );})
        }


        $scope.loginSubmit = function (invalid) {

            if (invalid) {
                SIEJS.alert('请检查必填项', 'warning');
                return;
            }
            $scope.loginFlag = true;
            if ($scope.errnumber <= -1) {
                SIEJS.alert('登录密码错误次数太多，请10分钟后再重试!', 'error');
                return;
            }

            var p = {};
            var host=window.location.host;
            host=host.indexOf(':')!=-1?host.substring(0,host.indexOf(':')):host;
            host='http://'+host;
            // if (localStorage[appName + '_autoLogin']) {
            if (false) {
                p = JSON.parse(Base64.decode(localStorage[appName + '_autoLogin']))

            } else {

                var key = CryptoJS.enc.Utf8.parse("abcdefgabcdefg12");
                var srcs = CryptoJS.enc.Utf8.parse($scope.params.password);
                var encrypted = CryptoJS.AES.encrypt(srcs, key, {mode:CryptoJS.mode.ECB,padding: CryptoJS.pad.Pkcs7});
                encrypted = encrypted.toString();
                p = {
                    "params": JSON.stringify({
                        "userName": trimStr($scope.params.userName),
                        //"pwd": MD5($scope.params.password), 2019.8.12注释,不用这种方式
                        //"pwd": $scope.params.password,
                        "pwd": encrypted,
                        "loginType": "TTA",
                        "domain":host
                    })
                };
                // 自动登录
                if ($scope.loginStatus.autoLogin) {
                    localStorage[appName + '_autoLogin'] = Base64.encode(p);
                }
            }

            httpServer.post(
                URLAPI.login,
                p,
                function (res) {
                    $rootScope.spinnerFlag = false;
                    if (res.status === "S") {
                        Cookies.delCookie(appName + '_loginError'); // 删除错误次数
                        var obj = res.data;
                        window.isEmployeePortal = obj.loadResource;//系统来源
                        if (obj.isadmin === 'Y') {
                            $rootScope.userRespList = [{
                                responsibilityName: '超级管理员',
                                responsibilityId: -10000,
                                isAdmin: true
                            }];
                        } else {
                            $rootScope.userRespList = arrayFindObj(obj.userRespList, appName, 'systemCode', true); // 查找当前系统的职责
                        }

                        if (!$rootScope.userRespList || $rootScope.userRespList.length === 0) {
                            SIEJS.alert('您没有当前系统访问权限，请联系管理员', 'warning');
                            return;
                        }

                        localStorage[appName + '_successLoginInfo'] = sessionStorage[appName + '_successLoginInfo'] = JSON.stringify(res.data);
                        //Cookies.setCookie(appName + '_certificate', res.data.certificate);// 设置Cookies
                        localStorage[appName + '_certificate'] = res.data.certificate;
                        sessionStorage[appName + '_certificate'] = res.data.certificate;
                        localStorage.lastUserName = $scope.params.userName.toUpperCase();// 最后登录的用户
                        localStorage.lastAppName = window.appName.toUpperCase();// 最后登录的系统
                        $rootScope.userName= $scope.params.userName.toUpperCase();// 当前用户
                        $rootScope.currentResp = $rootScope.userRespList[0];


                        for (var n in localStorage) {
                            if (n.indexOf('successLoginInfo') > -1) { // 更新所有子系统的 successLoginInfo
                                localStorage[n] = JSON.stringify(res.data);
                                sessionStorage[n] = JSON.stringify(res.data);
                            }
                        }

                        var uul = $location;


                        if ($location.search().returnURL){
                            var gorouter =   window.appName.toLocaleLowerCase() +'/' + $location.url().split('?')[1].replace('returnURL=','');
                            gorouter = decodeURIComponent(gorouter);
                            $location.url(gorouter);
                        }else{
                            $location.url(window.appName.toLocaleLowerCase() +'/main');
                        }
                    }
                    else {
                        $scope.errnumber--;
                        if ($scope.errnumber < 3) {
                            SIEJS.alert(res.msg, "warning", "确定", '您还可以重试登录' + ($scope.errnumber + 1) + ' 次');
                        } else {
                            SIEJS.alert(res.msg, "warning", "确定");
                        }

                        $scope.loginFlag = false;
                        localStorage.removeItem([appName + '_autoLogin']);
                        Cookies.setCookie(appName + '_loginError', $scope.errnumber, 10)
                    }

                    $scope.reloadVerifyCode();
                },
                function (err) {
                    $scope.loginFlag = false;
                    SIEJS.alert("连接超时", "warning", "确定");
                },
                true)

        };


        if (localStorage[appName + '_autoLogin']) {
            $scope.loginSubmit(false);
        } else {
            //clearStore();

        }


    })

});


