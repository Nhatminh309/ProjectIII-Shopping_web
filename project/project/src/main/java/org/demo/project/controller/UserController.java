package org.demo.project.controller;

import jakarta.servlet.http.HttpSession;
import org.demo.project.dto.request.CartDTO;
import org.demo.project.dto.request.OrderItemDTO;
import org.demo.project.dto.request.OrderRequest;
import org.demo.project.entity.Product;
import org.demo.project.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class UserController {
    @Autowired
    ProductService productService;
    @Autowired
    private QuantityService quantityService;
    @Autowired
    private Cart_ItemService cartItemService;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private LocationService locationService;
    @Autowired
    private OrderService orderService;


    private static final String PROVINCES_API_URL = "https://provinces.open-api.vn/api/p";


    @GetMapping("/user/menu")
    public String menuUser(Model model) {
        // Các sản phẩm nổi bật
        List<Product> hotProducts = productService.getAllHotProduct();
        model.addAttribute("hotProducts", hotProducts);
        return "user/menu";
    }

    // Xem các sản phẩm theo loại
    @GetMapping("/user/viewProduct")
    public String viewProduct(@RequestParam("type") String type, Model model) {
        // Danh sách các sản phẩm
        List<Product> products = productService.getAllProductType(type);
        System.out.println(products);
        model.addAttribute("products", products);

        return "user/viewProduct";
    }

    @PostMapping("/user/addToCart")
    // Thêm sản phẩm vào giỏ hàng
    public String addToCart(@RequestParam("productId") String productId,
                            @RequestParam("size") String size,
                            @RequestParam("quantity") Optional<Integer> quantity,
                            @RequestParam("prize") Integer prize,
                            HttpSession session,
                            Model model) {

        String userId = (String) session.getAttribute("userId");

        if (quantity.isPresent() && !size.isEmpty() && !quantity.get().equals(0) ) {

            if (userId == null) {
                return "redirect:/user/login";
            }

            cartItemService.addToCart(productId, userId, size, quantity.get(), prize);
        } else {

            return "redirect:/user/viewDetail?productId=" + productId;
        }


        return "redirect:/user/viewCart";
    }

    @GetMapping("/user/viewDetail")
    public String viewDetail(@RequestParam("productId") String productId,
                             Model model) {
        Product product = productService.getProduct(productId);
        model.addAttribute("product", product);
        return "user/viewDetail/productDetail";
    }

    @GetMapping("/user/viewCart")
    public String viewCart(HttpSession session, Model model) {
        String userId = (String) session.getAttribute("userId");
        String fullName = (String) session.getAttribute("fullName");
        if (fullName == null) {
            fullName = "Nhập thông tin giao hàng";
        }
        List<CartDTO> cartItems = cartItemService.findByUserId(userId);
        int totalQuantity = cartItems.size();

        model.addAttribute("totalQuantity", totalQuantity);
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("fullName", fullName);

        return "user/viewDetail/viewCart";
    }

    @PostMapping("/user/removeCart")
    public ResponseEntity<Void> removeCart(@RequestBody Map<String, List<String>> payload, HttpSession session){
        // Lấy danh sách các ID sản phẩm bị xóa từ payload
        System.out.println("111");
        List<String> removedProductIds = payload.get("cartItemIds");
        System.out.println(removedProductIds);
        // Thực hiện xóa
        if (removedProductIds!= null) {
            // Lặp qua từng sản phẩm trong giỏ hàng
            for (String removedProductId : removedProductIds) {
                cartItemService.removeCart(removedProductId);
            }
        }
        // Báo cho client biết yêu cầu đã được xử lý thành công nhưng không có nội dung trả về
        return ResponseEntity.noContent().build();
    }


    // Chỉnh sửa thông tin giao hàng
    @GetMapping("/user/shippingInfo")
    public String shippingInfo(){
        return "user/shippingInfo";
    }

    // Hiển thị thông tin giao hàng
    @GetMapping("/user/ShippingInfo")
    public String ShippingInfo(HttpSession session, Model model) {
        String fullName = (String) session.getAttribute("fullName");
        String phone = (String) session.getAttribute("phone");
        String province = (String) session.getAttribute("province");
        String district = (String) session.getAttribute("district");
        String ward = (String) session.getAttribute("ward");
        String address = (String) session.getAttribute("address");

        if(province == null)
            province = "Chọn Thành phố/Tỉnh";
        if(district == null)
            district = "Chọn quận/huyện";
        if(ward == null)
            ward = "Chọn phường/xã";

        model.addAttribute("fullName", fullName);
        model.addAttribute("phone", phone);
        model.addAttribute("province", province);
        model.addAttribute("district", district);
        model.addAttribute("ward", ward);
        model.addAttribute("address", address);
        return "user/shippingInfo";
    }
    // Lấy thông tin giao hàng
    @PostMapping("/user/submitShippingInfo")
    public String submitShippingInfo(@RequestParam("fullName") String fullName,
                                     @RequestParam("phone") String phone,
                                     @RequestParam("province") String province,
                                     @RequestParam("district") String district,
                                     @RequestParam("ward") String ward,
                                     @RequestParam("address") String address,
                                     HttpSession session,
                                     Model model) {
        // Xử lý check thông tin name, phone
        // Lấy tên tỉnh, huyện, xã
        String provinceName = locationService.getProvinceName(province);
        String districtName = locationService.getDistrictName(district);
        String wardName = locationService.getWardName(ward);

        // Tạo địa chỉ đầy đủ
        String shipping_address = wardName + " " + districtName + " " + provinceName;
        session.setAttribute("fullName", fullName); // Lưu fullName vào session
        session.setAttribute("phone", phone);
        session.setAttribute("province", provinceName);
        session.setAttribute("district", districtName);
        session.setAttribute("ward", wardName);
        session.setAttribute("address", address);
        // Chuyển hướng đến trang giỏ hàng
        return "redirect:/user/viewCart";

    }

}
