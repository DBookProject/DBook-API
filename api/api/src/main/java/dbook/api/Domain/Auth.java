package dbook.api.Domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class Auth {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer aNo;

    @Column(length = 320)
    String uEmail;

    @Column(length = 6)
    String aAuth;

    @Column
    String uPassword;

    public Auth(String auth) {
        this.aAuth = auth;
    }

    public Integer getNo() { return this.aNo; }
    public String getAuth() { return this.aAuth; }
    public String getEmail() { return this.uEmail; }
    public String getPassword() { return this.uPassword; }

    public void setAuth(String auth) {
        this.aAuth = auth;
    }

    public void setEmail(String email) {
        this.uEmail = email;
    }

    public void setNo(Integer no) {
        this.aNo = no;
    }

    public void setPassword(String password) { this.uPassword = password; }

}
