package dgsw.dbook.api.Domain;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@Data
public class EBookData implements Serializable {

    private String title;

    private String author;

    private String category;

    private MultipartFile coverImage;

    private MultipartFile bookFile;

    private String description;

    private String publisher;

}
