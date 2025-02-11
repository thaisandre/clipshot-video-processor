//package com.fiap.clipshot_video_processor.infrastructure.storage.local;
//
//import com.fiap.clipshot_video_processor.application.storage.StorageGateway;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.io.File;
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Paths;
//
//public class LocalStorageGateway implements StorageGateway {
//
//    private static final Logger logger = LoggerFactory.getLogger(LocalStorageGateway.class);
//
//    @Override
//    public void upload(File file, String path) {
//        try {
//            System.out.println("File file: " + file.getName());
//            System.out.println("Path path: " + path);
//            Files.copy(file.toPath(), Paths.get( path + file.getName()));
//            logger.info("[LocalStorageGateway] Arquivo salvo localmente: {}", file.getName());
//        } catch (IOException e) {
//            String errorMessage = "Falha ao salvar o arquivo localmente: %s".formatted(file.getName());
//            logger.error("[LocalStorageGateway] {}", errorMessage);
//            throw new RuntimeException(errorMessage, e);
//        }
//    }
//}
