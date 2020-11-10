package dgsw.dbook.api.Domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@NoArgsConstructor
public class EBookFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ebook_id")
    Long eBookFileId;

    @Lob
    @Column(name = "ebook_file", nullable = false)
    byte[] eBookFile;

    public EBookFile(@NotNull byte[] eBookImageFile) {
        this.eBookFile = eBookImageFile;
    }

}