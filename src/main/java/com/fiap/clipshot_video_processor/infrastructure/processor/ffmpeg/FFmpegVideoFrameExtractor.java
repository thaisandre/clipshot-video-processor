package com.fiap.clipshot_video_processor.infrastructure.processor.ffmpeg;

import com.fiap.clipshot_video_processor.application.processor.VideoFrameExtractor;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class FFmpegVideoFrameExtractor implements VideoFrameExtractor {

    @Override
    public List<BufferedImage> execute(String videoPath) {
        try (FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(videoPath)) {

            grabber.start();

            double fps = grabber.getVideoFrameRate();
            int intervalInFrames = (int) (5 * fps);

            List<BufferedImage> frames = new ArrayList<>();
            for (int currentFrame = 0; currentFrame < grabber.getLengthInFrames(); currentFrame += intervalInFrames) {
                grabber.setFrameNumber(currentFrame);
                Frame frame = grabber.grabImage();
                if (frame != null) {
                    Java2DFrameConverter converter = new Java2DFrameConverter();
                    BufferedImage img = converter.convert(frame);
                    frames.add(img);
                }
            }

            grabber.stop();
            return frames;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}