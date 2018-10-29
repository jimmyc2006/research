package research.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import research.file.FileCharsetDetector;


/**
 * @author shuwei
 * @version 创建时间：2018年7月13日 上午11:18:50 类说明
 */
public class CsvReader {
  public static void main(String[] args)
      throws IOException {
    String file = "/Users/apple/Downloads/tmp/导入csv.csv";
    String encoding = new FileCharsetDetector().guessFileEncoding(new File(file));
    System.out.println(encoding);
    java.io.Reader in = new InputStreamReader(new FileInputStream(file), "gbk");
    CSVFormat csvFormat = CSVFormat.DEFAULT;
    Iterable<CSVRecord> records = csvFormat.parse(in);
    for (CSVRecord csvRecord : records) {
      for (int i = 0, len = csvRecord.size(); i < len; i++) {
        System.out.println(csvRecord.get(i));
      }
    }
  }
}
