package com.mine.blog.controller.api;

import com.mine.blog.dto.ResponseDTO;
import com.mine.blog.model.RoleType;
import com.mine.blog.model.User;
import com.mine.blog.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserApiController {

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @PostMapping("/auth/joinProc")
    public ResponseDTO<Integer> save(@RequestBody User user){
        String rawPassword = user.getPassword();
        String encodedPassword = encoder.encode(rawPassword);
        user.setPassword(encodedPassword);
        user.setRole(RoleType.USER);
        userService.회원가입(user);
        return new ResponseDTO<>(HttpStatus.OK.value(), 1);
    }



}
