package xin.common.handler;

import org.apache.commons.lang3.StringUtils;
import xin.common.converter.BigDecimalFieldConverter;
import xin.common.converter.ConvertException;
import xin.common.converter.DateFieldConverter;
import xin.common.converter.FieldValueConverter;

import java.lang.reflect.Array;
import java.lang.reflect.Field;

/**
 * <pre>
 * Default Setter
 * </pre>
 * @Version 1.1
 * @author lixin_ma@outlook.com
 * @since 2019/5/17
 */
public class DefaultFieldValueHandler implements FieldValueHandler {

    private final FieldConverterHandler handler;

    public DefaultFieldValueHandler(){
        handler = new DefaultFieldConverterHandler();
    }

    public void registerConverter(Class<?> clazz, FieldValueConverter converter) {
        handler.registerConverter(clazz, converter);
    }

    @Override
    public void setValue(Field field, Object bean, String source, String format, String scale, Integer roundMode) throws ConvertException {
        try {
            Class<?> converterClass;
            if (field.getType().isArray()) {
                converterClass = Array.class;
            } else {
                converterClass = field.getType();
            }
            field.setAccessible(true);
            FieldValueConverter converter = handler.getFieldConverter(converterClass);
            if (converter != null) {
                Object obj;
                if(converter instanceof DateFieldConverter && StringUtils.isNotBlank(format)){
                    obj = converter.toDate(source, field,format);
                }else if(converter instanceof BigDecimalFieldConverter && StringUtils.isNotBlank(scale)){
                    obj = converter.toBigDecimal(source, field,Integer.valueOf(scale),roundMode);
                }else{
                    obj= converter.toObject(source, field);
                }
                field.set(bean, obj);
                return ;
            }
            field.set(bean, source);
        }catch (Exception e){
            throw  new ConvertException(e.getMessage(),e);
        }
    }
}
