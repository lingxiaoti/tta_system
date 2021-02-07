/**
 * Created by zhengxiaowen on 2018-12-07.
 */

/*
*
* 通过主窗口的window.iframeObject作核心变量，实现跨iframe通讯功能
* 提供，set，get，remove操作iframeObject变量
*
* */


define(['angular', 'app', 'sweetalert', 'webconfig'], function (angular, app, sweetalert, webconfig) {
    var module = angular.module('iframe.Services', []);
    // ************************************* 服 务
    module

        .service('iframeMessage', function () {
            var iframeMessage = {
                set: function (name,obj) {
                    var w = window.parent!=window?window.parent:window
                    if(w.iframeObject[name]){
                        console.warn('变量名称已存在，进行更新操作，'+name)
                    }
                    w.iframeObject[name] = obj

                },
                get: function (name) {
                    var w = window.parent!=window?window.parent:window
                    try {
                        return w.iframeObject[name]
                    }catch (e) {
                        console.warn('变量不存在，不执行，get，'+name)
                    }
                },
                remove: function (name) {
                    var w = window.parent!=window?window.parent:window
                    try {
                        delete w.iframeObject[name]
                    }catch (e) {
                        console.warn('变量不存在，不执行，remove，'+name)
                    }
                },
                run: function (name,data) {
                    if(iframeMessage.get(name)){
                        iframeMessage.get(name)(data)
                    }else{
                        console.log('函数不存在，' + name)
                    }
                }
            }
            return iframeMessage
        })

    return module;
});
