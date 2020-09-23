package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;
import hr.fer.zemris.java.custom.collections.Collection;
import hr.fer.zemris.java.custom.collections.ElementsGetter;
import hr.fer.zemris.java.custom.collections.ObjectStack;
import hr.fer.zemris.java.custom.collections.List;
import hr.fer.zemris.java.custom.collections.Processor;
import hr.fer.zemris.java.custom.collections.Tester;
import hr.fer.zemris.java.custom.collections.LinkedListIndexedCollection;

public class InitializationExamples {

	public static void main(String[] args) {
		Collection<String> c1=new ArrayIndexedCollection<>();
		Collection<String> c3=new ArrayIndexedCollection<>();
		c1.add("Stipica");
		c1.add("Marko");
		Collection<Double> c2=new ArrayIndexedCollection<>();
		c2.add(3.5);
		c2.add(3.1415926535);
		List<Integer> l1=new LinkedListIndexedCollection<>();
		ElementsGetter<Double> e1=c2.createElementsGetter();
		while(e1.hasNextElement()) {
			System.out.println(e1.getNextElement());
		}
		Processor<String> p=value -> System.out.println(value+" 5");
		c1.forEach(p);
		ObjectStack<List<String>> os = new ObjectStack<List<String>>();
		System.out.println(os.isEmpty());
		l1.add(5);
		System.out.println(l1.remove(Integer.valueOf(5)));
		c1.add("Bero");
		Tester<String> t1=a-> a.length()>5;
		c3.addAllSatisfying(c1, t1);
		System.out.println(c3.size());
		c3.forEach(p);
	}

}
