package hr.fer.zemris.java.gui.prim;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class PrimListModelTest {
	@Test
    public void testForSize() {
        PrimListModel model = new PrimListModel();
        assertEquals(1, model.getSize());
        model.next();
        assertEquals(2, model.getSize());
        model.next();
        assertEquals(3, model.getSize());
        model.next();
        assertEquals(4, model.getSize());
    }
	
	@Test
	public void testForNextAndElementAt() {
		PrimListModel model = new PrimListModel();
        assertEquals("1", model.getElementAt(0));
        model.next();
        assertEquals("2", model.getElementAt(1));
        model.next();
        assertEquals("3", model.getElementAt(2));
        model.next();
        assertEquals("5", model.getElementAt(3));
	}
}
