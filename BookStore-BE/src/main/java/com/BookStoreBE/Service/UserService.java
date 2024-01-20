package com.BookStoreBE.Service;

import com.BookStoreBE.Model.User;
import com.BookStoreBE.Repository.UserRepository;
import com.BookStoreBE.utilityClasses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public void createUser(User user){

        Optional<User> userByEmail=userRepository.findByEmail(user.getEmail());

        if(userByEmail.isPresent()){
            throw new IllegalStateException("User already exit with email "+user.getEmail());
        }

        userRepository.save(user);
    }

    public ApiResponse<User> getUser(String email, String password){
        Optional<User> userByEmail=userRepository.findByEmail(email);
        System.out.println(email);
        if(userByEmail.isEmpty()){
            ApiResponse<User> resApi=new ApiResponse<User>(404,"Fail","User not found",null);
            return resApi;
        }
        System.out.println(userByEmail.get().getPassword());
        System.out.println(password);
        System.out.println(password.equals(userByEmail.get().getPassword()));
        if(!password.equals(userByEmail.get().getPassword())){
            ApiResponse<User> resApi=new ApiResponse<User>(401,"Fail","Unauthorized",null);
            return resApi;
        }
        //Select email
        userByEmail.get().setPassword("");
        ApiResponse<User> resApi=new ApiResponse<User>(200,"Success", "Logged In",userByEmail.get());
        return resApi;


    }

}
