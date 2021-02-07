/**
 * Created by dengdunxin on 2018/1/30.
 */
define(['webconfig'],function (webconfig) {

    return {
        imgDelete: webconfig.url.baseServer + 'fileUploadService/imgDelete', // 图片删除
        imgUpload : webconfig.url.baseServer + 'fileUploadService/imgUpload' // 图片保存

    }
})