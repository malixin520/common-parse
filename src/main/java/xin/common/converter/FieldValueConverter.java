package xin.common.converter;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;


/**
 * <pre>
 * 转换器顶层接口
 * </pre>
 *
 * @author lixin_ma@outlook.com
 * @version 1.0
 * @since 2018年11月20日
 */
public interface FieldValueConverter {

    /**
     *
     * <pre>
     * 说明:测试Class是否可以被转换
     * </pre>
     * @param clazz
     * @return
     * @author lixin_ma@outlook.com
     * @since 2018年11月20日
     */
    boolean canConvert(Class<?> clazz);

    /**
     *
     * <pre>
     * 说明:将String类型的source转成为指定的Class对象
     * </pre>
     * @param source
     * @param field
     * @return
     * @author lixin_ma@outlook.com
     * @since 2018年11月20日
     */
    Object toObject(String source, Field field) throws ConvertException;


    Date toDate(String source, Field field, String format) throws ConvertException;

    /**
     * 转换为BigDecimal
     * @param source 源
     * @param field Field
     * @param scale 精度
     * @param roundMode 舍入模式  {@linkplain RoundingMode}
     * @return  BigDecimal
     * @throws ConvertException 类型转换异常<br>{@link ArithmeticException} - if {@code roundingMode==ROUND_UNNECESSARY}
     *         and the specified scaling operation would require rounding. <br> {@link IllegalArgumentException} - if {@code roundingMode} does not
     *         represent a valid rounding mode.
     */
    BigDecimal toBigDecimal(String source, Field field, int scale, int roundMode) throws ConvertException;

}