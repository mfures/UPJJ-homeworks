package hr.fer.zemris.java.hw06.shell;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Used for help over filtering single file name
 * 
 * @author Matej Fureš
 *
 */
public class FilterResult {
	/**
	 * Used for matching
	 */
	Matcher m;

	/**
	 * Used to match over
	 */
	Path path;

	/**
	 * Constructor. Arguments can't be null. Path mustn't be to a directory and must
	 * exist
	 * 
	 * @param patter for matcher
	 * @param path   path to file
	 * @throws NullPointerException     if arguments are null
	 * @throws IllegalArgumentException if arguments are invalid
	 */
	public FilterResult(String pattern, Path path) {
		Objects.requireNonNull(pattern);
		Objects.requireNonNull(path);

		this.path = path;
		m = Pattern.compile(pattern).matcher(path.getFileName().toString());
		m.find();
	}

	/**
	 * Returns file name
	 */
	public String toString() {
		return path.getFileName().toString();
	}

	/**
	 * Returns number of groups in matcher
	 * 
	 * @return number of groups
	 */
	public int numberOfGroups() {
		return m.groupCount() + 1;
	}

	/**
	 * Returns index group. If index is valid
	 * 
	 * @param index for group
	 * @return group at that position
	 * @throws IllegalArgumentException for invalid index
	 */
	public String group(int index) {
		if (index < 0 || index > m.groupCount()) {
			throw new IllegalArgumentException();
		}

		return m.group(index);
	}

	/**
	 * Returns list of filter results that match pattern
	 * 
	 * @param dir     to be checked
	 * @param pattern to be matched
	 * @return list of filterResult
	 * @throws IOException              if something goes wrong
	 * @throws NullPointerException     if something is null
	 * @throws IllegalArgumentException if input is invalid
	 */
	public static List<FilterResult> filter(Path dir, String pattern) throws IOException {
		Objects.requireNonNull(dir);
		Objects.requireNonNull(pattern);

		if (!Files.exists(dir)) {
			throw new IllegalArgumentException("File doesn't exist");
		}

		if (!Files.isDirectory(dir)) {
			throw new IllegalArgumentException("File isn't directory");
		}

		List<FilterResult> list = new ArrayList<>();
		Pattern pat = Pattern.compile(pattern, Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
		
		Matcher mat;

		for (Path p : Files.newDirectoryStream(dir)) {
			if (Files.isDirectory(p)) {
				continue;
			}

			mat = pat.matcher(p.getFileName().toString());
			if (mat.find()) {
				list.add(new FilterResult(pattern, p));
			}
		}

		return list;
	}

}
