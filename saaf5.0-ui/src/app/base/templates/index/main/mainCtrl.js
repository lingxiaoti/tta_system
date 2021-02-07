/**
 * Created by luofuo on 2016-09-03.
 */
'use strict';
define(["app"], function (app) {
    //app.useModule('ng.ueditor');//按需加载编辑器

    //app.useModule('ngDialog');//按需加载弹出层模块

    app.controller('mainCtrl', function ($interval, saveTabToStorage, changePassword, gotoLogin, $rootScope, $scope, httpServer,
                                         lookupCode, SIEJS, URLAPI, $state, tabAction, Base64, Fullscreen, $http, $location, $timeout, $window,logout) {
        if (!sessionStorage[appName + '_successLoginInfo']) {

            $location.path('/login');
            return;
        }

        $scope.timer = (new Date()).getTime();


        $rootScope.userName = JSON.parse(sessionStorage[appName + '_successLoginInfo']).userName;
        $scope.language = localStorage[appName + '_language'] || '';
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

        $scope.logout = logout;
        //改变用户职责
        $scope.saafChangeUserRespon = function (row) {

            if ($rootScope.currentResp.responsibilityId === row.responsibilityId)return;

            localStorage[appName + '_currentResp'] = JSON.stringify(row);
            $rootScope.currentResp = row;
            $rootScope.saafHeadTab = []; // 清除菜单
            saveTabToStorage();// 保存菜单
            $window.location.reload();
            /* $state.go('main');
             $window.location.reload();*/
        }
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
            var passwordForm = {
                oldPassword: Base64.encode($scope.passwordForm.oldPassword),
                newPassword: Base64.encode($scope.passwordForm.newPassword),
                userId: user.userId,
                userName: user.userName,
            }

            changePassword({params: JSON.stringify(passwordForm)}, function (res) {
                $('#changePassword').modal('toggle');
                // SIEJS.alertByRes(res);
            })

        };


        //查询自己的待办信息
        $scope.getNeedByself = function (flag) {

            var searchP = {var_inList_state: ["UNREADED", "READED"], var_equal_messageType: 'NEED'}
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
            var searchP = {var_equal_state: 'UNREADED', var_equal_messageType: 'NOTICE'}
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
            //$scope.getNeedByself(flag); // ---------------------------------------------------------  ---- - --  项目注释
            //$scope.getNoticeByself(flag);// ---------------------------------------------------------  ---- - --  项目注释
        }
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
                'params': JSON.stringify({data: data, varInputRespId: $rootScope.currentUserRespId}),
            }, function (res) {

                $scope.searchShortcut();

            })
        };

        //查询用户快捷菜单
        $scope.searchShortcut = function () {

            //console.log('项目注释：查询用户快捷菜单－－－－－－－－－')
            /*httpServer.getData(URLAPI.queryShortcutInUser, 'POST', {
             'params': JSON.stringify({varRespId: $rootScope.currentUserRespId}),
             }, function (res) {
             $scope.userShortcut = res;
             }, function(res){
             gotoLogin.goLogin();
             })*/
        };
        $timeout($scope.searchShortcut, 500);
        //删除用户快捷菜单
        $scope.deleteCutAction = function (id) {
            httpServer.getData(URLAPI.shortcutService_delete, 'POST', {
                'params': JSON.stringify({id: id}),
            }, function (res) {
                $scope.searchShortcut();
            })

        };

        /*
         window.onbeforeunload = function (event) {
         console.log('刷新或离开系统');
         return false;
         }
         */

        // 获取块码
        lookupCode({}, function (res) {
            $rootScope.lookupCode = res.data;   //
        })

        $scope.myProcessTabName = 'myProcessUpcoming';
        $scope.myProcessTabChange = function (tabName) {
            $scope.myProcessTabName = tabName;
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
        }

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
        }

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
        }

        $scope.myProcessNewWindow = function (name, url) {
            tabAction(name, url, null, true, false);
        };

        $scope.newTabWindow = function (name, url) {
            tabAction(name, url, null, true, false);
        }

    });
});
