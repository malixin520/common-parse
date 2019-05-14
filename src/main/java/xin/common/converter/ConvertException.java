package xin.common.converter;

/**
 * <pre>
 * 转换异常
 * </pre>
 *
 * @author lixin_ma@outlook.com
 * @version 1.0
 * @since 2019/5/14
 */
public class ConvertException extends RuntimeException {

    public ConvertException() {
    }

    public ConvertException(String message) {
        super(message);
    }

    public ConvertException(Throwable cause) {
        super(cause);
    }

    public ConvertException(String message, Throwable cause) {
        super(message, cause, true, true);
    }
}
