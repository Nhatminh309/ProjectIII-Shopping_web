package org.demo.project.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.demo.project.service.AdminService;
import org.demo.project.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
    @Autowired
    LoginService loginService;
    AdminService adminService;

    // Xử lý yêu cầu GET để hiển thị trang đăng nhập
    @GetMapping("/user/login")
    public String showLoginPage() {
        return "logIn";
    }
    @PostMapping("/user/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        HttpSession session,
                        HttpServletRequest request){  // Thêm tham số HttpServletRequest


        // Hủy tất cả các thuộc tính trong session
        session.invalidate();  // Xóa toàn bộ session

        // Sau khi hủy session cũ, tạo session mới
        session = request.getSession(true); // Tạo session mới

        if(loginService.LoginUser(username, password)){
            String userId = loginService.getIdByUsername(username);
            session.setAttribute("userId", userId); // Lưu userId vào session
            return "redirect:/user/menu";
        }
        if(loginService.LoginManager(username, password)){
            session.setAttribute("role", "manager"); // Lưu role vào session
            return "redirect:/manager/menu";
        }
        if(loginService.LoginAdmin(username, password)){
            session.setAttribute("role", "admin"); // Lưu role vào session
            return "redirect:/admin/menu";
        }
        return "logIn";
    }


}
