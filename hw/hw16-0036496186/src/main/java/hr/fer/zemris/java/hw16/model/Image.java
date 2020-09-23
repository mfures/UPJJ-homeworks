package hr.fer.zemris.java.hw16.model;

/**
 * Defines image as used in galery
 * 
 * @author mfures
 *
 */
public class Image {
	/**
	 * Path to images location on disc
	 */
	private String path;

	/**
	 * Name to be shown in galery
	 */
	private String name;

	/**
	 * Pictures tags
	 */
	private String[] tags;

	/**
	 * Constructor
	 * 
	 * @param path for image
	 * @param name for image
	 * @param tags of image
	 */
	public Image(String path, String name, String[] tags) {
		this.path = path;
		this.name = name;
		this.tags = tags;
	}

	/**
	 * Getter for path
	 * 
	 * @return path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * Getter for name
	 * 
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Getter for tags
	 * 
	 * @return tags
	 */
	public String[] getTags() {
		return tags;
	}
}
