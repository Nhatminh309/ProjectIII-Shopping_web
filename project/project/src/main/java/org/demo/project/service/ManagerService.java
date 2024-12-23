package org.demo.project.service;

import org.demo.project.dto.request.ProductRequest;
import org.demo.project.entity.Image;
import org.demo.project.entity.Product;
import org.demo.project.entity.Quantity;
import org.demo.project.repository.ImageRepository;
import org.demo.project.repository.ProductRepository;
import org.demo.project.repository.QuantityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class ManagerService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private QuantityRepository quantityRepository;
    @Autowired
    private ImageRepository imageRepository;

    // Xem sản phẩm
    public List<Product> viewAllProducts() {
        return productRepository.viewAllProducts();
    }

    // Xoa san pham
    @Transactional
    public void deleteProduct(String productId){
        productRepository.updateIsDeleted(productId);
    }

    // Tìm sản phẩm với id
    public Product getProduct(String productId) {
        return productRepository.findProductById(productId);
    }

    // Cập nhật thông tin sản phẩm
    @Transactional
    public void updateProduct(String productId,
                              String productName,
                              String productDescription,
                              String productType,
                              String productPrize) {
        productRepository.updateInfo(productId, productType, productName, productDescription, productPrize);
    }
    // Cập nhật hình ảnh chính sản phẩm
    @Transactional
    public void updateMainImage(String id, String mainImg){
        productRepository.updateMainImage(id, mainImg);
    }
    // Xóa hình ảnh theo Product_Id
    @Transactional
    public void deleteByProductId(String productId) {
        imageRepository.deleteByProductId(productId);
    }

    // Thêm hình ảnh
    public void addImage(Image image) {
        imageRepository.save(image);
    }

    // Thêm sản phẩm
    public void addProduct(ProductRequest productRequest) {
        Product product = new Product();
        product.setProduct_name(productRequest.getProduct_name());
        product.setDescription(productRequest.getDescription());
        product.setType(productRequest.getType());
        product.setPrize(productRequest.getPrize());
        product.setCreated_at(LocalDate.now());
        product.setMainImage(productRequest.getMainImage());
        Product saveProduct = productRepository.save(product);

        // Lấy ra id vừa tạo
        String productID = saveProduct.getId();

        // Thêm dữ liệu vào bảng Quantity cho từng kích thước
        for (String size : new String[]{"S", "M", "L", "XL", "XXL"}) {
            Quantity quantity = new Quantity(); // Tạo một đối tượng mới cho mỗi kích thước
            quantity.setProduct_id(productID);
            quantity.setSize(size);
            // Set the corresponding quantity for each size
            switch (size) {
                case "S":
                    quantity.setQuantity(productRequest.getSizeS());
                    break;
                case "M":
                    quantity.setQuantity(productRequest.getSizeM());
                    break;
                case "L":
                    quantity.setQuantity(productRequest.getSizeL());
                    break;
                case "XL":
                    quantity.setQuantity(productRequest.getSizeXL());
                    break;
                case "XXL":
                    quantity.setQuantity(productRequest.getSizeXXL());
                    break;
            }
            quantityRepository.save(quantity); // Lưu từng đối tượng quantity vào cơ sở dữ liệu
        }

        // Thêm dữ liệu vào bảng images

        List<String> images = productRequest.getImages();
        System.out.println("abc");
        System.out.println(images);
        if (images != null && !images.isEmpty()) {
            for (String imageUrl : images) {
                Image image = new Image();
                image.setProduct_id(productID); // Assuming productID is already defined
                image.setUrl(imageUrl);

                // Save the image entity to the database
                imageRepository.save(image); // Assuming you have an imageRepository for CRUD operations
            }
        }

    }
}
