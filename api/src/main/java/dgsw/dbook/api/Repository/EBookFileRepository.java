package dgsw.dbook.api.Repository;

import dgsw.dbook.api.Domain.EBookFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.sql.Blob;
import java.util.Optional;

public interface EBookFileRepository extends JpaRepository<EBookFile, Long> {

    Optional<EBookFile> findById(long id);

    @Transactional
    @Modifying
    @Query(value = "UPDATE ebook_file SET ebook_file = :ebookFile WHERE ebook_id = :ebookId", nativeQuery = true)
    void editFile(@Param("ebookId") long ebookId, @Param("ebookFile") Blob ebookFile);

}
