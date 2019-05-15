import lombok.Data;
import lombok.ToString;
import xin.common.excel.annotation.CellConfig;
import xin.common.xml.annotation.XmlField;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <pre>
 * 测试试实体-Person
 * </pre>
 *
 * @author lixin_ma@outlook.com
 * @version 1.0
 * @since 2019/5/14 21:36
 */
@Data
@ToString
public class Person {

    @CellConfig(index = -1)
    @XmlField
    private Long id;

    @XmlField
    private String name;

    @XmlField
    private String sex;

    @XmlField
    private Integer age;

    @XmlField
    private Float height;

    @XmlField
    private String address;

    @XmlField(format = "yyyy/MM/dd")
    private Date birthday;

    @XmlField
    private BigDecimal salary;

    @XmlField(scale = "2",roundingMode = BigDecimal.ROUND_FLOOR)
    private BigDecimal monthSalary;

    @XmlField(scale = "4")
    private BigDecimal yearSalary;

    @XmlField(name = "ismarriage")
    private Boolean isMarriage;

    @XmlField(format = "yyyy-MM-dd HH:mm:ss")
    private Date crtTime;

    /**
     * 非解析字段
     */
    private String remark;
}
