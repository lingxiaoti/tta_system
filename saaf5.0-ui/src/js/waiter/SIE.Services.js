/**
 * Created by houxingzhang on 2016-09-06.
 */

define(['angular', 'app', 'sweetalert', 'webconfig', 'md5', 'GooFlow'], function (angular, app, sweetalert, webconfig, md5) {
    var module = angular.module('SIE.Services', []);
    // ************************************* 服 务
    module

    //跳转至登陆的服务，跳转至登陆并且刷新登陆界面
        .service('gotoLogin', function (clearStore,$state) {
            return function (isLogout) {

                clearStore(isLogout);
                var w = window.location;
                var href = w.origin + w.pathname + '#/' + appName.toLowerCase() + '/login?action=logout';

                if (window.isEmployeePortal === 1){
                    if (window.parent != window) { // 子级页面 iframe
                        window.parent.location.href = href;
                    } else {
                        w.href = href;
                    }
                } else {//给employee portal系统提示错误信息
                    //var errors = encodeURIComponent('退出当前系统成功');
                    //$state.go('error',{msg:errors});
                    //ep正式环境
                    //window.location.href = "http://wtccn-gz-ept/Portal/Login.aspx";
                    //window.location.href = "http://wtccn-watsonsportal:2020/wui/index.html#/?logintype=1&_key=7qlz7s";
                    //ep测试环境
                    //window.location.href = "http://wtccn-vm-sdst/portal/login.aspx";
                    window.location.href = webconfig.jumpUrl;
                }

            };

        })
        // 清除本地 store
        /* isLogout 是否是正常退出？ 正常退出将清除所有当前系统的store

         * */
        .service('clearStore', function (Cookies, systemJump, $rootScope) {
            return function (isLogout, appName) {

                isLogout = isLogout || false;
                appName = appName || window.appName;


                var lang = localStorage[appName + '_language'] || 'CN';
                var autoLogin = localStorage[appName + '_autoLogin'];
                $rootScope.saafHeadTab = [];
                $rootScope.MenuTree = [];
                $rootScope.MenuTreeRoot = [];
                Cookies.delCookie(appName + '_certificate');
                /*  清除所有
                 localStorage.clear();
                 sessionStorage.clear();
                 */
                /*　只退出当前系统 */
                for (var n in localStorage) {
                    if (n.indexOf(appName + '_') > -1) {
                        localStorage.removeItem(n);
                    }
                }
                for (var s in sessionStorage) {
                    if (s.indexOf(appName + '_') > -1) {
                        sessionStorage.removeItem(s);
                    }
                }
                localStorage[appName + '_language'] = lang;// 保存语言转换

                if (autoLogin && !isLogout) localStorage[appName + '_autoLogin'] = autoLogin;

            }
        })
        // 系统之间跳转
        .service('systemJump', function ($rootScope, $location,$state) {
            return function () {
                if (localStorage.successLoginInfo) {
                    window.location.href = '/' + webconfig.SSOName;// 返回protal 页
                } else {

                    if (window.isEmployeePortal === 1){//TTA系统
                        $location.path(appName.toLocaleLowerCase() + '/login');
                    } else {//给employee portal系统提示错误信息
                        //  $location.path(appName.toLocaleLowerCase() + '/E404');
                        //$state.go('error',{id:2});
                    }

                }
            }
        })

        .service('httpServer', function (gotoLogin, $http, $rootScope, $window, SIEJS, $location, $state, $timeout, Cookies, ObjectToValueKey) {
            var loading = $("#saafLoading");
            if (loading.length === 0) {
                var loading = $('<div id="saafLoading"><div  style="position: absolute; top:50%;left:50%; text-align: center;margin: 0 auto; width:100px;"><img src="img/loading1.gif"></div></div>');
                loading.css({
                    position: "fixed",
                    top: 0,
                    width: "100%",
                    "z-index": 9000,
                    "height": "100%",
                    'display': 'none'
                });
                loading.prependTo(angular.element('body'));
            } else {
                loading.removeAttr('data-loading');
            }
            var service = {};
            //增加tokenCode 参数到Header
            var successLoginInfo = eval('(' + sessionStorage[appName + '_successLoginInfo'] + ')');
            var tokenCode = null;
            if (( successLoginInfo != null && typeof(successLoginInfo) != "undefined") && typeof(successLoginInfo.TokenCode) != "undefined") {
                tokenCode = successLoginInfo.TokenCode;
            } else {
                tokenCode = "INDEX_TOKEN_CODE";
            }

            var loadingStatus = [];
            // 设置loading显示状态
            function setLoadingHide(id) {
                var b = true;
                for (var i in loadingStatus) {
                    var item = loadingStatus[i];
                    if (item.id === id) {
                        item.state = true;
                    }
                    if (item.state === false) b = false;
                }
                if (b) {
                    loading.hide();
                }
                //log(loadingStatus);
            }

            function baseHttp(type, url, params, suc, err, loadingFlag, method) {
              // console.log(url);
              if (!url) {
                  console.log("缺少URL参数" + url);
                    SIEJS.alert($rootScope.$l('缺少URL参数'), 'warning');
                    return;
                }
                if (!params) {
                    SIEJS.alert($rootScope.$l('缺少Params参数'), 'warning');
                    return;
                }

                var wating =   120; //超时等待时间(秒)  0为无限制
                var __errorRepeated =  0;
                var errorNum = 0;
                if (loadingFlag == null || loadingFlag == true) {
                    loading.show();
                    var timestampId = 'loading' + new Date().getTime();
                    loadingStatus.push({
                        id: timestampId,
                        state: false
                    });
                }

                if (params && params.__timeout) {
                    wating =  params.__timeout *1;
                    delete params.__timeout;
                }
                if (params && params.__errorRepeated) {
                    __errorRepeated = params.__errorRepeated * 1;
                    delete params.__errorRepeated;
                }

                var newReq =false;
                // 新接口传参处理，JSON
                if (url.indexOf("/v1/")>-1) {
                    newReq=true;
                    objectJsonData = {};
                    judgeParamsType(params); // 判断参数类型

                    function judgeParamsType (arg) {
                        if (arg && typeof arg === 'string') {
                            try {
                                var paramsData = JSON.parse(arg);
                                dealParamsJson (paramsData);
                            } catch (err) { // 防止带params字段名的参数结构 {params: '测试'}
                                objectJsonData.params = arg;
                                console.log('SAAF.line = 179 ,' + err);
                            }
                        } else if (arg && Object.prototype.toString.call(arg) === '[object Object]') {
                            dealParamsJson(arg);
                        }
                    }
                    function dealParamsJson (formData) { // 处理参数
                        for (var attr in formData) {
                            objectJsonData[attr] = formData[attr];
                            if (formData.params) {
                                judgeParamsType(formData.params);
                            }
                        }
                        if (objectJsonData.params) {
                            delete objectJsonData.params;
                        }
                    }
                }else if (url.indexOf("/v2/")>-1){
                    newReq=true;
                    objectJsonData = {};
                    if (params && typeof params === 'string') {
                        try {
                            objectJsonData = JSON.parse(params);
                        } catch (err) { // 防止带params字段名的参数结构 {params: '测试'}
                            objectJsonData.params = arg;
                            console.error(err);
                        }
                    } else if (params && Object.prototype.toString.call(params) === '[object Object]') {
                        objectJsonData=params;
                    }
                    if (objectJsonData.params && typeof objectJsonData.params==='string'){
                        objectJsonData.params=JSON.parse(objectJsonData.params);
                    }

                }

                function  getData (){
                    $http({
                        url: url,
                        method: method || 'post',
                        data: params ? (newReq ? objectJsonData : $.param(params)) : null,
                        //withCredentials: true,//带证书
                        headers: {
                            "Content-Type": newReq ? "application/json;charset=UTF-8" : "application/x-www-form-urlencoded;charset=UTF-8;",
                            "Certificate": Cookies.getCookie(appName + '_certificate') || sessionStorage[appName + '_certificate'] || localStorage[appName + '_certificate'] || 'nothing',
                            "TokenCode": tokenCode
                        }
                        ,  timeout: wating * 1000  //超时
                    })
                        .success(function (res,status, headers, config) {
                            if (loadingFlag == null || loadingFlag == true) {
                                setLoadingHide(timestampId);
                            }
                            if (!res) {
                                SIEJS.alert('后台服务异常', 'warning');
                                return;
                            }
                            if (res.status.toUpperCase() === 'TIMEOUT') {
                                SIEJS.alert('系统超时了,请重新登录', 'warning', '确定', '', function () {
                                    gotoLogin();
                                });
                                return;
                            }
                            if (type !== 'post' && type !== 'get') {
                                if (res.status.toUpperCase() === "S") {
                                    SIEJS.alert($rootScope.$l(type + '成功'), 'success');
                                }
                                else {
                                    SIEJS.alert(res.msg, 'error');
                                    console.log(res); //　打印失败结果
                                }
                            }
                            if (suc && typeof suc === 'function') {
                                suc(res,status, headers, config);
                            } else {
                                console.log('success 不是一个function'); //　打印失败结果
                            }
                            Cookies.setCookie(window.appName + '_sessionTime', 'running', webconfig.sessionTime);// 系统会话时长
                            Cookies.setCookie('sessionTime', 'running', webconfig.sessionTime);// 系统会话时长

                        })
                        .error(
                            function (res, status, headers, config) {
                                if (__errorRepeated  && errorNum < __errorRepeated) {
                                    $timeout(function(){
                                        getData();
                                    },1000);
                                    errorNum ++;
                                }else {
                                    errorNum=0;
                                    setLoadingHide(timestampId);
                                    if (res) {
                                        console.log(res.status);
                                        if (res.status == "NOT-LOGGED-IN") {

                                            $(".modal-backdrop").remove();
                                            window.parent.swal({
                                                title: "您的登录已过期!",
                                                text: "3秒后将自动跳转至登陆界面",
                                                type: "info",
                                                confirmButtonText: "确定",
                                                closeOnConfirm: true,
                                                timer: 3000
                                            }, function () {
                                                // gotoLogin.goLogin();
                                            });
                                            return;
                                        }
                                    }
                                    if (typeof err === 'function') {
                                        err(res, status, headers, config);
                                    } else {
                                        SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                                    }
                                }
                            }
                        )
                }

                getData();
            }


            service.post = function (url, params, suc, err, loadingFlag) {
                return baseHttp('post', url, params, suc, err, loadingFlag);
            };
            service.remove = function (url, params, suc, err, loadingFlag) {
                SIEJS.confirm('您确认要删除吗？', '', '确定', function () {
                    return baseHttp('删除', url, params, suc, err, loadingFlag);
                })
            };
            service.add = function (url, params, suc, err, loadingFlag) {
                return baseHttp('添加', url, params, suc, err, loadingFlag);
            };
            service.save = function (url, params, suc, err, loadingFlag) {
                return baseHttp('保存', url, params, suc, err, loadingFlag);
            };
            service.update = function (url, params, suc, err, loadingFlag) {
                return baseHttp('更新', url, params, suc, err, loadingFlag);
            };
            service.put = function (url, params, suc, err, loadingFlag) {
                return baseHttp('更新', url, params, suc, err, loadingFlag);
            };

            service.get = function (url, params, suc, err, loadingFlag) {
                return baseHttp('get', url, params, suc, err, loadingFlag, 'get');
            };
            service.exportFile = function (url, method, params, suc, err, loadingFlag) {
                if (loadingFlag == null || loadingFlag == true) {
                    loading.show();
                }

                var wating = (params && (params.timeout || params.timeout == 0 )) ? params.timeout : 60; //超时等待时间(秒)  0为无限制
                $http({
                    url: url,
                    method: method,
                    data: params ? $.param(params) : null,
                    headers: {
                        "Content-Type": "application/x-www-form-urlencoded;charset=UTF-8",
                    },
                    responseType: 'arraybuffer',
                    timeout: wating * 1000  //超时
                })
                    .success(function (res) {
                        if (loadingFlag == null || loadingFlag == true) {
                            loading.hide();
                        }
                        suc(res);
                        $timeout(function () {
                            angular.forEach(angular.element('input,select,textarea'), function (input, index) {
                                $(input).removeClass('ng-dirty');/// 清除已修改的标识
                                $(input).attr('data-oldval', $(input).val());// 清除已修改的旧值
                            });
                        }, 500)
                    })
                    .error(function (res, status) {
                        loading.hide();
                        console.log('下载失败!');
                        console.log('status:' + status);
                        if (status == 401) {
                            localStorage[appName + '_successLoginInfo'];
                            sessionStorage[appName + '_successLoginInfo'];
                            $rootScope.inTab = [];/// 清除菜单tab
                            $(".modal-backdrop").remove();
                            SIEJS.alert('您的登录已过期！', "error", "确定");
                            $state.go("login");
                        }
                    })
            };
            return service;
        })
        .service("SIE.JS", function (SIEJS) {
            return SIEJS;
        })
        //xls导出
        /*
         * webserviceUrl  apiUrl
         * params 参数数
         * sheetName 下载时表名
         * labelName [userName,row.userFullName,platformName,employeeNumber,employeeName,mobilePhone,telPhone] 表格对应字段
         * */
        .service('saafXlsExport', function (httpServer, SIEJS, URLAPI) {
            function xlsDown(webserviceUrl, params, sheetName, labelName, name) {
                console.log('xls导出信息↓↓↓↓↓');
                var xlsParams = {
                    sheetName: sheetName, labelName: labelName, name: name
                };

                function submPost(config) {
                    console.log(config.data);
                    httpServer.post(config.url,
                        {
                            params: config.data.params,
                            xlsParams: config.data.xlsParams,
                            pageIndex: 1,
                            pageRows: 1000,
                            exportExcel: 'Y'
                        },
                        function (res) {
                            console.log(res)

                            //$scope.current.articleContent=decodeURI($scope.current.articleContent)

                        })
                }

                var DownLoadFile = function (options) {
                    var config = $.extend(true, {method: 'post'}, options);
                    var downloadIframe = $('<iframe id="down-file-iframe" style="display:block"/>');
                    var downloadForm = $('<form id="down-file-form" target="down-file-iframe" method="' + config.method + '"/>');
                    downloadForm.attr('action', config.url);
                    //for (var key in config.data) {
                    //    downloadForm.append('<input type=\'text\' name=\'' + key + '\' value=\'' + config.data[key] + '\' />');
                    //}
                    downloadForm.append('<input type=\'submit\' style="display: block" onclick="' + submPost(config) + '" />');
                    downloadIframe.append(downloadForm);
                    $(document.body).append(downloadIframe);
                    downloadForm.submit();
                    //downloadIframe.remove();
                };

                var data = {
                    'params': params.params,
                    'pageIndex': -1,
                    'pageRows': -1,
                    'exportExcel': 'Y',
                    'xlsParams': JSON.stringify({
                        "sheetName": "执行事务补偿",
                        "labelName": ["角色名称2", "角色编号", "系统编码", "角色描述"],
                        "name": ["userName", "row.userFullName", "systemCode", "roleDesc"]
                    })
                };
                console.log(data);
                DownLoadFile({url: webserviceUrl, data: data});
            }

            return xlsDown;
        })

        // 主Tab的添加或切换
        .service('tabAction', function ($rootScope, Base64, saveTabToStorage, SIEJS, httpServer, URLAPI, $stateParams, $location) {


            // 全局 tab操作
            if (!window.tabAction) {
                window.tabAction = function (name, url, id, apply, isParent) {
                    return actions(name, url, id, apply, isParent);
                };
            }
            var maxTabs = 20;// 最大打开tabs数

            function actions(name, url, id, apply, isParent) {
                if ($rootScope.saafHeadTab.length >= maxTabs) {
                    SIEJS.alert({
                        title: '最多能打开' + maxTabs + '个tab标签!',
                        text: '为了保证系统的流畅和稳定，请关闭部分标签页。\n[Ctrl + Q] 进行关闭',
                        type: 'warning'
                    });
                    return;
                }

                if (!name || !url) return;
                url = url.indexOf('/') === 0 ? url.substr(1) : url;
                if ($rootScope.saafHeadTab.length === 0) {
                    $rootScope.saafHeadTab.push({
                        name: name,
                        url: url,
                        id: id,
                        isParent: isParent === undefined ? true : isParent,
                        active: true,
                        version: new Date().getTime()
                    })
                } else {
                    var hasTab = false;
                    for (var n in $rootScope.saafHeadTab) {
                        var item = $rootScope.saafHeadTab[n];
                        if (item.name === name && item.url === url) { // 已有当前菜单
                            item.active = true;
                            hasTab = true;
                        } else {
                            item.active = false;
                        }
                    }
                    if (!hasTab) { // 没有菜单，新增

                        $rootScope.saafHeadTab.push({
                            name: name,
                            url: url,
                            id: id,
                            isParent: isParent === undefined ? true : isParent,
                            active: true,
                            version: new Date().getTime()
                        })
                    }
                }
                if (!apply) {
                    $rootScope.$apply();
                }
                if (id) {
                    //菜单统计服务
                    httpServer.post(URLAPI.menuClickCount, {
                        params: JSON.stringify({menuId: id})
                    }, function (res) {
                        if (res.status !== 'S') {
                            console.error(res.msg)
                        }
                    }, function (err) {
                        console.error('菜单统计报错')
                    }, 'false')
                }

                saveTabToStorage();
            }

            return actions;
        })
        // iframe 内面操作 tabs
        .service('iframeTabAction', function ($rootScope, tabAction, $location, $stateParams) {

            return function (name, url, id) {

                var hash = window.location.hash;
                var isParent = true;
                if (!id && hash) { /// 若没有传递菜单 ID 将会继承上级页面的权限
                    var reg = /id=[0-9]+$/;
                    hash = hash.match(reg);
                    if (hash) id = hash[0].match(/[0-9]+/)[0];
                    isParent = false;
                }

                if (window.parent != window) { // 子级页面 iframe
                    window.parent.tabAction(name, url, id, null, isParent);
                }
            }
        })
        // 把TabMenu保存到Storage
        .service('saveTabToStorage', function ($rootScope, Base64) {
            return function () {
                sessionStorage[appName + '_headTab'] = Base64.encode(JSON.stringify($rootScope.saafHeadTab));// 保存菜单
            }
        })

        ////////普通包含父子结构的JSON转为子级嵌套结构
        .service("ToChildrenJson", function () {
            return function (source, pIdKey, idKey, pIdVal, children) {
                //source;//  要转换的包含父子结构的简单json格式
                pIdKey = pIdKey ? pIdKey : 'parendId'; //父级ID名称
                idKey = idKey ? idKey : 'id'; //子级ID名称
                pIdVal = pIdVal ? pIdVal : 0;   //父级ID根节点的ID值
                children = children ? children : 'children';// 嵌套的子级名称

                var tmp = {}, parent = [], json = [];
                for (var n in source) {
                    var item = source[n];
                    ///// 取值关键
                    if (item[pIdKey] == pIdVal) {
                        parent.push(item[idKey]);
                    }
                    if (!tmp[item[idKey]]) {
                        tmp[item[idKey]] = {};
                    }

                    for (m in item) {
                        tmp[item[idKey]][m] = item[m];
                    }

                    if (!(children in tmp[item[idKey]])) tmp[item[idKey]].children = [];

                    if (item[idKey] != item[pIdKey]) {
                        if (tmp[item[pIdKey]]) {
                            tmp[item[pIdKey]].children.push(tmp[item[idKey]]);
                        }
                        else {
                            tmp[item[pIdKey]] = {children: [tmp[item[idKey]]]};
                        }
                    }
                }
                //return tmp[parent];
                for (var p in parent) {
                    json.push(tmp[parent[p]]);
                }
                return json;


            }

        })

        /*递归数据
         * data :需要处理的数据
         * id  当前id的主键名
         * pId 依赖的父级id名
         * pIdVal 父级id 起始值

         * @返回一个新数据
         *
         * */
        .service('recursionJSON', function () {
            return function (data, id, pId, pIdVal) {
                if (!data) {
                    console.log('缺少数据源-data');
                    return;
                }
                var list = [];
                var root = [];

                function to(pval) {
                    for (var i = 0; i < data.length; i++) {
                        var item = data[i];
                        if (item[pId] === pval) { //　存在当前父级
                            list.push(item);

                            to(item[id]);
                        }
                    }
                }

                to(pIdVal);
                return list;
            }
        })

        .service("SIEJS", function ($timeout, IEVersion) {
            var services = {};

            var ie = IEVersion();
            if (ie > 8) {

                services.alert = function (title, type, buttonText, text, func) {

                    if (typeof title === 'object') {
                        swal(title);
                        return;
                    }
                    swal({
                        title: title,
                        type: type ? type : 'success',
                        text: text,
                        confirmButtonText: buttonText ? buttonText : "确定"
                    }, function (isConfirm) {

                        if (typeof func == 'function')//如果是个func 添加回调
                            $timeout(function () {
                                func(isConfirm);
                            }, 200)

                    });

                    if ('success' == type || !type) {
                        setTimeout(function () {
                            swal.close();
                        }, 1500)
                    }
                };
                services.confirm = function (title, text, buttonText, func) {
                    swal({
                            title: title ? title : "确定该操作？",
                            text: text,
                            type: "warning",
                            showCancelButton: true,
                            confirmButtonColor: "#DD6B55",
                            cancelButtonText: '取消',
                            confirmButtonText: buttonText ? buttonText : "确定",
                            closeOnConfirm: true
                        },
                        function (isConfirm) {
                            if (isConfirm) {
                                if (typeof func == 'function')//如果是个func 添加回调
                                    $timeout(function () {
                                        func(isConfirm);
                                    }, 200)
                            } else {
                                return false;
                            }
                        });

                };
            } else {
                //alert(ie);
                services.alert = function (title, type, buttonText, text, func) {

                    alert(title);
                };
                services.confirm = function (title, text, buttonText, func) {

                    if (confirm(title + '\n' + text)) {
                        if (typeof func == 'function')//如果是个func 添加回调
                            $timeout(function () {
                                func(isConfirm);
                            }, 200)
                    }


                };
            }


            return services;
        })

        .service('IEVersion', function () {
            return function () {
                var userAgent = navigator.userAgent; //取得浏览器的userAgent字符串
                var isIE = userAgent.indexOf("compatible") > -1 && userAgent.indexOf("MSIE") > -1; //判断是否IE<11浏览器
                var isEdge = userAgent.indexOf("Edge") > -1 && !isIE; //判断是否IE的Edge浏览器
                var isIE11 = userAgent.indexOf('Trident') > -1 && userAgent.indexOf("rv:11.0") > -1;
                if (isIE) {
                    var reIE = new RegExp("MSIE (\\d+\\.\\d+);");
                    reIE.test(userAgent);
                    var fIEVersion = parseFloat(RegExp["$1"]);
                    if (fIEVersion == 7) {
                        return 7;
                    } else if (fIEVersion == 8) {
                        return 8;
                    } else if (fIEVersion == 9) {
                        return 9;
                    } else if (fIEVersion == 10) {
                        return 10;
                    } else {
                        return 6;//IE版本<=7
                    }
                } else if (isEdge) {
                    return 12;//edge
                } else if (isIE11) {
                    return 11; //IE11
                } else {
                    return 10000;//不是ie浏览器
                }
            }
        })
        .service('Fullscreen', function () {

            var service = {};
            var el;
            var fullChange = function (e) {
                el = e;
                if ($.trim(e.target.innerText) == '全屏') {
                    In();
                } else {
                    Exit();
                }
            };

            //进入全屏
            var In = function () {
                var docElm = document.documentElement;
                //W3C

                if (docElm.requestFullscreen) {
                    docElm.requestFullscreen();
                }
                //FireFox
                else if (docElm.mozRequestFullScreen) {
                    docElm.mozRequestFullScreen();
                }
                //Chrome等
                else if (docElm.webkitRequestFullScreen) {

                    docElm.webkitRequestFullScreen();
                }
                //    //IE11
                //else if (elem.msRequestFullscreen) {
                //    elem.msRequestFullscreen();
                //}
                else {
                    alert("您的浏览器不支持全屏！请按键盘上的F11试试！");
                    // if (el) el.target.innerHTML = '';
                    return false;
                }

                return true;
            };

            ///退出全屏
            var Exit = function () {
                if (document.exitFullscreen) {
                    document.exitFullscreen();
                }
                else if (document.mozCancelFullScreen) {
                    document.mozCancelFullScreen();
                }
                else if (document.webkitCancelFullScreen) {
                    document.webkitCancelFullScreen();
                }
                else if (document.msExitFullscreen) {
                    document.msExitFullscreen();
                }
            };
            document.addEventListener("fullscreenchange", function () {
                fulltxt();
            }, false);

            document.addEventListener("mozfullscreenchange", function () {
                fulltxt();
            }, false);

            document.addEventListener("webkitfullscreenchange", function () {
                fulltxt();

            }, false);
            document.addEventListener("msfullscreenchange", function () {
                fulltxt();
            }, false);

            function fulltxt() {
                if (el.target.innerText == '全屏') {
                    el.target.innerHTML = '<span class="fa fa-clone mr5"></span>退出全屏';
                } else {
                    el.target.innerHTML = '<span class="fa fa-square-o mr5"></span>全屏';
                }
            }

            service = {
                fullChange: fullChange,
                In: In,
                Exit: Exit
            };
            return service;

        })


        // 格式数字保留 N 个小数点
        .service('FormatFloat', function () {
            return function (src, pos) {
                return Math.round(src * Math.pow(10, pos)) / Math.pow(10, pos);
            }
        })
        // 统计 合计表格数据
        .service('TotalRows', function (FormatFloat) {
            return function (source) {
                var result = {};
                for (var i in source) {
                    for (var n in source[i]) {
                        if (typeof source[i][n] === 'number') {
                            result[n] = !result[n] ? source[i][n] * 1 : (result[n] * 1 + source[i][n] * 1);
                            result[n] = FormatFloat(result[n], 2)
                        }
                    }
                }
                return result;
            }
        })

        /* 数组对象排序 用于数据里是对象的列表进行升序倒序排序
         * attr 排序的字段值
         * rev 排序方式，倒序或顺序
         *
         *
         * 使用方法： res.data.sort(SortBy(_sort.value, _sort.by))
         * */
        .service('SortBy', function () {
            return function (attr, rev) {
                rev = rev === 'desc' ? -1 : 1;
                return function (a, b) {
                    a = a[attr];
                    b = b[attr];
                    if (a < b) {
                        return rev * -1;
                    }
                    if (a > b) {
                        return rev * 1;
                    }
                    return 0;
                }
            }
        })

        // 根据dom　获取适合的行数
        .service('PageRows', function () {
            return function (name) {
                var _top = ($(name).offset().top * 1);//顶部距离
                var _scoll = document.body.scrollTop;
                var _h = document.body.clientHeight;// 网页可见高度

                var _view = _h - _top - 90; // 数据行可显示的高度  减去表头 、分页
                var row = Math.round(_view / 34) <= 1 ? 6 : Math.round(_view / 34); // 计算出当前应该显示的数据行数  34px为当前表格行的高度
                var i = _h === 0 ? (localStorage.pageRows || row) : row;
                localStorage.pageRows = row;
                return i * 1;

            }
        })
        // 是否是数组
        .service('IsArray', function () {
            return function (obj) {
                if (typeof obj === 'object' && Array == obj.constructor) {
                    return true;
                } else {
                    return false;
                }
            }
        })
        // 去掉两头空格
        .service('trimStr', function () {
            return function (str) {
                if (str) {
                    return str.replace(/(^\s*)|(\s*$)/g, "");
                } else return str;
            }
        })
        ///// 数字转化
        .service('formatInt', function () {

            var service = {};
            //JS把全角转为半角的函数
            service.CtoH = function (str) {
                var result = "";
                for (var i = 0; i < str.length; i++) {
                    if (str.charCodeAt(i) == 12288) {
                        result += String.fromCharCode(str.charCodeAt(i) - 12256);
                        continue;
                    }
                    if (str.charCodeAt(i) > 65280 && str.charCodeAt(i) < 65375) {
                        result += String.fromCharCode(str.charCodeAt(i) - 65248);
                    } else {
                        result += String.fromCharCode(str.charCodeAt(i));
                    }
                }
                return result;
            };
            //判断是否是数字，并去除非数字字符
            service.formatNumber = function (str, y) {
                if (!str || str == 0)return;
                str = service.CtoH(str);//转换全角字符为半角
                str = str.match(/[\d|\.]+/gim);
                if (str == null) {
                    return '';
                } else {
                    str = str.join('');//截取所有数字字小数点
                    var re = new RegExp('^[1-9]\\d*(\\.\\d{1,' + y + '})?|^0\\.\\d{1,' + y + '}'); // 正则式采用变量时　注意　\ 要加反义多加一个\
                    var s = str.match(re);
                    return s ? s[0] : ''; ////  截取匹配的部分
                }

            };

            // 判断是否是整数，并去除非数字字符
            service.formatIntNumber = function (str) {
                str = service.CtoH(str);//转换全角字符为半角
                return str.replace(/\D/g, '');
            };
            return service;

        })

        ////// 倒计时
        /** 倒计时参数　
         * stratTime 开始时间　
         * endTime 结束时间
         * difference 服务器与本地时间的差　默认不使用
         * msg []　 定义 时间的状态显示信息(三种，即将开始，进行中，已结束)
         * format 显示格式  dd:hh:mm:ss  / hh:mm:ss 中文几天几时几分几秒
         * 　*/
        .service('countdown', function () {
            ////倒计时 补0
            function pad(mun) {
                if (mun < 10) return '0' + mun;
                else return mun;
            }

            ///// endTime结束时间，difference差值，服务器与本地时间的差值，msg定义到期的显示信息，format显示格式
            ////   时间格式为 2016-10-31 20:12:11 或是 2016/10/31 20:12:11
            return function (startTime, endTime, difference, msg, format) {
                var EndTime = endTime ? new Date(Date.parse(endTime.replace(/-/g, "/"))) : new Date().getTime() + dif; //截止时间
                var StartTime = startTime ? new Date(Date.parse(startTime.replace(/-/g, "/"))) : new Date().getTime() + dif; //开始时间,如果不传传取当前时间
                var dif = difference ? difference : 0;  //服务器与本地时间的差值
                var NowTime = new Date().getTime() + dif; ///当地时间加上服务器的差值
                var txt;
                var times = '';
                var t;
                var types;
                var run = false;
                var d = 0; //天
                var h = 0;//时
                var m = 0;//分
                var s = 0;//秒
                if (NowTime < StartTime) { //当前时间小于　开始时间　　还没开始
                    //t = (NowTime.getTime() + dif) - StartTime.getTime();
                    t = StartTime - NowTime;
                    txt = msg ? msg[0] : '敬请期待';
                    run = false;
                    types = 0;

                } else if (NowTime < EndTime) {//当前时间小于结束时间，活动还在进行中
                    t = EndTime - NowTime;
                    txt = msg ? msg[1] : '活动进行中';
                    run = true;
                    types = 1;
                } else {
                    txt = msg ? msg[2] : '活动已结束';
                    t = 0;
                    run = false;
                    types = 2;

                }

                d = Math.floor(t / 1000 / 60 / 60 / 24);
                h = pad(Math.floor(t / 1000 / 60 / 60 % 24));
                m = pad(Math.floor(t / 1000 / 60 % 60));
                s = pad(Math.floor(t / 1000 % 60));

                switch (format) {
                    case 'dd:hh:mm:ss':
                        times = d + ':' + h + ':' + m + ':' + s;
                        break;
                    case 'hh:mm:ss':
                        times = h + ':' + m + ':' + s;
                        break;
                    default:
                        times = (d == 0 ? '' : d + '天') + h + '时' + m + '分' + s + '秒';
                        break;
                }

                return {
                    msg: txt,
                    times: times,
                    type: types,  //返回运行类型，（未开始＼运行中＼已过期）
                    run: run //是否运行中
                };

            }

        })

        /* 计算日期时间差
         * type, 时间类型 天、月、年、日,时
         * number, 时间间隔数
         * date 时间对象 是对象。。注意。。。。。
         * format 返回的格式
         * 返回:新的时间对象
         * */
        .service('DateAdd', function ($filter) {
            return function (type, number, date, format) {
                switch (type.toLowerCase()) {
                    case 'y':
                        date.setFullYear(date.getFullYear() + number);
                        break;

                    case 'q':
                        date.setMonth(date.getMonth() + number * 3);
                        break;

                    case 'm':
                        date.setMonth(date.getMonth() + number);
                        break;

                    case 'w':
                        date.setDate(date.getDate() + number * 7);

                        break;

                    case 'd':
                        date.setDate(date.getDate() + number);

                        break;

                    case 'h':
                        date.setHours(date.getHours() + number);

                        break;

                    case 'mm':
                        date.setMinutes(date.getMinutes() + number);

                        break;

                    case 's':
                        date.setSeconds(date.getSeconds() + number);

                        break;

                    default:
                        date.setDate(d.getDate() + number);
                        break;

                }
                return $filter('date')(date, format || 'yyyy-MM-dd')
            }
        })

        /* html table 导出为　excel
         * 参数：　tableid　　为需要导出表格的 id
         * */
        .service('TableToExcel', function () {
            var idTmr;

            function getExplorer() {
                var explorer = window.navigator.userAgent;
                //ie
                if (explorer.indexOf("MSIE") >= 0) {
                    return 'ie';
                }
                //firefox
                else if (explorer.indexOf("Firefox") >= 0) {
                    return 'Firefox';
                }
                //Chrome
                else if (explorer.indexOf("Chrome") >= 0) {
                    return 'Chrome';
                }
                //Opera
                else if (explorer.indexOf("Opera") >= 0) {
                    return 'Opera';
                }
                //Safari
                else if (explorer.indexOf("Safari") >= 0) {
                    return 'Safari';
                }
            }

            function ToExcel(tableid, name) {//整个表格拷贝到EXCEL中
                if (!tableid) {
                    alert('请设置需要导出table的 Id');
                    return;
                }
                if (getExplorer() == 'ie') {
                    var curTbl = document.getElementById(tableid);
                    var oXL = new ActiveXObject("Excel.Application");

                    //创建AX对象excel
                    var oWB = oXL.Workbooks.Add();
                    //获取workbook对象
                    var xlsheet = oWB.Worksheets(1);
                    //激活当前sheet
                    var sel = document.body.createTextRange();
                    sel.moveToElementText(curTbl);
                    //把表格中的内容移到TextRange中
                    sel.select();
                    //全选TextRange中内容
                    sel.execCommand("Copy");
                    //复制TextRange中内容
                    xlsheet.Paste();
                    //粘贴到活动的EXCEL中
                    oXL.Visible = true;
                    //设置excel可见属性

                    try {
                        var fname = oXL.Application.GetSaveAsFilename("Excel.xls", "Excel Spreadsheets (*.xls), *.xls");
                    } catch (e) {
                        print("Nested catch caught " + e);
                    } finally {
                        oWB.SaveAs(fname);

                        oWB.Close(savechanges = false);
                        //xls.visible = false;
                        oXL.Quit();
                        oXL = null;
                        //结束excel进程，退出完成
                        //window.setInterval("Cleanup();",1);
                        idTmr = window.setInterval("Cleanup();", 1);

                    }

                }
                else {

                    tableToExcel(tableid, name)
                }
            }

            function Cleanup() {
                window.clearInterval(idTmr);
                CollectGarbage();
            }

            var tableToExcel = (function () {
                var uri = 'data:application/vnd.ms-excel;base64,',
                    template = '<html xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns="http://www.w3.org/TR/REC-html40">' +
                        '<head><meta http-equiv="Content-Type" content="text/html; charset=utf-8" /><!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet><x:Name>{worksheet}</x:Name><x:WorksheetOptions><x:DisplayGridlines/></x:WorksheetOptions></x:ExcelWorksheet></x:ExcelWorksheets></x:ExcelWorkbook></xml><![endif]--></head><body>{table}</body></html>',
                    base64 = function (s) {
                        return window.btoa(unescape(encodeURIComponent(s)))

                    },
                    format = function (s, c) {
                        return s.replace(/{(\w+)}/g,
                            function (m, p) {
                                return c[p];
                            })
                    };
                return function (table, name) {
                    if (!table.nodeType) table = document.getElementById(table);
                    var _table = table.innerHTML.replace(/<!--[^>]*-->/ig, '');  // 清除不需要的　注释 <!-- --> 的标签
                    _table = _table.replace(/<td[^>]*>/ig, '<td>'); // 清除　td 里的所有其它属性
                    _table = _table.replace(/<tr[^>]*>/ig, '<tr>'); // 清除　tr 里的所有其它属性
                    _table = _table.replace(/<th[^>]*>/ig, '<th>'); // 清除　th 里的所有其它属性
                    _table = _table.replace(/<\/?span[^>]*>/ig, ''); // 清除　span 标签
                    _table = _table.replace(/<\/?a[^>]*>/ig, ''); // 清除　a 标签
                    _table = _table.replace(/<\/?div[^>]*>/ig, ''); // 清除　div 标签
                    _table = _table.replace(/<\/?p[^>]*>/ig, ''); // 清除　p 标签
                    _table = _table.replace(/\s\s|	/ig, ''); // 清除　连续两个以上的空白符

                    var ctx = {worksheet: name || 'worksheet', table: _table};

                    //window.location.href = uri + base64(format(template, ctx));

                    document.getElementById("dlink").href = uri + base64(format(template, ctx));
                    document.getElementById("dlink").download = name ? name + '.xls' : '报表.xls';
                    document.getElementById("dlink").click();

                }
            })();

            /// 插件另存为法，使用插件：FileSaver.js
            var fileSave = function (tableid, name) {
                var blob = new Blob([document.getElementById(tableid).innerHTML], {
                    type: "data:application/vnd.ms-excel;charset=UTF-8"
                });
                var strFile = name + ".xls";
                saveAs(blob, strFile);
            };
            //return q;
            return ToExcel;
        })

        /*
         * 对象转为键值对
         * */
        .service('ObjectToValueKey', function () {
            return function (obj) {
                var s = '';
                for (var n in obj) {
                    s += '&' + n + '=' + obj[n];
                }
                s = s.substr(1);
                return s;
            }
        })

        .service('Base64', function () {
            var _keyStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";
            // 加密
            this.encode = function (input) {
                if (!input) return;
                if (typeof input === 'object') {
                    input = JSON.stringify(input);

                }
                var output = "";
                var chr1, chr2, chr3, enc1, enc2, enc3, enc4;
                var i = 0;
                input = _utf8_encode(input);
                while (i < input.length) {
                    chr1 = input.charCodeAt(i++);
                    chr2 = input.charCodeAt(i++);
                    chr3 = input.charCodeAt(i++);
                    enc1 = chr1 >> 2;
                    enc2 = ((chr1 & 3) << 4) | (chr2 >> 4);
                    enc3 = ((chr2 & 15) << 2) | (chr3 >> 6);
                    enc4 = chr3 & 63;
                    if (isNaN(chr2)) {
                        enc3 = enc4 = 64;
                    } else if (isNaN(chr3)) {
                        enc4 = 64;
                    }
                    output = output +
                        _keyStr.charAt(enc1) + _keyStr.charAt(enc2) +
                        _keyStr.charAt(enc3) + _keyStr.charAt(enc4);
                }
                return output;
            };
            // 解密
            this.decode = function (input) {
                var output = "";
                var chr1, chr2, chr3;
                var enc1, enc2, enc3, enc4;
                var i = 0;
                input = input.replace(/[^A-Za-z0-9\+\/\=]/g, "");
                while (i < input.length) {
                    enc1 = _keyStr.indexOf(input.charAt(i++));
                    enc2 = _keyStr.indexOf(input.charAt(i++));
                    enc3 = _keyStr.indexOf(input.charAt(i++));
                    enc4 = _keyStr.indexOf(input.charAt(i++));
                    chr1 = (enc1 << 2) | (enc2 >> 4);
                    chr2 = ((enc2 & 15) << 4) | (enc3 >> 2);
                    chr3 = ((enc3 & 3) << 6) | enc4;
                    output = output + String.fromCharCode(chr1);
                    if (enc3 != 64) {
                        output = output + String.fromCharCode(chr2);
                    }
                    if (enc4 != 64) {
                        output = output + String.fromCharCode(chr3);
                    }
                }
                output = _utf8_decode(output);
                return output;
            };
            // private method for UTF-8 encoding
            var _utf8_encode = function (string) {
                string = string.replace(/\r\n/g, "\n");
                var utftext = "";
                for (var n = 0; n < string.length; n++) {
                    var c = string.charCodeAt(n);
                    if (c < 128) {
                        utftext += String.fromCharCode(c);
                    } else if ((c > 127) && (c < 2048)) {
                        utftext += String.fromCharCode((c >> 6) | 192);
                        utftext += String.fromCharCode((c & 63) | 128);
                    } else {
                        utftext += String.fromCharCode((c >> 12) | 224);
                        utftext += String.fromCharCode(((c >> 6) & 63) | 128);
                        utftext += String.fromCharCode((c & 63) | 128);
                    }
                }
                return utftext;
            };
            // private method for UTF-8 decoding
            var _utf8_decode = function (utftext) {
                var string = "";
                var i = 0;
                var c = c1 = c2 = 0;
                while (i < utftext.length) {
                    c = utftext.charCodeAt(i);
                    if (c < 128) {
                        string += String.fromCharCode(c);
                        i++;
                    } else if ((c > 191) && (c < 224)) {
                        c2 = utftext.charCodeAt(i + 1);
                        string += String.fromCharCode(((c & 31) << 6) | (c2 & 63));
                        i += 2;
                    } else {
                        c2 = utftext.charCodeAt(i + 1);
                        c3 = utftext.charCodeAt(i + 2);
                        string += String.fromCharCode(((c & 15) << 12) | ((c2 & 63) << 6) | (c3 & 63));
                        i += 3;
                    }
                }
                return string;
            }
        })

        .service('MD5', function () {
            return function (val) {
                val = webconfig.md5Config.prefix + val + webconfig.md5Config.suffix;
                return md5(val);
            };
        })

        .service('Cookies', function () {

            function getcookie(name) {
                var cookie_start = document.cookie.indexOf(name);
                var cookie_end = document.cookie.indexOf(";", cookie_start);
                return cookie_start == -1 ? '' : unescape(document.cookie.substring(cookie_start + name.length + 1, (cookie_end > cookie_start ? cookie_end : document.cookie.length)));
            }

            /* 设置cookie
             * cookieName 名称
             * cookieValue 值
             * longtime 时长 / 分钟
             * */
            function setcookie(cookieName, cookieValue, longtime) {
                var expires = new Date();
                var now = parseInt(expires.getTime());

                var et = longtime ? parseFloat(longtime) * 1000 * 60 : 1000 * 60 * 60 * 24 * 1; // 默认一天

                expires.setTime(now + et);
                document.cookie = escape(cookieName) + "=" + escape(cookieValue) + ";expires=" + expires.toGMTString() + "; path=/";
            }

            function delCookie(name) {
                var exp = new Date();
                exp.setTime(exp.getTime() - 1);
                var cval = getcookie(name);
                if (cval != null)
                    document.cookie = escape(name) + "=##############;expires=" + exp.toGMTString() + "; path=/";
            }

            return {
                getCookie: getcookie,
                setCookie: setcookie,
                delCookie: delCookie
            }
        })

        /* 更新选择的row 为最新修改的值
         * 用在当前数据表格进行编辑时对当前ROW的更新
         * row 当前的 dataTable.selectRow(旧值)
         * newRow 当前修的值 （新值）
         * */
        .service('setNewRow', function () {
            return function (row, newRow) {
                for (var n in newRow) {
                    row[n] = newRow[n];
                }
            }
        })

        /*删除数组数据
         * @array 需要删除的数组
         * @item  需要从数据删除出数据
         * @objKey 如果是object时需要提交删除关键key
         * @return n 返回被删除的数据总数
         * */
        .service('arrayDeleteItem', function () {
            return function (array, item, objKey) {
                var n = 0;
                if (typeof item === 'object') {
                    for (var i = 0; i < array.length; i++) {
                        if (array[i][objKey] === item[objKey]) {
                            array.splice(i--, 1);
                            n++;
                        }
                    }
                } else {
                    array.splice(array.indexOf(item), 1);
                    n = 1
                }
                return n;
            }
        })

        /*数据查找 对象的index
         * @array 需要查找的数组
         * @item  需要从数数组查找的数据
         * @objKey 如果是object时需要提交查找关键key
         * @return n 返回index
         * */
        .service('arrayFindItemIndex', function () {

            return function (array, item, objKey) {

                var n = -1;
                if (typeof item === 'object') {
                    for (var i = 0; i < array.length; i++) {
                        if (array[i][objKey] === item[objKey]) {
                            n = i;
                            break;
                        }
                    }
                } else {
                    n = array.indexOf(item);
                }
                return n;
            }

        })


        /*数组查找 对象
         * @array 需要查找的数组
         * @value  需要从数数组查找的关键值
         * @objKey 如果是object时需要提交查找关键key
         * @multi 返回单个还是多个  默认只取回第一个
         *
         * @return 查找到的对象
         * */
        .service('arrayFindObj', function () {
            return function (array, value, objKey, multi) {
                var list = multi === true ? [] : {};
                if (array.length === 0)return;
                if (typeof array[0] === 'object') {
                    for (var i = 0; i < array.length; i++) {
                        var item = array[i];
                        if (item[objKey].toString().toLowerCase() === value.toString().toLowerCase()) {
                            if (multi === true) {
                                list.push(item);
                            } else {
                                list = item;
                                break
                            }

                        }
                    }
                }
                return list;
            }
        })

        /*数组去重
         *  array  要去重的数组
         *  key 根据 主键去重（对象）
         * */
        .service('arrayUnique', function () {
            return function (array, key) {
                if (!array) return;
                var list = [];
                if (typeof array[0] === 'string' || typeof array[0] === 'number') { // 字符或数字
                    for (var n in array) {
                        var item = array[n];
                        if (list.indexOf(item) === -1) {
                            list.push(item);
                        }
                    }
                } else if (typeof array[0] === 'object') {
                    if (!key) {
                        alert('当数组为对象，请指定主键Key值进行去重');
                        return;
                    }

                    for (var i = 0, _list = []; i < array.length; i++) {
                        var item = array[i];
                        if (_list.indexOf(item[key]) === -1) {
                            _list.push(item[key]);
                            list.push(item);
                        }
                    }
                }

                return list;
            }
        })

        /*
         *函数节流
         * 应用场景: DOM 元素动态定位，window对象的resize和scroll 事件 鼠标移动，mousemove 事件
         * 频率控制 返回函数连续调用时，fn 执行频率限定为每多少时间执行一次
         * @param fn {function}  需要调用的函数
         * @param delay  {number}    延迟时间，单位毫秒
         * @param immediate  {bool} 给 immediate参数传递false 绑定的函数先执行，而不是delay后后执行。
         * @return {function}实际调用函数
         */
        .service('throttle', function () {
            return function (fn, delay, immediate, debounce) {
                var curr = +new Date(),//当前事件
                    last_call = 0,
                    last_exec = 0,
                    timer = null,
                    diff, //时间差
                    context,//上下文
                    args,
                    exec = function () {
                        last_exec = curr;
                        fn.apply(context, args);
                    };
                return function () {
                    curr = +new Date();
                    context = this,
                        args = arguments,
                        diff = curr - (debounce ? last_call : last_exec) - delay;
                    clearTimeout(timer);
                    if (debounce) {
                        if (immediate) {
                            timer = setTimeout(exec, delay);
                        } else if (diff >= 0) {
                            exec();
                        }
                    } else {
                        if (diff >= 0) {
                            exec();
                        } else if (immediate) {
                            timer = setTimeout(exec, -diff);
                        }
                    }
                    last_call = curr;
                }
            };

        })


        /*
         * 空闲控制 返回函数连续调用时，空闲时间必须大于或等于 delay，fn 才会执行
         * 应用场景: 文本输入keydown 事件，keyup 事件
         * @param fn {function}  要调用的函数
         * @param delay   {number}    空闲时间
         * @param immediate  {bool} 给 immediate参数传递false 绑定的函数先执行，而不是delay后后执行。
         * @return {function}实际调用函数
         */
        .service('debounce', function (throttle) {
            return function (fn, delay, immediate) {
                immediate = immediate === undefined ? true : false;
                return throttle(fn, delay, immediate, true);
            }
        })

        ///自定义验证表单
        .service('customValidator', function ($timeout) {
            return function (type, ngModel, obj, tip, element) {
                //"[data-error-msg='"+element.attr('name')+"']"
                $timeout(function () {
                    var errorBox = $("[data-error-msg='" + element.attr('name') + "']");
                    var errorMsg = $('<span class="red">' + tip + '</span>');
                    errorMsg.appendTo(errorBox);
                    //errorMsg.attr('ng-show','"restForm.phoneNumber.$invalid && !restForm.phoneNumber.$untouched"');
                    //debugger;
                    errorMsg.hide();

                    if (!element.attr('placeholder')) element.attr('placeholder', tip);
                    if (!element.attr('title')) element.attr('title', tip);
                    var valid = function (value) {
                        var reg;
                        switch (type) {
                            //值比较  常用用于 密码重复验证
                            case 'equals':
                                reg = new RegExp('^' + obj.equals + '$');
                                break;
                            default:
                                reg = obj;
                                break;
                        }

                        var validity = ngModel.$isEmpty(value) || reg.test(value);

                        /*          if (!ngModel.error) ngModel.error = '';
                         if (validity) {
                         var rpl = new RegExp(tip + '，?');
                         ngModel.error = ngModel.error.replace(rpl, '');//清除对应提示
                         } else {
                         if (ngModel.error.indexOf(tip) === -1)
                         ngModel.error += tip + '，'
                         }
                         */
                        //var errorMsg = $("[data-toggle='error']").find("[data-target='"+element.attr('name')+"']");

                        //console.log(ngModel)
                        if (validity) {
                            ngModel.error = '';//清除对应提示
                            errorMsg.hide()
                        } else {
                            ngModel.error = tip;
                            errorMsg.show()
                        }


                        ngModel.$setValidity(type, validity);
                        return validity ? value : null;   // 2018-5-8 修改

                    };

                    if (type !== 'remoteVerify') {
                        ngModel.$formatters.push(valid);
                        ngModel.$parsers.push(valid);
                    }
                })

            }
        })

        // 表单验证
        /* form 表单需要验证的必须带name属性，并且是唯一的
         * 参数 form 是一个ng form 对象
         * <form name="myForm">
         * <input name="userName" ng-model="parame.userName"  data-required-msg="请填写用户名" data-error-msg="格式错误" account>
         *  data-required-msg="请填写用户名"   当为必填时的错误信息，若没有此值，侧取当前dom元素上一个dom的text()来做提示信息
         *   data-error-msg="格式错误"   当填写的格式错误的时候，提示信息
         *   account 自定义校验 账号 的指令，自带有错误提示信息，若设置了data-error-msg侧被此错误信息覆盖
         * */
        .service('validateForm', function ($rootScope, $location, SIEJS) {
            return function (form) {
                var thisForm = $('[name=' + form.$name + ']');
                thisForm.removeClass('isInvalid');
                var r = true;
                for (var n in form) {

                    var obj = $("[name='" + n + "']");
                    if (n.indexOf('$') === -1 && form[n].$invalid) {
                        var tip = '';
                        if (form[n].error) { // 使用form 的错误信息
                            tip = obj.attr('data-error-msg') || form[n].error;
                        } else {
                            var t = '';
                            if (obj[0].tagName === 'SELECT') {
                                t = "请选择"
                            } else {
                                t = "请填写"
                            }
                            tip = obj.prev().text();
                            tip = t + tip.replace(/[:：\*\s]/gim, '');
                            tip = obj.attr('data-required-msg') || tip;
                        }
                        SIEJS.alert(tip, 'warning', '', '表单校验未通过');
                        thisForm.addClass('isInvalid');
                        r = false;
                        break;
                    }
                }
                return r;
            }
        })

        /*
         * @tableName: base-data-table名称 如   base-data-table="dataTable" 中的dataTable
         * @params : 当前查询的参数
         * @count : 当前的需要导出的总记录
         * @callBack 回调
         * */
        // 表格导出
        .service('tableXlsExport', function (httpServer, URLAPI, SIEJS, $rootScope) {
            return function (tableName, params, count, callBack) {
                var findTableStr = "base-data-table='" + tableName + "'";
                if (!tableName) {
                    SIEJS.alert("请输入base-data-table名称", "warning", null);
                    return
                }
                if (!$("div[" + findTableStr + "]").length) {
                    SIEJS.alert("请输入正确的base-data-table名称", "warning", null);
                    return
                }
                var dataLenged = $rootScope.$eval($("div[" + findTableStr + "]").attr('data-legend'));
                var params = params || {};
                var url = URLAPI[$("div[" + findTableStr + "]").attr('data-url')];
                var labelName = [];
                var name = [];
                for (var i = 0; i < dataLenged.length; i++) {
                    labelName.push(dataLenged[i]['name']);
                    name.push(dataLenged[i]['value'])
                }

               /* var p = {  // 旧版导出参数
                 exportURL: url,
                 params: params,
                 xlsParams: {"sheetName": 'ABC', labelName: labelName, name: name},
                 pageIndex: 1,
                 pageRows: webconfig.exportPageRows,
                 exportExcel: 'Y',
                 appName: appName
                 };*/

                var p = {
                    labelName: labelName,
                    attributeNames: name,
                    sheetName: "sheet1",
                    exportNum: -1,
                    //requestUrl: window.location.protocol + url,
                    requestUrl: url,
                    requestParam: {params: params},
                    appName: window.appName.toLocaleUpperCase(),
                    emailSubject: '数据导出',
                    post: true,
                    pageIndexParamName: 'pageIndex',
                    pageRowsParamName: 'pageRows',
                    dataCount: 'count',  // 总记录
                    structure: 'data',
                    toakenAttributeName: 'certificate',
                    token: window.sessionStorage[window.appName + '_certificate'],
                    exportApi: URLAPI.exportData, // 导出接口
                    exportStatusApi: URLAPI.exportDataStatus // 查询导出状态

                };
                angular.forEach(window.parent.saafrootScope.saafHeadTab, function (data, index) {
                    if (data.active) {
                        var reg = /[`~!@#$%^&*()_+<>?:："{},.\/;'[\]]/im;
                        var str = data.name.replace(reg, '-');
                        //p.xlsParams.sheetName = str;// 旧版导出
                        p.emailSubject = str; // 新版导出
                    }
                });

                //debugger;
                sessionStorage.setItem("downloadParams", JSON.stringify(p));
                if (callBack) {
                    callBack()
                }
                window.open("./js/export.html")
            }
        })

        /*拖动
         * */
        .service('dragTable', function () {
            return function (tableId) {

                var tTD; //用来存储当前更改宽度的Table Cell,表格避免快速移动鼠标的问题
                var table = document.getElementById(tableId);
                for (j = 0; j < table.rows[0].cells.length; j++) {
                    table.rows[0].cells[j].onmousedown = function () {
//记录单元格
                        tTD = this;
                        if (event.offsetX > tTD.offsetWidth - 10) {
                            tTD.mouseDown = true;
                            tTD.oldX = event.x;
                            tTD.oldWidth = tTD.offsetWidth;
                        }

//记录Table宽度
//table = tTD; while (table.tagName != ‘TABLE') table = table.parentElement;
//tTD.tableWidth = table.offsetWidth;
                    };
                    table.rows[0].cells[j].onmouseup = function () {
                        //结束宽度调整
                        if (tTD == undefined) tTD = this;
                        tTD.mouseDown = false;
                        tTD.style.cursor = 'default';

                    };
                    table.rows[0].cells[j].onmousemove = function () {
                        //更改鼠标样式
                        if (event.offsetX > this.offsetWidth - 10) {
                            this.style.cursor = 'col-resize';
                        }
                        else {
                            this.style.cursor = 'default';

                        }
                        //取出暂存的Table Cell
                        if (tTD == undefined) tTD = this;
                        //调整宽度
                        if (tTD.mouseDown != null && tTD.mouseDown == true) {
                            tTD.style.cursor = 'default';
                            if (tTD.oldWidth + (event.x - tTD.oldX) > 0)
                                tTD.width = tTD.oldWidth + (event.x - tTD.oldX);
                            //调整列宽
                            tTD.style.width = tTD.width;
                            tTD.style.cursor = 'col-resize';
                            //调整该列中的每个Cell
                            table = tTD;
                            while (table.tagName != 'TABLE') table = table.parentElement;
                            for (j = 0; j < table.rows.length; j++) {
                                table.rows[j].cells[tTD.cellIndex].width = tTD.width;
                            }
                            //调整整个表
                            //table.width = tTD.tableWidth + (tTD.offsetWidth – tTD.oldWidth);
                            //table.style.width = table.width;
                        }
                    };
                }
            }
        })

        //使用a 标签打开新窗口
        .service('openWindow', function () {
            return function (url) {
                $('<a href="' + url + '" id="openWindow" target="_blank"></a>')[0].click()
            }
        })
        /*  获取用户当前页面的对应职责
         * get ()  获取当前页面对应的职责
         * save() 把当前页面的职责更新保存到storage
         *  */
        .service('pageResp', function ($location, arrayFindItemIndex, IsArray, $rootScope, arrayFindObj) {
            var services = {};
            // 根据当前 url 获取对应的 职责
            services.get = function (url) {
                /*    url = url?url:$location.$$path;
                 console.log('url:',url)*/
// debugger;



                var respId = $location.search().respId;

                var menucode = $location.search().menucode;

                url = url || $location.path();
                if(url.indexOf('?')===-1){
                    url =url + '?menucode='+menucode
                }else {
                    url =url + '&menucode='+menucode
                }

                var respList;
                if (respId) {
                    if (!localStorage[appName + '_currentResp']) {
                        return null;
                    }
                    respList = JSON.parse(localStorage[window.appName + '_currentResp']);

                    var userResp;
                    for (var i = 0; i < respList.length; i++) {
                        var node = respList[i].resp;

                        if (node && node.responsibilityId == respId && respList[i].url == url) {
                            userResp = node;
                            break;
                        }
                    }
                    $rootScope.currentResp = userResp;

                    return userResp;
                }

                if (!localStorage[appName + '_currentResp']) {
                    return null;
                } else {
                    respList = JSON.parse(localStorage[appName + '_currentResp']);
                    var resp = null;
                    for (var n = 0; n < respList.length; n++) {
                        var item = respList[n];
                        if (item.url === url) {
                            resp = item.resp;
                            break;
                        }
                    }
                    return resp;
                }
            };

            // 把当前职责存到放当前页面职责列表
            services.save = function (resp, url) {
                if (!resp.responsibilityId) return;
                // url = url || $location.url();
                var resplist = !localStorage[appName + '_currentResp'] ? [] : JSON.parse(localStorage[appName + '_currentResp']);
                var respId = $location.search().respId;
                var pageId = $location.search().id;
                var pageUrl;

                // 继承的页面
                if (pageId && respId && !resp) {
                    for (var n in window.parent.saafrootScope.saafHeadTab) {
                        var tab = window.parent.saafrootScope.saafHeadTab[n];
                        if (tab.id === pageId) {
                            pageUrl = '/' + window.appName.toLowerCase() + '/' + tab.url;
                            break;
                        }
                    }
                    for (var a in resplist) {
                        if (resplist[a].url === pageUrl && resplist[a].resp.responsibilityId === parseInt(respId)) {
                            resp = resplist[a].resp;
                            break;
                        }
                    }
                }

                url = url || $location.path();
                var menucode = $location.search().menucode;

                if(url.indexOf('?')===-1){
                    url =url + '?menucode='+menucode
                }else {
                    url =url + '&menucode='+menucode

                }

                var has = false;
                if (!IsArray(resplist)) {
                    resplist = [];
                }

                // 获取当前职责的详情信息。
                resp = this.getRespInfo(resp.responsibilityId,resp);
                for (var n = 0; n < resplist.length; n++) {
                    var item = resplist[n];
                    if (item.url === url) {
                        item.resp = resp;
                        has = true;
                        break;
                    }
                }
                if (!has) {
                    resplist.push({
                        url: url,
                        resp: resp
                    });
                }
                localStorage[appName + '_currentResp'] = JSON.stringify(resplist);
                // $rootScope.$broadcast("changeCurrentResp", new Date().getTime())
            };


            // 把当前职责存到放当前页面职责列表
            services.delete = function (url) {

                if (!url || !localStorage[appName + '_currentResp']) return;
                var resplist = JSON.parse(localStorage[appName + '_currentResp']);

                for (var n in resplist) {
                    if (resplist[n].url === url) {
                        resplist.splice(n, 1);
                        localStorage[appName + '_currentResp'] = JSON.stringify(resplist);
                        break;
                    }
                }
            };

            // 获取对应职位信息
            services.getRespInfo = function (responsibilityId,resp) {
                var userInfo = JSON.parse(localStorage[window.appName + '_successLoginInfo']);
                var userRespList = userInfo.userRespList; // 职责列表
                var positionList = userInfo.positionList;  // 职位列表
                var obj = resp || arrayFindObj(userRespList, responsibilityId, 'responsibilityId')||{};
                var positionListByOrgId = [];// 当前职责下的职位
                var primaryPosition; // 当前职责的主职位
                if (obj.orgBean && obj.orgBean.orgId) {
                    for (var n = 0; n < positionList.length; n++) {
                        var item = positionList[n];
                        if (item.orgId === obj.orgBean.orgId) {
                            positionListByOrgId.push(item);
                            if (item.primaryFlag === 'Y') { // 主职位
                                primaryPosition = item;
                            }
                        }

                    }
                }
                obj.positionList = positionListByOrgId;
                obj.primaryPosition = primaryPosition;
                // localStorage[window.appName + '_currentResp'] = JSON.stringify(obj);

                return obj;
            };


            return services;

        })
        /* 获取当前用户信息 */
        .service('userInfo', function () {
            var services = {};
            services.get = function () {
                var obj =localStorage[window.appName + '_successLoginInfo'];
                return obj? JSON.parse(obj):null;
            };
            services.set=function (user) {
                localStorage[window.appName + '_successLoginInfo'] = JSON.stringify(user);
                return true;
            };

            return services;

        })

        /* JS 模拟Post或Get方法打开新窗口
         *
         * 使用访求post('pages.html', {money:'400',bankName:'ABC'})
         * */
        .service('postFrom', function () {
            return function (url, params, post) {
                var postForm = document.createElement("form");
                postForm.action = url;
                postForm.method = post || "post";
                postForm.target = 'blank';
                postForm.style.display = "none";
                for (var x in params) {
                    var opt = document.createElement("input");
                    opt.name = x;
                    opt.value = params[x];
                    postForm.appendChild(opt);
                }
                document.body.appendChild(postForm);
                postForm.submit();
                return postForm;
            }
        })
        .service('saafLoading', function () {
            var loading = $("#saafLoading");
            if (loading.length === 0) {
                var loading = $('<div id="saafLoading"><div  style="position: absolute; top:50%;left:50%; text-align: center;margin: 0 auto; width:100px;"><img src="img/loading1.gif"></div></div>');
                loading.css({
                    position: "fixed",
                    top: 0,
                    width: "100%",
                    "z-index": 9000,
                    "height": "100%",
                    'display': 'none'
                });
                loading.prependTo(angular.element('body'));
            } else {
                loading.removeAttr('data-loading');
            }
            return loading;
        })
        /**
         *获取两个时间之间的年份差
         */
        .service('poorYear', function () {
            return function (startDate,endDate, diffDay) {
                if (!diffDay) {
                    diffDay = 0;
                }
                //第一种方式:
                let startTime = new Date(startDate);
                let startYear = startTime.getFullYear();
                let startMonth = startTime.getMonth() + 1;
                let startDay = startTime.getDate();
                console.log(startDay);
                let endTime = new Date(endDate);
                endTime =new Date(endTime.setDate(endTime.getDate()+ diffDay));//加减某一天
                let currentYear = endTime.getFullYear();
                let currentMonth = endTime.getMonth() + 1;
                let currentDatDay = endTime.getDate();

                var years = 0;//声明一个年数变量
                var months = currentMonth - startMonth + (currentYear - startYear) * 12;//总月
                if (currentMonth * 100 + currentDatDay < startMonth * 100 + startDay) {
                    months--;//如果结束日期小于输入日期，月数要-1
                }
                years = Math.floor(months / 12);//取整计算年数

                //第二种方式:
              /*  var inputStartTime = new Date(startDate);
                var inputEndTime = new Date(endDate);
                //总秒数
                var millisecond = Math.floor((inputEndTime.getTime() - inputStartTime.getTime()) / 1000);
                //总天数
                var allDay = Math.floor(millisecond/(24*60*60));
                var inputStartYear = inputStartTime.getFullYear();
                var inputEndYear = inputEndTime.getFullYear();

                //判断是否是闰年
                function isLeapYear(year){
                    if((year % 4 == 0 && year % 100 != 0)||( year % 100 == 0 && year % 400 ==0)){
                        return true;
                    }
                    return false;
                }
                //闰年个数
                var leapYear = 0;
                for(var i= inputStartYear;i < inputEndYear;i++){
                    if(isLeapYear(i)){
                        leapYear++;
                    }
                }
                //年数
                var years = Math.floor((allDay - leapYear * 366) / 365 + leapYear);*/
                return years;
            }
        })
        /**
         * 普通table 标签 html转换成excel，然后导出excel
         * @param tableId  table表格id
         * @param excelName 导出excel名
         * 使用示例：
         *     var ttaDeptFeeLFindReportTableId = document.querySelector("#ttaDeptFeeLFindReportTableId");
         *     var excelName = "贸易协议_" +$scope.params.proposalNbr + ".xlsx";*
         *    tableHtmlToExcelExport(ttaDeptFeeLFindReportTableId,excelName);
         *注意：要js文件中引入XLSX
         */
        .service('tableHtmlToExcelExport', function (httpServer, URLAPI, SIEJS, $rootScope) {
           return function (tableId,excelName) {
               function table2csv(table) {
                   var csv = [];
                   $(table).find('tr').each(function() {
                       var temp = [];
                       $(this).find('td').each(function() {
                           console.log($(this).html());
                           temp.push($(this).text());
                       });
                       //temp.shift(); // 移除第一个
                       csv.push(temp.join('@'));
                   });
                   //csv.shift();
                   return csv.join('\n');
               }

               // csv转sheet对象
               function csv2sheet(csv) {
                   var sheet = {}; // 将要生成的sheet
                   csv = csv.split('\n');
                   csv.forEach(function(row, i) {
                       row = row.split('@');
                       if(i == 0) sheet['!ref'] = 'A1:'+String.fromCharCode(65+row.length-1)+(csv.length-1);
                       row.forEach(function(col, j) {
                           sheet[String.fromCharCode(65+j)+(i+1)] = {v: col};
                       });
                   });
                   return sheet;
               }

               // 将一个sheet转成最终的excel文件的blob对象，然后利用URL.createObjectURL下载
               function sheet2blob(sheet, sheetName) {
                   sheetName = sheetName || 'sheet1';
                   var workbook = {
                       SheetNames: [sheetName],
                       Sheets: {}
                   };
                   workbook.Sheets[sheetName] = sheet;
                   // 生成excel的配置项
                   var wopts = {
                       bookType: 'xlsx', // 要生成的文件类型
                       bookSST: false, // 是否生成Shared String Table，官方解释是，如果开启生成速度会下降，但在低版本IOS设备上有更好的兼容性
                       type: 'binary'
                   };
                   var wbout = XLSX.write(workbook, wopts);
                   var blob = new Blob([s2ab(wbout)], {type:"application/octet-stream"});
                   // 字符串转ArrayBuffer
                   function s2ab(s) {
                       var buf = new ArrayBuffer(s.length);
                       var view = new Uint8Array(buf);
                       for (var i=0; i!=s.length; ++i) view[i] = s.charCodeAt(i) & 0xFF;
                       return buf;
                   }
                   return blob;
               }

               /**
                * 通用的打开下载对话框方法，没有测试过具体兼容性
                * @param url 下载地址，也可以是一个blob对象，必选
                * @param saveName 保存文件名，可选
                */
               function openDownloadDialog(url, saveName) {
                   if(typeof url == 'object' && url instanceof Blob) {
                       url = URL.createObjectURL(url); // 创建blob地址
                   }
                   var aLink = document.createElement('a');
                   aLink.href = url;
                   aLink.download = saveName || ''; // HTML5新增的属性，指定保存文件名，可以不要后缀，注意，file:///模式下不会生效
                   var event;
                   if(window.MouseEvent) event = new MouseEvent('click');
                   else {
                       event = document.createEvent('MouseEvents');
                       event.initMouseEvent('click', true, false, window, 0, 0, 0, 0, 0, false, false, false, false, 0, null);
                   }
                   aLink.dispatchEvent(event);
               }

               var csv = table2csv(tableId);
               var sheet = csv2sheet(csv);
               var blob = sheet2blob(sheet);
               openDownloadDialog(blob,excelName);
           }
        })
        /**
         * json转换为excel，如下为使用例子：
         *          var sheetName = {
         *             name: '姓名',
         *             age: '年龄',
         *             sex: '性别'
         *          };
         *
         *       var datas = [
         *                {"name": "路飞", "age": "100", "sex": "男"},
         *                {"name": "女帝", "age": "30", "sex": "女"},
         *                {"name": "娜美", "age": "30", "sex": "女"},
         *                {"name": "索隆", "age": "solo", "sex": "男"},
         *              ];
         *
         *      var workbook = jsonToExcel.toSheet({
         *      sheetName: sheetName,
         *      datas: datas
         *     });
         *     jsonToExcel.downloadExl(workbook,"贸易协议_" + $scope.params.orderNbr);
         */
        .service('jsonToExcel', function (httpServer, URLAPI, SIEJS, $rootScope) {
            // 下载excel方法 start
            function downloadExl(data,fileName, type) {
                const wopts = {bookType: 'xlsx', bookSST: false, type: 'binary'};//这里的数据是用来定义导出的格式类型
                // const wopts = { bookType: 'csv', bookSST: false, type: 'binary' };//ods格式
                // const wopts = { bookType: 'ods', bookSST: false, type: 'binary' };//ods格式
                // const wopts = { bookType: 'xlsb', bookSST: false, type: 'binary' };//xlsb格式
                // const wopts = { bookType: 'fods', bookSST: false, type: 'binary' };//fods格式
                // const wopts = { bookType: 'biff2', bookSST: false, type: 'binary' };//xls格式

                var wb = {SheetNames: ['Sheet1'], Sheets: {}, Props: {}};
                //wb.Sheets['Sheet1'] = XLSX.utils.json_to_sheet(data);//通过json_to_sheet转成单页(Sheet)数据
                var keys = Object.keys(data[0]);
                var firstRow = {};
                keys.forEach(function (item) {
                    firstRow[item] = item;
                });
                data.unshift(firstRow);


                // 把json格式的数据转为excel的行列形式
                var sheetsData = data.map(function (item, rowIndex) {
                    return keys.map(function (key, columnIndex) {
                        return Object.assign({}, {
                            value: item[key],
                            position: (columnIndex > 25 ? getCharCol(columnIndex) : String.fromCharCode(65 + columnIndex)) + (rowIndex + 1),
                        });
                    });
                }).reduce(function (prev, next) {
                    return prev.concat(next);
                });

                var content = {};
                sheetsData.forEach(function (item, index) {
                    content[item.position] = { v: item.value };
                });

                //设置区域,比如表格从A1到D10
                var coordinate = Object.keys(content);
                //合并对象
                wb.Sheets['Sheet1'] = Object.assign({}, content, { "!ref": coordinate[0] + ":" + coordinate[coordinate.length - 1] });
                saveAs(new Blob([s2ab(XLSX.write(wb, wopts))], {type: "application/octet-stream"}), fileName + '.' + (wopts.bookType == "biff2" ? "xls" : wopts.bookType));
            }

            function s2ab(s) {
                if (typeof ArrayBuffer !== 'undefined') {
                    var buf = new ArrayBuffer(s.length);
                    var view = new Uint8Array(buf);
                    for (var i = 0; i != s.length; ++i) view[i] = s.charCodeAt(i) & 0xFF;
                    return buf;
                } else {
                    var buf = new Array(s.length);
                    for (var i = 0; i != s.length; ++i) buf[i] = s.charCodeAt(i) & 0xFF;
                    return buf;
                }
            }

            //如果不能下载，那么清注释saveAs方法，第八行恢复FileSaver.js的引入
            function saveAs(obj, fileName) {    //当然可以自定义简单的下载文件实现方式
                var hrefDom = document.createElement("a");
                hrefDom.download = fileName || '下载.xls';
                hrefDom.href = URL.createObjectURL(obj);    //绑定a标签
                hrefDom.style.display = "none";
                document.body.appendChild(hrefDom);
                hrefDom.click(); //模拟点击实现下载
                document.body.removeChild(hrefDom);
                var set = setTimeout(function () { //延时释放
                    URL.revokeObjectURL(obj); //用URL.revokeObjectURL()来释放这个object URL
                    clearTimeout(set);  //释放内存
                    set = null;
                }, 100);
            }

            // 下载excel方法 end

            // util start
            /*
            * 将json数据转成sheet数据
            * */
            function toSheet(params) {
                if (!params) return [];
                var sheetName = params.sheetName;
                var datas = params.datas;

                var _datas = [];
                for (var i = 0; i < datas.length; i++) {
                    var data = datas[i];
                    var _data = {};
                    for (var key in sheetName) {
                        _data[sheetName[key]] = data[key]
                    }
                    _datas.push(_data);
                }
                return _datas;
            }
            /*
            * 将sheet数据转成json数据
            * */
            function toJson(params) {
                if (!params) return [];
                var sheetName = params.sheetName;
                var datas = params.datas;

                var i = 0, length = datas.length, data;
                var _datas = [];
                for (i; i < length; i++) {
                    data = datas[i];
                    var key;
                    var _data = {};
                    for (key in sheetName) {
                        _data[key] = data[sheetName[key]]
                    }
                    _datas.push(_data);
                }
                return _datas;
            }

            // 将指定的自然数转换为26进制表示。映射关系：[0-25] -> [A-Z]。
            function getCharCol(n) {
                var  temCol = "",
                    s = "",
                    m = 0;
                while (n > 0) {
                    m = n % 26 + 1;
                    s = String.fromCharCode(m + 64) + s;
                    n = (n - m) / 26
                }
                return s
            }

            // util end
            return {
                downloadExl: downloadExl,
                toSheet: toSheet,
                toJson: toJson
                // s2ab: s2ab,
                // saveAs: saveAs,
            };
        })
    ;


    //**************************************   过滤器

    //输出正确的html 非转义
    module.filter('tohtml', function ($sce) {
        return function (text) {
            return $sce.trustAsHtml(text);
        }
    });

    //表达式维护：维度为值集时隐藏<两者之间>
    module.filter('hideBetween', function () {
        return function (input, valueType) {
            var arr = [];
            if ('valueSet' == valueType) {
                arr = input.slice();
                for (var i = 0; i < input.length; i++) {
                    if ('between##and' == input[i].operatorCode) {
                        arr.splice(i, 1);
                        return arr;
                    }
                }
            } else {
                return input.slice();
            }
        }
    });

    //流程表单的公用视图
    module.directive('processFormView', function () {
        return {
            restrict: 'EA',
            replace: true,
            scope: {
              urlParams: '=urlParams',
              renderFlowchartFn: '&renderFlowchartFn' // 渲染流程图的方法
            },
            transclude: true,
            templateUrl: 'js/directives/html/process-form-view.html',
            link: function (scope, elem, attr) {
              // var id = $(elem).attr('id');
              scope.formTitle = attr.formTitle ? attr.formTitle : '表单信息';

              scope.processTabAction = 'tabForm';
              scope.processTabChange = function (name) { // 流程图选项卡切换
                scope.processTabAction = name;
                if (name === 'tabFlowchart') {
                  scope.renderFlowchartFn();
                }
              };

              // 流程图放大缩小功能
              if (undefined === scope.zoom) {
                scope.zoomNum = 1;
                scope.zoom = function (str) {
                  if (str === 'zoom') {
                    if (scope.zoomNum <= 0.2) {
                      return;
                    }
                    scope.zoomNum = scope.zoomNum - 0.1;
                  }

                  if (str === 'enlarge') {
                    if (scope.zoomNum > 1.5) {
                      return;
                    }
                    scope.zoomNum = scope.zoomNum + 0.1;
                  }

                  if (str === 'reduction') {
                    scope.zoomNum = 1;
                  }

                  $('.GooFlow_work_inner').css({
                    '-webkit-transform': 'scale(' + scope.zoomNum + ')',
                    '-moz-transform': 'scale(' + scope.zoomNum + ')',
                    '-ms-transform': 'scale(' + scope.zoomNum + ')',
                    '-o-transform': 'scale(' + scope.zoomNum + ')',
                    'transform': 'scale(' + scope.zoomNum + ')'
                  });
                };
              }
            }
        };
    });

    // 流程图在表单应用的基础控制器
    module.controller('processBase', function ($scope,$stateParams, $filter,$timeout, SIEJS, httpServer, URLAPI) {
        // 通过字符串URL转换为对象
        $scope.urlToObj = function (url) {
          var index = url.lastIndexOf('?');
          var params = url.substring(index+1);

          var arr = params.split('&');
          var obj = {};
          arr.forEach(function (item) {
            obj[item.split('=')[0]] = item.split('=')[1];
          });
          return obj;
        };

        // 获取主键值
        $scope.getPkVal = function () {
          return $scope.urlParams.businessKey ? $scope.urlParams.businessKey : $scope.id;
        };

        // 获取表单主键ID，如果获取不到则采用流程的ID
        $scope.getId = function () {
          if ($stateParams.id){
            return $stateParams.id;
          } else if ($scope.urlParams.businessKey){
            return $scope.urlParams.businessKey;
          }
        };

        // 获取流程信息
        $scope.getProcessInfo = function (callback) {
            httpServer.post(URLAPI.processGet, {
                'params': JSON.stringify({
                  processDefinitionKey: $scope.processImageParams.key,
                  businessKey:$scope.id
                })
              },
              function (res) {
                if (res.status === 'S') {
                  $scope.processImageParams.instanceId = res.data.processInstanceId;
                }
                callback && callback(res);
              }
            );
        };

        // 设置流程图参数
        $scope.setProcessImageParams = function (obj) {
            angular.forEach(obj, function (value, key) {
              $scope.processImageParams[key] = value;
            });
        };

        // 渲染流程图
        $scope.renderFlowchart = function () {
          if (!$scope.processImgLoad) {
            $scope.getProcessInfo(function () {
              var p = $scope.processImageParams;
              $timeout(function () {
                processImageDraw(p.token, p.id, p.instanceId, p.key); //  绘制流程图
                $scope.processImgLoad = true;
              }, 100)
            });
          }
        };

        // 表单页审批操作后的回调方法
        $scope.TJSP = function(title, formParams, callback){
            title = title || '生产单OMS审批流程';
            $scope.extend ={
              "tasks_assignees":[]
            };
            $scope.variables = [];
            angular.forEach(formParams, function (value, key) {
              $scope.variables.push({
                name: key,
                value: value,
                type: 'string'
              });
            });
            $scope.properties={
              "menucode": "HTGL",
              "opinion": ""
            };
            $scope.paramsBpm={
              "extend":$scope.extend,
              "variables":$scope.variables,
              "properties":$scope.properties,
              "responsibilityId": "990021",
              "respId": "990021",
              "processDefinitionKey": $scope.processImageParams.key,
              "saveonly": false,
              "businessKey": formParams.headerId,
              "title": title + formParams.orderCode,
              "positionId": 0,
              "orgId": 0,
              "userType": "20",
              "billNo": formParams.orderCode
            };
            httpServer.post(URLAPI.bpmStart, {
              'params': JSON.stringify($scope.paramsBpm)
            }, function (res) {
              if (res.status === 'S') {
                SIEJS.alert("提交成功", "success", "确定");
                callback && callback();
                // $scope.search();
              } else {
                SIEJS.alert(res.msg, "error", "确定");
              }
            }, function (err) {
              SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
            });
        };

        // 把查询的数据赋值给formParams
        $scope.setFormParams = function(params){
          angular.forEach(params, function (value, key) {
            $scope.formParams[key] = value;
          });
        };

        // 从流程管理打开会携带一大串URL参数可供使用
        $scope.urlParams = $scope.urlToObj(location.hash);
        // 流程图默认参数
        $scope.processImageParams = {
            token: sessionStorage.getItem(window.appName + '_certificate')||localStorage.getItem(window.appName + '_certificate'),
            id: 'processImg',
            instanceId: $scope.urlParams.processInstanceId,
            key: 'PICKINGORDER_OMS.-999'
        };
    });





    return module;
});
