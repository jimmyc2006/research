package research;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.HTablePool;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.filter.CompareFilter;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.RegexStringComparator;
import org.apache.hadoop.hbase.filter.RowFilter;
import org.apache.hadoop.hbase.util.Bytes;

import com.alibaba.fastjson.JSON;

public class HbaseTest {
  public static Configuration configuration;
  public static Connection connection;
  public static Admin admin;

  public static void main(String[] args) throws Exception {
    // createTable("t2", new String[] { "cf1", "cf2" });
    init();
    try {
      // String searchStr = MD5keyUtil.MD5("353114085129247");
      System.out.println("开始查询");
      List list = (List)scanByStartAndStopRow("golden_compass:USER_ACT_INFO",
          "e483d7a7ce84912e629bacd67a0e42a70000000000000000",
          "e483d7a7ce84912e629bacd67a0e42a79999999999999999", null);
      System.out.println("结果是：：：：" + JSON.toJSONString(list));
    } catch (Exception e) {
      e.printStackTrace();
    }
//     listTables();
//     System.out.println("-----------");
     // getResultScann("golden_compass:test_USER_ACT_INFO");
    /*
     * insterRow("t2", "rw1", "cf1", "q1", "val1"); getData("t2", "rw1", "cf1", "q1");
     * scanData("t2", "rw1", "rw2"); deleRow("t2","rw1","cf1","q1"); deleteTable("t2");
     */
  }

  // 初始化链接
  public static void init() {
    configuration = HBaseConfiguration.create();
    /*
     * configuration.set("hbase.zookeeper.quorum", "10.10.3.181,10.10.3.182,10.10.3.183");
     * configuration.set("hbase.zookeeper.property.clientPort","2181");
     * configuration.set("zookeeper.znode.parent","/hbase");
     */
    configuration.set("hbase.zookeeper.property.clientPort", "2181");
//     configuration.set("hbase.zookeeper.quorum",
//     "test-001.qianjin.com,test-002.qianjin.com,test-003.qianjin.com,test-004.qianjin.com,test-005.qianjin.com");

    configuration.set("hbase.zookeeper.quorum",
        "hadoop-006.qianjin.com,hadoop-005.qianjin.com,hadoop-004.qianjin.com");
    // configuration.set("hbase.master", "101.236.39.141:60000");
    /*
     * File workaround = new File("."); System.getProperties().put("hadoop.home.dir",
     * workaround.getAbsolutePath()); new File("./bin").mkdirs(); try { new
     * File("./bin/winutils.exe").createNewFile(); } catch (IOException e) { // TODO Auto-generated
     * catch block e.printStackTrace(); }
     */
    try {
      connection = ConnectionFactory.createConnection(configuration);
      admin = connection.getAdmin();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public Result getResult(String tableName, String rowKey) throws Exception {
    Get get = new Get(Bytes.toBytes(rowKey));
    HTable htable = new HTable(configuration, Bytes.toBytes(tableName));
    Result result = htable.get(get);
    return result;
  }

  public static ResultScanner getResultScann(String tableName) throws Exception {
    Scan scan = new Scan();
    scan.setMaxResultSize(1);
    scan.setCaching(1);
    scan.setBatch(1);
//    scan.setFilter(
//        new RowFilter(CompareFilter.CompareOp.EQUAL, new RegexStringComparator(".*001")));
    ResultScanner rs = null;
    HTable htable = new HTable(configuration, tableName);
    try {
      rs = htable.getScanner(scan);
      for (Result r : rs) {
        for (KeyValue kv : r.list()) {
          System.out.println(Bytes.toString(kv.getRow()));
          System.out.println(Bytes.toString(kv.getFamily()));
          System.out.println(Bytes.toString(kv.getQualifier()));
          System.out.println(Bytes.toString(kv.getValue()));
          System.out.println(kv.getTimestamp());
        }
        System.out.println("++++");
      }
    } finally {
      rs.close();
    }
    return rs;
  }

  public static void listTables() throws IOException {
    init();
    HTableDescriptor hTableDescriptors[] = admin.listTables();
    for (HTableDescriptor hTableDescriptor : hTableDescriptors) {
      System.out.println(hTableDescriptor.getNameAsString());
    }
    close();
  }

  // 关闭连接
  public static void close() {
    try {
      if (null != admin)
        admin.close();
      if (null != connection)
        connection.close();
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  public static List scanByStartAndStopRow(String tableName, String startRow, String stopRow,
      Filter filter) throws Exception {
    Table table = connection.getTable(TableName.valueOf(tableName));
    Scan scan = new Scan();
    scan.setStartRow(Bytes.toBytes(startRow));
    scan.setStopRow(Bytes.toBytes(stopRow));

    scan.setCacheBlocks(false);
    scan.setCaching(100);

    if (filter != null) {
      scan.setFilter(filter);
    }
    System.out.println("---0---" + System.currentTimeMillis());
    ResultScanner rs = table.getScanner(scan);
    System.out.println("---1---" + System.currentTimeMillis());
    List list = new ArrayList<>();
    if (rs != null) {
      for (Result r : rs) {
        Map<String, Object> rmap = new HashMap<String, Object>();
        rmap = result2Map(r);
        list.add(rmap);
      }
      return list;
    }
    rs.close();
    table.close();// 释放资源
    return null;
  }

  private static Map<String, Object> result2Map(Result result) {
    Map<String, Object> ret = new HashMap<String, Object>();
    if (result != null && result.listCells() != null) {
      for (Cell cell : result.listCells()) {
        String key = Bytes.toString(CellUtil.cloneQualifier(cell));
        String value = Bytes.toString(CellUtil.cloneValue(cell));
        // System.out.println(key + " => " + value);
        ret.put(key, value);
      }
    }
    return ret;
  }
}
