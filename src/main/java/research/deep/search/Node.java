package research.deep.search;

import java.util.List;

/** 
* @author shuwei 
* @version 创建时间：2018年6月21日 上午11:46:42 
* 类说明 
*/
public class Node {
  private String name;
  private String value;
  List<Node> children;
  
  public Node(String name) {
    super();
    this.name = name;
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public String getValue() {
    return value;
  }
  public void setValue(String value) {
    this.value = value;
  }
  public List<Node> getChildren() {
    return children;
  }
  public void setChildren(List<Node> children) {
    this.children = children;
  }
  
  @Override
  public String toString() {
    return "Node [name=" + name + "]";
  }
}
