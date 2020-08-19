package dbook.api.Repository;

import dbook.api.Domain.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Integer> {

    Optional<Token> findBytOwnerid(String ownerId);

}
