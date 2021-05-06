package com.jetco.shop.util.json;

import cn.hutool.core.date.DatePattern;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * Jackson是SpringBoot默认使用的JSON处理库，它可以轻松的将Java对象转换成JSON对象，同样也可以将JSON转换成Java对象
 *
 * 解析效率很快，很全面，用的人最广，建议使用
 *
 * </p>
 *
 * @author lhw
 * @version 1.0
 * @since 2020-11-11
 */
public class JacksonUtils {

    private static final Logger logger = LoggerFactory.getLogger(JacksonUtils.class);

    /**
     * 定义jackson对象
     */
    private static final ObjectMapper MAPPER = new ObjectMapper();

    static {
        /*
         * 默认非空不输出，时间格式
         */
        MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        MAPPER.setDateFormat(new SimpleDateFormat(DatePattern.NORM_DATETIME_PATTERN));
        MAPPER.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
    }

    private JacksonUtils(){}

    public static ObjectMapper getInstance() {
        return MAPPER;
    }

    /**
     * 将javaBean、列表、数组转换为json字符串
     * @param obj Object对象
     * @return json字符串
     */
    public static String objToJson(Object obj) {
        try {
            return MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            logger.error("将javaBean、列表、数组转换成json字符串报错>>>>>", e);
        }
        return null;
    }

    /**
     * 将javaBean、列表、数组转换为json字符串,忽略空值
     * @param obj Object对象
     * @return json字符串
     */
    public static String objToJsonIgnoreNull(Object obj) {
        try {
            return MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            logger.error("将javaBean、列表、数组转换为json字符串,忽略空值报错>>>>>", e);
        }
        return null;
    }


    /**
     * 将json串结果集转化为java对象
     * @param jsonData json字符串
     * @param clazz 实体类对象
     * @param <T> 泛型T
     * @return 实体类对象
     */
    public static <T> T jsonToPojo(String jsonData, Class<T> clazz) {
        try {
            return MAPPER.readValue(jsonData, clazz);
        } catch (Exception e) {
            logger.error("将json结果集转化为java对象报错>>>>>", e);
        }
        return null;
    }

    /**
     * 将json串数据转换成实体类对象列表
     * @param jsonData json字符串
     * @param clazz 实体类对象
     * @param <T> 泛型T
     * @return 实体类对象集合
     */
     public static <T> List<T> jsonToList(String jsonData, Class<T> clazz) {
         JavaType javaType = MAPPER.getTypeFactory().constructParametricType(List.class, clazz);
         try {
             return MAPPER.readValue(jsonData, javaType);
         } catch (Exception e) {
             logger.error("将json数据转换成实体类对象列表报错>>>>>", e);
         }
         return Collections.emptyList();
     }

    /**
     * 把json字符串解析成list，如果list内部的元素存在jsonString，继续解析
     *
     * @param jsonData json字符串
     * @return list对象
     */
    private static List<Object> jsonToListRecursion(String jsonData) {
        if (jsonData == null) {
            return Collections.emptyList();
        }
        try {
            List<Object> list = MAPPER.readValue(jsonData, List.class);
            for (Object obj : list) {
                if (obj instanceof String) {
                    String str = (String) obj;
                    if (str.startsWith("[")) {
                        jsonToListRecursion(str);
                    } else if (obj.toString().startsWith("{")) {
                        jsonToMapRecursion(str);
                    }
                }
            }
            return list;
        } catch (JsonProcessingException e) {
            logger.error("把json字符串解析成list，如果list内部的元素存在jsonString，继续解析报错>>>>>", e);
        }
        return Collections.emptyList();
    }

    /**
     * 把json字符串解析成map，如果map内部的value存在jsonString，继续解析
     *
     * @param jsonData
     * @return
     */
    private static Map<String, Object> jsonToMapRecursion(String jsonData) {
        if (jsonData == null) {
            return null;
        }
        try {
            Map<String, Object> map = MAPPER.readValue(jsonData, Map.class);
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                Object obj = entry.getValue();
                if (obj instanceof String) {
                    String str = ((String) obj);
                    if (str.startsWith("[")) {
                        List<?> list = jsonToListRecursion(str);
                        map.put(entry.getKey(), list);
                    } else if (str.startsWith("{")) {
                        Map<String, Object> mapRecursion = jsonToMapRecursion(str);
                        map.put(entry.getKey(), mapRecursion);
                    }
                }
            }
            return map;
        } catch (JsonProcessingException e) {
            logger.error("把json字符串解析成map，如果map内部的value存在jsonString，继续解析报错>>>>>", e);
        }
        return Collections.emptyMap();
    }

    /**
     * json字符串转换为map对象
     * @param jsonData jaon字符串
     * @return map对象
     */
    public static <T> Map<String, T> jsonToMap(String jsonData) {
        try {
            return MAPPER.readValue(jsonData, Map.class);
        } catch (JsonProcessingException e) {
            logger.error("json字符串转换为map对象报错>>>>>", e);
        }
        return Collections.emptyMap();
    }

    /**
     * json字符串转换为map对象
     */
    public static <T> Map<String, T> jsonToMap(String jsonData, Class<T> clazz) {
        try {
            Map<String, Map<String, Object>> map = (Map<String, Map<String, Object>>) MAPPER.readValue(jsonData, new TypeReference<Map<String, T>>() {});
            Map<String, T> result = Maps.newHashMap();
            for (Map.Entry<String, Map<String, Object>> entry : map.entrySet()) {
                result.put(entry.getKey(), mapToPojo(entry.getValue(), clazz));
            }
            return result;
        } catch (JsonProcessingException e) {
            logger.error("json字符串转换为map对象报错>>>>>", e);
        }
        return Collections.emptyMap();
    }

    /**
     * 将map对象转为实体类对象
     * @param map Map对象
     * @param clazz 实体类对象
     * @param <T> 泛型T
     * @return 实体类对象
     */
    public static <T> T mapToPojo(Map map, Class<T> clazz) {
        return MAPPER.convertValue(map, clazz);
    }

    /**
     * 将map对象转为json字符串
     * @param map map对象
     * @return json字符串
     */
    public static String mapToJson(Map<String, Object> map) {
        try {
            return MAPPER.writeValueAsString(map);
        } catch (Exception e) {
            logger.error("", e);
        }
        return null;
    }

    /**
     * 将obj对象转为实体类对象
     * @param obj Object对象
     * @param clazz 实体类对象
     * @param <T> 泛型T
     * @return 实体类对象
     */
    public static <T> T objToPojo(Object obj, Class<T> clazz) {
        return MAPPER.convertValue(obj, clazz);
    }

}
