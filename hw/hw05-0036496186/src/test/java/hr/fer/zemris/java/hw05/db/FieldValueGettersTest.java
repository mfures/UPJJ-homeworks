package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class FieldValueGettersTest {
	private class MyList {
		List<String> list;

		public MyList() {
			list = new ArrayList<>();
			list.add("1 Zeci Janko 2");
			list.add("2 Zecii Jankok 2");
			list.add("3 Zeciii Janko 2");
		}
	}

	@Test
	public void getLastNameTest() {
		StudentDatabase db = new StudentDatabase(new MyList().list);
		assertEquals("Zeciii", FieldValueGetters.LAST_NAME.get(db.forJMBAG("3")));
	}

	@Test
	public void getFirstNameTest() {
		StudentDatabase db = new StudentDatabase(new MyList().list);
		assertEquals("Jankok", FieldValueGetters.FIRST_NAME.get(db.forJMBAG("2")));
	}

	@Test
	public void getJmbagTest() {
		StudentDatabase db = new StudentDatabase(new MyList().list);
		assertEquals("1", FieldValueGetters.JMBAG.get(db.forJMBAG("1")));
	}
}
