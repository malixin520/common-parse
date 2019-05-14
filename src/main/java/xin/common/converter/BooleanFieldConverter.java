package xin.common.converter;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;

/**
 * <pre>
 *   布尔 转换器
 * </pre>
 *
 * @author lixin_ma@outlook.com
 * @version 1.0
 * @since 2018/11/20
 */
@AllArgsConstructor
public class BooleanFieldConverter extends AbstractFieldValueConverter {
    private static final String DEFAULT_TRUE_CASE_STR = "TRUE";
    private static final String DEFAULT_FALSE_CASE_STR = "FALSE";
    /**
     * true的默认String值
     */
    private final String trueCaseStr;
    /**
     * false的默认String值
     */
    private final String falseCaseStr;
    /**
     * 是否忽略大小写，默认忽略
     */
    private boolean ignoreCase;

    {
        supportClazzs.add(Boolean.class);
        supportClazzs.add(boolean.class);
    }

    public BooleanFieldConverter() {
        this(true);
    }

    public BooleanFieldConverter(boolean ignoreCase) {
        this(DEFAULT_TRUE_CASE_STR, DEFAULT_FALSE_CASE_STR, ignoreCase);
    }

    @Override
    public Object toObject(String source, Field field)  throws ConvertException{
        try {
            if (StringUtils.isBlank(source)) {
                return null;
            }
            validateValue(source);
            boolean result;
            if (ignoreCase) {
                result = source.equalsIgnoreCase(trueCaseStr);
            } else {
                result = source.equals(trueCaseStr);
            }
            return result;
        } catch (Exception e) {
            throw new ConvertException(e.getMessage(),e);
        }
    }

    private void validateValue(String source) {
        /**
         * 校验结果，默认false，有匹配结果则更新为true，表示校验通过
         */
        boolean result = false;
        if (ignoreCase) {
            result = source.equalsIgnoreCase(trueCaseStr) || source.equalsIgnoreCase(falseCaseStr);
        } else {
            result = source.equals(trueCaseStr) || source.equals(falseCaseStr);
        }

        if (!result) {
            throw new IllegalArgumentException("[" + source + "]" + "is not a valid value");
        }
    }
}