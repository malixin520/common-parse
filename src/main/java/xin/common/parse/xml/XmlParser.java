package xin.common.parse.xml;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Document;
import xin.common.converter.*;
import xin.common.handler.DefaultFieldConverterHandler;
import xin.common.handler.FieldConverterHandler;
import xin.common.parse.xml.annotation.XmlField;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.math.BigDecimal;

/**
 * <pre>
 * 自定义 xml 解析器。
 * </pre>
 *
 * @author lixin_ma@outlook.com
 * @version 1.0
 * @since 2019/5/14
 */
@Slf4j
public class XmlParser implements FieldValueSetter {

    private final FieldConverterHandler handler;


    public XmlParser(){
        handler = new DefaultFieldConverterHandler();
        handler.registerConverter(byte.class,new ByteFieldConverter());
        handler.registerConverter(Byte.class,new ByteFieldConverter());
        handler.registerConverter(short.class,new ShortFieldConverter());
        handler.registerConverter(Short.class,new ShortFieldConverter());
        handler.registerConverter(long.class,new LongFieldConverter());
        handler.registerConverter(Long.class,new LongFieldConverter());
        handler.registerConverter(float.class,new FloatFieldConverter());
        handler.registerConverter(Float.class,new FloatFieldConverter());
    }


    public <T> T parse(InputStream is,Class<T> clazz) throws xin.common.parse.ParseException {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(is);
            return doParse(document,clazz);
        } catch (Exception e) {
            throw new xin.common.parse.ParseException(e.getMessage(),e);
        }
    }

    public <T> T parse(String path,Class<T> clazz) throws xin.common.parse.ParseException {
        try {
            InputStream is = new FileInputStream(new File(path));
            return parse(is,clazz);
        } catch (Exception e) {
            throw new xin.common.parse.ParseException(e.getMessage(),e);
        }

    }

    private <T> T doParse(Document document, Class<T> clazz)
            throws IllegalAccessException, IllegalArgumentException, ConvertException, InstantiationException {
        final Field[] fields = clazz.getDeclaredFields();
        T bean = clazz.newInstance();
        for (final Field field : fields) {
            XmlField xmlField =  field.getAnnotation(XmlField.class);
            if(xmlField != null){
                String tag = StringUtils.isNoneBlank(xmlField.name()) ? xmlField.name() : field.getName();
                String source = document.getElementsByTagName(tag).item(0).getTextContent();
                setValue(field,bean, source,xmlField.format(),xmlField.scale(),xmlField.roundingMode());
            }
        }
        return bean;
    }

    @Override
    public void setValue(Field field, Object bean, String source,String format,String scale,int roundMode)
            throws IllegalAccessException,IllegalArgumentException,ConvertException {
        Class<?> converterClass;
        if (field.getType().isArray()) {
            converterClass = Array.class;
        } else {
            converterClass = field.getType();
        }
        field.setAccessible(true);
        FieldValueConverter converter = handler.getFieldConverter(converterClass);
        if (converter != null) {
            Object obj = null;
            if(converter instanceof DateFieldConverter && StringUtils.isNotBlank(format)){
                DateFieldConverter dateConverter= (DateFieldConverter) converter;
                obj = dateConverter.toDate(source, field,format);
            }else if(converter instanceof BigDecimalFieldConverter && StringUtils.isNotBlank(scale)){
                BigDecimalFieldConverter dateConverter= (BigDecimalFieldConverter) converter;
                obj = dateConverter.toBigDecimal(source, field,Integer.valueOf(scale),roundMode);
            }else{
                obj= converter.toObject(source, field);
            }
            field.set(bean, obj);
            return ;
        }
        field.set(bean, source);//默认按String类型处理
    }
}
