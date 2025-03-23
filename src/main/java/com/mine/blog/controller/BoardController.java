package com.mine.blog.controller;

import com.mine.blog.config.auth.PrincipalDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BoardController {

    @GetMapping("/blog")
    public String index(@AuthenticationPrincipal PrincipalDetail principalDetail) {
        if (principalDetail != null) {
            System.out.println("로그인 사용자 아이디 : " + principalDetail.getUsername());
        } else {
            System.out.println("로그인 안 됨");
        }
        return "index";
    }

    @GetMapping({"", "/"})
    public String rootRedirect() {
        return "redirect:/blog";
    }
}
