package xin.common.converter;

import java.lang.reflect.Field;

/**
 * <pre>
 * setter
 * </pre>
 *
 * @author lixin_ma@outlook.com
 * @version 1.0
 * @since 2019/5/14
 */
@FunctionalInterface
public interface FieldValueSetter {

    /**
     * Setter - 默认按String类型处理
     *
     * @param field 字段
     * @param bean 实体Bean
     * @param source 源
     * @param format 日期格式
     * @param scale 数字精度
     * @param roundMode 舍入模式
     * @throws IllegalAccessException
     * @throws ConvertException 当使用反射转换类型时，错误的数据格式等会出现此错误
     */
    void setFieldValue(Field field, Object bean, String source,String format,String scale,Integer roundMode) throws ConvertException;
}
