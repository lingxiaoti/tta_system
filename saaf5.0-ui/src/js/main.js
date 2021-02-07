/**
 * Created by houxingzhang on 2016-09-05.
 */

define(['config', 'webconfig'], function (config, webconfig) {
    var h = window.location.hostname;
    var _appName = window.location.hash.match(/#\/[a-zA-Z]+/gmi);

    if (!_appName && !webconfig.defaultApp) {
        document.getElementById('loadingTxt').innerHTML = '系统参数错误：没有此应用系统';
        console.log('没有获取到正确的应用APP NAME名称');
        return false;
    } else if (_appName) {
        _appName = _appName[0].replace('#/', '');
    } else if (webconfig.defaultApp) {
        _appName = webconfig.defaultApp;
    }
    for (var n in webconfig.compList) {
        var item = webconfig.compList[n];
        if (item.domain === h) {
            window.domain = item;
            break;
        }
    }

    var hasApp = false;
    for (var m in webconfig.appList) {
        var item = webconfig.appList[m];
        if (item.appName === _appName.toUpperCase()) {
            window.appName = item.appName; // 应用名称 *******************************************************
            window.appTitle = item.appTitle; //  应用标题
            hasApp = true;
            break;
        }
    }

    if (!hasApp) {
        document.getElementById('loadingTxt').innerHTML = '系统参数错误：没有此应用系统';
        console.log('没有获取到正确的应用APP NAME名称');
        return;
    }


    window.console = window.console || (function () {
            var c = {};
            c.log = c.warn = c.debug = c.info = c.error = c.time = c.dir = c.profile
                = c.clear = c.exception = c.trace = c.assert = function () {
            };
            return c;
        })();

    window.log = window.console.log;

    require(["route"], function (router) {
        'use strict';
        angular.element(document).ready(function () {
            angular.bootstrap(document, ['SAAFAPP']);
            angular.element(document).find('html').addClass('ng-app');
        });

    });

});

