package research;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import research.deep.search.Node;

/** 
* @author shuwei 
* @version 创建时间：2018年6月21日 上午11:46:42 
* 类说明 
*/
public class NodeTest {
  public static void main(String[] args) {
    Node root = init();
    find(root, 11);
    System.out.println(result());
  }
  
  static Node init() {
    Node root = new Node("A");
    List<Node> aChildren = new ArrayList<>();
    aChildren.add(new Node("B"));
    aChildren.add(new Node("C"));
    aChildren.add(new Node("D"));
    Node nodeE = new Node("E");
    aChildren.add(nodeE);
    Node nodeN = new Node("N");
    aChildren.add(nodeN);
    aChildren.add(new Node("L"));
    root.setChildren(aChildren);
    List<Node> eChildren = new ArrayList<>();
    eChildren.add(new Node("F"));
    eChildren.add(new Node("G1"));
    eChildren.add(new Node("G2"));
    eChildren.add(new Node("V"));
    nodeE.setChildren(eChildren);
    List<Node> nChildren = new ArrayList<>();
    nChildren.add(new Node("M"));
    nodeN.setChildren(nChildren);
    return root;
  }
  static LinkedList<Node> stack = new LinkedList<>();
  static int cIndex = 1;
  
  static boolean find(Node root, int index) {
    if (cIndex == index) {
      stack.addLast(root);
      return true;
    } else {
      cIndex++;
      if (root.getChildren() == null) {
        return false;
      } else {
        stack.addLast(root);
        for (Node child : root.getChildren()) {
          if(find(child, index)) {
            return true;
          }
        }
        stack.removeLast();
      }
    }
    return false;
  }
  
  static String result() {
    StringBuilder sb = new StringBuilder();
    for (Node n : stack) {
      sb.append(n.getName() + ".");
    }
    sb.deleteCharAt(sb.length() - 1);
    return sb.toString();
  }
}
