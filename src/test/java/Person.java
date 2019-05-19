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

    @CellConfig(aliasName = "编号",index = 0)
    @XmlField
    private Long id;

    @CellConfig(aliasName = "姓名",index = 1,required = true)
    @XmlField
    private String name;

    @CellConfig(aliasName = "性别",index = 2)
    @XmlField
    private String sex;

    @CellConfig(aliasName = "年龄",index = 3)
    @XmlField
    private Integer age;

    @CellConfig(aliasName = "身高",index = 4,required = true)
    @XmlField
    private Float height;

    @CellConfig(aliasName = "地址",index = 5)
    @XmlField
    private String address;

    @CellConfig(aliasName = "出生日期",index = 6,dataType=DataType.date,pattern = "yyyy/MM/dd")
    @XmlField(format = "yyyy/MM/dd")
    private Date birthday;

    @CellConfig(aliasName = "工资",index = 7,dataType=DataType.currency)
    @XmlField
    private BigDecimal salary;

    @CellConfig(index = 8,omit = true)
    @XmlField(scale = "2",roundingMode = BigDecimal.ROUND_FLOOR)
    private BigDecimal salary1;

    @CellConfig(index = 9,omit = true)
    @XmlField(scale = "4")
    private BigDecimal salary2;

    @CellConfig(aliasName = "婚姻状态",index = 10,statusValue = "{true: {text:\"已婚\"},false:{text:\"未婚\"}}")
    @XmlField(name = "ismarriage")
    private Boolean isMarriage;

    @CellConfig(index = 11,omit = true)
    @XmlField(format = "yyyy-MM-dd HH:mm:ss")
    private Date crtTime;

    /**
     * 非xml解析字段
     */
    @CellConfig(aliasName = "备注",index = 12)
    private String remark;
}
