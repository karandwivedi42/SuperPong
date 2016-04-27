import java.io.*;
import java.util.*;
class Test{
	public static void main(String[] args) {
		HashMap<String,String> map = new HashMap<String,String>();
		map.put("Fuck","fuck");
		System.out.println(map.get("Fuck"));
		map.put("Fuck","fuc2");
		System.out.println(map.get("Fuck"));
	}
}