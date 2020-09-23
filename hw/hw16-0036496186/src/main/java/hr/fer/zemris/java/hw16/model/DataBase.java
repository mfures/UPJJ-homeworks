package hr.fer.zemris.java.hw16.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Simple implementation of object provider for gallery
 * 
 * @author mfures
 *
 */
public class DataBase {
	/**
	 * Acts as table of tags
	 */
	private static List<String> tags = new ArrayList<>();

	/**
	 * Mapping of String to images
	 */
	private static Map<String, Image> images = new HashMap<>();

	/**
	 * Mapping of tags to Lists of images
	 */
	private static Map<String, List<String>> imagesPerTag = new HashMap<>();

	/**
	 * Getter for tags
	 * 
	 * @return tags
	 */
	public static List<String> getTags() {
		return tags;
	}

	/**
	 * Returns image for given name
	 * 
	 * @param name of image
	 * @return image
	 */
	public static Image imageForName(String name) {
		return images.get(name);
	}

	/**
	 * Returns list of images for given tage
	 * 
	 * @param tag of images
	 * @return list of images
	 */
	public static List<String> imagesForTag(String tag) {
		return imagesPerTag.get(tag);
	}

	/**
	 * Adds image to database
	 * 
	 * @param image to be added
	 */
	public static void put(Image image) {
		images.put(image.getPath(), image);
		for (String tag : image.getTags()) {
			if (!imagesPerTag.containsKey(tag)) {
				imagesPerTag.put(tag, new ArrayList<>());
				tags.add(tag);
			}

			imagesPerTag.get(tag).add(image.getPath());
		}
	}

}
