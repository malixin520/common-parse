package xin.common.converter;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.RoundingMode;


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


    /**
     *
     * @param source 源
     * @param field Field
     * @param scale 精度
     * @param roundMode 舍入模式  {@linkplain RoundingMode}
     * @return
     * @throws ConvertException
     */
    public Object toBigDecimal(String source, Field field,int scale,int roundMode) throws ConvertException{
        BigDecimal target = (BigDecimal) toObject(source,field);
        return target.setScale(scale,roundMode);
    }

}