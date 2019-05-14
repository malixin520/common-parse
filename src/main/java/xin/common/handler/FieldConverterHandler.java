package xin.common.handler;


import xin.common.converter.FieldValueConverter;

/**
 * <pre>
 *   转换句柄顶层接口
 * </pre>
 *
 * @author lixin_ma@outlook.com
 * @version 1.0
 * @since 2018/11/20
 */
public interface FieldConverterHandler {

    FieldValueConverter getFieldConverter(Class<?> clazz);

    /**
     *
     * <pre>
     * 说明:注册自定义转换器
     * </pre>
     * @param clazz
     * @param converter
     * @author lixin_ma@outlook.com
     * @since 2018/11/20
     */
    void registerConverter(Class<?> clazz, FieldValueConverter converter);

}