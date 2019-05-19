package xin.common.parse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import xin.common.parse.excel.ExcelHandler;
import xin.common.parse.excel.annotation.CellConfig;
import xin.common.parse.excel.annotation.TableConfig;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <pre>
 * excel 导出器
 * </pre>
 *
 * @author lixin_ma@outlook.com
 * @version 1.1
 * @since 2019/5/19 13:01
 */
public class ExcelExporter extends ExcelHandler {

    /**
     * <pre>
     * 说明:导出excel
     * </pre>
     *
     * @param beans java bean 集合
     * @param fos 输出流
     * @param title 标题
     * @param sheet 工作簿名称,默认：sheet1
     * @param target 目标类
     * @author lixin_ma@outlook.com
     * @since 2018/11/20
     */
    public <T> void parseBeansToExcel(List<T> beans, FileOutputStream fos, String title, String sheet, Class<T> target) throws IOException, InvalidFormatException {
        Workbook workbook = parseBeansToWorkbook(beans, title, sheet,target);
        workbook.write(fos);
        workbook.close();
    }

    /**
     * <pre>
     * 说明:导出excel
     * </pre>
     *
     * @param beans java bean 集合
     * @param filename 文件名称
     * @param title 标题
     * @param sheet 工作簿名称,默认：sheet1
     * @param target 目标类
     * @author lixin_ma@outlook.com
     * @since 2018/11/20
     */
    public <T> void parseBeansToExcel(List<T> beans, String filename, String title, String sheet, Class<T> target) throws IOException, InvalidFormatException {
        File file = new File(filename);
        if (!file.exists()) {
            file.createNewFile();
        }
        parseBeansToExcel(beans,new FileOutputStream(file),title,sheet,target);
    }

    /**
     * <pre>
     * 说明:导出excel
     * </pre>
     * @param beans java bean 集合
     * @param title 标题
     * @param sheetName 工作簿名称,默认：sheet1
     * @param target 目标类
     * @param <T>
     * @return
     * @throws IOException
     * @throws InvalidFormatException
     */
    public <T> Workbook parseBeansToWorkbook(List<T> beans, String title,String sheetName, Class<T> target) throws IOException, InvalidFormatException {
        Workbook workbook = new SXSSFWorkbook();
        Sheet sheet = workbook.createSheet(StringUtils.isNotBlank(sheetName)?sheetName:"sheet1");
        TableConfig tableConfig = target.getAnnotation(TableConfig.class);
        if (tableConfig != null) {
            String[] exclude = tableConfig.exclude();
            if (exclude.length != 0) {
                excludes.addAll(Arrays.asList(exclude));
            }
        }

        setDefaultStyle(sheet);

        Field[] fields = target.getDeclaredFields();
        Class<?> superClazz = target.getSuperclass();
        Field[] superClazzFields = superClazz.getDeclaredFields();

        List<Field> temp = new ArrayList<>();
        temp.addAll(Arrays.asList(fields));
        temp.addAll(Arrays.asList(superClazzFields));
        temp.sort((Field f1, Field f2) -> {
            CellConfig c1 = f1.getAnnotation(CellConfig.class);
            CellConfig c2 = f2.getAnnotation(CellConfig.class);
            int result = 0;
            if (c1 == null || c2 == null) {
                return result;
            }
            if (c1.priority() > c2.priority()) {
                result = 1;
            } else {
                if (c1.priority() < c2.priority()) {
                    result = -1;
                }
            }
            return result;
        });
        fields = temp.toArray(fields);
        Field.setAccessible(fields, true);

        if (StringUtils.isNotBlank(title)) {
            createTitle(workbook, sheet, fields, title);
        }

        createHeadRow(workbook, sheet, fields);
        beansToRow(workbook, sheet, beans, fields);
        return workbook;
    }

}
