package hr.fer.zemris.java.gui.prim;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

/**
 * Demo program for showing of PrimList implementation
 * 
 * @author matfures
 *
 */
public class PrimDemo extends JFrame{
	private static final long serialVersionUID = 1L;

	/**
	 * Sets up prime demo
	 */
	private PrimDemo() {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		initGUI();
		pack();
	}
	
	/**
	 * Sets up window
	 */
	private void initGUI() {
		PrimListModel model=new PrimListModel();
		getContentPane().setLayout(new BorderLayout());
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1, 2));
		panel.add(new JScrollPane(new JList<String>(model)));
		panel.add(new JScrollPane(new JList<String>(model)));
		getContentPane().add(panel,BorderLayout.CENTER);
		JButton button = new JButton("sljedeći");
        button.addActionListener(x -> model.next());
        getContentPane().add(button, BorderLayout.PAGE_END);
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(()->new PrimDemo().setVisible(true));
	}
}
