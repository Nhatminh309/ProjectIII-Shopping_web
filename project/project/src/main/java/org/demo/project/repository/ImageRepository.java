package org.demo.project.repository;

import org.demo.project.entity.Image;
import org.demo.project.entity.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface ImageRepository extends JpaRepository<Image, String> {
    @Modifying
    @Query("DELETE FROM Image i WHERE i.product_id = :productId")
    public void deleteByProductId(@Param("productId") String productId);
}
