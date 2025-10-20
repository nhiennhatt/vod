package com.hiennhatt.vod.utils;

import org.apache.tika.mime.MimeTypeException;
import org.apache.tika.mime.MimeTypes;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.UUID;

public class StoreUtils {
    private static Path save(Path directory, String fileName, MultipartFile file, boolean isTemp) throws MimeTypeException, IOException {
        MimeTypes mimeTypes = MimeTypes.getDefaultMimeTypes();
        String extension = mimeTypes.forName(file.getContentType()).getExtension();
        Path path = directory.resolve(fileName + (isTemp ? "_temp" : extension));
        file.transferTo(path);
        return path;
    }

    public static Path save(Path directory, String fileName, MultipartFile file) throws MimeTypeException, IOException {
        return save(directory, fileName, file, false);
    }

    public static Path saveTemp(Path directory, String fileName, MultipartFile file) throws MimeTypeException, IOException {
        return save(directory, fileName, file, true);
    }

    public static String generateUid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
