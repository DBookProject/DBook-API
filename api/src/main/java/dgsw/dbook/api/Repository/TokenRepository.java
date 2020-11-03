package dgsw.dbook.api.Repository;

import dgsw.dbook.api.Domain.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {

    Optional<Token> findByUserEmail(String userEmail);

    Optional<Token> findByToken(String token);

}
