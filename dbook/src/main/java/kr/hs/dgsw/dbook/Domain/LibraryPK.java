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
    Integer u_no;

    @NotNull
    Integer e_no;

    public LibraryPK(@NotNull Integer u_no, @NotNull Integer e_no) {
        super();
        this.u_no = u_no;
        this.e_no = e_no;
    }

}
