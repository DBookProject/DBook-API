package dgsw.dbook.api.Domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    Long userId;

    @Column(name = "user_email", unique = true)
    String email;

    @Column(name = "user_name")
    String name;

    @Column(name = "user_password")
    String password;

    @Column(name = "user_profile_image")
    Long profileImage;

    public User(String email) {
        this.email = email;
    }

}