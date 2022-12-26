package homework.java.service.query;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.*;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.lang.reflect.*;
import java.util.Arrays;

public class GenerateExcel<T> {
    public void WriteExcelWorkbook(ArrayList<T> data, String fileName, String sheetName) throws FileNotFoundException, IOException, InvocationTargetException, IllegalAccessException {
        String basePath = "./src/main/resources/homework/java/excelBooks/";
        try (XSSFWorkbook workbook = new XSSFWorkbook();
             FileOutputStream outputStream = new FileOutputStream(basePath + fileName);
        ) {
            XSSFSheet sheet = workbook.createSheet(sheetName);
            sheet.createFreezePane(0, 1);
            CellStyle style = workbook.createCellStyle();
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            style.setFillForegroundColor(IndexedColors.BLUE.getIndex());

            ArrayList<Field> fields = getFields(data.get(0));
            int rows = data.toArray().length;
            int cols = fields.toArray().length;

            XSSFRow name_row = sheet.createRow(0);
            for (int i = 0; i < cols; i++) {
                XSSFCell cell = name_row.createCell(i);

                cell.setCellStyle(style);

                String value = fields.get(i).getName();
                if (!value.equals("this$0")) {
                    cell.setCellValue(value);

                } else {
                    cell.setCellValue("");
                }
            }

            int rowCount = 1;
            for (T instance : data) {
                XSSFRow row = sheet.createRow(rowCount++);
                int columnCount = 0;
                for (Field field : fields) {
                    XSSFCell cell = row.createCell(columnCount++);
                    if (!field.getName().equals("this$0")) {
                        final Object value = ValueByFieldGetter.getFieldValueWithGetter(instance, field);
                        if (value instanceof String) {
                            cell.setCellValue((String) value);
                        }
                        if (value instanceof Integer) {
                            cell.setCellValue((Integer) value);
                        }
                        if (value instanceof ExecuteQuery.Day) {
                            cell.setCellValue(value.toString());
                        }
                    } else {
                        cell.setCellValue("");
                    }

                }
            }
            workbook.write(outputStream);
        }

    }

    private <F> ArrayList<Field> getFields(F f) {
        ArrayList<Field> fields = new ArrayList<>();
        Class current_class = f.getClass();
        while (current_class != Object.class) {
            fields.addAll(Arrays.asList(current_class.getDeclaredFields()));
            current_class = current_class.getSuperclass();
        }
        return fields;
    }
}
