/**
 * Created by houxingzhang on 2016-09-26.
 */
'use strict'

;(function (factory) {
        if (typeof define === "function" && define.amd) {
            // AMD模式
            define(["jquery"], factory);
        } else {
            // 全局模式
            factory(jQuery);
        }
    }(function ($) {
        /* 插件的定义 */
        $.fn.CompOrgTree = function (options) {

            var thumLayout = null;//缩略图的布局
            var mainLayout = null; //  选座主图
            var Json = {};
            var opts = $.extend({}, $.fn.CompOrgTree.defaults, options);


            if (opts.url == "") { //没有填写ＡＪＡＸ地址 ，以JSON
                Json = {
                    ColumnCount: opts.json.SeatLayoutData.Areas[0].ColumnCount,
                    RowCount: opts.json.SeatLayoutData.Areas[0].RowCount,
                    Rows: opts.json.SeatLayoutData.Areas[0].Rows
                };
            } else {
                $.ajaxSettings.async = false;//同步一请求
                $.getJSON(opts.url, opts.data, function (res) {
                    Json = {
                        ColumnCount: res.SeatLayoutData.Areas[0].ColumnCount,
                        RowCount: res.SeatLayoutData.Areas[0].RowCount,
                        Rows: res.SeatLayoutData.Areas[0].Rows
                    };
                });

            }


            //插件代码 开始
            this.each(function () {
                var layout = $(this);//组织布局

            });


            return {
                
            }
            //插件代码 结束
        };

        // 插件的defaults
        $.fn.CompOrgTree.defaults = {
            showSeatNumber: false,//是否显示座位号
            maxSelected: 6,//　单笔订单最大选择数　
            direction: 3,///  计算物理坐标方向，默认为 3 VISTA　计算方向，0,0点为右下角
            statistical: true,///   显示与统计已选中的影票
            thumbnail: 0, //显示缩略图 默认不显示缩略图
            onlyThumLayout: false,   ///  只返回当前缩略图的布局对象，而不直接生成缩略图
            price: {  //电影票价格
                normal: 55
            },
            cssStyle: { //默认的选择样式
                available: 'available',
                unavailable: 'unavailable',
                selected: 'selected'
            },
            icon: [],//订制icon
            json: {},//　JSON　座位数据    与　url 二者必选一个
            url: "", //　AJAX  ＵＲＬ地址
            data: {}, // AJAX 参数
            click: null, //单击座位时触发
            submit: null  //提交
        };


        /* 设置版本号 */
        $.fn.CompOrgTree.version = 1.0;

    

    })

);