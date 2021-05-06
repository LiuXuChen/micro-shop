package com.jetco.shop.bean;

/**
 * <p>
 *     swagger2参数类型，方便Swagger 中 @ApiImplicitParam(paramType = ApiParamType.HEADER)
 * </p>
 *
 * @author lhw
 * @since 2020-05-04
 * @version 1.0
 *
 */
public final class ApiParamType {

    private ApiParamType() {
    }

    public static final String QUERY = "query";
    public static final String HEADER = "header";
    public static final String PATH = "path";
    public static final String BODY = "body";
    public static final String FORM = "form";
    public static final String FILE = "file";

}
