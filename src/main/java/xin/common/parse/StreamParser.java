package xin.common.parse;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.stream.Stream;

/**
 * <pre>
 * 流-{@link Stream} 解析器顶层接口
 * </pre>
 *
 * @author lixin_ma@outlook.com
 * @version 1.1
 * @since 2019/5/15
 */
public interface StreamParser {

    /**
     * 从流中读取数据，解析为Java Bean 的流
     * @param is 输入流
     * @param clazz 目标Clazz
     * @param <T> 实体源
     * @return Java Bean 流 - 方便后续操作
     * @throws xin.common.parse.ParseException
     */
    <T> Stream<T> parseStream(InputStream is, Class<T> clazz) throws xin.common.parse.ParseException;

    /**
     * 从给定文件中读取数据，解析为Java Bean 的流
     * @param path 文件路径
     * @param clazz 目标Clazz
     * @param <T> 实体源
     * @return Java Bean 流 - 方便后续操作
     * @throws xin.common.parse.ParseException
     */
    default <T> Stream<T> parseStream(String path, Class<T> clazz) throws xin.common.parse.ParseException {
        try {
            InputStream is = new FileInputStream(new File(path));
            return parseStream(is,clazz);
        } catch (Exception e) {
            throw new xin.common.parse.ParseException(e.getMessage(),e);
        }
    }
}
