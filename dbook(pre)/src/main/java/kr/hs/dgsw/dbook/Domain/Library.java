package kr.hs.dgsw.dbook.Domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class Library {

    @EmbeddedId
    LibraryPK pk;

}
