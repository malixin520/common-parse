package xin.common.parse;

import xin.common.handler.FieldValueHandler;

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
public interface Parser extends FieldValueHandler {

    /**
     * 从流中读取数据，解析为Java Bean
     * @param is 输入流
     * @param clazz 目标Clazz
     * @param <T> 实体源
     * @return 单一实体实例
     * @throws xin.common.parse.ParseException
     */
    <T> T parse(InputStream is, Class<T> clazz) throws xin.common.parse.ParseException;

    /**
     * 从给定文件中读取数据，解析为Java Bean
     * @param path 文件路径
     * @param clazz 目标Clazz
     * @param <T> 实体源
     * @return 单一实体实例
     * @throws xin.common.parse.ParseException
     */
    default <T> T parse(String path,Class<T> clazz) throws xin.common.parse.ParseException {
        try {
            InputStream is = new FileInputStream(new File(path));
            return parse(is,clazz);
        } catch (Exception e) {
            throw new xin.common.parse.ParseException(e.getMessage(),e);
        }
    }
}
