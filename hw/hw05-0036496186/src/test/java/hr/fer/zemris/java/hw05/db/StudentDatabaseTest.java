package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class StudentDatabaseTest {
	private class MyList {
		List<String> list;

		public MyList() {
			list = new ArrayList<>();
			list.add("1 Zeci Janko 2");
			list.add("2 Zecii Janko 2");
			list.add("3 Zeciii Janko 2");
		}
	}

	@Test
	public void forJmbagTest() {
		StudentDatabase db = new StudentDatabase(new MyList().list);
		assertNull(db.forJMBAG("4"));
		assertEquals("Zeciii", db.forJMBAG("3").getLastName());
	}

	@Test
	public void filterAll() {
		StudentDatabase db = new StudentDatabase(new MyList().list);
		assertEquals(0, db.filter(x -> false).size());
	}

	@Test
	public void filterNone() {
		StudentDatabase db = new StudentDatabase(new MyList().list);
		assertEquals(3, db.filter(x -> true).size());
	}
	
	@Test
	public void filterAllButOne() {
		StudentDatabase db = new StudentDatabase(new MyList().list);
		assertEquals(1, db.filter(x -> x.getLastName().equals("Zecii")).size());
	}
}
