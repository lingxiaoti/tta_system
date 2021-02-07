package com.sie.saaf.base.user.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.yhg.base.utils.SToolUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import redis.clients.jedis.JedisCluster;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/codeService")
public class CodeService extends CommonAbstractService {

    // 集合中保存大小写字母
    private static List<String> words = new ArrayList<String>();

    public CodeService() {
        super();
    }

    @Autowired
    private JedisCluster jedisCluster;

    @Autowired
    private BaseLoginService baseLoginService;

    @Override
    public IBaseCommon<?> getBaseCommonServer() {
        return null;
    }

    static {
        for (int i = 97; i < 123; i++) {
            char c = (char) i;
            words.add(String.valueOf(c));
        }
        for (int i = 65; i < 91; i++) {
            char c = (char) i;
            words.add(String.valueOf(c));
        }
    }

    /**
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping(value = "getImg")
    public void getImg(String imgCode, String newImgCode) throws IOException {
        int width = 120;
        int height = 30;
        // 步骤一 绘制一张内存中图片
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        // 步骤二 图片绘制背景颜色 ---通过绘图对象
        Graphics graphics = bufferedImage.getGraphics();
        // 得到画图对象 --- 画笔
        // 绘制任何图形之前 都必须指定一个颜色
        graphics.setColor(getRandColor(200, 250));
        graphics.fillRect(0, 0, width, height);
        // 步骤三 绘制边框
        graphics.setColor(Color.WHITE);
        graphics.drawRect(0, 0, width - 1, height - 1);
        // 步骤四 四个随机数字
        Graphics2D graphics2d = (Graphics2D) graphics;
        // 设置输出字体
        graphics2d.setFont(new Font("宋体", Font.BOLD, 18));
        SecureRandom random = new SecureRandom();
        // 生成随机数
        String word = "";
        for (int i = 0; i < 4; i++) {
            int index = random.nextInt(words.size());
            word += words.get(index);
        }
        // 获得成语
        // 定义x坐标
        int x = 10;
        for (int i = 0; i < word.length(); i++) {
            // 随机颜色
            graphics2d.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
            // 旋转 -30 --- 30度
            int jiaodu = random.nextInt(60) - 30;
            // 换算弧度
            double theta = jiaodu * Math.PI / 180;
            // 获得字母数字
            char c = word.charAt(i);
            // 将c 输出到图片
            graphics2d.rotate(theta, x, 20);
            graphics2d.drawString(String.valueOf(c), x, 20);
            graphics2d.rotate(-theta, x, 20);
            x += 30;
        }
        // 将验证码内容保存redis中
        if (newImgCode == null || "".equals(newImgCode)) {
            jedisCluster.set(imgCode, word);
            jedisCluster.expire(imgCode, 300);
        } else {
            jedisCluster.del(imgCode);
            jedisCluster.set(newImgCode, word);
            jedisCluster.expire(newImgCode, 300);
        }


        // 步骤五 绘制干扰线
        graphics.setColor(getRandColor(160, 200));
        int x1;
        int x2;
        int y1;
        int y2;
        for (int i = 0; i < 30; i++) {
            x1 = random.nextInt(width);
            x2 = random.nextInt(12);
            y1 = random.nextInt(height);
            y2 = random.nextInt(12);
            graphics.drawLine(x1, y1, x1 + x2, x2 + y2);
        }
        // 将上面图片输出到浏览器 ImageIO
        graphics.dispose();// 释放资源
        ImageIO.write(bufferedImage, "jpg", response.getOutputStream());
    }

    private Color getRandColor(int fc, int bc) {
        // 取其随机颜色
        SecureRandom random = new SecureRandom();
        if (fc > 255) {
            fc = 255;
        }
        if (bc > 255) {
            bc = 255;
        }
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }

    @RequestMapping(method = RequestMethod.POST, value = "login")
    private String login(@RequestParam(required = false) String params) {
        try {
            JSONObject paramJson = parseJson(params);
            String imgCode = paramJson.getString("imgCode");
            String redisCode = paramJson.getString("redisCode");

            Boolean exists = jedisCluster.exists(redisCode);
            String imgCodeRedis = null;
            if (exists) {
                imgCodeRedis = jedisCluster.get(redisCode);
            } else {
                return SToolUtils.convertResultJSONObj(ERROR_STATUS, "图形验证码已失效", 0, null).toString();
            }

            if ((StringUtils.isBlank(imgCode) || !imgCodeRedis.equalsIgnoreCase(imgCode))) {
                return SToolUtils.convertResultJSONObj(ERROR_STATUS, "图形验证码错误", 0, null).toString();
            } else {
                jedisCluster.del(redisCode);
            }

            String login = baseLoginService.login(params);

            return login;
        } catch (Exception e) {
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }
}
