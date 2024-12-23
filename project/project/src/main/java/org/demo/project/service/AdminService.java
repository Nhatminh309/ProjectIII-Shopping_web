package org.demo.project.service;

import org.demo.project.dto.request.ManagerCreationRequest;
import org.demo.project.dto.request.ManagerUpdateRequest;
import org.demo.project.dto.request.UserCreationRequest;
import org.demo.project.dto.request.UserUpdateRequest;
import org.demo.project.entity.Manager;
import org.demo.project.entity.User;
import org.demo.project.repository.UserRepository;
import org.demo.project.repository.ManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ManagerRepository managerRepository;

    // Service for CRUD User
    public User createRequest(UserCreationRequest request){
        User user = new User();

        if (userRepository.findExistByUser_name(request.getUser_name()))
            throw new RuntimeException("Username already exists");

        if (userRepository.findExistByEmail(request.getEmail()))
            throw new RuntimeException("Email already exists");

        user.setUser_name(request.getUser_name());
        user.setPass_word(request.getPass_word());
        user.setEmail(request.getEmail());

        return userRepository.save(user);
    }

    public List<User> getUser(){
        return userRepository.findAll();
    }

    public List<User> getUser(String user_name, String email){

        return userRepository.findUser(user_name, email);
    }
    public User getUser(String user_name){
        return userRepository.findById(user_name).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User updateUser(String userId, UserUpdateRequest request){
        if (userRepository.findExistByEmail(request.getEmail()))
            throw new RuntimeException("Email already exists");

        User user = getUser(userId);
        user.setPass_word(request.getPass_word());
        user.setEmail(request.getEmail());

        return userRepository.save(user);
    }


    public void deleteUser(String userId){
        userRepository.deleteById(userId);
    }


    // Service for CRUD Manager
    public boolean createProductRequest(ManagerCreationRequest request){
        Manager manager = new Manager();

        if (managerRepository.findExistByUser_name(request.getUser_name()))
            return false;

        manager.setUser_name(request.getUser_name());
        manager.setPass_word(request.getPass_word());

        managerRepository.save(manager);
        return true;
    }

    public List<Manager> getManager(){
        return managerRepository.findAll();
    }

    public Manager getManager(String user_name){
        if (!managerRepository.findExistByUser_name(user_name))
            throw new RuntimeException("Manager does not exist");
        return managerRepository.findManagerByUser_name(user_name);
    }

    public Manager updateManager(String managerId, ManagerUpdateRequest request){

        Manager manager = getManager(managerId);
        manager.setPass_word(request.getPass_word());

        return managerRepository.save(manager);
    }

    public void deleteManager(String managerId){
        managerRepository.deleteById(managerId);
    }



}
