package xin.common.converter;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <pre>
 *   双精度浮点 转换器
 * </pre>
 *
 * @author lixin_ma@outlook.com
 * @version 1.0
 * @since 2018/11/20
 */
public class DoubleFieldConverter extends AbstractFieldValueConverter {

    {
        supportClazzs.add(double.class);
        supportClazzs.add(Double.class);
    }

    @Override
    public Object toObject(String source, Field field) throws ConvertException {
        try {
            if (StringUtils.isBlank(source)) {
                return null;
            }
            return Double.valueOf(source);
        } catch (Exception e) {
            throw new ConvertException(e.getMessage(),e);
        }
    }

    @Override
    public Date toDate(String source, Field field, String format) throws ConvertException {
        return null;
    }

    @Override
    public BigDecimal toBigDecimal(String source, Field field, int scale, int roundMode) throws ConvertException {
        return null;
    }

}