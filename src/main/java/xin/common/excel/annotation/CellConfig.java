package xin.common.excel.annotation;

import java.lang.annotation.*;

/**
 * <pre>
 * 单元格配置注解
 * </pre>
 *
 * @author lixin_ma@outlook.com
 * @version 1.1
 * @since 2019/5/15
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface CellConfig {

    /**
     *
     * <pre>
     * 说明:下标值，从0开始<br/>
     * index值为-1，表示解析excel时去根据这个字段取数（即这个字段不是excel中的列）
     * </pre>
     * @return
     * @author lixin_ma@outlook.com
     * @since 2019/5/15
     */
    int index();

    /**
     *
     * <pre>
     * 说明:解析excel cell的必填标识，用于限制参数是否为必填。true为必填，默认false
     * </pre>
     * @return
     * @author lixin_ma@outlook.com
     * @since 2019/5/15
     */
    boolean required() default false;

    /**
     *
     * <pre>
     * 说明:导出excel，字段是否忽略。true为忽略，默认false
     * </pre>
     * @return
     * @author lixin_ma@outlook.com
     * @since 2019/5/15
     */
    boolean omit() default false;

    /**
     *
     * <pre>
     * 说明:导出excel，title的名称。默认""，导出时取用属性名
     * </pre>
     * @return
     * @author lixin_ma@outlook.com
     * @since 2019/5/15
     */
    String aliasName() default "";

    /**
     * 状态值描述(JSON字符串)
     * @return
     */
    String statusValue() default "{}";

    /**
     * 数据类型[string/currency/date]
     * @return
     */
    String dataType() default "string";

    /**
     * 格式化模板，与dataType配套使用
     * @return
     */
    String pattern() default "";

    /**
     * 顺序权值，默认-1，不排序
     * @return
     */
    int priority() default -1;

}