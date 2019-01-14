package io.github.wushuzh.core.stream;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Helper
 */
public class Helper {

	public static Reader openReader(String fileName) throws IOException {
		return Files.newBufferedReader(Paths.get(fileName));
	}

	public static Writer openWriter(String fileName) throws IOException {
		return Files.newBufferedWriter(Paths.get(fileName));
	}

}