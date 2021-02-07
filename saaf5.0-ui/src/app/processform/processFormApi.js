/**
 * Created by Administrator on 2018/5/28.
 */
define([
    '../processform/processOaFormApi',
    '../processform/processEmsFormApi',
    '../processform/processOuterFormApi',
], function (processOaFormApi,processEmsFormApi,processOuterFormApi) {
    return $.extend(processOaFormApi,processEmsFormApi,processOuterFormApi);
})