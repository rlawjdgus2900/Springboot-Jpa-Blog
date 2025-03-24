package com.mine.blog.controller;

import com.mine.blog.config.auth.PrincipalDetail;
import com.mine.blog.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Controller
public class BoardController {

    @Autowired
    private BoardService boardService;

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
    public String index(Model model, @PageableDefault(size = 3,sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        model.addAttribute("boards", boardService.글목록(pageable));
        return "index";
    }

    @GetMapping("/board/{id}")
    public String findById(@PathVariable int id,Model model){
        model.addAttribute("board", boardService.글상세보기(id));
        return "board/detail";
    }


    @GetMapping("/board/saveForm")
    public String saveForm() {
        return "board/saveForm";
    }


}
