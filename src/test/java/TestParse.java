import lombok.extern.slf4j.Slf4j;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.Assert;
import org.junit.Test;
import xin.common.converter.DateFieldConverter;
import xin.common.parse.ExcelExporter;
import xin.common.parse.ExcelParser;
import xin.common.parse.XmlParser;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    Stream<Person> stream;

    @Test
    public void testXml(){
        String path = getClass().getResource("/person.xml").getFile();
        XmlParser parser = new XmlParser();
        Person person = parser.parse(path,Person.class);
        Assert.assertNotNull("Person is null",person);
        log.info("parse test resource person.xml to java Person bean : {}",person.toString());
        Assert.assertNotNull("Person attr salary is wrong","12345.4325".equals(person.getSalary().toPlainString()));
        Assert.assertNotNull("Person attr salary1 is wrong","370362.97".equals(person.getSalary2().toPlainString()));
        Assert.assertNotNull("Person attr salary2 is wrong","4444355.7000".equals(person.getSalary2().toPlainString()));
    }


    @Test
    public void testExcel(){
        String path = getClass().getResource("/person.xls").getFile();
        ExcelParser parser = new ExcelParser();
        parser.registerConverter(Date.class,new DateFieldConverter("yyyy/MM/dd"));
        stream = parser.parseStream(path,Person.class);
        Assert.assertNotNull("parse result stream is null",stream);
        List<Person> beans =  stream.collect(Collectors.toList());
        beans.forEach(person -> {
            Assert.assertNotNull("Person is null",person);
            log.info("parse test resource person.xls to java Person bean : {}",person.toString());
        });

        ExcelExporter exporter = new ExcelExporter();
        try {
            String export = getClass().getResource("").getFile() + "export.xls";
            log.info("export : {}",export);
            exporter.parseBeansToExcel(beans,export ,"员工薪资表","薪资",Person.class);
        } catch (IOException | InvalidFormatException e) {
            e.printStackTrace();
        }
    }
}
