package xin.common.parse.xml;

import org.w3c.dom.Document;
import xin.common.converter.ConvertException;
import xin.common.converter.FieldValueConverter;
import xin.common.converter.FieldValueSetter;
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

/**
 * <pre>
 * 自定义 xml 解析器。
 * </pre>
 *
 * @author lixin_ma@outlook.com
 * @version 1.0
 * @since 2019/5/14
 */
public class XmlParser implements FieldValueSetter {

    private FieldConverterHandler handler = new DefaultFieldConverterHandler();


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
        T t = clazz.newInstance();
        for (final Field field : fields) {
            XmlField xmlField =  field.getAnnotation(XmlField.class);
            if(xmlField != null){
                String tag = xmlField.name();
                String text = document.getElementsByTagName(tag).item(0).getTextContent();
                setValue(field, t, text);
            }
        }
        return t;
    }

    @Override
    public void setValue(Field field, Object bean, String source)
            throws IllegalAccessException,IllegalArgumentException,ConvertException {
        Class<?> converterClass;
        if (field.getType().isArray()) {
            converterClass = Array.class;
        } else {
            converterClass = field.getType();
        }
        FieldValueConverter converter = handler.getFieldConverter(converterClass);
        if (converter != null) {
            Object obj = converter.toObject(source, field);
            field.set(bean, obj);
            return;
        }
        field.set(bean, source);
    }
}
