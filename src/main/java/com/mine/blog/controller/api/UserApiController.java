package com.mine.blog.controller.api;

import com.mine.blog.config.auth.PrincipalDetail;
import com.mine.blog.dto.ResponseDTO;
import com.mine.blog.model.User;
import com.mine.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserApiController {

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/auth/joinProc")
    public ResponseEntity<ResponseDTO<Integer>> save(@RequestBody User user) {
        try {
            userService.회원가입(user);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDTO<>(HttpStatus.OK.value(), 1));
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseDTO<>(HttpStatus.BAD_REQUEST.value(), -1));
        }
    }


    @PutMapping("/user")
    public ResponseDTO<Integer> update(@RequestBody User user) {
        userService.회원수정(user);

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return new ResponseDTO<>(HttpStatus.OK.value(), 1);
    }


}
