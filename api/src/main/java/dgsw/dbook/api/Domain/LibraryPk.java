package dgsw.dbook.api.Domain;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
public class LibraryPk implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    String userEmail;

    @NotNull
    Long ebookId;

    public LibraryPk(@NotNull String userEmail, @NotNull Long ebookId) {
        super();
        this.userEmail = userEmail;
        this.ebookId = ebookId;
    }

}
