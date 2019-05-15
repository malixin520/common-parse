package xin.common.xml.annotation;

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

    /**
     * xml 标签名称，默认为空，取字段名
     * @return
     */
    String name() default "";

    /**
     * 日期类型时的format
     * @return
     */
    String format() default "";

    /**
     * 数字类型的精度
     * @return
     */
    String scale() default "";

    /**
     * 舍入模式：默认4
     * @return
     */
    int  roundingMode() default 4;
}
