package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.SimpleHashtable;

public class Task4Demo2 {
	public static void main(String[] args) {
		SimpleHashtable<String, String> map= new SimpleHashtable<String, String>(10);
		map.put("key1", "value1");
		map.put("key2", "value2");
		map.put("key3", "value3");		

		//System.out.println(map.toString());
		//"[key1=value1, key2=value2, key3=value3]".
		
		
		SimpleHashtable<String, String> map2= new SimpleHashtable<String, String>(1);
		System.out.println(map2.toString());
		map2.put("C1", "value2");
		System.out.println(map2.toString());
		map2.put("C4", "value1");
		System.out.println(map2.toString());
		map2.put("C2", "value4");		
		System.out.println(map2.toString());
		map2.put("C3", "value3");
		System.out.println(map2.toString());
		map2.put("C0", "value3");
		System.out.println(map2.toString());
	}
}
