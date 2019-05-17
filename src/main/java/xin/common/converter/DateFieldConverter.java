package xin.common.converter;
import lombok.AllArgsConstructor;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <pre>
 *   Date 转换器
 * </pre>
 *
 * @author lixin_ma@outlook.com
 * @version 1.0
 * @since 2018/11/20
 */
@AllArgsConstructor
public class DateFieldConverter extends AbstractFieldValueConverter {

    private SimpleDateFormat DEFAULT_DATE_FORMAT;

    {
        supportClazzs.add(Date.class);
    }

    public DateFieldConverter() {
        this(new SimpleDateFormat("yyyy-MM-dd"));
    }

    public DateFieldConverter(String format) {
        this(new SimpleDateFormat(format));
    }

    @Override
    public Object toObject(String source, Field field) throws ConvertException {
        try {
            return DEFAULT_DATE_FORMAT.parse(source);
        } catch (ParseException e) {
            throw new ConvertException(e.getMessage(),e);
        }
    }

    public Date toDate(String source, Field field,String format) throws ConvertException {
        try {
            return new SimpleDateFormat(format).parse(source);
        } catch (ParseException e) {
            throw new ConvertException(e.getMessage(),e);
        }
    }

    @Override
    public BigDecimal toBigDecimal(String source, Field field, int scale, int roundMode) throws ConvertException {
        return null;
    }
}