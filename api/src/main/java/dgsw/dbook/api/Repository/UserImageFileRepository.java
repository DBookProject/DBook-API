package dgsw.dbook.api.Repository;

import dgsw.dbook.api.Domain.UserImageFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserImageFileRepository extends JpaRepository<UserImageFile, Long> {

    Optional<UserImageFile> findById(long id);

}
