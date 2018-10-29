package research.excel;

import java.io.File;
import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * @author shuwei
 * @version 创建时间：2018年7月12日 上午10:35:15 测试读取excel文件
 */
public class ReaderTest {
  public static void main(String[] args) throws InvalidFormatException, IOException {
    String path = "/Users/apple/Downloads/tmp/aaaaaaaaaaaaaaaaa.xlsx";
    XSSFWorkbook workbook = new XSSFWorkbook(new File(path));
    try {
      int sheet_size = workbook.getNumberOfSheets();
      for (int index = 0; index < sheet_size; index++) {
        // 每个页签创建一个Sheet对象
        XSSFSheet sheet = workbook.getSheetAt(index);
        // sheet.getRows()返回该页的总行数
        for (int i = 0; i < sheet.getPhysicalNumberOfRows(); i++) {
          // sheet.getColumns()返回该页的总列数
          Row row = sheet.getRow(i);
          for (int j = 0; j < row.getPhysicalNumberOfCells(); j++) {
            // row.getCell(j);
            Cell cell = row.getCell(j);
            int cellType = cell.getCellType();
            if (cellType == Cell.CELL_TYPE_NUMERIC) {
              System.out.println(cell.getNumericCellValue());
            } else {
              System.out.println(cell.getStringCellValue());
            }
            System.out.println("NUMERIC:" + Cell.CELL_TYPE_NUMERIC);
            System.out.println("STRING:" + Cell.CELL_TYPE_STRING);
            System.out.println("getRawValue:" + cell.getNumericCellValue());
            // System.out.println("getNumericCellValue" + cell.getNumericCellValue());
            System.out.println(cell.toString());
          }
        }
      }
    } finally {
      workbook.close();
    }
  }
}
