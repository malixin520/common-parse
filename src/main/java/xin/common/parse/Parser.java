package xin.common.parse;

import xin.common.converter.FieldValueSetter;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * <pre>
 * 解析器顶层接口
 * </pre>
 *
 * @author lixin_ma@outlook.com
 * @version 1.0
 * @since 2019/5/15
 */
public interface Parser extends FieldValueSetter {

    <T> T parse(InputStream is, Class<T> clazz) throws xin.common.parse.ParseException;

    default <T> T parse(String path,Class<T> clazz) throws xin.common.parse.ParseException {
        try {
            InputStream is = new FileInputStream(new File(path));
            return parse(is,clazz);
        } catch (Exception e) {
            throw new xin.common.parse.ParseException(e.getMessage(),e);
        }

    }
}
