package dbook.api.Domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class EBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer eNo;

    Long eUploader;

    String eName;

    String eGenre;

    String eAuthor;

    @CreationTimestamp
    LocalDateTime eUploadDate;

    String eImage;

    Boolean eIsSharable;

    String ePath;

    String eCategory;

    public EBook(String name) {
        this.setName(name);
    }

    public Integer getNo() {
        return eNo;
    }

    public void setMo(Integer no) {
        this.eNo = no;
    }

    public Long getUploader() {
        return eUploader;
    }

    public void setUploader(Long uploader) {
        this.eUploader = uploader;
    }

    public String getName() {
        return eName;
    }

    public void setName(String name) {
        this.eName = name;
    }

    public String getGenre() {
        return eGenre;
    }

    public void setGenre(String genre) {
        this.eGenre = genre;
    }

    public String getAuthor() {
        return eAuthor;
    }

    public void setAuthor(String author) {
        this.eAuthor = author;
    }

    public LocalDateTime getUploadDate() {
        return eUploadDate;
    }

    public void setUploadDate(LocalDateTime uploadDate) {
        this.eUploadDate = uploadDate;
    }

    public String getImage() {
        return eImage;
    }

    public void setImage(String image) {
        this.eImage = image;
    }

    public Boolean getIsSharable() {
        return eIsSharable;
    }

    public void setIsSharable(Boolean isSharable) {
        this.eIsSharable = isSharable;
    }

    public String getPath() {
        return ePath;
    }

    public void setPath(String path) {
        this.ePath = path;
    }

    public String getCategory() {
        return eCategory;
    }

    public void setCategory(String category) {
        this.eCategory = category;
    }
}
