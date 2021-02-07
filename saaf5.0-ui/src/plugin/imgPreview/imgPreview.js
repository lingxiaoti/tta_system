/**
 * Created by houxingzhang on 2018-10-11.
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
        $.fn.imgPreview = function (options) {
            var opts = $.extend({}, $.fn.imgPreview.defaults, options);
            //插件代码 开始

            var scale=1; // 放大倍率
            var speed=0.2; // 放大或缩递进数


            if ($("#imgPreviewBox").length === 0) {
                var w = $(window);
                var _h = w.innerHeight();
                var html = '<div id="imgPreviewBox" ' +
                    'style=" line-height:' + _h + 'px;">' +
                    '<img style="" src="">' +
                    '</div>';
                $('body').append(html);
            }

            var imgPreviewBox = $("#imgPreviewBox");
             w.bind('resize', function () {
                 imgPreviewBox.css("line-height", w.innerHeight() + "px");
             });
            // 点击隐藏
            imgPreviewBox.click(function () {
                imgPreviewBox.find('img').css({
                    transform: 'scale(0.05)',
                    opacity: 0
                });
                setTimeout(function () {
                    imgPreviewBox.hide();
                }, 200)
            })

            $("body").on('click', '.img-preview', function () {
                var src = $(this).attr('src');
                if (!src) return;
                imgPreviewBox.find('img').attr('src', src);
                imgPreviewBox.show();
                setTimeout(function(){
                    imgPreviewBox.find('img').css({
                        transform: 'scale(1)',
                        opacity: 1
                    });
                },10)

            })

            var myimage =imgPreviewBox[0];

            if (myimage.addEventListener) {
                // IE9, Chrome, Safari, Opera
                myimage.addEventListener("mousewheel", MouseWheelHandler, false);
                // Firefox
                myimage.addEventListener("DOMMouseScroll", MouseWheelHandler, false);
            }

            function MouseWheelHandler(e) {
                  e = window.event || e;
                  if (e.wheelDelta > 0) {
                      if (scale < 2) {
                          scale =scale + scale * speed;
                      }else{
                          return
                      }
                  }else{
                      if (scale> 0.5) {
                          scale =scale - scale * speed;
                      }else{
                          return
                      }
                  }

                imgPreviewBox.find('img').css(
                    {
                        transform: 'scale('+ scale +')'
                    }
                )
            }
          /*  this.each(function () {

            });*/

            return {

            }
            //插件代码 结束
        };

        // 插件的defaults
        $.fn.imgPreview.defaults = {

        };


        /* 设置版本号 */
        $.fn.imgPreview.version = 1.0;


        $.fn.imgPreview();


    })

);