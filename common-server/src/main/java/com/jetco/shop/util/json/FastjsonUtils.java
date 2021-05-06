package com.jetco.shop.util.json;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * fastjson是阿里巴巴的开源JSON解析库，它可以解析JSON格式的字符串，
 * 支持将Java Bean序列化为JSON字符串，也可以从JSON字符串反序列化到JavaBean。
 *
 * 解析效率最快，不用依赖其他jar包，但是存在很多漏洞，不建议使用
 *
 * </p>
 *
 * @author lhw
 * @version 1.0
 * @since 2020-11-11
 */
public class FastjsonUtils {

    private static final Logger logger = LoggerFactory.getLogger(FastjsonUtils.class);

    private FastjsonUtils(){}

    /**
     * 将json字符串解析为实体类对象
     *
     * @param jsonStr json字符串
     * @param clazz   实体类对象
     * @return 实体类对象
     */
    public static <T> T jsonToObject(String jsonStr, Class<T> clazz) {
        T t = null;
        try {
            t = JSON.parseObject(jsonStr, clazz);
        } catch (Exception e) {
            logger.error("将json字符串解析为实体类对象报错>>>>>", e);
        }
        return t;
    }

    /**
     * 将json字符串解析成list对象
     *
     * @param jsonStr json字符串
     * @param clazz   实体类对象
     * @return list对象
     */
    public static <T> List<T> jsonToList(String jsonStr, Class<T> clazz) {
        List<T> list = new ArrayList<>();
        try {
            list = JSON.parseArray(jsonStr, clazz);
        } catch (Exception e) {
            logger.error("将json字符串解析成list对象报错>>>>>", e);
        }
        return list;
    }
    /**
     * 将json字符串解析成list对象
     *
     * @param jsonStr json字符串
     * @return list对象
     */
    public static <T> List<T> getArrayJson(String jsonStr) {
        List<T> list = new ArrayList<>();
        try {
            list = (List<T>) JSON.parseArray(jsonStr);
        } catch (Exception e) {
            logger.error("将json字符串解析成list对象报错>>>>>", e);
        }
        return list;
    }

    /**
     * 将jsonString解析成list带map的集合
     *
     * @param jsonStr json字符串
     * @return list对象
     */
    public static List<Map<String, Object>> jsonToListMaps(String jsonStr) {
        List<Map<String, Object>> list = new ArrayList<>();
        try {
           list = JSON.parseObject(jsonStr, new TypeReference<List<Map<String, Object>>>(){}.getType());
        } catch (Exception e) {
            logger.error("将jsonString解析成list带map的集合报错>>>>>", e);
        }
        return list;
    }

}
