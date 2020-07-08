package kr.hs.dgsw.dbook.Repository;

import kr.hs.dgsw.dbook.Domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserIdAndPassword(String userId, String password);

    Optional<User> findByUserId(String userId);
}