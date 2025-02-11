package com.fiap.clipshot_video_processor.utils;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipFile;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class FileUtilsTest {

    private static Path testDir;
    private static Path zipOutputPath;

    @BeforeAll
    static void setUp() throws IOException {
        testDir = Files.createTempDirectory("testDir");
        zipOutputPath = Paths.get(testDir.toString(), "test.zip");

        Files.createFile(testDir.resolve("file1.txt"));
        Files.createFile(testDir.resolve("file2.txt"));
    }

    @AfterAll
    static void tearDown() throws IOException {
        Files.walk(testDir)
                .map(Path::toFile)
                .forEach(File::delete);
    }

    @Test
    void zipFiles_createZipWithFiles() throws IOException {
        FileUtils.zipFiles(testDir.toString(), zipOutputPath.toString());

        assertThat(Files.exists(zipOutputPath)).isTrue();

        try (ZipFile zipFile = new ZipFile(zipOutputPath.toFile())) {
            assertThat(zipFile.getEntry("file1.txt")).isNotNull();
            assertThat(zipFile.getEntry("file2.txt")).isNotNull();
        }
    }

    @Test
    void zipFiles_shouldThrowExceptionWhenFolderDoesNotExist() {
        String invalidDir = "invalidDir";
        String zipOutput = "output.zip";

        assertThatThrownBy(() -> FileUtils.zipFiles(invalidDir, zipOutput))
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    void zipFiles_shouldThrowExceptionWhenFailToReadFile() throws IOException {
        Path testDir = Files.createTempDirectory("testDir");
        Path file = Files.createFile(testDir.resolve("file.txt"));

        file.toFile().setReadable(false);

        assertThatThrownBy(() -> FileUtils.zipFiles(testDir.toString(), "output.zip"))
                .isInstanceOf(RuntimeException.class);
    }
}