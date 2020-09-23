package hr.fer.zemris.java.hw02;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class ComplexNumberTest {
	
	@Test
	public void toStringTest() {
		String s=(new ComplexNumber(0, 0)).toString();
		assertEquals(s, "0");
		s=(new ComplexNumber(3.5, 0)).toString();
		assertEquals(s, "3.5");
		s=(new ComplexNumber(-3.5, 0)).toString();
		assertEquals(s, "-3.5");
		s=(new ComplexNumber(0, 3.5)).toString();
		assertEquals(s, "3.5i");
		s=(new ComplexNumber(0, -3.5)).toString();
		assertEquals(s, "-3.5i");
		s=(new ComplexNumber(1, -3.5)).toString();
		assertEquals(s, "1.0-3.5i");
		s=(new ComplexNumber(-1, -3.5)).toString();
		assertEquals(s, "-1.0-3.5i");
		s=(new ComplexNumber(1, 3.5)).toString();
		assertEquals(s, "1.0+3.5i");
		s=(new ComplexNumber(-1, 3.5)).toString();
		assertEquals(s, "-1.0+3.5i");
	}
	
	@Test
	public void rootTests() {
		assertThrows(IllegalArgumentException.class, ()->(new ComplexNumber(4, 2)).root(-5));
		assertThrows(IllegalArgumentException.class, ()->(new ComplexNumber(4, 2)).root(0));
		ComplexNumber c1 = new ComplexNumber(2, 3);
		ComplexNumber c2 = ComplexNumber.parse("2.5-3i");
		ComplexNumber[] c3 = c1.add(ComplexNumber.fromMagnitudeAndAngle(2, 1.57)).div(c2).power(3).root(2);
		assertEquals(true, c3[0].equals(new ComplexNumber(-1.618175, +0.068786)));
		assertEquals(true, c3[1].equals(new ComplexNumber(1.618175, -0.068786)));
	}
	
	@Test
	public void powerTest() {
		ComplexNumber num=new ComplexNumber(1, 1);
		ComplexNumber num2=num.power(33);
		assertEquals(true, num2.equals(new ComplexNumber(65536, 65536)));
		num2=new ComplexNumber(-17.5,15);
		num2=num2.power(3);
		assertEquals(true, num2.equals(new ComplexNumber(6453.125, 10406.25)));
		num2=num2.power(0);
		assertEquals(true, num2.equals(new ComplexNumber(1, 0)));
		assertThrows(IllegalArgumentException.class, ()->(new ComplexNumber(4, 2)).power(-1));
		assertThrows(IllegalArgumentException.class, ()->(new ComplexNumber(4, 2)).power(-5));
	}
	
	@Test
	public void divisionTests() {
		ComplexNumber num=new ComplexNumber(0, 0),num3=new ComplexNumber(2, 0);
		assertThrows(NullPointerException.class, ()->num.div((null)));
		ComplexNumber num2=num.div(new ComplexNumber(1, 0));
		assertEquals(0, num2.getReal());
		assertEquals(0, num2.getImaginary());
		num3=(new ComplexNumber(1, 0).div(num));
		assertEquals(true,Double.isNaN(num3.getReal()));
		assertEquals(true,Double.isNaN(num3.getImaginary()));
		num3=new ComplexNumber(17.5, -55);
		num2=num3.div(new ComplexNumber(5, 40));
		assertEquals(-1.3, num2.getReal());
		assertEquals(-0.6, num2.getImaginary());
		
	}
	
	@Test
	public void multiplicationTests() {
		ComplexNumber num=new ComplexNumber(0, 0),num3=new ComplexNumber(2, 0);
		assertThrows(NullPointerException.class, ()->num.mul((null)));
		ComplexNumber num2=num.mul(new ComplexNumber(1, 0));
		assertEquals(0, num2.getReal());
		assertEquals(0, num2.getImaginary());
		num2=num.mul(new ComplexNumber(1, -15));
		assertEquals(0, num2.getReal());
		assertEquals(0, num2.getImaginary());
		num2=num3.mul(new ComplexNumber(1, 0));
		assertEquals(2, num2.getReal());
		assertEquals(0, num2.getImaginary());
		num2=num3.mul(new ComplexNumber(1, -15));
		assertEquals(2, num2.getReal());
		assertEquals(-30, num2.getImaginary());
		num3=new ComplexNumber(0, 1);
		num2=num3.mul(new ComplexNumber(1, 0));
		assertEquals(0, num2.getReal());
		assertEquals(1, num2.getImaginary());
		num2=num3.mul(new ComplexNumber(1, -15));
		assertEquals(15, num2.getReal());
		assertEquals(1, num2.getImaginary());
		num3=new ComplexNumber(1, 1);
		num2=num3.mul(new ComplexNumber(1, -1));
		assertEquals(2, num2.getReal());
		assertEquals(0, num2.getImaginary());
		num3=new ComplexNumber(15, 3);
		num2=num3.mul(new ComplexNumber(11, -5));
		assertEquals(180, num2.getReal());
		assertEquals(-42, num2.getImaginary());
		
	}
	
	@Test
	public void subtractionTests() {
		ComplexNumber num=new ComplexNumber(0, 0);
		assertThrows(NullPointerException.class, ()->num.sub(null));
		ComplexNumber num2=num.sub(new ComplexNumber(-1, -1));
		assertEquals(1, num2.getReal());
		assertEquals(1, num2.getImaginary());
		num2=num.sub(new ComplexNumber(1, -1));
		assertEquals(-1, num2.getReal());
		assertEquals(1, num2.getImaginary());
		num2=num.sub(new ComplexNumber(-1, 1));
		assertEquals(1, num2.getReal());
		assertEquals(-1, num2.getImaginary());
		num2=num.sub(new ComplexNumber(1, 1));
		assertEquals(-1, num2.getReal());
		assertEquals(-1, num2.getImaginary());
	
	}
	
	@Test
	public void additionTests() {
		ComplexNumber num=new ComplexNumber(0, 0);
		assertThrows(NullPointerException.class, ()->num.add(null));
		ComplexNumber num2=num.add(new ComplexNumber(-1, -1));
		assertEquals(-1, num2.getReal());
		assertEquals(-1, num2.getImaginary());
		num2=num.add(new ComplexNumber(1, -1));
		assertEquals(1, num2.getReal());
		assertEquals(-1, num2.getImaginary());
		num2=num.add(new ComplexNumber(-1, 1));
		assertEquals(-1, num2.getReal());
		assertEquals(1, num2.getImaginary());
		num2=num.add(new ComplexNumber(1, 1));
		assertEquals(1, num2.getReal());
		assertEquals(1, num2.getImaginary());
	
	}

	@Test
	public void badInputsForParse() {
		assertThrows(NullPointerException.class, ()->ComplexNumber.parse(null));
		assertThrows(IllegalArgumentException.class, ()->ComplexNumber.parse("i351"));
		assertThrows(IllegalArgumentException.class, ()->ComplexNumber.parse("i371"));
		assertThrows(IllegalArgumentException.class, ()->ComplexNumber.parse("i3.51"));
		assertThrows(IllegalArgumentException.class, ()->ComplexNumber.parse("i3.51"));
		assertThrows(IllegalArgumentException.class, ()->ComplexNumber.parse("-+2.71"));
		assertThrows(IllegalArgumentException.class, ()->ComplexNumber.parse("--2.71"));
		assertThrows(IllegalArgumentException.class, ()->ComplexNumber.parse("-2.71+-3.15i"));
	}
	@Test
	public void createComplexNumberFromDoubleExampleStringsAssignedInHomework() {
		assertThrows(IllegalArgumentException.class, ()->ComplexNumber.parse(""));
		assertThrows(IllegalArgumentException.class, ()->ComplexNumber.parse("+"));
		ComplexNumber num = ComplexNumber.parse("351");
		assertEquals(351, num.getReal());
		assertEquals(0, num.getImaginary());
		num = ComplexNumber.parse("-317");
		assertEquals(-317, num.getReal());
		assertEquals(0, num.getImaginary());
		num = ComplexNumber.parse("3.51");
		assertEquals(3.51, num.getReal());
		assertEquals(0, num.getImaginary());
		num = ComplexNumber.parse("-3.17");
		assertEquals(-3.17, num.getReal());
		assertEquals(0, num.getImaginary());
		num = ComplexNumber.parse("351i");
		assertEquals(0, num.getReal());
		assertEquals(351, num.getImaginary());
		num = ComplexNumber.parse("-317i");
		assertEquals(0, num.getReal());
		assertEquals(-317, num.getImaginary());
		num = ComplexNumber.parse("3.51i");
		assertEquals(0, num.getReal());
		assertEquals(3.51, num.getImaginary());
		num = ComplexNumber.parse("-3.17i");
		assertEquals(0, num.getReal());
		assertEquals(-3.17, num.getImaginary());
		num = ComplexNumber.parse("1");
		assertEquals(1, num.getReal());
		assertEquals(0, num.getImaginary());
		num = ComplexNumber.parse("i");
		assertEquals(0, num.getReal());
		assertEquals(1, num.getImaginary());
		num = ComplexNumber.parse("-2.71-3.15i");
		assertEquals(-2.71, num.getReal());
		assertEquals(-3.15, num.getImaginary());
		num = ComplexNumber.parse("31+24i");
		assertEquals(31, num.getReal());
		assertEquals(24, num.getImaginary());
		num = ComplexNumber.parse("-1-i");
		assertEquals(-1, num.getReal());
		assertEquals(-1, num.getImaginary());
		num = ComplexNumber.parse("1+i");
		assertEquals(1, num.getReal());
		assertEquals(1, num.getImaginary());
	}

	@Test
	public void createComplexNumberFromDoubleStringUnsigned() {
		ComplexNumber num = ComplexNumber.parse("2");
		assertEquals(2, num.getReal());
		assertEquals(0, num.getImaginary());
		num = ComplexNumber.parse("2i");
		assertEquals(0, num.getReal());
		assertEquals(2, num.getImaginary());
		num = ComplexNumber.parse("2-2i");
		assertEquals(2, num.getReal());
		assertEquals(-2, num.getImaginary());
		num = ComplexNumber.parse("2+2i");
		assertEquals(2, num.getReal());
		assertEquals(2, num.getImaginary());
	}

	@Test
	public void createComplexNumberFromDoubleStringWithLeadingMinus() {
		ComplexNumber num = ComplexNumber.parse("-2");
		assertEquals(-2, num.getReal());
		assertEquals(0, num.getImaginary());
		num = ComplexNumber.parse("-2i");
		assertEquals(0, num.getReal());
		assertEquals(-2, num.getImaginary());
		num = ComplexNumber.parse("-2-2i");
		assertEquals(-2, num.getReal());
		assertEquals(-2, num.getImaginary());
		num = ComplexNumber.parse("-2+2i");
		assertEquals(-2, num.getReal());
		assertEquals(2, num.getImaginary());
		num = ComplexNumber.parse("2.3+4.5i");
		assertEquals(2.3, num.getReal());
		assertEquals(4.5, num.getImaginary());
		num = ComplexNumber.parse("2.3-4.5i");
		assertEquals(2.3, num.getReal());
		assertEquals(-4.5, num.getImaginary());
		num = ComplexNumber.parse("2.5-3i");
		assertEquals(2.5, num.getReal());
		assertEquals(-3, num.getImaginary());
	}

	@Test
	public void createComplexNumberFromDoubleStringWithLeadingPlus() {
		ComplexNumber num = ComplexNumber.parse("+2");
		assertEquals(2, num.getReal());
		assertEquals(0, num.getImaginary());
		num = ComplexNumber.parse("+2i");
		assertEquals(0, num.getReal());
		assertEquals(2, num.getImaginary());
		num = ComplexNumber.parse("+2-2i");
		assertEquals(2, num.getReal());
		assertEquals(-2, num.getImaginary());
		num = ComplexNumber.parse("+2+2i");
		assertEquals(2, num.getReal());
		assertEquals(2, num.getImaginary());
	}

	@Test
	public void createComplexNumberInFirstQuadrant() {
		ComplexNumber cNum = new ComplexNumber(1, 1);
		assertEquals(1, cNum.getReal());
		assertEquals(1, cNum.getImaginary());
		assertEquals(Math.sqrt(2), cNum.getMagnitude());
		assertEquals(Math.PI / 4, cNum.getAngle());
	}

	@Test
	public void createComplexNumberInSecondQuadrant() {
		ComplexNumber cNum = new ComplexNumber(-1, 1);
		assertEquals(-1, cNum.getReal());
		assertEquals(1, cNum.getImaginary());
		assertEquals(Math.sqrt(2), cNum.getMagnitude());
		assertEquals((3 * Math.PI) / 4, cNum.getAngle());
	}

	@Test
	public void createComplexNumberInThirdQuadrant() {
		ComplexNumber cNum = new ComplexNumber(-1, -1);
		assertEquals(-1, cNum.getReal());
		assertEquals(-1, cNum.getImaginary());
		assertEquals(Math.sqrt(2), cNum.getMagnitude());
		assertEquals((5 * Math.PI) / 4, cNum.getAngle());
	}

	@Test
	public void createComplexNumberInForthQuadrant() {
		ComplexNumber cNum = new ComplexNumber(1, -1);
		assertEquals(1, cNum.getReal());
		assertEquals(-1, cNum.getImaginary());
		assertEquals(Math.sqrt(2), cNum.getMagnitude());
		assertEquals((7 * Math.PI) / 4, cNum.getAngle());
	}

	@Test
	public void angleOfAxis() {
		assertEquals(0, (new ComplexNumber(1, 0)).getAngle());
		assertEquals(Math.PI, (new ComplexNumber(-1, 0)).getAngle());
		assertEquals((3 * Math.PI / 2), (new ComplexNumber(0, -1)).getAngle());
		assertEquals((Math.PI / 2), (new ComplexNumber(0, 1)).getAngle());
	}

	@Test
	public void magnitudeOfAxis() {
		assertEquals(1, (new ComplexNumber(1, 0)).getMagnitude());
		assertEquals(1, (new ComplexNumber(-1, 0)).getMagnitude());
		assertEquals(1, (new ComplexNumber(0, -1)).getMagnitude());
		assertEquals(1, (new ComplexNumber(0, 1)).getMagnitude());
	}
}
