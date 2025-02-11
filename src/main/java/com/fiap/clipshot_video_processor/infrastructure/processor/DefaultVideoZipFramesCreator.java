package com.fiap.clipshot_video_processor.infrastructure.processor;

import com.fiap.clipshot_video_processor.application.processor.VideoZipFramesCreator;
import com.fiap.clipshot_video_processor.utils.FileUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class DefaultVideoZipFramesCreator implements VideoZipFramesCreator {

    private static final String TMP_DIR = "tmp/zips";

    @Override
    public File execute(List<BufferedImage> frames, String folderPath, String fileName) {
        saveFrames(frames, folderPath, fileName);
        return createZipFromDirectory(folderPath, fileName);
    }

    private void saveFrames(List<BufferedImage> frames, String imagesDir, String fileName) {
        File tempDirectory = new File(imagesDir);
        tempDirectory.mkdirs();

        for (int i = 0; i < frames.size(); i++) {
            BufferedImage img = frames.get(i);
            File tempFile = new File( imagesDir + "/frame_" + i + ".jpg");

            try {
                ImageIO.write(img, "jpg", tempFile);
            } catch (IOException e) {
                throw new RuntimeException("Failed to save frame", e);
            }
        }
    }

    private File createZipFromDirectory(String imagesDir, String fileName) {
        String outputZipPath = "%s/%s.zip".formatted(TMP_DIR, fileName);
        FileUtils.zipFiles(imagesDir, outputZipPath);
        return new File(outputZipPath);
    }
}
