package dbook.api.Repository;

import dbook.api.Domain.Auth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthRepository extends JpaRepository<Auth, Integer> {

    Optional<Auth> findByaAuth(String auth);
    Optional<Auth> findByaNo(Integer no);
    Optional<Auth> findByuEmail(String email);

}
