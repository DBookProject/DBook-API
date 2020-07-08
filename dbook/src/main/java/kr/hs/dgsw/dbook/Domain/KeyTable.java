package kr.hs.dgsw.dbook.Domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class KeyTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer k_no;

    @Column(nullable = false, unique = true, length = 45)
    String k_userId;

    String k_key;

    String k_ip;

    @CreationTimestamp
    LocalDateTime k_date;

}
