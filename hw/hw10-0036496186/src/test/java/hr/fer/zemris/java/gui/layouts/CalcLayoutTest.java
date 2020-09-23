package hr.fer.zemris.java.gui.layouts;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.junit.jupiter.api.Test;

public class CalcLayoutTest {
	@Test
	public void invalidPosition() {
		JPanel p = new JPanel(new CalcLayout(2));
		assertThrows(CalcLayoutException.class, ()->p.add(new JLabel(""),"0,5"));
		assertThrows(CalcLayoutException.class, ()->p.add(new JLabel(""),"6,5"));
		assertThrows(CalcLayoutException.class, ()->p.add(new JLabel(""),"2,0"));
		assertThrows(CalcLayoutException.class, ()->p.add(new JLabel(""),"2,8"));
		assertThrows(CalcLayoutException.class, ()->p.add(new JLabel(""),"1,3"));
		JLabel l1 = new JLabel("");
		p.add(l1,"1,1");
		assertThrows(CalcLayoutException.class, ()->p.add(new JLabel(""),"1,1"));
	}
	
	@Test
	public void testPrefSize() {
		JPanel p = new JPanel(new CalcLayout(2));
		JLabel l1 = new JLabel("");
		l1.setPreferredSize(new Dimension(10, 30));
		JLabel l2 = new JLabel("");
		l2.setPreferredSize(new Dimension(20, 15));
		p.add(l1, new RCPosition(2, 2));
		p.add(l2, new RCPosition(3, 3));
		Dimension dim = p.getPreferredSize();
		assertEquals(152, dim.width);
		assertEquals(158, dim.height);
	}

	@Test
	public void testPrefSize2() {
		JPanel p = new JPanel(new CalcLayout(2));
		JLabel l1 = new JLabel("");
		l1.setPreferredSize(new Dimension(108, 15));
		JLabel l2 = new JLabel("");
		l2.setPreferredSize(new Dimension(16, 30));
		p.add(l1, new RCPosition(1, 1));
		p.add(l2, new RCPosition(3, 3));
		Dimension dim = p.getPreferredSize();
		assertEquals(152, dim.width);
		assertEquals(158, dim.height);
	}
}
