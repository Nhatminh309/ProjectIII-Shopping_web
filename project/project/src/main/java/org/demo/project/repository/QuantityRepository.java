package org.demo.project.repository;

import org.demo.project.entity.Quantity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface QuantityRepository extends JpaRepository<Quantity, String> {

    @Query("select q from Quantity q where q.product_id = :productId ORDER BY " +
            "    CASE q.size " +
            "        WHEN 'S' THEN 1 " +
            "        WHEN 'M' THEN 2 " +
            "        WHEN 'L' THEN 3 " +
            "        WHEN 'XL' THEN 4 " +
            "        WHEN 'XXL' THEN 5 " +
            "        ELSE 6 " +
            "    END")
    public List<Quantity> findQuantityById(@Param("productId") String id);


    @Modifying
    @Query("update Quantity q set q.quantity = :quantity where q.product_id = :id and q.size = :size")
    public void updateQuantity(@Param("id") String id,
                               @Param("size") String size,
                               @Param("quantity") Integer quantity);

    @Query("select q.quantity from Quantity q where q.product_id = :id and q.size = :size")
    public int getQuantity(@Param("id") String id,
                           @Param("size") String size);

}
