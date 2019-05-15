package xin.common.excel;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import xin.common.converter.ConvertException;
import xin.common.converter.FieldValueConverter;
import xin.common.converter.FieldValueSetter;
import xin.common.handler.DefaultFieldConverterHandler;
import xin.common.handler.FieldConverterHandler;
import xin.common.excel.annotation.CellConfig;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <pre>
 * Excel处理类 - 父类
 * </pre>
 *
 * @author lixin_ma@outlook.com
 * @version 1.1
 * @since 2019/5/15
 */
public class ExcelHanlder implements FieldValueSetter {
    protected final static int START_ROW_NUM = 1;
    protected final static int MAX_CELL_WIDTH = 6000;
    protected final static int MIN_CELL_WIDTH = 2000;

    protected FieldConverterHandler handler = new DefaultFieldConverterHandler();

    protected static short headRowBGColorIndex;
    protected static short titleGBColorIndex;
    protected static short oddColorIndex;
    protected static short evenColorIndex;
    protected static XSSFColor oddColor;
    protected static XSSFColor evenColor;
    protected static XSSFColor headRowBGColor;
    protected static XSSFColor titleGBColor;
    protected static HSSFPalette palette;

    static {
        palette = new HSSFWorkbook().getCustomPalette();
        palette.setColorAtIndex(HSSFColor.GREY_40_PERCENT.index, (byte) 165, (byte) 165, (byte) 165);
        headRowBGColorIndex = HSSFColor.GREY_40_PERCENT.index;

        palette.setColorAtIndex(HSSFColor.GREY_50_PERCENT.index, (byte) 237, (byte) 237, (byte) 237);
        oddColorIndex = HSSFColor.GREY_50_PERCENT.index;
        palette.setColorAtIndex(HSSFColor.GREY_80_PERCENT.index, (byte) 255, (byte) 255, (byte) 255);
        evenColorIndex = HSSFColor.GREY_80_PERCENT.index;

        palette.setColorAtIndex(HSSFColor.GREY_80_PERCENT.index, (byte) 91, (byte) 155, (byte) 213);
        titleGBColorIndex = HSSFColor.GREY_80_PERCENT.index;


        headRowBGColor = new XSSFColor(new java.awt.Color(165, 165, 165));
        oddColor = new XSSFColor(new java.awt.Color(237, 237, 237));
        evenColor = new XSSFColor(new java.awt.Color(255, 255, 255));
        titleGBColor = new XSSFColor(new java.awt.Color(91, 155, 213));
    }

    public void registerConverter(Class<?> clazz, FieldValueConverter converter) {
        handler.registerConverter(clazz, converter);
    }

    protected List<String> excludes = new ArrayList<>();

    /**
     * 设置默认工作簿样式-默认背景色，白色
     * @param sheet 工作簿
     */
    protected void setDefaultStyle(Sheet sheet) {
        sheet.getColumnStyle(Integer.MAX_VALUE).setFillBackgroundColor(HSSFColor.WHITE.index);
    }

    /**
     * 格式化转换器，
     * @param val 转换器对象
     * @param cellConfig 单元格配置
     * @return
     */
    protected Object format(Object val, CellConfig cellConfig) {
        String pattern = cellConfig.pattern();
        String dataType = cellConfig.dataType();
        if (StringUtils.isNotBlank(dataType) && val != null) {
            if ("date".equalsIgnoreCase(dataType)) {
                if (StringUtils.isBlank(pattern)) {
                    pattern = "yyyy-MM-dd";
                }
                if (val instanceof LocalDateTime) {
                    val = DateTimeFormatter.ofPattern(pattern).format((LocalDateTime) val);
                } else if (val instanceof Date) {
                    val = new SimpleDateFormat(pattern).format((Date) val);
                }
            } else if ("currency".equalsIgnoreCase(dataType)) {
                if (StringUtils.isBlank(pattern)) {
                    pattern = "¤#,##0.00;-¤#,##0.00";
                }
                val = new DecimalFormat(pattern).format(val);
            }
        }
        return val;
    }

    protected boolean canExport(Field field, CellConfig cellConfig) {
        if (cellConfig == null) {
            cellConfig = field.getAnnotation(CellConfig.class);
        }
        return cellConfig != null;
    }

    /**
     * 最后一个单元格的索引
     * @param row 行
     * @return
     */
    protected int getLastCellNum(Row row) {
        int cellIndex = row.getLastCellNum();
        if (cellIndex < 0) {
            cellIndex = 0;
        }
        return cellIndex;
    }

    /**
     *  设置单元格宽度
     * @param sheet 工作簿
     * @param cellIndex 列索引
     */
    protected void setAutoSizeColumn(Sheet sheet, int cellIndex) {
        ((SXSSFSheet) sheet).trackAllColumnsForAutoSizing();
        sheet.autoSizeColumn(cellIndex);
        float columnWidthInPixels = sheet.getColumnWidth(cellIndex);
        if (columnWidthInPixels > MAX_CELL_WIDTH) {
            sheet.setColumnWidth(cellIndex, MAX_CELL_WIDTH);
        } else if (columnWidthInPixels < MIN_CELL_WIDTH) {
            sheet.setColumnWidth(cellIndex, MIN_CELL_WIDTH);
        }
    }

