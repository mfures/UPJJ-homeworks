package hr.fer.zemris.java.hw17.jvdraw.drawing;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

/**
 * General tool for drawing
 */
public interface Tool {
	/**
	 * Triggers on mouse press
	 * 
	 * @param e event
	 */
	public void mousePressed(MouseEvent e);

	/**
	 * Triggers on mouse release
	 * 
	 * @param e event
	 */
	public void mouseReleased(MouseEvent e);

	/**
	 * Triggers on mouse click
	 * 
	 * @param e event
	 */
	public void mouseClicked(MouseEvent e);

	/**
	 * Triggers on mouse move
	 * 
	 * @param e event
	 */
	public void mouseMoved(MouseEvent e);

	/**
	 * Triggers on mouse drag
	 * 
	 * @param e event
	 */
	public void mouseDragged(MouseEvent e);

	/**
	 * Paints
	 * 
	 * @param g2d used for painting
	 */
	public void paint(Graphics2D g2d);
}