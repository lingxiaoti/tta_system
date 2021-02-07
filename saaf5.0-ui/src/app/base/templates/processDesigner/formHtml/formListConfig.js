/**
 * Created by Administrator on 2018/5/28.
 */

define(function () {

    // 流程使用的表单列表
    var pcList = [
        {
            title: '请假流程',
            url: 'OAForm' // 路由
        },
        {
            title: '固定资产申请单表单',
            url: 'assetApplyForm'
        },
        {
            title: '固定资产验收单表单',
            url: 'assetCheckForm'
        },
        {
            title: '固定资产报废单表单',
            url: 'assetScrapForm'
        },

        {
            title: '合同管理表单',
            url: 'contractDetails'
        },
        {
            title: '办公用品表单',
            url: 'officeForm'
        },
    ];

    var mobileList = [
        {
            title: '请假流程申请(移动端面页)',
            url: 'OAForm'
        }
    ];
    return {
        pcList: pcList,
        mobileList: mobileList
    }

})

