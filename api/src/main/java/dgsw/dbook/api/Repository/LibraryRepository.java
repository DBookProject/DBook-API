package dgsw.dbook.api.Repository;

import dgsw.dbook.api.Domain.Library;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LibraryRepository extends JpaRepository<Library, Integer> {

    List<Library> findByPk_UserEmail(String userEmail);

}
