package com.loc.electronics_store.repository;

import com.loc.electronics_store.entity.Token;
import com.loc.electronics_store.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {
    List<Token> findByUser(User user);

    Optional<Token> findByToken(String token);
}

