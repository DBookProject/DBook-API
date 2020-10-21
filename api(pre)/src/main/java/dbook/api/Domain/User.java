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

    @Column(nullable = true)
    String uPassword;

    @Column(nullable = true)
    String uName;

    @Column(nullable = false)
    Boolean uIsCertified;

    @Column(nullable = true)
    Integer uCertifyCode;

    @Column(nullable = true)
    String uProfileImage;

    @Column(nullable = true)
    String uLibraryImage;

    @Column(nullable = true)
    String uLibraryName;

    public User(String email) {
        this.setEmail(email);
        this.setIsCertified(false);
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

    public Boolean getIsCertified() { return this.uIsCertified; }

    public Integer getCertifyCode() {
        return uCertifyCode;
    }

    public String getProfileImage() { return uProfileImage; }

    public String getLibraryImage() { return uLibraryImage; }

    public String getLibraryName() { return uLibraryName; }

    public void setPassword(String password) { this.uPassword = password; }
    public void setEmail(String email) {this.uEmail = email; }
    public void setName(String name) {
        this.uName = name;
    }
    public void setIsCertified(Boolean isCertified) { this.uIsCertified = isCertified; }
    public void setCertifyCode(Integer certifyCode) { this.uCertifyCode = certifyCode; }
    public void setProfileImage(String profileImage) { this.uProfileImage = profileImage; }
    public void setLibraryImage(String libraryImage) { this.uLibraryImage = libraryImage; }
    public void setLibraryName(String libraryName) { this.uLibraryName = libraryName; }

}