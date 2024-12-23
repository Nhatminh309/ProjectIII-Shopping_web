package org.demo.project.controller;

import jakarta.servlet.http.HttpSession;
import org.demo.project.dto.request.ProductRequest;
import org.demo.project.entity.Image;
import org.demo.project.entity.Product;
import org.demo.project.entity.Quantity;
import org.demo.project.service.ManagerService;
import org.demo.project.service.QuantityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ManagerController {

    @Autowired
    ManagerService managerService;
    @Autowired
    QuantityService quantityService;

    private static final Path CURRENT_FOLDER = Paths.get(System.getProperty("user.dir"));

    // Lấy giá trị role từ session trong mỗi phương thức yêu cầu
    private String getRole(HttpSession session) {
        return (String) session.getAttribute("role");
    }

    @GetMapping("/manager/menu")
    public String menu(HttpSession session) {
        String role = getRole(session);
        if ("manager".equals(role)) {
            return "manager/menu";
        }
        return "logIn";
    }

    @GetMapping("/manager/viewAllProduct")
    public String viewAllProduct(Model model, HttpSession session) {
        String role = getRole(session);
        if ("manager".equals(role)) {
            List<Product> products = managerService.viewAllProducts();
            model.addAttribute("products", products);
            return "manager/viewAllProduct";
        }
        return "logIn";
    }

    @DeleteMapping("/manager/deleteProduct")
    public String deleteProduct(@RequestParam("productId") String productId, HttpSession session) {
        String role = getRole(session);
        if ("manager".equals(role)) {
            managerService.deleteProduct(productId);
            return "redirect:/manager/viewAllProduct";
        }
        return "logIn";
    }

    @PostMapping("/manager/redirectToUpdate")
    public String redirectToUpdate(@RequestParam("productId") String productId, Model model, HttpSession session) {
        String role = getRole(session);
        if ("manager".equals(role)) {
            Product product = managerService.getProduct(productId);
            model.addAttribute("product", product);

            List<Quantity> quantity = quantityService.getQuantity(productId);
            model.addAttribute("quantity", quantity);
            return "manager/updateProduct";
        }
        return "logIn";
    }

    @PostMapping("/manager/updateInfoProduct")
    public String updateInfoProduct(@RequestParam("productId") String productId,
                                    @RequestParam("productName") String productName,
                                    @RequestParam("productDescription") String productDescription,
                                    @RequestParam("productType") String productType,
                                    @RequestParam("productPrize") String productPrize,
                                    @RequestParam("mainImage") MultipartFile mainImage,
                                    @RequestParam("otherImages") List<MultipartFile> otherImages, HttpSession session) throws IOException {
        String role = getRole(session);
        if ("manager".equals(role)) {
            Path staticPath = Paths.get("static");
            Path imagePath = Paths.get("images");

            if (!Files.exists(CURRENT_FOLDER.resolve(staticPath).resolve(imagePath))) {
                Files.createDirectories(CURRENT_FOLDER.resolve(staticPath).resolve(imagePath));
            }

            if (mainImage != null) {
                String mainImagePath = saveImage(mainImage, staticPath, imagePath);
                managerService.updateMainImage(productId, mainImagePath);
            }

            if (otherImages != null && !otherImages.isEmpty()) {
                managerService.deleteByProductId(productId);
                for (MultipartFile otherImage : otherImages) {
                    String otherImagePath = saveImage(otherImage, staticPath, imagePath);
                    Image img = new Image();
                    img.setProduct_id(productId);
                    img.setUrl(otherImagePath);
                    managerService.addImage(img);
                }
            }

            Product product = managerService.getProduct(productId);
            if (product != null) {
                managerService.updateProduct(productId, productName, productDescription, productType, productPrize);
            }

            return "redirect:/manager/viewAllProduct";
        }
        return "logIn";
    }

    @PostMapping("/manager/updateQuantity")
    public String updateQuantity(@RequestParam("productId") String productId,
                                 @RequestParam("sizeS") Integer sizeS,
                                 @RequestParam("sizeM") Integer sizeM,
                                 @RequestParam("sizeL") Integer sizeL,
                                 @RequestParam("sizeXL") Integer sizeXL,
                                 @RequestParam("sizeXXL") Integer sizeXXL, HttpSession session) {
        String role = getRole(session);
        if ("manager".equals(role)) {
            List<Quantity> quantity = quantityService.getQuantity(productId);
            if (quantity != null) {
                if (!quantity.get(0).getQuantity().equals(sizeS)) {
                    quantityService.updateQuantity(productId, "S", sizeS);
                }
                if (!quantity.get(1).getQuantity().equals(sizeM)) {
                    quantityService.updateQuantity(productId, "M", sizeM);
                }
                if (!quantity.get(2).getQuantity().equals(sizeL)) {
                    quantityService.updateQuantity(productId, "L", sizeL);
                }
                if (!quantity.get(3).getQuantity().equals(sizeXL)) {
                    quantityService.updateQuantity(productId, "XL", sizeXL);
                }
                if (!quantity.get(4).getQuantity().equals(sizeXXL)) {
                    quantityService.updateQuantity(productId, "XXL", sizeXXL);
                }
            }
            return "redirect:/manager/viewAllProduct";
        }
        return "logIn";
    }

    @GetMapping("/manager/addProduct")
    public String addProduct(HttpSession session) {
        String role = getRole(session);
        if ("manager".equals(role)) {
            return "manager/addProduct";
        }
        return "logIn";
    }

    @PostMapping("/manager/addProduct")
    public String addProduct(@RequestParam("name") String name,
                             @RequestParam("description") String description,
                             @RequestParam("type") String type,
                             @RequestParam("prize") Integer prize,
                             @RequestParam("sizeS") Integer sizeS,
                             @RequestParam("sizeM") Integer sizeM,
                             @RequestParam("sizeL") Integer sizeL,
                             @RequestParam("sizeXL") Integer sizeXL,
                             @RequestParam("sizeXXL") Integer sizeXXL,
                             @RequestParam("mainImage") MultipartFile mainImage,
                             @RequestParam("otherImages") List<MultipartFile> otherImages, HttpSession session) throws IOException {
        String role = getRole(session);
        if ("manager".equals(role)) {
            Path staticPath = Paths.get("static");
            Path imagePath = Paths.get("images");

            if (!Files.exists(CURRENT_FOLDER.resolve(staticPath).resolve(imagePath))) {
                Files.createDirectories(CURRENT_FOLDER.resolve(staticPath).resolve(imagePath));
            }

            String mainImagePath = saveImage(mainImage, staticPath, imagePath);

            List<String> otherImagePaths = new ArrayList<>();
            if (otherImages != null && !otherImages.isEmpty()) {
                for (MultipartFile otherImage : otherImages) {
                    String otherImagePath = saveImage(otherImage, staticPath, imagePath);
                    otherImagePaths.add(otherImagePath);
                }
            }

            ProductRequest productRequest = new ProductRequest();
            productRequest.setProduct_name(name);
            productRequest.setDescription(description);
            productRequest.setType(type);
            productRequest.setPrize(prize);
            productRequest.setSizeS(sizeS);
            productRequest.setSizeM(sizeM);
            productRequest.setSizeL(sizeL);
            productRequest.setSizeXL(sizeXL);
            productRequest.setSizeXXL(sizeXXL);
            productRequest.setMainImage(mainImagePath);
            if (!otherImagePaths.isEmpty()) {
                productRequest.setImages(otherImagePaths);
            }

            managerService.addProduct(productRequest);
            return "redirect:/manager/viewAllProduct";
        }
        return "logIn";
    }

    private String saveImage(MultipartFile image, Path staticPath, Path imagePath) throws IOException {
        String uniqueFileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
        Path filePath = CURRENT_FOLDER.resolve(staticPath).resolve(imagePath).resolve(uniqueFileName);
        try (OutputStream os = Files.newOutputStream(filePath)) {
            os.write(image.getBytes());
        }

        return imagePath.resolve(uniqueFileName).toString().replace("\\", "/");
    }

    @ExceptionHandler(IOException.class)
    public String handleIOException(IOException e) {
        return "error";
    }
}
