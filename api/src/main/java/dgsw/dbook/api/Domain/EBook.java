package dgsw.dbook.api.Domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class EBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ebook_id")
    Long id;

    @Column(name = "ebook_title")
    String title;

    @Column(name = "ebook_author")
    String author;

    @Column(name = "category")
    Long category;

    @Column(name = "ebook_cover_image")
    Long coverImage;

    @Column(name = "ebook_book_file")
    Long bookFile;

    @Column(name = "ebook_description")
    String description;

    @Column(name = "ebook_uploader")
    Long uploader;

    @Column(name = "ebook_publisher")
    String publisher;
    
}