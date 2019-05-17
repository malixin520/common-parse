import lombok.Data;
import lombok.ToString;
import xin.common.parse.excel.annotation.CellConfig;
import xin.common.parse.excel.annotation.DataType;
import xin.common.parse.xml.annotation.XmlField;

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

    @CellConfig(index = 0)
    @XmlField
    private Long id;

    @CellConfig(index = 1,required = true)
    @XmlField
    private String name;

    @CellConfig(index = 2)
    @XmlField
    private String sex;

    @CellConfig(index = 3)
    @XmlField
    private Integer age;

    @CellConfig(index = 4,required = true)
    @XmlField
    private Float height;

    @CellConfig(index = 5)
    @XmlField
    private String address;

    @CellConfig(index = 6)
    @XmlField(format = "yyyy/MM/dd")
    private Date birthday;

    @CellConfig(index = 7)
    @XmlField
    private BigDecimal salary;

    @CellConfig(index = 8)
    @XmlField(scale = "2",roundingMode = BigDecimal.ROUND_FLOOR)
    private BigDecimal monthSalary;

    @CellConfig(index = 9)
    @XmlField(scale = "4")
    private BigDecimal yearSalary;

    @CellConfig(index = 10)
    @XmlField(name = "ismarriage")
    private Boolean isMarriage;

    @CellConfig(index = 11,dataType=DataType.date,pattern = "yyyy/MM/dd HH:mm")
    @XmlField(format = "yyyy-MM-dd HH:mm:ss")
    private Date crtTime;

    /**
     * 非xml解析字段
     */
    @CellConfig(index = 12)
    private String remark;
}
