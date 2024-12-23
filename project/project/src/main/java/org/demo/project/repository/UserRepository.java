package org.demo.project.repository;

import org.demo.project.entity.Manager;
import org.demo.project.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    @Query("select case when count(u) > 0 then true else false end from User u where u.user_name = ?1")
    public boolean findExistByUser_name(String user_name);

    @Query("select case when count(u) > 0 then true else false end from User u where u.email = ?1")
    public boolean findExistByEmail(String email);

    @Query("select u from User u where u.user_name = :user_name or u.email = :email")
    public List<User> findUser(@Param("user_name") String user_name, @Param("email") String email);

    @Query("select case when count(u) > 0 then true else false end from User u where u.user_name = ?1 and u.pass_word = ?2")
    public boolean findExistByUser_nameAndPass_word(String user_name, String pass_word);

    @Query("select id from User where user_name = :user_name")
    public String findIdByUsername(String user_name);


}


