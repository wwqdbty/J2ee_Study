package com.kulang.study.domain.util;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.Iterator;
import java.util.Properties;

/**
 * by zaishu.ye on 2015/6/24.
 */
//@Slf4j
public class PropertiesUtil {
    private static Properties prop = new Properties();

    static {
        try {
//            log.info("load properties... ");
            //prop.load(ClassLoader.getSystemResourceAsStream("*config.properties"));
            prop.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("admin-jdbc.properties"));
            prop.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("admin-config.properties"));
//            log.info("load properties success.");
        } catch (IOException e) {
//            log.error("load properties failed.", e);
        }
    }

    public static String getProperty(String key) {
        return prop.getProperty(key);
    }

    public static String getProperty(String key, String defaultValue) {
        return StringUtils.isEmpty(prop.getProperty(key)) ? defaultValue : prop.getProperty(key);
    }

    public static String getPropertyReplaced(String key, String[] replacements) {
        try {
            return String.format(prop.getProperty(key), replacements);
        } catch (Exception e) {
//            log.error("getReplacedValue failed. key={}, replacements={}", key, replacements, e);
            return null;
        }
    }

    public static void main(String[] args) {
        Iterator<Object> keySet = prop.keySet().iterator();
        while (keySet.hasNext()) {
            String key = (String) keySet.next();
//            log.info("{} = {}", key, prop.get(key));
        }
    }
}
