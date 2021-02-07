package com.sie.saaf.common.util;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

import freemarker.template.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FreemarkerTemplateUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(FreemarkerTemplateUtil.class);
    //配置信息,代码本身写的还是很可读的,就不过多注解了
    private static Configuration configuration = null;

    public FreemarkerTemplateUtil(Class c,String templatePath) {
        configuration = new Configuration();
        configuration.setDefaultEncoding("UTF-8");
        configuration.setEncoding(Locale.SIMPLIFIED_CHINESE,"UTF-8");
        try {
            configuration.setClassForTemplateLoading(c,templatePath);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(),e);
        }
    }

    public FreemarkerTemplateUtil(String templateFolder) {
        configuration = new Configuration();
        configuration.setDefaultEncoding("UTF-8");
        configuration.setEncoding(Locale.SIMPLIFIED_CHINESE,"UTF-8");
        try {
            configuration.setDirectoryForTemplateLoading(new File(templateFolder));
        } catch (IOException e) {
            LOGGER.error(e.getMessage(),e);
        }
    }

    public FreemarkerTemplateUtil() {
    }

    public static Configuration getConfiguration() {
        return configuration;
    }

    public static void setConfiguration(Configuration configuration) {
        FreemarkerTemplateUtil.configuration = configuration;
    }
}
