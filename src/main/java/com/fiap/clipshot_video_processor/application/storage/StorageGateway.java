package com.fiap.clipshot_video_processor.application.storage;

import java.io.File;

public interface StorageGateway {
    String upload(File file, String path);
}
