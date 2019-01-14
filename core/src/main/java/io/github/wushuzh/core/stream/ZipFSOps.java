package io.github.wushuzh.core.stream;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;

/**
 * ZipFSOps
 */
public class ZipFSOps {
    public static void main(String[] args) {
        try (FileSystem zipFs = openZip(Paths.get("myData.zip"))) {
            copyToZip(zipFs);
        } catch (Exception e) {
            System.out.println(e.getClass().getSimpleName() + " - " + e.getMessage());
        }
    }

    private static void copyToZip(FileSystem zipFs) throws IOException {
        Path sourceFile = Paths.get("file1.txt");
        Path destFile = zipFs.getPath("/file1Copied");

        Files.copy(sourceFile, destFile, StandardCopyOption.REPLACE_EXISTING);
    }

    private static FileSystem openZip(Path zipPath) throws URISyntaxException, IOException {
        Map<String, String> providerProps = new HashMap<>();
        providerProps.put("create", "true");

        URI zipUri = new URI("jar:file", zipPath.toUri().getPath(), null);
        FileSystem zipFs = FileSystems.newFileSystem(zipUri, providerProps);

        return zipFs;
	}
}