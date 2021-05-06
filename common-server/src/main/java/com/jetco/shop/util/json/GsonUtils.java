package com.jetco.shop.util.json;

import cn.hutool.core.date.DatePattern;
import com.google.common.reflect.TypeToken;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * Gson是Google公司发布的一个开发源码的Java库，可用于将Java对象转换为JSON字符串，也可用于将JSON字符串转换为对应的Java对象。
 *
 * 解析效率较快，解析很全面，不用依赖其他jar包
 *
 * </p>
 *
 * @author lhw
 * @version 1.0
 * @since 2020-11-11
 */
public class GsonUtils {


    private static Gson gson;

    static {
        gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .setDateFormat(DatePattern.NORM_DATETIME_PATTERN)
                .create();
    }

    private GsonUtils() {}

    /**
     * 将对象转成json格式
     *
     * @param object Object对象
     * @return String
     */
    public static String objToStr(Object object) {
        String gsonString = null;
        if (gson != null) {
            gsonString = gson.toJson(object);
        }
        return gsonString;
    }

    /**
     * 将json转成特定的clazz的对象
     *
     * @param <T>        参数类型
     * @param jsonStr    json字符串
     * @param clazz      实体类对象
     * @return 实体类对象
     */
    public static <T> T jsonToObj(String jsonStr, Class<T> clazz) {
        T t = null;
        if (gson != null) {
            //传入json对象和对象类型,将json转成对象
            t = gson.fromJson(jsonStr, clazz);
        }
        return t;
    }

    /**
     * 将json字符串转换为特定的clazz对象集合
     *
     * @param <T>        参数类型
     * @param jsonStr    json字符串
     * @return 实体类对象集合
     */
    public static <T> List<T> jsonToList(String jsonStr) {
        List<T> list = null;
        if (gson != null) {
            //根据泛型返回解析指定的类型,TypeToken<List<T>>{}.getType()获取返回类型
            list = gson.fromJson(jsonStr, new TypeToken<List<T>>() {}.getType());
        }
        return list;
    }

    /**
     * 将json字符串转成list中有map的集合
     *
     * @param <T>        泛型
     * @param jsonStr    json字符串
     * @return list对象
     */
    public static <T> List<Map<String, T>> jsonToListMaps(String jsonStr) {
        List<Map<String, T>> list = null;
        if (gson != null) {
            list = gson.fromJson(jsonStr, new TypeToken<List<Map<String, T>>>() {}.getType());
        }
        return list;
    }

    /**
     * 将json字符串转成map对象
     *
     * @param <T>        泛型
     * @param jsonStr    json字符串
     * @return Map对象
     */
    public static <T> Map<String, T> jsonToMap(String jsonStr) {
        Map<String, T> map = null;
        if (gson != null) {
            map = gson.fromJson(jsonStr, new TypeToken<Map<String, T>>() {}.getType());
        }
        return map;
    }

}
