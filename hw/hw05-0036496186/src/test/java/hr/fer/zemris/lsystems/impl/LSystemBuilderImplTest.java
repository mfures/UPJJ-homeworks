package hr.fer.zemris.lsystems.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class LSystemBuilderImplTest {
	@Test
	public void generateTestGen0() {
		LSystemBuilderImpl l=new LSystemBuilderImpl();
		l.setAxiom("F");
		l.registerProduction('F', "F+F--F+F");
		
		assertEquals("F",l.build().generate(0));
	}
	
	@Test
	public void generateTestGen1() {
		LSystemBuilderImpl l=new LSystemBuilderImpl();
		l.setAxiom("F");
		l.registerProduction('F', "F+F--F+F");
		
		assertEquals("F+F--F+F",l.build().generate(1));
	}
	
	@Test
	public void generateTestGen2() {
		LSystemBuilderImpl l=new LSystemBuilderImpl();
		l.setAxiom("F");
		l.registerProduction('F', "F+F--F+F");
		
		assertEquals("F+F--F+F+F+F--F+F--F+F--F+F+F+F--F+F",l.build().generate(2));
	}
}
