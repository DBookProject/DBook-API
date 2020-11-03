package dgsw.dbook.api.Domain;

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
    @Column(name = "token_id")
    Long id;

    @Column(name = "token_ip")
    String ip;

    @Column(name = "token_token", unique = true)
    String token;

    @CreationTimestamp
    @Column(name = "token_created")
    LocalDateTime createdTime;

    @Column(name = "user_email")
    String userEmail;

    @UpdateTimestamp
    @Column(name = "token_updated")
    LocalDateTime updatedTime;

}