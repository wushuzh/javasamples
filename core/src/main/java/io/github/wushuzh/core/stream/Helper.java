package io.github.wushuzh.core.stream;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Helper
 */
public class Helper {

	public static Reader openReader(String fileName) throws IOException {
		return Files.newBufferedReader(Paths.get(fileName));
	}

}