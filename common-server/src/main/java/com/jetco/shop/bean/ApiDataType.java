package com.jetco.shop.bean;

/**
 * <p>
 *     swagger2数据类型,方便Swagger 中 @ApiImplicitParam(dataType = ApiDataType.STRING)
 * </p>
 *
 * @author lhw
 * @since 2020-05-04
 * @version 1.0
 *
 */
public final class ApiDataType {

    private ApiDataType(){}

    public static final String STRING = "String";
    public static final String INT = "int";
    public static final String LONG = "long";
    public static final String DOUBLE = "double";
    public static final String FLOAT = "float";
    public static final String BYTE = "byte";
    public static final String BOOLEAN = "boolean";
    public static final String ARRAY = "array";
    public static final String BINARY = "binary";
    public static final String DATETIME = "dateTime";

}
