package org.demo.project.service;

import org.demo.project.repository.AdminRepository;
import org.demo.project.repository.ManagerRepository;
import org.demo.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ManagerRepository managerRepository;
    @Autowired
    private AdminRepository adminRepository;


    public boolean LoginUser(String username, String password){
        if (userRepository.findExistByUser_nameAndPass_word(username, password))
            return true;
        return false;
    }

    public boolean LoginAdmin(String username, String password){
        if (adminRepository.findExistByUser_nameAndPass_word(username, password))
            return true;
        return false;
    }

    public boolean LoginManager(String username, String password){
        if (managerRepository.findExistByUser_nameAndPass_word(username, password))
            return true;
        return false;
    }

    public String getIdByUsername(String username){
        return userRepository.findIdByUsername(username);
    }
}
