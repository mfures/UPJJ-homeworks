package hr.fer.zemris.java.hw01;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import hr.fer.zemris.java.hw01.UniqueNumbers.TreeNode;

import org.junit.jupiter.api.Test;

public class UniqueNumbersTest {

	@Test
	public void treeSizeEmpty() {
		int size = UniqueNumbers.treeSize(null);
		assertEquals(0, size);

	}

	@Test
	public void treeSizeRoot() {
		TreeNode node = null;
		node = UniqueNumbers.addNode(node, 2);
		int size = UniqueNumbers.treeSize(node);
		assertEquals(1, size);

	}

	@Test
	public void containsEmpty() {
		assertFalse(UniqueNumbers.containsValue(null, 5));
	}

	@Test
	public void containsElement() {
		TreeNode node = null;
		node = UniqueNumbers.addNode(node, 0);
		assertTrue(UniqueNumbers.containsValue(node, 0));
	}

	@Test
	public void addContainsTreeSizeComplexTest() {
		TreeNode glava = null;
		int treeSize = UniqueNumbers.treeSize(glava);
		assertEquals(0, treeSize);
		assertFalse(UniqueNumbers.containsValue(glava, 15));
		glava = UniqueNumbers.addNode(glava, 42);
		assertTrue(UniqueNumbers.containsValue(glava, 42));
		assertFalse(UniqueNumbers.containsValue(glava, 15));
		treeSize = UniqueNumbers.treeSize(glava);
		assertEquals(1, treeSize);
		glava = UniqueNumbers.addNode(glava, 76);
		assertFalse(UniqueNumbers.containsValue(glava, 15));
		treeSize = UniqueNumbers.treeSize(glava);
		assertEquals(2, treeSize);
		glava = UniqueNumbers.addNode(glava, 21);
		treeSize = UniqueNumbers.treeSize(glava);
		assertEquals(3, treeSize);
		glava = UniqueNumbers.addNode(glava, 76);
		assertTrue(UniqueNumbers.containsValue(glava, 76));
		treeSize = UniqueNumbers.treeSize(glava);
		assertEquals(3, treeSize);
		glava = UniqueNumbers.addNode(glava, 35);
		assertFalse(UniqueNumbers.containsValue(glava, 15));
		treeSize = UniqueNumbers.treeSize(glava);
		assertEquals(4, treeSize);
		assertTrue(UniqueNumbers.containsValue(glava, 42));
		assertTrue(UniqueNumbers.containsValue(glava, 35));
		assertTrue(UniqueNumbers.containsValue(glava, 76));
		assertTrue(UniqueNumbers.containsValue(glava, 21));
	}

	@Test
	public void sortiranojendvatri() {
		TreeNode glava = null;
		glava = UniqueNumbers.addNode(glava, 2);
		glava = UniqueNumbers.addNode(glava, 3);
		glava = UniqueNumbers.addNode(glava, 1);
		String s = UniqueNumbers.minToMax(glava);
		assertEquals(" 1 2 3", s);
	}

	@Test
	public void sortiranoNullJedan() {
		String s = UniqueNumbers.maxToMin(null);
		assertEquals("", s);
	}

	@Test
	public void sortiranoNullDva() {
		String s = UniqueNumbers.minToMax(null);
		assertEquals("", s);
	}

	@Test
	public void sortiranotridvajen() {
		TreeNode glava = null;
		glava = UniqueNumbers.addNode(glava, 2);
		glava = UniqueNumbers.addNode(glava, 3);
		glava = UniqueNumbers.addNode(glava, 1);
		String s = UniqueNumbers.maxToMin(glava);
		assertEquals(" 3 2 1", s);
	}
}
