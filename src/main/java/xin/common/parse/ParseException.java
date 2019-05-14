package xin.common.parse;

/**
 * <pre>
 * 解析异常
 * </pre>
 *
 * @author lixin_ma@outlook.com
 * @since 2019/5/14
 */
public class ParseException  extends RuntimeException{

    public ParseException() {
    }

    public ParseException(String message) {
        super(message);
    }

    public ParseException(Throwable cause) {
        super(cause);
    }

    public ParseException(String message, Throwable cause) {
        super(message, cause, true, true);
    }
}
