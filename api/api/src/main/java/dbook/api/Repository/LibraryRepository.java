package dbook.api.Repository;

import dbook.api.Domain.Library;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LibraryRepository extends JpaRepository<Library, Integer> {

    List<Library> findByPk_uNo(Integer no);

}
