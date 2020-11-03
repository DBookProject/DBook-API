package dgsw.dbook.api.Repository;

import dgsw.dbook.api.Domain.EBookImageFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.sql.Blob;
import java.util.Optional;

public interface EBookImageFileRepository extends JpaRepository<EBookImageFile, Long> {

    Optional<EBookImageFile> findById(long id);

    @Transactional
    @Modifying
    @Query(value = "UPDATE ebook_file SET ebook_image = :ebookImage WHERE ebook_id = :ebookId", nativeQuery = true)
    void editFile(@Param("ebookId") long ebookId, @Param("ebookImage") Blob ebookImage);

}
