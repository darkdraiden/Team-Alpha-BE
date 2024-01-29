package com.BookStoreBE.Controller;


import com.BookStoreBE.Model.User;
import com.BookStoreBE.Security.JwtHelper;
import com.BookStoreBE.Service.UserService;
import com.BookStoreBE.utilityClasses.ApiResponse;
import com.BookStoreBE.utilityClasses.JwtResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping(path = "api/v1/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    JwtHelper jwtHelper;

    @PostMapping(path = "/signup")
    public ResponseEntity<ApiResponse> createUser(@RequestBody User user){
        ApiResponse<User> resultResponse=userService.createUser(user);

        return new ResponseEntity<>(
                resultResponse,
                HttpStatusCode.valueOf(resultResponse.getStatusCode())
        );
    }


    @PostMapping(path = "/login")
    public ResponseEntity<JwtResponse> login(@RequestBody User userBody){

        ApiResponse<User> resultResponse=userService.getUser(userBody.getEmail(), userBody.getPassword());

        String token = jwtHelper.generateToken(resultResponse.getData());

        return new ResponseEntity<>(
                new JwtResponse(resultResponse.getData(),token),
                HttpStatusCode.valueOf(resultResponse.getStatusCode())
        );
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse> deleteUser(@RequestBody User userBody){
        ApiResponse<String> resultResponse=userService.deleteUser(userBody.getEmail(), userBody.getPassword());
        return new ResponseEntity<>(
                resultResponse,
                HttpStatusCode.valueOf(resultResponse.getStatusCode())
        );
    }

    @PutMapping
    public ResponseEntity<ApiResponse> updateUser(@RequestBody User userBody){
        ApiResponse<String> resultResponse=userService.updateUser(userBody.getName(),userBody.getEmail());
        return new ResponseEntity<>(
                resultResponse,
                HttpStatusCode.valueOf(resultResponse.getStatusCode())
        );
    }

}
