/**
 * Created by Administrator on 2017/8/14.
 */
'use strict';
// 事件回调，初始化
var mixOnInit = function (){
    //接口加载完毕
    var appElement = document.querySelector('[ng-controller=mainCtrl]');

    var $scope = angular.element(appElement).scope();
    //alert($scope);
    $scope.mixOnInit();
    //调用方法后，可以重新绑定，在页面同步
    $scope.$apply();

}
// 呼叫中心事件触发，
var mixOnCallback=function(str){

    if(str.indexOf('Event: ExtensionStatus')>-1){
        var appElement = document.querySelector('[ng-controller=mainCtrl]');
        var $scope = angular.element(appElement).scope();
        //alert($scope);
        $scope.mixOnCallback(str);
        //调用方法后，可以重新绑定，在页面同步
        $scope.$apply();
    }


}

// 触发电话弹屏事件
var openPopUp=function(str){
    var appElement = document.querySelector('[ng-controller=mainCtrl]');
    var $scope = angular.element(appElement).scope();
    //alert($scope);
    $scope.openPopUp(str);
    //调用方法后，可以重新绑定，在页面同步
    $scope.$apply();
}

define(["jquery",'jQuery-browser'], function ($) {
    //弹屏注册
    //var url='http://192.168.9.167/admin/?m=interface&c=api' ;

    var url='http://192.168.6.221/admin/?m=interface&c=api' ;

    var  popscreenRegist =function(str){
        var config ={
            extension:'',  // 分机号码，多个用逗号隔开， 8001,8002
            //pop_url:'',     // 弹屏地址 poen_type =1 时有效
            pop_type:'LINK',   // 弹屏时机，有效值为RING(振铃时弹屏)、LINK(接通时弹屏)。
            pop_out:'DialIn',   // 呼入弹屏还是呼出弹屏  有效值为ALL(呼入呼出都弹屏)、DialIn(仅呼入弹屏)、DialOut(仅呼出弹屏)
            open_type:'2',  //弹屏方式 有效值为1(弹出新窗口)、2(注册JS回调函数)。
            mixcallback:'openPopUp'  // JS回调函数名   poen_type =2 时有效
        } ;

        var result= $.extend({},config,str);
        var param = $.param(result);

        $.getScript(url+'&a=popscreen&'+param,function(){

        });

    }
    //事件回调
    var callback = function () {
        $.getScript(url+'&a=command',function(){

        })
    }


    return {
        callback:callback,
        popscreenRegist:popscreenRegist,
        telURL:url

    }



})