package org.demo.project.repository;

import org.demo.project.entity.Admin;
import org.demo.project.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AdminRepository extends JpaRepository<Admin, String> {
    @Query("select case when count(u) > 0 then true else false end from Admin u where u.user_name = ?1 and u.pass_word = ?2")
    public boolean findExistByUser_nameAndPass_word(String user_name, String pass_word);
}
