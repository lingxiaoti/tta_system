'use strict';
define(["app", 'hilink_softphone','webconfig'], function (app, hilink_softphone,webconfig) {
    //app.useModule('ng.ueditor');//按需加载编辑器

    //app.useModule('ngDialog');//按需加载弹出层模块

    app.controller('mainCtrl', function ($interval, saveTabToStorage, changePassword, gotoLogin, $rootScope, $scope, httpServer, Cookies, iframeMessage,
                                         arrayFindObj, arrayDeleteItem, $compile, clearStore, systemJump, lookupCode, findUserInfo, SIEJS, URLAPI, $state,
                                         tabAction, Base64, Fullscreen, $http, $location, $stateParams, iframeTabAction, $timeout, $window, logout,$filter,processGetTaskUrl, processGetStartUrl) {


        //从sessionStorage中取出登录信息
        if (sessionStorage.successLoginInfo) {

            $scope.userData = JSON.parse(sessionStorage.successLoginInfo);
            $scope.userInfo = $scope.userData;
            //用户职责
            $scope.userRespList = $scope.userData.userRespList;
        }

        //获取orgId参数
        $scope.userData = JSON.parse(localStorage.getItem(appName + '_successLoginInfo'));
        $scope.positionList = JSON.parse(localStorage.getItem(appName + '_successLoginInfo')).positionList;
        //申請人
        if ($scope.userData.userRespList.length > 0) {
            // 默认取当前系统的第一个配置包含有orgId 的 值
            for (var n = 0; n < $scope.userData.userRespList.length; n++) {
                var item = $scope.userData.userRespList[n];
                if (item.systemCode.toLocaleUpperCase() === window.appName.toLocaleUpperCase() && item.orgBean) {
                    $rootScope.orgId = window.orgId = item.orgBean.orgId || -1;
                    break;
                }
            }
        }

        if (!$rootScope.orgId) {
            $rootScope.orgId = window.orgId = -1;// 没有配置orgId 将返回-1
            console.warn('没有配置orgId,将设置为 -1');
        }

        $scope.meunData = {};
        $scope.menuModal = {};

        $rootScope.userName = JSON.parse(sessionStorage[appName + '_successLoginInfo']).userName; // 重新获取userName，预防页面刷新丢失
        $scope.language = localStorage[appName + '_language'] || '';
        if (!sessionStorage[appName + '_successLoginInfo']) {

            if (window.isEmployeePortal === 2) {
                clearStore();
                //EP正式环境
                //window.location.href = "http://wtccn-gz-ept/Portal/Login.aspx";
                //window.location.href = "http://wtccn-watsonsportal:2020/wui/index.html#/?logintype=1&_key=7qlz7s";
                //EP测试环境
                //window.location.href = "http://wtccn-vm-sdst/portal/login.aspx";
                window.location.href = webconfig.jumpUrl;
                log('系统超时退出了');
                return
            }

            clearStore();
            systemJump();
            return;
        } else {

            /* // 处理URL portal 中转过来时带来的敏感字符。
             var w = window.location;
             if(w.search.length>2){
             w.href = w.origin + w.pathname + '#/' + appName.toLowerCase() + '/main';
             }*/
        }
        /*   if(!$rootScope.currentResp){
         SIEJS.alert('您没有当前系统访问权限，请联系管理员','warning');
         gotoLogin(true);　// 退出当前系统
         return;
         }*/
        var startTime = new Date();

        // 检测是否登录 或更换用户名。。 建议webSocket 协议 ★★★★★★★★★★★★★
        $scope.interval = $interval(function () {
            if (!sessionStorage[appName + '_successLoginInfo'] || !localStorage[appName + '_successLoginInfo'] || (!Cookies.getCookie(window.appName + '_sessionTime') && !localStorage[window.appName + '_autoLogin'])) {
                if (window.isEmployeePortal === 2) {
                    clearStore();
                    //var errors = encodeURIComponent('登录信息已失效,系统超时退出');
                    //$state.go('error',{msg:errors});
                    //EP正式环境
                    //window.location.href = "http://wtccn-gz-ept/Portal/Login.aspx";
                    //window.location.href = "http://wtccn-watsonsportal:2020/wui/index.html#/?logintype=1&_key=7qlz7s";
                    //EP测试环境
                    //window.location.href = "http://wtccn-vm-sdst/portal/login.aspx";
                    window.location.href = webconfig.jumpUrl;
                    log('系统超时退出了');
                    return
                }
                clearStore();
                systemJump();
                log('系统超时退出了');
                return;

            } else if(JSON.parse(sessionStorage[appName + '_successLoginInfo']).userType==='60'){

                httpServer.post(URLAPI.findUserInfo, {}, function (res) {
                    if(res.status==='E'){
                        SIEJS.alert(res.msg,'error','确定');
                        clearStore();
                        systemJump();
                        log('系统超时退出了');

                    }
                }, console.log('error'), false);
            }
            else {
                console.log(appTitle + '正在运行...');
            }
            if (undefined !== localStorage.lastUserName && localStorage.lastUserName.toUpperCase() != $rootScope.userName.toUpperCase()) {
                clearStore(); // 退出当前系统
                console.log('用户更改为' + localStorage.lastUserName + '，系统退出');
                //$window.location.reload();
            }
        }, 3000);


        function runtime(startTime) {
            var endTime = startTime;
            var nowTime = new Date();
            var t = nowTime.getTime() - endTime.getTime();

            var d = Math.floor(t / 1000 / 60 / 60 / 24);
            var h = Math.floor(t / 1000 / 60 / 60 % 24);
            var m = Math.floor(t / 1000 / 60 % 60);
            var s = Math.floor(t / 1000 % 60);
            var month = Math.ceil(d / 30);
            var year = Math.ceil(d / 365);

            var times = '';

            if (year) times = year + '天';
            if (month) times += month + '月';
            if (d) times += d + '日　';
            if (h) times += h + '时';
            if (m) times += m + '分';
            if (s) times += s + '秒';

            return times;

        }

        // 获取块码
        lookupCode(function (res) {
            $rootScope.lookupCode = res.data;
            sessionStorage[appName + '_lookupCode'] = JSON.stringify($rootScope.lookupCode);
        });

        $scope.timer = (new Date()).getTime();


        //全屏按钮
        $scope.full = function (e) {
            Fullscreen.fullChange(e);
        };

        if (window.parent == window) {
            var newValue = $rootScope.saafWindowWidth;
            if (newValue < 1110) {
                $('body').addClass('shrinkage-app');
            } else {
                $('body').removeClass('shrinkage-app');
            }
        }

        // tabs切换激活项
        $scope.cutTabs = function (item) {
            if ($rootScope.saafHeadTab) {
                $.each($rootScope.saafHeadTab, function (n, row) {
                    row.active = false;
                })
            }
            item.active = true;
            saveTabToStorage();// 保存菜单
        };
        $scope.deleteTabs = function (item, index) {
            if(item.active&&item.url.indexOf("plmProjectDetail")>-1){
                iframeMessage.run('plmProjectDetail_check');
                return;
            } else if(item.active&&item.url.indexOf('plmDevelopmentDetail')>-1){
                iframeMessage.run('plmDevelopmentDetail_check');
                return;
            } else if(item.active&&item.url.indexOf('plmProductExceptionDetail')>-1){
                iframeMessage.run('plmProductExceptionDetail_check');
                return;
            }
            if (item.active) {
                if (index > 0) {
                    $rootScope.saafHeadTab[index - 1].active = true;
                } else {
                    $scope.showMain();// 第一个tab被删除，显示首页
                }
            }
            $rootScope.saafHeadTab.splice(index, 1);
            saveTabToStorage();// 保存菜单
        };

        //是否显示主页
        $scope.mainPageShow = function () {
            var flag = true;
            if ($rootScope.saafHeadTab) {
                $.each($rootScope.saafHeadTab, function (n, row) {
                    if (row.active) {
                        flag = false;
                    }
                })
            }
            return flag;
        };
        //显示主页
        $scope.showMain = function () {
            var flag = true;
            if ($rootScope.saafHeadTab) {
                $.each($rootScope.saafHeadTab, function (n, row) {
                    row.active = false;
                })
            }
        };

        //$scope.logout = logout;    // 调接口退出所有系统
        $scope.logout = function () {
            SIEJS.confirm('您确定要退出当前系统吗？', '', '确定退出', function () {
                gotoLogin(true);　// 退出当前系统
            })
        };
        var rightMenu = '<div id="rightMenuBox" ng-right-click="showRightMenu=false" ng-click="showRightMenu=false" style=" z-index: 100;position: fixed; top:0;left:0;right:0;bottom:0;  " ng-show="showRightMenu">' +
            '<ul class="rightMenu" style="position: absolute; top:0; left:0px; white-space:nowrap;" ng-style="rightStyle" >' +
            '<li><a href="javascript:" ng-click="saveToHome()" ng-show="currentTab.isParent"><i class="fa fa-home mr5"></i>添加至主页</a></li>' +
            '<li><a href="javascript:" ng-click="reloadIframe()" ><i class="fa fa-repeat mr5"></i>刷新当前标签页</a></li>' +
            '<li class="divider"> </li>' +
            '<li><a href="javascript:"  ng-click="closeTab(\'current\')"><i class="fa fa-trash-o mr5"></i>关闭当前标签页</a></li>' +
            '<li><a href="javascript:"  ng-click="closeTab(\'other\')"><i class="fa fa-trash mr5"></i>关闭其它标签页</a></li>' +
            '<li><a href="javascript:"  ng-click="closeTab(\'all\')"><i class="fa fa-remove mr5"></i>关闭全部标签页</a></li>' +
            '</ul></div>';

        $('body').append($compile(rightMenu)($scope));


        // 右键事件
        $scope.rightClick = function (item, index, event) {
            $scope.currentTab = item;
            $scope.currentTab.index = index;
            $scope.cutTabs(item);
            var e = event || window.event;
            var scrollX = document.documentElement.scrollLeft || document.body.scrollLeft;
            var scrollY = document.documentElement.scrollTop || document.body.scrollTop;
            var x = e.pageX || e.clientX + scrollX;
            var y = e.pageY || e.clientY + scrollY;
            $scope.showRightMenu = true;
            $scope.rightStyle = {
                top: y,
                left: x
            }
        };
        // 保存至首页
        $scope.saveToHome = function () {
            $scope.saveQuickMenu($scope.currentTab.id);
        };
        // 刷新Tab页面
        $scope.reloadIframe = function () {
            var id = 'iframe_' + $scope.currentTab.id + '_' + $scope.currentTab.index;
            document.getElementById(id).contentWindow.location.reload(true);
        };
        // 关闭tab
        $scope.closeTab = function (type) {
            var array = $rootScope.saafHeadTab;
            if (type === 'all') {
                $rootScope.saafHeadTab = [];
            } else {
                for (var i = 0; i < array.length; i++) {
                    if (array[i].active === (type === 'current')) {
                        array.splice(i--, 1);
                    }
                }
            }
            saveTabToStorage();// 保存菜单
        };
        //改变用户职责
        $scope.saafChangeUserRespon = function (row) {
            if ($rootScope.currentResp.responsibilityId === row.responsibilityId) return;
            localStorage[appName + '_currentResp'] = JSON.stringify(row);
            $rootScope.currentResp = row;
            $rootScope.saafHeadTab = []; // 清除菜单
            saveTabToStorage();// 保存菜单

            $window.location.reload();
            /* $state.go('main');
             $window.location.reload();*/
        };
        //点击数据全屏，自适应高度
        $scope.heightForIframe = 85;
        $scope.$on("data-on-all-screen", function (event, data) {
            $timeout(function () {
                if (data.openFlag) {
                    $scope.heightForIframe = 0
                } else {
                    $scope.heightForIframe = 85
                }
            }, 0)
        });
        $scope.changePassword = function () {
            var user = JSON.parse(window.sessionStorage[appName + '_successLoginInfo']);


            if ($scope.passwordForm.newPassword != $scope.passwordForm.newPassword2) {
                SIEJS.alert('两次密码输入不一致', "error", "确定");
                return;
            }

            //2019.8.12 注释,因修改密码复杂,改成简单的方式
            var pattern = /^.*(?=.{14,16})(?=.*\d)(?=.*[A-Z]{5,})(?=.*[a-z]{5,})(?=.*[!@#$%^&*?\(\)]{3,}).*$/;
            if(!pattern.test($scope.passwordForm.newPassword)){
                SIEJS.alert('密码长度至少14位，且至少包含1个数字，5个大写字母，5个小写字母，3个特殊符号（!@#$%^&*?）', "error", "确定");
                return;
            }

            var key = CryptoJS.enc.Utf8.parse("abcdefgabcdefg12");
            var op = CryptoJS.enc.Utf8.parse($scope.passwordForm.oldPassword);
            var np = CryptoJS.enc.Utf8.parse($scope.passwordForm.newPassword);
            var ope = CryptoJS.AES.encrypt(op, key, { mode: CryptoJS.mode.ECB, padding: CryptoJS.pad.Pkcs7 });
            var npe = CryptoJS.AES.encrypt(np, key, { mode: CryptoJS.mode.ECB, padding: CryptoJS.pad.Pkcs7 });
            var passwordForm = {
                oldPassword: ope.toString(),
                newPassword: npe.toString(),
                userId: user.userId,
                userName: user.userName,
            };
/*            var passwordForm = {
                oldPassword: Base64.encode($scope.passwordForm.oldPassword),
                newPassword: Base64.encode($scope.passwordForm.newPassword),
                userId: user.userId,
                userName: user.userName,
            }*/

            changePassword({params: JSON.stringify(passwordForm)}, function (res) {
                $('#changePassword').modal('toggle');
                // SIEJS.alertByRes(res);
            })

        };


        //查询自己的待办信息
        $scope.getNeedByself = function (flag) {

            var searchP = {var_inList_state: ["UNREADED", "READED"], var_equal_messageType: 'NEED'};
            httpServer.getData(URLAPI.saafMessagePushServicefindBySelf, 'POST', {
                'params': JSON.stringify(searchP),
                pageRows: 6,
                pageIndex: 1
            }, function (res) {
                $scope.unReadedNeed = {data: res.data, count: res.count};

            }, function () {
                console.log('同步待办失败，是否该页面已经太久没有动作，导致浏览器已经关闭了输出流！！！');
            }, flag == null ? false : flag)
        };
        //查询自己的通知信息
        $scope.getNoticeByself = function (flag) {
            var searchP = {var_equal_state: 'UNREADED', var_equal_messageType: 'NOTICE'};
            httpServer.getData(URLAPI.saafMessagePushServicefindBySelf, 'POST', {
                'params': JSON.stringify(searchP),
                pageRows: 6,
                pageIndex: 1
            }, function (res) {

                $scope.unReadedNotice = {data: res.data, count: res.count};
            }, function () {
                console.log('同步通知消息失败，是否该页面已经太久没有动作，导致浏览器已经关闭了输出流！！！');
            }, flag == null ? false : flag)
        };
        $rootScope.saafMessage = {};
        //刷新通知和待办
        $rootScope.saafMessage.refreshMessage = function (flag) {
            //$scope.getNeedByself(flag); // ---------------------------------------------------------  ---- - -- 惠科 项目注释
            //$scope.getNoticeByself(flag);// ---------------------------------------------------------  ---- - -- 惠科 项目注释
        };
        $timeout(function () {
            $rootScope.saafMessage.refreshMessage();
        }, 1000);
        //设置待办和通知定时
        $interval(function () {
            $rootScope.saafMessage.refreshMessage();
        }, 1000 * 60 * 5);
        //设置待办和通知为已读
        $rootScope.saafMessage.setReaded = function (messagePushId) {
            $timeout(function () {
                httpServer.getData(URLAPI.saafMessagePushServicesetReaded, 'POST', {
                    'params': JSON.stringify({messagePushId: messagePushId}),

                }, function (res) {

                    $rootScope.saafMessage.refreshMessage();
                }, null, false)
            }, 1500);

        };
        var recordFunctionOpenFlag = true;

        $scope.saveRecords = function (name, url) {
            if (recordFunctionOpenFlag) {
                $timeout(function () {
                    httpServer.getData(URLAPI.saafFunctionRecordsServicessave, 'POST', {
                        'params': JSON.stringify({var_page_url: url, var_page_name: name})

                    }, function (res) {
                        if (res.state == 'CLOSE') {
                            recordFunctionOpenFlag = false;
                        }

                    }, null, false)
                }, 3000);

            }

        };

        //保存用户快捷菜单新增
        $scope.lovReturnFunction = function (selectRowData) {
            var data = [];
            $.each(selectRowData, function (index, row) {
                data.push(row.menuId);
            });
            httpServer.getData(URLAPI.shortcutService_save, 'POST', {
                'params': JSON.stringify({data: data, varInputRespId: $rootScope.currentUserRespId})
            }, function (res) {

                $scope.searchShortcut();

            })
        };

        //当前职责与编码
        $scope.currentRespAndSys = {
            respId: $rootScope.currentResp.responsibilityId,
            systemCode: window.appName
        };

        // console.log($rootScope.currentResp.responsibilityId)
        // console.log($scope.currentRespAndSys)
        //查询用户快捷菜单
        $scope.searchShortcut = function () {

            //console.log('惠科项目注释：查询用户快捷菜单－－－－－－－－－')
            // console.log($rootScope.currentResp)

            httpServer.post(URLAPI.findInCollection, {

                params: JSON.stringify({
                    respId: $scope.currentRespAndSys.respId,
                    systemCode: $scope.currentRespAndSys.systemCode
                })
            }, function (res) {

                $scope.userShortcut = res.data;
            }, function (error) {
                console.error(error);

            })
        };
        $scope.searchShortcut();
        $scope.saveQuickMenu = function (id) {
            var menuIds;
            if (id && typeof id === 'string') {
                menuIds = id;
            } else {
                menuIds = id.join(',') || $scope.meunData.rp.key.join(',');
            }
            console.log(id);
            httpServer.post(URLAPI.addInCollection, {
                params: JSON.stringify({
                    respId: $scope.currentRespAndSys.respId,
                    menuIds: menuIds,
                    systemCode: $scope.currentRespAndSys.systemCode
                })
            }, function (res) {
                $scope.searchShortcut();
            }, function (error) {
                console.error(error);

            })
        };
        $timeout($scope.searchShortcut, 500);


        //删除用户快捷菜单
        $scope.deleteCutAction = function (data) {

            httpServer.post(URLAPI.deleteInCollection, {
                params: JSON.stringify({functionCollectionId: data.functionCollectionId})
            }, function (res) {
                $scope.searchShortcut();
            }, function (error) {
                console.error(error);

            })

        };

        $scope.showMenuList = function () {
            $scope.menuModal.search();
            $('#menuModal').modal('toggle')

        };


        $scope.goto = function (data) {
            //debugger;
            // iframeTabAction(data.functionName, data.functionUrl.replace('/', ''))
            // iframeTabAction('用户维护详情', 'userDetail/')
            // tabAction({name: data.functionName, url: data.functionUrl, id: data.menuId, apply: true})//新版打开
            tabAction(data.functionName, data.functionUrl, data.menuId, true)//


        };
        /*    window.onbeforeunload = function (event) {saveQuickMenu
         console.log('刷新或离开系统');
         return false;
         }*/

        $scope.$on("$destroy", function () {
            //console.clear();
            console.log(appTitle + '已运行了　' + runtime(startTime));

            $interval.cancel($scope.interval);
        });
        //快速通道权限控制
        $scope.menuModalParams = {
            isValid: true,
            systemCode: appName
        };

        if (!$rootScope.currentResp.isAdmin) {
            //$scope.menuModalParams.responsibilityId = $rootScope.currentResp.responsibilityId;
            $scope.menuModalParams.responsibilityId = 570003;
            //p.systemCode = appName;
        }


        $rootScope.telDataList = {};
        $scope.showInfo = function (info) {
            $scope.info = info;
            $("#showInfo").modal('toggle');

            $timeout(function () {
                $("#showInfo").modal('toggle');
            }, 2000);
        };


        // --------------------------------------------------- CTI -----------------------------------------------------


        // ----------------------------------------------------------------------------------------------------------

        function pCallBackTS(strRetValue)
        {
            //alert("RetValue:"+strRetValue);
        }

        function pAutoLoginCallback(strRetValue) {
            //alert("RetValue:"+strRetValue);
        }

        var softphone;
        // 分解
        var status = 12001; // 未登录状态


        // 加载工号初始化登录
        $scope.telLogin = function () {

            $scope.telData.extension = $scope.telData.newExtension;

            var txt_AgentID = $scope.telData.extension;
            $('#telLogin').modal('toggle');
            softphone = new fCreateSoftPhone(txt_AgentID, "2"); // txt_AgentID: 工号,构造函数 1：表示是硬A模式 2：表示是软A模式；
            document.getElementById("zxGonghao_text").value = txt_AgentID;
            var res = softphone.f_initws(); // 进行WebSocket初始化
            if (res == 1){
                status = 12003;
            }

            // 一键登录
            //$scope.btnYiJian();

            //softphone.f_sp_agentlogin(); //座席登录

        };

        // 电话外呼
        $scope.telDial = function () {

            var Called = $scope.tel.extensionDst;
            var Caller = "";
            if (status == '12001') {
                SIEJS.alert("该座机没有登录，请先加载工号登录", "error", "确定");
                return;
            }
            if (Called == null || Called == "") {
                SIEJS.alert('必须填写被叫号码!', "error", "确定");
                return;
            }

            $("#telDial").modal('toggle');

            var res = softphone.f_sp_makecall(Called, Caller);
            if (res == 1) {
                SIEJS.alert('电话号码:' + Called + "呼叫成功!");
            } else {
                SIEJS.alert('电话号码:' + Called + "呼叫成功!", "error", "确定");
            }

        };
        // 就绪
        $scope.readySend = function () {
            //tabAction('来电弹屏', '/customerCTIPopup', '1',true);
            if (status == 12001) {
                SIEJS.alert("该座机没有登录，请先加载工号登录", "error", "确定");
                return;
            }
            if (status == 12002) {
                SIEJS.alert("该座机已经就绪，不需要重复操作。");

            }
            var result = softphone.f_sp_agentready();
            if (result == 1) {
                SIEJS.alert("就绪成功!");
                status = 12002; // 就绪状态
                change_pic(); // 改变按钮演示
            } else {
                SIEJS.alert("就绪失败", "error", "确定");
            }
        };

        // 小休
        $scope.changeTelStatus = function () {


            if (status == 12001) {
                SIEJS.alert("该座机没有登录，请先加载工号登录", "error", "确定");
                return;
            }
            softphone.f_sp_agentnotready(0);
        };

        // 开会
        $scope.notreadysend1 = function () {
            if (status != '12201') {
                SIEJS.alert("该座机没有登录，请先加载工号登录", "error", "确定");
                return;
            }
            softphone.f_sp_agentnotready(1);
        };

        // 会议
        $scope.huiYi = function ()
        {
            var CCID3 = document.getElementById("txtCustomerCallID").value;
            var zhuanjie3 = document.getElementById("zhuanjie").value;
            var txt_zAgentID3 = document.getElementById("txtAgentID").value;
            var zxGonghao3 = document.getElementById("zxGonghao_text").value;

            var date = new Date();
            var seperator1 = "-";
            var seperator2 = ":";
            var month = date.getMonth() + 1;
            var strDate = date.getDate();
            if (month >= 1 && month <= 9) {
                month = "0" + month;
            }
            if (strDate >= 0 && strDate <= 9) {
                strDate = "0" + strDate;
            }
            var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate
                + " " + date.getHours() + seperator2 + date.getMinutes()
                + seperator2 + date.getSeconds();

            var ziduan6 = "Cust_Add_Field6,1,"+currentdate+" "+txt_zAgentID3+"会议"+zxGonghao3+"分机"+zhuanjie3;
            var ziduan6_ar = ziduan6.split(',');
            var ziduan6_arm = ziduan6_ar[2];
            softphone.f_sp_transfermessage(zxGonghao3,ziduan6_arm,pCallBackTS);
            softphone.f_sp_setcustomercallinfo(CCID3,ziduan6);
            softphone.f_sp_conference();
        };


        // 离开
        $scope.notreadysend2 = function () {
            if (status != '12201') {
                SIEJS.alert("该座机没有登录，请先加载工号登录", "error", "确定");
                return;
            }
            softphone.f_sp_agentnotready(2);
        };


        // 转接
        $scope.telTransfer = function () {
            var zhuanjie  = $scope.tel.telTransfer;
            $('#telTransfer').modal('toggle');
            document.getElementById("zhuanjie").value = zhuanjie;

            var CCID2 = document.getElementById("txtCustomerCallID").value;
            var zhuanjie2 = document.getElementById("zhuanjie").value;
            var txt_zAgentID2 = document.getElementById("txtAgentID").value;
            var zxGonghao2 = document.getElementById("zxGonghao_text").value;

            var date = new Date();
            var seperator1 = "-";
            var seperator2 = ":";
            var month = date.getMonth() + 1;
            var strDate = date.getDate();
            if (month >= 1 && month <= 9) {
                month = "0" + month;
            }
            if (strDate >= 0 && strDate <= 9) {
                strDate = "0" + strDate;
            }
            var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate
                + " " + date.getHours() + seperator2 + date.getMinutes()
                + seperator2 + date.getSeconds();

            var ziduan6 = "Cust_Add_Field6,1," + currentdate + " " + txt_zAgentID2 + "转接" + zxGonghao2 + "分机" + zhuanjie2;
            var ziduan6_ar = ziduan6.split(',');
            var ziduan6_arm = ziduan6_ar[2];
            softphone.f_sp_transfermessage(zxGonghao2, ziduan6_arm, pCallBackTS);
            softphone.f_sp_setcustomercallinfo(CCID2, ziduan6);
            softphone.f_sp_transfercall();
        };


        // 保持
        $scope.telHold = function () {
            if (status == 12001) {
                SIEJS.alert("该座机没有登录，请先加载工号登录", "error", "确定");
                return;
            }
            var res = softphone.f_sp_holdcall();
            if (res == 1) {
                document.location.reload();
                SIEJS.alert("电话保持成功!");
            } else {
                SIEJS.alert("电话保持失败", "error", "确定");
            }

        };

        var answerTimeStart; // 通话开始时间
        var answerTimeEnd; // 通话结束时间
        var callTimeTotal; // 通话总时长

        var answerTimeStartStr; // 通话开始时间
        var answerTimeEndStr; // 通话结束时间
        var callTimeTotalStr; // 通话总时长

        // 应答
        $scope.telAnswer = function () {

            answerTimeStart = null; // 先清空之前的开始通话时间
            answerTimeStartStr = null;
            if (status == 12001) {
                SIEJS.alert("该座机没有登录，请先加载工号登录", "error", "确定");
                return;
            }
            var res = softphone.f_sp_answercall();
            if (res == 1) {
                status = 12205; // 通话中
                // 重新获取开始应答时间
                answerTimeStart = (new Date()).getTime();
                answerTimeStartStr = $filter('date')(answerTimeStart,'yyyy-MM-dd HH:mm:ss');

            } else {
                SIEJS.alert("电话保持失败", "error", "确定");
            }
        };

        //电话挂断
        $scope.telHangup = function () {

            callTimeTotal = null; // 首先清空之间的通话时间
            callTimeTotalStr = null; // 首先清空之间的通话时间
            answerTimeEnd = null;
            answerTimeEndStr = null;

            if (status != 12001) {
                SIEJS.alert("该座机没有登录，请先加载工号登录", "error", "确定");
            }
            var res = softphone.f_sp_hangupcall();
            if (res == 1) {

                status = 12201; // 通话中

                SIEJS.alert("挂断成功！");
                // 应答结束时间-挂机时间(数字格式)
                answerTimeEnd = (new Date()).getTime();
                // 应答结束时间-挂机时间(时间格式)
                answerTimeEndStr = $filter('date')(answerTimeEnd,'yyyy-MM-dd HH:mm:ss');
                // 重新计算通话时间
                //callTimeTotal = $filter('date')((answerTimeEnd - answerTimeStart), 'HH:mm:ss');
                callTimeTotal = answerTimeEnd - answerTimeStart;
                callTimeTotalStr = $filter('date')(callTimeTotal,'mm:ss');
                // 给隐藏域赋值-通话总时间
                document.getElementById("callTimeTotal").value = callTimeTotalStr; // 通话总时长
                document.getElementById("callTimeStart").value = answerTimeStartStr; // 开始通话
                document.getElementById("callTimeEnd").value = answerTimeEndStr; // 结束通话


                // 保存来电记录
                saveTelRecord();

                // 保存通话记录
                saveCallRecord();

                findCallRecord();

            } else {
                SIEJS.alert('电话挂断失败！', "error", "确定");
            }

        };

        // 接回
        $scope.unHoldSend = function () {
            if (status == 12001) {
                SIEJS.alert("该座机没有登录，请先加载工号登录", "error", "确定");
                return;
            }
            var res = softphone.f_sp_reconnectcall();
            if (res == 1) {
                document.location.reload();
                SIEJS.alert("电话接回成功!");
            } else {
                SIEJS.alert("电话接回失败", "error", "确定");
            }

        };

        // 电话销毁
        $scope.btNunInit = function () {
            if (status == 12001) {
                SIEJS.alert("该座机没有登录，请先加载工号登录", "error", "确定");
                return;
            }
            var res = softphone.f_spuninit();
            if (res == 1) {
                status == 12001;
                document.location.reload();
                SIEJS.alert("电话销毁成功!");
            } else {
                SIEJS.alert("电话销毁失败", "error", "确定");
            }

        };


        function spcmdcallback(retvalue) {
            //alert("spcmdcallback="+retvalue);
        }

        // 一键登录
        $scope.btnYiJian = function () {

            // if (status == 12001) {
            //     SIEJS.alert("请先加载工号", "error", "确定");
            //     return;
            // }

            softphone.f_sp_SetCommandCallback("f_spinit", spcmdcallback);
            softphone.f_sp_SetCommandCallback("f_spuninit", spcmdcallback);
            softphone.f_sp_SetCommandCallback("f_sp_direct_login", spcmdcallback);
            softphone.f_sp_SetCommandCallback("f_sp_agentlogin", spcmdcallback);
            softphone.f_sp_SetCommandCallback("f_sp_agentlogout", spcmdcallback);
            softphone.f_sp_SetCommandCallback("f_sp_agentready", spcmdcallback);
            softphone.f_sp_SetCommandCallback("f_sp_makecall", spcmdcallback);
            softphone.f_sp_SetCommandCallback("f_sp_agentnotready", spcmdcallback);
            softphone.f_sp_SetCommandCallback("f_sp_hangupcall", spcmdcallback);
            softphone.f_sp_SetCommandCallback("f_sp_holdcall", spcmdcallback);
            softphone.f_sp_SetCommandCallback("f_sp_reconnectcall", spcmdcallback);
            softphone.f_sp_SetCommandCallback("f_sp_agentacw", spcmdcallback);
            softphone.f_sp_SetCommandCallback("f_spsetivrno", spcmdcallback);
            softphone.f_sp_SetCommandCallback("f_sp_transfercall", spcmdcallback);
            softphone.f_sp_SetCommandCallback("f_sp_transfertoivr", spcmdcallback);
            softphone.f_sp_SetCommandCallback("f_sp_consultcall", spcmdcallback);
            softphone.f_sp_SetCommandCallback("f_sp_conference", spcmdcallback);
            softphone.f_sp_SetCommandCallback("f_sp_senddtmf", spcmdcallback);
            softphone.f_sp_SetCommandCallback("f_sp_answercall", spcmdcallback);
            softphone.f_sp_SetCommandCallback("f_spsetloginstate", spcmdcallback);
            softphone.f_sp_SetCommandCallback("f_sp_startsubscribe", spcmdcallback);
            softphone.f_sp_SetCommandCallback("f_sp_cancelsubscribe", spcmdcallback);
            softphone.f_sp_SetCommandCallback("f_sp_getcustomercallinfo", spcmdcallback);
            softphone.f_sp_SetCommandCallback("f_sp_setcustomercallinfo", spcmdcallback);
            softphone.f_sp_SetCommandCallback("f_sp_queryrt", spcmdcallback);
            softphone.f_sp_SetCommandCallback("f_sp_transfermessage", spcmdcallback);

            softphone.f_sp_SetInboundCallEventCallback(pInboundCallCallBack); // 来电弹屏


            var result = softphone.f_sp_direct_login("1104", "7701", pAutoLoginCallback);	//1103：表示登录后自动变为未就绪状态；1104：表示登录后自动变成就绪状态
            if (result == 1) {
                status = 12203; // 未就绪
                SIEJS.alert('一键登录成功');
            } else {
                SIEJS.alert('一键登录失败', "error", "确定");
            }

        };

        // 来电事件的回调函数
        // strAgentID：表示当前座席ID
        // strCustomerCallID：表示当前的客户呼叫标识
        // strCallID：表示呼叫的callid
        // strCusomterNo：表示呼叫的客户号码
        // strServiceNo：表示呼叫的服务号码
        // strQueue：表示呼叫的排队队列
        // strTrunkNo：表示呼叫的中继通道

        function pInboundCallCallBack(strAgentID, strCustomerCallID, strCallID, strCustomerNo, strServiceNo, strQueue, strTrunkNo) {
            if (status == '12204' || status == '12205') {
                SIEJS.alert('座机正在忙，请稍后再拨！');
            }
            status == 12209;
            //alert("InboundCall:11");
            //alert("InboundCall:"+strCusomterNo);
            var CCID = strCustomerCallID;
            var customerNo = strCustomerNo;


            // 给隐藏域赋值
            document.getElementById("txtCustomerCallID").value = strCustomerCallID; // 来电id
            document.getElementById("txtCustomerNo").value = strCustomerNo; // 来电号码
            document.getElementById("txtCurrQueue").value = strQueue; // 分配-业务队列
            document.getElementById("txtServiceNo").value = strServiceNo; // 服务号码
            document.getElementById("txtTrunkNo").value = strTrunkNo; // 中继通道
            document.getElementById("strAgentID").value = strAgentID; // strAgentID
            document.getElementById("strCallID").value = strCallID; // strCallID
            // softphone.f_sp_setcustomercallinfo(CCID,"Cust_Add_Field7,1,Test7测试;Cust_Add_Field9,1,CallIn9测试下;Cust_Add_Field10,1,Over10好了");
            softphone.f_sp_getcustomercallinfo(CCID, "Cust_Add_Field1,1;Cust_Add_Field2,1;Cust_Add_Field3,1;Cust_Add_Field4,1;Cust_Add_Field5,1;Cust_Add_Field6,1;Cust_Add_Field7,1;Cust_Add_Field8,1;Cust_Add_Field9,1;Cust_Add_Field10,1;Province,1;City,1;Cust_Start_Time,3", pGetCustomerDataCallback);

        }

        // 来电获取信息
        function pGetCustomerDataCallback(strRetValue, strDataList) {

            var yldata = strDataList;
            var yldata_ar = yldata.split(';');
            var yldata_ar1 = yldata_ar[0].split(',');
            var yldata_ar2 = yldata_ar[1].split(',');
            var yldata_ar3 = yldata_ar[2].split(',');
            var yldata_ar4 = yldata_ar[3].split(',');
            var yldata_ar5 = yldata_ar[4].split(',');
            var yldata_ar6 = yldata_ar[5].split(',');
            var yldata_ar7 = yldata_ar[6].split(',');
            var yldata_ar8 = yldata_ar[7].split(',');
            var yldata_ar9 = yldata_ar[8].split(',');
            var yldata_ar10 = yldata_ar[9].split(',');
            var yldata_ar11 = yldata_ar[10].split(',');
            var yldata_ar12 = yldata_ar[11].split(',');
            var yldata_ar13 = yldata_ar[12].split(',');
            // $scope.txt_Province = yldata_ar11[2];
            // $scope.txt_City = yldata_ar12[2];
            // $scope.txt_inTime = yldata_ar13[2];
            // document.getElementById("txtgetcustomercallinfo").value = ("字段1: " + yldata_ar1[2] + " 字段2: " + yldata_ar2[2] + " 字段3: " + yldata_ar3[2] + " 字段4: " + yldata_ar4[2] + " 字段5: " + yldata_ar5[2] + " 字段6: " + yldata_ar6[2] + " 字段7: " + yldata_ar7[2] + " 字段8: " + yldata_ar8[2] + " 字段9: " + yldata_ar9[2] + " 字段10: " + yldata_ar10[2]);
            document.getElementById("txt_Province").value = (" " + yldata_ar11[2]); // 来电省份
            document.getElementById("txt_City").value = (" " + yldata_ar12[2]); // 归属地
            document.getElementById("txt_inTime").value = (" " + yldata_ar13[2]); // 进线时间
            // alert("RetValue:"+strRetValue+",数据:"+strDataList);

            // 保存来电记录
            saveTelRecord();

            var strCustomerCallID = document.getElementById("txtCustomerCallID").value; // 来电id
            var customerNo = document.getElementById("txtCustomerNo").value;  // 来电号码
            var strQueue = document.getElementById("txtCurrQueue").value;  // 分配-业务队列
            var strServiceNo = document.getElementById("txtServiceNo").value;  // 服务号码
            var strTrunkNo = document.getElementById("txtTrunkNo").value; // 中继通道
            var txtProvince = document.getElementById("txt_Province").value; // 来电省份
            var txtCity = document.getElementById("txt_City").value;  // 归属地
            var strAgentID = document.getElementById("strAgentID").value; // strAgentID
            var strCallID = document.getElementById("strCallID").value; // strCallID


            // 把来电信息传到弹屏页面
            var jsonObject = {
                'strAgentID': strAgentID,
                'strCustomerCallID': strCustomerCallID,
                'strCallID': strCallID,
                'customerMobile': customerNo,
                'province': txtProvince,
                'city': txtCity,
                'serviceNo': strServiceNo,
                'queue': strQueue,
                'strTrunkNo': strTrunkNo
            };

            var data = JSON.stringify(jsonObject);



            // 电话弹屏
            tabAction('来电弹屏', 'customerCtiPopupDetail/' + data, '1', true);

            // 根据来电号码查询是否存在该用户
            // httpServer.post(URLAPI.findCustomerInfoByPhone, {
            //     params: JSON.stringify({
            //         customerMobile: customerNo,
            //     })
            // }, function (res) {
            //     console.log("返回数据"+JSON.stringify(res.data));
            //     sessionStorage.setItem("customerData", JSON.stringify(res.data));
            //     $scope.searchShortcut();
            // }, function (error) {
            //     console.error(error);
            //
            // })


        }

        // 保存来电记录
        function saveTelRecord() {
            var txtCustomerCallID = document.getElementById("txtCustomerCallID").value; // 来电id
            var txtCustomerNo = document.getElementById("txtCustomerNo").value;  // 来电号码
            var txtCurrQueue = document.getElementById("txtCurrQueue").value;  // 分配-业务队列
            var txtServiceNo = document.getElementById("txtServiceNo").value;  // 服务号码
            var txtTrunkNo = document.getElementById("txtTrunkNo").value; // 中继通道
            var txtProvince = document.getElementById("txt_Province").value; // 来电省份
            var txtCity = document.getElementById("txt_City").value;  // 归属地
            var txtInTime = document.getElementById("txt_inTime").value; // 进线时间

            // var jsonObject = {
            //     'txtCustomerCallID':txtCustomerCallID,
            //     'strAgentID': strAgentID,
            //     'strCustomerCallID': strCustomerCallID,
            //     'strCallID': strCallID,
            //     'customerMobile': customerNo,
            //     'serviceNo': strServiceNo,
            //     'queue': strQueue,
            //     'strTrunkNo': strTrunkNo
            // };

            httpServer.post(URLAPI.saveCrmCtiTelephoneInfo, {
                params: JSON.stringify({
                    customerCallID: txtCustomerCallID,
                    customerNo: txtCustomerNo,
                    txtCurrQueue: txtCurrQueue,
                    serviceNo: txtServiceNo,
                    trunkNo: txtTrunkNo,
                    txtProvince: txtProvince,
                    txtCity: txtCity,
                    txtInTime: txtInTime
                })
            }, function (res) {
                //$scope.searchShortcut();
            }, function (error) {
                console.error(error);

            })
        }

        // 保存通话记录--电话应答才记录
        function saveCallRecord() {

            var txtCustomerCallID = document.getElementById("txtCustomerCallID").value; // 来电id
            var txtCustomerNo = document.getElementById("txtCustomerNo").value;  // 来电号码
            var txtCurrQueue = document.getElementById("txtCurrQueue").value;  // 分配-业务队列
            var txtServiceNo = document.getElementById("txtServiceNo").value;  // 服务号码
            var txtTrunkNo = document.getElementById("txtTrunkNo").value; // 中继通道
            var txtProvince = document.getElementById("txt_Province").value; // 来电省份
            var txtCity = document.getElementById("txt_City").value;  // 归属地
            var txtInTime = document.getElementById("txt_inTime").value; // 进线时间
            var callTimeStart = document.getElementById("callTimeStart").value; // 开始通话时间
            var callTimeEnd = document.getElementById("callTimeEnd").value; // 结束通话时间
            var callTimeTotal = document.getElementById("callTimeTotal").value; // 通话总时长
            var strAgentID = document.getElementById("strAgentID").value;  // strAgentID
            var strCallID = document.getElementById("strCallID").value; // strCallID
            // var jsonObject = {
            //     'txtCustomerCallID':txtCustomerCallID,
            //     'strAgentID': strAgentID,
            //     'strCustomerCallID': strCustomerCallID,
            //     'strCallID': strCallID,
            //     'customerMobile': customerNo,
            //     'serviceNo': strServiceNo,
            //     'queue': strQueue,
            //     'strTrunkNo': strTrunkNo
            // };

            httpServer.post(URLAPI.saveOrUpdateCallRecordsInfo, {
                params: JSON.stringify({
                    customerCallID: txtCustomerCallID,
                    phoneNumber: txtCustomerNo,
                    queue: txtCurrQueue,
                    serviceNo: txtServiceNo,
                    trunkNo: txtTrunkNo,
                    province: txtProvince,
                    city: txtCity,
                    inTime: txtInTime,
                    callStartDate: callTimeStart,
                    callEndDate: callTimeEnd,
                    callDuration: callTimeTotal,
                    agentID: strAgentID,
                    callId: strCallID
                })
            }, function (res) {
                // $scope.searchShortcut();
            }, function (error) {
                console.error(error);

            })
        }

        // 查询通话记录--电话挂机后
        function findCallRecord() {

            var txtCustomerCallID = document.getElementById("txtCustomerCallID").value; // 来电id
            console.log('txtCustomerCallID:'+txtCustomerCallID);
            var strCallID = document.getElementById("strCallID").value; // strCallID
            console.log('strCallID:'+strCallID);
            var phoneNumber = document.getElementById("txtCustomerNo").value; // 来电号码
            console.log('phoneNumber:'+phoneNumber);

            httpServer.post(URLAPI.findCallRecordsInfoByPhone, {
                params: JSON.stringify({
                    customerCallID: txtCustomerCallID,
                    phoneNumber:phoneNumber,
                    callId: strCallID
                })
            }, function (res) {

                console.log("返回mainCtrl通话记录数据"+JSON.stringify(res.data));
                // $rootScope.callRecordData = JSON.stringify(res.data);

                // $rootScope.callRecordData = JSON.stringify([{
                //     callStartDate:"2019-03-22 11:52:00",
                //     // 结束时间
                //     callEndDate:"2019-03-23 11:52:00",
                //    // 通话总时间
                //     allDuration:'10:10'
                // }]);
                sessionStorage.setItem("callData", JSON.stringify(res.data));

                console.log("------12345678："+sessionStorage.getItem("callData"));
                //$scope.$broadcast("callRecordData",JSON.stringify(res.data));
                // $scope.setCallData(JSON.stringify(res.data));
                $scope.searchShortcut();
            }, function (error) {
                console.error(error);

            })


        }

        // 获取当前日期
        //获取当前时间，格式YYYY-MM-DD
        function getNowFormatDate() {
            var date = new Date();
            var seperator1 = "-";
            var year = date.getFullYear();
            var month = date.getMonth() + 1;
            var strDate = date.getDate();
            if (month >= 1 && month <= 9) {
                month = "0" + month;
            }
            if (strDate >= 0 && strDate <= 9) {
                strDate = "0" + strDate;
            }
            var currentdate = year + seperator1 + month + seperator1 + strDate;
            return currentdate;
        }

        // --------------------------------------------------- 通话记录测试代码 开始 -----------------------------------------------------
        $scope.ceshi = function () {


            //findCallRecord();


            var sdate = new Date();
            sleep(5000);
            var now = new Date();
            var days = now.getTime() - sdate.getTime();

            var  dd = $filter('date')(days,'mm:ss');
            console.log(sdate);
            console.log(now);
            console.log(dd);


            var t1 = $filter('date')((new Date()),'HH:mm:ss');
            sleep(5000);
            var t2 = $filter('date')((new Date()),'HH:mm:ss');
            var t3 = t2-t1;
            console.log(t1);
            console.log(t2);
            console.log(t3);

            answerTimeStart = (new Date()).getTime();
            console.log(answerTimeStart);
            sleep(2000);
            answerTimeEnd = (new Date()).getTime();
            console.log(answerTimeEnd);
            //var timeTotal = $filter('date')((answerTimeEnd - answerTimeStart),'HH:mm:ss');
            var timeTotal = answerTimeEnd - answerTimeStart;
            var tt = $filter('date')(timeTotal,'mm:ss');
            console.log(tt);

            var  txtCustomerCallID = '20190321';
            var txtCustomerNo = '13829792997';
            var txtCurrQueue = '2019011';
            var txtServiceNo ='0079';
            var txtTrunkNo ='8899';
            var txtProvince='广东';
            var txtCity = '广州';
            var txtInTime ='2019-03-21 08:24:05';
            var callTimeStart = '2019-03-21 08:24:07';
            var callTimeEnd = '2019-03-21 08:24:27';
            var callTimeTotal = '00:20';
            var strAgentID = '8256';
            var strCallID ='008';

            httpServer.post(URLAPI.saveOrUpdateCallRecordsInfo, {
                params: JSON.stringify({
                    customerCallID: txtCustomerCallID,
                    phoneNumber: txtCustomerNo,
                    queue: txtCurrQueue,
                    serviceNo: txtServiceNo,
                    trunkNo: txtTrunkNo,
                    province: txtProvince,
                    city: txtCity,
                    inTime: txtInTime,
                    callStartDate: callTimeStart,
                    callEndDate: callTimeEnd,
                    callDuration: callTimeTotal,
                    agentID: strAgentID,
                    callId: strCallID
                })
            }, function (res) {

            }, function (error) {
                console.error(error);

            })

        };

        function sleep(delay) {
            var start = (new Date()).getTime();
            while ((new Date()).getTime() - start < delay) {

            }
        }
        // 查询通话记录--电话挂机后
        $scope.findCallRecordTest = function() {


            httpServer.post(URLAPI.findCallRecordsInfoByPhone, {
                params: JSON.stringify({
                    customerCallID: '1404569066',
                    phoneNumber:'13829792997',
                    callId: 10249
                })
            }, function (res) {

                console.log("返回通话记录数据------"+JSON.stringify(res.data));
                $rootScope.mainCtrl_callRecordData = JSON.stringify(res.data);
                //sessionStorage.setItem("callData", JSON.stringify(res.data));
                $scope.searchShortcut();
            }, function (error) {
                console.error(error);

            })
        };
        // --------------------------------------------------- 通话记录测试代码  结束 -----------------------------------------------------
        $scope.myApprovalTable = [];
        $scope.dataTable = [];
        $scope.myProcessTable = [];
        $scope.myProcessTabName = 'myProcessUpcoming';
        $scope.myProcessTabChange = function (tabName) {
            $scope.myProcessTabName = tabName;
            if ('myProcessUpcoming' == tabName) {
                $scope.dataTable.search(1);
            } else if ('myProcessApproval' == tabName) {
                $scope.myApprovalTable.search(1);
            } else if ('myProcessProcessList' == tabName) {
                $scope.myProcessTable.search(1);
            }

        };
        // 我的待办跳转页
        $scope.myUpcomingGo = function (item) {
            var p = {
                taskId: item.taskId
            };
            processGetTaskUrl({
                params: JSON.stringify(p)
            }, function (res) {
                if (res.status === 'S') {
                    var parentIframeId = 690005;
                    var url = 'showProcess/' + decodeURIComponent(res.data.url);
                    var title = '';
                    var action = '';
                    if (res.data.isStart === true) { // 草稿
                        title = '审批驳回：' + item.bpm_title;
                        action = 'refusal';
                    } else {
                        title = '待审批流程：' + item.bpm_title;
                        action = 'approval';
                    }
                    tabAction(title, url +'&action='+action, parentIframeId, true, true)
                } else {
                    SIEJS.alert(res.msg, 'error');
                }
            })
        };

        // 我的已审批跳转页
        $scope.myApprovalGo = function (item) {
            var p = {
                taskId: item.taskId
            };
            processGetTaskUrl({
                params: JSON.stringify(p)
            }, function (res) {
                var parentIframeId = 690006;
                if (res.status === 'S') {
                    tabAction('流程详情：' + item.bpm_title,
                        'showProcess/' +decodeURIComponent(res.data.url) + '&action=detail',
                        parentIframeId, true, true
                    )
                } else {
                    SIEJS.alert(res.msg, 'error');
                }
            })
        };

        // 我的流程跳转页
        $scope.myProcessListGo = function (item) {
            var p = {
                listId: item.listId
            };
            processGetStartUrl({
                params: JSON.stringify(p)
            }, function (res) {
                var parentIframeId = 690007;
                var url = 'showProcess/' + decodeURIComponent(res.data.url);
                var title = '';
                var urlParams = '';

                if (res.status === 'S') {
                    if (item.status === -1) { // 草稿
                        title = '编辑流程：' + item.title;
                        urlParams = '&action=draft&activeTab=myProcess';
                    } else if (item.status === 1){ // 审批通过
                        title = '流程详情：' + item.title;
                        urlParams = '&action=detail&activeTab=myProcess';
                    }else if (item.status===0  && res.data.isStart===true){ // 审批驳回
                        title = '审批驳回：' + item.title;
                        urlParams = '&action=refusal&activeTab=myProcess';
                    }else if (item.status===0  && res.data.isStart===false){ // 审批中
                        title = '流程详情：' + item.title;
                        urlParams = '&action=detail&activeTab=myProcess';
                    }
                    tabAction(title, url +urlParams, parentIframeId, true, true)
                } else {
                    SIEJS.alert(res.msg, 'error');
                }
            })
        };

        $scope.myProcessNewWindow = function (name, url) {
            tabAction(name, url, null, true, false);
        };

        $scope.newTabWindow = function (name, url) {
            tabAction(name, url, null, true, false);
        }
    });
});
