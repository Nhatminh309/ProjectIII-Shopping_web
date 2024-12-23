package org.demo.project.service;

import org.demo.project.entity.Product;
import org.demo.project.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    // Danh sách cách sản phẩm
    public List<Product> getAllProductType(String type){
        return productRepository.getAllProductType(type);
    }

    // Các sản phẩm hot
    public List<Product> getAllHotProduct(){
        return productRepository.getAllHotProduct();
    }

    // Lấy sản phẩm theo id
    public Product getProduct(String id) {
        return productRepository.findById(id).orElse(null);
    }

}
