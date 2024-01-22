package com.BookStoreBE.Repository;

import com.BookStoreBE.Model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

    Optional<User> findByEmail(String email);

    @Transactional
    @Modifying
    @Query("update User set name=:n where email=:e")
    Integer modifyName(@Param("n") String name,@Param("e") String email);


}
