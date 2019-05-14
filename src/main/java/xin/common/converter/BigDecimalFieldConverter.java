package xin.common.converter;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.math.BigDecimal;


/**
 * <pre>
 *   BigDecimal 转换器
 * </pre>
 *
 * @author lixin_ma@outlook.com
 * @version 1.0
 * @since 2018/11/20
 */
public class BigDecimalFieldConverter extends AbstractFieldValueConverter {

    {
        supportClazzs.add(byte.class);
        supportClazzs.add(Byte.class);
    }

    @Override
    public Object toObject(String source, Field field) throws ConvertException{
        try {
            if (StringUtils.isBlank(source)) {
                return null;
            }
            source = source.replaceAll(",", "");
            source = source.replaceAll("，", "");
            return new BigDecimal(source);
        } catch (Exception e) {
            throw new ConvertException(e.getMessage(),e);
        }
    }

}