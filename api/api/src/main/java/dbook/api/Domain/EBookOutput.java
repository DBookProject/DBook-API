package dbook.api.Domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class EBookOutput {

    Integer no;

    Long uploader;

    String name;

    String genre;

    String author;

    LocalDateTime uploadDate;

    String image;

    String preview;

    Boolean isSharable;

    String path;

    public EBookOutput(EBook eBook) {
        this.no = eBook.getNo();
        this.uploader = eBook.getUploader();
        this.name = eBook.getName();
        this.genre = eBook.getGenre();
        this.author = eBook.getAuthor();
        this.uploadDate = eBook.getUploadDate();
        this.image = eBook.getImage();
        this.preview = eBook.getPreview();
        this.isSharable = eBook.getIsSharable();
        this.path = eBook.getPath();
    }

    public Integer getNo() {
        return no;
    }

    public Long getUploader() {
        return uploader;
    }

    public String getName() {
        return name;
    }

    public String getGenre() {
        return genre;
    }

    public String getAuthor() {
        return author;
    }

    public LocalDateTime getUploadDate() {
        return uploadDate;
    }

    public String getImage() {
        return image;
    }

    public String getPreview() {
        return preview;
    }

    public Boolean getIsSharable() {
        return isSharable;
    }

    public String getPath() {
        return path;
    }

}
