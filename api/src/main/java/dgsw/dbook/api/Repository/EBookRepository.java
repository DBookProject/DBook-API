package dgsw.dbook.api.Repository;

import dgsw.dbook.api.Domain.EBook;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EBookRepository extends JpaRepository<EBook, Integer> {

    Optional<EBook> findById(Long id);

    List<EBook> findByUploader(String email);

}
