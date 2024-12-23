package org.demo.project.service;

import org.demo.project.entity.Product;
import org.demo.project.entity.Quantity;
import org.demo.project.repository.ProductRepository;
import org.demo.project.repository.QuantityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class QuantityService {
    @Autowired
    private QuantityRepository quantityRepository;
    @Autowired
    private ProductRepository productRepository;


    // Tìm số lượng sản phẩm với id
    public List<Quantity> getQuantity(String productId) {
        return quantityRepository.findQuantityById(productId);
    }

    // Cập nhật số lượng
    @Transactional
    public void updateQuantity(String Id,
                               String size,
                               Integer quantity) {
        quantityRepository.updateQuantity(Id, size, quantity);
    }

    // Lấy ra số lượng sản phẩm
    public int getQuantity(String productId, String size) {
        return quantityRepository.getQuantity(productId, size);
    }
}
