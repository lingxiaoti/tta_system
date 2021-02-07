'use strict';
define(["app"],function(app){
    app.controller('ruleHeaderCtrl', function ($scope) {
        $scope.navData=[
            {name:'业务线维护',href:'.business'},
            {name:'表达式设置',href:'.expression'},
            {name:'执行结果',href:'.doResult'},
            {name:'服务查询',href:'.service'},
            {name:'模板设置',href:'.model'},
            {name:'生成页面',href:'.buildPage'},
        ];
    })
})