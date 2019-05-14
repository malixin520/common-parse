package xin.common.converter;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;

/**
 * <pre>
 *   整型 转换器
 * </pre>
 *
 * @author lixin_ma@outlook.com
 * @version 1.0
 * @since 2018/11/20
 */
public class IntegerFieldConverter extends AbstractFieldValueConverter {

    {
        supportClazzs.add(int.class);
        supportClazzs.add(Integer.class);
    }


    @Override
    public Object toObject(String source, Field field) throws ConvertException {
        try {
            if (StringUtils.isBlank(source)) {
                return null;
            }
            return Integer.decode(source);
        } catch (Exception e) {
            throw new ConvertException(e.getMessage(),e);
        }
    }

}