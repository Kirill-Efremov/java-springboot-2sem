package itis.spring.services;

import itis.spring.models.FileInfo;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;

public interface FileService {
    void saveFileToStorageUser(Long userId, InputStream inputStream, String originalFileName, String contentType, Long size);
    void readFileFromStorage(Long fileId, OutputStream outputStream) throws FileNotFoundException;
    void saveFileToStoragePost(Long postId, InputStream inputStream, String originalFileName, String contentType, Long size);
    FileInfo getFileInfo(Long fileId);
}
