package hr.fer.zemris.java.hw17.trazilica;

import java.util.ArrayList;
import java.util.List;

/**
 * Defines a vector
 * 
 * @author mfures
 *
 */
public class Vector {
	/**
	 * Vectors data
	 */
	private List<Double> data = new ArrayList<>();

	/**
	 * Adds value to vector
	 * 
	 * @param d to be added
	 */
	public void add(double d) {
		data.add(d);
	}

	/**
	 * Returns data
	 * 
	 * @return data
	 */
	public List<Double> getData() {
		return data;
	}

	/**
	 * Cosines of angle between two vectors
	 * 
	 * @param v other vector
	 * @return Cosines of angle
	 */
	public double cos(Vector v) {
		List<Double> list = v.getData();
		double dot = 0, nA = 0, nB = 0;

		for (int i = 0; i < list.size(); i++) {
			nA += Math.pow(list.get(i), 2);
			nB += Math.pow(data.get(i), 2);
			dot += list.get(i) * data.get(i);
		}

		return dot / (Math.sqrt(nA) * Math.sqrt(nB));
	}
}
