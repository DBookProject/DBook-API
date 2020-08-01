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

    Integer eUploader;

    String eName;

    String eGenre;

    String eAuthor;

    @CreationTimestamp
    LocalDateTime eUploadDate;

    String eImage;

    String ePreview;

    Boolean eIsSharable;

    String ePath;

}
