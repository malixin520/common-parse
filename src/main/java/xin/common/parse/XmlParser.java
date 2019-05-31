package xin.common.parse;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import xin.common.converter.*;
import xin.common.handler.DefaultFieldValueHandler;
import xin.common.parse.xml.annotation.XmlField;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
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
@Slf4j
public class XmlParser extends DefaultFieldValueHandler implements Parser {

    @Override
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

    private <T> T doParse(Document document, Class<T> clazz)
            throws IllegalAccessException, IllegalArgumentException, ConvertException, InstantiationException {
        final Field[] fields = clazz.getDeclaredFields();
        T bean = clazz.newInstance();
        for (final Field field : fields) {
            XmlField xmlField =  field.getAnnotation(XmlField.class);
            if(xmlField != null){
                String tag = StringUtils.isNoneBlank(xmlField.name()) ? xmlField.name() : field.getName();
                NodeList nodeList = document.getElementsByTagName(tag);
                String source = null;
                if(nodeList!= null && nodeList.getLength() > 0){
                    source = document.getElementsByTagName(tag).item(0).getTextContent();
                    source = StringUtils.isBlank(source) ? null :source;
                }
                setValue(field,bean, source,xmlField.format(),xmlField.scale(),xmlField.roundingMode());

            }
        }
        return bean;
    }
}
