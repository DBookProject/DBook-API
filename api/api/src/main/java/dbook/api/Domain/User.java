package dbook.api.Domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long uId;

    @Column(nullable = false, unique = true, length = 320)
    String uEmail;

    @Column(nullable = false)
    String uPassword;

    @Column(nullable = true)
    String uName;

    public User(String email) {
        this.setEmail(email);
    }

    public Long getId() {
        return uId;
    }

    public String getPassword() {
        return uPassword;
    }

    public String getName() {
        return uName;
    }

    public String getEmail() {
        return uEmail;
    }

    public void setPassword(String password) { this.uPassword = password; }
    public void setEmail(String email) {this.uEmail = email; }
    public void setName(String name) {
        this.uName = name;
    }
}