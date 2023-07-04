package com.poly.TKShop.repository;

import com.poly.TKShop.dto.UserDto;
import com.poly.TKShop.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

//    @Query(value ="select * from users  ", nativeQuery = true)
    List<User> findAll();
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update users o set o.password = ?2 where email = ?1", nativeQuery = true)
    void updateNewPassword(String email, String password);
}
