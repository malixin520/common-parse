package xin.common.handler;

import xin.common.converter.*;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 *   默认字段转换句柄
 * </pre>
 *
 * @author lixin_ma@outlook.com
 * @version 1.0
 * @since 2018/11/20
 */
public class DefaultFieldConverterHandler implements FieldConverterHandler {
    private static final Map<Class<?>, FieldValueConverter>
            defaultLocalConverterMapper = Collections.synchronizedMap(new HashMap<>());

    private final Map<Class<?>, FieldValueConverter> localConverterMapper;

    static {
        /**
         * 内置初始化的转换器
         */
        FieldValueConverter converter;
        converter = new IntegerFieldConverter();
        defaultLocalConverterMapper.put(int.class, converter);
        defaultLocalConverterMapper.put(Integer.class, converter);
        converter = new DoubleFieldConverter();
        defaultLocalConverterMapper.put(Double.class, converter);
        defaultLocalConverterMapper.put(double.class, converter);
        converter = new BooleanFieldConverter();
        defaultLocalConverterMapper.put(Boolean.class, converter);
        defaultLocalConverterMapper.put(boolean.class, converter);
        converter = new CharacterFieldConverter();
        defaultLocalConverterMapper.put(char.class, converter);
        defaultLocalConverterMapper.put(Character.class, converter);
        defaultLocalConverterMapper.put(Date.class, new DateFieldConverter());
        defaultLocalConverterMapper.put(Array.class, new ArrayFieldConverter());

        converter = new BigDecimalFieldConverter();
        defaultLocalConverterMapper.put(BigDecimal.class, converter);
    }

    public DefaultFieldConverterHandler() {
        localConverterMapper = Collections
                .synchronizedMap(new HashMap<Class<?>, FieldValueConverter>());
        localConverterMapper.putAll(defaultLocalConverterMapper);
    }

    @Override
    public FieldValueConverter getFieldConverter(Class<?> clazz) {
        return localConverterMapper.get(clazz);
    }

    public static FieldValueConverter getLocalFieldConverter(Class<?> clazz) {
        return defaultLocalConverterMapper.get(clazz);
    }

    @Override
    public void registerConverter(Class<?> clazz, FieldValueConverter converter) {
        if (!converter.canConvert(clazz)) {
            throw new IllegalArgumentException("不支持的类型：" + clazz.getName());
        }
        localConverterMapper.put(clazz, converter);
    }

}