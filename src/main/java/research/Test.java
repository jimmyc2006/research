package research;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;

public class Test {
  
  public static void main(String[] args) {
	  System.out.println(12 * 16);
  }
  
  static class Te {
	  private int id;

	public Te(int id) {
		super();
		this.id = id;
	}
	@Override
	public String toString() {
		return "Te [id=" + id + "]";
	}
  }
}
