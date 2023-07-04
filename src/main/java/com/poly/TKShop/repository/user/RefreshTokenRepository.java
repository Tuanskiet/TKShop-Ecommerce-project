package com.poly.TKShop.repository;

import com.poly.TKShop.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer> {
    @Query("SELECT o FROM RefreshToken o WHERE o.token = :token")
    Optional<RefreshToken> findRefreshTokenByToken(String token);
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update RefreshToken o set o.token = :newToken where o.token = :oldToken")
    void updateRefreshToken(String oldToken, String newToken);
}
