package org.demo.project.repository;

import org.demo.project.dto.request.CartDTO;
import org.demo.project.entity.Cart_item;
import org.hibernate.type.descriptor.converter.spi.JpaAttributeConverter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface Cart_ItemRepository extends JpaRepository<Cart_item, String>{
    // Cập nhật số lượng sản phẩm
    @Modifying
    @Query("update Cart_item c set c.quantity = :new_quantity " +
            "where c.productId = :productIid and c.userId = :userId and c.size = :size")
    public void updateQuantity(@Param("productIid") String productIid,
                               @Param(("userId")) String userId,
                               @Param("size") String size,
                               @Param("new_quantity") Integer new_quantity);
    // Kiểm tra sản phẩm đã tồn tại trong giỏ hàng
    @Query("select case when count(c) > 0 then true else false end from Cart_item c " +
            "where c.productId = :productId and c.userId = :userId and c.size = :size")
    public boolean findProduct(@Param("productId") String productId,
                               @Param(("userId")) String userId,
                               @Param("size") String size);

    @Query("SELECT ci.id, p.product_name, p.mainImage, ci.quantity, ci.size, ci.prize " +
            " FROM Product p " +
            "JOIN Cart_item ci ON p.id = ci.productId " +
            "WHERE ci.userId = :userId")
    List<Object[]> findByUserId(@Param("userId") String userId);

}
