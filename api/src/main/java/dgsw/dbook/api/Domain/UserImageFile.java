package dgsw.dbook.api.Domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@NoArgsConstructor
public class UserImageFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    Long userImageId;

    @Lob
    @Column(name = "user_profile_image", nullable = false)
    byte[] userProfileImage;

    public UserImageFile(@NotNull byte[] userProfileImage) {
        this.userProfileImage = userProfileImage;
    }

}
