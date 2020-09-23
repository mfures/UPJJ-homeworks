package hr.fer.zemris.java.gui.calc.model;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleUnaryOperator;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.zemris.java.gui.layouts.CalcLayout;

/**
 * Calculator. It has custom layout and implementation
 * 
 * @author matfures
 *
 */
public class Calculator extends JFrame {
	private static final long serialVersionUID = 1L;

	/**
	 * Default gap
	 */
	private static final int DEFAULT_GAP = 2;

	/**
	 * Actual calculator
	 */
	private CalcModelImpl calc = new CalcModelImpl();

	/**
	 * Panel containing calculators components
	 */
	private JPanel panel;

	private Stack<Double> stack = new Stack<>();

	/**
	 * Creates calculator
	 */
	public Calculator() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		initGUI();
		pack();
	}

	private void initGUI() {
		panel = new JPanel(new CalcLayout(DEFAULT_GAP));

		addGeneralUtility();
		addNumbers();
		addOperations();

		getContentPane().add(panel);
	}

	/**
	 * Adds operation buttons to panel
	 */
	private void addOperations() {
		panel.add(new MyButton("+", null, binaryActionListener((x, y) -> x + y), null, false), "5,6");
		panel.add(new MyButton("-", null, binaryActionListener((x, y) -> x - y), null, false), "4,6");
		panel.add(new MyButton("*", null, binaryActionListener((x, y) -> x * y), null, false), "3,6");
		panel.add(new MyButton("/", null, binaryActionListener((x, y) -> x / y), null, false), "2,6");

		panel.add(new MyButton("sin", "arcsin", unaryActionListener(Math::sin), unaryActionListener(Math::asin), false),
				"2,2");
		panel.add(new MyButton("cos", "arccos", unaryActionListener(Math::cos), unaryActionListener(Math::acos), false),
				"3,2");
		panel.add(new MyButton("tan", "arctan", unaryActionListener(Math::tan), unaryActionListener(Math::atan), false),
				"4,2");
		panel.add(new MyButton("ctg", "arcctg", unaryActionListener(x -> 1 / Math.tan(x)),
				unaryActionListener(x -> 1 / Math.atan(x)), false), "5,2");

		panel.add(new MyButton("1/x", null, unaryActionListener(x -> 1 / x), null, false), "2,1");
		panel.add(new MyButton("log", "10^x", unaryActionListener(Math::log10),
				unaryActionListener(x -> Math.pow(10, x)), false), "3,1");
		panel.add(new MyButton("ln", "e^x", unaryActionListener(Math::log),
				unaryActionListener(x -> Math.pow(Math.E, x)), false), "4,1");
		panel.add(new MyButton("x^n", "x^(1/n)", binaryActionListener((x, y) -> Math.pow(x, y)),
				binaryActionListener((x, y) -> Math.pow(x, 1 / y)), false), "5,1");

	}

	/**
	 * Creates new action listener that handles unary operators
	 * 
	 * @param dbu operator for doubles
	 * @return new action listener
	 */
	private ActionListener unaryActionListener(DoubleUnaryOperator duo) {
		return new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (calc.getPendingBinaryOperation() == null) {
					calc.setValue(duo.applyAsDouble(calc.getValue()));
				} else {
					calc.setValue(duo.applyAsDouble(
							calc.getPendingBinaryOperation().applyAsDouble(calc.getActiveOperand(), calc.getValue())));
				}
			}
		};
	}

	/**
	 * Creates new action listener that handles binary operators
	 * 
	 * @param dbo operator for doubles
	 * @return new action listener
	 */
	private ActionListener binaryActionListener(DoubleBinaryOperator dbo) {
		return new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (calc.getPendingBinaryOperation() == null) {
					calc.setActiveOperand(calc.getValue());
					calc.clear();
					calc.setPendingBinaryOperation(dbo);
				} else {
					calc.setActiveOperand(
							calc.getPendingBinaryOperation().applyAsDouble(calc.getActiveOperand(), calc.getValue()));
					calc.clear();
					calc.setPendingBinaryOperation(dbo);
				}
			}
		};
	}

	/**
	 * Adds general utility buttons to panel
	 */
	private void addGeneralUtility() {
		panel.add(screen(calc.toString()), "1,1");
		panel.add(new MyButton("=", null, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (calc.getPendingBinaryOperation() != null) {
					calc.setValue(
							calc.getPendingBinaryOperation().applyAsDouble(calc.getActiveOperand(), calc.getValue()));
				} else {
					calc.setValue(calc.getValue());
				}
			}
		}, null, false), "1,6");
		panel.add(new MyButton("clr", null, x -> calc.clear(), null, false), "1,7");
		panel.add(new MyButton("  reset  ", null, x -> calc.clearAll(), null, false), "2,7");
		panel.add(new MyButton("+/-", null, myActionListener(x -> calc.swapSign()), null, false), "5,4");
		panel.add(new MyButton(".", null, myActionListener(x -> calc.insertDecimalPoint()), null, false), "5,5");
		panel.add(new MyButton("push", null, myActionListener(x -> stack.push(calc.getValue())), null, false), "3,7");
		panel.add(new MyButton("pop", null, myActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (stack.isEmpty()) {
					JOptionPane.showMessageDialog(panel, "Stack is empty");
				} else {
					calc.setValue(stack.pop());
				}
			}
		}), null, false), "4,7");

		JCheckBox cbox = new JCheckBox("inv");
		cbox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				for (var x : panel.getComponents()) {
					if (x instanceof MyButton) {
						((MyButton) x).inverse();
					}
				}
			}
		});

		panel.add(cbox, "5,7");
	}

	/**
	 * Adds number buttons to panel
	 */
	private void addNumbers() {
		panel.add(new MyButton("0", null, myActionListener(x -> calc.insertDigit(0)), null, true), "5,3");
		panel.add(new MyButton("1", null, myActionListener(x -> calc.insertDigit(1)), null, true), "4,3");
		panel.add(new MyButton("2", null, myActionListener(x -> calc.insertDigit(2)), null, true), "4,4");
		panel.add(new MyButton("3", null, myActionListener(x -> calc.insertDigit(3)), null, true), "4,5");
		panel.add(new MyButton("4", null, myActionListener(x -> calc.insertDigit(4)), null, true), "3,3");
		panel.add(new MyButton("5", null, myActionListener(x -> calc.insertDigit(5)), null, true), "3,4");
		panel.add(new MyButton("6", null, myActionListener(x -> calc.insertDigit(6)), null, true), "3,5");
		panel.add(new MyButton("7", null, myActionListener(x -> calc.insertDigit(7)), null, true), "2,3");
		panel.add(new MyButton("8", null, myActionListener(x -> calc.insertDigit(8)), null, true), "2,4");
		panel.add(new MyButton("9", null, myActionListener(x -> calc.insertDigit(9)), null, true), "2,5");
	}

	/**
	 * Creates a screen
	 * 
	 * @param text on screen
	 * @return JLabel used as screen
	 */
	private JLabel screen(String text) {
		JLabel l = new JLabel(text, SwingConstants.RIGHT);
		l.setBackground(Color.YELLOW);
		l.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		l.setOpaque(true);
		l.setFont(l.getFont().deriveFont(30f));
		calc.addCalcValueListener(x -> l.setText(x.toString()));
		return l;
	}

	/**
	 * Creates calculator
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new Calculator().setVisible(true);
		});
	}

	/**
	 * Extension of JButton with a constructor that creates button with given text
	 * and adds listener to it. Provides functionality with inverse operations
	 * 
	 * @author matfures
	 *
	 */
	private static class MyButton extends JButton {
		private static final long serialVersionUID = 1L;

		/**
		 * Currently used listener
		 */
		private ActionListener listener;

		/**
		 * Listener used if inversion occurs
		 */
		private ActionListener inverse;

		/**
		 * Currently shown text
		 */
		private String text;

		/**
		 * Text shown if inversion occurs
		 */
		private String itext;

		/**
		 * Constructor. Creates button with given text, and adds listener to it, and
		 * remembers inverse listener
		 * 
		 * @param text     to be placed
		 * @param listener to be
		 */
		private MyButton(String text, String itext, ActionListener listener, ActionListener inverse, boolean bigFont) {
			super(text);
			addActionListener(listener);

			this.listener = listener;
			this.inverse = inverse == null ? listener : inverse;

			this.text = text;
			this.itext = itext == null ? text : itext;

			setBackground(new Color(0xCCFFFF));

			if (bigFont) {
				setFont(this.getFont().deriveFont(30f));
			}
		}

		/**
		 * Inverses active listener on button
		 */
		public void inverse() {
			removeActionListener(listener);
			addActionListener(inverse);
			setText(itext);

			ActionListener tmp = inverse;
			inverse = listener;
			listener = tmp;

			String tmp2 = itext;
			itext = text;
			text = tmp2;
		}
	}

	/**
	 * Creates an action listener that handles exceptions
	 * 
	 * @param ac to be handled
	 * @return new action listener
	 */
	private ActionListener myActionListener(ActionListener ac) {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					ac.actionPerformed(e);
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(panel, e2.getMessage());
				}

			}
		};
	}
}
