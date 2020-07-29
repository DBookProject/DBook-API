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
    Integer e_no;

    Integer e_uploader;

    String e_name;

    String e_genre;

    String e_author;

    @CreationTimestamp
    LocalDateTime e_uploadDate;

    String e_image;

    String e_preview;

    Boolean e_isSharable;

    String e_path;

}
