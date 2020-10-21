package dbook.api.Domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer tNo;

    @Column(nullable = false, unique = true, length = 45)
    String tOwnerid;

    String tKey;

    String tIp;

    @CreationTimestamp
    LocalDateTime tCreated;

    @UpdateTimestamp
    LocalDateTime tUpdated;

    public Token(String tOwnerid, String tKey, String tIp) {
        this.tOwnerid = tOwnerid;
        this.tKey = tKey;
        this.tIp = tIp;
    }

    public String getTOwnerid() {
        return tOwnerid;
    }

    public void setTOwnerid(String tOwnerid) {
        this.tOwnerid = tOwnerid;
    }

    public String getTKey() {
        return tKey;
    }

    public void setTKey(String tKey) {
        this.tKey = tKey;
    }

    public String getTIp() {
        return tIp;
    }

    public void setTIp(String tIp) {
        this.tIp = tIp;
    }

}
