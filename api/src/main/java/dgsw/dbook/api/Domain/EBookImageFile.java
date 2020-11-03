package dgsw.dbook.api.Domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.sql.rowset.serial.SerialBlob;
import javax.validation.constraints.NotNull;
import java.sql.Blob;
import java.sql.SQLException;

@Entity
@Data
@NoArgsConstructor
public class EBookImageFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ebook_id")
    Long eBookImageId;

    @Column(name = "ebook_image", nullable = false)
    Blob eBookImageFile;

    public EBookImageFile(@NotNull byte[] eBookImageFile) throws SQLException {
        this.eBookImageFile = new SerialBlob(eBookImageFile);
    }

}