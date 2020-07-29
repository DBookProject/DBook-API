package dbook.api.Repository;

import dbook.api.Domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LibraryRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserId(String userId);

}
