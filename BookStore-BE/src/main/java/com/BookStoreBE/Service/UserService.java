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

    public ApiResponse<User> createUser(User user){
        // get user by email if exist
        Optional<User> userByEmail=userRepository.findByEmail(user.getEmail());

        // if email already in use
        if(userByEmail.isPresent()){
            return new ApiResponse<User>(
                    400,
                    "fail",
                    "user already exist with email "+user.getEmail(),
                    null
            );
        }

        // user signed up successfully
        return new ApiResponse<User>(
                201,
                "success",
                "User signed up",
                null
        );
    }

    public ApiResponse<User> getUser(String email, String password){
        // get the user by email
        Optional<User> userByEmail=userRepository.findByEmail(email);

        // check if user exists
        if(userByEmail.isEmpty()){
            return new ApiResponse<User>(
                    404,
                    "fail",
                    "User not found",
                    null
            );
        }

        // check if password is correct
        if(!password.equals(userByEmail.get().getPassword())){
            ApiResponse<User> resApi=new ApiResponse<User>(401,"fail","Unauthorized",null);
            return resApi;
        }
        // set password field to ""
        userByEmail.get().setPassword("");

        // return ApiResponse to controller
        return new ApiResponse<User>(200,"success", "Logged In",userByEmail.get());
    }

}
