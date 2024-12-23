package org.demo.project.repository;

import org.demo.project.entity.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ManagerRepository extends JpaRepository<Manager, String> {
    @Query("select case when count(u) > 0 then true else false end from Manager u where u.user_name = ?1")
    public boolean findExistByUser_name(String user_name);

    @Query("select m from Manager m where m.user_name = :user_name")
    public Manager findManagerByUser_name(@Param("user_name") String user_name);

    @Query("select case when count(u) > 0 then true else false end from Manager u where u.user_name = ?1 and u.pass_word = ?2")
    public boolean findExistByUser_nameAndPass_word(String user_name, String pass_word);
}
