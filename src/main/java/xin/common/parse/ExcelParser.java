package xin.common.parse;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import xin.common.excel.ExcelHanlder;
import xin.common.excel.annotation.TableConfig;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * <pre>
 * excel 解析器
 * </pre>
 *
 * @author lixin_ma@outlook.com
 * @version 1.1
 * @since 2019/5/15
 */
public class ExcelParser extends ExcelHanlder implements StreamParser {

    @Override
    public <T> Stream<T> parseStream(InputStream is, Class<T> clazz) throws ParseException {
        List<T> beans = new ArrayList<>();
        try {
            Field[] fields = clazz.getDeclaredFields();
            Field.setAccessible(fields, true);

            Workbook workbook = WorkbookFactory.create(is);
            Sheet sheet = workbook.getSheetAt(0);

            TableConfig tableConfig = clazz.getAnnotation(TableConfig.class);
            int startRowNum = START_ROW_NUM;
            if (tableConfig != null) {
                startRowNum = tableConfig.startRowNum();
            }
            int rowNum = sheet.getLastRowNum();

            if (startRowNum > rowNum) {
                IllegalArgumentException e = new IllegalArgumentException("start row index larger than last row index");
                throw  new ParseException(e.getMessage(),e);
            }

            DataFormatter formatter = new DataFormatter();
            for (; startRowNum <= rowNum; startRowNum++) {
                T bean = clazz.newInstance();
                beans.add(bean);
                rowToBean(sheet.getRow(startRowNum), bean, fields, formatter);
            }
            return beans.stream();
        }catch (Exception e){
            throw  new ParseException(e.getMessage(),e);
        }
    }
}
