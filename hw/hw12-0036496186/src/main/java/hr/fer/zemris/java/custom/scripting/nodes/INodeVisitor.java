package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * Visitor interface used for visiting nodes
 * 
 * @author matfures
 *
 */
public interface INodeVisitor {
	/**
	 * Used for visiting TextNode nodes
	 * 
	 * @param node that is visited
	 */
	public void visitTextNode(TextNode node);

	/**
	 * Used for visiting ForLoopNode nodes
	 * 
	 * @param node that is visited
	 */
	public void visitForLoopNode(ForLoopNode node);

	/**
	 * Used for visiting EchoNode nodes
	 * 
	 * @param node that is visited
	 */
	public void visitEchoNode(EchoNode node);

	/**
	 * Used for visiting DocumentNode nodes
	 * 
	 * @param node that is visited
	 */
	public void visitDocumentNode(DocumentNode node);
}
