package xin.common.converter;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *  抽象转换器
 * </pre>
 *
 * @author lixin_ma@outlook.com
 * @version 1.0
 * @since 2018/11/20
 */
public abstract class AbstractFieldValueConverter implements FieldValueConverter {
    protected List<Class<?>> supportClazzs = new ArrayList<>();

    @Override
    public boolean canConvert(Class<?> clazz) {
        boolean isSup = false;
        for (Class<?> cls : supportClazzs) {
            if (cls == clazz) {
                isSup = true;
                break;
            }
        }
        return isSup;
    }
}