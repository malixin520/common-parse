## 解析工具包

解析xml，excel、csv、txt等格式的文件数据为Java Bean

## Demo

### xml解析

使用`xin.common.xml.annotation.XmlField`注解标注`bean`的属性

 **实体bean**

```java
import lombok.Data;
import lombok.ToString;
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

    /**
     * 日期类型
     * 可以通过format属性 设置转换需要的日期格式。
     */
    @XmlField(format = "yyyy/MM/dd")
    private Date birthday;

    @XmlField
    private BigDecimal salary;

    /**
     * BigDecimal类型
     * 如果有精度要求，通过 scal 属性设置精度，通过 roundingMode 属性设置舍入模式
     * 当输入精度后，舍入模式 如果不输入，默认使用4 ，即 ROUND_HALF_UP
     */
    @XmlField(scale = "2",roundingMode = BigDecimal.ROUND_FLOOR)
    private BigDecimal monthSalary;

    @XmlField(scale = "4")
    private BigDecimal yearSalary;
 	
    /**
     * name -映射xml中的tag名称，为空时，默认取字段字段名
     */
    @XmlField(name = "ismarriage")
    private Boolean isMarriage;

    /**
     * 日期类型
     * 可以通过format属性 设置转换需要的日期格式
     */
    @XmlField(format = "yyyy-MM-dd HH:mm:ss")
    private Date crtTime;

    /**
     * 非解析字段
     */
    private String remark;
}
```

**xml文件**

```xml
<?xml version="1.0" encoding="utf-8" standalone="yes" ?>
<person>
    <id>1567890</id>
    <name>张三</name>
    <sex>男</sex>
    <age>28</age>
    <height>1.75</height>
    <address>北京市海淀区</address>
    <birthday>1991/10/04</birthday>
    <salary>12345.4325</salary>
    <monthSalary>370,362.975</monthSalary>
    <yearSalary>4,444,355.7</yearSalary>
    <ismarriage>false</ismarriage>
    <crtTime>2019-05-14 21:29:44</crtTime>
</person>
```

**解析demo**

```java
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import xin.common.parse.XmlParser;

/**
 * <pre>
 * 测试解析
 * </pre>
 *
 * @author lixin_ma@outlook.com
 * @version 1.0
 * @since 2019/5/14 21:49
 */
@Slf4j
public class TestParse {

    @Test
    public void test(){
        String path = getClass().getResource("/person.xml").getFile();
        XmlParser parser = new XmlParser();
        Person person = parser.parse(path,Person.class);
        log.info("parse test resource person.xml to java Person bean :{}",person.toString());
        Assert.assertNotNull("Person is null",person);
        Assert.assertNotNull("Person attr salary is wrong","12345.4325".equals(person.getYearSalary().toPlainString()));
        Assert.assertNotNull("Person attr monthSalary is wrong","370362.97".equals(person.getYearSalary().toPlainString()));
        Assert.assertNotNull("Person attr yearSalary is wrong","4444355.7000".equals(person.getYearSalary().toPlainString()));
    }
}
```

person解析后各字段值：

```json
{
    "id": 1567890, 
    "name": "张三", 
    "sex": "男", 
    "age": 28, 
    "height": 1.75, 
    "address": "北京市海淀区", 
    "birthday": "Fri Oct 04 00:00:00 CST 1991", 
    "salary": 12345.4325, 
    "monthSalary": 370362.97, 
    "yearSalary": 4444355.7, 
    "isMarriage": false, 
    "crtTime": "Tue May 14 21:29:44 CST 2019", 
    "remark": null
}
```

### Excel

待整理

### CSV

待整理

### Txt

待整理



