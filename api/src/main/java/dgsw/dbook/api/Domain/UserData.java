package dgsw.dbook.api.Domain;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@Data
public class UserData implements Serializable {

    private String email;

    private String name;

    private String password;

    private MultipartFile profileImage;

}
