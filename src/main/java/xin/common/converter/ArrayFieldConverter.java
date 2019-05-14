package xin.common.converter;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import xin.common.handler.DefaultFieldConverterHandler;

import java.lang.reflect.Array;
import java.lang.reflect.Field;


/**
 * <pre>
 *  数组转换器
 * </pre>
 *
 * @author lixin_ma@outlook.com
 * @version 1.0
 * @since 2018/11/20
 */
@AllArgsConstructor
public class ArrayFieldConverter extends AbstractFieldValueConverter {

    private String separator;

    {
        supportClazzs.add(Array.class);
    }

    public ArrayFieldConverter() {
        this(",");
    }

    @Override
    public Object toObject(String source, Field field) throws ConvertException {
        try {
            if (StringUtils.isBlank(source)) {
                return null;
            }
            String[] sources = source.split(separator);
            Class<?> actualType = field.getType().getComponentType();
            Object array = Array.newInstance(actualType, sources.length);
            for (int i = 0; i < sources.length; i++) {
                Object data = sources[i];
                FieldValueConverter converter = DefaultFieldConverterHandler.getLocalFieldConverter(actualType);
                if (converter != null) {
                    data = converter.toObject(sources[i], field);
                }
                Array.set(array, i, data);
            }
            return array;
        } catch (Exception e) {
            throw new ConvertException(e.getMessage(),e);
        }
    }

}