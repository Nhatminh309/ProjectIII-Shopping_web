package org.demo.project.controller;

import jakarta.servlet.http.HttpSession;
import org.demo.project.dto.request.ManagerCreationRequest;
import org.demo.project.dto.request.ManagerUpdateRequest;
import org.demo.project.dto.request.UserCreationRequest;
import org.demo.project.dto.request.UserUpdateRequest;
import org.demo.project.entity.Manager;
import org.demo.project.entity.User;
import org.demo.project.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AdminController {
    @Autowired
    private AdminService adminService;

    @GetMapping("/admin/menu")
    public String menu(HttpSession session) {
        String role = (String) session.getAttribute("role");
        if ("admin".equals(role)) {
            return "admin/menu";
        }
        return "logIn";
    }

    // CRUD User
    @PostMapping("/user/register")
    public String createUser(@RequestParam("username") String username,
                             @RequestParam("email") String email,
                             @RequestParam("password") String password,
                             HttpSession session) {
        String role = (String) session.getAttribute("role");
        if (!("admin".equals(role))) {
            return "logIn";
        }
        UserCreationRequest user = new UserCreationRequest();
        user.setUser_name(username);
        user.setEmail(email);
        user.setPass_word(password);
        User newUser = adminService.createRequest(user);

        session.setAttribute("userId", newUser.getId());
        System.out.println(newUser);
        return "redirect:/user/login";
    }


    @GetMapping("/admin/viewAllUsers")
    public String getUser(Model model, HttpSession session) {
        String role = (String) session.getAttribute("role");
        if (!("admin".equals(role))) {
            return "logIn";
        }
        List<User> users = adminService.getUser();  // Lấy danh sách người dùng từ service
        model.addAttribute("users", users);
        return "admin/viewUser";
    }

    @GetMapping("/admin/findUser")
    public String getUser(@RequestParam("user_name") String user_name,@RequestParam("email") String email, Model model, HttpSession session) {
        String role = (String) session.getAttribute("role");
        if (!("admin".equals(role))) {
            return "logIn";
        }
        List<User> data = adminService.getUser(user_name, email);
        model.addAttribute("users", data);
        return "admin/viewUser";
    }

    @DeleteMapping("/admin/deleteUser")
    String deleteUser(@RequestParam("userId") String userId, RedirectAttributes redirectAttributes, HttpSession session) {
        String role = (String) session.getAttribute("role");
        if (!("admin".equals(role))) {
            return "logIn";
        }
        System.out.println(userId);
        adminService.deleteUser(userId);
        redirectAttributes.addFlashAttribute("message", "Người dùng đã được xóa thành công!");
        return "redirect:/admin/viewAllUsers";
    }

    // CRUD Product Manager
    @GetMapping("/admin/createManager")
    public String createManager(HttpSession session) {
        String role = (String) session.getAttribute("role");
        if (!("admin".equals(role))) {
            return "logIn";
        }
        return "admin/createManager";
    }

    @PostMapping("/admin/creatManager")
    public String createManager(@RequestParam("username") String username,
                                @RequestParam("password") String password,
                                @RequestParam("confirm-password") String confirmPassword,
                                Model model,
                                HttpSession session) {
        String role = (String) session.getAttribute("role");
        if (!("admin".equals(role))) {
            return "logIn";
        }

        if (!password.equals(confirmPassword)) {
            model.addAttribute("errorMessage", "Mật khẩu không trùng khớp!");
            return "admin/createManager";
        }

        ManagerCreationRequest request = new ManagerCreationRequest(username, password);
        if (!adminService.createProductRequest(request)) {
            model.addAttribute("errorMessage", "Username đã tồn tại!");
            return "admin/createManager";
        }

        model.addAttribute("successMessage", "Tạo tài khoản thành công!"); // Có thể thêm thông báo thành công nếu cần
        return "redirect:/admin/viewAllManager"; // Chuyển hướng đến trang xem tất cả quản lý
    }

    @GetMapping("/admin/viewAllManager")
    public String getManager(Model model, HttpSession session) {
        String role = (String) session.getAttribute("role");
        if (!("admin".equals(role))) {
            return "logIn";
        }
        List<Manager> managers = adminService.getManager();
        model.addAttribute("managers", managers);
        return "admin/viewManager";
    }

    @GetMapping("/admin/findByManger")
    public String getManager(@RequestParam("manager") String manager, Model model, HttpSession session) {
        String role = (String) session.getAttribute("role");
        if (!("admin".equals(role))) {
            return "logIn";
        }
        Manager data = adminService.getManager(manager);
        model.addAttribute("managers", data);
        return "admin/viewManager";
    }


    @DeleteMapping("/admin/deleteManager")
    String deleteManager(@RequestParam("managerId") String managerId, RedirectAttributes redirectAttributes, HttpSession session) {
        String role = (String) session.getAttribute("role");
        if (!("admin".equals(role))) {
            return "logIn";
        }
        adminService.deleteManager(managerId);
        redirectAttributes.addFlashAttribute("message", "Người dùng đã được xóa thành công!");
        return "redirect:/admin/viewAllManager";
    }
}
