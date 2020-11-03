package dgsw.dbook.api.Repository;

import dgsw.dbook.api.Domain.EBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface EBookRepository extends JpaRepository<EBook, Long> {

    Optional<EBook> findById(Long id);

    List<EBook> findByUploader(Long uploader);

    List<EBook> findByCategory(Long categoryId);

    @Transactional
    @Modifying
    @Query(value = "UPDATE ebook SET ebook_title = :ebookTitle, ebook_author = :ebookAuthor, ebook_category = :ebookCategory, " +
            "ebook_description = :ebookDescription, ebook_publisher = :ebookPublisher WHERE ebook_id = :ebookId", nativeQuery = true)
    void editBook(@Param("ebookId") long ebookId, @Param("ebookTitle") String ebookTitle,
                  @Param("ebookAuthor") String ebookAuthor, @Param("ebookCategory") String ebookCategory,
                  @Param("ebookDescription") String ebookDescription, @Param("ebookPublisher") String ebookPublisher);

}
