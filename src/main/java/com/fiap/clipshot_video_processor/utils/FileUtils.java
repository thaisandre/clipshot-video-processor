package com.fiap.clipshot_video_processor.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public final class FileUtils {

    private FileUtils() {}

    public static void zipFiles(String folder, String outputFilePath) {
//        outputFilePath = "%s.zip".formatted(outputFilePath);

        try (FileOutputStream fos = new FileOutputStream(outputFilePath);
             ZipOutputStream zos = new ZipOutputStream(fos)) {

            Path dirPath = Paths.get(folder);
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(dirPath)) {
                for (Path entry : stream) {
                    if (Files.isRegularFile(entry)) {
                        addToZip(entry, zos);
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private static void addToZip(Path file, ZipOutputStream zos) {
        try (FileInputStream fis = new FileInputStream(file.toFile())) {
            ZipEntry zipEntry = new ZipEntry(file.getFileName().toString());
            zos.putNextEntry(zipEntry);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer)) > 0) {
                zos.write(buffer, 0, length);
            }

            zos.closeEntry();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static void deleteFiles(String outputDir) {
        Path dirPath = Paths.get(outputDir);
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dirPath)) {
            for (Path entry : stream) {
                if (Files.isRegularFile(entry)) {
                    Files.delete(entry);
                    System.out.println("Arquivo excluído: " + entry.getFileName());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