    /**
     * <pre>
     * 说明:创建表头
     * </pre>
     *
     * @param sheet 工作簿
     * @param fields 属性集
     * @author lixin_ma@outlook.com
     * @since 2019/5/15
     */
    protected void createHeadRow(Workbook workbook, Sheet sheet, Field[] fields) {
        CellRangeAddress rangeAddress = new CellRangeAddress(0, 0, 0, 0);
        int rowIndex = sheet.getLastRowNum() + 1;
        Row row = sheet.createRow(rowIndex);
        CellStyle style = workbook.createCellStyle();


        setFillForegroundColor(style, headRowBGColorIndex, headRowBGColor);

        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        for (Field field : fields) {
            CellConfig cellConfig = field.getAnnotation(CellConfig.class);
            if (cellConfig == null) {
                continue;
            }
            if (cellConfig.omit()
                    && !excludes.contains(field.getName())) {
                int cellIndex = getLastCellNum(row);
                Cell cell = row.createCell(cellIndex);
                String cellVal = cellConfig.aliasName();
                if (StringUtils.isBlank(cellVal)) {
                    cellVal = field.getName();
                }
                cell.setCellValue(cellVal);
                setBorder(style, cell);
                cell.setCellStyle(style);
                setAutoSizeColumn(sheet, cellIndex);
            }
        }
    }

    /**
     * <pre>
     * 说明:创建表格标题
     * </pre>
     *
     * @param workbook book
     * @param sheet 工作簿
     * @param fields 属性集
     * @param title 标题
     * @author lixin_ma@outlook.com
     * @since 2018/11/20
     */
    protected void createTitle(Workbook workbook, Sheet sheet, Field[] fields, String title) {
        int cellCount = 0;
        for (Field field : fields) {
            CellConfig cellConfig = field.getAnnotation(CellConfig.class);
            if (canExport(field, cellConfig) && cellConfig.omit()
                    && !excludes.contains(field.getName())) {
                cellCount++;
            }
        }
        int rowIndex = sheet.getLastRowNum();
        Row row = sheet.createRow(rowIndex);
        int cellIndex = getLastCellNum(row);
        Cell cell = row.createCell(cellIndex);
        int lastCol = cellCount - 1;
        CellRangeAddress rangeAddress = new CellRangeAddress(rowIndex, rowIndex, cellIndex, lastCol);
        sheet.addMergedRegion(rangeAddress);
        cell.setCellValue(title);

        setRegionBorder(rangeAddress, sheet);

        CellStyle style = cell.getCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        Font font = workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 24);
        style.setFont(font);
        style.setFillForegroundColor(HSSFColor.BLUE_GREY.index);

        cell.setCellStyle(style);
        setFillForegroundColor(style, titleGBColorIndex, titleGBColor);
    }

    /**
     * 设置Body样式-隔行换色
     * @param style 样式
     * @param cell 单元格
     * @param rowIndex 行号
     */
    protected void setBodyCellStyle(CellStyle style, Cell cell, int rowIndex) {
        if (rowIndex % 2 == 0) {
            setFillForegroundColor(style, oddColorIndex, oddColor);
        } else {
            setFillForegroundColor(style, evenColorIndex, evenColor);
        }
        setBorder(style, cell);
    }

    /**
     * 设置Border
     * @param style 样式
     * @param cell cell
     */
    protected void setBorder(CellStyle style, Cell cell) {
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        cell.setCellStyle(style);
    }

    protected void setFillForegroundColor(CellStyle style, short colorIndex, XSSFColor color) {
        if (style instanceof XSSFCellStyle) {
            ((XSSFCellStyle) style).setFillForegroundColor(color);
        } else {
            style.setFillForegroundColor(colorIndex);
        }
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setFillBackgroundColor(HSSFColor.WHITE.index);
    }

    protected void setRegionBorder(CellRangeAddress rangeAddress, Sheet sheet) {
        RegionUtil.setBorderTop(BorderStyle.THIN.getCode(), rangeAddress, sheet);
        RegionUtil.setBorderRight(BorderStyle.THIN.getCode(), rangeAddress, sheet);
        RegionUtil.setBorderBottom(BorderStyle.THIN.getCode(), rangeAddress, sheet);
        RegionUtil.setBorderLeft(BorderStyle.THIN.getCode(), rangeAddress, sheet);
    }

    /**
     * <pre>
     * 说明: 将一行转为一个实体bean
     * </pre>
     *
     * @param row 行
     * @param bean 实体 bean
     * @param fields 属性集
     * @param formatter 格式转换器
     * @author lixin_ma@outlook.com
     * @since 2019/5/15
     */
    protected void rowToBean(Row row, Object bean, Field[] fields, DataFormatter formatter) throws ConvertException{
        if (row == null) {
            return;
        }
        for (Field field : fields) {
            CellConfig cellConfig = field.getAnnotation(CellConfig.class);
            if (cellConfig == null || cellConfig.index() == -1) {
                continue;
            }

            Cell cell = row.getCell(cellConfig.index());
            String text = formatter.formatCellValue(cell);//内容
            validateRequired(cellConfig, text, row.getRowNum());// 必输校验
            setFieldValue(field, bean, text,null,null,null);//映射赋值
        }
    }

    /**
     * 必输校验
     * @param cellConfig 单元格配置
     * @param text 内容
     * @param rowNum 行号
     */
    protected void validateRequired(CellConfig cellConfig, String text, int rowNum) {
        if (cellConfig.required()) {
            if (StringUtils.isBlank(text)) {
                CellReference cellReference = new CellReference(rowNum, cellConfig.index());
                throw new NullPointerException(" cell [" + cellReference.formatAsString() + " ] can not be null");
            }
        }
    }

    @Override
    public void setFieldValue(Field field, Object bean, String source,String format,String scale,Integer roundMode) throws ConvertException {
        try {
            Class<?> converterClass;
            if (field.getType().isArray()) {
                converterClass = Array.class;
            } else {
                converterClass = field.getType();
            }
            FieldValueConverter converter = handler.getFieldConverter(converterClass);
            if (converter != null) {
                Object obj = converter.toObject(source, field);
                field.set(bean, obj);
                return;
            }
            field.set(bean, source);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
