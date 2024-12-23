package org.demo.project.repository;


import org.demo.project.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
    @Query("select o from Order o where o.status = :status")
    public List<Order> findAllOrder(String status);
    @Modifying
    @Query("update Order set status = :status where id = :order_id")
    public void updateStatus(String order_id, String status);

}
