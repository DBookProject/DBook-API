package dgsw.dbook.api.Domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@NoArgsConstructor
public class EBookImageFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ebook_id")
    Long eBookImageId;

    @Lob
    @Column(name = "ebook_image", nullable = false)
    byte[] eBookImageFile;

    public EBookImageFile(@NotNull byte[] eBookImageFile) {
        this.eBookImageFile = eBookImageFile;
    }

}