/**
 * Created by dengdunxin on 2018/1/4.
 */

define(['webconfig'],function (webconfig) {

    return {
        //快码头表
        querylookupType: webconfig.url.baseServer + 'baseLookupTypeService/find', // 数据字典头表分页查询
        // saveLookupType: webconfig.url.baseServer + 'baseLookupTypeService/saveOrUpdate', // 数据字典头表保存/修改
        deleteLookupType: webconfig.url.baseServer + 'baseLookupTypeService/delete', // 删除数据字典头表

        //快码行表
        queryLookupLine: webconfig.url.baseServer + 'baseLookupValuesService/find', // 行表分页查询
        saveLookupLine: webconfig.url.baseServer + 'baseLookupValuesService/saveOrUpdate', // 行表修改保存
        deleteLookupLine: webconfig.url.baseServer + 'baseLookupValuesService/delete', // 删除数据字典行表
        // SSOFind: webconfig.url.baseServer + 'baseLookupValuesService/findDic', // 查询数据字典
        saveLookupType: webconfig.url.baseServer + 'baseLookupValuesService/saveOrUpdateALL', // 同时修改/保存 数据字典头行表
        queryLookupLineDic: webconfig.url.baseServer + 'baseLookupValuesService/findDic', // 字典使用查询
        queryLookupLineDicPagination: webconfig.url.baseServer + 'baseLookupValuesService/findPagination' // 字典使用查询


    }
})