package hr.fer.zemris.java.gui.calc.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.DoubleBinaryOperator;

/**
 * CalcModel implementation. Provides an implementation of calculator with
 * several operations
 * 
 * @author matfures
 *
 */
public class CalcModelImpl implements CalcModel {
	/**
	 * List containing all listeners
	 */
	private List<CalcValueListener> listeners = new ArrayList<>();

	/**
	 * Currently held value for operations
	 */
	private String current = "";

	/**
	 * Is currently held string negative
	 */
	private boolean negative;

	/**
	 * Hold information of current string being editable
	 */
	private boolean editable = true;

	/**
	 * Currently used operation
	 */
	private DoubleBinaryOperator operation;

	/**
	 * Stored value
	 */
	private double activeOperand;

	/**
	 * Hold information of current activeOperand being set
	 */
	private boolean operandSet;

	@Override
	public void addCalcValueListener(CalcValueListener l) {
		Objects.requireNonNull(l);

		listeners.add(l);
	}

	@Override
	public void removeCalcValueListener(CalcValueListener l) {
		listeners.remove(l);
	}

	@Override
	public double getValue() {
		if (current.isEmpty()) {
			if (negative) {
				return -0;
			}

			return 0.0;
		}

		return Double.parseDouble((negative ? "-" : "") + current);
	}

	@Override
	public void setValue(double value) {
		current = String.valueOf(value);
		if (current.startsWith("-")) {
			current = current.substring(1);
			negative = true;
		} else {
			negative = false;
		}

		operandSet=false;
		operation=null;
		editable = false;
		notifyAllListeners();
	}

	@Override
	public boolean isEditable() {
		return editable;
	}

	@Override
	public void clear() {
		current = "";
		negative = false;
		editable = true;
		notifyAllListeners();
	}

	@Override
	public void clearAll() {
		clear();
		operandSet = false;
		operation = null;
	}

	@Override
	public void swapSign() throws CalculatorInputException {
		if (!editable) {
			throw new CalculatorInputException("Input wasn't editable");
		}

		negative = !negative;

		notifyAllListeners();
	}

	@Override
	public void insertDecimalPoint() throws CalculatorInputException {
		if (current.isEmpty() || current.contains(".") || !editable) {
			throw new CalculatorInputException("Invali operation");
		}

		current += ".";
		notifyAllListeners();
	}

	@Override
	public void insertDigit(int digit) throws CalculatorInputException, IllegalArgumentException {
		if (!editable) {
			throw new CalculatorInputException("Not editable");
		}

		if (digit < 0 || digit > 9) {
			throw new IllegalArgumentException("Invalid digit");
		}

		if (Double.parseDouble((negative ? "-" : "") + current + digit) == Double.POSITIVE_INFINITY) {
			throw new CalculatorInputException("Adding that digit would make number out of range for operations");
		}

		if (digit == 0) {
			if (!current.isEmpty()) {
				if (!current.equals("0")) {
					current += digit;
					notifyAllListeners();
				}
			} else {
				current = "0";
			}
		} else {
			if (current.equals("0")) {
				current = String.valueOf(digit);
			} else {
				current += digit;
			}
			notifyAllListeners();
		}
	}

	@Override
	public boolean isActiveOperandSet() {
		return operandSet;
	}

	@Override
	public double getActiveOperand() throws IllegalStateException {
		if (!operandSet) {
			throw new IllegalStateException("Operand wasn't set");
		}

		return activeOperand;
	}

	@Override
	public void setActiveOperand(double activeOperand) {
		this.activeOperand = activeOperand;
		operandSet = true;
	}

	@Override
	public void clearActiveOperand() {
		operandSet = false;
	}

	@Override
	public DoubleBinaryOperator getPendingBinaryOperation() {
		return operation;
	}

	@Override
	public void setPendingBinaryOperation(DoubleBinaryOperator op) {
		operation = op;
	}

	/**
	 * Notifies all held listeners that value has been changed
	 */
	private void notifyAllListeners() {
		for (CalcValueListener cvl : listeners) {
			cvl.valueChanged(this);
		}
	}

	@Override
	public String toString() {
		if (current.isEmpty()) {
			if (negative) {
				return "-0";
			}

			return "0";
		}

		return (negative ? "-" : "") + current;
	}
}
