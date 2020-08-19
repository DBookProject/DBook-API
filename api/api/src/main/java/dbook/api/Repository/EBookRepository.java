package dbook.api.Repository;

import dbook.api.Domain.EBook;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EBookRepository extends JpaRepository<EBook, Integer> {

    Optional<EBook> findByeNo(Integer no);

}
