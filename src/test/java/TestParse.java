import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import xin.common.parse.xml.XmlParser;

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
        log.info("parse test resource person.xml to java Person bean : {}",person.toString());
        Assert.assertNotNull("Person is null",person);
        Assert.assertNotNull("Person attr salary is wrong","12345.4325".equals(person.getYearSalary().toPlainString()));
        Assert.assertNotNull("Person attr monthSalary is wrong","370362.97".equals(person.getYearSalary().toPlainString()));
        Assert.assertNotNull("Person attr yearSalary is wrong","4444355.7000".equals(person.getYearSalary().toPlainString()));
    }
}
