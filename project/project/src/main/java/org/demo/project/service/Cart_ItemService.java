package org.demo.project.service;

import jakarta.servlet.http.HttpSession;
import org.demo.project.dto.request.CartDTO;
import org.demo.project.entity.Cart_item;
import org.demo.project.repository.Cart_ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class Cart_ItemService {
    @Autowired
    Cart_ItemRepository cart_ItemRepository;

    public boolean findProduct(String productId, String userId, String size){
        return cart_ItemRepository.findProduct(productId, userId, size);
    }

    public void updateQuantity(String productId, String userId, String size, int quantity){
        cart_ItemRepository.updateQuantity(productId, userId, size, quantity);
    }

    public void addToCart(String productId, String userId, String size, int quantity, Integer prize){
        Cart_item cart_item = new Cart_item();
        cart_item.setProductId(productId);
        cart_item.setUserId(userId);
        cart_item.setSize(size);
        cart_item.setQuantity(quantity);
        cart_item.setPrize(prize);
        cart_ItemRepository.save(cart_item);
    }

    public List<CartDTO> convertToCartDTO(List<Object[]> resultList) {
        return resultList.stream()
                .map(row -> new CartDTO(
                        (String) row[0],  // id
                        (String) row[1],  // name
                        (String) row[2],  // img
                        (Integer) row[3], // quantity
                        (String) row[4],  // size
                        (Integer) row[5]  // price
                ))
                .collect(Collectors.toList());
    }

    public List<CartDTO> findByUserId(String userId){
        List<Object[]> tmp = cart_ItemRepository.findByUserId(userId);
        return convertToCartDTO(tmp);
    }

    public void removeCart(String cartItemID){
        cart_ItemRepository.deleteById(cartItemID);
    }


    // Lấy ra thông tin card qua id
    public Cart_item getCartItemById(String cartItemID){
        return cart_ItemRepository.findById(cartItemID).orElse(null);
    }

}
