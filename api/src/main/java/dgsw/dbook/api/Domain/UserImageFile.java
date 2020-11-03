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
public class UserImageFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    Long userImageId;

    @Column(name = "user_profile_image", nullable = false)
    Blob userProfileImage;

    public UserImageFile(@NotNull byte[] userProfileImage) throws SQLException {
        this.userProfileImage = new SerialBlob(userProfileImage);
    }

}
