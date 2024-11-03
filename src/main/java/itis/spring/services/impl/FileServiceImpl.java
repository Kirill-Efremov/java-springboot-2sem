package itis.spring.services.impl;

import itis.spring.exceptions.FileSizeException;
import itis.spring.models.FileInfo;
import itis.spring.repository.FileRepository;
import itis.spring.repository.PostRepository;
import itis.spring.repository.UserRepository;
import itis.spring.services.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {
    @Value("${file.storage.path}")
    private String path;
    private final FileRepository fileRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    @Override
    public void saveFileToStorageUser(Long userId, InputStream inputStream, String originalFileName, String contentType, Long size) {
        if (size > 10_000_000) {
            throw new FileSizeException("File is too large");
        }
        FileInfo newFileInfo = new FileInfo(
                null,
                originalFileName,
                UUID.randomUUID().toString(),
                size,
                contentType
        );
        try {
            FileInfo previousFileInfo = userRepository.findAvatarFileInfoByUserId(userId);
            newFileInfo = fileRepository.save(newFileInfo);
            userRepository.updateAvatarForUser(userId, newFileInfo);
            if (previousFileInfo != null) {
                Files.deleteIfExists(Paths.get(path + previousFileInfo.getStorageFileName() + "." + previousFileInfo.getType().split("/")[1]));
                fileRepository.deleteById(previousFileInfo.getId());
                log.info("LOG - Delete previous user photo");
            }
            log.info("LOG - Update user photo");
            Files.copy(inputStream, Paths.get(path + newFileInfo.getStorageFileName() + "." + newFileInfo.getType().split("/")[1]));
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }

    }
    @Override
    public void readFileFromStorage(Long fileId, OutputStream outputStream) throws FileNotFoundException {
        FileInfo fileInfo = fileRepository.findById(fileId)
                .orElseThrow(() -> new FileNotFoundException("File not found"));
        File file = new File(path + fileInfo.getStorageFileName() + "." + fileInfo.getType().split("/")[1]);
        try {
            Files.copy(file.toPath(), outputStream);
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }
    }
    @Override
    public void saveFileToStoragePost(Long postId, InputStream inputStream, String originalFileName, String contentType, Long size) {
        if (size > 10_000_000) {
            throw new FileSizeException("File is too large");
        }

        FileInfo fileInfo = new FileInfo(
                null,
                originalFileName,
                UUID.randomUUID().toString(),
                size,
                contentType
        );

        try {
            fileInfo = fileRepository.save(fileInfo);
            postRepository.setPhotoForPost(postId, fileInfo);
            log.info("LOG - Save post photo");
            Files.copy(inputStream, Paths.get(path  + fileInfo.getStorageFileName() + "." + fileInfo.getType().split("/")[1]));
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }
    @Override
    public FileInfo getFileInfo(Long fileId) {
        return fileRepository.findById(fileId)
                .orElseThrow(() -> new NoSuchElementException("File not found"));
    }
}
