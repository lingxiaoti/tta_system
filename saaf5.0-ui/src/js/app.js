/**
 * 建立angular.module
 */

define(['exports', 'module', 'angular',
        'jquery',
        'angular-async-loader',
        'angular-ui-router',
        'SAAF.URLAPI',
        'SIE.Service',
        'http.Service',
        'bw.paging',
        'SAAF.Directives',
        'SAAF.DirectivesForCommon',
        'SAAF.filters',
        'angular-nicescroll',
        'datetimepicker',
        'EMS.ControlDirectives',
        'bootstrap-colorpicker',
        'imgPreview',
        'ngWebsocket',
        'iframe.Services'

    ],
    function (exports, module, angular, $, asyncLoader) {

        var app = angular.module('SAAFAPP', ['http.Service', 'ui.router', 'bw.paging', 'SAAF.Directives','iframe.Services', 'SAAF.URLAPI', 'SIE.Services', 'angular-nicescroll', 'SAAF.DirectivesForCommon', 'SAAF.filters', 'EMS.ControlDirectives', 'ngWebSocket']);

        app
            .run(function ($rootScope, $location, $stateParams, $state, $timeout, iframeTabAction, Base64, saveTabToStorage, lookupCode) {
                $rootScope.appName = window.appName;
                $rootScope.niceOpction={cursorcolor: '#1e90c2',cursorwidth:10,zindex:10}
                $rootScope.appTitle = window.appTitle;
                $rootScope.$state = $state;
                $rootScope.$stateParams = $stateParams;
                $rootScope.$copy = angular.copy;
                $rootScope.$getToday = function (addDay, addMonth) {
                    function addDate(dd, dadd) {
                        var a = new Date(dd);
                        a = a.valueOf();
                        a = a + dadd * 24 * 60 * 60 * 1000;
                        a = new Date(a);
                        return a;
                    }

                    var date = new Date();
                    if (addDay) {
                        date = addDate(date, addDay);
                    }
                    var mon = date.getMonth() + 1;
                    var day = date.getDate();
                    var year = date.getFullYear();
                    if (addMonth) {
                        mon += addMonth;
                        if (mon > 12) {
                            year++;
                            mon -= 12;
                        } else if (mon < 1) {
                            year--;
                            mon += 12;
                        }
                    }

                    return year + "-" + (mon < 10 ? "0" + mon : mon) + "-" + (day < 10 ? "0" + day : day);
                };
                window.saafrootScope = $rootScope;//将$rootScope放到全局变量，方便其他页面和js调用
                // 通用页面跳转
                $rootScope.goto = function (name, url, id) {
                    iframeTabAction(name, url, id);
                };
                // 删除tab
                window.deleteHeadTab = function (activeId) {
                    var index;
                    var activeIndex = -1;
                    var len = $rootScope.saafHeadTab.length;
                    for (var n = 0; n < len; n++) {
                        var tab = $rootScope.saafHeadTab[n];
                        if (tab.active === true) {
                            index = n;
                        }
                        if (activeId && tab.id === activeId && tab.isParent) {
                            activeIndex = n;
                        }
                    }
                    if (activeIndex > -1) {
                        $rootScope.saafHeadTab[activeIndex].active = true;
                    } else if (index === 0 && len > 1) {
                        $rootScope.saafHeadTab[1].active = true;
                    } else if (index > 0 && len > 0) {
                        $rootScope.saafHeadTab[index - 1].active = true;
                    } else {
                        // 第一个tab被删除，显示首页
                    }
                    $rootScope.saafHeadTab.splice(index, 1);
                    saveTabToStorage();// 保存菜单
                    $rootScope.$apply();
                };
                $(document).keydown(function (event) {
                    //  Ctrl + Q 删除当前 Tab
                    if (event.ctrlKey && event.keyCode === 81) {
                        if (window.parent != window) { // 当前为 iframe
                            window.parent.deleteHeadTab();
                        } else {
                            window.deleteHeadTab();
                        }
                    }
                });

                /* window.onbeforeunload = function (event) {
                 console.log('刷新或离开系统')
                 return false;
                 }*/

                /*多语言*/
                $rootScope.changLanguage = function (lang) {
                    localStorage[appName + '_language'] = lang;
                    window.location.reload();
                };
                var langPackage;
                switch (localStorage[appName + '_language'] || 'CN') {
                    case "EN":
                        langPackage = 'lang/en';
                        break;
                    default :
                        langPackage = 'CN';
                        break;
                }

                if (langPackage === 'CN') {
                    $rootScope.$l = function (s) {
                        return s;
                    }
                } else {
                    // 加载缓存的本地语言包
                    if (localStorage[appName + '_language'] && localStorage[appName + '_langPackage']) {
                        var lang = JSON.parse(Base64.decode(localStorage[appName + '_langPackage']));// 语言包
                        $rootScope.$l = function (s) {
                            return lang[s] || s;
                        };
                    } else {
                        // 加载语言包
                        require([langPackage], function (lang) {
                            $rootScope.$l = function (s) {
                                return lang[s] || s;
                            };
                            localStorage[appName + '_langPackage'] = Base64.encode(JSON.stringify(lang));// 缓存语言包
                        });
                    }
                }

            })
            .run(function ($rootScope, $location, systemJump, arrayFindObj, clearStore,SIEJS) {

                if ($location.$$path === '/' + window.appName.toLowerCase() + '/entrance') {
                    // 其它入口 ，如邮件链接
                    if ( localStorage[appName + '_successLoginInfo']) {
                        sessionStorage[appName + '_successLoginInfo']=localStorage[appName + '_successLoginInfo'];//
                    }
                }
                if (sessionStorage.successLoginInfo) {
                    //log('处理' + sessionStorage.successLoginInfo)
                    localStorage.successLoginInfo = sessionStorage.successLoginInfo;
                    sessionStorage.removeItem('successLoginInfo');//
                }
                // localStorage.successLoginInfo 这个是入口首页（门户）登录成功的信息
                if (localStorage.successLoginInfo || (sessionStorage[appName + '_successLoginInfo'] && localStorage[appName + '_successLoginInfo'])) {
                    if (localStorage.successLoginInfo && !sessionStorage[appName + '_successLoginInfo']) {
                        localStorage[appName + '_successLoginInfo'] = sessionStorage[appName + '_successLoginInfo'] = localStorage.successLoginInfo;

                    }

                    if (sessionStorage.certificate && !sessionStorage[appName + '_certificate']) {
                        sessionStorage[appName + '_certificate'] = sessionStorage.certificate;
                        localStorage[appName + '_certificate'] = sessionStorage.certificate;
                    }

                    var obj = JSON.parse(sessionStorage[appName + '_successLoginInfo']);


                    if (obj.isadmin === 'Y') {
                        $rootScope.userRespList = [{
                            responsibilityName: '超级管理员',
                            responsibilityId: -10000,
                            isAdmin: true
                        }];
                    } else {
                        $rootScope.userRespList = arrayFindObj(obj.userRespList, appName, 'systemCode', true);
                    }

                    if (localStorage[appName + '_currentResp']) { // 已经存在当前职责
                        $rootScope.currentResp = JSON.parse(localStorage[appName + '_currentResp']);//
                        if (arrayFindObj($rootScope.userRespList, $rootScope.currentResp.responsibilityId, 'responsibilityId')) {
                            return;
                        }
                    }
                    $rootScope.currentResp = $rootScope.userRespList[0];
                    if (!$rootScope.currentResp){
                        SIEJS.alert('您没有当前系统访问权限，请联系管理员','warning');

                        return;
                    }
                    localStorage[appName + '_currentResp'] = JSON.stringify($rootScope.currentResp);
                } else {
                    clearStore();
                    systemJump();
                }
            })
            .run(
                function ($rootScope) {

                    $rootScope.$watch(function () {
                            return $(window).height();
                        },
                        function (newValue) {
                            $rootScope.saafWindowHeight = newValue;
                        });
                    $rootScope.$watch(function () {
                            return $(window).width();
                        },
                        function (newValue) {
                            $rootScope.saafWindowWidth = newValue;
                        });


                })
            //路由跳转模块
            .run(function ($rootScope, $timeout, $interval, SIEJS, $state, $sce, ObjectToValueKey, tabAction, Base64) {
                //require('contextify')
                $rootScope.$on('$stateChangeStart', function (event, toState, toParams, fromState, fromParams) {
                    if (window.parent != window) { // 子级页面 iframe
                        //log('严重等级    ★★★★★' + toState.url)
                        if (toState.url === '/' + appName.toLowerCase() + '/login') {
                            if (localStorage.successLoginInfo || (sessionStorage[appName + '_successLoginInfo'] && localStorage[appName + '_successLoginInfo'])) {
                                //  严重等级    ★★★★★
                                // 处理网络加载文件失败，重载页面到login的问题，此问题导致tab里的iframe包含着首页
                                $timeout(function () {
                                    var obj = window.parent.saafrootScope.saafHeadTab;// 父窗的saafHeadTab
                                    var index = -1; // 当前激活状态的 tab
                                    for (var n = 0; n < obj.length; n++) {
                                        if (obj[n].active === true) {
                                            index = n;
                                            break;
                                        }
                                    }
                                    if (index === -1)return;
                                    var url = window.parent.$("iframe")[index].src;// 获得当前iframe的　src
                                    window.location.href = url;
                                    log('加载iframe页面失败，正在重载当前iframe SRC', url);
                                })
                                event.preventDefault();
                            } else {
                                window.parent.location.href = '#/' + appName.toLowerCase() + '/login';
                            }
                        }
                    }
                })
            })
            .run(function ($rootScope, $timeout, $interval, $location, httpServer, URLAPI) {
                $rootScope.$on('$stateChangeSuccess', function (event, toState, toParams, fromState, fromParams) {
                    $rootScope.URL = {
                        from: fromState,
                        fromParams: fromParams,
                        to: toState,
                        toParams: toParams
                    };

                    // 获取当前页面的按钮权限
                    var menuId = $location.$$search.id;
                    if (!menuId)return;
                    if (!localStorage[appName + '_currentResp']) return;
                    var resp = JSON.parse(localStorage[appName + '_currentResp']);
                    var p = {
                        menuId: menuId,
                        respId: resp.responsibilityId
                    };

                    // 获取当前页面的权限按钮
                    httpServer.post(URLAPI.resourceByRespMenuId, {params: JSON.stringify(p)}, function (res) {
                        $rootScope.permission = res.data;

                    }, function (res) {
                    }, 'false');


                })
            });

        asyncLoader.configure(app);
        module.exports = app;
        //return app
    });


