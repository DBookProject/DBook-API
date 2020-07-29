package kr.hs.dgsw.dbook.Domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
public class LibraryPK implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    Integer uNo;

    @NotNull
    Integer eNo;

    public LibraryPK(@NotNull Integer uNo, @NotNull Integer eNo) {
        super();
        this.uNo = uNo;
        this.eNo = eNo;
    }

}
