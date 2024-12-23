package org.demo.project.repository;

import org.demo.project.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, String> {
    @Modifying
    @Query("update Product set is_deleted = true where id = :id")
    public void updateIsDeleted(@Param("id") String id);
    @Query("select p from Product p where p.is_deleted = false ORDER BY p.product_name ASC")
    public List<Product> viewAllProducts();
    @Query("select p from Product p where p.id = :id")
    public Product findProductById(@Param("id") String id);

    @Modifying
    @Query("update Product set type = :type, product_name = :name, description = :description,prize = :prize where id = :id")
    public void updateInfo(@Param("id") String id ,
                           @Param("type") String type,
                           @Param("name") String name,
                           @Param("description") String description,
                           @Param("prize") String prize);
    @Modifying
    @Query("update Product set mainImage = :img where id = :id")
    public void updateMainImage(@Param("id") String id,@Param("img") String img);

    @Query("select p from Product p where p.type = :type")
    public List<Product> getAllProductType(@Param("type") String type);

    @Query("select p from Product p ORDER BY p.bought DESC LIMIT 4")
    public List<Product> getAllHotProduct();
}
