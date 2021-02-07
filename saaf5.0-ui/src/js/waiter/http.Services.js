/**
 * Created by houxingzhang on 2018-01-05.
 *    // ************************************* http 服 务
 */
'use strict';
define([
    '../app/' + appName.toLowerCase() + '/service/http'
], function (module) {

    //var module = angular.module('http.Service', []);

    module
    //块码\数据字典
    .service('lookupCode', function (httpServer, URLAPI) {
        return function ( callBack, errCallBack) {
            var params = {
                params:JSON.stringify({

                })
            }
            httpServer.post(URLAPI.queryLookupLineDic, params, callBack, errCallBack,'false')
        }
    })
    /*修改密码*/
        .service('changePassword', function (httpServer, URLAPI) {
            return function (params, callBack, errCallBack) {
                httpServer.save(URLAPI.changePassword, params, callBack, errCallBack)
            }
        })
        // 获取权限BUTTON
        .service('buttonList', function (httpServer, URLAPI) {
            return function (params, success, error) {
                var p = {
                    params: JSON.stringify(params),
                    pageIndex: 1,
                    pageRows: 1000
                }
                httpServer.post(URLAPI.buttonList, p, success, error)
            }
        })
        .service('logout', function (httpServer, URLAPI, gotoLogin, SIEJS) {
            return function () {
                SIEJS.confirm('您确定要退出系统吗？','','确定',function(){
                    httpServer.get(URLAPI.logout, {}, function (res) {
                        if (res.status == 'S') {
                            gotoLogin(true);
                        }
                        else {
                            SIEJS.alert('退出失败', "error", "确定");
                        }
                    }, null, true);
                })

            }
        })
        .service('getResourceByResMenuCode', function (httpServer, URLAPI, $location, $rootScope) {

            return function (respId, callback) {
                var menuCode = $location.search().menucode || $location.search().menuCode;

                if (!menuCode) return;
                if (!localStorage[appName + '_currentResp']) return;
                respId = respId || $location.search().respId || resp.responsibilityId;
                var resp = JSON.parse(localStorage[appName + '_currentResp']);
                var p = {
                    menuCode: menuCode,
                    responsibilityId: respId
                };

                // 获取当前页面的权限按钮
                httpServer.post(URLAPI.resourceByRespMenuCode, {params: JSON.stringify(p)}, function (res) {
                    $rootScope.permission = res.data;

                    if (typeof  callback === 'function') {
                        callback(res.data);
                    }
                }, function (res) {
                }, 'false');
            }
        })
        .service('findUserInfo',function(httpServer,URLAPI,gotoLogin, SIEJS){
            return function (success) {
                httpServer.post(URLAPI.findUserInfo, {}, success, null, true);

            }


        });


    return module;

});