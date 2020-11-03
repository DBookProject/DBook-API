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
public class EBookFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ebook_id")
    Long eBookFileId;

    @Column(name = "ebook_file", nullable = false)
    Blob eBookFile;

    public EBookFile(@NotNull byte[] eBookImageFile) throws SQLException {
        this.eBookFile = new SerialBlob(eBookImageFile);
    }

}