package com.mine.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BoardController {

    @GetMapping("/blog")
    public String index(){
        return "index";
    }
    @GetMapping({"", "/"})
    public String rootRedirect(){
        return "redirect:/blog";
    }
}
