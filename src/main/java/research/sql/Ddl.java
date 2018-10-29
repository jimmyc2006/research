package research.sql;

import java.util.Arrays;
import java.util.List;

/**
 * @author shuwei
 * @version 创建时间：2018年7月12日 下午2:58:43 类说明
 */
public class Ddl {
  public static String generateCreateTableSql (String tableName, int columnCount, 
      List<Integer> intColumnsIndex, List<Integer> doubleColumnsIndex) {
    StringBuilder sb = new StringBuilder("create table " + tableName);
    sb.append(" (");
    for (int i = 0; i < columnCount; i++) {
      int index = i + 1;
      sb.append("c_" + index);
      if (intColumnsIndex.contains(index)) {
        sb.append(" int,");
      } else if(doubleColumnsIndex.contains(index)) {
        sb.append(" double,");
      } else {
        sb.append(" string,");
      }
    }
    sb.deleteCharAt(sb.length() - 1);
    sb.append(")");
    return sb.toString();
  }

  public static void main(String[] args) {
    List<Integer> intColumnsIndex = Arrays.asList(1, 6, 7, 8, 9, 10);
    List<Integer> doubleColumnsIndex = Arrays.asList(11, 12, 13, 14);
    System.out.println(generateCreateTableSql("ttt", 58, intColumnsIndex, doubleColumnsIndex));
  }
}
