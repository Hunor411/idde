package edu.bbte.idde.dhim2228.repository;

import edu.bbte.idde.dhim2228.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token, Long> {
    Token findByValue(String token);
}
