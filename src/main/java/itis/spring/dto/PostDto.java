package itis.spring.dto;

import itis.spring.models.FileInfo;
import lombok.*;

@Data
public class PostDto {
    private Long id;
    private String title;
    private String content;
    private Long price;
    private String city;
    private Long userId;
    private FileInfo photo;
}
