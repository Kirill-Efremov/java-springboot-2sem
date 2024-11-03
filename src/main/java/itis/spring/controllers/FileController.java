package itis.spring.controllers;

import itis.spring.exceptions.FileSizeException;
import itis.spring.models.FileInfo;
import itis.spring.models.User;
import itis.spring.services.FileService;
import itis.spring.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;


@RestController
@RequiredArgsConstructor
public class FileController {
    private final UserService userService;
    private final FileService fileService;

    @PostMapping("/file-upload-settings")
    public ResponseEntity<String> uploadFileForUser(@RequestParam("file") MultipartFile file, Principal principal) {
        try {
            User user = userService.getUserByPrincipal(principal);
            fileService.saveFileToStorageUser(user.getId(), file.getInputStream(),
                    file.getOriginalFilename(),
                    file.getContentType(),
                    file.getSize());
            return ResponseEntity.ok("Новая фотография успешно загружена!");
        } catch (FileSizeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ошибка при загрузке файла!");
        }
    }

    @GetMapping("/files/{fileId}")
    public ResponseEntity<?> downloadFile(@PathVariable Long fileId, HttpServletResponse response) {
        try {
            FileInfo fileInfo = fileService.getFileInfo(fileId);
            response.setContentType(fileInfo.getType());
            response.setContentLength(fileInfo.getSize().intValue());
            response.setHeader("Content-Disposition", "filename=\"" + fileInfo.getOriginalFileName() + "\"");
            fileService.readFileFromStorage(fileId, response.getOutputStream());
            return ResponseEntity.ok().build();
        } catch (RuntimeException | IOException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/file-upload-post")
    public ResponseEntity<String> uploadFileForPost(@RequestParam("file") MultipartFile file, @RequestParam Long postId) {
        try {
            fileService.saveFileToStoragePost(postId, file.getInputStream(),
                    file.getOriginalFilename(),
                    file.getContentType(),
                    file.getSize());
            return ResponseEntity.ok("Новая фотография успешно загружена!");
        } catch (FileSizeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ошибка при загрузке файла!");
        }
    }
}

