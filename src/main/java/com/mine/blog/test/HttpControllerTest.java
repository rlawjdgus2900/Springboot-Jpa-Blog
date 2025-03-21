package com.mine.blog.test;

import org.springframework.web.bind.annotation.*;

// 사용자가 요청 -> 응답(HTML) @Controller
// 사용자가 요청 -> 응답(DATA) @RestController

@RestController
public class HttpControllerTest {

    private static final String TAG = "HttpControllerTest";

    @GetMapping("/http/lombok")
    public String lombokTest(){
        Member member = new Member(1, "mine", "rla369", "email");
        System.out.println(TAG+"getter : "+member.getId());
        member.setId(5000);
        System.out.println(TAG+"setter : "+member.getId());
        return "lombok test";
    }

    @GetMapping("/http/get")
    public String getTest(Member member){
        return "get 요청: " + member.getId() +","+member.getUsername()+","+member.getPassword()+","+member.getEmail();
    }

    @PostMapping("/http/post")//text/plain, application/json
    public String postTest(@RequestBody Member member){ //MessageConverter(스프링부트)
        return "post 요청: " + member.getId() +","+member.getUsername()+","+member.getPassword()+","+member.getEmail();
    }

    @PutMapping("/http/put")
    public String putTest(@RequestBody Member member){
        return "put 요청: " + member.getId() +","+member.getUsername()+","+member.getPassword()+","+member.getEmail();
    }

    @DeleteMapping("/http/delete")
    public String deleteTest(){
        return "delete 요청";
    }

}
