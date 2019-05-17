package xin.common.converter;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Date;


/**
 * <pre>
 *   Character 转换器
 * </pre>
 *
 * @author lixin_ma@outlook.com
 * @version 1.0
 * @since 2018/11/20
 */
public class CharacterFieldConverter extends AbstractFieldValueConverter {

    {
        supportClazzs.add(char.class);
        supportClazzs.add(Character.class);
    }

    @Override
    public Object toObject(String source, Field field) throws ConvertException{
        try {
            if (StringUtils.isBlank(source)) {
                return null;
            }
            if (source.toCharArray().length > 1) {
                throw new IllegalArgumentException(source + " is not a Character type value");
            }
            return source.charAt(0);
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