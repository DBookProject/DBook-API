package dbook.api.Domain;

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
    Long uNo;

    @NotNull
    Integer eNo;

    public LibraryPK(@NotNull Long uNo, @NotNull Integer eNo) {
        super();
        this.uNo = uNo;
        this.eNo = eNo;
    }

    public Long getuNo() {
        return uNo;
    }

    public void setuNo(Long no) {
        this.uNo = no;
    }

    public Integer geteNo() {
        return eNo;
    }

    public void seteNo(Integer no) {
        this.eNo = no;
    }

}
