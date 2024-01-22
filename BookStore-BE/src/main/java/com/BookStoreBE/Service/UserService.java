package com.BookStoreBE.Service;

import com.BookStoreBE.Model.User;
import com.BookStoreBE.Repository.UserRepository;
import com.BookStoreBE.utilityClasses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

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
            return new ApiResponse<User>(
                    401,
                    "fail",
                    "Unauthorized",
                    null
            );
        }
        // set password field to ""
        userByEmail.get().setPassword("");

        // return ApiResponse to controller
        return new ApiResponse<User>(
                200,
                "success",
                "Logged In",
                userByEmail.get()
            );
        }

    public ApiResponse<String> deleteUser(String email, String password){
        Optional<User> delUser=userRepository.findByEmail(email);
        if(delUser.isEmpty()){
            return new ApiResponse<String>(
                    404,
                    "fail",
                    "User not found",
                    "NA"
            );
        }
        if(!password.equals(delUser.get().getPassword())){
            return new ApiResponse<String>(
                    401,
                    "fail",
                    "Unauthorized",
                    "NA"
            );
        }
        userRepository.deleteById(delUser.get().getId());
        return new ApiResponse<String>(
                200,
                "success",
                "Deleted Account",
                delUser.get().getName()
        );
    }


    public ApiResponse<String> updateUser(String name,String email){
        Optional<User> updatedUser=userRepository.findByEmail(email);
        if(updatedUser.isEmpty()){
            return new ApiResponse<String>(
                    404,
                    "fail",
                    "Record not found",
                    "NA"
            );
        }
        updatedUser.get().setName(name);
        if(!name.equals(updatedUser.get().getName())){
            return new ApiResponse<String>(
                    500,
                    "fail",
                    "Internal Server Error",
                    "NA"
            );
        }
        userRepository.deleteById(updatedUser.get().getId());
        User newUser=new User();
        newUser.setName(updatedUser.get().getName());
        newUser.setId(updatedUser.get().getId());
        newUser.setPhone(updatedUser.get().getPhone());
        newUser.setEmail(updatedUser.get().getEmail());
        newUser.setPassword(updatedUser.get().getPassword());
        userRepository.save(newUser);
        return new ApiResponse<String>(
                200,
                "success",
                "Updated Account",
                newUser.getName()
        );
    }

}
