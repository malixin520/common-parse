package xin.common.excel.annotation;

import java.lang.annotation.*;

/**
 * <pre>
 * 表格配置注解
 * </pre>
 *
 * @author lixin_ma@outlook.com
 * @version 1.1
 * @since 2019/5/15
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TableConfig {

    /**
     * <pre>
     * 说明:起始行下标，从0开始，默认1
     * </pre>
     *
     * @return
     * @author lixin_ma@outlook.com
     * @since 2019/5/15
     */
    int startRowNum() default 1;

    /**
     * <pre>
     * 说明 : 导出Excel时要忽略的字段(仅限CellConfig标识导出的字段)1
     * </pre>
     *
     * @return
     */
    String[] exclude() default {};

}