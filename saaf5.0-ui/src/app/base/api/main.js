/**
 * Created by houxingzhang on 2018-02-03.
 */
'use strict';
// SAAF ＡＰＩ配置
define([
    '../api/json',
    '../api/systemSetting',
    '../api/SSOAPI',
    '../api/lookuptype',
    '../api/img',
    '../api/baseApi',
    '../api/dispatch',
    '../api/process',
    '../../processform/processFormApi',
    '../api/msg',
    '../api/equotation',
    '../api/processForm'
], function (json, systemSetting, SSOAPI, lookuptype, img, baseApi, dispatch,process,equotation,processForm) {
    return $.extend({}, json, systemSetting, SSOAPI, lookuptype, img, baseApi, dispatch,process,equotation,processForm);
})