package com.sie.watsons.base.api.services;

import com.sie.watsons.base.api.model.inter.IPlmSendEmail;
import com.sie.watsons.base.redisUtil.EmailUtil;
import com.yhg.base.utils.SToolUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.sie.saaf.common.services.CommonAbstractService.ERROR_STATUS;

/**
 * Created by Administrator on 2019/12/16/016.
 */
@RestController
@RequestMapping("/plmSendEmailService")
public class PlmSendEmailService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PlmSendEmailService.class);
    @Autowired
    private IPlmSendEmail plmSendEmailServer;

    public PlmSendEmailService() {
        super();
    }


    /**
     * 邮件反馈
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "sendPlmEmail")
    public String sendPurchaseDivideEmail() {

        Map<String, String> map = weekByDate();
        if (map.isEmpty()) {
            LOGGER.info("/plmSendEmailService/sendPlmEmail 当前时段不符合规范 map{}:" + map);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, "当前时段不符合规范", 0, null).toString();
        }
        try {
//            Map<String, String> emails = new HashMap<>();
//            emails.put("wujigao", "1198415652@qq.com");
//            emails.put("daijie", "daijie1@chinasie.com");
//            for (int x = 0; x < 2; x++) {
//                if (x == 0) {
//                    com.sie.saaf.base.mailUtil.EmailUtil.sendMailFromWatsons("1198415652@qq.com",
//                            "2001测试1006 发邮件 次数123",
//                            "人员名:wujigao " );
//                    EmailUtil.sendMailFromWatsons("1198415652@qq.com","测试2001 发邮件的","这是一个测试");
//
//                } else {
//                    com.sie.saaf.base.mailUtil.EmailUtil.sendMailFromWatsons("daijie1@chinasie.com",
//                            "2001测试1006 发邮件 次数123",
//                            "人员名:daijie " );
//
//                    String[] strs = {"1198415652@qq.com", "daijie1@chinasie.com"};
//                    EmailUtil.sendMailFromWatsons(strs, "测试2001 发邮件的", "这是一个测试", "/home/sieuat/modelForSup.xls");//emails.values().toArray(new String[emails.size()])
//                }
//
//            }
//            EmailUtil.SendEmail("测试2001附件:/home/sieuat/svn/ui/saaf-ui-uat:" , "测试邮寄附件", "/home/sieuat/svn/ui/saaf-ui-uat/productUpdate.xlsx",strs );

            plmSendEmailServer.timingSendEmail(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "S";
    }


    /**
     * 条件一：当前时段必须为周一至周五(周六至周日不可操作)
     * 条件二：必须为11:00 / 15:00 / 17:00 / 19:00 时段才可操作
     *
     * @return
     */
    public Map<String, String> weekByDate() {
        // 获取当前时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH");
        String time = sdf.format(new Date());

        // 年
        String year = time.substring(0, time.indexOf("-"));
        //月
        String month = time.substring(time.indexOf("-") + 1, time.lastIndexOf("-"));
        //日
        String day = time.substring(time.lastIndexOf("-") + 1, time.indexOf(" "));

        SimpleDateFormat format = new SimpleDateFormat("dd MM yyyy");
        Date d = null;
        try {
            d = format.parse(day + " " + month + " " + year);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        boolean flag = true;
        String str = "";
        // 获取当前日期为周几
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        int weekDay = cal.get(Calendar.DAY_OF_WEEK);
        switch (weekDay) {
            case 1:
                flag = false;
                break; // SUNDAY(星期天)
            case 2:
                str = "MONDAY";
                break; // MONDAY(星期一)
            case 3:
                break; // TUESDAY(星期二)
            case 4:
                break; // WEDNESDAY(星期三)
            case 5:
                break; // THURSDAY(星期四)
            case 6:
                break;  // FRIDAY(星期五)
            case 7:
                flag = false;
                break; // SATURDAY(星期六)
            default:
                flag = false;
                break;
        }

        Map<String, String> map = new LinkedHashMap<>();
        if (flag) {
            // 时
            String hour = time.substring(time.indexOf(" ") + 1, time.length());

            SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = null;
            try {
                date = fm.parse(time + ":00:00");
            } catch (Exception ex) {
                ex.printStackTrace();
                return map;
            }
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);

            if ("MONDAY".equals(str) && "11".equals(hour)) {
                // 如果当前时间为星期一11点时段，将上周五19:00 之后 至 这周星期一 11:00 之前生成的订货清单汇总
                calendar.set(calendar.DATE, calendar.get(calendar.DATE) - 3);
                calendar.add(Calendar.HOUR, 8);
            } else if ("11".equals(hour)) {
                calendar.set(calendar.DATE, calendar.get(calendar.DATE) - 1);
                calendar.add(Calendar.HOUR, 8);
            } else if ("15".equals(hour)) {
                calendar.set(calendar.HOUR, calendar.get(calendar.HOUR) - 4);
            } else if ("17".equals(hour)) {
                calendar.set(calendar.HOUR, calendar.get(calendar.HOUR) - 2);
            } else if ("19".equals(hour)) {
                calendar.set(calendar.HOUR, calendar.get(calendar.HOUR) - 2);
            } else {

                calendar.set(calendar.DATE, calendar.get(calendar.DATE) - 1);
                calendar.add(Calendar.HOUR, 8);

                // 开始时间
                map.put("startTime", fm.format(calendar.getTime()));
                // 结束时间
                map.put("endTime", fm.format(date));
                return map;
            }
            // 开始时间
            map.put("startTime", fm.format(calendar.getTime()));
            // 结束时间
            map.put("endTime", fm.format(date));
            return map;
        }
        return map;
    }

}
