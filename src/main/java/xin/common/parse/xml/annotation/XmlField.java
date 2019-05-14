package xin.common.parse.xml.annotation;

import java.lang.annotation.*;

/**
 * <pre>
 * 自定义xml解析注解
 * </pre>
 *
 * @author lixin_ma@outlook.com
 * @version 1.0
 * @since 2019/4/20 20:39
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public  @interface XmlField {
    String name() default "";
}
