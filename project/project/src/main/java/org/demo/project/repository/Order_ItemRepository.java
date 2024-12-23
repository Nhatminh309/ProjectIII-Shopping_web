package org.demo.project.repository;

import org.demo.project.entity.Order_Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Order_ItemRepository extends JpaRepository<Order_Item, String> {
    @Query("select o from Order_Item o where o.order_id = :order_id")
    public List<Order_Item> findByOrder_id(@Param("order_id") String order_id);
}
