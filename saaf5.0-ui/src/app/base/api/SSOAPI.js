/**
 * Created by dengdunxin on 2018/1/3.
 */
define(['webconfig'],function (webconfig) {
    //var webconfig.url.baseServer = serviceURL + 'baseServer/' ; // 基础服务\ 主服务
    return {
        SSOFind: webconfig.url.baseServer + 'baseSystemSsoService/find', // 分页查询
        SSOSave: webconfig.url.baseServer + 'baseSystemSsoService/saveOrUpdate', // 保存
        SSODel: webconfig.url.baseServer + 'baseSystemSsoService/delete', // 删除
        //角色映射
        SSORoleFind: webconfig.url.baseServer + 'baseRoleSystemService/find', // 角色查询
        SSORoleSave: webconfig.url.baseServer + 'baseRoleSystemService/saveOrUpdate', // 角色保存
        SSORoleDel: webconfig.url.baseServer + 'baseRoleSystemService/delete', // 角色删除

        SSORespDel: webconfig.url.baseServer + 'baseResponsibilitySystemService/delete', // 角色删除
        ssoRespFind: webconfig.url.baseServer + 'baseResponsibilitySystemService/find', // 职责查询
        ssoRespSave: webconfig.url.baseServer + 'baseResponsibilitySystemService/saveOrUpdate' // 职责保存

    }
})