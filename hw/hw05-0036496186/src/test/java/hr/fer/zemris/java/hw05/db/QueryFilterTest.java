package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class QueryFilterTest {
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
	public void nullTest() {
		assertThrows(NullPointerException.class, ()->new QueryFilter(null));
	}
	
	@Test
	public void directQueryAsList() {
		QueryParser qp1 = new QueryParser(" jmbag =\"1\" ");
		QueryFilter filter=new QueryFilter(qp1.getQuery());
		StudentDatabase db=new StudentDatabase(new MyList().list);
		assertEquals(1, db.filter(filter).size());
	}
	
	@Test
	public void nonDirectQuery() {
		QueryParser qp1 = new QueryParser(" jmbag >=\"1\" ");
		QueryFilter filter=new QueryFilter(qp1.getQuery());
		StudentDatabase db=new StudentDatabase(new MyList().list);
		assertEquals(3, db.filter(filter).size());
	}
	
	@Test
	public void complexQueryFilter() {
		QueryParser qp1 = new QueryParser(" jmbag >=\"2\" and lastName LIKE \"Ze*iii\"");
		QueryFilter filter=new QueryFilter(qp1.getQuery());
		StudentDatabase db=new StudentDatabase(new MyList().list);
		assertEquals(1, db.filter(filter).size());
	}
}
